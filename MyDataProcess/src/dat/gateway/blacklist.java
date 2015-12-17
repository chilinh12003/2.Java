package dat.gateway;

import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class blacklist
{
	public MyExecuteData mExec;
	public MyGetData mGet;
	

	public blacklist(DBConfig mDBConfig) throws Exception
	{
		try
		{
			mExec = new MyExecuteData(mDBConfig);
			mGet = new MyGetData(mDBConfig);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}


	
	public MyTableModel Select(int Type) throws Exception
	{
		String Query = "";
		try
		{
			if(Type == 0)//Lấy tất cả dữ liệu
			{
				Query = " SELECT * FROM blacklist";
			}
			return mGet.GetData_Query(Query);
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}

}
