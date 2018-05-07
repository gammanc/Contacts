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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gamma.contacts.R;
import com.gamma.contacts.beans.Contact;
import com.gamma.contacts.utils.ContactUtils;

/**
 * Created by emers on 2/5/2018.
 */

public class DetailContactFragment extends Fragment {

    public static final String ARG_ITEM_ID = "detail_contact";
    Activity activity;
    Contact mContact;
    LayoutInflater mlayoutInflater;

    LinearLayout info_container;
    ImageView contact_picture;
    TextView txtName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        mlayoutInflater = LayoutInflater.from(activity);
        Bundle bundle = getArguments();
        if(bundle != null){
            mContact = bundle.getParcelable("contact");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_contact,container, false);

        findViews(v);

        Bitmap picture = getUserImage();
        if(picture != null) contact_picture.setImageBitmap(picture);
        txtName.setText(mContact.getmName());

        loadContactInfo();

        return v;
    }

    private void findViews(View v){
        info_container = v.findViewById(R.id.info_container);
        contact_picture = v.findViewById(R.id.contact_picture);
        txtName = v.findViewById(R.id.txt_name);
    }

    private void loadContactInfo(){
        if(mContact.getmNumber() != null){
            View phoneview = mlayoutInflater.inflate(R.layout.contact_info_item, null);
            ImageView icon = phoneview.findViewById(R.id.item_icon);
            icon.setImageResource(R.drawable.ic_phone);

            TextView title = phoneview.findViewById(R.id.txt_title);
            title.setText(mContact.getmNumber());

            TextView subtitle = phoneview.findViewById(R.id.txt_subtitle);
            subtitle.setText("Llamar");

            phoneview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "Llamando...", Toast.LENGTH_SHORT).show();
                }
            });

            info_container.addView(phoneview);
        }

        if(mContact.getmEmail() != null && !mContact.getmEmail().equals("")){
            View mailview = mlayoutInflater.inflate(R.layout.contact_info_item, null);
            ImageView icon = mailview.findViewById(R.id.item_icon);
            icon.setImageResource(R.drawable.ic_mail);

            TextView title = mailview.findViewById(R.id.txt_title);
            title.setText(mContact.getmEmail());

            TextView subtitle = mailview.findViewById(R.id.txt_subtitle);
            subtitle.setText(getResources().getString(R.string.form_mail));

            info_container.addView(mailview);
        }

        if(mContact.getmAddress() != null && !mContact.getmAddress().equals("")){
            View addressview = mlayoutInflater.inflate(R.layout.contact_info_item, null);
            ImageView icon = addressview.findViewById(R.id.item_icon);
            icon.setImageResource(R.drawable.ic_address);

            TextView title = addressview.findViewById(R.id.txt_title);
            title.setText(mContact.getmAddress());

            TextView subtitle = addressview.findViewById(R.id.txt_subtitle);
            subtitle.setText(getResources().getString(R.string.form_address));

            info_container.addView(addressview);
        }
        if(mContact.getmBirthday() != null && !mContact.getmBirthday().equals("")){
            View birthdayview = mlayoutInflater.inflate(R.layout.contact_info_item, null);
            ImageView icon = birthdayview.findViewById(R.id.item_icon);
            icon.setImageResource(R.drawable.ic_bday);

            TextView title = birthdayview.findViewById(R.id.txt_title);
            title.setText(mContact.getmBirthday());

            TextView subtitle = birthdayview.findViewById(R.id.txt_subtitle);
            subtitle.setText(getResources().getString(R.string.form_birthday));

            info_container.addView(birthdayview);
        }
    }

    public Bitmap getUserImage(){
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeStream(ContactUtils.getInstace().openPhoto(mContact.getmId()));
        return bitmap;
    }


}
