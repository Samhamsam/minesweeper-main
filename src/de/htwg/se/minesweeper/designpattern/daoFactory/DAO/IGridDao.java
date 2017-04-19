package de.htwg.se.minesweeper.designpattern.daoFactory.DAO;

import de.htwg.se.minesweeper.model.Cell;
import de.htwg.se.minesweeper.model.Grid;

public interface IGridDao {

	public Grid createGrid(int row, int col);

	public void getNumberOfRows();

	public void getNumberOfColumns();

	public void getNumberOfMines();

	public void getAllNeighbors();

	public void getNumberOfRevealedCells();

	public void getTotalNumberOfCells();

}
