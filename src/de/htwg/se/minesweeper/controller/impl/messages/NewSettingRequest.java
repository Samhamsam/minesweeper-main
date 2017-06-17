package de.htwg.se.minesweeper.controller.impl.messages;

public class NewSettingRequest {
	int numRowsAndColumns;
	int numberOfMines;
	

	public NewSettingRequest(int numRowsAndColumns, int numberOfMines)  
	{
		this.numRowsAndColumns = numRowsAndColumns;
		this.numberOfMines = numberOfMines;
	}
}
