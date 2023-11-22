package com.example.actions;

import com.example.forbiddenislandgame.FiGameState;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.players.GamePlayer;

public class FiShoreUpAction extends GameAction {
    private FiGameState.TileName t;
    private FiGameState.Value v;
    /**
     * constructor for GameAction
     *
     * @param player    the player who created the action
     * @param selection
     */
    public FiShoreUpAction(GamePlayer player, FiGameState.TileName selection, FiGameState.Value sel) {
        super(player);
        t = selection;
        v = sel;
    }

    public FiGameState.Value getValue() {
        return v;
    }
    public FiGameState.TileName getTileName() {return t;}
}
