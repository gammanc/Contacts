package com.gamma.contacts;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.gamma.contacts.adapter.ViewPagerAdapter;
import com.gamma.contacts.fragment.DetailContactFragment;
import com.gamma.contacts.fragment.FavoriteListFragment;
import com.gamma.contacts.fragment.ListContactFragment;
import com.gamma.contacts.fragment.TabContactFragment;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{

    private TabContactFragment tabContactFragment;
    private DetailContactFragment detailContactFragment;

    private Fragment contentF;
    private FragmentManager fragmentManager;

    Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey("myContent")){
                String content = savedInstanceState.getString("myContent");
                if(content.equals(DetailContactFragment.ARG_ITEM_ID)){
                    if(fragmentManager.findFragmentByTag(DetailContactFragment.ARG_ITEM_ID) != null){
                        contentF = fragmentManager.findFragmentByTag(DetailContactFragment.ARG_ITEM_ID);
                    }
                }
            }

            if(fragmentManager.findFragmentByTag(TabContactFragment.ARG_ITEM_ID) != null){
                tabContactFragment = (TabContactFragment) fragmentManager.findFragmentByTag(TabContactFragment.ARG_ITEM_ID);
                contentF = tabContactFragment;
            }
        } else {
            tabContactFragment = new TabContactFragment();
            setTitle(R.string.app_name);
            switchContent(tabContactFragment, TabContactFragment.ARG_ITEM_ID);
        }
    }

    public void setTitle(int resource){
        getSupportActionBar().setTitle(getResources().getString(resource));
    }

    //NAVEGACIÓN
    public void switchContent(Fragment fragment, String tag) {
        while (fragmentManager.popBackStackImmediate());

        if (fragment != null){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_down, R.anim.slide_out_up,
                    R.anim.slide_in_down, R.anim.slide_out_up);
            transaction.replace(R.id.contentFrame, fragment, tag);

            //Si no es TabContact, se agrega al backStack
            if(!(fragment instanceof TabContactFragment)){
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentF = fragment;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if( contentF instanceof DetailContactFragment) outState.putString("myContent", DetailContactFragment.ARG_ITEM_ID);
        else outState.putString("myContent", TabContactFragment.ARG_ITEM_ID);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        navigate();
    }

    @Override
    public void onBackStackChanged() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(fragmentManager.getBackStackEntryCount()>0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        navigate();
        return true;
    }

    public void navigate(){
        if (fragmentManager.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentF instanceof TabContactFragment
                || fragmentManager.getBackStackEntryCount() == 0) {
            finish();
        }
    }
}
