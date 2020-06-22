package gamer.ui.controller;

import gamer.Auto.ControlaPartidaAuto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import novosTestes.Agentes10MelhoresAtivo;
import novosTestes.Agentes4MelhoresAtivo;
import novosTestes.ControllerAutomaticoNew;
import treinamentoModelo.AgenteModelo;
import treinamentoModelo.SetCbrModelo;

import java.util.HashMap;
import java.util.Random;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 05/12/2019.
 */


public class LoaderActiveLearningController {

    private ControllerAutomaticoNew controllerAutomaticoNew;

    public ControllerAutomaticoNew getControllerAutomaticoNew() {
        return controllerAutomaticoNew;
    }

    public void setControllerAutomaticoNew(ControllerAutomaticoNew controllerAutomaticoNew) {
        this.controllerAutomaticoNew = controllerAutomaticoNew;
    }

    @FXML
    private Button btnPlay;

    @FXML
    public void initialize() {

        btnPlay.setOnAction((event) -> {

            int numeroExecucoes = 5;

            //Agentes10MelhoresAtivo agentes = new Agentes10MelhoresAtivo();
            Agentes4MelhoresAtivo agentes = new Agentes4MelhoresAtivo();
            HashMap<Integer, AgenteModelo> agentesParaAprender = agentes.retornaHashDeAgentesParaAprender();
            HashMap<Integer, AgenteModelo> agentesParaNaoAprender = agentes.retornaHashDeAgentesParaNaoAprender();
            Random randomAprender = new Random();
            Random randomNaoAprender = new Random();

            for(int i= 0; i< numeroExecucoes; i++) {
                ControlaPartidaAuto.controlaPartidaAuto = null;
                int agenteSorteadoAprender = randomAprender.nextInt(4) + 1;
                int agenteSorteadoNaoAprender = randomNaoAprender.nextInt(4) + 1;
                AgenteModelo agenteUm = agentesParaAprender.get(agenteSorteadoAprender);
                AgenteModelo agenteDois = agentesParaNaoAprender.get(agenteSorteadoNaoAprender);
                SetCbrModelo setTreinamento = new SetCbrModelo(agenteUm, agenteDois);
                controllerAutomaticoNew = new ControllerAutomaticoNew();
                controllerAutomaticoNew.Treinar(setTreinamento, 1);
            }
        });

    }
}
