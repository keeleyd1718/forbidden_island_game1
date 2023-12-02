package com.example.forbiddenislandgame;

import com.example.actions.FiDrawFloodAction;
import com.example.actions.FiDrawTreasureAction;
import com.example.actions.FiEndTurnAction;
import com.example.actions.FiMoveAction;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.players.GameComputerPlayer;

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

    protected void receiveInfo(GameInfo info){
        //message gets the state from LocalGame and decides what move it's making
        //needs to call game.sendAction at some point
        if(info instanceof FiGameState) {
            FiGameState gameState = (FiGameState) info;
            if(this.playerNum == gameState.getPlayerTurn()){//checking if it is dumb ai's turn
                game.sendAction(new FiDrawTreasureAction(this));//if it is then draw two cards from the treasure deck
                while (gameState.getActionsRemaining() < 4) {
                    /*if(gameState.getTreasureCount() == 4){
                        //move towards fools landing tile
                        game.sendAction(new FiMoveAction(this, selection));
                    }
                    if(/*check if holding 4 cards){
                        if(/*check if cards are same){
                            game.sendAction(/*draw treasure);
                        }
                        else if(gameState.getActionChoices() <4){
                            //looks for and attempts to move towards tile with the card the hand has the most of
                        }
                    }
                    else if(/*check if human player has 4 cards){
                        if(/*check if cards are same){
                            if(/*check if card is in treasure deck) {
                                game.sendAction(/*give card);
                                gameState.setActionChoices(gameState.getActionChoices()+1);
                            }
                        }
                    }*/
                }

                game.sendAction(new FiDrawFloodAction(this));//then the dumb ai draws two flood cards
                game.sendAction(new FiEndTurnAction(this));//end their turn
            }
        }
    }
}