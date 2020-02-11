package gamer.ui.utils;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 06/06/2019.
 */


public class Action {

    private String action;
    private String player;
    private int when;

    public Action(String action, String player) {
        this.action = action;
        this.player = player;
        this.when = 0;
    }

    public Action(String action, String player, int when) {
        this.action = action;
        this.player = player;
        this.when = when;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getWhen() {
        return when;
    }

    public void setWhen(int when) {
        this.when = when;
    }
}
