package com.lofiwang.icc;

import android.content.Context;
import android.os.Handler;

public class IccClient {
    private Context base;
    private Handler handler;
    private String pkg;
    private Context pkgContext;
    private Handler pkgHandler;

    public IccClient(Context base, Handler handler, String pkg) {
        this.base = base;
        this.handler = handler;
        this.pkg = pkg;
        pkgContext = ContextUtil.createPkgContext(base, pkg);
        pkgHandler = ContextUtil.getPkgHandler(base, pkgContext, handler);
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
                '}';
    }
}
