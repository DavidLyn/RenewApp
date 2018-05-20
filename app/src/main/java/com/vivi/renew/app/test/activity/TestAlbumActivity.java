package com.vivi.renew.app.test.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import com.vivi.renew.app.base.CommonResult;
import com.vivi.renew.app.test.http.IRetrofitTest;
import com.vivi.renew.app.test.model.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    @BindView(R.id.test_generic)
    Button genericBtn;

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
                Logger.d("Eception",t);
            }
        });

    }

    @OnClick(R.id.test_generic)
    public void onClickedOnTestGeneric(View view) {
        Logger.d("i'm clicked!");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.105:8080/renew/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRetrofitTest request = retrofit.create(IRetrofitTest.class);

        Call<CommonResult<String>> call = request.testGenericResult();

        call.enqueue(new Callback<CommonResult<String>>() {
            @Override
            public void onResponse(Call<CommonResult<String>> call, Response<CommonResult<String>> response) {
                Toast.makeText(TestAlbumActivity.this,response.body().getData(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CommonResult<String>> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.choose_from_album)
    public void onClicked(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        } else {
            openAlbum();
        }

        //this.finish();
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1 :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO :
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default :
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath =null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri,null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }

        displayImage(imagePath);

    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();

        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);

    }

    private String getImagePath(Uri uri,String selection) {
        String path = null;

        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        Logger.d("imagePath="+imagePath);
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);

            // 使用retrofit上传图片
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.105:8080/renew/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IRetrofitTest request = retrofit.create(IRetrofitTest.class);

            File file = new File(imagePath);

            //------------------------------------------------------------
            // one file upload
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // 注意下面这个"file"要与spring boot controller中的upload(@RequestParam("file")对应起来
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            String descriptionString = "This is a description";

            RequestBody description =
                    RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

            Call<ResponseBody> call = request.upload(description,body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    Logger.d("file upload success:"+ response.body().toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            //------------------------------------------------------------
            // multi files upload
            List<MultipartBody.Part> parts = new ArrayList<>();
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);

            RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
            parts.add(part1);

            Call<ResponseBody> calls = request.uploads(parts);
            calls.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    Logger.d("files upload success:"+ response.body().toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });


        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
