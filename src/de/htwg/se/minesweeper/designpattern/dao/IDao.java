package de.htwg.se.minesweeper.designpattern.dao;

import de.htwg.se.minesweeper.model.Grid;

public interface IDao {

	public void create(Grid grid);

	public void read();

	public void update();

	public void delete();

}
