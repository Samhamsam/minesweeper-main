package de.htwg.se.minesweeper.persistence;

import de.htwg.se.minesweeper.persistence.db4o.DB4ODAOFactory;

public abstract class DAOFactory {

	public static final int DB4O = 1;
	public static final int Hibernate = 2;
	public static final int CouchDB = 3;
	
	
	
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
