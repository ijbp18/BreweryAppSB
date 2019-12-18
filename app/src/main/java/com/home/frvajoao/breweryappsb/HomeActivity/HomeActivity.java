package com.home.frvajoao.breweryappsb.HomeActivity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.home.frvajoao.breweryappsb.Adapter.BrewerySliderAdapter;
import com.home.frvajoao.breweryappsb.Adapter.MyBrewerydapter;
import com.home.frvajoao.breweryappsb.Common.Common;
import com.home.frvajoao.breweryappsb.Model.Brewery;
import com.home.frvajoao.breweryappsb.R;
import com.home.frvajoao.breweryappsb.Retrofit.IMyBreweryAPI;
import com.home.frvajoao.breweryappsb.Retrofit.RetrofitClient;
import com.home.frvajoao.breweryappsb.Services.PicassoImageLoaderService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.disposables.CompositeDisposable;
import ss.com.bannerslider.Slider;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IHomeContract.view {

    IMyBreweryAPI myBreweryAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AlertDialog dialog;
    MyBrewerydapter searchAdapter, adapter;

    @BindView(R.id.banner_slider)
    Slider banner_slider;
    @BindView(R.id.recycler_brewery)
    RecyclerView recycler_brewery;

    private IHomeContract.presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_home_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name_title));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        init();
        initView();

        loadBreweries();
    }

    private void loadBreweries() {

        dialog.show();
        presenter.searchAllPlaces(this);

    }

    private void init() {

        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myBreweryAPI = RetrofitClient.getInstance(Common.API_BREWERY_ENDPOINT).create(IMyBreweryAPI.class);
        Slider.init(new PicassoImageLoaderService());
        presenter = new HomePresenter(this);
    }

    private void initView() {

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_brewery.setLayoutManager(layoutManager);
        recycler_brewery.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            presenter.onExitApp(this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_serach, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                startSearchFood(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                loadBreweries();
                return true;
            }
        });

        return true;

    }

    private void startSearchFood(String query) {

        dialog.show();
        presenter.searchBreweryItems(this, query);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_log_out) {
            presenter.onExitApp(this);
        } else if (id == R.id.nav_nearby) {
            Toast.makeText(this, "Para una próxima versión... :)", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_fav) {
            Toast.makeText(this, "Para una próxima versión... :)", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void displayBreweriesItems(List<Brewery> breweryList) {
        if (dialog.isShowing()) dialog.dismiss();
        adapter = new MyBrewerydapter(this, breweryList);
        recycler_brewery.setAdapter(adapter);


    }

    @Override
    public void displayFailedSearch() {
        if (dialog.isShowing()) dialog.dismiss();
        recycler_brewery.setAdapter(null);
        Toast.makeText(this, getString(R.string.failed_search), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySearchNotFound() {

        if (dialog.isShowing()) dialog.dismiss();
        Toast.makeText(this, getString(R.string.not_found_search), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void displayBreweriesBanner(List<Brewery> breweryList) {

        if (dialog.isShowing()) dialog.dismiss();
        banner_slider.setAdapter(new BrewerySliderAdapter(breweryList));
    }

    @Override
    public void acceptExitApp() {
        finish();
    }

    @Override
    public void displayNetworkError() {
        if (dialog.isShowing()) dialog.dismiss();
        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayServerError() {
        if (dialog.isShowing()) dialog.dismiss();
        Toast.makeText(this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
    }


}
