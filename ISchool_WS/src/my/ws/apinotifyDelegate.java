package my.ws;

import javax.jws.WebParam;

@javax.jws.WebService(targetNamespace = "http://ws.payment.com/", serviceName = "apinotifyService", portName = "doNotify", wsdlLocation = "WEB-INF/wsdl/apinotifyService.wsdl")
public class apinotifyDelegate
{

	my.ws.apinotify apinotify = new my.ws.apinotify();

	public String doNotify(@WebParam(name = "username") String username, @WebParam(name = "password") String password,
			@WebParam(name = "cpRequestId") String cpRequestId, @WebParam(name = "mobile") String mobile,
			@WebParam(name = "price") int price, @WebParam(name = "responseCode") String responseCode,
			@WebParam(name = "cmd") String cmd)
	{
		return apinotify.doNotify(username, password, cpRequestId, mobile, price, responseCode, cmd);
	}

}