package kalah.Controller;

import kalah.Model.House;
import kalah.Model.Pit;
import kalah.Model.Store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PitTrackingPerPlayer {
    private Map<Player, Store> per_player_store_map;
    private Map<Player, MapHousesPerPlayer> per_player_housing_map;
    private List<Pit> per_player_pit_list;

    public PitTrackingPerPlayer(){
        per_player_housing_map = new HashMap<>();
        per_player_pit_list = new ArrayList<>();
        per_player_store_map = new HashMap<>();

        for (Player player : Player.values()){
            per_player_housing_map.put(player, new MapHousesPerPlayer());
        }
    }

    public void addPitsToHouse(House house){
        per_player_housing_map.get(house.getPitOwner()).placeInHouse(house);
        per_player_pit_list.add(house);
    }

    public void addPitsToStore(Store store) {
        per_player_store_map.put(store.getPitOwner(), store);
        per_player_pit_list.add(store);
    }


    public House getHouseFromMap(Player player, int houseNumber) {
        return per_player_housing_map.get(player).getFromHouse(houseNumber);
    }

    public House getOpposingHouse(Pit pit) {
        if (pit instanceof House) {
            House house = (House) pit;
            return per_player_housing_map.get(house.getPitOwner().nextTurnPlayer()).getFromHouse(house.getOppositeHouseID());
        } else {
            return null;
        }
    }

    public Store getStoreFromMap(Player player) {
        return per_player_store_map.get(player);
    }

    public Pit getNextInList(Pit current) {
        int nextIndex = (per_player_pit_list.indexOf(current) + 1) % per_player_pit_list.size();
        return per_player_pit_list.get(nextIndex);
    }



     //Below class returns a list of seeds in the pits of each player

    public Map<Player, List<Integer>> getSeedState() {

        Map<Player, List<Integer>> seedsInHousesForPlayers = new HashMap<>();

        for (Player player : Player.values()) {
            List<Integer> seedsPerPit = new ArrayList<>();

            seedsPerPit.add(per_player_store_map.get(player).getSeedAmount());
            seedsPerPit.addAll(per_player_housing_map.get(player).grabSeedsPerHouse());

            seedsInHousesForPlayers.put(player, seedsPerPit);

        }
        return seedsInHousesForPlayers;
    }
}
