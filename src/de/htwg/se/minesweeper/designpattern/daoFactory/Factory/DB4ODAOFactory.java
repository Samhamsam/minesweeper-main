package de.htwg.se.minesweeper.designpattern.daoFactory.Factory;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import de.htwg.se.minesweeper.designpattern.daoFactory.DAO.DB4OGridDAO;
import de.htwg.se.minesweeper.designpattern.daoFactory.DAO.IGridDao;

public class DB4ODAOFactory extends DAOFactory {
	
	private final String db4oDatabase = "db4oDatabase.yap";
	
	/*
	 * Hier kommt die ganze contection geschichte hin
	 */
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), db4oDatabase);

	@Override
	public IGridDao getGridDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
