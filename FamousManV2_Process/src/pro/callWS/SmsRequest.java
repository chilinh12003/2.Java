package pro.callWS;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="msisdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shortcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="params" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"username", "password", "msisdn", "content", "shortcode", "alias", "params"})
@XmlRootElement(name = "smsRequest")
public class SmsRequest
{

	@XmlElementRef(name = "username", namespace = "http://smsws/xsd", type = JAXBElement.class)
	protected JAXBElement<String> username;
	@XmlElementRef(name = "password", namespace = "http://smsws/xsd", type = JAXBElement.class)
	protected JAXBElement<String> password;
	@XmlElementRef(name = "msisdn", namespace = "http://smsws/xsd", type = JAXBElement.class)
	protected JAXBElement<String> msisdn;
	@XmlElementRef(name = "content", namespace = "http://smsws/xsd", type = JAXBElement.class)
	protected JAXBElement<String> content;
	@XmlElementRef(name = "shortcode", namespace = "http://smsws/xsd", type = JAXBElement.class)
	protected JAXBElement<String> shortcode;
	@XmlElementRef(name = "alias", namespace = "http://smsws/xsd", type = JAXBElement.class)
	protected JAXBElement<String> alias;
	@XmlElementRef(name = "params", namespace = "http://smsws/xsd", type = JAXBElement.class)
	protected JAXBElement<String> params;

	/**
	 * Gets the value of the username property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getUsername()
	{
		return username;
	}

	/**
	 * Sets the value of the username property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setUsername(JAXBElement<String> value)
	{
		this.username = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the password property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getPassword()
	{
		return password;
	}

	/**
	 * Sets the value of the password property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setPassword(JAXBElement<String> value)
	{
		this.password = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the msisdn property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getMsisdn()
	{
		return msisdn;
	}

	/**
	 * Sets the value of the msisdn property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setMsisdn(JAXBElement<String> value)
	{
		this.msisdn = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the content property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getContent()
	{
		return content;
	}

	/**
	 * Sets the value of the content property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setContent(JAXBElement<String> value)
	{
		this.content = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the shortcode property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getShortcode()
	{
		return shortcode;
	}

	/**
	 * Sets the value of the shortcode property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setShortcode(JAXBElement<String> value)
	{
		this.shortcode = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the alias property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getAlias()
	{
		return alias;
	}

	/**
	 * Sets the value of the alias property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setAlias(JAXBElement<String> value)
	{
		this.alias = ((JAXBElement<String>) value);
	}

	/**
	 * Gets the value of the params property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link String }
	 *         {@code >}
	 * 
	 */
	public JAXBElement<String> getParams()
	{
		return params;
	}

	/**
	 * Sets the value of the params property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link String }
	 *            {@code >}
	 * 
	 */
	public void setParams(JAXBElement<String> value)
	{
		this.params = ((JAXBElement<String>) value);
	}

}
