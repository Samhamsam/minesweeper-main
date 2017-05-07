package de.htwg.se.minesweeper.persistence;

import java.util.List;

import de.htwg.se.minesweeper.model.Grid;

public interface IGridDao {

	void saveOrUpdateGrid(Grid grid);

	void deleteGridById(String id);

	Grid getGridById(String id);

	boolean containsGridById(String id);

	List<Grid> getAllGrids();

}
