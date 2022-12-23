package Screens.game;

import Screens.MainGameLoop.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainMenuScreen implements Screen {
	// variables
	MyGame game;
	Texture playButtonNormal;
	Texture playButtonHovered;
	Texture quitButtonNormal;
	Texture quitButtonHovered;
	Texture backgroundTexture;
	TextureRegion mainBackground;

	//
	// constructor for main menu
	//
	public MainMenuScreen (MyGame game) {
		this.game = game;
		this.playButtonNormal = new Texture("play-green.PNG");
		this.playButtonHovered = new Texture("play-red.PNG");
		this.quitButtonNormal = new Texture("quit-inactive.PNG");
		this.quitButtonHovered = new Texture("quit-active.PNG");
		this.backgroundTexture = new Texture("menuback.PNG");
		this.mainBackground = new TextureRegion(backgroundTexture, 0,0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(mainBackground, 0, 0);

		//draw red button for hover over play button
		if (Gdx.input.getX() > Constants.GAME_WIDTH/2 - 50 && Gdx.input.getX() < Constants.GAME_WIDTH/2 - 50 + Constants.BUTTON_WIDTH
				&&    Gdx.input.getY() < Constants.GAME_HEIGHT* 0.5  && Gdx.input.getY() > Constants.GAME_HEIGHT* 0.5 - Constants.BUTTON_HEIGHT) {
			game.batch.draw(playButtonHovered, Constants.GAME_WIDTH/2 - 50,Constants.GAME_HEIGHT/2, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
			if(Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen((Screen) new GameScreen(game));
			}
		}
		else {
			game.batch.draw(playButtonNormal, Constants.GAME_WIDTH/2 - 50,Constants.GAME_HEIGHT/2, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
		}


		//draw active button for hover over quit button
		if (Gdx.input.getX() > Constants.GAME_WIDTH/2 - 50 && Gdx.input.getX() < Constants.GAME_WIDTH/2 - 50 + Constants.BUTTON_WIDTH
				&&    Gdx.input.getY() < Constants.GAME_HEIGHT* 0.75  && Gdx.input.getY() > Constants.GAME_HEIGHT* 0.75 - Constants.BUTTON_HEIGHT) {
			game.batch.draw(quitButtonHovered, Constants.GAME_WIDTH/2 - 50,Constants.GAME_HEIGHT/4, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
			if(Gdx.input.isTouched()) {
				this.dispose();
				game.dispose();
				System.exit(0);
			}
		}
		else {
			game.batch.draw(quitButtonNormal, Constants.GAME_WIDTH/2 - 50,Constants.GAME_HEIGHT/4, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
		}
		
		
		game.batch.end();
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
