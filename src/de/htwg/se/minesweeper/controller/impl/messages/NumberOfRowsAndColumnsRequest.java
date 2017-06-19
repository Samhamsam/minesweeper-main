package de.htwg.se.minesweeper.controller.impl.messages;

public class NumberOfRowsAndColumnsRequest {
	public int numberOfRows;
	public int numberOfColumns;
	public int numberOfMines;
	
	public NumberOfRowsAndColumnsRequest(int numberOfRows,int numberOfColumns, int numberOfMines) {
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		this.numberOfMines = numberOfMines;
	}
}
