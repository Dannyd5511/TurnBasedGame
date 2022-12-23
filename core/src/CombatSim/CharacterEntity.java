package CombatSim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.sun.org.apache.xpath.internal.operations.Bool;
import sun.jvm.hotspot.utilities.Assert;

import javax.xml.stream.events.Characters;

public class CharacterEntity {

    public enum CharacterState
    {
        STATE_IDLE,
        STATE_ATTACK,
        STATE_HIT,
        STATE_TAUNT
    }

    private static final int INVALID_ATTACK = -1;

    private Animation<TextureRegion> m_idleAnim;
    private Animation<TextureRegion> m_attackAnim;
    private Animation<TextureRegion> m_tauntAnim;

    public int m_currentHealth;
    public int m_currentMana;

    public boolean m_flipY = false;

    public Vector2 m_position = new Vector2(0.0f, 0.0f);

    private int m_playerStats[];
    private Attack m_attacks[];

    private int m_currAttackIdx;

    private SpriteBatch m_spriteBatch;

    private float m_currAnimTime = 0.0f;
    
    private CharacterState m_state;

    public CharacterEntity(int[] playerStats, Attack[] attacks, SpriteBatch spriteBatch, Animation<TextureRegion> idleAnim, Animation<TextureRegion> attackAnim, Animation<TextureRegion> tauntAnim ) {
        m_state = CharacterState.STATE_IDLE;
        m_idleAnim = idleAnim;
        m_attackAnim = attackAnim;
        m_tauntAnim = tauntAnim;

        m_spriteBatch = spriteBatch;
        //check length of passed in playerStats
        assert playerStats.length == Stats.STAT_MAX.ordinal();
        m_playerStats = playerStats;
        m_attacks = attacks;
        m_currentHealth = grabStat(Stats.STAT_MAX_HEALTH);
    }

    public void changeState(CharacterState newState)
    {
        if(m_state != newState) {
            m_currAnimTime = 0.0f;
            m_state = newState;
            switch(newState)
            {
                case STATE_ATTACK:
                    assert m_currAttackIdx != INVALID_ATTACK;
                    break;
                default:
                    break;

            }
        }
    }

    public void setCurrentAttack(int attackIdx)
    {
        assert attackIdx > 0 && attackIdx < m_attacks.length;
        m_currAttackIdx = attackIdx;
    }

    public int getCurrentAttack()
    {
        return m_currAttackIdx;
    }

    public void render(float deltaTime)
    {
        m_currAnimTime += deltaTime;
        TextureRegion currFrame = null;
        switch(m_state)
        {
            case STATE_IDLE:
                currFrame = m_idleAnim.getKeyFrame(m_currAnimTime, true);
                break;
            case STATE_ATTACK:
                currFrame = m_attacks[m_currAttackIdx].getCurrAttackFrame(m_currAnimTime);
                break;
            default:
                assert false;
                currFrame = m_idleAnim.getKeyFrame(m_currAnimTime, true);
                break;
        }
        m_spriteBatch.draw(currFrame, m_flipY ?  m_position.x + currFrame.getRegionWidth() : m_position.x, m_position.y, m_flipY ? -currFrame.getRegionWidth()  : currFrame.getRegionWidth() , currFrame.getRegionHeight());
    }

    public Attack[] getAttacks()
    {
        return m_attacks;
    }

    public int grabStat(Stats stat)
    {
        return m_playerStats[stat.ordinal()];
    }

    public void takeDamage(int damageTaken)
    {
        m_currentHealth -= damageTaken;
    }

    public float getCurrentHealthPct()
    {
        return m_currentHealth / grabStat(Stats.STAT_MAX_HEALTH);
    }

}
