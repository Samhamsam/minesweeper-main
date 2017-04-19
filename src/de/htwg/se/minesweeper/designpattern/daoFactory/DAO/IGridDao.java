package de.htwg.se.minesweeper.designpattern.daoFactory.DAO;

import de.htwg.se.minesweeper.model.Grid;

public interface IGridDao {

	public Grid createGrid(int row, int col);

	public void saveGrid(final Grid grid);

	public void getNumberOfRows();

	public void getNumberOfColumns();

	public void getNumberOfMines();

	public void getAllNeighbors();

	public void getNumberOfRevealedCells();

	public void getTotalNumberOfCells();
	
	public boolean containGrid(final Grid grid);

}
