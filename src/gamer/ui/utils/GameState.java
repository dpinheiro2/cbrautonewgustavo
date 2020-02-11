package gamer.ui.utils;

import cbr.AtualizaConsultas.AuxiliaConsultas.CartasModelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 06/08/2019.
 */


public class GameState implements Serializable {

    private int agentPoints;
    private int opponentPoints;
    private int envidoPoints;
    private int opponentEnvidoPoints;
    private String playedCards;
    private String opponentPlayedCards;
    private String handCards;
    private String move;
    private boolean isHand;
    private ArrayList<String> moves;
    private String historyEnvido;
    private String historyFlor;
    private String historyTruco;
    private String historyCard;
    private double prob;
    //moveType: 0 Flor/ 1 Envido/ 2 Truco/ 3 Play Card
    private int moveType;
    private int countBluff1Success = 0;
    private int countBluff2Success = 0;
    private int countBluff3Success = 0;
    private int countBluff4Success = 0;
    private int countBluff5Success = 0;
    private int countBluff6Success = 0;

    private int countBluff1Failure = 0;
    private int countBluff2Failure = 0;
    private int countBluff3Failure = 0;
    private int countBluff4Failure = 0;
    private int countBluff5Failure = 0;
    private int countBluff6Failure = 0;

    private int countBluff1Opponent = 0;
    private int countBluff2Opponent = 0;
    private int countBluff3Opponent = 0;
    private int countBluff4Opponent = 0;
    private int countBluff5Opponent = 0;
    private int countBluff6Opponent = 0;

    private int countBluff1ShowDown = 0;
    private int countBluff2ShowDown = 0;
    private int countBluff3ShowDown = 0;
    private int countBluff4ShowDown = 0;
    private int countBluff5ShowDown = 0;
    private int countBluff6ShowDown = 0;

    private LinkedList<Action> tableCards;
    private List<CartasModelo> handImageCards;


    public GameState() {
        prob = 0.0;
        agentPoints = 0;
        opponentPoints = 0;
        envidoPoints = 0;
        opponentEnvidoPoints = 0;
        playedCards = "";
        opponentPlayedCards = "";
        handCards = "";
        move = "";
        isHand = false;
        moves = new ArrayList<>();
        moveType = 0;
        countBluff1Success = 0;
        countBluff2Success = 0;
        countBluff3Success = 0;
        countBluff4Success = 0;
        countBluff5Success = 0;

        countBluff6Success = 0;
        countBluff1Failure = 0;
        countBluff2Failure = 0;
        countBluff3Failure = 0;
        countBluff4Failure = 0;
        countBluff5Failure = 0;
        countBluff6Failure = 0;

        countBluff1Opponent = 0;
        countBluff2Opponent = 0;
        countBluff3Opponent = 0;
        countBluff4Opponent = 0;
        countBluff5Opponent = 0;
        countBluff6Opponent = 0;

        countBluff1ShowDown = 0;
        countBluff2ShowDown = 0;
        countBluff3ShowDown = 0;
        countBluff4ShowDown = 0;
        countBluff5ShowDown = 0;
        countBluff6ShowDown = 0;

        tableCards = new LinkedList<>();
        handImageCards = new ArrayList<>();

    }

    public int getAgentPoints() {
        return agentPoints;
    }

    public void setAgentPoints(int agentPoints) {
        this.agentPoints = agentPoints;
    }

    public int getOpponentPoints() {
        return opponentPoints;
    }

    public void setOpponentPoints(int opponentPoints) {
        this.opponentPoints = opponentPoints;
    }

    public int getEnvidoPoints() {
        return envidoPoints;
    }

    public void setEnvidoPoints(int envidoPoints) {
        this.envidoPoints = envidoPoints;
    }

    public int getOpponentEnvidoPoints() {
        return opponentEnvidoPoints;
    }

    public void setOpponentEnvidoPoints(int opponentEnvidoPoints) {
        this.opponentEnvidoPoints = opponentEnvidoPoints;
    }

    public String getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(String playedCards) {
        this.playedCards = playedCards;
    }

    public String getOpponentPlayedCards() {
        return opponentPlayedCards;
    }

    public void setOpponentPlayedCards(String opponentPlayedCards) {
        this.opponentPlayedCards = opponentPlayedCards;
    }

    public String getHandCards() {
        return handCards;
    }

    public void setHandCards(String handCards) {
        this.handCards = handCards;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public boolean isHand() {
        return isHand;
    }

    public void setHand(boolean hand) {
        isHand = hand;
    }

    public ArrayList<String> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<String> moves) {
        this.moves = moves;
    }

    public String getHistoryEnvido() {
        return historyEnvido;
    }

    public void setHistoryEnvido(String historyEnvido) {
        this.historyEnvido = historyEnvido;
    }

    public String getHistoryFlor() {
        return historyFlor;
    }

    public void setHistoryFlor(String historyFlor) {
        this.historyFlor = historyFlor;
    }

    public String getHistoryTruco() {
        return historyTruco;
    }

    public void setHistoryTruco(String historyTruco) {
        this.historyTruco = historyTruco;
    }

    public String getHistoryCard() {
        return historyCard;
    }

    public void setHistoryCard(String historyCard) {
        this.historyCard = historyCard;
    }

    public int getMoveType() {
        return moveType;
    }

    public void setMoveType(int moveType) {
        this.moveType = moveType;
    }

    public int getCountBluff1Success() {
        return countBluff1Success;
    }

    public void setCountBluff1Success(int countBluff1Success) {
        this.countBluff1Success = countBluff1Success;
    }

    public int getCountBluff2Success() {
        return countBluff2Success;
    }

    public void setCountBluff2Success(int countBluff2Success) {
        this.countBluff2Success = countBluff2Success;
    }

    public int getCountBluff3Success() {
        return countBluff3Success;
    }

    public void setCountBluff3Success(int countBluff3Success) {
        this.countBluff3Success = countBluff3Success;
    }

    public int getCountBluff4Success() {
        return countBluff4Success;
    }

    public void setCountBluff4Success(int countBluff4Success) {
        this.countBluff4Success = countBluff4Success;
    }

    public int getCountBluff5Success() {
        return countBluff5Success;
    }

    public void setCountBluff5Success(int countBluff5Success) {
        this.countBluff5Success = countBluff5Success;
    }

    public int getCountBluff6Success() {
        return countBluff6Success;
    }

    public void setCountBluff6Success(int countBluff6Success) {
        this.countBluff6Success = countBluff6Success;
    }

    public int getCountBluff1Failure() {
        return countBluff1Failure;
    }

    public void setCountBluff1Failure(int countBluff1Failure) {
        this.countBluff1Failure = countBluff1Failure;
    }

    public int getCountBluff2Failure() {
        return countBluff2Failure;
    }

    public void setCountBluff2Failure(int countBluff2Failure) {
        this.countBluff2Failure = countBluff2Failure;
    }

    public int getCountBluff3Failure() {
        return countBluff3Failure;
    }

    public void setCountBluff3Failure(int countBluff3Failure) {
        this.countBluff3Failure = countBluff3Failure;
    }

    public int getCountBluff4Failure() {
        return countBluff4Failure;
    }

    public void setCountBluff4Failure(int countBluff4Failure) {
        this.countBluff4Failure = countBluff4Failure;
    }

    public int getCountBluff5Failure() {
        return countBluff5Failure;
    }

    public void setCountBluff5Failure(int countBluff5Failure) {
        this.countBluff5Failure = countBluff5Failure;
    }

    public int getCountBluff6Failure() {
        return countBluff6Failure;
    }

    public void setCountBluff6Failure(int countBluff6Failure) {
        this.countBluff6Failure = countBluff6Failure;
    }

    public int getCountBluff1Opponent() {
        return countBluff1Opponent;
    }

    public void setCountBluff1Opponent(int countBluff1Opponent) {
        this.countBluff1Opponent = countBluff1Opponent;
    }

    public int getCountBluff2Opponent() {
        return countBluff2Opponent;
    }

    public void setCountBluff2Opponent(int countBluff2Opponent) {
        this.countBluff2Opponent = countBluff2Opponent;
    }

    public int getCountBluff3Opponent() {
        return countBluff3Opponent;
    }

    public void setCountBluff3Opponent(int countBluff3Opponent) {
        this.countBluff3Opponent = countBluff3Opponent;
    }

    public int getCountBluff4Opponent() {
        return countBluff4Opponent;
    }

    public void setCountBluff4Opponent(int countBluff4Opponent) {
        this.countBluff4Opponent = countBluff4Opponent;
    }

    public int getCountBluff5Opponent() {
        return countBluff5Opponent;
    }

    public void setCountBluff5Opponent(int countBluff5Opponent) {
        this.countBluff5Opponent = countBluff5Opponent;
    }

    public int getCountBluff6Opponent() {
        return countBluff6Opponent;
    }

    public void setCountBluff6Opponent(int countBluff6Opponent) {
        this.countBluff6Opponent = countBluff6Opponent;
    }

    public int getCountBluff1ShowDown() {
        return countBluff1ShowDown;
    }

    public void setCountBluff1ShowDown(int countBluff1ShowDown) {
        this.countBluff1ShowDown = countBluff1ShowDown;
    }

    public int getCountBluff2ShowDown() {
        return countBluff2ShowDown;
    }

    public void setCountBluff2ShowDown(int countBluff2ShowDown) {
        this.countBluff2ShowDown = countBluff2ShowDown;
    }

    public int getCountBluff3ShowDown() {
        return countBluff3ShowDown;
    }

    public void setCountBluff3ShowDown(int countBluff3ShowDown) {
        this.countBluff3ShowDown = countBluff3ShowDown;
    }

    public int getCountBluff4ShowDown() {
        return countBluff4ShowDown;
    }

    public void setCountBluff4ShowDown(int countBluff4ShowDown) {
        this.countBluff4ShowDown = countBluff4ShowDown;
    }

    public int getCountBluff5ShowDown() {
        return countBluff5ShowDown;
    }

    public void setCountBluff5ShowDown(int countBluff5ShowDown) {
        this.countBluff5ShowDown = countBluff5ShowDown;
    }

    public int getCountBluff6ShowDown() {
        return countBluff6ShowDown;
    }

    public void setCountBluff6ShowDown(int countBluff6ShowDown) {
        this.countBluff6ShowDown = countBluff6ShowDown;
    }

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }

    public LinkedList<Action> getTableCards() {
        return tableCards;
    }

    public void setTableCards(LinkedList<Action> tableCards) {
        this.tableCards = tableCards;
    }

    public List<CartasModelo> getHandImageCards() {
        return handImageCards;
    }

    public void setHandImageCards(List<CartasModelo> handImageCards) {
        this.handImageCards = handImageCards;
    }
}
