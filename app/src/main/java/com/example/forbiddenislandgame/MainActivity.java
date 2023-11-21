package com.example.forbiddenislandgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.game.GameFramework.GameMainActivity;
import com.example.game.GameFramework.LocalGame;
import com.example.game.GameFramework.actionMessage.GameAction;
import com.example.game.GameFramework.gameConfiguration.GameConfig;
import com.example.game.GameFramework.gameConfiguration.GamePlayerType;
import com.example.game.GameFramework.infoMessage.GameState;
import com.example.game.GameFramework.players.GamePlayer;

import java.util.ArrayList;

public class MainActivity extends GameMainActivity{
    //The port number to be used IF network implementation is made
    private static final int PORT_NUMBER = 8080;

    @Override
    public GameConfig createDefaultConfig() {
        //Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<>();

        //Adds the human and computer types
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new HumanPlayer(name, R.layout.activity_main);
            }});
        playerTypes.add(new GamePlayerType("Smart AI Player") {
            public GamePlayer createPlayer(String name) {return new DumbComputerPlayer(name);
            }});
        playerTypes.add(new GamePlayerType("Base AI Player") {
            public GamePlayer createPlayer(String name) {return new DumbComputerPlayer(name);
            }});

        // Create a game configuration class for Forbidden Island:
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "Forbidden Island", PORT_NUMBER);
        defaultConfig.addPlayer("Human", 0); // player 1: a human player
        defaultConfig.addPlayer("Computer", 1); // player 2: a computer player

        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return new FiLocalGame();
    }
}