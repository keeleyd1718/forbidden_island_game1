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
    static int playerChosen;//to use for giveCard method

    //true if that treasure has been captures; false if not
    boolean earthStoneTreasure;
    boolean fireCrystalTreasure;
    boolean windStatueTreasure;
    boolean oceanChaliceTreasure;

    //number of treasure cards in each player's hand
    int[] numEarthStoneCards;
    int[] numFireCrystalCards;
    int[] numWindStatueCards;
    int[] numOceanChaliceCards;

    //keep track of tiles that the players pawn's are on
    TileName player1Location;
    TileName player2Location;
    TileName player3Location;
    HashMap<TileName, Value> map;//hashmap to hold the tile names and tile values (normal, flooded or sunk)

    public enum TreasureCards {//enum for treasure deck
        EARTH_STONE1,
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
        WATERS_RISE3
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
        NONE
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
        playerChosen = 0;
        earthStoneTreasure = false;
        fireCrystalTreasure = false;
        windStatueTreasure = false;
        oceanChaliceTreasure = false;

        //initialize the treasure card counts for the players
        numEarthStoneCards = new int[numPlayers];
        numFireCrystalCards = new int[numPlayers];
        numWindStatueCards = new int[numPlayers];
        numOceanChaliceCards = new int[numPlayers];

        //set treasure card counts for the players to 0
        for(int i = 0; i < numPlayers; i++){
            numEarthStoneCards[i] = 0;
            numFireCrystalCards[i] = 0;
            numWindStatueCards[i] = 0;
            numOceanChaliceCards[i] = 0;
        }
        actionChoices = 1; // defaults to move
        player1Location = TileName.ABANDONED_CLIFFS;
        player2Location = TileName.DECEPTION_DUNES;
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
        this.playerChosen = other.playerChosen;
        this.earthStoneTreasure = other.earthStoneTreasure;
        this.fireCrystalTreasure = other.fireCrystalTreasure;
        this.windStatueTreasure = other.windStatueTreasure;
        this.oceanChaliceTreasure = other.oceanChaliceTreasure;
        this.numOceanChaliceCards = other.numOceanChaliceCards;
        this.numEarthStoneCards = other.numEarthStoneCards;
        this.numFireCrystalCards = other.numFireCrystalCards;
        this.numWindStatueCards = other.numWindStatueCards;
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
                " Player 1's Earth Stone Cards = "+numEarthStoneCards[0]+
                " Player 1's Fire Crystal Cards = "+numFireCrystalCards[0]+
                " Player 1's Wind Statue Cards = "+numWindStatueCards[0]+
                " Player 1's Ocean Chalice Cards = "+numOceanChaliceCards[0]+
                " Player 2's Earth Stone Cards = "+numEarthStoneCards[1]+
                " Player 2's Fire Crystal Cards = "+numFireCrystalCards[1]+
                " Player 2's Wind Statue Cards = "+numWindStatueCards[1]+
                " Player 2's Ocean Chalice Cards = "+numOceanChaliceCards[1]+
                " Player 3's Earth Stone Cards = "+numEarthStoneCards[2]+
                " Player 3's Fire Crystal Cards = "+numFireCrystalCards[2]+
                " Player 3's Wind Statue Cards = "+numWindStatueCards[2]+
                " Player 3's Ocean Chalice Cards = "+numOceanChaliceCards[2]+
                " Action Choices = "+actionChoices + result;
    }

    //action methods
    //should all have boolean return value; false if illegal move for the current game state
    //modify the game state to reflect a given player has taken that action
    //each method should require that the player id of the player who is taking that action be passed in as the first parameter, might need other parameters

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
        if(actionsRemaining < 1 || playerId == 0){
            return false;
        }
        //choose card from array, remove, and add to another player's hand array
        if(playerTurn == 1 && humanPlayerHand.contains(card)) {
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
        else if(playerTurn == 2 && dumbAiHand.contains(card)) {
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
        else if(playerTurn == 3 && smartAiHand.contains(card)){
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
        return false;
    }//end of giveCard

    public boolean discard(int playerTurn, TreasureCards card){
        if(playerTurn == 1){
            humanPlayerHand.remove(card);
            discardTreasureDeck.add(card);
            return true;
        }
        else if(playerTurn == 2){
            dumbAiHand.remove(card);
            discardTreasureDeck.add(card);
            return true;
        }
        else if(playerTurn == 3){
            smartAiHand.remove(card);
            discardTreasureDeck.add(card);
            return true;
        }
        return false;
    }//end of discard

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
        for(int i = 1; i <= numPlayers; i++){
            if(playerTurn == i && earthStoneTile) {//if its player 1's turn and they are on the correct tile
                if (a.contains(numEarthStoneCards[i - 1] >= 4)) {//check if player one has the correct 4 treasure cards in their hand
                    int count = 0;

                    //remove the four (or four of if they have 5) treasure cards from their hand and add them to the discard pile
                    if (a.contains(TreasureCards.EARTH_STONE1)) {
                        discardTreasureDeck.add(TreasureCards.EARTH_STONE1);
                        a.remove(TreasureCards.EARTH_STONE1);
                        count++;
                    }
                    if (a.contains(TreasureCards.EARTH_STONE2)) {
                        discardTreasureDeck.add(TreasureCards.EARTH_STONE2);
                        a.remove(TreasureCards.EARTH_STONE2);
                        count++;
                    }
                    if (a.contains(TreasureCards.EARTH_STONE3)) {
                        discardTreasureDeck.add(TreasureCards.EARTH_STONE3);
                        a.remove(TreasureCards.EARTH_STONE3);
                        count++;
                    }
                    if (a.contains(TreasureCards.EARTH_STONE4)) {
                        discardTreasureDeck.add(TreasureCards.EARTH_STONE4);
                        a.remove(TreasureCards.EARTH_STONE4);
                        count++;
                    }
                    if (count != 4) {
                        if (a.contains(TreasureCards.EARTH_STONE5)) {
                            discardTreasureDeck.add(TreasureCards.EARTH_STONE5);
                            a.remove(TreasureCards.EARTH_STONE5);
                        }
                    }
                    numEarthStoneCards[i - 1] -= 4;//change the player's individual treasure card count
                    earthStoneTreasure = true;//they captured the earth stone treasure
                    actionsRemaining--;
                    return true;
                }
            }
            else if(playerTurn == i && fireCrystalTile) {
                if (a.contains(numFireCrystalCards[i - 1] >= 4)) {
                    int count = 0;
                    if (a.contains(TreasureCards.FIRE_CRYSTAL1)) {
                        discardTreasureDeck.add(TreasureCards.FIRE_CRYSTAL1);
                        a.remove(TreasureCards.FIRE_CRYSTAL1);
                        count++;
                    }
                    if (a.contains(TreasureCards.FIRE_CRYSTAL2)) {
                        discardTreasureDeck.add(TreasureCards.FIRE_CRYSTAL2);
                        a.remove(TreasureCards.FIRE_CRYSTAL2);
                        count++;
                    }
                    if (a.contains(TreasureCards.FIRE_CRYSTAL3)) {
                        discardTreasureDeck.add(TreasureCards.FIRE_CRYSTAL3);
                        a.remove(TreasureCards.FIRE_CRYSTAL3);
                        count++;
                    }
                    if (a.contains(TreasureCards.FIRE_CRYSTAL4)) {
                        discardTreasureDeck.add(TreasureCards.FIRE_CRYSTAL4);
                        a.remove(TreasureCards.FIRE_CRYSTAL4);
                        count++;
                    }
                    if (count != 4) {
                        if (a.contains(TreasureCards.FIRE_CRYSTAL5)) {
                            discardTreasureDeck.add(TreasureCards.FIRE_CRYSTAL5);
                            a.remove(TreasureCards.FIRE_CRYSTAL5);
                        }
                    }
                    numFireCrystalCards[i - 1] -= 4;
                    fireCrystalTreasure = true;
                    actionsRemaining--;
                    return true;
                }
            }
            else if(playerTurn == i && windStatueTile) {
                if (a.contains(numWindStatueCards[i - 1] >= 4)) {
                    int count = 0;
                    if (a.contains(TreasureCards.WIND_STATUE1)) {
                        discardTreasureDeck.add(TreasureCards.WIND_STATUE1);
                        a.remove(TreasureCards.WIND_STATUE1);
                        count++;
                    }
                    if (a.contains(TreasureCards.WIND_STATUE2)) {
                        discardTreasureDeck.add(TreasureCards.WIND_STATUE2);
                        a.remove(TreasureCards.WIND_STATUE2);
                        count++;
                    }
                    if (a.contains(TreasureCards.WIND_STATUE3)) {
                        discardTreasureDeck.add(TreasureCards.WIND_STATUE3);
                        a.remove(TreasureCards.WIND_STATUE3);
                        count++;
                    }
                    if (a.contains(TreasureCards.WIND_STATUE4)) {
                        discardTreasureDeck.add(TreasureCards.WIND_STATUE4);
                        a.remove(TreasureCards.WIND_STATUE4);
                        count++;
                    }
                    if (count != 4) {
                        if (a.contains(TreasureCards.WIND_STATUE5)) {
                            discardTreasureDeck.add(TreasureCards.WIND_STATUE5);
                            a.remove(TreasureCards.WIND_STATUE5);
                        }
                    }
                    numWindStatueCards[i - 1] -= 4;
                    windStatueTreasure = true;
                    actionsRemaining--;
                    return true;
                }
            }
            else if(playerTurn == i && oceanChaliceTile) {
                if (a.contains(numOceanChaliceCards[i - 1] >= 4)) {
                    int count = 0;
                    if (a.contains(TreasureCards.OCEAN_CHALICE1)) {
                        discardTreasureDeck.add(TreasureCards.OCEAN_CHALICE1);
                        a.remove(TreasureCards.OCEAN_CHALICE1);
                        count++;
                    }
                    if (a.contains(TreasureCards.OCEAN_CHALICE2)) {
                        discardTreasureDeck.add(TreasureCards.OCEAN_CHALICE2);
                        a.remove(TreasureCards.OCEAN_CHALICE2);
                        count++;
                    }
                    if (a.contains(TreasureCards.OCEAN_CHALICE3)) {
                        discardTreasureDeck.add(TreasureCards.OCEAN_CHALICE3);
                        a.remove(TreasureCards.OCEAN_CHALICE3);
                        count++;
                    }
                    if (a.contains(TreasureCards.OCEAN_CHALICE4)) {
                        discardTreasureDeck.add(TreasureCards.OCEAN_CHALICE4);
                        a.remove(TreasureCards.OCEAN_CHALICE4);
                        count++;
                    }
                    if (count != 4) {
                        if (a.contains(TreasureCards.OCEAN_CHALICE5)) {
                            discardTreasureDeck.add(TreasureCards.OCEAN_CHALICE5);
                            a.remove(TreasureCards.OCEAN_CHALICE5);
                        }
                    }
                    numOceanChaliceCards[i - 1] -= 4;
                    oceanChaliceTreasure = true;
                    actionsRemaining--;
                    return true;
                }
            }
        }
        return false;
    }//end of captureTreasure

    public void drawTreasure(ArrayList<TreasureCards> a) {
        //check if the treasure deck is empty and is so add the cards from the discard pile back into the treasure deck
        if(treasureDeck.isEmpty()){
            for(int i = 0; i < discardTreasureDeck.size(); i++){
                treasureDeck.add(getDiscardTreasureDeck().get(i));
            }
            Collections.shuffle(treasureDeck);
        }

        //drawing a treasure card and adding it to the player's hand whose turn it is
        TreasureCards card1 = treasureDeck.remove(0);
        a.add(card1);

        //increasing the floodMeter count or num treasure card counts if that card is drawn
        if(card1.equals(TreasureCards.WATERS_RISE1) || card1.equals(TreasureCards.WATERS_RISE2) || card1.equals(TreasureCards.WATERS_RISE3)){
            floodMeter++;
            a.remove(card1);
            discardTreasureDeck.add(card1);
        }
        else if(card1.equals(TreasureCards.EARTH_STONE1) || card1.equals(TreasureCards.EARTH_STONE2) || card1.equals(TreasureCards.EARTH_STONE3) || card1.equals(TreasureCards.EARTH_STONE4) || card1.equals(TreasureCards.EARTH_STONE5)){
            if(a.equals(humanPlayerHand)){
                numEarthStoneCards[0]++;
            }
            else if(a.equals(dumbAiHand)){
                numEarthStoneCards[1]++;
            }
            else if(a.equals(smartAiHand)){
                numEarthStoneCards[2]++;
            }
        }
        else if(card1.equals(TreasureCards.FIRE_CRYSTAL1) || card1.equals(TreasureCards.FIRE_CRYSTAL2) || card1.equals(TreasureCards.FIRE_CRYSTAL3) || card1.equals(TreasureCards.FIRE_CRYSTAL4) || card1.equals(TreasureCards.FIRE_CRYSTAL5)){
            if(a.equals(humanPlayerHand)){
                numFireCrystalCards[0]++;
            }
            else if(a.equals(dumbAiHand)){
                numFireCrystalCards[1]++;
            }
            else if(a.equals(smartAiHand)){
                numFireCrystalCards[2]++;
            }
        }
        else if(card1.equals(TreasureCards.WIND_STATUE1) || card1.equals(TreasureCards.WIND_STATUE2) || card1.equals(TreasureCards.WIND_STATUE3) || card1.equals(TreasureCards.WIND_STATUE4) || card1.equals(TreasureCards.WIND_STATUE5)){
            if(a.equals(humanPlayerHand)){
                numWindStatueCards[0]++;
            }
            else if(a.equals(dumbAiHand)){
                numWindStatueCards[1]++;
            }
            else if(a.equals(smartAiHand)){
                numWindStatueCards[2]++;
            }
        }
        else if(card1.equals(TreasureCards.OCEAN_CHALICE1) || card1.equals(TreasureCards.OCEAN_CHALICE2) || card1.equals(TreasureCards.OCEAN_CHALICE3) || card1.equals(TreasureCards.OCEAN_CHALICE4) || card1.equals(TreasureCards.OCEAN_CHALICE5)){
            if(a.equals(humanPlayerHand)){
                numOceanChaliceCards[0]++;
            }
            else if(a.equals(dumbAiHand)){
                numOceanChaliceCards[1]++;
            }
            else if(a.equals(smartAiHand)){
                numOceanChaliceCards[2]++;
            }
        }

        //drawing a second treasure card and adding it to the player's hand whose turn it is
        TreasureCards card2 = treasureDeck.remove(0);
        a.add(card2);

        //increasing the floodMeter count or num treasure card counts if that card is drawn
        if(card2.equals(TreasureCards.WATERS_RISE1) || card2.equals(TreasureCards.WATERS_RISE2) || card2.equals(TreasureCards.WATERS_RISE3)){
            floodMeter++;
            a.remove(card2);
            discardTreasureDeck.add(card2);
        }
        else if(card2.equals(TreasureCards.EARTH_STONE1) || card2.equals(TreasureCards.EARTH_STONE2) || card2.equals(TreasureCards.EARTH_STONE3) || card2.equals(TreasureCards.EARTH_STONE4) || card2.equals(TreasureCards.EARTH_STONE5)){
            if(a.equals(humanPlayerHand)){
                numEarthStoneCards[0]++;
            }
            else if(a.equals(dumbAiHand)){
                numEarthStoneCards[1]++;
            }
            else if(a.equals(smartAiHand)){
                numEarthStoneCards[2]++;
            }
        }
        else if(card2.equals(TreasureCards.FIRE_CRYSTAL1) || card2.equals(TreasureCards.FIRE_CRYSTAL2) || card2.equals(TreasureCards.FIRE_CRYSTAL3) || card2.equals(TreasureCards.FIRE_CRYSTAL4) || card2.equals(TreasureCards.FIRE_CRYSTAL5)){
            if(a.equals(humanPlayerHand)){
                numFireCrystalCards[0]++;
            }
            else if(a.equals(dumbAiHand)){
                numFireCrystalCards[1]++;
            }
            else if(a.equals(smartAiHand)){
                numFireCrystalCards[2]++;
            }
        }
        else if(card2.equals(TreasureCards.WIND_STATUE1) || card2.equals(TreasureCards.WIND_STATUE2) || card2.equals(TreasureCards.WIND_STATUE3) || card2.equals(TreasureCards.WIND_STATUE4) || card2.equals(TreasureCards.WIND_STATUE5)){
            if(a.equals(humanPlayerHand)){
                numWindStatueCards[0]++;
            }
            else if(a.equals(dumbAiHand)){
                numWindStatueCards[1]++;
            }
            else if(a.equals(smartAiHand)){
                numWindStatueCards[2]++;
            }
        }
        else if(card2.equals(TreasureCards.OCEAN_CHALICE1) || card2.equals(TreasureCards.OCEAN_CHALICE2) || card2.equals(TreasureCards.OCEAN_CHALICE3) || card2.equals(TreasureCards.OCEAN_CHALICE4) || card2.equals(TreasureCards.OCEAN_CHALICE5)){
            if(a.equals(humanPlayerHand)){
                numOceanChaliceCards[0]++;
            }
            else if(a.equals(dumbAiHand)){
                numOceanChaliceCards[1]++;
            }
            else if(a.equals(smartAiHand)){
                numOceanChaliceCards[2]++;
            }
        }
    }//end of drawTreasure

    public void drawFlood(ArrayList<FloodCards> a) {
        //check if the flood deck is empty and is so add the cards from the discard pile back into the flood deck
        if(floodDeck.isEmpty()){
            for(int i = 0; i < discardFloodDeck.size(); i++){
                FloodCards card = getDiscardFloodDeck().get(i);
                floodDeck.add(card);
            }
            Collections.shuffle(floodDeck);
        }
        //deals flood cards up to the number on the flood meter to the drawnFloodDeck which immediately flips the tiles over
        for(int i = 0; i < floodMeter; i++) {
            FloodCards card = floodDeck.remove(i);//remove the top card from the flood deck
            a.add(card);//add that card to the drawnFloodCards array which should be passed in
        }
    }//end of drawFlood


    //setter methods
    public void setPlayerTurn(int playerTurn){this.playerTurn = playerTurn;}
    public void setPlayerChosen(int playerChosen){this.playerChosen = playerChosen;}
    public void setActionsRemaining(int actionsRemaining) {this.actionsRemaining = actionsRemaining;}
    public void emptyDrawnFloodCards(){
        for(int i = 0; i < drawnFloodCards.size(); i++){
            discardFloodDeck.add(drawnFloodCards.remove(i));
        }
    }

    //getter methods
    public int getFloodMeter() {return this.floodMeter;}
    public int getPlayerTurn(){return this.playerTurn;}
    public int getActionsRemaining(){return this.actionsRemaining;}
    public int getNumberOfCardsInHand(ArrayList<TreasureCards> a) {return a.size();}
    public ArrayList<FloodCards> getDrawnFloodCards(){return this.drawnFloodCards;}
    public ArrayList<FloodCards> getDiscardFloodDeck(){return this.discardFloodDeck;}
    public ArrayList<TreasureCards> getDiscardTreasureDeck(){return this.discardTreasureDeck;}

    public String getHand(ArrayList<TreasureCards> a) {
        String str = "";
        for(int i = 0; i < a.size(); i++){
            str += a.get(i) + " ";
        }
        return str;
    }

    public ArrayList<TreasureCards> getPlayerTurnHand(int playerTurn){//get the hand of whoever's turn it is
        if(playerTurn == 1){
            return this.humanPlayerHand;
        }
        else if(playerTurn == 2){
            return this.dumbAiHand;
        }
        else{
            return this.smartAiHand;
        }
    }
    public TileName getPlayerLocation(int playerTurn) {
        if (playerTurn == 1) {
            return this.player1Location;
        } else if (playerTurn == 2) {
            return this.player2Location;
        } else {
            return this.player3Location;
        }
    }
}
