package de.htwg.se.minesweeper.persistence.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import de.htwg.se.minesweeper.persistence.DAOFactory;

public class HibernateFactory {

	private static SessionFactory sessionFactory;

	static {

		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure(HibernateFactory.class.getResource("/hibernate.cfg.xml")).build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception ex) {

			StandardServiceRegistryBuilder.destroy(registry);
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	public static SessionFactory getInstance() {
		return sessionFactory;
	}
}
