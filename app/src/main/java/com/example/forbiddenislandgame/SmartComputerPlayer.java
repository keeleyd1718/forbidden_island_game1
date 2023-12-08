package com.example.forbiddenislandgame;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiEndTurnAction;
import com.example.actions.FiGameOverAction;
import com.example.actions.FiGiveCardAction;
import com.example.actions.FiMoveAction;
import com.example.actions.FiShoreUpAction;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.players.GameComputerPlayer;

import java.util.Random;

public class SmartComputerPlayer extends GameComputerPlayer {
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public SmartComputerPlayer(String name) {
        super(name);
    }

    protected void receiveInfo(GameInfo info) {
        //message gets the state from LocalGame and decides what move it's making; needs to call game.sendAction at some point
        if (info instanceof FiGameState) {
            FiGameState gameState = (FiGameState) info;

            if(this.playerNum == gameState.getPlayerTurn()) {//checking if it is smart ai's turn
                if(gameState.areTreasuresCaptured()) {//if the 4 treasures have been collected
                    if(gameState.getPlayerTurnHand(gameState.getPlayerTurn()).contains(FiGameState.TreasureCards.HELICOPTER_LIFT1) || gameState.getPlayerTurnHand(gameState.getPlayerTurn()).contains(FiGameState.TreasureCards.HELICOPTER_LIFT2) || gameState.getPlayerTurnHand(gameState.getPlayerTurn()).contains(FiGameState.TreasureCards.HELICOPTER_LIFT3)){
                        if(gameState.getPlayerLocation(gameState.getPlayerTurn()).equals(FiGameState.TileName.FOOLS_LANDING)){//if they're also on the FOOLS_LANDING tile the game is won
                            game.sendAction(new FiGameOverAction(this));
                        }
                    }
                    //move to fools landing tile
                    game.sendAction(new FiMoveAction(this, FiGameState.TileName.FOOLS_LANDING));
                }
                else if(gameState.map.get(gameState.getPlayerLocation(gameState.getPlayerTurn())).equals(FiGameState.Value.FLOODED)) {//if the tile the player is on is flooded shore it up
                    game.sendAction(new FiShoreUpAction(this, gameState.getPlayerLocation(gameState.getPlayerTurn())));
                }
                else if(gameState.numWindStatueCardsInHand[gameState.getPlayerTurn()] == 3){

                    //check if other players have 3 of the same card && player has that card
                    for(int i = 0; i < gameState.getPlayerTurnHand(gameState.getPlayerTurn()).size(); i++) {
                        FiGameState.TreasureCards card = gameState.getPlayerTurnHand(gameState.getPlayerTurn()).get(i);

                        if(gameState.getWindStatueTreasureCards().contains(card) && gameState.numWindStatueCardsInHand[gameState.getPlayerTurn()] > 1){
                            game.sendAction(new FiGiveCardAction(this, gameState.getPlayerTurn(), card));
                        }
                        else if(gameState.getFireCrystalTreasureCards().contains(card) && gameState.numFireCrystalCardsInHand[gameState.getPlayerTurn()] > 1){
                            game.sendAction(new FiGiveCardAction(this, gameState.getPlayerTurn(), card));
                        }
                        else if(gameState.getEarthStoneTreasureCards().contains(card) && gameState.numEarthStoneCardsInHand[gameState.getPlayerTurn()] > 1){
                            game.sendAction(new FiGiveCardAction(this, gameState.getPlayerTurn(), card));
                        }
                        else if(gameState.getOceanChaliceTreasureCards().contains(card) && gameState.numOceanChaliceCardsInHand[gameState.getPlayerTurn()] > 1){
                            game.sendAction(new FiGiveCardAction(this, gameState.getPlayerTurn(), card));
                        }
                    }
                }
                else if(gameState.numWindStatueCardsInHand[gameState.getPlayerTurn()] >= 4){//if the smart ai has 4 treasure cards
                    if(gameState.getPlayerLocation(gameState.getPlayerTurn()).equals(FiGameState.TileName.WHISPERING_GARDENS) || gameState.getPlayerLocation(gameState.getPlayerTurn()).equals(FiGameState.TileName.HOWLING_GARDEN)){
                        //and they are on the correct tile capture the treasure
                        game.sendAction(new FiCaptureTreasureAction(this));
                    }
                    //if they are not on the correct tile move to a correct tile
                    game.sendAction(new FiMoveAction(this, FiGameState.TileName.HOWLING_GARDEN));
                }
                else if(gameState.numOceanChaliceCardsInHand[gameState.getPlayerTurn()] >= 4){
                    if(gameState.getPlayerLocation(gameState.getPlayerTurn()).equals(FiGameState.TileName.CORAL_PALACE) || gameState.getPlayerLocation(gameState.getPlayerTurn()).equals(FiGameState.TileName.TIDAL_PALACE)){
                        //and they are on the correct tile capture the treasure
                        game.sendAction(new FiCaptureTreasureAction(this));
                    }
                    //if they are not on the correct tile move to a correct tile
                    game.sendAction(new FiMoveAction(this, FiGameState.TileName.CORAL_PALACE));
                }
                else if(gameState.numFireCrystalCardsInHand[gameState.getPlayerTurn()] >= 4){//if the smart ai has 4 treasure cards
                    if(gameState.getPlayerLocation(gameState.getPlayerTurn()).equals(FiGameState.TileName.EMBER_CAVE) || gameState.getPlayerLocation(gameState.getPlayerTurn()).equals(FiGameState.TileName.SHADOW_CAVE)){
                        //and they are on the correct tile capture the treasure
                        game.sendAction(new FiCaptureTreasureAction(this));
                    }
                    //if they are not on the correct tile move to a correct tile
                    game.sendAction(new FiMoveAction(this, FiGameState.TileName.EMBER_CAVE));
                }
                else if(gameState.numEarthStoneCardsInHand[gameState.getPlayerTurn()] >= 4){//if the smart ai has 4 treasure cards
                    if(gameState.getPlayerLocation(gameState.getPlayerTurn()).equals(FiGameState.TileName.MOON_TEMPLE) || gameState.getPlayerLocation(gameState.getPlayerTurn()).equals(FiGameState.TileName.SUN_TEMPLE)){
                        //and they are on the correct tile capture the treasure
                        game.sendAction(new FiCaptureTreasureAction(this));
                    }
                    //if they are not on the correct tile move to a correct tile
                    game.sendAction(new FiMoveAction(this, FiGameState.TileName.MOON_TEMPLE));
                }
                else{//move to a random tile
                    FiGameState.TileName t = FiGameState.TileName.values()[new Random().nextInt(FiGameState.TileName.values().length)];
                    game.sendAction(new FiMoveAction(this, t));
                }
            }
        }
    }
}