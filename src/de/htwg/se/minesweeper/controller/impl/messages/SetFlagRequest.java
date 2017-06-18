package de.htwg.se.minesweeper.controller.impl.messages;

public class SetFlagRequest {
	public int col;
	public int row;
	

	public SetFlagRequest(int col, int row) {
		this.col = col;
		this.row = row;
	}
}
