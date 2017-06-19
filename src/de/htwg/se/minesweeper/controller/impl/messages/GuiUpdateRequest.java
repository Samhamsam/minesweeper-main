package de.htwg.se.minesweeper.controller.impl.messages;

import de.htwg.se.minesweeper.controller.IController;

public class GuiUpdateRequest {
	public String helpText;
	public long time;
	public IController.State state;
	
	public GuiUpdateRequest(String helpText, long time, IController.State state) {
		this.helpText = helpText;
		this.time = time;
		this.state = state;
	}
	
}
