package uti.utility;

public class VNPApplication
{
	public int GetValue()
	{
		return AppID;
	}
	public String toString()
	{
		return AppName;
	}
	public enum TelcoApplication
	{
		NoThing(0), CCOS(1), VASPORTAL(2), VASVOUCHER(3), VASDEALER(4), MOBILEADS(5), USSDGW(6), CROSSSALE(7), TEST(
				8), MOBILE_ADS(9), IOS(10), MONITOR(11), VASBUNDLE(12), UNSUB(13), UNSUBALL(14), ;

		private int value;

		private TelcoApplication(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static TelcoApplication FromInt(int iValue)
		{
			for (TelcoApplication type : TelcoApplication.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return NoThing;
		}
	}

	public String AppName = "";
	public int AppID = 0;
	public TelcoApplication mApp = TelcoApplication.NoThing;
	
	public VNPApplication()
	{
		AppName = TelcoApplication.NoThing.toString();
	}

	public static VNPApplication FromInt(int iValue)
	{
		VNPApplication mVNPApp = new VNPApplication();
		try
		{
			mVNPApp.mApp= TelcoApplication.FromInt(iValue);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			mVNPApp.mApp =  TelcoApplication.NoThing;
		}
		mVNPApp.AppID= mVNPApp.mApp.GetValue();
		mVNPApp.AppName= mVNPApp.mApp.toString();
		return mVNPApp;
	}
	
	public static VNPApplication valueOf(String AppName)
	{
		VNPApplication mVNPApp = new VNPApplication();
		try
		{
			mVNPApp.AppName = AppName;
			mVNPApp.mApp= TelcoApplication.valueOf(AppName.toUpperCase());
		}
		catch(Exception ex)
		{
			mVNPApp.mApp =  TelcoApplication.NoThing;
		}
		mVNPApp.AppID= mVNPApp.mApp.GetValue();
		
		return mVNPApp;
	}
	
	public boolean IsNull()
	{
		if(AppName.equalsIgnoreCase(TelcoApplication.NoThing.toString()))
			return true;
		else
			return false;
	}
}
