package com.lofiwang.icc;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ParcelUtil {
    public static Object readValue(byte[] bytes, Class parcelableClazz) {
        Parcel reply = Parcel.obtain();
        try {
            reply.unmarshall(bytes, 0, bytes.length);
            reply.setDataPosition(0);

            reply.readInt();
            reply.readDouble();
            reply.readString();
        } finally {
            reply.recycle();
        }
        Constructor constructor = null;
        try {
            constructor = parcelableClazz.getDeclaredConstructor(Parcel.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Object o = null;
        try {
            o = constructor.newInstance(reply);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static byte[] writeValue(Parcelable parcelable) {
        Parcel data = Parcel.obtain();
        byte[] bytes;
        try {
            parcelable.writeToParcel(data, parcelable.describeContents());
            bytes = data.marshall();
        } finally {
            data.recycle();
        }
        return bytes;
    }
}
