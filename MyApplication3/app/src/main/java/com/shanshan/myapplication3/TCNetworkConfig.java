package com.shanshan.myapplication3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by shanshan on 2018/3/24.
 */

public class TCNetworkConfig {

    public boolean isOnline;

    public OkHttpClient client;
    public Map retrofitMap;

    //单例模式
    private volatile static TCNetworkConfig instance = null;

    private TCNetworkConfig() {

        this.isOnline = true;

        client = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS) //设置连接超时
                .build();

        retrofitMap = new HashMap();
    }

    public static TCNetworkConfig getInstance() {
        if (instance == null) {
            synchronized (TCNetworkConfig.class) {
                if (instance == null) {
                    instance = new TCNetworkConfig();
                }
            }
        }
        return instance;
    }
}
