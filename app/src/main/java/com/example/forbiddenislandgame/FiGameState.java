package com.example.forbiddenislandgame;

import com.example.game.GameFramework.infoMessage.GameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class FiGameState extends GameState {
    //instance variables  need to go over if we need all of them
    private ArrayList<TreasureCards> treasureDeck;
    private ArrayList<TreasureCards> discardTreasureDeck;
    private ArrayList<FloodCards> floodDeck;
    private ArrayList<FloodCards> drawnFloodCards;
    private ArrayList<FloodCards> discardFloodDeck;
    private ArrayList<TreasureCards> humanPlayerHand;//arraylist to keep track of cards in player's hand
    private ArrayList<TreasureCards> dumbAiHand;//arraylist to keep track of cards in dumb computer's hand
    private ArrayList<TreasureCards> smartAiHand;//arraylist to keep track of cards in smart computer's hand

    int numPlayers;//how many players are playing
    int playerTurn;//whose turn it is
    int floodMeter;//keeps track
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
    Tile.TileName player1Location;
    Tile.TileName player2Location;
    Tile.TileName player3Location;
    Tile.Value tileValue;

    //enum for treasure deck
    public enum TreasureCards {
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
        SANDBAG2,
        HELICOPTER_LIFT1,
        HELICOPTER_LIFT2,
        HELICOPTER_LIFT3,
        WATERS_RISE1,
        WATERS_RISE2,
        WATERS_RISE3;
    }

    //enum for flood deck
    public enum FloodCards {
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

    /** Default constructor for the game state */
    public FiGameState(){
        this.treasureDeck = new ArrayList<>();
        this.discardTreasureDeck = new ArrayList<>();
        this.floodDeck = new ArrayList<>();
        this.discardFloodDeck = new ArrayList<>();
        this.humanPlayerHand = new ArrayList<>();
        this.dumbAiHand = new ArrayList<>();
        this.smartAiHand = new ArrayList<>();
        playerTurn = 1; // sets player 1 as start of game;
        floodMeter = 1;
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
        player1Location = Tile.TileName.ABANDONED_CLIFFS;
        player2Location = Tile.TileName.DECEPTION_DUNES;
        player3Location = Tile.TileName.OBSERVATORY;

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
        this.floodMeter = other.floodMeter;
        this.treasureDeck = other.treasureDeck;
        this.discardTreasureDeck = other.discardTreasureDeck;
        this.floodDeck = other.floodDeck;
        this.discardFloodDeck = other.discardFloodDeck;
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
    public boolean move(int playerTurn, Tile.TileName t){//takes tile to move to and player whose turn it is
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
    }

    //A player can flip over any of the 4 adjacent tiles or the tile they are on if it has been flipped to flooded previously
    public boolean shoreUp(int playerTurn, Tile.TileName t, Tile.Value v) {//
        // if the shore up is 5 or greater return or no actions
        if(actionsRemaining < 1){
            return false;
        }
        else{
            player1Location = t;
            tileValue = v;
            actionsRemaining--;
            return true;
        }
    }

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
    }

    //If a player has 4 of the same treasure cards and they are the correct tile they can choose to capture a treasure
    public boolean captureTreasure(int playerTurn, ArrayList<TreasureCards> a){//
        if(actionsRemaining < 1){
            return false;
        }
        if(playerTurn == 1) {
            if (a.contains(numEarthStoneCards1 >= 4)) { //&& they are on correct tile
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
            else if (a.contains(numFireCrystalCards1 >= 4)) { //&& they are on correct tile
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
            else if (a.contains(numWindStatueCards1 >= 4)) { //&& they are on correct tile
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
            } else if (a.contains(numOceanChaliceCards1 >= 4)) { //&& they are on correct tile
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
            numEarthStoneCards1++;
        }
        else if(card1.equals(TreasureCards.FIRE_CRYSTAL1) || card1.equals(TreasureCards.FIRE_CRYSTAL2) || card1.equals(TreasureCards.FIRE_CRYSTAL3) || card1.equals(TreasureCards.FIRE_CRYSTAL4) || card1.equals(TreasureCards.FIRE_CRYSTAL5)){
            numFireCrystalCards1++;
        }
        else if(card1.equals(TreasureCards.WIND_STATUE1) || card1.equals(TreasureCards.WIND_STATUE2) || card1.equals(TreasureCards.WIND_STATUE3) || card1.equals(TreasureCards.WIND_STATUE4) || card1.equals(TreasureCards.WIND_STATUE5)){
            numWindStatueCards1++;
        }
        else if(card1.equals(TreasureCards.OCEAN_CHALICE1) || card1.equals(TreasureCards.OCEAN_CHALICE2) || card1.equals(TreasureCards.OCEAN_CHALICE3) || card1.equals(TreasureCards.OCEAN_CHALICE4) || card1.equals(TreasureCards.OCEAN_CHALICE5)){
            numOceanChaliceCards1++;
        }

        //drawing a second treasure card and adding it to the player's hand whose turn it is
        TreasureCards card2 = treasureDeck.remove(0);
        a.add(card2);

        //increasing the floodMeter count or num treasure card counts if that card is drawn
        if(card2.equals(TreasureCards.WATERS_RISE1) || card2.equals(TreasureCards.WATERS_RISE2) || card2.equals(TreasureCards.WATERS_RISE3)){
            floodMeter++;
        }
        else if(card2.equals(TreasureCards.EARTH_STONE) || card2.equals(TreasureCards.EARTH_STONE2) || card2.equals(TreasureCards.EARTH_STONE3) || card2.equals(TreasureCards.EARTH_STONE4) || card2.equals(TreasureCards.EARTH_STONE5)){
            numEarthStoneCards1++;
        }
        else if(card2.equals(TreasureCards.FIRE_CRYSTAL1) || card2.equals(TreasureCards.FIRE_CRYSTAL2) || card2.equals(TreasureCards.FIRE_CRYSTAL3) || card2.equals(TreasureCards.FIRE_CRYSTAL4) || card2.equals(TreasureCards.FIRE_CRYSTAL5)){
            numFireCrystalCards1++;
        }
        else if(card2.equals(TreasureCards.WIND_STATUE1) || card2.equals(TreasureCards.WIND_STATUE2) || card2.equals(TreasureCards.WIND_STATUE3) || card2.equals(TreasureCards.WIND_STATUE4) || card2.equals(TreasureCards.WIND_STATUE5)){
            numWindStatueCards1++;
        }
        else if(card2.equals(TreasureCards.OCEAN_CHALICE1) || card2.equals(TreasureCards.OCEAN_CHALICE2) || card2.equals(TreasureCards.OCEAN_CHALICE3) || card2.equals(TreasureCards.OCEAN_CHALICE4) || card2.equals(TreasureCards.OCEAN_CHALICE5)){
            numOceanChaliceCards1++;
        }
    } // end of drawTreasure

    public void drawFlood(ArrayList<FloodCards> a) {
        //deals flood cards up to the number on the flood meter to the drawn flood deck to immediately slip the tiles over
        for(int i = 0; i < floodMeter; i++) {
            a.add(floodDeck.remove(0));
        }
    }//end of drawFlood

    //setter methods
    public void setFloodMeter(int floodMeter) {
        this.floodMeter = floodMeter;
    }
    public void setPlayerTurn(int playerTurn){
        this.playerTurn = playerTurn;
    }
    public void setNumPlayers(int numPlayers){
        this.numPlayers = numPlayers;
    }
    public void setActionsRemaining(int actionsRemaining) {this.actionsRemaining = actionsRemaining;}
    public void setTreasureCount(int treasureCount) {
        this.treasureCount = treasureCount;
    }
    public void setTileValue(Tile.Value tileValue){this.tileValue = tileValue;}
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
    public int getTreasureCount(){return this.treasureCount;}
    public int getNumberOfCardsInHand(ArrayList<TreasureCards> a) {
        return a.size();
    }
    public ArrayList<TreasureCards> getHumanPlayerHand(){return this.humanPlayerHand;}
    public ArrayList<TreasureCards> getDumbAiHand(){return this.dumbAiHand;}
    public ArrayList<TreasureCards> getSmartAiHand(){return this.smartAiHand;}
    public ArrayList<FloodCards> getDrawnFloodCards(){return this.drawnFloodCards;}
    public Tile.TileName getPlayer1Location(){return this.player1Location;}
    public Tile.TileName getPlayer2Location(){return this.player2Location;}
    public Tile.TileName getPlayer3Location(){return this.player3Location;}
    public Tile.Value getTileValue(){return this.tileValue;}
}
