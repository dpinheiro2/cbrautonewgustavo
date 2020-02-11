package gamer.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 05/12/2019.
 */


public class Teste2Controller {

    @FXML
    private Label lblTeste;

    public void initData(String teste) {

        //System.out.println("Teste Tela2");

        lblTeste.setText(teste);



    }

}
