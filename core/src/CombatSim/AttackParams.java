package CombatSim;

public class AttackParams {
    AttackType m_type;

    public float m_attackPct;

    public AttackParams(AttackType type, float attackPct)
    {
        m_type = type;
        m_attackPct = attackPct;
    }
}
