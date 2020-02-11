package novosTestes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;


import gamer.Auto.ControlaPartidaAuto;
import treinamentoModelo.AgenteModelo;
import treinamentoModelo.SetCbrModelo;

public class SetTreinamentoAtivo {



	
public static void main(String[] args) {
	int numeroExecucoes = 100;
	
	Agentes10MelhoresAtivo agentes = new Agentes10MelhoresAtivo();
	HashMap<Integer, AgenteModelo> agentesParaAprender = agentes.retornaHashDeAgentesParaAprender();
	HashMap<Integer, AgenteModelo> agentesParaNaoAprender = agentes.retornaHashDeAgentesParaNaoAprender();
	Random randomAprender = new Random();
	Random randomNaoAprender = new Random();
	
	 for(int i= 0; i< numeroExecucoes; i++) {
	      	ControlaPartidaAuto.controlaPartidaAuto = null;
	      	int agenteSorteadoAprender = randomAprender.nextInt(10) + 1;
	      	int agenteSorteadoNaoAprender = randomNaoAprender.nextInt(10) + 1;
	      	AgenteModelo agenteUm = agentesParaAprender.get(agenteSorteadoAprender);
	      	AgenteModelo agenteDois = agentesParaNaoAprender.get(agenteSorteadoNaoAprender);
	      	SetCbrModelo setTreinamento = new SetCbrModelo(agenteUm, agenteDois);
	      	new ControllerAutomaticoNew().Treinar(setTreinamento, 1);
	      }
}
}
