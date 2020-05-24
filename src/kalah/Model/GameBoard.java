package kalah.Model;
import com.qualitascorpus.testsupport.IO;
import kalah.Controller.PitTrackingPerPlayer;
import kalah.Kalah;
import kalah.Controller.Player;

import java.util.List;
import java.util.Map;

public class GameBoard {
    protected House House;
    protected PitTrackingPerPlayer pitTrackingPerPlayer;
    public static final int Houses_Per_Player = 6;
    private final IO io;

    public GameBoard(IO io) {
        pitTrackingPerPlayer = new PitTrackingPerPlayer();
        boardSetup();
        this.io = io;
    }


    private void houseGenerator(Player player) {
        for (int i = 1; i <= Houses_Per_Player; i++) {
            pitTrackingPerPlayer.addPitsToHouse(new House(player, i));
        }
    }
    private void boardSetup() {
        pitTrackingPerPlayer.addPitsToStore(new Store(Player.ONE));
        houseGenerator(Player.TWO);
        pitTrackingPerPlayer.addPitsToStore(new Store(Player.TWO));
        houseGenerator(Player.ONE);
    }


    //Below class determines the gameplay of Kalah

    public Kalah.TurnActions Turn(Player player, int initialHouse) {
        House currentHouse = pitTrackingPerPlayer.getHouseFromMap(player, initialHouse);
        Pit currentPit = pitTrackingPerPlayer.getHouseFromMap(player, initialHouse);

        int game_seeds = currentHouse.pickupSeeds();
        if (game_seeds == 0) {
            return Kalah.TurnActions.EMPTY_HOLE;
        }
        while (game_seeds != 0) {
            currentPit = pitTrackingPerPlayer.getNextInList(currentPit);
            game_seeds = currentPit.plant_seeds(player, game_seeds);
        }

        //As the rules dictate, if the final pit is in the current player's store, they get a bonus move
        if (currentPit.equals(pitTrackingPerPlayer.getStoreFromMap(player))) {
//            io.println("print.Player P2 (Robot) chooses house #2 because it leads to an extra move");
            return Kalah.TurnActions.BONUS_MOVE;
        }

        //Player owns current pit, finds pit to be empty, deposits their seed
        if (currentPit.getPitOwner().equals(player) && currentPit.getSeedAmount() == 1) {
            Pit oppositePit = pitTrackingPerPlayer.getOpposingHouse(currentPit);
            int oppositePitAmount = oppositePit.getSeedAmount();

            if (oppositePit != null && oppositePitAmount > 0) {

                int seedsCaptured = ((kalah.Model.House) oppositePit).pickupSeeds();
                seedsCaptured += ((kalah.Model.House) currentPit).pickupSeeds();
                pitTrackingPerPlayer.getStoreFromMap(player).depositSeeds(seedsCaptured);
            }
        }

        return Kalah.TurnActions.END_TURN;
    }

//    public Kalah.TurnActions RobotMove(Player player){
//        //Below was an incorrect usage of the RobotMove class.
//    }
//        for (int i=1; i<6;i++) {
//            House currentHouse = pitTrackingPerPlayer.getHouseFromMap(player, i);
//            Pit currentPit = pitTrackingPerPlayer.getHouseFromMap(player, i);
//
//            int game_seeds = currentHouse.pickupSeeds();
//            if (game_seeds == 0) {
//                i=6;
//                return Kalah.TurnActions.EMPTY_HOLE;
//            }
//            while (game_seeds != 0) {
//                currentPit = pitTrackingPerPlayer.getNextInList(currentPit);
//                game_seeds = currentPit.plant_seeds(player, game_seeds);
//            }
//
//            //As the rules dictate, if the final pit is in the current player's store, they get a bonus move
//            if (currentPit.equals(pitTrackingPerPlayer.getStoreFromMap(player))) {
//            io.println(String.format("print.Player P2 (Robot) chooses house #2 because it leads to an extra move",i));
//                return Kalah.TurnActions.BONUS_MOVE;
//            }
//
//            if (currentPit.getPitOwner().equals(player) && currentPit.getSeedAmount() == 1) {
//                Pit oppositePit = pitTrackingPerPlayer.getOpposingHouse(currentPit);
//                int oppositePitAmount = oppositePit.getSeedAmount();
//
//                if (oppositePit != null && oppositePitAmount > 0) {
//
//                    io.println(String.format("Player P2 (Robot) chooses house #2 because it is the first legal move",i));
//
//                    int seedsCaptured = ((kalah.Model.House) oppositePit).pickupSeeds();
//                    seedsCaptured += ((kalah.Model.House) currentPit).pickupSeeds();
//                    pitTrackingPerPlayer.getStoreFromMap(player).depositSeeds(seedsCaptured);
//                }
//            }
//        }
//
//        return Kalah.TurnActions.END_TURN;
//
//    }

    public Map<Player, List<Integer>> getBoardState() {
        return pitTrackingPerPlayer.getSeedState();
    }

    public boolean GameEnding(Player player) {
        List<Integer> seedsInPlayForPlayer = pitTrackingPerPlayer.getSeedState().get(player);

        for (int i = 0; i < seedsInPlayForPlayer.size(); i++) {
            if (i != 0 && seedsInPlayForPlayer.get(i) > 0) {
                return true;
            }
        }
        return false;
    }


}