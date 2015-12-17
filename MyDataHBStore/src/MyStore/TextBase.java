package MyStore;

import java.sql.SQLException;
import uti.utility.MyText;
import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;
public class TextBase
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public TextBase(DBConfig mConfigObj) throws Exception
	{
		try
		{
			mExec = new MyExecuteData(mConfigObj);
			mGet = new MyGetData(mConfigObj);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public MyTableModel Select(int Type) throws Exception, SQLException
	{
		String Query = "";
		try
		{
			if (Type == 0)// Lấy tất cả dữ liệu
			{
				Query = " SELECT TOP 10 * FROM STKText";
			}

			return mGet.GetData_Query(Query);
		}
		catch (SQLException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * 
	 * @param Type
	 *            Cach thuc lay du lieu Type =1: Lay thong tin chi tiet 1
	 *            TextBase (Para_1 = TextBaseID)
	 * @param Para_1
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1) throws Exception, SQLException
	{
		String Query = "";
		try
		{
			if (Type == 0)// Lấy tất cả dữ liệu
			{
				Query = " SELECT TOP 10 * FROM STKText";
			}
			else if (Type == 1)// Lay thong tin chi tiet cua 1 TextBase
			{
				// Xoa bo ky tu dac biet, chi giu lai ky tu so
				Para_1 = MyText.RemoveSpecialLetter(1, Para_1);
				Query = " SELECT * FROM STKText WHERE IsActive = 1 AND STKTextID = " + Para_1;
			}

			return mGet.GetData_Query(Query);
		}
		catch (SQLException ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
