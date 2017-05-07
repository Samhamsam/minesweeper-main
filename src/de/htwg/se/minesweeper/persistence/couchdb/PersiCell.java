package de.htwg.se.minesweeper.persistence.couchdb;

import org.ektorp.support.CouchDbDocument;

import de.htwg.se.minesweeper.model.Cell.Position;

public class PersiCell extends CouchDbDocument {
	
	
	
	private static final long serialVersionUID = 1L;

	private boolean hasMine;
	private boolean isFlagged;
	private boolean isRevealed;
	private Position position;
	private int surroundingMines;
	private int row;
	private int col;

	
	public PersiCell(boolean hasMine, boolean isFlagged, boolean isRevealed, int surroundingMines, int row, int col) {
		super();
		this.hasMine = hasMine;
		this.isFlagged = isFlagged;
		this.isRevealed = isRevealed;
		this.surroundingMines = surroundingMines;
		this.row = row;
		this.col = col;
	}

	public PersiCell() {

	}

	public boolean isHasMine() {
		return hasMine;
	}

	public void setHasMine(boolean hasMine) {
		this.hasMine = hasMine;
	}

	public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public boolean isRevealed() {
		return isRevealed;
	}

	public void setRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
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

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

}
