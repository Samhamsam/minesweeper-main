package de.htwg.se.minesweeper.controller.impl.messages;

public class RevealCellRequest {
	public int col;
	public int row;
	

	public RevealCellRequest(int col, int row)  
	{
		this.col = col;
		this.row = row;
	}
}

