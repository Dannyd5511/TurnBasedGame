package Screens;

import CombatSim.*;
import Screens.game.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



/*public class CombatScreen extends Game {
	SpriteBatch batch;

	CombatState combatState = null;

	CharacterEntity m_player = null;
	CharacterEntity m_enemy = null;

	public Stage m_stage;
	public Skin m_skin;

	public OrthographicCamera m_camera;
	ScreenViewport m_screenViewport = null;

	public int Width = 800;
	public int Height = 600;

	public Animation<TextureRegion> loadAnimation(String animationPath)
	{
		Texture sheet = new Texture(Gdx.files.internal(animationPath));
		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(sheet,
				sheet.getWidth() / 10,
				sheet.getHeight() / 1);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] frames = new TextureRegion[10 * 1];
		int index = 0;
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 10; j++) {
				frames[index++] = tmp[i][j];
			}
		}

		return new Animation<TextureRegion>(.1f, frames);
	}

	private void intializeCharacters()
	{
		AttackParams attackParam = new AttackParams(AttackType.ATTACK_FLAT, 0.5f);
		AttackParams[] attackParams = {attackParam};

		Attack attack = new Attack("Strike", loadAnimation("Attack.png"), attackParams);
		Attack strong = new Attack("Strong", loadAnimation("Attack.png"), attackParams);
		Attack taunt = new Attack("Taunt", loadAnimation("Taunt.png"),attackParams);
		Attack[] attacks = {attack, strong, taunt};


		int playerStats[] = new int[Stats.STAT_MAX.ordinal()];
		for(int i = 0; i < playerStats.length; i++)
		{
			playerStats[i] = 255;
		}

		// Initialize the Animation with the frame interval and array of frames
		Animation<TextureRegion> idleAnimation = loadAnimation("character_0.png");
		Animation<TextureRegion> attackAnimation =  loadAnimation("Attack.png");
		Animation<TextureRegion> tauntAnimation = loadAnimation("Taunt.png");

		m_player = new CharacterEntity(playerStats, attacks, batch, idleAnimation, attackAnimation, tauntAnimation);
		m_enemy = new CharacterEntity(playerStats, attacks, batch, idleAnimation, attackAnimation, tauntAnimation);

		combatState = new CombatState(m_player, m_enemy, this);
	}


	@Override
	public void create () {
		m_skin = new Skin(Gdx.files.internal("uiskin.json"));
		m_screenViewport = new ScreenViewport();
		m_stage = new Stage(m_screenViewport);

		batch = new SpriteBatch();

		m_camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		m_camera.position.set(new Vector2(0, 0), 0);
		m_camera.update();
		intializeCharacters();

		Gdx.input.setInputProcessor(m_stage);

		setScreen((Screen) new GameScreen(this));

	}

	@Override
	public void render () {

		float deltaTime = Gdx.graphics.getDeltaTime();
		if(combatState != null)
		{
			combatState.update(deltaTime);
		}

		m_camera.update();
		ScreenUtils.clear(0, 0, 0, 0);

		//draw Sprites
		batch.begin();
		m_player.render(deltaTime);
		m_enemy.render(deltaTime);
		batch.end();

		//draw UI
		m_stage.act(deltaTime);
		m_stage.draw();

		//delegate the render method to whatever screen is currently active
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		m_screenViewport.update(width, height);

	}
}

*/