package web;

import uti.MyLogger;
import load.fix.LFooter;
import load.fix.LHeader;
import load.fix.LHome;
import my.base.web.webBase;

public class home extends webBase
{
	public void buildAndWriteHTML()
	{
		try
		{
			LHeader mHeader = new LHeader(this);
			write(mHeader.getHTML());
			
			LHome mHome = new LHome(this);
			write(mHome.getHTML());
			
			LFooter mFooter = new LFooter(this);
			write(mFooter.getHTML());
		}
		catch(Exception ex)
		{
			mLog.log.error(ex);
		}
	}

}
