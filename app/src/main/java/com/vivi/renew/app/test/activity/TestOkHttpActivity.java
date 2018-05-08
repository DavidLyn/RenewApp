package com.vivi.renew.app.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vivi.renew.app.R;
import com.vivi.renew.app.base.CommonResult;
import com.vivi.renew.app.test.Test;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;

import okhttp3.Call;

public class TestOkHttpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button buttonRequest;
    private Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_okhttp);

        textView = (TextView)findViewById(R.id.result_textview);

        buttonRequest = (Button)findViewById(R.id.test_okhttp_request);
        buttonRequest.setOnClickListener(this);

        buttonReturn = (Button)findViewById(R.id.test_okhttp_return);
        buttonReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_okhttp_return: {
                this.finish();
                break;
            }
            case R.id.test_okhttp_request: {
                request();
                break;
            }
        }
    }

    private void request() {
        // 注意：下面不能用localhost或127.0.0.1，不然模拟器将其视为自身了
//        String url = "http://192.168.1.105:8080/renew/test";
        String url = "http://192.168.1.105:8080/renew/testObject";

        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("EEEEEEEEEEEEEEEEEError:",e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                Type jsonType = new TypeToken<CommonResult<Test>>(){}.getType();

                CommonResult<Test> result = new Gson().fromJson(response,jsonType);

                textView.setText(result.getData().getUserName());
                Log.d("tag","" + result.getData().getBooks().size());
            }
        });
    }

}
