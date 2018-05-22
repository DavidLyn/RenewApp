package com.vivi.renew.app.test.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.vivi.renew.app.R;
import com.vivi.renew.app.test.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusFirstActivity extends AppCompatActivity {

    @BindView(R.id.tv_text)
    TextView textTv;

    @BindView(R.id.btn_next)
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_first);

        ButterKnife.bind(this);

        // register event bus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_next)
    public void onClickNextBtn(View view) {
        Intent intent = new Intent(this, EventBusSecondActivity.class);
        this.startActivity(intent);

    }

    //订阅方法，当接收到事件的时候，会调用该方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent){
        Logger.d("i get it!");
        textTv.setText(messageEvent.getMessage());
        Toast.makeText(this, messageEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
