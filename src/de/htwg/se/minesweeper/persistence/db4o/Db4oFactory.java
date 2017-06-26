package de.htwg.se.minesweeper.persistence.db4o;

import java.io.IOException;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import de.htwg.se.minesweeper.model.Grid;

public class Db4oFactory {

	private static final String DB4ODATABASE = "db4oDatabase.data";
	private ObjectContainer db;

	public ObjectContainer connection() throws IOException {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().objectClass(Grid.class).cascadeOnUpdate(true);
		db = Db4oEmbedded.openFile(config, DB4ODATABASE);
		return db;
	}

}
