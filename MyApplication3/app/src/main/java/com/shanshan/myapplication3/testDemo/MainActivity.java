package com.shanshan.myapplication3.testDemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shanshan.myapplication3.R;
import com.shanshan.myapplication3.TCNetworkConfig;
import com.shanshan.myapplication3.TCNetworkManage;
import com.shanshan.myapplication3.TCNetworkManageRequestInterface;
import com.shanshan.myapplication3.TCNetworkServiceFactory;
import com.shanshan.myapplication3.TCNetworkServiceFactoryInterface;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.shanshan.myapplication3.testDemo.TCNetworkConstants.TCServicePath;
import static com.shanshan.myapplication3.testDemo.TCNetworkConstants.TCTrackServiceV1;
import static com.shanshan.myapplication3.testDemo.TCNetworkConstants.TCTrackServiceV2;

public class MainActivity extends AppCompatActivity implements TCNetworkManageRequestInterface,TCNetworkServiceFactoryInterface {

    public TCNetworkDemo manage1;
    public TCNetworkDemo2 manage2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        TCNetworkServiceFactory.getInstance().dataSource = this;
        TCNetworkConfig.getInstance().isOnline = false;

        initViews();
    }

    @BindView(R.id.button2)
    Button applyReleaseBtn;

    protected void initViews() {

        applyReleaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forwardAcitivty(OtherActivity.class);
            }
        });
    }

    protected void forwardAcitivty(Class clazz) {

        Intent intent = new Intent(this, clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void dataInit() {

        manage1 = new TCNetworkDemo();
        manage1.networkManageRequestInterface = this;
        double k = manage1.loadData();
//        manage1.cancelRequest(k);

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

        if (manage1 == manage) {

//            Log.d("33333333", Arrays.toString(manage.responseMap.entrySet().toArray()));
        } else if (manage2 == manage) {


        }
    }

    @Override
    public void requestDidFailed(TCNetworkManage manage) {

        if (manage1 == manage) {

//            Log.d("44444444", Arrays.toString(manage.responseMap.entrySet().toArray()));
        } else if (manage2 == manage) {


        }
    }

    @Override
    public Map serviceInfos() {

        Map serviceInfo = new HashMap();
        serviceInfo.put(TCTrackServiceV1, TCServicePath + TCTrackServiceV1);
        serviceInfo.put(TCTrackServiceV2, TCServicePath + TCTrackServiceV2);
        return serviceInfo;
    }
}
