package de.htwg.se.minesweeper.designpattern.daoFactory;

import java.io.IOException;

import de.htwg.se.minesweeper.designpattern.daoFactory.DAO.IGridDao;
import de.htwg.se.minesweeper.designpattern.daoFactory.Factory.DAOFactory;
import de.htwg.se.minesweeper.model.Grid;

public class TestClass {
	

	public static void main(String[] args) throws IOException {
		DAOFactory DB4ODBFactory = DAOFactory.getDAOFactory(DAOFactory.DB4O);
		
		IGridDao custDAO = DB4ODBFactory.getGridDao();
		
		Grid testGrid = custDAO.createGrid(7,7);
		custDAO.saveAndUpdateGrid(testGrid);
 		System.out.println( custDAO.containGrid(testGrid));
		
 	 
	}

}
