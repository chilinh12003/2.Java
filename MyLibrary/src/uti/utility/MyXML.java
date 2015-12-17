package uti.utility;

import java.util.Iterator;
import java.util.List;

public class MyXML
{
	/**
	 * Tạo chuỗi XML cho insert dữ liệu xuống DB Sql server
	 * @param DataSetName
	 * Mặc định là "Parent"
	 * @param TableName
	 * Mặc định là "Child"
	 * @param Arr_ColumnName
	 * Mảng các columnName
	 * @param List_Value
	 * List các mảng dữ liệu cho từng row
	 * @return
	 * @throws Exception
	 */
	public static String BuildXML(String DataSetName, String TableName, String[] Arr_ColumnName, List<String[]> List_Value) throws Exception
	{
		try
		{
			if(DataSetName == null || DataSetName=="")
			{
				DataSetName = "Parent";
			}
			if(TableName == null || TableName=="")
			{
				TableName = "Child";
			}
			
			String XML = "";
			String XML_Child = "";
			String Format_Parent = "<"+DataSetName+">%s</"+DataSetName+">";
			String Format_Child = "<"+TableName+">%s</"+TableName+">";
			String Format_Row = "";
			
			for(int i =0; i < Arr_ColumnName.length; i++)
			{
				Format_Row += "<"+ Arr_ColumnName[i]+">%s"+ "</"+ Arr_ColumnName[i]+">";
			}
			Format_Child = String.format(Format_Child, Format_Row);
			
			for(Iterator<String[]> item = List_Value.iterator(); item.hasNext();)
			{
				XML_Child += String.format(Format_Child, item);
			}
			
			XML = String.format(Format_Parent, XML_Child);
			
			return XML;
		}
		catch(Exception ex)
		{
			throw ex;
		}
	}
	
	/**
	 * Thay thế các ký tự đặc biệt của XML
	 * @param Content
	 * @return
	 * @throws Exception
	 */
	public static String ReplaceXMLSpecial(String Content) throws Exception
	{
		
		Content = MyText.RemoveSpecialLetter(2, Content, ".,;?:-_/[]{}()@!%&*=+ ><'\"");
		
		Content = Content.replace("<", "&lt;");
		Content = Content.replace(">", "&gt;");
		
		Content = Content.replace("&", "&amp;");
		
		Content = Content.replace("'", "&apos;");
		
		Content = Content.replace("\"", "&quot;");
		return Content;
		
	}
}
