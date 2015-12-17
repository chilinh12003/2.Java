
package my.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "resultRequest", namespace = "http://resultws/xsd")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultRequest", namespace = "http://resultws/xsd", propOrder = {
    "username",
    "password",
    "serviceid",
    "msisdn",
    "chargetime",
    "params",
    "mode",
    "amount"
})
public class ResultRequest {

    @XmlElement(name = "username", namespace = "")
    private String username;
    @XmlElement(name = "password", namespace = "")
    private String password;
    @XmlElement(name = "serviceid", namespace = "")
    private String serviceid;
    @XmlElement(name = "msisdn", namespace = "")
    private String msisdn;
    @XmlElement(name = "chargetime", namespace = "")
    private String chargetime;
    @XmlElement(name = "params", namespace = "")
    private String params;
    @XmlElement(name = "mode", namespace = "")
    private String mode;
    @XmlElement(name = "amount", namespace = "")
    private int amount;

    /**
     * 
     * @return
     *     returns String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 
     * @param username
     *     the value for the username property
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 
     * @param password
     *     the value for the password property
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getServiceid() {
        return this.serviceid;
    }

    /**
     * 
     * @param serviceid
     *     the value for the serviceid property
     */
    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getMsisdn() {
        return this.msisdn;
    }

    /**
     * 
     * @param msisdn
     *     the value for the msisdn property
     */
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getChargetime() {
        return this.chargetime;
    }

    /**
     * 
     * @param chargetime
     *     the value for the chargetime property
     */
    public void setChargetime(String chargetime) {
        this.chargetime = chargetime;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getParams() {
        return this.params;
    }

    /**
     * 
     * @param params
     *     the value for the params property
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getMode() {
        return this.mode;
    }

    /**
     * 
     * @param mode
     *     the value for the mode property
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * 
     * @return
     *     returns int
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * 
     * @param amount
     *     the value for the amount property
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

}
