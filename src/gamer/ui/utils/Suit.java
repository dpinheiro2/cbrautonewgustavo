package gamer.ui.utils;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 19/07/2018.
 */

public enum Suit {
    ESPADAS("e"), BASTOS("p"), COPAS("c"), OURO("o");

    private String value;

    Suit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
