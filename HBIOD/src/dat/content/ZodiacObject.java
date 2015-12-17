package dat.content;

import java.util.Vector;
import dat.content.Zodiac.ZodiacList;
import db.define.MyDataRow;
import db.define.MyTableModel;

public class ZodiacObject
{
	public ZodiacList mZodiacList = ZodiacList.Nothing;
	public String ZodiacName = "";
	public String Keyword = "";

	public boolean IsNull()
	{
		if (mZodiacList == ZodiacList.Nothing || Keyword.equalsIgnoreCase(""))
			return true;
		else return false;
	}

	public static ZodiacObject Convert(MyDataRow mRow) throws Exception
	{
		try
		{
			ZodiacObject mObject = new ZodiacObject();

			if (mRow == null)
				return mObject;

			mObject.mZodiacList = ZodiacList.FromInt(Integer.parseInt(mRow.GetValueCell("ZodiacID").toString()));
			mObject.ZodiacName = mRow.GetValueCell("ZodiacName").toString();
			mObject.Keyword = mRow.GetValueCell("Keyword").toString().toUpperCase();

			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public static Vector<ZodiacObject> ConvertToList(MyTableModel mTable) throws Exception
	{
		try
		{
			Vector<ZodiacObject> mList = new Vector<ZodiacObject>();
			if (mTable.GetRowCount() < 1)
				return mList;

			for (int i = 0; i < mTable.GetRowCount(); i++)
			{
				ZodiacObject mObject = new ZodiacObject();

				MyDataRow mRow = mTable.GetRow(i);
				mObject = Convert(mRow);
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