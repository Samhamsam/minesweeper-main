package de.htwg.se.minesweeper.persistence.couchdb;

import java.util.LinkedList;
import java.util.List;

import org.ektorp.CouchDbConnector;

import de.htwg.se.minesweeper.model.Cell;
import de.htwg.se.minesweeper.model.Cell.Position;
import de.htwg.se.minesweeper.model.Grid;
import de.htwg.se.minesweeper.persistence.IGridDao;

public class GridCouchdbDAO implements IGridDao {

	CouchDbConnector db;

	public GridCouchdbDAO() {
		db = new CouchDBFactory().connection();
	}

	private Grid copy(PersiGrid gridDao) {

		Grid grid = new Grid(gridDao.getRows(), gridDao.getCol(), gridDao.getMines());
		grid.setId(gridDao.getId());

		for (PersiCell cellCouch : gridDao.getCells()) {
			Cell newCell = this.cells(cellCouch);
			Cell oldCell = grid.getCellAt(cellCouch.getRow(), cellCouch.getCol());
			oldCell.setFlagged(newCell.isFlagged());
			oldCell.setHasMine(newCell.hasMine());
			oldCell.setPosition(newCell.getPosition());
			oldCell.setRevealed(newCell.isRevealed());
			oldCell.setSurroundingMines(newCell.getSurroundingMines());
		}

		return grid;
	}

	private PersiGrid copy(Grid grid) {
		PersiGrid gridCouch = new PersiGrid();

		List<PersiCell> cells = new LinkedList<PersiCell>();
		for (Cell cell : grid.getCells()) {
			cells.add(this.cells(cell));
		}

		gridCouch.setId(grid.getId());
		gridCouch.setCells(cells);
		gridCouch.setCol(grid.getNumberOfColumns());
		gridCouch.setRows(grid.getNumberOfRows());
		gridCouch.setMines(grid.getNumberOfMines());

		return gridCouch;

	}

	private Cell cells(PersiCell cellCouch) {
		Cell cell = new Cell();
		Position position = cell.getPosition();
		return new Cell(position);
	}

	private PersiCell cells(Cell cell) {
		PersiCell cellCouch = new PersiCell();
		cellCouch.setCol(cell.getPosition().getCol());
		cellCouch.setRow(cell.getPosition().getRow());
		cellCouch.setFlagged(cell.isFlagged());
		cellCouch.setHasMine(cell.hasMine());
		cellCouch.setRevealed(cell.isRevealed());
		cellCouch.setSurroundingMines(cell.getSurroundingMines());
		return cellCouch;
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
		if (containsGridById(grid.getId())) {
			db.update(copy(grid));
		} else {
			db.create(grid.getId(), copy(grid));
		}

	}

	@Override
	public Grid getGridById(String id) {
		PersiGrid grid = db.find(PersiGrid.class, id);
		if (grid == null) {
			return null;
		}
		return copy(grid);
	}

	@Override
	public boolean containsGridById(String id) {
		if (getGridById(id) == null) {
			return false;
		}
		return true;
	}

	@Override
	public void deleteGrid(String id) {
		db.delete(id);
	}

	@Override
	public Grid readGrid(Grid grid) {

		return db.get(Grid.class, grid.getId());

	}

	@Override
	public void deleteGrid(Grid grid) {
		// TODO Auto-generated method stub

	}

	@Override
	public Grid readGrid() {
		return null;
	}

}
