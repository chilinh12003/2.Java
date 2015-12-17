package web;

import uti.MyLogger;
import load.fix.LFooter;
import load.fix.LGuide;
import load.fix.LHeader;
import load.fix.LHome;
import load.fix.LService;
import my.base.web.webBase;

public class guide extends webBase
{
	public void buildAndWriteHTML()
	{
		try
		{
			LHeader mHeader = new LHeader(this);
			write(mHeader.getHTML());
			
			LGuide mGuide = new LGuide(this);
			write(mGuide.getHTML());
			
			LFooter mFooter = new LFooter(this);
			write(mFooter.getHTML());
		}
		catch(Exception ex)
		{
			mLog.log.error(ex);
		}
	}

}
