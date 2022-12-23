package world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class TiledGameMap extends GameMap {

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    TiledMapTileLayer mainLayer;
    MapLayer wallLayer;
    MapObjects objects;

    private int tileSize;

    public TiledGameMap () {
        initialize();
    }

    // Initializes the game map and retrieves information of "Walls" layer and the whole map
    void initialize() {

        tiledMap = new TmxMapLoader().load("Caves-Start-Room.tmx");
        wallLayer =  tiledMap.getLayers().get("Walls");
        mainLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        tileSize = mainLayer.getTileWidth();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        objects =  wallLayer.getObjects();
    }

    @Override
    public MapObjects getWallObject() {
        return objects;
    }

    @Override
    public int getMapWidth() {
        return mainLayer.getWidth() * tileSize;
    }

    @Override
    public int getMapHeight() {
        return mainLayer.getHeight() * tileSize;
    }

    @Override
    public void render(OrthographicCamera camera) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }


    @Override
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }



}
