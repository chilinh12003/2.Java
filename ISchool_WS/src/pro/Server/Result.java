package pro.Server;
public enum Result
	{
		/*
		 * 0 G?i n?i dung cho ng??i d�ng th�nh c�ng 1 G?i n?i dung cho ng??i
		 * d�ng th?t b?i 300 Sai tham s? 301 Sai t�n ??ng nh?p ho?c m?t kh?u 302
		 * Server b?n
		 */

		Success(0), Fail(1),
		/**
		 * Ri�ng enum n�y d�ng ?? ph?c v? cho h�m check invalid para input
		 */
		CheckParaIsOK(2), InputInvalid(300), AccountInvalid(301),
		// L�?i h�? th�?ng
		SystemError(302), ;

		private int value;

		private Result(int value)
		{
			this.value = value;
		}

		public Integer GetValue()
		{
			return this.value;
		}

		public static Result FromValue(int iValue)
		{
			for (Result type : Result.values())
			{
				if (type.GetValue() == iValue)
					return type;
			}
			return Fail;
		}
	}

	