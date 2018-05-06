package com.gamma.contacts.beans;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import com.gamma.contacts.R;

/**
 * Created by emers on 30/4/2018.
 */

public class Contact implements Parcelable{

    private String mId;
    private String mName, mNumber, mEmail, mBirthday, mAddress;
    private byte[] mPhoto, mThumbnail;

    public Contact() { super(); }

    public Contact(String mId, String mName, String mNumber) {
        this.mId = mId;
        this.mName = mName;
        this.mNumber = mNumber;
    }

    protected Contact(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mNumber = in.readString();
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmId() {
        return mId;
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmBirthday() {
        return mBirthday;
    }

    public void setmBirthday(String mBirthday) {
        this.mBirthday = mBirthday;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public byte[] getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(byte[] mPhoto) {
        this.mPhoto = mPhoto;
    }

    public byte[] getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(byte[] mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public Drawable getRoundedThumbnail(Context context) {
        Bitmap photo = getThumbnail();
        if (photo != null) {
            RoundedBitmapDrawable roundedPicture = RoundedBitmapDrawableFactory.create(context.getResources(), photo);
            roundedPicture.setCircular(true);
            return  roundedPicture;
        }
        else {
            return ContextCompat.getDrawable(context, R.drawable.ic_contact);
        }
    }

    private Bitmap getThumbnail() {
        Bitmap photo = null;
        if (mThumbnail != null && mThumbnail.length > 0) {
            photo = BitmapFactory.decodeByteArray(mThumbnail, 0, mThumbnail.length);
        }
        return photo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Integer.valueOf(mId);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        Contact other = (Contact) obj;
        if (!mId.equals(other.mId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [id=" + mId + ", name=" + mName + ", number="
                + mNumber + "]";
    }

    //Implementando metodos de Parcelable
    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mNumber);
    }
    //--Metodos Parcelable
}
