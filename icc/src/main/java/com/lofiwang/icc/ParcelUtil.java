package com.lofiwang.icc;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Constructor;

public class ParcelUtil {
    public static Object readValue(byte[] bytes, Class parcelableClazz) {
        Parcel reply = Parcel.obtain();
        try {
            reply.unmarshall(bytes, 0, bytes.length);
            reply.setDataPosition(0);
            reply.readInt();
            reply.readDouble();
            reply.readString();
            Constructor constructor = parcelableClazz.getDeclaredConstructor(Parcel.class);
            constructor.setAccessible(true);
            return constructor.newInstance(reply);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reply.recycle();
        }
        return null;
    }

    public static byte[] writeValue(Parcelable parcelable) {
        Parcel data = Parcel.obtain();
        byte[] bytes;
        try {
            parcelable.writeToParcel(data, parcelable.describeContents());
            bytes = data.marshall();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            data.recycle();
        }
        return null;
    }
}
