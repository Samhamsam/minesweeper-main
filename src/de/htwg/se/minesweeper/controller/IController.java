package de.htwg.se.minesweeper.controller;

import de.htwg.se.minesweeper.designpattern.observer.IObservable;
import de.htwg.se.minesweeper.model.Cell;
import de.htwg.se.minesweeper.model.Grid;
import de.htwg.se.minesweeper.persistence.IGridDao;

/**
 * @author Niels Boecker
 * @author Mark Unger
 * @author Aiham Abousaleh
 */
public interface IController extends IObservable {

	void quit();

	void startNewGame();

	void startNewGame(String gridSize, String difficulty);

	void startNewGame(int numberOfRowsAndCols, int numberOfMines);

	void commitNewSettingsAndRestart(int numberOfRowsAndCols, int numberOfMines);

	void revealCell(int row, int col);

	void revealCell(Cell cell);

	void recursiveRevealCell(Cell cell);

	boolean isFirstRound();

	void toggleFlag(int row, int col);

	boolean allCellsAreRevealed();

	void touch();

	void setStateAndNotifyObservers(State state);

	State getState();

	String getHelpText();

	Grid getGrid();

	long getElapsedTimeSeconds();

	enum State {
		NEW_GAME, INFO_TEXT, REVEAL_CELL, TOGGLE_FLAG, HELP_TEXT, GAME_WON, GAME_LOST, ERROR, CHANGE_SETTINGS_ACTIVATED, CHANGE_SETTINGS_SUCCESS, LOAD_GAME
	}

	void loadFromDB();

	void saveToDB();

	IGridDao chooseDB(int db);

	 

 

	 
}
