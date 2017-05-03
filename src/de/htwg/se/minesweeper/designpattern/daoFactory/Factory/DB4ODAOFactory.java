package de.htwg.se.minesweeper.designpattern.daoFactory.Factory;

import java.io.IOException;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

  import de.htwg.se.minesweeper.model.Grid;

public class DB4ODAOFactory extends DAOFactory {

	private static final String DB4ODATABASE = "db4oDatabase.yap";
	private ObjectContainer db;

 
	public ObjectContainer connection() throws IOException {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration(); 
		config.common().objectClass(Grid.class).cascadeOnUpdate(true);
		db = Db4oEmbedded.openFile(config, DB4ODATABASE);
		return db;
	}

 
}
