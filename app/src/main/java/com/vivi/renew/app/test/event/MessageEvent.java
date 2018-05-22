package com.vivi.renew.app.test.event;

/**
 * Created by lvweiwei on 18/5/22.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
