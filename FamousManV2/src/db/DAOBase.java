package db;
import java.io.Serializable;
import java.util.List;

import org.hibernate.*;

import db.HibernateSessionFactory;
public class DAOBase
{
	public Session GetSession() throws HibernateException, Exception
	{
		return HibernateSessionFactory.getSession();
	}
	public Transaction GetTransaction() throws HibernateException, Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		Transaction mTran = mSec.beginTransaction();
		return mTran;
	}
	
	/**
	 * Lấy tất cả dữ liệu trong db
	 * 
	 * @return
	 * @throws Exception 
	 * @throws HibernateException 
	 */
	public List<?> Get() throws HibernateException, Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		
		Transaction mTran = mSec.beginTransaction();

		List<?> mList = null;
		try
		{
			
			mList = (List<?>) mSec.createCriteria(this.getClass()).list();
			mTran.commit();
			return mList;
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}

	/**
	 * Lấy theo ID của đối tượng
	 * 
	 * @param ID
	 * @return
	 * @throws Exception 
	 * @throws HibernateException 
	 */
	public Object Get(Serializable ID) throws HibernateException, Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		
		Transaction mTran = mSec.beginTransaction();
		try
		{
			
			Object mObj = mSec.get(this.getClass(), (Serializable) ID);
			mTran.commit();
			return mObj;
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}

	public List<?> Get(String Query) throws HibernateException, Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		
		Transaction mTran = mSec.beginTransaction();
		List<?> mList = null;
		try
		{
			Query mQuery = mSec.createQuery(Query);
			mList = (List<?>) mQuery.list();
			mTran.commit();
			return mList;
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}

	public List<?> GetWithSQLQuery(String Query)throws HibernateException, Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		
		Transaction mTran = mSec.beginTransaction();

		List<?> mList = null;
		try
		{
			
			Query mQuery = mSec.createSQLQuery(Query).addEntity(this.getClass());

			mList = (List<?>) mQuery.list();
			mTran.commit();
			return mList;
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}

	
	public List<?> Get(String Query, int maxResults)throws HibernateException, Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		
		Transaction mTran = mSec.beginTransaction();

		List<?> mList = null;
		try
		{
			
			Query mQuery = mSec.createQuery(Query);
			mQuery.setMaxResults(maxResults);

			mList = (List<?>) mQuery.list();
			mTran.commit();
			return mList;
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}

	
	public List<?> Get(int beginRow, int maxResult) throws HibernateException, Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		
		Transaction mTran = mSec.beginTransaction();

		List<?> mList = null;
		try
		{
			Criteria crit=	 mSec.createCriteria(this.getClass()) ;
			crit.setMaxResults(maxResult);
			crit.setFirstResult(beginRow);
			mList = (List<?>) crit.list();
			mTran.commit();
			return mList;
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}
	
	
	public Object GetUnique(String Query)throws HibernateException, Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		
		Transaction mTran = mSec.beginTransaction();

		try
		{
			
			Query mQuery = mSec.createQuery(Query);

			Object mObj = mQuery.uniqueResult();
			mTran.commit();
			return mObj;
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}
	}

	public boolean Save() throws Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		Transaction mTran = mSec.beginTransaction();

		try
		{
			mSec.save(this);
			mTran.commit();
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}

		return true;
	}

	public boolean Save(Object mObj) throws Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		Transaction mTran = mSec.beginTransaction();

		try
		{
			mSec.save(mObj);
			mTran.commit();
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{

			HibernateSessionFactory.closeSession();
		}

		return true;
	}

	/**
	 * Lưu 1 danh sách đối tượng xuống db
	 * 
	 * @param mList
	 * @return
	 * @throws Exception
	 */
	public boolean Save(List<?> mList) throws Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		Transaction mTran = mSec.beginTransaction();

		try
		{
			int i = 1;
			for (Object mObj : mList)
			{
				mSec.save(mObj);

				if (i++ % 20 == 0)
				{
					// 20, same as the JDBC batch size flush a batch of inserts
					// and release memory:
					mSec.flush();
					
				}
			}
			mTran.commit();
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}

		return true;
	}

	public boolean Delete() throws Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		Transaction mTran = mSec.beginTransaction();

		try
		{
			mSec.delete(this);
			mTran.commit();
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}

		return true;
	}

	public boolean Delete(Object mObj) throws Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		Transaction mTran = mSec.beginTransaction();

		try
		{
			mSec.delete(mObj);
			mTran.commit();
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}

		return true;
	}

	/**
	 * Xóa 1 danh sách
	 * 
	 * @param mList
	 * @return
	 * @throws Exception
	 */
	public boolean Delete(List<?> mList) throws Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		Transaction mTran = mSec.beginTransaction();

		try
		{
			int i = 1;
			for (Object mObj : mList)
			{
				mSec.delete(mObj);

				if (i++ % 20 == 0)
				{
					// 20, same as the JDBC batch size flush a batch of inserts
					// and release memory:
					mSec.flush();
					
				}
			}
			mTran.commit();
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}

		return true;
	}

	public boolean Update() throws Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		Transaction mTran = mSec.beginTransaction();

		try
		{
			mSec.update(this);
			mTran.commit();
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}

		return true;
	}

	public boolean Update(List<?> mList) throws Exception
	{
		Session mSec = HibernateSessionFactory.getSession();
		Transaction mTran = mSec.beginTransaction();

		try
		{
			int i = 1;
			for (Object mObj : mList)
			{
				mSec.update(mObj);

				if (i++ % 20 == 0)
				{
					// 20, same as the JDBC batch size flush a batch of inserts
					// and release memory:
					mSec.flush();
					
				}
			}
			mTran.commit();
		}
		catch (Exception ex)
		{
			mTran.rollback();
			throw ex;
		}
		finally
		{
			HibernateSessionFactory.closeSession();
		}

		return true;
	}
}
