package com.goldsword.alao.soap.sync;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.goldsword.alao.soap.sync.server.GoldSwordService_ServiceLocator;

public class Main
{
    public static void main(String[] args) throws RemoteException, ServiceException
    {
        GoldSwordService_ServiceLocator locator = new GoldSwordService_ServiceLocator();
        locator.setGoldSwordServiceEndpointAddress("http://interface.alaogame.com/services/GoldSwordService");
        String response = locator.getGoldSwordService().syncCharge("", "", "", "", "", "", "", "", "");
        System.out.println(response);
    }
}
