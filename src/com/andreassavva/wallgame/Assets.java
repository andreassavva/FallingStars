package com.andreassavva.wallgame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

/**
 * Created by andreassavva on 2016-06-06.
 */
public class Assets {

    private final AssetManager assetManager = new AssetManager();

    public void load() {

        assetManager.load("uiskin.atlas", TextureAtlas.class);

        assetManager.load("buttons/TableForB.png", Texture.class);
        assetManager.load("buttons/RB.png", Texture.class);
        assetManager.load("buttons/OB.png", Texture.class);
        assetManager.load("buttons/GB.png", Texture.class);
        assetManager.load("buttons/BB.png", Texture.class);
        assetManager.load("buttons/pausebtn.png", Texture.class);

        assetManager.load("walls/RW.png", Texture.class);
        assetManager.load("walls/OW.png", Texture.class);
        assetManager.load("walls/GW.png", Texture.class);
        assetManager.load("walls/BW.png", Texture.class);

        assetManager.load("background/background.png", Texture.class);
        assetManager.load("background/Bird1.png", Texture.class);
        assetManager.load("background/Bird2.png", Texture.class);
        assetManager.load("background/C1.png", Texture.class);
        assetManager.load("background/C2.png", Texture.class);
        assetManager.load("background/C3.png", Texture.class);
        assetManager.load("background/darkbackg.png", Texture.class);

        assetManager.load("stars/RS.png", Texture.class);
        assetManager.load("stars/OS.png", Texture.class);
        assetManager.load("stars/GS.png", Texture.class);
        assetManager.load("stars/BS.png", Texture.class);

        assetManager.load("textbuttons/GT.png", Texture.class);

        assetManager.load("sounds/right.wav", Sound.class);
        assetManager.load("sounds/wrong.wav", Sound.class);
        assetManager.load("sounds/bgm.wav", Music.class);

        Array<Texture> textures = new Array<Texture>();
        assetManager.getAll(Texture.class, textures);
        for (Texture t: textures) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

//    public void dispose() {
//        assetManager.dispose();
//    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
