package com.lofiwang.iccandroid;

import android.app.Notification;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.lofiwang.icc.IccClient;
import com.lofiwang.icc.ParcelUtil;

public class MainActivity extends AppCompatActivity {
    private static final String PKG = "com.lofiwang.installedapk";
    ViewGroup host;
    IccClient iccClient;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                View view = (View)msg.obj;
                host.addView(view);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        host = findViewById(R.id.host);

        iccClient = new IccClient(this, handler, PKG);
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
