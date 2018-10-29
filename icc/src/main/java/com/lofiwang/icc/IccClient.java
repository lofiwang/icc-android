package com.lofiwang.icc;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class IccClient extends IccLifecycle {
    private static final String TAG = "IccClient";
    private Context base;
    private Handler handler;
    private String pkg;
    private Context pkgContext;
    private Handler pkgHandler;
    private Object iccServer;

    public IccClient(Context base, Handler handler, String pkg) {
        this.base = base;
        this.handler = handler;
        this.pkg = pkg;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        pkgContext = ContextUtil.createPkgContext(base, pkg);
        iccServer = IccUtil.createIccServer(base, pkgContext, handler);
        pkgHandler = IccUtil.getPkgHandler(pkgContext, iccServer);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        IccUtil.destroyIccServer(pkgContext, iccServer);
        base = null;
        handler = null;
        pkg = null;
        pkgContext = null;
        pkgHandler = null;
    }

    public Context getBase() {
        return base;
    }

    public void setBase(Context base) {
        this.base = base;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Context getPkgContext() {
        return pkgContext;
    }

    public void setPkgContext(Context pkgContext) {
        this.pkgContext = pkgContext;
    }

    public Handler getPkgHandler() {
        return pkgHandler;
    }

    public void setPkgHandler(Handler pkgHandler) {
        this.pkgHandler = pkgHandler;
    }

    @Override
    public String toString() {
        return "IccClient{" +
                "base=" + base +
                ", handler=" + handler +
                ", pkg='" + pkg + '\'' +
                ", pkgContext=" + pkgContext +
                ", pkgHandler=" + pkgHandler +
                ", iccServer=" + iccServer +
                '}';
    }
}
