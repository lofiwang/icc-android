package com.lofiwang.icc;

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
    private Handler baseHandler;
    private Context pkgContext;
    private Object iccAnnotated;

    private IccServer(Context base, Context pkgContext, Handler baseHandler) {
        this.base = base;
        this.baseHandler = baseHandler;
        this.pkgContext = pkgContext;
        sInstance = this;
    }

    public static IccServer peekInstance() {
        return sInstance;
    }

    public Context getBaseContext() {
        return base;
    }

    public Context getPkgContext() {
        return pkgContext;
    }

    public Handler getBaseHandler() {
        return baseHandler;
    }

    public void setReceiveHandler(Handler handler) {
        sHandlerWeakReference = new WeakReference<>(handler);
    }

    public void removeReceiveHandler() {
        sHandlerWeakReference = null;
    }

    @Override
    protected void onCreate() {
        Log.d(TAG, "onCreate");
        sPkgHandler = new PkgHandler();
        iccAnnotated = IccUtil.createIccAnnotated(pkgContext);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        IccUtil.destroyIccAnnotated(pkgContext, iccAnnotated);
        base = null;
        baseHandler = null;
        pkgContext = null;
        sPkgHandler = null;
        iccAnnotated = null;
    }

    private final Handler getPkgHandler() {
        return sPkgHandler;
    }

    public static class PkgHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage " + msg.toString());
            if (sHandlerWeakReference != null && sHandlerWeakReference.get() != null) {
                sHandlerWeakReference.get().sendMessage(Message.obtain(msg));
            }
        }
    }
}
