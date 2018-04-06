package com.shanshan.myapplication3;

/**
 * Created by shanshan on 2018/3/24.
 */

public class TCNetworkConfig {

    public int timeoutSeconds;
    public boolean isOnline;

    //单例模式
    private volatile static TCNetworkConfig instance = null;

    private TCNetworkConfig() {

        this.timeoutSeconds = 40;
        this.isOnline = true;
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
