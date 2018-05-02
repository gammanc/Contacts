package com.gamma.contacts.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gamma.contacts.R;
import com.gamma.contacts.adapter.ViewPagerAdapter;

/**
 * Created by emers on 1/5/2018.
 */

public class TabContactFragment extends Fragment {

    private View view;
    private TabLayout main_tab;
    private ViewPager main_viewpager;
    private ViewPagerAdapter adapter;
    private FloatingActionButton fab_addPerson;

    private ListContactFragment fragment_main;
    private FavoriteListFragment fragment_favs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_contact, container, false);
        prepareTabs();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void prepareTabs(){
        main_tab = view.findViewById(R.id.main_tablayout);
        main_viewpager = view.findViewById(R.id.main_viewpager);
        fab_addPerson = view.findViewById(R.id.fab_addperson);
        adapter = new ViewPagerAdapter(getFragmentManager());

        //Eliminado la sombra del ActionBar para Android 5+
        //Para versiones anteriores, en el archivo styles agregar
        //<item name="android:windowContentOverlay">@null</item>
        //getSupportActionBar().setElevation(0);

        //TODO: compartir con los fragment la lista de contactos

        fragment_main = new ListContactFragment();
        fragment_favs = new FavoriteListFragment();

        adapter.addFragment(fragment_main, getResources().getString(R.string.tab_contacts));
        adapter.addFragment(fragment_favs, getResources().getString(R.string.tab_favorites));

        main_viewpager.setAdapter(adapter);
        main_tab.setupWithViewPager(main_viewpager);

        main_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = adapter.getFragment(position);
                fragment.onResume();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab_addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "This WILL work", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
