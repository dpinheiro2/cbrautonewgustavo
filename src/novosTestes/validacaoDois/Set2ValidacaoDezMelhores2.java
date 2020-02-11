package novosTestes.validacaoDois;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import gamer.Auto.ControlaPartidaAuto;
import novosTestes.Agentes;
import novosTestes.Agentes10MelhoresImitacao;
import novosTestes.ControllerAutomaticoNew;
import treinamentoModelo.AgenteModelo;
import treinamentoModelo.SetCbrModelo;

public class Set2ValidacaoDezMelhores2 {
//agente de 1 a 5 contra todos, sempre são removidos agentes que já jogaram
	
	public static List<SetCbrModelo> criaConfiguracoesEntreJogadoresSet1() {
		HashMap<Integer, AgenteModelo> hashDeAgentes = new Agentes10MelhoresImitacao().retornaHashDeAgentesParaNaoAprender();
		List<SetCbrModelo> listaDeConfiguracoes = new ArrayList<SetCbrModelo>();
		//remove anteriores
		hashDeAgentes.remove(1);
		hashDeAgentes.remove(2);
		hashDeAgentes.remove(3);
		hashDeAgentes.remove(4);
		hashDeAgentes.remove(5);
		
		
		AgenteModelo desafiado = hashDeAgentes.get(6);
		hashDeAgentes.remove(6);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, desafiado);
		
		desafiado = hashDeAgentes.get(7);
		hashDeAgentes.remove(7);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, desafiado);
		
		desafiado = hashDeAgentes.get(8);
		hashDeAgentes.remove(8);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, desafiado);
		
		desafiado = hashDeAgentes.get(9);
		hashDeAgentes.remove(9);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, desafiado);
		
		desafiado = hashDeAgentes.get(10);
		hashDeAgentes.remove(10);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, desafiado);
		
		return listaDeConfiguracoes;
		
		
		
	}

private static List<SetCbrModelo> retornaConfiguracoesIndividuais(HashMap<Integer, AgenteModelo> hashDeAgentes,
		List<SetCbrModelo> listaDeConfiguracoes, AgenteModelo desafiado) {
	Set<Integer> keys;
	Iterator iKeys;
	keys = (Set<Integer>) hashDeAgentes.keySet();
	iKeys = keys.iterator();
	while(iKeys.hasNext()) {
		Integer idDesafiador = (Integer) iKeys.next();
		AgenteModelo desafiador = hashDeAgentes.get(idDesafiador);
		SetCbrModelo configuracaoMatch = new SetCbrModelo(desafiado, desafiador);
		listaDeConfiguracoes.add(configuracaoMatch);
		
		SetCbrModelo configuracaoMatchInvertida = new SetCbrModelo(desafiador, desafiado);
		listaDeConfiguracoes.add(configuracaoMatchInvertida);
		
	}
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
