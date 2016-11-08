package com.andreassavva.wallgame;

/**
 * Created by andreassavva on 2016-06-11.
 */
public interface IabInterface {

    String SKU_REMOVE_ADS = "remove_ads";

    // (arbitrary) request code for the purchase flow
    int RC_REQUEST = 10001;

    void removeAds();

    void processPurchases();
}
