package com.example.forbiddenislandgame;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiDiscardAction;
import com.example.actions.FiEndTurnAction;
import com.example.actions.FiGameOverAction;
import com.example.actions.FiGiveCardAction;
import com.example.actions.FiMoveAction;
import com.example.actions.FiShoreUpAction;
import com.example.game.GameFramework.GameMainActivity;
import com.example.game.GameFramework.infoMessage.GameInfo;
import com.example.game.GameFramework.players.GameHumanPlayer;
import com.example.game.GameFramework.utilities.MessageBox;

import java.util.HashMap;
import java.util.Map;

public class HumanPlayer extends GameHumanPlayer implements View.OnClickListener {
    private int layoutId;
    private GameMainActivity myActivity;
    int gameGreen = Color.rgb(63, 179, 66);//color for the tiles when they're normal
    private TextView floodView = null;//to display the flood meter
    HashMap<Button, FiGameState.TileName> buttonMap = new HashMap<>();//hashmap to connect the buttons to the tile names

    // all the buttons on the ui that can be pressed; action buttons and tile buttons
    private Button quitButton = null;
    private Button skipTurnButton = null;
    private Button discardButton = null;
    private Button moveButton = null;
    private Button shoreUpButton = null;
    private Button giveCardButton = null;
    private Button captureTreasureButton = null;
    private Button FOOLS_LANDING_B = null;
    private Button BRONZE_GATE_B = null;
    private Button GOLD_GATE_B = null;
    private Button CORAL_PALACE_B = null;
    private Button SUN_TEMPLE_B = null;
    private Button SILVER_GATE_B = null;
    private Button PHANTOM_ROCK_B = null;
    private Button WATCHTOWER_B = null;
    private Button COPPER_GATE_B = null;
    private Button ABANDONED_CLIFFS_B = null;
    private Button WHISPERING_GARDENS_B = null;
    private Button SHADOW_CAVE_B = null;
    private Button LOST_LAGOON_B = null;
    private Button MOON_TEMPLE_B = null;
    private Button DECEPTION_DUNES_B = null;
    private Button TWILIGHT_HOLLOW_B = null;
    private Button EMBER_CAVE_B = null;
    private Button TIDAL_PALACE_B = null;
    private Button OBSERVATORY_B = null;
    private Button IRON_GATE_B = null;
    private Button CRIMSON_FOREST_B = null;
    private Button MISTY_MARSH_B = null;
    private Button BREAKERS_BRIDGE_B = null;
    private Button HOWLING_GARDEN_B = null;

    //buttons to use for the give card action
    private Button giveCardToP1 = null;
    private Button giveCardToP2 = null;
    private Button giveCardToP3 = null;

    private ImageButton[] player1Cards = new ImageButton[6];//image buttons for the player's hand
    private ImageButton[] player2Cards = new ImageButton[6];//image buttons for the dumb ai's hand
    private ImageButton[] player3Cards = new ImageButton[6];//image buttons for the smart ai's hand
    private ImageButton[] treasures = new ImageButton[4];//image buttons for the treasures around the board

    //keep track of if that button was clicked
    private boolean moveButtonClicked = false;
    private boolean shoreUpButtonClicked = false;
    private boolean discardButtonClicked = false;
    private boolean giveCardButtonClicked = false;

    //to keep track of which of the player's cards was pressed
    private int playerCardsClicked;

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
    }

    //method to know what player's hand to display the Treasure cards
    public ImageButton[] setPlayerCards(int playerTurn){
        if(playerTurn == 0){
            return player1Cards;
        }
        else if(playerTurn == 1){
            return player2Cards;
        }
        else{
            return player3Cards;
        }
    }

    //our view in the model/view/controller sense; in charge of the user interface
    public void receiveInfo(GameInfo info){
        //should decide what move to make; just needs to update the user interface; drawing happens here
        Log.e("zzz recieve", "msg");
        if(info instanceof FiGameState){
            FiGameState gameState = (FiGameState) info;

            //setting the initial start locations for the player's pawns
            ABANDONED_CLIFFS_B.setText("ABANDONED_CLIFFS" + System.getProperty("line.separator") + "player 1");
            DECEPTION_DUNES_B.setText("DECEPTION_DUNES" + System.getProperty("line.separator") + "player 2");
            OBSERVATORY_B.setText("OBSERVATORY" + System.getProperty("line.separator") + "player 3");

            //set the image buttons to display what cards are in player 1, 2 and 3's hands
            for(int i = 0; i < gameState.numPlayers; i++){//loop to go through each player; i is for player's turn
                for(int j = 0; j < gameState.getPlayerTurnHand(i).size(); j++){//loop to go through the cards in the player's hand; j is for the card
                    FiGameState.TreasureCards tc = gameState.getPlayerTurnHand(i).get(j);

                    if(tc.equals(FiGameState.TreasureCards.SANDBAG1) || tc.equals(FiGameState.TreasureCards.SANDBAG2)){
                        setPlayerCards(i)[j].setImageResource(R.drawable.tc_sandbag);
                    }
                    else if(gameState.getHelicopterLiftCards().contains(tc)){
                        setPlayerCards(i)[j].setImageResource(R.drawable.tc_helicopter_lift);
                    }
                    else if(gameState.getEarthStoneTreasureCards().contains(tc)){
                        setPlayerCards(i)[j].setImageResource(R.drawable.tc_earth_stone);
                    }
                    else if(gameState.getWindStatueTreasureCards().contains(tc)){
                        setPlayerCards(i)[j].setImageResource(R.drawable.tc_wind_statue);
                    }
                    else if(gameState.getFireCrystalTreasureCards().contains(tc)){
                        setPlayerCards(i)[j].setImageResource(R.drawable.tc_fire_crystal);
                    }
                    else if(gameState.getOceanChaliceTreasureCards().contains(tc)){
                        setPlayerCards(i)[j].setImageResource(R.drawable.tc_ocean_chalice);
                    }
                }
            }

            //display pictures of the treasures in the four corners of the board
            treasures[0].setImageResource(R.drawable.earth_stone);
            treasures[1].setImageResource(R.drawable.fire_crystal);
            treasures[2].setImageResource(R.drawable.ocean_chalice);
            treasures[3].setImageResource(R.drawable.wind_statue);

            //set the tile player's must be on to capture certain treasures
            CORAL_PALACE_B.setText("CORAL_PALACE" + System.getProperty("line.separator") + "Ocean Chalice Treasure");
            TIDAL_PALACE_B.setText("TIDAL_PALACE" + System.getProperty("line.separator") + "Ocean Chalice Treasure");
            SHADOW_CAVE_B.setText("SHADOW_CAVE" + System.getProperty("line.separator") + "Fire Crystal Treasure");
            EMBER_CAVE_B.setText("EMBER_CAVE" + System.getProperty("line.separator") + "Fire Crystal Treasure");
            MOON_TEMPLE_B.setText("MOON_TEMPLE" + System.getProperty("line.separator") + "Earth Stone Treasure");
            SUN_TEMPLE_B.setText("SUN_TEMPLE" + System.getProperty("line.separator") + "Earth Stone Treasure");
            HOWLING_GARDEN_B.setText("HOWLING_GARDEN" + System.getProperty("line.separator") + "Wind Statue Treasure");
            WHISPERING_GARDENS_B.setText("WHISPERING_GARDENS" + System.getProperty("line.separator") + "Wind Statue Treasure");

            Button playerLocation = null;//to store the button to update a player's location

            //changing the text on a button to show where the pawns are
            FiGameState.TileName tileLocation = gameState.getPlayerLocation(gameState.getPlayerTurn());//get the tile the player should be on
            for(Map.Entry<Button, FiGameState.TileName> entry : buttonMap.entrySet()){
                if(entry.getValue().equals(tileLocation)){//if the buttonMap value is equal to the tile the player should be on...
                    playerLocation = entry.getKey();//...store the corresponding key in the button playerLocation
                }
            }
            if(playerLocation != null){
                playerLocation.setTextSize(6);
                playerLocation.setText(tileLocation + System.getProperty("line.separator") + "player " + gameState.getPlayerTurn());
            }

            //coloring the tiles the color of their value in the hashmap (normal = green, flooded = blue, sunk = gray)
            for(Button c : buttonMap.keySet()){
                FiGameState.TileName theTile = buttonMap.get(c);
                FiGameState.Value floodState = gameState.map.get(theTile);

                if(floodState.equals(FiGameState.Value.NORMAL)){
                    c.setBackgroundColor(gameGreen);//normal
                }
                else if(floodState.equals(FiGameState.Value.FLOODED)){
                    c.setBackgroundColor(Color.BLUE);//flooded
                }
                else if(floodState.equals(FiGameState.Value.SUNK)){
                    c.setBackgroundColor(Color.GRAY);//sunk
                }
            }

            floodView.setText("Flood Meter: "+gameState.getFloodMeter());
        }
        else {
            Log.e("zzz receive", "other msg");
        }
    }

    @Override
    public void onClick(View view) {
        Log.e("zzz onClick", "clicked! " + view.getId());

        //will decide what move the player made based on the button they clicked
        if(view.getId() == R.id.quitButton){
            game.sendAction(new FiGameOverAction(this));
        }
        else if(view.getId() == R.id.skipTurn){
            game.sendAction(new FiEndTurnAction(this));
        }
        else if(view.getId() == R.id.discard){
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
            giveCardButtonClicked = true;
            return;
        }
        else if(view.getId() == R.id.captureTreasureButton){
            game.sendAction(new FiCaptureTreasureAction(this));
        }

        //to know what tile they pressed for the move and shore up methods
        FiGameState.TileName selection = buttonMap.get(view);

        //when the player presses the discard button
        if(discardButtonClicked) {
            if(view.getId() == R.id.player1Card1) {
                playerCardsClicked = 0;
            }
            else if (view.getId() == R.id.player1Card2) {
                playerCardsClicked = 1;
            }
            else if (view.getId() == R.id.player1Card3) {
                playerCardsClicked = 2;
            }
            else if (view.getId() == R.id.player1Card4) {
                playerCardsClicked = 3;
            }
            else if (view.getId() == R.id.player1Card5) {
                playerCardsClicked = 4;
            }
            else if (view.getId() == R.id.player1Card6) {
                playerCardsClicked = 5;
            }

            String msg = "Please choose a card to discard";
            MessageBox.popUpMessage(msg, myActivity);

            //go through the playerCardsClicked array to see what card the player pressed and send the discard action with the card in that place
            if(player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_helicopter_lift)) {
                game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.HELICOPTER_LIFT1));
            }
            else if (player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_sandbag)) {
                game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.SANDBAG1));
            }
            else if (player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_earth_stone)) {
                game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.EARTH_STONE1));
            }
            else if (player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_fire_crystal)) {
                game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.FIRE_CRYSTAL1));
            }
            else if (player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_wind_statue)) {
                game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.WIND_STATUE1));
            }
            else if (player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_ocean_chalice)) {
                game.sendAction(new FiDiscardAction(this, FiGameState.TreasureCards.OCEAN_CHALICE1));
            }
            discardButtonClicked = false;
            playerCardsClicked = 1;
        }
        else if(giveCardButtonClicked) {//when the player presses the give card button
            if(view.getId() == R.id.giveCardToP1){
                FiGameState.playerChosen = 0;
            }
            else if(view.getId() == R.id.giveCardToP2){
                FiGameState.playerChosen = 1;
            }
            else if(view.getId() == R.id.giveCardToP3){
                FiGameState.playerChosen = 2;
            }
            if (view.getId() == R.id.player1Card1) {
                playerCardsClicked = 0;
            }
            else if (view.getId() == R.id.player1Card2) {
                playerCardsClicked = 1;
            }
            else if (view.getId() == R.id.player1Card3) {
                playerCardsClicked = 2;
            }
            else if (view.getId() == R.id.player1Card4) {
                playerCardsClicked = 3;
            }
            else if (view.getId() == R.id.player1Card5) {
                playerCardsClicked = 4;
            }
            else if (view.getId() == R.id.player1Card6) {
                playerCardsClicked = 5;
            }

            //go through the playerCardsClicked array to see what card the player pressed and send the giveCard action with the card in that place
            if(player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_helicopter_lift)){
                game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.HELICOPTER_LIFT1));
            }
            else if(player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_sandbag)){
                game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.SANDBAG1));
            }
            else if(player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_earth_stone)){
                game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.EARTH_STONE1));
            }
            else if(player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_fire_crystal)){
                game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.FIRE_CRYSTAL1));
            }
            else if(player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_wind_statue)){
                game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.WIND_STATUE1));
            }
            else if(player1Cards[playerCardsClicked].getBackground().equals(R.drawable.tc_ocean_chalice)){
                game.sendAction(new FiGiveCardAction(this, FiGameState.playerChosen, FiGameState.TreasureCards.OCEAN_CHALICE1));
            }
            giveCardButtonClicked = false;
            playerCardsClicked = 1;
        }
        else if(moveButtonClicked){
            moveButtonClicked = false;
            game.sendAction(new FiMoveAction(this, selection));
        }
        else if(shoreUpButtonClicked){
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
        this.skipTurnButton = activity.findViewById(R.id.skipTurn);
        this.discardButton = activity.findViewById(R.id.discard);
        this.moveButton = activity.findViewById(R.id.moveButton);
        this.shoreUpButton = activity.findViewById(R.id.shoreUpButton);
        this.giveCardButton = activity.findViewById(R.id.giveCardButton);
        this.captureTreasureButton = activity.findViewById(R.id.captureTreasureButton);
        this.giveCardToP1 = activity.findViewById(R.id.giveCardToP1);
        this.giveCardToP2 = activity.findViewById(R.id.giveCardToP2);
        this.giveCardToP3 = activity.findViewById(R.id.giveCardToP3);

        //initializing tile buttons and putting them into the buttonMap HashMap
        this.FOOLS_LANDING_B = activity.findViewById(R.id.FOOLS_LANDING);
        buttonMap.put(activity.findViewById(R.id.FOOLS_LANDING), FiGameState.TileName.FOOLS_LANDING);

        this.BRONZE_GATE_B = activity.findViewById(R.id.BRONZE_GATE);
        buttonMap.put(activity.findViewById(R.id.BRONZE_GATE), FiGameState.TileName.BRONZE_GATE);
        this.GOLD_GATE_B = activity.findViewById(R.id.GOLD_GATE);
        buttonMap.put(activity.findViewById(R.id.GOLD_GATE), FiGameState.TileName.GOLD_GATE);
        this.CORAL_PALACE_B = activity.findViewById(R.id.CORAL_PALACE);
        buttonMap.put(activity.findViewById(R.id.CORAL_PALACE), FiGameState.TileName.CORAL_PALACE);
        this.SUN_TEMPLE_B = activity.findViewById(R.id.SUN_TEMPLE);
        buttonMap.put(activity.findViewById(R.id.SUN_TEMPLE), FiGameState.TileName.SUN_TEMPLE);
        this.SILVER_GATE_B = activity.findViewById(R.id.SILVER_GATE);
        buttonMap.put(activity.findViewById(R.id.SILVER_GATE), FiGameState.TileName.SILVER_GATE);
        this.PHANTOM_ROCK_B = activity.findViewById(R.id.PHANTOM_ROCK);
        buttonMap.put(activity.findViewById(R.id.PHANTOM_ROCK), FiGameState.TileName.PHANTOM_ROCK);
        this.WATCHTOWER_B = activity.findViewById(R.id.WATCHTOWER);
        buttonMap.put(activity.findViewById(R.id.WATCHTOWER), FiGameState.TileName.WATCHTOWER);
        this.COPPER_GATE_B = activity.findViewById(R.id.COPPER_GATE);
        buttonMap.put(activity.findViewById(R.id.COPPER_GATE), FiGameState.TileName.COPPER_GATE);
        this.ABANDONED_CLIFFS_B = activity.findViewById(R.id.ABANDONED_CLIFFS);
        buttonMap.put(activity.findViewById(R.id.ABANDONED_CLIFFS), FiGameState.TileName.ABANDONED_CLIFFS);
        this.WHISPERING_GARDENS_B = activity.findViewById(R.id.WHISPERING_GARDENS);
        buttonMap.put(activity.findViewById(R.id.WHISPERING_GARDENS), FiGameState.TileName.WHISPERING_GARDENS);
        this.SHADOW_CAVE_B = activity.findViewById(R.id.SHADOW_CAVE);
        buttonMap.put(activity.findViewById(R.id.SHADOW_CAVE), FiGameState.TileName.SHADOW_CAVE);
        this.LOST_LAGOON_B = activity.findViewById(R.id.LOST_LAGOON);
        buttonMap.put(activity.findViewById(R.id.LOST_LAGOON), FiGameState.TileName.LOST_LAGOON);
        this.MOON_TEMPLE_B = activity.findViewById(R.id.MOON_TEMPLE);
        buttonMap.put(activity.findViewById(R.id.MOON_TEMPLE), FiGameState.TileName.MOON_TEMPLE);
        this.DECEPTION_DUNES_B = activity.findViewById(R.id.DECEPTION_DUNES);
        buttonMap.put(activity.findViewById(R.id.DECEPTION_DUNES), FiGameState.TileName.DECEPTION_DUNES);
        this.TWILIGHT_HOLLOW_B = activity.findViewById(R.id.TWILIGHT_HOLLOW);
        buttonMap.put(activity.findViewById(R.id.TWILIGHT_HOLLOW), FiGameState.TileName.TWILIGHT_HOLLOW);
        this.EMBER_CAVE_B = activity.findViewById(R.id.EMBER_CAVE);
        buttonMap.put(activity.findViewById(R.id.EMBER_CAVE), FiGameState.TileName.EMBER_CAVE);
        this.TIDAL_PALACE_B = activity.findViewById(R.id.TIDAL_PALACE);
        buttonMap.put(activity.findViewById(R.id.TIDAL_PALACE), FiGameState.TileName.TIDAL_PALACE);
        this.OBSERVATORY_B = activity.findViewById(R.id.OBSERVATORY);
        buttonMap.put(activity.findViewById(R.id.OBSERVATORY), FiGameState.TileName.OBSERVATORY);
        this.IRON_GATE_B = activity.findViewById(R.id.IRON_GATE);
        buttonMap.put(activity.findViewById(R.id.IRON_GATE), FiGameState.TileName.IRON_GATE);
        this.CRIMSON_FOREST_B = activity.findViewById(R.id.CRIMSON_FOREST);
        buttonMap.put(activity.findViewById(R.id.CRIMSON_FOREST), FiGameState.TileName.CRIMSON_FOREST);
        this.MISTY_MARSH_B = activity.findViewById(R.id.MISTY_MARSH);
        buttonMap.put(activity.findViewById(R.id.MISTY_MARSH), FiGameState.TileName.MISTY_MARSH);
        this.BREAKERS_BRIDGE_B = activity.findViewById(R.id.BREAKERS_BRIDGE);
        buttonMap.put(activity.findViewById(R.id.BREAKERS_BRIDGE), FiGameState.TileName.BREAKERS_BRIDGE);
        this.HOWLING_GARDEN_B = activity.findViewById(R.id.HOWLING_GARDEN);
        buttonMap.put(activity.findViewById(R.id.HOWLING_GARDEN), FiGameState.TileName.HOWLING_GARDEN);

        //initializing card hand image buttons
        player1Cards[0] = activity.findViewById(R.id.player1Card1);
        player1Cards[1] = activity.findViewById(R.id.player1Card2);
        player1Cards[2] = activity.findViewById(R.id.player1Card3);
        player1Cards[3] = activity.findViewById(R.id.player1Card4);
        player1Cards[4] = activity.findViewById(R.id.player1Card5);
        player1Cards[5] = activity.findViewById(R.id.player1Card6);
        player2Cards[0] = activity.findViewById(R.id.player2Card1);
        player2Cards[1] = activity.findViewById(R.id.player2Card2);
        player2Cards[2] = activity.findViewById(R.id.player2Card3);
        player2Cards[3] = activity.findViewById(R.id.player2Card4);
        player2Cards[4] = activity.findViewById(R.id.player2Card5);
        player2Cards[5] = activity.findViewById(R.id.player2Card6);
        player3Cards[0] = activity.findViewById(R.id.player3Card1);
        player3Cards[1] = activity.findViewById(R.id.player3Card2);
        player3Cards[2] = activity.findViewById(R.id.player3Card3);
        player3Cards[3] = activity.findViewById(R.id.player3Card4);
        player3Cards[4] = activity.findViewById(R.id.player3Card5);
        player3Cards[5] = activity.findViewById(R.id.player3Card6);

        //initializing treasure image buttons
        treasures[0] = activity.findViewById(R.id.treasure1);
        treasures[1] = activity.findViewById(R.id.treasure2);
        treasures[2] = activity.findViewById(R.id.treasure3);
        treasures[3] = activity.findViewById(R.id.treasure4);

        //if an action button is pressed call the onClickListener method
        quitButton.setOnClickListener(this);
        skipTurnButton.setOnClickListener(this);
        moveButton.setOnClickListener(this);
        shoreUpButton.setOnClickListener(this);
        giveCardButton.setOnClickListener(this);
        discardButton.setOnClickListener(this);
        captureTreasureButton.setOnClickListener(this);
        giveCardToP1.setOnClickListener(this);
        giveCardToP2.setOnClickListener(this);
        giveCardToP3.setOnClickListener(this);
        player1Cards[0].setOnClickListener(this);
        player1Cards[1].setOnClickListener(this);
        player1Cards[2].setOnClickListener(this);
        player1Cards[3].setOnClickListener(this);
        player1Cards[4].setOnClickListener(this);
        player1Cards[5].setOnClickListener(this);

        //call the onClickListener when tile buttons are clicked
        FOOLS_LANDING_B.setOnClickListener(this);
        BRONZE_GATE_B.setOnClickListener(this);
        GOLD_GATE_B.setOnClickListener(this);
        CORAL_PALACE_B.setOnClickListener(this);
        SUN_TEMPLE_B.setOnClickListener(this);
        SILVER_GATE_B.setOnClickListener(this);
        PHANTOM_ROCK_B.setOnClickListener(this);
        WATCHTOWER_B.setOnClickListener(this);
        COPPER_GATE_B.setOnClickListener(this);
        ABANDONED_CLIFFS_B.setOnClickListener(this);
        WHISPERING_GARDENS_B.setOnClickListener(this);
        SHADOW_CAVE_B.setOnClickListener(this);
        LOST_LAGOON_B.setOnClickListener(this);
        MOON_TEMPLE_B.setOnClickListener(this);
        DECEPTION_DUNES_B.setOnClickListener(this);
        TWILIGHT_HOLLOW_B.setOnClickListener(this);
        EMBER_CAVE_B.setOnClickListener(this);
        TIDAL_PALACE_B.setOnClickListener(this);
        OBSERVATORY_B.setOnClickListener(this);
        IRON_GATE_B.setOnClickListener(this);
        CRIMSON_FOREST_B.setOnClickListener(this);
        MISTY_MARSH_B.setOnClickListener(this);
        BREAKERS_BRIDGE_B.setOnClickListener(this);
        HOWLING_GARDEN_B.setOnClickListener(this);
    }
}
