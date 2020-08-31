package com.simonesolita.pswstorer.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.simonesolita.pswstorer.R;
import com.simonesolita.pswstorer.fragments.ListCredenzialeFragment;
import com.simonesolita.pswstorer.fragments.SettingsFragment;
import com.simonesolita.pswstorer.interfaces.OnDeleteListener;

public class CredenzialiListActivity extends PswStorerbaseActivity {
    private FrameLayout frameLayout;
    private BottomNavigationView bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credenziali_list);
        setContent();
        setListeners();
    }

    private void setContent() {
        bottomView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.container_fragment);

        loadFragment(initializeListCredenzialeFragment());
    }

    private void setListeners() {
        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_bottom_navigation_list_credenziali:
                        loadFragment(initializeListCredenzialeFragment());
                        return true;
                    case R.id.menu_bottom_navigation_settings:
                        loadFragment(SettingsFragment.getInstance());
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_fragment, fragment);
        ft.commit();
    }

    private Fragment initializeListCredenzialeFragment(){
        ListCredenzialeFragment listCredenzialeFragment = ListCredenzialeFragment.getInstance();
        listCredenzialeFragment.setOnDeleteListener(new OnDeleteListener(){
            @Override
            public void OnItemDelete(boolean esito) {
                if (esito){
                    setContent();
                }
            }
        });

        return listCredenzialeFragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContent();
    }
}
