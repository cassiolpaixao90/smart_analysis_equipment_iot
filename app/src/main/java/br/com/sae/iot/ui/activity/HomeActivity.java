package br.com.sae.iot.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.sae.iot.R;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_id);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        switch (id) {
            case R.id.home_id:
                return true;

            case R.id.area_id:
                return true;

            case R.id.product_id:
                return true;

        }
        return false;
    }
}
