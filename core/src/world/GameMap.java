package world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObjects;

public abstract class GameMap {


    // functions to retrieve data of the game map
    public abstract MapObjects getWallObject();
    public abstract void render(OrthographicCamera camera);
    public abstract void dispose();
    public abstract int getMapWidth();
    public abstract int getMapHeight();

}
