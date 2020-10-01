package training.dynamite;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyBot implements Bot {
    int noOfDynamites = 0;
    List<Integer> whenToDynamite = new ArrayList<>();
    int currentRound = 0;

    public MyBot() {
        Random random = new Random();
        int dynamiteNumber = 0;
        for (int i = 0; i < 500; i++){
            int nextRandom = random.nextInt(7) + 3;
            dynamiteNumber += nextRandom;
            whenToDynamite.add(dynamiteNumber);
        }
        System.out.println("Started new match");
    }

    @Override
    public Move makeMove(Gamestate gamestate) {
        Move move;
        if (gamestate.getRounds().size() < 3)
            move = defaultMove();
        else{
            if(checkForPattern(gamestate)){
                move =  getMoveThatBeats(gamestate);
            }
            else
                move = defaultMove();
        }
        return move;
    }

    private boolean checkForPattern(Gamestate gamestate) {
        Round lastRound = (Round)gamestate.getRounds().get(gamestate.getRounds().size() - 1);
        Round roundBefore = (Round)gamestate.getRounds().get(gamestate.getRounds().size() - 2);
        Round roundBeforeThat = (Round)gamestate.getRounds().get(gamestate.getRounds().size() - 3);
        if (lastRound == roundBefore && roundBefore == roundBeforeThat)
            return true;
        else
            return false;
    }

    private Move getMoveThatBeats(Gamestate gamestate) {
        Round lastRound = (Round)gamestate.getRounds().get(gamestate.getRounds().size() - 1);
        switch(lastRound.getP2()) {
            case R:
                return Move.P;
            case P:
                return Move.S;
            case S:
                return Move.R;
            default:
                return defaultMove();
        }
    }

    public boolean checkDynamite(){
        if (noOfDynamites < 101)
            return false;
        else
            return true;
    }

    public void addDynamite(){
        noOfDynamites++;
    }

    public Move getRandomMove() {
        int randomNumberBetween0And3 = (int)Math.floor(Math.random() * 3.0D);
        Move[] possibleMoves = new Move[]{Move.R, Move.P, Move.S};
        Move randomMove = possibleMoves[randomNumberBetween0And3];
        return randomMove;
    }

    public Move defaultMove(){
        Move move;
        if (whenToDynamite.contains(currentRound)){
            if (checkDynamite()){
                addDynamite();
                return Move.D;
            }
            else
                return  getRandomMove();
        }
        else
            return  getRandomMove();
    }
}
