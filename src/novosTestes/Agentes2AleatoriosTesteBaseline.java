package novosTestes;

import treinamentoModelo.AgenteModelo;

import java.util.HashMap;

public class Agentes2AleatoriosTesteBaseline {

	
	public HashMap<Integer, AgenteModelo> retornaHashDeAgentesParaNaoAprender(){

		HashMap<Integer, AgenteModelo> hashAgentes = new HashMap<Integer, AgenteModelo>();

		AgenteModelo agente1 = new AgenteModelo("probabilidadechance", "probabilidadechance", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(1, agente1);
		
		AgenteModelo agente2 = new AgenteModelo("probabilidadechance", "probabilidadechance", "Nenhum", "no", "nada", "baseline", 0.75, false);
		hashAgentes.put(2, agente2);

		return hashAgentes;
		
	}

	
}
