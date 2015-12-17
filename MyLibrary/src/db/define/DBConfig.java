package db.define;

import db.connect.MyGetData;

/**
 * Chứa thông tin cấu hình cho các Connection
 * 
 * @author Administrator
 * 
 */
public class DBConfig
{
	/**
	 * Tổng hợp lỗi của SQLState
	 * @author Administrator
	 *
	 */
	public enum  SQLState
	{
		Default("-1"),
		/**
		 * Lỗi timeout khi kết nối sql
		 */
		SQLState_TimeOut ("08S01"),
		
		;
		private String value;

		private SQLState(String value)
		{
			this.value = value;
		}

		public String GetValue()
		{
			return this.value;
		}

		public static SQLState FromString(String iValue)
		{
			for (SQLState type : SQLState.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Default;
		}

	}
	
	/**
	 * Tổng hợp lỗi của SQLErrorCode
	 * @author Administrator
	 *
	 */
	public enum  SQLErrorCode
	{
		Default(-1),		
		
		/**
		 * Lỗi timeout khi kết nối sql
		 */
		SQLErrorCode_TimeOut (0),
		
		;
		private int value;

		private SQLErrorCode(int value)
		{
			this.value = value;
		}

		public int GetValue()
		{
			return this.value;
		}

		public static SQLErrorCode FromInt(int iValue)
		{
			for (SQLErrorCode type : SQLErrorCode.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Default;
		}

	}
	
	private String _PoolName = "Default";
	public String ConfigPath = "ProxoolConfig.xml";

	public String GetPoolName()
	{
		if (AutoSwitchConnection && OrderConnection > 0)
			return this._PoolName + Integer.toString(OrderConnection);
		else return this._PoolName;
	}
	
	public void SetPoolName(String PoolName)
	{
		this._PoolName = PoolName;
	}

	public enum ConfigType
	{
		Nothing(0),

		/**
		 * Chỉ tồn tại poolname
		 */
		ExistPoolName(1),

		/**
		 * Chỉ tồn tại ExistPath
		 */
		ExistPath(2),

		/**
		 * Tồn tại cả 2
		 */
		Both(3);

		private int value;

		private ConfigType(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static ConfigType FromInt(Integer iValue)
		{
			for (ConfigType type : ConfigType.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Nothing;
		}
	}

	public DBConfig(String PoolName)
	{
		SetPoolName(PoolName);
	}

	public DBConfig(String ConfigPath, String PoolName)
	{
		this.ConfigPath = ConfigPath;
		SetPoolName(PoolName);
	}
	
	public DBConfig(String ConfigPath, String PoolName, boolean AutoSwitchConnection, int MaxConnectionBackup)
	{
		this.ConfigPath = ConfigPath;
		this.AutoSwitchConnection = AutoSwitchConnection;
		this.MaxConnectionBackup = MaxConnectionBackup;
		SetPoolName(PoolName);
	}

	/**
	 * Cho phép tự động chuyển connection sang con db backup
	 */
	public boolean AutoSwitchConnection = false;

	/**
	 * Cho biết connection đang sử dụng là gì. <br>
	 * OrderConnection = 0 : sử dụng poolname giữ nguyên OrderConnection = 1 :
	 * sử dụng poolname = poolname1
	 */
	public int OrderConnection = 0;

	/**
	 * Số lượng connection backup.
	 */
	public int MaxConnectionBackup = 1;
	
	public synchronized void  SetOrderConnection() throws Exception
	{
		try
		{
			if(OrderConnection < MaxConnectionBackup)
			{
				OrderConnection++; 
			}
			else
			{
				OrderConnection = MaxConnectionBackup;
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
	public ConfigType GetConfigType()
	{
		if (GetPoolName().equalsIgnoreCase("") && ConfigPath.equalsIgnoreCase(""))
			return ConfigType.Nothing;

		if (!GetPoolName().equalsIgnoreCase("") && ConfigPath.equalsIgnoreCase(""))
			return ConfigType.ExistPoolName;

		if (GetPoolName().equalsIgnoreCase("") && !ConfigPath.equalsIgnoreCase(""))
			return ConfigType.ExistPath;

		if (!GetPoolName().equalsIgnoreCase("") && !ConfigPath.equalsIgnoreCase(""))
			return ConfigType.Both;
		return ConfigType.Nothing;
	}

	/**
	 * Kiểm tra kết nối, đảm bảo rẳng proxool kết nối OK thì mới chạy tiếp.Nhiều
	 * trường hợp nhiều thread chạy, thread đầu chưa chạy xong thì thread 2 đã
	 * chạy--> load nhiều lần proxool hoặc chưa load xong đã get connection -->
	 * lỗi duplicate pool hoặc không có driver
	 * 
	 * @throws Exception
	 */
	public void FisrtTestConnection() throws Exception
	{
		try
		{
			MyGetData mGet = new MyGetData(this);
			mGet.GetData_Query("Select 1");
		}
		catch (Exception ex)
		{
			System.out.println(ex);
			throw ex;
		}
	}
}
