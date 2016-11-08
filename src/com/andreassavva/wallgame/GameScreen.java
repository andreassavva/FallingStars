package com.andreassavva.wallgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class GameScreen implements Screen {

    private final float GAME_WORLD_WIDTH = 100;
    private final float GAME_WORLD_HEIGHT = 200;

    private final float BUTTON_WIDTH = 15;
    private final float BUTTON_HEIGHT = 30;

    private final float WALL_WIDTH = 90;
    private final float WALL_HEIGHT = 10;

    private final float STAR_WIDTH = 20;
    private final float STAR_HEIGHT = 20;

    private final float PAUSE_BUTTON_WIDTH = 7;
    private final float PAUSE_BUTTON_HEIGHT = 14;

    private AssetManager assetManager;
    private Preferences preferences;

    private WallGame game;

    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private GameSkin skin;

    private boolean isPaused = false;
    private boolean creatingStars = false;
    private boolean firstTime, sfxOn;
    private boolean newHighscore = false;

    private BackgroundGroup backgroundGroup;

    private Texture pauseBtnTxtr;
    private Image pauseBtn;

    private Texture buttonBoardTexture, redBtnTexture, orangeBtnTexture, greenBtnTexture, blueBtnTexture;
    private Image buttonBoard, redBtn, orangeBtn, greenBtn, blueBtn;
    private Group buttonGroup;
    private Image darkBkgr;

    private Image wall;
    private Texture redWallTxtr, orangeWallTxtr, greenWallTxtr, blueWallTxtr;
    private String wallColor;

    private float aspectRatio;

    private Group starGroup;
    private Texture redStarTxtr, orangeStarTxtr, greenStarTxtr, blueStarTxtr;
    private float starSpeed;

    private Random random;
    private float starGroupTime = 0;
    private float timeBetweenStarGroups;

    private int score, topScore;
    private Label scoreLabel, topScoreLabel;
    private Group labelGroup;

    private TextButton restartButton, menuButton, resumeButton;
    private Dialog gameOverDialog, pauseDialog;

    private Sound rightSound, wrongSound;

    public GameScreen(final WallGame game) {
        this.game = game;
        aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        random = new Random();
        preferences = Gdx.app.getPreferences("My Preferences");
        firstTime = preferences.getBoolean("firstTime", true);
        sfxOn = preferences.getBoolean("sfxOn", true);


        // ***************************ASSET MANAGEMENT******************************************
        Assets assets = game.getAssets();
        assetManager = assets.getAssetManager();
        assets.load();
        assetManager.finishLoading();

        skin = new GameSkin(assetManager);


        // ***************************SOUNDS****************************************************
        rightSound = assetManager.get("sounds/right.wav");
        wrongSound = assetManager.get("sounds/wrong.wav");


        // ***************************BUTTONS************************************************

        // Button textures.
        buttonBoardTexture = assetManager.get("buttons/TableForB.png", Texture.class);
        redBtnTexture = assetManager.get("buttons/RB.png", Texture.class);
        orangeBtnTexture = assetManager.get("buttons/OB.png", Texture.class);
        greenBtnTexture = assetManager.get("buttons/GB.png", Texture.class);
        blueBtnTexture = assetManager.get("buttons/BB.png", Texture.class);
        pauseBtnTxtr = assetManager.get("buttons/pausebtn.png", Texture.class);

        // Creating button images.
        buttonBoard = new Image(buttonBoardTexture);
        redBtn = new Image(redBtnTexture);
        orangeBtn = new Image(orangeBtnTexture);
        greenBtn = new Image(greenBtnTexture);
        blueBtn = new Image(blueBtnTexture);
        pauseBtn = new Image(pauseBtnTxtr);

        // Setting button names.
        redBtn.setName("redBtn");
        orangeBtn.setName("orangeBtn");
        greenBtn.setName("greenBtn");
        blueBtn.setName("blueBtn");

        // Setting button positions.
        buttonBoard.setBounds(10, 20, 80, 45 * aspectRatio);
        redBtn.setBounds(14, 25, BUTTON_WIDTH, BUTTON_HEIGHT * aspectRatio);
        orangeBtn.setBounds(33, 25, BUTTON_WIDTH, BUTTON_HEIGHT * aspectRatio);
        greenBtn.setBounds(52, 25, BUTTON_WIDTH, BUTTON_HEIGHT * aspectRatio);
        blueBtn.setBounds(71, 25, BUTTON_WIDTH, BUTTON_HEIGHT * aspectRatio);
        pauseBtn.setBounds(GAME_WORLD_WIDTH - PAUSE_BUTTON_WIDTH - 2, GAME_WORLD_HEIGHT - PAUSE_BUTTON_HEIGHT * aspectRatio - 2,
                PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT * aspectRatio);

        // Adding click listeners to the buttons.
        redBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isPaused) {
                    wall.setDrawable(new SpriteDrawable(new Sprite(redWallTxtr)));
                    wallColor = "red";
                }
            }
        });
        orangeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isPaused) {
                    wall.setDrawable(new SpriteDrawable(new Sprite(orangeWallTxtr)));
                    wallColor = "orange";
                }
            }
        });
        greenBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isPaused) {
                    wall.setDrawable(new SpriteDrawable(new Sprite(greenWallTxtr)));
                    wallColor = "green";
                }
            }
        });
        blueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isPaused) {
                    wall.setDrawable(new SpriteDrawable(new Sprite(blueWallTxtr)));
                    wallColor = "blue";
                }
            }
        });
        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isPaused) {
                    pause();
                    showPauseDialog();
                }
            }
        });


        // Button group.
        buttonGroup = new Group();
        buttonGroup.addActor(buttonBoard);
        buttonGroup.addActor(redBtn);
        buttonGroup.addActor(orangeBtn);
        buttonGroup.addActor(greenBtn);
        buttonGroup.addActor(blueBtn);
        buttonGroup.addActor(pauseBtn);


        // ******************************WALL************************************************

        // Wall textures.
        redWallTxtr = assetManager.get("walls/RW.png", Texture.class);
        orangeWallTxtr = assetManager.get("walls/OW.png", Texture.class);
        greenWallTxtr = assetManager.get("walls/GW.png", Texture.class);
        blueWallTxtr = assetManager.get("walls/BW.png", Texture.class);

        // Create wall image.
        wall = new Image(redWallTxtr);

        // Setting wall attributes.
        wall.setBounds(5, 55, WALL_WIDTH, WALL_HEIGHT);
        wallColor = "red";


        // *****************************STARS************************************************

        // Star textures.
        redStarTxtr = assetManager.get("stars/RS.png", Texture.class);
        orangeStarTxtr = assetManager.get("stars/OS.png", Texture.class);
        greenStarTxtr = assetManager.get("stars/GS.png", Texture.class);
        blueStarTxtr = assetManager.get("stars/BS.png", Texture.class);

        // Set star speed.
        starSpeed = 10f;

        // Creating the star group.
        starGroup = new Group();

        // Create stars.
        createStars();


        // *********************BACKGROUND*****************************************************

        // Background group.
        backgroundGroup = new BackgroundGroup(assetManager, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, (int) (wall.getY() + WALL_HEIGHT));

        // Darken background image.
        darkBkgr = new Image((Texture) assetManager.get("background/darkbackg.png"));
        darkBkgr.setBounds(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);


        //**************************TEXT BUTTONS***********************************************

        // Creating the text buttons.
        skin.getFont("default-font").getData().setScale(0.07f, 0.07f);
        restartButton = new TextButton("Restart", skin, "blue-button");
        menuButton = new TextButton("Menu", skin, "red-button");
        resumeButton = new TextButton("Resume", skin, "orange-button");

        // Adding listeners to the buttons.
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                starGroupTime = 0;
                starGroup.clearChildren();
                if (gameOverDialog != null)
                    gameOverDialog.hide();
                if (pauseDialog != null) {
                    pauseDialog.hide();
                    pauseDialog = null;
                }
                score = 0;
                scoreLabel.setText(String.valueOf(score));
                isPaused = false;
                createStars();
                darkBkgr.remove();
            }
        });

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setWelcomeScreen();
            }
        });

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseDialog.hide(Actions.alpha(0f));
                pauseDialog = null;
                for (Actor s : starGroup.getChildren()) {

                    MoveByAction starMoveByAction = new MoveByAction();
                    starMoveByAction.setAmount(0, -1000);
                    starMoveByAction.setDuration(starSpeed);

                    if (s.getActions().size == 0) {
                        s.addAction(starMoveByAction);
                        Gdx.app.log("AKS", "REACHED");
                    }
                }
                isPaused = false;
                darkBkgr.remove();
            }
        });


        // *****************************STAGE & CAMERA & VIEWPORT***************************

        // Setting up camera and viewport.
        camera = new OrthographicCamera();
        viewport = new StretchViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, camera);
        viewport.apply();
        stage = new Stage(viewport);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        Gdx.input.setInputProcessor(stage);


        // ****************************SCORE**************************************************

        // Customizing the score labels.
        score = 0;
        topScore = preferences.getInteger("topScore", 0);

        // Score label.
        scoreLabel = new Label(String.valueOf(score), skin);
        scoreLabel.setBounds(GAME_WORLD_WIDTH / 2 - 10, GAME_WORLD_HEIGHT - 20 * aspectRatio,
                20, 20 * aspectRatio);
        scoreLabel.setFontScale(0.05f);
        scoreLabel.setAlignment(Align.center);

        // Top score label.
        topScoreLabel = new Label("Best: " + topScore, skin);
        topScoreLabel.setColor(Color.ORANGE);
        topScoreLabel.setBounds(3, GAME_WORLD_HEIGHT - 20 * aspectRatio,
                25, 20 * aspectRatio);
        topScoreLabel.setFontScale(0.05f);
        topScoreLabel.setAlignment(Align.left);


        // Label group.
        labelGroup = new Group();
        labelGroup.addActor(scoreLabel);
        labelGroup.addActor(topScoreLabel);


        // Add all the actors to the stage.
        stage.addActor(backgroundGroup);
        stage.addActor(buttonGroup);
        stage.addActor(wall);
        stage.addActor(starGroup);
        stage.addActor(labelGroup);

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();


        // *******************************FIRST TIME**************************************
        if (firstTime && !isPaused) {
            // Darker background.
            pause();
            stage.addActor(darkBkgr);

            final Dialog firstTimeDialog = new Dialog("", skin, "first-time");
            firstTimeDialog.setColor(1, 1, 1, 0);
            firstTimeDialog.show(stage);
            firstTimeDialog.setBounds(10, 70, 80, 100);
            firstTimeDialog.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    darkBkgr.remove();
                    starGroupTime = 0;
                    starGroup.clearChildren();
                    firstTimeDialog.hide();
                    isPaused = false;
                    createStars();
                    firstTime = false;
                    preferences.putBoolean("firstTime", false);
                    preferences.flush();
                }
            });
        }

        starGroupTime += delta;

        // Star touches wall.
        if (starGroup.getChildren().size > 0 && !isPaused) {
            final Star tempStar = (Star) starGroup.getChildren().get(0);
            if (tempStar.getY() <= wall.getY() + WALL_HEIGHT && tempStar.getColorName().equals(wallColor)) {
                starPop(tempStar);
            } else if (tempStar.getY() <= wall.getY() + WALL_HEIGHT * 1 / 4 && !tempStar.getColorName().equals(wallColor)) {

                // ****************************GAME OVER********************************************
                pause();
                if (sfxOn) {
                    wrongSound.play();
                }

                // Submit top score.
                game.getPlayServices().submitScore(topScore);

                // Show interstitial ad.
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.showInterstitialAd();
                    }
                }, 0.5f);

                // Dialog.
                showGameOverDialog();
            }
        }

        // Create new stars.
        if (!starGroup.hasChildren() && !isPaused && creatingStars) {
            creatingStars = false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    createStars();
                }
            }, 0.2f);
        }

        // Recreate background objects.
        backgroundGroup.recreateBackgroundObjects();

        stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        isPaused = true;
        for (Actor s : starGroup.getChildren()) {
            s.clearActions();
        }
    }

    @Override
    public void resume() {
        if (gameOverDialog == null && pauseDialog == null) {
            pause();
            showPauseDialog();
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        assetManager.dispose();
    }


    public int randomInt(int min, int max) {
        return random.nextInt((max + 1) - min) + min;
    }

    public Star randomStar(float y) {
        Star tempStar = null;
        switch (randomInt(1, 4)) {
            case 1:
                tempStar = new Star(redStarTxtr, "red", starSpeed);
                tempStar.setBounds(0, y, STAR_WIDTH, STAR_HEIGHT);
                break;
            case 2:
                tempStar = new Star(orangeStarTxtr, "orange", starSpeed);
                tempStar.setBounds(0, y, STAR_WIDTH, STAR_HEIGHT);
                break;
            case 3:
                tempStar = new Star(greenStarTxtr, "green", starSpeed);
                tempStar.setBounds(0, y, STAR_WIDTH, STAR_HEIGHT);
                break;
            case 4:
                tempStar = new Star(blueStarTxtr, "blue", starSpeed);
                tempStar.setBounds(0, y, STAR_WIDTH, STAR_HEIGHT);
                break;
        }

        if (tempStar != null) {
            switch (randomInt(1, 3)) {
                case 1:
                    tempStar.setX(GAME_WORLD_WIDTH / 4 * 1 - STAR_WIDTH / 2);
                    break;
                case 2:
                    tempStar.setX(GAME_WORLD_WIDTH / 2 - STAR_WIDTH / 2);
                    break;
                case 3:
                    tempStar.setX(GAME_WORLD_WIDTH / 4 * 3 - STAR_WIDTH / 2);
            }
        }
        return tempStar;
    }

    public void createStars() {
        // Setting the difficulty level based on score.
        int noOfNewStars;
        if (score < 5) {
            noOfNewStars = 1;
        } else if (score >= 5 && score < 15) {
            noOfNewStars = 2;
        } else if (score >= 15 && score < 30) {
            noOfNewStars = 3;
        } else if (score >= 30 && score < 50) {
            noOfNewStars = 4;
        } else if (score >= 50 && score < 75) {
            noOfNewStars = 5;
        } else if (score >= 75 && score < 100) {
            noOfNewStars = 5;
            starSpeed = 9.5f;
        } else if (score >= 100 && score < 125) {
            noOfNewStars = 5;
            starSpeed = 9f;
        } else if (score >= 125 && score < 150) {
            noOfNewStars = 5;
            starSpeed = 8.5f;
        } else if (score >= 150 && score < 175) {
            noOfNewStars = 5;
            starSpeed = 8f;
        } else if (score >= 175 && score < 200) {
            noOfNewStars = 5;
            starSpeed = 7.5f;
        } else if (score >= 200 && score < 225) {
            noOfNewStars = 5;
            starSpeed = 7f;
        } else if (score >= 225 && score < 250) {
            noOfNewStars = 5;
            starSpeed = 6.5f;
        } else if (score >= 250 && score < 275) {
            noOfNewStars = 5;
            starSpeed = 6f;
        } else if (score >= 275 && score < 300) {
            noOfNewStars = 5;
            starSpeed = 5.5f;
        } else {
            noOfNewStars = 5;
            starSpeed = 5f;
        }

        timeBetweenStarGroups = (starSpeed / 10) * noOfNewStars + 2f;

        for (int i = 1; i <= noOfNewStars; i++) {
            starGroup.addActor(randomStar(GAME_WORLD_HEIGHT + i * 50));
        }
    }

    public void starPop(Star tempStar) {
        tempStar.remove();
        score += 1;

        if (sfxOn)
            rightSound.play();

        if (score > topScore) {
            newHighscore = true;
            topScore = score;
            preferences.putInteger("topScore", topScore);
            preferences.flush();
            topScoreLabel.setText("Best: " + topScore);
        }
        scoreLabel.setText(String.valueOf(score));

        // Start the process of creating new stars.
        if (!starGroup.hasChildren()) {
            creatingStars = true;
        }
    }

    public void showGameOverDialog() {
        darkBkgr.clearActions();
        darkBkgr.setColor(1, 1, 1, 0);
        darkBkgr.addAction(new SequenceAction(
                Actions.delay(1f),
                Actions.alpha(1f, 0.5f)));
        stage.addActor(darkBkgr);

        gameOverDialog = new Dialog("", skin);
        gameOverDialog.setColor(1, 1, 1, 0);
        gameOverDialog.show(stage, new SequenceAction(
                Actions.delay(1f),
                Actions.alpha(1f, 0.5f)));
        gameOverDialog.setBounds(5, 45, 90, 100);
        if (newHighscore) {
            gameOverDialog.setHeight(110);
            gameOverDialog.add("New highscore!");
            gameOverDialog.row();
            newHighscore = false;
        }
        gameOverDialog.add("Your score");
        gameOverDialog.row();
        gameOverDialog.add("" + score).align(Align.center).padBottom(5);
        gameOverDialog.row();
        gameOverDialog.add(menuButton).width(80).height(20);
        gameOverDialog.row();
        gameOverDialog.add(restartButton).width(80).height(20).padBottom(10);
    }

    public void showPauseDialog() {
        stage.addActor(darkBkgr);
        pauseDialog = new Dialog("", skin);
        pauseDialog.show(stage, Actions.alpha(1f));
        pauseDialog.setBounds(5, 45, 90, 120);
        pauseDialog.add("Your score");
        pauseDialog.row();
        pauseDialog.add("" + score).align(Align.center).padBottom(5);
        pauseDialog.row();
        pauseDialog.add(resumeButton).width(80).height(20);
        pauseDialog.row();
        pauseDialog.add(menuButton).width(80).height(20);
        pauseDialog.row();
        pauseDialog.add(restartButton).width(80).height(20).padBottom(10);
    }
}
