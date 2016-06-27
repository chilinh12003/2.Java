package pro.Server;
public enum ParaType
	{
		/*
		 * 0: ??ng k� sub 1: H?y sub 2: T?m d?ng 3: ??ng k� l?i
		 */

		Nothing(-1), Register(0), Deregister(1), Pause(300), ReturnReg(301), ;

		private int value;

		private ParaType(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static ParaType FromValue(int iValue)
		{
			for (ParaType type : ParaType.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Nothing;
		}
	}

	