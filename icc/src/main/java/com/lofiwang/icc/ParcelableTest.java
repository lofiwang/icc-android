package com.lofiwang.icc;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableTest implements Parcelable {
    public int x;
    public int y;
    public String des;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.x);
        dest.writeInt(this.y);
        dest.writeString(this.des);
    }

    public ParcelableTest() {
    }

    protected ParcelableTest(Parcel in) {
        this.x = in.readInt();
        this.y = in.readInt();
        this.des = in.readString();
    }

    public static final Creator<ParcelableTest> CREATOR = new Creator<ParcelableTest>() {
        @Override
        public ParcelableTest createFromParcel(Parcel source) {
            return new ParcelableTest(source);
        }

        @Override
        public ParcelableTest[] newArray(int size) {
            return new ParcelableTest[size];
        }
    };

    @Override
    public String toString() {
        return "ParcelableTest{" +
                "x=" + x +
                ", y=" + y +
                ", des='" + des + '\'' +
                '}';
    }
}
