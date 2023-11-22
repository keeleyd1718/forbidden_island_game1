package com.example.actions;

import com.example.forbiddenislandgame.Tile;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.players.GamePlayer;

public class FiMoveAction extends GameAction {

    private Tile.TileName t;

    /**
     * constructor for GameAction
     *
     * @param player    the player who created the action
     * @param selection
     */
    public FiMoveAction(GamePlayer player, Tile.TileName selection) {
        super(player);
        t = selection;
    }

    public Tile.TileName getTileName() {
        return t;
    }
}
