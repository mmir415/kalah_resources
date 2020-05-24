package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.View.BoardDisplay;
import kalah.Controller.Player;
import kalah.Model.GameBoard;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */

/**
 * Rules for Kalah:
 *• Game starts with 6 stones per hole.
 * • Your Kalah is on your right.
 * • Moves are counterclockwise.
 * • If the last stone lands in your Kalah, you get another turn.
 * • If the last stone lands in your empty hole, all the stones from the
 * other player’s opposite hole go into your kalah.
 * • If one player runs out of stones, the game ends and the other
 * player puts the rest of his/her stones in her/his kalah.
 * • The player with the most stones at the end of the game wins.
 *
 *
 * Expanded version of the rules:
 * Kalah is played on a board of two rows, each consisting of six round pits that have a large store at either end
 * called kalah.
 * A player owns the six pits closest to him and the kalah on his right side. Beginners may start with three seeds
 * in each pit, but the game becomes more and more challenging by starting with 4, 5 or up to 6 pieces in each pit.
 * Today, four seeds per hole has become the most common variant, but Champion recommended the expert game
 *
 *
 * Most Challenging Set-up (Expert Game)
 *
 * Play is counterclockwise. The seeds are distributed one by one in the pits and the players own kalah,
 * but not into the opponent's store.
 *
 * If the last seed is dropped into an opponent's pit or a non-empty pit of the player,
 * the move ends without anything being captured.
 *
 * If the last seed falls into the player's kalah, he must move again.
 *
 * If the last seed is put into an empty pit owned by the player, they capture all contents of the opposite pit together
 * with the capturing piece and puts them in his kalah. If the opposite pit is empty, nothing is captured.
 * A capture ends the move.
 *
 * The game ends when one player no longer has any seeds in any of his holes.
 * The remaining pieces are captured by his adversary. The player who has captured most pieces is declared the winner.
 */

public class Kalah {

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public enum TurnActions {
		EMPTY_HOLE, END_TURN, BONUS_MOVE, NEW_GAME
	}

	public void play(IO io) {
        Player currentPlayer = Player.ONE;
        final String STARTUP_COMMAND = "Player P%d's turn - Specify house number or 'q' to quit: ";
        final String Testing_Robot = "Player P2 (Robot) chooses house #2 because it leads to an extra move";

		GameBoard board = new GameBoard(io);
		BoardDisplay printer = new BoardDisplay(board, io);

		//To pass all Assignment 3 tests, uncomment printer.boardPrinting() and comment out printer.printVertical()
		//To pass all Assignment 4 tests, comment out printer.boardPrinting() and uncomment printer.printVertical()
		printer.boardPrinting();
//		printer.printVertical();

		if (currentPlayer == Player.ONE) {
			String userPressed = io.readFromKeyboard(String.format(STARTUP_COMMAND, currentPlayer.player_number())).trim();

//        else{
//				String userPressed = io.readFromKeyboard(String.format(Testing_Robot, currentPlayer.player_number())).trim();
//			}
//		}

			while (!userPressed.equals("q")) {
				try {
					if (checkValidity(userPressed)) {
						TurnActions result = board.Turn(currentPlayer, Integer.valueOf(userPressed));

						//If a player chooses an empty hole, tell them to move again

						if (result.equals(TurnActions.EMPTY_HOLE)) {
							io.println("House is empty. Move again.");
						}  //If turn is ending and a player gets another move, keep currentPlayer the same

						else if (result.equals(TurnActions.END_TURN)) {
							currentPlayer = currentPlayer.nextTurnPlayer();
						}
					}
				} catch (NumberFormatException e) {
					io.println("Invalid House Number " + userPressed);
				}

				//To pass all Assignment 3 tests, uncomment printer.boardPrinting() and comment out printer.printVertical()
				//To pass all Assignment 4 tests, comment out printer.boardPrinting() and uncomment printer.printVertical()
				printer.boardPrinting();

//				if (!board.GameEnding(currentPlayer)) break;

				if (currentPlayer == Player.TWO) {
					TurnActions result = board.RobotMove(currentPlayer);

					if (result.equals(TurnActions.EMPTY_HOLE)) {
						io.println("House is empty. Move again.");
					}  //If turn is ending and a player gets another move, keep currentPlayer the same

					else if (result.equals(TurnActions.END_TURN)) {
						currentPlayer = currentPlayer.nextTurnPlayer();
					}
				}
//			printer.printVertical();

				//If a player cannot make any more moves, must evaluate out to a Game over
				if (!board.GameEnding(currentPlayer)) break;

				//Must show prompt and get users input each turn
				userPressed = io.readFromKeyboard(String.format(STARTUP_COMMAND, currentPlayer.player_number())).trim();
			}

			io.println("Game over");

			//To pass all Assignment 3 tests, uncomment printer.boardPrinting() and comment out printer.printVertical()
			//To pass all Assignment 4 tests, comment out printer.boardPrinting() and uncomment printer.printVertical()
			printer.boardPrinting();
//		printer.printVertical();

			//Print the score if neither player pressed q to trigger the Game over
			if (!userPressed.equals("q")) {
				printer.printScore();
			}
		}
		if (currentPlayer == Player.TWO){
			TurnActions result = board.RobotMove(currentPlayer);

			if (result.equals(TurnActions.EMPTY_HOLE)) {
				io.println("House is empty. Move again.");
			}  //If turn is ending and a player gets another move, keep currentPlayer the same

			else if (result.equals(TurnActions.END_TURN)) {
				currentPlayer = currentPlayer.nextTurnPlayer();
			}

			printer.boardPrinting();

			if (!board.GameEnding(currentPlayer)) return;


		}
	}

	private boolean checkValidity(String input) throws NumberFormatException {
		int houseNumber = Integer.valueOf(input);
		int maxHouses = GameBoard.Houses_Per_Player;
		return houseNumber > 0 && houseNumber <= maxHouses;
	}
}
