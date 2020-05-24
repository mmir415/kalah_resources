package kalah.Controller;

import com.qualitascorpus.testsupport.MockIO;

import java.util.Vector;

public class RobotPlayer {
        private BoardManager boardManager;
        private Vector<Player> players;
        public RobotPlayer(BoardManager boardManager,Vector<Player> players) {
            this.boardManager = boardManager;
            this.players = players;
        }
        public int calculateRobotMove(){

            StringBuilder reason = new StringBuilder("Player P2 (Robot) chooses house #");
            int houseNumber = 0;
            String choiceReason = "";

            for (int i =this.boardManager.getNumberHouses();i>0;i--){
                BoardManager dummyBoardManager = createDummyBoardManager();
                houseNumber = this.boardManager.getNumberHouses()-i+1;
                if(dummyBoardManager.playMove(2,houseNumber)==2){
                    choiceReason = " because it leads to an extra move";
                    reason.append(houseNumber);
                    reason.append(choiceReason);
                    this.boardManager.boardPrinter.printMessage(String.valueOf(reason));
                    return houseNumber;
                }
            }
            for (int i =this.boardManager.getNumberHouses();i>0;i--){
                BoardManager dummyBoardManager = createDummyBoardManager();
                houseNumber = this.boardManager.getNumberHouses()-i+1;
                if(dummyBoardManager.playMove(2,houseNumber) == 1){
                    choiceReason = " because it leads to a capture";
                    reason.append(houseNumber);
                    reason.append(choiceReason);
                    this.boardManager.boardPrinter.printMessage(String.valueOf(reason));
                    return houseNumber;
                }
            }
            for (int i =this.boardManager.getNumberHouses();i>0;i--){
                if(this.boardManager.board.getPit(0,i).total() > 0){
                    houseNumber = this.boardManager.getNumberHouses()-i+1;
                    choiceReason = " because it is the first legal move";
                    reason.append(houseNumber);
                    reason.append(choiceReason);
                    this.boardManager.boardPrinter.printMessage(String.valueOf(reason));
                    return houseNumber;
                }
            }
            return houseNumber;
        }
        /*This is an alternative to a deepCopy, main point is original board should not be touched */
        private BoardManager createDummyBoardManager(){
            BoardManager dummy = new BoardManager(this.boardManager.getNumberHouses(),this.players,0, new MockIO(),false);
            dummy.boardPrinter.printEnable = false;
            for (int row = 0; row < players.size(); row++) {
                for (int col = 0; col <= this.boardManager.getNumberHouses();col++){
                    int numberSeeds = this.boardManager.board.getPit(row,col).total();
                    dummy.board.getPit(row,col).increment(numberSeeds);
                }
            }
            return dummy;
        }
}
