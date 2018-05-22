package com.vivi.renew.app.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vivi.renew.app.R;
import com.vivi.renew.app.test.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusSecondActivity extends AppCompatActivity {

    @BindView(R.id.btn_send_message)
    Button sendMessageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_second);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_send_message)
    public void onClickSendMessageBtn(View view) {
        EventBus.getDefault().post(new MessageEvent("Hello !....."));
        this.finish();
    }
}
