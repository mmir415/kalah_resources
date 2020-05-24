package kalah.Model;

import kalah.Controller.Player;

public abstract class Pit {
    protected final Player owner;
    protected int seeds;

    protected Pit(Player owner, int seeds) {
        this.owner = owner;
        this.seeds = seeds;
    }

    public abstract int plant_seeds(Player player, int seedsToPlant);

    public Player getPitOwner() {
        return owner;
    }

    public int getSeedAmount() {
        return seeds;
    }

}
