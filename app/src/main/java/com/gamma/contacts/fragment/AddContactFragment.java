package com.gamma.contacts.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gamma.contacts.R;
import com.gamma.contacts.beans.Contact;
import com.gamma.contacts.utils.ContactUtils;

import java.util.Calendar;

/**
 * Created by emers on 2/5/2018.
 */

public class AddContactFragment extends Fragment{
    public static final String ARG_ITEM_ID = "add_contact";
    Activity activity;

    private FloatingActionButton fab_picture;
    private TextInputEditText txt_name;
    private TextInputEditText txt_phone;
    private TextInputEditText txt_email;
    private TextInputEditText txt_address;
    private TextInputEditText txt_birthday;
    private Button btn_clear;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_contact,container, false);

        txt_name = v.findViewById(R.id.edt_name);
        txt_phone = v.findViewById(R.id.edt_phone);
        txt_email = v.findViewById(R.id.edt_mail);
        txt_address = v.findViewById(R.id.edt_address);
        txt_birthday = v.findViewById(R.id.edt_birthday);
        btn_clear = v.findViewById(R.id.btn_clear);
        fab_picture = v.findViewById(R.id.fab_addpicture);
        //txtBirthday.setEnabled(false);

        txt_birthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePicker = new DatePickerDialog(activity,
                            android.R.style.Theme_Holo_Light_Dialog, mDateSetListener, year, month, day);
                    //datePicker.getDatePicker().setSpinnersShown(true);
                    datePicker.getDatePicker().setMaxDate(c.getTimeInMillis());
                    datePicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePicker.getDatePicker().setCalendarViewShown(false);
                    datePicker.setTitle(getResources().getString(R.string.pick_date));
                    datePicker.show();
                    txt_birthday.clearFocus();
                }
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;
                txt_birthday.setText(day+"/"+month+"/"+year);
                btn_clear.setVisibility(View.VISIBLE);
            }
        };

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_birthday.setText("");
                btn_clear.setVisibility(View.GONE);
            }
        });

        fab_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "This will work", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        activity = getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_contact,menu);
        MenuItem hideitem = menu.findItem(R.id.action_search);
        hideitem.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_contact:
                addContact();
                //Ocultando el teclado
                View view = getActivity().findViewById(android.R.id.content);
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addContact(){
        if (!txt_name.getText().toString().trim().isEmpty()){
            Contact c = new Contact();
            c.setmId(String.valueOf(ContactUtils.getInstace().generateId()));
            c.setmName(txt_name.getText().toString().trim());
            c.setmNumber(txt_phone.getText().toString().trim());
            c.setmEmail(txt_email.getText().toString().trim());
            c.setmAddress(txt_address.getText().toString().trim());
            c.setmBirthday(txt_birthday.getText().toString().trim());
            ContactUtils.getInstace().addContact(c);
            Toast.makeText(getContext(), "El contacto fue agregado", Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        }
        else {
            Toast.makeText(getContext(), "El nombre es requerido",Toast.LENGTH_SHORT).show();
        }
    }
}
