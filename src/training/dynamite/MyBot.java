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
        for (int i = 0; i < 101; i++){
            int nextRandom = random.nextInt(5);
            dynamiteNumber += nextRandom;
            whenToDynamite.add(dynamiteNumber);
        }
        System.out.println("Started new match");
    }

    @Override
    public Move makeMove(Gamestate gamestate) {
        Move move;
        if (whenToDynamite.contains(currentRound)){
            move = BoomKapow();
        }
        else{
            move = getRandomMove();
        }
        currentRound ++;
        return move;
    }

    public boolean checkDynamite(){
        if (noOfDynamites < 101){
            return false;
        }
        else{
            return true;
        }
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

    public Move BoomKapow(){
        Move move;
        if (checkDynamite()){
            addDynamite();
            return Move.D;
        }
        else
            return  getRandomMove();
    }

}
