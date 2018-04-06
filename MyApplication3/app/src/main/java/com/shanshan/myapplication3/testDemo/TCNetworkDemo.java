package com.shanshan.myapplication3.testDemo;

import com.shanshan.myapplication3.TCNetworkManage;
import com.shanshan.myapplication3.TCNetworkManageInterface;

import static com.shanshan.myapplication3.testDemo.TCNetworkConstants.TCAPIService;
import static com.shanshan.myapplication3.testDemo.TCNetworkConstants.TCServicePath;
import static com.shanshan.myapplication3.testDemo.TCNetworkConstants.TCTrackServiceV1;

/**
 * Created by shanshan on 2018/3/28.
 */

public class TCNetworkDemo extends TCNetworkManage implements TCNetworkManageInterface {

    @Override
    public String serviceIdentifier() {

        return TCTrackServiceV1;
    }

    @Override
    public String apiClassPath() {

        return TCServicePath + TCAPIService;
    }

    @Override
    public String apiMethodName() {

        return "getResult1";
    }
}
