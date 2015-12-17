package db.define;

import java.util.Vector;

public class MyDataRow implements Cloneable
{
	private MyDataColumn mColumn = null;
	private Vector<Object> ThisRow = null;

	public MyDataRow(MyDataColumn Column) throws Exception
	{
		try
		{
			this.mColumn = Column;

			ThisRow = new Vector<Object>();
			for (int i = 0; i < mColumn.Size(); i++)
			{
				ThisRow.add(new Object());
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Vector<Object> GetRowValue()
	{
		return ThisRow;
	}

	public MyDataColumn GetColumn()
	{
		return mColumn;

	}

	public void SetRowValue(Vector<Object> mValue)
	{

		this.ThisRow = mValue;

	}

	public MyDataRow CreateNewRow() throws Exception
	{
		try
		{
			ThisRow = new Vector<Object>();
			for (int i = 0; i < mColumn.Size(); i++)
			{
				ThisRow.add("");
			}
			return this;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void SetValueCell(String ColumnName, Object CellValue) throws Exception
	{
		try
		{
			if (!mColumn.Contain(ColumnName))
				throw new Exception("Ten Column (" + ColumnName + ") khong ton tai trong table");
			
			ThisRow.set(mColumn.GetColumnIndex(ColumnName), CellValue);
			
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Object GetValueCell(int ColumnIndex) throws Exception
	{
		try
		{
			return ThisRow.get(ColumnIndex);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Object GetValueCell(String ColumnName) throws Exception
	{
		try
		{

			if (!mColumn.Contain(ColumnName))
				throw new Exception("Ten Column (" + ColumnName + ") khong ton tai trong table");

			return ThisRow.get(mColumn.GetColumnIndex(ColumnName));
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public MyDataRow clone() throws CloneNotSupportedException
	{
		return (MyDataRow) super.clone();
	}

	public boolean ClearRow() throws Exception
	{
		try
		{
			if (ThisRow == null || ThisRow.size() < 1)
				return true;
			else
			{
				ThisRow.clear();
				return true;
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

}
