package com.lofiwang.icc;

import android.content.Context;
import android.os.Handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by chunsheng.wang on 2018/10/29.
 */

public class IccUtil {
    private static final String ICC_SERVER_CLAZZ = "com.lofiwang.icc.IccServer";

    public static final Object createIccServer(Context base, Context pkgContext, Handler handler) {
        try {
            Object iccServer;
            Class clazz = pkgContext.getClassLoader().loadClass(ICC_SERVER_CLAZZ);
            Constructor constructor = clazz.getDeclaredConstructor(Context.class, Context.class, Handler.class);
            constructor.setAccessible(true);
            iccServer = constructor.newInstance(base, pkgContext, handler);
            callIccServerCreate(clazz, iccServer);
            return iccServer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final void destroyIccServer(Context pkgContext, Object iccServerObj) {
        try {
            Class clazz = pkgContext.getClassLoader().loadClass(ICC_SERVER_CLAZZ);
            callIccServerDestroy(clazz, iccServerObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final void callIccServerCreate(Class iccServerClazz, Object iccServerObj) {
        try {
            Method method = iccServerClazz.getDeclaredMethod("onCreate");
            method.setAccessible(true);
            method.invoke(iccServerObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final void callIccServerDestroy(Class iccServerClazz, Object iccServerObj) {
        try {
            Method method = iccServerClazz.getDeclaredMethod("onDestroy");
            method.setAccessible(true);
            method.invoke(iccServerObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Handler getPkgHandler(Context pkgContext, Object iccServer) {
        try {
            Class clazz = pkgContext.getClassLoader().loadClass(ICC_SERVER_CLAZZ);
            Method method = clazz.getDeclaredMethod("getPkgHandler");
            method.setAccessible(true);
            return (Handler) method.invoke(iccServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
