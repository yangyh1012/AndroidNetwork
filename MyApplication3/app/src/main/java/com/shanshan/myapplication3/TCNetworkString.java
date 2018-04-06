package com.shanshan.myapplication3;

import java.security.MessageDigest;

/**
 * Created by shanshan on 2018/3/24.
 */


public class TCNetworkString {

    public static final String TCRespMsg = "respMsg";
    public static final String TCRespStatus = "respStatus";
    public static final String TCRespResult = "respResult";

    public static final double TCRespStatusNormalValue = 0;
    public static final double TCRespStatusCancelValue = 1;
    public static final double TCRespStatusNetErrorValue = 2;

    public static final String TCRespCancelCheckValue = "";

    public static final String TCRespCancelString = "";
    public static final String TCRespNetErrorString = "";
    public static final String TCRespLocationString = "";

    public static String strRandSetting(int bit) {

        String strRand = "";
        for(int i = 0; i < bit; i++) {

            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }

        return strRand;
    }
}