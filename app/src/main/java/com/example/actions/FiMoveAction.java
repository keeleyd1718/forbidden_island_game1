package com.example.actions;

import com.example.forbiddenislandgame.FiGameState;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.players.GamePlayer;

public class FiMoveAction extends GameAction {

    private FiGameState.TileName t;

    /**
     * constructor for GameAction
     *
     * @param player    the player who created the action
     * @param selection
     */
    public FiMoveAction(GamePlayer player, FiGameState.TileName selection) {
        super(player);
        t = selection;
    }

    public FiGameState.TileName getTileName() {
        return t;
    }
}
