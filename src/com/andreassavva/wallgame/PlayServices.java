package com.andreassavva.wallgame;

/**
 * Created by andreassavva on 2016-06-11.
 */
public interface PlayServices {

    void signIn();

    void signOut();

    void rateGame();

    void unlockAchievementFromHeroToZero();

    void unlockAchievementStarstruck();

    void unlockAchievementWrittenInTheStars();

    void unlockAchievementYouAreAStar();

    void unlockAchievementRockstar();

    void submitScore(int highScore);

    void showAchievements();

    void showScore();

    boolean isSignedIn();

}
