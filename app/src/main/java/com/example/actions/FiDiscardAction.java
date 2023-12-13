package com.example.actions;

import com.example.forbiddenislandgame.FiGameState;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.players.GamePlayer;

public class FiDiscardAction extends GameAction {
    private int index;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     * @param indexOfCard the index of the card the player clicked to discard
     */
    public FiDiscardAction(GamePlayer player, int indexOfCard) {
        super(player);
        index = indexOfCard;
    }

    public int getIndexOfCard() {
        return index;
    }
}
