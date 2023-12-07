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
    static int playerChosen;//to use for giveCard method

    //true if that treasure has been captures; false if not
    boolean isCapturedEarthStone;
    boolean isCapturedFireCrystal;
    boolean isCapturedOceanChalice;
    boolean isCapturedWindStatue;

    //number of treasure cards in each player's hand
    int[] numEarthStoneCardsInHand;
    int[] numFireCrystalCardsInHand;
    int[] numWindStatueCardsInHand;
    int[] numOceanChaliceCardsInHand;

    //arrays for the treasure cards to make methods easier
    TreasureCards[] earthStoneTreasureCards;
    TreasureCards[] fireCrystalTreasureCards;
    TreasureCards[] windStatueTreasureCards;
    TreasureCards[] oceanChaliceTreasureCards;

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
        playerTurn = 0; // sets player 1 at start of game;
        numPlayers = 3;//starts with 3 players: human player, dumb ai, smart ai
        floodMeter = 0;
        actionsRemaining = 3;
        playerChosen = 0;
        isCapturedEarthStone = false;
        isCapturedFireCrystal= false;
        isCapturedWindStatue = false;
        isCapturedOceanChalice = false;

        //initialize the treasure card counts for the players
        numEarthStoneCardsInHand = new int[numPlayers];
        numFireCrystalCardsInHand = new int[numPlayers];
        numWindStatueCardsInHand = new int[numPlayers];
        numOceanChaliceCardsInHand = new int[numPlayers];

        //set treasure card counts for the players to 0
        for(int i = 0; i < numPlayers; i++){
            numEarthStoneCardsInHand[i] = 0;
            numFireCrystalCardsInHand[i] = 0;
            numWindStatueCardsInHand[i] = 0;
            numOceanChaliceCardsInHand[i] = 0;
        }

        //initializing and setting the array of each type of treasure card to make methods simpler
        TreasureCards[] earthStoneTreasureCards = new TreasureCards[5];
        TreasureCards[] fireCrystalTreasureCards = new TreasureCards[5];
        TreasureCards[] windStatueTreasureCards = new TreasureCards[5];
        TreasureCards[] oceanChaliceTreasureCards = new TreasureCards[5];

        earthStoneTreasureCards[0] = TreasureCards.EARTH_STONE1;
        earthStoneTreasureCards[1] = TreasureCards.EARTH_STONE2;
        earthStoneTreasureCards[2] = TreasureCards.EARTH_STONE3;
        earthStoneTreasureCards[3] = TreasureCards.EARTH_STONE4;
        earthStoneTreasureCards[4] = TreasureCards.EARTH_STONE5;
        fireCrystalTreasureCards[0] = TreasureCards.FIRE_CRYSTAL1;
        fireCrystalTreasureCards[1] = TreasureCards.FIRE_CRYSTAL2;
        fireCrystalTreasureCards[2] = TreasureCards.FIRE_CRYSTAL3;
        fireCrystalTreasureCards[3] = TreasureCards.FIRE_CRYSTAL4;
        fireCrystalTreasureCards[4] = TreasureCards.FIRE_CRYSTAL5;
        windStatueTreasureCards[0] = TreasureCards.WIND_STATUE1;
        windStatueTreasureCards[1] = TreasureCards.WIND_STATUE2;
        windStatueTreasureCards[2] = TreasureCards.WIND_STATUE3;
        windStatueTreasureCards[3] = TreasureCards.WIND_STATUE4;
        windStatueTreasureCards[4] = TreasureCards.WIND_STATUE5;
        oceanChaliceTreasureCards[0] = TreasureCards.OCEAN_CHALICE1;
        oceanChaliceTreasureCards[1] = TreasureCards.OCEAN_CHALICE2;
        oceanChaliceTreasureCards[2] = TreasureCards.OCEAN_CHALICE3;
        oceanChaliceTreasureCards[3] = TreasureCards.OCEAN_CHALICE4;
        oceanChaliceTreasureCards[4] = TreasureCards.OCEAN_CHALICE5;

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
        isCapturedEarthStone = other.isCapturedEarthStone;
        isCapturedFireCrystal = other.isCapturedFireCrystal;
        isCapturedOceanChalice = other.isCapturedOceanChalice;
        isCapturedWindStatue = other.isCapturedWindStatue;
        int[] numEarthStoneCardsInHand;
        int[] numFireCrystalCardsInHand;
        int[] numWindStatueCardsInHand;
        int[] numOceanChaliceCardsInHand;
        TreasureCards[] earthStoneTreasureCards;
        TreasureCards[] fireCrystalTreasureCards;
        TreasureCards[] windStatueTreasureCards;
        TreasureCards[] oceanChaliceTreasureCards;
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
        if (playerTurn == 0) {
            result += "Player 1's Turn";
        } else if (playerTurn == 1) {
            result += "Player 2's Turn";

        }
        else if (playerTurn == 2) {
            result += "Player 3's Turn";
        }

        return "Turn = "+playerTurn+
                " Flood = "+floodMeter+
                " Remaining Actions = "+actionsRemaining;//+
            /*    " Player 1's Earth Stone Cards = "+numEarthStoneCards[0]+
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
                " Player 3's Ocean Chalice Cards = "+numOceanChaliceCards[2]+ result;*/
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
            if(playerTurn == 0){
                player1Location = t;
            }
            else if (playerTurn == 1){
                player2Location = t;
            }
            else if (playerTurn == 2){
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
        else if(getPlayerTurn() == playerTurn){
            map.put(t, Value.NORMAL);
            actionsRemaining--;
            return true;
        }
        else{
            return false;
        }
    }//end of shoreUp

    //Choose a player to give a treasure card to
    public boolean giveCard(int playerTurn, int playerId, TreasureCards card){ //player's whose turn it is, player to give card to, card to give away
        if(actionsRemaining < 1){
            return false;
        }
        //choose card from array, remove, and add to another player's hand array
        if(getPlayerTurn() == playerTurn && getPlayerTurnHand(getPlayerTurn()).contains(card)){
            getPlayerTurnHand(playerId).add(card);
            getPlayerTurnHand(getPlayerTurn()).remove(card);
            actionsRemaining--;
            return true;
        }
        else{
            return false;
        }
    }//end of giveCard

    public boolean discard(int playerTurn, TreasureCards card){
        if(getPlayerTurn() == playerTurn){
            getPlayerTurnHand(playerTurn).remove(card);
            discardTreasureDeck.add(card);
            return true;
        }
        else{
            return false;
        }
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
        for(int i = 0; i < numPlayers; i++){
            if(playerTurn == i && earthStoneTile) {//if its player 1's turn and they are on the correct tile
                if (a.contains(numEarthStoneCardsInHand[i] >= 4)) {//check if player one has the correct 4 treasure cards in their hand
                    int count = 0;

                    //remove the four (or four of if they have 5) treasure cards from their hand and add them to the discard pile
                    for(int j = 0; j < earthStoneTreasureCards.length; j++){
                        if(count == 4){
                            numEarthStoneCardsInHand[i] -= 4;//change the player's individual treasure card count
                            isCapturedEarthStone = true;//they captured the earth stone treasure
                            actionsRemaining--;
                            return true;
                        }
                        if (a.contains(earthStoneTreasureCards[j])){
                            count += tryRemoveCard(a, earthStoneTreasureCards[j]);
                        }
                    }
                }
            }
            else if(playerTurn == i && fireCrystalTile) {
                if (a.contains(numFireCrystalCardsInHand[i] >= 4)) {//check if player one has the correct 4 treasure cards in their hand
                    int count = 0;

                    //remove the four (or four of if they have 5) treasure cards from their hand and add them to the discard pile
                    for(int j = 0; j < fireCrystalTreasureCards.length; j++){
                        if(count == 4){
                            numFireCrystalCardsInHand[i] -= 4;//change the player's individual treasure card count
                            isCapturedFireCrystal = true;//they captured the earth stone treasure
                            actionsRemaining--;
                            return true;
                        }
                        if (a.contains(fireCrystalTreasureCards[j])){
                            count += tryRemoveCard(a, fireCrystalTreasureCards[j]);
                        }
                    }
                }
            }
            else if(playerTurn == i && windStatueTile) {
                if (a.contains(numWindStatueCardsInHand[i] >= 4)) {//check if player one has the correct 4 treasure cards in their hand
                    int count = 0;

                    //remove the four (or four of if they have 5) treasure cards from their hand and add them to the discard pile
                    for(int j = 0; j < windStatueTreasureCards.length; j++){
                        if(count == 4){
                            numWindStatueCardsInHand[i] -= 4;//change the player's individual treasure card count
                            isCapturedWindStatue = true;//they captured the earth stone treasure
                            actionsRemaining--;
                            return true;
                        }
                        if (a.contains(windStatueTreasureCards[j])){
                            count += tryRemoveCard(a, windStatueTreasureCards[j]);
                        }
                    }
                }
            }
            else if(playerTurn == i && oceanChaliceTile) {
                if (a.contains(numOceanChaliceCardsInHand[i] >= 4)) {//check if player one has the correct 4 treasure cards in their hand
                    int count = 0;

                    //remove the four (or four of if they have 5) treasure cards from their hand and add them to the discard pile
                    for(int j = 0; j < oceanChaliceTreasureCards.length; j++){
                        if(count == 4){
                            numOceanChaliceCardsInHand[i] -= 4;//change the player's individual treasure card count
                            isCapturedOceanChalice = true;//they captured the earth stone treasure
                            actionsRemaining--;
                            return true;
                        }
                        if (a.contains(oceanChaliceTreasureCards[j])){
                            count += tryRemoveCard(a, oceanChaliceTreasureCards[j]);
                        }
                    }
                }
            }
        }
        return false;
    }//end of captureTreasure

    //method to try to remove a card from a player's hand and if it works return 1
    int tryRemoveCard(ArrayList<TreasureCards> a, TreasureCards c){
        if(a.contains(c)){
            discardTreasureDeck.add(c);
            a.remove(c);
            return 1;
        }
        else{
            return 0;
        }
    }//end of tryRemoveCard

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
        else{
            for(int i = 0; i < 5; i++){
                if(card1.equals(earthStoneTreasureCards[i])){
                    numEarthStoneCardsInHand[playerTurn]++;
                }
                else if(card1.equals(fireCrystalTreasureCards[i])){
                    numFireCrystalCardsInHand[playerTurn]++;
                }
                else if(card1.equals(windStatueTreasureCards[i])){
                    numWindStatueCardsInHand[playerTurn]++;
                }
                else if(card1.equals(oceanChaliceTreasureCards[i])){
                    numOceanChaliceCardsInHand[playerTurn]++;
                }
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
        else{
            for(int i = 0; i < 5; i++){
                if(card2.equals(earthStoneTreasureCards[i])){
                    numEarthStoneCardsInHand[playerTurn]++;
                }
                else if(card1.equals(fireCrystalTreasureCards[i])){
                    numFireCrystalCardsInHand[playerTurn]++;
                }
                else if(card1.equals(windStatueTreasureCards[i])){
                    numWindStatueCardsInHand[playerTurn]++;
                }
                else if(card1.equals(oceanChaliceTreasureCards[i])){
                    numOceanChaliceCardsInHand[playerTurn]++;
                }
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
    public int getDiscardTreasureDeckSize() {
        return discardTreasureDeck.size();
    }

    public String getHand(ArrayList<TreasureCards> a) {
        String str = "";
        for(int i = 0; i < a.size(); i++){
            str += a.get(i) + " ";
        }
        return str;
    }

    public ArrayList<TreasureCards> getPlayerTurnHand(int playerTurn){//get the hand of whoever's turn it is
        if(playerTurn == 0){
            return this.humanPlayerHand;
        }
        else if(playerTurn == 1){
            return this.dumbAiHand;
        }
        else{
            return this.smartAiHand;
        }
    }
    public TileName getPlayerLocation(int playerTurn) {
        if (playerTurn == 0) {
            return this.player1Location;
        }
        else if (playerTurn == 1) {
            return this.player2Location;
        }
        else {
            return this.player3Location;
        }
    }
}
