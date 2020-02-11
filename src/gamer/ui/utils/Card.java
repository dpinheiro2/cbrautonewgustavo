package gamer.ui.utils;

import java.util.Comparator;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 19/07/2018.
 */

public class Card {

    private Face face;
    private Suit suit;
    private int cbrCode;
    private double hierarquia;
    private String carta;

    public Card(Face face, Suit suit, int cbrCode, double hierarquia) {
        this.face = face;
        this.cbrCode = cbrCode;
        this.suit = suit;
        this.hierarquia = hierarquia;
        this.carta = face.getValue()+suit.getValue();
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public int getCbrCode() {
        return cbrCode;
    }


    public void setCbrCode(int cbrCode) {
        this.cbrCode = cbrCode;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public double getHierarquia() {
        return hierarquia;
    }

    public void setHierarquia(double hierarquia) {
        this.hierarquia = hierarquia;
    }

    public String getCarta() {
        return carta;
    }

    public void setCarta(String carta) {
        this.carta = carta;
    }

    public static Comparator<Card> compareCards() {
        Comparator<Card> comp = new Comparator<Card>() {
            @Override
            public int compare(Card c1, Card c2) {
                if(c1 == null || c2 == null) {
                    return -1;
                }
                return new Integer(c1.getCbrCode()).compareTo(new Integer(c2.getCbrCode()));
            }
        };
        return comp;
    }

    @Override
    public String toString() {
        return "Card{" +
                "face=" + face +
                ", suit=" + suit +
                ", cbrCode=" + cbrCode +
                ", hierarquia=" + hierarquia +
                ", carta='" + carta + '\'' +
                '}';
    }

    public static int getCbrCode(Face face, Suit suit) {
        int cbrCode = 0;
        switch (face) {
            case AS:
                switch (suit) {
                    case ESPADAS:
                        cbrCode = 52;
                        break;
                    case BASTOS:
                        cbrCode = 50;
                        break;
                    default:
                        cbrCode = 12;
                }
                break;
            case DOIS:
                cbrCode = 16;
                break;
            case TRES:
                cbrCode = 24;
                break;
            case QUATRO:
                cbrCode = 1;
                break;
            case CINCO:
                cbrCode = 2;
                break;
            case SEIS:
                cbrCode = 3;
                break;
            case SETE:
                switch (suit) {
                    case ESPADAS:
                        cbrCode = 42;
                        break;
                    case OURO:
                        cbrCode = 40;
                        break;
                    default:
                        cbrCode = 4;
                }
                break;
            case DEZ:
                cbrCode = 6;
                break;
            case VALETE:
                cbrCode = 7;
                break;
            case REI:
                cbrCode = 8;
                break;
        }

        return cbrCode;
    }

    public static double getHierarquia(Face face, Suit suit) {
        double hierarquia = 0;
        switch (face) {
            case AS:
                switch (suit) {
                    case ESPADAS:
                        hierarquia = 14;
                        break;
                    case BASTOS:
                        hierarquia = 13;
                        break;
                    default:
                        hierarquia = 8;
                }
                break;
            case DOIS:
                hierarquia = 9;
                break;
            case TRES:
                hierarquia = 10;
                break;
            case QUATRO:
                hierarquia = 1;
                break;
            case CINCO:
                hierarquia = 2;
                break;
            case SEIS:
                hierarquia = 3;
                break;
            case SETE:
                switch (suit) {
                    case ESPADAS:
                        hierarquia = 12;
                        break;
                    case OURO:
                        hierarquia = 11;
                        break;
                    default:
                        hierarquia = 4;
                }
                break;
            case DEZ:
                hierarquia = 5;
                break;
            case VALETE:
                hierarquia = 6;
                break;
            case REI:
                hierarquia = 7;
                break;
        }

        return hierarquia;
    }

    public static int getCbrCode(String card) {
        int cbrCode = 0;

        if (card.equals("1e")) {
            cbrCode = 52;
        } else if (card.equals("1p")) {
            cbrCode = 50;
        } else if (card.equals("7e")) {
            cbrCode = 42;
        } else if (card.equals("70")) {
            cbrCode = 40;
        } else if (card.equals("3e") || card.equals("3p") || card.equals("3c") || card.equals("3o")) {
            cbrCode = 24;
        } else if (card.equals("2e") || card.equals("2p") || card.equals("2c") || card.equals("2o")) {
            cbrCode = 16;
        } else if (card.equals("1c") || card.equals("1o")) {
            cbrCode = 12;
        } else if (card.equals("12e") || card.equals("12p") || card.equals("12c") || card.equals("12o")) {
            cbrCode = 8;
        } else if (card.equals("11e") || card.equals("11p") || card.equals("11c") || card.equals("11o")) {
            cbrCode = 7;
        } else if (card.equals("10e") || card.equals("10p") || card.equals("10c") || card.equals("10o")) {
            cbrCode = 6;
        } else if (card.equals("7p") || card.equals("7c")) {
            cbrCode = 4;
        } else if (card.equals("6e") || card.equals("6p") || card.equals("6c") || card.equals("6o")) {
            cbrCode = 3;
        } else if (card.equals("5e") || card.equals("5p") || card.equals("5c") || card.equals("5o")) {
            cbrCode = 2;
        } else if (card.equals("4e") || card.equals("4p") || card.equals("4c") || card.equals("4o")) {
            cbrCode = 1;
        }

        return cbrCode;
    }
}
