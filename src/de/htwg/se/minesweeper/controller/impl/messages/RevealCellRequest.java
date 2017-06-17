package de.htwg.se.minesweeper.controller.impl.messages;

public class RevealCellRequest {
	int col;
	int row;
	

	public RevealCellRequest(int col, int row)  
	{
		this.col = col;
		this.row = row;
	}
}

