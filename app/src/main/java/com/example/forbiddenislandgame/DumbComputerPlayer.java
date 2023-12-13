package com.example.forbiddenislandgame;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiDiscardAction;
import com.example.actions.FiEndTurnAction;
import com.example.actions.FiGiveCardAction;
import com.example.actions.FiMoveAction;
import com.example.actions.FiShoreUpAction;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.players.GameComputerPlayer;

import java.util.Random;

public class DumbComputerPlayer extends GameComputerPlayer {
    private int playerChosen = 0;//defaults to player 1
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public DumbComputerPlayer(String name) {
        super(name);
    }

    //dumb ai only does 1 random action
    protected void receiveInfo(GameInfo info){
        if(info instanceof FiGameState) {
            FiGameState gameState = (FiGameState) info;
            FiGameState.TileName t;

            if(this.playerNum == gameState.getPlayerTurn()){//checking if it is dumb ai's turn
                int randomNum = (int) Math.random()*2;//generate a random number 0-1

                //gets a random tile to move to from the enum list
                t = FiGameState.TileName.values()[new Random().nextInt(FiGameState.TileName.values().length)];
                game.sendAction(new FiMoveAction(this, t));

                //captures treasure if able to
                if(gameState.getEarthStoneTreasureCards().size() <= 4 || gameState.getOceanChaliceTreasureCards().size() <= 4 || gameState.getWindStatueTreasureCards().size() <=4 || gameState.getFireCrystalTreasureCards().size() <= 4){
                    game.sendAction(new FiCaptureTreasureAction(this));
                }

                //if too many cards, will discard or give a card at random
                if(gameState.getPlayerHand(gameState.getPlayerTurn()).size() >= 5){//gives a card to the next player
                    if(randomNum == 0) {
                        if (this.playerNum++ <= gameState.getNumPlayers()) {//sets player chosen to the player whose turn is next
                            playerChosen++;
                        }

                        if (playerChosen != playerNum) {//if the chosen player isn't themself
                            game.sendAction(new FiGiveCardAction(this, playerChosen, 0));
                        }
                    }
                    if(randomNum == 1){
                        game.sendAction(new FiDiscardAction(this, 0));
                    }
                }
            }
        }
    }
}
