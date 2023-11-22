package com.example.actions;

import com.example.forbiddenislandgame.Tile;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.players.GamePlayer;

public class FiShoreUpAction extends GameAction {
    private Tile.TileName t;
    private Tile.Value v;
    /**
     * constructor for GameAction
     *
     * @param player    the player who created the action
     * @param selection
     */
    public FiShoreUpAction(GamePlayer player, Tile.TileName selection, Tile.Value sel) {
        super(player);
        t = selection;
        v = sel;
    }

    public Tile.Value getValue() {
        return v;
    }
    public Tile.TileName getTileName() {return t;}
}
