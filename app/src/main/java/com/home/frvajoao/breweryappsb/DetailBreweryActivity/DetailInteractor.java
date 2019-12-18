package com.home.frvajoao.breweryappsb.DetailBreweryActivity;

import android.app.Activity;

import com.home.frvajoao.breweryappsb.Common.Common;
import com.home.frvajoao.breweryappsb.HomeActivity.HomePresenter;
import com.home.frvajoao.breweryappsb.HomeActivity.IHomeContract;
import com.home.frvajoao.breweryappsb.Model.EventBus.BreweryLoadEvent;
import com.home.frvajoao.breweryappsb.Retrofit.IMyBreweryAPI;
import com.home.frvajoao.breweryappsb.Retrofit.RetrofitClient;

import org.greenrobot.eventbus.EventBus;

import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailInteractor implements IDetailContract.interactor {

    IMyBreweryAPI myBreweryAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DetailInteractor() {
        myBreweryAPI = RetrofitClient.getInstance(Common.API_BREWERY_ENDPOINT).create(IMyBreweryAPI.class);
    }


    @Override
    public void performSearchByName(Activity context, String searchtext, HomePresenter callback) {

        compositeDisposable.add(myBreweryAPI.searchBrewery(Common.PER_PAGE, searchtext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(breweryModel -> {

                            if (breweryModel != null) {
                                callback.onLoadImagesItems(context, breweryModel);
                                callback.onBrewerytemsFound(breweryModel);
                            } else {
                                callback.onSearchNotFound();
                            }

                        },
                        throwable -> {
                            callback.onFailedSearch();
                        })
        );

    }

    @Override
    public void performSearchPlaces(Activity context, HomePresenter callback) {

        Paper.init(context);

        compositeDisposable.add(
                myBreweryAPI.getBreweries(Common.PER_PAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(breweryModel -> {
                                    EventBus.getDefault().post(new BreweryLoadEvent(true, breweryModel));

                                    if (breweryModel != null) {

                                        callback.onLoadImagesItems(context, breweryModel);
                                        callback.onBrewerytemsFound(breweryModel);

                                    } else {
                                        callback.onSearchNotFound();
                                    }
                                },
                                throwable -> callback.onFailedSearch())
        );

    }


}
