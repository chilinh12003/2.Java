package my.ws;

import javax.jws.WebParam;

@javax.jws.WebService(targetNamespace = "http://mtws/xsd", serviceName = "molistenerService", portName = "moRequest", wsdlLocation = "WEB-INF/wsdl/molistenerService.wsdl")
public class molistenerDelegate
{

	my.ws.molistener molistener = new my.ws.molistener();

	public String moRequest(@WebParam(name = "username") String username, @WebParam(name = "password") String password,
			@WebParam(name = "source") String source, @WebParam(name = "dest") String dest,
			@WebParam(name = "content") String content)
	{
		return molistener.moRequest(username, password, source, dest, content);
	}

}