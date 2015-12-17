
package my.ws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "moRequest", namespace = "http://mtws/xsd")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "moRequest", namespace = "http://mtws/xsd", propOrder = {
    "username",
    "password",
    "source",
    "dest",
    "content"
})
public class MoRequest {

    @XmlElement(name = "username", namespace = "")
    private String username;
    @XmlElement(name = "password", namespace = "")
    private String password;
    @XmlElement(name = "source", namespace = "")
    private String source;
    @XmlElement(name = "dest", namespace = "")
    private String dest;
    @XmlElement(name = "content", namespace = "")
    private String content;

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
    public String getSource() {
        return this.source;
    }

    /**
     * 
     * @param source
     *     the value for the source property
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getDest() {
        return this.dest;
    }

    /**
     * 
     * @param dest
     *     the value for the dest property
     */
    public void setDest(String dest) {
        this.dest = dest;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 
     * @param content
     *     the value for the content property
     */
    public void setContent(String content) {
        this.content = content;
    }

}
