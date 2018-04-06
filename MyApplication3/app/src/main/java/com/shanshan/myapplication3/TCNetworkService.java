package com.shanshan.myapplication3;

/**
 * Created by shanshan on 2018/3/24.
 */

public class TCNetworkService {

    public TCNetworkServiceInterface dataSource;

    public String requestBaseUrl() {

        return TCNetworkConfig.getInstance().isOnline ? dataSource.onlineRequestBaseUrl() : dataSource.offlineRequestBaseUrl();
    }

    public String requestVersion() {

        return TCNetworkConfig.getInstance().isOnline ? dataSource.onlineRequestVersion() : dataSource.offlineRequestVersion();
    }
}
