package com.shanshan.myapplication3.testDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shanshan.myapplication3.R;
import com.shanshan.myapplication3.TCNetworkManage;
import com.shanshan.myapplication3.TCNetworkManageRequestInterface;
import com.shanshan.myapplication3.TCNetworkServiceFactory;
import com.shanshan.myapplication3.TCNetworkServiceFactoryInterface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/4/4.
 */

public class OtherActivity extends AppCompatActivity implements TCNetworkManageRequestInterface {

    public TCNetworkDemo manage1;
    public TCNetworkDemo2 manage2;

    @BindView(R.id.button3)
    Button applyReleaseBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_other);

        dataInit();

        applyReleaseBtn = findViewById(R.id.button3);
        applyReleaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        manage1.networkManageRequestInterface = null;
//        manage2.networkManageRequestInterface = null;
//        manage1.cancelAllRequest();
//        manage2.cancelAllRequest();

        Log.d("activity销毁"," \n" + "activity销毁");
    }

    private void dataInit() {

        manage1 = new TCNetworkDemo();
        manage1.networkManageRequestInterface = this;
        double k = manage1.loadData();
        manage1.cancelRequest(k);



        manage2 = new TCNetworkDemo2();
        manage2.networkManageRequestInterface = this;
        manage2.loadData();
    }

    @Override
    public Map paramsForRequest(TCNetworkManage manage) {

        if (manage1 == manage) {

            Map map = new HashMap();
            map.put("tttt","123");
            return map;
        } else if (manage2 == manage) {

            Map map = new HashMap();
            return map;
        }

        return null;
    }

    @Override
    public void requestDidSuccess(TCNetworkManage manage) {


    }

    @Override
    public void requestDidFailed(TCNetworkManage manage) {

    }
}
