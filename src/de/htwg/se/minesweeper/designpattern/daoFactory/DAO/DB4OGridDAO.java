package de.htwg.se.minesweeper.designpattern.daoFactory.DAO;

//import com.db4o.Db4oEmbedded;
//import com.db4o.ObjectContainer;

import de.htwg.se.minesweeper.model.Grid;

public class DB4OGridDAO implements IGridDao {

	// ObjectContainer db;
	// private String DB4OFILENAME =
	// "src/de/htwg/se/minesweeper/designpattern/dao/impl/Database/test";
	//
	// public DB4O() {
	// this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
	// DB4OFILENAME);
	// }

	@Override
	public Grid createGrid(int row, int col) {
		return new Grid(row, col);
	}

	@Override
	public void getNumberOfRows() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getNumberOfColumns() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getNumberOfMines() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getAllNeighbors() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getNumberOfRevealedCells() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getTotalNumberOfCells() {
		// TODO Auto-generated method stub

	}


}
