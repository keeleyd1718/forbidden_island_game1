package com.example.forbiddenislandgame;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiDiscardAction;
import com.example.actions.FiDrawFloodAction;
import com.example.actions.FiDrawTreasureAction;
import com.example.actions.FiGameOverAction;
import com.example.actions.FiGiveCardAction;
import com.example.actions.FiMoveAction;
import com.example.actions.FiShoreUpAction;
import com.example.actions.FiSkipTurnAction;
import com.example.game.GameFramework.GameMainActivity;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.players.GameHumanPlayer;

public class HumanPlayer extends GameHumanPlayer implements View.OnClickListener {
    private int layoutId;
    private GameMainActivity myActivity;
    int gameGreen = Color.rgb(63, 179, 66);//color for the tiles when they're normal
    private TextView floodView = null;//to display the flood meter

    // all the buttons on the ui that can be pressed; action buttons and tile buttons
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

    //buttons to use for the give card action
    private Button giveCardToP1 = null;
    private Button giveCardToP2 = null;
    private Button giveCardToP3 = null;


    //might delete these if we delete treasure ui
    private Button EARTH_STONE = null;
    private Button EARTH_STONE2 = null;
    private Button EARTH_STONE3 = null;
    private Button EARTH_STONE4 = null;
    private Button EARTH_STONE5 = null;
    private Button FIRE_CRYSTAL1 = null;
    private Button FIRE_CRYSTAL2 = null;
    private Button FIRE_CRYSTAL3 = null;
    private Button FIRE_CRYSTAL4 = null;
    private Button FIRE_CRYSTAL5 = null;
    private Button WIND_STATUE1 = null;
    private Button WIND_STATUE2 = null;
    private Button WIND_STATUE3 = null;
    private Button WIND_STATUE4 = null;
    private Button WIND_STATUE5 = null;
    private Button OCEAN_CHALICE1 = null;
    private Button OCEAN_CHALICE2 = null;
    private Button OCEAN_CHALICE3 = null;
    private Button OCEAN_CHALICE4 = null;
    private Button OCEAN_CHALICE5 = null;
    private Button SANDBAG1 = null;
    private Button SANDBAG2 = null;
    private Button HELICOPTER_LIFT1 = null;
    private Button HELICOPTER_LIFT2 = null;
    private Button HELICOPTER_LIFT3 = null;
    private ImageButton[] playerCards = new ImageButton[6];//image buttons for the player's hand
    private ImageButton[] treasures = new ImageButton[4];//image buttons for the treasures around the board

    //keep track of if that button was clicked
    private boolean moveButtonClicked = false;
    private boolean shoreUpButtonClicked = false;
    private boolean discardButtonClicked = false;
    private boolean giveCardButtonClicked = false;
    private boolean giveCardToP1Clicked = false;
    private boolean giveCardToP2Clicked = false;
    private boolean giveCardToP3Clicked = false;

    //to keep track of when the player's cards are pressed
    private boolean[] playerCardsClicked = new boolean[6];

    /*playerCardsClicked[0] = false;
    playerCardsClicked[1] = false;
    playerCardsClicked[2] = false;
    playerCardsClicked[3] = false;
    playerCardsClicked[4] = false;
    playerCardsClicked[5] = false;*/

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
            ABANDONED_CLIFFS.setText("ABANDONED_CLIFFS" + System.getProperty("line.separator") + "player 1");
            DECEPTION_DUNES.setText("DECEPTION_DUNES" + System.getProperty("line.separator") + "player 2");
            OBSERVATORY.setText("OBSERVATORY" + System.getProperty("line.separator") + "player 3");

            //set the image of the player card buttons to display what cards are in the player's hand
            for(int i = 0; i < gameState.getPlayerTurnHand(gameState.playerTurn).size(); i++){
                FiGameState.TreasureCards tc = gameState.getPlayerTurnHand(gameState.playerTurn).get(i);

                if(tc.equals(FiGameState.TreasureCards.WATERS_RISE1) || tc.equals(FiGameState.TreasureCards.WATERS_RISE2) || tc.equals(FiGameState.TreasureCards.WATERS_RISE3)){
                    playerCards[i].setImageResource(R.drawable.tc_waters_rise);
                }
                else if(gameState.getPlayerTurnHand(gameState.playerTurn).get(i).equals(FiGameState.TreasureCards.SANDBAG1) || tc.equals(FiGameState.TreasureCards.SANDBAG2)){
                    playerCards[i].setImageResource(R.drawable.tc_sandbag);
                }
                else if(gameState.getPlayerTurnHand(gameState.playerTurn).get(i).equals(FiGameState.TreasureCards.HELICOPTER_LIFT1) || tc.equals(FiGameState.TreasureCards.HELICOPTER_LIFT2) || tc.equals(FiGameState.TreasureCards.HELICOPTER_LIFT3)){
                    playerCards[i].setImageResource(R.drawable.tc_helicopter_lift);
                }
                else if(gameState.getPlayerTurnHand(gameState.playerTurn).get(i).equals(FiGameState.TreasureCards.EARTH_STONE) || tc.equals(FiGameState.TreasureCards.EARTH_STONE2) || tc.equals(FiGameState.TreasureCards.EARTH_STONE3) || tc.equals(FiGameState.TreasureCards.EARTH_STONE4) || tc.equals(FiGameState.TreasureCards.EARTH_STONE5)){
                    playerCards[i].setImageResource(R.drawable.tc_earth_stone);
                }
                else if(gameState.getPlayerTurnHand(gameState.playerTurn).get(i).equals(FiGameState.TreasureCards.WIND_STATUE1) || tc.equals(FiGameState.TreasureCards.WIND_STATUE2) || tc.equals(FiGameState.TreasureCards.WIND_STATUE3) || tc.equals(FiGameState.TreasureCards.WIND_STATUE4) || tc.equals(FiGameState.TreasureCards.WIND_STATUE5)){
                    playerCards[i].setImageResource(R.drawable.tc_wind_statue);
                }
                else if(gameState.getPlayerTurnHand(gameState.playerTurn).get(i).equals(FiGameState.TreasureCards.FIRE_CRYSTAL1) || tc.equals(FiGameState.TreasureCards.FIRE_CRYSTAL2) || tc.equals(FiGameState.TreasureCards.FIRE_CRYSTAL3) || tc.equals(FiGameState.TreasureCards.FIRE_CRYSTAL4) || tc.equals(FiGameState.TreasureCards.FIRE_CRYSTAL5)){
                    playerCards[i].setImageResource(R.drawable.tc_fire_crystal);
                }
                else if(gameState.getPlayerTurnHand(gameState.playerTurn).get(i).equals(FiGameState.TreasureCards.OCEAN_CHALICE1) || tc.equals(FiGameState.TreasureCards.OCEAN_CHALICE2) || tc.equals(FiGameState.TreasureCards.OCEAN_CHALICE3) || tc.equals(FiGameState.TreasureCards.OCEAN_CHALICE4) || tc.equals(FiGameState.TreasureCards.OCEAN_CHALICE5)){
                    playerCards[i].setImageResource(R.drawable.tc_ocean_chalice);
                }
            }

            //display pictures of the treasures in the four corners of the board
            treasures[0].setImageResource(R.drawable.earth_stone);
            treasures[1].setImageResource(R.drawable.fire_crystal);
            treasures[2].setImageResource(R.drawable.ocean_chalice);
            treasures[3].setImageResource(R.drawable.wind_statue);

            //set the tile player's must be on to capture certain treasures
            CORAL_PALACE.setText("CORAL_PALACE" + System.getProperty("line.separator") + "Ocean Chalice Treasure");
            TIDAL_PALACE.setText("TIDAL_PALACE" + System.getProperty("line.separator") + "Ocean Chalice Treasure");
            SHADOW_CAVE.setText("SHADOW_CAVE" + System.getProperty("line.separator") + "Fire Crystal Treasure");
            EMBER_CAVE.setText("EMBER_CAVE" + System.getProperty("line.separator") + "Fire Crystal Treasure");
            MOON_TEMPLE.setText("MOON_TEMPLE" + System.getProperty("line.separator") + "Earth Stone Treasure");
            SUN_TEMPLE.setText("SUN_TEMPLE" + System.getProperty("line.separator") + "Earth Stone Treasure");
            HOWLING_GARDEN.setText("HOWLING_GARDEN" + System.getProperty("line.separator") + "Wind Statue Treasure");
            WHISPERING_GARDENS.setText("WHISPERING_GARDENS" + System.getProperty("line.separator") + "Wind Statue Treasure");

            //changing the text on a button to show where the pawns are
            FiGameState.TileName t = gameState.getPlayerLocation(gameState.getPlayerTurn());
            Button b;
            switch(t)
            {
                case ABANDONED_CLIFFS:
                    b = this.ABANDONED_CLIFFS;
                    if(gameState.map.get(FiGameState.TileName.ABANDONED_CLIFFS).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);//normal
                    }
                    else if(gameState.map.get(FiGameState.TileName.ABANDONED_CLIFFS).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);//flooded
                    }
                    else if(gameState.map.get(FiGameState.TileName.ABANDONED_CLIFFS).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);//sunk
                    }
                    break;
                case BRONZE_GATE:
                    b = this.BRONZE_GATE;
                    if(gameState.map.get(FiGameState.TileName.BRONZE_GATE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.BRONZE_GATE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.BRONZE_GATE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case BREAKERS_BRIDGE:
                    b = this.BREAKERS_BRIDGE;
                    if(gameState.map.get(FiGameState.TileName.BREAKERS_BRIDGE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.BREAKERS_BRIDGE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.BREAKERS_BRIDGE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case COPPER_GATE:
                    b = this.COPPER_GATE;
                    if(gameState.map.get(FiGameState.TileName.COPPER_GATE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.COPPER_GATE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.COPPER_GATE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case CORAL_PALACE:
                    b = this.CORAL_PALACE;
                    if(gameState.map.get(FiGameState.TileName.CORAL_PALACE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.CORAL_PALACE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.CORAL_PALACE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case CRIMSON_FOREST:
                    b = this.CRIMSON_FOREST;
                    if(gameState.map.get(FiGameState.TileName.CRIMSON_FOREST).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.CRIMSON_FOREST).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.CRIMSON_FOREST).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case DECEPTION_DUNES:
                    b = this.DECEPTION_DUNES;
                    if(gameState.map.get(FiGameState.TileName.DECEPTION_DUNES).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.DECEPTION_DUNES).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.DECEPTION_DUNES).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case EMBER_CAVE:
                    b = this.EMBER_CAVE;
                    if(gameState.map.get(FiGameState.TileName.EMBER_CAVE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.EMBER_CAVE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.EMBER_CAVE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case FOOLS_LANDING:
                    b = this.FOOLS_LANDING;
                    if(gameState.map.get(FiGameState.TileName.FOOLS_LANDING).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.FOOLS_LANDING).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.FOOLS_LANDING).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case GOLD_GATE:
                    b = this.GOLD_GATE;
                    if(gameState.map.get(FiGameState.TileName.GOLD_GATE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.GOLD_GATE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.GOLD_GATE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case IRON_GATE:
                    b = this.IRON_GATE;
                    if(gameState.map.get(FiGameState.TileName.IRON_GATE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.IRON_GATE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.IRON_GATE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case HOWLING_GARDEN:
                    b = this.HOWLING_GARDEN;
                    if(gameState.map.get(FiGameState.TileName.HOWLING_GARDEN).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.HOWLING_GARDEN).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.HOWLING_GARDEN).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case MISTY_MARSH:
                    b = this.MISTY_MARSH;
                    if(gameState.map.get(FiGameState.TileName.MISTY_MARSH).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.MISTY_MARSH).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.MISTY_MARSH).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case MOON_TEMPLE:
                    b = this.MOON_TEMPLE;
                    if(gameState.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.MOON_TEMPLE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case SILVER_GATE:
                    b = this.SILVER_GATE;
                    if(gameState.map.get(FiGameState.TileName.SILVER_GATE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.SILVER_GATE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.SILVER_GATE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case SUN_TEMPLE:
                    b = this.SUN_TEMPLE;
                    if(gameState.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.SUN_TEMPLE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case PHANTOM_ROCK:
                    b = this.PHANTOM_ROCK;
                    if(gameState.map.get(FiGameState.TileName.PHANTOM_ROCK).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.PHANTOM_ROCK).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.PHANTOM_ROCK).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case WHISPERING_GARDENS:
                    b = this.WHISPERING_GARDENS;
                    if(gameState.map.get(FiGameState.TileName.WHISPERING_GARDENS).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.WHISPERING_GARDENS).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.WHISPERING_GARDENS).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case WATCHTOWER:
                    b = this.WATCHTOWER;
                    if(gameState.map.get(FiGameState.TileName.WATCHTOWER).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.WATCHTOWER).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.WATCHTOWER).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case TWILIGHT_HOLLOW:
                    b = this.TWILIGHT_HOLLOW;
                    if(gameState.map.get(FiGameState.TileName.TWILIGHT_HOLLOW).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.TWILIGHT_HOLLOW).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.TWILIGHT_HOLLOW).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case TIDAL_PALACE:
                    b = this.TIDAL_PALACE;
                    if(gameState.map.get(FiGameState.TileName.TIDAL_PALACE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.TIDAL_PALACE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.TIDAL_PALACE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case OBSERVATORY:
                    b = this.OBSERVATORY;
                    if(gameState.map.get(FiGameState.TileName.OBSERVATORY).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.OBSERVATORY).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.OBSERVATORY).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case LOST_LAGOON:
                    b = this.LOST_LAGOON;
                    if(gameState.map.get(FiGameState.TileName.LOST_LAGOON).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.LOST_LAGOON).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.LOST_LAGOON).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                case SHADOW_CAVE:
                    b = this.SHADOW_CAVE;
                    if(gameState.map.get(FiGameState.TileName.SHADOW_CAVE).equals(FiGameState.Value.NORMAL)){
                        b.setBackgroundColor(gameGreen);
                    }
                    else if(gameState.map.get(FiGameState.TileName.SHADOW_CAVE).equals(FiGameState.Value.FLOODED)){
                        b.setBackgroundColor(Color.BLUE);
                    }
                    else if(gameState.map.get(FiGameState.TileName.SHADOW_CAVE).equals(FiGameState.Value.SUNK)){
                        b.setBackgroundColor(Color.GRAY);
                    }
                    break;
                default:
                    b = null;
            }
            if(b != null)
            {
                b.setTextSize(6);
                b.setText(t + System.getProperty("line.separator") + "player " + gameState.getPlayerTurn());
            }

            this.floodView.setText("Flood Meter: "+gameState.getFloodMeter());
        }
    }

    @Override
    public void onClick(View view) {
        //will decide what move the player made based on the button they clicked
        if(view.getId() == R.id.quitButton){
            game.sendAction(new FiGameOverAction(this));
        }
        else if(view.getId() == R.id.drawTreasureButton){
            game.sendAction(new FiDrawTreasureAction(this));
        }
        else if(view.getId() == R.id.drawFloodButton){
            game.sendAction(new FiDrawFloodAction(this));
        }
        else if(view.getId() == R.id.skipTurn){
            game.sendAction(new FiSkipTurnAction(this));
        }
        else if(view.getId() == R.id.discard){
            if(view.getId() == R.id.playerCard1){
                playerCardsClicked[0] = true;
                return;
            }
            else if(view.getId() == R.id.playerCard2){
                playerCardsClicked[1] = true;
                return;
            }
            else if(view.getId() == R.id.playerCard3){
                playerCardsClicked[2] = true;
                return;
            }
            else if(view.getId() == R.id.playerCard4){
                playerCardsClicked[3] = true;
                return;
            }
            else if(view.getId() == R.id.playerCard5){
                playerCardsClicked[4] = true;
                return;
            }
            else if(view.getId() == R.id.playerCard6){
                playerCardsClicked[5] = true;
                return;
            }
            discardButtonClicked = true;
            return;
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
            if(view.getId() == R.id.giveCardToP1){
                FiGameState.playerChosen = 1;
            }
            else if(view.getId() == R.id.giveCardToP2){
                FiGameState.playerChosen = 2;
            }
            else if(view.getId() == R.id.giveCardToP3){
                FiGameState.playerChosen = 3;
            }
            if(view.getId() == R.id.playerCard1){
                playerCardsClicked[0] = true;
                return;
            }
            else if(view.getId() == R.id.playerCard2){
                playerCardsClicked[1] = true;
                return;
            }
            else if(view.getId() == R.id.playerCard3){
                playerCardsClicked[2] = true;
                return;
            }
            else if(view.getId() == R.id.playerCard4){
                playerCardsClicked[3] = true;
                return;
            }
            else if(view.getId() == R.id.playerCard5){
                playerCardsClicked[4] = true;
                return;
            }
            else if(view.getId() == R.id.playerCard6){
                playerCardsClicked[5] = true;
                return;
            }
            giveCardButtonClicked = true;
            return;
        }
        else if(view.getId() == R.id.captureTreasureButton){
            game.sendAction(new FiCaptureTreasureAction(this));
        }

        //to know what tile they pressed for the move and shore up methods
        FiGameState.TileName selection;
        switch(view.getId()){
            case R.id.ABANDONED_CLIFFS:
                selection = FiGameState.TileName.ABANDONED_CLIFFS;
                break;
            case R.id.BRONZE_GATE:
                selection = FiGameState.TileName.BRONZE_GATE;
                break;
            case R.id.BREAKERS_BRIDGE:
                selection = FiGameState.TileName.BREAKERS_BRIDGE;
                break;
            case R.id.COPPER_GATE:
                selection = FiGameState.TileName.COPPER_GATE;
                break;
            case R.id.CORAL_PALACE:
                selection = FiGameState.TileName.CORAL_PALACE;
                break;
            case R.id.CRIMSON_FOREST:
                selection = FiGameState.TileName.CRIMSON_FOREST;
                break;
            case R.id.DECEPTION_DUNES:
                selection = FiGameState.TileName.DECEPTION_DUNES;
                break;
            case R.id.EMBER_CAVE:
                selection = FiGameState.TileName.EMBER_CAVE;
                break;
            case R.id.FOOLS_LANDING:
                selection = FiGameState.TileName.FOOLS_LANDING;
                break;
            case R.id.GOLD_GATE:
                selection = FiGameState.TileName.GOLD_GATE;
                break;
            case R.id.IRON_GATE:
                selection = FiGameState.TileName.IRON_GATE;
                break;
            case R.id.HOWLING_GARDEN:
                selection = FiGameState.TileName.HOWLING_GARDEN;
                break;
            case R.id.MISTY_MARSH:
                selection = FiGameState.TileName.MISTY_MARSH;
                break;
            case R.id.MOON_TEMPLE:
                selection = FiGameState.TileName.MOON_TEMPLE;
                break;
            case R.id.SILVER_GATE:
                selection = FiGameState.TileName.SILVER_GATE;
                break;
            case R.id.SUN_TEMPLE:
                selection = FiGameState.TileName.SUN_TEMPLE;
                break;
            case R.id.PHANTOM_ROCK:
                selection = FiGameState.TileName.PHANTOM_ROCK;
                break;
            case R.id.WHISPERING_GARDENS:
                selection = FiGameState.TileName.WHISPERING_GARDENS;
                break;
            case R.id.WATCHTOWER:
                selection = FiGameState.TileName.WATCHTOWER;
                break;
            case R.id.TWILIGHT_HOLLOW:
                selection = FiGameState.TileName.TWILIGHT_HOLLOW;
                break;
            case R.id.TIDAL_PALACE:
                selection = FiGameState.TileName.TIDAL_PALACE;
                break;
            case R.id.OBSERVATORY:
                selection = FiGameState.TileName.OBSERVATORY;
                break;
            case R.id.LOST_LAGOON:
                selection = FiGameState.TileName.LOST_LAGOON;
                break;
            case R.id.SHADOW_CAVE:
                selection = FiGameState.TileName.SHADOW_CAVE;
                break;
            default:
                selection = FiGameState.TileName.NONE;
        }

        //need to figure out how to know which card in the button they pressed to give away!!
        if(discardButtonClicked)
        {
            for(int i = 0; i < playerCards.length; i++){
                if(playerCardsClicked[i]) {
                    if(playerCards[i].getBackground().equals(R.drawable.tc_waters_rise)){
                        game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.WATERS_RISE1));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_helicopter_lift)){
                        game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.HELICOPTER_LIFT1));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_sandbag)){
                        game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.SANDBAG1));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_earth_stone)){
                        game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.EARTH_STONE));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_fire_crystal)){
                        game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.FIRE_CRYSTAL1));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_wind_statue)){
                        game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.WIND_STATUE1));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_ocean_chalice)){
                        game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.OCEAN_CHALICE1));
                    }
                    discardButtonClicked = false;
                    playerCardsClicked[i] = false;
                }
            }
        }
        //need to add other cards for which button they pressed to give that card away
        else if(giveCardButtonClicked)
        {
            for(int i = 0; i < playerCards.length; i++){
                if(playerCardsClicked[i]) {
                    if(playerCards[i].getBackground().equals(R.drawable.tc_waters_rise)){
                        game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.WATERS_RISE1));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_helicopter_lift)){
                        game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.HELICOPTER_LIFT1));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_sandbag)){
                        game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.SANDBAG1));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_earth_stone)){
                        game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.EARTH_STONE));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_fire_crystal)){
                        game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.FIRE_CRYSTAL1));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_wind_statue)){
                        game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.WIND_STATUE1));
                    }
                    else if(playerCards[i].getBackground().equals(R.drawable.tc_ocean_chalice)){
                        game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.OCEAN_CHALICE1));
                    }
                    giveCardButtonClicked = false;
                    playerCardsClicked[i] = false;
                }
            }
        }
        if(moveButtonClicked && selection != FiGameState.TileName.NONE)
        {
            moveButtonClicked = false;
            game.sendAction(new FiMoveAction(this, selection));
        }
        else if(shoreUpButtonClicked && selection != FiGameState.TileName.NONE)
        {
            shoreUpButtonClicked = false;
            game.sendAction(new FiShoreUpAction(this, selection));
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
        this.BRONZE_GATE = activity.findViewById(R.id.BRONZE_GATE);
        this.GOLD_GATE = activity.findViewById(R.id.GOLD_GATE);
        this.CORAL_PALACE = activity.findViewById(R.id.CORAL_PALACE);
        this.SUN_TEMPLE = activity.findViewById(R.id.SUN_TEMPLE);
        this.SILVER_GATE = activity.findViewById(R.id.SILVER_GATE);
        this.PHANTOM_ROCK = activity.findViewById(R.id.PHANTOM_ROCK);
        this.WATCHTOWER = activity.findViewById(R.id.WATCHTOWER);
        this.COPPER_GATE = activity.findViewById(R.id.COPPER_GATE);
        this.ABANDONED_CLIFFS = activity.findViewById(R.id.ABANDONED_CLIFFS);
        this.WHISPERING_GARDENS = activity.findViewById(R.id.WHISPERING_GARDENS);
        this.SHADOW_CAVE = activity.findViewById(R.id.SHADOW_CAVE);
        this.LOST_LAGOON = activity.findViewById(R.id.LOST_LAGOON);
        this.MOON_TEMPLE = activity.findViewById(R.id.MOON_TEMPLE);
        this.DECEPTION_DUNES = activity.findViewById(R.id.DECEPTION_DUNES);
        this.TWILIGHT_HOLLOW = activity.findViewById(R.id.TWILIGHT_HOLLOW);
        this.EMBER_CAVE = activity.findViewById(R.id.EMBER_CAVE);
        this.TIDAL_PALACE = activity.findViewById(R.id.TIDAL_PALACE);
        this.OBSERVATORY = activity.findViewById(R.id.OBSERVATORY);
        this.IRON_GATE = activity.findViewById(R.id.IRON_GATE);
        this.CRIMSON_FOREST = activity.findViewById(R.id.CRIMSON_FOREST);
        this.MISTY_MARSH = activity.findViewById(R.id.MISTY_MARSH);
        this.BREAKERS_BRIDGE = activity.findViewById(R.id.BREAKERS_BRIDGE);
        this.HOWLING_GARDEN = activity.findViewById(R.id.HOWLING_GARDEN);

        //initializing card hand buttons
        playerCards[0] = activity.findViewById(R.id.playerCard1);
        playerCards[1] = activity.findViewById(R.id.playerCard2);
        playerCards[2] = activity.findViewById(R.id.playerCard3);
        playerCards[3] = activity.findViewById(R.id.playerCard4);
        playerCards[4] = activity.findViewById(R.id.playerCard5);
        playerCards[5] = activity.findViewById(R.id.playerCard6);
        treasures[0] = activity.findViewById(R.id.treasure1);
        treasures[1] = activity.findViewById(R.id.treasure2);
        treasures[2] = activity.findViewById(R.id.treasure3);
        treasures[3] = activity.findViewById(R.id.treasure4);
        this.giveCardToP2 = activity.findViewById(R.id.giveCardToP2);
        this.giveCardToP3 = activity.findViewById(R.id.giveCardToP3);

        //layout resource for treasure deck gui
        //activity.setContentView(R.layout.treasure_deck_ui);

        //initializing buttons used for treasure deck ui
        this.EARTH_STONE = activity.findViewById(R.id.EARTH_STONE);
        this.EARTH_STONE2  = activity.findViewById(R.id.EARTH_STONE2);
        this.EARTH_STONE3 = activity.findViewById(R.id.EARTH_STONE3);
        this.EARTH_STONE4 = activity.findViewById(R.id.EARTH_STONE4);
        this.EARTH_STONE5 = activity.findViewById(R.id.EARTH_STONE5);
        this.FIRE_CRYSTAL1 = activity.findViewById(R.id.FIRE_CRYSTAL1);
        this.FIRE_CRYSTAL2 = activity.findViewById(R.id.FIRE_CRYSTAL2);
        this.FIRE_CRYSTAL3 = activity.findViewById(R.id.FIRE_CRYSTAL3);
        this.FIRE_CRYSTAL4 = activity.findViewById(R.id.FIRE_CRYSTAL4);
        this.FIRE_CRYSTAL5 = activity.findViewById(R.id.FIRE_CRYSTAL5);
        this.WIND_STATUE1 = activity.findViewById(R.id.WIND_STATUE1);
        this.WIND_STATUE2 = activity.findViewById(R.id.WIND_STATUE2);
        this.WIND_STATUE3 = activity.findViewById(R.id.WIND_STATUE3);
        this.WIND_STATUE4 = activity.findViewById(R.id.WIND_STATUE4);
        this.WIND_STATUE5 = activity.findViewById(R.id.WIND_STATUE5);
        this.OCEAN_CHALICE1 = activity.findViewById(R.id.OCEAN_CHALICE1);
        this.OCEAN_CHALICE2 = activity.findViewById(R.id.OCEAN_CHALICE2);
        this.OCEAN_CHALICE3 = activity.findViewById(R.id.OCEAN_CHALICE3);
        this.OCEAN_CHALICE4 = activity.findViewById(R.id.OCEAN_CHALICE4);
        this.OCEAN_CHALICE5 = activity.findViewById(R.id.OCEAN_CHALICE5);
        this.SANDBAG1 = activity.findViewById(R.id.SANDBAG1);
        this.SANDBAG2 = activity.findViewById(R.id.SANDBAG2);
        this.HELICOPTER_LIFT1 = activity.findViewById(R.id.HELICOPTER_LIFT1);
        this.HELICOPTER_LIFT2 = activity.findViewById(R.id.HELICOPTER_LIFT2);
        this.HELICOPTER_LIFT3 = activity.findViewById(R.id.HELICOPTER_LIFT3);

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
        playerCards[0].setOnClickListener(this);
        playerCards[1].setOnClickListener(this);
        playerCards[2].setOnClickListener(this);
        playerCards[3].setOnClickListener(this);
        playerCards[4].setOnClickListener(this);
        playerCards[5].setOnClickListener(this);
    }
}
