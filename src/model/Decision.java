package model;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

import java.io.Serializable;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 14/03/2020.
 */


public class Decision implements CaseComponent, Serializable {

    private Integer idDecision;
    private Player player;
    private Match match;
    private Integer handNumber;

    private Integer agenteScore;
    private Integer oponenteScore;
    private double envidoHandStrength;
    private double probWin;
    private Integer stateDecision;
    private String lastMove;

    private Integer bluff1Success;
    private Integer bluff2Success;
    private Integer bluff3Success;
    private Integer bluff4Success;
    private Integer bluff5Success;
    private Integer bluff6Success;

    private Integer bluff1Failure;
    private Integer bluff2Failure;
    private Integer bluff3Failure;
    private Integer bluff4Failure;
    private Integer bluff5Failure;
    private Integer bluff6Failure;

    private Integer bluff1Opponent;
    private Integer bluff2Opponent;
    private Integer bluff3Opponent;
    private Integer bluff4Opponent;
    private Integer bluff5Opponent;
    private Integer bluff6Opponent;

    private Integer bluff1ShowDown;
    private Integer bluff2ShowDown;
    private Integer bluff3ShowDown;
    private Integer bluff4ShowDown;
    private Integer bluff5ShowDown;
    private Integer bluff6ShowDown;

    private Integer typeAction;
    private Integer typeDecision;
    private String decision;
    private Integer isBluff;
    private Integer typeBluff;

    private Integer indSuccess;
    private Integer possiblePoints;
    private Integer achievedPoints;

    private Integer isActiveLearning;
    private Integer bluffCanBeDetected;
    private Integer bluffDetectedOpponent;

    public Integer getIdDecision() {
        return idDecision;
    }

    public void setIdDecision(Integer idDecision) {
        this.idDecision = idDecision;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Integer getHandNumber() {
        return handNumber;
    }

    public void setHandNumber(Integer handNumber) {
        this.handNumber = handNumber;
    }

    public Integer getAgenteScore() {
        return agenteScore;
    }

    public void setAgenteScore(Integer agenteScore) {
        this.agenteScore = agenteScore;
    }

    public Integer getOponenteScore() {
        return oponenteScore;
    }

    public void setOponenteScore(Integer oponenteScore) {
        this.oponenteScore = oponenteScore;
    }

    public double getEnvidoHandStrength() {
        return envidoHandStrength;
    }

    public void setEnvidoHandStrength(double envidoHandStrength) {
        this.envidoHandStrength = envidoHandStrength;
    }

    public double getProbWin() {
        return probWin;
    }

    public void setProbWin(double probWin) {
        this.probWin = probWin;
    }

    public Integer getStateDecision() {
        return stateDecision;
    }

    public void setStateDecision(Integer stateDecision) {
        this.stateDecision = stateDecision;
    }

    public String getLastMove() {
        return lastMove;
    }

    public void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    public Integer getBluff1Success() {
        return bluff1Success;
    }

    public void setBluff1Success(Integer bluff1Success) {
        this.bluff1Success = bluff1Success;
    }

    public Integer getBluff2Success() {
        return bluff2Success;
    }

    public void setBluff2Success(Integer bluff2Success) {
        this.bluff2Success = bluff2Success;
    }

    public Integer getBluff3Success() {
        return bluff3Success;
    }

    public void setBluff3Success(Integer bluff3Success) {
        this.bluff3Success = bluff3Success;
    }

    public Integer getBluff4Success() {
        return bluff4Success;
    }

    public void setBluff4Success(Integer bluff4Success) {
        this.bluff4Success = bluff4Success;
    }

    public Integer getBluff5Success() {
        return bluff5Success;
    }

    public void setBluff5Success(Integer bluff5Success) {
        this.bluff5Success = bluff5Success;
    }

    public Integer getBluff6Success() {
        return bluff6Success;
    }

    public void setBluff6Success(Integer bluff6Success) {
        this.bluff6Success = bluff6Success;
    }

    public Integer getBluff1Failure() {
        return bluff1Failure;
    }

    public void setBluff1Failure(Integer bluff1Failure) {
        this.bluff1Failure = bluff1Failure;
    }

    public Integer getBluff2Failure() {
        return bluff2Failure;
    }

    public void setBluff2Failure(Integer bluff2Failure) {
        this.bluff2Failure = bluff2Failure;
    }

    public Integer getBluff3Failure() {
        return bluff3Failure;
    }

    public void setBluff3Failure(Integer bluff3Failure) {
        this.bluff3Failure = bluff3Failure;
    }

    public Integer getBluff4Failure() {
        return bluff4Failure;
    }

    public void setBluff4Failure(Integer bluff4Failure) {
        this.bluff4Failure = bluff4Failure;
    }

    public Integer getBluff5Failure() {
        return bluff5Failure;
    }

    public void setBluff5Failure(Integer bluff5Failure) {
        this.bluff5Failure = bluff5Failure;
    }

    public Integer getBluff6Failure() {
        return bluff6Failure;
    }

    public void setBluff6Failure(Integer bluff6Failure) {
        this.bluff6Failure = bluff6Failure;
    }

    public Integer getBluff1Opponent() {
        return bluff1Opponent;
    }

    public void setBluff1Opponent(Integer bluff1Opponent) {
        this.bluff1Opponent = bluff1Opponent;
    }

    public Integer getBluff2Opponent() {
        return bluff2Opponent;
    }

    public void setBluff2Opponent(Integer bluff2Opponent) {
        this.bluff2Opponent = bluff2Opponent;
    }

    public Integer getBluff3Opponent() {
        return bluff3Opponent;
    }

    public void setBluff3Opponent(Integer bluff3Opponent) {
        this.bluff3Opponent = bluff3Opponent;
    }

    public Integer getBluff4Opponent() {
        return bluff4Opponent;
    }

    public void setBluff4Opponent(Integer bluff4Opponent) {
        this.bluff4Opponent = bluff4Opponent;
    }

    public Integer getBluff5Opponent() {
        return bluff5Opponent;
    }

    public void setBluff5Opponent(Integer bluff5Opponent) {
        this.bluff5Opponent = bluff5Opponent;
    }

    public Integer getBluff6Opponent() {
        return bluff6Opponent;
    }

    public void setBluff6Opponent(Integer bluff6Opponent) {
        this.bluff6Opponent = bluff6Opponent;
    }

    public Integer getBluff1ShowDown() {
        return bluff1ShowDown;
    }

    public void setBluff1ShowDown(Integer bluff1ShowDown) {
        this.bluff1ShowDown = bluff1ShowDown;
    }

    public Integer getBluff2ShowDown() {
        return bluff2ShowDown;
    }

    public void setBluff2ShowDown(Integer bluff2ShowDown) {
        this.bluff2ShowDown = bluff2ShowDown;
    }

    public Integer getBluff3ShowDown() {
        return bluff3ShowDown;
    }

    public void setBluff3ShowDown(Integer bluff3ShowDown) {
        this.bluff3ShowDown = bluff3ShowDown;
    }

    public Integer getBluff4ShowDown() {
        return bluff4ShowDown;
    }

    public void setBluff4ShowDown(Integer bluff4ShowDown) {
        this.bluff4ShowDown = bluff4ShowDown;
    }

    public Integer getBluff5ShowDown() {
        return bluff5ShowDown;
    }

    public void setBluff5ShowDown(Integer bluff5ShowDown) {
        this.bluff5ShowDown = bluff5ShowDown;
    }

    public Integer getBluff6ShowDown() {
        return bluff6ShowDown;
    }

    public void setBluff6ShowDown(Integer bluff6ShowDown) {
        this.bluff6ShowDown = bluff6ShowDown;
    }

    public Integer getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(Integer typeAction) {
        this.typeAction = typeAction;
    }

    public Integer getTypeDecision() {
        return typeDecision;
    }

    public void setTypeDecision(Integer typeDecision) {
        this.typeDecision = typeDecision;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Integer getIsBluff() {
        return isBluff;
    }

    public void setIsBluff(Integer isBluff) {
        this.isBluff = isBluff;
    }

    public Integer getTypeBluff() {
        return typeBluff;
    }

    public void setTypeBluff(Integer typeBluff) {
        this.typeBluff = typeBluff;
    }

    public Integer getIndSuccess() {
        return indSuccess;
    }

    public void setIndSuccess(Integer indSuccess) {
        this.indSuccess = indSuccess;
    }

    public Integer getPossiblePoints() {
        return possiblePoints;
    }

    public void setPossiblePoints(Integer possiblePoints) {
        this.possiblePoints = possiblePoints;
    }

    public Integer getAchievedPoints() {
        return achievedPoints;
    }

    public void setAchievedPoints(Integer achievedPoints) {
        this.achievedPoints = achievedPoints;
    }

    public Integer getIsActiveLearning() {
        return isActiveLearning;
    }

    public void setIsActiveLearning(Integer isActiveLearning) {
        this.isActiveLearning = isActiveLearning;
    }

    public Integer getBluffCanBeDetected() {
        return bluffCanBeDetected;
    }

    public void setBluffCanBeDetected(Integer bluffCanBeDetected) {
        this.bluffCanBeDetected = bluffCanBeDetected;
    }

    public Integer getBluffDetectedOpponent() {
        return bluffDetectedOpponent;
    }

    public void setBluffDetectedOpponent(Integer bluffDetectedOpponent) {
        this.bluffDetectedOpponent = bluffDetectedOpponent;
    }

    @Override
    public Attribute getIdAttribute() {
        return new Attribute("idDecision", this.getClass());
    }

    @Override
    public String toString() {
        return "Decision{" +
                "idDecision=" + idDecision +
                ", player=" + player +
                ", match=" + match +
                ", handNumber=" + handNumber +
                ", agenteScore=" + agenteScore +
                ", oponenteScore=" + oponenteScore +
                ", envidoHandStrength=" + envidoHandStrength +
                ", probWin=" + probWin +
                ", stateDecision=" + stateDecision +
                ", lastMove='" + lastMove + '\'' +
                ", bluff1Success=" + bluff1Success +
                ", bluff2Success=" + bluff2Success +
                ", bluff3Success=" + bluff3Success +
                ", bluff4Success=" + bluff4Success +
                ", bluff5Success=" + bluff5Success +
                ", bluff6Success=" + bluff6Success +
                ", bluff1Failure=" + bluff1Failure +
                ", bluff2Failure=" + bluff2Failure +
                ", bluff3Failure=" + bluff3Failure +
                ", bluff4Failure=" + bluff4Failure +
                ", bluff5Failure=" + bluff5Failure +
                ", bluff6Failure=" + bluff6Failure +
                ", bluff1Opponent=" + bluff1Opponent +
                ", bluff2Opponent=" + bluff2Opponent +
                ", bluff3Opponent=" + bluff3Opponent +
                ", bluff4Opponent=" + bluff4Opponent +
                ", bluff5Opponent=" + bluff5Opponent +
                ", bluff6Opponent=" + bluff6Opponent +
                ", bluff1ShowDown=" + bluff1ShowDown +
                ", bluff2ShowDown=" + bluff2ShowDown +
                ", bluff3ShowDown=" + bluff3ShowDown +
                ", bluff4ShowDown=" + bluff4ShowDown +
                ", bluff5ShowDown=" + bluff5ShowDown +
                ", bluff6ShowDown=" + bluff6ShowDown +
                ", typeAction=" + typeAction +
                ", typeDecision=" + typeDecision +
                ", decision='" + decision + '\'' +
                ", isBluff=" + isBluff +
                ", typeBluff=" + typeBluff +
                ", indSuccess=" + indSuccess +
                ", possiblePoints=" + possiblePoints +
                ", achievedPoints=" + achievedPoints +
                ", isActiveLearning=" + isActiveLearning +
                ", bluffCanBeDetected=" + bluffCanBeDetected +
                ", bluffDetectedOpponent=" + bluffDetectedOpponent +
                '}';
    }
}
