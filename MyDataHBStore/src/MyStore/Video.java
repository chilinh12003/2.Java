package MyStore;

import java.sql.SQLException;
import uti.utility.MyText;
import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class Video
{
	public MyExecuteData mExec;
	public MyGetData mGet;

	public Video(DBConfig mConfigObj) throws Exception
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
	/**
	 * 
	 * @param Type
	 *            Cach thuc lay du lieu Type =1: Lay thong tin chi tiet 1 Video
	 *            (Para_1 = VideoID)
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
			if (Type == 1)// Lay thong tin chi tiet cua 1 Video
			{
				// Xoa bo ky tu dac biet, chi giu lai ky tu so
				Para_1 = MyText.RemoveSpecialLetter(1, Para_1);
				Query = " SELECT * FROM Video WHERE IsActive = 1 AND VideoID = " + Para_1;
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
