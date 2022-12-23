package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Entity extends Sprite {

    public World world;
    public Body b2body;

    protected int healthPoints;
    protected int attackDamage;
    protected int defensePoints;
    protected int speed;

    //Constructor
    public Entity(World world) {
        this.world = world;
        defineEntity();
        BodyDef bdef = new BodyDef();
        bdef.position.set(50,50);
        bdef.type = BodyDef.BodyType.DynamicBody;

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void defineEntity(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32, 32);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

}

