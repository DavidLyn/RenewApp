package com.vivi.renew.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.vivi.renew.app.R;
import com.vivi.renew.app.base.BaseActivity;
import com.vivi.renew.app.test.activity.EventBusFirstActivity;
import com.vivi.renew.app.test.activity.TestAlbumActivity;
import com.vivi.renew.app.test.activity.TestGlideActivity;
import com.vivi.renew.app.test.activity.TestOkHttpActivity;
import com.vivi.renew.app.test.activity.TestUploadActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button testEventBusBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // okHttp test
        button1 = (Button)findViewById(R.id.button_1);
        button1.setOnClickListener(this);

        // glide test
        button2 = (Button)findViewById(R.id.button_2);
        button2.setOnClickListener(this);

        // upload test
        button3 = (Button)findViewById(R.id.button_3);
        button3.setOnClickListener(this);

        // album test
        button4 = (Button)findViewById(R.id.button_4);
        button4.setOnClickListener(this);

        // eventbus test
        testEventBusBtn = (Button)findViewById(R.id.btn_test_eventbus);
        testEventBusBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_1: {
                Intent intent = new Intent(this, TestOkHttpActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.button_2: {
                Intent intent = new Intent(this, TestGlideActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.button_3: {
                Intent intent = new Intent(this, TestUploadActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.button_4: {
                Intent intent = new Intent(this, TestAlbumActivity.class);
                this.startActivity(intent);
                break;
            }
            case R.id.btn_test_eventbus: {
                Intent intent = new Intent(this, EventBusFirstActivity.class);
                this.startActivity(intent);
                break;
            }

        }
    }
}

