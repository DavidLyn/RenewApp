package com.vivi.renew.app.test.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.vivi.renew.app.R;
import com.vivi.renew.app.test.http.IRetrofitTest;
import com.vivi.renew.app.test.model.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestAlbumActivity extends AppCompatActivity {

    public static final int CHOOSE_PHOTO =2;

    @BindView(R.id.test_retrofit)
    Button testRetrofit;

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

    @OnClick(R.id.test_retrofit)
    public void onClickedOnTestRetrofit(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.105:8080/renew/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRetrofitTest request = retrofit.create(IRetrofitTest.class);

        Call<Result> call = request.getResult();

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Logger.d("data = " + response.body().getData());
                Toast.makeText(TestAlbumActivity.this, response.body().getData(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.choose_from_album)
    public void onClicked(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        } else {
            //openAlbum();
        }

        //this.finish();
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }
}
