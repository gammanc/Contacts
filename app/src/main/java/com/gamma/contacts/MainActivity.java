package com.gamma.contacts;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.gamma.contacts.adapter.ViewPagerAdapter;
import com.gamma.contacts.fragment.FavoriteListFragment;
import com.gamma.contacts.fragment.ListContactFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout main_tab;
    private ViewPager main_viewpager;
    private ViewPagerAdapter adapter;

    private ListContactFragment fragment_main;
    private FavoriteListFragment fragment_favs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Obtener los contactos del sistema
        prepareContacts();
        prepareTabs();
    }

    public void prepareContacts(){

    }

    public void prepareTabs(){
        main_tab = findViewById(R.id.main_tablayout);
        main_viewpager = findViewById(R.id.main_viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //Eliminado la sombra del ActionBar para Android 5+
        //Para versiones anteriores, en el archivo styles agregar
        //<item name="android:windowContentOverlay">@null</item>
        getSupportActionBar().setElevation(0);

        //TODO: compartir con los fragment la lista de contactos

        fragment_main = new ListContactFragment();
        fragment_favs = new FavoriteListFragment();

        adapter.addFragment(fragment_main, "Contactos");
        adapter.addFragment(fragment_favs, "Favoritos");

        main_viewpager.setAdapter(adapter);
        main_tab.setupWithViewPager(main_viewpager);

        //main_tab.getTabAt(0).setIcon(R.drawable.ic_movie);
        main_tab.getTabAt(1).setIcon(R.drawable.ic_star);

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
    }
}
