package gamer.ui.controller;


import cbr.AtualizaConsultas.AuxiliaConsultas.CartasModelo;
import cbr.cbrDescriptions.TrucoDescription;
import com.sun.javafx.charts.Legend;
import gamer.Auto.ControlaRodadaAuto;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
    @FXML private Label lblSimilarity;
    @FXML private Label lblCaseId;
    @FXML private Button btnDetails;
    @FXML private CheckBox cbIsOpportunity;
    @FXML private CheckBox cbIsBluff;

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
    private List<CartasModelo> cartas;
    private boolean isDeceptionOpportunity;
    private boolean reponseIsBluff;

    private ObservableList<String> observableListBlefes = FXCollections.observableArrayList();

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isDeceptionOpportunity() {
        return isDeceptionOpportunity;
    }

    public void setDeceptionOpportunity(boolean deceptionOpportunity) {
        isDeceptionOpportunity = deceptionOpportunity;
    }

    public void initData(GameState gameState, ControlaRodadaAuto controlaRodadaAuto) {

        this.gameState = gameState;
        this.controlaRodadaAuto = controlaRodadaAuto;
        this.cartas = getCartas();
        this.isDeceptionOpportunity = (Double.valueOf(gameState.getProb()) < 0.5 || Double.valueOf(gameState.getProb()) > 0.85);
        //System.out.println(gameState.getMove());
        setMoves();
        setInfoGameState();
        setInfoRetrievedMove(gameState.getMove());
        setInfoMostSimilarCase();
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
        reponseIsBluff = false;
        moves = new ArrayList<>();

        cbConfirmMove.setSelected(true);
        pnlNewMove.setVisible(!cbConfirmMove.isSelected());
        pnlRetievedMove.setVisible(cbConfirmMove.isSelected());

        /*cbIsBluff.setSelected(false);
        cbIsBluff.setDisable(true);*/
        cbIsBluff.setVisible(false);
        cbIsOpportunity.setVisible(false);

        /*cbIsOpportunity.setOnAction((event) -> {
            boolean selected = cbIsOpportunity.isSelected();
            cbIsBluff.setDisable(!selected);
        });*/

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
            controlaRodadaAuto.setResponseIsBluff(cbIsBluff.isSelected());
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

        btnDetails.setOnAction((event) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Most Similar Case Details");
            alert.getDialogPane().setHeader(null);
            alert.getDialogPane().setGraphic(null);
            alert.getDialogPane().setContent(MountGridPane(gameState));

            alert.showAndWait();
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

    public GridPane MountGridPane(GameState gameState) {

        GridPane gridpane = new GridPane();
        gridpane.setHgap(10);

        Label lblSimilaridade = new Label("Similaridade:");
        GridPane.setHalignment(lblSimilaridade, HPos.RIGHT);
        gridpane.add(lblSimilaridade, 0, 0);

        Label lblCaseId = new Label("Case Id:");
        GridPane.setHalignment(lblCaseId, HPos.RIGHT);
        gridpane.add(lblCaseId, 0, 1);

        Label lbljogadorMao = new Label("Jogador Mão:");
        GridPane.setHalignment(lbljogadorMao, HPos.RIGHT);
        gridpane.add(lbljogadorMao, 0, 2);

        Label lblcartaAltaAgente = new Label("Carta Alta Agente:");
        GridPane.setHalignment(lblcartaAltaAgente, HPos.RIGHT);
        gridpane.add(lblcartaAltaAgente, 0, 3);

        Label lblcartaMediaAgente = new Label("Carta Media Agente:");
        GridPane.setHalignment(lblcartaMediaAgente, HPos.RIGHT);
        gridpane.add(lblcartaMediaAgente, 0, 4);

        Label lblcartaBaixaAgente = new Label("Carta Baixa Agente:");
        GridPane.setHalignment(lblcartaBaixaAgente, HPos.RIGHT);
        gridpane.add(lblcartaBaixaAgente, 0, 5);

        Label lblPontosEnvidoAgente = new Label("Pontos Envido Agente:");
        GridPane.setHalignment(lblPontosEnvidoAgente, HPos.RIGHT);
        gridpane.add(lblPontosEnvidoAgente, 0, 6);

        Label lblPontuacaoAgente = new Label("Pontos Anteriores/Posteriores Agente:");
        GridPane.setHalignment(lblPontuacaoAgente, HPos.RIGHT);
        gridpane.add(lblPontuacaoAgente, 0, 7);

        Label lblPontuacaoOponente = new Label("Pontos Anteriores/Posteriores Oponente:");
        GridPane.setHalignment(lblPontuacaoOponente, HPos.RIGHT);
        gridpane.add(lblPontuacaoOponente, 0, 8);

        Label lblCartasJogadasAgente = new Label("Cartas Jogadas Agente:");
        GridPane.setHalignment(lblCartasJogadasAgente, HPos.RIGHT);
        gridpane.add(lblCartasJogadasAgente, 0, 9);

        Label lblCartasJogadasOponente = new Label("Cartas Jogadas Oponente:");
        GridPane.setHalignment(lblCartasJogadasOponente, HPos.RIGHT);
        gridpane.add(lblCartasJogadasOponente, 0, 10);

        Label lblGanhadoresRodadas = new Label("Ganhadores Rodadas:");
        GridPane.setHalignment(lblGanhadoresRodadas, HPos.RIGHT);
        gridpane.add(lblGanhadoresRodadas, 0, 11);

        Label lblEnvido = new Label("Quem Envido:");
        GridPane.setHalignment(lblEnvido, HPos.RIGHT);
        gridpane.add(lblEnvido, 0, 12);

        Label lblRealEnvido = new Label("Quem Real Envido:");
        GridPane.setHalignment(lblRealEnvido, HPos.RIGHT);
        gridpane.add(lblRealEnvido, 0, 13);

        Label lblFaltaEnvido = new Label("Quem Falta Envido:");
        GridPane.setHalignment(lblFaltaEnvido, HPos.RIGHT);
        gridpane.add(lblFaltaEnvido, 0, 14);

        Label lblNegouEnvido = new Label("Quem Negou Envido:");
        GridPane.setHalignment(lblNegouEnvido, HPos.RIGHT);
        gridpane.add(lblNegouEnvido, 0, 15);

        Label lblTruco = new Label("Quem Truco:");
        GridPane.setHalignment(lblTruco, HPos.RIGHT);
        gridpane.add(lblTruco, 0, 16);

        Label lblRetruco = new Label("Quem Retruco:");
        GridPane.setHalignment(lblRetruco, HPos.RIGHT);
        gridpane.add(lblRetruco, 0, 17);

        Label lblVale4 = new Label("Quem Vale 4:");
        GridPane.setHalignment(lblVale4, HPos.RIGHT);
        gridpane.add(lblVale4, 0, 18);

        Label lblNegouTruco = new Label("Quem Negou Truco:");
        GridPane.setHalignment(lblNegouTruco, HPos.RIGHT);
        gridpane.add(lblNegouTruco, 0, 19);

        Label lblTentosEnvido = new Label("Tentos Envido:");
        GridPane.setHalignment(lblTentosEnvido, HPos.RIGHT);
        gridpane.add(lblTentosEnvido, 0, 20);

        Label lblTentosTruco = new Label("Tentos Truco:");
        GridPane.setHalignment(lblTentosTruco, HPos.RIGHT);
        gridpane.add(lblTentosTruco, 0, 21);

        Label infoSimilaridade = new Label(String.valueOf(gameState.getSimilarity()));
        GridPane.setHalignment(infoSimilaridade, HPos.LEFT);
        gridpane.add(infoSimilaridade, 1, 0);

        Label infoCaseId = new Label(String.valueOf(gameState.getCasoMaisSimilar().getId()));
        GridPane.setHalignment(infoCaseId, HPos.LEFT);
        gridpane.add(infoCaseId, 1, 1);

        Label infoJogadorMao = new Label(String.valueOf(gameState.getCasoMaisSimilar().getJogadorMao()));
        GridPane.setHalignment(infoJogadorMao, HPos.LEFT);
        gridpane.add(infoJogadorMao, 1, 2);

        String cartaAlta = getCarta(gameState.getCasoMaisSimilar().getCartaAltaRobo(),
                gameState.getCasoMaisSimilar().getNaipeCartaAltaRobo());

        String cartaMedia = getCarta(gameState.getCasoMaisSimilar().getCartaMediaRobo(),
                gameState.getCasoMaisSimilar().getNaipeCartaMediaRobo());

        String cartaBaixa = getCarta(gameState.getCasoMaisSimilar().getCartaBaixaRobo(),
                gameState.getCasoMaisSimilar().getNaipeCartaBaixaRobo());

        Label infoCartaAlta = new Label(cartaAlta);
        GridPane.setHalignment(infoCartaAlta, HPos.LEFT);
        gridpane.add(infoCartaAlta, 1, 3);

        Label infoCartaMedia = new Label(cartaMedia);
        GridPane.setHalignment(infoCartaMedia, HPos.LEFT);
        gridpane.add(infoCartaMedia, 1, 4);

        Label infoCartaBaixa = new Label(cartaBaixa);
        GridPane.setHalignment(infoCartaBaixa, HPos.LEFT);
        gridpane.add(infoCartaBaixa, 1, 5);

        Label infoPontosEnvido = new Label(String.valueOf(gameState.getCasoMaisSimilar().getPontosEnvidoRobo()));
        GridPane.setHalignment(infoPontosEnvido, HPos.LEFT);
        gridpane.add(infoPontosEnvido, 1, 6);

        Label infoPontuacaoAgente = new Label("Antes: " + String.valueOf(gameState.getCasoMaisSimilar().getTentosAnterioresRobo())
                + " | Depois: " + String.valueOf(gameState.getCasoMaisSimilar().getTentosPosterioresRobo()));
        GridPane.setHalignment(infoPontuacaoAgente, HPos.LEFT);
        gridpane.add(infoPontuacaoAgente, 1, 7);

        Label infoPontuacaoOponente = new Label("Antes: " + String.valueOf(gameState.getCasoMaisSimilar().getTentosAnterioresHumano())
                + " | Depois: " + String.valueOf(gameState.getCasoMaisSimilar().getTentosPosterioresHumano()));
        GridPane.setHalignment(infoPontuacaoOponente, HPos.LEFT);
        gridpane.add(infoPontuacaoOponente, 1, 8);

        String carta1Agente = gameState.getCasoMaisSimilar().getPrimeiraCartaRobo() != null ?
                getCarta(gameState.getCasoMaisSimilar().getPrimeiraCartaRobo(), gameState.getCasoMaisSimilar().getNaipePrimeiraCartaRobo())
                : "null";

        String carta2Agente = gameState.getCasoMaisSimilar().getSegundaCartaRobo() != null ?
                getCarta(gameState.getCasoMaisSimilar().getSegundaCartaRobo(), gameState.getCasoMaisSimilar().getNaipeSegundaCartaRobo())
                : "null";

        String carta3Agente = gameState.getCasoMaisSimilar().getTerceiraCartaRobo() != null ?
                getCarta(gameState.getCasoMaisSimilar().getTerceiraCartaRobo(), gameState.getCasoMaisSimilar().getNaipeTerceiraCartaRobo())
                : "null";


        String carta1Oponente = gameState.getCasoMaisSimilar().getPrimeiraCartaHumano() != null ?
                getCarta(gameState.getCasoMaisSimilar().getPrimeiraCartaHumano(), gameState.getCasoMaisSimilar().getNaipePrimeiraCartaHumano())
                : "null";

        String carta2Oponente = gameState.getCasoMaisSimilar().getSegundaCartaHumano() != null ?
                getCarta(gameState.getCasoMaisSimilar().getSegundaCartaHumano(), gameState.getCasoMaisSimilar().getNaipeSegundaCartaHumano())
                : "null";

        String carta3Oponente = gameState.getCasoMaisSimilar().getTerceiraCartaHumano() != null ?
                getCarta(gameState.getCasoMaisSimilar().getTerceiraCartaHumano(), gameState.getCasoMaisSimilar().getNaipeTerceiraCartaHumano())
                : "null";

        Label infoCartasJogadasAgente = new Label("1ª Carta: " + carta1Agente + " | 2ª Carta: " +
                carta2Agente + " | 3ª Carta: " + carta3Agente);
        GridPane.setHalignment(infoCartasJogadasAgente, HPos.LEFT);
        gridpane.add(infoCartasJogadasAgente, 1, 9);

        Label infoCartasJogadasOponente = new Label("1ª Carta: " + carta1Oponente + " | 2ª Carta: " +
                carta2Oponente + " | 3ª Carta: " + carta3Oponente);
        GridPane.setHalignment(infoCartasJogadasOponente, HPos.LEFT);
        gridpane.add(infoCartasJogadasOponente, 1, 10);

        String ganhador1 = gameState.getCasoMaisSimilar().getGanhadorPrimeiraRodada() != null ?
                String.valueOf(gameState.getCasoMaisSimilar().getGanhadorPrimeiraRodada())  : "null";

        String ganhador2 = gameState.getCasoMaisSimilar().getGanhadorSegundaRodada() != null ?
                String.valueOf(gameState.getCasoMaisSimilar().getGanhadorSegundaRodada())  : "null";

        String ganhador3 = gameState.getCasoMaisSimilar().getGanhadorTerceiraRodada() != null ?
                String.valueOf(gameState.getCasoMaisSimilar().getGanhadorTerceiraRodada())  : "null";

        Label infoGanhadoresRodadas = new Label("1ª Rodada: " + ganhador1 + " | 2ª Rodada: " + ganhador2 +
                " | 3ª Rodada: " + ganhador3);
        GridPane.setHalignment(infoGanhadoresRodadas, HPos.LEFT);
        gridpane.add(infoGanhadoresRodadas, 1, 11);

        Label infoEnvido = new Label((gameState.getCasoMaisSimilar().getQuemPediuEnvido() != null &&
                !gameState.getCasoMaisSimilar().getQuemPediuEnvido().equals(0)) ?
                String.valueOf(gameState.getCasoMaisSimilar().getQuemPediuEnvido())  : "null");
        GridPane.setHalignment(infoEnvido, HPos.LEFT);
        gridpane.add(infoEnvido, 1, 12);

        Label infoRealEnvido = new Label((gameState.getCasoMaisSimilar().getQuemPediuRealEnvido() != null &&
                !gameState.getCasoMaisSimilar().getQuemPediuRealEnvido().equals(0)) ?
                String.valueOf(gameState.getCasoMaisSimilar().getQuemPediuRealEnvido())  : "null");
        GridPane.setHalignment(infoRealEnvido, HPos.LEFT);
        gridpane.add(infoRealEnvido, 1, 13);

        Label infoFaltaEnvido = new Label((gameState.getCasoMaisSimilar().getQuemPediuFaltaEnvido() != null &&
                !gameState.getCasoMaisSimilar().getQuemPediuFaltaEnvido().equals(0)) ?
                String.valueOf(gameState.getCasoMaisSimilar().getQuemPediuFaltaEnvido())  : "null");
        GridPane.setHalignment(infoFaltaEnvido, HPos.LEFT);
        gridpane.add(infoFaltaEnvido, 1, 14);

        Label infoNegouEnvido = new Label((gameState.getCasoMaisSimilar().getQuemNegouEnvido() != null &&
                !gameState.getCasoMaisSimilar().getQuemNegouEnvido().equals(0)) ?
                String.valueOf(gameState.getCasoMaisSimilar().getQuemNegouEnvido())  : "null");
        GridPane.setHalignment(infoNegouEnvido, HPos.LEFT);
        gridpane.add(infoNegouEnvido, 1, 15);

        Label infoTruco = new Label((gameState.getCasoMaisSimilar().getQuemTruco() != null &&
                !gameState.getCasoMaisSimilar().getQuemTruco().equals(0)) ?
                String.valueOf(gameState.getCasoMaisSimilar().getQuemTruco())  : "null");
        GridPane.setHalignment(infoTruco, HPos.LEFT);
        gridpane.add(infoTruco, 1, 16);

        Label infoRetruco = new Label((gameState.getCasoMaisSimilar().getQuemRetruco() != null &&
                !gameState.getCasoMaisSimilar().getQuemRetruco().equals(0)) ?
                String.valueOf(gameState.getCasoMaisSimilar().getQuemRetruco())  : "null");
        GridPane.setHalignment(infoRetruco, HPos.LEFT);
        gridpane.add(infoRetruco, 1, 17);

        Label infoVale4 = new Label((gameState.getCasoMaisSimilar().getQuemValeQuatro() != null &&
                !gameState.getCasoMaisSimilar().getQuemValeQuatro().equals(0)) ?
                String.valueOf(gameState.getCasoMaisSimilar().getQuemValeQuatro())  : "null");
        GridPane.setHalignment(infoVale4, HPos.LEFT);
        gridpane.add(infoVale4, 1, 18);

        Label infoNegouTruco = new Label((gameState.getCasoMaisSimilar().getQuemNegouTruco() != null &&
                !gameState.getCasoMaisSimilar().getQuemNegouTruco().equals(0)) ?
                String.valueOf(gameState.getCasoMaisSimilar().getQuemNegouTruco())  : "null");
        GridPane.setHalignment(infoNegouTruco, HPos.LEFT);
        gridpane.add(infoNegouTruco, 1, 19);

        Label infoTentosEnvido = new Label((gameState.getCasoMaisSimilar().getTentosEnvido() != null &&
                !gameState.getCasoMaisSimilar().getTentosEnvido().equals(0)) ?
                String.valueOf(gameState.getCasoMaisSimilar().getTentosEnvido())  : "0");
        GridPane.setHalignment(infoTentosEnvido, HPos.LEFT);
        gridpane.add(infoTentosEnvido, 1, 20);

        Label infoTentosTruco = new Label((gameState.getCasoMaisSimilar().getTentosTruco() != null &&
                !gameState.getCasoMaisSimilar().getTentosTruco().equals(0)) ?
                String.valueOf(gameState.getCasoMaisSimilar().getTentosTruco())  : "0");
        GridPane.setHalignment(infoTentosTruco, HPos.LEFT);
        gridpane.add(infoTentosTruco, 1, 21);


        return gridpane;
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

    public void setInfoMostSimilarCase() {
        if (gameState.getCasoMaisSimilar() != null) {
            lblSimilarity.setText("Similarity: " + String.valueOf(gameState.getSimilarity()));
            lblCaseId.setText("CaseId: " + String.valueOf(gameState.getCaseId()));
        } else {
            btnDetails.setDisable(true);
        }

       /* cbIsOpportunity.setSelected(isDeceptionOpportunity);
        cbIsBluff.setDisable(!cbIsOpportunity.isSelected());*/
        //Colocar aqui as informações do grid

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

    public List<CartasModelo> getCartas(){

        List<CartasModelo> cartas = new ArrayList<CartasModelo>();
        cartas.add(new CartasModelo("4o", 0, "OURO", 4, 1));
        cartas.add(new CartasModelo("4c", 0, "COPAS", 4, 1));
        cartas.add(new CartasModelo("4p", 0, "BASTOS", 4, 1));
        cartas.add(new CartasModelo("4e", 0, "ESPADAS", 4, 1));
        cartas.add(new CartasModelo("5o", 1, "OURO", 5, 2));
        cartas.add(new CartasModelo("5c", 1, "COPAS", 5, 2));
        cartas.add(new CartasModelo("5p", 1, "BASTOS", 5, 2));
        cartas.add(new CartasModelo("5e", 1, "ESPADAS", 5, 2));
        cartas.add(new CartasModelo("6o", 2, "OURO", 6, 3));
        cartas.add(new CartasModelo("6c", 2, "COPAS", 6, 3));
        cartas.add(new CartasModelo("6p", 2, "BASTOS", 6, 3));
        cartas.add(new CartasModelo("6e", 2, "ESPADAS", 6, 3));
        cartas.add(new CartasModelo("7c", 3, "COPAS", 7, 4));
        cartas.add(new CartasModelo("7p", 3, "BASTOS", 7, 4));
        cartas.add(new CartasModelo("10o", 4, "OURO", 0, 6));
        cartas.add(new CartasModelo("10c", 4, "COPAS", 0, 6));
        cartas.add(new CartasModelo("10p", 4, "BASTOS", 0, 6));
        cartas.add(new CartasModelo("10e", 4, "ESPADAS", 0, 6));
        cartas.add(new CartasModelo("11o", 5, "OURO", 0, 7));
        cartas.add(new CartasModelo("11c", 5, "COPAS", 0, 7));
        cartas.add(new CartasModelo("11p", 5, "BASTOS", 0, 7));
        cartas.add(new CartasModelo("11e", 5, "ESPADAS", 0, 7));
        cartas.add(new CartasModelo("12o", 6, "OURO", 0, 8));
        cartas.add(new CartasModelo("12c", 6, "COPAS", 0, 8));
        cartas.add(new CartasModelo("12p", 6, "BASTOS", 0, 8));
        cartas.add(new CartasModelo("12e", 6, "ESPADAS", 0, 8));
        cartas.add(new CartasModelo("1o", 7, "OURO", 1, 12));
        cartas.add(new CartasModelo("1c", 7, "COPAS", 1, 12));
        cartas.add(new CartasModelo("2o", 8, "OURO", 2, 16));
        cartas.add(new CartasModelo("2c", 8, "COPAS", 2, 16));
        cartas.add(new CartasModelo("2p", 8, "BASTOS", 2, 16));
        cartas.add(new CartasModelo("2e", 8, "ESPADAS", 2, 16));
        cartas.add(new CartasModelo("3o", 9, "OURO", 3, 24));
        cartas.add(new CartasModelo("3c", 9, "COPAS", 3, 24));
        cartas.add(new CartasModelo("3p", 9, "BASTOS", 3, 24));
        cartas.add(new CartasModelo("3e", 9, "ESPADAS", 3, 24));
        cartas.add(new CartasModelo("7o", 10, "OURO", 7, 40));
        cartas.add(new CartasModelo("7e", 11, "ESPADAS", 7, 42));
        cartas.add(new CartasModelo("1p", 12, "BASTOS", 1, 50));
        cartas.add(new CartasModelo("1e", 13, "ESPADAS", 1, 52));

        return cartas;
    }

    public String getCarta(int cbrCode, String naipe) {

        String carta = "";

        for (CartasModelo card : cartas) {
            if (card.getId().equals(cbrCode) && card.getNaipe().equals(naipe)) {
                carta = card.getCarta();
            }
        }
        return carta;
    }

}
