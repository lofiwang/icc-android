package com.lofiwang.icc;

import android.content.Context;
import android.os.Handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by chunsheng.wang on 2018/10/29.
 */

public class IccUtil {
    private static final String ICC_SERVER_CLAZZ = "com.lofiwang.icc.IccServer";
    private static final String ICC_CONFIG_CLAZZ = "com.lofiwang.icc.IccConfig";

    public static Object createIccServer(Context base, Context pkgContext, Handler handler) {
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

    public static void destroyIccServer(Context pkgContext, Object iccServerObj) {
        try {
            Class clazz = pkgContext.getClassLoader().loadClass(ICC_SERVER_CLAZZ);
            callIccServerDestroy(clazz, iccServerObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void callIccServerCreate(Class iccServerClazz, Object iccServerObj) {
        try {
            Method method = iccServerClazz.getDeclaredMethod("onCreate");
            method.setAccessible(true);
            method.invoke(iccServerObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void callIccServerDestroy(Class iccServerClazz, Object iccServerObj) {
        try {
            Method method = iccServerClazz.getDeclaredMethod("onDestroy");
            method.setAccessible(true);
            method.invoke(iccServerObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Handler getPkgHandler(Context pkgContext, Object iccServer) {
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

    public static Object createIccAnnotated(Context pkgContext) {
        Class iccAnnotatedClazz = getIccAnnotatedClazz(pkgContext);
        try {
            Object iccAnnotatedObj = iccAnnotatedClazz.newInstance();
            callIccAnnotatedCreate(iccAnnotatedClazz, iccAnnotatedObj);
            return iccAnnotatedObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void destroyIccAnnotated(Context pkgContext, Object iccAnnotatedObj) {
        Class iccAnnotatedClazz = getIccAnnotatedClazz(pkgContext);
        try {
            callIccAnnotatedDestroy(iccAnnotatedClazz, iccAnnotatedObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Class getIccAnnotatedClazz(Context pkgContext) {
        try {
            Class clazz = pkgContext.getClassLoader().loadClass(ICC_CONFIG_CLAZZ);
            Field field = clazz.getDeclaredField("ICC_MAIN_CLAZZ");
            field.setAccessible(true);
            String annotatedClazzName = (String) field.get(clazz);
            return pkgContext.getClassLoader().loadClass(annotatedClazzName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void callIccAnnotatedCreate(Class iccAnnotatedClazz, Object iccAnnotatedObj) {
        try {
            Method method = iccAnnotatedClazz.getDeclaredMethod("onCreate");
            method.setAccessible(true);
            method.invoke(iccAnnotatedObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void callIccAnnotatedDestroy(Class iccAnnotatedClazz, Object iccAnnotatedObj) {
        try {
            Method method = iccAnnotatedClazz.getDeclaredMethod("onDestroy");
            method.setAccessible(true);
            method.invoke(iccAnnotatedObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
