package de.htwg.se.minesweeper.designpattern.daoFactory.Factory;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import de.htwg.se.minesweeper.designpattern.daoFactory.DAO.DB4OGridDAO;
 

public class DB4ODAOFactory extends DAOFactory {

	private static final String DB4ODATABASE = "db4oDatabase.yap";

	@Override
	public DB4OGridDAO getGridDao() {

		return new DB4OGridDAO();
	}

	public static  ObjectContainer connection() {
		ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB4ODATABASE);
		return db;
	}

	
}
