package com.shanshan.myapplication3;

import java.util.Map;

/**
 * Created by shanshan on 2018/3/24.
 */

public interface TCNetworkServiceInterface {

    public String offlineRequestBaseUrl();
    public String onlineRequestBaseUrl();

    public String offlineRequestVersion();
    public String onlineRequestVersion();

    public String baseUrl();

    public Map mapWithExtraParmas(Map param);

    public boolean requestFailedCommonHandle(Map data);
}
