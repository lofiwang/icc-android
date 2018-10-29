package com.lofiwang.installedapk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lofiwang.icc.IccServer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IccServer.peekInstance();
    }
}
