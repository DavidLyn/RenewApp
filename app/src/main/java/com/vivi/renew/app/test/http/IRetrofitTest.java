package com.vivi.renew.app.test.http;

import com.vivi.renew.app.test.model.Result;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by lvweiwei on 18/5/16.
 */

public interface IRetrofitTest {
    @GET("retrofitTest")
    Call<Result> getResult();

    // 单文件上传
    @Multipart
    @POST("testUpload")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);


    // 多文件上传
    @Multipart
    @POST("testUploads")
    Call<ResponseBody> uploads(@Part() List<MultipartBody.Part> parts);

}
