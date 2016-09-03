package com.daking.aidltest2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daking.aidl.IRemoteService;
import com.daking.aidl.School;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    IRemoteService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBind = (Button) findViewById(R.id.btn_bind);
        Button btnUnbind = (Button) findViewById(R.id.btn_unbind);
        Button btnCall = (Button) findViewById(R.id.btn_call);
        btnBind.setOnClickListener(this);
        btnUnbind.setOnClickListener(this);
        btnCall.setOnClickListener(this);
    }

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IRemoteService.Stub.asInterface(service);
            Toast.makeText(MainActivity.this, "onServiceConnected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            Toast.makeText(MainActivity.this, "onServiceDisconnected", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bind:
                Intent intent = new Intent();
                intent.setPackage("com.daking.aidl");
                intent.setAction("com.daking.aidl.RemoteService");
                bindService(intent, conn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(conn);
                break;
            case R.id.btn_call:
                if (null != mService){
                    try {
                        School school = mService.getSchool();
                        Toast.makeText(MainActivity.this, "name: " + school.getName() + ", type: " + school.getType(),
                                Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
