package com.home.frvajoao.breweryappsb.DetailBreweryActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.home.frvajoao.breweryappsb.Common.Common;
import com.home.frvajoao.breweryappsb.Model.Brewery;
import com.home.frvajoao.breweryappsb.Model.EventBus.BreweryDetailEvent;
import com.home.frvajoao.breweryappsb.R;
import com.home.frvajoao.breweryappsb.Retrofit.IMyBreweryAPI;
import com.home.frvajoao.breweryappsb.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import io.reactivex.disposables.CompositeDisposable;

public class BreweryDetailActivity extends AppCompatActivity implements IDetailContract.view {

    @BindView(R.id.btn_website_view)
    Button btn_regresar;
    @BindView(R.id.txt_brewery_name)
    TextView txt_brewery_name;
    @BindView(R.id.txt_brewery_type)
    TextView txt_brewery_type;
    @BindView(R.id.txt_brewery_address)
    TextView txt_brewery_address;
    @BindView(R.id.txt_brewery_city)
    TextView txt_brewery_city;
    @BindView(R.id.txt_country)
    TextView txt_country;
    @BindView(R.id.txt_phone)
    TextView txt_phone;
    @BindView(R.id.img_brewery_detail)
    KenBurnsView img_brewery_detail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Brewery selectedBrewery;

    private IDetailContract.presenter presenter;

    @OnClick(R.id.btn_website_view)
    public void onClickPageButton() {

        presenter.onPageView(this, selectedBrewery.getWebsite_url());
    }

    IMyBreweryAPI myBreweryAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_detail);

        init();
        initView();
    }

    private void initView() {

        ButterKnife.bind(this);
        presenter = new DetailPresenter(this);

    }

    private void init() {
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myBreweryAPI = RetrofitClient.getInstance(Common.API_BREWERY_ENDPOINT).create(IMyBreweryAPI.class);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    //LISTEN EVENTBUS
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void displayItemDetail(BreweryDetailEvent event) {

        dialog.show();

        if (event.isSuccess()) {

            toolbar.setTitle(event.getBrewery().getName());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            selectedBrewery = event.getBrewery();

            txt_brewery_name.setText(event.getBrewery().getName());
            txt_brewery_type.setText(new StringBuilder(getString(R.string.type)).append(event.getBrewery().getBrewery_type()));
            txt_brewery_address.setText(event.getBrewery().getStreet());
            txt_brewery_city.setText(new StringBuilder(event.getBrewery().getCity() != null ? event.getBrewery().getCity() : getString(R.string.no_city))
                    .append(" - ").append(event.getBrewery().getState() != null ? event.getBrewery().getState() : getString(R.string.no_state)));
            txt_country.setText(event.getBrewery().getCountry());
            txt_phone.setText(event.getBrewery().getPhone());

            Picasso.get().load(event.getBrewery().getImage()).into(img_brewery_detail);

        }

        dialog.dismiss();

    }



    @Override
    public void displayErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showIntentWebsite(String url_page) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url_page));
        startActivity(intent);

    }
}
