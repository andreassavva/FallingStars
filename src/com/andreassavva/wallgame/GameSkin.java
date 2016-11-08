package com.andreassavva.wallgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Created by andreassavva on 2016-06-06.
 */
public class GameSkin extends Skin {

    private AssetManager assetManager;
    private TextureAtlas atlas;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont defaultFont;

    private TextButton.TextButtonStyle redButtonStyle, orangeButtonStyle, greenButtonStyle, blueButtonStyle, defaultButtonStyle;

    private Label.LabelStyle labelStyle;

    private Window.WindowStyle windowStyle, firstTimeWindowStyle;

    public GameSkin(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.finishLoading();

        atlas = assetManager.get("uiskin.atlas");
        addRegions(atlas);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/komika.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        defaultFont = generator.generateFont(parameter);
        defaultFont.setUseIntegerPositions(false);
        generator.dispose();

        defaultButtonStyle = new TextButton.TextButtonStyle();
        redButtonStyle = new TextButton.TextButtonStyle();
        orangeButtonStyle = new TextButton.TextButtonStyle();
        greenButtonStyle = new TextButton.TextButtonStyle();
        blueButtonStyle = new TextButton.TextButtonStyle();

        redButtonStyle.font = defaultFont;
        redButtonStyle.up = getDrawable("rbup");
        redButtonStyle.down = getDrawable("rbdown");

        orangeButtonStyle.font = defaultFont;
        orangeButtonStyle.up = getDrawable("obup");
        orangeButtonStyle.down = getDrawable("obdown");

        greenButtonStyle.font = defaultFont;
        greenButtonStyle.up = getDrawable("gbup");
        greenButtonStyle.down = getDrawable("gbdown");

        blueButtonStyle.font = defaultFont;
        blueButtonStyle.up = getDrawable("bbup");
        blueButtonStyle.down = getDrawable("bbdown");

        defaultButtonStyle.font = defaultFont;
        defaultButtonStyle.up = getDrawable("obup");
        defaultButtonStyle.down = getDrawable("obdown");

        labelStyle = new Label.LabelStyle(defaultFont, Color.WHITE);

        windowStyle = new Window.WindowStyle(defaultFont, Color.WHITE, getDrawable("GT"));

        firstTimeWindowStyle = new Window.WindowStyle(defaultFont, Color.WHITE, getDrawable("guide"));

        add("default-font", defaultFont);
        add("red-button", redButtonStyle);
        add("orange-button", orangeButtonStyle);
        add("green-button", greenButtonStyle);
        add("blue-button", blueButtonStyle);
        add("default", defaultButtonStyle);
        add("default", labelStyle);
        add("default", windowStyle);
        add("first-time", firstTimeWindowStyle);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
