package novosTestes;

import java.util.HashMap;

import treinamentoModelo.AgenteModelo;

public class Agentes10MelhoresImitacao {

	public HashMap<Integer, AgenteModelo> retornaHashDeAgentesParaAprender(){
		HashMap<Integer, AgenteModelo> hashAgentes = new HashMap<Integer, AgenteModelo>();
		AgenteModelo agente1 = new AgenteModelo("probabilidadechance", "probabilidadechance", "imitacao", "yes", "nada", "imitacao", 0.75, true);
		hashAgentes.put(1, agente1);
		
		AgenteModelo agente2 = new AgenteModelo("probabilidadechance", "probabilidadechance", "imitacao", "no", "nada", "imitacao", 0.75, true);
		hashAgentes.put(2, agente2);
		
		AgenteModelo agente3 = new AgenteModelo("chancesucesso", "chancesucesso", "imitacao", "yes", "nada", "imitacao", 0.75, true);
		hashAgentes.put(3, agente3);
		
		AgenteModelo agente4 = new AgenteModelo("chancesucesso", "chancesucesso", "imitacao", "no", "nada", "imitacao", 0.75, true);
		hashAgentes.put(4, agente4);
		
		
		AgenteModelo agente11 = new AgenteModelo("probabilidadechance", "chancesucesso", "imitacao", "yes", "nada", "imitacao", 0.75, true);
		hashAgentes.put(5, agente11);
		
		AgenteModelo agente12 = new AgenteModelo("chancesucesso", "maioria", "imitacao", "yes", "nada", "imitacao", 0.75, true);
		hashAgentes.put(6, agente12);
		
			
		AgenteModelo agente15 = new AgenteModelo("chancesucesso", "probabilidadesorteio", "imitacao", "yes", "nada", "imitacao", 0.75, true);
		hashAgentes.put(7, agente15);
		
		
		AgenteModelo agente18 = new AgenteModelo("chancesucesso", "probabilidadechance", "imitacao", "yes", "nada", "imitacao", 0.75, true);
		hashAgentes.put(8, agente18);
		
		AgenteModelo agente19 = new AgenteModelo("maioria", "probabilidadechance", "imitacao", "yes", "nada", "imitacao", 0.75, true);
		hashAgentes.put(9, agente19);
		
		AgenteModelo agente20 = new AgenteModelo("probabilidadesorteio", "probabilidadechance", "imitacao", "yes", "nada", "imitacao", 0.75, true);
		hashAgentes.put(10, agente20);
		
		
		return hashAgentes;
		
	}
	
	public HashMap<Integer, AgenteModelo> retornaHashDeAgentesParaNaoAprender(){
		HashMap<Integer, AgenteModelo> hashAgentes = new HashMap<Integer, AgenteModelo>();
		AgenteModelo agente1 = new AgenteModelo("probabilidadechance", "probabilidadechance", "Nenhum", "yes", "nada", "imitacao", 0.75, false);
		hashAgentes.put(1, agente1);
		
		AgenteModelo agente2 = new AgenteModelo("probabilidadechance", "probabilidadechance", "Nenhum", "no", "nada", "imitacao", 0.75, false);
		hashAgentes.put(2, agente2);
		
		AgenteModelo agente3 = new AgenteModelo("chancesucesso", "chancesucesso", "Nenhum", "yes", "nada", "imitacao", 0.75, false);
		hashAgentes.put(3, agente3);
		
		AgenteModelo agente4 = new AgenteModelo("chancesucesso", "chancesucesso", "Nenhum", "no", "nada", "imitacao", 0.75, false);
		hashAgentes.put(4, agente4);
		
		
		AgenteModelo agente11 = new AgenteModelo("probabilidadechance", "chancesucesso", "Nenhum", "yes", "nada", "imitacao", 0.75, false);
		hashAgentes.put(5, agente11);
		
		AgenteModelo agente12 = new AgenteModelo("chancesucesso", "maioria", "Nenhum", "yes", "nada", "imitacao", 0.75, false);
		hashAgentes.put(6, agente12);
		
			
		AgenteModelo agente15 = new AgenteModelo("chancesucesso", "probabilidadesorteio", "Nenhum", "yes", "nada", "imitacao", 0.75, false);
		hashAgentes.put(7, agente15);
		
		
		AgenteModelo agente18 = new AgenteModelo("chancesucesso", "probabilidadechance", "Nenhum", "yes", "nada", "imitacao", 0.75, false);
		hashAgentes.put(8, agente18);
		
		AgenteModelo agente19 = new AgenteModelo("maioria", "probabilidadechance", "Nenhum", "yes", "nada", "imitacao", 0.75, false);
		hashAgentes.put(9, agente19);
		
		AgenteModelo agente20 = new AgenteModelo("probabilidadesorteio", "probabilidadechance", "Nenhum", "yes", "nada", "imitacao", 0.75, false);
		hashAgentes.put(10, agente20);
		
		
		return hashAgentes;
		
	}

	
}
