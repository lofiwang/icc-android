package com.lofiwang.icc;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class IccServer implements IccLifecycle {
    private static final String TAG = "IccServer";
    private Context base;
    private Handler handler;
    private Context pkgContext;
    private Handler pkgHandler;

    public IccServer(Context base, Context pkgContext, Handler handler) {
        this.base = base;
        this.handler = handler;
        this.pkgContext = pkgContext;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        pkgHandler = new PkgHandler();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        base = null;
        handler = null;
        pkgContext = null;
        pkgHandler = null;
    }

    public final Handler getPkgHandler() {
        return pkgHandler;
    }

    public static class PkgHandler extends Handler {
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
    }
}
