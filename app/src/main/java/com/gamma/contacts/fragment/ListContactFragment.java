package com.gamma.contacts.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.gamma.contacts.R;
import com.gamma.contacts.adapter.ContactsAdapter;
import com.gamma.contacts.beans.Contact;
import com.gamma.contacts.utils.ContactUtils;
import com.gamma.contacts.utils.Permissions;
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

    TabContactFragment parent;
    LinearLayoutManager gManager;

    SharedPreference sharedPreference;

    //TabContactFragment

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        activity = getActivity();
        sharedPreference = new SharedPreference();
        if(savedInstanceState != null){
            mContacts = savedInstanceState.getParcelableArrayList("contacts");
        } else {
            if(Permissions.hasPermission(activity, Manifest.permission.READ_CONTACTS)){
                mContacts = ContactUtils.getInstace().getContactList();
            } else {
                Permissions.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, Permissions.READ_CONTACTS_CODE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_contact, container, false);

        contactListView = v.findViewById(R.id.main_recycler);
        contactListView.setHasFixedSize(true);

        gManager = new LinearLayoutManager(container.getContext());
        contactListView.setLayoutManager(gManager);

        contactsAdapter = new ContactsAdapter(activity, mContacts, this);
        contactListView.setAdapter(contactsAdapter);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mContacts.clear();
        mContacts.addAll(contactsAdapter.getOriginal());
        outState.putParcelableArrayList("contacts", (ArrayList) mContacts);
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

        FragmentManager fm2 = getFragmentManager();

        parent = (TabContactFragment) fm2.findFragmentByTag(TabContactFragment.ARG_ITEM_ID);
        parent.setFabVisible(View.GONE);

        FragmentTransaction ft2 = fm2.beginTransaction();
        ft2.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_down,
                R.anim.slide_in_down, R.anim.slide_out_down);

        DetailContactFragment addContactFragment = new DetailContactFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("contact", mContacts.get(position));
        addContactFragment.setArguments(bundle);

        ft2.addToBackStack(AddContactFragment.ARG_ITEM_ID);
        ft2.hide(fm2.findFragmentByTag(TabContactFragment.ARG_ITEM_ID));
        ft2.add(R.id.contentFrame, addContactFragment);
        ft2.commit();
    }

    @Override
    public void onResume() {
        contactsAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Permissions.READ_CONTACTS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mContacts = ContactUtils.getInstace().getContactList();
                    contactsAdapter = new ContactsAdapter(activity, mContacts, this);
                    contactListView.setAdapter(contactsAdapter);
                }
                else{
                    mContacts = new ArrayList<>();
                    Toast.makeText(activity, "No se pudo leer los contactos. Por favor concede el permiso.",
                            Toast.LENGTH_SHORT);
                }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search,menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQueryHint(getText(R.string.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactsAdapter.filter(query);
                //se oculta el EditText
                searchView.setQuery("", false);
                searchView.setIconified(true);
                contactsAdapter.restoreList();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                contactsAdapter.filter(newText);
                return true;
            }
        });
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    contactsAdapter.restoreList();
                }
            }
        });
    }
}
