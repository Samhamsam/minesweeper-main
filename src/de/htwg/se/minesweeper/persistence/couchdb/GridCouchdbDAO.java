package de.htwg.se.minesweeper.persistence.couchdb;

import java.util.LinkedList;
import java.util.List;

import org.ektorp.CouchDbConnector;

import de.htwg.se.minesweeper.model.Cell;
import de.htwg.se.minesweeper.model.Cell.Position;
import de.htwg.se.minesweeper.model.Grid;
import de.htwg.se.minesweeper.persistence.IGridDao;
import junit.framework.Test;

public class GridCouchdbDAO implements IGridDao {

	CouchDbConnector db;

	public GridCouchdbDAO() {
		db = new CouchDBFactory().connection();
	}

	private Grid updateGridInDB(PersiGrid persiGrid) { // copy Grid ***From***
														// DB

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
				cellCouch.getSurroundingMines());

		 
	}

	private PersiGrid copyGridToDB(Grid grid) { // copy Grid ***To*** DB
		if (grid == null) {
			return null;
		}
		PersiGrid persiGrid = new PersiGrid();
		String id = grid.getId();
 
		List<PersiCell> cells = new LinkedList<PersiCell>();
		for (Cell cell : grid.getCells()) {
			cells.add(this.cellsToDB(cell));
		}

		persiGrid.setId(id);
		persiGrid.setCells(cells);
		persiGrid.setCol(grid.getNumberOfColumns());
		persiGrid.setRows(grid.getNumberOfRows());
		persiGrid.setMines(grid.getNumberOfMines());
 
		return persiGrid;

	}

	private PersiCell cellsToDB(Cell cell) {
		return new PersiCell(cell.hasMine(), cell.isFlagged(), cell.isRevealed(),
				cell.getSurroundingMines(), cell.getPosition().getRow(), cell.getPosition().getCol());
 	}


	@Override
	public void saveOrUpdateGrid(Grid grid) {
		if (containsGridById(grid.getId())) {
			db.update(copyGridToDB(grid));
		} else {
			db.create(grid.getId(), copyGridToDB(grid));
		}

	}

	@Override
	public Grid getGridById(String id) {
		PersiGrid persiGrid = db.find(PersiGrid.class, id);
		if (persiGrid == null) {
			return null;
		}
		return updateGridInDB(persiGrid);
	}

	@Override
	public boolean containsGridById(String id) {
		if (getGridById(id) == null) {
			return false;
		}
		return true;
	}

	@Override
	public void deleteGridById(String id) {
		db.delete(copyGridToDB(getGridById(id)));
	}

	@Override
	public List<Grid> getAllGrids() {
		// TODO Auto-generated method stub
		return null;
	}

	 
 

}
