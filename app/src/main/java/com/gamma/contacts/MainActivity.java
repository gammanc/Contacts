package com.gamma.contacts;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Toast;

import com.gamma.contacts.adapter.ViewPagerAdapter;
import com.gamma.contacts.fragment.FavoriteListFragment;
import com.gamma.contacts.fragment.ListContactFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout main_tab;
    private ViewPager main_viewpager;
    private ViewPagerAdapter adapter;
    private FloatingActionButton fab_addPerson;

    private ListContactFragment fragment_main;
    private FavoriteListFragment fragment_favs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO: Obtener los contactos del sistema
        //prepareContacts();
        //prepareTabs();
    }

    public void prepareContacts(){

    }

    public void prepareTabs(){
        main_tab = findViewById(R.id.main_tablayout);
        main_viewpager = findViewById(R.id.main_viewpager);
        fab_addPerson = findViewById(R.id.fab_addperson);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

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
