package de.htwg.se.minesweeper.persistence;

 
import de.htwg.se.minesweeper.persistence.couchdb.CouchDBFactory;
import de.htwg.se.minesweeper.persistence.db4o.Db4oFactory;
import de.htwg.se.minesweeper.persistence.hibernate.HibernateFactory;

public abstract class DAOFactory {

	public static final int DB4O = 1;
	public static final int Hibernate = 2;
	public static final int CouchDB = 3;
	
	
	
	public static DAOFactory getDAOFactory(int whichFactory){
		switch(whichFactory){
		case DB4O:
			return new Db4oFactory();
		case Hibernate:
			return new HibernateFactory();
		case CouchDB:
			return new CouchDBFactory();
		default: return null;
		}
		
	}

 

	
}
