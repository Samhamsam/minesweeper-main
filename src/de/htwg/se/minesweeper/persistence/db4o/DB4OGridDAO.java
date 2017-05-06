package de.htwg.se.minesweeper.persistence.db4o;

import java.io.IOException;
import java.util.List;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

import de.htwg.se.minesweeper.model.Grid;
import de.htwg.se.minesweeper.persistence.IGridDao;

public class DB4OGridDAO implements IGridDao {

	ObjectContainer db;

	public DB4OGridDAO() throws IOException {
		db = new DB4ODAOFactory().connection();
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
		db.store(grid);
		db.commit();

	}
//	
//	@Override
//	public boolean containGrid(final Grid grid) {
//		final String gridString = grid.toString();
//		List<Grid> grids = db.query(new Predicate<Grid>() {
//
//			private static final long serialVersionUID = 1L;
//
//			public boolean match(Grid grid) {
//				return (grid.toString().equals(gridString));
//			}
//		});
//
//		if (grids.size() > 0) {
//			return true;
//		}
//		return false;
//
//	}
//	
	@Override
	public Grid readGrid() {
 		List<Grid> grids = db.query(new Predicate<Grid>() {

			private static final long serialVersionUID = 1L;

			public boolean match(Grid grid) {
				return true;
			}
		});

		if (grids.size() > 0) {
		 
			return grids.get(0);
		}
		return null;

	}


	@Override
	public void deleteGrid(Grid grid) {
		db.delete(grid);
	}

	@Override
	public Grid readGrid(Grid grid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteGrid(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Grid getGridById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsGridById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}
