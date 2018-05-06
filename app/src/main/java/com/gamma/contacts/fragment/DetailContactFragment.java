package com.gamma.contacts.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gamma.contacts.R;
import com.gamma.contacts.utils.ContactUtils;

/**
 * Created by emers on 2/5/2018.
 */

public class DetailContactFragment extends Fragment {

    public static final String ARG_ITEM_ID = "detail_contact";
    Activity activity;
    String contact_id;

    ImageView contact_picture;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

        Bundle bundle = getArguments();
        contact_id = bundle.getString("contact_id");
        System.out.println("El id de contacto es : "+contact_id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_contact,container, false);

        contact_picture = v.findViewById(R.id.contact_picture);
        Bitmap picture = getUserImage();

        if(picture != null) contact_picture.setImageBitmap(picture);
        return v;
    }

    public Bitmap getUserImage(){
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeStream(ContactUtils.getInstace().openPhoto(contact_id));
        return bitmap;
    }


}
