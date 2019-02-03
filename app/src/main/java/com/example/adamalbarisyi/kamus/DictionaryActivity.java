package com.example.adamalbarisyi.kamus;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.adamalbarisyi.kamus.adapter.DictionaryAdapter;
import com.example.adamalbarisyi.kamus.db.DictionaryHelper;
import com.example.adamalbarisyi.kamus.model.DictionaryModel;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    RecyclerView rvWord;
    DictionaryAdapter dictionaryAdapter;
    DictionaryHelper dictionaryHelper;
    boolean isEnglish;

    ArrayList<DictionaryModel> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvWord = findViewById(R.id.recyclerview);


        dictionaryHelper = new DictionaryHelper(this);
        dictionaryAdapter = new DictionaryAdapter(this);
        rvWord.setLayoutManager(new LinearLayoutManager(this));
        rvWord.setAdapter(dictionaryAdapter);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSearch("");
    }

    private void getSearch( String s) {
        dictionaryHelper.open();
        models = dictionaryHelper.getData(s, isEnglish);
        dictionaryHelper.close();
        dictionaryAdapter.addItem(models);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String word) {
                    getSearch(word);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String word) {
                    getSearch(word);
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_english_indonesia) {
            isEnglish = true;
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("English to Indonesia");
                getSearch(" ");
            }
        } else if (id == R.id.nav_indonesia_english) {
            isEnglish = false;
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Indonesia to English");
                getSearch(" ");
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
