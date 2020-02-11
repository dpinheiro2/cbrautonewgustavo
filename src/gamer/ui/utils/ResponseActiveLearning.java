package gamer.ui.utils;

import java.io.Serializable;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 23/07/2019.
 */


public class ResponseActiveLearning implements Serializable {

    private String action;
    private boolean hasBluff;
    private boolean isUtilCarta;
    private boolean isUtilEnvido;
    private boolean isUtilTruco;


    public ResponseActiveLearning() {
        action = null;
        hasBluff = false;
        isUtilCarta = false;
        isUtilEnvido = false;
        isUtilTruco = false;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isHasBluff() {
        return hasBluff;
    }

    public void setHasBluff(boolean hasBluff) {
        this.hasBluff = hasBluff;
    }

    public boolean isUtilCarta() {
        return isUtilCarta;
    }

    public void setUtilCarta(boolean utilCarta) {
        isUtilCarta = utilCarta;
    }

    public boolean isUtilEnvido() {
        return isUtilEnvido;
    }

    public void setUtilEnvido(boolean utilEnvido) {
        isUtilEnvido = utilEnvido;
    }

    public boolean isUtilTruco() {
        return isUtilTruco;
    }

    public void setUtilTruco(boolean utilTruco) {
        isUtilTruco = utilTruco;
    }

}
