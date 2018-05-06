package com.gamma.contacts.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.gamma.contacts.MainActivity;
import com.gamma.contacts.beans.Contact;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
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

    private Contact mContact;
    private void loadDeviceContacts(){
        mContacts = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        String sort = ContactsContract.Contacts.DISPLAY_NAME + " ASC";
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[] {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.HAS_PHONE_NUMBER
                }, null, null, sort);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                mContact = new Contact();
                mContact.setmId(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
                mContact.setmName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                int hasPhoneNumber = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) retrievePhone(contentResolver);
                retrieveEmail(contentResolver);
                retrieveAddress(contentResolver);
                retrieveBirthday(contentResolver);
                mContact.setmThumbnail(openThumbnail(mContact.getmId()));

                mContacts.add(mContact);
            }
        }
        cursor.close();
    }

    private void retrievePhone(ContentResolver contentResolver) {
        String phoneNumber="";
        Cursor cursor  = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{String.valueOf(mContact.getmId())},
                null
        );

        while (cursor.moveToNext()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
        mContact.setmNumber(phoneNumber);
        cursor.close();
    }

    private void retrieveEmail(ContentResolver contentResolver) {
        String email = "";
        Cursor cursor  = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Email.ADDRESS},
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
                new String[]{mContact.getmId()},
                null
        );
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
        }
        cursor.close();
        mContact.setmEmail(email);
    }

    private void retrieveAddress(ContentResolver contentResolver) {
        String address = "";
        Cursor cursor  = contentResolver.query(
                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                new String[]{
                        ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS
                },
                ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + "=?",
                new String[]{mContact.getmId()},
                null
        );
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            address = cursor.getString(0);
        }
        cursor.close();
        mContact.setmAddress(address);
    }

    private void retrieveBirthday(ContentResolver contentResolver) {
        String birthday = "";
        Cursor cursor = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                new String[] {ContactsContract.CommonDataKinds.Event.START_DATE},
                ContactsContract.Data.MIMETYPE + " = ? AND " +
                        ContactsContract.CommonDataKinds.Event.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Event.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY,
                new String[]{
                        ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                        mContact.getmId()
                },
                null
        );
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            birthday = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
        }
        cursor.close();
        mContact.setmBirthday(birthday);
    }

    /* Acorde a la documentación de Android :
    *  Devuelve la versión thumnail de la foto de contacto
    */
    public byte[] openThumbnail(String contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactId));
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    //return new ByteArrayInputStream(data);
                    return data;
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    /* Acorde a la documentación de Android :
    *  Devuelve la foto de contacto de mayor calidad desde donde está almacenada
    */
    public InputStream openPhoto(String contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactId));
        Uri displayPhotoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);
        try {
            AssetFileDescriptor fd = context.getContentResolver().openAssetFileDescriptor(displayPhotoUri, "r");
            return fd.createInputStream();
        } catch (IOException e) {
            return null;
        }
    }

}
