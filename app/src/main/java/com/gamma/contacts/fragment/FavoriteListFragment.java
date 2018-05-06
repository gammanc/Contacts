package com.gamma.contacts.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
 * Created by emers on 1/5/2018.
 */

public class FavoriteListFragment extends Fragment implements ContactsAdapter.ContactsClickListener{

    public static final String ARG_ITEM_ID = "contact_fav_list";

    Activity activity;
    ContactsAdapter adapter;

    SharedPreference sharedPreference;
    ArrayList<Contact> mFavContacts;
    LinearLayoutManager gManager;

    RecyclerView contactListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_contact,container,false);

        //Obtener la lista de favoritos guardada
        sharedPreference = new SharedPreference();
        mFavContacts = sharedPreference.getFavorites(activity);

        if(mFavContacts == null){
            //TODO: hacer algo si noy hay favoritos
        } else {
            if(mFavContacts.size() == 0){
                //TODO: hacer algo si noy hay favoritos
            }
            contactListView = v.findViewById(R.id.main_recycler);
            if(mFavContacts != null) {
                contactListView.setHasFixedSize(true);

                gManager = new LinearLayoutManager(container.getContext());
                contactListView.setLayoutManager(gManager);

                adapter = new ContactsAdapter(activity, mFavContacts, this);
                contactListView.setAdapter(adapter);
            }
        }
        return v;
    }

    public void printFavorites(){
        System.out.println("Imprimiendo favoritos: --------------------------");
        for (int i = 0; i<mFavContacts.size(); i++){
            System.out.println("["+i+"] ="+ mFavContacts.get(i).getmName());
        }
    }

    //Implementando metodos de Interfaz
    @Override
    public void onContactChecked(View v, int position) {
        ImageView button = (ImageView) v.findViewById(R.id.btn_favorite);

        String tag = button.getTag().toString();

        if(tag.equalsIgnoreCase("inactive")){
            sharedPreference.addFavorite(activity, mFavContacts.get(position));
            Toast.makeText(activity, "Añadido a Favoritos", Toast.LENGTH_SHORT).show();

            button.setTag("active");
            button.setImageResource(R.drawable.ic_star);
        }else {
            sharedPreference.removeFavorite(activity, mFavContacts.get(position));
            adapter.remove(mFavContacts.get(position));
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
        mFavContacts = sharedPreference.getFavorites(activity);

        if(mFavContacts == null){
            //TODO: hacer algo si noy hay favoritos
        } else {
            if(mFavContacts.size() == 0){
                //TODO: hacer algo si noy hay favoritos
            }
            if(mFavContacts != null) {
                //printFavorites();
                adapter = new ContactsAdapter(activity, mFavContacts, this);
                contactListView.setAdapter(adapter);
            }
        }
        super.onResume();
    }
}
