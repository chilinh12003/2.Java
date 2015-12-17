package web;

import load.fix.LContact;
import load.fix.LFooter;
import load.fix.LHeader;
import my.base.web.webBase;

public class contact extends webBase
{
	public void buildAndWriteHTML()
	{
		try
		{
			LHeader mHeader = new LHeader(this);
			write(mHeader.getHTML());
			
			LContact mContact = new LContact(this);
			write(mContact.getHTML());
			
			LFooter mFooter = new LFooter(this);
			write(mFooter.getHTML());
		}
		catch(Exception ex)
		{
			mLog.log.error(ex);
		}
	}

}
