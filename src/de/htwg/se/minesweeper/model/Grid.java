package de.htwg.se.minesweeper.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Niels Boecker
 * @author Mark Unger
 * @author Aiham Abousaleh
 */
public class Grid {

	private List<Cell> cells;
	private int numberOfRows;
	private int numberOfColumns;
	private int numberOfMines;

	public Grid() {
	}

	String id;

	// default constructor for empty grid
	public Grid(int numberOfRows, int numberOfColums) {

		cells = new ArrayList<>(numberOfColums * numberOfRows);
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColums;
		this.id = UUID.randomUUID().toString();

		for (int row = 0; row < numberOfRows; row++) {
			for (int col = 0; col < numberOfColums; col++) {
				final Cell cell = new Cell(new Cell.Position(row, col));
				cells.add(cell);
			}
		}
	}

	// constructor for grid with mines
	public Grid(int numberOfRows, int numberOfColums, int numberOfMines) {
		this(numberOfRows, numberOfColums);
		this.numberOfMines = numberOfMines;
		this.id = UUID.randomUUID().toString();

		// place mines on grid
		final Random random = new Random();
		int remainingMines = numberOfMines;
		while (remainingMines > 0) {
			final int mineRow = random.nextInt(numberOfRows);
			final int mineCol = random.nextInt(numberOfColums);
			final Cell cellAtPosition = getCellAt(mineRow, mineCol);
			if (cellAtPosition.hasMine()) {
				continue;
			}
			cellAtPosition.setHasMine(true);
			remainingMines--;
		}

		// set number of surrounding mines accordingly
		for (Cell cell : cells) {
			int surroundingMines = 0;
			final Cell.Position ownPosition = cell.getPosition();
			final List<Cell> neighbors = getAllNeighbors(cell);
			for (Cell neighbor : neighbors) {
				if (neighbor.hasMine()) {
					surroundingMines++;
				}
			}
			cell.setSurroundingMines(surroundingMines);
		}
	}

	public List<Cell> getAllNeighbors(Cell cell) {
		final Cell.Position ownPosition = cell.getPosition();
		final List<Cell.Position> surroundingPositions = Arrays.asList(ownPosition.getNorth(),
				ownPosition.getNorthEast(), ownPosition.getEast(), ownPosition.getSouthEast(), ownPosition.getSouth(),
				ownPosition.getSouthWest(), ownPosition.getWest(), ownPosition.getNorthWest());
		return surroundingPositions.stream().map(position -> getCellAt(position)).filter(result -> result != null)
				.collect(Collectors.toList());
	}

	public Cell getCellAt(Cell.Position position) {
		return getCellAt(position.getRow(), position.getCol());
	}

	public Cell getCellAt(int row, int column) {
		for (Cell cell : cells) {
			final Cell.Position position = cell.getPosition();
			if (position.getRow() == row && position.getCol() == column) {
				return cell;
			}
		}
		// no cell at this position, edge got hit
		return null;
	}

	public List<List<Cell>> getCellsAsRows() {

		List<List<Cell>> result = new ArrayList<>(numberOfRows);

		for (int row = 0; row < numberOfRows; row++) {
			final int currentRow = row; // to use it in Lambda expression
			final List<Cell> cellsInRow = cells.stream().filter(cell -> cell.getPosition().getRow() == currentRow)
					.collect(Collectors.toList());
			result.add(cellsInRow);
		}

		return result;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getTotalNumberOfCells() {
		return cells.size();
	}

	public int getNumberOfRevealedCells() {
		int numberOfRevealedCells = 0;
		for (Cell cell : cells) {
			if (cell.isRevealed()) {
				numberOfRevealedCells++;
			}
		}
		return numberOfRevealedCells;
	}

	public int getNumberOfMines() {
		return numberOfMines;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public String getId() {
		return id;

	}

	public void setId(String id) {
		this.id = id;
	}

}
