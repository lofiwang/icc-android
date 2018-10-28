package com.lofiwang.icc;

import android.app.Notification;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ContextManager {
    public static final Handler getHandler() {
        return new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("test", "handleMessage1 " + msg.toString());
                Log.d("test", "handleMessage2 " + msg.getData().toString());
                ParcelableTest parcelableTest = (ParcelableTest) ParcelUtil.readValue((byte[]) msg.getData().get("test"), ParcelableTest.class);
                Log.d("test", "handleMessage3 " + parcelableTest.toString());
                Notification notification = (Notification) msg.getData().get("test1");
                Log.d("test", "handleMessage4 " + notification.toString());

            }
        };
    }
}
