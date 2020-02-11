package model;



import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 16/10/2019.
 */


public class Player implements Serializable {

    private Integer idPlayer;
    private String extraClusterReuse;
    private String intraClusterReuse;
    private Integer indCluster;
    private String caseBase;

    private Collection<Match> player1 = new LinkedList<Match>();

    private Collection<Match> player2 = new LinkedList<Match>();

    private Collection<Match> winner = new LinkedList<Match>();

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getExtraClusterReuse() {
        return extraClusterReuse;
    }

    public void setExtraClusterReuse(String extraClusterReuse) {
        this.extraClusterReuse = extraClusterReuse;
    }

    public String getIntraClusterReuse() {
        return intraClusterReuse;
    }

    public void setIntraClusterReuse(String intraClusterReuse) {
        this.intraClusterReuse = intraClusterReuse;
    }

    public Integer getIndCluster() {
        return indCluster;
    }

    public void setIndCluster(Integer indCluster) {
        this.indCluster = indCluster;
    }

    public String getCaseBase() {
        return caseBase;
    }

    public void setCaseBase(String caseBase) {
        this.caseBase = caseBase;
    }

    public Collection<Match> getPlayer1() {
        return player1;
    }

    public void setPlayer1(Collection<Match> player1) {
        this.player1 = player1;
    }

    public Collection<Match> getPlayer2() {
        return player2;
    }

    public void setPlayer2(Collection<Match> player2) {
        this.player2 = player2;
    }

    public Collection<Match> getWinner() {
        return winner;
    }

    public void setWinner(Collection<Match> winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Player{" +
                "idPlayer=" + idPlayer +
                ", extraClusterReuse='" + extraClusterReuse + '\'' +
                ", intraClusterReuse='" + intraClusterReuse + '\'' +
                ", indCluster=" + indCluster +
                ", caseBase='" + caseBase + '\'' +
                '}';
    }
}
