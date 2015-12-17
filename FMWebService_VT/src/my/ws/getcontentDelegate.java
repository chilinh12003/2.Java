package my.ws;


import javax.jws.WebParam;

@javax.jws.WebService(targetNamespace = "http://contentws/xsd", serviceName = "getcontentService", portName = "contentRequest", wsdlLocation = "WEB-INF/wsdl/getcontentService.wsdl")
public class getcontentDelegate
{

	my.ws.getcontent getcontent = new my.ws.getcontent();

	public String contentRequest(@WebParam(name="username")String username, @WebParam(name="password")String password, 
			@WebParam(name="serviceid")String serviceid, @WebParam(name="msisdn")String msisdn, 
			@WebParam(name="params")String params, @WebParam(name="mode")String mode, @WebParam(name="amount") int  amount)
	{
		return getcontent.contentRequest(username, password, serviceid, msisdn, params, mode, amount);
	}

}