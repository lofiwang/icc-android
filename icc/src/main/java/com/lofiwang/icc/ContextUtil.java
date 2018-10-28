package com.lofiwang.icc;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ContextUtil {
    public static final Context createPkgContext(Context base, String pkg) {
        Context context = null;
        try {
            context = base.createPackageContext(pkg, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return context;
    }

    public static final Handler getPkgHandler(Context base, Context pkgContext, Handler handler) {
        try {
            Class clazz = pkgContext.getClassLoader().loadClass("com.lofiwang.icc.IccServer");
            try {
                Object instance = null;

                Constructor constructor = clazz.getConstructor(Context.class, Context.class, Handler.class);
                try {
                    instance = constructor.newInstance(base, pkgContext, handler);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                Method method = clazz.getDeclaredMethod("getPkgHandler");
                try {
                    return (Handler) method.invoke(instance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
