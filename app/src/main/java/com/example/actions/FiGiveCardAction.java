package com.example.actions;

import com.example.forbiddenislandgame.FiGameState;
import com.example.forbiddenislandgame.HumanPlayer;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.players.GamePlayer;

public class FiGiveCardAction extends GameAction {
    private int index;
    private int p;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     * @param playerChosen the player chosen to get the card to
     * @param indexOfCard the index of the card the player clicked to give away
     */
    public FiGiveCardAction(GamePlayer player, int playerChosen, int indexOfCard) {
        super(player);
        index = indexOfCard;
        p = playerChosen;
    }

    public int getIndexOfCard() {
        return index;
    }
    public int getPlayerChosen() {
        return p;
    }
}
