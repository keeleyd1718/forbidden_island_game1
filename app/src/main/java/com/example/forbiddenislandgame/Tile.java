package com.example.forbiddenislandgame;

public class Tile {
    // Instance Variables //
    private TileName tileName;
    private Value value;

    /** Tile Default Cst */
    public Tile(TileName tileName) {
        this.value = Value.NORMAL;
        this.tileName = tileName;
    }

    // set Tile value
    public enum Value {
        NORMAL,
        FLOODED,
        SUNK,
        NONE
    }//Value

    // Enum for the names of the tiles
    public enum TileName {
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


    /**
     * Reverts tile back to its "empty state"
     */
    /*public void revertTile() {
        setValue(Value.NORMAL);
    }//revertTile

    //getter methods
    public Value getValue() {return value;}//getValue
    public TileName getTileName() {return tileName;}//getTilename

    //setter methods
    public void setValue(Value value) {this.value = value;} //set Tile Value
    public void setTileName(TileName tileName) {this.tileName = tileName;} //set Tile Name*/
}//Tile
