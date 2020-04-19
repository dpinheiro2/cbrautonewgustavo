package gamer.Auto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import CbrQuerys.CBR;

import cbr.Adaptacoes.CbrModular;
import model.Decision;
import model.Match;
import model.Player;
import hibernate.utils.HibernateUtil;
import jcolibri.exception.ExecutionException;
import markov.RespondeMarkovEngine;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import treinamentoModelo.SetCbrModelo;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class ControlaPartidaAuto {

	public static ControlaPartidaAuto getControlaPartidaAuto() {
		return controlaPartidaAuto;
	}

	public static void setControlaPartidaAuto(ControlaPartidaAuto controlaPartidaAuto) {
		ControlaPartidaAuto.controlaPartidaAuto = controlaPartidaAuto;
	}

	static	Session session = null;

	private Player player1;
	private Player player2;
	private CbrModular trucoCBR1;
	private CbrModular trucoCBR2;

	private String tecnica1 = "";
	private String tecnica2="";

	private boolean autoAjusteCluster1 = false;
	private boolean autoAjusteCluster2 = false;

	private Match match;

	private int contadorRodadas = 0;
	private int pontosAgente2 = 0;
	private int pontosAgente1 = 0;
	private int pontosAnterioresAgente1 = 0;
	private int pontosAnterioresAgente2 = 0;
	private int partidaNumber = 0;
	private int rodadaNumber = 0;

	private int countBluff1Success = 0;
	private int countBluff2Success = 0;
	private int countBluff3Success = 0;
	private int countBluff4Success = 0;
	private int countBluff5Success = 0;
	private int countBluff6Success = 0;

    private int countBluff1Failure = 0;
    private int countBluff2Failure = 0;
    private int countBluff3Failure = 0;
    private int countBluff4Failure = 0;
    private int countBluff5Failure = 0;
    private int countBluff6Failure = 0;

    private int countBluff1Opponent = 0;
    private int countBluff2Opponent = 0;
    private int countBluff3Opponent = 0;
    private int countBluff4Opponent = 0;
    private int countBluff5Opponent = 0;
    private int countBluff6Opponent = 0;

	private int countBluff1ShowDown = 0;
	private int countBluff2ShowDown = 0;
	private int countBluff3ShowDown = 0;
	private int countBluff4ShowDown = 0;
	private int countBluff5ShowDown = 0;
	private int countBluff6ShowDown = 0;

	private static int LastIdArquivo = 0;

	private static String pathHistoricoPontos = "src/Logs/historicoPlacares.txt";

	private List<String> listaRodadas = new ArrayList<String>();

	// classe padrÃ£o singleton
	public static ControlaPartidaAuto controlaPartidaAuto = null;

	private ControlaPartidaAuto() {

	}

	public static ControlaPartidaAuto getInstacia() {

		if (controlaPartidaAuto == null)
			controlaPartidaAuto = new ControlaPartidaAuto();
		return controlaPartidaAuto;
	}

	public static ControlaPartidaAuto getInstaciaZerada() {

		
			controlaPartidaAuto = new ControlaPartidaAuto();
		return controlaPartidaAuto;
	}

	public static ControlaPartidaAuto mataInstacia() {

			controlaPartidaAuto = new ControlaPartidaAuto();
		return controlaPartidaAuto;
	}

	public boolean isAutoAjusteCluster1() {
		return autoAjusteCluster1;
	}

	public void setAutoAjusteCluster1(boolean autoAjusteCluster1) {
		this.autoAjusteCluster1 = autoAjusteCluster1;
		trucoCBR1.setAjusteAutomaticoDoK(autoAjusteCluster1);
	}

	public boolean isAutoAjusteCluster2() {
		return autoAjusteCluster2;
	}

	public void setAutoAjusteCluster2(boolean autoAjusteCluster2) {
		this.autoAjusteCluster2 = autoAjusteCluster2;
		trucoCBR2.setAjusteAutomaticoDoK(autoAjusteCluster2);
	}

	public String getTecnica1() {
		return tecnica1;
	}

	public void setTecnica1(String tecnica1) {
		this.tecnica1 = tecnica1;
	}

	public String getTecnica2() {
		return tecnica2;
	}

	public void setTecnica2(String tecnica2) {
		this.tecnica2 = tecnica2;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public int getPartidaNumber() {
		return partidaNumber;
	}

	public void setPartidaNumber(int partidaNumber) {
		this.partidaNumber = partidaNumber;
	}

	public int getPontosAnterioresAgente1() {
		return pontosAnterioresAgente1;
	}

	public void setPontosAnterioresAgente1(int pontosAnterioresAgente1) {
		this.pontosAnterioresAgente1 = pontosAnterioresAgente1;
	}

	public int getPontosAnterioresAgente2() {
		return pontosAnterioresAgente2;
	}

	public void setPontosAnterioresAgente2(int pontosAnterioresAgente2) {
		this.pontosAnterioresAgente2 = pontosAnterioresAgente2;
	}

	public int getContadorRodadas() {
		return contadorRodadas;
	}

	public void setContadorRodadas(int contadorRodadas) {
		this.contadorRodadas = contadorRodadas;
	}

	public int getPontosAgente1() {
		return pontosAgente1;
	}

	public void setPontosAgente1(int pontosAgente1) {
		if (pontosAgente1 > 24) {
			this.pontosAgente1 = 24;
		} else {
			this.pontosAgente1 = pontosAgente1;
		}

	}

	public int getPontosAgente2() {
		return pontosAgente2;
	}

	public void setPontosAgente2(int pontosAgente2) {
		if (pontosAgente2 > 24) {
			this.pontosAgente2 = 24;
		} else {
			this.pontosAgente2 = pontosAgente2;
		}

	}

	public CbrModular getCBR(int i) {
		if (i == 1)
			return trucoCBR1;
		else
			return trucoCBR2;
	}

	public boolean isTemMaisRodada() {
		boolean temRodada = (pontosAgente1 < 24 && pontosAgente2 < 24 ? true : false);
		return temRodada;
	}

	public void adicionaPartida() {
		String Dados = "";
		Dados += +partidaNumber + "," + pontosAgente1 + "," + pontosAgente2 + ",";
		// quem ganhou
		if (pontosAgente1 > pontosAgente2)
			Dados += "1,0\n";
		else if (pontosAgente2 > pontosAgente1)
			Dados += "0,1\n";
		else
			Dados += "0,0";
		if (Dados.equalsIgnoreCase("0,0,0,0,0"))
			Dados = "";
		listaRodadas.add(Dados);
		if (pontosAgente1 != 0 && pontosAgente2 != 0) {
			/*Match match = new Match();
			match.setPlayer1(player1);
			match.setPlayer2(player2);
			match.setPointsPlayer1(pontosAgente1);
			match.setPointsPlayer2(pontosAgente2);
			match.setWinner(pontosAgente1 > pontosAgente2 ? player1 : player2);
			saveMatch(match);*/
		}

	}

	public CBR getTrucoCBR1() {
		return trucoCBR1;
	}

	public void setTrucoCBR1(CbrModular trucoCBR1) {
		this.trucoCBR1 = trucoCBR1;
	}

	public CBR getTrucoCBR2() {
		return trucoCBR2;
	}

	public void setTrucoCBR2(CbrModular trucoCBR1) {
		this.trucoCBR2 = trucoCBR2;
	}

	public List<String> getListaRodadas() {
		return listaRodadas;
	}

	public void setListaRodadas(List<String> listaRodadas) {
		this.listaRodadas = listaRodadas;
	}

	public int getCountBluff1Success() {
		return countBluff1Success;
	}

	public void setCountBluff1Success(int countBluff1Success) {
		this.countBluff1Success = countBluff1Success;
	}

	public int getCountBluff2Success() {
		return countBluff2Success;
	}

	public void setCountBluff2Success(int countBluff2Success) {
		this.countBluff2Success = countBluff2Success;
	}

	public int getCountBluff3Success() {
		return countBluff3Success;
	}

	public void setCountBluff3Success(int countBluff3Success) {
		this.countBluff3Success = countBluff3Success;
	}

	public int getCountBluff4Success() {
		return countBluff4Success;
	}

	public void setCountBluff4Success(int countBluff4Success) {
		this.countBluff4Success = countBluff4Success;
	}

	public int getCountBluff5Success() {
		return countBluff5Success;
	}

	public void setCountBluff5Success(int countBluff5Success) {
		this.countBluff5Success = countBluff5Success;
	}

	public int getCountBluff6Success() {
		return countBluff6Success;
	}

	public void setCountBluff6Success(int countBluff6Success) {
		this.countBluff6Success = countBluff6Success;
	}

	public int getCountBluff1Failure() {
		return countBluff1Failure;
	}

	public void setCountBluff1Failure(int countBluff1Failure) {
		this.countBluff1Failure = countBluff1Failure;
	}

	public int getCountBluff2Failure() {
		return countBluff2Failure;
	}

	public void setCountBluff2Failure(int countBluff2Failure) {
		this.countBluff2Failure = countBluff2Failure;
	}

	public int getCountBluff3Failure() {
		return countBluff3Failure;
	}

	public void setCountBluff3Failure(int countBluff3Failure) {
		this.countBluff3Failure = countBluff3Failure;
	}

	public int getCountBluff4Failure() {
		return countBluff4Failure;
	}

	public void setCountBluff4Failure(int countBluff4Failure) {
		this.countBluff4Failure = countBluff4Failure;
	}

	public int getCountBluff5Failure() {
		return countBluff5Failure;
	}

	public void setCountBluff5Failure(int countBluff5Failure) {
		this.countBluff5Failure = countBluff5Failure;
	}

	public int getCountBluff6Failure() {
		return countBluff6Failure;
	}

	public void setCountBluff6Failure(int countBluff6Failure) {
		this.countBluff6Failure = countBluff6Failure;
	}

	public int getCountBluff1Opponent() {
		return countBluff1Opponent;
	}

	public void setCountBluff1Opponent(int countBluff1Opponent) {
		this.countBluff1Opponent = countBluff1Opponent;
	}

	public int getCountBluff2Opponent() {
		return countBluff2Opponent;
	}

	public void setCountBluff2Opponent(int countBluff2Opponent) {
		this.countBluff2Opponent = countBluff2Opponent;
	}

	public int getCountBluff3Opponent() {
		return countBluff3Opponent;
	}

	public void setCountBluff3Opponent(int countBluff3Opponent) {
		this.countBluff3Opponent = countBluff3Opponent;
	}

	public int getCountBluff4Opponent() {
		return countBluff4Opponent;
	}

	public void setCountBluff4Opponent(int countBluff4Opponent) {
		this.countBluff4Opponent = countBluff4Opponent;
	}

	public int getCountBluff5Opponent() {
		return countBluff5Opponent;
	}

	public void setCountBluff5Opponent(int countBluff5Opponent) {
		this.countBluff5Opponent = countBluff5Opponent;
	}

	public int getCountBluff6Opponent() {
		return countBluff6Opponent;
	}

	public void setCountBluff6Opponent(int countBluff6Opponent) {
		this.countBluff6Opponent = countBluff6Opponent;
	}

	public int getCountBluff1ShowDown() {
		return countBluff1ShowDown;
	}

	public void setCountBluff1ShowDown(int countBluff1ShowDown) {
		this.countBluff1ShowDown = countBluff1ShowDown;
	}

	public int getCountBluff2ShowDown() {
		return countBluff2ShowDown;
	}

	public void setCountBluff2ShowDown(int countBluff2ShowDown) {
		this.countBluff2ShowDown = countBluff2ShowDown;
	}

	public int getCountBluff3ShowDown() {
		return countBluff3ShowDown;
	}

	public void setCountBluff3ShowDown(int countBluff3ShowDown) {
		this.countBluff3ShowDown = countBluff3ShowDown;
	}

	public int getCountBluff4ShowDown() {
		return countBluff4ShowDown;
	}

	public void setCountBluff4ShowDown(int countBluff4ShowDown) {
		this.countBluff4ShowDown = countBluff4ShowDown;
	}

	public int getCountBluff5ShowDown() {
		return countBluff5ShowDown;
	}

	public void setCountBluff5ShowDown(int countBluff5ShowDown) {
		this.countBluff5ShowDown = countBluff5ShowDown;
	}

	public int getCountBluff6ShowDown() {
		return countBluff6ShowDown;
	}

	public void setCountBluff6ShowDown(int countBluff6ShowDown) {
		this.countBluff6ShowDown = countBluff6ShowDown;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public int getQuemEhMao() {
		return (contadorRodadas % 2 == 0 ? 2 : 1);
	}

	public void setCBR(String Tipo1, String Aprendizagem1, String Tipo2, String Aprendizagem2,
					   String TipoBase1, String TipoBase2, String usarCluster1, String usarCluster2,
					   double threshold1, double threshold2, String tipoReusoIntraCluster1, String tipoReusoIntraCluster2,
					   boolean recalcularCentroideAgenteUm, boolean recalcularCentroideAgenteDois, boolean revisarAtivo1, boolean revisarAtivo2) {

		/*if(tecnica1.equalsIgnoreCase("markov".trim())) {
		trucoCBR1 = new RespondeMarkovEngine();
		}*/
		/*else {*/

		trucoCBR1 = new CbrModular("Treinamento", TipoBase1);
		trucoCBR1.setTipoDecisao(Tipo1, tipoReusoIntraCluster1);
		trucoCBR1.setReusoComCluster(usarCluster1.equalsIgnoreCase("yes")? true: false );
		trucoCBR1.setThreshold(threshold1);
		trucoCBR1.setAprendizagem(Aprendizagem1);
		trucoCBR1.setAjusteAutomaticoDoK(recalcularCentroideAgenteUm);
		trucoCBR1.setRevisarAtivo(revisarAtivo1);
		trucoCBR1.realizaConfiguracoesIniciais();

		player1 = getPlayer(Tipo1, tipoReusoIntraCluster1, (usarCluster1.equalsIgnoreCase("yes")? 1 : 0),
				TipoBase1);

		System.out.println("Player1: " + player1.toString() + "--> Aprendizado: " + Aprendizagem1);

		/*}*/
	/*	if(tecnica2.equalsIgnoreCase("markov".trim())) {
			trucoCBR2 = new RespondeMarkovEngine();
		}else {*/


		trucoCBR2 = new CbrModular("Treinamento", TipoBase2);
		trucoCBR2.setTipoDecisao(Tipo2,  tipoReusoIntraCluster2 );
		trucoCBR2.setReusoComCluster(usarCluster2.equalsIgnoreCase("yes")?true : false);
		trucoCBR2.setThreshold(threshold2);
		trucoCBR2.setAprendizagem(Aprendizagem2);
		trucoCBR2.setAjusteAutomaticoDoK(recalcularCentroideAgenteDois);
		trucoCBR2.setRevisarAtivo(revisarAtivo2);
		trucoCBR2.realizaConfiguracoesIniciais();

		player2 = getPlayer(Tipo2, tipoReusoIntraCluster2, (usarCluster2.equalsIgnoreCase("yes")? 1 : 0),
				TipoBase2);

		System.out.println("Player2: " + player2.toString()+ "--> Aprendizado: " + Aprendizagem2);

	}


	public void setCBR(String Tipo1, String Aprendizagem1, String Tipo2, String Aprendizagem2,
					   String TipoBase1, String TipoBase2, String usarCluster1, String usarCluster2,
					   double threshold1, double threshold2, String tipoReusoIntraCluster1, String tipoReusoIntraCluster2, 
					   boolean recalcularCentroideAgenteUm, boolean recalcularCentroideAgenteDois) {
		
		/*if(tecnica1.equalsIgnoreCase("markov".trim())) {
		trucoCBR1 = new RespondeMarkovEngine();	
		}*/
		/*else {*/
		//System.out.println("Resuso Extra 1: " +  Tipo1 + " Reuso Intra 1: "+ tipoReusoIntraCluster1  + " usar cluster: "+ usarCluster1);
		//System.out.println("Resuso Extra 2: " +  Tipo2 + " Reuso Intra 2: "+ tipoReusoIntraCluster2  + " usar cluster: "+ usarCluster2);
		
		
		
		
		trucoCBR1 = new CbrModular("Treinamento", TipoBase1);
		trucoCBR1.setTipoDecisao(Tipo1, tipoReusoIntraCluster1);
		trucoCBR1.setReusoComCluster(usarCluster1.equalsIgnoreCase("yes")? true: false );
		trucoCBR1.setThreshold(threshold1);
		trucoCBR1.setAprendizagem(Aprendizagem1);
		trucoCBR1.setAjusteAutomaticoDoK(recalcularCentroideAgenteUm);
		trucoCBR1.realizaConfiguracoesIniciais();
		
		

		player1 = getPlayer(Tipo1, tipoReusoIntraCluster1, (usarCluster1.equalsIgnoreCase("yes")? 1 : 0),
				TipoBase1);

		System.out.println("Player1: " + player1.toString() + "--> Aprendizado: " + Aprendizagem1);

		/*}*/
	/*	if(tecnica2.equalsIgnoreCase("markov".trim())) {
			trucoCBR2 = new RespondeMarkovEngine();
		}else {*/
		
	
		trucoCBR2 = new CbrModular("Treinamento", TipoBase2);
		trucoCBR2.setTipoDecisao(Tipo2,  tipoReusoIntraCluster2 );
		trucoCBR2.setReusoComCluster(usarCluster2.equalsIgnoreCase("yes")?true : false);
		trucoCBR2.setThreshold(threshold2);
		trucoCBR2.setAprendizagem(Aprendizagem2);
		trucoCBR2.setAjusteAutomaticoDoK(recalcularCentroideAgenteDois);
		trucoCBR2.realizaConfiguracoesIniciais();
		

		player2 = getPlayer(Tipo2, tipoReusoIntraCluster2, (usarCluster2.equalsIgnoreCase("yes")? 1 : 0),
				TipoBase2);

		System.out.println("Player2: " + player2.toString()+ "--> Aprendizado: " + Aprendizagem2);
		/*}*/
	}

	public void novaPartida(int PartidaNumber) {

		adicionaPartida();
		contadorRodadas = 0;
		pontosAgente2 = 0;
		pontosAgente1 = 0;
		rodadaNumber = 0;
		pontosAnterioresAgente1 = 0;
		pontosAnterioresAgente2 = 0;
		partidaNumber = PartidaNumber + LastIdArquivo;

        countBluff1Success = 0;
        countBluff2Success = 0;
        countBluff3Success = 0;
        countBluff4Success = 0;
        countBluff5Success = 0;
        countBluff6Success = 0;

        countBluff1Failure = 0;
        countBluff2Failure = 0;
        countBluff3Failure = 0;
        countBluff4Failure = 0;
        countBluff5Failure = 0;
        countBluff6Failure = 0;

        countBluff1Opponent = 0;
        countBluff2Opponent = 0;
        countBluff3Opponent = 0;
        countBluff4Opponent = 0;
        countBluff5Opponent = 0;
        countBluff6Opponent = 0;

		countBluff1ShowDown = 0;
		countBluff2ShowDown = 0;
		countBluff3ShowDown = 0;
		countBluff4ShowDown = 0;
		countBluff5ShowDown = 0;
		countBluff6ShowDown = 0;

	}

	public void fecharBases() {
	try {
		trucoCBR1.fechaBase();
		trucoCBR2.fechaBase();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
}

	public void salvaHistorico() {
		
		adicionaPartida();
		String Dados = "";
		for (String Tento : listaRodadas) {
			Dados = Dados + Tento;
		}
		//System.out.println(Dados);
		try {
			File arquivo = new File(pathHistoricoPontos);
			FileWriter fw = new FileWriter(arquivo, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(Dados);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setaUltimoId(String tipoReuso1, String tipoAprendizagem1,  String Base1,
							 String tipoReuso2, String tipoAprendizagem2,  String Base2, String usarCluster1, String usarCluster2,
							 String tipoReusoIntraCluster1, String tipoReusoIntraCluster2) {

		pathHistoricoPontos = "Extra_" + tipoReuso1 + "_Intra_" + tipoReusoIntraCluster1 + " Cluster "+ usarCluster1 + trucoCBR1.getThreshold() + "% _V" +
				"s_" + "Extra_" + tipoReuso2 + "_Intra_" + tipoReusoIntraCluster2  + " Cluster "+ usarCluster2 +trucoCBR2.getThreshold() +"%"+ ".txt";
		//System.out.println(pathHistoricoPontos);

//		pathHistoricoPontos = tipoReuso1 + "_" + tipoAprendizagem1 + "_" + tipoRetencao1 + "-" + tipoReuso2 + "_"
//				+ tipoAprendizagem2 + "_" + tipoRetencao2 + ".txt";
		File arquivo = new File(pathHistoricoPontos);
		try {
			arquivo.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String conteudoArquivo = "";
		try {
			conteudoArquivo = new String(Files.readAllBytes(Paths.get(arquivo.getAbsolutePath())));
			String[] Historicos = conteudoArquivo.split("\n");
//			//System.out.println("hist " + Historicos);
			String lastCase = Historicos[Historicos.length - 1];
//			//System.out.println("Last  " + lastCase);
			String lastIdString = lastCase.split(",")[0];
//			//System.out.println(lastIdString);
			if (lastIdString.equalsIgnoreCase("")) {
				LastIdArquivo = 0;
			} else {
//				//System.out.println("lastId: "+ lastIdString.split(",")[0]);
				int idInt = Integer.parseInt(lastIdString);
				LastIdArquivo = idInt;
			}
//			//System.out.println("ahhh " + LastIdArquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void novaRodada() {
		rodadaNumber++;
		//System.out.println("Mão nº: " + rodadaNumber);
	}

	public int getRodadaNumber() {
		return rodadaNumber;
	}

	public void setRodadaNumber(int rodadaNumber) {
		this.rodadaNumber = rodadaNumber;
	}

	public void savePlayer(Player player) {
		try {
			
		    if(session == null || !session.isOpen()) session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(player);
			session.getTransaction().commit();
			//System.out.println("Player inserido com sucesso!");

		if(session.isOpen())	 session.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveMatch(Match match) {

		try {
			if(session == null || !session.isOpen()) session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(match);
			session.getTransaction().commit();
			//System.out.println("Partida inserida com sucesso!");

			if(session.isOpen() ) session.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveMatch() {
		try {
			if(session == null || !session.isOpen()) session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(match);
			session.getTransaction().commit();
			//System.out.println("Partida inserida com sucesso!");

			if(session.isOpen() ) session.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Player getPlayer(String extraClusterReuse, String intraClusterReuse, int indCluster, String caseBase) {

		Player player;
		if(session == null || !session.isOpen()) 
			session = HibernateUtil.getSessionFactory().openSession();
		player = (Player) session.createCriteria(Player.class)
				.add(Restrictions.eq("extraClusterReuse", extraClusterReuse))
				.add(Restrictions.eq("intraClusterReuse", intraClusterReuse))
				.add(Restrictions.eq("indCluster", indCluster))
				.add(Restrictions.eq("caseBase", caseBase))
				.uniqueResult();
                 if(session.isOpen()) session.close();
		if (player != null) {
			return player;
		} else {
			player = new Player();
			player.setExtraClusterReuse(extraClusterReuse);
			player.setIntraClusterReuse(intraClusterReuse);
			player.setIndCluster(indCluster);
			player.setCaseBase(caseBase);
			savePlayer(player);
			 if(session.isOpen()) session.close();
			//System.out.println("Id Player inserido: " + player.getIdPlayer());
			return player;
		}
	}

}