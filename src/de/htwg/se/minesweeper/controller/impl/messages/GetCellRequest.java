package de.htwg.se.minesweeper.controller.impl.messages;

import de.htwg.se.minesweeper.model.Cell;

public class GetCellRequest {
	public Cell cellAt;
	public int row;
	public int col;
	public String typeOfRequest;
	
	public GetCellRequest(Cell cellAt, int row, int col, String typeOfRequest) {
		this.cellAt = cellAt;
		this.col = col;
		this.row = row;
		this.typeOfRequest = typeOfRequest;
	}
}
