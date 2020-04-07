package gamer.ui.loader;

import gamer.ui.controller.ActiveController;
import gamer.ui.controller.LoaderActiveLearningController;
import gamer.ui.controller.Teste2Controller;
import utils.GameState;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 05/12/2019.
 */


public class LoaderActiveLearning extends Application {

    private static Scene scene1;
    private static Scene scene2;
    private static Scene activeScene;
    public static LoaderActiveLearningController loaderActiveLearningController;
    public static Teste2Controller teste2Controller;
    public static ActiveController activeController;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loaderTela1 = new FXMLLoader();
        loaderTela1.setLocation(LoaderActiveLearning.class.getResource("/gamer/ui/view/loaderActiveLearning.fxml"));
        try {
            Parent fxmlTela1 = loaderTela1.load();
            scene1 = new Scene(fxmlTela1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        loaderActiveLearningController = loaderTela1.getController();
        stage.setScene(scene1);
        stage.setResizable(false);
        stage.setTitle("Bot Vs Bot");
        stage.show();

    }

    public static void openTeste2Screen() {

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("UFSM - CBR Group");

        FXMLLoader activeLoader = new FXMLLoader();
        activeLoader.setLocation(LoaderActiveLearning.class.getResource("/gamer/ui/view/teste2.fxml"));
        try {
            Parent fxmlActive = activeLoader.load();
            scene2 = new Scene(fxmlActive);
        } catch (IOException e) {
            e.printStackTrace();
        }

        teste2Controller = activeLoader.getController();
        teste2Controller.initData("DANIEL");
        stage.setScene(scene2);
        stage.showAndWait();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                //System.out.println("Stage is closing");
                //controllerTelaAutomatica.getControlaRodadaAuto().setResponseActiveLearning(null);
            }
        });
    }

    public static void openActiveLearningScreen(GameState gameState) {

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Active Learning :: UFSM - CBR Group");

        FXMLLoader activeLoader = new FXMLLoader();
        activeLoader.setLocation(LoaderActiveLearning.class.getResource("/gamer/ui/view/activeLearningScreen.fxml"));
        try {
            Parent fxmlActive = activeLoader.load();
            activeScene = new Scene(fxmlActive);
        } catch (IOException e) {
            e.printStackTrace();
        }

        activeController = activeLoader.getController();
        activeController.initData(gameState, loaderActiveLearningController.getControllerAutomaticoNew().getControlaRodadaAuto());
        stage.setScene(activeScene);
        stage.showAndWait();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                //System.out.println("Stage is closing");
                loaderActiveLearningController.getControllerAutomaticoNew().getControlaRodadaAuto().setResponseActiveLearning(null);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }


}
