package novosTestes;

import treinamentoModelo.AgenteModelo;

import java.util.HashMap;

public class Agentes4MelhoresImitacao {

	public HashMap<Integer, AgenteModelo> retornaHashDeAgentesParaAprender(){

		HashMap<Integer, AgenteModelo> hashAgentes = new HashMap<Integer, AgenteModelo>();
		AgenteModelo agente1 = new AgenteModelo("probabilidadechance", "probabilidadechance", "imitacao", "yes", "nada", "imitacao", 0.75, false, false);
		hashAgentes.put(1, agente1);
		
		AgenteModelo agente2 = new AgenteModelo("probabilidadechance", "probabilidadechance", "imitacao", "no", "nada", "imitacao", 0.75, false, false);
		hashAgentes.put(2, agente2);

		AgenteModelo agente4 = new AgenteModelo("chancesucesso", "chancesucesso", "imitacao", "no", "nada", "imitacao", 0.75, false, false);
		hashAgentes.put(3, agente4);

		AgenteModelo agente18 = new AgenteModelo("chancesucesso", "probabilidadechance", "imitacao", "yes", "nada", "imitacao", 0.75, false, false);
		hashAgentes.put(4, agente18);

		return hashAgentes;
		
	}
	
	public HashMap<Integer, AgenteModelo> retornaHashDeAgentesParaNaoAprender(){

		HashMap<Integer, AgenteModelo> hashAgentes = new HashMap<Integer, AgenteModelo>();
		AgenteModelo agente1 = new AgenteModelo("probabilidadechance", "probabilidadechance", "Nenhum", "yes", "nada", "imitacao", 0.75, false, false);
		hashAgentes.put(1, agente1);
		
		AgenteModelo agente2 = new AgenteModelo("probabilidadechance", "probabilidadechance", "Nenhum", "no", "nada", "imitacao", 0.75, false, false);
		hashAgentes.put(2, agente2);

		AgenteModelo agente4 = new AgenteModelo("chancesucesso", "chancesucesso", "Nenhum", "no", "nada", "imitacao", 0.75, false, false);
		hashAgentes.put(3, agente4);

		AgenteModelo agente18 = new AgenteModelo("chancesucesso", "probabilidadechance", "Nenhum", "yes", "nada", "imitacao", 0.75, false, false);
		hashAgentes.put(4, agente18);

		return hashAgentes;
		
	}

	
}
