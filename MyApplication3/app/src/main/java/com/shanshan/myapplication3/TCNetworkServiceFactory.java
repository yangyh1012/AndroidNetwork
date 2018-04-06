package com.shanshan.myapplication3;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shanshan on 2018/3/24.
 */

public class TCNetworkServiceFactory {

    public TCNetworkServiceFactoryInterface dataSource;

    private Map serviceStorage;

    //单例模式
    private volatile static TCNetworkServiceFactory instance = null;

    private TCNetworkServiceFactory() {

        serviceStorage = new HashMap();
    }

    public static TCNetworkServiceFactory getInstance() {
        if (instance == null) {
            synchronized (TCNetworkServiceFactory.class) {
                if (instance == null) {
                    instance = new TCNetworkServiceFactory();
                }
            }
        }
        return instance;
    }


    public TCNetworkServiceInterface serviceWithIdentifier(String identifier) {

        if (serviceStorage.get(identifier) == null) {

            serviceStorage.put(identifier, newServiceWithIdentifier(identifier));
        }

        return (TCNetworkServiceInterface)serviceStorage.get(identifier);
    }

    private TCNetworkServiceInterface newServiceWithIdentifier(String identifier) {

        if (dataSource.serviceInfos().get(identifier) != null) {

            Class<?> subClass = null;
            TCNetworkServiceInterface service = null;
            try {

                subClass = Class.forName((String)dataSource.serviceInfos().get(identifier));
                service = (TCNetworkServiceInterface)subClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return service;
        } else {

            return null;
        }
    }
}
