package com.gamma.contacts.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gamma.contacts.R;
import com.gamma.contacts.adapter.ContactsAdapter;
import com.gamma.contacts.beans.Contact;
import com.gamma.contacts.utils.SharedPreference;

import java.util.ArrayList;

/**
 * Created by emers on 30/4/2018.
 */

public class ListContactFragment extends Fragment implements ContactsAdapter.ContactsClickListener{

    public static final String ARG_ITEM_ID = "contact_list";

    Activity activity;
    RecyclerView contactListView;
    ArrayList<Contact> mContacts;
    ContactsAdapter contactsAdapter;
    GridLayoutManager gManager;

    SharedPreference sharedPreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sharedPreference = new SharedPreference();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_contact, container, false);

        contactListView = v.findViewById(R.id.main_recycler);
        contactListView.setHasFixedSize(true);

        gManager = new GridLayoutManager(container.getContext(), 3);
        contactListView.setLayoutManager(gManager);

        //TODO: Obtener los contactos del sistema
        setExampleContacts();

        contactsAdapter = new ContactsAdapter(activity, mContacts, this);
        contactListView.setAdapter(contactsAdapter);

        return v;
    }

    public void setExampleContacts(){
        mContacts = new ArrayList<Contact>();
        mContacts.add(new Contact(1, "Contacto 1", "7878 7878"));
        mContacts.add(new Contact(2, "Contacto 2", "7878 7878"));
        mContacts.add(new Contact(3, "Contacto 3", "7878 7878"));
        mContacts.add(new Contact(4, "Contacto 4", "7878 7878"));
        mContacts.add(new Contact(5, "Contacto 5", "7878 7878"));
        mContacts.add(new Contact(6, "Contacto 6", "7878 7878"));
        mContacts.add(new Contact(7, "Contacto 7", "7878 7878"));
        mContacts.add(new Contact(8, "Contacto 8", "7878 7878"));
        mContacts.add(new Contact(9, "Contacto 9", "7878 7878"));
        mContacts.add(new Contact(10, "Contacto 10", "7878 7878"));
        mContacts.add(new Contact(11, "Contacto 11", "7878 7878"));
        mContacts.add(new Contact(12, "Contacto 12", "7878 7878"));
        mContacts.add(new Contact(13, "Contacto 13", "7878 7878"));
        mContacts.add(new Contact(14, "Contacto 14", "7878 7878"));
        mContacts.add(new Contact(15, "Contacto 15", "7878 7878"));
        mContacts.add(new Contact(16, "Contacto 16", "7878 7878"));
        mContacts.add(new Contact(17, "Contacto 17", "7878 7878"));
        mContacts.add(new Contact(18, "Contacto 18", "7878 7878"));
        mContacts.add(new Contact(19, "Contacto 19", "7878 7878"));
    }

    //Implementando metodos de interfaz
    @Override
    public void onContactChecked(View v, int position) {
        ImageView button = (ImageView) v.findViewById(R.id.btn_favorite);

        String tag = button.getTag().toString();

        if(tag.equalsIgnoreCase("inactive")){
            sharedPreference.addFavorite(activity, mContacts.get(position));
            Toast.makeText(activity, "Añadido a Favoritos", Toast.LENGTH_SHORT).show();

            button.setTag("active");
            button.setImageResource(R.drawable.ic_star);
        }else {
            sharedPreference.removeFavorite(activity, mContacts.get(position));
            button.setTag("inactive");
            button.setImageResource(R.drawable.ic_star_border);
            Toast.makeText(activity, "Eliminado de Favoritos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onContactClick(View v, int position) {

    }

    @Override
    public void onResume() {
        //TODO: Obtener los contactos del sistema
        contactsAdapter.notifyDataSetChanged();
        super.onResume();
    }
}
