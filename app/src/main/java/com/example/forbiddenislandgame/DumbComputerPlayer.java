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
        //message gets the state from LocalGame and decides what move it's making; needs to call game.sendAction at some point
        if(info instanceof FiGameState) {
            FiGameState gameState = (FiGameState) info;
            FiGameState.TileName t;

            if(this.playerNum == gameState.getPlayerTurn()){//checking if it is dumb ai's turn
                int randomNum = (int) (Math.random() * 4);//generate a random number 1-4

                if(randomNum == 1){//moves the dumb ai to a random tile
                    //gets a random tile to move to from the enum list
                    t = FiGameState.TileName.values()[new Random().nextInt(FiGameState.TileName.values().length)];

                    //if the TileName enum value is randomly set to the TileName none they stay where they are
                    if(t == FiGameState.TileName.NONE){
                        t = gameState.getPlayerLocation(gameState.getPlayerTurn());
                    }
                    game.sendAction(new FiMoveAction(this, t));
                }
                else if(randomNum == 2){//shores up the tile they are on if it's possible
                    t = gameState.getPlayerLocation(gameState.getPlayerTurn());
                    game.sendAction(new FiShoreUpAction(this, t));
                }
                else if(randomNum == 3){//captures a treasure if they are able to
                    game.sendAction(new FiCaptureTreasureAction(this));
                }
                else{//sets player chosen to the player whose turn is next
                    if(this.playerNum++ >= gameState.numPlayers){
                        gameState.setPlayerChosen(0);
                    }
                    else{
                        gameState.setPlayerChosen(this.playerNum++);
                    }

                    //getting the first card in the dumb ai's hand
                    FiGameState.TreasureCards tc = gameState.getPlayerTurnHand(gameState.getPlayerTurn()).get(0);

                    //gives the first card in the dumb ai's hand to the player whose turn it is next
                    game.sendAction(new FiGiveCardAction(this, gameState.playerChosen, tc));
                }
            }
        }
    }
}
