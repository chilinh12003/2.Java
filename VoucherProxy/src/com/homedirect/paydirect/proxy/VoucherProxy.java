/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.homedirect.paydirect.proxy;

import com.homedirect.paydirect.util.VoucherServiceUtil;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import vn.paydirect.voucherservice.VoucherService;
import vn.paydirect.voucherservice.VoucherService_Service;

/**
 *
 * @author dodongduc
 */
public class VoucherProxy {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("WSConfig");
    private static String partnerCode, password, secretKey, endpointURL, namespaceURI, localPart;
    private static VoucherService service;

    static {

        endpointURL = bundle.getString("voucher_wsdl");
        partnerCode = bundle.getString("partner_code");
        password = bundle.getString("password");
        secretKey = bundle.getString("secretKey");
        namespaceURI = bundle.getString("namespace_uri");
        localPart = bundle.getString("localpart");
    }

    public static VoucherService getVoucherService() throws Exception {
        if (service == null) {
            service = new VoucherService_Service(new URL(endpointURL), new QName(namespaceURI, localPart)).getVoucherServicePort();
        }
        return service;
    }

    public static String useCard(String issuer, String cardSerial, String cardCode, String transRef)
            throws Exception {
        String signature = VoucherServiceUtil.hashData(issuer + cardCode + transRef + partnerCode + password + secretKey);
        return getVoucherService().useCard(issuer, cardSerial, cardCode, transRef, partnerCode, password, signature);
    }

    public static String getTransactionDetail(String transRef) throws Exception {
        String signature = VoucherServiceUtil.hashData(transRef + partnerCode + password + secretKey);
        return getVoucherService().getTransactionDetail(transRef, partnerCode, password, signature);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("--------------partnerCode:" + partnerCode);
        System.out.println("--------------password:" + password);
        System.out.println("--------------secretKey:" + secretKey);
        System.out.println("=================date 1:" + Calendar.getInstance().getTime());
        System.out.println(useCard("MOBI", "030251001159340", "937602972251", String.valueOf(System.currentTimeMillis())));
        System.out.println("=================date 2:" + Calendar.getInstance().getTime());
    }
}
