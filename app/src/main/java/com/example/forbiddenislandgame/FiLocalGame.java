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

    //check if the game is over. if it is return the name of the winner (our game is a team game)
    protected String checkIfGameOver() {
        //If a pawn is on a tile that sinks that player is prompted to “Choose an Adjacent Tile” but if there is no available tile then the pawn sinks and the game is lost (need to do still or not include this part of the game)

        //the game is won if all 4 treasures have been captured, the player whose turn it is has a helicopter lift card and they are on the FOOLS_LANDING tile
        if(gs.areTreasuresCaptured()){
            for(int i = 0; i < gs.getPlayerTurnHand(gs.getPlayerTurn()).size(); i++){
                if((gs.getHelicopterLiftCards().contains(gs.getPlayerTurnHand(gs.getPlayerTurn()).get(i))) && gs.getPlayerLocation(gs.getPlayerTurn()).equals(FiGameState.TileName.FOOLS_LANDING)) {
                    return "Congrats you guys have won the game!!";
                }
            }
        }

        if (gs.map.get(FiGameState.TileName.FOOLS_LANDING).equals(FiGameState.Value.SUNK)) {
            return "Game Over! You lost because Fools Landing sunk!";
        }
        if (gs.map.get(FiGameState.TileName.CORAL_PALACE).equals(FiGameState.Value.SUNK) && gs.map.get(FiGameState.TileName.TIDAL_PALACE).equals(FiGameState.Value.SUNK) && (!gs.isCapturedOceanChalice)) { //Ocean Chalice Tiles
            return "Game Over! You lost because your Ocean tiles sunk before you collected the treasure!";
        }
        if (gs.map.get(FiGameState.TileName.EMBER_CAVE).equals(FiGameState.Value.SUNK) && gs.map.get(FiGameState.TileName.SHADOW_CAVE).equals(FiGameState.Value.SUNK) && (!gs.isCapturedFireCrystal)){ //Fire Crystal Tiles
            return "Game Over! You lost because your Fire Crystal tiles sunk before you collected the treasure!";
        }
        if (gs.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.SUNK) && gs.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.SUNK) && (!gs.isCapturedEarthStone)) { //Earth Stone Tiles
            return "Game Over! You lost because your Earth Stone tiles sunk before you collected the treasure!";
        }
        if (gs.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.SUNK) && gs.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.SUNK) && (!gs.isCapturedWindStatue)) { //Wind Statues
            return "Game Over! You lost because your Wind Statue tiles sunk before you collected the treasure!";
        }
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
            //always start the player's turn by drawing treasure cards
            gs.drawTreasure(gs.getPlayerTurnHand(gs.getPlayerTurn()));

            if(action instanceof FiMoveAction) {
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
                FiGameState.TreasureCards t = a.getTreasureCardName();//get the card they want to give away
                gs.giveCard(gs.getPlayerTurn(), gs.playerChosen, t);//call the giveCard() method to give the card (t) to the playerChosen
            }
            else if (action instanceof FiCaptureTreasureAction) {
                gs.captureTreasure(gs.getPlayerTurn(), gs.getPlayerTurnHand(gs.playerTurn), gs.getPlayerLocation(gs.getPlayerTurn()));
            }
            else if(action instanceof FiEndTurnAction){//if a player skips their turn
            }
            else if (action instanceof FiGameOverAction) {
                checkIfGameOver();
            }

            //always end the turn by drawing flood cards and switching whose turn it is
            gs.drawFlood(gs.getDrawnFloodCards());
            gs.endTurn();
            return true;
        }
        return false;
    }
}
