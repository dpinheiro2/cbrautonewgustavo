package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 16/10/2019.
 */


public class Match implements Serializable {


    private Integer idMatch;
    private Player player1;
    private Player player2;
    private Integer pointsPlayer1;
    private Integer pointsPlayer2;
    private Player winner;
    private Set<Decision> decisions = new HashSet<Decision>(0);

    public Integer getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Integer idMatch) {
        this.idMatch = idMatch;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Integer getPointsPlayer1() {
        return pointsPlayer1;
    }

    public void setPointsPlayer1(Integer pointsPlayer1) {
        this.pointsPlayer1 = pointsPlayer1;
    }

    public Integer getPointsPlayer2() {
        return pointsPlayer2;
    }

    public void setPointsPlayer2(Integer pointsPlayer2) {
        this.pointsPlayer2 = pointsPlayer2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Set<Decision> getDecisions() {
        return decisions;
    }

    public void setDecisions(Set<Decision> decisions) {
        this.decisions = decisions;
    }
}
