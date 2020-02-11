package gamer.ui.controller;

import gamer.Auto.ControlaPartidaAuto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

            int numeroExecucoes = 100;

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
                agenteUm.setTipoAprendizagem("ativo");
                agenteUm.setTipoBase("ativo");
                SetCbrModelo setTreinamento = new SetCbrModelo(agenteUm, agenteDois);
                controllerAutomaticoNew = new ControllerAutomaticoNew();
                controllerAutomaticoNew.Treinar(setTreinamento, 1);
            }


            //LoaderActiveLearning.openTeste2Screen();

            //Selecionadas as melhores configurações conforme os testes do gustavo. Agent = Melhor - Opponent = Segundo Melhor
           /* AgenteModelo agent = new AgenteModelo("chancesucesso", "probabilidadechance", "Nenhum", "yes", "nada", "ativo", 0.75, false);
            AgenteModelo opponent = new AgenteModelo("chancesucesso", "chancesucesso", "Nenhum", "no", "nada", "baseline", 0.75, false);

            SetCbrModelo configuracaoMatch = new SetCbrModelo(agent, opponent);*/

            //System.out.println("Iniciou");

           /* int numeroDePartidas = 0;

            ControlaPartidaAuto.controlaPartidaAuto = null;
            controllerAutomaticoNew = new ControllerAutomaticoNew();
            controllerAutomaticoNew.Treinar(configuracaoMatch, numeroDePartidas);*/

            //System.out.println("terminou");

            /*Stage stage = (Stage) btnPlay.getScene().getWindow();
            stage.close();*/
        });

    }
}
