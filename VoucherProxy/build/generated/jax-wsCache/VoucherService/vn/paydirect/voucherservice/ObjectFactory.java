
package vn.paydirect.voucherservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the vn.paydirect.voucherservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UseCard_QNAME = new QName("http://paydirect.vn/voucherservice", "useCard");
    private final static QName _GetTransactionDetail_QNAME = new QName("http://paydirect.vn/voucherservice", "getTransactionDetail");
    private final static QName _GetTransactionDetailResponse_QNAME = new QName("http://paydirect.vn/voucherservice", "getTransactionDetailResponse");
    private final static QName _UseCardResponse_QNAME = new QName("http://paydirect.vn/voucherservice", "useCardResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: vn.paydirect.voucherservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTransactionDetail }
     * 
     */
    public GetTransactionDetail createGetTransactionDetail() {
        return new GetTransactionDetail();
    }

    /**
     * Create an instance of {@link UseCard }
     * 
     */
    public UseCard createUseCard() {
        return new UseCard();
    }

    /**
     * Create an instance of {@link UseCardResponse }
     * 
     */
    public UseCardResponse createUseCardResponse() {
        return new UseCardResponse();
    }

    /**
     * Create an instance of {@link GetTransactionDetailResponse }
     * 
     */
    public GetTransactionDetailResponse createGetTransactionDetailResponse() {
        return new GetTransactionDetailResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UseCard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://paydirect.vn/voucherservice", name = "useCard")
    public JAXBElement<UseCard> createUseCard(UseCard value) {
        return new JAXBElement<UseCard>(_UseCard_QNAME, UseCard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransactionDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://paydirect.vn/voucherservice", name = "getTransactionDetail")
    public JAXBElement<GetTransactionDetail> createGetTransactionDetail(GetTransactionDetail value) {
        return new JAXBElement<GetTransactionDetail>(_GetTransactionDetail_QNAME, GetTransactionDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTransactionDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://paydirect.vn/voucherservice", name = "getTransactionDetailResponse")
    public JAXBElement<GetTransactionDetailResponse> createGetTransactionDetailResponse(GetTransactionDetailResponse value) {
        return new JAXBElement<GetTransactionDetailResponse>(_GetTransactionDetailResponse_QNAME, GetTransactionDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UseCardResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://paydirect.vn/voucherservice", name = "useCardResponse")
    public JAXBElement<UseCardResponse> createUseCardResponse(UseCardResponse value) {
        return new JAXBElement<UseCardResponse>(_UseCardResponse_QNAME, UseCardResponse.class, null, value);
    }

}
