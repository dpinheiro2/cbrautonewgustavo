
package novosTestes.validacaoDois;

import gamer.Auto.ControlaPartidaAuto;
import novosTestes.*;
import treinamentoModelo.AgenteModelo;
import treinamentoModelo.SetCbrModelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetValidaAtivo {
//agente de 1 a 5 contra todos, sempre são removidos agentes que já jogaram

	public static List<SetCbrModelo> criaConfiguracoesEntreJogadoresSet1() {

		HashMap<Integer, AgenteModelo> hashDeAgentesImitacao = new Agentes4MelhoresImitacao().retornaHashDeAgentesParaNaoAprender();
		HashMap<Integer, AgenteModelo> hashDeAgentesAtivo = new Agentes4MelhoresAtivo().retornaHashDeAgentesParaNaoAprender();

		List<SetCbrModelo> listaDeConfiguracoes = new ArrayList<SetCbrModelo>();
		SetCbrModelo setAgente1 = new SetCbrModelo(hashDeAgentesImitacao.get(1), hashDeAgentesAtivo.get(1));
		SetCbrModelo setAgente2 = new SetCbrModelo(hashDeAgentesImitacao.get(2), hashDeAgentesAtivo.get(2));
		SetCbrModelo setAgente3 = new SetCbrModelo(hashDeAgentesImitacao.get(3), hashDeAgentesAtivo.get(3));
		SetCbrModelo setAgente4 = new SetCbrModelo(hashDeAgentesImitacao.get(4), hashDeAgentesAtivo.get(4));


		SetCbrModelo setAgente1Invertido = new SetCbrModelo(hashDeAgentesAtivo.get(1),hashDeAgentesImitacao.get(1));
		SetCbrModelo setAgente2Invertido = new SetCbrModelo(hashDeAgentesAtivo.get(2), hashDeAgentesImitacao.get(2));
		SetCbrModelo setAgente3Invertido = new SetCbrModelo(hashDeAgentesAtivo.get(3), hashDeAgentesImitacao.get(3));
		SetCbrModelo setAgente4Invertido = new SetCbrModelo(hashDeAgentesAtivo.get(4), hashDeAgentesImitacao.get(4));

		listaDeConfiguracoes.add(setAgente1);
		listaDeConfiguracoes.add(setAgente2);
		listaDeConfiguracoes.add(setAgente3);
		listaDeConfiguracoes.add(setAgente4);

		listaDeConfiguracoes.add(setAgente1Invertido);
		listaDeConfiguracoes.add(setAgente2Invertido);
		listaDeConfiguracoes.add(setAgente3Invertido);
		listaDeConfiguracoes.add(setAgente4Invertido);

		return listaDeConfiguracoes;



	}



	public static void main(String[] args) {
		List<SetCbrModelo> listaDeCbrParaPartidas = criaConfiguracoesEntreJogadoresSet1();
		ControllerAutomaticoNew auto;
		System.out.println("Iniciou");
		int numeroDePartidas = 1;
		for(int i= 0; i<= listaDeCbrParaPartidas.size(); i++) {
			ControlaPartidaAuto.controlaPartidaAuto = null;
			auto = new ControllerAutomaticoNew();
			auto.Treinar(listaDeCbrParaPartidas.get(i), numeroDePartidas);
		}
		System.out.println("terminou");
	}
}
