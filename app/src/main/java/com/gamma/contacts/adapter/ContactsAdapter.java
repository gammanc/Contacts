package com.gamma.contacts.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gamma.contacts.R;
import com.gamma.contacts.beans.Contact;
import com.gamma.contacts.utils.SharedPreference;

import java.util.ArrayList;

/**
 * Created by emers on 30/4/2018.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>{

    //Variables de la clase
    private Context mContext;
    private ArrayList<Contact> mContacts;
    SharedPreference sharedPreference;

    //Interfaz necesaria para manejar eventos
    private ContactsClickListener mListener;
    public interface ContactsClickListener{
        public void onContactChecked(View v, int position, boolean checked);
        public void onContactClick(View v, int position);
    }

    //Constructor de la clase
    public ContactsAdapter(Context mContext, ArrayList<Contact> mContacts, ContactsClickListener mListener) {
        this.mContext = mContext;
        this.mContacts = mContacts;
        this.mListener = mListener;
        sharedPreference = new SharedPreference();
    }

    //Subclase para ViewHolder
    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        CardView card;
        TextView txtName, txtPhone;
        ToggleButton btnFav;

        public ContactViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            card = itemView.findViewById(R.id.item_cardview);
            txtName = itemView.findViewById(R.id.txt_name);
            txtPhone = itemView.findViewById(R.id.txt_number);
            btnFav = itemView.findViewById(R.id.btn_fav);
        }
    }


    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_contact ,parent,false);
        return (new ContactViewHolder(v));
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {
        final Contact contact = mContacts.get(position);
        holder.txtName.setText(contact.getmName());
        holder.txtPhone.setText(contact.getmNumber());
        holder.btnFav.setChecked(checkFavoriteItem(contact));

        holder.btnFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mListener.onContactChecked(holder.itemView, position, isChecked);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mContacts != null ? mContacts.size() : 0;
    }


    public boolean checkFavoriteItem(Contact checkContact) {
        boolean check = false;
        ArrayList<Contact> favorites = sharedPreference.getFavorites(mContext);
        if (favorites != null) {
            check = favorites.contains(checkContact);
        }
        return check;
    }

    public void remove(Contact contact) {
        //super.remove(product);
        mContacts.remove(contact);
        notifyDataSetChanged();
    }
}
