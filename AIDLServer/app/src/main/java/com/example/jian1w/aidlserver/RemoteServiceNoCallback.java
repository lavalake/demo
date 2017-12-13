package com.example.jian1w.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jian1.w on 10/30/17.
 */

public class RemoteServiceNoCallback extends Service {
    private static final String TAG = "RemoteServiceNoCallback";

    public RemoteServiceNoCallback() {
        Log.d(TAG, "RemoteServiceNoCallback created tid: " + android.os.Process.myPid() + ":" + Process.myTid());

    }

    @Override
    public IBinder onBind(Intent itent) {
        return mBinder;
    }

    private final IRemoteServiceNoCallback.Stub mBinder = new IRemoteServiceNoCallback.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }

        @Override
        public String getName() throws RemoteException {
            Log.d(TAG, "getName");
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RemoteServiceNoCallback.this, "getName with callback called ", Toast.LENGTH_LONG).show();
                }
            });
            Log.d(TAG, "getName toast");
            return "RemoteServiceNoCallback";
        }
    };
}
