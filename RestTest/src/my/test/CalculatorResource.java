package my.test;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Produces("application/xml")
@Path("claculator")
public class CalculatorResource {

	@GET
	public void getInfo() {
		throw new UnsupportedOperationException("Not yet implemented.");
	}
	
}