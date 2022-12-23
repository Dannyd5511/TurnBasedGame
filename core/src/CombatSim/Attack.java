package CombatSim;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Attack {
    private String m_attackName;
    private Animation<TextureRegion> m_attackAnim;

    private AttackParams[] m_params;

    public Attack()
    {
        m_attackName = "INVALID";
        m_attackAnim = null;
    }

    public Attack(String attackName, Animation<TextureRegion> attackAnim, AttackParams[] params)
    {
        m_attackName = attackName;
        m_attackAnim = attackAnim;
        m_params = params;
    }

    public String getName()
    {
        return m_attackName;
    }

    public TextureRegion getCurrAttackFrame(float currAnimTime)
    {
        return m_attackAnim.getKeyFrame(currAnimTime, true);
    }


    void applyAttack(CharacterEntity attacker, CharacterEntity enemy)
    {
        for(int paramIdx = 0; paramIdx < m_params.length; paramIdx++)
        {
            switch(m_params[paramIdx].m_type)
            {
                case ATTACK_FLAT:
                {
                    //calculate attack
                    int attackVal = (int)(m_params[paramIdx].m_attackPct * attacker.grabStat(Stats.STAT_ATTACK));
                    //calculate percentage of attack being mitigated based on enemy's defense
                    float defenseMitigationPct =  (enemy.grabStat(Stats.STAT_DEFENSE)/CombatDefines.MAX_STAT_VAL);
                    //clamp the max amount of damage mitigated
                    if(defenseMitigationPct > CombatDefines.MAX_DMG_MITIGATION)
                    {
                        defenseMitigationPct = CombatDefines.MAX_DMG_MITIGATION;
                    }
                    int damageReduction = (int)(defenseMitigationPct * attackVal);
                    int damageTaken = attackVal - damageReduction;

                    //should always be more than 0 but just make sure
                    assert  damageTaken >= 0;

                    enemy.takeDamage(damageTaken);
                    break;
                }
                default:
                {
                    assert false;
                    break;
                }
            }
        }
    }
}
