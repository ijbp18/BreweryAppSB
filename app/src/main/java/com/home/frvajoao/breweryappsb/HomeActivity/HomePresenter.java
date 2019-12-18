package com.home.frvajoao.breweryappsb.HomeActivity;

import android.app.Activity;
import android.content.res.Resources;

import androidx.appcompat.app.AlertDialog;


import com.home.frvajoao.breweryappsb.Model.Brewery;
import com.home.frvajoao.breweryappsb.R;

import java.util.List;

public class HomePresenter implements IHomeContract.presenter {

    IHomeContract.view homeView;
    IHomeContract.interactor interactor;

    public HomePresenter(IHomeContract.view homeView) {
        this.homeView = homeView;
        this.interactor = new HomeInteractor();
    }


    @Override
    public void searchBreweryItems(Activity context, String query) {
        interactor.performSearchByName(context, query, this);
    }

    @Override
    public void onBrewerytemsFound(List<Brewery> breweries) {
        homeView.displayBreweriesItems(breweries);
        homeView.displayBreweriesBanner(breweries);
    }

    @Override
    public void onFailedSearch() {
        homeView.displayFailedSearch();
    }

    @Override
    public void searchAllPlaces(Activity context) {
        interactor.performSearchPlaces(context, this);
    }

    @Override
    public void onSearchNotFound() {
        homeView.displaySearchNotFound();
    }

    @Override
    public void onLoadImagesItems(Activity context, List<Brewery> breweries) {
        Resources res = context.getResources();
        String[] planets = res.getStringArray(R.array.image_url);
        int i = 0;
        for (String url : planets) {
            breweries.get(i).setImage(url);
            i++;
        }
    }

    @Override
    public void onExitApp(Activity context) {
        androidx.appcompat.app.AlertDialog confirmDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.salir))
                .setMessage(context.getString(R.string.message_exit))
                .setNegativeButton(context.getString(R.string.cancelar), (dialog, which) -> dialog.dismiss())
                .setPositiveButton(context.getString(R.string.aceptar), (dialog, which) -> homeView.acceptExitApp()).create();

        confirmDialog.show();
    }


    @Override
    public void onNetworkError() {
        homeView.displayNetworkError();
    }

    @Override
    public void onServerError() {
        homeView.displayServerError();
    }
}
