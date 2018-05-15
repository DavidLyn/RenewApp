package com.vivi.renew.app.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vivi.renew.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestAlbumActivity extends AppCompatActivity {

    @BindView(R.id.choose_from_album)
    Button chooseButton;

    @BindView(R.id.picture)
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_album);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.choose_from_album)
    public void onClicked(View view) {
        this.finish();
    }
}
