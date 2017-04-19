package de.htwg.se.minesweeper.designpattern.daoFactory.DAO;

import java.util.List;

 
import com.db4o.query.Predicate;

import de.htwg.se.minesweeper.designpattern.daoFactory.Factory.DB4ODAOFactory;

import de.htwg.se.minesweeper.model.Grid;
 

public class DB4OGridDAO implements IGridDao {

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

	@Override
	public void saveGrid(Grid grid) {

		DB4ODAOFactory.connection().store(grid);
	}

	@Override
	public  boolean containGrid(final Grid grid) {
		//final String gridString = grid.toString();
		
		List<Grid> grids = DB4ODAOFactory.connection().query(new Predicate<Grid>() {
			
 
 
			private static final long serialVersionUID = 1L;

			public boolean match(Grid grid) {
				return grid.getNumberOfColumns() == 10 ;
			}
		});

		if (grids.size() > 0) {
			 
			return true;
		}
		return false;

	}
	 

}
