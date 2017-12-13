package com.example.jian1w.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.jian1w.aidlserver.IRemoteService;
import com.example.jian1w.aidlserver.IRemoteServiceNoCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AidlClient";
    private static Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button btnBind = (Button) findViewById(R.id.button_bind);
        Button btnUnbind = (Button) findViewById(R.id.button_unbind);
        Button btnCall = (Button) findViewById(R.id.button_call);
        updateBtn = btnBind;
        btnBind.setOnClickListener(this);
        btnUnbind.setOnClickListener(this);
        btnCall.setOnClickListener(this);

        ((Button) findViewById(R.id.btn_bind2)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_unbind2)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_call2)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.button_bind:
                //intent.setComponent(new ComponentName("com.example.jian1w.aidlserver","com.example.jian1w.aidlserver.START_SERVICE"));
                intent.setAction("com.example.jian1w.aidlserver.START_SERVICE");
                intent.setPackage("com.example.jian1w.aidlserver");
                bindService(intent, conn, Context.BIND_AUTO_CREATE);
                Log.d(TAG, "bind service");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                        Toast.makeText(getApplicationContext(), "from thread " + Process.myTid(), Toast.LENGTH_LONG).show();
                                    }
                            });
                        }
                }).run();
                break;
            case R.id.button_call:
                try {
                    Log.d(TAG, mService.getName(new ClientCallback()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_unbind:
                unbindService(conn);
                break;
            case R.id.btn_bind2:
                //intent.setComponent(new ComponentName("com.example.jian1w.aidlserver","com.example.jian1w.aidlserver.START_SERVICE"));
                intent.setAction("com.example.jian1w.aidlserver.SERVICE_NOCALLBACK");
                intent.setPackage("com.example.jian1w.aidlserver");
                bindService(intent, conn2, Context.BIND_AUTO_CREATE);
                Log.d(TAG, "bind service no callback");
                break;
            case R.id.btn_unbind2:
                unbindService(conn2);
                break;
            case R.id.btn_call2:
                try {
                   Log.d(TAG,mServiceNoCallback.getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }

    private static class ClientCallback extends IClientCallback.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }
        @Override
        public void onResult() {
            int myPid = Process.myPid();
            Log.d(TAG, "my pid " + myPid);
            //Toast.makeText(, "my pid " + myPid, Toast.LENGTH_LONG).show();
            StringBuilder text = new StringBuilder(updateBtn.getText().toString());

            updateBtn.setText(text.reverse());
        }
    }
    private IRemoteService mService;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = IRemoteService.Stub.asInterface(iBinder);
            try {
                Log.d(TAG, "call getName" + mService.getName(new ClientCallback()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private IRemoteServiceNoCallback mServiceNoCallback;
    private ServiceConnection conn2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceNoCallback = IRemoteServiceNoCallback.Stub.asInterface(iBinder);
            try {
                Log.d(TAG, "call getName" + mServiceNoCallback.getName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
