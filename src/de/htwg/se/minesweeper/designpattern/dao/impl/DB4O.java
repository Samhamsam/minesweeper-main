package de.htwg.se.minesweeper.designpattern.dao.impl;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
 import de.htwg.se.minesweeper.designpattern.dao.IDao;
import de.htwg.se.minesweeper.model.Grid;
  
public class DB4O implements IDao {

	ObjectContainer db;
	private String DB4OFILENAME = "src/de/htwg/se/minesweeper/designpattern/dao/impl/Database/test";

	public DB4O() {
		this.db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB4OFILENAME);
	}

	@Override
	public void create(Grid grid) {
		this.db.store(grid);
	}

	@Override
	public void read() {

	}

	@Override
	public void update() {

	}

	@Override
	public void delete() {

	}

	// public static void main(String[] args) {
	//
	//
	//
	// }
}
