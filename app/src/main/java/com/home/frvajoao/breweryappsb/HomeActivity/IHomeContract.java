package com.home.frvajoao.breweryappsb.HomeActivity;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.home.frvajoao.breweryappsb.Callback.IServerCallback;
import com.home.frvajoao.breweryappsb.Model.Brewery;

import java.util.List;

public interface IHomeContract {

    interface view{

        void displayBreweriesItems(List<Brewery> breweryList);

        void displayFailedSearch();

        void displaySearchNotFound();

        void displayNetworkError();

        void displayServerError();

        void displayBreweriesBanner(List<Brewery> breweryList);

        void acceptExitApp();
    }

    interface presenter extends IServerCallback {

        void searchBreweryItems(Activity context, String query);

        void onBrewerytemsFound(List<Brewery> breweries);


        void onFailedSearch();
        
        // Items Places
        void searchAllPlaces(Activity context);

        void onSearchNotFound();

        void onLoadImagesItems(Activity context, List<Brewery> breweries);

        void onExitApp(Activity context);
    }

    interface interactor{
        void performSearchByName(Activity context, String searchText, HomePresenter homePresenter);
        void performSearchPlaces(Activity context, HomePresenter homePresenter);

    }



}
