package kalah.Model;

import kalah.Controller.Player;

import java.util.Objects;

public class House extends Pit {

    private final int houseID;
    private static final int Initial_Seed_Count = 4;

    public House(Player owner, int houseID){
        super(owner, Initial_Seed_Count);
        this.houseID = houseID;
    }


    public int pickupSeeds() {
        int seeds = super.seeds;
        super.seeds = 0;

        return seeds;
    }

    public int getHouseID() {
    return houseID;
}

public int getOppositeHouseID(){
        int opposite_number = (GameBoard.Houses_Per_Player + 1) - houseID;
    return opposite_number;
}

@Override
public boolean equals(Object obj){
        if (obj instanceof House) {
            House other = (House) obj;
            return owner.equals(other.owner) && seeds == other.seeds && houseID == other.houseID;
        }
        return false;
    }

@Override
public int plant_seeds(Player player,int seedsToPlant) {
    super.seeds++;
    return seedsToPlant - 1;
}

@Override
public int hashCode(){
    return Objects.hash(owner, seeds, houseID);
}

}