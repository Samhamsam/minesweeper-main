package de.htwg.se.minesweeper;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import de.htwg.se.minesweeper.controller.IController;
import de.htwg.se.minesweeper.controller.impl.Controller;
import de.htwg.se.minesweeper.persistence.IGridDao;
import de.htwg.se.minesweeper.persistence.couchdb.GridCouchdbDAO;
import de.htwg.se.minesweeper.persistence.db4o.GridDb4oDAO;
import de.htwg.se.minesweeper.persistence.hibernate.GridHibernateDAO;

public class MinesweeperModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IController.class).to(Controller.class).in(Singleton.class);

		//bind(IGridDao.class).to(GridCouchdbDAO.class).in(Singleton.class);
		//bind(IGridDao.class).to(GridDb4oDAO.class).in(Singleton.class);
		bind(IGridDao.class).to(GridHibernateDAO.class).in(Singleton.class);

	}

}
