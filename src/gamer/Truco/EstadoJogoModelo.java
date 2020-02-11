package gamer.Truco;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cbr.AtualizaConsultas.AuxiliaConsultas.JogadasAceitasModelo;
import cbr.AtualizaConsultas.AuxiliaConsultas.JogadasChamadasModelo;
import cbr.AtualizaConsultas.AuxiliaConsultas.JogadasQuePodemSerAceitasModelo;
import gamer.ui.utils.Action;

public class EstadoJogoModelo {

	private int quemJogaAgora;
	private int emQueRodadaEsta;
	private int pontuacaoAgente;
	private int pontuacaoHumano;
	private int qualEhAmao;
	private boolean AgenteTemFlor;
	private boolean humanoTemFlor;

	// cada mão tem duas "jogadas"
	private int contadorJogadas;

	// listas jogadas
	private List<String> listaJogadasQuePodemSerChamadas = new ArrayList<String>();
	private List<JogadasChamadasModelo> listaJogadasChamadas = new ArrayList<JogadasChamadasModelo>();
	private List<JogadasQuePodemSerAceitasModelo> listaJogadasQuePodemSerAceitas = new ArrayList<JogadasQuePodemSerAceitasModelo>();
	private List<JogadasAceitasModelo> listaJogadasAceitas = new ArrayList<JogadasAceitasModelo>();
	// listas pontos
	private List<String> listaPontosQuePodemSerChamados = new ArrayList<String>();
	private List<JogadasChamadasModelo> listaPontosChamados = new ArrayList<JogadasChamadasModelo>();
	private List<JogadasQuePodemSerAceitasModelo> listaPontosQuePodemSerAceitos = new ArrayList<JogadasQuePodemSerAceitasModelo>();
	private List<JogadasAceitasModelo> listaPontosAceitos = new ArrayList<JogadasAceitasModelo>();

	LinkedList<Action> envidoHistory = new LinkedList<>();
	LinkedList<Action> trucoHistory = new LinkedList<>();
	LinkedList<Action> florHistory = new LinkedList<>();
	LinkedList<Action> cardHistory = new LinkedList<>();

	public List<String> getListaJogadasQuePodemSerChamadas() {
		return listaJogadasQuePodemSerChamadas;
	}

	public void setListaJogadasQuePodemSerChamadas(List<String> listaJogadasQuePodemSerChamadas) {
		this.listaJogadasQuePodemSerChamadas = listaJogadasQuePodemSerChamadas;
	}

	public List<JogadasQuePodemSerAceitasModelo> getListaJogadasQuePodemSerAceitas() {
		return listaJogadasQuePodemSerAceitas;
	}

	public List<String> getListaPontosQuePodemSerChamados() {
		return listaPontosQuePodemSerChamados;
	}

	public void setListaPontosQuePodemSerChamados(List<String> listaPontosQuePodemSerChamados) {
		this.listaPontosQuePodemSerChamados = listaPontosQuePodemSerChamados;
	}

	public List<JogadasChamadasModelo> getListaPontosChamados() {
		return listaPontosChamados;
	}

	public int getQuemJogaAgora() {
		return quemJogaAgora;
	}

	public void setQuemJogaAgora(int quemJogaAgora) {
		this.quemJogaAgora = quemJogaAgora;
	}

	public int getEmQueRodadaEsta() {
		return emQueRodadaEsta;
	}

	public void setEmQueRodadaEsta(int emQueRodadaEsta) {
		this.emQueRodadaEsta = emQueRodadaEsta;
	}

	public int getPontuacaoAgente() {
		return pontuacaoAgente;
	}

	public void setPontuacaoAgente(int pontuacaoAgente) {
		this.pontuacaoAgente = pontuacaoAgente;
	}

	public boolean isAgenteTemFlor() {
		return AgenteTemFlor;
	}

	public void setAgenteTemFlor(boolean AgenteTemFlor) {
		this.AgenteTemFlor = AgenteTemFlor;
	}

	public boolean isHumanoTemFlor() {
		return humanoTemFlor;
	}

	public void setHumanoTemFlor(boolean humanoTemFlor) {
		this.humanoTemFlor = humanoTemFlor;
	}

	public int getPontuacaoHumano() {
		return pontuacaoHumano;
	}

	public void setPontuacaoHumano(int pontuacaoHumano) {
		this.pontuacaoHumano = pontuacaoHumano;
	}

	public int getQualEhAmao() {
		return qualEhAmao;
	}

	public void setQualEhAmao(int qualEhAmao) {
		this.qualEhAmao = qualEhAmao;
	}

	public LinkedList<Action> getEnvidoHistory() {
		return envidoHistory;
	}

	public void setEnvidoHistory(LinkedList<Action> envidoHistory) {
		this.envidoHistory = envidoHistory;
	}

	public LinkedList<Action> getTrucoHistory() {
		return trucoHistory;
	}

	public void setTrucoHistory(LinkedList<Action> trucoHistory) {
		this.trucoHistory = trucoHistory;
	}

	public LinkedList<Action> getFlorHistory() {
		return florHistory;
	}

	public void setFlorHistory(LinkedList<Action> florHistory) {
		this.florHistory = florHistory;
	}

	public LinkedList<Action> getCardHistory() {
		return cardHistory;
	}

	public void setCardHistory(LinkedList<Action> cardHistory) {
		this.cardHistory = cardHistory;
	}

	public int retornaRodadaDaMao() {
		if (contadorJogadas == 0 || contadorJogadas == 1 || contadorJogadas == 2)
			return 1;
		if (contadorJogadas == 3 || contadorJogadas == 4)
			return 2;
		if (contadorJogadas == 3 || contadorJogadas == 4)
			return 3;
		return 0;
	}

	public List<String> listaJogadasQuePodemSerChamadas() {

		if (listaJogadasChamadas.isEmpty() && listaJogadasQuePodemSerAceitas.isEmpty()) {

			if (!listaJogadasQuePodemSerChamadas.contains("Truco"))
				listaJogadasQuePodemSerChamadas.add("Truco");
			if (!listaJogadasQuePodemSerChamadas.contains("Ir ao baralho"))
				listaJogadasQuePodemSerChamadas.add("Ir ao baralho");
		} else if (!listaJogadasChamadas.isEmpty() && qualEhAmao == 1) {// verificar qual foi a última jogada que foi
			removeJogadasJaChamadas();
			verificaSeJogadaJaFoiChamadaEadicionaProxima();

		} else if (qualEhAmao != 1) {

			removeJogadasJaChamadas();
			verificaSeJogadaJaFoiChamadaEadicionaProxima();

		}

		return listaJogadasQuePodemSerChamadas;

	}

	public List<String> listaPontosQuePodemSerChamados() {

//		//System.out.println(listaPontosChamados);
		if (listaPontosChamados.isEmpty() && listaPontosQuePodemSerChamados.isEmpty() && retornaRodadaDaMao() == 1) {
//			//System.out.println("Lista tava vazia, add pontos");
			listaPontosQuePodemSerChamados.add("Envido");
			listaPontosQuePodemSerChamados.add("RealEnvido");
			listaPontosQuePodemSerChamados.add("FaltaEnvido");
			if (humanoTemFlor || AgenteTemFlor) {
//				//System.out.println("Flor disponivel adicionando");
				listaPontosQuePodemSerChamados.add("Flor");
			}
		}

		if (!listaPontosChamados.isEmpty()) {
			removePontosJaChamados();
		}
		if (qualEhAmao == 2 || qualEhAmao == 3) {

			removePontosQueSoPodemSerChamadosNaPrimeiraRodadaDaMao();
		}

//		//System.out.println(listaPontosQuePodemSerChamados);
		return listaPontosQuePodemSerChamados;

	}

	private void removePontosQueSoPodemSerChamadosNaPrimeiraRodadaDaMao() {
		for (int i = 0; i < listaPontosQuePodemSerChamados.size(); i++) {
			if (listaPontosQuePodemSerChamados.get(i).equals("Envido"))
				listaPontosQuePodemSerChamados.remove(i);
			if (listaPontosQuePodemSerChamados.get(i).equals("RealEnvido"))
				listaPontosQuePodemSerChamados.remove(i);
			if (listaPontosQuePodemSerChamados.get(i).equals("FaltaEnvido"))
				listaPontosQuePodemSerChamados.remove(i);
			if (listaPontosQuePodemSerChamados.get(i).equals("Flor"))
				listaPontosQuePodemSerChamados.remove(i);
		}
	}

	private void verificaSeJogadaJaFoiChamadaEadicionaProxima() {
		for (int i = 0; i < listaJogadasChamadas.size(); i++) {
			// verifica se foi chamado Truco e disponibiliza o Retruco para ser chamado.
			if (listaJogadasChamadas.get(i).getJogadaChamada().contains("Truco")
					&& !listaJogadasChamadas.get(i).getJogadaChamada().contains("ReTruco"))
				listaJogadasQuePodemSerChamadas.add("ReTruco");
			// verifica se foi chamado ReTruco e disponibiliza o ValeQuatro para ser
			// chamado.
			if (listaJogadasChamadas.get(i).getJogadaChamada().contains("ReTruco")
					&& !listaJogadasChamadas.get(i).getJogadaChamada().contains("ValeQuatro"))
				listaJogadasQuePodemSerChamadas.add("ValeQuatro");

		}
	}

	private void verificaSePontoJaFoiChamadoEadicionaProximo() {
		for (int i = 0; i < listaPontosChamados.size(); i++) {
			// verifica se foi chamado envido ou real envido e disponibiliza o falta envido.
			if (listaPontosChamados.get(i).getJogadaChamada().contains("Envido")
					|| listaPontosChamados.get(i).getJogadaChamada().contains("RealEnvido")
							&& !listaPontosChamados.get(i).getJogadaChamada().contains("FaltaEnvido")
							&& !pontoEstaNaListaParaSerAceito("FaltaEnvido"))
				listaPontosQuePodemSerChamados.add("FaltaEnvido");

		}
	}

	public void removeJogadasJaChamadas() {
		for (JogadasChamadasModelo jogadasChamadas : listaJogadasChamadas) {
			for (int i = 0; i < listaJogadasQuePodemSerAceitas.size(); i++) {
				if (jogadasChamadas.getJogadaChamada().equalsIgnoreCase(listaJogadasQuePodemSerChamadas.get(i)))
					listaJogadasQuePodemSerChamadas.remove(i);
			}

		}
	}

	public void umRemoveJogadasJaChamadasQueFuncione() {
		Iterator it = listaJogadasQuePodemSerChamadas.iterator();
		while (it.hasNext()) {
			String jogada = (String) it.next();
			for (JogadasChamadasModelo jogadasChamadas : listaJogadasChamadas) {
				if (jogadasChamadas.getJogadaChamada().equalsIgnoreCase(jogada)) {
					it.remove();
				}
			}

		}
	}

	private boolean pontoEstaNaListaParaSerAceito(String pontoQueVaiSerAdicionado) {
		boolean jaExiste = false;
		for (JogadasQuePodemSerAceitasModelo ponto : listaPontosQuePodemSerAceitos) {
			if (ponto.getJogadaQuePodeSerAceita().equalsIgnoreCase(pontoQueVaiSerAdicionado))
				;
			jaExiste = true;
		}
		return jaExiste;
	}

	private void removePontosJaChamados() {
//		//System.out.println("removePontosJaChamados");

		for (JogadasChamadasModelo pontosChamados : listaPontosChamados) {
			if (pontosChamados.getJogadaChamada().equalsIgnoreCase("Flor")) {
//				//System.out.println("chamado flor");
				listaPontosQuePodemSerChamados.remove("Envido");
				listaPontosQuePodemSerChamados.remove("RealEnvido");
				listaPontosQuePodemSerChamados.remove("FaltaEnvido");
				listaPontosQuePodemSerChamados.remove("Flor");
				if (humanoTemFlor && AgenteTemFlor) {
					if (!listaPontosQuePodemSerChamados.contains("ContraFlor"))
						listaPontosQuePodemSerChamados.add("ContraFlor");
					if (!listaPontosQuePodemSerChamados.contains("ContraFlorResto"))
						listaPontosQuePodemSerChamados.add("ContraFlorResto");
				}
			} else if (pontosChamados.getJogadaChamada().equalsIgnoreCase("FaltaEnvido")) {
//				//System.out.println("chamado falta envido   removendo real e envido");
				listaPontosQuePodemSerChamados.remove("Envido");
				listaPontosQuePodemSerChamados.remove("RealEnvido");
				listaPontosQuePodemSerChamados.remove("FaltaEnvido");
			} else if (pontosChamados.getJogadaChamada().equalsIgnoreCase("RealEnvido")) {
//				//System.out.println("chamado Real envido , removendo envido");
				listaPontosQuePodemSerChamados.remove("RealEnvido");
				listaPontosQuePodemSerChamados.remove("Envido");
			} else if (pontosChamados.getJogadaChamada().equalsIgnoreCase("Envido")) {
				listaPontosQuePodemSerChamados.remove("Envido");
			} else if (pontosChamados.getJogadaChamada().equalsIgnoreCase("ContraFlor")) {
				listaPontosQuePodemSerChamados.remove("ContraFlor");
			} else if (pontosChamados.getJogadaChamada().equalsIgnoreCase("ContraFlorResto")) {
				listaPontosQuePodemSerChamados.remove("ContraFlor");
				listaPontosQuePodemSerChamados.remove("ContraFlorResto");
			}

		}
	}

	public int getContadorJogadas() {
		return contadorJogadas;
	}

	public void setContadorJogadas(int contadorJogadas) {
		this.contadorJogadas = contadorJogadas;
	}

	public List<JogadasChamadasModelo> getListaJogadasChamadas() {
		return listaJogadasChamadas;
	}

	public void setListaJogadasChamadas(List<JogadasChamadasModelo> listaJogadasChamadasModelo) {
		this.listaJogadasChamadas = listaJogadasChamadasModelo;
	}

	// retorna as jogadas que podem ser aceitas por jogador
	public List<String> getListaJogadasQuePodemSerAceitas(int jogador) {
		List<String> listaJogadasQuePodemSerAceitasPorJogador = new ArrayList<String>();
		for (int i = 0; i < listaJogadasQuePodemSerAceitas.size(); i++) {
			if (listaJogadasQuePodemSerAceitas.get(i).getQuemPodeAceitar() == jogador)
				listaJogadasQuePodemSerAceitasPorJogador
						.add(listaJogadasQuePodemSerAceitas.get(i).getJogadaQuePodeSerAceita());
		}
		if (listaJogadasQuePodemSerAceitasPorJogador.isEmpty())
			listaJogadasQuePodemSerAceitasPorJogador.add("Não existem jogadas para serem aceitas no momento.");

		if (!listaJogadasQuePodemSerAceitasPorJogador.isEmpty())
			listaJogadasQuePodemSerAceitasPorJogador.add("Não Aceitar");

		return listaJogadasQuePodemSerAceitasPorJogador;
	}

	private void preencheListaPontosQuePodemSerAceitosPorJogador(int jogadorQuePodeAceitar) {
		int jogadorQueChamou = 0;
		// verifica quem que chamou com base de quem tem que aceitar
		if (jogadorQuePodeAceitar == 1)
			jogadorQueChamou = 2;
		if (jogadorQuePodeAceitar == 2)
			jogadorQueChamou = 1;
		// pega o ultimo ponto chamado e adiciona na lista
		JogadasChamadasModelo pontoQueFoiChamado = listaPontosChamados.get(listaPontosChamados.size() - 1);
		// verifica se quem chamou foi o outro jogador e habilita
		if (pontoQueFoiChamado.getQuemChamou() == jogadorQueChamou) {
			JogadasQuePodemSerAceitasModelo pontosQuePodemSerAceitos = new JogadasQuePodemSerAceitasModelo();
			pontosQuePodemSerAceitos.setQuemPodeAceitar(jogadorQuePodeAceitar);
			pontosQuePodemSerAceitos.setJogadaQuePodeSerAceita(pontoQueFoiChamado.getJogadaChamada());
			listaPontosQuePodemSerAceitos.add(pontosQuePodemSerAceitos);
		}

	}

	// retorna pontos que podem ser aceitos por jogador
	public List<String> getListaPontosQuePodemSerAceitos(int jogador) {
		preencheListaPontosQuePodemSerAceitosPorJogador(jogador);
		List<String> listaPontosQuePodemSerAceitosPorJogador = new ArrayList<String>();
		for (int i = 0; i < listaPontosQuePodemSerAceitos.size(); i++) {
			if (listaPontosQuePodemSerAceitos.get(i).getQuemPodeAceitar() == jogador)
				listaPontosQuePodemSerAceitosPorJogador
						.add(listaPontosQuePodemSerAceitos.get(i).getJogadaQuePodeSerAceita());
		}
		if (listaPontosQuePodemSerAceitosPorJogador.isEmpty())
			listaPontosQuePodemSerAceitosPorJogador.add("Não existem pontos para serem aceitas no momento.");

		if (!listaPontosQuePodemSerAceitosPorJogador.isEmpty())
			listaPontosQuePodemSerAceitosPorJogador.add("Não Aceitar");
		return listaPontosQuePodemSerAceitosPorJogador;
	}

	public List<JogadasQuePodemSerAceitasModelo> getListaPontosQuePodemSerAceitos() {
		return listaPontosQuePodemSerAceitos;
	}

	public void setListaPontosQuePodemSerAceitos(List<JogadasQuePodemSerAceitasModelo> listaPontosQuePodemSerAceitos) {
		this.listaPontosQuePodemSerAceitos = listaPontosQuePodemSerAceitos;
	}

	public void setListaJogadasQuePodemSerAceitas(
			List<JogadasQuePodemSerAceitasModelo> listaJogadasQuePodemSerAceitas) {
		this.listaJogadasQuePodemSerAceitas = listaJogadasQuePodemSerAceitas;
	}

	public List<JogadasAceitasModelo> getListaPontosAceitosModelo() {
		return listaPontosAceitos;
	}

	public void setListaPontosAceitosModelo(List<JogadasAceitasModelo> listaPontosAceitosModelo) {
		this.listaPontosAceitos = listaPontosAceitosModelo;
	}

	public void setListaPontosChamados(List<JogadasChamadasModelo> listaPontosChamados) {
		this.listaPontosChamados = listaPontosChamados;
	}

	public List<JogadasAceitasModelo> getListaJogadasAceitas() {
		return listaJogadasAceitas;
	}

	public void setListaJogadasAceitas(List<JogadasAceitasModelo> listaJogadasAceitas) {
		this.listaJogadasAceitas = listaJogadasAceitas;
	}

	public List<JogadasAceitasModelo> getListaPontosAceitos() {
		return listaPontosAceitos;
	}

	public void setListaPontosAceitos(List<JogadasAceitasModelo> listaPontosAceitos) {
		this.listaPontosAceitos = listaPontosAceitos;
	}

	public String toString() {
		return "\n pontuacaoAgente=    " + pontuacaoAgente + "\n pontuacaoHumano=    " + pontuacaoHumano
				+ "\n qualEhAmao=    " + qualEhAmao + "\n AgenteTemFlor=    " + AgenteTemFlor + "\n humanoTemFlor=    "
				+ humanoTemFlor + "\n contadorJogadas=    " + contadorJogadas + "\n listaPontosAceitos=    "
				+ lista_to_String1(listaPontosAceitos) + "\n listaJogadasAceitas=    "
				+ lista_to_String1(listaJogadasAceitas) + "\n listaJogadasChamadas=    "
				+ lista_to_String3(listaJogadasChamadas) + "\n listaPontosChamados=    "
				+ lista_to_String3(listaPontosChamados);
	}

	public String toString2() {
		String Separator = ",";
		String EndLine = "\n";
		return quemJogaAgora + Separator + emQueRodadaEsta + Separator + pontuacaoAgente + Separator + pontuacaoHumano
				+ Separator + qualEhAmao + Separator + AgenteTemFlor + Separator + humanoTemFlor + Separator
				+ contadorJogadas + Separator + lista_to_String1(listaPontosAceitos) + Separator
				+ lista_to_String1(listaJogadasAceitas) + Separator + lista_to_String2(listaJogadasQuePodemSerChamadas)
				+ Separator + lista_to_String2(listaPontosQuePodemSerChamados) + Separator
				+ lista_to_String3(listaJogadasChamadas) + Separator + lista_to_String3(listaPontosChamados) + Separator
				+ lista_to_String4(listaJogadasQuePodemSerAceitas) + Separator
				+ lista_to_String4(listaPontosQuePodemSerAceitos) + EndLine;
	}

	private String lista_to_String4(List<JogadasQuePodemSerAceitasModelo> Lista) {
		String texto = "";
		for (JogadasQuePodemSerAceitasModelo x : Lista) {
			texto = texto + "  Jogada Que pode ser Aceita= " + x.getJogadaQuePodeSerAceita() + "   Quem Pode Aceitar = "
					+ x.getQuemPodeAceitar();
		}
		return texto;
	}

	private String lista_to_String3(List<JogadasChamadasModelo> Lista) {
		String texto = "";
		for (JogadasChamadasModelo x : Lista) {
			texto = texto + "  Jogada Chamada = " + x.getJogadaChamada() + "   Quem Chamou = " + x.getQuemChamou()
					+ "  Na Mao = " + x.getEmQualRodada();
		}
		return texto;
	}

	private String lista_to_String2(List<String> Lista) {
		String texto = "";
		for (String x : Lista) {
			texto = texto + " " + x;
		}
		return texto;
	}

	private String lista_to_String1(List<JogadasAceitasModelo> Lista) {
		String texto = "";
		for (JogadasAceitasModelo x : Lista) {
			texto = texto + " Jogada Aceita = " + x.getJogadaAceita() + "   Quem Aceitou = " + x.getQuemAceitou();
		}
		return texto;
	}

}
