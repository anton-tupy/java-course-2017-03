/**
 * Created by Anatoliy on 10.04.2017.
 */
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateWorker implements AutoCloseable {
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

	public void save(List<WordCounter> list, int[] countRefs) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (WordCounter c : list) {
			session.save(new WordCounterEntity(0L, c.getWord(), c.getRefCount(), ((double)c.getRefCount())/countRefs[0]*100, new Date()));
		}
		session.getTransaction().commit();
		session.close();
	}

	public void close() {
		sessionFactory.close();
	}
}
