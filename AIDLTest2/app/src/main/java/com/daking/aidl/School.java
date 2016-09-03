package com.daking.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class School implements Parcelable {

    private String name;
    private String type;

    public School() {
    }

    public School(Parcel source) {
        super();
        this.setName(source.readString());
        this.setType(source.readString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
    }

    public static final Parcelable.Creator<School> CREATOR = new Parcelable.Creator<School>() {
        public School createFromParcel(Parcel source) {
            return new School(source);
        }

        public School[] newArray(int size) {
            return new School[size];
        }
    };

}
