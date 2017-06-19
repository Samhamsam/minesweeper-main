package de.htwg.se.minesweeper.controller.impl.messages;

import de.htwg.se.minesweeper.model.Cell;

public class GetCellRequest {
	public String cellName;
	public int row;
	public int col;
	public String typeOfRequest;
	
	public GetCellRequest(String cellName, int row, int col, String typeOfRequest) {
		this.cellName = cellName;
		this.col = col;
		this.row = row;
		this.typeOfRequest = typeOfRequest;
	}
}
