package gamer.ui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gamer.Auto.*;
import model.Match;
import model.Player;

public class ControllerTelaAutomatica {
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
	
	String tecnica1;
	String tecnica2;
	
	boolean autoAjustarK1;
	boolean autoAjustark2;

	private String inicioPartida;
	private String finalPartida;
	


	@FXML
	private Button btnTreinar;

	@FXML
	private TextField txtNumeroPartidas;

	@FXML
	private ComboBox<String> cmbTiposReuso1;

	@FXML
	private ComboBox<String> cmbTiposReuso2;

	@FXML
	private ComboBox<String> cmbTipoReusoIntra1;

	@FXML
	private ComboBox<String> cmbTipoReusoIntra2;

	@FXML
	private ComboBox<String> cmbUsarCluster1;

	@FXML
	private ComboBox<String> cmbUsarCluster2;

	@FXML
	private ComboBox<String> cmbTiposAprendizagem1;

	@FXML
	private ComboBox<String> cmbTiposAprendizagem2;

	@FXML
	private ComboBox<String> cmbTiposBase1;

	@FXML
	private ComboBox<String> cmbTiposBase2;

	@FXML
	private ComboBox<String> cmbThreshold1;

	@FXML
	private ComboBox<String> cmbThreshold2;

	@FXML
	private ComboBox<String> cmbTecnica1;

	@FXML
	private ComboBox<String> cmbTecnica2;


    @FXML
    private ComboBox<String> cmbAutoAJustarK1;

    @FXML
    private ComboBox<String> cmbAutoAjustarK2;
	
	@FXML
	public void initialize() {
		List<String> TiposAprendizagem = new ArrayList<String>();
		TiposAprendizagem.add("Nenhum");
		TiposAprendizagem.add("imitacao");
		TiposAprendizagem.add("Ativo");
		TiposAprendizagem.add("Aleatorio");

		// TiposAprendizagem.add("ACE");

		List<String> TiposReuso = new ArrayList<String>();
		TiposReuso.add("chancesucesso");
		TiposReuso.add("maioria");
		TiposReuso.add("probabilidadesorteio");
		TiposReuso.add("probabilidadechance");

		List<String> TiposBase = new ArrayList<String>();
		TiposBase.add("baseline");
		TiposBase.add("ativo");
		TiposBase.add("imitacao");

		List<String> listaUsarCluster = new ArrayList<String>();
		listaUsarCluster.add("yes");
		listaUsarCluster.add("no");

		List<String> listaThreshold = new ArrayList<String>();
		listaThreshold.add("0.70");
		listaThreshold.add("0.75");
		listaThreshold.add("0.80");
		
		List<String> listaTecnicas = new ArrayList<String>();
		listaTecnicas.add("cbr");
		listaTecnicas.add("markov");
		
		List<String> listaAutoAjustarK = new ArrayList<String>();
		listaAutoAjustarK.add("yes");
		listaAutoAjustarK.add("no");

		cmbTiposReuso1.setItems(FXCollections.observableArrayList(TiposReuso));
		cmbTiposReuso1.getSelectionModel().selectFirst();
		cmbTiposReuso2.setItems(FXCollections.observableArrayList(TiposReuso));
		cmbTiposReuso2.getSelectionModel().selectFirst();

		cmbTipoReusoIntra1.setItems(FXCollections.observableArrayList(TiposReuso));
		cmbTipoReusoIntra1.getSelectionModel().selectFirst();
		cmbTipoReusoIntra2.setItems(FXCollections.observableArrayList(TiposReuso));
		cmbTipoReusoIntra2.getSelectionModel().selectFirst();

		cmbTiposAprendizagem1.setItems(FXCollections.observableArrayList(TiposAprendizagem));
		cmbTiposAprendizagem1.getSelectionModel().selectFirst();
		cmbTiposAprendizagem2.setItems(FXCollections.observableArrayList(TiposAprendizagem));
		cmbTiposAprendizagem2.getSelectionModel().selectFirst();

		cmbTiposBase1.setItems(FXCollections.observableArrayList(TiposBase));
		cmbTiposBase1.getSelectionModel().selectFirst();
		cmbTiposBase2.setItems(FXCollections.observableArrayList(TiposBase));
		cmbTiposBase2.getSelectionModel().selectFirst();

		cmbUsarCluster1.setItems(FXCollections.observableArrayList(listaUsarCluster));
		cmbUsarCluster1.getSelectionModel().selectFirst();
		cmbUsarCluster2.setItems(FXCollections.observableArrayList(listaUsarCluster));
		cmbUsarCluster2.getSelectionModel().selectFirst();

		cmbThreshold1.setItems(FXCollections.observableArrayList(listaThreshold));
		cmbThreshold1.getSelectionModel().selectFirst();
		cmbThreshold2.setItems(FXCollections.observableArrayList(listaThreshold));
		cmbThreshold2.getSelectionModel().selectFirst();

		cmbTecnica1.setItems(FXCollections.observableArrayList(listaTecnicas));
		cmbTecnica1.getSelectionModel().selectFirst();
		
		cmbTecnica2.setItems(FXCollections.observableArrayList(listaTecnicas));
		cmbTecnica2.getSelectionModel().selectFirst();
		
		cmbAutoAJustarK1.setItems(FXCollections.observableArrayList(listaAutoAjustarK));
		cmbAutoAJustarK1.getSelectionModel().selectFirst();
		
		cmbAutoAjustarK2.setItems(FXCollections.observableArrayList(listaAutoAjustarK));
		cmbAutoAjustarK2.getSelectionModel().selectFirst();
	}

	@FXML
	public void Treinar() {
		controlaPartida = ControlaPartidaAuto.mataInstacia();
		contadorPartidas = 1;
		TipoReuso1 = cmbTiposReuso1.getValue();
		TipoReusoIntra1 = cmbTipoReusoIntra1.getValue();
		TipoAprendizagem1 = cmbTiposAprendizagem1.getValue();

		TipoBase1 = cmbTiposBase1.getValue();
		UsarCluster1 = cmbUsarCluster1.getValue();
		threshold1 = Double.parseDouble(cmbThreshold1.getValue());

		TipoReuso2 = cmbTiposReuso2.getValue();
		TipoReusoIntra2 = cmbTipoReusoIntra2.getValue();
		TipoAprendizagem2 = cmbTiposAprendizagem2.getValue();

		TipoBase2 = cmbTiposBase2.getValue();
		UsarCluster2 = cmbUsarCluster2.getValue();
		threshold2 = Double.parseDouble(cmbThreshold2.getValue());

		tecnica1 = cmbTecnica1.getValue();
		tecnica2 = cmbTecnica2.getValue();
		
		autoAjustarK1 = cmbAutoAJustarK1.getValue().equalsIgnoreCase("yes") ? true : false;
		
		autoAjustark2 = cmbAutoAjustarK2.getValue().equalsIgnoreCase("yes") ? true : false;
		
		String NumeroPartidas = txtNumeroPartidas.getText();
		if (NumeroPartidas.isEmpty()) {
			NumeroPartidas = "1";
		}
		
		numeroDePartidasSolicitadas = Integer.parseInt(NumeroPartidas);

		btnTreinar.setDisable(true);

		Calendar now = Calendar.getInstance();

		String hora = now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND);

		Log = "\n\nInicio -- " + hora;
		LogPlacar = "\n\nInicio -- " + hora;
		

		
		
		
		while (contadorPartidas <= numeroDePartidasSolicitadas) {
			// controlaPartida = controlaPartida.getInstaciaZerada();
			controlaPartida.setTecnica1(tecnica1);
			controlaPartida.setTecnica2(tecnica2);
			
			controlaPartida.setCBR(TipoReuso1, TipoAprendizagem1, TipoReuso2, TipoAprendizagem2, TipoBase1, TipoBase2,
					UsarCluster1, UsarCluster2, threshold1, threshold2, TipoReusoIntra1, TipoReusoIntra2, autoAjustarK1, autoAjustark2);
			controlaPartida.setaUltimoId(TipoReuso1, TipoAprendizagem1, TipoBase1, TipoReuso2, TipoAprendizagem2, TipoBase2,
					UsarCluster1, UsarCluster2, TipoReusoIntra1, TipoReusoIntra2);
			controlaPartida.setAutoAjusteCluster1(autoAjustarK1);
			controlaPartida.setAutoAjusteCluster2(autoAjustark2);
			
			SalvaLog();
			SalvaLogPlacar();
			Log = "\n\n\t######  Partida = " + contadorPartidas + "  ######";
			LogPlacar = "\n\n\t######  Partida = " + contadorPartidas + "  ######";
			now = Calendar.getInstance();
			hora = now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND);

			Log += "\nNovaPartida -- " + hora;
			LogPlacar += "\nNovaPartida -- " + hora;

			contadorPartidas++;
			//System.out.println("iniciou partida: " + contadorPartidas);

			novaPartida(contadorPartidas);
		
			controlaPartida.fecharBases();
		}

		controlaPartida.salvaHistorico();
		btnTreinar.setDisable(false);

		now = Calendar.getInstance();
		hora = now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND);

		Log = "\n\n\nFim -- " + hora;
		LogPlacar = "\n\n\nFim -- " + hora;

		SalvaLog();
		SalvaLogPlacar();
		//System.out.println("concluido treinamento");

	}

	public void novaPartida(int PartidaNumber) {
		inicioPartida = getDateTime();
		//System.out.println("NumPartida: " + PartidaNumber + " - " + inicioPartida);
		controlaPartida.novaPartida(PartidaNumber);
		IniciaRodada();
	}

	private void IniciaRodada() {
		controlaRodadaAuto = new ControlaRodadaAuto();
		controlaPartida.novaRodada();
		Log += controlaRodadaAuto.getCartas();
		joga(controlaPartida.getQuemEhMao());

	}

	private void joga(int QuemJoga) {
		Log += "\nJOGADOR  " + QuemJoga + "  na mao  " + controlaRodadaAuto.getQualEhAmao();
		//System.out.println("\nJOGADOR  " + QuemJoga + "  na rodada  " + controlaRodadaAuto.getQualEhAmao());

		// verifica se ponto ta disponivel
		if (controlaRodadaAuto.getPodeChamarPontosAinda()) {
			// se tem ponto disponivel, verifica se vai chamar
			String PontoChamado = controlaRodadaAuto.ChamarPonto(QuemJoga);
			Log += "\n" + QuemJoga + " vai chamar ponto == ( " + PontoChamado + " )";
			//// System.out.println(QuemJoga + " vai chamar ponto == ( " + PontoChamado + "
			//// )");
			if (!PontoChamado.equals("")) {
				// Pergunta se o oponente aceita ponto
				perguntaOponenteAceitarPontos(PontoChamado, QuemJoga);
			}
		}

		// verifica se vai ao baralho
		if (controlaRodadaAuto.IrAoBaralho(QuemJoga)) {
			Log += "\nJogador  " + QuemJoga + "  Foi ao baralho";
			//System.out.println("Jogador  " + QuemJoga + "  Foi ao baralho");
			controlaRodadaAuto.quemFoiBaralho(QuemJoga, controlaRodadaAuto.getQualEhAmao());
			// System.out.println("++++++++++++++++++++++++++++++FOI AO BARALHO: "+ QuemJoga
			// +" na rodada: "+controlaRodadaAuto.getQualEhAmao());
			FimRodada();
		} else {
			// verifica se chamada de jogo da disponivel
			if (controlaRodadaAuto.quemPodeChamarJogoAgora() == 0
					|| controlaRodadaAuto.quemPodeChamarJogoAgora() == QuemJoga) {
				String jogoQueSeraChamado = controlaRodadaAuto.chamarJogada(QuemJoga);
				Log += "\n" + QuemJoga + " vai chamar jogada  == ( " + jogoQueSeraChamado + " )";
				//System.out.println(QuemJoga + " vai chamar jogada  == ( " + jogoQueSeraChamado + " )");
				if (!jogoQueSeraChamado.equals("")) {
					perguntaOponenteAceitarJogo(jogoQueSeraChamado, QuemJoga);
				}
			}

			if (controlaPartida.isTemMaisRodada()) {
				// System.out.print(QuemJoga + " jogou a carta == ");
				String carta = controlaRodadaAuto.jogarAgente(QuemJoga, controlaRodadaAuto.getQualEhAmao());
				Log += "\n" + QuemJoga + " jogou a carta == " + carta;

				verificaQuemJoga();
			}
		}
	}

	public void perguntaOponenteAceitarPontos(String PontoChamado, int QuemPediu) {

		int Oponente = QuemOponente(QuemPediu);
		boolean oponenteAceitouPontos = controlaRodadaAuto.AceitarPontos(PontoChamado, Oponente);

		if (oponenteAceitouPontos) {
			//// System.out.println(Oponente + " aceitou ponto ( " + PontoChamado + " )");
			Log += "\n" + Oponente + "  aceitou ponto ( " + PontoChamado + " )";
			// se ele aceitou verifica se ainda tem pontos a serem aumentados
			boolean aindaTemPontos = controlaRodadaAuto.temPontosParaSerChamados();
			if (aindaTemPontos) {
				//// System.out.println("Verificando se aumenta ponto");
				// se tem ponto a ser aumentado verifica se vai ser aumentado

				String AumentarPontos = controlaRodadaAuto.ChamarPonto(Oponente);
				if (!AumentarPontos.equals("")) {
					Log += "\n" + Oponente + "  aumentou ponto ( " + PontoChamado + " )  para ( " + AumentarPontos
							+ " )";
					//// System.out.println(Oponente + " aumentou ponto ( " + PontoChamado + " )
					//// para ( " + AumentarPontos + " )");
					// Vai ser aumentado ponto, entao verifica se o outro aceita
					perguntaOponenteAceitarPontos(AumentarPontos, Oponente);
				}
			}
		} else {
			// nao aceitou pontos,
			Log += "\n" + Oponente + "  negou ponto ( " + PontoChamado + " )";
			//// System.out.println(Oponente + " negou ponto ( " + PontoChamado + " )");
			controlaRodadaAuto.naoAceitouPontos(PontoChamado, Oponente);
		}
		// controlaRodadaAuto.pontuaQuemGanhouPontos();
	}

	private void perguntaOponenteAceitarJogo(String jogoQueSeraChamado, int QuemPediu) {
		int Oponente = QuemOponente(QuemPediu);

		boolean OponenteAceitou = controlaRodadaAuto.AceitarJogada(jogoQueSeraChamado, Oponente);

		if (OponenteAceitou) {
			// se aceitou verifica pode aumentar ponto
			Log += "\n" + Oponente + "  aceitou jogo ( " + jogoQueSeraChamado + " )";
			//// System.out.println(Oponente + " aceitou jogo ( " + jogoQueSeraChamado + "
			//// )");

			if (controlaRodadaAuto.quemPodeChamarJogoAgora() == Oponente && controlaRodadaAuto.temJogadaAinda()) {

				// Ja que pode chamar jogo verifica se vai aumentar
				String jogoQueSeraAumentado = controlaRodadaAuto.chamarJogada(Oponente);
				Log += "\n" + Oponente + "  aumentou aceitou jogo para ( " + jogoQueSeraAumentado + " )";
				//// System.out.println(Oponente + " aumentou aceitou jogo para ( " +
				//// jogoQueSeraAumentado + " )");

				if (!jogoQueSeraAumentado.equals("")) {
					// pergunta se o oponente aceita o aumento de jogo
					perguntaOponenteAceitarJogo(jogoQueSeraAumentado, Oponente);
				}
			}
		} else {
			Log += "\n" + Oponente + "  negou jogo ( " + jogoQueSeraChamado + " )  na mao "
					+ controlaRodadaAuto.getQualEhAmao();
			//// System.out.println(Oponente + " negou jogo ( " + jogoQueSeraChamado + " )
			//// na rodada " + controlaRodadaAuto.getQualEhAmao());
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
		//// System.out.println("Fim Partida");
		controlaRodadaAuto.pontuaQuemGanhouPontos();
		controlaRodadaAuto.pontuaQuemGanhouArodada();
		String pontos = controlaRodadaAuto.pontosRodada();
		// System.out.println("=====END HAND=====");
		// System.out.println("=====END MATCH=====");
		Log += "\n" + pontos + "\n\n";
		LogPlacar += "\n" + pontos + "\n\n";
		controlaRodadaAuto.criaDescriptionParaPersistir();
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

		// System.out
		// .println("Placar " + controlaPartida.getPontosAgente1() + " X " +
		// controlaPartida.getPontosAgente2());
	}

	private void FimRodada() {

		//System.out.println("Fim Rodada");
		controlaRodadaAuto.pontuaQuemGanhouPontos();
		controlaRodadaAuto.pontuaQuemGanhouArodada();
		String pontos = controlaRodadaAuto.pontosRodada();
		// System.out.println("=====END HAND=====");
		LogPlacar += "\n" + pontos;
		Log += "\n" + pontos + "\n\n";
		controlaRodadaAuto.criaDescriptionParaPersistir();
		if (!controlaPartida.isTemMaisRodada()) {
			//// System.out.println("Placar " + controlaPartida.getPontosAgente1() + " X " +
			//// controlaPartida.getPontosAgente2());
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

	private void SalvaLog() {

		try {
			File arquivo = new File("Logs_Partida_" + "Extra_" + TipoReuso1 + "_Intra_" + TipoReusoIntra1 + " Cluster "
					+ UsarCluster1 + " Vs " + "Extra_" + TipoReuso2 + "_Intra_" + TipoReusoIntra2 + " cluster "
					+ UsarCluster2 + ".txt");
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

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			diffTime = String.format("%02d", diffMinutes) + ":" + String.format("%02d", diffSeconds);

			// System.out.print(diffDays + " days, ");
			// System.out.print(diffHours + " hours, ");
			// System.out.print(diffMinutes + " minutes, ");
			// System.out.print(diffSeconds + " seconds.");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return diffTime;
	}

}
