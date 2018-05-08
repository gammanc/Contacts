package com.gamma.contacts.utils;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by emers on 30/4/2018.
 */
import com.gamma.contacts.beans.Contact;
import com.google.gson.Gson;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SharedPreference {

    public static final String SHARED_PREFS_NAME = "CONTACT_APP ";
    public static final String FAVS = "CONTACT_Favorite";


    public SharedPreference(){
        super();
    }

    //Los siguientes 4 metodos se usan para manejar los favoritos
    public void saveFavorites (Context c, List<Contact> favs){
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = c.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        editor  = settings.edit();

        Gson gson = new Gson();
        String jsonFavs = gson.toJson(favs);

        editor.putString(FAVS ,jsonFavs);
        editor.commit();
    }

    public ArrayList<Contact> getFavorites(Context c){
        SharedPreferences settings;
        List<Contact> favs;

        settings = c.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);

        if(settings.contains(FAVS)){
            String jsonFavs = settings.getString(FAVS, null);
            Gson g = new Gson();
            Contact[] favItems = g.fromJson(jsonFavs, Contact[].class);

            favs = Arrays.asList(favItems);
            favs = new ArrayList<Contact>(favs);
        } else return null;

        return (ArrayList<Contact>) favs;
    }

    public void addFavorite(Context c, Contact p){
        List<Contact> favs = getFavorites(c);
        if(favs == null) favs = new ArrayList<Contact>();
        favs.add(p);
        System.out.println("Favorito guardado");
        saveFavorites(c, favs);
    }

    public void removeFavorite(Context c, Contact p){
        ArrayList<Contact> favs = getFavorites(c);
        if(favs != null) {
            if(favs.remove(p)) System.out.println("Favorito eliminado");
            saveFavorites(c, favs);
            System.out.println("Favorito guardado en array");
        }
    }

    public void printFavorites(Context c){
        ArrayList<Contact> contacts = getFavorites(c);
        System.out.println("Imprimiendo favoritos: --------------------------");
        for (int i = 0; i<contacts.size(); i++){
            System.out.println("["+i+"] ="+ contacts.get(i).getmName());
        }
    }
}
