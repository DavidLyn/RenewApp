package com.vivi.renew.app.test.http;

import com.vivi.renew.app.test.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lvweiwei on 18/5/16.
 */

public interface IRetrofitTest {
    @GET("retrofitTest")
    Call<Result> getResult();
}
