package com.example.forbiddenislandgame;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiDrawFloodAction;
import com.example.actions.FiDrawTreasureAction;
import com.example.actions.FiEndTurnAction;
import com.example.actions.FiGiveCardAction;
import com.example.actions.FiMoveAction;
import com.example.actions.FiShoreUpAction;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.players.GameComputerPlayer;

import java.util.Random;

public class SmartComputerPlayer extends GameComputerPlayer {
    private FiGameState gameState;

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

                game.sendAction(new FiDrawTreasureAction(this));//if it is then draw two cards from the treasure deck

                while(gameState.getActionsRemaining() > 0) {
                    if(gameState.oceanChaliceTreasure && gameState.windStatueTreasure && gameState.fireCrystalTreasure && gameState.earthStoneTreasure) {//if the 4 treasures have been collected
                        //move to fools landing tile
                        game.sendAction(new FiMoveAction(this, FiGameState.TileName.FOOLS_LANDING));
                    }
                    else if(gameState.map.get(gameState.getPlayerLocation(gameState.getPlayerTurn())).equals(FiGameState.Value.FLOODED)) {//if the tile the player is on is flooded shore it up
                        game.sendAction(new FiShoreUpAction(this, gameState.getPlayerLocation(gameState.getPlayerTurn())));
                    }
                    //else if(check if other players have 3 of the same card && player has that card){
                      //  game.sendAction(new FiGiveCardAction(this, ));
                    //}
                    else if(gameState.numWindStatueCards[gameState.getPlayerTurn() - 1] >= 4 || gameState.numFireCrystalCards[gameState.getPlayerTurn() - 1] >= 4 || gameState.numOceanChaliceCards[gameState.getPlayerTurn() - 1] >= 4  || gameState.numEarthStoneCards[gameState.getPlayerTurn() - 1] >= 4){
                        //if the smart ai has 4 treasure cards attempt to capture a treasure
                        game.sendAction(new FiCaptureTreasureAction(this));
                    }
                    else{//move to a random tile
                        FiGameState.TileName t = FiGameState.TileName.values()[new Random().nextInt(FiGameState.TileName.values().length)];

                        //if the TileName enum value is randomly set to the TileName none they stay where they are
                        if(t == FiGameState.TileName.NONE){
                            t = gameState.getPlayerLocation(gameState.getPlayerTurn());
                        }
                        game.sendAction(new FiMoveAction(this, t));
                    }
                }
                game.sendAction(new FiDrawFloodAction(this));//then the smart ai draws two flood cards
                game.sendAction(new FiEndTurnAction(this));//end their turn
            }
        }
    }
}

/*
        if all 4 treasures have been captured, the ai is on the fools landing tile, and the ai has a helicopter lift card
            send game over action
        if all 4 treasures have been captured
            the ai moves to the FOOLS_LANDING tile
        if the ai has 4 treasure cards
            if the ai is on the correct tile
                send captureTreasureAction
            move to the correct tile
        if other players have 3 of the same card and the ai has that card
            give the card to the player
        if the tile the ai is on is flooded
            shore it up and flip it over
        else
            move to a random tile
 */