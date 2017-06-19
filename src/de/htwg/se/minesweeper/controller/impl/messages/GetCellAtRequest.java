package de.htwg.se.minesweeper.controller.impl.messages;

public class GetCellAtRequest {
	public int col;
	public int row;
	public String typeOfRequest;
	

	public GetCellAtRequest(int col, int row, String typeOfRequest)  
	{
		this.col = col;
		this.row = row;
		this.typeOfRequest = typeOfRequest;
	}
}
