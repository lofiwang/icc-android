package com.lofiwang.icc;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

public class IccServer extends IccLifecycle {
    private static final String TAG = "IccServer";
    private static IccServer sInstance;
    private static Handler sPkgHandler;
    private static WeakReference<Handler> sHandlerWeakReference;

    private Context base;
    private Handler handler;
    private Context pkgContext;


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
        sHandlerWeakReference = new WeakReference<>(handler);
    }

    public void removeHandler() {
        sHandlerWeakReference = null;
    }

    @Override
    protected void onCreate() {
        Log.d(TAG, "onCreate");
        sPkgHandler = new PkgHandler();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        base = null;
        handler = null;
        pkgContext = null;
        sPkgHandler = null;
    }

    private final Handler getPkgHandler() {
        return sPkgHandler;
    }

    public static class PkgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (sHandlerWeakReference != null && sHandlerWeakReference.get() != null) {
                sHandlerWeakReference.get().sendMessage(Message.obtain(msg));
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
