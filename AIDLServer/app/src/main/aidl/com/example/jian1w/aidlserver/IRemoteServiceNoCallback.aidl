// RemoteService.aidl
package com.example.jian1w.aidlserver;

// Declare any non-default types here with import statements

interface IRemoteServiceNoCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getName();
}
