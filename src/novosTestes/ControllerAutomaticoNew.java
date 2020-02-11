package novosTestes;

import gamer.Auto.ControlaPartidaAuto;
import gamer.Auto.ControlaRodadaAuto;
import model.Match;
import model.Player;
import treinamentoModelo.SetCbrModelo;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControllerAutomaticoNew {

	static int numeroDePartidasSolicitadas = 0;
	static int contadorPartidas;
	String Log = "";
	String LogPlacar = "";
	ControlaPartidaAuto controlaPartida = ControlaPartidaAuto.getInstacia();
	ControlaRodadaAuto controlaRodadaAuto;

	String TipoReuso1;
	String TipoReusoIntra1;
	String TipoAprendizagem1;
	String UsarCluster1;

	String TipoBase1;
	double threshold1;

	String TipoReuso2;
	String TipoReusoIntra2;
	String TipoAprendizagem2;
	String UsarCluster2;

	String TipoBase2;
	double threshold2;

	boolean recalcularCentroidesUm;
	boolean recalcularCentroidesDois;

	private String inicioPartida;
	private String finalPartida;


	public void Treinar(SetCbrModelo set, int NumeroPartidas) {

		salvarSaidaSystemOutPrintParaArquivo();

		controlaPartida = ControlaPartidaAuto.mataInstacia();
		contadorPartidas = 0;
		TipoReuso1 = set.getTipoReusoExtraCluster1();
		TipoReusoIntra1 = set.getTipoReusoIntraCluster1();
		TipoAprendizagem1 = set.getTipoAprendizagem1();

		TipoBase1 = set.getTipoBase1();
		UsarCluster1 = set.getUsarCluster1();
		threshold1 = set.getThreshold1();

		TipoReuso2 = set.getTipoReusoExtraCluster2();
		TipoReusoIntra2 = set.getTipoReusoIntraCluster2();
		TipoAprendizagem2 = set.getTipoAprendizagem2();

		TipoBase2 = set.getTipoBase2();
		UsarCluster2= set.getUsarCluster2();
		threshold2 = set.getThreshold2();

		recalcularCentroidesUm = set.getRecalculaCentroideAgenteUm();
		recalcularCentroidesDois = set.getRecalculaCentroideAgenteDois();

		numeroDePartidasSolicitadas = (NumeroPartidas == 0) ? 1 : NumeroPartidas;


		//while (contadorPartidas <= numeroDePartidasSolicitadas) {
			controlaPartida.setCBR(TipoReuso1, TipoAprendizagem1, TipoReuso2, TipoAprendizagem2, TipoBase1, TipoBase2, UsarCluster1, UsarCluster2, threshold1, threshold2, TipoReusoIntra1, TipoReusoIntra2,
					recalcularCentroidesUm, recalcularCentroidesDois);
			controlaPartida.setaUltimoId(TipoReuso1, TipoAprendizagem1,  TipoBase1, TipoReuso2, TipoAprendizagem2,
					TipoBase2,UsarCluster1, UsarCluster2, TipoReusoIntra1, TipoReusoIntra2);

			contadorPartidas++;
			novaPartida(contadorPartidas);
			controlaPartida.fecharBases();

		//}

	}

	public void novaPartida(int PartidaNumber) {
		inicioPartida = getDateTime();
		controlaPartida.novaPartida(PartidaNumber);
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("START_MATCH " + "Nr. " + controlaPartida.getPartidaNumber() + " - " + getDateTime());
		IniciaRodada();
	}

	private void IniciaRodada() {
		System.out.println("*********************************************");
		System.out.println("START_HAND " + "Nr. " + (controlaPartida.getRodadaNumber() + 1) + " - Hand Player: " + (controlaPartida.getQuemEhMao() == 1 ? 2 : 1));
		controlaRodadaAuto = new ControlaRodadaAuto();
		controlaPartida.novaRodada();
		//Log += controlaRodadaAuto.getCartas();
		joga(controlaPartida.getQuemEhMao());

	}

	private void joga(int QuemJoga) {
		String ultimaJogadaChamada = "";
		if (!controlaRodadaAuto.getListaJogadasChamadas().isEmpty()) {
			ultimaJogadaChamada = controlaRodadaAuto.getListaJogadasChamadas().get(controlaRodadaAuto.getListaJogadasChamadas().size() - 1).getJogadaChamada();
		}

		if (controlaRodadaAuto.getPodeChamarPontosAinda()) {
			String PontoChamado = controlaRodadaAuto.ChamarPonto(QuemJoga);
			if (!PontoChamado.equals("")) {
				perguntaOponenteAceitarPontos(PontoChamado, QuemJoga);
			}
		}
		// verifica se vai ao baralho
		if (controlaRodadaAuto.IrAoBaralho(QuemJoga)) {

			controlaRodadaAuto.quemFoiBaralho(QuemJoga, controlaRodadaAuto.getQualEhAmao());
			FimRodada();
		} else {

			if (!ultimaJogadaChamada.equals("ValeQuatro")) {

				if (controlaRodadaAuto.quemPodeChamarJogoAgora() == 0
						|| controlaRodadaAuto.quemPodeChamarJogoAgora() == QuemJoga) {
					String jogoQueSeraChamado = controlaRodadaAuto.chamarJogada(QuemJoga);

					if (!jogoQueSeraChamado.equals("")) {
						perguntaOponenteAceitarJogo(jogoQueSeraChamado, QuemJoga);
					}
				}
			}

			if (controlaPartida.isTemMaisRodada()) {
				String carta = controlaRodadaAuto.jogarAgente(QuemJoga, controlaRodadaAuto.getQualEhAmao());

				verificaQuemJoga();
			}

		}
	}

	public void perguntaOponenteAceitarPontos(String PontoChamado, int QuemPediu) {

		int Oponente = QuemOponente(QuemPediu);
		boolean oponenteAceitouPontos = controlaRodadaAuto.AceitarPontos(PontoChamado, Oponente);

		if (oponenteAceitouPontos) {
			boolean aindaTemPontos = controlaRodadaAuto.temPontosParaSerChamados();
			if (aindaTemPontos) {
				String AumentarPontos = controlaRodadaAuto.ChamarPonto(Oponente);
				if (!AumentarPontos.equals("")) {
					perguntaOponenteAceitarPontos(AumentarPontos, Oponente);
				}
			}
		} else {
			controlaRodadaAuto.naoAceitouPontos(PontoChamado, Oponente);
		}
		//controlaRodadaAuto.pontuaQuemGanhouPontos();
	}

	private void perguntaOponenteAceitarJogo(String jogoQueSeraChamado, int QuemPediu) {

		int Oponente = QuemOponente(QuemPediu);
		String ultimaJogadaChamada = "";

		boolean OponenteAceitou = controlaRodadaAuto.AceitarJogada(jogoQueSeraChamado, Oponente);

		if (OponenteAceitou) {
			// se aceitou verifica pode aumentar ponto
			Log += "\n" + Oponente + "  aceitou jogo ( " + jogoQueSeraChamado + " )";
			//System.out.println(Oponente + "  aceitou jogo ( " + jogoQueSeraChamado + " )");

			if (!controlaRodadaAuto.getListaJogadasChamadas().isEmpty()) {
				ultimaJogadaChamada = controlaRodadaAuto.getListaJogadasChamadas().get(controlaRodadaAuto.getListaJogadasChamadas().size() - 1).getJogadaChamada();
			}



			if (controlaRodadaAuto.quemPodeChamarJogoAgora() == Oponente && controlaRodadaAuto.temJogadaAinda() &&
					!ultimaJogadaChamada.equals("ValeQuatro")) {

				// Ja que pode chamar jogo verifica se vai aumentar
				String jogoQueSeraAumentado = controlaRodadaAuto.chamarJogada(Oponente);
				Log += "\n" + Oponente + "  aumentou aceitou jogo para ( " + jogoQueSeraAumentado + " )";
				//System.out.println(Oponente + "  aumentou aceitou jogo para ( " + jogoQueSeraAumentado + " )");

				if (!jogoQueSeraAumentado.equals("")) {
					// pergunta se o oponente aceita o aumento de jogo
					perguntaOponenteAceitarJogo(jogoQueSeraAumentado, Oponente);
				}
			}
		} else {
			Log += "\n" + Oponente + "  negou jogo ( " + jogoQueSeraChamado + " )  na mao "
					+ controlaRodadaAuto.getQualEhAmao();
			//System.out.println(Oponente + "  negou jogo ( " + jogoQueSeraChamado + " )  na rodada " + controlaRodadaAuto.getQualEhAmao());
			controlaRodadaAuto.naoAceitouJogada(jogoQueSeraChamado, Oponente);
			verificaQuemJoga();
		}

	}

	private void verificaQuemJoga() {

		if (controlaRodadaAuto.aindaTemJogoNaRodada()) {
			joga(controlaRodadaAuto.quemJogaAgora());
		} else if (controlaPartida.isTemMaisRodada() && !controlaRodadaAuto.aindaTemJogoNaRodada()) {
			FimRodada();
		} else {
			FimPartida();
		}
	}

	private void FimPartida() {
		//System.out.println("Fim Partida");
		controlaRodadaAuto.pontuaQuemGanhouPontos();
		controlaRodadaAuto.pontuaQuemGanhouArodada();
		String pontos = controlaRodadaAuto.pontosRodada();
		//System.out.println("=====END HAND=====");
		//System.out.println("=====END MATCH=====");
		Match match = new Match();
		Player player1 = ControlaPartidaAuto.getInstacia().getPlayer1();
		Player player2 = ControlaPartidaAuto.getInstacia().getPlayer2();
		int pontosAgente1 = ControlaPartidaAuto.getInstacia().getPontosAgente1();
		int pontosAgente2 = ControlaPartidaAuto.getInstacia().getPontosAgente2();
		match.setPlayer1(player1);
		match.setPlayer2(player2);
		match.setPointsPlayer1(pontosAgente1);
		match.setPointsPlayer2(pontosAgente2);
		match.setWinner(pontosAgente1 > pontosAgente2 ? player1 : player2);
		ControlaPartidaAuto.getInstacia().saveMatch(match);
		Log += "\n" + pontos + "\n\n";
		LogPlacar += "\n" + pontos + "\n\n";
		controlaRodadaAuto.criaDescriptionParaPersistir();
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("FINISH_MATCH " + "Nr. " + controlaPartida.getPartidaNumber() + " - " + getDateTime());
		//System.out.println("Placar   " + controlaPartida.getPontosAgente1() + " X " + controlaPartida.getPontosAgente2());
	}

	private void FimRodada() {

		//System.out.println("Fim Rodada");
		controlaRodadaAuto.pontuaQuemGanhouPontos();
		controlaRodadaAuto.pontuaQuemGanhouArodada();
		String pontos = controlaRodadaAuto.pontosRodada();
		//System.out.println("=====END HAND=====");

		controlaRodadaAuto.criaDescriptionParaPersistir();

		controlaRodadaAuto.atualizaScoutBlefesRealizadosAgente();
		controlaRodadaAuto.atualizaScoutBlefesPlotadosOpponente();

		if (!controlaPartida.isTemMaisRodada()) {
			Match match = new Match();
			Player player1 = ControlaPartidaAuto.getInstacia().getPlayer1();
			Player player2 = ControlaPartidaAuto.getInstacia().getPlayer2();
			int pontosAgente1 = ControlaPartidaAuto.getInstacia().getPontosAgente1();
			int pontosAgente2 = ControlaPartidaAuto.getInstacia().getPontosAgente2();
			match.setPlayer1(player1);
			match.setPlayer2(player2);
			match.setPointsPlayer1(pontosAgente1);
			match.setPointsPlayer2(pontosAgente2);
			match.setWinner(pontosAgente1 > pontosAgente2 ? player1 : player2);
			ControlaPartidaAuto.getInstacia().saveMatch(match);
		} else {
			IniciaRodada();
		}

	}

	private void salvarSaidaSystemOutPrintParaArquivo() {
		File arquivo;

		try {

			arquivo = new File("Logs_Treino_Ativo.txt");
			//arquivo.createNewFile();

			System.setOut(new PrintStream(new FileOutputStream(arquivo, true)));

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void SalvaLog() {

		try {
			File arquivo = new File("Logs_Partida_"+ "Extra_" + TipoReuso1 + "_Intra_" + TipoReusoIntra1  + " Cluster "+ UsarCluster1 +" Vs "+
					"Extra_" + TipoReuso2 + "_Intra_" + TipoReusoIntra2 +  " cluster "+ UsarCluster2 +".txt");
			FileWriter fw = new FileWriter(arquivo, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(Log);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SalvaLogPlacar() {
//
//		try {
//			File arquivo = new File("LogPlacar.txt");
//			FileWriter fw = new FileWriter(arquivo, true);
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.append(LogPlacar);
//			bw.flush();
//			bw.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public int QuemOponente(int Atual) {
		int AgenteOponente;
		if (Atual == 1)
			AgenteOponente = 2;
		else
			AgenteOponente = 1;
		return AgenteOponente;
	}

	public void play() {
		String url_open = "https://www.youtube.com/watch?v=2ccaHpy5Ewo";
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getDiffDateTime() {

		String diffTime = "";

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = dateFormat.parse(inicioPartida);
			d2 = dateFormat.parse(finalPartida);

			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			diffTime = String.format ("%02d", diffMinutes) + ":" + String.format ("%02d", diffSeconds);

			//System.out.print(diffDays + " days, ");
			//System.out.print(diffHours + " hours, ");
			//System.out.print(diffMinutes + " minutes, ");
			//System.out.print(diffSeconds + " seconds.");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return diffTime;
	}

	public ControlaRodadaAuto getControlaRodadaAuto() {
		return controlaRodadaAuto;
	}

	public void setControlaRodadaAuto(ControlaRodadaAuto controlaRodadaAuto) {
		this.controlaRodadaAuto = controlaRodadaAuto;
	}
}
