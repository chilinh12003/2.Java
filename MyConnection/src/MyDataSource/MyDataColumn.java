package MyDataSource;

import java.util.Hashtable;

public class MyDataColumn
{
	private Hashtable<String, Integer> ListColumn_Name = new Hashtable<String, Integer>();
	private Hashtable<Integer, String> ListColumn_Index = new Hashtable<Integer, String>();

	public Integer Size()
	{
		return ListColumn_Index.size();
	}
	
	public MyDataColumn(String[] Arr_ColumnNane) throws Exception
	{
		try
		{
			for (Integer i = 0; i < Arr_ColumnNane.length; i++)
			{
				ListColumn_Name.put(Arr_ColumnNane[i].toLowerCase(), i);
				ListColumn_Index.put(i, Arr_ColumnNane[i]);
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public String GetColumnName(Integer Index) throws Exception
	{
		try
		{
			if (!ListColumn_Index.containsKey(Index))
				throw new Exception("Index = " + Index.toString() + " khong ton tai trong Table");
			else
				return ListColumn_Index.get(Index);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Integer GetColumnIndex(String Name) throws Exception
	{
		try
		{
			String Name_Low = Name.toLowerCase();

			if (!ListColumn_Name.containsKey(Name_Low))
				throw new Exception("Index = " + Name + " khong ton tai trong Table");
			else
				return ListColumn_Name.get(Name_Low);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public boolean Contain(String ColumnName)
	{
		ColumnName = ColumnName.toLowerCase();
		return ListColumn_Name.containsKey(ColumnName);
	}
}
