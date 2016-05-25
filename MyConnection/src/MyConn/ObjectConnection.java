package MyConn;

import java.sql.Connection;
import java.util.Calendar;

public class ObjectConnection
{
	public Connection mConn = null;
	
	/**
	 * PoolName của connection này
	 */
	public String Alias = "";

	/**
	 * Cho biết connection này đang execute hay đang không làm gì
	 */
	public boolean IsExecuting = false;

	public Integer Index = 0;

	/**
	 * Thời gian connection được khởi tạo
	 */
	public Calendar CreateDate = null;

	/**
	 * Thời gian bắt đầu thực thi lệnh
	 */
	public Calendar ExecuteDate = null;

	/**
	 * Thời gian lần cuối cùng được sử dụng, cũng là thời gian kết thúc thực thi
	 * câu lệnh sql
	 */
	public Calendar FinishDate = null;

	/**
	 * Cho phép đóng connection khi kết thúc thực thi câu lệnh sql
	 */
	public boolean CloseWhenFisnish = true;

	/**
	 * Thời gian cho phép sống 1 connection (được tính bằng giấy)
	 */
	public Integer LiveTime = 60;

	public boolean IsNull()
	{
		if (mConn == null)
			return true;
		else
			return false;
	}

	/**
	 * kiểm tra xem thời gian cho phép chạy connection này còn được phép hay
	 * không
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean CheckLiveTime() throws Exception
	{
		try
		{
			if (CreateDate == null)
				return true;

			Calendar Temp = (Calendar) CreateDate.clone();
			
			Temp.add(Calendar.SECOND, LiveTime);
			
			if (Temp.after(Calendar.getInstance()))
				return true;
			else
				return false;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void Close() throws Exception
	{
		try
		{
			IsExecuting = false;
			FinishDate = Calendar.getInstance();
			if (CloseWhenFisnish)
			{
				if (!mConn.isClosed())
				{
					mConn.close();
				}
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void RealClose() throws Exception
	{
		try
		{
			IsExecuting = false;
			FinishDate = Calendar.getInstance();
			
			if (!mConn.isClosed())
			{
				mConn.close();
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public boolean CheckAllowExecute() throws Exception
	{
		try
		{
			if (mConn == null || mConn.isClosed() || this.IsExecuting == true || !this.CheckLiveTime())
				return false;

			return true;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}
