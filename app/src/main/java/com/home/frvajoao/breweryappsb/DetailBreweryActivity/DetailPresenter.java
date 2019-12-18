package com.home.frvajoao.breweryappsb.DetailBreweryActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.home.frvajoao.breweryappsb.HomeActivity.HomeInteractor;
import com.home.frvajoao.breweryappsb.HomeActivity.IHomeContract;
import com.home.frvajoao.breweryappsb.Model.Brewery;
import com.home.frvajoao.breweryappsb.R;

import java.util.List;

public class DetailPresenter implements IDetailContract.presenter {

    IDetailContract.view homeView;
    IDetailContract.interactor interactor;

    public DetailPresenter(IDetailContract.view homeView) {
        this.homeView = homeView;
        this.interactor = new DetailInteractor();
    }


    @Override
    public void onPageView(Context context, String url_page) {

        try {
            if (!URLUtil.isValidUrl(url_page)) {
                homeView.displayErrorMessage(context.getString(R.string.url_invalidate));
            } else {
                homeView.showIntentWebsite(url_page);

            }
        } catch (ActivityNotFoundException e) {
            homeView.displayErrorMessage(context.getString(R.string.no_browser));
        }

    }



    @Override
    public void onNetworkError() {

    }

    @Override
    public void onServerError() {

    }
}
