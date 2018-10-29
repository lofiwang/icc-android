package com.lofiwang.icc;

import android.content.Context;
import android.content.pm.PackageManager;

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
}
