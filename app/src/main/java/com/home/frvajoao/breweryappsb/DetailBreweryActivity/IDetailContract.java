package com.home.frvajoao.breweryappsb.DetailBreweryActivity;

import android.app.Activity;
import android.content.Context;

import com.home.frvajoao.breweryappsb.Callback.IServerCallback;
import com.home.frvajoao.breweryappsb.HomeActivity.HomePresenter;
import com.home.frvajoao.breweryappsb.Model.Brewery;

import java.util.List;

public interface IDetailContract {

    interface view{

        void displayErrorMessage(String message);

        void showIntentWebsite(String url_page);
    }

    interface presenter extends IServerCallback {

        void onPageView(Context context, String url);

    }

    interface interactor{
        void performSearchByName(Activity context, String searchText, HomePresenter homePresenter);
        void performSearchPlaces(Activity context, HomePresenter homePresenter);

    }



}
