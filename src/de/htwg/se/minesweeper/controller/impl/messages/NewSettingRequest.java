package de.htwg.se.minesweeper.controller.impl.messages;

public class NewSettingRequest {
	public int numRowsAndColumns;
	public int numberOfMines;
	

	public NewSettingRequest(int numRowsAndColumns, int numberOfMines)  
	{
		this.numRowsAndColumns = numRowsAndColumns;
		this.numberOfMines = numberOfMines;
	}
}
