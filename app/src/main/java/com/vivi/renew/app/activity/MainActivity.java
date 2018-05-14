package com.vivi.renew.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vivi.renew.app.R;
import com.vivi.renew.app.base.BaseActivity;
import com.vivi.renew.app.test.activity.TestGlideActivity;
import com.vivi.renew.app.test.activity.TestOkHttpActivity;
import com.vivi.renew.app.test.activity.TestUploadActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private Button button1;
    private Button button2;
    private Button button3;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_1: {
                Intent intent = new Intent(this, TestOkHttpActivity.class);
                this.startActivity(intent);
            }
            case R.id.button_2: {
                Intent intent = new Intent(this, TestGlideActivity.class);
                this.startActivity(intent);
            }
            case R.id.button_3: {
                Intent intent = new Intent(this, TestUploadActivity.class);
                this.startActivity(intent);
            }

        }
    }
}

