package Sprites;

public class CombatMove {

    public String name;
    public int damage;
    public int weight;

    public CombatMove(String name, int damage, int weight){
        this.name = name;
        this.damage = damage;

        // weight is how probable the move is.
        // If an enemy has 2 moves, "roar" with a weight of 1 and "slash" with a weight of 4,
        // "slash" will happen 4/5 times, and "roar" will happen 1/5 times.
        // [5 because (roar)1 + (slash)4 = (total)5]

        this.weight = weight;
    }

}
