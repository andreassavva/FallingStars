package com.andreassavva.wallgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by andreassavva on 2016-05-30.
 */
public class WelcomeScreen implements Screen {

    private AssetManager assetManager;

    private static final float GAME_WORLD_WIDTH = 100;
    private static final float GAME_WORLD_HEIGHT = 200;

    private static final float TABLE_WIDTH = 60;
    private static final float TABLE_HEIGHT = 100;

    private Preferences preferences;

    private WallGame game;
    private Stage stage;
    private Camera camera;
    private Viewport viewport;

    private GameSkin skin;

    private Table table;
    private Image background;
    private Texture backgroundTxtr;
    private BackgroundGroup backgroundGroup;

    private TextButton playButton, highscoresButton, musicButton, rateUsButton, removeAdsButton, sfxButton;

    private boolean musicToggle, sfxToggle;

    private Music bgm;

    public WelcomeScreen(final WallGame game) {
        this.game = game;
        Assets assets = game.getAssets();
        assetManager = assets.getAssetManager();
        assets.load();
        assetManager.finishLoading();
        preferences = Gdx.app.getPreferences("My Preferences");

        // Settings
        musicToggle = preferences.getBoolean("musicOn", true);
        sfxToggle = preferences.getBoolean("sfxOn", true);

        // Setting up camera and viewport.
        camera = new OrthographicCamera();
        viewport = new StretchViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera);
        viewport.apply();
        stage = new Stage(viewport);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        Gdx.input.setInputProcessor(stage);

        // Creating the skin.
        skin = new GameSkin(assetManager);
        skin.getFont("default-font").getData().setScale(0.08f, 0.08f);

        // Sound.
        bgm = assetManager.get("sounds/bgm.wav");
        if (preferences.getBoolean("musicOn", true)) {
            bgm.setLooping(true);
            bgm.play();
        }

        // Background.
        backgroundTxtr = assetManager.get("background/background.png");
        background = new Image(backgroundTxtr);
        background.setBounds(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);

        // Table.
        table = new Table(skin);
        table.setBounds(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);

        // Creating buttons.
        playButton = new TextButton("Play", skin, "green-button");
//        highscoresButton = new TextButton("Highscores", skin, "red-button");
        rateUsButton = new TextButton("Rate us", skin, "orange-button");
        removeAdsButton = new TextButton("Remove ads", skin, "blue-button");
        musicButton = new TextButton("Music: ON", skin, "red-button");
        if (!musicToggle)
            musicButton.setText("Music: OFF");
        sfxButton = new TextButton("SFX: ON", skin, "green-button");
        if (!sfxToggle)
            sfxButton.setText("SFX: OFF");

        table.add(playButton).width(60).height(20).pad(4).padTop(20);
        table.row();
//        table.add(highscoresButton).width(60).height(20).pad(4);
//        table.row();
        table.add(rateUsButton).width(60).height(20).pad(4);
        table.row();
        table.add(removeAdsButton).width(60).height(20).pad(4);
        table.row();
        table.add(musicButton).width(60).height(20).pad(4);
        table.row();
        table.add(sfxButton).width(60).height(20).pad(4).padBottom(20);

        playButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setGameScreen();
            }
        });
        rateUsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.getPlayServices().rateGame();
            }
        });
        removeAdsButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.getIabInterface().removeAds();
            }
        });
        musicButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (musicToggle) {
                    musicButton.setText("Music: OFF");
                    bgm.stop();
                } else {
                    musicButton.setText("Music: ON");
                    bgm.setLooping(true);
                    bgm.play();
                }

                musicToggle = !musicToggle;
                preferences.putBoolean("musicOn", musicToggle);
                preferences.flush();
            }
        });
        sfxButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (sfxToggle) {
                    sfxButton.setText("SFX: OFF");
                } else {
                    sfxButton.setText("SFX: ON");
                }

                sfxToggle = !sfxToggle;
                preferences.putBoolean("sfxOn", sfxToggle);
                preferences.flush();
            }
        });

        backgroundGroup = new BackgroundGroup(assetManager, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, 0);

        stage.addActor(background);
        stage.addActor(backgroundGroup);
        stage.addActor(table);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        assetManager.update();
        stage.act();
        stage.draw();
        backgroundGroup.recreateBackgroundObjects();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();

        assetManager.dispose();
        skin.getFont("default-font").dispose();
    }


}
