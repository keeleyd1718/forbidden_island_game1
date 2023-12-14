package com.example.forbiddenislandgame;

import com.example.game.GameFramework.GameMainActivity;
import com.example.game.GameFramework.LocalGame;
import com.example.game.GameFramework.gameConfiguration.GameConfig;
import com.example.game.GameFramework.gameConfiguration.GamePlayerType;
import com.example.game.GameFramework.infoMessage.GameState;
import com.example.game.GameFramework.players.GamePlayer;

import java.util.ArrayList;

/*
    Tags
    @author - Keeley Dockter
    @author - Elias Marcelin
 */

public class MainActivity extends GameMainActivity{
    //The port number to be used IF network implementation is made
    private static final int PORT_NUMBER = 8080;

    @Override
    public GameConfig createDefaultConfig() {
        //Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        //Adds the human and computer types
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new HumanPlayer(name, R.layout.activity_main);
            }});
        playerTypes.add(new GamePlayerType("Base AI Player") {
            public GamePlayer createPlayer(String name) {
                return new DumbComputerPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Smart AI Player") {
            public GamePlayer createPlayer(String name) {
                return new SmartComputerPlayer(name);
            }});

        // Create a game configuration class for Forbidden Island:
        GameConfig defaultConfig = new GameConfig(playerTypes, 3, 3, "Forbidden Island", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Base AI Player", 1); // player 2: a dumb computer player
        defaultConfig.addPlayer("Smart AI Player", 2); // player 3: a smart computer player
        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return new FiLocalGame();
    }
}