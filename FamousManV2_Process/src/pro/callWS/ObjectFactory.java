package pro.callWS;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the pro.callWS package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory
{

	private final static QName _SmsRequestPassword_QNAME = new QName("http://smsws/xsd", "password");
	private final static QName _SmsRequestShortcode_QNAME = new QName("http://smsws/xsd", "shortcode");
	private final static QName _SmsRequestParams_QNAME = new QName("http://smsws/xsd", "params");
	private final static QName _SmsRequestAlias_QNAME = new QName("http://smsws/xsd", "alias");
	private final static QName _SmsRequestMsisdn_QNAME = new QName("http://smsws/xsd", "msisdn");
	private final static QName _SmsRequestContent_QNAME = new QName("http://smsws/xsd", "content");
	private final static QName _SmsRequestUsername_QNAME = new QName("http://smsws/xsd", "username");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: pro.callWS
	 * 
	 */
	public ObjectFactory()
	{
	}

	/**
	 * Create an instance of {@link SmsRequestResponse }
	 * 
	 */
	public SmsRequestResponse createSmsRequestResponse()
	{
		return new SmsRequestResponse();
	}

	/**
	 * Create an instance of {@link SmsRequest }
	 * 
	 */
	public SmsRequest createSmsRequest()
	{
		return new SmsRequest();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://smsws/xsd", name = "password", scope = SmsRequest.class)
	public JAXBElement<String> createSmsRequestPassword(String value)
	{
		return new JAXBElement<String>(_SmsRequestPassword_QNAME, String.class, SmsRequest.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://smsws/xsd", name = "shortcode", scope = SmsRequest.class)
	public JAXBElement<String> createSmsRequestShortcode(String value)
	{
		return new JAXBElement<String>(_SmsRequestShortcode_QNAME, String.class, SmsRequest.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://smsws/xsd", name = "params", scope = SmsRequest.class)
	public JAXBElement<String> createSmsRequestParams(String value)
	{
		return new JAXBElement<String>(_SmsRequestParams_QNAME, String.class, SmsRequest.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://smsws/xsd", name = "alias", scope = SmsRequest.class)
	public JAXBElement<String> createSmsRequestAlias(String value)
	{
		return new JAXBElement<String>(_SmsRequestAlias_QNAME, String.class, SmsRequest.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://smsws/xsd", name = "msisdn", scope = SmsRequest.class)
	public JAXBElement<String> createSmsRequestMsisdn(String value)
	{
		return new JAXBElement<String>(_SmsRequestMsisdn_QNAME, String.class, SmsRequest.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://smsws/xsd", name = "content", scope = SmsRequest.class)
	public JAXBElement<String> createSmsRequestContent(String value)
	{
		return new JAXBElement<String>(_SmsRequestContent_QNAME, String.class, SmsRequest.class, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://smsws/xsd", name = "username", scope = SmsRequest.class)
	public JAXBElement<String> createSmsRequestUsername(String value)
	{
		return new JAXBElement<String>(_SmsRequestUsername_QNAME, String.class, SmsRequest.class, value);
	}

}
