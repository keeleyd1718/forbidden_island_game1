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

            //changing the text on a button to show where the pawns are
            Tile.TileName t = gameState.getPlayerLocation();
            Button b = null;
            switch(t)
            {
                case ABANDONED_CLIFFS:
                    b = this.ABANDONED_CLIFFS;
                    break;
                case BRONZE_GATE:
                    b = this.BRONZE_GATE;
                    break;
                case BREAKERS_BRIDGE:
                    b = this.BREAKERS_BRIDGE;
                    break;
                case COPPER_GATE:
                    b = this.COPPER_GATE;
                    break;
                case CORAL_PALACE:
                    b = this.CORAL_PALACE;
                    break;
                case CRIMSON_FOREST:
                    b = this.CRIMSON_FOREST;
                    break;
                case DECEPTION_DUNES:
                    b = this.DECEPTION_DUNES;
                    break;
                case EMBER_CAVE:
                    b = this.EMBER_CAVE;
                    break;
                case FOOLS_LANDING:
                    b = this.FOOLS_LANDING;
                    break;
                case GOLD_GATE:
                    b = this.GOLD_GATE;
                    break;
                case IRON_GATE:
                    b = this.IRON_GATE;
                    break;
                case HOWLING_GARDEN:
                    b = this.HOWLING_GARDEN;
                    break;
                case MISTY_MARSH:
                    b = this.MISTY_MARSH;
                    break;
                case MOON_TEMPLE:
                    b = this.MOON_TEMPLE;
                    break;
                case SILVER_GATE:
                    b = this.SILVER_GATE;
                    break;
                case SUN_TEMPLE:
                    b = this.SUN_TEMPLE;
                    break;
                case PHANTOM_ROCK:
                    b = this.PHANTOM_ROCK;
                    break;
                case WHISPERING_GARDENS:
                    b = this.WHISPERING_GARDENS;
                    break;
                case WATCHTOWER:
                    b = this.WATCHTOWER;
                    break;
                case TWILIGHT_HOLLOW:
                    b = this.TWILIGHT_HOLLOW;
                    break;
                case TIDAL_PALACE:
                    b = this.TIDAL_PALACE;
                    break;
                case OBSERVATORY:
                    b = this.OBSERVATORY;
                    break;
                case LOST_LAGOON:
                    b = this.LOST_LAGOON;
                    break;
                case SHADOW_CAVE:
                    b = this.SHADOW_CAVE;

                    break;
                default:
                    b = null;
            }
            if(b != null)
            {
                b.setText(gameState.getPlayerTurn() + "'s pawn");
            }

            //for the shore up method
            if(gameState.getTileValue() == Tile.Value.FLOODED){
                gameState.setTileValue(Tile.Value.NORMAL);
                b.setBackgroundColor(gameGreen);//normal
            }

            //for the draw flood card method; changing whatever tiles are drawn
            if(gameState.getTileValue() == Tile.Value.NORMAL){
                gameState.setTileValue(Tile.Value.FLOODED);
                b.setBackgroundColor(Color.BLUE);//flooded
            }
            else if(gameState.getTileValue() == Tile.Value.FLOODED){
                gameState.setTileValue(Tile.Value.SUNK);
                b.setBackgroundColor(Color.GRAY);//sunk
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
        Tile.TileName selection;
        Tile.Value sel;
        switch(view.getId()){
            case R.id.ABANDONED_CLIFFS:
                selection = Tile.TileName.ABANDONED_CLIFFS;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.BRONZE_GATE:
                selection = Tile.TileName.BRONZE_GATE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.BREAKERS_BRIDGE:
                selection = Tile.TileName.BREAKERS_BRIDGE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.COPPER_GATE:
                selection = Tile.TileName.COPPER_GATE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.CORAL_PALACE:
                selection = Tile.TileName.CORAL_PALACE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.CRIMSON_FOREST:
                selection = Tile.TileName.CRIMSON_FOREST;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.DECEPTION_DUNES:
                selection = Tile.TileName.DECEPTION_DUNES;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.EMBER_CAVE:
                selection = Tile.TileName.EMBER_CAVE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.FOOLS_LANDING:
                selection = Tile.TileName.FOOLS_LANDING;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.GOLD_GATE:
                selection = Tile.TileName.GOLD_GATE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.IRON_GATE:
                selection = Tile.TileName.IRON_GATE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.HOWLING_GARDEN:
                selection = Tile.TileName.HOWLING_GARDEN;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.MISTY_MARSH:
                selection = Tile.TileName.MISTY_MARSH;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.MOON_TEMPLE:
                selection = Tile.TileName.MOON_TEMPLE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.SILVER_GATE:
                selection = Tile.TileName.SILVER_GATE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.SUN_TEMPLE:
                selection = Tile.TileName.SUN_TEMPLE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.PHANTOM_ROCK:
                selection = Tile.TileName.PHANTOM_ROCK;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.WHISPERING_GARDENS:
                selection = Tile.TileName.WHISPERING_GARDENS;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.WATCHTOWER:
                selection = Tile.TileName.WATCHTOWER;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.TWILIGHT_HOLLOW:
                selection = Tile.TileName.TWILIGHT_HOLLOW;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.TIDAL_PALACE:
                selection = Tile.TileName.TIDAL_PALACE;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.OBSERVATORY:
                selection = Tile.TileName.OBSERVATORY;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.LOST_LAGOON:
                selection = Tile.TileName.LOST_LAGOON;
                sel = Tile.Value.NORMAL;
                break;
            case R.id.SHADOW_CAVE:
                selection = Tile.TileName.SHADOW_CAVE;
                sel = Tile.Value.NORMAL;
                break;
            default:
                selection = Tile.TileName.NONE;
                sel = Tile.Value.NONE;
        }
        if(moveButtonClicked && selection != Tile.TileName.NONE)
        {
            moveButtonClicked = false;
            game.sendAction(new FiMoveAction(this, selection));
        }
        else if(shoreUpButtonClicked && selection != Tile.TileName.NONE && sel != Tile.Value.NONE)
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
        this.quitButton = (Button)activity.findViewById(R.id.quitButton);
        this.deckButton = (Button)activity.findViewById(R.id.deckButton);
        this.discardButton = (Button)activity.findViewById(R.id.discard);
        this.drawTreasureButton = (Button)activity.findViewById(R.id.drawTreasureButton);
        this.drawFloodButton = (Button)activity.findViewById(R.id.drawFloodButton);
        this.moveButton = (Button)activity.findViewById(R.id.moveButton);
        this.shoreUpButton = (Button)activity.findViewById(R.id.shoreUpButton);
        this.giveCardButton = (Button)activity.findViewById(R.id.giveCardButton);
        this.captureTreasureButton = (Button)activity.findViewById(R.id.captureTreasureButton);

        //initializing tile buttons
        this.FOOLS_LANDING = (Button)activity.findViewById(R.id.FOOLS_LANDING);
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
