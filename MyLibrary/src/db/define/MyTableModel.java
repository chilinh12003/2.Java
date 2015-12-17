package db.define;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class MyTableModel implements Cloneable
{
	private MyDataColumn mColumn = null;
	private MyDataRow mRow = null;

	private DefaultTableModel mTable = null;

	public MyTableModel(Vector<Vector<Object>> row, Vector<String> columnNames) throws Exception
	{
		try
		{
			this.mTable = new DefaultTableModel(row, columnNames);

			if (this.mTable != null)
			{
				String[] Arr_ColumnName = new String[this.mTable.getColumnCount()];

				for (int i = 0; i < this.mTable.getColumnCount(); i++)
				{
					Arr_ColumnName[i] = GetColumnName(i);
				}

				mColumn = new MyDataColumn(Arr_ColumnName);
				mRow = new MyDataRow(mColumn);
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public MyTableModel(ResultSet mResult) throws Exception
	{
		try
		{
			this.mTable = ResultSetToTableModel(mResult);

			// lay column cho vao hashtable
			if (this.mTable != null)
			{
				String[] Arr_ColumnName = new String[this.mTable.getColumnCount()];

				for (int i = 0; i < this.mTable.getColumnCount(); i++)
				{
					Arr_ColumnName[i] = GetColumnName(i);
				}

				mColumn = new MyDataColumn(Arr_ColumnName);
				mRow = new MyDataRow(mColumn);
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Tạo một row mới
	 * 
	 * @return
	 * @throws Exception
	 */
	public MyDataRow CreateNewRow() throws Exception
	{
		return mRow.CreateNewRow();
	}

	/**
	 * Thêm 1 row mới vào Table
	 * 
	 * @param NewRow
	 * @throws Exception
	 */
	public void AddNewRow(MyDataRow NewRow) throws Exception
	{
		try
		{
			mTable.addRow(NewRow.GetRowValue());
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Lấy một Row trong Table theo index truy�?n vào
	 * 
	 * @param Index
	 * @return
	 * @throws Exception
	 */
	public MyDataRow GetRow(Integer Index) throws Exception
	{
		try
		{
			if (Index < 0 || Index >= mTable.getRowCount())
				throw new Exception("Index = " + Index.toString() + " khong ton tai trong table");

			Vector<Object> mValue = new Vector<Object>();

			for (int i = 0; i < mColumn.Size(); i++)
			{
				mValue.add(GetValueAt(Index, i));
			}

			MyDataRow mNewRow = new MyDataRow(mColumn);
			mNewRow.SetRowValue(mValue);
			return mNewRow;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	/**
	 * Cho biet Table co du lieu hay khong
	 * 
	 * @return
	 */
	public boolean IsEmpty()
	{
		if (mTable == null || mTable.getRowCount() < 1)
			return true;
		else
			return false;
	}

	public Integer GetRowCount() throws Exception
	{
		try
		{
			if (this.mTable == null)
				return 0;

			return mTable.getRowCount();
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public int GetColumnCount() throws Exception
	{
		try
		{
			if (this.mTable == null)
				return 0;
			return mTable.getColumnCount();
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public String GetColumnName(int columnIndex) throws Exception
	{
		try
		{
			if (this.mTable == null)
				return "";

			return mTable.getColumnName(columnIndex);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Class<?> GetColumnClass(int columnIndex) throws Exception
	{
		try
		{
			if (this.mTable == null)
				return null;

			return mTable.getColumnClass(columnIndex);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public boolean IsCellEditable(int rowIndex, int columnIndex) throws Exception
	{
		try
		{
			if (this.mTable == null)
				return false;

			return mTable.isCellEditable(rowIndex, columnIndex);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Object GetValueAt(int rowIndex, int columnIndex) throws Exception
	{
		try
		{
			if (this.mTable == null)
				return null;
			return mTable.getValueAt(rowIndex, columnIndex);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public boolean CheckColumnsExists(String ColumnName) throws Exception
	{
		try
		{
			return mColumn.Contain(ColumnName);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Object GetValueAt(int rowIndex, String columnName) throws Exception
	{
		try
		{
			if (this.mTable == null)
				return null;

			if (!mColumn.Contain(columnName))
				throw new Exception("Tên column không tồn tại trong table.");

			return mTable.getValueAt(rowIndex, mColumn.GetColumnIndex(columnName));
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void SetValueAt(Object aValue, int rowIndex, int columnIndex) throws Exception
	{
		try
		{
			if (this.mTable == null)
				return;
			mTable.setValueAt(aValue, rowIndex, columnIndex);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void SetValueAt(Object aValue, int rowIndex, String columnName) throws Exception
	{
		try
		{
			if (this.mTable == null)
				return;

			if (!mColumn.Contain(columnName))
				throw new Exception("Tên column không tồn tại trong table.");

			mTable.setValueAt(aValue, rowIndex, mColumn.GetColumnIndex(columnName));
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void AddTableModelListener(TableModelListener l) throws Exception
	{
		try
		{
			if (this.mTable == null)
				return;
			mTable.addTableModelListener(l);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void RemoveTableModelListener(TableModelListener l) throws Exception
	{
		try
		{
			if (this.mTable == null)
				return;
			mTable.removeTableModelListener(l);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void Clear() throws Exception
	{
		try
		{
			while(mTable.getRowCount() > 0)
			{
				mTable.removeRow(0);
			}
			mRow.ClearRow();
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public DefaultTableModel ResultSetToTableModel(ResultSet rs)
	{
		try
		{
			if (rs == null)
				return null;

			ResultSetMetaData metaData = rs.getMetaData();
			int numberOfColumns = metaData.getColumnCount();

			Vector<String> columnNames = new Vector<String>();

			// Get the column names
			for (int column = 0; column < numberOfColumns; column++)
			{
				columnNames.addElement(metaData.getColumnLabel(column + 1));
			}

			// Get all rows.
			Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

			while (rs.next())
			{
				Vector<Object> newRow = new Vector<Object>();

				for (int i = 1; i <= numberOfColumns; i++)
				{
					newRow.addElement(rs.getObject(i));
				}

				rows.addElement(newRow);
			}

			return new DefaultTableModel(rows, columnNames);
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return null;
		}
	}

	public String GetXML() throws Exception
	{
		try
		{
			return GetXML("Parent", "Child");
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public String GetXML(String DataSetName, String TableName) throws Exception
	{
		try
		{
			if (DataSetName == null || DataSetName == "")
			{
				DataSetName = "Parent";
			}
			if (TableName == null || TableName == "")
			{
				TableName = "Child";
			}

			String XML = "";
			String XML_Child = "";
			String Format_Parent = "<" + DataSetName + ">%s</" + DataSetName + ">";
			String Format_Child = "<" + TableName + ">%s</" + TableName + ">";

			/*
			 * for (int i = 0; i < mColumn.Size(); i++) { Format_Row += "<" +
			 * mColumn.GetColumnName(i) + ">%s" + "</" +
			 * mColumn.GetColumnName(i) + ">"; } Format_Child =
			 * String.format(Format_Child, Format_Row);
			 */

			for (int i = 0; i < GetRowCount(); i++)
			{
				String XML_Row = "";
				for (int j = 0; j < GetColumnCount(); j++)
				{
					Object mObj = GetValueAt(i, j);
					if (mObj instanceof String)
					{
						mObj = uti.utility.MyXML.ReplaceXMLSpecial(mObj.toString());
					}
					if (mObj == null || mObj.toString() == "")
					{
						continue;
					}
					else
					{
						XML_Row += "<" + mColumn.GetColumnName(j) + ">" + mObj.toString() + "</" + mColumn.GetColumnName(j) + ">";
					}
				}
				XML_Child += String.format(Format_Child, XML_Row);
			}

			XML = String.format(Format_Parent, XML_Child);

			return XML;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}

	public void Import(MyTableModel mTable) throws Exception
	{
		try
		{
			if (mTable.IsEmpty())
			{
				return;
			}

			for (Integer i = 0; i < mTable.GetRowCount(); i++)
			{
				this.AddNewRow(mTable.GetRow(i).clone());
			}
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}
