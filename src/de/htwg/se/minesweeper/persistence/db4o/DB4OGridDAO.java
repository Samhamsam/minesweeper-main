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
		db = new DB4OFactory().connection();
	}

	@Override
	public void saveOrUpdateGrid(Grid grid) {
		db.store(grid);
		db.commit();

	}

	@Override
	public void deleteGridById(String id) {
		db.delete(getGridById(id));

	}

	@Override
	public Grid getGridById(String id) {
		List<Grid> grids = db.query(new Predicate<Grid>() {
			private static final long serialVersionUID = 1L;

			public boolean match(Grid grid) {
				return (id.equals(grid.getId()));
			}
		});

		if (grids.size() > 0) {
			return grids.get(0);
		}
		return null;

	}

	@Override
	public boolean containsGridById(String id) {
		List<Grid> grids = db.query(new Predicate<Grid>() {

			private static final long serialVersionUID = 1L;

			public boolean match(Grid grid) {
				return (grid.getId().equals(id));
			}
		});

		if (grids.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Grid> getAllGrids() {
		return db.query(Grid.class);

	}

}
