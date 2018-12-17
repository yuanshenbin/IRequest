package com.ysb.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.ysb.R;
import com.yuanshenbin.network.AbstractResponse;
import com.yuanshenbin.network.request.IRequest;
import com.yuanshenbin.network.request.RequestManager;


/**
 * author : yuanshenbin
 * time   : 2018/12/15
 * desc   :
 */

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);


        IRequest.get(this, "https://www.sojson.com/open/api/weather/json.shtml?city=北京")
                .execute(new AbstractResponse<TestBean>() {
                    @Override
                    public void onSuccess(TestBean result) {

                    }
                });

    }

    @Override
    protected void onDestroy() {
        RequestManager.getInstance().clearRequest(this);
        super.onDestroy();

    }

}
