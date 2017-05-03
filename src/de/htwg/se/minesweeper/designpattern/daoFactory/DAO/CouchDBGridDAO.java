package de.htwg.se.minesweeper.designpattern.daoFactory.DAO;

import org.ektorp.CouchDbConnector;
import de.htwg.se.minesweeper.designpattern.daoFactory.Factory.CouchDBFactory;
import de.htwg.se.minesweeper.model.Grid;

public class CouchDBGridDAO implements IGridDao {

	CouchDbConnector db;

	public CouchDBGridDAO() {
		db = new CouchDBFactory().connection();
	}

	@Override
	public Grid createGrid(int row, int col) {
		return new Grid(row, col);
	}

	@Override
	public Grid createGrid(int row, int col, int mines) {
		return new Grid(row, col, mines);
	}

	@Override
	public void saveAndUpdateGrid(Grid grid) {
	
	}

	@Override
	public void deleteGrid(Grid grid) {
 	}

	@Override
	public Grid readGrid() {
		return null;
	 

	}
}
