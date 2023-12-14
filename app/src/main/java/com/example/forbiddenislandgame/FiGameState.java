package com.example.forbiddenislandgame;

import android.graphics.Color;
import android.widget.Button;

import com.example.actions.FiCaptureTreasureAction;
import com.example.actions.FiDiscardAction;
import com.example.actions.FiEndTurnAction;
import com.example.actions.FiMoveAction;
import com.example.game.GameFramework.infoMessage.GameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FiGameState extends GameState {
    private ArrayList<TreasureCards> treasureDeck;//arraylist for the treasure deck
    private ArrayList<TreasureCards> discardTreasureDeck;//arraylist for the treasure deck discard pile
    private ArrayList<FloodCards> floodDeck;//arraylist for the flood deck
    private ArrayList<FloodCards> drawnFloodCards;//arraylist for the flood deck cards that get drawn
    private ArrayList<FloodCards> discardFloodDeck;//arraylist for the flood deck discard pile
    private ArrayList<TreasureCards> humanPlayerHand;//arraylist for human player's hand
    private ArrayList<TreasureCards> dumbAiHand;//arraylist for dumb computer's hand
    private ArrayList<TreasureCards> smartAiHand;//arraylist for smart computer's hand
    private int numPlayers;//how many players are playing
    private int playerTurn;//player whose turn it is
    private int floodMeter;//keeps track of the flood meter level
    private int actionsRemaining;//can have up to 3 per turn

    //true if that treasure has been captures; false if not
    private boolean isCapturedEarthStone;
    private boolean isCapturedFireCrystal;
    private boolean isCapturedOceanChalice;
    private boolean isCapturedWindStatue;

    //number of treasure cards in each player's hand
    int[] numEarthStoneCardsInHand;
    int[] numFireCrystalCardsInHand;
    int[] numOceanChaliceCardsInHand;
    int[] numWindStatueCardsInHand;
    int[] numHelicopterLiftCardsInHand;

    //arrayLists for the treasure cards to make methods easier
    private ArrayList<TreasureCards> earthStoneTreasureCards;
    private ArrayList<TreasureCards> fireCrystalTreasureCards;
    private ArrayList<TreasureCards> oceanChaliceTreasureCards;
    private ArrayList<TreasureCards> windStatueTreasureCards;
    private ArrayList<TreasureCards> helicopterLiftCards;

    //keep track of tiles that the players pawn's are on
    private TileName player1Location;
    private TileName player2Location;
    private TileName player3Location;
    HashMap<TileName, Value> map;//hashmap to hold the tile names (treasureCards enum) and tile values (normal, flooded or sunk)

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
        SANDBAG2,//can shore up a tile by discarding this instead of using an action to shore up
        HELICOPTER_LIFT1,//needed for end of game to win
        HELICOPTER_LIFT2,
        HELICOPTER_LIFT3,
        WATERS_RISE1,
        WATERS_RISE2,
        WATERS_RISE3
    }//TreasureCards enum
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
    }//FloodCards enum

    public enum Value {//enum for the values of the tiles
        NORMAL,
        FLOODED,
        SUNK
    }//Value enum

    public enum TileName {//enum for the names of the tiles
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
        HOWLING_GARDEN,
    }//TileName enum

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
        numPlayers = 3;//starts with 3 players: human player, dumb ai, smart ai
        playerTurn = 0; // sets player 1 at start of game;
        floodMeter = 0;
        actionsRemaining = 3;
        isCapturedEarthStone = false;
        isCapturedFireCrystal= false;
        isCapturedOceanChalice = false;
        isCapturedWindStatue = false;

        //initialize the treasure card counts for the players
        numEarthStoneCardsInHand = new int[numPlayers];
        numFireCrystalCardsInHand = new int[numPlayers];
        numOceanChaliceCardsInHand = new int[numPlayers];
        numWindStatueCardsInHand = new int[numPlayers];
        numHelicopterLiftCardsInHand = new int[numPlayers];

        //set treasure card counts for each of the players to 0
        for(int i = 0; i < numPlayers; i++){
            numEarthStoneCardsInHand[i] = 0;
            numFireCrystalCardsInHand[i] = 0;
            numOceanChaliceCardsInHand[i] = 0;
            numWindStatueCardsInHand[i] = 0;
            numHelicopterLiftCardsInHand[i] = 0;
        }

        //initialize the arrayLists of treasure cards types
        this.earthStoneTreasureCards = new ArrayList<>();
        this.fireCrystalTreasureCards = new ArrayList<>();
        this.oceanChaliceTreasureCards = new ArrayList<>();
        this.windStatueTreasureCards = new ArrayList<>();
        this.helicopterLiftCards = new ArrayList<>();

        earthStoneTreasureCards.add(TreasureCards.EARTH_STONE1);
        earthStoneTreasureCards.add(TreasureCards.EARTH_STONE2);
        earthStoneTreasureCards.add(TreasureCards.EARTH_STONE3);
        earthStoneTreasureCards.add(TreasureCards.EARTH_STONE4);
        earthStoneTreasureCards.add(TreasureCards.EARTH_STONE5);
        fireCrystalTreasureCards.add(TreasureCards.FIRE_CRYSTAL1);
        fireCrystalTreasureCards.add(TreasureCards.FIRE_CRYSTAL2);
        fireCrystalTreasureCards.add(TreasureCards.FIRE_CRYSTAL3);
        fireCrystalTreasureCards.add(TreasureCards.FIRE_CRYSTAL4);
        fireCrystalTreasureCards.add(TreasureCards.FIRE_CRYSTAL5);
        oceanChaliceTreasureCards.add(TreasureCards.OCEAN_CHALICE1);
        oceanChaliceTreasureCards.add(TreasureCards.OCEAN_CHALICE2);
        oceanChaliceTreasureCards.add(TreasureCards.OCEAN_CHALICE3);
        oceanChaliceTreasureCards.add(TreasureCards.OCEAN_CHALICE4);
        oceanChaliceTreasureCards.add(TreasureCards.OCEAN_CHALICE5);
        windStatueTreasureCards.add(TreasureCards.WIND_STATUE1);
        windStatueTreasureCards.add(TreasureCards.WIND_STATUE2);
        windStatueTreasureCards.add(TreasureCards.WIND_STATUE3);
        windStatueTreasureCards.add(TreasureCards.WIND_STATUE4);
        windStatueTreasureCards.add(TreasureCards.WIND_STATUE5);
        helicopterLiftCards.add(TreasureCards.HELICOPTER_LIFT1);
        helicopterLiftCards.add(TreasureCards.HELICOPTER_LIFT2);
        helicopterLiftCards.add(TreasureCards.HELICOPTER_LIFT3);

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
        this.treasureDeck = other.treasureDeck;
        //Collections.copy(treasureDeck, other.treasureDeck);
        this.discardTreasureDeck = other.discardTreasureDeck;
        //Collections.copy(discardTreasureDeck, other.discardTreasureDeck);
        this.floodDeck = other.floodDeck;
        //Collections.copy(floodDeck, other.floodDeck);
        this.drawnFloodCards= other.drawnFloodCards;
        //Collections.copy(drawnFloodCards, other.drawnFloodCards);
        this.discardFloodDeck = other.discardFloodDeck;
        //Collections.copy(discardFloodDeck, other.discardFloodDeck);
        this.humanPlayerHand = other.humanPlayerHand;
        //Collections.copy(humanPlayerHand, other.humanPlayerHand);
        this.dumbAiHand = other.dumbAiHand;
        //Collections.copy(dumbAiHand, other.dumbAiHand);
        this.smartAiHand = other.smartAiHand;
        //Collections.copy(smartAiHand, other.smartAiHand);
        this.numPlayers = other.numPlayers;
        this.playerTurn = other.playerTurn;
        this.floodMeter = other.floodMeter;
        this.actionsRemaining = other.actionsRemaining;
        this.isCapturedEarthStone = other.isCapturedEarthStone;
        this.isCapturedFireCrystal = other.isCapturedFireCrystal;
        this.isCapturedOceanChalice = other.isCapturedOceanChalice;
        this.isCapturedWindStatue = other.isCapturedWindStatue;
        this.numEarthStoneCardsInHand = other.numEarthStoneCardsInHand;
        //
        this.numFireCrystalCardsInHand = other.numFireCrystalCardsInHand;
        //
        this.numOceanChaliceCardsInHand = other.numOceanChaliceCardsInHand;
        //
        this.numWindStatueCardsInHand = other.numWindStatueCardsInHand;
        //
        this.numHelicopterLiftCardsInHand = other.numHelicopterLiftCardsInHand;
        //
        this.earthStoneTreasureCards = other.earthStoneTreasureCards;
        //Collections.copy(earthStoneTreasureCards, other.earthStoneTreasureCards);
        this.fireCrystalTreasureCards = other.fireCrystalTreasureCards;
        //Collections.copy(fireCrystalTreasureCards, other.fireCrystalTreasureCards);
        this.oceanChaliceTreasureCards = other.oceanChaliceTreasureCards;
        //Collections.copy(oceanChaliceTreasureCards, other.oceanChaliceTreasureCards);
        this.windStatueTreasureCards = other.windStatueTreasureCards;
        //Collections.copy(windStatueTreasureCards, other.windStatueTreasureCards);
        this.helicopterLiftCards = other.helicopterLiftCards;
        //Collections.copy(helicopterLiftCards, other.helicopterLiftCards);
        this.player1Location = other.player1Location;
        this.player2Location = other.player2Location;
        this.player3Location = other.player3Location;
        /*HashMap<TileName, Value> copy = new HashMap<>();
        for(Map.Entry<TileName, Value> entry : this.map.entrySet()){
            copy.put(entry.getKey(), entry.getValue());
        }*/
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
                " Flood Meter = "+floodMeter+
                " Number of players = "+numPlayers+
                " Remaining Actions = "+actionsRemaining+
                " Player 1's Earth Stone Cards = "+numEarthStoneCardsInHand[0]+
                " Player 1's Fire Crystal Cards = "+numFireCrystalCardsInHand[0]+
                " Player 1's Wind Statue Cards = "+numWindStatueCardsInHand[0]+
                " Player 1's Ocean Chalice Cards = "+numOceanChaliceCardsInHand[0]+
                " Player 1's Helicopter Lift Cards = "+numHelicopterLiftCardsInHand[0]+
                " Player 2's Earth Stone Cards = "+numEarthStoneCardsInHand[1]+
                " Player 2's Fire Crystal Cards = "+numFireCrystalCardsInHand[1]+
                " Player 2's Wind Statue Cards = "+numWindStatueCardsInHand[1]+
                " Player 2's Ocean Chalice Cards = "+numOceanChaliceCardsInHand[1]+
                " Player 2's Helicopter Lift Cards = "+numHelicopterLiftCardsInHand[1]+
                " Player 3's Earth Stone Cards = "+numEarthStoneCardsInHand[2]+
                " Player 3's Fire Crystal Cards = "+numFireCrystalCardsInHand[2]+
                " Player 3's Wind Statue Cards = "+numWindStatueCardsInHand[2]+
                " Player 3's Ocean Chalice Cards = "+numOceanChaliceCardsInHand[2]+
                " Player 3's Helicopter Lift Cards = "+numHelicopterLiftCardsInHand[2]+
                " Player 1's Location = "+player1Location+
                " Player 2's Location = "+player2Location+
                " Player 3's Location = "+player3Location+ result;
    }

    //action methods
    //should all have boolean return value; false if illegal move for the current game state
    //modify the game state to reflect a given player has taken that action
    //each method should require that the player id of the player who is taking that action be passed in as the first parameter, might need other parameters

    public boolean move(int playerId, TileName t){//takes tile to move to and player whose turn it is
        if(actionsRemaining < 1){
            return false;
        }
        else if (getPlayerTurn() == playerId){//if it is that player's turn
            if(getPlayerTurn() == 0){
                if(getPlayerLocation(1) != t && getPlayerLocation(2) != t){//if the other player's aren't on that tile already
                    player1Location = t;//set the player's location to the tile they clicked
                }
            }
            else if (getPlayerTurn() == 1){
                if(getPlayerLocation(2) != t && getPlayerLocation(0) != t){
                    player2Location = t;
                }
            }
            else if (getPlayerTurn() == 2){
                if(getPlayerLocation(0) != t && getPlayerLocation(1) != t){
                    player3Location = t;
                }
            }
            actionsRemaining--;
            return true;
        }
        else{
            return false;
        }
    }//end of move

    //A player can flip over any of the 4 adjacent tiles or the tile they are on if it has been flipped to flooded previously
    public boolean shoreUp(int playerId, TileName t) {//
        if(actionsRemaining < 1){
            return false;
        }
        else if(getPlayerTurn() == playerId && map.get(t) != Value.SUNK){
            map.put(t, Value.NORMAL);
            actionsRemaining--;
            return true;
        }
        else{
            return false;
        }
    }//end of shoreUp

    //Choose a player to give a treasure card to
    public boolean giveCard(int playerId, int chosenPlayer, int index){
        if(actionsRemaining < 1 || getPlayerHand(chosenPlayer).size() >= 5){//don't give the card away if the recipient has too many cards
            return false;
        }

        if(getPlayerTurn() == playerId && chosenPlayer != getPlayerTurn()){
            TreasureCards card = getPlayerHand(getPlayerTurn()).remove(index);//remove the card the player clicked from their hand
            getPlayerHand(chosenPlayer).add(card);//add the card to the chosen player's hand
            actionsRemaining--;
            return true;
        }
        else{
            return false;
        }
    }//end of giveCard

    public boolean discard(int playerId, int index){
        if(getPlayerTurn() == playerId){
            TreasureCards card = getPlayerHand(getPlayerTurn()).remove(index);//remove the card the player clicked from their hand
            discardTreasureDeck.add(card);//add the card to the discard pile
            return true;
        }
        else{
            return false;
        }
    }//end of discard

    public boolean captureTreasure(int playerId, ArrayList<TreasureCards> a, TileName t){//takes the player whose turn it is, their hand and their location
        if(actionsRemaining < 1){
            return false;
        }

        //create boolean variables to check if the player is on one of the correct tiles
        boolean earthStoneTile = t.equals(TileName.MOON_TEMPLE) || t.equals(TileName.SUN_TEMPLE);
        boolean fireCrystalTile = (t.equals(TileName.SHADOW_CAVE) || t.equals(TileName.EMBER_CAVE));
        boolean oceanChaliceTile = (t.equals(TileName.CORAL_PALACE) || t.equals(TileName.TIDAL_PALACE));
        boolean windStatueTile = (t.equals(TileName.HOWLING_GARDEN) || t.equals(TileName.WHISPERING_GARDENS));

        int count = 0;//keep track of how many treasure cards we have removed so if they have 5 treasure cards we leave 1

        //removing four earth stone cards from the player's hand and capturing the treasure
        for(int i = 0; i < numPlayers; i++){
            if(playerId == i && earthStoneTile) {//if its player 1's turn and they are on the correct tile

                if (a.contains(numEarthStoneCardsInHand[i] >= 4)) {//check if player one has the correct 4 treasure cards in their hand

                    //remove the four (or four of if they have 5) treasure cards from their hand and add them to the discard pile
                    for(int j = 0; j < earthStoneTreasureCards.size(); j++){
                        if(count == 4){
                            numEarthStoneCardsInHand[i] -= 4;//change the player's individual treasure card count
                            isCapturedEarthStone = true;//they captured the earth stone treasure
                            actionsRemaining--;
                            return true;
                        }
                        else if(a.contains(earthStoneTreasureCards.get(j))){
                            count += tryRemoveCard(a, earthStoneTreasureCards.get(j));
                        }
                    }
                }
            }
            else if(playerId == i && fireCrystalTile) {
                if (a.contains(numFireCrystalCardsInHand[i] >= 4)) {//check if player one has the correct 4 treasure cards in their hand

                    //remove the four (or four of if they have 5) treasure cards from their hand and add them to the discard pile
                    for(int j = 0; j < fireCrystalTreasureCards.size(); j++){
                        if(count == 4){
                            numFireCrystalCardsInHand[i] -= 4;//change the player's individual treasure card count
                            isCapturedFireCrystal = true;//they captured the earth stone treasure
                            actionsRemaining--;
                            return true;
                        }
                        else if(a.contains(fireCrystalTreasureCards.get(j))){
                            count += tryRemoveCard(a, fireCrystalTreasureCards.get(j));
                        }
                    }
                }
            }
            else if(playerTurn == i && windStatueTile) {
                if (a.contains(numWindStatueCardsInHand[i] >= 4)) {//check if player one has the correct 4 treasure cards in their hand

                    //remove the four (or four of if they have 5) treasure cards from their hand and add them to the discard pile
                    for(int j = 0; j < windStatueTreasureCards.size(); j++){
                        if(count == 4){
                            numWindStatueCardsInHand[i] -= 4;//change the player's individual treasure card count
                            isCapturedWindStatue = true;//they captured the earth stone treasure
                            actionsRemaining--;
                            return true;
                        }
                        else if(a.contains(windStatueTreasureCards.get(j))){
                            count += tryRemoveCard(a, windStatueTreasureCards.get(j));
                        }
                    }
                }
            }
            else if(playerTurn == i && oceanChaliceTile) {
                if (a.contains(numOceanChaliceCardsInHand[i] >= 4)) {//check if player one has the correct 4 treasure cards in their hand

                    //remove the four (or four of if they have 5) treasure cards from their hand and add them to the discard pile
                    for(int j = 0; j < oceanChaliceTreasureCards.size(); j++){
                        if(count == 4){
                            numOceanChaliceCardsInHand[i] -= 4;//change the player's individual treasure card count
                            isCapturedOceanChalice = true;//they captured the earth stone treasure
                            actionsRemaining--;
                            return true;
                        }
                        else if(a.contains(oceanChaliceTreasureCards.get(j))){
                            count += tryRemoveCard(a, oceanChaliceTreasureCards.get(j));
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
        //check if the treasure deck is empty and if so add the cards from the discard pile back into the treasure deck
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
                if(card1.equals(earthStoneTreasureCards.get(i))){
                    numEarthStoneCardsInHand[playerTurn]++;
                }
                else if(card1.equals(fireCrystalTreasureCards.get(i))){
                    numFireCrystalCardsInHand[playerTurn]++;
                }
                else if(card1.equals(windStatueTreasureCards.get(i))){
                    numWindStatueCardsInHand[playerTurn]++;
                }
                else if(card1.equals(oceanChaliceTreasureCards.get(i))){
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
                if(card2.equals(earthStoneTreasureCards.get(i))){
                    numEarthStoneCardsInHand[playerTurn]++;
                }
                else if(card1.equals(fireCrystalTreasureCards.get(i))){
                    numFireCrystalCardsInHand[playerTurn]++;
                }
                else if(card1.equals(windStatueTreasureCards.get(i))){
                    numWindStatueCardsInHand[playerTurn]++;
                }
                else if(card1.equals(oceanChaliceTreasureCards.get(i))){
                    numOceanChaliceCardsInHand[playerTurn]++;
                }
            }
        }
        drawTreasureLimitCheck();//check if any player has over the 5 card limit in their hand
    }//end of drawTreasure

    //check if any player has over the 5 card limit in their hand and if so remove cards for them to get back to 5
    public void drawTreasureLimitCheck(){
        for(int j = 0; j < numPlayers; j++){
            if(getPlayerHand(j).size() > 5){
                int removeAmount = getPlayerHand(j).size() - 5;
                for(int i = 0; i < removeAmount; i++){
                    discardTreasureDeck.add(getPlayerHand(j).remove(i));//remove those cards from their hand and add to the discardTreasure deck
                }
            }
        }
    }//end of drawTreasureLimitCheck

    public void drawFlood(ArrayList<FloodCards> a) {
        //check if the flood deck is empty and if so add the cards from the discard pile back into the flood deck and shuffle
        if(floodDeck.isEmpty()){
            for(int i = 0; i < discardFloodDeck.size(); i++){
                floodDeck.add(getDiscardFloodDeck().get(i));
            }
            Collections.shuffle(floodDeck);
        }

        //deals flood cards up to the number on the flood meter to the drawnFloodDeck
        for(int i = 0; i < floodMeter; i++) {
            if(floodDeck.size() > 0){
                a.add(floodDeck.remove(i));//remove a card from the floodDeck and add it to the drawnFloodCards array which should be passed in
            }
        }

        goThroughDrawnFloodCards();//change the value of the tile to match the drawn flood cards
    }//end of drawFlood

    public void goThroughDrawnFloodCards() {
        for(int i = 0; i < getDrawnFloodCards().size(); i++){
            if(getDrawnFloodCards().get(i).equals(FloodCards.ABANDONED_CLIFFS) || getDrawnFloodCards().get(i).equals(FloodCards.ABANDONED_CLIFFS1)){
                changeFloodValue(TileName.ABANDONED_CLIFFS);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.FOOLS_LANDING1) || getDrawnFloodCards().get(i).equals(FloodCards.FOOLS_LANDING)){
                changeFloodValue(TileName.FOOLS_LANDING);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.BRONZE_GATE) || getDrawnFloodCards().get(i).equals(FloodCards.BRONZE_GATE1)){
                changeFloodValue(TileName.BRONZE_GATE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.GOLD_GATE) || getDrawnFloodCards().get(i).equals(FloodCards.GOLD_GATE1)){
                changeFloodValue(TileName.GOLD_GATE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.CORAL_PALACE) || getDrawnFloodCards().get(i).equals(FloodCards.CORAL_PALACE1)){
                changeFloodValue(TileName.CORAL_PALACE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.SUN_TEMPLE) || getDrawnFloodCards().get(i).equals(FloodCards.SUN_TEMPLE1)){
                changeFloodValue(TileName.SUN_TEMPLE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.SILVER_GATE) || getDrawnFloodCards().get(i).equals(FloodCards.SILVER_GATE1)){
                changeFloodValue(TileName.SILVER_GATE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.PHANTOM_ROCK) || getDrawnFloodCards().get(i).equals(FloodCards.PHANTOM_ROCK1)){
                changeFloodValue(TileName.PHANTOM_ROCK);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.WATCHTOWER) || getDrawnFloodCards().get(i).equals(FloodCards.WATCHTOWER1)){
                changeFloodValue(TileName.WATCHTOWER);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.COPPER_GATE) || getDrawnFloodCards().get(i).equals(FloodCards.COPPER_GATE1)){
                changeFloodValue(TileName.COPPER_GATE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.WHISPERING_GARDENS) || getDrawnFloodCards().get(i).equals(FloodCards.WHISPERING_GARDENS1)){
                changeFloodValue(TileName.WHISPERING_GARDENS);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.SHADOW_CAVE) || getDrawnFloodCards().get(i).equals(FloodCards.SHADOW_CAVE1)){
                changeFloodValue(TileName.SHADOW_CAVE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.LOST_LAGOON) || getDrawnFloodCards().get(i).equals(FloodCards.LOST_LAGOON1)){
                changeFloodValue(TileName.LOST_LAGOON);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.MOON_TEMPLE) || getDrawnFloodCards().get(i).equals(FloodCards.MOON_TEMPLE1)){
                changeFloodValue(TileName.MOON_TEMPLE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.DECEPTION_DUNES) || getDrawnFloodCards().get(i).equals(FloodCards.DECEPTION_DUNES1)){
                changeFloodValue(TileName.DECEPTION_DUNES);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.TWILIGHT_HOLLOW) || getDrawnFloodCards().get(i).equals(FloodCards.TWILIGHT_HOLLOW1)){
                changeFloodValue(TileName.TWILIGHT_HOLLOW);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.EMBER_CAVE) || getDrawnFloodCards().get(i).equals(FloodCards.EMBER_CAVE1)){
                changeFloodValue(TileName.EMBER_CAVE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.TIDAL_PALACE) || getDrawnFloodCards().get(i).equals(FloodCards.TIDAL_PALACE1)){
                changeFloodValue(TileName.TIDAL_PALACE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.OBSERVATORY) || getDrawnFloodCards().get(i).equals(FloodCards.OBSERVATORY1)){
                changeFloodValue(TileName.OBSERVATORY);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.IRON_GATE) || getDrawnFloodCards().get(i).equals(FloodCards.IRON_GATE1)){
                changeFloodValue(TileName.IRON_GATE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.CRIMSON_FOREST) || getDrawnFloodCards().get(i).equals(FloodCards.CRIMSON_FOREST1)){
                changeFloodValue(TileName.CRIMSON_FOREST);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.MISTY_MARSH) || getDrawnFloodCards().get(i).equals(FloodCards.MISTY_MARSH1)){
                changeFloodValue(TileName.MISTY_MARSH);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.BREAKERS_BRIDGE) || getDrawnFloodCards().get(i).equals(FloodCards.BREAKERS_BRIDGE1)){
                changeFloodValue(TileName.BREAKERS_BRIDGE);
            }
            else if(getDrawnFloodCards().get(i).equals(FloodCards.HOWLING_GARDEN) || getDrawnFloodCards().get(i).equals(FloodCards.HOWLING_GARDEN1)){
                changeFloodValue(TileName.HOWLING_GARDEN);
            }
        }
        emptyDrawnFloodCards();//empty the drawnFloodCards arrayList
    }//end of changeFloodValue

    public void changeFloodValue(TileName card){
        //if the value of the tile was normal change it to flooded
        if(map.get(card).equals(Value.NORMAL)){
            map.put(card, Value.FLOODED);
        }
        //if the value of the tile was flooded change it to sunk
        else if(map.get(card).equals(Value.FLOODED)){
            map.put(card, Value.SUNK);
        }
    }//end of changeFloodValue

    public void emptyDrawnFloodCards(){
        for(int i = 0; i < drawnFloodCards.size(); i++){
            discardFloodDeck.add(drawnFloodCards.remove(i));
        }
    }//end of emptyDrawnFloodCards

    public void endTurn(){//change whose turn it is
        if (numPlayers > 2) {
            if (getPlayerTurn() == 0) {
                setPlayerTurn(1);
            }
            else if(getPlayerTurn() == 1) {
                setPlayerTurn(2);
            }
            else if(getPlayerTurn() == 2) {
                setPlayerTurn(0);
            }
        }
        else {
            if(getPlayerTurn() == 0) {
                setPlayerTurn(1);
            }
            else if(getPlayerTurn() == 1) {
                setPlayerTurn(0);
            }
        }

        setActionsRemaining(3);//set the next player's actionsRemaining back to 3
    }//end of endTurn

    public boolean areTreasuresCaptured(){//method to check if all four treasures have been captured
        if(isCapturedEarthStone && isCapturedFireCrystal && isCapturedWindStatue && isCapturedOceanChalice){
            return true;
        }
        else{
            return false;
        }
    }//end of areTreasuresCaptured

    TileName locationToCheck = getPlayerLocation(getPlayerTurn());
    public boolean isOnCorrectWSTile(){//method to check if smart ai is on one of the correct tiles to capture a treasure
        if(locationToCheck.equals(FiGameState.TileName.WHISPERING_GARDENS) || locationToCheck.equals(FiGameState.TileName.HOWLING_GARDEN)){
            return true;
        }
        else{
            return false;
        }
    }//end of isOnCorrectWSTile
    public boolean isOnCorrectOCTile(){//method to check if smart ai is on one of the correct tiles to capture a treasure
        if(locationToCheck.equals(FiGameState.TileName.CORAL_PALACE) || locationToCheck.equals(FiGameState.TileName.TIDAL_PALACE)){
            return true;
        }
        else{
            return false;
        }
    }//end of isOnCorrectOCTile
    public boolean isOnCorrectFCTile(){//method to check if smart ai is on one of the correct tiles to capture a treasure
        if(locationToCheck.equals(FiGameState.TileName.EMBER_CAVE) || locationToCheck.equals(FiGameState.TileName.SHADOW_CAVE)){
            return true;
        }
        else{
            return false;
        }
    }//end of isOnCorrectFCTile
    public boolean isOnCorrectESTile(){//method to check if smart ai is on one of the correct tiles to capture a treasure
        if(locationToCheck.equals(FiGameState.TileName.MOON_TEMPLE) || locationToCheck.equals(FiGameState.TileName.SUN_TEMPLE)){
            return true;
        }
        else{
            return false;
        }
    }//end of isOnCorrectESTile

    public boolean areAllTilesSunk(){
        int count = 0;//keep track of how many tiles are sunk

        //go through the Tiles and count how many tiles have the value: sunk
        TileName values[] = TileName.values();
        for(TileName value: values){
            if(map.get(value).equals(Value.SUNK)){
                count++;
            }
        }

        if(count == 24){//if all the tiles have sunk
            return true;
        }
        else{
            return false;
        }
    }//end of areAllTilesSunk

    public String isGameLost(){//method to check possible ways the game could be lost
        if(map.get(TileName.FOOLS_LANDING).equals(Value.SUNK)) {
            return "Game Over! You lost because Fools Landing sunk!";
        }
        else if(map.get(TileName.CORAL_PALACE).equals(Value.SUNK) && map.get(TileName.TIDAL_PALACE).equals(Value.SUNK) && !isCapturedOceanChalice){
            return "Game Over! You lost because your Ocean tiles sunk before you collected the treasure!";
        }
        else if(map.get(TileName.EMBER_CAVE).equals(Value.SUNK) && map.get(TileName.SHADOW_CAVE).equals(Value.SUNK) && !isCapturedFireCrystal){
            return "Game Over! You lost because your Fire Crystal tiles sunk before you collected the treasure!";
        }
        else if (map.get(TileName.MOON_TEMPLE).equals(Value.SUNK) && map.get(TileName.SUN_TEMPLE).equals(Value.SUNK) && !isCapturedEarthStone){
            return "Game Over! You lost because your Earth Stone tiles sunk before you collected the treasure!";
        }
        else if (map.get(TileName.MOON_TEMPLE).equals(Value.SUNK) && map.get(TileName.SUN_TEMPLE).equals(Value.SUNK) && !isCapturedWindStatue){
            return "Game Over! You lost because your Wind Statue tiles sunk before you collected the treasure!";
        }
        else if(areAllTilesSunk()){
            return "Game Over! You lost because all the tiles sunk before you collected the treasures!";
        }
        return null;
    }//end of isGameLost

    //setter methods
    public void setPlayerTurn(int playerId){this.playerTurn = playerId;}
    public void setActionsRemaining(int aR) {this.actionsRemaining = aR;}
    public void setPlayerLocation(int playerId, TileName t){//returns the location of the player passed in as a parameter
        if(playerId == 0) {
            this.player1Location = t;
        }
        else if(playerId == 1) {
            this.player2Location = t;
        }
        else{
            this.player3Location = t;
        }
    }

    //getter methods
    public int getFloodMeter() {return this.floodMeter;}
    public int getPlayerTurn(){return this.playerTurn;}
    public int getNumberOfCardsInHand(ArrayList<TreasureCards> a) {return a.size();}
    public ArrayList<FloodCards> getDrawnFloodCards(){return this.drawnFloodCards;}
    public ArrayList<FloodCards> getDiscardFloodDeck(){return this.discardFloodDeck;}
    public ArrayList<TreasureCards> getDiscardTreasureDeck(){return this.discardTreasureDeck;}
    public int getDiscardTreasureDeckSize() {return discardTreasureDeck.size();}
    public int getActionsRemaining() {return this.actionsRemaining;}
    public ArrayList<TreasureCards> getEarthStoneTreasureCards(){return this.earthStoneTreasureCards;}
    public ArrayList<TreasureCards> getOceanChaliceTreasureCards(){return this.oceanChaliceTreasureCards;}
    public ArrayList<TreasureCards> getWindStatueTreasureCards(){return this.windStatueTreasureCards;}
    public ArrayList<TreasureCards> getFireCrystalTreasureCards(){return this.fireCrystalTreasureCards;}
    public ArrayList<TreasureCards> getHelicopterLiftCards(){
        return this.helicopterLiftCards;
    }
    public int getNumPlayers() {return this.numPlayers;}

    public String getHand(ArrayList<TreasureCards> a) {//returns the hand of the arrayList passed as a string
        String str = "";
        for(int i = 0; i < a.size(); i++){
            str += a.get(i) + " ";
        }
        return str;
    }

    public ArrayList<TreasureCards> getPlayerHand(int playerId){//get the hand of the player passed in as a parameter
        if(playerId == 0){
            return this.humanPlayerHand;
        }
        else if(playerId == 1){
            return this.dumbAiHand;
        }
        else{
            return this.smartAiHand;
        }
    }
    public TileName getPlayerLocation(int playerId){//returns the location of the player passed in as a parameter
        if(playerId == 0) {
            return this.player1Location;
        }
        else if(playerId == 1) {
            return this.player2Location;
        }
        else{
            return this.player3Location;
        }
    }
}
