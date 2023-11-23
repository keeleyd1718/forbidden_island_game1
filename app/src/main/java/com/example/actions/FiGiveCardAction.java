package com.example.actions;

import com.example.forbiddenislandgame.FiGameState;
import com.example.forbiddenislandgame.HumanPlayer;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.players.GamePlayer;

public class FiGiveCardAction extends GameAction {
    private FiGameState.TreasureCards t;
    private int p;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public FiGiveCardAction(GamePlayer player, int playerChosen, FiGameState.TreasureCards sel) {
        super(player);
        t = sel;
        p = playerChosen;
    }

    public FiGameState.TreasureCards getTreasureCardName() {
        return t;
    }
}
