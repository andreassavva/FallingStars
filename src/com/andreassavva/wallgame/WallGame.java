package com.andreassavva.wallgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by andreassavva on 2016-06-04.
 */
public class WallGame extends Game implements ApplicationListener {

    private Assets assets;

    private GameScreen gameScreen;
    private WelcomeScreen welcomeScreen;

    private AdHandler handler;
    private PlayServices playServices;
    private IabInterface iabInterface;

    public WallGame(AdHandler handler, PlayServices playServices, IabInterface iabInterface) {
        this.handler = handler;
        this.playServices = playServices;
        this.iabInterface = iabInterface;
    }

    @Override
    public void create() {
        assets = new Assets();

        playServices.signIn();
        setWelcomeScreen();
    }

    public void setGameScreen() {
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
    }

    public void setWelcomeScreen() {
        welcomeScreen = new WelcomeScreen(this);
        setScreen(welcomeScreen);
    }

//    public void showAds() {
//        handler.showAds(true);
//    }

//    public void hideAds() {
//        handler.showAds(false);
//    }

    public void showInterstitialAd() {
        handler.showInterstitial();
    }


    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }

    @Override
    public void resize(int width, int height) {

        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    public PlayServices getPlayServices() {
        return playServices;
    }

    public IabInterface getIabInterface() {
        return iabInterface;
    }

    public Assets getAssets() {
        return assets;
    }
}
