package com.example.weather_watch;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState==null){//if we saw activity first time and when we roatate savedInstances is not null then homescreen(base fragment) is not loaded
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new fragment_base()).commit();
        navigationView.setCheckedItem(R.id.nav_home);}
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
        super.onBackPressed();
        }
    }

    public void watch(View view) {

        Intent intent=new Intent(getApplicationContext(),watchActivity.class);
        startActivity(intent);

    }

    public void weather(View view) {
        Intent intent=new Intent(getApplicationContext(),weatherActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new fragment_base()).commit();

                break;
            case R.id.nav_watch:
                Intent intent=new Intent(getApplicationContext(),watchActivity.class);
                startActivity(intent);

                break;
            case R.id.nav_weather:
                Intent intent1=new Intent(getApplicationContext(),weatherActivity.class);
                startActivity(intent1);

                break;
            case R.id.nav_about:
                Toast.makeText(this, "Developed by Abhay Kaushal.", Toast.LENGTH_SHORT).show();

                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
