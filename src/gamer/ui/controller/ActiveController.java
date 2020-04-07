package gamer.ui.controller;


import cbr.AtualizaConsultas.AuxiliaConsultas.CartasModelo;
import com.sun.javafx.charts.Legend;
import gamer.Auto.ControlaRodadaAuto;
import utils.Action;
import utils.GameState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 15/07/2019.
 */


public class ActiveController {

    private static final int GRAFICO_ALTURA = 160;
    private static final int GRAFICO_LARGURA = 340;

    @FXML private Label lblAgent;
    @FXML private Label lblOpponent;
    @FXML private Label lblAgentPoints;
    @FXML private Label lblOpponentPoints;
    @FXML private Label lblAgentEnvidoPoints;
    @FXML private Label lblOpponentEnvidoPoints;
    @FXML private Label lblEnvidoHistory;
    @FXML private Label lblTrucoHistory;
    @FXML private AnchorPane panelGrafico;
    @FXML private BarChart barChartGrafico;
    @FXML private CategoryAxis categoryAxis;
    @FXML private NumberAxis numberAxis;
    @FXML private Label lblProbGanhar;
    @FXML private HBox pnlRetievedMove;
    @FXML private Label lblRetrievedMove;
    @FXML private HBox pnlNewMove;
    @FXML private ComboBox<String> comboBoxNewMove;
    @FXML private CheckBox cbConfirmMove;
    @FXML private Button btnCancel;
    @FXML private Button btnSave;
    @FXML private ImageView imgCardAgent1;
    @FXML private ImageView imgCardAgent2;
    @FXML private ImageView imgCardAgent3;
    @FXML private ImageView imgCardOponent1;
    @FXML private ImageView imgCardOponent2;
    @FXML private ImageView imgCardOponent3;
    @FXML private Label lblAgentLabelRound1;
    @FXML private Label lblAgentLabelRound2;
    @FXML private Label lblAgentLabelRound3;
    @FXML private Label lblOpponentLabelRound1;
    @FXML private Label lblOpponentLabelRound2;
    @FXML private Label lblOpponentLabelRound3;
    @FXML private ImageView imageViewHandCard1;
    @FXML private ImageView imageViewHandCard2;
    @FXML private ImageView imageViewHandCard3;

    final String bluff1 = "Bet/Raise Envido";
    final String bluff2 = "Slow Play Envido";
    final String bluff3 = "Fishing Envido";
    final String bluff4 = "Bet/Raise Truco";
    final String bluff5 = "Slow Play Truco";
    final String bluff6 = "Fishing Truco";

    private List<String> moves;

    private GameState gameState;
    private String responseActiveLearning;
    private ControlaRodadaAuto controlaRodadaAuto;

    private ObservableList<String> observableListBlefes = FXCollections.observableArrayList();

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void initData(GameState gameState, ControlaRodadaAuto controlaRodadaAuto) {
        this.gameState = gameState;
        this.controlaRodadaAuto = controlaRodadaAuto;
        //System.out.println(gameState.getMove());
        setMoves();
        setInfoGameState();
        setInfoRetrievedMove(gameState.getMove());
        setInfoHistoryHand();
        carregaCartas();
        carregaHandCards();
        //setPreviousBluffMoves();
        //resetCheckBoxes();


        //System.out.println("tela active");

       // numberAxis.setTickUnit(1);

        XYChart.Series<String, Integer> success = new XYChart.Series();
        success.setName("Success");

        success.getData().add(new XYChart.Data<>(bluff1, gameState.getCountBluff1Success()));
        success.getData().add(new XYChart.Data<>(bluff2, gameState.getCountBluff2Success()));
        success.getData().add(new XYChart.Data<>(bluff3, gameState.getCountBluff3Success()));
        success.getData().add(new XYChart.Data<>(bluff4, gameState.getCountBluff4Success()));
        success.getData().add(new XYChart.Data<>(bluff5, gameState.getCountBluff5Success()));
        success.getData().add(new XYChart.Data<>(bluff6, gameState.getCountBluff6Success()));

        XYChart.Series<String, Integer> failure = new XYChart.Series();
        failure.setName("Failure");

        failure.getData().add(new XYChart.Data<>(bluff1, gameState.getCountBluff1Failure()));
        failure.getData().add(new XYChart.Data<>(bluff2, gameState.getCountBluff2Failure()));
        failure.getData().add(new XYChart.Data<>(bluff3, gameState.getCountBluff3Failure()));
        failure.getData().add(new XYChart.Data<>(bluff4, gameState.getCountBluff4Failure()));
        failure.getData().add(new XYChart.Data<>(bluff5, gameState.getCountBluff5Failure()));
        failure.getData().add(new XYChart.Data<>(bluff6, gameState.getCountBluff6Failure()));

        XYChart.Series<String, Integer> showDown = new XYChart.Series();
        showDown.setName("Show Down");

        showDown.getData().add(new XYChart.Data<>(bluff1, gameState.getCountBluff1ShowDown()));
        showDown.getData().add(new XYChart.Data<>(bluff2, gameState.getCountBluff2ShowDown()));
        showDown.getData().add(new XYChart.Data<>(bluff3, gameState.getCountBluff3ShowDown()));
        showDown.getData().add(new XYChart.Data<>(bluff4, gameState.getCountBluff4ShowDown()));
        showDown.getData().add(new XYChart.Data<>(bluff5, gameState.getCountBluff5ShowDown()));
        showDown.getData().add(new XYChart.Data<>(bluff6, gameState.getCountBluff6ShowDown()));

        XYChart.Series<String, Integer> opponent = new XYChart.Series();
        opponent.setName("Opponent");


        opponent.getData().add(new XYChart.Data<>(bluff1, gameState.getCountBluff1Opponent()));
        opponent.getData().add(new XYChart.Data<>(bluff2,  gameState.getCountBluff2Opponent()));
        opponent.getData().add(new XYChart.Data<>(bluff3,  gameState.getCountBluff3Opponent()));
        opponent.getData().add(new XYChart.Data<>(bluff4,  gameState.getCountBluff4Opponent()));
        opponent.getData().add(new XYChart.Data<>(bluff5,  gameState.getCountBluff5Opponent()));
        opponent.getData().add(new XYChart.Data<>(bluff6,  gameState.getCountBluff6Opponent()));


        barChartGrafico.getData().addAll(success, failure, showDown, opponent);

        for(Node n : barChartGrafico.getChildrenUnmodifiable()){
            if(n instanceof Legend){
                for(Legend.LegendItem legendItem : ((Legend)n).getItems()){

                    if (legendItem.getText().equals("Success")) {
                        legendItem.getSymbol().setStyle("-fx-background-color: mediumseagreen;");
                    } else  if (legendItem.getText().equals("Failure")) {
                        legendItem.getSymbol().setStyle("-fx-background-color: orangered;");
                    } else  if (legendItem.getText().equals("Show Down")) {
                        legendItem.getSymbol().setStyle("-fx-background-color: cornflowerblue;");
                    } else  if (legendItem.getText().equals("Opponent")) {
                        legendItem.getSymbol().setStyle("-fx-background-color: goldenrod;");
                    }


                    //legendItem.getSymbol().setStyle("-fx-background-color: #0000ff, white;");
                }
            }
        }

        for (Node node : barChartGrafico.lookupAll(".chart-legend-item-symbol")) {
            System.out.println(node.toString());
            /*if (node instanceof Label) {
                System.out.println("Label instance");
                *//*((Label) node).setWrapText(true);
                ((Label) node).setManaged(true);
                ((Label) node).setPrefWidth(380);*//*
            }*/
        }


        for(Node n : barChartGrafico.lookupAll(".default-color0.chart-bar")) {
            n.setStyle("-fx-bar-fill: mediumseagreen;");
        }

        for(Node n : barChartGrafico.lookupAll(".default-color1.chart-bar")) {
            n.setStyle("-fx-bar-fill: orangered;");
        }

        for(Node n : barChartGrafico.lookupAll(".default-color2.chart-bar")) {
            n.setStyle("-fx-bar-fill: cornflowerblue;");
        }

        for(Node n : barChartGrafico.lookupAll(".default-color3.chart-bar")) {
            n.setStyle("-fx-bar-fill: goldenrod;");
        }

        barChartGrafico.setLegendVisible(true);
    }

    @FXML
    public void initialize() {

        responseActiveLearning = null;
        moves = new ArrayList<>();

        cbConfirmMove.setSelected(true);
        pnlNewMove.setVisible(!cbConfirmMove.isSelected());
        pnlRetievedMove.setVisible(cbConfirmMove.isSelected());

        cbConfirmMove.setOnAction((event) -> {
            boolean selected = cbConfirmMove.isSelected();
            pnlRetievedMove.setVisible(selected);
            pnlNewMove.setVisible(!selected);
        });

        btnCancel.setOnAction((event) -> {
            String log = "Active Learning Canceled\n\n";
            responseActiveLearning = null;
            controlaRodadaAuto.setResponseActiveLearning(responseActiveLearning);
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        });

        btnSave.setOnAction((event) -> {
            controlaRodadaAuto.setCompulsoryRetention(true);
            responseActiveLearning = getSelectedAction();
            controlaRodadaAuto.setResponseActiveLearning(responseActiveLearning);
            switch (gameState.getMoveType()) {
                case 1:
                    controlaRodadaAuto.setUtilEnvido(true);
                    break;
                case 2:
                    controlaRodadaAuto.setUtilTruco(true);
                    break;
                case 3:
                    controlaRodadaAuto.setUtilCarta(true);
                    break;
                case 11:
                    controlaRodadaAuto.setUtilEnvido(true);
                    break;
                case 22:
                    controlaRodadaAuto.setUtilTruco(true);
                    break;
            }

            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        });

        String[] arrayBlefes = {bluff1, bluff2, bluff3, bluff4, bluff5, bluff6};
        observableListBlefes.addAll(arrayBlefes);
        categoryAxis.setCategories(observableListBlefes);

        imgCardAgent1.setVisible(false);
        imgCardAgent1.setImage(null);
        imgCardAgent2.setVisible(false);
        imgCardAgent2.setImage(null);
        imgCardAgent3.setVisible(false);
        imgCardAgent3.setImage(null);

        imgCardOponent1.setVisible(false);
        imgCardOponent1.setImage(null);
        imgCardOponent2.setVisible(false);
        imgCardOponent2.setImage(null);
        imgCardOponent3.setVisible(false);
        imgCardOponent3.setImage(null);

        lblAgentLabelRound1.setVisible(false);
        lblAgentLabelRound2.setVisible(false);
        lblAgentLabelRound3.setVisible(false);
        lblOpponentLabelRound1.setVisible(false);
        lblOpponentLabelRound2.setVisible(false);
        lblOpponentLabelRound3.setVisible(false);

        imageViewHandCard1.setVisible(false);
        imageViewHandCard1.setImage(null);

        imageViewHandCard2.setVisible(false);
        imageViewHandCard2.setImage(null);

        imageViewHandCard2.setVisible(false);
        imageViewHandCard2.setImage(null);

    }


    public void setMoves() {
        moves = gameState.getMoves();
        comboBoxNewMove.setItems(FXCollections.observableArrayList(moves));
    }

    public void setInfoGameState() {
        lblAgentPoints.setText(String.valueOf(gameState.getAgentPoints()));
        lblOpponentPoints.setText(String.valueOf(gameState.getOpponentPoints()));
        lblAgent.setUnderline(gameState.isHand());
        lblOpponent.setUnderline(!gameState.isHand());
        lblAgentEnvidoPoints.setText(String.valueOf(gameState.getEnvidoPoints()));
        if (gameState.getOpponentEnvidoPoints() == -1) {
            lblOpponentEnvidoPoints.setText("No Show");
        } else {
            lblOpponentEnvidoPoints.setText(String.valueOf(gameState.getOpponentEnvidoPoints()));
        }

    }

    public void setInfoRetrievedMove(String retrievedMove) {
        lblRetrievedMove.setText(retrievedMove);
        lblProbGanhar.setText("Prob Ganhar: " + gameState.getProb());
    }


    public void carregaHandCards() {
        int countCards = 0;
        if (gameState.getHandImageCards().size() > 0) {
            for (CartasModelo carta : gameState.getHandImageCards()) {
                String path = "/cartas/" + carta.getCarta() + ".png";
                Image image = new Image(getClass().getResourceAsStream(path));
                countCards++;
                switch (countCards) {
                    case 1:
                        imageViewHandCard1.setImage(image);
                        imageViewHandCard1.setVisible(true);
                        break;
                    case 2:
                        imageViewHandCard2.setImage(image);
                        imageViewHandCard2.setVisible(true);
                        break;
                    case 3:
                        imageViewHandCard3.setImage(image);
                        imageViewHandCard3.setVisible(true);
                        break;
                }

            }
        }
    }

        public void carregaCartas() {

            int countCards = 0;

            if (gameState.getTableCards().size() > 0) {
                for (Action action : gameState.getTableCards()) {
                    String path = "/cartas/" + action.getAction() + ".png";
                    Image image = new Image(getClass().getResourceAsStream(path));
                    countCards++;
                    switch (countCards) {
                        case 1:
                            if (action.getPlayer().equals("1")) {
                                imgCardAgent1.setImage(image);
                                imgCardAgent1.setVisible(true);
                                imgCardAgent1.toFront();
                                lblAgentLabelRound1.setVisible(true);
                            } else {
                                imgCardOponent1.setImage(image);
                                imgCardOponent1.setVisible(true);
                                imgCardOponent1.toFront();
                                lblOpponentLabelRound1.setVisible(true);
                            }
                            break;
                        case 2:
                            if (action.getPlayer().equals("1")) {
                                imgCardAgent1.setImage(image);
                                imgCardAgent1.setVisible(true);
                                imgCardAgent1.toFront();
                                lblAgentLabelRound1.setVisible(true);
                            } else {
                                imgCardOponent1.setImage(image);
                                imgCardOponent1.setVisible(true);
                                imgCardOponent1.toFront();
                                lblOpponentLabelRound1.setVisible(true);
                            }
                            break;
                        case 3:
                            if (action.getPlayer().equals("1")) {
                                imgCardAgent2.setImage(image);
                                imgCardAgent2.setVisible(true);
                                imgCardAgent2.toFront();
                                lblAgentLabelRound2.setVisible(true);
                            } else {
                                imgCardOponent2.setImage(image);
                                imgCardOponent2.setVisible(true);
                                imgCardOponent2.toFront();
                                lblOpponentLabelRound2.setVisible(true);
                            }
                            break;
                        case 4:
                            if (action.getPlayer().equals("1")) {
                                imgCardAgent2.setImage(image);
                                imgCardAgent2.setVisible(true);
                                imgCardAgent2.toFront();
                                lblAgentLabelRound2.setVisible(true);
                            } else {
                                imgCardOponent2.setImage(image);
                                imgCardOponent2.setVisible(true);
                                imgCardOponent2.toFront();
                                lblOpponentLabelRound2.setVisible(true);
                            }
                            break;
                        case 5 :
                            if (action.getPlayer().equals("1")) {
                                imgCardAgent3.setImage(image);
                                imgCardAgent3.setVisible(true);
                                imgCardAgent3.toFront();
                                lblAgentLabelRound3.setVisible(true);
                            } else {
                                imgCardOponent3.setImage(image);
                                imgCardOponent3.setVisible(true);
                                imgCardOponent3.toFront();
                                lblOpponentLabelRound3.setVisible(true);
                            }
                            break;
                        case 6:
                            if (action.getPlayer().equals("1")) {
                                imgCardAgent3.setImage(image);
                                imgCardAgent3.setVisible(true);
                                imgCardAgent3.toFront();
                                lblAgentLabelRound3.setVisible(true);
                            } else {
                                imgCardOponent3.setImage(image);
                                imgCardOponent3.setVisible(true);
                                imgCardOponent3.toFront();
                                lblOpponentLabelRound3.setVisible(true);
                            }
                            break;
                    }
                }
            }


        }

        public void setInfoHistoryHand() {

            //lblCardHistory.setText(gameState.getHistoryCard());
            lblEnvidoHistory.setText(gameState.getHistoryEnvido());
            lblTrucoHistory.setText(gameState.getHistoryTruco());
        }

        public String getSelectedAction() {

            String move = null;

            if (cbConfirmMove.isSelected()) {
                if (!lblRetrievedMove.getText().startsWith("No Call")) {
                    move = lblRetrievedMove.getText();
                } else {
                    move = "";
                }

            } else {
                if (comboBoxNewMove.getSelectionModel().getSelectedItem() != null) {
                    if (comboBoxNewMove.getSelectionModel().getSelectedItem().equals("No Call")) {
                        move = "";
                    } else {
                        move = comboBoxNewMove.getSelectionModel().getSelectedItem();
                    }

                }
            }

            return move;
        }


    }
