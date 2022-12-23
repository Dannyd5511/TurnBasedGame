package Screens.game;

import Screens.MainGameLoop.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class OldGameScreen implements Screen {

    private final MyGame game;
    Texture img;
    SpriteBatch coins;
    Stage stage;
    SpriteBatch batch;

    public boolean objectRange(double left, double right, double top, double bottom) {
        if (Gdx.input.getX() > left && Gdx.input.getX() < right && Gdx.input.getY() < top && Gdx.input.getY() > bottom) {
            return true;
        } else {
            return false;
        }
    }

    public OldGameScreen(MyGame game) {
        this.game = game;
        this.img= new Texture("gameback.png");
    }

    public void show() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        coins = new SpriteBatch();

    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#render(float)
     */
    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(img, 0, 0);
        batch.begin();
        stage.draw();
        game.batch.end();
        batch.end();
    }


    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
