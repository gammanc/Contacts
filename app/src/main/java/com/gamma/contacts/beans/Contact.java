package com.gamma.contacts.beans;

/**
 * Created by emers on 30/4/2018.
 */

public class Contact {
    private int mId;
    private String mName, mNumber;

    public Contact() { super(); }

    public Contact(int mId, String mName, String mNumber) {
        this.mId = mId;
        this.mName = mName;
        this.mNumber = mNumber;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + mId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contact other = (Contact) obj;
        if (mId != other.mId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [id=" + mId + ", name=" + mName + ", number="
                + mNumber + "]";
    }
}
