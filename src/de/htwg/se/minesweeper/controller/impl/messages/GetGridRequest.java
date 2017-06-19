package de.htwg.se.minesweeper.controller.impl.messages;

import de.htwg.se.minesweeper.controller.IController;
import de.htwg.se.minesweeper.model.Grid;

public class GetGridRequest {
	public Grid grid;
	public IController.State state;
	
	public GetGridRequest(Grid grid, IController.State state) {
		this.grid = grid;
		this.state = state;
	}
}
