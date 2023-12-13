package com.example.forbiddenislandgame;

import com.example.actions.FiCaptureTreasureAction;
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
                int randomNum = (int) (Math.random() * 4);//generate a random number 1-4

                if(randomNum == 1){//moves the dumb ai to a random tile
                    //gets a random tile to move to from the enum list
                    t = FiGameState.TileName.values()[new Random().nextInt(FiGameState.TileName.values().length)];
                    game.sendAction(new FiMoveAction(this, t));
                }
                else if(randomNum == 2){//shores up the tile they are on if it's possible
                    t = gameState.getPlayerLocation(gameState.getPlayerTurn());
                    game.sendAction(new FiShoreUpAction(this, t));
                }
                else if(randomNum == 3){//captures a treasure if they are able to
                    game.sendAction(new FiCaptureTreasureAction(this));
                }
                else{//gives a card to the next player
                    if(this.playerNum++ <= gameState.getNumPlayers()){//sets player chosen to the player whose turn is next
                        playerChosen++;
                    }

                    if(playerChosen != playerNum){//if the chosen player isn't themself
                        game.sendAction(new FiGiveCardAction(this, playerChosen, 0));
                    }
                }
            }
        }
    }
}
