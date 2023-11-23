package com.example.forbiddenislandgame;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiDrawFloodAction;
import com.example.actions.FiDrawTreasureAction;
import com.example.actions.FiGameOverAction;
import com.example.actions.FiGiveCardAction;
import com.example.actions.FiMoveAction;
import com.example.actions.FiShoreUpAction;
import com.example.game.GameFramework.GameMainActivity;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.players.GameHumanPlayer;

public class HumanPlayer extends GameHumanPlayer implements View.OnClickListener {
    private int layoutId;
    // all the buttons on the ui that can be pressed
    private TextView floodView = null;
    private Button quitButton = null;
    private Button deckButton = null;
    private Button drawTreasureButton = null;
    private Button drawFloodButton = null;
    private Button discardButton = null;
    private Button moveButton = null;
    private Button shoreUpButton = null;
    private Button giveCardButton = null;
    private Button captureTreasureButton = null;
    private Button FOOLS_LANDING = null;
    private Button BRONZE_GATE = null;
    private Button GOLD_GATE = null;
    private Button CORAL_PALACE = null;
    private Button SUN_TEMPLE = null;
    private Button SILVER_GATE = null;
    private Button PHANTOM_ROCK = null;
    private Button WATCHTOWER = null;
    private Button COPPER_GATE = null;
    private Button ABANDONED_CLIFFS = null;
    private Button WHISPERING_GARDENS = null;
    private Button SHADOW_CAVE = null;
    private Button LOST_LAGOON = null;
    private Button MOON_TEMPLE = null;
    private Button DECEPTION_DUNES = null;
    private Button TWILIGHT_HOLLOW = null;
    private Button EMBER_CAVE = null;
    private Button TIDAL_PALACE = null;
    private Button OBSERVATORY = null;
    private Button IRON_GATE = null;
    private Button CRIMSON_FOREST = null;
    private Button MISTY_MARSH = null;
    private Button BREAKERS_BRIDGE = null;
    private Button HOWLING_GARDEN = null;
    private GameMainActivity myActivity;
    private boolean moveButtonClicked = false;
    private boolean shoreUpButtonClicked = false;
    int gameGreen = Color.rgb(63, 179, 66);

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public HumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    @Override
    public View getTopView() {
        return null;
        //return myActivity.findViewbyId(R.id.top_gui_layout);
    }

    //our view in the model/view/controller sense; in charge of the user interface
    public void receiveInfo(GameInfo info){
        //should decide what move to make; just needs to update the user interface; drawing happens here

        if(info instanceof FiGameState){
            FiGameState gameState = (FiGameState) info;

            //setting the initial start places for the players
            ABANDONED_CLIFFS.setText("player 1");
            DECEPTION_DUNES.setText("player 2");
            OBSERVATORY.setText("player 3");

            //set the tile player's must be on to capture certain treasures
            CORAL_PALACE.setText("Ocean Chalice Treasure");
            TIDAL_PALACE.setText("Ocean Chalice Treasure");
            SHADOW_CAVE.setText("Fire Crystal Treasure");
            EMBER_CAVE.setText("Fire Crystal Treasure");
            MOON_TEMPLE.setText("Earth Stone Treasure");
            SUN_TEMPLE.setText("Earth Stone Treasure");
            HOWLING_GARDEN.setText("Wind Statue Treasure");
            WHISPERING_GARDENS.setText("Wind Statue Treasure");

            //changing the text on a button to show where the pawns are
            FiGameState.TileName t = gameState.getPlayer1Location();
            Button b = null;
            switch(t)
            {
                case ABANDONED_CLIFFS:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.ABANDONED_CLIFFS).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.ABANDONED_CLIFFS, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.ABANDONED_CLIFFS).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.ABANDONED_CLIFFS, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.ABANDONED_CLIFFS).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.ABANDONED_CLIFFS, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.ABANDONED_CLIFFS;
                    break;
                case BRONZE_GATE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.BRONZE_GATE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.BRONZE_GATE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.BRONZE_GATE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.BRONZE_GATE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.BRONZE_GATE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.BRONZE_GATE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.BRONZE_GATE;
                    break;
                case BREAKERS_BRIDGE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.BREAKERS_BRIDGE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.BREAKERS_BRIDGE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.BREAKERS_BRIDGE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.BREAKERS_BRIDGE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.BREAKERS_BRIDGE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.BREAKERS_BRIDGE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.BREAKERS_BRIDGE;
                    break;
                case COPPER_GATE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.COPPER_GATE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.COPPER_GATE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.COPPER_GATE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.COPPER_GATE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.COPPER_GATE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.COPPER_GATE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.COPPER_GATE;
                    break;
                case CORAL_PALACE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.CORAL_PALACE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.CORAL_PALACE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.CORAL_PALACE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.CORAL_PALACE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.CORAL_PALACE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.CORAL_PALACE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.CORAL_PALACE;
                    break;
                case CRIMSON_FOREST:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.CRIMSON_FOREST).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.CRIMSON_FOREST, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.CRIMSON_FOREST).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.CRIMSON_FOREST, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.CRIMSON_FOREST).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.CRIMSON_FOREST, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.CRIMSON_FOREST;
                    break;
                case DECEPTION_DUNES:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.DECEPTION_DUNES).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.DECEPTION_DUNES, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.DECEPTION_DUNES).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.DECEPTION_DUNES, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.DECEPTION_DUNES).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.DECEPTION_DUNES, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.DECEPTION_DUNES;
                    break;
                case EMBER_CAVE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.EMBER_CAVE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.EMBER_CAVE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.EMBER_CAVE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.EMBER_CAVE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.EMBER_CAVE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.EMBER_CAVE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.EMBER_CAVE;
                    break;
                case FOOLS_LANDING:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.FOOLS_LANDING).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.FOOLS_LANDING, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.FOOLS_LANDING).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.FOOLS_LANDING, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.FOOLS_LANDING).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.FOOLS_LANDING, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.FOOLS_LANDING;
                    break;
                case GOLD_GATE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.GOLD_GATE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.GOLD_GATE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.GOLD_GATE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.GOLD_GATE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.GOLD_GATE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.GOLD_GATE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.GOLD_GATE;
                    break;
                case IRON_GATE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.IRON_GATE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.IRON_GATE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.IRON_GATE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.IRON_GATE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.IRON_GATE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.IRON_GATE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.IRON_GATE;
                    break;
                case HOWLING_GARDEN:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.HOWLING_GARDEN).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.HOWLING_GARDEN, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.HOWLING_GARDEN).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.HOWLING_GARDEN, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.HOWLING_GARDEN).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.HOWLING_GARDEN, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.HOWLING_GARDEN;
                    break;
                case MISTY_MARSH:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.MISTY_MARSH).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.MISTY_MARSH, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.MISTY_MARSH).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.MISTY_MARSH, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.MISTY_MARSH).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.MISTY_MARSH, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.MISTY_MARSH;
                    break;
                case MOON_TEMPLE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.MOON_TEMPLE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.MOON_TEMPLE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.MOON_TEMPLE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.MOON_TEMPLE;
                    break;
                case SILVER_GATE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.SILVER_GATE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.SILVER_GATE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.SILVER_GATE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.SILVER_GATE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.SILVER_GATE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.SILVER_GATE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.SILVER_GATE;
                    break;
                case SUN_TEMPLE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.SUN_TEMPLE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.SUN_TEMPLE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.SUN_TEMPLE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.SUN_TEMPLE;
                    break;
                case PHANTOM_ROCK:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.PHANTOM_ROCK).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.PHANTOM_ROCK, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.PHANTOM_ROCK).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.PHANTOM_ROCK, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.PHANTOM_ROCK).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.PHANTOM_ROCK, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.PHANTOM_ROCK;
                    break;
                case WHISPERING_GARDENS:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.WHISPERING_GARDENS).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.WHISPERING_GARDENS, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.WHISPERING_GARDENS).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.WHISPERING_GARDENS, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.WHISPERING_GARDENS).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.WHISPERING_GARDENS, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.WHISPERING_GARDENS;
                    break;
                case WATCHTOWER:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.WATCHTOWER).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.WATCHTOWER, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.WATCHTOWER).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.WATCHTOWER, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.WATCHTOWER).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.WATCHTOWER, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.WATCHTOWER;
                    break;
                case TWILIGHT_HOLLOW:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.TWILIGHT_HOLLOW).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.TWILIGHT_HOLLOW, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.TWILIGHT_HOLLOW).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.TWILIGHT_HOLLOW, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.TWILIGHT_HOLLOW).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.TWILIGHT_HOLLOW, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.TWILIGHT_HOLLOW;
                    break;
                case TIDAL_PALACE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.TIDAL_PALACE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.TIDAL_PALACE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.TIDAL_PALACE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.TIDAL_PALACE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.TIDAL_PALACE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.TIDAL_PALACE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.TIDAL_PALACE;
                    break;
                case OBSERVATORY:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.OBSERVATORY).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.OBSERVATORY, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.OBSERVATORY).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.OBSERVATORY, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.OBSERVATORY).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.OBSERVATORY, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.OBSERVATORY;
                    break;
                case LOST_LAGOON:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.LOST_LAGOON).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.LOST_LAGOON, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.LOST_LAGOON).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.LOST_LAGOON, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.LOST_LAGOON).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.LOST_LAGOON, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.LOST_LAGOON;
                    break;
                case SHADOW_CAVE:
                    //for the draw flood card method; changing whatever tiles are drawn
                    if(gameState.map.get(FiGameState.TileName.SHADOW_CAVE).equals(FiGameState.Value.NORMAL)){
                        gameState.map.put(FiGameState.TileName.SHADOW_CAVE, FiGameState.Value.FLOODED);
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.SHADOW_CAVE).equals(FiGameState.Value.FLOODED)){
                        gameState.map.put(FiGameState.TileName.SHADOW_CAVE, FiGameState.Value.SUNK);
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    //for the shore up method
                    else if(gameState.map.get(FiGameState.TileName.SHADOW_CAVE).equals(FiGameState.Value.SUNK)){
                        gameState.map.put(FiGameState.TileName.SHADOW_CAVE, FiGameState.Value.NORMAL);
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    b = this.SHADOW_CAVE;
                    break;
                default:
                    b = null;
            }
            if(b != null)
            {
                b.setText(gameState.getPlayerTurn() + "'s pawn");
            }

            this.floodView.setText("Flood Meter: "+gameState.getFloodMeter());
        }
    }

    @Override
    public void onClick(View view) {
        //will decide what move the player made
        if(view.getId() == R.id.quitButton){
            game.sendAction(new FiGameOverAction(this));
        }
        else if(view.getId() == R.id.drawTreasureButton){
            game.sendAction(new FiDrawTreasureAction(this));
        }
        else if(view.getId() == R.id.drawFloodButton){
            game.sendAction(new FiDrawFloodAction(this));
        }
        else if(view.getId() == R.id.moveButton){
            moveButtonClicked = true;
            return;
        }
        else if(view.getId() == R.id.shoreUpButton){
            shoreUpButtonClicked = true;
            return;
        }
        else if(view.getId() == R.id.giveCardButton){
            game.sendAction(new FiGiveCardAction(this));
        }
        else if(view.getId() == R.id.captureTreasureButton){
            game.sendAction(new FiCaptureTreasureAction(this));
        }
        FiGameState.TileName selection;
        FiGameState.Value sel;
        switch(view.getId()){
            case R.id.ABANDONED_CLIFFS:
                selection = FiGameState.TileName.ABANDONED_CLIFFS;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.BRONZE_GATE:
                selection = FiGameState.TileName.BRONZE_GATE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.BREAKERS_BRIDGE:
                selection = FiGameState.TileName.BREAKERS_BRIDGE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.COPPER_GATE:
                selection = FiGameState.TileName.COPPER_GATE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.CORAL_PALACE:
                selection = FiGameState.TileName.CORAL_PALACE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.CRIMSON_FOREST:
                selection = FiGameState.TileName.CRIMSON_FOREST;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.DECEPTION_DUNES:
                selection = FiGameState.TileName.DECEPTION_DUNES;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.EMBER_CAVE:
                selection = FiGameState.TileName.EMBER_CAVE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.FOOLS_LANDING:
                selection = FiGameState.TileName.FOOLS_LANDING;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.GOLD_GATE:
                selection = FiGameState.TileName.GOLD_GATE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.IRON_GATE:
                selection = FiGameState.TileName.IRON_GATE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.HOWLING_GARDEN:
                selection = FiGameState.TileName.HOWLING_GARDEN;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.MISTY_MARSH:
                selection = FiGameState.TileName.MISTY_MARSH;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.MOON_TEMPLE:
                selection = FiGameState.TileName.MOON_TEMPLE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.SILVER_GATE:
                selection = FiGameState.TileName.SILVER_GATE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.SUN_TEMPLE:
                selection = FiGameState.TileName.SUN_TEMPLE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.PHANTOM_ROCK:
                selection = FiGameState.TileName.PHANTOM_ROCK;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.WHISPERING_GARDENS:
                selection = FiGameState.TileName.WHISPERING_GARDENS;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.WATCHTOWER:
                selection = FiGameState.TileName.WATCHTOWER;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.TWILIGHT_HOLLOW:
                selection = FiGameState.TileName.TWILIGHT_HOLLOW;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.TIDAL_PALACE:
                selection = FiGameState.TileName.TIDAL_PALACE;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.OBSERVATORY:
                selection = FiGameState.TileName.OBSERVATORY;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.LOST_LAGOON:
                selection = FiGameState.TileName.LOST_LAGOON;
                sel = FiGameState.Value.NORMAL;
                break;
            case R.id.SHADOW_CAVE:
                selection = FiGameState.TileName.SHADOW_CAVE;
                sel = FiGameState.Value.NORMAL;
                break;
            default:
                selection = FiGameState.TileName.NONE;
                sel = FiGameState.Value.NONE;
        }
        if(moveButtonClicked && selection != FiGameState.TileName.NONE)
        {
            moveButtonClicked = false;
            game.sendAction(new FiMoveAction(this, selection));
        }
        else if(shoreUpButtonClicked && selection != FiGameState.TileName.NONE && sel != FiGameState.Value.NONE)
        {
            shoreUpButtonClicked = false;
            game.sendAction(new FiShoreUpAction(this, selection, sel));
        }
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        //remember the activity
        myActivity = activity;

        //layout resource for gui
        activity.setContentView(R.layout.activity_main);

        //initializing action buttons
        this.floodView = activity.findViewById(R.id.floodView);
        this.quitButton = activity.findViewById(R.id.quitButton);
        this.deckButton = activity.findViewById(R.id.floodDeck);
        this.discardButton = activity.findViewById(R.id.discard);
        this.drawTreasureButton = activity.findViewById(R.id.drawTreasureButton);
        this.drawFloodButton = activity.findViewById(R.id.drawFloodButton);
        this.moveButton = activity.findViewById(R.id.moveButton);
        this.shoreUpButton = activity.findViewById(R.id.shoreUpButton);
        this.giveCardButton = activity.findViewById(R.id.giveCardButton);
        this.captureTreasureButton = activity.findViewById(R.id.captureTreasureButton);

        //initializing tile buttons
        this.FOOLS_LANDING = activity.findViewById(R.id.FOOLS_LANDING);
        this.BRONZE_GATE = (Button)activity.findViewById(R.id.BRONZE_GATE);
        this.GOLD_GATE = (Button)activity.findViewById(R.id.GOLD_GATE);
        this.CORAL_PALACE = (Button)activity.findViewById(R.id.CORAL_PALACE);
        this.SUN_TEMPLE = (Button)activity.findViewById(R.id.SUN_TEMPLE);
        this.SILVER_GATE = (Button)activity.findViewById(R.id.SILVER_GATE);
        this.PHANTOM_ROCK = (Button)activity.findViewById(R.id.PHANTOM_ROCK);
        this.WATCHTOWER = (Button)activity.findViewById(R.id.WATCHTOWER);
        this.COPPER_GATE = (Button)activity.findViewById(R.id.COPPER_GATE);
        this.ABANDONED_CLIFFS = (Button)activity.findViewById(R.id.ABANDONED_CLIFFS);
        this.WHISPERING_GARDENS = (Button)activity.findViewById(R.id.WHISPERING_GARDENS);
        this.SHADOW_CAVE = (Button)activity.findViewById(R.id.SHADOW_CAVE);
        this.LOST_LAGOON = (Button)activity.findViewById(R.id.LOST_LAGOON);
        this.MOON_TEMPLE = (Button)activity.findViewById(R.id.MOON_TEMPLE);
        this.DECEPTION_DUNES = (Button)activity.findViewById(R.id.DECEPTION_DUNES);
        this.TWILIGHT_HOLLOW = (Button)activity.findViewById(R.id.TWILIGHT_HOLLOW);
        this.EMBER_CAVE = (Button)activity.findViewById(R.id.EMBER_CAVE);
        this.TIDAL_PALACE = (Button)activity.findViewById(R.id.TIDAL_PALACE);
        this.OBSERVATORY = (Button)activity.findViewById(R.id.OBSERVATORY);
        this.IRON_GATE = (Button)activity.findViewById(R.id.IRON_GATE);
        this.CRIMSON_FOREST = (Button)activity.findViewById(R.id.CRIMSON_FOREST);
        this.MISTY_MARSH = (Button)activity.findViewById(R.id.MISTY_MARSH);
        this.BREAKERS_BRIDGE = (Button)activity.findViewById(R.id.BREAKERS_BRIDGE);
        this.HOWLING_GARDEN = (Button)activity.findViewById(R.id.HOWLING_GARDEN);

        //if an action button is pressed call the onClickListener method
        quitButton.setOnClickListener(this);
        deckButton.setOnClickListener(this);
        drawTreasureButton.setOnClickListener(this);
        drawFloodButton.setOnClickListener(this);
        moveButton.setOnClickListener(this);
        shoreUpButton.setOnClickListener(this);
        giveCardButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);
        captureTreasureButton.setOnClickListener(this);
    }
}
