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
    //TODO: read about in(Singilton.class)
	@Override
	protected void configure() {
		bind(IController.class).to(Controller.class);

		// bind(IGridDao.class).to(GridCouchdbDAO.class);
		// bind(IGridDao.class).to(GridDb4oDAO.class);
		bind(IGridDao.class).to(GridHibernateDAO.class);

	}

}
