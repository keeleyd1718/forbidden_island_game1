package com.example.forbiddenislandgame;

import com.example.game.GameFramework.infoMessage.GameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FiGameState extends GameState {
    private ArrayList<TreasureCards> treasureDeck;//arraylist for the treasure deck
    private ArrayList<TreasureCards> discardTreasureDeck;//arraylist for the treasure deck discard pile
    private ArrayList<FloodCards> floodDeck;//arraylist for the flood deck
    private ArrayList<FloodCards> drawnFloodCards;//arraylist for the flood deck cards that get drawn
    private ArrayList<FloodCards> discardFloodDeck;//arraylist for the flood deck discard pile
    private ArrayList<TreasureCards> humanPlayerHand;//arraylist for human player's hand
    private ArrayList<TreasureCards> dumbAiHand;//arraylist for dumb computer's hand
    private ArrayList<TreasureCards> smartAiHand;//arraylist for smart computer's hand
    int numPlayers;//how many players are playing
    int playerTurn;//whose turn it is
    int floodMeter;//keeps track of the flood meter level
    int actionsRemaining;//can have up to 3 per turn
    int actionChoices;// which of 4 actions they choose: move, shore up, capture treasure, give card

    //treasure deck instance variables
    int treasureCount;
    int numEarthStoneCards1;
    int numFireCrystalCards1;
    int numWindStatueCards1;
    int numOceanChaliceCards1;
    int numEarthStoneCards2;
    int numFireCrystalCards2;
    int numWindStatueCards2;
    int numOceanChaliceCards2;
    int numEarthStoneCards3;
    int numFireCrystalCards3;
    int numWindStatueCards3;
    int numOceanChaliceCards3;

    //keep track of tiles that the players pawn's are on
    TileName player1Location;
    TileName player2Location;
    TileName player3Location;
    HashMap<TileName, Value> map;//hashmap to hold the tile names and tile values (normal, flooded or sunk)

    public enum TreasureCards {//enum for treasure deck
        EARTH_STONE,
        EARTH_STONE2,
        EARTH_STONE3,
        EARTH_STONE4,
        EARTH_STONE5,
        FIRE_CRYSTAL1,
        FIRE_CRYSTAL2,
        FIRE_CRYSTAL3,
        FIRE_CRYSTAL4,
        FIRE_CRYSTAL5,
        WIND_STATUE1,
        WIND_STATUE2,
        WIND_STATUE3,
        WIND_STATUE4,
        WIND_STATUE5,
        OCEAN_CHALICE1,
        OCEAN_CHALICE2,
        OCEAN_CHALICE3,
        OCEAN_CHALICE4,
        OCEAN_CHALICE5,
        SANDBAG1,
        SANDBAG2,//can shore up a tile by discarding instead of using an action to shore up
        HELICOPTER_LIFT1,//needed for end of game to win
        HELICOPTER_LIFT2,
        HELICOPTER_LIFT3,
        WATERS_RISE1,
        WATERS_RISE2,
        WATERS_RISE3;
    }
    public enum FloodCards {//enum for flood deck
        FOOLS_LANDING1,
        BRONZE_GATE1,
        GOLD_GATE1,
        CORAL_PALACE1,
        SUN_TEMPLE1,
        SILVER_GATE1,
        PHANTOM_ROCK1,
        WATCHTOWER1,
        COPPER_GATE1,
        ABANDONED_CLIFFS1,
        WHISPERING_GARDENS1,
        SHADOW_CAVE1,
        LOST_LAGOON1,
        MOON_TEMPLE1,
        DECEPTION_DUNES1,
        TWILIGHT_HOLLOW1,
        EMBER_CAVE1,
        TIDAL_PALACE1,
        OBSERVATORY1,
        IRON_GATE1,
        CRIMSON_FOREST1,
        MISTY_MARSH1,
        BREAKERS_BRIDGE1,
        HOWLING_GARDEN1,
        FOOLS_LANDING,
        BRONZE_GATE,
        GOLD_GATE,
        CORAL_PALACE,
        SUN_TEMPLE,
        SILVER_GATE,
        PHANTOM_ROCK,
        WATCHTOWER,
        COPPER_GATE,
        ABANDONED_CLIFFS,
        WHISPERING_GARDENS,
        SHADOW_CAVE,
        LOST_LAGOON,
        MOON_TEMPLE,
        DECEPTION_DUNES,
        TWILIGHT_HOLLOW,
        EMBER_CAVE,
        TIDAL_PALACE,
        OBSERVATORY,
        IRON_GATE,
        CRIMSON_FOREST,
        MISTY_MARSH,
        BREAKERS_BRIDGE,
        HOWLING_GARDEN
    }

    public enum Value {//enum for the values of the tiles
        NORMAL,
        FLOODED,
        SUNK,
        NONE
    }//Value

    public enum TileName {//enum for the names of the tiles
        FOOLS_LANDING,//none
        BRONZE_GATE,//none
        GOLD_GATE,//none
        CORAL_PALACE,//ocean chalice
        SUN_TEMPLE,//earth stone
        SILVER_GATE,//none
        PHANTOM_ROCK,//none
        WATCHTOWER,//none
        COPPER_GATE,//none
        ABANDONED_CLIFFS,//none
        WHISPERING_GARDENS,//wind statue
        SHADOW_CAVE,//fire crystal
        LOST_LAGOON,//none
        MOON_TEMPLE,//earth stone
        DECEPTION_DUNES,//none
        TWILIGHT_HOLLOW,//none
        EMBER_CAVE,//fire crystal
        TIDAL_PALACE,//ocean chalice
        OBSERVATORY,//none
        IRON_GATE,//none
        CRIMSON_FOREST,//none
        MISTY_MARSH,//none
        BREAKERS_BRIDGE,//none
        HOWLING_GARDEN,//wind statue
        NONE;
    }

    /** Default constructor for the game state */
    public FiGameState(){
        this.treasureDeck = new ArrayList<>();
        this.discardTreasureDeck = new ArrayList<>();
        this.floodDeck = new ArrayList<>();
        this.drawnFloodCards = new ArrayList<>();
        this.discardFloodDeck = new ArrayList<>();
        this.humanPlayerHand = new ArrayList<>();
        this.dumbAiHand = new ArrayList<>();
        this.smartAiHand = new ArrayList<>();
        playerTurn = 1; // sets player 1 at start of game;
        numPlayers = 3;//starts with 3 players: human player, dumb ai, smart ai
        floodMeter = 0;
        actionsRemaining = 3;
        treasureCount = 0;
        //treasure card counts for player 1
        numEarthStoneCards1 = 0;
        numFireCrystalCards1 = 0;
        numWindStatueCards1 = 0;
        numOceanChaliceCards1 = 0;
        //treasure card counts for player 2
        numEarthStoneCards2 = 0;
        numFireCrystalCards2 = 0;
        numWindStatueCards2 = 0;
        numOceanChaliceCards2 = 0;
        //treasure card counts for player 3
        numEarthStoneCards3 = 0;
        numFireCrystalCards3 = 0;
        numWindStatueCards3 = 0;
        numOceanChaliceCards3 = 0;
        actionChoices = 1; // defaults to move
        player1Location = TileName.CORAL_PALACE;
        player2Location = TileName.EMBER_CAVE;
        player3Location = TileName.OBSERVATORY;
        this.map = new HashMap<>();

        //adding the tile name enum values to the hashmap with all of the values set as normal
        TileName vs[] = TileName.values();
        for(TileName value: vs){
            map.put(value, Value.NORMAL);
        }

        //adding the treasure cards enum values to the treasure deck arraylist
        TreasureCards values[] = TreasureCards.values();
        for(TreasureCards value: values) {
            treasureDeck.add(value);
        }

        //adding the flood cards enum values to the flood deck arraylist
        FloodCards v[] = FloodCards.values();
        for(FloodCards value: v) {
            floodDeck.add(value);
        }

        //shuffle the cards in the decks
        Collections.shuffle(treasureDeck);
        Collections.shuffle(floodDeck);
    }

    /** Copy Constructor */
    public FiGameState(FiGameState other){
        this.playerTurn = other.playerTurn;
        this.numPlayers = other.numPlayers;
        this.floodMeter = other.floodMeter;
        this.treasureDeck = other.treasureDeck;
        this.discardTreasureDeck = other.discardTreasureDeck;
        this.floodDeck = other.floodDeck;
        this.discardFloodDeck = other.discardFloodDeck;
        this.drawnFloodCards= other.drawnFloodCards;
        this.humanPlayerHand = other.humanPlayerHand;
        this.dumbAiHand = other.dumbAiHand;
        this.smartAiHand = other.smartAiHand;
        this.actionsRemaining = other.actionsRemaining;
        this.treasureCount = other.treasureCount;
        this.numEarthStoneCards1 = other.numEarthStoneCards1;
        this.numFireCrystalCards1 = other.numFireCrystalCards1;
        this.numWindStatueCards1 = other.numWindStatueCards1;
        this.numOceanChaliceCards1 = other.numOceanChaliceCards1;
        this.numEarthStoneCards2 = other.numEarthStoneCards2;
        this.numFireCrystalCards2 = other.numFireCrystalCards2;
        this.numWindStatueCards2 = other.numWindStatueCards2;
        this.numOceanChaliceCards2 = other.numOceanChaliceCards2;
        this.numEarthStoneCards3 = other.numEarthStoneCards3;
        this.numFireCrystalCards3 = other.numFireCrystalCards3;
        this.numWindStatueCards3 = other.numWindStatueCards3;
        this.numOceanChaliceCards3 = other.numOceanChaliceCards3;
        this.actionChoices = other.actionChoices;
        this.player1Location = other.player1Location;
        this.player2Location = other.player2Location;
        this.player3Location = other.player3Location;
        this.map = other.map;
    }

    /**
     * Turns all board data into one long string
     * @return The appended string
     */
    @Override
    public String toString(){
        String result = "Player's Turn: ";
        if (playerTurn == 1) {
            result += "Player 1's Turn";
        } else if (playerTurn == 2) {
            result += "Player 2's Turn";

        }
        else if (playerTurn == 3) {
            result += "Player 3's Turn";
        }

        return "Turn = "+playerTurn+
                " Flood = "+floodMeter+
                " Remaining Actions = "+actionsRemaining+
                " Treasure Count = "+treasureCount+
                " Player 1's Earth Stone Cards = "+numEarthStoneCards1+
                " Player 1's Fire Crystal Cards = "+numFireCrystalCards1+
                " Player 1's Wind Statue Cards = "+numWindStatueCards1+
                " Player 1's Ocean Chalice Cards = "+numOceanChaliceCards1+
                " Player 2's Earth Stone Cards = "+numEarthStoneCards2+
                " Player 2's Fire Crystal Cards = "+numFireCrystalCards2+
                " Player 2's Wind Statue Cards = "+numWindStatueCards2+
                " Player 2's Ocean Chalice Cards = "+numOceanChaliceCards2+
                " Player 3's Earth Stone Cards = "+numEarthStoneCards3+
                " Player 3's Fire Crystal Cards = "+numFireCrystalCards3+
                " Player 3's Wind Statue Cards = "+numWindStatueCards3+
                " Player 3's Ocean Chalice Cards = "+numOceanChaliceCards3+
                " Action Choices = "+actionChoices + result;
    }

    //action methods
    //should all have boolean return value
    //false if illegal move for the current game state
    //modify the game state to reflect a given player has taken that action
    //each method should require that the player id of the player who is taking that action be passed in as the first parameter, might need other parameters

    //A player can move to any of the 4 adjacent tiles around them (not diagonally)
    public boolean move(int playerTurn, TileName t){//takes tile to move to and player whose turn it is
        // check if tile is empty
        if(actionsRemaining < 1){
            return false;
        }
        else{
            if(playerTurn == 1){
                player1Location = t;
            }
            else if (playerTurn == 2){
                player2Location = t;
            }
            else if (playerTurn == 3){
                player3Location = t;
            }
            actionsRemaining--;
            return true;
        }
    }//end of move

    //A player can flip over any of the 4 adjacent tiles or the tile they are on if it has been flipped to flooded previously
    public boolean shoreUp(int playerTurn, TileName t) {//
        if(actionsRemaining < 1){
            return false;
        }
        else{
            map.put(t, Value.NORMAL);
            actionsRemaining--;
            return true;
        }
    }//end of shoreUp

    //Choose a player to give a treasure card to
    public boolean giveCard(int playerTurn, int playerId, TreasureCards card){ //player's whose turn it is, player to give card to, card to give away
        if(actionsRemaining < 1){
            return false;
        }
        //choose card from array, remove, and add to another player's hand array
        if(playerTurn == 1) {
            humanPlayerHand.remove(card);
            if(playerId == 2) {
                dumbAiHand.add(card);
            }
            else if(playerId == 3) {
                smartAiHand.add(card);
            }
            else {
                return false;
            }
            actionsRemaining--;
            return true;
        }
        else if(playerTurn == 2) {
            dumbAiHand.remove(card);
            if(playerId == 1) {
                humanPlayerHand.add(card);
            }
            else if(playerId == 3) {
                smartAiHand.add(card);
            }
            else {
                return false;
            }
            actionsRemaining--;
            return true;
        }
        else {
            smartAiHand.remove(card);
            if(playerId == 2) {
                dumbAiHand.add(card);
            }
            else if(playerId == 1) {
                humanPlayerHand.add(card);
            }
            else {
                return false;
            }
            actionsRemaining--;
            return true;
        }
    }//end of giveCard

    public boolean captureTreasure(int playerTurn, ArrayList<TreasureCards> a, TileName t){
        if(actionsRemaining < 1){
            return false;
        }
        //create boolean variables to check if the player is on one of the correct tiles
        boolean earthStoneTile = t.equals(TileName.MOON_TEMPLE) || t.equals(TileName.SUN_TEMPLE);
        boolean fireCrystalTile = (t.equals(TileName.SHADOW_CAVE) || t.equals(TileName.EMBER_CAVE));
        boolean oceanChaliceTile = (t.equals(TileName.CORAL_PALACE) || t.equals(TileName.TIDAL_PALACE));
        boolean windStatueTile = (t.equals(TileName.HOWLING_GARDEN) || t.equals(TileName.WHISPERING_GARDENS));

        //removing four earth stone cards from the player's hand and capturing the treasure
        if(playerTurn == 1 && earthStoneTile) {
            if (a.contains(numEarthStoneCards1 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.EARTH_STONE)) {
                    a.remove(TreasureCards.EARTH_STONE);
                    count++;
                }
                if (a.contains(TreasureCards.EARTH_STONE2)) {
                    a.remove(TreasureCards.EARTH_STONE2);
                    count++;
                }
                if (a.contains(TreasureCards.EARTH_STONE3)) {
                    a.remove(TreasureCards.EARTH_STONE3);
                    count++;
                }
                if (a.contains(TreasureCards.EARTH_STONE4)) {
                    a.remove(TreasureCards.EARTH_STONE4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.EARTH_STONE5)) {
                        a.remove(TreasureCards.EARTH_STONE5);
                    }
                }
                numEarthStoneCards1 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if(playerTurn == 1 && fireCrystalTile) {
            if (a.contains(numFireCrystalCards1 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.FIRE_CRYSTAL1)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL1);
                    count++;
                }
                if (a.contains(TreasureCards.FIRE_CRYSTAL2)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL2);
                    count++;
                }
                if (a.contains(TreasureCards.FIRE_CRYSTAL3)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL3);
                    count++;
                }
                if (a.contains(TreasureCards.FIRE_CRYSTAL4)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.FIRE_CRYSTAL5)) {
                        a.remove(TreasureCards.FIRE_CRYSTAL5);
                    }
                }
                numFireCrystalCards1 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if(playerTurn == 1 && windStatueTile) {
            if (a.contains(numWindStatueCards1 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.WIND_STATUE1)) {
                    a.remove(TreasureCards.WIND_STATUE1);
                    count++;
                }
                if (a.contains(TreasureCards.WIND_STATUE2)) {
                    a.remove(TreasureCards.WIND_STATUE2);
                    count++;
                }
                if (a.contains(TreasureCards.WIND_STATUE3)) {
                    a.remove(TreasureCards.WIND_STATUE3);
                    count++;
                }
                if (a.contains(TreasureCards.WIND_STATUE4)) {
                    a.remove(TreasureCards.WIND_STATUE4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.WIND_STATUE5)) {
                        a.remove(TreasureCards.WIND_STATUE5);
                    }
                }
                numWindStatueCards1 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if(playerTurn == 1 && oceanChaliceTile) {
            if (a.contains(numOceanChaliceCards1 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.OCEAN_CHALICE1)) {
                    a.remove(TreasureCards.OCEAN_CHALICE1);
                    count++;
                }
                if (a.contains(TreasureCards.OCEAN_CHALICE2)) {
                    a.remove(TreasureCards.OCEAN_CHALICE2);
                    count++;
                }
                if (a.contains(TreasureCards.OCEAN_CHALICE3)) {
                    a.remove(TreasureCards.OCEAN_CHALICE3);
                    count++;
                }
                if (a.contains(TreasureCards.OCEAN_CHALICE4)) {
                    a.remove(TreasureCards.OCEAN_CHALICE4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.OCEAN_CHALICE5)) {
                        a.remove(TreasureCards.OCEAN_CHALICE5);
                    }
                }
                numOceanChaliceCards1 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if(playerTurn == 2 && earthStoneTile) {
            if (a.contains(numEarthStoneCards2 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.EARTH_STONE)) {
                    a.remove(TreasureCards.EARTH_STONE);
                    count++;
                }
                if (a.contains(TreasureCards.EARTH_STONE2)) {
                    a.remove(TreasureCards.EARTH_STONE2);
                    count++;
                }
                if (a.contains(TreasureCards.EARTH_STONE3)) {
                    a.remove(TreasureCards.EARTH_STONE3);
                    count++;
                }
                if (a.contains(TreasureCards.EARTH_STONE4)) {
                    a.remove(TreasureCards.EARTH_STONE4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.EARTH_STONE5)) {
                        a.remove(TreasureCards.EARTH_STONE5);
                    }
                }
                numEarthStoneCards2 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if(playerTurn == 2 && fireCrystalTile) {
            if (a.contains(numFireCrystalCards2 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.FIRE_CRYSTAL1)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL1);
                    count++;
                }
                if (a.contains(TreasureCards.FIRE_CRYSTAL2)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL2);
                    count++;
                }
                if (a.contains(TreasureCards.FIRE_CRYSTAL3)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL3);
                    count++;
                }
                if (a.contains(TreasureCards.FIRE_CRYSTAL4)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.FIRE_CRYSTAL5)) {
                        a.remove(TreasureCards.FIRE_CRYSTAL5);
                    }
                }
                numFireCrystalCards2 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if(playerTurn == 2 && windStatueTile) {
            if (a.contains(numWindStatueCards2 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.WIND_STATUE1)) {
                    a.remove(TreasureCards.WIND_STATUE1);
                    count++;
                }
                if (a.contains(TreasureCards.WIND_STATUE2)) {
                    a.remove(TreasureCards.WIND_STATUE2);
                    count++;
                }
                if (a.contains(TreasureCards.WIND_STATUE3)) {
                    a.remove(TreasureCards.WIND_STATUE3);
                    count++;
                }
                if (a.contains(TreasureCards.WIND_STATUE4)) {
                    a.remove(TreasureCards.WIND_STATUE4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.WIND_STATUE5)) {
                        a.remove(TreasureCards.WIND_STATUE5);
                    }
                }
                numWindStatueCards2 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if(playerTurn == 2 && oceanChaliceTile) {
            if (a.contains(numOceanChaliceCards2 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.OCEAN_CHALICE1)) {
                    a.remove(TreasureCards.OCEAN_CHALICE1);
                    count++;
                }
                if (a.contains(TreasureCards.OCEAN_CHALICE2)) {
                    a.remove(TreasureCards.OCEAN_CHALICE2);
                    count++;
                }
                if (a.contains(TreasureCards.OCEAN_CHALICE3)) {
                    a.remove(TreasureCards.OCEAN_CHALICE3);
                    count++;
                }
                if (a.contains(TreasureCards.OCEAN_CHALICE4)) {
                    a.remove(TreasureCards.OCEAN_CHALICE4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.OCEAN_CHALICE5)) {
                        a.remove(TreasureCards.OCEAN_CHALICE5);
                    }
                }
                numOceanChaliceCards2 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if(playerTurn == 3 && earthStoneTile) {
            if (a.contains(numEarthStoneCards3 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.EARTH_STONE)) {
                    a.remove(TreasureCards.EARTH_STONE);
                    count++;
                }
                if (a.contains(TreasureCards.EARTH_STONE2)) {
                    a.remove(TreasureCards.EARTH_STONE2);
                    count++;
                }
                if (a.contains(TreasureCards.EARTH_STONE3)) {
                    a.remove(TreasureCards.EARTH_STONE3);
                    count++;
                }
                if (a.contains(TreasureCards.EARTH_STONE4)) {
                    a.remove(TreasureCards.EARTH_STONE4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.EARTH_STONE5)) {
                        a.remove(TreasureCards.EARTH_STONE5);
                    }
                }
                numEarthStoneCards3 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if(playerTurn == 3 && fireCrystalTile) {
            if (a.contains(numFireCrystalCards3 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.FIRE_CRYSTAL1)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL1);
                    count++;
                }
                if (a.contains(TreasureCards.FIRE_CRYSTAL2)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL2);
                    count++;
                }
                if (a.contains(TreasureCards.FIRE_CRYSTAL3)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL3);
                    count++;
                }
                if (a.contains(TreasureCards.FIRE_CRYSTAL4)) {
                    a.remove(TreasureCards.FIRE_CRYSTAL4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.FIRE_CRYSTAL5)) {
                        a.remove(TreasureCards.FIRE_CRYSTAL5);
                    }
                }
                numFireCrystalCards3 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if((playerTurn == 3) && windStatueTile) {
            if (a.contains(numWindStatueCards3 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.WIND_STATUE1)) {
                    a.remove(TreasureCards.WIND_STATUE1);
                    count++;
                }
                if (a.contains(TreasureCards.WIND_STATUE2)) {
                    a.remove(TreasureCards.WIND_STATUE2);
                    count++;
                }
                if (a.contains(TreasureCards.WIND_STATUE3)) {
                    a.remove(TreasureCards.WIND_STATUE3);
                    count++;
                }
                if (a.contains(TreasureCards.WIND_STATUE4)) {
                    a.remove(TreasureCards.WIND_STATUE4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.WIND_STATUE5)) {
                        a.remove(TreasureCards.WIND_STATUE5);
                    }
                }
                numWindStatueCards3 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        else if(playerTurn == 3 && oceanChaliceTile) {
            if (a.contains(numOceanChaliceCards3 >= 4)) {
                int count = 0;
                if (a.contains(TreasureCards.OCEAN_CHALICE1)) {
                    a.remove(TreasureCards.OCEAN_CHALICE1);
                    count++;
                }
                if (a.contains(TreasureCards.OCEAN_CHALICE2)) {
                    a.remove(TreasureCards.OCEAN_CHALICE2);
                    count++;
                }
                if (a.contains(TreasureCards.OCEAN_CHALICE3)) {
                    a.remove(TreasureCards.OCEAN_CHALICE3);
                    count++;
                }
                if (a.contains(TreasureCards.OCEAN_CHALICE4)) {
                    a.remove(TreasureCards.OCEAN_CHALICE4);
                    count++;
                }
                if (count != 4) {
                    if (a.contains(TreasureCards.OCEAN_CHALICE5)) {
                        a.remove(TreasureCards.OCEAN_CHALICE5);
                    }
                }
                numOceanChaliceCards3 -= 4;
                treasureCount++;
                actionsRemaining--;
                return true;
            }
        }
        return false;
    }//end of captureTreasure

    public void drawTreasure(ArrayList<TreasureCards> a) {
        //drawing a treasure card and adding it to the player's hand whose turn it is
        TreasureCards card1 = treasureDeck.remove(0);
        a.add(card1);

        //increasing the floodMeter count or num treasure card counts if that card is drawn
        if(card1.equals(TreasureCards.WATERS_RISE1) || card1.equals(TreasureCards.WATERS_RISE2) || card1.equals(TreasureCards.WATERS_RISE3)){
            floodMeter++;
        }
        else if(card1.equals(TreasureCards.EARTH_STONE) || card1.equals(TreasureCards.EARTH_STONE2) || card1.equals(TreasureCards.EARTH_STONE3) || card1.equals(TreasureCards.EARTH_STONE4) || card1.equals(TreasureCards.EARTH_STONE5)){
            if(a.equals(humanPlayerHand)){
                numEarthStoneCards1++;
            }
            else if(a.equals(dumbAiHand)){
                numEarthStoneCards2++;
            }
            else if(a.equals(smartAiHand)){
                numEarthStoneCards3++;
            }
        }
        else if(card1.equals(TreasureCards.FIRE_CRYSTAL1) || card1.equals(TreasureCards.FIRE_CRYSTAL2) || card1.equals(TreasureCards.FIRE_CRYSTAL3) || card1.equals(TreasureCards.FIRE_CRYSTAL4) || card1.equals(TreasureCards.FIRE_CRYSTAL5)){
            if(a.equals(humanPlayerHand)){
                numFireCrystalCards1++;
            }
            else if(a.equals(dumbAiHand)){
                numFireCrystalCards2++;
            }
            else if(a.equals(smartAiHand)){
                numFireCrystalCards3++;
            }
        }
        else if(card1.equals(TreasureCards.WIND_STATUE1) || card1.equals(TreasureCards.WIND_STATUE2) || card1.equals(TreasureCards.WIND_STATUE3) || card1.equals(TreasureCards.WIND_STATUE4) || card1.equals(TreasureCards.WIND_STATUE5)){
            if(a.equals(humanPlayerHand)){
                numWindStatueCards1++;
            }
            else if(a.equals(dumbAiHand)){
                numWindStatueCards2++;
            }
            else if(a.equals(smartAiHand)){
                numWindStatueCards3++;
            }
        }
        else if(card1.equals(TreasureCards.OCEAN_CHALICE1) || card1.equals(TreasureCards.OCEAN_CHALICE2) || card1.equals(TreasureCards.OCEAN_CHALICE3) || card1.equals(TreasureCards.OCEAN_CHALICE4) || card1.equals(TreasureCards.OCEAN_CHALICE5)){
            if(a.equals(humanPlayerHand)){
                numOceanChaliceCards1++;
            }
            else if(a.equals(dumbAiHand)){
                numOceanChaliceCards2++;
            }
            else if(a.equals(smartAiHand)){
                numOceanChaliceCards3++;
            }
        }

        //drawing a second treasure card and adding it to the player's hand whose turn it is
        TreasureCards card2 = treasureDeck.remove(0);
        a.add(card2);

        //increasing the floodMeter count or num treasure card counts if that card is drawn
        if(card2.equals(TreasureCards.WATERS_RISE1) || card2.equals(TreasureCards.WATERS_RISE2) || card2.equals(TreasureCards.WATERS_RISE3)){
            floodMeter++;
        }
        else if(card2.equals(TreasureCards.EARTH_STONE) || card2.equals(TreasureCards.EARTH_STONE2) || card2.equals(TreasureCards.EARTH_STONE3) || card2.equals(TreasureCards.EARTH_STONE4) || card2.equals(TreasureCards.EARTH_STONE5)){
            if(a.equals(humanPlayerHand)){
                numEarthStoneCards1++;
            }
            else if(a.equals(dumbAiHand)){
                numEarthStoneCards2++;
            }
            else if(a.equals(smartAiHand)){
                numEarthStoneCards3++;
            }
        }
        else if(card2.equals(TreasureCards.FIRE_CRYSTAL1) || card2.equals(TreasureCards.FIRE_CRYSTAL2) || card2.equals(TreasureCards.FIRE_CRYSTAL3) || card2.equals(TreasureCards.FIRE_CRYSTAL4) || card2.equals(TreasureCards.FIRE_CRYSTAL5)){
            if(a.equals(humanPlayerHand)){
                numFireCrystalCards1++;
            }
            else if(a.equals(dumbAiHand)){
                numFireCrystalCards2++;
            }
            else if(a.equals(smartAiHand)){
                numFireCrystalCards3++;
            }
        }
        else if(card2.equals(TreasureCards.WIND_STATUE1) || card2.equals(TreasureCards.WIND_STATUE2) || card2.equals(TreasureCards.WIND_STATUE3) || card2.equals(TreasureCards.WIND_STATUE4) || card2.equals(TreasureCards.WIND_STATUE5)){
            if(a.equals(humanPlayerHand)){
                numWindStatueCards1++;
            }
            else if(a.equals(dumbAiHand)){
                numWindStatueCards2++;
            }
            else if(a.equals(smartAiHand)){
                numWindStatueCards3++;
            }
        }
        else if(card2.equals(TreasureCards.OCEAN_CHALICE1) || card2.equals(TreasureCards.OCEAN_CHALICE2) || card2.equals(TreasureCards.OCEAN_CHALICE3) || card2.equals(TreasureCards.OCEAN_CHALICE4) || card2.equals(TreasureCards.OCEAN_CHALICE5)){
            if(a.equals(humanPlayerHand)){
                numOceanChaliceCards1++;
            }
            else if(a.equals(dumbAiHand)){
                numOceanChaliceCards2++;
            }
            else if(a.equals(smartAiHand)){
                numOceanChaliceCards3++;
            }
        }
    }//end of drawTreasure

    public void drawFlood(ArrayList<FloodCards> a) {
        //deals flood cards up to the number on the flood meter to the drawn flood deck to immediately slip the tiles over
        for(int i = 0; i < floodMeter; i++) {
            a.add(floodDeck.remove(0));
        }
    }//end of drawFlood

    //setter methods
    public void setPlayerTurn(int playerTurn){this.playerTurn = playerTurn;}
    public void setActionsRemaining(int actionsRemaining) {this.actionsRemaining = actionsRemaining;}
    public void emptyDrawnFloodCards(){
        for(int i = 0; i < drawnFloodCards.size(); i++){
            drawnFloodCards.remove(i);
        }
    }

    //getter methods
    public int getFloodMeter() {return this.floodMeter;}
    public int getPlayerTurn(){return this.playerTurn;}
    public int getNumPlayers(){return this.numPlayers;}
    public int getActionsRemaining(){return this.actionsRemaining;}
    public int getNumberOfCardsInHand(ArrayList<TreasureCards> a) {return a.size();}
    public ArrayList<TreasureCards> getHumanPlayerHand(){return this.humanPlayerHand;}
    public ArrayList<TreasureCards> getDumbAiHand(){return this.dumbAiHand;}
    public ArrayList<TreasureCards> getSmartAiHand(){return this.smartAiHand;}
    public ArrayList<FloodCards> getDrawnFloodCards(){return this.drawnFloodCards;}
    public TileName getPlayer1Location(){return this.player1Location;}
    public TileName getPlayer2Location(){return this.player2Location;}
    public TileName getPlayer3Location(){return this.player3Location;}
}
