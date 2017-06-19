package de.htwg.se.minesweeper.controller.impl.messages;

public class NumberOfRowsAndColumnsRequest {
	public int numberOfRows;
	public int numberOfColumns;
	
	public NumberOfRowsAndColumnsRequest(int numberOfRows,int numberOfColumns) {
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
	}
}
