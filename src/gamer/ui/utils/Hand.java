package gamer.ui.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 27/11/2019.
 */


public class Hand {

    private Card card1;
    private Card card2;
    private Card card3;
    private Double strenght;
    private int envidoPoints;

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public Card getCard3() {
        return card3;
    }

    public void setCard3(Card card3) {
        this.card3 = card3;
    }

    public Double getStrenght() {
        return strenght;
    }

    public void setStrenght(Double strenght) {
        this.strenght = strenght;
    }

    public int getEnvidoPoints() {
        return envidoPoints;
    }

    public void setEnvidoPoints(int envidoPoints) {
        this.envidoPoints = envidoPoints;
    }

    public static double getStrenghtHand(Hand hand) {

        double strenght = 0.0;


        strenght = (( hand.getCard1().getHierarquia() + hand.getCard2().getHierarquia() + hand.getCard3().getHierarquia()) / 3);

        return strenght;
    }

    public int getEnvidoFlorPoints() {

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);

        HashMap<Suit, ArrayList<Card>> cardsBySuit = new HashMap<>();

        cards.forEach(card -> {
            if (!cardsBySuit.containsKey(card.getSuit())) {
                cardsBySuit.put(card.getSuit(), new ArrayList<>());
            }
            cardsBySuit.get(card.getSuit()).add(card);
        });

        int modeLength = 0;
        Suit modeSuit = Suit.ESPADAS;

        for(Suit suit : cardsBySuit.keySet()){
            if(cardsBySuit.get(suit).size() > modeLength){
                modeLength = cardsBySuit.get(suit).size();
                modeSuit = suit;
            }
        }
        int envidoPoints = 0;

        if(modeLength > 1) {
            ArrayList<Card> envidoCards = cardsBySuit.get(modeSuit);
            envidoPoints = 20;

            for(Card card : envidoCards){
                if (card.getFace().getValue() < 10)
                    envidoPoints += card.getFace().getValue();
                else
                    envidoPoints += 0;
            }
            return envidoPoints;
        } else {

            for(Card card : cards) {
                if(card.getFace().getValue() > envidoPoints && card.getFace().getValue() < 10) {
                    envidoPoints = card.getFace().getValue();
                }
            }

            return envidoPoints;
        }
    }

    public static Comparator<Hand> compareHands() {
        Comparator<Hand> comp = new Comparator<Hand>() {
            @Override
            public int compare(Hand h1, Hand h2) {
                if(h1 == null || h2 == null) {
                    return -1;
                }
                return (h1.getStrenght()).compareTo((h2.getStrenght()));
            }
        };
        return comp;
    }



    @Override
    public String toString() {
        return  "{" +
                card1.getCarta() +
                ", " + card2.getCarta() +
                ", " + card3.getCarta() +
                ", " + strenght +
                ", " + envidoPoints + "}";
    }
}
