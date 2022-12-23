package Enemy;

import CombatSim.Attack;
import CombatSim.AttackParams;
import CombatSim.AttackType;
import CombatSim.CharacterEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static java.lang.Math.*;

public class Enemy {

    // Mainly information for other classes to pick up
    private CharacterEntity combatForm;
    public Sprite mapSprite;

    // Detection is whether or not the enemy has spotted the player
    private boolean detection = false;

    public Enemy(SpriteBatch batch, String name){
        // Add more to if then statement for more enemies
        if (name.equals("rogue")){
            createRogue(batch);
        }
    }

    private void createRogue(SpriteBatch batch) {
        // Setup to make the combatForm line shorter and allow for easier changes
        int[] stats = {50, 0, 20, 5, 5, 0, 6};
        Animation<TextureRegion> slashAnimation = loadAnimation("badlogic.jpg");
        Attack[] attacks = {new Attack("Slash", slashAnimation,
                new AttackParams[]{new AttackParams(AttackType.ATTACK_FLAT, 4.0F)})};
        Animation<TextureRegion> idleAnimation = loadAnimation("character_0.png");
        Animation<TextureRegion> attackAnimation = loadAnimation("Attack.png");
        Animation<TextureRegion> tauntAnimation = loadAnimation("Taunt.png");

        combatForm = new CharacterEntity(stats, attacks, batch, idleAnimation, attackAnimation, tauntAnimation);

        Texture tex = new Texture(Gdx.files.internal("RogueImage.png"));
        mapSprite = new Sprite(tex, 0, 0, 64, 64);
    }

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

    public CharacterEntity getCharacterEntity() {return combatForm;}
    // For the tests
    public boolean getDetection() {return detection;}
    public void setDetection() {detection = true;}

    // Checks if the player is close enough to get seen
    public void checkDetection(Sprite player){
        float xDistance = player.getX() - mapSprite.getX();
        float yDistance = player.getY() - mapSprite.getY();
        float distance = (float) sqrt(pow(xDistance, 2) + pow(yDistance, 2));

        if (distance < 200){
            detection = true;
        }
    }

    // Moves the enemy towards the hero at a set pace
    public void move(Sprite player){
        float xDistance = player.getX() - mapSprite.getX();
        float yDistance = player.getY() - mapSprite.getY();
        float distance = (float) sqrt(pow(xDistance, 2) + pow(yDistance, 2));
        // The 50 is the speed on the map
        float ratio = (float) 50 / distance;

        // Ratio and (blank)distance makes sure that the enemy is taking the most direct route to the player
        mapSprite.setPosition(mapSprite.getX() + xDistance * ratio,mapSprite.getY() + yDistance * ratio);
    }
}
