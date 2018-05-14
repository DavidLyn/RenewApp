package com.vivi.renew.app.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vivi.renew.app.R;
import com.vivi.renew.app.activity.MainActivity;
import com.vivi.renew.app.utils.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

public class TestUploadActivity extends AppCompatActivity implements View.OnClickListener {

    private Button b_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_upload);

        b_upload = (Button)findViewById(R.id.button_upload);
        b_upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 下面被注释的写法现在运行时会报错
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");

        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor.getString(actual_image_column_index);
            File file = new File(img_path);
//            Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "file is opened!", Toast.LENGTH_SHORT).show();

            String url = "http://192.168.1.105:8080/renew/testUpload";

            OkHttpUtils
                    .postFile()
                    .url(url)
                    .file(file)
                    .build()
                    .execute(new StringCallback(){
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            LogUtil.d("error",e.getMessage());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            LogUtil.d("tag","response = " + response);

                        }
                    });

        }
    }
}
