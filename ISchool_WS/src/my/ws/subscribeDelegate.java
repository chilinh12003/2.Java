package my.ws;

import javax.jws.WebParam;

@javax.jws.WebService(targetNamespace = "http://contentws/xsd", serviceName = "subscribeService", portName = "subRequest", wsdlLocation = "WEB-INF/wsdl/subscribeService.wsdl")
public class subscribeDelegate
{

	my.ws.subscribe subscribe = new my.ws.subscribe();

	public String subRequest(@WebParam(name="username")String username, @WebParam(name="password")String password, 
			@WebParam(name="serviceid")String serviceid, @WebParam(name="msisdn")String msisdn, 
			@WebParam(name="chargetime")String chargetime,@WebParam(name="params")String params, 
			@WebParam(name="mode")String mode, @WebParam(name="amount") int  amount, 
			@WebParam(name="command")String command)
	{
		return subscribe.subRequest(username, password, serviceid, msisdn, chargetime, params, mode, amount, command);
	}

}