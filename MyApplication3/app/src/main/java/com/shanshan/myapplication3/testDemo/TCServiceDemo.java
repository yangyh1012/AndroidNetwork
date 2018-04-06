package com.shanshan.myapplication3.testDemo;

import android.util.Base64;

import com.google.gson.Gson;
import com.shanshan.myapplication3.TCNetworkService;
import com.shanshan.myapplication3.TCNetworkServiceInterface;
import com.shanshan.myapplication3.TCNetworkString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.shanshan.myapplication3.TCNetworkString.TCRespStatus;
import static com.shanshan.myapplication3.testDemo.TCNetworkConstants.TCRespStatusTokenInvailidValue;

/**
 * Created by shanshan on 2018/3/28.
 */

public class TCServiceDemo extends TCNetworkService implements TCNetworkServiceInterface {

    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    @Override
    public String offlineRequestBaseUrl() {

        return "http://test.test.com:8088/app/";
    }

    @Override
    public String onlineRequestBaseUrl() {

        return "http://test.test.com:8088/app/";
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

        if ((Double)data.get(TCRespStatus) == TCRespStatusTokenInvailidValue) {

            //处理token失效

            return true;
        } else {

            return false;
        }
    }


}
