package MyDataSource;

import javax.sql.PooledConnection;

/**
 * Chứa thông tin cấu hình cho các Connection
 * @author Administrator
 *
 */
public class DBConfig
{
	public String PoolName = "Default";
	public String ConfigPath = "ProxoolConfig.xml";
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
				if (type.GetValue() == iValue) return type;
			}
			return Nothing;
		}
	}

	public DBConfig(String PoolName)
	{
		this.PoolName = PoolName;
	}
	
	public DBConfig(String ConfigPath, String PoolName)
	{
		this.ConfigPath = ConfigPath;
		this.PoolName = PoolName;
	}
	
	public ConfigType GetConfigType()
	{
		if(PoolName.equalsIgnoreCase("") && ConfigPath.equalsIgnoreCase(""))
			return ConfigType.Nothing;
		
		if(!PoolName.equalsIgnoreCase("") && ConfigPath.equalsIgnoreCase(""))
		return ConfigType.ExistPoolName;
		
		if(PoolName.equalsIgnoreCase("") && !ConfigPath.equalsIgnoreCase(""))
			return ConfigType.ExistPath;
		
		if(!PoolName.equalsIgnoreCase("") && !ConfigPath.equalsIgnoreCase(""))
			return ConfigType.Both;
		return ConfigType.Nothing;
	}
}
