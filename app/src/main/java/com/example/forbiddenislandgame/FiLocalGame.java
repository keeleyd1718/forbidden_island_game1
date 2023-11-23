package com.example.forbiddenislandgame;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiDiscardAction;
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
    protected String checkIfGameOver() {
        //If a pawn is on a tile that sinks that player is prompted to “Choose an Adjacent Tile” but if there is no available tile then the pawn sinks and the game is lost
        //need to do still or not include this part of the game

        if (gs.map.get(FiGameState.TileName.FOOLS_LANDING).equals(FiGameState.Value.SUNK)) {
            return "Game Over! You lost because Fools Landing sunk!";
        }
        if (gs.map.get(FiGameState.TileName.CORAL_PALACE).equals(FiGameState.Value.SUNK) && gs.map.get(FiGameState.TileName.TIDAL_PALACE).equals(FiGameState.Value.SUNK)) { //Ocean Chalice Tiles
            if ((gs.oceanChaliceTreasureCount != 1)) {
                return "Game Over! You lost because your Ocean tiles sunk before you collected the treasure!";
            }
        }
        if (gs.map.get(FiGameState.TileName.EMBER_CAVE).equals(FiGameState.Value.SUNK) && gs.map.get(FiGameState.TileName.SHADOW_CAVE).equals(FiGameState.Value.SUNK)) { //Fire Crystal Tiles
            if ((gs.fireCrystalTreasureCount != 1)) {
                return "Game Over! You lost because your Fire Crystal tiles sunk before you collected the treasure!";
            }
        }
        if (gs.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.SUNK) && gs.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.SUNK)) { //Earth Stone Tiles
            if ((gs.earthStoneTreasureCount != 1)) {
                return "Game Over! You lost because your Earth Stone tiles sunk before you collected the treasure!";
            }
        }
        if (gs.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.SUNK) && gs.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.SUNK)) { //Wind Statues
            if ((gs.windStatueTreasureCount != 1)) {
                return "Game Over! You lost because your Wind Statue tiles sunk before you collected the treasure!";
            }
        }
        return "You clicked the quit button. Bye!";
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
                gs.drawFlood(gs.getDrawnFloodCards());//call the drawFlood method on the drawnFloodCards array
                //if there are any flood cards in the drawn flood cards arraylist go through each element and change those tiles value to flooded or sunk depending on what the values were
                for(int i = 0; i < gs.getDrawnFloodCards().size(); i++){
                    if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.ABANDONED_CLIFFS) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.ABANDONED_CLIFFS1)){
                        if(gs.map.get(FiGameState.TileName.ABANDONED_CLIFFS).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.ABANDONED_CLIFFS, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.ABANDONED_CLIFFS).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.ABANDONED_CLIFFS, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.FOOLS_LANDING1) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.FOOLS_LANDING)){
                        if(gs.map.get(FiGameState.TileName.FOOLS_LANDING).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.FOOLS_LANDING, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.FOOLS_LANDING).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.FOOLS_LANDING, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.BRONZE_GATE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.BRONZE_GATE1)){
                        if(gs.map.get(FiGameState.TileName.BRONZE_GATE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.BRONZE_GATE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.BRONZE_GATE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.BRONZE_GATE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.GOLD_GATE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.GOLD_GATE1)){
                        if(gs.map.get(FiGameState.TileName.GOLD_GATE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.GOLD_GATE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.GOLD_GATE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.GOLD_GATE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.CORAL_PALACE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.CORAL_PALACE1)){
                        if(gs.map.get(FiGameState.TileName.CORAL_PALACE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.CORAL_PALACE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.CORAL_PALACE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.CORAL_PALACE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.SUN_TEMPLE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.SUN_TEMPLE1)){
                        if(gs.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.SUN_TEMPLE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.SUN_TEMPLE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.SILVER_GATE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.SILVER_GATE1)){
                        if(gs.map.get(FiGameState.TileName.SILVER_GATE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.SILVER_GATE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.SILVER_GATE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.SILVER_GATE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.PHANTOM_ROCK) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.PHANTOM_ROCK1)){
                        if(gs.map.get(FiGameState.TileName.PHANTOM_ROCK).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.PHANTOM_ROCK, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.PHANTOM_ROCK).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.PHANTOM_ROCK, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.WATCHTOWER) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.WATCHTOWER1)){
                        if(gs.map.get(FiGameState.TileName.WATCHTOWER).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.WATCHTOWER, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.WATCHTOWER).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.WATCHTOWER, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.COPPER_GATE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.COPPER_GATE1)){
                        if(gs.map.get(FiGameState.TileName.COPPER_GATE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.COPPER_GATE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.COPPER_GATE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.COPPER_GATE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.WHISPERING_GARDENS) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.WHISPERING_GARDENS1)){
                        if(gs.map.get(FiGameState.TileName.WHISPERING_GARDENS).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.WHISPERING_GARDENS, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.WHISPERING_GARDENS).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.WHISPERING_GARDENS, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.SHADOW_CAVE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.SHADOW_CAVE1)){
                        if(gs.map.get(FiGameState.TileName.SHADOW_CAVE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.SHADOW_CAVE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.SHADOW_CAVE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.SHADOW_CAVE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.LOST_LAGOON) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.LOST_LAGOON1)){
                        if(gs.map.get(FiGameState.TileName.LOST_LAGOON).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.LOST_LAGOON, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.LOST_LAGOON).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.LOST_LAGOON, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.MOON_TEMPLE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.MOON_TEMPLE1)){
                        if(gs.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.MOON_TEMPLE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.MOON_TEMPLE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.DECEPTION_DUNES) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.DECEPTION_DUNES1)){
                        if(gs.map.get(FiGameState.TileName.DECEPTION_DUNES).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.DECEPTION_DUNES, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.DECEPTION_DUNES).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.DECEPTION_DUNES, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.TWILIGHT_HOLLOW) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.TWILIGHT_HOLLOW1)){
                        if(gs.map.get(FiGameState.TileName.TWILIGHT_HOLLOW).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.TWILIGHT_HOLLOW, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.TWILIGHT_HOLLOW).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.TWILIGHT_HOLLOW, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.EMBER_CAVE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.EMBER_CAVE1)){
                        if(gs.map.get(FiGameState.TileName.EMBER_CAVE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.EMBER_CAVE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.EMBER_CAVE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.EMBER_CAVE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.TIDAL_PALACE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.TIDAL_PALACE1)){
                        if(gs.map.get(FiGameState.TileName.TIDAL_PALACE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.TIDAL_PALACE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.TIDAL_PALACE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.TIDAL_PALACE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.OBSERVATORY) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.OBSERVATORY1)){
                        if(gs.map.get(FiGameState.TileName.OBSERVATORY).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.OBSERVATORY, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.OBSERVATORY).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.OBSERVATORY, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.IRON_GATE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.IRON_GATE1)){
                        if(gs.map.get(FiGameState.TileName.IRON_GATE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.IRON_GATE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.IRON_GATE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.IRON_GATE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.CRIMSON_FOREST) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.CRIMSON_FOREST1)){
                        if(gs.map.get(FiGameState.TileName.CRIMSON_FOREST).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.CRIMSON_FOREST, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.CRIMSON_FOREST).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.CRIMSON_FOREST, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.MISTY_MARSH) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.MISTY_MARSH1)){
                        if(gs.map.get(FiGameState.TileName.MISTY_MARSH).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.MISTY_MARSH, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.MISTY_MARSH).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.MISTY_MARSH, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.BREAKERS_BRIDGE) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.BREAKERS_BRIDGE1)){
                        if(gs.map.get(FiGameState.TileName.BREAKERS_BRIDGE).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.BREAKERS_BRIDGE, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.BREAKERS_BRIDGE).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.BREAKERS_BRIDGE, FiGameState.Value.SUNK);
                        }
                    }
                    else if(gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.HOWLING_GARDEN) || gs.getDrawnFloodCards().get(i).equals(FiGameState.FloodCards.HOWLING_GARDEN1)){
                        if(gs.map.get(FiGameState.TileName.HOWLING_GARDEN).equals(FiGameState.Value.NORMAL)){
                            gs.map.put(FiGameState.TileName.HOWLING_GARDEN, FiGameState.Value.FLOODED);
                        }
                        else if(gs.map.get(FiGameState.TileName.HOWLING_GARDEN).equals(FiGameState.Value.FLOODED)){
                            gs.map.put(FiGameState.TileName.HOWLING_GARDEN, FiGameState.Value.SUNK);
                        }
                    }
                    else{
                        return false;
                    }
                }
                gs.emptyDrawnFloodCards();//empty the array of drawn flood cards
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
                gs.shoreUp(gs.getPlayerTurn(), t);
                return true;
            }
            else if (action instanceof FiGiveCardAction) {
                FiGiveCardAction a = (FiGiveCardAction) action;
                FiGameState.TreasureCards t = a.getTreasureCardName();
                gs.giveCard(gs.getPlayerTurn(), gs.getPlayerChosen(), t);
                return true;
            }
            else if (action instanceof FiCaptureTreasureAction) {
                if(gs.getPlayerTurn() == 1){
                    gs.captureTreasure(gs.getPlayerTurn(), gs.getHumanPlayerHand(), gs.getPlayer1Location());
                    return true;
                }
                else if(gs.getPlayerTurn() == 2){
                    gs.captureTreasure(2, gs.getDumbAiHand(), gs.getPlayer2Location());
                    return true;
                }
                else if(gs.getPlayerTurn() == 3){
                    gs.captureTreasure(3, gs.getSmartAiHand(), gs.getPlayer3Location());
                    return true;
                }
                else{
                    return false;
                }
            }
            else if (action instanceof FiDiscardAction) {
                //add the card the player chose to discard to the discard treasure deck
                FiDiscardAction a = (FiDiscardAction) action;
                FiGameState.TreasureCards t = a.getTreasureCardName();
                if(gs.getPlayerTurn() == 1){
                    if(gs.getHumanPlayerHand().size() > 5){
                        gs.discard(gs.getPlayerTurn(), t);
                    }
                }
                else if(gs.getPlayerTurn() == 2){
                    if(gs.getDumbAiHand().size() > 5){
                        gs.discard(gs.getPlayerTurn(), t);
                    }
                }
                else if(gs.getPlayerTurn() == 3){
                    if(gs.getSmartAiHand().size() > 5){
                        gs.discard(gs.getPlayerTurn(), t);
                    }
                }
                gs.discard(gs.getPlayerTurn(), t);
            }
            else if (action instanceof FiEndTurnAction) {
                if (gs.numPlayers > 2) {
                    if (gs.getPlayerTurn() == 1) {
                        gs.setPlayerTurn(2);
                        gs.setActionsRemaining(3);
                    } else if (gs.getPlayerTurn() == 2) {
                        gs.setPlayerTurn(3);
                        gs.setActionsRemaining(3);
                    } else if (gs.getPlayerTurn() == 3) {
                        gs.setPlayerTurn(1);
                        gs.setActionsRemaining(3);
                    }
                }
                else {
                    if (gs.getPlayerTurn() == 1) {
                        gs.setPlayerTurn(2);
                        gs.setActionsRemaining(3);
                    }
                    else if (gs.getPlayerTurn() == 2) {
                        gs.setPlayerTurn(1);
                        gs.setActionsRemaining(3);
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
