package de.htwg.se.minesweeper.persistence.couchdb;

import java.util.List;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

 
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersiGrid extends CouchDbDocument {
	
	private static final long serialVersionUID = 1L;
	
	
	@TypeDiscriminator
	private String id;
	private List<PersiCell> cells;
	private int rows;
	private int col;
	private int mines;

	public PersiGrid() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

 

}
