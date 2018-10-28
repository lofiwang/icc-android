package com.lofiwang.iccandroid;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.lofiwang.icc.ContextUtil;
import com.lofiwang.icc.ParcelUtil;
import com.lofiwang.icc.ParcelableTest;

public class MainActivity extends AppCompatActivity {
    private static final String PKG = "com.lofiwang.installedapk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context newContext = ContextUtil.createContext(this, PKG);
        Handler handler = ContextUtil.getNewCtxHandler(newContext);
        ParcelableTest parcelableTest = new ParcelableTest();
        parcelableTest.x = 0;
        parcelableTest.y = 1;
        parcelableTest.des = "test";

        Notification notification = new Notification();
        notification.category = "xxxxxxx";

        Message msg = new Message();
        msg.what = 0;


        msg.getData().putByteArray("test", ParcelUtil.writeValue(parcelableTest));
        msg.getData().putParcelable("test1", notification);

        handler.sendMessage(msg);
    }
}
