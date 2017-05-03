package de.htwg.se.minesweeper.designpattern.daoFactory.Factory;

import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

 
public class CouchDBFactory extends DAOFactory {

	private CouchDbConnector db = null;
 	private static final Logger LOGGER = LogManager.getRootLogger();

	public CouchDbConnector connection() {
		HttpClient client = null;
		try {
			client = new StdHttpClient.Builder().url("http://lenny2.in.htwg-konstanz.de:5984").build();

		} catch (MalformedURLException e) {
	 		LOGGER.error("Malformed URL", e);
 		}
		CouchDbInstance dbInstance = new StdCouchDbInstance(client);
		db = dbInstance.createConnector("minesweeper_ma", true);
		db.createDatabaseIfNotExists();
		return db;
	}

}
