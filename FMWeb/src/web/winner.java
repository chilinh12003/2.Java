package web;


import load.fix.LFooter;
import load.fix.LHeader;
import load.fix.LWinner;
import my.base.web.webBase;

public class winner extends webBase
{
	public void buildAndWriteHTML()
	{
		try
		{
			LHeader mHeader = new LHeader(this);
			write(mHeader.getHTML());
			
			LWinner mWinner = new LWinner(this);
			write(mWinner.getHTML());
			
			LFooter mFooter = new LFooter(this);
			write(mFooter.getHTML());
		}
		catch(Exception ex)
		{
			mLog.log.error(ex);
		}
	}

}
