package SoundEffects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class soundEffect {

    private Sound sound;

    /**
     * Must declare an id to get the setVolume and setLooping
     * functions to work properly.
     */
    long id = sound.play(1.0f);

    /**
     * Each sound uses private encapsulation because the objects only
     * need to be accessed within this class. We can use the Gdx library
     * to create instances of Sound that will depend on .mp3 files in
     * the assets[main] folder.
     */
    private Sound selectButtonSound = Gdx.audio.newSound(Gdx.files.internal("buttonPressed.mp3"));
    private Sound footsteps = Gdx.audio.newSound(Gdx.files.internal("footstep.mp3"));
    private Sound fightInitiatedSound = Gdx.audio.newSound(Gdx.files.internal("fightInitiated.mp3"));
    private Sound enemyDeathSound = Gdx.audio.newSound(Gdx.files.internal("enemyDeath.mp3"));
    private Sound playerDeathSound = Gdx.audio.newSound(Gdx.files.internal("playerDeath.mp3"));
    private Sound roomCompletedSound = Gdx.audio.newSound(Gdx.files.internal("roomComplete.mp3"));
    private Sound itemObtained = Gdx.audio.newSound(Gdx.files.internal("itemObtained.mp3"));

    /**
     * The dispose function is a publicly encapsulated
     * garbage-collector function which is necessary for
     * clearing memory used for playing the sounds.
     * It is meant called after every sound plays
     */
    public void dispose(){
        sound.dispose();
    }

    /**
     * The playSelectSound function is publicly encapsulated so
     * that it may be called from other classes when an event
     * occurs where a player presses a button. This may happen
     * from anywhere including the main menu, game over menu,
     * pause menu, and shop menu. This function sets looping to
     * false because we only want the sound to play once per
     * stimulus. We then call the 'play()' function which is
     * a built-in libGDX function specifically for playing sounds.
     */
    public void playSelectSound(){
        selectButtonSound.setVolume(id,0.2f);
        selectButtonSound.setLooping(id, false);
        selectButtonSound.play();
        selectButtonSound.dispose();
    }

    /**
     * The playFootsteps function is publicly encapsulated so
     * that it may be called from other classes when an event
     * occurs where a player is moving throughout the world.
     * The footstep sound is meant to play in a loop while and
     * only while the player is pressing the button to move. As
     * soon as the button is release and the player sprite is
     * no longer moving, the footstep sounds will cease. This
     * function sets looping to true because we want the sound
     * to loop. We then call the 'play()' function which is a
     * built-in libGDX function specifically for playing sounds.
     */
    public void playFootsteps(){
        footsteps.setVolume(id,0.2f);
        footsteps.setLooping(id, true);
        if(Gdx.input.justTouched()){
            footsteps.play();
        }
        footsteps.dispose();
    }

    /**
     * The playFightSound function is publicly encapsulated so
     * that it may be called from other classes when an event
     * occurs where a player encounters an enemy in the world.
     * The fightInitiatedSound is meant to play only once right
     * before the battle screen loads. This function sets looping
     * to false because we onlt want to play the sound once per
     * stimulus. We then call the 'play()' function which is a
     * built-in libGDX function specifically for playing sounds.
     */
    public void playFightSound(){
        fightInitiatedSound.setVolume(id,0.5f);
        fightInitiatedSound.setLooping(id, false);
        fightInitiatedSound.play();
        fightInitiatedSound.dispose();
    }

    /**
     * The playEnemyDeath function is publicly encapsulated so
     * that it may be called from other classes when an event
     * occurs where a player an enemy in the battle screen.
     * The enemyDeathSound is meant to play only once right
     * before the battle screen is ended and the player returns
     * to the overworld. This function sets looping to false
     * because we only want to play the sound once per stimulus
     * event. We then call the 'play()' function which is
     * a built-in libGDX function specifically for playing sounds.
     */
    public void playEnemyDeathSound(){
        enemyDeathSound.setVolume(id,0.4f);
        enemyDeathSound.setLooping(id, false);
        enemyDeathSound.play();
        enemyDeathSound.dispose();
    }

    /**
     * The playPlayerDeathSound function is publicly encapsulated so
     * that it may be called from other classes when an event
     * occurs where a player dies while in the world or in battle.
     * The playerDeathSound is meant to play only once right
     * before the game over screen loads. This function sets looping
     * to false because we only want to play the sound once
     * per stimulus. We then call the 'play()' function which is
     * a built-in libGDX function specifically for playing sounds.
     */
    public void playPlayerDeathSound(){
        playerDeathSound.setVolume(id,0.5f);
        playerDeathSound.setLooping(id, false);
        playerDeathSound.play();
        playerDeathSound.dispose();
    }

    /**
     * The playRoomCompletedSound function is publicly encapsulated
     * so that it may be called from other classes when an event
     * occurs where a player completes a room in the world.
     * The roomCompleteSound is meant to play only once right
     * after the doorway to the adjacent rooms becomes available.
     * This sound is especially important because the player depends
     * on hearing this sound to know when to advance. This function
     * sets looping to false because we only want to play the sound once
     * per stimulus. We then call the 'play()' function which is
     * a built-in libGDX function specifically for playing sounds.
     */
    public void playRoomCompletedSound(){
        roomCompletedSound.setVolume(id,0.5f);
        roomCompletedSound.setLooping(id, false);
        roomCompletedSound.play();
        roomCompletedSound.dispose();
    }

    /**
     * The playItemSound function is publicly encapsulated
     * so that it may be called from other classes when an event
     * occurs where a player obtains an item from the world or from
     * a battle. The itemObtainedSound is meant to play only once
     * right as the item is added to the players inventory. This
     * sound is especially important because the player depends
     * on hearing this sound to know that an item was added to
     * their inventory. This function sets looping to false because
     * we only want to play the sound once per stimulus. We then
     * call the 'play()' function which is a built-in libGDX
     * function specifically for playing sounds.
     */
    public void playItemSound(){
        itemObtained.setVolume(id,0.4f);
        itemObtained.setLooping(id, false);
        itemObtained.play();
        itemObtained.dispose();
    }

}
