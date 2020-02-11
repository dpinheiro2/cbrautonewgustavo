
package novosTestes.validacaoDois;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import gamer.Auto.ControlaPartidaAuto;
import novosTestes.Agentes10MelhoresBaseline;
import novosTestes.Agentes10MelhoresImitacao;
import novosTestes.ControllerAutomaticoNew;
import treinamentoModelo.AgenteModelo;
import treinamentoModelo.SetCbrModelo;

public class SetValidaImitacao {
//agente de 1 a 5 contra todos, sempre são removidos agentes que já jogaram
	
	public static List<SetCbrModelo> criaConfiguracoesEntreJogadoresSet1() {
		HashMap<Integer, AgenteModelo> hashDeAgentesBaseline = new Agentes10MelhoresBaseline().retornaHashDeAgentesParaNaoAprender();
		HashMap<Integer, AgenteModelo> hashDeAgentesImitacao = new Agentes10MelhoresImitacao().retornaHashDeAgentesParaNaoAprender();
		
		List<SetCbrModelo> listaDeConfiguracoes = new ArrayList<SetCbrModelo>();
		SetCbrModelo setAgente1 = new SetCbrModelo(hashDeAgentesBaseline.get(1), hashDeAgentesImitacao.get(1));
		SetCbrModelo setAgente2 = new SetCbrModelo(hashDeAgentesBaseline.get(2), hashDeAgentesImitacao.get(2));
		SetCbrModelo setAgente3 = new SetCbrModelo(hashDeAgentesBaseline.get(3), hashDeAgentesImitacao.get(3));
		SetCbrModelo setAgente4 = new SetCbrModelo(hashDeAgentesBaseline.get(4), hashDeAgentesImitacao.get(4));
		SetCbrModelo setAgente5 = new SetCbrModelo(hashDeAgentesBaseline.get(5), hashDeAgentesImitacao.get(5));
		SetCbrModelo setAgente6 = new SetCbrModelo(hashDeAgentesBaseline.get(6), hashDeAgentesImitacao.get(6));
		SetCbrModelo setAgente7 = new SetCbrModelo(hashDeAgentesBaseline.get(7), hashDeAgentesImitacao.get(7));
		SetCbrModelo setAgente8 = new SetCbrModelo(hashDeAgentesBaseline.get(8), hashDeAgentesImitacao.get(8));
		SetCbrModelo setAgente9 = new SetCbrModelo(hashDeAgentesBaseline.get(9), hashDeAgentesImitacao.get(9));
		SetCbrModelo setAgente10 = new SetCbrModelo(hashDeAgentesBaseline.get(10), hashDeAgentesImitacao.get(10));
		
		
		SetCbrModelo setAgente1Invertido = new SetCbrModelo(hashDeAgentesImitacao.get(1),hashDeAgentesBaseline.get(1));
		SetCbrModelo setAgente2Invertido = new SetCbrModelo(hashDeAgentesImitacao.get(2), hashDeAgentesBaseline.get(2));
		SetCbrModelo setAgente3Invertido = new SetCbrModelo(hashDeAgentesImitacao.get(3), hashDeAgentesBaseline.get(3));
		SetCbrModelo setAgente4Invertido = new SetCbrModelo(hashDeAgentesImitacao.get(4), hashDeAgentesBaseline.get(4));
		SetCbrModelo setAgente5Invertido = new SetCbrModelo(hashDeAgentesImitacao.get(5), hashDeAgentesBaseline.get(5));
		SetCbrModelo setAgente6Invertido = new SetCbrModelo(hashDeAgentesImitacao.get(6), hashDeAgentesBaseline.get(6));
		SetCbrModelo setAgente7Invertido = new SetCbrModelo(hashDeAgentesImitacao.get(7), hashDeAgentesBaseline.get(7));
		SetCbrModelo setAgente8Invertido = new SetCbrModelo(hashDeAgentesImitacao.get(8),hashDeAgentesBaseline.get(8));
		SetCbrModelo setAgente9Invertido = new SetCbrModelo(hashDeAgentesImitacao.get(9), hashDeAgentesBaseline.get(9));
		SetCbrModelo setAgente10Invertido = new SetCbrModelo(hashDeAgentesImitacao.get(10), hashDeAgentesBaseline.get(10));
		
		listaDeConfiguracoes.add(setAgente1);
		listaDeConfiguracoes.add(setAgente2);
		listaDeConfiguracoes.add(setAgente3);
		listaDeConfiguracoes.add(setAgente4);
		listaDeConfiguracoes.add(setAgente5);
		listaDeConfiguracoes.add(setAgente6);
		listaDeConfiguracoes.add(setAgente7);
		listaDeConfiguracoes.add(setAgente8);
		listaDeConfiguracoes.add(setAgente9);
		listaDeConfiguracoes.add(setAgente10);
		
		listaDeConfiguracoes.add(setAgente1Invertido);
		listaDeConfiguracoes.add(setAgente2Invertido);
		listaDeConfiguracoes.add(setAgente3Invertido);
		listaDeConfiguracoes.add(setAgente4Invertido);
		listaDeConfiguracoes.add(setAgente5Invertido);
		listaDeConfiguracoes.add(setAgente6Invertido);
		listaDeConfiguracoes.add(setAgente7Invertido);
		listaDeConfiguracoes.add(setAgente8Invertido);
		listaDeConfiguracoes.add(setAgente9Invertido);
		listaDeConfiguracoes.add(setAgente10Invertido);
		
		return listaDeConfiguracoes;
		
		
		
	}


	
public static void main(String[] args) {
	List<SetCbrModelo> listaDeCbrParaPartidas = criaConfiguracoesEntreJogadoresSet1();
	ControllerAutomaticoNew auto;
System.out.println("Iniciou");
	int numeroDePartidas = 25;
	for(int i= 0; i<= listaDeCbrParaPartidas.size(); i++) {
	      	ControlaPartidaAuto.controlaPartidaAuto = null;
	      	auto = new ControllerAutomaticoNew();
	      	auto.Treinar(listaDeCbrParaPartidas.get(i), numeroDePartidas);
	      }
	System.out.println("terminou");
}
}
