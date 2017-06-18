package de.htwg.se.minesweeper.controller.impl.messages;

import java.util.List;

import de.htwg.se.minesweeper.controller.IController;
import de.htwg.se.minesweeper.model.Cell;

public class PrintTUIRequest {
	public IController.State state;
	
	public long elapsedTimeSeconds;
	
	public String getHelpText;
	
	public int numberOfMines;
	
	public int numberOfRows;
	
	public String gridAsString;
	
	public PrintTUIRequest(IController.State state, long elapsedTimeSeconds, String getHelpText, 
			int numberOfMines, int numberOfRows,List<Cell> allCells)
	{
		this.state = state;
		this.elapsedTimeSeconds = elapsedTimeSeconds;
		this.getHelpText = getHelpText;
		this.numberOfMines = numberOfMines;
		this.numberOfRows = numberOfRows;
		this.gridAsString = getGridAsString(allCells,numberOfRows);
	}
	
	
	private String getGridAsString(List<Cell> cells, int numberOfRows) {
		StringBuilder result = new StringBuilder();
		final List<Cell> allCells = cells;
	
		for (int row = 0; row < numberOfRows; row++) {
			final int currentRow = row; // to use it in Lambda expression
			allCells.stream().filter(cell -> cell.getPosition().getRow() == currentRow)
					.forEach(cell -> result.append(cell.toString()).append(" "));
	
			result.append("\n");
		}
	
		return result.toString();
	}
	
	
}
