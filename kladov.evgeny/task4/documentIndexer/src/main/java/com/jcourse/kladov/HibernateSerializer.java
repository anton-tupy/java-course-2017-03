package com.jcourse.kladov;

import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.Closeable;
import java.util.Iterator;

@Log4j
public class HibernateSerializer implements MetricSerializer, Closeable {
	private SessionFactory sessionFactory;

	HibernateSerializer() {
		try {
			init();
		} catch (Exception e) {
			log.warn("Exception on db serializing", e);
		}
	}

	@Override
	public void serialize(Metric m) {
		log.info("Hibernate serialization for " + m.getName() + " begin");

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (Iterator<Metric.Row> it = m.getIterator(); it.hasNext(); )
			session.save(it.next().entity);
		session.getTransaction().commit();
		session.close();

		log.info("Hibernate serialization for " + m.getName() + " complete");
	}

	protected void init() throws Exception {
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
