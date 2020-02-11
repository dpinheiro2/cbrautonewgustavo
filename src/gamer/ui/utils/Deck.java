package gamer.ui.utils;

import org.paukov.combinatorics3.Generator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 27/11/2019.
 */


public class Deck {

    private ArrayList<Card> cards;
    private ArrayList<Hand> allHands;
    private ArrayList<Hand> allOpponentHands;

    public Deck() {
        this.cards = new ArrayList<Card>();
        this.allHands = new ArrayList<Hand>();
        this.allOpponentHands = new ArrayList<Hand>();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Hand> getAllHands() {
        return allHands;
    }

    public void setAllHands(ArrayList<Hand> allHands) {
        this.allHands = allHands;
    }

    public ArrayList<Hand> getAllOpponentHands() {
        return allOpponentHands;
    }

    public void setAllOpponentHands(ArrayList<Hand> allOpponentHands) {
        this.allOpponentHands = allOpponentHands;
    }

    public void createDeck() {
        for(Suit suit : Suit.values()) {
            for(Face face : Face.values()) {
                this.cards.add(new Card(face, suit, Card.getCbrCode(face, suit), Card.getHierarquia(face, suit)));
            }
        }
        Collections.sort(cards, Collections.reverseOrder(Card.compareCards()));
        //System.out.println("Total de Cartas: " + cards.size());
    }

    public void gerarAllHandsPossible() {
        Generator.combination(cards)
                .simple(3)
                .stream()
                .forEach(
                        cards -> {
                            Hand hand = new Hand();
                            hand.setCard1(cards.get(0));
                            hand.setCard2(cards.get(1));
                            hand.setCard3(cards.get(2));
                            hand.setStrenght(Hand.getStrenghtHand(hand));
                            hand.setEnvidoPoints(hand.getEnvidoFlorPoints());
                            allHands.add(hand);
                        });
        Collections.sort(allHands, Collections.reverseOrder(Hand.compareHands()));
        //System.out.println("Total Mãos: " + allHands.size());
        //System.out.println(allHands.toString());
        /*allHands.forEach(hand -> {
            System.out.println(hand.toString());
        });*/
    }

    public void gerarAllOpponentHandsPossible(String card1, String card2, String card3) {

        ArrayList<Card> tempCards = new ArrayList<>();
        tempCards.addAll(cards);

        tempCards.removeIf(card -> (card.getCarta().equals(card1) || card.getCarta().equals(card2) || card.getCarta().equals(card3)));

        Generator.combination(tempCards)
                .simple(3)
                .stream()
                .forEach(
                        cards -> {
                            Hand hand = new Hand();
                            hand.setCard1(cards.get(0));
                            hand.setCard2(cards.get(1));
                            hand.setCard3(cards.get(2));
                            hand.setStrenght(Hand.getStrenghtHand(hand));
                            hand.setEnvidoPoints(hand.getEnvidoFlorPoints());
                            allOpponentHands.add(hand);
                        });

        Collections.sort(allOpponentHands, Collections.reverseOrder(Hand.compareHands()));
        //System.out.println(allOpponentHands.size());
    }

    public double getStrenghtHand(String card1, String card2, String card3) {

        double strenghHand = 0.0;

        strenghHand = allHands.stream().filter(hand -> ( (hand.getCard1().getCarta().equals(card1) || hand.getCard1().getCarta().equals(card2) || hand.getCard1().getCarta().equals(card3)) &&
                (hand.getCard2().getCarta().equals(card1) || hand.getCard2().getCarta().equals(card2) || hand.getCard2().getCarta().equals(card3)) &&
                (hand.getCard3().getCarta().equals(card1) || hand.getCard3().getCarta().equals(card2) || hand.getCard3().getCarta().equals(card3)))).findFirst().get().getStrenght();

        return strenghHand;
    }


    public int getPossibilidadesMelhorMao(int isHand, String card1, String card2, String card3) {

        if (isHand == 0) {
            return (int) allOpponentHands.stream().filter(hand -> (hand.getStrenght() >= getStrenghtHand(card1, card2, card3))).count();
        } else {
            return (int) allOpponentHands.stream().filter(hand -> (hand.getStrenght() > getStrenghtHand(card1, card2, card3))).count();
        }

    }

    public double getProbabilidadeMelhorMao(int isHand, String card1, String card2, String card3) {

     /*  BigDecimal bigDecimal1 = new BigDecimal(getPossibilidadesMelhorMao(isHand, card1, card2, card3));
        BigDecimal bigDecimal2 = new BigDecimal(allOpponentHands.size());


        return (bigDecimal1.divide(bigDecimal2)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();*/

        BigDecimal bigDecimal1 = new BigDecimal(getPossibilidadesMelhorMao(isHand, card1, card2, card3));
        BigDecimal bigDecimal2 = new BigDecimal(allOpponentHands.size());

        double resultado = bigDecimal1.divide(bigDecimal2, 2, RoundingMode.HALF_EVEN).doubleValue();

        return resultado;

    }

    public int getPossibilidadesMelhorMao(int isHand, String card1, String card2, String card3, String opponentCard1) {

        int possiveisMaos = (int) allOpponentHands
                .stream()
                .filter(hand -> (hand.getCard1().getCarta().equals(opponentCard1) || hand.getCard2().getCarta().equals(opponentCard1)
                        || hand.getCard3().getCarta().equals(opponentCard1)) )
                .count();

        if (isHand == 0) {
            return (int) allOpponentHands
                    .stream()
                    .filter(hand -> (hand.getStrenght() >= getStrenghtHand(card1, card2, card3) &&
                            (hand.getCard1().getCarta().equals(opponentCard1) || hand.getCard2().getCarta().equals(opponentCard1)
                                    || hand.getCard3().getCarta().equals(opponentCard1)) ))
                    .count();
        } else {
            return (int) allOpponentHands
                    .stream()
                    .filter(hand -> (hand.getStrenght() > getStrenghtHand(card1, card2, card3)  &&
                            (hand.getCard1().getCarta().equals(opponentCard1) || hand.getCard2().getCarta().equals(opponentCard1)
                                    || hand.getCard3().getCarta().equals(opponentCard1))))
                    .count();
        }
    }

    public double getProbabilidadeMelhorMao(int isHand, String card1, String card2, String card3, String opponentCard1) {

        int possibilidades = 0;

        List<Hand> tmp = allOpponentHands.stream()
                .filter(hand -> (hand.getCard1().getCarta().equals(opponentCard1) || hand.getCard2().getCarta().equals(opponentCard1)
                        || hand.getCard3().getCarta().equals(opponentCard1)) ).collect(Collectors.toList());
        //tmp.forEach(System.out::println);

        int possiveisMaos = tmp.size();

        if (isHand == 0) {
            possibilidades = (int) tmp
                    .stream()
                    .filter(hand -> (hand.getStrenght() >= getStrenghtHand(card1, card2, card3) ))
                    .count();
        } else {
            possibilidades = (int) tmp
                    .stream()
                    .filter(hand -> (hand.getStrenght() > getStrenghtHand(card1, card2, card3) ))
                    .count();
        }

        return (double) possibilidades / (double) possiveisMaos;

    }

    public double getProbabilidadeMelhorMao(int isHand, String card1, String card2, String card3, int opponentEnvidoPoints) {

        int possibilidades = 0;

        List<Hand> tmp = allOpponentHands.stream()
                .filter(hand -> (hand.getEnvidoPoints() == opponentEnvidoPoints)).collect(Collectors.toList());
        //tmp.forEach(System.out::println);

        int possiveisMaos = tmp.size();

        if (isHand == 0) {
            possibilidades = (int) tmp
                    .stream()
                    .filter(hand -> (hand.getStrenght() >= getStrenghtHand(card1, card2, card3) ))
                    .count();
        } else {
            possibilidades = (int) tmp
                    .stream()
                    .filter(hand -> (hand.getStrenght() > getStrenghtHand(card1, card2, card3) ))
                    .count();
        }

        return (double) possibilidades / (double) possiveisMaos;

    }


    public double getProbabilidadeMelhorMao(int isHand, String card1, String card2, String card3, String opponentCard1,
                                            int opponentEnvidoPoints) {

        int possibilidades = 0;

        List<Hand> tmp = allOpponentHands.stream()
                .filter(hand -> (hand.getEnvidoPoints() == opponentEnvidoPoints &&
                        (hand.getCard1().getCarta().equals(opponentCard1) || hand.getCard2().getCarta().equals(opponentCard1) ||
                                hand.getCard3().getCarta().equals(opponentCard1)))).collect(Collectors.toList());
        //tmp.forEach(System.out::println);

        int possiveisMaos = tmp.size();

        if (isHand == 0) {
            possibilidades = (int) tmp
                    .stream()
                    .filter(hand -> (hand.getStrenght() >= getStrenghtHand(card1, card2, card3) ))
                    .count();
        } else {
            possibilidades = (int) tmp
                    .stream()
                    .filter(hand -> (hand.getStrenght() > getStrenghtHand(card1, card2, card3) ))
                    .count();
        }

        return (double) possibilidades / (double) possiveisMaos;

    }

    public double getProbabilidadeMelhorMao(int isHand, String card1, String card2, String card3, String opponentCard1,
                                            String opponentCard2) {

        int possibilidades = 0;

        List<Hand> tmp = allOpponentHands.stream()
                .filter(hand -> ( (hand.getCard1().getCarta().equals(opponentCard1) || hand.getCard2().getCarta().equals(opponentCard1) ||
                        hand.getCard3().getCarta().equals(opponentCard1)) &&
                        (hand.getCard1().getCarta().equals(opponentCard2) || hand.getCard2().getCarta().equals(opponentCard2) ||
                                hand.getCard3().getCarta().equals(opponentCard2)))).collect(Collectors.toList());
        //tmp.forEach(System.out::println);

        int possiveisMaos = tmp.size();

        if (isHand == 0) {
            possibilidades = (int) tmp
                    .stream()
                    .filter(hand -> (hand.getStrenght() >= getStrenghtHand(card1, card2, card3) ))
                    .count();
        } else {
            possibilidades = (int) tmp
                    .stream()
                    .filter(hand -> (hand.getStrenght() > getStrenghtHand(card1, card2, card3) ))
                    .count();
        }


        return (double) possibilidades / (double) possiveisMaos;

    }

    public double getProbabilidadeMelhorMao(int isHand, String card1, String card2, String card3, String opponentCard1,
                                            String opponentCard2, int opponentEnvidoPoints) {

        int possibilidades = 0;

        List<Hand> tmp = allOpponentHands.stream()
                .filter(hand -> (hand.getEnvidoPoints() == opponentEnvidoPoints &&
                        (hand.getCard1().getCarta().equals(opponentCard1) || hand.getCard2().getCarta().equals(opponentCard1) ||
                                hand.getCard3().getCarta().equals(opponentCard1)) &&
                        (hand.getCard1().getCarta().equals(opponentCard2) || hand.getCard2().getCarta().equals(opponentCard2) ||
                                hand.getCard3().getCarta().equals(opponentCard2)))).collect(Collectors.toList());
        //tmp.forEach(System.out::println);

        int possiveisMaos = tmp.size();

        if (isHand == 0) {
            possibilidades = (int) tmp
                    .stream()
                    .filter(hand -> (hand.getStrenght() >= getStrenghtHand(card1, card2, card3) ))
                    .count();
        } else {
            possibilidades = (int) tmp
                    .stream()
                    .filter(hand -> (hand.getStrenght() > getStrenghtHand(card1, card2, card3) ))
                    .count();
        }



        return (double) possibilidades / (double) possiveisMaos;

    }

    public double getProbabilidadeMelhorCarta(int isHand, String card1, String card2, String card3, String opponentCard1,
                                              String opponentCard2) {

        int possibilidades = 0;

        ArrayList<Card> tempCards = new ArrayList<>();
        tempCards.addAll(cards);

        tempCards.removeIf(card -> (card.getCarta().equals(card1) || card.getCarta().equals(card2) || card.getCarta().equals(card3)
                || card.getCarta().equals(opponentCard1) || card.getCarta().equals(opponentCard2)));

        //tempCards.forEach(System.out::println);

        int possiveisCartas = tempCards.size();

        if (isHand == 0) {
            possibilidades = (int) tempCards
                    .stream()
                    .filter(card -> (card.getCbrCode() >= Card.getCbrCode(card3)))
                    .count();
        } else {
            possibilidades = (int) tempCards
                    .stream()
                    .filter(card -> (card.getCbrCode() > Card.getCbrCode(card3)))
                    .count();
        }

        return (double) possibilidades / (double) possiveisCartas;
    }

    public double getProbabilidadeMelhorEnvido(int isHand, int envidoPoints) {

        List<Hand> tmp;

        if (isHand == 0) {

            tmp = allOpponentHands.stream()
                    .filter(hand -> (hand.getEnvidoPoints() >= envidoPoints)).collect(Collectors.toList());
        } else {
            tmp = allOpponentHands.stream()
                    .filter(hand -> (hand.getEnvidoPoints() > envidoPoints)).collect(Collectors.toList());
        }

        // tmp.forEach(System.out::println);

        int possiveisMaos = tmp.size();

        //System.out.println(possiveisMaos);



        return (double) possiveisMaos / (double) allOpponentHands.size();

    }

    public double getProbabilidadeMelhorEnvido(int isHand, int envidoPoints, String opponentCard1) {

        List<Hand> aux1;
        List<Hand> aux2;

        aux1 = allOpponentHands.stream().filter(hand -> (hand.getCard1().getCarta().equals(opponentCard1) || hand.getCard2().getCarta().equals(opponentCard1) ||
                hand.getCard3().getCarta().equals(opponentCard1))).collect(Collectors.toList());


        aux2 = aux1.stream().filter(hand -> (hand.getEnvidoPoints() >= envidoPoints)).collect(Collectors.toList());


        //System.out.println("Todas as maos possiveis do oponente: " + aux1.size());


        return (double) aux2.size() / (double) aux1.size();

        /*List<Hand> tmp;

        if (isHand == 0) {

            tmp = allOpponentHands.stream()
                    .filter(hand -> (hand.getEnvidoPoints() >= envidoPoints && (hand.getCard1().getCarta().equals(opponentCard1) || hand.getCard2().getCarta().equals(opponentCard1) ||
                            hand.getCard3().getCarta().equals(opponentCard1)))).collect(Collectors.toList());
        } else {
            tmp = allOpponentHands.stream()
                    .filter(hand -> (hand.getEnvidoPoints() > envidoPoints && (hand.getCard1().getCarta().equals(opponentCard1) || hand.getCard2().getCarta().equals(opponentCard1) ||
                            hand.getCard3().getCarta().equals(opponentCard1)))).collect(Collectors.toList());
        }

        //tmp.forEach(System.out::println);

        int possiveisMaos = tmp.size();

        //System.out.println(possiveisMaos);


        return (double) possiveisMaos / (double) allOpponentHands.size();*/

    }


}
