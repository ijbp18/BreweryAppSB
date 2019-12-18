package com.home.frvajoao.breweryappsb.Model.EventBus;

import com.home.frvajoao.breweryappsb.Model.Brewery;

public class BreweryDetailEvent {

    private boolean success = false;
    private Brewery brewery;

    public BreweryDetailEvent(boolean success, Brewery brewery) {
        this.success = success;
        this.brewery = brewery;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }
}
