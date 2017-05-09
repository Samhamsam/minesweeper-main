package de.htwg.se.minesweeper.persistence.hibernate;

import java.util.List;

import de.htwg.se.minesweeper.model.Grid;
import de.htwg.se.minesweeper.persistence.IGridDao;

public class GridHibernateDAO implements IGridDao {

	@Override
	public void saveOrUpdateGrid(Grid grid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteGridById(String id) {
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

	@Override
	public List<Grid> getAllGrids() {
		// TODO Auto-generated method stub
		return null;
	}

}
