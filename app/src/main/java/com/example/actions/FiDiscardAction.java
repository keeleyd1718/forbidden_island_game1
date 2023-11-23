package com.example.actions;

import com.example.forbiddenislandgame.FiGameState;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.players.GamePlayer;

public class FiDiscardAction extends GameAction {
    private FiGameState.TreasureCards t;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     * @param sel
     */
    public FiDiscardAction(GamePlayer player, FiGameState.TreasureCards sel) {
        super(player);
        t = sel;
    }

    public FiGameState.TreasureCards getTreasureCardName() {
        return t;
    }
}
