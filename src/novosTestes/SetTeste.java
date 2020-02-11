package novosTestes;

import gamer.Auto.ControlaPartidaAuto;
import treinamentoModelo.AgenteModelo;
import treinamentoModelo.SetCbrModelo;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 14/01/2020.
 */


public class SetTeste {
    public static void main(String[] args) {


        //Selecionadas as melhores configurações conforme os testes do gustavo. Agent = Melhor - Opponent = Segundo Melhor
        AgenteModelo agent = new AgenteModelo("chancesucesso", "probabilidadechance", "Nenhum", "yes", "nada", "baseline", 0.75, false);
        AgenteModelo opponent = new AgenteModelo("chancesucesso", "chancesucesso", "Nenhum", "no", "nada", "baseline", 0.75, false);

        SetCbrModelo configuracaoMatch = new SetCbrModelo(agent, opponent);

        //System.out.println("Iniciou");

        int numeroDePartidas = 1;

        ControlaPartidaAuto.controlaPartidaAuto = null;
        ControllerAutomaticoNew auto = new ControllerAutomaticoNew();
        auto.Treinar(configuracaoMatch, numeroDePartidas);

        //System.out.println("terminou");
    }
}
