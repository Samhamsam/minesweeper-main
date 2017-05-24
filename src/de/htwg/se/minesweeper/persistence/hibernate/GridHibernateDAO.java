package de.htwg.se.minesweeper.persistence.hibernate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.htwg.se.minesweeper.model.Cell;
import de.htwg.se.minesweeper.model.Grid;
import de.htwg.se.minesweeper.persistence.IGridDao;

public class GridHibernateDAO implements IGridDao {

	private Grid gridFromDB(PersiGrid persiGrid) {

		Grid grid = new Grid(persiGrid.getRows(), persiGrid.getCol(), persiGrid.getMines());
		grid.setId(persiGrid.getId());

		for (PersiCell cellCouch : persiGrid.getCells()) {
			Cell updateCells = this.updateCellInDB(cellCouch);

			Cell cell = grid.getCellAt(cellCouch.getRow(), cellCouch.getCol());
			cell.setFlagged(updateCells.isFlagged());
			cell.setHasMine(updateCells.hasMine());
			cell.setPosition(updateCells.getPosition());
			cell.setRevealed(updateCells.isRevealed());
			cell.setSurroundingMines(updateCells.getSurroundingMines());
		}
		return grid;
	}

	private Cell updateCellInDB(PersiCell cellCouch) {
		return new Cell(cellCouch.isHasMine(), cellCouch.isFlagged(), cellCouch.isRevealed(),
				cellCouch.getSurroundingMines(), cellCouch.getRow(), cellCouch.getCol());

	}

	private PersiGrid copyGridToDB(Grid grid) {
		if (grid == null) {
			return null;
		}
		PersiGrid persiGrid;
		String id = grid.getId();
		// String id = UUID.randomUUID().toString();

	  	if (containsGridById(id)) {

			try {
				Session session = HibernateFactory.getInstance().openSession();
				 session.beginTransaction();
				persiGrid = (PersiGrid) session.get(PersiGrid.class, id);
				System.out.println("---------------");
				System.out.println(persiGrid.getId());
			} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage());
 
			}
	 	} else {
		  persiGrid = new PersiGrid();
	 	 }

		List<PersiCell> cells = new LinkedList<PersiCell>();
		for (Cell cell : grid.getCells()) {
			cells.add(this.cellsToDB(cell));
		}

		persiGrid.setId(id);
		persiGrid.setCells(cells);
		persiGrid.setCol(grid.getNumberOfColumns());
		persiGrid.setRows(grid.getNumberOfRows());
		persiGrid.setMines(grid.getNumberOfMines());
		System.out.println("--s---s-----s----");
		System.out.println(persiGrid.getId());
		return persiGrid;

	}

	private PersiCell cellsToDB(Cell cell) {
		return new PersiCell(cell.hasMine(), cell.isFlagged(), cell.isRevealed(), cell.getSurroundingMines(),
				cell.getPosition().getRow(), cell.getPosition().getCol());
	}

	@Override
	public void saveOrUpdateGrid(Grid grid) {
		Transaction tx = null;
		Session session = null;

		try {
			session = HibernateFactory.getInstance().getCurrentSession();
		tx = session.beginTransaction();

			PersiGrid persigrid = copyGridToDB(grid);
			session.saveOrUpdate(persigrid);
			for (PersiCell persicell : persigrid.getCells()) {
				session.saveOrUpdate(persicell);
			}

			tx.commit();

			// session.close();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();

			throw new RuntimeException(ex.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public void deleteGridById(String id) {
		Transaction tx = null;
		Session session = null;

		try {
			session = HibernateFactory.getInstance().openSession(); // getCurrentSession();
			tx = session.beginTransaction();

			PersiGrid persigrid = (PersiGrid) session.get(PersiGrid.class, id);

			for (PersiCell c : persigrid.getCells()) {
				session.delete(c);
			}
			session.delete(persigrid);

			tx.commit();

		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			throw new RuntimeException(ex.getMessage());

		} finally {
			session.close();
		}
	}

	@Override
	public Grid getGridById(String id) {
	//	Grid grid = null;
	//	Transaction tx = null;
	//	Session session = null;
		try {
			Session session = HibernateFactory.getInstance().getCurrentSession();
	 	//	session.beginTransaction();
			 
			Grid grid =  gridFromDB((PersiGrid) session.get(PersiGrid.class, id));
		//	tx.commit();
		//	session.close();
			return grid;
		} catch (HibernateException ex) {
			//if (tx != null)
				//tx.rollback();
			throw new RuntimeException(ex.getMessage());

		}  
 	}

	@Override
	public boolean containsGridById(String id) {
		if (getGridById(id) != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Grid> getAllGrids() {
		Session session = HibernateFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<PersiGrid> criteria = builder.createQuery(PersiGrid.class);

		@SuppressWarnings("unchecked")
		List<PersiGrid> results = (List<PersiGrid>) criteria.from(PersiGrid.class);

		List<Grid> grids = new ArrayList<Grid>();
		for (PersiGrid pgrid : results) {
			Grid grid = gridFromDB(pgrid);
			grids.add(grid);
		}
		return grids;
	}

}
