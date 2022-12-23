package CombatSim;
/*
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import Screens.CombatScreen;
import java.util.Random;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class CombatState {

    private enum State
    {
        STATE_NONE,
        STATE_START_OF_BATTLE,
        STATE_ATTACK_SELECTION,
        STATE_ATTACKING,
        STATE_END_OF_BATTLE
    }

    private final int MAX_COMBAT_ENTS = 2;

    private CharacterEntity m_player;
    private CharacterEntity m_enemy;

    //Start of battle vars
    final private float m_totalTimeToStart = 2.0f;
    private float m_currentTimeStarting = 0.0f;

    Vector2 m_playerPos = new Vector2(50, 50);
    Vector2 m_enemyPos = new Vector2(250, 50);

    ProgressBar m_playerHealth = null;
    ProgressBar m_enemyHealth = null;

    private State m_state;

    private CombatScreen m_game;

    private int m_currentAttacker = 0;
    private CharacterEntity m_attackOrder[] = new  CharacterEntity[MAX_COMBAT_ENTS];

    public CombatState(CharacterEntity player, CharacterEntity enemy, CombatScreen game)
    {
        m_game = game;
        m_state = State.STATE_NONE;
        m_player = player;
        m_enemy = enemy;

        m_player.m_flipY = false;
        m_enemy.m_flipY = true;

        m_playerHealth = new ProgressBar(0.0f, 1.0f, 0.1f, false, m_game.m_skin);
        m_enemyHealth = new ProgressBar(0.0f, 1.0f, 0.1f, false, m_game.m_skin);

        m_player.m_position = new Vector2(0.0f, 0.0f);
        m_enemy.m_position = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        transitionState(State.STATE_START_OF_BATTLE);
    }

    private void AttackChooserEnemy()
    {
        Random rand = new Random();
        m_enemy.setCurrentAttack(rand.nextInt(m_enemy.getAttacks().length));
    }

    private void calculateAttackOrder()
    {
        if(m_player.grabStat(Stats.STAT_SPEED) > m_enemy.grabStat(Stats.STAT_SPEED))
        {
            m_attackOrder[0] = m_player;
            m_attackOrder[1] = m_enemy;
        }
        else if(m_enemy.grabStat(Stats.STAT_SPEED) > m_player.grabStat(Stats.STAT_SPEED))
        {
            m_attackOrder[0] = m_enemy;
            m_attackOrder[1] = m_player;
        }
        //speed tie choose random
        else
        {
            Random rand = new Random();
            if(rand.nextInt(2) == 0)
            {
                m_attackOrder[0] = m_player;
                m_attackOrder[1] = m_enemy;
            }
            else
            {
                m_attackOrder[0] = m_enemy;
                m_attackOrder[1] = m_player;
            }
        }
    }

    private AttackResult applyAttacks()
    {
        Attack playerAttackChosen = m_player.getAttacks()[m_player.getCurrentAttack()];
        Attack enemyAttackChosen = m_enemy.getAttacks()[m_player.getCurrentAttack()];
        if(m_player.grabStat(Stats.STAT_SPEED) > m_enemy.grabStat(Stats.STAT_SPEED))
        {
            playerAttackChosen.applyAttack(m_player, m_enemy);

            if(m_enemy.m_currentHealth <= 0)
            {
                return AttackResult.PLAYER_WON;
            }

            enemyAttackChosen.applyAttack(m_enemy, m_player);

            if(m_player.m_currentHealth <= 0)
            {
                return AttackResult.ENEMY_WON;
            }
        }
        else
        {
            enemyAttackChosen.applyAttack(m_enemy, m_player);
            if(m_player.m_currentHealth <= 0)
            {
                return AttackResult.ENEMY_WON;
            }

            playerAttackChosen.applyAttack(m_player, m_enemy);
            if(m_enemy.m_currentHealth <= 0)
            {
                return AttackResult.PLAYER_WON;
            }
        }

        return AttackResult.NOT_DONE;
    }

    private void transitionState(State newState) {
        if (newState != m_state) {
            m_state = newState;
            setupUI();
            switch (m_state) {
                case STATE_START_OF_BATTLE:
//                    m_player.changeState(CharacterEntity.CharacterState.STATE_IDLE);
//                    m_enemy.changeState(CharacterEntity.CharacterState.STATE_IDLE);
//                    break;
                case STATE_ATTACK_SELECTION:
                    m_player.changeState(CharacterEntity.CharacterState.STATE_IDLE);
                    m_enemy.changeState(CharacterEntity.CharacterState.STATE_IDLE);
                    break;
                case STATE_ATTACKING:
                    m_currentAttacker = 0;
                    calculateAttackOrder();
                    break;
                case STATE_END_OF_BATTLE:
                    break;
                default:
                    assert false;
                    break;
            }
        }
    }

    private Vector2 interpVector2(Vector2 current, Vector2 end, float pct)
    {
        float x = MathUtils.lerp(current.x, end.x, pct);
        float y = MathUtils.lerp(end.y, end.y, pct);
        return new Vector2(x, y);
    }

    private Boolean introduceCharacters(float timeStep)
    {
        float height = Gdx.graphics.getHeight() / 2.0f;
        float leftX = Gdx.graphics.getWidth() / 3.0f;
        float rightX = Gdx.graphics.getWidth() - leftX;

        Vector2 endPosPlayer = new Vector2(leftX, height);
        Vector2 endPosEnemy = new Vector2(rightX, height);

        if(m_currentTimeStarting < m_totalTimeToStart)
        {
            m_player.m_position = interpVector2(m_player.m_position, endPosPlayer, m_currentTimeStarting/m_totalTimeToStart);
            m_enemy.m_position = interpVector2(m_enemy.m_position, endPosEnemy, m_currentTimeStarting/m_totalTimeToStart);
            m_currentTimeStarting += timeStep;
        }
        else
        {
            return true;
        }

        return false;
    }


    class ButtonChangeListener extends ChangeListener
    {

        private CharacterEntity m_player;
        private int m_attackIdx;
        private CombatState m_state;

        ButtonChangeListener(int attackIdx, CharacterEntity player, CombatState state)
        {
            m_attackIdx = attackIdx;
            m_state = state;
            m_player = player;
        }

        @Override
        public void changed (ChangeListener.ChangeEvent event, Actor actor) {
            System.out.println("Clicked " + m_player.getAttacks()[m_attackIdx].getName());
            m_player.setCurrentAttack(m_attackIdx);
            m_state.AttackChooserEnemy();
            m_state.transitionState(State.STATE_ATTACKING);
            currentAttackingTime = 0.0f;
        }
    }

    float attackingTime = 1.0f;
    float currentAttackingTime = 0.0f;

    public void setupUI()
    {
        Table table = new Table();
        table.setDebug(true);
        table.setFillParent(true);
        m_game.m_stage.clear();
        switch(m_state)
        {
            case STATE_START_OF_BATTLE:
                Label battleStartingText = new Label("Battle Starting!", m_game.m_skin);
                table.center();
                table.add(battleStartingText).padTop(50.0f);
                break;
            case STATE_ATTACK_SELECTION:
                Attack[] attacks = m_player.getAttacks();
                for(int attackIdx = 0; attackIdx < attacks.length; attackIdx++)
                {
                    Attack attack = attacks[attackIdx];
                    final TextButton button = new TextButton(attack.getName(), m_game.m_skin, "default");
                    ChangeListener listener = new ButtonChangeListener(attackIdx, m_player, this);
                    button.addListener(listener);
                    table.bottom().left();
                    table.add(button).pad(30.0f).width(50.0f).height(50.0f);
                }
//                break;
            case STATE_ATTACKING:
                m_playerHealth.setPosition(m_player.m_position.x - m_playerHealth.getWidth() / 2.0f, m_player.m_position.y + 50.0f);
                m_enemyHealth.setPosition(m_enemy.m_position.x - m_enemyHealth.getWidth() / 2.0f, m_enemy.m_position.y + 50.0f);
                m_game.m_stage.addActor(m_playerHealth);
                m_game.m_stage.addActor(m_enemyHealth);
                break;
            case STATE_END_OF_BATTLE:
                break;
            default:
                break;
        }
        m_game.m_stage.addActor(table);
    }

    public AttackResult update(float timeStep)
    {
        //update player health bars
        {
            m_enemyHealth.setValue(m_enemy.m_currentHealth/ m_enemy.grabStat(Stats.STAT_MAX_HEALTH));
            m_playerHealth.setValue(m_player.m_currentHealth/ m_player.grabStat(Stats.STAT_MAX_HEALTH));
        }

        switch(m_state)
        {
            case STATE_START_OF_BATTLE:
                if(introduceCharacters(timeStep))
                {
                    transitionState(State.STATE_ATTACK_SELECTION);
                }
                break;
            case STATE_ATTACK_SELECTION:
                //handled in UI callback
                break;
            case STATE_ATTACKING:
                if(m_currentAttacker == MAX_COMBAT_ENTS)
                {
                    transitionState(State.STATE_ATTACK_SELECTION);
                }
                else
                {
                    CharacterEntity currentAttacker = m_attackOrder[m_currentAttacker];
                    CharacterEntity currentReciever = m_attackOrder[m_currentAttacker ^ 1];
                    currentAttacker.changeState(CharacterEntity.CharacterState.STATE_ATTACK);
                    currentReciever.changeState(CharacterEntity.CharacterState.STATE_HIT);
                    if(currentAttackingTime > attackingTime)
                    {
                        currentAttacker.getAttacks()[currentAttacker.getCurrentAttack()].applyAttack(currentAttacker, currentReciever);
                        m_currentAttacker++;
                        currentAttackingTime = 0.0f;
                    }
                    currentAttackingTime += timeStep;
                }
                break;
            case STATE_END_OF_BATTLE:
                break;
        }

        return AttackResult.NOT_DONE;
    }
}
*/