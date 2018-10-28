package com.lofiwang.icc;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ContextUtil {
    public static final Context createContext(Context base, String pkg) {
        Context context = null;
        try {
            context = base.createPackageContext(pkg, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return context;
    }

    public static final Handler getNewCtxHandler(Context newCtx) {
        try {
            Class clazz = newCtx.getClassLoader().loadClass("com.lofiwang.icc.ContextManager");
            try {
                Method method = clazz.getDeclaredMethod("getHandler");
                try {
                    return (Handler) method.invoke(clazz);
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
