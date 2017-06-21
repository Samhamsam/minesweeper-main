package de.htwg.se.minesweeper.aview.tui;

import de.htwg.se.minesweeper.controller.IAkkaController;
import de.htwg.se.minesweeper.controller.IController;
import de.htwg.se.minesweeper.controller.impl.messages.NewSettingRequest;
import de.htwg.se.minesweeper.controller.impl.messages.RevealCellRequest;
import de.htwg.se.minesweeper.controller.impl.messages.ScannerRequest;
import de.htwg.se.minesweeper.controller.impl.messages.SetFlagRequest;
import de.htwg.se.minesweeper.controller.impl.messages.ShowHelpTextRequest;
import de.htwg.se.minesweeper.controller.impl.messages.UpdateRequest;
import de.htwg.se.minesweeper.model.Cell;
import de.htwg.se.minesweeper.model.Grid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.stream.impl.fusing.Grouped;
import akka.stream.impl.fusing.Log;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

import static de.htwg.se.minesweeper.controller.IController.State.*;

public class TUI extends AbstractActor {

	private static final Logger LOGGER = LogManager.getRootLogger(); // LogManager.getLogger();

	private static final String HELP_COMMAND = "h";
	private static final String NEW_GAME_COMMAND = "n";
	private static final String CHANGE_SETTINGS_COMMAND = "c";
	private static final String QUIT_COMMAND = "q";

	private String lastUserInput = "";

	//private IAkkaController controller;
	ActorRef controller;
	
	public TUI(final ActorRef controller) {
		this.controller = controller;
	}
	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(ShowHelpTextRequest.class , s->{
					LOGGER.info(s.helpText);;
				})
				.match(ScannerRequest.class , s->{
					processInput(s.input);
				})
				.match(UpdateRequest.class, s->{
					printTUIImpl(s.state,s.elapsedTimeSeconds,s.getHelpText,s.grid);
				})
				.build();
	}

	public boolean processInput(String input) {
		LOGGER.info("Enter INput Area");
		lastUserInput = input;
		List<String> inputParts = Arrays.asList(input.split(","));

		String userInput = inputParts.get(0);
		
		switch (userInput) {

		case QUIT_COMMAND:
			return runQuitCommand();

		case NEW_GAME_COMMAND:
			newGameAction();
			break;

		case HELP_COMMAND:
			controller.tell(HELP_TEXT, self());
			break;

		case CHANGE_SETTINGS_COMMAND:
			runSettingsAction(inputParts);
			break;

		default:
			playRoundAction(inputParts);
			break;

		}
		
		System.out.println(self().path());
		return true;
		
	}

	private boolean runQuitCommand() {
		controller.tell(IController.State.EXIT_GAME, self());
		return false; // quit loop in main program
	}

/*	private void showHelpAction() {
		
	}*/

	private void newGameAction() {
		controller.tell(IController.State.NEW_GAME, self());
	}

	private void playRoundAction(List<String> inputParts) {
		controller.tell(IController.State.INFO_TEXT, self());
		
		if (inputParts.size() == 2) {
			revealCell(inputParts);
		} else if (inputParts.size() == 3) {
			setFlag(inputParts);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private void setFlag(List<String> answerAsList) {
		try {
			int row = Integer.parseInt(answerAsList.get(1));
			int col = Integer.parseInt(answerAsList.get(2));

			controller.tell(new SetFlagRequest(col, row), self());
		} catch (Exception e) {
			controller.tell(IController.State.ERROR, self());
			LOGGER.error(e);
		}
	}

	private void revealCell(List<String> answerAsList) {

		int row = Integer.parseInt(answerAsList.get(0));
		int col = Integer.parseInt(answerAsList.get(1));
		
		controller.tell(new RevealCellRequest(col, row),self());//revealCell(row, col);
	}

	private void runSettingsAction(List<String> list) {

		try {
			int numRowsAndColumns = Integer.parseInt(list.get(1));
			int numberOfMines = Integer.parseInt(list.get(2));
			//controller.commitNewSettingsAndRestart(numRowsAndColumns, numberOfMines);
			controller.tell(new NewSettingRequest(numRowsAndColumns,numberOfMines, null), self());
		
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	
	public void printTUIImpl(IController.State state, long time, String helpText,Grid grid){

		if (state.equals(ERROR)) {
			LOGGER.error("NOT A NUMBER!");
			return;
		}
		// show this in every step? HELP_TEXT..
		LOGGER.info(getGridAsString(grid.getCells(), grid.getNumberOfRows()));

		if ("".equals(lastUserInput)) {
			LOGGER.info("You typed: " + lastUserInput + "\n");
		}

		switch (state) {

		case GAME_LOST:
			LOGGER.info("You Lost!");
			break;

		case GAME_WON:
			LOGGER.info("You Won! " + time + " Points!");
			break;

		case HELP_TEXT:
			LOGGER.info(helpText);
			break;

		case CHANGE_SETTINGS_ACTIVATED:
			LOGGER.info("Set number of column/row and mines:");
			break;

		case CHANGE_SETTINGS_SUCCESS:
			LOGGER.info("You set row/column to: " + grid.getNumberOfRows() + " and mines to: "
					+ grid.getNumberOfMines());
			break;

		case INFO_TEXT: // or status == 0, running? default?
		default:
			LOGGER.info("Type:\n\tx,x | x is a number between 0 and 9 (row, column) to reveal field.\n"
					+ "\tf,x,x | Same as above, but only put / remove a flag at this position.\n" + "\tOr press "
					+ HELP_COMMAND + " to get more help.");
		}

		if (state == GAME_LOST || state == GAME_WON) {
			LOGGER.info("New Game? Type: n");
		}
	}
	
	private String getGridAsString(List<Cell> cells, int numberOfRows) {
		StringBuilder result = new StringBuilder();
		final List<Cell> allCells = cells;
	
		for (int row = 0; row < numberOfRows; row++) {
			final int currentRow = row; // to use it in Lambda expression
			allCells.stream().filter(cell -> cell.getPosition().getRow() == currentRow)
					.forEach(cell -> result.append(cell.toString()).append(" "));
	
			result.append("\n");
		}
	
		return result.toString();
	}

	/*public String printTUIAsString() {
		controller.tell("printTUIAsString", sender);
	}
	public String printTUIAsStringImpl(PrintTUIRequest printTUIRequest) {
		final IController.State state = printTUIRequest.state;

		if (state.equals(ERROR)) {
			return "NOT A NUMBER!";
		}

		final StringBuilder result = new StringBuilder(printTUIRequest.gridAsString);

		if ("".equals(lastUserInput)) {
			result.append("You typed: " + lastUserInput + "\n");
		}

		switch (state) {

		case GAME_LOST:
			result.append("You Lost!");
			break;

		case GAME_WON:
			result.append("You Won! " + printTUIRequest.elapsedTimeSeconds + " Points!");
			break;

		case HELP_TEXT:
			result.append(printTUIRequest.getHelpText);
			break;

		case CHANGE_SETTINGS_ACTIVATED:
			result.append("Set number of column/row and mines:");
			break;

		case CHANGE_SETTINGS_SUCCESS:
			result.append("You set row/column to: " + printTUIRequest.numberOfRows + " and mines to: "
					+ printTUIRequest.numberOfMines);
			break;

		case INFO_TEXT: // or status == 0, running? default?
		default:
			result.append("Type:\n\tx,x | x is a number between 0 and 9 (row, column) to reveal field.\n"
					+ "\tf,x,x | Same as above, but only put / remove a flag at this position.\n" + "\tOr press "
					+ HELP_COMMAND + " to get more help.");
		}

		if (state == GAME_LOST || state == GAME_WON) {
			result.append("New Game? Type: n");
		}

		return result.toString();
	}*/


}
