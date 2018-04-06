package com.shanshan.myapplication3.testDemo;

import com.shanshan.myapplication3.TCNetworkService;
import com.shanshan.myapplication3.TCNetworkServiceInterface;

import java.util.Map;

/**
 * Created by shanshan on 2018/4/2.
 */

public class TCServiceDemo2 extends TCNetworkService implements TCNetworkServiceInterface {

    @Override
    public String offlineRequestBaseUrl() {

        return "http://test.test.com:8088/";
    }

    @Override
    public String onlineRequestBaseUrl() {

        return "http://test.test.com:8088/";
    }

    @Override
    public String offlineRequestVersion() {

        return null;
    }

    @Override
    public String onlineRequestVersion() {

        return null;
    }

    @Override
    public String baseUrl() {

        if (requestVersion() != null && requestVersion().length() > 0) {

            return requestBaseUrl() + requestVersion();
        } else {

            return requestBaseUrl();
        }
    }

    @Override
    public Map mapWithExtraParmas(Map param) {

        return param;
    }

    @Override
    public boolean requestFailedCommonHandle(Map data) {

        return false;
    }
}
