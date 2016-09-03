// IRemoteService.aidl
package com.daking.aidl;

// Declare any non-default types here with import statements
import com.daking.aidl.School;

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getName();

    School getSchool();
}
