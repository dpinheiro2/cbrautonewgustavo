package novosTestes;

import treinamentoModelo.AgenteModelo;

import java.util.HashMap;

public class Agentes4MelhoresAtivo {

	public HashMap<Integer, AgenteModelo> retornaHashDeAgentesParaAprender(){

		HashMap<Integer, AgenteModelo> hashAgentes = new HashMap<Integer, AgenteModelo>();
		AgenteModelo agente1 = new AgenteModelo("probabilidadechance", "probabilidadechance", "ativo", "yes", "nada", "ativo", 0.75, true, false);
		hashAgentes.put(1, agente1);
		
		AgenteModelo agente2 = new AgenteModelo("probabilidadechance", "probabilidadechance", "ativo", "no", "nada", "ativo", 0.75, true, false);
		hashAgentes.put(2, agente2);

		AgenteModelo agente4 = new AgenteModelo("chancesucesso", "chancesucesso", "ativo", "no", "nada", "ativo", 0.75, true, false);
		hashAgentes.put(3, agente4);

		AgenteModelo agente18 = new AgenteModelo("chancesucesso", "probabilidadechance", "ativo", "yes", "nada", "ativo", 0.75, true, false);
		hashAgentes.put(4, agente18);

		return hashAgentes;
		
	}
	
	public HashMap<Integer, AgenteModelo> retornaHashDeAgentesParaNaoAprender(){

		HashMap<Integer, AgenteModelo> hashAgentes = new HashMap<Integer, AgenteModelo>();
		AgenteModelo agente1 = new AgenteModelo("probabilidadechance", "probabilidadechance", "Nenhum", "yes", "nada", "ativo", 0.75, false, false);
		hashAgentes.put(1, agente1);
		
		AgenteModelo agente2 = new AgenteModelo("probabilidadechance", "probabilidadechance", "Nenhum", "no", "nada", "ativo", 0.75, false, false);
		hashAgentes.put(2, agente2);

		AgenteModelo agente4 = new AgenteModelo("chancesucesso", "chancesucesso", "Nenhum", "no", "nada", "ativo", 0.75, false, false);
		hashAgentes.put(3, agente4);

		AgenteModelo agente18 = new AgenteModelo("chancesucesso", "probabilidadechance", "Nenhum", "yes", "nada", "ativo", 0.75, false, false);
		hashAgentes.put(4, agente18);

		return hashAgentes;
		
	}

	
}
