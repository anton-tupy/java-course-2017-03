package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.List;

@Log4j
public class GuestBookController {
	private SessionFactory sessionFactory;

	GuestBookController() {
		try {
			init();
		} catch (Exception e) {
			log.warn("Exception on db serializing", e);
		}
	}

	void addRecord(String message) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(new Record(0L, message, new Date(System.currentTimeMillis())));
		session.getTransaction().commit();
		session.close();

	}

	List<Record> getRecords() {
		Session session = sessionFactory.openSession();
		session = sessionFactory.openSession();
		session.beginTransaction();
		List result = session.createQuery( "from Record").list();
		session.getTransaction().commit();
		session.close();
		return result;
	}

	private void init() {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			log.warn("Exception on db init", e);
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	public void close() {
		sessionFactory.close();
	}
}
