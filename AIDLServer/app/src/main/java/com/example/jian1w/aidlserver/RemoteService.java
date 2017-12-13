package com.example.jian1w.aidlserver;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.example.jian1w.aidlclient.*;
/**
 * Created by jian1.w on 10/30/17.
 */

public class RemoteService extends Service {
    private static final String TAG = "RemoteService";

    public RemoteService() {
        Log.d(TAG, "RemoteService created tid: " + android.os.Process.myPid() + ":" + Process.myTid());

    }

    @Override
    public IBinder onBind(Intent itent) {
        return mBinder;
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        }

        @Override
        public String getName(IBinder callback) throws RemoteException {
            Log.d(TAG, "getName");
            IClientCallback icb = IClientCallback.Stub.asInterface(callback);
            icb.onResult();

            /*
            Handler handler = new Handler(Looper.getMainLooper());

            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(RemoteService.this, "getName with callback called ", Toast.LENGTH_LONG).show();
                }
            };
            handler.post(task);
            */
            Toast.makeText(RemoteService.this, "getName with callback called ", Toast.LENGTH_LONG).show();
            Log.d(TAG, "getName toast");
            return "daking";
        }
    };
}
