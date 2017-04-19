package de.htwg.se.minesweeper.designpattern.daoFactory.DAO;

import java.io.IOException;
import java.util.List;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import de.htwg.se.minesweeper.designpattern.daoFactory.Factory.DB4ODAOFactory;
import de.htwg.se.minesweeper.model.Grid;

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

	}

	@Override
	public boolean containGrid(final Grid grid) {
		final String gridString = grid.toString();
		List<Grid> grids = db.query(new Predicate<Grid>() {

			private static final long serialVersionUID = 1L;

			public boolean match(Grid grid) {
				return (grid.toString().equals(gridString));
			}
		});

		if (grids.size() > 0) {
			return true;
		}
		return false;

	}
	
	@Override
	public Grid readGrid() {
 		List<Grid> grids = db.query(new Predicate<Grid>() {

			private static final long serialVersionUID = 1L;

			public boolean match(Grid grid) {
				return true;
			}
		});

		if (grids.size() > 0) {
			System.out.println(grids.get(0).getNumberOfColumns());
			return grids.get(0);
		}
		return null;

	}


	@Override
	public void deleteGrid(Grid grid) {
		db.delete(grid);
	}

}
