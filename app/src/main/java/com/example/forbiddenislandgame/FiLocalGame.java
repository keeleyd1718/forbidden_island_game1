package com.example.forbiddenislandgame;

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

    //check if the game is over. if it is return the name of the winner
    /*
    If the Fools’ Landing tile sinks the game is lost.
    If both of each the Temples, Caves, Palaces, or Gardens tiles sink before their treasure is collected the game is lost
    If a pawn is on a tile that sinks that player is prompted to “Choose an Adjacent Tile” but if there is no available tile then the pawn sinks and the game is lost
    */
    protected String checkIfGameOver(){
        /*If a pawn is on a tile that sinks that player is prompted to “Choose an Adjacent Tile” but if there is no available tile then the pawn sinks and the game is lost
        if(t instanceof Tile)
        {
            if(t.getTileName().equals("FOOLS_LANDING") && t.getValue() == Tile.Value.SUNK)
            {
                return "Game Over! You lost because Fools Landing has sunken!";
            }
            if(t.getTileName().equals("CORAL_PALACE") || t.getTileName().equals("TIDAL_PALACE")) //Ocean Tiles
            {
                if(t.getValue() == Tile.Value.SUNK)
                {
                    return "Game Over! You lost because your Ocean tiles sunk before you collected the treasure!";
                }
            }
            if(t.getTileName().equals("EMBER_CAVE") || t.getTileName().equals("SHADOW_CAVE")) //Fire Crystal
            {
                if(t.getValue() == Tile.Value.SUNK)
                {
                    return "Game Over! You lost because your Fire Crystal tiles sunk before you collected the treasure!";
                }
            }
            if(t.getTileName().equals("MOON_TEMPLE") || t.getTileName().equals("SUN_TEMPLE")) //Earth Stones
            {
                if (t.getValue() == Tile.Value.SUNK) {
                    return "Game Over! You lost because your Earth Stone tiles sunk before you collected the treasure!";
                }
                if (t.getTileName().equals("WHISPERING_GARDENS") || t.getTileName().equals("HOWLING_GARDEN")) //Wind Statues
                {
                    if (t.getValue() == Tile.Value.SUNK) {
                        return "Game Over! You lost because your Wind Statue tiles sunk before you collected the treasure!";
                    }
                } else {
                    return "You clicked the quit button. Bye!";
                }
            }
        }*/
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
                gs.drawFlood(gs.getDrawnFloodCards());
                if(gs.getDrawnFloodCards().size() > 0){
                    //if there are any flood cards in the drawn flood cards arraylist flip those tiles over
                }
                gs.emptyDrawnFloodCards();
                return true;
            }
            else if (action instanceof FiMoveAction) {
                FiMoveAction a = (FiMoveAction) action;
                FiGameState.TileName t = a.getTileName();
                gs.move(gs.getPlayerTurn(), t);
                return true;
            }
            else if (action instanceof FiShoreUpAction) {
                FiShoreUpAction a = (FiShoreUpAction) action;
                FiGameState.TileName t = a.getTileName();
                FiGameState.Value v = a.getValue();
                gs.shoreUp(gs.getPlayerTurn(), t, v);
                return true;
            }
            else if (action instanceof FiGiveCardAction) {
                FiGiveCardAction a = (FiGiveCardAction) action;
                FiGameState.TreasureCards t = a.getTreasureCardName();
                gs.giveCard(gs.getPlayerTurn(), gs.getPlayerTurn() + 1, t);
                return true;
            }
            else if (action instanceof FiCaptureTreasureAction) {
                if(gs.getPlayerTurn() == 1){
                    gs.captureTreasure(1, gs.getHumanPlayerHand());
                    return true;
                }
                else if(gs.getPlayerTurn() == 2){
                    gs.captureTreasure(2, gs.getDumbAiHand());
                    return true;
                }
                else if(gs.getPlayerTurn() == 3){
                    gs.captureTreasure(3, gs.getSmartAiHand());
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
                checkIfGameOver();
                return true;
            }
        }
        return false;
    }
}
