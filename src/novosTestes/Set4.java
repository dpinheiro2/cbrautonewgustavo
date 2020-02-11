package novosTestes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import gamer.Auto.ControlaPartidaAuto;
import treinamentoModelo.AgenteModelo;
import treinamentoModelo.SetCbrModelo;

public class Set4 {
//agente de 1 a 5 contra todos, sempre são removidos agentes que já jogaram
	
	public static List<SetCbrModelo> criaConfiguracoesEntreJogadoresSet4() {
		HashMap<Integer, AgenteModelo> hashDeAgentes = new Agentes().retornaHashDeAgentes();
		List<SetCbrModelo> listaDeConfiguracoes = new ArrayList<SetCbrModelo>();
		
		//remove anteriores
		hashDeAgentes.remove(1);
		hashDeAgentes.remove(2);
		hashDeAgentes.remove(3);
		hashDeAgentes.remove(4);
		hashDeAgentes.remove(5);
		hashDeAgentes.remove(6);
		hashDeAgentes.remove(7);
		hashDeAgentes.remove(8);
		hashDeAgentes.remove(9);
		hashDeAgentes.remove(10);
		hashDeAgentes.remove(11);
		hashDeAgentes.remove(12);
		hashDeAgentes.remove(13);
		hashDeAgentes.remove(14);
		hashDeAgentes.remove(15);
		
		AgenteModelo desafiado = hashDeAgentes.get(16);
		hashDeAgentes.remove(16);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, desafiado);
		
		desafiado = hashDeAgentes.get(17);
		hashDeAgentes.remove(17);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, desafiado);
		
		desafiado = hashDeAgentes.get(18);
		hashDeAgentes.remove(18);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, desafiado);
		
		desafiado = hashDeAgentes.get(19);
		hashDeAgentes.remove(19);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, desafiado);
		
		desafiado = hashDeAgentes.get(20);
		hashDeAgentes.remove(20);
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
	List<SetCbrModelo> listaDeCbrParaPartidas = criaConfiguracoesEntreJogadoresSet4();
	 for(int i= 0; i<= listaDeCbrParaPartidas.size(); i++) {
	      	ControlaPartidaAuto.controlaPartidaAuto = null;
	      	new ControllerAutomaticoNew().Treinar(listaDeCbrParaPartidas.get(i), 25);
	      }
}
}
