package kalah.View;

import com.qualitascorpus.testsupport.IO;
import kalah.Controller.Player;
import kalah.Model.GameBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardDisplay {
    private final IO io;
    private final GameBoard board;

    public BoardDisplay(GameBoard board, IO io) {
        this.io = io;
        this.board = board;
    }

    public void boardPrinting() {
        Map<Player, List<Integer>> stateOfBoard = board.getBoardState();
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println(String.format("| P2 | 6[%2d] | 5[%2d] | 4[%2d] | 3[%2d] | 2[%2d] | 1[%2d] | %2d |",
                stateOfBoard.get(Player.TWO).get(6),
                stateOfBoard.get(Player.TWO).get(5),
                stateOfBoard.get(Player.TWO).get(4),
                stateOfBoard.get(Player.TWO).get(3),
                stateOfBoard.get(Player.TWO).get(2),
                stateOfBoard.get(Player.TWO).get(1),
                stateOfBoard.get(Player.ONE).get(0))); //Player One Store
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");

        io.println(String.format("| %2d | 1[%2d] | 2[%2d] | 3[%2d] | 4[%2d] | 5[%2d] | 6[%2d] | P1 |",
                stateOfBoard.get(Player.TWO).get(0), //Player Two Store
                stateOfBoard.get(Player.ONE).get(1),
                stateOfBoard.get(Player.ONE).get(2),
                stateOfBoard.get(Player.ONE).get(3),
                stateOfBoard.get(Player.ONE).get(4),
                stateOfBoard.get(Player.ONE).get(5),
                stateOfBoard.get(Player.ONE).get(6)));
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");

    }

    public void printVertical() {
        Map<Player, List<Integer>> stateOfBoard = board.getBoardState();
        io.println("+---------------+");
        io.println(String.format("|       | P2 %2d |", stateOfBoard.get(Player.TWO).get(0))); // Player Two Store
        io.println("+-------+-------+");
        io.println(String.format("| 1[%2d] | 6[%2d] |", stateOfBoard.get(Player.ONE).get(1),stateOfBoard.get(Player.TWO).get(6)));
        io.println(String.format("| 2[%2d] | 5[%2d] |", stateOfBoard.get(Player.ONE).get(2),stateOfBoard.get(Player.TWO).get(5)));
        io.println(String.format("| 3[%2d] | 4[%2d] |", stateOfBoard.get(Player.ONE).get(3),stateOfBoard.get(Player.TWO).get(4)));
        io.println(String.format("| 4[%2d] | 3[%2d] |", stateOfBoard.get(Player.ONE).get(4),stateOfBoard.get(Player.TWO).get(3)));
        io.println(String.format("| 5[%2d] | 2[%2d] |", stateOfBoard.get(Player.ONE).get(5),stateOfBoard.get(Player.TWO).get(2)));
        io.println(String.format("| 6[%2d] | 1[%2d] |", stateOfBoard.get(Player.ONE).get(6),stateOfBoard.get(Player.TWO).get(1)));
        io.println("+-------+-------+");
        io.println(String.format("| P1 %2d |       |", stateOfBoard.get(Player.ONE).get(0))); //Player One Store
        io.println("+---------------+");
    }

    public void printScore() {
        Map<Player, List<Integer>> boardState = board.getBoardState();
        Map<Player, Integer> playerSums = new HashMap<>();

        for (Player player : Player.values()) {
            playerSums.put(player, 0);

            for (int i = 0; i <= GameBoard.Houses_Per_Player; i++) {
                playerSums.put(player, playerSums.get(player) + boardState.get(player).get(i));
            }
            io.println(String.format("\tplayer %d:%d", player.player_number(), playerSums.get(player)));
        }

        int tie_scores = 0;
        Map.Entry<Player, Integer> maxMapEntry = null;

        for (Map.Entry<Player, Integer> entry : playerSums.entrySet()) {

            if (maxMapEntry == null || entry.getValue() > maxMapEntry.getValue()) {
                maxMapEntry = entry;
                tie_scores = 0;

            }
            else if (entry.getValue().equals(maxMapEntry.getValue())) {
                tie_scores++;
            }
        }

        if (tie_scores== 0) {
            io.println(String.format("Player %d wins!", maxMapEntry.getKey().player_number()));
        } else {
            io.println("A tie!");
        }
    }
}