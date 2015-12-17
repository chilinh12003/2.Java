package dat.service;

import java.sql.SQLException;
import java.util.Vector;

import uti.utility.MyText;
import db.connect.MyExecuteData;
import db.connect.MyGetData;
import db.define.DBConfig;
import db.define.MyTableModel;

public class Service
{
	public enum ServiceType
	{
		NoThing(0),
		/**
		 * Push tin ngay nếu có tin trong table news
		 */
		PushNow(1),
		/**
		 * Push tin theo khung giờ nhất định
		 */
		PushByTimer(2),
		/**
		 * Push tin theo khung giờ, nếu có tin HOT thì push ngay
		 */
		PushByTimerAndHot(3);

		private int value;

		private ServiceType(int value)
		{
			this.value = value;
		}

		public int GetValue()
		{
			return this.value;
		}

		public static ServiceType FromInt(int iValue)
		{
			for (ServiceType type : ServiceType.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return NoThing;
		}
	}

	public MyExecuteData mExec;
	public MyGetData mGet;

	public Service(DBConfig mConfigObj) throws Exception
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
	 *            <p>
	 *            Type = 4: Lất tất cả đã được kích thoạt
	 *            </p>
	 * @param Para_1
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public MyTableModel Select(int Type, String Para_1) throws Exception, SQLException
	{
		try
		{
			String Arr_Name[] = { "Type", "Para_1" };
			String Arr_Value[] = { Integer.toString(Type), Para_1 };

			return mGet.GetData_Pro("Sp_Service_Select", Arr_Name, Arr_Value);
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
	 * Lấy danh sách các Service trong database đã được kích hoạt
	 * 
	 * @return
	 * @throws Exception
	 */
	public Vector<ServiceObject> GetAllService() throws Exception
	{
		try
		{
			Vector<ServiceObject> mList = new Vector<ServiceObject>();
			MyTableModel mTable = Select(4, "");
			if (mTable.IsEmpty())
				return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				ServiceObject mObject = new ServiceObject();

				mObject.ServiceID = Integer.parseInt(mTable.GetValueAt(i, "ServiceID").toString());
				mObject.ServiceName = MyText.RemoveSignVietnameseString((String) mTable.GetValueAt(i, "ServiceName"));
				mObject.PushTime = (String) mTable.GetValueAt(i, "PushTime");
				mObject.RegKeyword = (String) mTable.GetValueAt(i, "RegKeyword");
				mObject.DeregKeyword = (String) mTable.GetValueAt(i, "DeregKeyword");
				mObject.PacketName = (String) mTable.GetValueAt(i, "PacketName");
				if (mTable.GetValueAt(i, "TableName") != null)
				mObject.TableName = (String) mTable.GetValueAt(i, "TableName");
				mObject.IsFree = mTable.GetValueAt(i, "IsFree").toString() == "1" ? true : false;

				if (mTable.GetValueAt(i, "ServiceTypeID") != null)
					mObject.mServiceType = ServiceType.FromInt(Integer.parseInt(mTable.GetValueAt(i, "ServiceTypeID").toString()));

				if (mTable.GetValueAt(i, "MTNumber") != null)
					mObject.MTNumber = Integer.parseInt(mTable.GetValueAt(i, "MTNumber").toString());

				mList.add(mObject);
			}
			return mList;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
