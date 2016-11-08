package com.andreassavva.wallgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by andreassavva on 2016-06-03.
 */
public class Star extends Image {

    private String colorName;

    public Star(Texture texture, String colorName, float starSpeed) {
        super(texture);
        this.colorName = colorName;
        // Star move to action.
        MoveByAction starMoveByAction = new MoveByAction();
        starMoveByAction.setAmount(0, -1000);
        starMoveByAction.setDuration(starSpeed);
        this.addAction(starMoveByAction);
    }

    public String getColorName() {
        return colorName;
    }
}
