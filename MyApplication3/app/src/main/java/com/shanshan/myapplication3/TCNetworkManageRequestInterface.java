package com.shanshan.myapplication3;

import java.util.Map;

/**
 * Created by shanshan on 2018/3/30.
 */

public interface TCNetworkManageRequestInterface {

    public Map paramsForRequest(TCNetworkManage manage);

    public void requestDidSuccess(TCNetworkManage manage);

    public void requestDidFailed(TCNetworkManage manage);
}
