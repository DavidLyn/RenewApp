package com.vivi.renew.app.base;

/**
 * Created by lvweiwei on 18/5/8.
 */

public class CommonResult<T> {
    private boolean result = true;
    private String message = "ok";
    private T data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
