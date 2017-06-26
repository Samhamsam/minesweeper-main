package de.htwg.se.minesweeper.persistence.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import de.htwg.se.minesweeper.persistence.DAOFactory;

public class HibernateFactory {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
					.configure(HibernateFactory.class.getResource("/hibernate.cfg.xml")).build();
			// sessionFactory = new
			// MetadataSources(registry).buildMetadata().buildSessionFactory();
			// Create a metadata sources using the specified service registry.
			final Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

			return metadata.getSessionFactoryBuilder().build();
		} catch (Exception ex) {
			// StandardServiceRegistryBuilder.destroy(registry);
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}
