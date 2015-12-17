package web;

import uti.MyLogger;
import load.fix.LFooter;
import load.fix.LGuide;
import load.fix.LHeader;
import load.fix.LHome;
import load.fix.LRule;
import load.fix.LService;
import my.base.web.webBase;

public class rule extends webBase
{
	public void buildAndWriteHTML()
	{
		try
		{
			LHeader mHeader = new LHeader(this);
			write(mHeader.getHTML());
			
			LRule mRule = new LRule(this);
			write(mRule.getHTML());
			
			LFooter mFooter = new LFooter(this);
			write(mFooter.getHTML());
		}
		catch(Exception ex)
		{
			mLog.log.error(ex);
		}
	}

}
