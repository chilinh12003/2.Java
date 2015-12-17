package web;

import uti.MyLogger;
import load.fix.LFaq;
import load.fix.LFooter;
import load.fix.LHeader;
import load.fix.LHome;
import load.fix.LService;
import my.base.web.webBase;

public class faq extends webBase
{
	public void buildAndWriteHTML()
	{
		try
		{
			LHeader mHeader = new LHeader(this);
			write(mHeader.getHTML());
			
			LFaq mFaq = new LFaq(this);
			write(mFaq.getHTML());
			
			LFooter mFooter = new LFooter(this);
			write(mFooter.getHTML());
		}
		catch(Exception ex)
		{
			mLog.log.error(ex);
		}
	}

}
