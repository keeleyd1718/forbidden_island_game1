package com.example.forbiddenislandgame;

import android.util.Log;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiDiscardAction;
import com.example.actions.FiEndTurnAction;
import com.example.actions.FiGameOverAction;
import com.example.actions.FiGiveCardAction;
import com.example.actions.FiMoveAction;
import com.example.actions.FiShoreUpAction;
import com.example.game.GameFramework.LocalGame;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.players.GamePlayer;

public class FiLocalGame extends LocalGame {

    FiGameState gs;
    public FiLocalGame() {
        gs = new FiGameState();
        this.state = gs;
    }

    public void start(GamePlayer[] players){
        //actually starts the game. game won't run without this
        super.start(players);
    }

    //check if the game is over. if it is return the name of the winner (our game is a team game so no name is returned)
    protected String checkIfGameOver() {
        //the game is won if all 4 treasures have been captured, the player whose turn it is has a helicopter lift card, and they are on the FOOLS_LANDING tile
        if(gs.areTreasuresCaptured()){
            for(int i = 0; i < gs.getPlayerHand(gs.getPlayerTurn()).size(); i++){
                if((gs.getHelicopterLiftCards().contains(gs.getPlayerHand(gs.getPlayerTurn()).get(i))) && gs.getPlayerLocation(gs.getPlayerTurn()).equals(FiGameState.TileName.FOOLS_LANDING)) {
                    return "Congrats you guys have won the game!!";
                }
            }
        }
        //check if the game was lost
        gs.isGameLost();

        return null;
    }

    protected void sendUpdatedStateTo(GamePlayer p){
        //sends the GameState to a player. this is needed so the player can make a move
        p.sendInfo(new FiGameState((gs)));
    }
    protected boolean canMove(int playerIdx){
        //return true if the player can make a move
        if(gs instanceof FiGameState){
            return gs.getPlayerTurn() == playerIdx;
        }
        return false;
    }
    protected boolean makeMove(GameAction action){
        Log.e("zzz makeMove", "received move!");

        //actually makes a move. the players don't make moves, the players tell LocalGame to make a move
        if(canMove(getPlayerIdx(action.getPlayer()))) {
            gs.drawTreasure(gs.getPlayerHand(gs.getPlayerTurn()));//always start the player's turn by drawing treasure cards

            if(gs.getActionsRemaining() < 1){
                //always end the turn by drawing flood cards and switching whose turn it is
                gs.drawFlood(gs.getDrawnFloodCards());
                gs.endTurn();
                return true;
            }
            else if(action instanceof FiMoveAction) {
                FiMoveAction a = (FiMoveAction) action;//create an instance of a move action
                FiGameState.TileName t = a.getTileName();//get the tile the player pressed
                gs.move(gs.getPlayerTurn(), t);//call the move() method
            }
            else if (action instanceof FiShoreUpAction) {
                //same steps as the move action above
                FiShoreUpAction a = (FiShoreUpAction) action;
                FiGameState.TileName t = a.getTileName();
                gs.shoreUp(gs.getPlayerTurn(), t);
            }
            else if (action instanceof FiGiveCardAction) {
                FiGiveCardAction a = (FiGiveCardAction) action;
                int index = a.getIndexOfCard();
                int playerChosen = a.getPlayerChosen();
                gs.giveCard(gs.getPlayerTurn(), playerChosen, index);//call the giveCard() method to give the card at the index in their hand to the playerChosen
            }
            else if (action instanceof FiCaptureTreasureAction) {
                gs.captureTreasure(gs.getPlayerTurn(), gs.getPlayerHand(gs.getPlayerTurn()), gs.getPlayerLocation(gs.getPlayerTurn()));
            }
            else if (action instanceof FiDiscardAction) {
                FiDiscardAction a = (FiDiscardAction) action;
                int index = a.getIndexOfCard();//get the card they want to discard
                gs.discard(gs.getPlayerTurn(), index);
            }
            else if(action instanceof FiEndTurnAction){//if a player skips their turn
                gs.drawFlood(gs.getDrawnFloodCards());
                gs.endTurn();
                return true;
            }
            else if (action instanceof FiGameOverAction) {
                checkIfGameOver();
                return true;
            }
        }
        return false;
    }
}
