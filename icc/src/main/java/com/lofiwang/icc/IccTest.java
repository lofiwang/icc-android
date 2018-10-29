package com.lofiwang.icc;

import android.app.Notification;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by chunsheng.wang on 2018/10/29.
 */

public class IccTest extends IccLifecycle {
    private static final String TAG = "MainActivity";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage1 " + msg.toString());
            Log.d(TAG, "handleMessage2 " + msg.getData().toString());
            ParcelableTest parcelableTest = (ParcelableTest) ParcelUtil.readValue((byte[]) msg.getData().get("test"), ParcelableTest.class);
            Log.d(TAG, "handleMessage3 " + parcelableTest.toString());
            Notification notification = (Notification) msg.getData().get("test1");
            Log.d(TAG, "handleMessage4 " + notification.toString());
        }
    };

    @Override
    protected void onCreate() {
        IccServer.peekInstance().setHandler(handler);
    }

    @Override
    protected void onDestroy() {
        IccServer.peekInstance().removeHandler();
    }
}