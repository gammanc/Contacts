package com.gamma.contacts.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.gamma.contacts.MainActivity;
import com.gamma.contacts.beans.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emers on 5/5/2018.
 */

public class ContactUtils {

    public static final ContactUtils CONTACT_UTILS = new ContactUtils();
    private ArrayList<Contact> mContacts;
    Context context = MainActivity.getContext();

    public ContactUtils() {
        loadDeviceContacts();
    }

    public static ContactUtils getInstace(){
        return CONTACT_UTILS;
    }

    public void setContactList(ArrayList<Contact> contactList){
        mContacts = contactList;
    }

    public ArrayList<Contact> getContactList(){
        return mContacts;
    }

    public void addContact(Contact c){
        mContacts.add(c);
    }

    public void removeContact(Contact c){
        mContacts.remove(c);
    }

    private void loadDeviceContacts(){
        mContacts = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        Cursor phoneCursor;
        String id, name, phoneNumber = "";

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null
                    );
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phoneCursor.close();
                }
                mContacts.add(new Contact(Integer.valueOf(id), name, phoneNumber));
            }
        }
        cursor.close();
    }

}
