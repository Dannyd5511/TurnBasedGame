package Screens.MainGameLoop;

import Screens.game.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGame extends Game {

    public Batch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }


    @Override
    public void dispose () {
        batch.dispose();
    }

}
