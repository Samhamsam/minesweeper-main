package de.htwg.se.minesweeper.designpattern.daoFactory;

import de.htwg.se.minesweeper.designpattern.daoFactory.DAO.IGridDao;
import de.htwg.se.minesweeper.designpattern.daoFactory.Factory.DAOFactory;
import de.htwg.se.minesweeper.model.Grid;

public class TestClass {
	

	public static void main(String[] args) {
		DAOFactory DB4ODBFactory = DAOFactory.getDAOFactory(DAOFactory.DB4O);
		
		IGridDao custDAO = DB4ODBFactory.getGridDao();
		
		Grid testGrid = custDAO.createGrid(10,10);
		custDAO.saveGrid(testGrid);
		
		System.out.println( custDAO.containGrid(testGrid));
		
		
	 
	}

}
