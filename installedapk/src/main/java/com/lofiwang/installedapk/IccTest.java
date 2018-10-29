package com.lofiwang.installedapk;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.lofiwang.icc.IccLifecycle;
import com.lofiwang.icc.IccServer;
import com.lofiwang.icc.ParcelUtil;
import com.lofiwang.iccann.Icc;

/**
 * Created by chunsheng.wang on 2018/10/29.
 */

@Icc
public class IccTest extends IccLifecycle {
    private static final String TAG = "IccTest";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            View view = LayoutInflater.from(IccServer.peekInstance().getPkgContext()).inflate(R.layout.activity_main,null);
            Message message = new Message();
            message.what = 1;
            message.obj = view;
            IccServer.peekInstance().getBaseHandler().sendMessage(message);
            Log.d(TAG, "handleMessage1 " + msg.toString());
            Log.d(TAG, "handleMessage2 " + msg.getData().toString());
            ParcelableTest parcelableTest = (ParcelableTest) ParcelUtil.readValue((byte[]) msg.getData().get("test"), ParcelableTest.class);
            Log.d(TAG, "handleMessage3 " + parcelableTest.toString());
            Notification notification = (Notification) msg.getData().get("test1");
            Log.d(TAG, "handleMessage4 " + notification.toString());
        }
    };

    @Override
    protected void onCreate() {
        IccServer.peekInstance().setReceiveHandler(handler);
    }

    @Override
    protected void onDestroy() {
        IccServer.peekInstance().removeReceiveHandler();
    }
}
