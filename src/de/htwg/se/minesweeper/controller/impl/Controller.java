package de.htwg.se.minesweeper.controller.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.routing.BroadcastGroup;
import de.htwg.se.minesweeper.controller.IController;
import de.htwg.se.minesweeper.controller.impl.messages.NewSettingRequest;
import de.htwg.se.minesweeper.controller.impl.messages.RevealCellRequest;
import de.htwg.se.minesweeper.controller.impl.messages.SetFlagRequest;
import de.htwg.se.minesweeper.controller.impl.messages.ShowHelpTextRequest;
import de.htwg.se.minesweeper.controller.impl.messages.UpdateRequest;
import de.htwg.se.minesweeper.model.Cell;
import de.htwg.se.minesweeper.model.Grid;
import de.htwg.se.minesweeper.persistence.IGridDao;

/**
 * @author Niels Boecker
 * @author Mark Unger
 * @author Aiham Abousaleh
 */
public class Controller extends AbstractActor implements IController {
	
	private static final String DEFAULT_DIFFICULTY = "intermediate";
	private static final String DEFAULT_SIZE = "small";
	private Grid grid;
	private State state;

	// for time measuring
	private long timeOfGameStartMills;
	private long elapsedTimeSeconds;
	private IGridDao dao;
	private Set<IGridDao> allOfThem;
	ActorRef notifyRef;
	
	public Controller() {		
		startNewGame();
		List<String> paths = Arrays.asList("/user/mainActor/tui","/user/mainActor/gui");
		notifyRef =
				getContext().actorOf(new BroadcastGroup(paths).props(), "notifyRef");
		setStateAndNotifyObservers(State.FIRST_START);
	}
	


	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.matchEquals(State.EXIT_GAME, s->{
					quit();
				})
				.matchEquals(State.NEW_GAME, s->{
					startNewGame();
					notifyObservers();
				})
				.matchEquals(State.GAME_LOST, s->{
					setStateAndNotifyObservers(State.GAME_LOST);
				})
				.matchEquals(State.HELP_TEXT, s->{
					setStateAndNotifyObservers(State.HELP_TEXT);
				})
				.matchEquals(State.INFO_TEXT, s->{
					setStateAndNotifyObservers(State.INFO_TEXT);
				})
				.matchEquals(State.ERROR, s->{
					this.state = State.ERROR;
				})
/*				.matchEquals("loadDB", s->{
					loadFromDB();
				})
				.matchEquals("safeDB", s->{
					loadFromDB();
				})*/
				.match(SetFlagRequest.class, s->{
					toggleFlag(s.row, s.col);
				})
				.match(RevealCellRequest.class, s->{
					revealCell(s.row,s.col);
				})
				.match(NewSettingRequest.class, s->{
					commitNewSettingsAndRestart(s.numRowsAndColumns, s.numberOfMines);
				})
				.build();
	}
	
	@Override
	public IGridDao chooseDB(int db) {
		List<IGridDao> list = new ArrayList<IGridDao>(allOfThem);
		try {
			this.dao = list.get(db);
 		} catch (Exception e) {
			state = State.ERROR;
		} finally {
			notifyObservers();
		}
		System.out.println(list.get(db));
		return this.dao  ;
	}

	@Override
	public void quit() {
		System.exit(0);
	}

	@Override
	public void startNewGame() {
		startNewGame(DEFAULT_SIZE, DEFAULT_DIFFICULTY);
	}

	@Override
	public void startNewGame(String gridSize, String difficulty) {
		int numberOfRowsAndCols;
		int numberOfMines;

		switch (gridSize) {
		case "large":
			numberOfRowsAndCols = 17;
			break;
		case "small":
			numberOfRowsAndCols = 10;
			break;
		case "medium":
		default:
			numberOfRowsAndCols = 12;
		}

		switch (difficulty) {
		case "expert":
			numberOfMines = (int) 2.0 * numberOfRowsAndCols;
			break;
		case "beginner":
			numberOfMines = (int) (0.8 * numberOfRowsAndCols);
			break;
		case "intermediate":
		default:
			numberOfMines = (int) (1.5 * numberOfRowsAndCols);
		}

		startNewGame(numberOfRowsAndCols, numberOfMines);
		//notifyObservers();
	}

	@Override
	public void startNewGame(int numberOfRowsAndCols, int numberOfMines) {
		try {
			this.grid = new Grid(numberOfRowsAndCols, numberOfRowsAndCols, numberOfMines);
			this.state = State.NEW_GAME;
			this.timeOfGameStartMills = System.currentTimeMillis();
		} 
		catch(Exception e) {
			
			state = State.ERROR;
		}
	}

	@Override
	public void loadFromDB() {
		try {
			List<Grid> allGrids = dao.getAllGrids();

			for (Grid grid : allGrids) {

				this.grid = dao.getGridById(grid.getId());

			}
			this.state = State.LOAD_GAME;
		} catch (Exception e) {
			state = State.ERROR;
		} finally {
			notifyObservers();
		}

	}

	@Override
	public void saveToDB() {
		try {

			dao.saveOrUpdateGrid(this.grid);

			this.state = State.LOAD_GAME;
		} catch (Exception e) {
			state = State.ERROR;
		} finally {
			notifyObservers();
		}
	}

	@Override
	public void commitNewSettingsAndRestart(int numberOfRowsAndCols, int numberOfMines) {
		startNewGame(numberOfRowsAndCols, numberOfMines);
		state = State.CHANGE_SETTINGS_SUCCESS;
		notifyObservers();
	}

	@Override
	public void revealCell(int row, int col) {
		revealCell(this.grid.getCellAt(row, col));

	}

	@Override
	public void revealCell(Cell cell) {
		// ignore if game is not running
		if (getState() == State.GAME_LOST || getState() == State.GAME_WON)
			return;

		this.state = State.REVEAL_CELL;

		// potentially recursive revealing, or winning / losing
		recursiveRevealCell(cell);

		// notify observers only once
		notifyObservers();

	}

	/**
	 * Use this inner method for the recursive calls to prevent notifying
	 * observers after each reveal.
	 */
	@Override
	public void recursiveRevealCell(Cell cell) {
		// ignore if cell is revealed
		if (cell == null || cell.isRevealed())
			return;

		// never explode in first round
		if (isFirstRound() && cell.hasMine()) {
			System.err.println("Prevented first round explosion.");
			this.grid = new Grid(this.grid.getNumberOfRows(), this.grid.getNumberOfColumns(),
					this.grid.getNumberOfMines());
			Cell cellAtSamePosition = this.grid.getCellAt(cell.getPosition());
			recursiveRevealCell(cellAtSamePosition);
			return;
		}

		// reveal cell
		cell.setRevealed(true);

		// check if lost
		if (cell.hasMine()) {
			setElapsedTime();
			this.state = State.GAME_LOST; // notifyObservers handled by wrapping
											// revealCell() method
			return;
		}

		// check if won
		if (allCellsAreRevealed()) {
			setElapsedTime();
			this.state = State.GAME_WON; // notifyObservers handled by wrapping
											// revealCell() method
			return;
		}

		// check if we can propagate revealing
		if (cell.getSurroundingMines() == 0) {
			final List<Cell> neighbors = this.grid.getAllNeighbors(cell);
			for (Cell neighbor : neighbors) {
				recursiveRevealCell(neighbor);
			}
		}

	}

	@Override
	public boolean isFirstRound() {
		return grid.getNumberOfRevealedCells() == 0;
	}

	private void setElapsedTime() {
		long elapsedTimeNanos = System.nanoTime() - timeOfGameStartMills;
		elapsedTimeSeconds = TimeUnit.SECONDS.convert(elapsedTimeNanos, TimeUnit.NANOSECONDS);
	}

	@Override
	public void toggleFlag(int row, int col) {
		final Cell cell = this.grid.getCellAt(row, col);

		if (cell == null || getState() == State.GAME_LOST || getState() == State.GAME_WON)
			return;

		cell.setFlagged(!cell.isFlagged());
		setStateAndNotifyObservers(State.TOGGLE_FLAG);
	}

	@Override
	public boolean allCellsAreRevealed() {
		return grid.getTotalNumberOfCells() == grid.getNumberOfRevealedCells() + grid.getNumberOfMines();
	}

	/**
	 * If a game is currently running, notify observers. Else, start new game
	 * with standard values.
	 */
	@Override
	public void touch() {
		if (this.grid != null){
			notifyObservers();
		}
		else
			startNewGame();
	}

	@Override
	public void setStateAndNotifyObservers(State state) {
		this.state = state;
		notifyObservers();
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public String getHelpText() {
		return "(TUI:n) GUI: Menu	->	New Game: 	This command starts a new game. (reset)\n"
				+ "(TUI:q) GUI: Menu	->	Quit:		This command ends the Game and close it\n"
				+ "(TUI:c) GUI: Menu	->	Settings:	This command sets the number for column/row and mines\n"
				+ "(TUI:h) GUI: ?	        ->	Help:		This command shows the help text";
	}

	@Override
	public Grid getGrid() {
		return grid;
	}

	@Override
	public long getElapsedTimeSeconds() {
		return elapsedTimeSeconds;
	}
	
	private void notifyObservers(){
		notifyRef.tell(new UpdateRequest(getState(), getElapsedTimeSeconds(), getHelpText(), getGrid()), self());
	}
	


}
