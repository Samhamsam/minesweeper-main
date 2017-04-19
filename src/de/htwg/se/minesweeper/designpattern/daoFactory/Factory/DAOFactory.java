package de.htwg.se.minesweeper.designpattern.daoFactory.Factory;

import de.htwg.se.minesweeper.designpattern.daoFactory.DAO.DB4OGridDAO;
import de.htwg.se.minesweeper.designpattern.daoFactory.DAO.IGridDao;

public abstract class DAOFactory {

	public static final int DB4O = 1;
	public static final int Hibernate = 2;
	public static final int CouchDB = 3;
	
	
	//hier kommt IUserDao noch hin
	public abstract DB4OGridDAO getGridDao();
	
	public static DAOFactory getDAOFactory(int whichFactory){
		switch(whichFactory){
		case DB4O:
			return new DB4ODAOFactory();
		case Hibernate:
			return null;
		case CouchDB:
			return null;
		default: return null;
		}
		
	}

	
}
