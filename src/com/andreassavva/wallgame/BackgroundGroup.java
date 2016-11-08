package com.andreassavva.wallgame;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Random;

/**
 * Created by andreassavva on 2016-06-06.
 */
public class BackgroundGroup extends Group {

    private AssetManager assetManager;

    private final float BIRD_WIDTH = 10;
    private final float BIRD_HEIGHT = 10;

    private final float CLOUD_WIDTH = 30;
    private final float CLOUD_HEIGHT = 20;

    private Random random;

    private float gameWorldWidth, gameWorldHeight;
    private int lowestY;

    private Texture backgroundTxtr, bird1Txtr, bird2Txtr, cloud1Txtr, cloud2Txtr, cloud3Txtr;
    private Image background, bird1, bird2, cloud1, cloud2, cloud3;

    public BackgroundGroup(AssetManager assetManager, float gameWorldWidth, float gameWorldHeight, int lowestY) {
        this.assetManager = assetManager;
        this.gameWorldWidth = gameWorldWidth;
        this.gameWorldHeight = gameWorldHeight;
        this.lowestY = lowestY;

        random = new Random();

        // Background textures.
        backgroundTxtr = assetManager.get("background/background.png", Texture.class);
        cloud1Txtr = assetManager.get("background/C1.png", Texture.class);
        cloud2Txtr = assetManager.get("background/C2.png", Texture.class);
        cloud3Txtr = assetManager.get("background/C3.png", Texture.class);
        bird1Txtr = assetManager.get("background/Bird1.png", Texture.class);
        bird2Txtr = assetManager.get("background/Bird2.png", Texture.class);

        // Creating background images
        background = new Image(backgroundTxtr);
        cloud1 = new Image(cloud1Txtr);
        cloud2 = new Image(cloud2Txtr);
        cloud3 = new Image(cloud3Txtr);
        bird1 = new Image(bird1Txtr);
        bird2 = new Image(bird2Txtr);

        // Setting names to actors.
        background.setName("background");
        cloud1.setName("cloud1");
        cloud2.setName("cloud2");
        cloud3.setName("cloud3");
        bird1.setName("bird1");
        bird2.setName("bird2");

        // Adding actors.
        addActor(background);
        addActor(cloud1);
        addActor(cloud2);
        addActor(cloud3);
        addActor(bird1);
        addActor(bird2);

        // Setting background images bounds.
        background.setBounds(0, 0, gameWorldWidth, gameWorldHeight);
        setBackgroundObjectBounds("cloud1");
        setBackgroundObjectBounds("cloud2");
        setBackgroundObjectBounds("cloud3");
        setBackgroundObjectBounds("bird1");
        setBackgroundObjectBounds("bird2");

        // Add actions to background objects
        cloud1.addAction(randomBackgroundObjectMovement("cloud1"));
        cloud2.addAction(randomBackgroundObjectMovement("cloud2"));
        cloud3.addAction(randomBackgroundObjectMovement("cloud3"));
        bird1.addAction(randomBackgroundObjectMovement("bird1"));
        bird2.addAction(randomBackgroundObjectMovement("bird2"));
    }

    public void setBackgroundObjectBounds(String object) {
        if (object.equals("bird1")) {
            bird1.setBounds(0 - BIRD_WIDTH,
                    randomInt(lowestY, (int) (gameWorldHeight - BIRD_HEIGHT)),
                    BIRD_WIDTH,
                    BIRD_HEIGHT);
        } else if (object.equals("bird2")) {
            bird2.setBounds(gameWorldWidth,
                    randomInt(lowestY, (int) (gameWorldHeight - BIRD_HEIGHT)),
                    BIRD_WIDTH,
                    BIRD_HEIGHT);
        } else if (object.equals("cloud1")) {
            cloud1.setBounds(0 - CLOUD_WIDTH,
                    randomInt(lowestY, (int) (gameWorldHeight - CLOUD_HEIGHT)),
                    CLOUD_WIDTH,
                    CLOUD_HEIGHT);
        } else if (object.equals("cloud2")) {
            cloud2.setBounds(gameWorldWidth,
                    randomInt(lowestY, (int) (gameWorldHeight - CLOUD_HEIGHT)),
                    CLOUD_WIDTH,
                    CLOUD_HEIGHT);
        } else if (object.equals("cloud3")) {
            cloud3.setBounds(0 - CLOUD_WIDTH,
                    randomInt(lowestY, (int) (gameWorldHeight - CLOUD_HEIGHT)),
                    CLOUD_WIDTH,
                    CLOUD_HEIGHT);
        }
    }

    public MoveByAction randomBackgroundObjectMovement(String object) {
        MoveByAction moveByAction = new MoveByAction();

        if (object.equals("cloud1") || object.equals("cloud3")) {
            moveByAction.setAmount(gameWorldWidth + CLOUD_WIDTH * 2, 0);
            moveByAction.setDuration(randomInt(40, 50));
        } else if (object.equals("cloud2")) {
            moveByAction.setAmount(0 - gameWorldWidth - CLOUD_WIDTH * 2, 0);
            moveByAction.setDuration(randomInt(40, 50));
        } else if (object.equals("bird1")) {
            moveByAction.setAmount(gameWorldWidth + BIRD_WIDTH * 2, 0);
            moveByAction.setDuration(randomInt(20, 30));
        } else if (object.equals("bird2")) {
            moveByAction.setAmount(0 - gameWorldWidth - BIRD_WIDTH * 2, 0);
            moveByAction.setDuration(randomInt(20, 30));
        }

        return moveByAction;
    }

    public void recreateBackgroundObjects() {
        if (cloud1.getX() >= gameWorldWidth) {
            setBackgroundObjectBounds("cloud1");
            cloud1.clearActions();
            cloud1.addAction(randomBackgroundObjectMovement("cloud1"));
        }
        if (cloud2.getX() <= 0 - CLOUD_WIDTH) {
            setBackgroundObjectBounds("cloud2");
            cloud2.clearActions();
            cloud2.addAction(randomBackgroundObjectMovement("cloud2"));
        }
        if (cloud3.getX() >= gameWorldWidth) {
            setBackgroundObjectBounds("cloud3");
            cloud3.clearActions();
            cloud3.addAction(randomBackgroundObjectMovement("cloud3"));
        }
        if (bird1.getX() >= gameWorldWidth) {
            setBackgroundObjectBounds("bird1");
            bird1.clearActions();
            bird1.addAction(randomBackgroundObjectMovement("bird1"));
        }
        if (bird2.getX() <= 0 - BIRD_WIDTH) {
            setBackgroundObjectBounds("bird2");
            bird2.clearActions();
            bird2.addAction(randomBackgroundObjectMovement("bird2"));
        }
    }

    public int randomInt(int min, int max) {
        return random.nextInt((max + 1) - min) + min;
    }
}
