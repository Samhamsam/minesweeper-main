package de.htwg.se.minesweeper.persistence.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;

@Entity
@Table(name = "GRID")
public class PersiGrid implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	private String gridId;

	@OneToMany(mappedBy = "persiGrid")
	@Column(name = "cell")
	private List<PersiCell> cells;

	private int rows;
	private int col;
	private int mines;

	public PersiGrid() {
		// super();
	}

	public PersiGrid(List<PersiCell> cells, int rows, int col, int mines, String id) {
		// super();
		this.cells = cells;
		this.rows = rows;
		this.col = col;
		this.mines = mines;
		this.gridId = id;
	}

	public List<PersiCell> getCells() {
		return cells;
	}

	public void setCells(List<PersiCell> cells) {
		this.cells = cells;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getMines() {
		return mines;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}

	public String getId() {
		return gridId;
	}

	public void setId(String id) {
		this.gridId = id;
	}

}
