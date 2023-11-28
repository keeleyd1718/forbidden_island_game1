package com.example.forbiddenislandgame;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void drawTreasure(){
        FiGameState gs = new FiGameState();
        ArrayList<FiGameState.TreasureCards> hand = new ArrayList<>();

        gs.drawTreasure(hand);//should add 2 treasure cards to hand unless flood cards were drawn

        int numCardInHand = gs.getNumberOfCardsInHand(hand);//should be 2 unless flood cards were drawn
        int twoUnlessFloodCards = 2;

        System.out.println(gs.getHand(hand));

        //if both cards were flood cards expected should be zero now
        if((gs.getHand(hand).startsWith("WATERS_RISE")) && (gs.getHand(hand).endsWith("WATERS_RISE1 ")) || (gs.getHand(hand).endsWith("WATERS_RISE2 ")) || (gs.getHand(hand).endsWith("WATERS_RISE3 "))){
            twoUnlessFloodCards -= 2;
        }
        else if(gs.getHand(hand).endsWith("WATERS_RISE1 ")){
            twoUnlessFloodCards -= 1;
        }
        else if(gs.getHand(hand).endsWith("WATERS_RISE2 ")){
            twoUnlessFloodCards -= 1;
        }
        else if(gs.getHand(hand).endsWith("WATERS_RISE3 ")){
            twoUnlessFloodCards -= 1;
        }
        else if(gs.getHand(hand).startsWith("WATERS_RISE")){
            twoUnlessFloodCards -= 1;
        }
        assertEquals(twoUnlessFloodCards, numCardInHand);
    }

    @Test
    public void floodMeter(){
        FiGameState gs = new FiGameState();
        ArrayList<FiGameState.TreasureCards> hand = new ArrayList<>();

        gs.drawTreasure(hand);//should add 2 treasure cards to hand unless flood cards were drawn

        System.out.println(gs.getHand(hand));

        if((gs.getHand(hand).startsWith("WATERS_RISE")) && (gs.getHand(hand).endsWith("WATERS_RISE1 ")) || (gs.getHand(hand).endsWith("WATERS_RISE2 ")) || (gs.getHand(hand).endsWith("WATERS_RISE3 "))){
            int fM = gs.getFloodMeter();
            assertEquals(2, fM);
        }
        else if(gs.getHand(hand).endsWith("WATERS_RISE1 ")){
            int fM = gs.getFloodMeter();
            assertEquals(1, fM);
        }
        else if(gs.getHand(hand).endsWith("WATERS_RISE2 ")){
            int fM = gs.getFloodMeter();
            assertEquals(1, fM);
        }
        else if(gs.getHand(hand).endsWith("WATERS_RISE3 ")){
            int fM = gs.getFloodMeter();
            assertEquals(1, fM);
        }
        else if(gs.getHand(hand).startsWith("WATERS_RISE")){
            int fM = gs.getFloodMeter();
            assertEquals(1, fM);
        }
        else{
            int fM = gs.getFloodMeter();
            assertEquals(0, fM);
        }
    }

    @Test
    public void discardTreasureDeck(){
        FiGameState gs = new FiGameState();
        ArrayList<FiGameState.TreasureCards> hand = new ArrayList<>();

        gs.drawTreasure(hand);//should add 2 treasure cards to hand unless flood cards were drawn

        System.out.println(gs.getHand(hand));

        if((gs.getHand(hand).startsWith("WATERS_RISE")) && (gs.getHand(hand).endsWith("WATERS_RISE1 ")) || (gs.getHand(hand).endsWith("WATERS_RISE2 ")) || (gs.getHand(hand).endsWith("WATERS_RISE3 "))){
            int discardT = gs.getDiscardTreasureDeckSize();
            assertEquals(2, discardT);
        }
        else if(gs.getHand(hand).endsWith("WATERS_RISE1 ")){
            int discardT = gs.getDiscardTreasureDeckSize();
            assertEquals(1, discardT);
        }
        else if(gs.getHand(hand).endsWith("WATERS_RISE2 ")){

            int discardT = gs.getDiscardTreasureDeckSize();
            assertEquals(1, discardT);
        }
        else if(gs.getHand(hand).endsWith("WATERS_RISE3 ")){
            int discardT = gs.getDiscardTreasureDeckSize();
            assertEquals(1, discardT);
        }
        else if(gs.getHand(hand).startsWith("WATERS_RISE")){
            int discardT = gs.getDiscardTreasureDeckSize();
            assertEquals(1, discardT);
        }
        else{
            int discardT = gs.getDiscardTreasureDeckSize();
            assertEquals(0, discardT);
        }
    }
}