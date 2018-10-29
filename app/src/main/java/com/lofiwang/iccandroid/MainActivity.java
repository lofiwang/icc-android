package com.lofiwang.iccandroid;

import android.app.Notification;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.lofiwang.icc.IccClient;
import com.lofiwang.icc.ParcelUtil;

public class MainActivity extends AppCompatActivity {
    private static final String PKG = "com.lofiwang.installedapk";
    IccClient iccClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iccClient = new IccClient(this, new Handler(), PKG);
        iccClient.onCreate();

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

        iccClient.getPkgHandler().sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iccClient.onDestroy();
    }
}
