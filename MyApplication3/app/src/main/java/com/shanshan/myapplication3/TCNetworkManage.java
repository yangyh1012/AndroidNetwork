package com.shanshan.myapplication3;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.shanshan.myapplication3.TCNetworkString.TCRespCancelCheckValue;
import static com.shanshan.myapplication3.TCNetworkString.TCRespCancelString;
import static com.shanshan.myapplication3.TCNetworkString.TCRespLocationString;
import static com.shanshan.myapplication3.TCNetworkString.TCRespMsg;
import static com.shanshan.myapplication3.TCNetworkString.TCRespNetErrorString;
import static com.shanshan.myapplication3.TCNetworkString.TCRespStatus;
import static com.shanshan.myapplication3.TCNetworkString.TCRespStatusCancelValue;
import static com.shanshan.myapplication3.TCNetworkString.TCRespStatusNetErrorValue;
import static com.shanshan.myapplication3.TCNetworkString.TCRespStatusNormalValue;

/**
 * Created by shanshan on 2018/3/24.
 */

public class TCNetworkManage {

    public TCNetworkManageInterface networkManageInterface;
    public TCNetworkManageRequestInterface networkManageRequestInterface;

    private Map requestIdMap;
    private SimpleDateFormat detailTimeFormat = new SimpleDateFormat("dHHmmssSSS");

    public Map responseMap;

    private Handler mainHandler = new Handler(Looper.getMainLooper());

    protected TCNetworkManage() {

        requestIdMap = new HashMap();
        networkManageInterface = (TCNetworkManageInterface)this;
    }

    public double loadData() {

        final double requestId = getCurrentRequestId();

        Map params = networkManageRequestInterface.paramsForRequest(this);

        final TCNetworkServiceInterface serviceInterface = TCNetworkServiceFactory.getInstance().serviceWithIdentifier(networkManageInterface.serviceIdentifier());
        final TCNetworkService networkService = (TCNetworkService)serviceInterface;
        networkService.dataSource = (TCNetworkServiceInterface)networkService;

        String url = serviceInterface.baseUrl();

        Map mapParamValue = serviceInterface.mapWithExtraParmas(params);
        Log.d("请求详细信息"," \n"
                + "请求Id：" + requestId + "\n"
                + "请求URI：" + url + "\n"
                + "参数：" + Arrays.toString(params.entrySet().toArray()) + "\n"
                + "调用服务：" + networkManageInterface.serviceIdentifier() + "\n"
                + "调用API：" + networkManageInterface.apiClassPath() + "\n"
                + "调用方法：" + networkManageInterface.apiMethodName() + "\n"
                + "封装后的参数：" + Arrays.toString(mapParamValue.entrySet().toArray()) + "\n");

        Retrofit retrofit = null;
        if (TCNetworkConfig.getInstance().retrofitMap.get(url) == null) {

            retrofit = new Retrofit.Builder()
                    .client(TCNetworkConfig.getInstance().client)
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();

            TCNetworkConfig.getInstance().retrofitMap.put(url, retrofit);
        } else {

            retrofit = (Retrofit)TCNetworkConfig.getInstance().retrofitMap.get(url);
        }

        Class interfaceService = null;
        Object service = null;
        Method method = null;
        Call<ResponseBody> call = null;
        try {

            interfaceService = Class.forName(networkManageInterface.apiClassPath());
            service = retrofit.create(interfaceService);
            method = service.getClass().getMethod(networkManageInterface.apiMethodName(), Map.class);
            call = (Call<ResponseBody>)method.invoke(service, mapParamValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        requestIdMap.put(requestId, call);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                removeRequestId(requestId);

                if (networkManageRequestInterface == null) {

                    return ;
                }

                try {

                    String jsonStr = new String(response.body().bytes());

                    String enToStr = jsonStr;

                    Gson gson = new Gson();
                    Type type = new TypeToken<Map>() {}.getType();
                    responseMap = gson.fromJson(enToStr, type);
                    Log.d("网络请求成功，返回的Map数据为"," \n" + Arrays.toString(responseMap.entrySet().toArray())
                            + "\n" + "请求Id：" + requestId);

                    //已在主线程中，可以更新UI
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            if (!serviceInterface.requestFailedCommonHandle(responseMap)) {

                                if (networkManageRequestInterface == null) {

                                    return ;
                                }

                                networkManageRequestInterface.requestDidSuccess(TCNetworkManage.this);
                            }
                        }
                    });

                } catch (IllegalArgumentException e) {

                    if (e.getMessage().equals("1111")) {

                        responseMap = new HashMap();
                        responseMap.put(TCRespStatus, TCRespStatusNormalValue);
                        responseMap.put(TCRespMsg, TCRespLocationString);
                        Log.d("网络请求成功，返回的Map数据为"," \n" + Arrays.toString(responseMap.entrySet().toArray())
                                + "\n" + "请求Id：" + requestId);

                        //已在主线程中，可以更新UI
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                if (networkManageRequestInterface == null) {

                                    return ;
                                }

                                networkManageRequestInterface.requestDidSuccess(TCNetworkManage.this);
                            }
                        });
                    } else {

                        e.printStackTrace();
                    }

                } catch (IOException e) {

                    //暂时不处理
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                removeRequestId(requestId);

                if (networkManageRequestInterface == null) {

                    return ;
                }

                responseMap = new HashMap();
                if (t.getMessage().equals(TCRespCancelCheckValue)) {

                    responseMap.put(TCRespStatus, TCRespStatusCancelValue);
                    responseMap.put(TCRespMsg, TCRespCancelString);

                    Log.d("网络请求失败，原因"," \n" + TCRespCancelString
                            + "\n" + "请求Id：" + requestId);
                } else {

                    responseMap.put(TCRespStatus, TCRespStatusNetErrorValue);
                    responseMap.put(TCRespMsg, TCRespNetErrorString);

                    Log.d("网络请求失败，原因"," \n" + TCRespNetErrorString
                            + "\n" + "请求Id：" + requestId);

                    t.printStackTrace();
                }

                //已在主线程中，可以更新UI
                mainHandler.post(new Runnable() {

                    @Override
                    public void run() {

                        if (networkManageRequestInterface == null) {

                            return ;
                        }

                        networkManageRequestInterface.requestDidFailed(TCNetworkManage.this);
                    }
                });
            }
        });

        return requestId;
    }

    public double getCurrentRequestId () {

        Date date = new Date();
        String dateStr = detailTimeFormat.format(date);
        String RequestIdStr = dateStr + TCNetworkString.strRandSetting(4);

        return Double.parseDouble(RequestIdStr);
    }

    public void removeRequestId (double requestId) {

        double requestIdRemoveId = 0;
        for (Object object: requestIdMap.keySet()) {

            double storeRequestId = ((Double)object).doubleValue();
            if (storeRequestId == requestId) {

                requestIdRemoveId = requestId;
            }
        }

        if (requestIdRemoveId != 0) {

            requestIdMap.remove(requestIdRemoveId);
        }
    }

    public void cancelRequest (double requestId) {

        if (requestIdMap.get(requestId) != null) {

            Call<ResponseBody> call = (Call<ResponseBody>)requestIdMap.get(requestId);
            call.cancel();
        }

        removeRequestId(requestId);
    }

    public void cancelAllRequest () {

        for (Object object: requestIdMap.keySet()) {

            double requestId = ((Double)object).doubleValue();
            cancelRequest(requestId);
        }
    }
}
