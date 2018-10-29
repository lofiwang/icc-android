package com.lofiwang.icc;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class IccServer extends IccLifecycle {
    private static final String TAG = "IccServer";
    private static IccServer sInstance;
    private Handler usrHandler;
    private Context base;
    private Handler handler;
    private Context pkgContext;
    private Handler pkgHandler;

    private IccServer(Context base, Context pkgContext, Handler handler) {
        this.base = base;
        this.handler = handler;
        this.pkgContext = pkgContext;
        sInstance = this;
    }

    public static IccServer peekInstance() {
        return sInstance;
    }

    public void setHandler(Handler handler) {
        usrHandler = handler;
    }

    public void removeHandler() {
        usrHandler = null;
    }

    @Override
    protected void onCreate() {
        Log.d(TAG, "onCreate");
        pkgHandler = new PkgHandler();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        base = null;
        handler = null;
        pkgContext = null;
        pkgHandler = null;
    }

    public final Handler getPkgHandler() {
        return pkgHandler;
    }

    public class PkgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (usrHandler != null) {
                usrHandler.sendMessage(Message.obtain(msg));
            }
            Log.d(TAG, "handleMessage1 " + msg.toString());
            Log.d(TAG, "handleMessage2 " + msg.getData().toString());
            ParcelableTest parcelableTest = (ParcelableTest) ParcelUtil.readValue((byte[]) msg.getData().get("test"), ParcelableTest.class);
            Log.d(TAG, "handleMessage3 " + parcelableTest.toString());
            Notification notification = (Notification) msg.getData().get("test1");
            Log.d(TAG, "handleMessage4 " + notification.toString());
        }
    }
}
