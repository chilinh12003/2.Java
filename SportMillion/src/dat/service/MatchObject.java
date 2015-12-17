package dat.service;

import java.util.Date;

import uti.utility.MyConfig;
import db.define.MyTableModel;

public class MatchObject
{
	public int MatchID = 0;
	public String TeamName1 = "";
	public String TeamName2 = "";
	public Date PlayDate = null;
	public Date BeginDate = null;
	public Date EndDate = null;
	public Date CodeDate = null;
	public String AnswerKQ = "";
	public String AnswerBT = "";
	public String AnswerGB = "";
	public String AnswerTS = "";
	public String AnswerTV = "";
	public int StatusID  = 0;
	public int Priority = 0;
	public boolean IsCompute = false;
	
 	public boolean IsNull()
	{
		if (MatchID < 1)
			return true;
		else
			return false;
	}

	public String GetMatchName()
	{
		if(MatchID == 0)
		{
			return "";
		}
		else
		{
			return TeamName1 +"-"+TeamName2;
		}
	}
	public String GetPlayDate()
	{
		if(PlayDate == null)
			return "";
		else
			return MyConfig.Get_DateFormat_VNShort().format(PlayDate);
	}
	public String GetCodeDate()
	{
		if(CodeDate == null)
			return "";
		else
			return MyConfig.Get_DateFormat_VNShort().format(CodeDate);
	}
	public String GetPlayHour()
	{
		if(PlayDate == null)
			return "";
		else
			return MyConfig.Get_DateFormat_VNTimeShort().format(PlayDate);
	}
	
	public String GetBeginDate()
	{
		if(BeginDate == null)
			return "";
		else
			return MyConfig.Get_DateFormat_VNShort().format(BeginDate);
	}
	public String GetBeginHour()
	{
		if(BeginDate == null)
			return "";
		else
			return MyConfig.Get_DateFormat_VNTimeShort().format(BeginDate);
	}
	
	public String GetEndDate()
	{
		if(EndDate == null)
			return "";
		else
			return MyConfig.Get_DateFormat_VNShort().format(EndDate);
	}
	public String GetEndHour()
	{
		if(EndDate == null)
			return "";
		else
			return MyConfig.Get_DateFormat_VNTimeShort().format(EndDate);
	}
	
	public static  MatchObject Convert(MyTableModel mTable) throws Exception
	{
		try
		{
			MatchObject mObject = new MatchObject();
			if (mTable.GetRowCount() < 1)
				return mObject;			

			mObject.MatchID = Integer.parseInt(mTable.GetValueAt(0, "MatchID").toString());
			mObject.TeamName1 = mTable.GetValueAt(0, "TeamName1").toString();
			mObject.TeamName2 = mTable.GetValueAt(0, "TeamName2").toString();
			
			mObject.PlayDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(0, "PlayDate").toString());
			mObject.BeginDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(0, "BeginDate").toString());
			mObject.EndDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(0, "EndDate").toString());
			mObject.CodeDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(0, "CodeDate").toString());
			mObject.PlayDate = MyConfig.Get_DateFormat_InsertDB().parse(mTable.GetValueAt(0, "PlayDate").toString());
			
			mObject.AnswerKQ = mTable.GetValueAt(0, "AnswerKQ").toString();
			mObject.AnswerBT = mTable.GetValueAt(0, "AnswerBT").toString();
			mObject.AnswerGB = mTable.GetValueAt(0, "AnswerGB").toString();
			mObject.AnswerTS = mTable.GetValueAt(0, "AnswerTS").toString();
			mObject.AnswerTV = mTable.GetValueAt(0, "AnswerTV").toString();
			
			mObject.StatusID = Integer.parseInt(mTable.GetValueAt(0, "StatusID").toString());
			mObject.Priority = Integer.parseInt(mTable.GetValueAt(0, "Priority").toString());
			mObject.IsCompute = Boolean.parseBoolean(mTable.GetValueAt(0, "IsCompute").toString());
			
			return mObject;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
}
