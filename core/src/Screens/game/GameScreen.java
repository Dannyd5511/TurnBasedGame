package Screens.game;

import Screens.MainGameLoop.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import world.GameMap;
import world.TiledGameMap;


public class GameScreen implements Screen {

    private final MyGame game;
    GameMap gameMap;
    OrthographicCamera cam;
    SpriteBatch batch;
    ShapeRenderer visualHitbox;
    Texture player;
    Texture img;
    SpriteBatch coins;
    Stage stage;
    Rectangle playerHitBox;

    public boolean objectRange(double left, double right, double top, double bottom) {
        if (Gdx.input.getX() > left && Gdx.input.getX() < right && Gdx.input.getY() < top && Gdx.input.getY() > bottom) {
            return true;
        } else {
            return false;
        }
    }
    public GameScreen(MyGame game) {
        this.game = game;
        this.img= new Texture("gameback.png");
    }

    // player position and speed variables
    private float positionX = 200f, prevX;
    private float positionY = 200f, prevY;

    // player hitbox information
    private float hitboxWidth = 13f; // Right Y
    private float hitBoxHeight = 15f; // Top X
    private float hitBoxX = 12f; // Left Y
    private float hitboxY = 7f; // Moves Bottom X up


    //Handles the players movement. (WASD)
    void playerMovement() {

        float playerSpeed = 100f;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            // System.out.println("Player is moving up");
            prevY = positionY;
            positionY += Gdx.graphics.getDeltaTime() * playerSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            // System.out.println("Player is moving left");
            prevX = positionX;
            positionX -= Gdx.graphics.getDeltaTime()  * playerSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            // System.out.println("Player is moving down");
            prevY = positionY;
            positionY -= Gdx.graphics.getDeltaTime()  * playerSpeed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            // System.out.println("Player is moving right");
            prevX = positionX;
            positionX += Gdx.graphics.getDeltaTime()  * playerSpeed;
        }
    }

    boolean isCollidedWithRectangles() {

        // iterating through all the rectangles on the "Wall" layer and checking for collision between the player and the rectangles
        for (RectangleMapObject wall : gameMap.getWallObject().getByType(RectangleMapObject.class)) {

            Rectangle r = wall.getRectangle();

            // when player collides with any rectangle on the "Wall" layer
            if (Intersector.overlaps(r, playerHitBox)) {
                return true;
            }

        }

        return false;
    }

    public void show() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        coins = new SpriteBatch();

        player = new Texture("idle.png");
        playerHitBox = new Rectangle(positionX + hitBoxX,positionY + hitboxY,hitboxWidth,hitBoxHeight);
        batch = new SpriteBatch();
        visualHitbox = new ShapeRenderer();
        gameMap = new TiledGameMap();
        cam = new OrthographicCamera();

        cam.setToOrtho(false, gameMap.getMapWidth(), gameMap.getMapHeight());
        cam.update();

        prevX = 200;
        prevY = 120;

    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        Gdx.gl.glClearColor(1,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // renders map of the game
        gameMap.render(cam);

        batch.begin();
        batch.draw(player,positionX,positionY, 40,40);

        // checks for collision between the player and the invisible rectangles from tilemap
        if(isCollidedWithRectangles()) {
            positionX = prevX;
            positionY = prevY;
        }

        // handles player movement
        playerMovement();

        // redraws hitbox
        playerHitBox = new Rectangle(positionX + hitBoxX,positionY + hitboxY,hitboxWidth,hitBoxHeight);

        batch.end();

        // draws hitbox
        visualHitbox.begin(ShapeRenderer.ShapeType.Line);
        visualHitbox.rect(positionX + hitBoxX,positionY + hitboxY,hitboxWidth,hitBoxHeight);
        visualHitbox.end();


    }

    @Override
    public void resize(int width, int height) {

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



    public void dispose () {
        batch.dispose();
        visualHitbox.dispose();
        player.dispose();
        gameMap.dispose();

    }
}
