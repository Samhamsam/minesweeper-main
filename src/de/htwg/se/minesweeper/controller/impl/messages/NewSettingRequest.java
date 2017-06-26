package de.htwg.se.minesweeper.controller.impl.messages;

import javax.swing.JFrame;

public class NewSettingRequest {
	public int numRowsAndColumns;
	public int numberOfMines;
	public JFrame mainFrame;

	public NewSettingRequest(int numRowsAndColumns, int numberOfMines, JFrame mainFrame) {
		this.numRowsAndColumns = numRowsAndColumns;
		this.numberOfMines = numberOfMines;
		this.mainFrame = mainFrame;
	}
}
