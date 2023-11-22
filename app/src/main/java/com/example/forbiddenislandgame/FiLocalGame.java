package com.example.forbiddenislandgame;

import android.app.ActivityManager;
import android.graphics.Color;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiDrawFloodAction;
import com.example.actions.FiDrawTreasureAction;
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
    protected String checkIfGameOver(){
        //check if the game is over. if it is return the name of the winner
        /*
        If the Fools’ Landing tile sinks the game is lost.
        If both of each the Temples, Caves, Palaces, or Gardens tiles sink before their treasure is collected the game is lost
        If a pawn is on a tile that sinks that player is prompted to “Choose an Adjacent Tile” but if there is no available tile then the pawn sinks and the game is lost
        */
        return null;
    }
    protected void sendUpdatedStateTo(GamePlayer p){
        //sends the GameState to a player. this is needed so the player can make a move
        p.sendInfo(gs);
    }
    protected boolean canMove(int playerIdx){
        //return true if the player can make a move
        if(gs instanceof FiGameState){
            return gs.getPlayerTurn() == playerIdx;
        }
        return false;
    }
    protected boolean makeMove(GameAction action){
        if(canMove(getPlayerIdx(action.getPlayer()))) {
            //actually makes a move. the players don't make moves, the players tell LocalGame to make a move
            if (action instanceof FiDrawTreasureAction) {
                if(gs.getPlayerTurn() == 1){
                    gs.drawTreasure(gs.getHumanPlayerHand());
                    return true;
                }
                else if(gs.getPlayerTurn() == 2){
                    gs.drawTreasure(gs.getDumbAiHand());
                    return true;
                }
                else if(gs.getPlayerTurn() == 3){
                    gs.drawTreasure(gs.getSmartAiHand());
                    return true;
                }
                else{
                    return false;
                }
            }
            else if (action instanceof FiDrawFloodAction) {
                if(gs.getPlayerTurn() == 1){
                    gs.drawFlood(gs.getHumanPlayerHand());
                    return true;
                }
                else if(gs.getPlayerTurn() == 2){
                    gs.drawFlood(gs.getDumbAiHand());
                    return true;
                }
                else if(gs.getPlayerTurn() == 3){
                    gs.drawFlood(gs.getSmartAiHand());
                    return true;
                }
                else{
                    return false;
                }
            }
            else if (action instanceof FiMoveAction) {
                FiMoveAction a = (FiMoveAction) action;
                Tile.TileName t = a.getTileName();
                gs.move(gs.getPlayerTurn(), t);
                return true;
            }
            else if (action instanceof FiShoreUpAction) {
                FiShoreUpAction a = (FiShoreUpAction) action;
                Tile.value v = a.getValue();
                gs.shoreUp(gs.getPlayerTurn(), v);
                return true;
            }
            else if (action instanceof FiGiveCardAction) {
                gs.giveCard(gs.getPlayerTurn(), , );
                return true;
            }
            else if (action instanceof FiCaptureTreasureAction) {
                if(gs.getPlayerTurn() == 1){
                    gs.captureTreasure(gs.getHumanPlayerHand());
                    return true;
                }
                else if(gs.getPlayerTurn() == 2){
                    gs.captureTreasure(gs.getDumbAiHand());
                    return true;
                }
                else if(gs.getPlayerTurn() == 3){
                    gs.captureTreasure(gs.getSmartAiHand());
                    return true;
                }
                else{
                    return false;
                }
            }
            else if (action instanceof FiEndTurnAction) {
                if (gs.numPlayers > 2) {
                    if (gs.getPlayerTurn() == 1) {
                        gs.setPlayerTurn(2);
                    } else if (gs.getPlayerTurn() == 2) {
                        gs.setPlayerTurn(3);
                    } else if (gs.getPlayerTurn() == 3) {
                        gs.setPlayerTurn(1);
                    }
                }
                else {
                    if (gs.getPlayerTurn() == 1) {
                        gs.setPlayerTurn(2);
                    }
                    else if (gs.getPlayerTurn() == 2) {
                        gs.setPlayerTurn(1);
                    }
                }
                return true;
            }
            else if (action instanceof FiGameOverAction) {
                return true;
                //finish this
            }
        }
        return false;
    }
}
