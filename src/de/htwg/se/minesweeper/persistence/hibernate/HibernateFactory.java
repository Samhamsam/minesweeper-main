package de.htwg.se.minesweeper.persistence.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import de.htwg.se.minesweeper.persistence.DAOFactory;

public class HibernateFactory extends DAOFactory {

	private static final SessionFactory sessionFactory;
	
	static {
		final Configuration cfg = new Configuration();
		cfg.configure("/hibernate.cfg.xml");
		sessionFactory = cfg.buildSessionFactory();
	}

	

	public static SessionFactory getInstance() {
		return sessionFactory;
	}
}
