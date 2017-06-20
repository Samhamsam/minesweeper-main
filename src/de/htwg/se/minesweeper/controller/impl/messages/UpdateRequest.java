package de.htwg.se.minesweeper.controller.impl.messages;

import de.htwg.se.minesweeper.controller.IController;
import de.htwg.se.minesweeper.model.Grid;

public class UpdateRequest {
	
	public IController.State state;
	
	public long elapsedTimeSeconds;
	
	public String getHelpText;
	
	public Grid grid;
	
	public UpdateRequest(IController.State state, long elapsedTimeSeconds, String getHelpText, Grid grid) {
		this.state = state;
		this.elapsedTimeSeconds = elapsedTimeSeconds;
		this.getHelpText = getHelpText;
		this.grid = grid;
	}

}
