package com.home.frvajoao.breweryappsb.Model.EventBus;

import com.home.frvajoao.breweryappsb.Model.Brewery;

import java.util.List;

public class BreweryLoadEvent {

    private boolean success = false;
    private List<Brewery> breweryList;
    private String message;

    public BreweryLoadEvent(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BreweryLoadEvent(boolean success, List<Brewery> breweryList) {
        this.success = success;
        this.breweryList = breweryList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Brewery> getBreweryList() {
        return breweryList;
    }

    public void setBreweryList(List<Brewery> breweryList) {
        this.breweryList = breweryList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
