package novosTestes;

import java.util.HashMap;

import treinamentoModelo.AgenteModelo;

public class Agentes {

	public HashMap<Integer, AgenteModelo> retornaHashDeAgentes(){
		HashMap<Integer, AgenteModelo> hashAgentes = new HashMap<Integer, AgenteModelo>();
		AgenteModelo agente1 = new AgenteModelo("probabilidadechance", "probabilidadechance", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(1, agente1);
		
		AgenteModelo agente2 = new AgenteModelo("probabilidadechance", "probabilidadechance", "Nenhum", "no", "nada", "baseline", 0.75, false);
		hashAgentes.put(2, agente2);
		
		AgenteModelo agente3 = new AgenteModelo("chancesucesso", "chancesucesso", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(3, agente3);
		
		AgenteModelo agente4 = new AgenteModelo("chancesucesso", "chancesucesso", "Nenhum", "no", "nada", "baseline", 0.75, false);
		hashAgentes.put(4, agente4);
		
		AgenteModelo agente5 = new AgenteModelo("maioria", "maioria", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(5, agente5);
		
		AgenteModelo agente6 = new AgenteModelo("maioria", "maioria", "Nenhum", "no", "nada", "baseline", 0.75, false);
		hashAgentes.put(6, agente6);
		
		
		AgenteModelo agente7 = new AgenteModelo("probabilidadesorteio", "probabilidadesorteio", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(7, agente7);
		
		AgenteModelo agente8 = new AgenteModelo("probabilidadesorteio", "probabilidadesorteio", "Nenhum", "no", "nada", "baseline", 0.75, false);
		hashAgentes.put(8, agente8);
		
		AgenteModelo agente9 = new AgenteModelo("maioria", "chancesucesso",  "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(9, agente9);
		
		AgenteModelo agente10 = new AgenteModelo("probabilidadesorteio", "chancesucesso", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(10, agente10);
		
		AgenteModelo agente11 = new AgenteModelo("probabilidadechance", "chancesucesso", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(11, agente11);
		
		AgenteModelo agente12 = new AgenteModelo("chancesucesso", "maioria", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(12, agente12);
		
		AgenteModelo agente13 = new AgenteModelo("probabilidadechance", "maioria", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(13, agente13);
		
		AgenteModelo agente14 = new AgenteModelo("probabilidadesorteio", "maioria", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(14, agente14);
		
		AgenteModelo agente15 = new AgenteModelo("chancesucesso", "probabilidadesorteio", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(15, agente15);
		
		AgenteModelo agente16 = new AgenteModelo("maioria", "probabilidadesorteio", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(16, agente16);
		
		AgenteModelo agente17 = new AgenteModelo("probabilidadechance", "probabilidadesorteio", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(17, agente17);
		
		AgenteModelo agente18 = new AgenteModelo("chancesucesso", "probabilidadechance", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(18, agente18);
		
		AgenteModelo agente19 = new AgenteModelo("maioria", "probabilidadechance", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(19, agente19);
		
		AgenteModelo agente20 = new AgenteModelo("probabilidadesorteio", "probabilidadechance", "Nenhum", "yes", "nada", "baseline", 0.75, false);
		hashAgentes.put(20, agente20);
		
		
		return hashAgentes;
		
	}
	
}
