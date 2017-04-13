package de.htwg.se.minesweeper.model;

/**
 * @author Niels Boecker
 * @author Mark Unger
 * @author Aiham Abousaleh
 */
public class Cell {

	// String represenations
	private static final String MINE_CELL_STRING = "b";
	private static final String HIDDEN_CELL_STRING = "x";
	private static final String FLAGGED_CELL_STRING = "f";

	private boolean hasMine;
	private boolean isFlagged;
	private boolean isRevealed;
	private Position position;
	private int surroundingMines;

	// default constructor
	public Cell(Position position) {
		this.hasMine = false;
		this.isFlagged = false;
		this.isRevealed = false;
		this.position = position;
		this.surroundingMines = 0;
	}

	public boolean hasMine() {
		return hasMine;
	}

	public void setHasMine(boolean hasMine) {
		this.hasMine = hasMine;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean flagged) {
		isFlagged = flagged;
	}

	public boolean isRevealed() {
		return isRevealed;
	}

	public void setRevealed(boolean revealed) {
		isRevealed = revealed;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getSurroundingMines() {
		return surroundingMines;
	}

	public void setSurroundingMines(int surroundingMines) {
		this.surroundingMines = surroundingMines;
	}

	@Override
	public String toString() {
		if (!isRevealed && !isFlagged) {
			return HIDDEN_CELL_STRING;
		} else if (!isRevealed && isFlagged) {
			return FLAGGED_CELL_STRING;
		} else if (hasMine) {
			return MINE_CELL_STRING;
		} else {
			return String.valueOf(surroundingMines);
		}
	}

	public static class Position {

		private int row;
		private int col;

		// default constructor
		public Position() {
			this(0, 0);
		}

		// copy constructor
		public Position(Position g) {
			this(g.getRow(), g.getCol());
		}

		// int constructor
		public Position(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}

		public Position getNorth() {
			return new Position(getRow() - 1, getCol());
		}

		public Position getNorthEast() {
			return new Position(getRow() - 1, getCol() + 1);
		}

		public Position getEast() {
			return new Position(getRow(), getCol() + 1);
		}

		public Position getSouthEast() {
			return new Position(getRow() + 1, getCol() + 1);
		}

		public Position getSouth() {
			return new Position(getRow() + 1, getCol());
		}

		public Position getSouthWest() {
			return new Position(getRow() + 1, getCol() - 1);
		}

		public Position getWest() {
			return new Position(getRow(), getCol() - 1);
		}

		public Position getNorthWest() {
			return new Position(getRow() - 1, getCol() - 1);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Position) {
				Position g = (Position) obj;
				return (getRow() == g.getRow()) && (getCol() == g.getCol());
			}
			return false;
		}

		@Override
		public int hashCode() {
			int sum = getRow() + getCol();
			return sum * (sum + 1) / 2 + getRow();
		}

		@Override
		public String toString() {
			return getClass().getName() + "[row=" + getRow() + ",col=" + getCol() + "]";
		}
	}
}
