package novosTestes;

import gamer.Auto.ControlaPartidaAuto;
import treinamentoModelo.AgenteModelo;
import treinamentoModelo.SetCbrModelo;

import java.util.*;

public class SetCompetition10Agents3K {

	/** Experimento 1
	 * - Competição entre os 10 melhores agente do Gustavo;
	 * - Todos contra todos;
	 * - Base de casos de 3k;
	 * - Objetivo observar o comportamento de cada agente através de contadores de jogadas realizadas;
	 * */

	public static List<SetCbrModelo> criaConfiguracoesEntreJogadores() {

		/**
		 * - Obtem um hash com os 10 melhores agentes Base 3k;
		 * - Monta os confrontos da seguite maneira:
		 *      - após atribuir o agente para variável agent, o mesmo é deletado do hash de agentes (para evitar que ele jogue contra ele mesmo);
		 * 		- para cada agente do hash monta um confronto do agente vs cada agente restante no hash de agentes;
		 * 	    - confronto normal e invertido;
		 * */

		HashMap<Integer, AgenteModelo> hashDeAgentes = new Agentes10MelhoresBaseline().retornaHashDeAgentesParaNaoAprender();
		List<SetCbrModelo> listaDeConfiguracoes = new ArrayList<SetCbrModelo>();

		AgenteModelo agent = hashDeAgentes.get(1);
		hashDeAgentes.remove(1);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, agent);

		agent = hashDeAgentes.get(2);
		hashDeAgentes.remove(2);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, agent);

		agent = hashDeAgentes.get(3);
		hashDeAgentes.remove(3);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, agent);

		agent = hashDeAgentes.get(4);
		hashDeAgentes.remove(4);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, agent);

		agent = hashDeAgentes.get(5);
		hashDeAgentes.remove(5);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, agent);

		agent = hashDeAgentes.get(6);
		hashDeAgentes.remove(6);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, agent);

		agent = hashDeAgentes.get(7);
		hashDeAgentes.remove(7);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, agent);

		agent = hashDeAgentes.get(8);
		hashDeAgentes.remove(8);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, agent);

		agent = hashDeAgentes.get(9);
		hashDeAgentes.remove(9);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, agent);

		agent = hashDeAgentes.get(10);
		hashDeAgentes.remove(10);
		listaDeConfiguracoes = retornaConfiguracoesIndividuais(hashDeAgentes, listaDeConfiguracoes, agent);

		return listaDeConfiguracoes;

	}

	private static List<SetCbrModelo> retornaConfiguracoesIndividuais(HashMap<Integer, AgenteModelo> hashDeAgentes,
																	  List<SetCbrModelo> listaDeConfiguracoes, AgenteModelo agent) {
		Set<Integer> keys;
		Iterator iKeys;
		keys = (Set<Integer>) hashDeAgentes.keySet();
		iKeys = keys.iterator();
		while(iKeys.hasNext()) {
			Integer idOpponent = (Integer) iKeys.next();
			AgenteModelo opponent = hashDeAgentes.get(idOpponent);
			SetCbrModelo configuracaoMatch = new SetCbrModelo(agent, opponent);
			listaDeConfiguracoes.add(configuracaoMatch);

			SetCbrModelo configuracaoMatchInvertida = new SetCbrModelo(opponent, agent);
			listaDeConfiguracoes.add(configuracaoMatchInvertida);

		}
		return listaDeConfiguracoes;
	}

	public static void main(String[] args) {

		List<SetCbrModelo> listaDeCbrParaPartidas = criaConfiguracoesEntreJogadores();
		ControllerAutomaticoNew auto;
		int numeroDePartidas = 25;
		for(int i = 0; i < listaDeCbrParaPartidas.size(); i++) {
			ControlaPartidaAuto.controlaPartidaAuto = null;
			auto = new ControllerAutomaticoNew();
			auto.Treinar(listaDeCbrParaPartidas.get(i), numeroDePartidas);
		}
	}
}
