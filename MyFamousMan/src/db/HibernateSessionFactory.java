package db;

import java.io.File;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.Stoppable;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory
{

	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static org.hibernate.SessionFactory sessionFactory;

	private static Configuration configuration = new Configuration();
	private static ServiceRegistry serviceRegistry;

	public static String ConfigPath = null;
	public static void init()
	{
		try
		{
			File fileConfig = new File(ConfigPath);
			configuration.configure(fileConfig);
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		catch (Exception e)
		{
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	private HibernateSessionFactory()
	{

	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() throws HibernateException, Exception
	{
		Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen())
		{
			if (sessionFactory == null)
			{
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}
		session.clear();
		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 * 
	 */
	public static void rebuildSessionFactory() throws Exception
	{
		try
		{
			File fileConfig = new File(ConfigPath);
			configuration.configure(fileConfig);
			
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		catch (Exception e)
		{
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			throw e;
		}
	}

	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException
	{
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);

		if (session != null)
		{
			session.close();
		}
	}

	/*
	public static Transaction getTrans()
	{
		try
		{
			return getSession().beginTransaction();
		}
		catch (Exception ex)
		{
			System.out.println("LOI KHI GET Transaction:" + ex.getMessage());

			try
			{
				sessionFactoryClose();
			}
			catch (Exception e)
			{

			}

			try
			{

				rebuildSessionFactory();
				return getSession().beginTransaction();
			}
			catch (Exception exx)
			{
				System.out.println("LOI KHI REBUILT Transaction:" + exx.getMessage());
			}
		}
		return null;
	}

	public static void sessionFactoryClose()
	{
		final SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor) sessionFactory;
		ConnectionProvider connectionProvider = sessionFactoryImplementor.getConnectionProvider();
		if (Stoppable.class.isInstance(connectionProvider))
		{
			((Stoppable) connectionProvider).stop();
		}
	}
	*/

}