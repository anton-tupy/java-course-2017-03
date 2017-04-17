package com.javacourse.task61;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.List;

public class HibernateWorker implements AutoCloseable, GuestBookController {
	private SessionFactory sessionFactory;

	public HibernateWorker() {
		try {
			setUp();
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	protected void setUp() throws Exception {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		try {
			sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}

	public void close() {
		sessionFactory.close();
	}

	@Override
	public void addRecord(String message) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(new GuestBookEntity(0L, new Date(), message));
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public List<GuestBookEntity> getRecords() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(GuestBookEntity.class);
		List<GuestBookEntity> list = criteria.list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
}
