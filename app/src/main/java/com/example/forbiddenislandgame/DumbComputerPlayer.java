package com.example.forbiddenislandgame;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiDrawFloodAction;
import com.example.actions.FiDrawTreasureAction;
import com.example.actions.FiGiveCardAction;
import com.example.actions.FiMoveAction;
import com.example.actions.FiShoreUpAction;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.players.GameComputerPlayer;

public class DumbComputerPlayer extends GameComputerPlayer {
    private FiGameState gameState;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public DumbComputerPlayer(String name) {
        super(name);
    }

    protected void receiveInfo(GameInfo info){
        //message gets the state from LocalGame and decides what move it's making
        //needs to call game.sendAction at some point
        if(info instanceof FiGameState) {
            FiGameState gameState = (FiGameState) info;
            if(this.playerNum == gameState.getPlayerTurn()){//checking if it is dumb ai's turn
                game.sendAction(new FiDrawTreasureAction(this));//if it is then draw two cards from the treasure deck
                int randomNum = (int) (Math.random() * 4);//generate a random 1-4

                //dumb ai only does 1 random action
                if(randomNum == 1){
                    game.sendAction(new FiMoveAction(this));
                }
                else if(randomNum == 2){
                    game.sendAction(new FiShoreUpAction(this));
                }
                else if(randomNum == 3){
                    game.sendAction(new FiCaptureTreasureAction(this));
                }
                else {
                    game.sendAction(new FiGiveCardAction(this));
                }
                game.sendAction(new FiDrawFloodAction(this));//then the dumb ai draws two flood cards
            }
        }
    }
}
