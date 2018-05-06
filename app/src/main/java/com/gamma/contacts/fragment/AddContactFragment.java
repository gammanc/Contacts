package com.gamma.contacts.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gamma.contacts.R;

import java.util.Calendar;

/**
 * Created by emers on 2/5/2018.
 */

public class AddContactFragment extends Fragment{
    public static final String ARG_ITEM_ID = "add_contact";
    Activity activity;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextInputEditText txtBirthday;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_contact,container, false);

        txtBirthday = v.findViewById(R.id.edt_birthday);
        //txtBirthday.setEnabled(false);

        txtBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                    txtBirthday.clearFocus();
                }
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;
                txtBirthday.setText(day+"/"+month+"/"+year);
            }
        };
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_contact:
                Toast.makeText(getContext(), "This will work", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
