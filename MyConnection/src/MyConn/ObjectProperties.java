package MyConn;

public class ObjectProperties
{
	public String Alias = "";
	public String DriverURL = "";
	public String DriverClass = "";
	public String UserName = "";
	public String Password = "";
	public boolean CloseWhenFisnish = false;
	public Integer LiveTime = 60;
	public boolean IsEmpty()
	{
		if (Alias.equals(""))
			return true;
		return false;
	}
}
