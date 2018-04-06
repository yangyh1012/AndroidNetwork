package com.shanshan.myapplication3.testDemo;

import com.shanshan.myapplication3.TCNetworkManage;
import com.shanshan.myapplication3.TCNetworkManageInterface;

import static com.shanshan.myapplication3.testDemo.TCNetworkConstants.TCAPIService;
import static com.shanshan.myapplication3.testDemo.TCNetworkConstants.TCServicePath;
import static com.shanshan.myapplication3.testDemo.TCNetworkConstants.TCTrackServiceV2;

/**
 * Created by shanshan on 2018/4/2.
 */

public class TCNetworkDemo2 extends TCNetworkManage implements TCNetworkManageInterface {

    public static final String TCTestRequestParam = "requestParam";

    public static final String TCTestBackParam = "backParam";

    @Override
    public String serviceIdentifier() {

        return TCTrackServiceV2;
    }

    @Override
    public String apiClassPath() {

        return TCServicePath + TCAPIService;
    }

    @Override
    public String apiMethodName() {

        return "getResult2";
    }
}
