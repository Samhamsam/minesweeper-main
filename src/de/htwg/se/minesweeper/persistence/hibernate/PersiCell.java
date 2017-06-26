package de.htwg.se.minesweeper.persistence.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CELL")
public class PersiCell implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "gridid", columnDefinition = "varchar(100)", unique=true)
	private PersiGrid persiGrid;
	@Column(name = "hasMine")

	private boolean hasMine;
	@Column(name = "isFlagged")

	private boolean isFlagged;
	@Column(name = "isReleaved")

	private boolean isRevealed;
	// private Position position;
	private int surroundingMines;

	@Column(name = "rowcell")
	private int row;
	@Column(name = "columncell")
	private int col;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	protected PersiCell() {

	}

	public PersiCell(boolean hasMine, boolean isFlagged, boolean isRevealed, int surroundingMines, int row, int col) {
		// super();
		this.hasMine = hasMine;
		this.isFlagged = isFlagged;
		this.isRevealed = isRevealed;
		this.surroundingMines = surroundingMines;
		this.row = row;
		this.col = col;
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

	public PersiGrid getGrid() {
		return persiGrid;
	}

	public void setGrid(PersiGrid grid) {
		this.persiGrid = grid;
	}

}
