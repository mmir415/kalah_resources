package kalah.Model;

import kalah.Controller.Player;

import java.util.Objects;

public class Store extends Pit {
    public Store(Player owner) {
        super(owner, 0);
    }


    public void depositSeeds(int capturedSeeds) {
        super.seeds += capturedSeeds;
    }

    @Override
    public int plant_seeds(Player player, int seedsToPlant) {
        if (player.equals(super.owner)) {
            super.seeds++;
            int planting = seedsToPlant - 1;
            return planting;
        } else {
            return seedsToPlant;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, seeds, 11);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Store) {
            Store other = (Store) obj;
            return owner.equals(other.owner) && seeds == other.seeds;
        }
        return false;
    }
}