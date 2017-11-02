package com.example.jian1w.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by jian1.w on 10/30/17.
 */

public class RemoteService extends Service {
    private static final String TAG = "RemoteService";

    public RemoteService() {

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
        public String getName() throws RemoteException {
            Log.d(TAG, "getName");
            return "daking";
        }
    };
}
