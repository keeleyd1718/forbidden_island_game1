package com.example.actions;

import com.example.forbiddenislandgame.FiGameState;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.players.GamePlayer;

public class FiGiveCardAction extends GameAction {
    private FiGameState.TreasureCards t;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public FiGiveCardAction(GamePlayer player) {
        super(player);
    }

    public FiGameState.TreasureCards getTreasureCardName() {
        return t;
    }
}
