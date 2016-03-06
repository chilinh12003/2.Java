package my.ws;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.jws.WebParam;

import db.ChargeLog;
import db.ChargeLogId;
import db.Subscriber;
import db.DefineMt.MTType;
import pro.Server.LocalConfig;
import pro.Server.Program;
import uti.MyCheck;
import uti.MyConfig;
import uti.MyConvert;
import uti.MyDate;
import uti.MyLogger;

@javax.jws.WebService(targetNamespace = "http://resultws/xsd", serviceName = "receiveresultService", portName = "resultRequest", wsdlLocation = "WEB-INF/wsdl/receiveresultService.wsdl")
public class receiveresultDelegate
{

	my.ws.receiveresult receiveresult = new my.ws.receiveresult();

	public String resultRequest(@WebParam(name="username")String username, @WebParam(name="password")String password, 
			@WebParam(name="serviceid")String serviceid, @WebParam(name="msisdn")String msisdn, 
			@WebParam(name="chargetime")String chargetime,@WebParam(name="params")String params, 
			@WebParam(name="mode")String mode, @WebParam(name="amount") int  amount, 
			@WebParam(name="detail") String detail,
			@WebParam(name="Chargecode") String Chargecode,
			@WebParam(name="nextRenewalTime") String nextRenewalTime,
			@WebParam(name="transid") String transid)
	{
		return receiveresult.resultRequest(username, password, serviceid, msisdn, chargetime, params, mode, amount, 
				 detail,
				 Chargecode,
				 nextRenewalTime,
				 transid);
	}

}