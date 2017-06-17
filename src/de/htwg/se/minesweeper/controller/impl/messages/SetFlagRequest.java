package de.htwg.se.minesweeper.controller.impl.messages;

public class SetFlagRequest {
	int col;
	int row;
	

	public SetFlagRequest(int col, int row) {
		this.col = col;
		this.row = row;
	}
}
