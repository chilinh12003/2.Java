package MyUtility;

import java.sql.Date;
import java.util.Calendar;

public class MyDate
{
	/**
	 * lấy ngày đầu tiên của tháng hiện tại
	 * 
	 * @return
	 */
	public static Date GetFirstDayOfMonth()
	{
		Calendar mCal_First = Calendar.getInstance();
		Calendar mCal_Curr = Calendar.getInstance();

		mCal_First.set(mCal_Curr.get(Calendar.YEAR), mCal_Curr.get(Calendar.MONTH), 1);
		return (Date) mCal_First.getTime();
	}

	/**
	 * Lấy hiệu 2 ngày với nhau
	 * 
	 * @param mCal_Begin
	 * @param mCal_End
	 * @param CalendarField
	 *            : được lấy từ Calendar.SECOND hoặc
	 *            ,Calendar.MINUTE,Calendar.DATE
	 * @return
	 * @throws Exception
	 */
	public static Long SubDate(Calendar mCal_Begin, Calendar mCal_End, int CalendarField) throws Exception
	{
		long BeginSecond = mCal_Begin.getTimeInMillis();
		long EndSecond = mCal_End.getTimeInMillis();

		long diff = EndSecond - BeginSecond;
		if (Calendar.SECOND == CalendarField)
		{
			long diffSeconds = diff / 1000;
			return diffSeconds;
		}
		else if (Calendar.MINUTE == CalendarField)
		{
			long diffMinutes = diff / (60 * 1000);
			return diffMinutes;
		}
		else if (Calendar.HOUR == CalendarField)
		{
			long diffHours = diff / (60 * 60 * 1000);
			return diffHours;
		}
		else if (Calendar.DATE == CalendarField)
		{

			long diffDays = diff / (24 * 60 * 60 * 1000);
			return diffDays;
		}
		return diff;
	}

	private static long DAY_MILLIS = 1000 * 24 * 60 * 60;

	public static long diffDays(Calendar from, Calendar to)
	{
		Calendar mCal_From = Calendar.getInstance();
		Calendar mCal_To = Calendar.getInstance();
		
		mCal_From.set(from.get(Calendar.YEAR),from.get(Calendar.MONTH),from.get(Calendar.DAY_OF_MONTH),0,0,0);
		mCal_To.set(to.get(Calendar.YEAR),to.get(Calendar.MONTH),to.get(Calendar.DAY_OF_MONTH),0,0,0);
		
		return (mCal_To.getTimeInMillis() - mCal_From.getTimeInMillis()) / DAY_MILLIS;
	}

	/**
	 * So sánh 2 ngày với nhau theo Calender.Field
	 * 
	 * @param mCal_Begin
	 * @param mCal_End
	 * @param CalendarField
	 * @return
	 * @throws Exception
	 */
	public static boolean Compare(Calendar mCal_Begin, Calendar mCal_End, int CalendarField) throws Exception
	{
		if (Calendar.SECOND == CalendarField)
		{
			if (mCal_Begin.get(Calendar.YEAR) == mCal_End.get(Calendar.YEAR) && mCal_Begin.get(Calendar.MONTH) == mCal_End.get(Calendar.MONTH)
					&& mCal_Begin.get(Calendar.DATE) == mCal_End.get(Calendar.DATE)
					&& mCal_Begin.get(Calendar.HOUR_OF_DAY) == mCal_End.get(Calendar.HOUR_OF_DAY)
					&& mCal_Begin.get(Calendar.MINUTE) == mCal_End.get(Calendar.MINUTE) && mCal_Begin.get(Calendar.SECOND) == mCal_End.get(Calendar.SECOND))
				return true;
			else
				return false;

		}
		else if (Calendar.MINUTE == CalendarField)
		{
			if (mCal_Begin.get(Calendar.YEAR) == mCal_End.get(Calendar.YEAR) && mCal_Begin.get(Calendar.MONTH) == mCal_End.get(Calendar.MONTH)
					&& mCal_Begin.get(Calendar.DATE) == mCal_End.get(Calendar.DATE)
					&& mCal_Begin.get(Calendar.HOUR_OF_DAY) == mCal_End.get(Calendar.HOUR_OF_DAY)
					&& mCal_Begin.get(Calendar.MINUTE) == mCal_End.get(Calendar.MINUTE))
				return true;
			else
				return false;
		}
		else if (Calendar.HOUR_OF_DAY == CalendarField)
		{
			if (mCal_Begin.get(Calendar.YEAR) == mCal_End.get(Calendar.YEAR) && mCal_Begin.get(Calendar.MONTH) == mCal_End.get(Calendar.MONTH)
					&& mCal_Begin.get(Calendar.DATE) == mCal_End.get(Calendar.DATE)
					&& mCal_Begin.get(Calendar.HOUR_OF_DAY) == mCal_End.get(Calendar.HOUR_OF_DAY))
				return true;
			else
				return false;
		}
		else if (Calendar.HOUR == CalendarField)
		{
			if (mCal_Begin.get(Calendar.YEAR) == mCal_End.get(Calendar.YEAR) && mCal_Begin.get(Calendar.MONTH) == mCal_End.get(Calendar.MONTH)
					&& mCal_Begin.get(Calendar.DATE) == mCal_End.get(Calendar.DATE)
					&& mCal_Begin.get(Calendar.HOUR_OF_DAY) == mCal_End.get(Calendar.HOUR_OF_DAY))
				return true;
			else
				return false;
		}
		else if (Calendar.DATE == CalendarField)
		{
			if (mCal_Begin.get(Calendar.YEAR) == mCal_End.get(Calendar.YEAR) && mCal_Begin.get(Calendar.MONTH) == mCal_End.get(Calendar.MONTH)
					&& mCal_Begin.get(Calendar.DATE) == mCal_End.get(Calendar.DATE))
				return true;
			else
				return false;
		}
		else if (Calendar.MONTH == CalendarField)
		{
			if (mCal_Begin.get(Calendar.YEAR) == mCal_End.get(Calendar.YEAR) && mCal_Begin.get(Calendar.MONTH) == mCal_End.get(Calendar.MONTH))
				return true;
			else
				return false;
		}
		else if (Calendar.YEAR == CalendarField)
		{
			if (mCal_Begin.get(Calendar.YEAR) == mCal_End.get(Calendar.YEAR))
				return true;
			else
				return false;
		}
		return false;
	}

	/**
	 * Kiểm tra 1 ngày có phải là ngày hôm nay không
	 * @param CheckDate
	 * @return
	 * @throws Exception
	 */
	public static boolean IsToday(Date CheckDate) throws Exception
	{
		if (CheckDate == null) return false;
		
		Calendar mCal_Current = Calendar.getInstance();
		Calendar mCal_CheckDate = Calendar.getInstance();

		mCal_CheckDate.setTime(CheckDate);
		
		if (mCal_Current.get(Calendar.YEAR) == mCal_CheckDate.get(Calendar.YEAR)
				&& mCal_Current.get(Calendar.MONTH) == mCal_CheckDate.get(Calendar.MONTH)
				&& mCal_Current.get(Calendar.DATE) == mCal_CheckDate.get(Calendar.DATE)) return true;
		
		else return false;
	}
	
}
