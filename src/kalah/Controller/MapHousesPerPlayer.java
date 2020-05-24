package kalah.Controller;
import kalah.Model.House;

import java.util.*;

public class MapHousesPerPlayer {
    private Map<Integer, House> per_player_housing_map;

    public MapHousesPerPlayer() {
        per_player_housing_map = new HashMap<>();
    }

    public void placeInHouse(House house) {
        per_player_housing_map.put(house.getHouseID(), house);
    }

    public List<Integer> grabSeedsPerHouse() {
        List<Integer> seedsInHouse = new ArrayList<>();
        for (House house : per_player_housing_map.values()) {
            seedsInHouse.add(house.getSeedAmount());
        }
        return seedsInHouse;
    }


    public House getFromHouse(int houseNumber) {
        return per_player_housing_map.get(houseNumber);
    }


    @Override
    public int hashCode() {
        return Objects.hash(per_player_housing_map);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MapHousesPerPlayer) {
            MapHousesPerPlayer other_house = (MapHousesPerPlayer) obj;
            return per_player_housing_map.equals(other_house.per_player_housing_map);
        }
        return false;
    }
}