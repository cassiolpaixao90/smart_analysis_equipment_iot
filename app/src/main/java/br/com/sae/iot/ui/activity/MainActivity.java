package br.com.sae.iot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import br.com.sae.iot.R;
import br.com.sae.iot.ui.fragments.HomeFragment;
import br.com.sae.iot.ui.fragments.IndustryFragment;

/**
 * @author cassiopaixao
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_id);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.home_id);

    }

    public void goToHome(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }


    private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;

        switch (itemId) {
            case R.id.home_id:
                fragment = new HomeFragment();
                break;
            case R.id.business_id:
                fragment = new IndustryFragment();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_container_wrapper, fragment);
            ft.commit();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    @Override
    public void onClick(View v) {
        displaySelectedScreen(v.getId());
    }

}

