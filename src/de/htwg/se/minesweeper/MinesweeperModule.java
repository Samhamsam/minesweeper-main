package de.htwg.se.minesweeper;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

import de.htwg.se.minesweeper.controller.IAkkaController;
import de.htwg.se.minesweeper.controller.impl.AkkaController;
import de.htwg.se.minesweeper.persistence.IGridDao;
import de.htwg.se.minesweeper.persistence.couchdb.GridCouchdbDAO;
import de.htwg.se.minesweeper.persistence.db4o.GridDb4oDAO;
import de.htwg.se.minesweeper.persistence.hibernate.GridHibernateDAO;

public class MinesweeperModule extends AbstractModule {

	@Override
	protected void configure() {
		try {
			bind(IAkkaController.class).to(AkkaController.class);

			Multibinder<IGridDao> mb = Multibinder.newSetBinder(binder(), IGridDao.class);

			//mb.addBinding().to(GridCouchdbDAO.class);
			mb.addBinding().to(GridHibernateDAO.class);
			mb.addBinding().to(GridDb4oDAO.class);
		} catch (Exception e) {
			System.out.println("Falta VPN");
		}
	}

}
