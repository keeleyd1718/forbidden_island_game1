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
        if (info instanceof FiGameState) {
            FiGameState gameState = (FiGameState) info;

            if(this.playerNum == gameState.getPlayerTurn()) {//checking if it is smart ai's turn
                if(gameState.areTreasuresCaptured()) {//if the 4 treasures have been collected
                    if(gameState.numHelicopterLiftCardsInHand[gameState.getPlayerTurn()] >= 1){//if the smart ai has at least 1 helicopter lift card
                        if(gameState.getPlayerLocation(gameState.getPlayerTurn()).equals(FiGameState.TileName.FOOLS_LANDING)){//if they're also on the FOOLS_LANDING tile
                            game.sendAction(new FiGameOverAction(this));//the game should be won
                        }
                    }
                    //else move to fools landing tile
                    game.sendAction(new FiMoveAction(this, FiGameState.TileName.FOOLS_LANDING));
                }
                else if(gameState.map.get(gameState.getPlayerLocation(gameState.getPlayerTurn())).equals(FiGameState.Value.FLOODED)) {//if the tile the player is on is flooded shore it up
                    game.sendAction(new FiShoreUpAction(this, gameState.getPlayerLocation(gameState.getPlayerTurn())));
                }
                else if(gameState.numWindStatueCardsInHand[gameState.getPlayerTurn()] >= 4){//if the smart ai has 4 treasure cards
                    if(gameState.isOnCorrectWSTile()){//and they are on the correct tile
                        game.sendAction(new FiCaptureTreasureAction(this));//capture the treasure
                    }

                    //if they are not on the correct tile move to a correct tile
                    game.sendAction(new FiMoveAction(this, FiGameState.TileName.HOWLING_GARDEN));
                }
                else if(gameState.numOceanChaliceCardsInHand[gameState.getPlayerTurn()] >= 4){//if the smart ai has 4 treasure cards
                    if(gameState.isOnCorrectOCTile()){//and they are on the correct tile
                        game.sendAction(new FiCaptureTreasureAction(this));//capture the treasure
                    }

                    //if they are not on the correct tile move to a correct tile
                    game.sendAction(new FiMoveAction(this, FiGameState.TileName.CORAL_PALACE));
                }
                else if(gameState.numFireCrystalCardsInHand[gameState.getPlayerTurn()] >= 4){//if the smart ai has 4 treasure cards
                        if(gameState.isOnCorrectFCTile()){//and they are on the correct tile
                            game.sendAction(new FiCaptureTreasureAction(this));//capture the treasure
                        }

                        //if they are not on the correct tile move to a correct tile
                    game.sendAction(new FiMoveAction(this, FiGameState.TileName.EMBER_CAVE));
                }
                else if(gameState.numEarthStoneCardsInHand[gameState.getPlayerTurn()] >= 4){//if the smart ai has 4 treasure cards
                    if(gameState.isOnCorrectESTile()){//and they are on the correct tile
                        game.sendAction(new FiCaptureTreasureAction(this));//capture the treasure
                    }

                    //if they are not on the correct tile move to a correct tile
                    game.sendAction(new FiMoveAction(this, FiGameState.TileName.MOON_TEMPLE));
                }
                else{//first check if smart ai can give a card away otherwise default to move
                    for(int i = 0; i < gameState.getNumPlayers(); i++) {//check if other players have 3 of the same card and smart ai has that card
                        for(int j = 0; j < gameState.getPlayerTurnHand(gameState.getPlayerTurn()).size(); j++) {
                            FiGameState.TreasureCards card = gameState.getPlayerTurnHand(gameState.getPlayerTurn()).get(j);

                            while (i != gameState.getPlayerTurn()) {
                                if (gameState.numWindStatueCardsInHand[i] == 3 && gameState.getWindStatueTreasureCards().contains(card)){
                                    game.sendAction(new FiGiveCardAction(this, i, j));
                                } else if (gameState.numFireCrystalCardsInHand[i] == 3 && gameState.getWindStatueTreasureCards().contains(card)){
                                    game.sendAction(new FiGiveCardAction(this, i, j));
                                } else if (gameState.numEarthStoneCardsInHand[i] == 3 && gameState.getWindStatueTreasureCards().contains(card)){
                                    game.sendAction(new FiGiveCardAction(this, i, j));
                                } else if (gameState.numOceanChaliceCardsInHand[i] == 3 && gameState.getWindStatueTreasureCards().contains(card)){
                                    game.sendAction(new FiGiveCardAction(this, i, j));
                                }
                            }
                        }
                    }

                    //move to a random tile
                    FiGameState.TileName t = FiGameState.TileName.values()[new Random().nextInt(FiGameState.TileName.values().length)];
                    game.sendAction(new FiMoveAction(this, t));
                }
            }
        }
    }
}