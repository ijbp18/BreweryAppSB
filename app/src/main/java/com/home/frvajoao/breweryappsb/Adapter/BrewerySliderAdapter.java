package com.home.frvajoao.breweryappsb.Adapter;

import com.home.frvajoao.breweryappsb.Common.Common;
import com.home.frvajoao.breweryappsb.Model.Brewery;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class BrewerySliderAdapter extends SliderAdapter {

    List<Brewery> breweryList;

    public BrewerySliderAdapter(List<Brewery> breweryList) {
        this.breweryList = breweryList;
    }

    @Override
    public int getItemCount() {
        return breweryList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {

        imageSlideViewHolder.bindImageSlide(breweryList.get(position).getImage());

    }
}
