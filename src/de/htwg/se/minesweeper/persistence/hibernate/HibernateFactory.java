package de.htwg.se.minesweeper.persistence.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import de.htwg.se.minesweeper.persistence.DAOFactory;

public class HibernateFactory extends DAOFactory {

	private static SessionFactory sessionFactory;

	static {
//		
//		try{
//			final Configuration cfg = new Configuration();
//			cfg.configure("/hibernate.cfg.xml");
//			sessionFactory = cfg.buildSessionFactory();
//	    }
//
//	    catch(HibernateException exception){
//	        System.out.println("Problem creating session factory");
//	        exception.printStackTrace();
//
//	    }
		
		try {
			Configuration configuration = new Configuration().configure(HibernateFactory.class.getResource("/hibernate.cfg.xml"));
			StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
			serviceRegistryBuilder.applySettings(configuration.getProperties());
			ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
			  catch(HibernateException exception){
 			        System.out.println("Problem creating session factory");
 			        exception.printStackTrace();
		}
	
	}

	

	public static SessionFactory getInstance() {
		return sessionFactory;
	}
}
