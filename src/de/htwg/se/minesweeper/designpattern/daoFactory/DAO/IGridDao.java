package de.htwg.se.minesweeper.designpattern.daoFactory.DAO;

import de.htwg.se.minesweeper.model.Grid;

public interface IGridDao {

	Grid createGrid(int row, int col);

	Grid createGrid(int row, int col, int mines);

	void saveAndUpdateGrid(Grid grid);

//	boolean containGrid(final Grid grid);

	void deleteGrid(final Grid grid);

	Grid readGrid();

 
}
