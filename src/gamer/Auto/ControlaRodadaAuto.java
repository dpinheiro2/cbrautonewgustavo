
package gamer.Auto;




import java.util.*;

import cbr.Adaptacoes.CbrModular;
import cbr.AtualizaConsultas.AtualizarConsultas;
import cbr.AtualizaConsultas.AuxiliaConsultas.*;
import cbr.cbrDescriptions.TrucoDescription;
import gamer.Truco.EstadoJogoModelo;
import gamer.Truco.Cartas.DarCartasModelo;
import gamer.ui.loader.LoaderActiveLearning;
import model.Decision;
import utils.Action;
import utils.Deck;
import utils.GameState;
import outros.GeraCartas;

public class ControlaRodadaAuto {

    int contadorMaos = 0;
    private ControlaPartidaAuto controlaPartidaAuto = ControlaPartidaAuto.getInstacia();
    private CbrModular trucoCbr_Agente1 = controlaPartidaAuto.getCBR(1);
    private CbrModular trucoCbr_Agente2 = controlaPartidaAuto.getCBR(2);
    public List<String> historicoTentos;
    private EstadoJogoModelo estadoJogo;

    private TentosGanhos tentosRodada;
    private int contadorMao = 0;
    public int ultimoAjogar = 0;
    private int contadorJogadas;
    private Integer quemBaralho = null;
    private Integer quandoBaralho = null;
    private List<CartasModelo> listaCartasRecebidasAgente1 = new ArrayList<CartasModelo>();
    private List<CartasModelo> listaCartasRecebidasAgente2 = new ArrayList<CartasModelo>();
    private static List<CartasModelo> listaCartasAgente1TemNaMao = new ArrayList<CartasModelo>();
    private static List<CartasModelo> listaCartasAgente2TemNaMao = new ArrayList<CartasModelo>();
    private List<CartasModelo> listaCartasJogadasAgente1 = new ArrayList<CartasModelo>();
    private List<CartasModelo> listaCartasJogadasAgente2 = new ArrayList<CartasModelo>();
    private List<JogadasChamadasModelo> listaJogadasChamadas = new ArrayList<JogadasChamadasModelo>();
    private List<JogadasAceitasModelo> listaJogadasAceitas = new ArrayList<JogadasAceitasModelo>();
    private List<JogadasNaoAceitasModelo> listaJogadasNaoAceitas = new ArrayList<JogadasNaoAceitasModelo>();
    private List<JogadasChamadasModelo> listaPontosChamados = new ArrayList<JogadasChamadasModelo>();
    private List<JogadasAceitasModelo> listaPontosAceitos = new ArrayList<JogadasAceitasModelo>();
    private List<JogadasNaoAceitasModelo> listPontosNaoAceitos = new ArrayList<JogadasNaoAceitasModelo>();
    private Integer GanhadorPrimeira = null;
    private Integer GanhadorSegunda = null;
    private Integer GanhadorTerceira = null;
    private boolean pontosJaChamados;
    private boolean pontosJaContabilizados;
    private List<CartasModelo> cartasJogadasMesa;
    private Deck deck;
    private String responseActiveLearning;
    private boolean utilEnvido = false;
    private boolean utilTruco = false;
    private boolean utilCarta = false;
    private boolean hasDeception = false;
    GameState gameState;
    private String envidoJustifications;
    private String trucoJustifications;
    private boolean compulsoryRetention = false;


    public ControlaRodadaAuto() {

        gameState = new GameState();
        cartasJogadasMesa = new ArrayList<CartasModelo>();
        responseActiveLearning = null;
        utilEnvido = false;
        utilTruco = false;
        utilCarta = false;
        hasDeception = false;
        deck = new Deck();
        deck.createDeck();
        deck.gerarAllHandsPossible();
        historicoTentos = new ArrayList<String>();
        estadoJogo = new EstadoJogoModelo();
        contadorMao = 0;
        ultimoAjogar = 0;
        contadorJogadas = 0;
        tentosRodada = new TentosGanhos();
        compulsoryRetention = false;
        printPlacar();
        //darCartasFile();
        darCartas();
        printCartas(1, listaCartasRecebidasAgente1, "DEALT_CARDS");
        printCartas(2, listaCartasRecebidasAgente2, "DEALT_CARDS");
        System.out.println("ENVIDO_POINTS Player1 --> " + calcularPontosEnvidoAgente(1));
        //System.out.println("ENVIDO_POINTS Player2 --> " + calcularPontosEnvidoAgente(2));
        deck.gerarAllOpponentHandsPossible(listaCartasRecebidasAgente1.get(0).getCarta(),
                listaCartasRecebidasAgente1.get(1).getCarta(), listaCartasRecebidasAgente1.get(2).getCarta());
        controlaPartidaAuto.setContadorRodadas(controlaPartidaAuto.getContadorRodadas() + 1);
        trucoCbr_Agente1.realizaConfiguracoesIniciais2(listaCartasRecebidasAgente1.get(0).getCarta(),
                listaCartasRecebidasAgente1.get(1).getCarta(), listaCartasRecebidasAgente1.get(2).getCarta());
        trucoCbr_Agente2.realizaConfiguracoesIniciais2(listaCartasRecebidasAgente2.get(0).getCarta(),
                listaCartasRecebidasAgente2.get(1).getCarta(), listaCartasRecebidasAgente2.get(2).getCarta());
        verificarEstadoJogo();

    }

    public void printPlacar() {
        System.out.println("MATCH_SCORE " + "Player1 " + controlaPartidaAuto.getPontosAgente1() + " vs " +
                controlaPartidaAuto.getPontosAgente2() + " Player2 ");
    }

    public void printCartas(int player, List<CartasModelo> cartas, String info) {

        String auxCartas = "";
        for (CartasModelo carta : cartas) {
            auxCartas += carta.getCarta() + "; ";
        }

        System.out.println(info + " Player" + player + " --> " + auxCartas);

    }

    public void setaIndexacoesAgentes() {

        TrucoDescription stateAgent1Jogada = retornaDescriptionIndexacaoJogadaAgenteUm();
        TrucoDescription stateAgent2Jogada = retornaDescriptionIndexacaoJogadaAgenteDois();
        trucoCbr_Agente1.setaGrupoMaisSimilarIndexadoJogada(stateAgent1Jogada);

        trucoCbr_Agente2.setaGrupoMaisSimilarIndexadoJogada(stateAgent2Jogada);

        TrucoDescription stateAgent1Envido = retornaDescriptionIndexacaoEnvidoAgenteUm();
        TrucoDescription stateAgent2Envido = retornaDescriptionIndexacaoEnvidoAgenteDois();
        trucoCbr_Agente1.setaGrupoMaisSimilarIndexadoPontos(stateAgent1Envido);
        trucoCbr_Agente2.setaGrupoMaisSimilarIndexadoPontos(stateAgent2Envido);

    }

    private void darCartas() {
        contadorMaos ++;
        LimpaPontos();
        DarCartasModelo darCartasAuto = new DarCartasModelo();
        List<CartasModelo> listaCartas = new ArrayList<CartasModelo>();
        // adiciona 6 cartas e retorna, depois na interface retorna 3 p/ cada jogador
        for (int i = 0; i < 6; i++) {
            listaCartas.add(darCartasAuto.entregarCartas());
            if (i % 2 == 0) {
                listaCartasRecebidasAgente1.add(listaCartas.get(i));
            } else {
                listaCartasRecebidasAgente2.add(listaCartas.get(i));
            }
        }
        if (contadorMao == 0 || contadorMao <= 3) {
            contadorMao++;
        } else {
            contadorMao = 0;
            controlaPartidaAuto.setContadorRodadas(controlaPartidaAuto.getContadorRodadas() + 1);
        }
        // Sorting
        Collections.sort(listaCartasRecebidasAgente1, new Comparator<CartasModelo>() {
            public int compare(CartasModelo carta2, CartasModelo carta1) {
                return Integer.compare(carta1.valorImportancia, carta2.valorImportancia);
            }
        });
        Collections.sort(listaCartasRecebidasAgente2, new Comparator<CartasModelo>() {
            public int compare(CartasModelo carta2, CartasModelo carta1) {
                return Integer.compare(carta1.valorImportancia, carta2.valorImportancia);
            }
        });
        listaCartasAgente1TemNaMao = new ArrayList<CartasModelo>(listaCartasRecebidasAgente1);
        listaCartasAgente2TemNaMao = new ArrayList<CartasModelo>(listaCartasRecebidasAgente2);

    }

    private void darCartasFile() {
        contadorMaos ++;
        LimpaPontos();
        int PartidaNumber = controlaPartidaAuto.getPartidaNumber();
        int RodadaNumber = controlaPartidaAuto.getRodadaNumber();

        listaCartasRecebidasAgente1 = GeraCartas.Ler(1, PartidaNumber, RodadaNumber);
        listaCartasRecebidasAgente2 = GeraCartas.Ler(2, PartidaNumber, RodadaNumber);


        if (contadorMao == 0 || contadorMao <= 3) {
            contadorMao++;
        } else {
            contadorMao = 0;
            controlaPartidaAuto.setContadorRodadas(controlaPartidaAuto.getContadorRodadas() + 1);
        }

        listaCartasAgente1TemNaMao = new ArrayList<CartasModelo>(listaCartasRecebidasAgente1);
        listaCartasAgente2TemNaMao = new ArrayList<CartasModelo>(listaCartasRecebidasAgente2);

    }

    private void LimpaPontos() {
        listaCartasRecebidasAgente1.clear();
        listaCartasRecebidasAgente2.clear();
        listaCartasAgente1TemNaMao.clear();
        listaCartasAgente2TemNaMao.clear();
        listaCartasJogadasAgente1.clear();
        listaCartasJogadasAgente2.clear();
        listaJogadasChamadas.clear();
        listaJogadasAceitas.clear();
        listaJogadasNaoAceitas.clear();
        listaPontosChamados.clear();
        listaPontosAceitos.clear();
        listPontosNaoAceitos.clear();
    }

    public String getCartas() {
        String cartas = "";
        cartas += "\nRecebida por 1";
        for (CartasModelo c : listaCartasRecebidasAgente1) {
            cartas += "\t\t" + c.getCarta() + "- Imp(" + c.getValorImportancia() + ")-Id(" + c.getId() + ")";
        }
        cartas += "\nRecebida por 2";
        for (CartasModelo c : listaCartasRecebidasAgente2) {
            cartas += "\t\t" + c.getCarta() + "- Imp(" + c.getValorImportancia() + ")-Id(" + c.getId() + ")";
        }
        return cartas;
    }

    public void verificarEstadoJogo() {

        estadoJogo.setQuemJogaAgora(quemJogaAgora());
        estadoJogo.setEmQueRodadaEsta(controlaPartidaAuto.getContadorRodadas());
        estadoJogo.setPontuacaoAgente(controlaPartidaAuto.getPontosAgente1());
        estadoJogo.setPontuacaoHumano(controlaPartidaAuto.getPontosAgente2());
        estadoJogo.setQualEhAmao(contadorMao);
        estadoJogo.setContadorJogadas(contadorJogadas);
        estadoJogo.setListaJogadasChamadas(listaJogadasChamadas);
        estadoJogo.setListaPontosChamados(listaPontosChamados);
        estadoJogo.setListaJogadasAceitas(listaJogadasAceitas);
        estadoJogo.setListaPontosAceitos(listaPontosAceitos);
        estadoJogo.setAgenteTemFlor(verificarSeTemFlor(1));
        estadoJogo.setHumanoTemFlor(verificarSeTemFlor(2));
    }

    public int quemJogaAgora() {

        int quemJoga = 0;
        if (ultimoAjogar == 0 && listaCartasJogadasAgente1.isEmpty() && listaCartasJogadasAgente2.isEmpty()) {
            quemJoga = controlaPartidaAuto.getQuemEhMao();
            armazenaTentosInicioDaRodada();
            setaIndexacoesAgentes();
        } else if (listaCartasJogadasAgente1.size() == 1 && listaCartasJogadasAgente2.isEmpty()) {
            quemJoga = 2;
            setaIndexacoesAgentes();
        } else if (listaCartasJogadasAgente1.isEmpty() && listaCartasJogadasAgente2.size() == 1) {
            quemJoga = 1;
            setaIndexacoesAgentes();
        }
        // mÃƒÂ£o 2
        else if (quemGanhouAPrimeiraMao() == 1 && listaCartasJogadasAgente1.size() == 1
                && listaCartasJogadasAgente2.size() == 1) {
            quemJoga = 1;
        } else if (quemGanhouAPrimeiraMao() == 2 && listaCartasJogadasAgente1.size() == 1
                && listaCartasJogadasAgente2.size() == 1) {
            quemJoga = 2;
        } else if (quemGanhouAPrimeiraMao() == 0 && listaCartasJogadasAgente1.size() == 1
                && listaCartasJogadasAgente2.size() == 1 && ultimoAjogar == 1) {
            quemJoga = 2;
        } else if (quemGanhouAPrimeiraMao() == 0 && listaCartasJogadasAgente1.size() == 1
                && listaCartasJogadasAgente2.size() == 1 && ultimoAjogar == 2) {
            quemJoga = 1;
        } else if (listaCartasJogadasAgente1.size() == 2 && listaCartasJogadasAgente2.size() == 1) {
            quemJoga = 2;
        } else if (listaCartasJogadasAgente1.size() == 1 && listaCartasJogadasAgente2.size() == 2) {
            quemJoga = 1;
        }
        // mÃƒÂ£o 3
        else if (quemGanhouASegundaMao() == 1 && listaCartasJogadasAgente1.size() == 2
                && listaCartasJogadasAgente2.size() == 2) {
            quemJoga = 1;
        } else if (quemGanhouASegundaMao() == 2 && listaCartasJogadasAgente1.size() == 2
                && listaCartasJogadasAgente2.size() == 2) {
            quemJoga = 2;
        } else if (quemGanhouASegundaMao() == 0 && listaCartasJogadasAgente1.size() == 2
                && listaCartasJogadasAgente2.size() == 2 && ultimoAjogar == 2) {
            quemJoga = 1;
        } else if (quemGanhouASegundaMao() == 0 && listaCartasJogadasAgente1.size() == 2
                && listaCartasJogadasAgente2.size() == 2 && ultimoAjogar == 1) {
            quemJoga = 2;
        } else if (listaCartasJogadasAgente1.size() == 3 && listaCartasJogadasAgente2.size() == 2) {
            quemJoga = 2;
        } else if (contadorMao == 3 && listaCartasJogadasAgente1.size() == 2 && listaCartasJogadasAgente2.size() == 3) {
            quemJoga = 1;
        }
        return quemJoga;
    }

    private Integer quemGanhouAPrimeiraMao() {

        Integer quemGanhou = null;
        int valorImportanciaPrimeiraCartaAgente1 = 0;
        int valorImportanciaPrimeiraCartaAgente2 = 0;

        if (listaCartasJogadasAgente1.size() >= 1) {
            valorImportanciaPrimeiraCartaAgente1 = listaCartasJogadasAgente1.get(0).getValorImportancia();
        }

        if (listaCartasJogadasAgente2.size() >= 1) {
            valorImportanciaPrimeiraCartaAgente2 = listaCartasJogadasAgente2.get(0).getValorImportancia();
        }

        if (listaCartasJogadasAgente1.size() >= 1 && listaCartasJogadasAgente2.size() >= 1) {
            if (valorImportanciaPrimeiraCartaAgente1 == valorImportanciaPrimeiraCartaAgente2)
                quemGanhou = 0;
            if (valorImportanciaPrimeiraCartaAgente1 > valorImportanciaPrimeiraCartaAgente2)
                quemGanhou = 1;
            if (valorImportanciaPrimeiraCartaAgente2 > valorImportanciaPrimeiraCartaAgente1)
                quemGanhou = 2;
        }

        if (listaCartasJogadasAgente1.size() == 1 && listaCartasJogadasAgente2.size() == 1) {
            GanhadorPrimeira = quemGanhou;
        }

        return quemGanhou;
    }

    private Integer quemGanhouASegundaMao() {

        Integer quemGanhou = null;
        int valorImportanciaSegundaCartaJogadaAgente1 = 0;
        int valorImportanciaSegundaCartaJogadaAgente2 = 0;

        if (listaCartasJogadasAgente1.size() >= 2) {
            valorImportanciaSegundaCartaJogadaAgente1 = listaCartasJogadasAgente1.get(1).getValorImportancia();
        }

        if (listaCartasJogadasAgente2.size() >= 2) {
            valorImportanciaSegundaCartaJogadaAgente2 = listaCartasJogadasAgente2.get(1).getValorImportancia();
        }

        if (listaCartasJogadasAgente1.size() >= 2 && listaCartasJogadasAgente2.size() >= 2) {
            if (valorImportanciaSegundaCartaJogadaAgente1 == valorImportanciaSegundaCartaJogadaAgente2)
                quemGanhou = 0;
            if (valorImportanciaSegundaCartaJogadaAgente1 > valorImportanciaSegundaCartaJogadaAgente2)
                quemGanhou = 1;
            if (valorImportanciaSegundaCartaJogadaAgente2 > valorImportanciaSegundaCartaJogadaAgente1)
                quemGanhou = 2;
        }
        if (listaCartasJogadasAgente1.size() == 2 && listaCartasJogadasAgente2.size() == 2) {
            GanhadorSegunda = quemGanhou;
        }

        return quemGanhou;
    }

    private Integer quemGanhouATerceiraMao() {

        Integer quemGanhou = null;
        int valorImportanciaTerceiraCartaJogadaAgente1 = 0;
        int valorImportanciaTerceiraCartaJogadaAgente2 = 0;
        if (listaCartasJogadasAgente1.size() >= 3) {
            valorImportanciaTerceiraCartaJogadaAgente1 = listaCartasJogadasAgente1.get(2).getValorImportancia();
        }
        if (listaCartasJogadasAgente2.size() >= 3) {
            valorImportanciaTerceiraCartaJogadaAgente2 = listaCartasJogadasAgente2.get(2).getValorImportancia();
        }
        if (listaCartasJogadasAgente1.size() >= 3 && listaCartasJogadasAgente2.size() >= 3) {
            if (valorImportanciaTerceiraCartaJogadaAgente1 == valorImportanciaTerceiraCartaJogadaAgente2)
                quemGanhou = 0;
            if (valorImportanciaTerceiraCartaJogadaAgente1 > valorImportanciaTerceiraCartaJogadaAgente2)
                quemGanhou = 1;
            if (valorImportanciaTerceiraCartaJogadaAgente2 > valorImportanciaTerceiraCartaJogadaAgente1)
                quemGanhou = 2;
        }
        if (listaCartasJogadasAgente1.size() == 3 && listaCartasJogadasAgente2.size() == 3) {
            GanhadorTerceira = quemGanhou;
        }
        return quemGanhou;
    }

    public void armazenaTentosInicioDaRodada() {

        controlaPartidaAuto.setPontosAnterioresAgente1(controlaPartidaAuto.getPontosAgente1());
        controlaPartidaAuto.setPontosAnterioresAgente2(controlaPartidaAuto.getPontosAgente2());
    }

    public boolean getPodeChamarPontosAinda() {
        if (pontosJaChamados == false)
            return true && (getQualEhAmao() == 1);
        else
            return false;
    }

    public void setPontosJaChamados(boolean jaFoiChamado) {
        pontosJaChamados = jaFoiChamado;
    }

    public int quemPodeChamarJogoAgora() {
        if (listaJogadasChamadas.isEmpty())
            return 0;
        if (!listaJogadasChamadas.isEmpty()
                && listaJogadasChamadas.get(listaJogadasChamadas.size() - 1).getQuemChamou() == 2)
            return 1;
        if (!listaJogadasChamadas.isEmpty()
                && listaJogadasChamadas.get(listaJogadasChamadas.size() - 1).getQuemChamou() == 1)
            return 2;
        return 0;
    }

    public boolean temJogadaAinda() {
        if (verificaInformacoesJogada("ValeQuatro") != null)
            return false;
        else
            return true;
    }

    public List<CartasModelo> getListaCartasJogadasAgente1() {
        return listaCartasJogadasAgente1;
    }

    public List<CartasModelo> getListaCartasJogadasAgente2() {
        return listaCartasJogadasAgente2;
    }

    private QuemGanhouPontosModelo setaQuemGanhou(int pontuacaoVencedor, int pontuacaoPerdedor, int ganhador) {
        QuemGanhouPontosModelo quemGanhou = new QuemGanhouPontosModelo();
        quemGanhou.setPontuacaoQuePossuiGanhador(pontuacaoVencedor);
        quemGanhou.setPontuacaoQuepossuiQuemPerdeu(pontuacaoPerdedor);
        quemGanhou.setQuemGanhou(ganhador);
        return quemGanhou;
    }

    private int QuemGanhouOsPontos() {
        if (calcularPontosEnvidoAgente(1) > calcularPontosEnvidoAgente(2))
            return 1;
        else if (calcularPontosEnvidoAgente(1) < calcularPontosEnvidoAgente(2))
            return 2;
        else
            return controlaPartidaAuto.getQuemEhMao();

    }

    private int calcularPontosEnvidoAgente(int AgenteId) {
        int somaDosPontos = 0;
        if (verificarSeAgenteTemPontosParaEnvido(AgenteId)) {
            if (AgenteId == 1) {
                if (listaCartasRecebidasAgente1.get(0).getNaipe().equals(listaCartasRecebidasAgente1.get(1).getNaipe()))
                    somaDosPontos = listaCartasRecebidasAgente1.get(0).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente1.get(1).getValorQueContaParaOenvido() + 20;
                if (listaCartasRecebidasAgente1.get(0).getNaipe().equals(listaCartasRecebidasAgente1.get(2).getNaipe()))
                    somaDosPontos = listaCartasRecebidasAgente1.get(0).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente1.get(2).getValorQueContaParaOenvido() + 20;
                if (listaCartasRecebidasAgente1.get(1).getNaipe().equals(listaCartasRecebidasAgente1.get(2).getNaipe()))
                    somaDosPontos = listaCartasRecebidasAgente1.get(1).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente1.get(2).getValorQueContaParaOenvido() + 20;
            } else {
                if (listaCartasRecebidasAgente2.get(0).getNaipe().equals(listaCartasRecebidasAgente2.get(1).getNaipe()))
                    somaDosPontos = listaCartasRecebidasAgente2.get(0).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente2.get(1).getValorQueContaParaOenvido() + 20;
                if (listaCartasRecebidasAgente2.get(0).getNaipe().equals(listaCartasRecebidasAgente2.get(2).getNaipe()))
                    somaDosPontos = listaCartasRecebidasAgente2.get(0).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente2.get(2).getValorQueContaParaOenvido() + 20;
                if (listaCartasRecebidasAgente2.get(1).getNaipe().equals(listaCartasRecebidasAgente2.get(2).getNaipe()))
                    somaDosPontos = listaCartasRecebidasAgente2.get(1).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente2.get(2).getValorQueContaParaOenvido() + 20;
            }
        } else {
            if (AgenteId == 1) {
                for (CartasModelo carta : listaCartasRecebidasAgente1) {
                    if (carta.getValorQueContaParaOenvido() > somaDosPontos)
                        somaDosPontos = carta.getValorQueContaParaOenvido();
                }
            } else {
                for (CartasModelo carta : listaCartasRecebidasAgente2) {
                    if (carta.getValorQueContaParaOenvido() > somaDosPontos)
                        somaDosPontos = carta.getValorQueContaParaOenvido();
                }
            }
        }
        //System.out.println(AgenteId +"  Pontos Envido  " + somaDosPontos);
        return somaDosPontos;
    }

    private boolean verificarSeAgenteTemPontosParaEnvido(int AgenteId) {
        if (AgenteId == 1) {
            String naipePosicaoUm = listaCartasRecebidasAgente1.get(0).getNaipe();
            String naipePosicaoDois = listaCartasRecebidasAgente1.get(1).getNaipe();
            String naipePosicaoTres = listaCartasRecebidasAgente1.get(2).getNaipe();
            if (naipePosicaoUm.equals(naipePosicaoDois) || naipePosicaoUm.equals(naipePosicaoTres)
                    || naipePosicaoDois.equals(naipePosicaoTres))
                return true;
            return false;
        } else {
            String naipePosicaoUm = listaCartasRecebidasAgente2.get(0).getNaipe();
            String naipePosicaoDois = listaCartasRecebidasAgente2.get(1).getNaipe();
            String naipePosicaoTres = listaCartasRecebidasAgente2.get(2).getNaipe();
            if (naipePosicaoUm.equals(naipePosicaoDois) || naipePosicaoUm.equals(naipePosicaoTres)
                    || naipePosicaoDois.equals(naipePosicaoTres))
                return true;
            return false;
        }
    }

    public boolean IrAoBaralho(int quemJoga) {
        boolean desiste = false;
        if (quemJoga == 1)
            desiste = trucoCbr_Agente1.irAoBaralho(atualizarConsultaBaralho(quemJoga, 1), contadorMao);
        else
            desiste = trucoCbr_Agente2.irAoBaralho(atualizarConsultaBaralho(quemJoga, 1), contadorMao);
        if (desiste) {
            System.out.println("[IR_BARALHO] Player" + quemJoga);
        }
        return desiste;
    }

    public boolean temPontosParaSerChamados() {
        verificarEstadoJogo();
        return !estadoJogo.listaPontosQuePodemSerChamados().isEmpty();
    }

    public void quemFoiBaralho(int quem, int mao) {
        quemBaralho = new Integer(quem);
        quandoBaralho = new Integer(mao);
    }

    public QuemGanhouRodadaModelo pontuaQuemGanhouArodada() {
        QuemGanhouRodadaModelo quemGanhouArodada = new QuemGanhouRodadaModelo(0, 0, 0);
        String ultimaJogadaChamada = "";
        String ultimaJogadaAceita = "";
        int quemChamouAultimaJogada = 0;
        String ultimaJogadaNaoAceita = "";
        if (!listaJogadasChamadas.isEmpty())
            ultimaJogadaChamada = listaJogadasChamadas.get(listaJogadasChamadas.size() - 1).getJogadaChamada();
        if (!listaJogadasAceitas.isEmpty())
            ultimaJogadaAceita = listaJogadasAceitas.get(listaJogadasAceitas.size() - 1).getJogadaAceita();
        if (!listaJogadasChamadas.isEmpty())
            quemChamouAultimaJogada = listaJogadasChamadas.get(listaJogadasChamadas.size() - 1).getQuemChamou();
        if (!listaJogadasNaoAceitas.isEmpty())
            ultimaJogadaNaoAceita = listaJogadasNaoAceitas.get(listaJogadasNaoAceitas.size() - 1).getJogadaNaoAceita();
        // caso jogada nÃƒÂ£o seja aceita pelo humÃƒÂ¢no pontua rÃƒÂ´bo
        if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita) && ultimaJogadaChamada.equalsIgnoreCase("Truco")
                && quemChamouAultimaJogada == 1) {
            controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 1);
            quemGanhouArodada = new QuemGanhouRodadaModelo(1, 1, 2);
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ReTruco") && quemChamouAultimaJogada == 1) {
            controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 2);
            quemGanhouArodada = new QuemGanhouRodadaModelo(1, 2, 2);
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ValeQuatro") && quemChamouAultimaJogada == 1) {
            controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 3);
            quemGanhouArodada = new QuemGanhouRodadaModelo(1, 3, 2);
        }
        // caso a jogada nÃƒÂ£o seja aceita pelo rÃƒÂ´bo pontua humÃƒÂ¢no
        else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("Truco") && quemChamouAultimaJogada == 2) {
            controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 1);
            quemGanhouArodada = new QuemGanhouRodadaModelo(2, 1, 1);
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ReTruco") && quemChamouAultimaJogada == 2) {
            controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 2);
            quemGanhouArodada = new QuemGanhouRodadaModelo(2, 2, 1);
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ValeQuatro") && quemChamouAultimaJogada == 2) {
            controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 3);
            quemGanhouArodada = new QuemGanhouRodadaModelo(2, 3, 1);
        }
        // jogador 1 ganhou jogando quieto
        else if (quemGanhouARodada() == 1 && ultimaJogadaChamada.equalsIgnoreCase("")) {
            controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 1);
            quemGanhouArodada = new QuemGanhouRodadaModelo(1, 1, 0);
        } else if (quemGanhouARodada() == 1 && ultimaJogadaChamada.equalsIgnoreCase("Truco")
                && ultimaJogadaAceita.equalsIgnoreCase("Truco")) {
            controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 2);
            quemGanhouArodada = new QuemGanhouRodadaModelo(1, 2, 0);
        } else if (quemGanhouARodada() == 1 && ultimaJogadaChamada.equalsIgnoreCase("ReTruco")
                && ultimaJogadaAceita.equalsIgnoreCase("ReTruco")) {
            controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 3);
            quemGanhouArodada = new QuemGanhouRodadaModelo(1, 3, 0);
        } else if (quemGanhouARodada() == 1 && ultimaJogadaChamada.equalsIgnoreCase("ValeQuatro")
                && ultimaJogadaAceita.equalsIgnoreCase("ValeQuatro")) {
            controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 4);
            quemGanhouArodada = new QuemGanhouRodadaModelo(1, 4, 0);
        }
        // pontua caso o humÃƒÂ¢no venÃƒÂ§a a rodada
        else if (quemGanhouARodada() == 2 && ultimaJogadaChamada.equalsIgnoreCase("")) {
            controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 1);
            quemGanhouArodada = new QuemGanhouRodadaModelo(2, 1, 0);
        } else if (quemGanhouARodada() == 2 && ultimaJogadaChamada.equalsIgnoreCase("Truco")
                && ultimaJogadaAceita.equalsIgnoreCase("Truco")) {
            controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 2);
            quemGanhouArodada = new QuemGanhouRodadaModelo(2, 2, 0);
        } else if (quemGanhouARodada() == 2 && ultimaJogadaChamada.equalsIgnoreCase("ReTruco")
                && ultimaJogadaAceita.equalsIgnoreCase("ReTruco")) {
            controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 3);
            quemGanhouArodada = new QuemGanhouRodadaModelo(2, 3, 0);
        } else if (quemGanhouARodada() == 2 && ultimaJogadaChamada.equalsIgnoreCase("ValeQuatro")
                && ultimaJogadaAceita.equalsIgnoreCase("ValeQuatro")) {
            controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 4);
            quemGanhouArodada = new QuemGanhouRodadaModelo(2, 4, 0);
        }
        MarcaTentosRodada(quemGanhouArodada);
        return quemGanhouArodada;
    }

    private void MarcaTentosRodada(QuemGanhouRodadaModelo quemGanhouArodada) {
        tentosRodada.setTruco(quemGanhouArodada.getQuemGanhou(), quemGanhouArodada.getQuantosPontosGanhou());
    }

    public Integer quemGanhouARodada() {
        if (quemBaralho != null) {
            if (quemBaralho == 2)
                return 1;
            if (quemBaralho == 1)
                return 2;
        }
        // combinaÃƒÂ§ÃƒÂµes em que o rÃƒÂ´bo 1 ganha
        if (quemGanhouAPrimeiraMao() == 1 && quemGanhouASegundaMao() == 1)
            return 1;
        if (quemGanhouAPrimeiraMao() == 0 && quemGanhouASegundaMao() == 1)
            return 1;
        if (quemGanhouAPrimeiraMao() == 1 && quemGanhouASegundaMao() == 0)
            return 1;
        if (quemGanhouAPrimeiraMao() == 1 && quemGanhouATerceiraMao() == 1)
            return 1;
        if (quemGanhouASegundaMao() == 1 && quemGanhouATerceiraMao() == 1)
            return 1;

        // combinaÃƒÂ§ÃƒÂµes em que o Agente 2 ganha
        if (quemGanhouAPrimeiraMao() == 2 && quemGanhouASegundaMao() == 2)
            return 2;
        if (quemGanhouAPrimeiraMao() == 0 && quemGanhouASegundaMao() == 2)
            return 2;
        if (quemGanhouAPrimeiraMao() == 2 && quemGanhouASegundaMao() == 0)
            return 2;
        if (quemGanhouAPrimeiraMao() == 2 && quemGanhouATerceiraMao() == 2)
            return 2;
        if (quemGanhouASegundaMao() == 2 && quemGanhouATerceiraMao() == 2)
            return 2;

        if (quemGanhouATerceiraMao() != null) {

            if (quemGanhouAPrimeiraMao() == 0 && quemGanhouASegundaMao() == 0 && quemGanhouATerceiraMao() == 1)
                return 1;
            if (quemGanhouAPrimeiraMao() == 0 && quemGanhouASegundaMao() == 0 && quemGanhouATerceiraMao() == 2)
                return 2;
            if (quemGanhouAPrimeiraMao() == 1 && quemGanhouASegundaMao() == 2 && quemGanhouATerceiraMao() == 0)
                return 1;
            if (quemGanhouAPrimeiraMao() == 2 && quemGanhouASegundaMao() == 1 && quemGanhouATerceiraMao() == 0)
                return 2;
            if (quemGanhouAPrimeiraMao() == 0 && quemGanhouASegundaMao() == 0 && quemGanhouATerceiraMao() == 0)
                return controlaPartidaAuto.getQuemEhMao();
        }
        return 0;
    }

    public int getQualEhAmao() {
        verificarEstadoJogo();
        int qualEhMao = estadoJogo.getQualEhAmao();
        return qualEhMao;
    }

    public int retornaQuemChamouEnvido() {
        if (retornaPontosChamado("Envido") != null)
            return retornaPontosChamado("Envido").getQuemChamou();
        else
            return 0;
    }

    public int retornaQuemChamouRealEnvido() {
        if (retornaPontosChamado("RealEnvido") != null)
            return retornaPontosChamado("RealEnvido").getQuemChamou();
        else
            return 0;
    }

    public int retornaQuemChamouFaltaEnvido() {
        if (retornaPontosChamado("FaltaEnvido") != null)
            return retornaPontosChamado("FaltaEnvido").getQuemChamou();
        else
            return 0;
    }

    public JogadasChamadasModelo retornaPontosChamado(String qualPonto) {
        JogadasChamadasModelo jogadaChamada = null;
        for (JogadasChamadasModelo pontoChamado : listaPontosChamados) {
            if (pontoChamado.getJogadaChamada().equalsIgnoreCase(qualPonto)) {
                jogadaChamada = new JogadasChamadasModelo();
                jogadaChamada.setJogadaChamada(pontoChamado.getJogadaChamada());
                jogadaChamada.setQuemChamou(pontoChamado.getQuemChamou());
            }
        }
        return jogadaChamada;
    }

    public JogadasChamadasModelo verificaInformacoesJogada(String jogada) {
        JogadasChamadasModelo retorno = null;
        for (JogadasChamadasModelo jogadaChamada : listaJogadasChamadas) {
            if (jogadaChamada.getJogadaChamada().equalsIgnoreCase(jogada))
                retorno = jogadaChamada;
        }
        return retorno;
    }

    private TrucoDescription atualizaScoutBlefes() {
        TrucoDescription description = new TrucoDescription();

        description.setBluff1Failure(controlaPartidaAuto.getCountBluff1Failure());
        description.setBluff1Success(controlaPartidaAuto.getCountBluff1Success());

        description.setBluff2Failure(controlaPartidaAuto.getCountBluff2Failure());
        description.setBluff2Success(controlaPartidaAuto.getCountBluff2Success());
        description.setBluff3Failure(controlaPartidaAuto.getCountBluff3Failure());
        description.setBluff3Success(controlaPartidaAuto.getCountBluff3Success());
        description.setBluff4Failure(controlaPartidaAuto.getCountBluff4Failure());
        description.setBluff4Success(controlaPartidaAuto.getCountBluff4Success());
        description.setBluff5Failure(controlaPartidaAuto.getCountBluff5Failure());
        description.setBluff5Success(controlaPartidaAuto.getCountBluff5Success());
        description.setBluff6Failure(controlaPartidaAuto.getCountBluff6Failure());
        description.setBluff6Success(controlaPartidaAuto.getCountBluff6Success());
        description.setBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent());
        description.setBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent());
        description.setBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent());
        description.setBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent());
        description.setBluff5Opponent(controlaPartidaAuto.getCountBluff5Opponent());
        description.setBluff6Opponent(controlaPartidaAuto.getCountBluff6Opponent());

        description.setBluff1ShowDown(controlaPartidaAuto.getCountBluff1ShowDown());
        description.setBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown());
        description.setBluff3ShowDown(controlaPartidaAuto.getCountBluff3ShowDown());
        description.setBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown());
        description.setBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown());
        description.setBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown());

        return description;
    }

    private TrucoDescription atualizarConsultaEnvido(int AgenteId, int Como) {

        if (AgenteId == 1)
            return new AtualizarConsultas().atualizaConsultaEnvidoRobo(controlaPartidaAuto.getQuemEhMao(),
                    calcularPontosEnvidoAgente(1), listaCartasJogadasAgente2, controlaPartidaAuto.getPontosAnterioresAgente1(), controlaPartidaAuto.getPontosAnterioresAgente2());
        else
            return new AtualizarConsultas().atualizaConsultaEnvidoRobo(
                    inverterJogadores(controlaPartidaAuto.getQuemEhMao()), calcularPontosEnvidoAgente(2),
                    listaCartasJogadasAgente1,controlaPartidaAuto.getPontosAnterioresAgente2(), controlaPartidaAuto.getPontosAnterioresAgente1());

    }

    private TrucoDescription atualizarConsultaRealEnvido(int AgenteId, int Como) {

        if (AgenteId == 1)
            return new AtualizarConsultas().atualizaConsultaRealEnvidoRobo(controlaPartidaAuto.getQuemEhMao(),
                    calcularPontosEnvidoAgente(1), listaCartasJogadasAgente2, retornaQuemChamouEnvido(), controlaPartidaAuto.getPontosAnterioresAgente1(), controlaPartidaAuto.getPontosAnterioresAgente2());
        else
            return new AtualizarConsultas().atualizaConsultaRealEnvidoRobo(
                    inverterJogadores(controlaPartidaAuto.getQuemEhMao()), calcularPontosEnvidoAgente(2),
                    listaCartasJogadasAgente1, inverterJogadores(retornaQuemChamouEnvido()), controlaPartidaAuto.getPontosAnterioresAgente2(), controlaPartidaAuto.getPontosAnterioresAgente1());

    }

    private TrucoDescription atualizarConsultaFaltaEnvido(int AgenteId, int Como) {
        // como ==1 cria consulta como robo
        // como ==2 cria consulta como humano

        if (AgenteId == 1)
            return new AtualizarConsultas().atualizaConsultaFaltaEnvidoRobo(controlaPartidaAuto.getQuemEhMao(),
                    calcularPontosEnvidoAgente(1), listaCartasJogadasAgente2, retornaQuemChamouEnvido(),
                    retornaQuemChamouRealEnvido(), controlaPartidaAuto.getPontosAgente1(),
                    controlaPartidaAuto.getPontosAgente2());
        else
            return new AtualizarConsultas().atualizaConsultaFaltaEnvidoRobo(
                    inverterJogadores(controlaPartidaAuto.getQuemEhMao()), calcularPontosEnvidoAgente(2),
                    listaCartasJogadasAgente1, inverterJogadores(retornaQuemChamouEnvido()),
                    inverterJogadores(retornaQuemChamouRealEnvido()), controlaPartidaAuto.getPontosAgente2(),
                    controlaPartidaAuto.getPontosAgente1());

    }

    private TrucoDescription atualizarConsultaBaralho(int AgenteId, int Como) {

        if (AgenteId == 1)
            return new AtualizarConsultas().atualizaConsultaBaralhoRobo(controlaPartidaAuto.getQuemEhMao(),
                    listaCartasRecebidasAgente1.get(0).getId(), listaCartasRecebidasAgente1.get(1).getId(),
                    listaCartasRecebidasAgente1.get(2).getId(), quemGanhouAPrimeiraMao(), quemGanhouASegundaMao(),
                    quemGanhouATerceiraMao(), listaCartasJogadasAgente1, listaCartasJogadasAgente2,
                    listaJogadasChamadas, contadorMao, controlaPartidaAuto.getPontosAnterioresAgente1(), controlaPartidaAuto.getPontosAnterioresAgente2());
        else
            return new AtualizarConsultas().atualizaConsultaBaralhoRobo(
                    inverterJogadores(controlaPartidaAuto.getQuemEhMao()), listaCartasRecebidasAgente2.get(0).getId(),
                    listaCartasRecebidasAgente2.get(1).getId(), listaCartasRecebidasAgente2.get(2).getId(),
                    inverterJogadores(quemGanhouAPrimeiraMao()), inverterJogadores(quemGanhouASegundaMao()),
                    inverterJogadores(quemGanhouATerceiraMao()), listaCartasJogadasAgente2, listaCartasJogadasAgente1,
                    listaJogadasChamadas, contadorMao, controlaPartidaAuto.getPontosAnterioresAgente2(), controlaPartidaAuto.getPontosAnterioresAgente1());

    }

    private int retornaCartaJogada(int carta, int Agente) {
        if (Agente == 1) {
            if (contadorMao >= 1 && listaCartasJogadasAgente1.size() >= 1 && carta == 1) {
                Integer id = listaCartasJogadasAgente1.get(0).getId();
                if (id != null)
                    return id;
                else
                    return 0;
            }
            if (contadorMao >= 2 && listaCartasJogadasAgente1.size() >= 2 && carta == 2) {
                Integer id = listaCartasJogadasAgente1.get(1).getId();
                if (id != null)
                    return id;
                else
                    return 0;
            }
            if (contadorMao >= 3 && listaCartasJogadasAgente1.size() >= 3 && carta == 3) {
                Integer id = listaCartasJogadasAgente1.get(2).getId();
                if (id != null)
                    return id;
                else
                    return 0;
            } else
                return 0;
        } else {
            if (contadorMao >= 1 && listaCartasJogadasAgente2.size() >= 1 && carta == 1) {
                Integer id = listaCartasJogadasAgente2.get(0).getId();
                if (id != null)
                    return id;
                else
                    return 0;
            }
            if (contadorMao >= 2 && listaCartasJogadasAgente2.size() >= 2 && carta == 2) {
                Integer id = listaCartasJogadasAgente2.get(1).getId();
                if (id != null)
                    return id;
                else
                    return 0;
            }
            if (contadorMao >= 3 && listaCartasJogadasAgente2.size() >= 3 && carta == 3) {
                Integer id = listaCartasJogadasAgente2.get(2).getId();
                if (id != null)
                    return id;
                else
                    return 0;
            } else
                return 0;
        }
    }

    private TrucoDescription atualizarConsultaFlor(int AgenteId, int Como) {
        if (Como == 1) {
            if (AgenteId == 1)
                return new AtualizarConsultas().atualizaConsultaFlorRobo(listaCartasRecebidasAgente1);
            else
                return new AtualizarConsultas().atualizaConsultaFlorRobo(listaCartasRecebidasAgente2);
        } else {
            if (AgenteId == 1)
                return new AtualizarConsultas().atualizaConsultaFlorHumano(listaCartasRecebidasAgente1);
            else
                return new AtualizarConsultas().atualizaConsultaFlorHumano(listaCartasRecebidasAgente2);
        }
    }

    private TrucoDescription atualizarConsultaContraFlor(int AgenteId, int Como) {
        if (AgenteId == 1)
            return new AtualizarConsultas().atualizaConsultaContraFlorRobo(calcularPontosFlor(1));
        else
            return new AtualizarConsultas().atualizaConsultaContraFlorRobo(calcularPontosFlor(2));

    }

    private TrucoDescription atualizarConsultaContraFlorResto(int AgenteId, int Como) {

        if (AgenteId == 1)
            return new AtualizarConsultas().atualizaConsultaContraFlorRestoRobo(calcularPontosFlor(1),
                    controlaPartidaAuto.getPontosAgente1(), controlaPartidaAuto.getPontosAgente2());
        else
            return new AtualizarConsultas().atualizaConsultaContraFlorRestoRobo(calcularPontosFlor(2),
                    controlaPartidaAuto.getPontosAgente2(), controlaPartidaAuto.getPontosAgente1());
    }

    private TrucoDescription atualizarConsultaTruco(int AgenteId, int Como) {
        // como ==1 cria consulta como robo
        // como ==2 cria consulta como humano
        int quandoTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getEmQualRodada()
                : 0);
        int quandoRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getEmQualRodada()
                : 0);
        int quandoValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getEmQualRodada()
                : 0);


        if (AgenteId == 1) {
            return new AtualizarConsultas().atualizaConsultaTrucoRobo(controlaPartidaAuto.getQuemEhMao(),
                    listaCartasRecebidasAgente1, quemGanhouAPrimeiraMao(), quemGanhouASegundaMao(),
                    quemGanhouATerceiraMao(), quemChamouJogo("Truco"), quemChamouJogo("ReTruco"),
                    quemChamouJogo("ValeQuatro"), listaCartasJogadasAgente1, listaCartasJogadasAgente2,
                    listaJogadasChamadas, contadorMao, quandoTruco, quandoRetruco, quandoValeQuatro, controlaPartidaAuto.getPontosAnterioresAgente1(), controlaPartidaAuto.getPontosAnterioresAgente2());
        } else {
            return new AtualizarConsultas().atualizaConsultaTrucoRobo(
                    inverterJogadores(controlaPartidaAuto.getQuemEhMao()), listaCartasRecebidasAgente2,
                    inverterJogadores(quemGanhouAPrimeiraMao()), inverterJogadores(quemGanhouASegundaMao()),
                    inverterJogadores(quemGanhouATerceiraMao()), inverterJogadores(quemChamouJogo("Truco")),
                    inverterJogadores(quemChamouJogo("ReTruco")), inverterJogadores(quemChamouJogo("ValeQuatro")),
                    listaCartasJogadasAgente2, listaCartasJogadasAgente1, listaJogadasChamadas, contadorMao,
                    quandoTruco, quandoRetruco, quandoValeQuatro, controlaPartidaAuto.getPontosAnterioresAgente2(), controlaPartidaAuto.getPontosAnterioresAgente1());
        }
    }

    private TrucoDescription atualizarConsultaCarta(int AgenteId, int Como) {

        if (AgenteId == 1)
            return new AtualizarConsultas().atualizaConsultaCartaRobo(controlaPartidaAuto.getQuemEhMao(),
                    listaCartasRecebidasAgente1.get(0).getId(), listaCartasRecebidasAgente1.get(1).getId(),
                    listaCartasRecebidasAgente1.get(2).getId(), listaCartasJogadasAgente1,
                    listaCartasJogadasAgente2);
        else
            return new AtualizarConsultas().atualizaConsultaCartaRobo(
                    inverterJogadores(controlaPartidaAuto.getQuemEhMao()), listaCartasRecebidasAgente2.get(0).getId(),
                    listaCartasRecebidasAgente2.get(1).getId(), listaCartasRecebidasAgente2.get(2).getId(),
                    listaCartasJogadasAgente2, listaCartasJogadasAgente1);

    }

    private int calcularPontosFlor(int AgenteId) {
        int somaDosPontos = 0;
        if (verificarSeTemFlor(AgenteId)) {
            if (AgenteId == 1) {
                if (listaCartasRecebidasAgente1.get(0).getNaipe().equals(listaCartasRecebidasAgente1.get(1).getNaipe())
                        && listaCartasRecebidasAgente1.get(0).getNaipe()
                        .equals(listaCartasRecebidasAgente1.get(2).getNaipe()))
                    somaDosPontos = listaCartasRecebidasAgente1.get(0).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente1.get(1).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente1.get(2).getValorQueContaParaOenvido() + 20;
            } else {
                if (listaCartasRecebidasAgente2.get(0).getNaipe().equals(listaCartasRecebidasAgente2.get(1).getNaipe())
                        && listaCartasRecebidasAgente2.get(0).getNaipe()
                        .equals(listaCartasRecebidasAgente2.get(2).getNaipe()))
                    somaDosPontos = listaCartasRecebidasAgente2.get(0).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente2.get(1).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente2.get(2).getValorQueContaParaOenvido() + 20;
            }
        }
        return somaDosPontos;
    }

    private boolean verificarSeTemFlor(int AgenteId) {
        if (AgenteId == 1) {
            if (listaCartasRecebidasAgente1.get(0).getNaipe().equals(listaCartasRecebidasAgente1.get(2).getNaipe())
                    && listaCartasRecebidasAgente1.get(0).getNaipe()
                    .equals(listaCartasRecebidasAgente1.get(1).getNaipe()))
                return true;
            return false;
        } else {
            if (listaCartasRecebidasAgente2.get(0).getNaipe().equals(listaCartasRecebidasAgente2.get(2).getNaipe())
                    && listaCartasRecebidasAgente2.get(0).getNaipe()
                    .equals(listaCartasRecebidasAgente2.get(1).getNaipe()))
                return true;
            return false;
        }
    }

    public int quemChamouJogo(String tipoDeJogoChamado) {

        if (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                && tipoDeJogoChamado.equalsIgnoreCase("Truco")) {
            return verificaInformacoesJogada("Truco").getQuemChamou();

        } else if (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                && tipoDeJogoChamado.equalsIgnoreCase("ReTruco")) {
            return verificaInformacoesJogada("ReTruco").getQuemChamou();

        }
//		else if (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null)
//			return verificaInformacoesJogada("ReTruco").getEmQualRodada();

        else if (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null) {
            return verificaInformacoesJogada("ValeQuatro").getQuemChamou();
        }

        return 0;
    }

    public int currentRodada() {
        if (cartasJogadasMesa.size() <= 2) {
            return 1;
        } else if (cartasJogadasMesa.size() > 2 && cartasJogadasMesa.size() < 5) {
            return 2;
        } else {
            return 3;
        }
    }

    public String ChamarPonto(int AgenteId) {

        double probGanhar = 0.0;
        boolean isOportunidadeBlefe = false;

        if (AgenteId == 1) {

            probGanhar = getProbWin(controlaPartidaAuto.getQuemEhMao() == 1 ? 1 : 0, "ENVIDO");
            isOportunidadeBlefe = (probGanhar < 0.5 || probGanhar > 0.85);

        }

        String pontoQueSeraChamado = "";
        boolean EnvidoTaDisponivel = false;
        boolean RealEnvidoTaDisponivel = false;
        boolean FaltaEnvidoTaDisponivel = false;
        boolean FlorTaDisponivel = false;
        boolean ContraFlorTaDisponivel = false;
        boolean ContraFlorRestoTaDisponivel = false;
        verificarEstadoJogo();
        List<String> JogadasDisp = estadoJogo.listaPontosQuePodemSerChamados();
        if (JogadasDisp.contains("Envido"))
            EnvidoTaDisponivel = true;
        if (JogadasDisp.contains("RealEnvido"))
            RealEnvidoTaDisponivel = true;
        if (JogadasDisp.contains("FaltaEnvido"))
            FaltaEnvidoTaDisponivel = true;
        if (JogadasDisp.contains("ContraFlor"))
            ContraFlorTaDisponivel = true;
        if (JogadasDisp.contains("ContraFlorResto"))
            ContraFlorRestoTaDisponivel = true;
        if (JogadasDisp.contains("Flor"))
            FlorTaDisponivel = true;
        boolean chamarEnvido = false;
        boolean chamarRealEnvido = false;
        boolean chamarFaltaEnvido = false;
        boolean chamarFlor = false;
        boolean chamarContraFlor = false;
        boolean chamarContraFlorResto = false;
        if (FlorTaDisponivel)
            chamarFlor = verificarSeAgenteTemFlor(AgenteId); // chama obrigatoriamente se tiver flor
//			chamarFlor = consultaChamarFlor(AgenteId);				//faz uma consulta
        if (ContraFlorTaDisponivel)
            chamarContraFlor = consultaChamarContraFlor(AgenteId);
        if (ContraFlorRestoTaDisponivel)
            chamarContraFlorResto = consultaChamarContraFlorResto(AgenteId);
        if (FaltaEnvidoTaDisponivel)
            chamarFaltaEnvido = consultaChamarFaltaEnvido(AgenteId);
        if (!chamarFaltaEnvido && RealEnvidoTaDisponivel)
            chamarRealEnvido = consultaChamarRealEnvido(AgenteId);
        if (!chamarFaltaEnvido && !chamarRealEnvido && EnvidoTaDisponivel)
            chamarEnvido = consultaChamarEnvido(AgenteId);

        if (chamarFlor)
            pontoQueSeraChamado = "Flor";
        else if (chamarEnvido && EnvidoTaDisponivel)
            pontoQueSeraChamado = "Envido";
        else if (chamarRealEnvido && RealEnvidoTaDisponivel)
            pontoQueSeraChamado = "RealEnvido";
        else if (chamarFaltaEnvido && FaltaEnvidoTaDisponivel)
            pontoQueSeraChamado = "FaltaEnvido";

        if (chamarContraFlorResto && ContraFlorRestoTaDisponivel)
            pontoQueSeraChamado = "ContraFlorResto";
        else if (chamarContraFlor && ContraFlorTaDisponivel)
            pontoQueSeraChamado = "ContraFlor";


        if (AgenteId == 1) {

            Decision decision = createDefaultDecision(1, 1);
            decision.setProbWin(probGanhar);

            if (trucoCbr_Agente1.isActiveLearning() && !pontoQueSeraChamado.equals("Flor") &&
                    !pontoQueSeraChamado.equals("ContraFlor") && !pontoQueSeraChamado.equals("ContraFlorResto")) {

                if (!pontoQueSeraChamado.equals("")) {

                    if (isOportunidadeBlefe) {

                        if (trucoCbr_Agente1.deveChamarTelaAtivoEmCadaAcaoIndividual("envido", contadorMao,
                                atualizarConsultaEnvido(1,1), getDescriptionAtibutosBlefe())){


                            System.out.println("[ENVIDO] REUSE_POLICY_MOVE: " + pontoQueSeraChamado);

                            //moveType: 0 Flor/ 1 Envido/ 2 Truco/ 3 Play Card
                            LoaderActiveLearning.openActiveLearningScreen(setInfoActiveScreen(pontoQueSeraChamado, 1, probGanhar));
                            System.out.println("[ENVIDO] EXPERT_MOVE: " + (responseActiveLearning != null ? responseActiveLearning : "Keep Reuse Policy"));
                            if (responseActiveLearning != null) {
                                pontoQueSeraChamado = responseActiveLearning;
                            } else {
                                pontoQueSeraChamado = pontoQueSeraChamado;
                            }
                            responseActiveLearning = null;
                            decision.setIsActiveLearning(1);
                        }
                    }

                } else {

                    if (isOportunidadeBlefe) {

                        if (trucoCbr_Agente1.deveChamarTelaAtivoEmCadaAcaoIndividual("envido", contadorMao,
                                atualizarConsultaEnvido(1, 1), getDescriptionAtibutosBlefe())){

                            System.out.println("[ENVIDO] REUSE_POLICY_MOVE: " + "No Call Envido");
                            //moveType: 0 Flor/ 1 Envido/ 2 Truco/ 3 Play Card
                            LoaderActiveLearning.openActiveLearningScreen(setInfoActiveScreen("No Call Envido", 1, probGanhar));
                            System.out.println("[ENVIDO] EXPERT_MOVE: " + (responseActiveLearning != null ? responseActiveLearning : "Keep Reuse Policy"));
                            if (responseActiveLearning != null) {
                                pontoQueSeraChamado = responseActiveLearning;
                            } else {
                                pontoQueSeraChamado = pontoQueSeraChamado;
                            }

                            responseActiveLearning = null;
                            decision.setIsActiveLearning(1);
                        }
                    }

                }
            }

            decision.setDecision(pontoQueSeraChamado.equals("") ? "No Call" : pontoQueSeraChamado);
            controlaPartidaAuto.getMatch().getDecisions().add(decision);
        }


        if (!pontoQueSeraChamado.equals("")) {
            JogadasChamadasModelo pontosChamados = new JogadasChamadasModelo();
            pontosChamados.setJogadaChamada(pontoQueSeraChamado);
            pontosChamados.setQuemChamou(AgenteId);
            pontosChamados.setEmQualRodada(1);
            listaPontosChamados.add(pontosChamados);
            estadoJogo.getEnvidoHistory().add(new Action(pontoQueSeraChamado, AgenteId + ""));

        } else {
            estadoJogo.getEnvidoHistory().add(new Action("No Call", AgenteId + ""));
        }
        System.out.println("[ENVIDO] Player" + AgenteId + " - " + (pontoQueSeraChamado.equals("") ? "No Call" : pontoQueSeraChamado));

        return pontoQueSeraChamado;
    }

    public String chamarJogada(int AgenteId) {

        double probGanhar = 0.0;
        boolean isOportunidadeBlefe = false;
        int Oponente;
        if (AgenteId == 1) {
            Oponente = 2;
            probGanhar = getProbWin(controlaPartidaAuto.getQuemEhMao() == 1 ? 1 : 0, "TRUCO");
            System.out.println("[PROBABILIDADE_TRUCO] --> " + probGanhar);
            isOportunidadeBlefe = (probGanhar < 0.5 || probGanhar > 0.85);
        } else {
            Oponente = 1;
        }

        String jogoQueSeraChamado = "";
        boolean TrucoTaDisponivel = false;
        boolean ReTrucoTaDisponivel = false;
        boolean ValeQuatroDisponivel = false;
        boolean chamarTruco = false;
        boolean chamarReTruco = false;
        boolean chamarValeQuatro = false;
        verificarEstadoJogo();
        List<String> JogadasDisponiveis = estadoJogo.listaJogadasQuePodemSerChamadas();
        for (String jogada : JogadasDisponiveis) {
//			System.out.println("\tJogada disponivel  "+ jogada);
            if (jogada.equalsIgnoreCase("Truco"))
                TrucoTaDisponivel = true;
            if (jogada.equalsIgnoreCase("ReTruco"))
                ReTrucoTaDisponivel = true;
            if (jogada.equalsIgnoreCase("ValeQuatro"))
                ValeQuatroDisponivel = true;
        }
        // faz essa verificaÃƒÂ§ÃƒÂ£o para nÃƒÂ£o arriscar ele ter perdido as duas primeiras
        // mÃƒÂ£os
        // e chamar alguma coisa
        if (!(contadorMao > 2 && quemGanhouAPrimeiraMao() != Oponente && quemGanhouASegundaMao() != Oponente)) {
            if (TrucoTaDisponivel && !ReTrucoTaDisponivel && !ValeQuatroDisponivel)
                chamarTruco = consultaChamarTruco(AgenteId);
            if (ReTrucoTaDisponivel && !ValeQuatroDisponivel)
                chamarReTruco = consultaChamarRetruco(AgenteId);
            if (ValeQuatroDisponivel)
                chamarValeQuatro = consultaChamarValeQuatro(AgenteId);
        }
        if (chamarValeQuatro && ValeQuatroDisponivel)
            jogoQueSeraChamado = "ValeQuatro";
        else if (chamarReTruco && ReTrucoTaDisponivel)
            jogoQueSeraChamado = "ReTruco";
        else if (chamarTruco && TrucoTaDisponivel)
            jogoQueSeraChamado = "Truco";


        if (AgenteId == 1) {

            Decision decision = createDefaultDecision(2, 1);
            decision.setProbWin(probGanhar);

            if (trucoCbr_Agente1.isActiveLearning()) {


                if (!jogoQueSeraChamado.equals("")) {


                    System.out.println(probGanhar);

                    if (isOportunidadeBlefe) {

                        if (trucoCbr_Agente1.deveChamarTelaAtivoEmCadaAcaoIndividual("truco", contadorMao,
                                atualizarConsultaTruco(1,1), getDescriptionTrucoActiveLearning())){

                            System.out.println("[TRUCO] REUSE_POLICY_MOVE: " + jogoQueSeraChamado);

                            //moveType: 0 Flor/ 1 Envido/ 2 Truco/ 3 Play Card
                            LoaderActiveLearning.openActiveLearningScreen(setInfoActiveScreen(jogoQueSeraChamado, 2, probGanhar));
                            System.out.println("[TRUCO] EXPERT_MOVE: " + (responseActiveLearning != null ? responseActiveLearning : "Keep Reuse Policy"));
                            if (responseActiveLearning != null) {
                                jogoQueSeraChamado = responseActiveLearning;
                            } else {
                                jogoQueSeraChamado = jogoQueSeraChamado;
                            }
                            responseActiveLearning = null;
                            decision.setIsActiveLearning(1);
                        }
                    }


                } else {

                    if (isOportunidadeBlefe) {

                        if (trucoCbr_Agente1.deveChamarTelaAtivoEmCadaAcaoIndividual("truco", contadorMao,
                                atualizarConsultaTruco(1, 1), getDescriptionAtibutosBlefe())){

                            System.out.println("[TRUCO] REUSE_POLICY_MOVE: " + "No Call Truco");

                            //System.out.println(jogada + "-" + probGanhar);
                            //mostra_cartas(listaCartasRecebidasAgente1);
                            //moveType: 0 Flor/ 1 Envido/ 2 Truco/ 3 Play Card
                            LoaderActiveLearning.openActiveLearningScreen(setInfoActiveScreen("No Call Truco", 2, probGanhar));
                            System.out.println("[TRUCO] EXPERT_MOVE: " + (responseActiveLearning != null ? responseActiveLearning : "Keep Reuse Policy"));
                            if (responseActiveLearning != null) {
                                jogoQueSeraChamado = responseActiveLearning;
                            } else {
                                jogoQueSeraChamado = jogoQueSeraChamado;
                            }
                            responseActiveLearning = null;
                            decision.setIsActiveLearning(1);
                        }
                    }


                }
            }
            decision.setDecision(jogoQueSeraChamado.equals("") ? "No Call" : jogoQueSeraChamado);
            controlaPartidaAuto.getMatch().getDecisions().add(decision);
        }


        if (!jogoQueSeraChamado.equalsIgnoreCase("")) {
            // Adiciona as jogadas Chamadas com jogador
            JogadasChamadasModelo jogadasChamadas = new JogadasChamadasModelo();
            jogadasChamadas.setJogadaChamada(jogoQueSeraChamado);
            jogadasChamadas.setQuemChamou(AgenteId);
            jogadasChamadas.setEmQualRodada(contadorMao);
            listaJogadasChamadas.add(jogadasChamadas);

            estadoJogo.getTrucoHistory().add(new Action(jogoQueSeraChamado, AgenteId + "",
                    currentRodada()));

            // adiciona Jogadas que podem ser aceitas pelo jogador oponente
            JogadasQuePodemSerAceitasModelo jogadasQuePodemSerAceitasModelo = new JogadasQuePodemSerAceitasModelo();
            jogadasQuePodemSerAceitasModelo.setJogadaQuePodeSerAceita(jogoQueSeraChamado);
            if (AgenteId == 1)
                jogadasQuePodemSerAceitasModelo.setQuemPodeAceitar(2);
            else
                jogadasQuePodemSerAceitasModelo.setQuemPodeAceitar(1);
        } else {
            estadoJogo.getTrucoHistory().add(new Action("No Call", AgenteId + "",
                    currentRodada()));
        }

        System.out.println("[TRUCO] Player" + AgenteId + " - " + (jogoQueSeraChamado.equals("") ? "No Call" : jogoQueSeraChamado));
        return jogoQueSeraChamado;
    }

    public boolean AceitarPontos(String pontoSolicitadoParaSerAceito, int AgenteId) {

        double probGanhar = 0.0;
        boolean isOportunidadeBlefe = false;

        if (AgenteId == 1) {
            probGanhar = getProbWin(controlaPartidaAuto.getQuemEhMao() == 1 ? 1 : 0, "ENVIDO");
            isOportunidadeBlefe = (probGanhar < 0.5 || probGanhar > 0.85);
        }


//		System.out.println("Verificando se "+ AgenteId+ " Aceita Ponto");
        boolean aceitouChamadaDePontos = false;
        setPontosJaChamados(true);
//		System.out.println("VERIFICANDO SE "+AgenteId + "  ACeita " + pontoSolicitadoParaSerAceito);
        boolean aceitarEnvido = false;
        boolean aceitarRealEnvido = false;
        boolean aceitarFaltaEnvido = false;
        boolean aceitarFlor = false;
        boolean aceitarContraFlor = false;
        boolean aceitarContraFlorResto = false;
        if (pontoSolicitadoParaSerAceito.equals("Flor"))
            aceitarFlor = true;
        if (pontoSolicitadoParaSerAceito.equals("Envido"))
            aceitarEnvido = consultaAceitarEnvido(AgenteId);
        if (pontoSolicitadoParaSerAceito.equals("RealEnvido"))
            aceitarRealEnvido = consultaAceitarRealEnvido(AgenteId);
        if (pontoSolicitadoParaSerAceito.equals("FaltaEnvido"))
            aceitarFaltaEnvido = consultaAceitarFaltaEnvido(AgenteId);
        if (pontoSolicitadoParaSerAceito.equals("ContraFlor"))
            aceitarContraFlor = consultaAceitarContraFlor(AgenteId);
        if (pontoSolicitadoParaSerAceito.equals("ContraFlorResto"))
            aceitarContraFlor = consultaAceitarContraFlorResto(AgenteId);
        aceitouChamadaDePontos = aceitarFaltaEnvido || aceitarRealEnvido || aceitarEnvido || aceitarFlor
                || aceitarContraFlor || aceitarContraFlorResto;


        if (AgenteId == 1) {

            Decision decision = createDefaultDecision(1, 0);
            decision.setProbWin(probGanhar);

            if (trucoCbr_Agente1.isActiveLearning() && !pontoSolicitadoParaSerAceito.equals("Flor") &&
                    !pontoSolicitadoParaSerAceito.equals("ContraFlor") && !pontoSolicitadoParaSerAceito.equals("ContraFlorResto")) {

                if (isOportunidadeBlefe) {

                    if (trucoCbr_Agente1.deveChamarTelaAtivoEmCadaAcaoIndividual("envido", contadorMao,
                            atualizarConsultaEnvido(1, 1) , getDescriptionAtibutosBlefe())){

                        System.out.println("[ENVIDO] REUSE_POLICY_MOVE: " + (aceitouChamadaDePontos ? "Accept" : "Decline"));

                        //moveType: 0 Flor/ 1 Envido/ 2 Truco/ 3 Play Card
                        LoaderActiveLearning.openActiveLearningScreen(setInfoActiveScreen(aceitouChamadaDePontos ? "Accept" : "Decline", 11, probGanhar));
                        System.out.println("[ENVIDO] EXPERT_MOVE: " + (responseActiveLearning != null ? responseActiveLearning : "Keep Reuse Policy"));
                        if (responseActiveLearning != null) {
                            aceitouChamadaDePontos = responseActiveLearning.startsWith("Accept");
                        } else {
                            aceitouChamadaDePontos = aceitouChamadaDePontos;
                        }
                        responseActiveLearning = null;
                        decision.setIsActiveLearning(1);
                    }
                }

            }

            decision.setDecision((aceitouChamadaDePontos ? "Accept" : "Disclaim"));
            controlaPartidaAuto.getMatch().getDecisions().add(decision);
        }


        if (aceitouChamadaDePontos) {
            JogadasAceitasModelo pontosAceitos = new JogadasAceitasModelo();
            pontosAceitos.setJogadaAceita(pontoSolicitadoParaSerAceito);
            pontosAceitos.setQuemAceitou(AgenteId);
            listaPontosAceitos.add(pontosAceitos);
            estadoJogo.getEnvidoHistory().add(new Action("Accept", AgenteId + ""));
        }
        // se decidir nÃƒÂ£o aceitar
        else {
            estadoJogo.getEnvidoHistory().add(new Action("Declined", AgenteId + ""));
        }

        System.out.println("[ENVIDO] Player" + AgenteId + " - " + (aceitouChamadaDePontos ? "Accept: " : "Disclaim: ") + pontoSolicitadoParaSerAceito);

        return aceitouChamadaDePontos;
    }

    public boolean AceitarJogada(String jogadaParaSerAceita, int AgenteId) {
//		System.out.println("Verificiando se Agente "+ AgenteId + "  Aceita  Jogo " + jogadaParaSerAceita );

        double probGanhar = 0.0;
        boolean isOportunidadeBlefe = false;
        int Oponente;

        if (AgenteId == 1) {
            Oponente = 2;

        } else {
            Oponente = 1;
        }


        String jogoQueSeraChamado = "";
        boolean seraAceita = false;
        boolean aceitarTruco = false;
        boolean aceitarReTruco = false;
        boolean aceitarValeQuatro = false;
        if (jogadaParaSerAceita.equalsIgnoreCase("ValeQuatro")) {
            aceitarValeQuatro = consultaAceitarValeQuatro(AgenteId);
            if (aceitarValeQuatro)
                jogoQueSeraChamado = "ValeQuatro";
        } else if (jogadaParaSerAceita.equalsIgnoreCase("ReTruco")) {
            aceitarReTruco = consultaAceitarRetruco(AgenteId);
            if (aceitarReTruco)
                jogoQueSeraChamado = "ReTruco";
        } else if (jogadaParaSerAceita.equalsIgnoreCase("Truco")) {
            aceitarTruco = consultaAceitarTruco(AgenteId);
            if (aceitarTruco)
                jogoQueSeraChamado = "Truco";
        }

        if (!jogoQueSeraChamado.equalsIgnoreCase(""))
            seraAceita = true;

        if (AgenteId == 1) {

            probGanhar = getProbWin(controlaPartidaAuto.getQuemEhMao() == 1 ? 1 : 0, "TRUCO");
            System.out.println("[PROBABILIDADE_TRUCO] --> " + probGanhar);
            //isOportunidadeBlefe = (probGanhar < 0.26 || probGanhar > 0.85);
            isOportunidadeBlefe = (probGanhar < 0.5 || probGanhar > 0.85);

            Decision decision = createDefaultDecision(2, 0);
            decision.setProbWin(probGanhar);

            if (trucoCbr_Agente1.isActiveLearning()) {

                if (isOportunidadeBlefe) {

                    if (trucoCbr_Agente1.deveChamarTelaAtivoEmCadaAcaoIndividual("truco", contadorMao,
                            atualizarConsultaTruco(1, 1), getDescriptionAtibutosBlefe())){

                        System.out.println("[TRUCO] REUSE_POLICY_MOVE: " + (seraAceita ? "Accept" : "Decline"));

                        //moveType: 0 Flor/ 1 Envido/ 2 Truco/ 3 Play Card
                        LoaderActiveLearning.openActiveLearningScreen(setInfoActiveScreen(seraAceita ? "Accept" : "Decline", 22, probGanhar));
                        System.out.println("[TRUCO] EXPERT_MOVE: " + (responseActiveLearning != null ? responseActiveLearning : "Keep Reuse Policy"));
                        if (responseActiveLearning != null) {
                            seraAceita = responseActiveLearning.startsWith("Accept");
                        } else {
                            seraAceita = seraAceita;
                        }
                        responseActiveLearning = null;
                        decision.setIsActiveLearning(1);
                    }
                }


            }
            decision.setDecision((seraAceita ? "Accept" : "Disclaim"));
            controlaPartidaAuto.getMatch().getDecisions().add(decision);
        }


        if (seraAceita) {
            JogadasAceitasModelo jogadaAceita = new JogadasAceitasModelo();
            jogadaAceita.setJogadaAceita(jogadaParaSerAceita);
            jogadaAceita.setQuemAceitou(AgenteId);
            listaJogadasAceitas.add(jogadaAceita);
            estadoJogo.getTrucoHistory().add(new Action("Accept", AgenteId + "",
                    currentRodada()));
        } else {
            estadoJogo.getTrucoHistory().add(new Action("Declined", AgenteId + "",
                    currentRodada()));
        }

        System.out.println("[TRUCO] Player" + AgenteId + " - " + (seraAceita ? "Accept: " : "Disclaim: ") + jogadaParaSerAceita);

        return seraAceita;
    }

    public void naoAceitouJogada(String jogadaParaSerAceita, int quemRecusou) {
        JogadasNaoAceitasModelo jogadaNaoAceita = new JogadasNaoAceitasModelo();
        jogadaNaoAceita.setJogadaNaoAceita(jogadaParaSerAceita);
        jogadaNaoAceita.setQuemNaoAceitou(quemRecusou);
        listaJogadasNaoAceitas.add(jogadaNaoAceita);
    }

    public void naoAceitouPontos(String jogadaNaoAceita, int AgenteId) {
        JogadasNaoAceitasModelo jogadasNaoAceitas = new JogadasNaoAceitasModelo();
        jogadasNaoAceitas.setJogadaNaoAceita(jogadaNaoAceita);
        jogadasNaoAceitas.setQuemNaoAceitou(AgenteId);
        listPontosNaoAceitos.add(jogadasNaoAceitas);
    }

    private TrucoDescription addInfoScoutBlefe(TrucoDescription cbr) {
        TrucoDescription description = cbr;

        description.setBluff1Failure(controlaPartidaAuto.getCountBluff1Failure());
        description.setBluff1Success(controlaPartidaAuto.getCountBluff1Success());

        description.setBluff2Failure(controlaPartidaAuto.getCountBluff2Failure());
        description.setBluff2Success(controlaPartidaAuto.getCountBluff2Success());
        description.setBluff3Failure(controlaPartidaAuto.getCountBluff3Failure());
        description.setBluff3Success(controlaPartidaAuto.getCountBluff3Success());
        description.setBluff4Failure(controlaPartidaAuto.getCountBluff4Failure());
        description.setBluff4Success(controlaPartidaAuto.getCountBluff4Success());
        description.setBluff5Failure(controlaPartidaAuto.getCountBluff5Failure());
        description.setBluff5Success(controlaPartidaAuto.getCountBluff5Success());
        description.setBluff6Failure(controlaPartidaAuto.getCountBluff6Failure());
        description.setBluff6Success(controlaPartidaAuto.getCountBluff6Success());
        description.setBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent());
        description.setBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent());
        description.setBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent());
        description.setBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent());
        description.setBluff5Opponent(controlaPartidaAuto.getCountBluff5Opponent());
        description.setBluff6Opponent(controlaPartidaAuto.getCountBluff6Opponent());

        description.setBluff1ShowDown(controlaPartidaAuto.getCountBluff1ShowDown());
        description.setBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown());
        description.setBluff3ShowDown(controlaPartidaAuto.getCountBluff3ShowDown());
        description.setBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown());
        description.setBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown());
        description.setBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown());

        description.setCartaAltaRobo(listaCartasRecebidasAgente1.get(0).getId());
        description.setCartaMediaRobo(listaCartasRecebidasAgente1.get(1).getId());
        description.setCartaBaixaRobo(listaCartasRecebidasAgente1.get(2).getId());
        description.setNaipeCartaAltaRobo(listaCartasRecebidasAgente1.get(0).getNaipe());
        description.setNaipeCartaMediaRobo(listaCartasRecebidasAgente1.get(1).getNaipe());
        description.setNaipeCartaBaixaRobo(listaCartasRecebidasAgente1.get(2).getNaipe());



        return description;
    }

    private boolean consultaChamarValeQuatro(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.chamarValeQuatro(addInfoScoutBlefe(atualizarConsultaTruco(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.chamarValeQuatro(addInfoScoutBlefe(atualizarConsultaTruco(2, 1)), contadorMao);
    }

    private boolean consultaChamarRetruco(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.chamarReTruco(addInfoScoutBlefe(atualizarConsultaTruco(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.chamarReTruco(addInfoScoutBlefe(atualizarConsultaTruco(2, 1)), contadorMao);
    }

    private boolean consultaChamarTruco(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.chamarTruco(addInfoScoutBlefe(atualizarConsultaTruco(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.chamarTruco(addInfoScoutBlefe(atualizarConsultaTruco(2, 1)), contadorMao);
    }

    private boolean consultaChamarEnvido(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.chamarEnvido(addInfoScoutBlefe(atualizarConsultaEnvido(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.chamarEnvido(addInfoScoutBlefe(atualizarConsultaEnvido(2, 1)), contadorMao);
    }

    private boolean consultaChamarRealEnvido(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.chamarRealEnvido(addInfoScoutBlefe(atualizarConsultaRealEnvido(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.chamarRealEnvido(addInfoScoutBlefe(atualizarConsultaRealEnvido(2, 1)), contadorMao);
    }

    private boolean consultaChamarFaltaEnvido(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.chamarFaltaEnvido(addInfoScoutBlefe(atualizarConsultaFaltaEnvido(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.chamarFaltaEnvido(addInfoScoutBlefe(atualizarConsultaFaltaEnvido(2, 1)), contadorMao);
    }

    private boolean consultaChamarContraFlor(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.chamarContraFlor(atualizarConsultaContraFlor(1, 1), contadorMao);
        else
            return trucoCbr_Agente2.chamarContraFlor(atualizarConsultaContraFlor(2, 1), contadorMao);
    }

    private boolean consultaChamarContraFlorResto(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.chamarContraFlorResto(atualizarConsultaContraFlorResto(1, 1), contadorMao);
        else
            return trucoCbr_Agente2.chamarContraFlorResto(atualizarConsultaContraFlorResto(2, 1), contadorMao);
    }

    private boolean consultaAceitarValeQuatro(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.aceitarValeQuatro(addInfoScoutBlefe(atualizarConsultaTruco(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.aceitarValeQuatro(addInfoScoutBlefe(atualizarConsultaTruco(2, 1)), contadorMao);
    }

    private boolean consultaAceitarRetruco(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.aceitarReTruco(addInfoScoutBlefe(atualizarConsultaTruco(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.aceitarReTruco(addInfoScoutBlefe(atualizarConsultaTruco(2, 1)), contadorMao);
    }

    private boolean consultaAceitarTruco(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.aceitarTruco(addInfoScoutBlefe(atualizarConsultaTruco(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.aceitarTruco(addInfoScoutBlefe(atualizarConsultaTruco(2, 1)), contadorMao);
    }

    private boolean consultaAceitarEnvido(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.aceitarEnvido(addInfoScoutBlefe(atualizarConsultaEnvido(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.aceitarEnvido(addInfoScoutBlefe(atualizarConsultaEnvido(2, 1)), contadorMao);
    }

    private boolean consultaAceitarRealEnvido(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.aceitarRealEnvido(addInfoScoutBlefe(atualizarConsultaRealEnvido(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.aceitarRealEnvido(addInfoScoutBlefe(atualizarConsultaRealEnvido(2, 1)), contadorMao);
    }

    private boolean consultaAceitarFaltaEnvido(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.aceitarFaltaEnvido(addInfoScoutBlefe(atualizarConsultaFaltaEnvido(1, 1)), contadorMao);
        else
            return trucoCbr_Agente2.aceitarFaltaEnvido(addInfoScoutBlefe(atualizarConsultaFaltaEnvido(2, 1)), contadorMao);
    }

    private boolean consultaAceitarContraFlor(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.aceitarContraFlor(atualizarConsultaContraFlor(1, 1), contadorMao);
        else
            return trucoCbr_Agente2.aceitarContraFlor(atualizarConsultaContraFlor(2, 1), contadorMao);
    }

    private boolean consultaAceitarContraFlorResto(int AgenteId) {
        if (AgenteId == 1)
            return trucoCbr_Agente1.aceitarContraFlorResto(atualizarConsultaContraFlorResto(1, 1), contadorMao);
        else
            return trucoCbr_Agente2.aceitarContraFlorResto(atualizarConsultaContraFlorResto(2, 1), contadorMao);
    }

    public String jogarAgente(int quemJoga, int mao) {
        int idDaCartaQueAgenteDeveJogar = 0;
        boolean CartaVirada;
        double probGanhar = 0.0;
        boolean isOportunidadeBlefe = false;

        if (quemJoga == 1) {

            probGanhar = getProbWin(controlaPartidaAuto.getQuemEhMao() == 1 ? 1 : 0, "TRUCO");
            System.out.println("[PROBABILIDADE_TRUCO] --> " + probGanhar);
            //isOportunidadeBlefe = (probGanhar < 0.26 || probGanhar > 0.85);
            isOportunidadeBlefe = (probGanhar < 0.5 || probGanhar > 0.85);

            if (contadorMao == 1) {
                idDaCartaQueAgenteDeveJogar = trucoCbr_Agente1.primeiraCarta(atualizarConsultaCarta(1, 1), contadorMao);
            } else if (contadorMao == 2) {
                idDaCartaQueAgenteDeveJogar = trucoCbr_Agente1.segundaCarta(atualizarConsultaCarta(1, 1), contadorMao);
            } else if (contadorMao == 3) {
                idDaCartaQueAgenteDeveJogar = trucoCbr_Agente1.terceiraCarta(atualizarConsultaCarta(1, 1), contadorMao);
            }
            CartaVirada = trucoCbr_Agente1.cartaVirada(atualizarConsultaCarta(1, 1), contadorMao);
            CartasModelo CartaJogada = consultaCartaPeloId(idDaCartaQueAgenteDeveJogar, 1);
            CartaJogada.setVirada(CartaVirada);

            Decision decision = createDefaultDecision(3, 1);
            decision.setProbWin(probGanhar);


            if (trucoCbr_Agente1.isActiveLearning()) {

                System.out.println(probGanhar);

                if (isOportunidadeBlefe) {
                    if (trucoCbr_Agente1.deveChamarTelaAtivoEmCadaAcaoIndividual("carta", contadorMao, atualizarConsultaCarta(1, 1), getDescriptionAtibutosBlefe())){

                        System.out.println("[CARD] REUSE_POLICY_MOVE: " +  CartaJogada.getCarta());

                        //moveType: 0 Flor/ 1 Envido/ 2 Truco/ 3 Play Card
                        LoaderActiveLearning.openActiveLearningScreen(setInfoActiveScreen(CartaJogada.getCarta(), 3, probGanhar));
                        System.out.println("[CARD] EXPERT_MOVE: " + (responseActiveLearning != null ? responseActiveLearning : "Keep Reuse Policy"));
                        if (responseActiveLearning != null) {
                            idDaCartaQueAgenteDeveJogar = consultaIdPelaCartaEx(responseActiveLearning, 1);
                            CartaJogada = consultaCartaPeloIdEx(idDaCartaQueAgenteDeveJogar, 1);
                        }
                        responseActiveLearning = null;
                        decision.setIsActiveLearning(1);
                    }
                }

            }

            removerCartaAgente(idDaCartaQueAgenteDeveJogar, 1);
            listaCartasJogadasAgente1.add(CartaJogada);
            cartasJogadasMesa.add(CartaJogada);
            contabilizarJogadaAgente(1);

            estadoJogo.getCardHistory().add(new Action(CartaVirada ? "FaceDown": CartaJogada.getCarta(), quemJoga+"",
                    currentRodada()));

            System.out.println("[PLAY_CARD_" + cartasJogadasMesa.size() + "] Player" + quemJoga + " - " + "Carta: " + (CartaVirada ? "FaceDown": CartaJogada.getCarta()));

            decision.setDecision(getTypeCarta(CartaJogada.getCarta()));
            controlaPartidaAuto.getMatch().getDecisions().add(decision);
        } else {
            if (contadorMao == 1) {
                idDaCartaQueAgenteDeveJogar = trucoCbr_Agente2.primeiraCarta(atualizarConsultaCarta(2, 1), contadorMao);
            } else if (contadorMao == 2) {
                idDaCartaQueAgenteDeveJogar = trucoCbr_Agente2.segundaCarta(atualizarConsultaCarta(2, 1), contadorMao);
            } else if (contadorMao == 3) {
                idDaCartaQueAgenteDeveJogar = trucoCbr_Agente2.terceiraCarta(atualizarConsultaCarta(2, 1), contadorMao);
            }
            CartaVirada = trucoCbr_Agente2.cartaVirada(atualizarConsultaCarta(2, 1), contadorMao);
            removerCartaAgente(idDaCartaQueAgenteDeveJogar, 2);
            CartasModelo CartaJogada = consultaCartaPeloId(idDaCartaQueAgenteDeveJogar, 2);
            CartaJogada.setVirada(CartaVirada);
            listaCartasJogadasAgente2.add(CartaJogada);
            cartasJogadasMesa.add(CartaJogada);
            contabilizarJogadaAgente(2);

            estadoJogo.getCardHistory().add(new Action(CartaVirada ? "FaceDown": CartaJogada.getCarta(), quemJoga+"",
                    currentRodada()));
            System.out.println("[PLAY_CARD_" + cartasJogadasMesa.size() + "] Player" + quemJoga + " - " + "Carta: " + (CartaVirada ? "FaceDown": CartaJogada.getCarta()));
        }
        if (CartaVirada) {
//			System.out.println("JOGOU VIRADA");
        }
        //System.out.println(consultaCartaPeloId(idDaCartaQueAgenteDeveJogar, quemJoga).getCarta());
        return consultaCartaPeloId(idDaCartaQueAgenteDeveJogar, quemJoga).getCarta();
    }

    public void contabilizarJogadaAgente(int AgenteId) {
        contadorJogadas++;
        ultimoAjogar = AgenteId;
        incrementarContadorMao();
    }

    private void incrementarContadorMao() {
        if (contadorJogadas >= 2 && contadorJogadas < 4 && ultimoAjogar == 1 && contadorMao == 2
                && quemJogaAgora() == 1)
            //System.out.println("ver o que esta acont	ecendo");
            if (contadorJogadas < 2)
                contadorMao = 1;
        if (contadorJogadas >= 2 && contadorJogadas < 4)
            contadorMao = 2;
        if (contadorJogadas >= 4 && contadorJogadas <= 6)
            contadorMao = 3;
    }

    public void removerCartaAgente(int id, int AgenteId) {
        CartasModelo carta = null;
        if (AgenteId == 1) {
            for (CartasModelo cartaModelo : listaCartasAgente1TemNaMao) {
                if (cartaModelo.getId() == id) {
                    carta = cartaModelo;
                }
            }
            if (carta != null)
                listaCartasAgente1TemNaMao.remove(carta);
        } else {
            for (CartasModelo cartaModelo : listaCartasAgente2TemNaMao) {
                if (cartaModelo.getId() == id) {
                    carta = cartaModelo;
                }
            }
            if (carta != null)
                listaCartasAgente2TemNaMao.remove(carta);
        }
    }

    private CartasModelo consultaCartaPeloId(int idCartaSolicitada, int Quem) {
        CartasModelo carta = new CartasModelo();
        if (Quem == 1) {
            for (CartasModelo cartas : listaCartasRecebidasAgente1) {
                if (cartas.getId() == idCartaSolicitada)
                    carta = cartas;
            }
            return carta;
        } else {
            for (CartasModelo cartas : listaCartasRecebidasAgente2) {
                if (cartas.getId() == idCartaSolicitada)
                    carta = cartas;
            }
            return carta;
        }
    }

    private CartasModelo consultaCartaPeloIdEx(int idCartaSolicitada, int Quem) {
        CartasModelo carta = new CartasModelo();
        if (Quem == 1) {
            for (CartasModelo cartas : listaCartasAgente1TemNaMao) {
                if (cartas.getId() == idCartaSolicitada)
                    carta = cartas;
            }
            return carta;
        } else {
            for (CartasModelo cartas : listaCartasAgente2TemNaMao) {
                if (cartas.getId() == idCartaSolicitada)
                    carta = cartas;
            }
            return carta;
        }
    }

    public boolean aindaTemJogoNaRodada() {
        return ((contadorJogadas < 6 ? true : false) && (listaJogadasNaoAceitas.size() == 0)) && !temGanhador();
    }

    public String pontosRodada() {
        String dados = "\t PLACAR" + "\tAgente 1 --- " + controlaPartidaAuto.getPontosAgente1() + "\tAgente 2 --- "
                + controlaPartidaAuto.getPontosAgente2();
        //System.out.println(dados);
        return dados;
    }

    public boolean temGanhador() {

//		System.out.println(quemGanhouAPrimeiraMao() + "    " + quemGanhouASegundaMao());

        if (quemGanhouAPrimeiraMao() != null && quemGanhouASegundaMao() != null) {
            if (quemGanhouAPrimeiraMao() == quemGanhouASegundaMao())
                return true;
            if (quemGanhouAPrimeiraMao() == 1 && quemGanhouASegundaMao() == 0)
                return true;
            if (quemGanhouAPrimeiraMao() == 2 && quemGanhouASegundaMao() == 0)
                return true;
            if (quemGanhouAPrimeiraMao() == 0 && quemGanhouASegundaMao() == 1)
                return true;
            if (quemGanhouAPrimeiraMao() == 0 && quemGanhouASegundaMao() == 2)
                return true;
        }
        return false;
    }

    public Integer retornaPontosNegados() {
        Integer jogadaNaoAceita = 0;
        for (JogadasNaoAceitasModelo pontoNaoAceito : listPontosNaoAceitos) {
            if (pontoNaoAceito.getJogadaNaoAceita().equalsIgnoreCase("Envido")
                    && pontoNaoAceito.getQuemNaoAceitou() == 1)
                jogadaNaoAceita = 1;
            if (pontoNaoAceito.getJogadaNaoAceita().equalsIgnoreCase("Envido")
                    && pontoNaoAceito.getQuemNaoAceitou() == 2)
                jogadaNaoAceita = 2;
            if (pontoNaoAceito.getJogadaNaoAceita().equalsIgnoreCase("FaltaEnvido")
                    && pontoNaoAceito.getQuemNaoAceitou() == 1)
                jogadaNaoAceita = 1;
            if (pontoNaoAceito.getJogadaNaoAceita().equalsIgnoreCase("FaltaEnvido")
                    && pontoNaoAceito.getQuemNaoAceitou() == 2)
                jogadaNaoAceita = 2;
            if (pontoNaoAceito.getJogadaNaoAceita().equalsIgnoreCase("RealEnvido")
                    && pontoNaoAceito.getQuemNaoAceitou() == 1)
                jogadaNaoAceita = 1;
            if (pontoNaoAceito.getJogadaNaoAceita().equalsIgnoreCase("RealEnvido")
                    && pontoNaoAceito.getQuemNaoAceitou() == 2)
                jogadaNaoAceita = 2;

        }
        return jogadaNaoAceita;
    }

    public Integer retornaQuemGanhouAflor() {
        Integer quemGanhou = 0;
        if (verificarSeAgenteTemFlor(1) && !verificarSeAgenteTemFlor(2))
            quemGanhou = 1;
        else if (!verificarSeAgenteTemFlor(1) && verificarSeAgenteTemFlor(1))
            quemGanhou = 2;
        else if (verificarSeAgenteTemFlor(1) && verificarSeAgenteTemFlor(2)
                && calcularPontosFlorAgente(1) > calcularPontosFlorAgente(2))
            quemGanhou = 1;
        else if (verificarSeAgenteTemFlor(1) && verificarSeAgenteTemFlor(2)
                && calcularPontosFlorAgente(1) < calcularPontosFlorAgente(2))
            quemGanhou = 2;
        return quemGanhou;
    }

    private int calcularPontosFlorAgente(int AgenteId) {
        int somaDosPontos = 0;
        if (verificarSeAgenteTemFlor(AgenteId)) {
            if (AgenteId == 1) {
                if (listaCartasRecebidasAgente1.get(0).getNaipe().equals(listaCartasRecebidasAgente1.get(1).getNaipe())
                        && listaCartasRecebidasAgente1.get(0).getNaipe()
                        .equals(listaCartasRecebidasAgente1.get(2).getNaipe()))
                    somaDosPontos = listaCartasRecebidasAgente1.get(0).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente1.get(1).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente1.get(2).getValorQueContaParaOenvido() + 20;
            } else {
                if (listaCartasRecebidasAgente2.get(0).getNaipe().equals(listaCartasRecebidasAgente2.get(1).getNaipe())
                        && listaCartasRecebidasAgente2.get(0).getNaipe()
                        .equals(listaCartasRecebidasAgente2.get(2).getNaipe()))
                    somaDosPontos = listaCartasRecebidasAgente2.get(0).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente2.get(1).getValorQueContaParaOenvido()
                            + listaCartasRecebidasAgente2.get(2).getValorQueContaParaOenvido() + 20;
            }
        }
        return somaDosPontos;
    }

    public Integer AgenteMentiuNoEnvido(int AgenteId) {
        int AgenteOponente;
        if (AgenteId == 1)
            AgenteOponente = 2;
        else
            AgenteOponente = 1;
        Integer mentiu = 0;
        if (calcularPontosEnvidoAgente(AgenteId) < 20 && !listaPontosAceitos.isEmpty()
                && !verificarSeAgenteTemFlor(AgenteId) && !verificarSeAgenteTemFlor(AgenteOponente)) {
            for (JogadasChamadasModelo pontosChamados : listaPontosChamados) {
                if (pontosChamados.getJogadaChamada().equalsIgnoreCase("Envido"))
                    return 1;
                else if (pontosChamados.getJogadaChamada().equalsIgnoreCase("RealEnvido"))
                    return 1;
                else if (pontosChamados.getJogadaChamada().equalsIgnoreCase("FaltaEnvido"))
                    return 1;
            }
        }
        return mentiu;
    }

    private Integer AgenteMentiuNaFlor(int AgenteId) {
        Integer mentiu = 0;
        if (!verificarSeAgenteTemFlor(AgenteId) && !listaPontosChamados.isEmpty()) {
            for (JogadasChamadasModelo pontosChamados : listaPontosChamados) {
                if (pontosChamados.getJogadaChamada().equalsIgnoreCase("Flor"))
                    return 1;
            }
        }
        return mentiu;
    }

    public boolean verificarSeAgenteTemFlor(int AgenteId) {
        if (AgenteId == 1) {
            if (listaCartasRecebidasAgente1.get(0).getNaipe().equals(listaCartasRecebidasAgente1.get(2).getNaipe())
                    && listaCartasRecebidasAgente1.get(0).getNaipe()
                    .equals(listaCartasRecebidasAgente1.get(1).getNaipe()))
                return true;
            return false;
        } else {
            if (listaCartasRecebidasAgente2.get(0).getNaipe().equals(listaCartasRecebidasAgente2.get(2).getNaipe())
                    && listaCartasRecebidasAgente2.get(0).getNaipe()
                    .equals(listaCartasRecebidasAgente2.get(1).getNaipe()))
                return true;
            return false;
        }
    }

    public int quemNegouTruco() {
        int retorno = -1;
        String ultimaJogadaChamada = "";
        int quemChamouAultimaJogada = 0;
        String ultimaJogadaNaoAceita = "";
        if (!listaJogadasChamadas.isEmpty())
            ultimaJogadaChamada = listaJogadasChamadas.get(listaJogadasChamadas.size() - 1).getJogadaChamada();
        if (!listaJogadasAceitas.isEmpty()) {
        }
        if (!listaJogadasChamadas.isEmpty())
            quemChamouAultimaJogada = listaJogadasChamadas.get(listaJogadasChamadas.size() - 1).getQuemChamou();
        if (!listaJogadasNaoAceitas.isEmpty())
            ultimaJogadaNaoAceita = listaJogadasNaoAceitas.get(listaJogadasNaoAceitas.size() - 1).getJogadaNaoAceita();

        if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita) && ultimaJogadaChamada.equalsIgnoreCase("Truco")
                && quemChamouAultimaJogada == 1) {
            retorno = 2;
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ReTruco") && quemChamouAultimaJogada == 1) {
            retorno = 2;
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ValeQuatro") && quemChamouAultimaJogada == 1) {
            retorno = 2;
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("Truco") && quemChamouAultimaJogada == 2) {
            retorno = 1;
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ReTruco") && quemChamouAultimaJogada == 2) {
            retorno = 1;
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ValeQuatro") && quemChamouAultimaJogada == 2) {
            retorno = 1;
        }
        return retorno;
    }

    public Integer retornaTentosEnvidoDeQuemGanhouPontos() {
        Integer maiorPontuacao = 0;
        for (JogadasAceitasModelo pontoAceito : listaJogadasAceitas) {
            if (pontoAceito.getJogadaAceita().equalsIgnoreCase("Envido")
                    || pontoAceito.getJogadaAceita().equalsIgnoreCase("RealEnvido")
                    || pontoAceito.getJogadaAceita().equalsIgnoreCase("FaltaEnvido")) {
                maiorPontuacao = Math.max(calcularPontosEnvidoAgente(1), calcularPontosEnvidoAgente(2));
            }
        }
        return maiorPontuacao;
    }

    public QuemGanhouRodadaModelo verificaQuemGanhouOjogo() {
        QuemGanhouRodadaModelo quemGanhouArodada = new QuemGanhouRodadaModelo(0, 0, 0);
        String ultimaJogadaChamada = "";
        int quemChamouAultimaJogada = 0;
        String ultimaJogadaNaoAceita = "";
        if (!listaJogadasChamadas.isEmpty())
            ultimaJogadaChamada = listaJogadasChamadas.get(listaJogadasChamadas.size() - 1).getJogadaChamada();
        if (!listaJogadasAceitas.isEmpty()) {
        }
        if (!listaJogadasChamadas.isEmpty())
            quemChamouAultimaJogada = listaJogadasChamadas.get(listaJogadasChamadas.size() - 1).getQuemChamou();
        if (!listaJogadasNaoAceitas.isEmpty())
            ultimaJogadaNaoAceita = listaJogadasNaoAceitas.get(listaJogadasNaoAceitas.size() - 1).getJogadaNaoAceita();
        // caso jogada nÃƒÂ£o seja aceita pelo humÃƒÂ¢no pontua rÃƒÂ´bo
        if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita) && ultimaJogadaChamada.equalsIgnoreCase("Truco")
                && quemChamouAultimaJogada == 1) {
            quemGanhouArodada = new QuemGanhouRodadaModelo(1, 1, 2);
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ReTruco") && quemChamouAultimaJogada == 1) {
            quemGanhouArodada = new QuemGanhouRodadaModelo(1, 2, 2);
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ValeQuatro") && quemChamouAultimaJogada == 1) {
            quemGanhouArodada = new QuemGanhouRodadaModelo(1, 3, 2);
        }
        // caso a jogada nÃƒÂ£o seja aceita pelo rÃƒÂ´bo pontua humÃƒÂ¢no
        else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("Truco") && quemChamouAultimaJogada == 2) {
            quemGanhouArodada = new QuemGanhouRodadaModelo(2, 1, 1);
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ReTruco") && quemChamouAultimaJogada == 2) {
            quemGanhouArodada = new QuemGanhouRodadaModelo(2, 2, 1);
        } else if (ultimaJogadaChamada.equalsIgnoreCase(ultimaJogadaNaoAceita)
                && ultimaJogadaChamada.equalsIgnoreCase("ValeQuatro") && quemChamouAultimaJogada == 2) {
            quemGanhouArodada = new QuemGanhouRodadaModelo(2, 3, 1);
        }
        return quemGanhouArodada;
    }

    public int validaCamposNulos(Integer quem) {
        if(quem != null) return quem;
        else return 0;

    }

    public TrucoDescription retornaDescriptionIndexacaoEnvidoAgenteUm() {
        TrucoDescription descriptionPontosEnvidoAgenteUm = new TrucoDescription();
        descriptionPontosEnvidoAgenteUm.setJogadorMao(controlaPartidaAuto.getQuemEhMao());
        descriptionPontosEnvidoAgenteUm.setPontosEnvidoRobo(calcularPontosEnvidoAgente(1));
        return descriptionPontosEnvidoAgenteUm;
    }

    public TrucoDescription retornaDescriptionIndexacaoEnvidoAgenteDois() {
        TrucoDescription descriptionPontosEnvidoAgenteDois = new TrucoDescription();
        descriptionPontosEnvidoAgenteDois.setJogadorMao(inverterJogadores(controlaPartidaAuto.getQuemEhMao()));
        descriptionPontosEnvidoAgenteDois.setPontosEnvidoRobo(calcularPontosEnvidoAgente(2));

        return descriptionPontosEnvidoAgenteDois;
    }

    public TrucoDescription retornaDescriptionIndexacaoJogadaAgenteUm() {
        TrucoDescription descriptionJogadaAgenteUm = new TrucoDescription();

        descriptionJogadaAgenteUm.setJogadorMao(validaCamposNulos(controlaPartidaAuto.getQuemEhMao()));
        descriptionJogadaAgenteUm.setCartaAltaRobo(validaCamposNulos(listaCartasRecebidasAgente1.get(0).getId()));
        descriptionJogadaAgenteUm.setCartaMediaRobo(validaCamposNulos(listaCartasRecebidasAgente1.get(1).getId()));
        descriptionJogadaAgenteUm.setCartaBaixaRobo(validaCamposNulos(listaCartasRecebidasAgente1.get(2).getId()));
        return descriptionJogadaAgenteUm;
    }

    public TrucoDescription retornaDescriptionIndexacaoJogadaAgenteDois() {
        TrucoDescription descriptionJogadaAgenteDois = new TrucoDescription();
        descriptionJogadaAgenteDois.setJogadorMao(inverterJogadores(controlaPartidaAuto.getQuemEhMao()));
        descriptionJogadaAgenteDois.setCartaAltaRobo(validaCamposNulos(listaCartasRecebidasAgente2.get(0).getId()));
        descriptionJogadaAgenteDois.setCartaMediaRobo(validaCamposNulos(listaCartasRecebidasAgente2.get(1).getId()));
        descriptionJogadaAgenteDois.setCartaBaixaRobo(validaCamposNulos(listaCartasRecebidasAgente2.get(2).getId()));
        return descriptionJogadaAgenteDois;
    }

    public boolean blefeEnvidoPodeSerDescoberto () {
        boolean oponenteSabe = false;

        Integer QuemPediuEnvido = (retornaPontosChamado("Envido") != null ? retornaPontosChamado("Envido").getQuemChamou() : 0);
        Integer QuemPediuFaltaEnvido = (retornaPontosChamado("FaltaEnvido") != null ? retornaPontosChamado("FaltaEnvido").getQuemChamou() : 0);
        Integer QuemPediuRealEnvido = (retornaPontosChamado("RealEnvido") != null ? retornaPontosChamado("RealEnvido").getQuemChamou() : 0);
        Integer QuemNegouEnvido = retornaPontosNegados();
        Integer QuemGanhouEnvido = (contadorMao > 1 && retornaPontosChamado("Envido") != null
                || retornaPontosChamado("RealEnvido") != null || retornaPontosChamado("FaltaEnvido") != null
                ? QuemGanhouOsPontos()
                : 0);

        if (listaCartasJogadasAgente1.size() == 3 && cartasJogadasMesa.size() > 4) {
            oponenteSabe = true;
        }

        if (listaCartasJogadasAgente1.size() == 2 && listaCartasJogadasAgente1.get(0).getNaipe().equals(listaCartasJogadasAgente1.get(1).getNaipe())) {
            oponenteSabe = true;
        }

        if ((QuemPediuEnvido != 0 || QuemPediuRealEnvido != 0 || QuemPediuFaltaEnvido != 0) && QuemNegouEnvido == 0 && QuemGanhouEnvido != 0) {
            oponenteSabe = true;
        }

        return oponenteSabe;
    }


    public boolean blefeTrucoPodeSerDescoberto () {
        boolean oponenteSabe = false;

        if (listaCartasJogadasAgente1.size() == 3 && cartasJogadasMesa.size() > 4) {
            oponenteSabe = true;
        }

        return oponenteSabe;
    }

    public void atualizaScoutBlefesRealizadosAgente() {

        Integer QuemFlor = 0;
        if (verificarSeAgenteTemFlor(2))
            QuemFlor = 2;
        else if (verificarSeAgenteTemFlor(1))
            QuemFlor = 1;

        Integer QuemPediuEnvido = (retornaPontosChamado("Envido") != null ? retornaPontosChamado("Envido").getQuemChamou() : 0);
        Integer QuemPediuFaltaEnvido = (retornaPontosChamado("FaltaEnvido") != null ? retornaPontosChamado("FaltaEnvido").getQuemChamou() : 0);
        Integer QuemPediuRealEnvido = (retornaPontosChamado("RealEnvido") != null ? retornaPontosChamado("RealEnvido").getQuemChamou() : 0);
        Integer QuemNegouEnvido = retornaPontosNegados();
        Integer QuemGanhouEnvido = (contadorMao > 1 && retornaPontosChamado("Envido") != null
                || retornaPontosChamado("RealEnvido") != null || retornaPontosChamado("FaltaEnvido") != null
                ? QuemGanhouOsPontos()
                : 0);
        int isHand = controlaPartidaAuto.getQuemEhMao() == 1 ? 1 : 0;

        Integer QuemTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getQuemChamou()
                : 0);
        Integer QuandoTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getEmQualRodada()
                : 0);
        Integer QuemRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getQuemChamou()
                : 0);
        Integer QuandoRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getEmQualRodada()
                : 0);
        Integer QuemValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getQuemChamou()
                : 0);
        Integer QuandoValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getEmQualRodada()
                : 0);
        Integer QuemNegouTruco = (!listaJogadasChamadas.isEmpty() ? verificaQuemGanhouOjogo().getQuemNaoAceitouPontos()
                : 0);

        ArrayList<CartasModelo> listaCartasNaMao = new ArrayList<>();
        for (CartasModelo carta : listaCartasRecebidasAgente1) {
            listaCartasNaMao.add(carta);
        }

        double probEnvido = 0.0;
        double probMao = 0.0;

        int pontosEnvido = calcularPontosEnvidoAgente(1);


        if (QuemFlor == 0) {

            //Blefes Envido
            if (isHand == 1) {

                probEnvido = deck.getProbBestPoint(isHand, pontosEnvido, deck.getAllOpponentHands());
                System.out.println("[#BLUFF_AGENT_HAND]: Probabilidade = " + probEnvido);

                if (probEnvido < 0.5) {
                    //# Blefe 1 Agente --> Agente é mão e possui poucos pontos e chama envido/real/falta
                    if (QuemPediuEnvido == 1 || QuemPediuRealEnvido == 1 || QuemPediuFaltaEnvido == 1) {
                        if (QuemNegouEnvido == 2) {
                            controlaPartidaAuto.setCountBluff1Success(controlaPartidaAuto.getCountBluff1Success() + 1);
                            System.out.println("Bluff1Success: " + controlaPartidaAuto.getCountBluff1Success());
                            if (blefeEnvidoPodeSerDescoberto()) {
                                controlaPartidaAuto.setCountBluff1ShowDown(controlaPartidaAuto.getCountBluff1ShowDown() + 1);
                                System.out.println("Bluff_Agent_1_ShowDown: " + controlaPartidaAuto.getCountBluff1ShowDown());
                            }
                        } else {
                            controlaPartidaAuto.setCountBluff1Failure(controlaPartidaAuto.getCountBluff1Failure() + 1);
                            System.out.println("Bluff1Failure: " + controlaPartidaAuto.getCountBluff1Failure());

                            controlaPartidaAuto.setCountBluff1ShowDown(controlaPartidaAuto.getCountBluff1ShowDown() + 1);
                            System.out.println("Bluff_Agent_1_ShowDown: " + controlaPartidaAuto.getCountBluff1ShowDown());
                        }
                        hasDeception = true;
                    }
                }

                if (probEnvido > 0.85) {

                    //# Blefe 2 Agente (SlowPlay) --> Agente é mão e possui muitos pontos e deixa de chamar envido/real/falta
                    if (QuemPediuEnvido == 0 && QuemPediuRealEnvido == 0 && QuemPediuFaltaEnvido == 0) {

                        controlaPartidaAuto.setCountBluff2Failure(controlaPartidaAuto.getCountBluff2Failure() + 1);
                        System.out.println("Bluff2Failure: " + controlaPartidaAuto.getCountBluff2Failure());

                        if (blefeEnvidoPodeSerDescoberto()) {
                            controlaPartidaAuto.setCountBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown() + 1);
                            System.out.println("Bluff_Agent_2_ShowDown: " + controlaPartidaAuto.getCountBluff2ShowDown());
                        }
                        hasDeception = true;

                    } else if (QuemPediuEnvido == 2 && (QuemPediuRealEnvido == 1 || QuemPediuFaltaEnvido == 1)) {

                        controlaPartidaAuto.setCountBluff2Success(controlaPartidaAuto.getCountBluff2Success() + 1);
                        System.out.println("Bluff2Success: " + controlaPartidaAuto.getCountBluff2Success());

                        if (blefeEnvidoPodeSerDescoberto()) {
                            controlaPartidaAuto.setCountBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown() + 1);
                            System.out.println("Bluff_Agent_2_ShowDown: " + controlaPartidaAuto.getCountBluff2ShowDown());
                        }
                        hasDeception = true;

                    } else if (QuemPediuRealEnvido == 2 || QuemPediuFaltaEnvido == 1) {

                        controlaPartidaAuto.setCountBluff2Success(controlaPartidaAuto.getCountBluff2Success() + 1);
                        System.out.println("Bluff2Success: " + controlaPartidaAuto.getCountBluff2Success());

                        if (blefeEnvidoPodeSerDescoberto()) {
                            controlaPartidaAuto.setCountBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown() + 1);
                            System.out.println("Bluff_Agent_2_ShowDown: " + controlaPartidaAuto.getCountBluff2ShowDown());
                        }
                        hasDeception = true;
                    }
                }

            } else {

                probEnvido =deck.getProbBestPoint(isHand, pontosEnvido,
                        deck.getFilteredOpponentHands(listaCartasJogadasAgente2.get(0).getCarta()));
                System.out.println("[#BLUFF_AGENT_FOOTER]: Probabilidade = " + probEnvido);

                if (probEnvido < 0.5) {
                    //# Blefe 3 Agente (Fishing) --> Agente é Pé e possui poucos pontos e chama envido/real/falta pq oponente não chamou
                    if (QuemPediuEnvido != 2 && QuemPediuRealEnvido != 2 && QuemPediuFaltaEnvido != 2) {

                        if ((QuemPediuEnvido == 1 || QuemPediuRealEnvido == 1 || QuemPediuFaltaEnvido == 1) && QuemNegouEnvido == 2) {

                            controlaPartidaAuto.setCountBluff3Success(controlaPartidaAuto.getCountBluff3Success() + 1);
                            System.out.println("Bluff3Success: " + controlaPartidaAuto.getCountBluff3Success());

                            if (blefeEnvidoPodeSerDescoberto()) {
                                controlaPartidaAuto.setCountBluff3ShowDown(controlaPartidaAuto.getCountBluff3ShowDown() + 1);
                                System.out.println("Bluff_Agent_3_ShowDown: " + controlaPartidaAuto.getCountBluff3ShowDown());
                            }
                            hasDeception = true;

                        } else if ((QuemPediuEnvido == 1 || QuemPediuRealEnvido == 1 || QuemPediuFaltaEnvido == 1) && QuemNegouEnvido != 2) {

                            controlaPartidaAuto.setCountBluff3Failure(controlaPartidaAuto.getCountBluff3Failure() + 1);
                            System.out.println("Bluff3Failure: " + controlaPartidaAuto.getCountBluff3Failure());

                            controlaPartidaAuto.setCountBluff3ShowDown(controlaPartidaAuto.getCountBluff3ShowDown() + 1);
                            System.out.println("Bluff_Agent_3_ShowDown: " + controlaPartidaAuto.getCountBluff3ShowDown());
                            hasDeception = true;
                        }


                    }
                }
            } // Fim Blefes Envido

            //Blefes Truco

        }

        Iterator it1 = listaCartasNaMao.iterator();
        Iterator it2 = listaCartasNaMao.iterator();

        double agentHandStrength = deck.getStrenghtHand(listaCartasRecebidasAgente1.get(0).getCarta(),
                listaCartasRecebidasAgente1.get(1).getCarta(), 	listaCartasRecebidasAgente1.get(2).getCarta());

        if (listaCartasJogadasAgente2.size() == 1) {

            probMao = deck.getProbBestHand(isHand, agentHandStrength,
                    deck.getFilteredOpponentHands(listaCartasJogadasAgente2.get(0).getCarta()));

        } else if (listaCartasJogadasAgente2.size() == 2) {

            probMao = deck.getProbBestHand(isHand, agentHandStrength,
                    deck.getFilteredOpponentHands(listaCartasJogadasAgente2.get(0).getCarta(),
                            listaCartasJogadasAgente2.get(1).getCarta()));

        } else {
            probMao = deck.getProbBestHand(isHand, agentHandStrength, deck.getAllOpponentHands());
        }

        boolean primeiraCartaOpponent = false;
        if (cartasJogadasMesa.size() > 0) {
            for (CartasModelo carta : listaCartasRecebidasAgente2) {
                if (carta.getCarta().equals(cartasJogadasMesa.get(0).getCarta())) {
                    primeiraCartaOpponent = true;
                }
            }
        }


        //if (isHand == 1) {

        if (primeiraCartaOpponent) {
            probMao = deck.getProbBestHand(isHand, agentHandStrength,
                    deck.getFilteredOpponentHands(listaCartasJogadasAgente2.get(0).getCarta()));
            System.out.println("[#BLUFF_AGENT_PLAYCARD2]: Probabilidade = " + probMao);
        } else {
            probMao = deck.getProbBestHand(isHand, agentHandStrength, deck.getAllOpponentHands());
            System.out.println("[#BLUFF_AGENT_PLAYCARD1]: Probabilidade = " + probMao);
        }

        System.out.println("PROB_BLUFF_AGENT: " + probMao);
        if (probMao > 0.85) {
            //# Blefe 5 Agente (SlowPlay) --> Agente é mão e possui mão forte e deixa de chamar truco/Retruco/Vale4
            if (QuemTruco == 0) {
                controlaPartidaAuto.setCountBluff5Failure(controlaPartidaAuto.getCountBluff5Failure() + 1);
                System.out.println("Bluff5Failure: " + controlaPartidaAuto.getCountBluff5Failure());

                if (blefeTrucoPodeSerDescoberto()) {
                    controlaPartidaAuto.setCountBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown() + 1);
                    System.out.println("Bluff_Agent_5_ShowDown: " + controlaPartidaAuto.getCountBluff5ShowDown());
                }
                hasDeception = true;

            } else if (QuemTruco == 2 && QuemRetruco == 1) {
                controlaPartidaAuto.setCountBluff5Success(controlaPartidaAuto.getCountBluff5Success() + 1);
                System.out.println("Bluff5Success: " + controlaPartidaAuto.getCountBluff5Success());

                if (blefeTrucoPodeSerDescoberto()) {
                    controlaPartidaAuto.setCountBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown() + 1);
                    System.out.println("Bluff_Agent_5_ShowDown: " + controlaPartidaAuto.getCountBluff5ShowDown());
                }
                hasDeception = true;
            }
        }

		/*	if (probMao < 0.26) {
				//# Blefe 4 Agente --> Agente é mão e possui mão fraca e chama truco/Retruco/Vale4
				if ((QuemTruco == 1) || (QuemRetruco == 1) || (QuemValeQuatro == 1)) {

					if (QuemNegouTruco == 2) {
						controlaPartidaAuto.setCountBluff4Success(controlaPartidaAuto.getCountBluff4Success() + 1);
						System.out.println("Bluff4Success: " + controlaPartidaAuto.getCountBluff4Success());

						if (blefeTrucoPodeSerDescoberto()) {
							controlaPartidaAuto.setCountBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown() + 1);
							System.out.println("Bluff_Agent_4_ShowDown: " + controlaPartidaAuto.getCountBluff4ShowDown());
						}

					} else {
						controlaPartidaAuto.setCountBluff4Failure(controlaPartidaAuto.getCountBluff4Failure() + 1);
						System.out.println("Bluff4Failure: " + controlaPartidaAuto.getCountBluff4Failure());

						if (blefeTrucoPodeSerDescoberto()) {
							controlaPartidaAuto.setCountBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown() + 1);
							System.out.println("Bluff_Agent_4_ShowDown: " + controlaPartidaAuto.getCountBluff4ShowDown());
						}
					}
				}
			}*/
        //}

        boolean terceiraCartaOpponent = false;
        if (cartasJogadasMesa.size() > 2) {
            for (CartasModelo carta : listaCartasRecebidasAgente2) {
                if (carta.getCarta().equals(cartasJogadasMesa.get(2).getCarta())) {
                    terceiraCartaOpponent = true;
                }
            }
        }

        if (quemGanhouAPrimeiraMao() == 2 && terceiraCartaOpponent) {

            CartasModelo cartaJogada = listaCartasJogadasAgente1.get(0);
            ArrayList<CartasModelo> cartasNaMao = new ArrayList<>();

            for (CartasModelo carta : listaCartasRecebidasAgente1) {
                if (!cartaJogada.getCarta().equals(carta.getCarta())) {
                    cartasNaMao.add(carta);
                }
            }

            boolean temCartaPraGanhar = false;
            for (CartasModelo carta : cartasNaMao) {
                if (carta.getId() <= listaCartasJogadasAgente2.get(1).getId()) {
                    probMao = 0.0;
                } else {
                    probMao = deck.getProbBestHand(isHand, agentHandStrength,
                            deck.getFilteredOpponentHands(listaCartasJogadasAgente2.get(0).getCarta(),
                                    listaCartasJogadasAgente2.get(1).getCarta()));
                }
                System.out.println("[#BLUFF_AGENT_PLAYCARD4]: Probabilidade = " + probMao);
            }

            //# Blefe 6 Agente Fishing --> Agente não tem carta para vencer e pede truco/retruco/vale4
            if (probMao == 0.0) {
                if ((QuemTruco == 1 && QuandoTruco == 2) || (QuemRetruco == 1 && QuandoRetruco == 2) ||
                        (QuemValeQuatro == 1 && QuandoValeQuatro == 2)) {

                    if (QuemNegouTruco == 2) {
                        controlaPartidaAuto.setCountBluff6Success(controlaPartidaAuto.getCountBluff6Success() + 1);
                        System.out.println("Bluff6Success: " + controlaPartidaAuto.getCountBluff6Success());

                        if (blefeTrucoPodeSerDescoberto()) {
                            controlaPartidaAuto.setCountBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown() + 1);
                            System.out.println("Bluff_Agent_6_ShowDown: " + controlaPartidaAuto.getCountBluff6ShowDown());
                        }
                        hasDeception = true;

                    } else {
                        controlaPartidaAuto.setCountBluff6Failure(controlaPartidaAuto.getCountBluff6Failure() + 1);
                        System.out.println("Bluff6Failure: " + controlaPartidaAuto.getCountBluff6Failure());


                        controlaPartidaAuto.setCountBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown() + 1);
                        System.out.println("Bluff_Agent_6_ShowDown: " + controlaPartidaAuto.getCountBluff6ShowDown());

                        hasDeception = true;
                    }
                }
            }

        } else if (quemGanhouAPrimeiraMao() == 1 && cartasJogadasMesa.size() > 1 && listaCartasJogadasAgente1.size() > 0
                && !terceiraCartaOpponent) {

            probMao = deck.getProbBestHand(isHand, agentHandStrength,
                    deck.getFilteredOpponentHands(listaCartasJogadasAgente2.get(0).getCarta()));
            System.out.println("[#BLUFF_AGENT_PLAYCARD3]: Probabilidade = " + probMao);

            //# Blefe 4 Agente --> Agente é mão e possui mão fraca e chama truco/Retruco/Vale4
			/*if (probMao < 0.26) {
				if ((QuemTruco == 1) || (QuemRetruco == 1) ||
						(QuemValeQuatro == 1)) {

					if (QuemNegouTruco == 2) {
						controlaPartidaAuto.setCountBluff4Success(controlaPartidaAuto.getCountBluff4Success() + 1);
						System.out.println("Bluff4Success: " + controlaPartidaAuto.getCountBluff4Success());

						if (blefeTrucoPodeSerDescoberto()) {
							controlaPartidaAuto.setCountBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown() + 1);
							System.out.println("Bluff_Agent_4_ShowDown: " + controlaPartidaAuto.getCountBluff4ShowDown());
						}

					} else {
						controlaPartidaAuto.setCountBluff4Failure(controlaPartidaAuto.getCountBluff6Failure() + 1);
						System.out.println("Bluff4Failure: " + controlaPartidaAuto.getCountBluff4Failure());

						if (blefeTrucoPodeSerDescoberto()) {
							controlaPartidaAuto.setCountBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown() + 1);
							System.out.println("Bluff_Agent_4_ShowDown: " + controlaPartidaAuto.getCountBluff4ShowDown());
						}
					}
				}
			}*/

            //# Blefe 5 Agente (SlowPlay) --> Agente é mão e possui mão forte e deixa de chamar truco/Retruco/Vale4
			/*if (probMao > 0.85) {
				if (QuemTruco == 0 ) {

					controlaPartidaAuto.setCountBluff5Failure(controlaPartidaAuto.getCountBluff5Failure() + 1);
					System.out.println("Bluff5Failure: " + controlaPartidaAuto.getCountBluff5Failure());

					if (blefeTrucoPodeSerDescoberto()) {
						controlaPartidaAuto.setCountBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown() + 1);
						System.out.println("Bluff_Agent_5_ShowDown: " + controlaPartidaAuto.getCountBluff5ShowDown());
					}

				} else if (QuemTruco == 2 && QuandoTruco == 2 && QuemRetruco == 1 && QuandoRetruco == 2) {
					controlaPartidaAuto.setCountBluff5Success(controlaPartidaAuto.getCountBluff5Success() + 1);
					System.out.println("Bluff5Success: " + controlaPartidaAuto.getCountBluff5Success());

					if (blefeTrucoPodeSerDescoberto()) {
						controlaPartidaAuto.setCountBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown() + 1);
						System.out.println("Bluff_Agent_5_ShowDown: " + controlaPartidaAuto.getCountBluff5ShowDown());
					}
				}
			}*/


        }

        boolean quintaCartaOpponent = false;
        if (cartasJogadasMesa.size() > 4) {
            for (CartasModelo carta : listaCartasRecebidasAgente2) {
                if (carta.getCarta().equals(cartasJogadasMesa.get(4).getCarta())) {
                    quintaCartaOpponent = true;
                }
            }
        }



        if (quemGanhouASegundaMao() == 2 && quintaCartaOpponent) {

            CartasModelo cartaNaMao = null;
            CartasModelo cartasJogada1 = listaCartasJogadasAgente1.get(0);
            CartasModelo cartasJogada2 = listaCartasJogadasAgente1.get(1);

            for (CartasModelo carta : listaCartasRecebidasAgente1) {
                if (!carta.getCarta().equals(cartasJogada1) && !carta.getCarta().equals(cartasJogada2)) {
                    cartaNaMao = carta;
                }
            }

            boolean temCartaPraGanhar = false;

            if (cartaNaMao.getId() <= listaCartasJogadasAgente2.get(2).getId() && quemGanhouAPrimeiraMao() == 2) {
                probMao = 0.0;
            } else {
                probMao = 1.0;
            }
            System.out.println("[#BLUFF_AGENT_PLAYCARD6]: Probabilidade = " + probMao);


            //# Blefe 6 Agente Fishing --> Agente não tem carta para vencer e pede truco/retruco/vale4
            if (probMao == 0.0) {
                if ((QuemTruco == 1 && QuandoTruco == 3) || (QuemRetruco == 1 && QuandoRetruco == 3) ||
                        (QuemValeQuatro == 1 && QuandoValeQuatro == 3)) {

                    if (QuemNegouTruco == 2) {
                        controlaPartidaAuto.setCountBluff6Success(controlaPartidaAuto.getCountBluff6Success() + 1);
                        System.out.println("Bluff6Success: " + controlaPartidaAuto.getCountBluff6Success());

                        if (blefeTrucoPodeSerDescoberto()) {
                            controlaPartidaAuto.setCountBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown() + 1);
                            System.out.println("Bluff_Agent_6_ShowDown: " + controlaPartidaAuto.getCountBluff6ShowDown());
                        }
                        hasDeception = true;
                    } else {
                        controlaPartidaAuto.setCountBluff6Failure(controlaPartidaAuto.getCountBluff6Failure() + 1);
                        System.out.println("Bluff6Failure: " + controlaPartidaAuto.getCountBluff6Failure());


                        controlaPartidaAuto.setCountBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown() + 1);
                        System.out.println("Bluff_Agent_6_ShowDown: " + controlaPartidaAuto.getCountBluff6ShowDown());

                        hasDeception = true;
                    }
                }
            }


        } else if (quemGanhouASegundaMao() == 1 && cartasJogadasMesa.size() > 3 && listaCartasJogadasAgente1.size() > 1
                && !quintaCartaOpponent) {

            probMao = deck.getProbBestHand(isHand, agentHandStrength,
                    deck.getFilteredOpponentHands(listaCartasJogadasAgente2.get(0).getCarta(),
                            listaCartasJogadasAgente2.get(1).getCarta()));
            System.out.println("[#BLUFF_AGENT_PLAYCARD5]: Probabilidade = " + probMao);

            //# Blefe 4 Agente --> Agente é mão e possui mão fraca e chama truco/Retruco/Vale4
			/*if (probMao < 0.26) {
				if ((QuemTruco == 1) || (QuemRetruco == 1) ||
						(QuemValeQuatro == 1)) {

					if (QuemNegouTruco == 2) {
						controlaPartidaAuto.setCountBluff4Success(controlaPartidaAuto.getCountBluff4Success() + 1);
						System.out.println("Bluff4Success: " + controlaPartidaAuto.getCountBluff4Success());

						if (blefeTrucoPodeSerDescoberto()) {
							controlaPartidaAuto.setCountBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown() + 1);
							System.out.println("Bluff_Agent_4_ShowDown: " + controlaPartidaAuto.getCountBluff4ShowDown());
						}
					} else {
						controlaPartidaAuto.setCountBluff4Failure(controlaPartidaAuto.getCountBluff6Failure() + 1);
						System.out.println("Bluff4Failure: " + controlaPartidaAuto.getCountBluff4Failure());

						if (blefeTrucoPodeSerDescoberto()) {
							controlaPartidaAuto.setCountBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown() + 1);
							System.out.println("Bluff_Agent_4_ShowDown: " + controlaPartidaAuto.getCountBluff4ShowDown());
						}
					}
				}
			}*/

            //# Blefe 5 Agente (SlowPlay) --> Agente é mão e possui mão forte e deixa de chamar truco/Retruco/Vale4
			/*if (probMao > 0.85) {
				if (QuemTruco == 0 ) {

					controlaPartidaAuto.setCountBluff5Failure(controlaPartidaAuto.getCountBluff5Failure() + 1);
					System.out.println("Bluff5Failure: " + controlaPartidaAuto.getCountBluff5Failure());

					if (blefeTrucoPodeSerDescoberto()) {
						controlaPartidaAuto.setCountBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown() + 1);
						System.out.println("Bluff_Agent_5_ShowDown: " + controlaPartidaAuto.getCountBluff5ShowDown());
					}

				} else if (QuemTruco == 2 && QuandoTruco == 3 && QuemRetruco == 1 && QuandoRetruco == 3) {
					controlaPartidaAuto.setCountBluff5Success(controlaPartidaAuto.getCountBluff5Success() + 1);
					System.out.println("Bluff5Success: " + controlaPartidaAuto.getCountBluff5Success());

					if (blefeTrucoPodeSerDescoberto()) {
						controlaPartidaAuto.setCountBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown() + 1);
						System.out.println("Bluff_Agent_5_ShowDown: " + controlaPartidaAuto.getCountBluff5ShowDown());
					}
				}
			}*/

        }

        //# Blefe 4 Agente --> Agente é mão e possui mão fraca e chama truco/Retruco/Vale4
        //if (probMao < 0.26) {
        if (probMao < 0.5) {
            if (QuemTruco == 1 || QuemRetruco == 1 || QuemValeQuatro == 1) {

                if (QuemNegouTruco == 2) {
                    controlaPartidaAuto.setCountBluff4Success(controlaPartidaAuto.getCountBluff4Success() + 1);
                    System.out.println("Bluff4Success: " + controlaPartidaAuto.getCountBluff4Success());

                    if (blefeTrucoPodeSerDescoberto()) {
                        controlaPartidaAuto.setCountBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown() + 1);
                        System.out.println("Bluff_Agent_4_ShowDown: " + controlaPartidaAuto.getCountBluff4ShowDown());
                    }
                    hasDeception = true;
                } else {
                    controlaPartidaAuto.setCountBluff4Failure(controlaPartidaAuto.getCountBluff4Failure() + 1);
                    System.out.println("Bluff4Failure: " + controlaPartidaAuto.getCountBluff4Failure());

                    controlaPartidaAuto.setCountBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown() + 1);
                    System.out.println("Bluff_Agent_4_ShowDown: " + controlaPartidaAuto.getCountBluff4ShowDown());

                    hasDeception = true;
                }
            }
        }

        // Fim Blefes Truco

        System.out.println("[Scout Bluff_Agent_1_Success]: " + controlaPartidaAuto.getCountBluff1Success());
        System.out.println("[Scout Bluff_Agent_1_Failure]: " + controlaPartidaAuto.getCountBluff1Failure());
        System.out.println("[Scout Bluff_Agent_1_ShowDown]: " + controlaPartidaAuto.getCountBluff1ShowDown());

        System.out.println("[Scout Bluff_Agent_2_Success]: " + controlaPartidaAuto.getCountBluff2Success());
        System.out.println("[Scout Bluff_Agent_2_Failure]: " + controlaPartidaAuto.getCountBluff2Failure());
        System.out.println("[Scout Bluff_Agent_2_ShowDown]: " + controlaPartidaAuto.getCountBluff2ShowDown());

        System.out.println("[Scout Bluff_Agent_3_Success]: " + controlaPartidaAuto.getCountBluff3Success());
        System.out.println("[Scout Bluff_Agent_3_Failure]: " + controlaPartidaAuto.getCountBluff3Failure());
        System.out.println("[Scout Bluff_Agent_3_ShowDown]: " + controlaPartidaAuto.getCountBluff3ShowDown());

        System.out.println("[Scout Bluff_Agent_4_Success]: " + controlaPartidaAuto.getCountBluff4Success());
        System.out.println("[Scout Bluff_Agent_4_Failure]: " + controlaPartidaAuto.getCountBluff4Failure());
        System.out.println("[Scout Bluff_Agent_4_ShowDown]: " + controlaPartidaAuto.getCountBluff4ShowDown());

        System.out.println("[Scout Bluff_Agent_5_Success]: " + controlaPartidaAuto.getCountBluff5Success());
        System.out.println("[Scout Bluff_Agent_5_Failure]: " + controlaPartidaAuto.getCountBluff5Failure());
        System.out.println("[Scout Bluff_Agent_5_ShowDown]: " + controlaPartidaAuto.getCountBluff5ShowDown());

        System.out.println("[Scout Bluff_Agent_6_Success]: " + controlaPartidaAuto.getCountBluff6Success());
        System.out.println("[Scout Bluff_Agent_6_Failure]: " + controlaPartidaAuto.getCountBluff6Failure());
        System.out.println("[Scout Bluff_Agent_6_ShowDown]: " + controlaPartidaAuto.getCountBluff6ShowDown());

    }

    public void atualizaScoutBlefesPlotadosOpponente() {


        Integer QuemPediuEnvido = (retornaPontosChamado("Envido") != null ? retornaPontosChamado("Envido").getQuemChamou() : 0);
        Integer QuemPediuFaltaEnvido = (retornaPontosChamado("FaltaEnvido") != null ? retornaPontosChamado("FaltaEnvido").getQuemChamou() : 0);
        Integer QuemPediuRealEnvido = (retornaPontosChamado("RealEnvido") != null ? retornaPontosChamado("RealEnvido").getQuemChamou() : 0);
        Integer QuemNegouEnvido = retornaPontosNegados();
        int isHand = controlaPartidaAuto.getQuemEhMao() == 2 ? 1 : 0;

        Integer QuemTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getQuemChamou()
                : 0);
        Integer QuandoTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getEmQualRodada()
                : 0);
        Integer QuemRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getQuemChamou()
                : 0);
        Integer QuandoRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getEmQualRodada()
                : 0);
        Integer QuemValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getQuemChamou()
                : 0);
        Integer QuandoValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getEmQualRodada()
                : 0);
        int pontosEnvido = calcularPontosEnvidoAgente(2);
        double probEnvido = deck.getProbBestPoint(isHand, pontosEnvido, deck.getFilteredAgentHands(listaCartasRecebidasAgente2.get(0).getCarta(),
                listaCartasRecebidasAgente2.get(1).getCarta(), 	listaCartasRecebidasAgente2.get(2).getCarta()));

        if (isHand == 0 && listaCartasJogadasAgente1.size() > 0) {
            probEnvido = deck.getProbBestPoint(isHand, pontosEnvido, deck.getFilteredAgentHands(listaCartasRecebidasAgente2.get(0).getCarta(),
                    listaCartasRecebidasAgente2.get(1).getCarta(),
                    listaCartasRecebidasAgente2.get(2).getCarta(), listaCartasJogadasAgente1.get(0).getCarta()));
        }

        double oppHandStrength = deck.getStrenghtHand(listaCartasRecebidasAgente2.get(0).getCarta(),
                listaCartasRecebidasAgente2.get(1).getCarta(), 	listaCartasRecebidasAgente2.get(2).getCarta());

        double probMao = deck.getProbBestHand(isHand, oppHandStrength, deck.getFilteredAgentHands(listaCartasRecebidasAgente2.get(0).getCarta(),
                listaCartasRecebidasAgente2.get(1).getCarta(), 	listaCartasRecebidasAgente2.get(2).getCarta()));

        if (listaCartasJogadasAgente2.size() == 3 && cartasJogadasMesa.size() > 4) {

            //ENVIDO

            if (isHand == 0) {

                if (probEnvido < 0.5) {
                    //#Blefe3 Oponente é o Pé tem poucos pontos e pede envido porque o agente não pediu FISHING
                    if (QuemPediuEnvido != 1 && QuemPediuRealEnvido != 1 && QuemPediuFaltaEnvido != 1) {
                        if (QuemPediuEnvido == 2 || QuemPediuRealEnvido == 2 || QuemPediuFaltaEnvido == 2) {
                            controlaPartidaAuto.setCountBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent() + 1);
                        }
                    }
                }

            } else {

                if (probEnvido > 0.85) {
                    //#Blefe2 Oponente é o mâo tem muitos pontos e não pede envido SLOW PLAY
                    if (QuemPediuEnvido == 0 && QuemPediuRealEnvido == 0 && QuemPediuFaltaEnvido == 0) {
                        controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);
                    } else if (QuemPediuEnvido == 1 && (QuemPediuRealEnvido != 1 || QuemPediuFaltaEnvido != 1)) {
                        controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);

                    } else if (QuemPediuRealEnvido == 1 && QuemPediuFaltaEnvido != 1) {
                        controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);
                    } else if (QuemPediuFaltaEnvido == 1) {
                        controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);
                    }

                } else if (probEnvido < 0.5) {
                    //#Blefe1 Oponente é o mâo tem poucos pontos e pede envido
                    if (QuemPediuEnvido == 2 || QuemPediuRealEnvido == 2 || QuemPediuFaltaEnvido == 2) {
                        controlaPartidaAuto.setCountBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent() + 1);
                    }
                }

            }

            // TRUCO
            if (QuemTruco == 2 || QuemRetruco == 2 || QuemValeQuatro == 2) {

                if (QuandoTruco == 1 || QuandoRetruco == 1 || QuandoValeQuatro == 1) {

                    if (isHand == 0) {

                        oppHandStrength = deck.getStrenghtHand(listaCartasJogadasAgente2.get(0).getCarta(),
                                listaCartasJogadasAgente2.get(1).getCarta(), listaCartasJogadasAgente2.get(2).getCarta());

                        probMao = deck.getProbBestHand(isHand, oppHandStrength, deck.getFilteredAgentHands(listaCartasJogadasAgente2.get(0).getCarta(),
                                listaCartasJogadasAgente2.get(1).getCarta(), listaCartasJogadasAgente2.get(2).getCarta(),
                                listaCartasJogadasAgente1.get(0).getCarta()));
                    }

                    //#Blefe4 Oponente tem mão fraca e pede truco para agente fugir
                    if (probMao < 0.5) {
                        controlaPartidaAuto.setCountBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent() + 1);
                    }

                } else if (QuandoTruco == 2 || QuandoRetruco == 2 || QuandoValeQuatro == 2) {

                    if (validaCamposNulos(GanhadorPrimeira) == 1) {

                        probMao = deck.getProbBestHand(isHand, oppHandStrength, deck.getFilteredAgentHands(listaCartasJogadasAgente2.get(0).getCarta(),
                                listaCartasJogadasAgente2.get(1).getCarta(), listaCartasJogadasAgente2.get(2).getCarta(),
                                listaCartasJogadasAgente1.get(0).getCarta(), listaCartasJogadasAgente1.get(1).getCarta()));

                    } else {
                        probMao = deck.getProbBestHand(isHand, oppHandStrength, deck.getFilteredAgentHands(listaCartasJogadasAgente2.get(0).getCarta(),
                                listaCartasJogadasAgente2.get(1).getCarta(), listaCartasJogadasAgente2.get(2).getCarta(),
                                listaCartasJogadasAgente1.get(0).getCarta()));
                    }

                    //#Blefe4 Oponente tem mão fraca e pede truco para agente fugir
                    if (probMao < 0.5) {
                        controlaPartidaAuto.setCountBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent() + 1);
                    }

                } else if (QuandoTruco == 3 || QuandoRetruco == 1 || QuandoValeQuatro == 3) {

                    if (validaCamposNulos(GanhadorSegunda) == 2) {

                        probMao = deck.getProbBestHand(isHand, oppHandStrength, deck.getFilteredAgentHands(listaCartasJogadasAgente2.get(0).getCarta(),
                                listaCartasJogadasAgente2.get(1).getCarta(), listaCartasJogadasAgente2.get(2).getCarta(),
                                listaCartasJogadasAgente1.get(0).getCarta(), listaCartasJogadasAgente1.get(1).getCarta()));

                        //#Blefe4 Oponente tem mão fraca e pede truco para agente fugir
                        if (probMao < 0.5) {
                            controlaPartidaAuto.setCountBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent() + 1);
                        }

                    } else {

                        CartasModelo ultimaCarta = null;
                        for (CartasModelo carta : listaCartasRecebidasAgente2) {
                            if (!carta.getCarta().equals(listaCartasJogadasAgente2.get(0).getCarta()) &&
                                    !carta.getCarta().equals(listaCartasJogadasAgente2.get(1).getCarta())) {
                                ultimaCarta = carta;
                            }
                        }

                        if (ultimaCarta.getId() > listaCartasJogadasAgente1.get(2).getId()) {
                            probMao = 1.0;
                        } else if (ultimaCarta.getId() == listaCartasJogadasAgente1.get(2).getId()) {
                            if (isHand == 1) {
                                probMao = 1.0;
                            } else {
                                probMao = 0.0;
                            }
                        } else {
                            probMao = 0.0;
                        }

                        //#Blefe6 Oponente não tem carta para vencer a última rodada e pede truco para agente fugir
                        if (probMao < 0.5) {
                            controlaPartidaAuto.setCountBluff6Opponent(controlaPartidaAuto.getCountBluff6Opponent() + 1);
                        }
                    }
                }

            } else if (QuemTruco != 2) {
                //#Blefe5 Oponente tem mão forte e não pede truco para poder aumentar SLOW PLAY
                if (probMao > 0.85) {
                    controlaPartidaAuto.setCountBluff5Opponent(controlaPartidaAuto.getCountBluff5Opponent() + 1);
                }
            }

            System.out.println("PROB_ENVIDO_BLUFF_OPP :" + probEnvido);
            System.out.println("PROB_TRUCO_BLUFF_OPP :" + probMao);

        } else if (listaCartasJogadasAgente2.size() == 2) {

            if (listaCartasJogadasAgente2.get(0).getNaipe().equals(listaCartasJogadasAgente2.get(1).getNaipe())) {

                //ENVIDO

                if (isHand == 0) {

                    if (probEnvido < 0.5) {
                        //#Blefe3 Oponente é o Pé tem poucos pontos e pede envido porque o agente não pediu FISHING
                        if (QuemPediuEnvido != 1 && QuemPediuRealEnvido != 1 && QuemPediuFaltaEnvido != 1) {
                            if (QuemPediuEnvido == 2 || QuemPediuRealEnvido == 2 || QuemPediuFaltaEnvido == 2) {
                                controlaPartidaAuto.setCountBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent() + 1);
                            }
                        }
                    }

                } else {

                    if (probEnvido > 0.85) {
                        //#Blefe2 Oponente é o mâo tem muitos pontos e não pede envido SLOW PLAY
                        if (QuemPediuEnvido == 0 && QuemPediuRealEnvido == 0 && QuemPediuFaltaEnvido == 0) {
                            controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);
                        } else if (QuemPediuEnvido == 1 && (QuemPediuRealEnvido != 1 || QuemPediuFaltaEnvido != 1)) {
                            controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);

                        } else if (QuemPediuRealEnvido == 1 && QuemPediuFaltaEnvido != 1) {
                            controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);
                        } else if (QuemPediuFaltaEnvido == 1) {
                            controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);
                        }

                    } else if (probEnvido < 0.5) {
                        //#Blefe1 Oponente é o mâo tem poucos pontos e pede envido
                        if (QuemPediuEnvido == 2 || QuemPediuRealEnvido == 2 || QuemPediuFaltaEnvido == 2) {
                            controlaPartidaAuto.setCountBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent() + 1);
                        }
                    }
                }
            }
        } else {

            if (QuemNegouEnvido == 0 && QuemPediuEnvido != 0 ) {
                //ENVIDO
                if (isHand == 0) {

                    if (probEnvido < 0.5) {
                        //#Blefe3 Oponente é o Pé tem poucos pontos e pede envido porque o agente não pediu FISHING
                        if (QuemPediuEnvido != 1 && QuemPediuRealEnvido != 1 && QuemPediuFaltaEnvido != 1) {
                            if (QuemPediuEnvido == 2 || QuemPediuRealEnvido == 2 || QuemPediuFaltaEnvido == 2) {
                                controlaPartidaAuto.setCountBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent() + 1);
                            }
                        }
                    }

                } else {

                    if (probEnvido > 0.85) {
                        //#Blefe2 Oponente é o mâo tem muitos pontos e não pede envido SLOW PLAY
                        if (QuemPediuEnvido == 0 && QuemPediuRealEnvido == 0 && QuemPediuFaltaEnvido == 0) {
                            controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);
                        } else if (QuemPediuEnvido == 1 && (QuemPediuRealEnvido != 1 || QuemPediuFaltaEnvido != 1)) {
                            controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);

                        } else if (QuemPediuRealEnvido == 1 && QuemPediuFaltaEnvido != 1) {
                            controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);
                        } else if (QuemPediuFaltaEnvido == 1) {
                            controlaPartidaAuto.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent() + 1);
                        }

                    } else if (probEnvido < 0.5) {
                        //#Blefe1 Oponente é o mâo tem poucos pontos e pede envido
                        if (QuemPediuEnvido == 2 || QuemPediuRealEnvido == 2 || QuemPediuFaltaEnvido == 2) {
                            controlaPartidaAuto.setCountBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent() + 1);
                        }
                    }
                }
            }
        }


        System.out.println("[Scout Bluff_Opponent_1]: " + controlaPartidaAuto.getCountBluff1Opponent());
        System.out.println("[Scout Bluff_Opponent_2]: " + controlaPartidaAuto.getCountBluff2Opponent());
        System.out.println("[Scout Bluff_Opponent_3]: " + controlaPartidaAuto.getCountBluff3Opponent());
        System.out.println("[Scout Bluff_Opponent_4]: " + controlaPartidaAuto.getCountBluff4Opponent());
        System.out.println("[Scout Bluff_Opponent_5]: " + controlaPartidaAuto.getCountBluff5Opponent());
        System.out.println("[Scout Bluff_Opponent_6]: " + controlaPartidaAuto.getCountBluff6Opponent());


    }

    public TrucoDescription getDescriptionEnvidoActiveLearning() {

        Integer QuemPediuEnvido = (retornaPontosChamado("Envido") != null
                ? retornaPontosChamado("Envido").getQuemChamou()
                : 0);
        Integer QuemPediuFaltaEnvido = (retornaPontosChamado("FaltaEnvido") != null
                ? retornaPontosChamado("FaltaEnvido").getQuemChamou()
                : 0);
        Integer QuemPediuRealEnvido = (retornaPontosChamado("RealEnvido") != null
                ? retornaPontosChamado("RealEnvido").getQuemChamou()
                : 0);

        TrucoDescription trucoDescription = new TrucoDescription();

        trucoDescription.setJogadorMao(controlaPartidaAuto.getQuemEhMao());
        trucoDescription.setCartaAltaRobo(listaCartasRecebidasAgente1.get(0).getId());
        trucoDescription.setCartaMediaRobo(listaCartasRecebidasAgente1.get(1).getId());
        trucoDescription.setCartaBaixaRobo(listaCartasRecebidasAgente1.get(2).getId());
        trucoDescription.setPontosEnvidoRobo(calcularPontosEnvidoAgente(1));
        if (listaCartasJogadasAgente2.size() > 0) {
            trucoDescription.setPrimeiraCartaHumano(listaCartasJogadasAgente2.get(0).getId());
        }
        trucoDescription.setTentosAnterioresRobo(controlaPartidaAuto.getPontosAnterioresAgente1());
        trucoDescription.setTentosAnterioresHumano(controlaPartidaAuto.getPontosAnterioresAgente2());

        if (!QuemPediuEnvido.equals(0)) {
            trucoDescription.setQuemPediuEnvido(QuemPediuEnvido);
        }

        if (!QuemPediuRealEnvido.equals(0)) {
            trucoDescription.setQuemPediuEnvido(QuemPediuRealEnvido);
        }

        if (!QuemPediuFaltaEnvido.equals(0)) {
            trucoDescription.setQuemPediuEnvido(QuemPediuFaltaEnvido);
        }

        trucoDescription.setBluff1Success(controlaPartidaAuto.getCountBluff1Success());
        trucoDescription.setBluff2Success(controlaPartidaAuto.getCountBluff2Success());
        trucoDescription.setBluff3Success(controlaPartidaAuto.getCountBluff3Success());
        trucoDescription.setBluff4Success(controlaPartidaAuto.getCountBluff4Success());
        trucoDescription.setBluff5Success(controlaPartidaAuto.getCountBluff5Success());
        trucoDescription.setBluff6Success(controlaPartidaAuto.getCountBluff6Success());


        trucoDescription.setBluff1Failure(controlaPartidaAuto.getCountBluff1Failure());
        trucoDescription.setBluff2Failure(controlaPartidaAuto.getCountBluff2Failure());
        trucoDescription.setBluff3Failure(controlaPartidaAuto.getCountBluff3Failure());
        trucoDescription.setBluff4Failure(controlaPartidaAuto.getCountBluff4Failure());
        trucoDescription.setBluff5Failure(controlaPartidaAuto.getCountBluff5Failure());
        trucoDescription.setBluff6Failure(controlaPartidaAuto.getCountBluff6Failure());

        trucoDescription.setBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent());
        trucoDescription.setBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent());
        trucoDescription.setBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent());
        trucoDescription.setBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent());
        trucoDescription.setBluff5Opponent(controlaPartidaAuto.getCountBluff5Opponent());
        trucoDescription.setBluff6Opponent(controlaPartidaAuto.getCountBluff6Opponent());

        trucoDescription.setBluff1ShowDown(controlaPartidaAuto.getCountBluff1ShowDown());
        trucoDescription.setBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown());
        trucoDescription.setBluff3ShowDown(controlaPartidaAuto.getCountBluff3ShowDown());
        trucoDescription.setBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown());
        trucoDescription.setBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown());
        trucoDescription.setBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown());

        return trucoDescription;

    }

    public TrucoDescription getDescriptionTrucoActiveLearning() {

        Integer QuemTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getQuemChamou()
                : 0);
        Integer QuandoTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getEmQualRodada()
                : 0);
        Integer QuemRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getQuemChamou()
                : 0);
        Integer QuandoRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getEmQualRodada()
                : 0);
        Integer QuemValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getQuemChamou()
                : 0);
        Integer QuandoValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getEmQualRodada()
                : 0);

        TrucoDescription trucoDescription = new TrucoDescription();

        trucoDescription.setJogadorMao(controlaPartidaAuto.getQuemEhMao());
        trucoDescription.setCartaAltaRobo(listaCartasRecebidasAgente1.get(0).getId());
        trucoDescription.setCartaMediaRobo(listaCartasRecebidasAgente1.get(1).getId());
        trucoDescription.setCartaBaixaRobo(listaCartasRecebidasAgente1.get(2).getId());

        if (listaCartasJogadasAgente1.size() > 0)
            trucoDescription.setPrimeiraCartaRobo(listaCartasJogadasAgente1.get(0).getId());
        if (listaCartasJogadasAgente1.size() > 1)
            trucoDescription.setSegundaCartaRobo(listaCartasJogadasAgente1.get(1).getId());
        if (listaCartasJogadasAgente1.size() > 2)
            trucoDescription.setTerceiraCartaRobo(listaCartasJogadasAgente1.get(2).getId());

        if (listaCartasJogadasAgente2.size() > 0)
            trucoDescription.setPrimeiraCartaHumano(listaCartasJogadasAgente2.get(0).getId());
        if (listaCartasJogadasAgente2.size() > 1)
            trucoDescription.setSegundaCartaHumano(listaCartasJogadasAgente2.get(1).getId());
        if (listaCartasJogadasAgente2.size() > 2)
            trucoDescription.setTerceiraCartaHumano(listaCartasJogadasAgente2.get(2).getId());

        if (contadorMao > 1)
            trucoDescription.setGanhadorPrimeiraRodada(quemGanhouAPrimeiraMao());
        if (contadorMao > 2)
            trucoDescription.setGanhadorSegundaRodada(quemGanhouASegundaMao());
        if (contadorMao > 3)
            trucoDescription.setGanhadorTerceiraRodada(quemGanhouATerceiraMao());

        if (!QuemTruco.equals(0))
            trucoDescription.setQuemTruco(QuemTruco);

        if (!QuemRetruco.equals(0))
            trucoDescription.setQuemRetruco(QuemRetruco);

        if (!QuemValeQuatro.equals(0))
            trucoDescription.setQuemValeQuatro(QuemValeQuatro);

        if(!QuandoTruco.equals(0))
            trucoDescription.setQuandoTruco(QuandoTruco);
        if(!QuandoRetruco.equals(0))
            trucoDescription.setQuandoRetruco(QuandoRetruco);
        if(!QuandoValeQuatro.equals(0))
            trucoDescription.setQuandoValeQuatro(QuandoValeQuatro);

        trucoDescription.setTentosAnterioresRobo(controlaPartidaAuto.getPontosAnterioresAgente1());
        trucoDescription.setTentosAnterioresHumano(controlaPartidaAuto.getPontosAnterioresAgente2());

        trucoDescription.setBluff1Success(controlaPartidaAuto.getCountBluff1Success());
        trucoDescription.setBluff2Success(controlaPartidaAuto.getCountBluff2Success());
        trucoDescription.setBluff3Success(controlaPartidaAuto.getCountBluff3Success());
        trucoDescription.setBluff4Success(controlaPartidaAuto.getCountBluff4Success());
        trucoDescription.setBluff5Success(controlaPartidaAuto.getCountBluff5Success());
        trucoDescription.setBluff6Success(controlaPartidaAuto.getCountBluff6Success());


        trucoDescription.setBluff1Failure(controlaPartidaAuto.getCountBluff1Failure());
        trucoDescription.setBluff2Failure(controlaPartidaAuto.getCountBluff2Failure());
        trucoDescription.setBluff3Failure(controlaPartidaAuto.getCountBluff3Failure());
        trucoDescription.setBluff4Failure(controlaPartidaAuto.getCountBluff4Failure());
        trucoDescription.setBluff5Failure(controlaPartidaAuto.getCountBluff5Failure());
        trucoDescription.setBluff6Failure(controlaPartidaAuto.getCountBluff6Failure());

        trucoDescription.setBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent());
        trucoDescription.setBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent());
        trucoDescription.setBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent());
        trucoDescription.setBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent());
        trucoDescription.setBluff5Opponent(controlaPartidaAuto.getCountBluff5Opponent());
        trucoDescription.setBluff6Opponent(controlaPartidaAuto.getCountBluff6Opponent());

        trucoDescription.setBluff1ShowDown(controlaPartidaAuto.getCountBluff1ShowDown());
        trucoDescription.setBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown());
        trucoDescription.setBluff3ShowDown(controlaPartidaAuto.getCountBluff3ShowDown());
        trucoDescription.setBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown());
        trucoDescription.setBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown());
        trucoDescription.setBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown());

        return trucoDescription;

    }

    public TrucoDescription getDescriptionAtibutosBlefe() {
        TrucoDescription trucoDescription = new TrucoDescription();

        trucoDescription.setBluff1Success(controlaPartidaAuto.getCountBluff1Success());
        trucoDescription.setBluff2Success(controlaPartidaAuto.getCountBluff2Success());
        trucoDescription.setBluff3Success(controlaPartidaAuto.getCountBluff3Success());
        trucoDescription.setBluff4Success(controlaPartidaAuto.getCountBluff4Success());
        trucoDescription.setBluff5Success(controlaPartidaAuto.getCountBluff5Success());
        trucoDescription.setBluff6Success(controlaPartidaAuto.getCountBluff6Success());


        trucoDescription.setBluff1Failure(controlaPartidaAuto.getCountBluff1Failure());
        trucoDescription.setBluff2Failure(controlaPartidaAuto.getCountBluff2Failure());
        trucoDescription.setBluff3Failure(controlaPartidaAuto.getCountBluff3Failure());
        trucoDescription.setBluff4Failure(controlaPartidaAuto.getCountBluff4Failure());
        trucoDescription.setBluff5Failure(controlaPartidaAuto.getCountBluff5Failure());
        trucoDescription.setBluff6Failure(controlaPartidaAuto.getCountBluff6Failure());

        trucoDescription.setBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent());
        trucoDescription.setBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent());
        trucoDescription.setBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent());
        trucoDescription.setBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent());
        trucoDescription.setBluff5Opponent(controlaPartidaAuto.getCountBluff5Opponent());
        trucoDescription.setBluff6Opponent(controlaPartidaAuto.getCountBluff6Opponent());

        trucoDescription.setBluff1ShowDown(controlaPartidaAuto.getCountBluff1ShowDown());
        trucoDescription.setBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown());
        trucoDescription.setBluff3ShowDown(controlaPartidaAuto.getCountBluff3ShowDown());
        trucoDescription.setBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown());
        trucoDescription.setBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown());
        trucoDescription.setBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown());

        return trucoDescription;

    }

    public void criaDescriptionParaPersistir() {

        System.out.println("TotalDecisions: " + controlaPartidaAuto.getMatch().getDecisions().size());

        verificaCartasNaoJogadas();
        Integer Agente1CartaVirada = verificaCartaVirada(1);
        Integer Agente2CartaVirada = verificaCartaVirada(2);
        Integer QuemPediuEnvido = (retornaPontosChamado("Envido") != null
                ? retornaPontosChamado("Envido").getQuemChamou()
                : 0);
        Integer QuemPediuFaltaEnvido = (retornaPontosChamado("FaltaEnvido") != null
                ? retornaPontosChamado("FaltaEnvido").getQuemChamou()
                : 0);
        Integer QuemPediuRealEnvido = (retornaPontosChamado("RealEnvido") != null
                ? retornaPontosChamado("RealEnvido").getQuemChamou()
                : 0);
        Integer PontosEnvidoAgente1 = 0;
        Integer PontosEnvidoAgente2 = 0;
        Integer QuemNegouEnvido = retornaPontosNegados();
        Integer QuemGanhouEnvido = (contadorMao > 1 && retornaPontosChamado("Envido") != null
                || retornaPontosChamado("RealEnvido") != null || retornaPontosChamado("FaltaEnvido") != null
                ? QuemGanhouOsPontos()
                : 0);
        if (QuemGanhouEnvido != null) {
            PontosEnvidoAgente1 = calcularPontosEnvidoAgente(1);
            PontosEnvidoAgente2 = calcularPontosEnvidoAgente(2);
        }
        Integer TentosEnvido = tentosRodada.getTentosEnvido();
        // verifica Flor
        Integer QuemFlor = 0;
        if (verificarSeAgenteTemFlor(2))
            QuemFlor = 2;
        else if (verificarSeAgenteTemFlor(1))
            QuemFlor = 1;
        Integer QuemContraFlor = 0;
        Integer QuemContraFlorResto = 0;
        Integer QuemNegouFlor = 0;
        Integer PontosFlorAgente1 = (verificarSeAgenteTemFlor(1) ? calcularPontosFlorAgente(1) : 0);
        Integer PontosFlorAgente2 = (verificarSeAgenteTemFlor(2) ? calcularPontosFlorAgente(2) : 0);
        Integer QuemGanhouFlor = retornaQuemGanhouAflor();
        Integer TentosFlor = tentosRodada.getTentosFlor();
        if (QuemGanhouFlor != null) {
            if (retornaQuemGanhouAflor() == 1)
                TentosFlor = calcularPontosFlorAgente(1);
            else if (retornaQuemGanhouAflor() == 2)
                TentosFlor = calcularPontosFlorAgente(2);
        }
        Integer QuemEscondeuPontosEnvido = 0;
        Integer QuemEscondeuPontosFlor = 0;
        Integer QuemTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getQuemChamou()
                : 0);
        Integer QuandoTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getEmQualRodada()
                : 0);
        Integer QuemRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getQuemChamou()
                : 0);
        Integer QuandoRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getEmQualRodada()
                : 0);
        Integer QuemValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getQuemChamou()
                : 0);
        Integer QuandoValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getEmQualRodada()
                : 0);
        Integer QuemNegouTruco = (!listaJogadasChamadas.isEmpty() ? verificaQuemGanhouOjogo().getQuemNaoAceitouPontos()
                : 0);
        Integer QuemGanhouTruco = (!listaJogadasChamadas.isEmpty() ? verificaQuemGanhouOjogo().getQuemGanhou() : 0);
        Integer TentosTruco = tentosRodada.getTentosTruco();
        Integer TentosAnterioresAgente1 = controlaPartidaAuto.getPontosAnterioresAgente1();
        Integer TentosAnterioresAgente2 = controlaPartidaAuto.getPontosAnterioresAgente2();
        Integer TentosPosterioresAgente1 = controlaPartidaAuto.getPontosAgente1();
        Integer TentosPosterioresAgente2 = controlaPartidaAuto.getPontosAgente2();
        Integer Agente1MentiuEnvido = AgenteMentiuNoEnvido(1);
        Integer Agente2MentiuEnvido = AgenteMentiuNoEnvido(2);
        Integer Agente1MentiuFlor = AgenteMentiuNaFlor(1);
        Integer Agente2MentiuFlor = AgenteMentiuNaFlor(2);
        Integer Agente1MentiuTruco = 0;
        Integer Agente2MentiuTruco = 0;

        //trocar trucoDescription setado individualmente
        TrucoDescription NovoDescription1 = new TrucoDescription();
        NovoDescription1.setIdMao(99);
        NovoDescription1.setIdPartida("Agente1-");
        NovoDescription1.setJogadorMao(validaCamposNulos(controlaPartidaAuto.getQuemEhMao()));
        NovoDescription1.setCartaAltaRobo(validaCamposNulos(listaCartasRecebidasAgente1.get(0).getId()));
        NovoDescription1.setCartaMediaRobo(validaCamposNulos(listaCartasRecebidasAgente1.get(1).getId()));
        NovoDescription1.setCartaBaixaRobo(validaCamposNulos(listaCartasRecebidasAgente1.get(2).getId()));

        NovoDescription1.setCartaAltaHumano(validaCamposNulos(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente2, "Alta").getId()));
        NovoDescription1.setCartaMediaHumano(validaCamposNulos( retornaListaDeCartasPorImportancia(listaCartasJogadasAgente2, "Media").getId()));
        NovoDescription1.setCartaBaixaHumano(validaCamposNulos(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente2, "Baixa").getId()));

        NovoDescription1.setPrimeiraCartaRobo(validaCamposNulos(listaCartasJogadasAgente1.get(0).getId()));
        NovoDescription1.setSegundaCartaRobo(validaCamposNulos(listaCartasJogadasAgente1.get(1).getId()));
        NovoDescription1.setTerceiraCartaRobo(validaCamposNulos(listaCartasJogadasAgente1.get(2).getId()));

        NovoDescription1.setPrimeiraCartaHumano(validaCamposNulos(listaCartasJogadasAgente2.get(0).getId()));
        NovoDescription1.setSegundaCartaHumano(validaCamposNulos(listaCartasJogadasAgente2.get(1).getId()));
        NovoDescription1.setTerceiraCartaHumano(validaCamposNulos(listaCartasJogadasAgente2.get(2).getId()));

        NovoDescription1.setGanhadorPrimeiraRodada(validaCamposNulos(GanhadorPrimeira));
        NovoDescription1.setGanhadorSegundaRodada(validaCamposNulos(GanhadorSegunda));
        NovoDescription1.setGanhadorTerceiraRodada(validaCamposNulos(GanhadorTerceira));

        NovoDescription1.setRoboCartaVirada(validaCamposNulos(Agente1CartaVirada));
        NovoDescription1.setHumanoCartaVirada(validaCamposNulos(Agente2CartaVirada));

        NovoDescription1.setQuemPediuEnvido(validaCamposNulos(QuemPediuEnvido));
        NovoDescription1.setQuemPediuRealEnvido(validaCamposNulos(QuemPediuRealEnvido));
        NovoDescription1.setQuemPediuFaltaEnvido(validaCamposNulos(QuemPediuFaltaEnvido));

        NovoDescription1.setPontosEnvidoRobo(validaCamposNulos( PontosEnvidoAgente1));
        NovoDescription1.setPontosEnvidoHumano(validaCamposNulos(PontosEnvidoAgente2));

        NovoDescription1.setQuemNegouEnvido(validaCamposNulos(QuemNegouEnvido));
        NovoDescription1.setQuemGanhouEnvido(validaCamposNulos(QuemGanhouEnvido));
        NovoDescription1.setTentosEnvido(validaCamposNulos(TentosEnvido));

        NovoDescription1.setQuemFlor(validaCamposNulos(QuemFlor));
        NovoDescription1.setQuemContraFlor(validaCamposNulos(QuemContraFlor));
        NovoDescription1.setQuemContraFlorResto(validaCamposNulos(QuemContraFlorResto));
        NovoDescription1.setQuemNegouFlor(validaCamposNulos(QuemNegouFlor));
        NovoDescription1.setPontosFlorRobo(validaCamposNulos(PontosFlorAgente1));
        NovoDescription1.setPontosFlorHumano(validaCamposNulos(PontosFlorAgente2));
        NovoDescription1.setQuemGanhouFlor(validaCamposNulos(QuemGanhouFlor));
        NovoDescription1.setQuemContraFlor(validaCamposNulos(QuemContraFlor));
        NovoDescription1.setQuemContraFlorResto(validaCamposNulos(QuemContraFlorResto));
        NovoDescription1.setQuemGanhouFlor(validaCamposNulos(QuemGanhouFlor));
        NovoDescription1.setTentosFlor(validaCamposNulos(TentosFlor));

        NovoDescription1.setQuemEscondeuPontosEnvido(validaCamposNulos(QuemEscondeuPontosEnvido));
        NovoDescription1.setQuemEscondeuPontosFlor(validaCamposNulos(QuemEscondeuPontosFlor));

        NovoDescription1.setQuemTruco(validaCamposNulos(QuemTruco));
        NovoDescription1.setQuemRetruco(validaCamposNulos(QuemRetruco));
        NovoDescription1.setQuemValeQuatro(validaCamposNulos(QuemValeQuatro));
        NovoDescription1.setQuemNegouTruco(validaCamposNulos(QuemNegouTruco));
        NovoDescription1.setQuemGanhouTruco(validaCamposNulos(QuemGanhouTruco));

        NovoDescription1.setQuandoTruco(validaCamposNulos(QuandoTruco));
        NovoDescription1.setQuandoRetruco(validaCamposNulos(QuandoRetruco));
        NovoDescription1.setQuandoValeQuatro(validaCamposNulos(QuandoValeQuatro));

        NovoDescription1.setTentosTruco(validaCamposNulos(TentosTruco));
        NovoDescription1.setTentosAnterioresRobo(validaCamposNulos(TentosAnterioresAgente1));
        NovoDescription1.setTentosAnterioresHumano(validaCamposNulos(TentosAnterioresAgente2));
        NovoDescription1.setTentosPosterioresRobo(validaCamposNulos(TentosPosterioresAgente1));
        NovoDescription1.setTentosPosterioresHumano(validaCamposNulos(TentosPosterioresAgente2));

        NovoDescription1.setRoboMentiuEnvido(validaCamposNulos(Agente1MentiuEnvido));
        NovoDescription1.setHumanoMentiuEnvido(validaCamposNulos(Agente2MentiuEnvido));
        NovoDescription1.setRoboMentiuFlor(validaCamposNulos(Agente1MentiuFlor));
        NovoDescription1.setHumanoMentiuFlor(validaCamposNulos(Agente2MentiuFlor));
        NovoDescription1.setRoboMentiuTruco(validaCamposNulos(Agente1MentiuTruco));

        NovoDescription1.setHumanoMentiuTruco(validaCamposNulos(Agente2MentiuTruco));

        NovoDescription1.setQuemBaralho(validaCamposNulos(quemBaralho));
        NovoDescription1.setQuandoBaralho(validaCamposNulos(quandoBaralho));

        NovoDescription1.setNaipeCartaAltaRobo(listaCartasRecebidasAgente1.get(0).getNaipe());
        NovoDescription1.setNaipeCartaMediaRobo(listaCartasRecebidasAgente1.get(1).getNaipe());
        NovoDescription1.setNaipeCartaBaixaRobo(listaCartasRecebidasAgente1.get(2).getNaipe());

        NovoDescription1.setNaipeCartaAltaHumano(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente2, "Alta").getNaipe());
        NovoDescription1.setNaipeCartaMediaHumano(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente2, "Media").getNaipe());
        NovoDescription1.setNaipeCartaBaixaHumano(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente2, "Baixa").getNaipe());

        NovoDescription1.setNaipePrimeiraCartaRobo(listaCartasJogadasAgente1.get(0).getNaipe());
        NovoDescription1.setNaipePrimeiraCartaHumano(listaCartasJogadasAgente2.get(0).getNaipe());

        NovoDescription1.setNaipeSegundaCartaRobo(listaCartasJogadasAgente1.get(1).getNaipe());
        NovoDescription1.setNaipeSegundaCartaHumano(listaCartasJogadasAgente2.get(1).getNaipe());
        NovoDescription1.setNaipeTerceiraCartaRobo(listaCartasJogadasAgente1.get(2).getNaipe());
        NovoDescription1.setNaipeTerceiraCartaHumano(listaCartasJogadasAgente2.get(2).getNaipe());

        NovoDescription1.setUtilCarta(utilCarta ? 1 : 0);
        NovoDescription1.setUtilTruco(utilTruco ? 1 : 0);
        NovoDescription1.setUtilEnvido(utilEnvido ? 1 : 0);
        NovoDescription1.setHasDeception(hasDeception ? 1 : 0);

        NovoDescription1.setBluff1Failure(controlaPartidaAuto.getCountBluff1Failure());
        NovoDescription1.setBluff1Success(controlaPartidaAuto.getCountBluff1Success());
        NovoDescription1.setBluff2Failure(controlaPartidaAuto.getCountBluff2Failure());
        NovoDescription1.setBluff2Success(controlaPartidaAuto.getCountBluff2Success());
        NovoDescription1.setBluff3Failure(controlaPartidaAuto.getCountBluff3Failure());
        NovoDescription1.setBluff3Success(controlaPartidaAuto.getCountBluff3Success());
        NovoDescription1.setBluff4Failure(controlaPartidaAuto.getCountBluff4Failure());
        NovoDescription1.setBluff4Success(controlaPartidaAuto.getCountBluff4Success());
        NovoDescription1.setBluff5Failure(controlaPartidaAuto.getCountBluff5Failure());
        NovoDescription1.setBluff5Success(controlaPartidaAuto.getCountBluff5Success());
        NovoDescription1.setBluff6Failure(controlaPartidaAuto.getCountBluff6Failure());
        NovoDescription1.setBluff6Success(controlaPartidaAuto.getCountBluff6Success());

        NovoDescription1.setBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent());
        NovoDescription1.setBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent());
        NovoDescription1.setBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent());
        NovoDescription1.setBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent());
        NovoDescription1.setBluff5Opponent(controlaPartidaAuto.getCountBluff5Opponent());
        NovoDescription1.setBluff6Opponent(controlaPartidaAuto.getCountBluff6Opponent());

        NovoDescription1.setBluff1ShowDown(controlaPartidaAuto.getCountBluff1ShowDown());
        NovoDescription1.setBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown());
        NovoDescription1.setBluff3ShowDown(controlaPartidaAuto.getCountBluff3ShowDown());
        NovoDescription1.setBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown());
        NovoDescription1.setBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown());
        NovoDescription1.setBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown());



		/*NovoDescription1.setEnvidoJustifications(envidoJustifications);
		NovoDescription1.setTrucoJustifications(trucoJustifications);*/


        //trocar trucoDescription setado individualmente
        TrucoDescription NovoDescription2 = new TrucoDescription();
        NovoDescription2.setIdMao(88);
        NovoDescription2.setIdPartida("agente2-");

        NovoDescription2.setJogadorMao(inverterJogadores(controlaPartidaAuto.getQuemEhMao()));

        NovoDescription2.setCartaAltaRobo(listaCartasRecebidasAgente2.get(0).getId());
        NovoDescription2.setCartaMediaRobo(listaCartasRecebidasAgente2.get(1).getId());
        NovoDescription2.setCartaBaixaRobo(listaCartasRecebidasAgente2.get(2).getId());

        NovoDescription2.setCartaAltaHumano(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente1, "Alta").getId());
        NovoDescription2.setCartaMediaHumano(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente1, "Media").getId());
        NovoDescription2.setCartaBaixaHumano(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente1, "Baixa").getId());

        NovoDescription2.setPrimeiraCartaRobo(listaCartasJogadasAgente2.get(0).getId());
        NovoDescription2.setSegundaCartaRobo(listaCartasJogadasAgente2.get(1).getId());
        NovoDescription2.setTerceiraCartaRobo(listaCartasJogadasAgente2.get(2).getId());

        NovoDescription2.setPrimeiraCartaHumano(listaCartasJogadasAgente1.get(0).getId());
        NovoDescription2.setSegundaCartaHumano(listaCartasJogadasAgente1.get(1).getId());
        NovoDescription2.setTerceiraCartaHumano(listaCartasJogadasAgente1.get(2).getId());

        NovoDescription2.setGanhadorPrimeiraRodada(inverterJogadores(GanhadorPrimeira));
        NovoDescription2.setGanhadorSegundaRodada(inverterJogadores(GanhadorSegunda));
        NovoDescription2.setGanhadorTerceiraRodada(inverterJogadores(GanhadorTerceira));

        NovoDescription2.setRoboCartaVirada(Agente2CartaVirada);
        NovoDescription2.setHumanoCartaVirada(Agente1CartaVirada);

        NovoDescription2.setQuemPediuEnvido(inverterJogadores(QuemPediuEnvido));
        NovoDescription2.setQuemPediuRealEnvido(inverterJogadores(QuemPediuRealEnvido));
        NovoDescription2.setQuemPediuFaltaEnvido(inverterJogadores(QuemPediuFaltaEnvido));

        NovoDescription2.setPontosEnvidoRobo(PontosEnvidoAgente2);
        NovoDescription2.setPontosEnvidoHumano(PontosEnvidoAgente1);

        NovoDescription2.setQuemNegouEnvido(inverterJogadores(QuemNegouEnvido));
        NovoDescription2.setQuemGanhouEnvido(inverterJogadores(QuemGanhouEnvido));
        NovoDescription2.setTentosEnvido(TentosEnvido);

        NovoDescription2.setQuemFlor(inverterJogadores(QuemFlor));
        NovoDescription2.setQuemContraFlor(inverterJogadores(QuemContraFlor));
        NovoDescription2.setQuemContraFlorResto(inverterJogadores(QuemContraFlorResto));
        NovoDescription2.setQuemNegouFlor(inverterJogadores(QuemNegouFlor));

        NovoDescription2.setPontosFlorRobo(PontosFlorAgente2);
        NovoDescription2.setPontosFlorHumano(PontosFlorAgente1);

        NovoDescription2.setQuemGanhouFlor(inverterJogadores(QuemGanhouFlor));
        NovoDescription2.setQuemContraFlor(inverterJogadores(QuemContraFlor));
        NovoDescription2.setQuemContraFlorResto(inverterJogadores(QuemContraFlorResto));
        NovoDescription2.setQuemGanhouFlor(inverterJogadores(QuemGanhouFlor));

        NovoDescription2.setTentosFlor(TentosFlor);

        NovoDescription2.setQuemEscondeuPontosEnvido(inverterJogadores(QuemEscondeuPontosEnvido));
        NovoDescription2.setQuemEscondeuPontosFlor(inverterJogadores(QuemEscondeuPontosFlor));

        NovoDescription2.setQuemTruco(inverterJogadores(QuemTruco));
        NovoDescription2.setQuemRetruco(inverterJogadores(QuemRetruco));
        NovoDescription2.setQuemValeQuatro(inverterJogadores(QuemValeQuatro));
        NovoDescription2.setQuemNegouTruco(inverterJogadores(QuemNegouTruco));
        NovoDescription2.setQuemGanhouTruco(inverterJogadores(QuemGanhouTruco));

        NovoDescription2.setQuandoTruco(QuandoTruco);
        NovoDescription2.setQuandoRetruco(QuandoRetruco);
        NovoDescription2.setQuandoValeQuatro(QuandoValeQuatro);

        NovoDescription2.setTentosTruco(TentosTruco);
        NovoDescription2.setTentosAnterioresRobo(TentosAnterioresAgente2);
        NovoDescription2.setTentosAnterioresHumano(TentosAnterioresAgente1);
        NovoDescription2.setTentosPosterioresRobo(TentosPosterioresAgente2);
        NovoDescription2.setTentosPosterioresHumano(TentosPosterioresAgente1);

        NovoDescription2.setRoboMentiuEnvido(Agente2MentiuEnvido);
        NovoDescription2.setHumanoMentiuEnvido(Agente1MentiuEnvido);
        NovoDescription2.setRoboMentiuFlor(Agente2MentiuFlor);
        NovoDescription2.setHumanoMentiuFlor(Agente1MentiuFlor);
        NovoDescription2.setRoboMentiuTruco(Agente2MentiuTruco);

        NovoDescription2.setHumanoMentiuTruco(Agente1MentiuTruco);

        NovoDescription2.setQuemBaralho(inverterJogadores(quemBaralho));
        NovoDescription2.setQuandoBaralho(quandoBaralho);

        NovoDescription2.setNaipeCartaAltaRobo(listaCartasRecebidasAgente2.get(0).getNaipe());
        NovoDescription2.setNaipeCartaMediaRobo(listaCartasRecebidasAgente2.get(1).getNaipe());
        NovoDescription2.setNaipeCartaBaixaRobo(listaCartasRecebidasAgente2.get(2).getNaipe());

        NovoDescription2.setNaipeCartaAltaHumano(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente1, "Alta").getNaipe());
        NovoDescription2.setNaipeCartaMediaHumano(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente1, "Media").getNaipe());
        NovoDescription2.setNaipeCartaBaixaHumano(retornaListaDeCartasPorImportancia(listaCartasJogadasAgente1, "Baixa").getNaipe());

        NovoDescription2.setNaipePrimeiraCartaRobo(listaCartasJogadasAgente2.get(0).getNaipe());
        NovoDescription2.setNaipePrimeiraCartaHumano(listaCartasJogadasAgente1.get(0).getNaipe());

        NovoDescription2.setNaipeSegundaCartaRobo(listaCartasJogadasAgente2.get(1).getNaipe());
        NovoDescription2.setNaipeSegundaCartaHumano(listaCartasJogadasAgente1.get(1).getNaipe());
        NovoDescription2.setNaipeTerceiraCartaRobo(listaCartasJogadasAgente2.get(2).getNaipe());
        NovoDescription2.setNaipeTerceiraCartaHumano(listaCartasJogadasAgente1.get(2).getNaipe());


        // aprende e retorna o caso como string para salvar no log
        trucoCbr_Agente1.retain(NovoDescription1, compulsoryRetention);
        trucoCbr_Agente2.retain(NovoDescription2);

        trucoCbr_Agente1.zeraGruposInformacoesRodadaFinalizada();
        trucoCbr_Agente2.zeraGruposInformacoesRodadaFinalizada();
    }

    public void setResultToEachDecision() {

        Integer QuemPediuEnvido = (retornaPontosChamado("Envido") != null ? retornaPontosChamado("Envido").getQuemChamou() : 0);
        Integer QuemPediuFaltaEnvido = (retornaPontosChamado("FaltaEnvido") != null ? retornaPontosChamado("FaltaEnvido").getQuemChamou() : 0);
        Integer QuemPediuRealEnvido = (retornaPontosChamado("RealEnvido") != null ? retornaPontosChamado("RealEnvido").getQuemChamou() : 0);
        Integer QuemNegouEnvido = retornaPontosNegados();
        Integer QuemGanhouEnvido = (retornaPontosChamado("Envido") != null || retornaPontosChamado("RealEnvido") != null || retornaPontosChamado("FaltaEnvido") != null
                ? QuemGanhouOsPontos()
                : 0);
        Integer ganhadorEnvido = 0;
        if (!QuemNegouEnvido.equals(0)) {
            ganhadorEnvido = QuemNegouEnvido == 1 ? 2 : 1;
        } else {
            if (retornaPontosChamado("Envido") != null || retornaPontosChamado("RealEnvido") != null || retornaPontosChamado("FaltaEnvido") != null) {
                ganhadorEnvido = QuemGanhouOsPontos();
            } else {
                ganhadorEnvido = 0;
            }
        }
        int isHand = controlaPartidaAuto.getQuemEhMao() == 2 ? 1 : 0;
        Integer TentosEnvido = tentosRodada.getTentosEnvido();
        Integer QuemTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getQuemChamou()
                : 0);
        Integer QuemRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getQuemChamou()
                : 0);
        Integer QuemNegouTruco = (!listaJogadasChamadas.isEmpty() ? verificaQuemGanhouOjogo().getQuemNaoAceitouPontos()
                : 0);
        Integer QuemGanhouTruco = (!listaJogadasChamadas.isEmpty() ? verificaQuemGanhouOjogo().getQuemGanhou() : 0);
        Integer TentosTruco = tentosRodada.getTentosTruco();

        for (Decision decision : controlaPartidaAuto.getMatch().getDecisions()) {

            if (decision.getHandNumber().equals(controlaPartidaAuto.getRodadaNumber())) {

                switch (decision.getTypeDecision()) {
                    case 1:

                        if (decision.getDecision().equals("No Call")) {
                            if (isHand == 1) {
                                if (decision.getProbWin() > 0.85) {
                                    decision.setIsBluff(1);
                                    decision.setTypeBluff(2);
                                    decision.setBluffCanBeDetected(blefeEnvidoPodeSerDescoberto() ? 1 : 0);
                                    //# Blefe 2 Agente (SlowPlay) --> Agente é mão e possui muitos pontos e deixa de chamar envido/real/falta
                                    if (QuemPediuEnvido == 0 && QuemPediuRealEnvido == 0 && QuemPediuFaltaEnvido == 0) {
                                        decision.setIndSuccess(0);
                                    } else if (QuemPediuEnvido == 2 && (QuemPediuRealEnvido == 1 || QuemPediuFaltaEnvido == 1)) {
                                        decision.setIndSuccess(1);
                                    } else if (QuemPediuRealEnvido == 2 && QuemPediuFaltaEnvido == 1) {
                                        decision.setIndSuccess(1);
                                    }
                                }
                            }


                        } else if (decision.getDecision().equals("Envido") || decision.getDecision().equals("RealEnvido") || decision.getDecision().equals("FaltaEnvido")) {
                            if (decision.getProbWin() < 0.50) {
                                decision.setIsBluff(1);
                                decision.setTypeBluff(decision.getStateDecision().equals(2) ? 3 : 1);
                                if (QuemNegouEnvido == 2) {
                                    decision.setIndSuccess(1);
                                    decision.setBluffCanBeDetected(blefeEnvidoPodeSerDescoberto() ? 1 : 0);
                                } else {
                                    decision.setIndSuccess(0);
                                    decision.setBluffCanBeDetected(1);
                                }
                            }
                        } else if (decision.getDecision().equals("Accept")) {

                            decision.setIndSuccess(QuemGanhouEnvido.equals(1) ? 1 : 0);

                        } else if (decision.getDecision().equals("Disclaim")) {
                            decision.setIndSuccess(isOponenteBlefouEnvido() ? 0 : 1);
                        }

                        if (ganhadorEnvido.equals(1)) {
                            decision.setAchievedPoints(TentosEnvido);
                        } else {
                            decision.setAchievedPoints(TentosEnvido * -1);
                        }
                        break;
                    case 2:
                        if (decision.getDecision().equals("No Call")) {
                            if (decision.getStateDecision() == 1 || decision.getStateDecision() == 3 || decision.getStateDecision() == 5) {
                                if (decision.getProbWin() > 0.85) {
                                    decision.setIsBluff(1);
                                    decision.setTypeBluff(5);
                                    decision.setBluffCanBeDetected(blefeTrucoPodeSerDescoberto() ? 1 : 0);
                                    //# Blefe 5 Agente (SlowPlay) --> Agente é mão e possui mão forte e deixa de chamar truco/Retruco/Vale4
                                    if (QuemTruco == 0) {
                                        decision.setIndSuccess(0);
                                    } else if (QuemTruco == 2 && QuemRetruco == 1) {
                                        decision.setIndSuccess(1);
                                    }
                                }
                            }

                        } else if (decision.getDecision().equals("Truco") || decision.getDecision().equals("Retruco") || decision.getDecision().equals("ValeQuatro")) {
                            if (decision.getProbWin() < 0.5) {
                                decision.setIsBluff(1);
                                decision.setTypeBluff(decision.getStateDecision().equals(6) ? 6 : 4);
                                if (QuemNegouTruco == 2) {
                                    decision.setIndSuccess(1);
                                    decision.setBluffCanBeDetected(blefeTrucoPodeSerDescoberto() ? 1 : 0);
                                } else {
                                    decision.setIndSuccess(0);
                                    decision.setBluffCanBeDetected(1);
                                }
                            }
                        } else if (decision.getDecision().equals("Accept")) {

                            decision.setIndSuccess(QuemGanhouTruco.equals(1) ? 1 : 0);

                        } else if (decision.getDecision().equals("Disclaim")) {
                            decision.setIndSuccess(isOponenteBlefouTruco() ? 0 : 1);
                        }

                        if (QuemGanhouTruco.equals(1)) {
                            decision.setAchievedPoints(TentosTruco);
                        } else {
                            decision.setAchievedPoints(TentosTruco * -1);
                        }
                        break;
                    case 3:
                        decision.setIndSuccess(QuemGanhouTruco.equals(1) ? 1 : 0);
                        break;

                }

            }

        }

    }

    public boolean isOponenteBlefouEnvido() {

        boolean blefou = false;

        Integer QuemPediuEnvido = (retornaPontosChamado("Envido") != null ? retornaPontosChamado("Envido").getQuemChamou() : 0);
        Integer QuemPediuFaltaEnvido = (retornaPontosChamado("FaltaEnvido") != null ? retornaPontosChamado("FaltaEnvido").getQuemChamou() : 0);
        Integer QuemPediuRealEnvido = (retornaPontosChamado("RealEnvido") != null ? retornaPontosChamado("RealEnvido").getQuemChamou() : 0);
        Integer QuemNegouEnvido = retornaPontosNegados();
        int isHand = controlaPartidaAuto.getQuemEhMao() == 2 ? 1 : 0;

        double probEnvido = 0.0;
        int pontosEnvido = calcularPontosEnvidoAgente(2);

        if (isHand == 0) {
            probEnvido = deck.getProbBestPoint(isHand, pontosEnvido, deck.getFilteredAgentHands(listaCartasRecebidasAgente2.get(0).getCarta(),
                    listaCartasRecebidasAgente2.get(1).getCarta(), listaCartasRecebidasAgente2.get(2).getCarta(),
                    listaCartasJogadasAgente1.get(0).getCarta()));
        } else {
            probEnvido = deck.getProbBestPoint(isHand, pontosEnvido, deck.getFilteredAgentHands(listaCartasRecebidasAgente2.get(0).getCarta(),
                    listaCartasRecebidasAgente2.get(1).getCarta(), listaCartasRecebidasAgente2.get(2).getCarta()));
        }

        if (listaCartasJogadasAgente2.size() == 3 && cartasJogadasMesa.size() > 4) {

            if (QuemNegouEnvido.equals(1)) {

                if ((QuemPediuEnvido == 2 || QuemPediuRealEnvido == 2 || QuemPediuFaltaEnvido == 2) && probEnvido < 0.5)  {
                    blefou = true;
                }

            }

        } else if (listaCartasJogadasAgente2.size() == 2) {

            if (listaCartasJogadasAgente2.get(0).getNaipe().equals(listaCartasJogadasAgente2.get(1).getNaipe())) {

                if (QuemNegouEnvido.equals(1)) {

                    if ((QuemPediuEnvido == 2 || QuemPediuRealEnvido == 2 || QuemPediuFaltaEnvido == 2) && probEnvido < 0.5)  {
                        blefou = true;
                    }

                }
            }

        }

        return blefou;

    }

    public boolean isOponenteBlefouTruco() {

        boolean blefou = false;

        Integer QuemTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getQuemChamou()
                : 0);
        Integer QuandoTruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("Truco") != null
                ? verificaInformacoesJogada("Truco").getEmQualRodada()
                : 0);
        Integer QuemRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getQuemChamou()
                : 0);
        Integer QuandoRetruco = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ReTruco") != null
                ? verificaInformacoesJogada("ReTruco").getEmQualRodada()
                : 0);
        Integer QuemValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getQuemChamou()
                : 0);
        Integer QuandoValeQuatro = (!listaJogadasChamadas.isEmpty() && verificaInformacoesJogada("ValeQuatro") != null
                ? verificaInformacoesJogada("ValeQuatro").getEmQualRodada()
                : 0);
        Integer QuemNegouTruco = (!listaJogadasChamadas.isEmpty() ? verificaQuemGanhouOjogo().getQuemNaoAceitouPontos()
                : 0);

        int isHand = controlaPartidaAuto.getQuemEhMao() == 2 ? 1 : 0;

        double oppHandStrength = deck.getStrenghtHand(listaCartasRecebidasAgente2.get(0).getCarta(),
                listaCartasRecebidasAgente2.get(1).getCarta(), 	listaCartasRecebidasAgente2.get(2).getCarta());

        double probMao = deck.getProbBestHand(isHand, oppHandStrength, deck.getFilteredAgentHands(listaCartasRecebidasAgente2.get(0).getCarta(),
                listaCartasRecebidasAgente2.get(1).getCarta(), 	listaCartasRecebidasAgente2.get(2).getCarta()));



        if (listaCartasJogadasAgente2.size() == 3 && cartasJogadasMesa.size() > 4) {

            if (QuemTruco == 2 || QuemRetruco == 2 || QuemValeQuatro == 2) {

                if (QuandoTruco == 1 || QuandoRetruco == 1 || QuandoValeQuatro == 1) {

                    if (isHand == 0) {
                        probMao = deck.getProbBestHand(isHand, oppHandStrength, deck.getFilteredAgentHands(listaCartasRecebidasAgente2.get(0).getCarta(),
                                listaCartasRecebidasAgente2.get(1).getCarta(), 	listaCartasRecebidasAgente2.get(2).getCarta(),
                                listaCartasJogadasAgente1.get(0).getCarta()));

                    }

                } else if (QuandoTruco == 2 || QuandoRetruco == 2 || QuandoValeQuatro == 2) {

                    if (validaCamposNulos(GanhadorPrimeira) == 1) {

                        probMao = deck.getProbBestHand(isHand, oppHandStrength, deck.getFilteredAgentHands(listaCartasRecebidasAgente2.get(0).getCarta(),
                                listaCartasRecebidasAgente2.get(1).getCarta(), 	listaCartasRecebidasAgente2.get(2).getCarta(),
                                listaCartasJogadasAgente1.get(0).getCarta(), listaCartasJogadasAgente1.get(1).getCarta()));

                    } else {
                        probMao = deck.getProbBestHand(isHand, oppHandStrength, deck.getFilteredAgentHands(listaCartasRecebidasAgente2.get(0).getCarta(),
                                listaCartasRecebidasAgente2.get(1).getCarta(), 	listaCartasRecebidasAgente2.get(2).getCarta(),
                                listaCartasJogadasAgente1.get(0).getCarta()));
                    }

                } else if (QuandoTruco == 3 || QuandoRetruco == 1 || QuandoValeQuatro == 3) {

                    if (validaCamposNulos(GanhadorSegunda) == 2) {

                        probMao = deck.getProbBestHand(isHand, oppHandStrength, deck.getFilteredAgentHands(listaCartasRecebidasAgente2.get(0).getCarta(),
                                listaCartasRecebidasAgente2.get(1).getCarta(), 	listaCartasRecebidasAgente2.get(2).getCarta(),
                                listaCartasJogadasAgente1.get(0).getCarta(), listaCartasJogadasAgente1.get(1).getCarta()));

                    } else {

                        CartasModelo ultimaCarta = null;
                        for (CartasModelo carta : listaCartasRecebidasAgente2) {
                            if (!carta.getCarta().equals(listaCartasJogadasAgente2.get(0).getCarta()) &&
                                    !carta.getCarta().equals(listaCartasJogadasAgente2.get(1).getCarta())) {
                                ultimaCarta = carta;
                            }
                        }

                        if (ultimaCarta.getId() > listaCartasJogadasAgente1.get(2).getId()) {
                            probMao = 1.0;
                        } else if (ultimaCarta.getId() == listaCartasJogadasAgente1.get(2).getId()) {
                            if (isHand == 1) {
                                probMao = 1.0;
                            } else {
                                probMao = 0.0;
                            }
                        } else {
                            probMao = 0.0;
                        }

                    }

                }
            }
        }


        if (listaCartasJogadasAgente2.size() == 3 && cartasJogadasMesa.size() > 4) {

            if (QuemNegouTruco.equals(1)) {

                if ((QuemTruco == 2 || QuemRetruco == 2 || QuemValeQuatro == 2) && probMao < 0.5)  {
                    blefou = true;
                }

            }

        }

        return blefou;

    }

    private Integer verificaCartaVirada(int quem) {
        Integer Virada = 0;
        if (quem == 1) {
            for (CartasModelo C : listaCartasJogadasAgente1) {
                if (C.isVirada())
                    Virada = listaCartasJogadasAgente1.indexOf(C);
            }
        } else {
            for (CartasModelo C : listaCartasJogadasAgente2) {
                if (C.isVirada())
                    Virada = listaCartasJogadasAgente2.indexOf(C);
            }
        }
        return Virada;
    }

    private CartasModelo retornaListaDeCartasPorImportancia(List<CartasModelo> listaCartas, String forca) {
        // so vai saber qual a alta, media e baixa se ele jogou as 3 cartas
        // dai ordena e retona, senao nao tem como saber de certeza qual ÃƒÂ© qual
        boolean jogouTodasCartas = true;
        for (CartasModelo c : listaCartas) {
            if (c.getId() == null) {
                jogouTodasCartas = false;
            }
        }
        if (jogouTodasCartas) {
            Collections.sort(listaCartas, new Comparator<CartasModelo>() {
                public int compare(CartasModelo carta2, CartasModelo carta1) {
                    return Integer.compare(carta1.valorImportancia, carta2.valorImportancia);
                }
            });
            if (listaCartas.size() == 3) {
                if (forca.equals("Alta"))
                    return listaCartas.get(0);
                if (forca.equals("Media"))
                    return listaCartas.get(1);
                if (forca.equals("Baixa"))
                    return listaCartas.get(2);
            }
        }
        return new CartasModelo();
    }

    public Integer inverterJogadores(Integer i) {
        int retorno =0;
        if (i != null) {
            if (i == 1)
                retorno = 2;
            if (i == 2)
                retorno =1;
        }

        return retorno;
    }

    public void verificaCartasNaoJogadas() {
//		System.out.println("VerificaCartaVirada");
        while (listaCartasJogadasAgente1.size() < 3) {
//			System.out.println("size 1   " +listaCartasJogadasAgente1.size());
            listaCartasJogadasAgente1.add(new CartasModelo(true));
        }
        while (listaCartasJogadasAgente2.size() < 3) {
//			System.out.println("Size 2    "+listaCartasJogadasAgente2.size());
            listaCartasJogadasAgente2.add(new CartasModelo(true));
        }
    }

    public void SalvaLog(String CasosDesc) {
        verificarEstadoJogo();
        String dados = pontosRodada();
        dados = dados + "\n\n\n\n\t\t\tLog Da Rodada" + estadoJogo.toString();
        String CartasRecebidasAgente1 = "\n Cartas Recebidas Agente 1 = ";
        for (CartasModelo c : listaCartasRecebidasAgente1)
            CartasRecebidasAgente1 = CartasRecebidasAgente1 + "  " + c.getCarta() + "\t";
        String CartasRecebidasAgente2 = "\n Cartas Recebidas Agente 2 = ";
        for (CartasModelo c : listaCartasRecebidasAgente2)
            CartasRecebidasAgente2 = CartasRecebidasAgente2 + "  " + c.getCarta() + "\t";
        String cartasJogadaAgente1 = "\n Cartas Jogada Agente 1 = ";
        for (CartasModelo c : listaCartasJogadasAgente1)
            cartasJogadaAgente1 = cartasJogadaAgente1 + "  " + (listaCartasJogadasAgente1.indexOf(c) + 1) + ":"
                    + c.toString2() + "\t";
        String cartasJogadaAgente2 = "\n Cartas Jogada Agente 2 = ";
        for (CartasModelo c : listaCartasJogadasAgente2)
            cartasJogadaAgente2 = cartasJogadaAgente2 + "  " + (listaCartasJogadasAgente2.indexOf(c) + 1) + ":"
                    + c.toString2() + "\t";
        dados = dados + CartasRecebidasAgente1 + CartasRecebidasAgente2 + cartasJogadaAgente1 + cartasJogadaAgente2;
        dados = dados + CasosDesc;
//	    System.out.println(dados);
        DadosJogoCSV(dados);
    }

    public void DadosJogoCSV(String Dados) {
//		try {
//			File arquivo = new File("Logs_Rodada.txt");
////			File arquivo = new File(Path);
//			FileWriter fw = new FileWriter(arquivo, true);
//			BufferedWriter bw = new BufferedWriter(fw);
////      bw.write(Dados);
//			bw.append(Dados);
//			bw.flush();
//			bw.close();
//			// System.out.println("concluido");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
    }

    /* a partir daqui  novos*/

    public int getScoreAtualPlayer1() {

        int score = controlaPartidaAuto.getPontosAgente1();

        QuemGanhouPontosModelo quemGanhou = new QuemGanhouPontosModelo();
        if (!listaPontosAceitos.isEmpty() && listPontosNaoAceitos.isEmpty()) {
            if (listaPontosAceitos.size() >= 2
                    && listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("RealEnvido")
                    && listaPontosAceitos.get(listaPontosAceitos.size() - 2).getJogadaAceita()
                    .equalsIgnoreCase("Envido")) {
                switch (QuemGanhouOsPontos()) {
                    case 1:
                        score += 5;
                        break;
                }

            }
            // verifica se for envido e pontua em 2 pontos
            else if (listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("Envido")) {
//						System.out.println("\t\tPontuando envido  +2");

                switch (QuemGanhouOsPontos()) {
                    case 1:
                        score += 2;
                        break;
                }

            }
            // verifica se foi chamado realEnvido Sem Envido antes e pontua em 3 pontos
            else if (listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("RealEnvido")) {

                switch (QuemGanhouOsPontos()) {
                    case 1:
                        score += 3;
                        break;
                }

            } else if (listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("FaltaEnvido")) {
                switch (QuemGanhouOsPontos()) {
                    case 1:
                        score += (24 - controlaPartidaAuto.getPontosAgente2());
                        break;
                }
            }
        }
        // caso o ponto chamado seja flor
        if (!listaPontosChamados.isEmpty() && listaPontosChamados.get(listaPontosChamados.size() - 1)
                .getJogadaChamada().equalsIgnoreCase("Flor")) {
            if (listaPontosChamados.get(listaPontosChamados.size() - 1).getQuemChamou() == 1) {
                score += 3;
            }
            // aqui pontua o penultimo que chamou, caso os dois tenham chamado flor
            if (!listaPontosChamados.isEmpty() && listaPontosChamados.size() >= 2) {
                if (listaPontosChamados.get(listaPontosChamados.size() - 2).getQuemChamou() == 1
                        && listaPontosChamados.get(listaPontosChamados.size() - 2).getJogadaChamada()
                        .equalsIgnoreCase("Flor")) {
                    score += 3;
                }
            }
        }
        // caso nÃƒÂ£o seja aceito pontos
        if (!listPontosNaoAceitos.isEmpty()) {

            String lastDeclinedPoint = listPontosNaoAceitos.get(listPontosNaoAceitos.size() - 1).getJogadaNaoAceita();
            String lastCalledPoint = listaPontosChamados.get(listaPontosChamados.size() - 1).getJogadaChamada();
            int lastPlayerCallPoints = listaPontosChamados.get(listaPontosChamados.size() - 1).getQuemChamou();

            if (listaPontosAceitos.isEmpty()) {

                if ((lastCalledPoint.equalsIgnoreCase("Envido") && lastDeclinedPoint.equals("Envido")) ||
                        (lastCalledPoint.equalsIgnoreCase("RealEnvido") && lastDeclinedPoint.equals("RealEnvido")) ||
                        (lastCalledPoint.equalsIgnoreCase("FaltaEnvido") && lastDeclinedPoint.equals("FaltaEnvido"))) {

                    if (lastPlayerCallPoints == 1) {
                        score += 1;
                    }
                }

            } else {

                String lastAcceptPoint = listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita();

                if (lastAcceptPoint.equals("Envido")) {

                    if (lastDeclinedPoint.equals("RealEnvido")) {

                        if (lastPlayerCallPoints == 1) {
                            score += 2;
                        }

                    } else if (lastDeclinedPoint.equals("FaltaEnvido")) {

                        if (lastPlayerCallPoints == 1) {
                            score += 2;
                        }

                    }

                } else if (lastAcceptPoint.equals("RealEnvido")) {

                    if (lastDeclinedPoint.equals("FaltaEnvido")) {

                        if (lastPlayerCallPoints == 1) {
                            score += 5;
                        }

                    }

                }

            }
        }
        return score;
    }

    public int getScoreAtualPlayer2() {

        int score = controlaPartidaAuto.getPontosAgente2();

        QuemGanhouPontosModelo quemGanhou = new QuemGanhouPontosModelo();
        if (!listaPontosAceitos.isEmpty() && listPontosNaoAceitos.isEmpty()) {
            if (listaPontosAceitos.size() >= 2
                    && listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("RealEnvido")
                    && listaPontosAceitos.get(listaPontosAceitos.size() - 2).getJogadaAceita()
                    .equalsIgnoreCase("Envido")) {
                switch (QuemGanhouOsPontos()) {
                    case 2:
                        score += 5;
                        break;
                }

            }
            // verifica se for envido e pontua em 2 pontos
            else if (listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("Envido")) {
//						System.out.println("\t\tPontuando envido  +2");

                switch (QuemGanhouOsPontos()) {
                    case 2:
                        score += 2;
                        break;
                }

            }
            // verifica se foi chamado realEnvido Sem Envido antes e pontua em 3 pontos
            else if (listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("RealEnvido")) {

                switch (QuemGanhouOsPontos()) {
                    case 2:
                        score += 3;
                        break;
                }

            } else if (listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("FaltaEnvido")) {
                switch (QuemGanhouOsPontos()) {
                    case 2:
                        score += (24 - controlaPartidaAuto.getPontosAgente1());
                        break;
                }
            }
        }
        // caso o ponto chamado seja flor
        if (!listaPontosChamados.isEmpty() && listaPontosChamados.get(listaPontosChamados.size() - 1)
                .getJogadaChamada().equalsIgnoreCase("Flor")) {
            if (listaPontosChamados.get(listaPontosChamados.size() - 1).getQuemChamou() == 2) {
                score += 3;
            }
            // aqui pontua o penultimo que chamou, caso os dois tenham chamado flor
            if (!listaPontosChamados.isEmpty() && listaPontosChamados.size() >= 2) {
                if (listaPontosChamados.get(listaPontosChamados.size() - 2).getQuemChamou() == 2
                        && listaPontosChamados.get(listaPontosChamados.size() - 2).getJogadaChamada()
                        .equalsIgnoreCase("Flor")) {
                    score += 3;
                }
            }
        }
        // caso nÃƒÂ£o seja aceito pontos
        if (!listPontosNaoAceitos.isEmpty()) {

            String lastDeclinedPoint = listPontosNaoAceitos.get(listPontosNaoAceitos.size() - 1).getJogadaNaoAceita();
            String lastCalledPoint = listaPontosChamados.get(listaPontosChamados.size() - 1).getJogadaChamada();
            int lastPlayerCallPoints = listaPontosChamados.get(listaPontosChamados.size() - 1).getQuemChamou();

            if (listaPontosAceitos.isEmpty()) {

                if ((lastCalledPoint.equalsIgnoreCase("Envido") && lastDeclinedPoint.equals("Envido")) ||
                        (lastCalledPoint.equalsIgnoreCase("RealEnvido") && lastDeclinedPoint.equals("RealEnvido")) ||
                        (lastCalledPoint.equalsIgnoreCase("FaltaEnvido") && lastDeclinedPoint.equals("FaltaEnvido"))) {

                    if (lastPlayerCallPoints == 2) {
                        score += 1;
                    }
                }

            } else {

                String lastAcceptPoint = listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita();

                if (lastAcceptPoint.equals("Envido")) {

                    if (lastDeclinedPoint.equals("RealEnvido")) {

                        if (lastPlayerCallPoints == 2) {
                            score += 2;
                        }

                    } else if (lastDeclinedPoint.equals("FaltaEnvido")) {

                        if (lastPlayerCallPoints == 2) {
                            score += 2;
                        }

                    }

                } else if (lastAcceptPoint.equals("RealEnvido")) {

                    if (lastDeclinedPoint.equals("FaltaEnvido")) {

                        if (lastPlayerCallPoints == 2) {
                            score += 5;
                        }
                    }

                }

            }
        }
        return score;
    }

    public QuemGanhouPontosModelo pontuaQuemGanhouPontos() {

        QuemGanhouPontosModelo quemGanhou = new QuemGanhouPontosModelo();
        //if (!pontosJaContabilizados) {
        if (!listaPontosAceitos.isEmpty() && listPontosNaoAceitos.isEmpty()) {
//				System.out.println("Entrou para pontuar, ponto chamado: "
//									+ listaPontosChamados.get(listaPontosChamados.size() - 1).getJogadaChamada() + " quem chamou "
//									+ listaPontosChamados.get(listaPontosChamados.size() - 1).getQuemChamou());
//				 verifica se foi chamado envido e aumentado para realEnvido aÃƒÂ­ pontua em 5
            if (listaPontosAceitos.size() >= 2
                    && listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("RealEnvido")
                    && listaPontosAceitos.get(listaPontosAceitos.size() - 2).getJogadaAceita()
                    .equalsIgnoreCase("Envido")) {
//					System.out.println("\t\tPontuando RealEnvido  e Envido  +5");
                switch (QuemGanhouOsPontos()) {
                    case 1:
                        controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 5);
                        tentosRodada.setEnvido(1, 5);
                        quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(1), calcularPontosEnvidoAgente(2), 1);
                        break;
                    case 2:
                        controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 5);
                        tentosRodada.setEnvido(2, 5);
                        quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(2), calcularPontosEnvidoAgente(1), 2);
                        break;
                }
				/*System.out.println("ENVIDO->REAL_ENVIDO:------");
				System.out.println("WINNER: " + QuemGanhouOsPontos());
				System.out.println("--Pontos Agente 1: " + controlaPartidaAuto.getPontosAgente1());
				System.out.println("--Pontos Agente 2: " + controlaPartidaAuto.getPontosAgente2());*/
					/*if (QuemGanhouOsPontos() == 1) {
//						System.out.println("\t\t1 Ganhou ");
						controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 5);
						tentosRodada.setEnvido(1, 5);
						quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(1), calcularPontosEnvidoAgente(2), 1);
					}
					if (QuemGanhouOsPontos() == 2) {
//						System.out.println("\t\t2 Ganhou ");
						controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 5);
						tentosRodada.setEnvido(2, 5);
						quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(2), calcularPontosEnvidoAgente(1), 2);
					}*/
            }
            // verifica se for envido e pontua em 2 pontos
            else if (listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("Envido")) {
//						System.out.println("\t\tPontuando envido  +2");

                switch (QuemGanhouOsPontos()) {
                    case 1:
                        controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 2);
                        tentosRodada.setEnvido(1, 2);
                        quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(1), calcularPontosEnvidoAgente(2), 1);
                        break;
                    case 2:
                        tentosRodada.setEnvido(2, 2);
                        controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 2);
                        quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(2), calcularPontosEnvidoAgente(1), 2);
                        break;
                }
				/*System.out.println("ENVIDO->:------");
				System.out.println("WINNER: " + QuemGanhouOsPontos());
				System.out.println("--Pontos Agente 1: " + controlaPartidaAuto.getPontosAgente1());
				System.out.println("--Pontos Agente 2: " + controlaPartidaAuto.getPontosAgente2());*/
					/*if (QuemGanhouOsPontos() == 1) {
//							System.out.println("\t\t1 Ganhou ");
						controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 2);
						tentosRodada.setEnvido(1, 2);
						quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(1), calcularPontosEnvidoAgente(2), 1);
					}
					if (QuemGanhouOsPontos() == 2) {
//							System.out.println("\t\t2 Ganhou ");
						tentosRodada.setEnvido(2, 2);
						controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 2);
						quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(2), calcularPontosEnvidoAgente(1), 2);
					}*/
            }
            // verifica se foi chamado realEnvido Sem Envido antes e pontua em 3 pontos
            else if (listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("RealEnvido")) {
//					System.out.println("\t\tPontuando RealEnvido + 3 ");

                switch (QuemGanhouOsPontos()) {
                    case 1:
                        controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 3);
                        tentosRodada.setEnvido(1, 3);
                        quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(1), calcularPontosEnvidoAgente(2), 1);
                        break;
                    case 2:
                        controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 3);
                        tentosRodada.setEnvido(2, 3);
                        quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(2), calcularPontosEnvidoAgente(1), 2);
                        break;
                }
				/*System.out.println("REAL_ENVIDO->:------");
				System.out.println("WINNER: " + QuemGanhouOsPontos());
				System.out.println("--Pontos Agente 1: " + controlaPartidaAuto.getPontosAgente1());
				System.out.println("--Pontos Agente 2: " + controlaPartidaAuto.getPontosAgente2());*/
					/*if (QuemGanhouOsPontos() == 1) {
//						System.out.println("\t\t1 Ganhou");
						controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 3);
						tentosRodada.setEnvido(1, 3);
						quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(1), calcularPontosEnvidoAgente(2), 1);
					}
					if (QuemGanhouOsPontos() == 2) {
//						System.out.println("\t\t2 Ganhou ");
						controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 3);
						tentosRodada.setEnvido(2, 3);
						quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(2), calcularPontosEnvidoAgente(1), 2);
					}*/
            } else if (listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita()
                    .equalsIgnoreCase("FaltaEnvido")) {
                int pontosAgent1 = controlaPartidaAuto.getPontosAgente1();
                int pontosAgent2 = controlaPartidaAuto.getPontosAgente2();
                switch (QuemGanhouOsPontos()) {
                    case 1:
                        controlaPartidaAuto.setPontosAgente1(pontosAgent1 + (24 - pontosAgent2));
                        tentosRodada.setEnvido(1, controlaPartidaAuto.getPontosAgente1()
                                - controlaPartidaAuto.getPontosAnterioresAgente1());
                        quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(1), calcularPontosEnvidoAgente(2), 1);
                        break;
                    case 2:
                        controlaPartidaAuto.setPontosAgente2(pontosAgent2 + (24 - pontosAgent1));
                        tentosRodada.setEnvido(2, controlaPartidaAuto.getPontosAgente2()
                                - controlaPartidaAuto.getPontosAnterioresAgente2());
                        quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(2), calcularPontosEnvidoAgente(1), 2);
                        break;
                }

				/*System.out.println("FALTA_ENVIDO->:------");
				System.out.println("WINNER: " + QuemGanhouOsPontos());
				System.out.println("--Pontos Agente 1: " + controlaPartidaAuto.getPontosAgente1());
				System.out.println("--Pontos Agente 2: " + controlaPartidaAuto.getPontosAgente2());*/

					/*if (QuemGanhouOsPontos() == 1) {
						controlaPartidaAuto.setPontosAgente1(24);
						tentosRodada.setEnvido(1, controlaPartidaAuto.getPontosAgente1()
								- controlaPartidaAuto.getPontosAnterioresAgente1());
						quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(1), calcularPontosEnvidoAgente(2), 1);
					}
					if (QuemGanhouOsPontos() == 2) {
						controlaPartidaAuto.setPontosAgente2(24);
						tentosRodada.setEnvido(2, controlaPartidaAuto.getPontosAgente2()
								- controlaPartidaAuto.getPontosAnterioresAgente2());
						quemGanhou = setaQuemGanhou(calcularPontosEnvidoAgente(2), calcularPontosEnvidoAgente(1), 2);
					}*/
            }
        }
        // caso o ponto chamado seja flor
        if (!listaPontosChamados.isEmpty() && listaPontosChamados.get(listaPontosChamados.size() - 1)
                .getJogadaChamada().equalsIgnoreCase("Flor")) {
            if (listaPontosChamados.get(listaPontosChamados.size() - 1).getQuemChamou() == 1) {
                controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 3);
            } else if (listaPontosChamados.get(listaPontosChamados.size() - 1).getQuemChamou() == 2) {
                controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 3);
            }
            // aqui pontua o penultimo que chamou, caso os dois tenham chamado flor
            if (!listaPontosChamados.isEmpty() && listaPontosChamados.size() >= 2) {

                if (listaPontosChamados.get(listaPontosChamados.size() - 2).getQuemChamou() == 1
                        && listaPontosChamados.get(listaPontosChamados.size() - 2).getJogadaChamada()
                        .equalsIgnoreCase("Flor")) {

                    controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + 3);

                } else 	if (listaPontosChamados.get(listaPontosChamados.size() - 2).getQuemChamou() == 2
                        && listaPontosChamados.get(listaPontosChamados.size() - 2).getJogadaChamada()
                        .equalsIgnoreCase("Flor")) {
                    controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + 3);
                }
            }
        }
        // caso nÃƒÂ£o seja aceito pontos
        if (!listPontosNaoAceitos.isEmpty()) {
            pontuaPontosNaoAceitos();
        }
        //}
        return quemGanhou;
    }

    private void pontuaPontosNaoAceitos() {

        String lastDeclinedPoint = listPontosNaoAceitos.get(listPontosNaoAceitos.size() - 1).getJogadaNaoAceita();
        String lastCalledPoint = listaPontosChamados.get(listaPontosChamados.size() - 1).getJogadaChamada();
        int lastPlayerCallPoints = listaPontosChamados.get(listaPontosChamados.size() - 1).getQuemChamou();

        if (listaPontosAceitos.isEmpty()) {

            if ((lastCalledPoint.equalsIgnoreCase("Envido") && lastDeclinedPoint.equals("Envido")) ||
                    (lastCalledPoint.equalsIgnoreCase("RealEnvido") && lastDeclinedPoint.equals("RealEnvido")) ||
                    (lastCalledPoint.equalsIgnoreCase("FaltaEnvido") && lastDeclinedPoint.equals("FaltaEnvido"))) {

                setResultEnvidoPhase(lastPlayerCallPoints, 1);
            }

        } else {

            String lastAcceptPoint = listaPontosAceitos.get(listaPontosAceitos.size() - 1).getJogadaAceita();
            //System.out.println("--- lastAcceptPoint: " + lastAcceptPoint);

            if (lastAcceptPoint.equals("Envido")) {

                if (lastDeclinedPoint.equals("RealEnvido")) {

                    setResultEnvidoPhase(lastPlayerCallPoints, 2);

                } else if (lastDeclinedPoint.equals("FaltaEnvido")) {

                    setResultEnvidoPhase(lastPlayerCallPoints, 2);

                }

            } else if (lastAcceptPoint.equals("RealEnvido")) {

                if (lastDeclinedPoint.equals("FaltaEnvido")) {

                    setResultEnvidoPhase(lastPlayerCallPoints, 5);

                }

            }

        }
    }

    private void setResultEnvidoPhase(int winner, int points) {
        switch (winner) {
            case 1:
                controlaPartidaAuto.setPontosAgente1(controlaPartidaAuto.getPontosAgente1() + points);
                break;
            case 2:
                controlaPartidaAuto.setPontosAgente2(controlaPartidaAuto.getPontosAgente2() + points);
                break;
        }
        tentosRodada.setEnvido(winner, points);
    }

    public TentosGanhos getTentosRodada() {
        return tentosRodada;
    }

    public void setTentosRodada(TentosGanhos tentosRodada) {
        this.tentosRodada = tentosRodada;
    }

    public String getResponseActiveLearning() {
        return responseActiveLearning;
    }

    public void setResponseActiveLearning(String responseActiveLearning) {
        this.responseActiveLearning = responseActiveLearning;
    }

    ////Active Learning

    //moveType: 0 Flor/ 1 Envido/ 2 Truco/ 3 Play Card
    public GameState setInfoActiveScreen(String move, int moveType, double prob) {

        verificarEstadoJogo();

        gameState.setMoveType(moveType);
        gameState.setAgentPoints(controlaPartidaAuto.getPontosAgente1());
        gameState.setOpponentPoints(controlaPartidaAuto.getPontosAgente2());
        gameState.setEnvidoPoints(calcularPontosEnvidoAgente(1));

        if ( (listaCartasJogadasAgente2.size() > 1 &&
                listaCartasJogadasAgente2.get(0).getNaipe().equals(listaCartasJogadasAgente2.get(1).getNaipe())) ) {
            gameState.setOpponentEnvidoPoints(calcularPontosEnvidoAgente(2));
        } else {
            gameState.setOpponentEnvidoPoints(0);
        }

        if (!verificarSeAgenteTemFlor(1) && !verificarSeAgenteTemFlor( 2)) {
            if (listPontosNaoAceitos.size() == 0) {
                if (listaPontosAceitos.size() > 0 && (estadoJogo.getListaPontosQuePodemSerChamados().isEmpty() || cartasJogadasMesa.size() >= 2)) {
                    gameState.setOpponentEnvidoPoints(calcularPontosEnvidoAgente(2));
                } else if (listaPontosAceitos.size() > 0 && !getPodeChamarPontosAinda() && estadoJogo.getListaPontosQuePodemSerChamados().isEmpty()) {
                    gameState.setOpponentEnvidoPoints(calcularPontosEnvidoAgente(2));
                }
            }
        }


        gameState.setPlayedCards(setPlayedCards(1));
        gameState.setOpponentPlayedCards(setPlayedCards(2));
        gameState.setHandCards(setHandCards());
        gameState.setMove(move);
        gameState.setHand(controlaPartidaAuto.getQuemEhMao() == 1);
        gameState.setHistoryEnvido(mountHistoryEnvido());
        gameState.setHistoryTruco(mountHistoryTruco());
        gameState.setHistoryCard(mountHistoryCard());
        gameState.setTableCards(estadoJogo.getCardHistory());
        gameState.setHandImageCards(listaCartasAgente1TemNaMao);
        gameState.setCountBluff1Success(controlaPartidaAuto.getCountBluff1Success());
        gameState.setCountBluff2Success(controlaPartidaAuto.getCountBluff2Success());
        gameState.setCountBluff3Success(controlaPartidaAuto.getCountBluff3Success());
        gameState.setCountBluff4Success(controlaPartidaAuto.getCountBluff4Success());
        gameState.setCountBluff5Success(controlaPartidaAuto.getCountBluff5Success());
        gameState.setCountBluff6Success(controlaPartidaAuto.getCountBluff6Success());

        gameState.setCountBluff1Failure(controlaPartidaAuto.getCountBluff1Failure());
        gameState.setCountBluff2Failure(controlaPartidaAuto.getCountBluff2Failure());
        gameState.setCountBluff3Failure(controlaPartidaAuto.getCountBluff3Failure());
        gameState.setCountBluff4Failure(controlaPartidaAuto.getCountBluff4Failure());
        gameState.setCountBluff5Failure(controlaPartidaAuto.getCountBluff5Failure());
        gameState.setCountBluff6Failure(controlaPartidaAuto.getCountBluff6Failure());

        gameState.setCountBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent());
        gameState.setCountBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent());
        gameState.setCountBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent());
        gameState.setCountBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent());
        gameState.setCountBluff5Opponent(controlaPartidaAuto.getCountBluff5Opponent());
        gameState.setCountBluff6Opponent(controlaPartidaAuto.getCountBluff6Opponent());

        gameState.setCountBluff1ShowDown(controlaPartidaAuto.getCountBluff1ShowDown());
        gameState.setCountBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown());
        gameState.setCountBluff3ShowDown(controlaPartidaAuto.getCountBluff3ShowDown());
        gameState.setCountBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown());
        gameState.setCountBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown());
        gameState.setCountBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown());


        gameState.setMoves(new ArrayList<>());
        estadoJogo.umRemoveJogadasJaChamadasQueFuncione();
        switch (moveType){
            case 11:
                gameState.getMoves().add("Accept");
                gameState.getMoves().add("Decline");
                break;
            case 1:
                gameState.setMoves((ArrayList<String>) estadoJogo.getListaPontosQuePodemSerChamados());
                gameState.getMoves().add("No Call");
                break;
            case 2:
                estadoJogo.umRemoveJogadasJaChamadasQueFuncione();
                gameState.setMoves((ArrayList<String>) estadoJogo.getListaJogadasQuePodemSerChamadas());
                gameState.getMoves().add("No Call");
                break;
            case 22:
                gameState.getMoves().add("Accept");
                gameState.getMoves().add("Decline");
                break;
            case 3:
                if (listaCartasAgente1TemNaMao.size() > 0) {
                    for (CartasModelo card : listaCartasAgente1TemNaMao) {
                        gameState.getMoves().add(card.getCarta());
                        //gameState.getMoves().add(card.getCarta() + " Face Down");
                    }
                }
                gameState.getMoves().add("Fold");
                break;
            case 33:
				/*gameState.getMoves().add("Fold");
				gameState.getMoves().add("No Fold");*/
                break;
        }

        gameState.setProb(prob);

        return gameState;
    }

    public String setPlayedCards(int agentId) {
        String cartasJogadas = "";

        if (agentId == 1) {
            if (listaCartasJogadasAgente1.size() > 0) {
                for (CartasModelo card : listaCartasJogadasAgente1) {
                    cartasJogadas += card.getCarta() + "; ";
                }
            }

        } else {
            if (listaCartasJogadasAgente2.size() > 0) {
                for (CartasModelo card : listaCartasJogadasAgente2) {
                    if (card.isVirada()) {
                        cartasJogadas += "Face Down Card; ";
                    } else {
                        cartasJogadas += card.getCarta() + "; ";
                    }
                }
            }
        }

        return cartasJogadas;
    }

    public String setHandCards() {
        String handCards = "";

        if (listaCartasAgente1TemNaMao.size() > 0) {
            for (CartasModelo card : listaCartasAgente1TemNaMao) {
                handCards += card.getCarta() + "; ";
            }
        }

        return handCards;
    }

    public double getProbHandByGameState(int isHand) {

        double agentHandStrength = deck.getStrenghtHand(listaCartasRecebidasAgente1.get(0).getCarta(),
                listaCartasRecebidasAgente1.get(1).getCarta(), 	listaCartasRecebidasAgente1.get(2).getCarta());

        double prob = deck.getProbBestHand(isHand, agentHandStrength, deck.getAllOpponentHands());

        if (cartasJogadasMesa.size() == 1 && listaCartasJogadasAgente2.size() > 0 || (cartasJogadasMesa.size() == 2)) {

            prob = deck.getProbBestHand(isHand, agentHandStrength,
                    deck.getFilteredOpponentHands(listaCartasJogadasAgente2.get(0).getCarta()));

        } else if (cartasJogadasMesa.size() == 3 && listaCartasJogadasAgente2.size() > 1 || (cartasJogadasMesa.size() == 4)){

            prob = deck.getProbBestHand(isHand, agentHandStrength,
                    deck.getFilteredOpponentHands(listaCartasJogadasAgente2.get(0).getCarta(),
                            listaCartasJogadasAgente2.get(1).getCarta()));

        } else if (cartasJogadasMesa.size() == 5 && listaCartasJogadasAgente2.size() > 2) {

            if (listaCartasAgente1TemNaMao.get(0).getId() > listaCartasJogadasAgente2.get(2).getId()) {
                prob = 1.0;
            } else if (listaCartasAgente1TemNaMao.get(0).getId() == listaCartasJogadasAgente2.get(2).getId()) {
                if (GanhadorPrimeira == 1) {
                    prob = 1.0;
                } else {
                    prob = 0.0;
                }
            } else {
                prob = 0.0;
            }
        }

        return prob;
    }

    /*public double getProbabilidadeMaoByEstadoJogo(int isHand) {

        double prob = deck.getProbabilidadeMelhorMao(isHand, listaCartasRecebidasAgente1.get(0).getCarta(),
                listaCartasRecebidasAgente1.get(1).getCarta(), 	listaCartasRecebidasAgente1.get(2).getCarta());

        if (cartasJogadasMesa.size() == 1 && listaCartasJogadasAgente2.size() > 0 || (cartasJogadasMesa.size() == 2)) {

            prob = deck.getProbabilidadeMelhorMao(isHand, listaCartasRecebidasAgente1.get(0).getCarta(),
                    listaCartasRecebidasAgente1.get(1).getCarta(), listaCartasRecebidasAgente1.get(2).getCarta(),
                    listaCartasJogadasAgente2.get(0).getCarta());

        } else if (cartasJogadasMesa.size() == 3 && listaCartasJogadasAgente2.size() > 1 || (cartasJogadasMesa.size() == 4)){

            prob = deck.getProbabilidadeMelhorMao(isHand, listaCartasRecebidasAgente1.get(0).getCarta(),
                    listaCartasRecebidasAgente1.get(1).getCarta(), listaCartasRecebidasAgente1.get(2).getCarta(),
                    listaCartasJogadasAgente2.get(0).getCarta(),
                    listaCartasJogadasAgente2.get(1).getCarta());

        } else if (cartasJogadasMesa.size() == 5 && listaCartasJogadasAgente2.size() > 2) {

            if (listaCartasAgente1TemNaMao.get(0).getId() > listaCartasJogadasAgente2.get(2).getId()) {
                prob = 1.0;
            } else if (listaCartasAgente1TemNaMao.get(0).getId() == listaCartasJogadasAgente2.get(2).getId()) {
                if (controlaPartidaAuto.getQuemEhMao() == 1) {
                    prob = 1.0;
                } else {
                    prob = 0.0;
                }
            } else {
                prob = 0.0;
            }
        }

        return prob;
    }*/

    public double getProbWin(int isHand, String jogada) {

        double prob = 0.0;

        int pontosEnvido = calcularPontosEnvidoAgente(1);

        if (jogada.equals("ENVIDO") || jogada.equals("REAL_ENVIDO") || jogada.equals("FALTA_ENVIDO")) {

            if (cartasJogadasMesa.size() == 1 && listaCartasJogadasAgente2.size() == 1) {
                prob = deck.getProbBestPoint(isHand, pontosEnvido, deck.getFilteredOpponentHands(listaCartasJogadasAgente2.get(0).getCarta()));
                System.out.println("[PROBABILIDADE_ENVIDO_PE] --> " + prob);
            } else {
                prob = deck.getProbBestPoint(isHand, pontosEnvido, deck.getAllOpponentHands());
                System.out.println("[PROBABILIDADE_ENVIDO_MAO] --> " + prob);
            }

        } else if (jogada.equals("TRUCO") || jogada.equals("RETRUCO") || jogada.equals("VALE4") || jogada.equals("PLAY_CARD")) {

            prob = getProbHandByGameState(isHand);
        }

        return prob;
    }


   /* public double getProbabilidadeGanhar(int isHand, String jogada) {
        double prob = 0.0;

        int pontosEnvido = calcularPontosEnvidoAgente(1);

        if (jogada.equals("ENVIDO") || jogada.equals("REAL_ENVIDO") || jogada.equals("FALTA_ENVIDO")) {

            if (cartasJogadasMesa.size() == 1 && listaCartasJogadasAgente2.size() == 1) {
                prob = 1-deck.getProbabilidadeMelhorEnvido(isHand, pontosEnvido, listaCartasJogadasAgente2.get(0).getCarta());
            } else {
                prob = 1-deck.getProbabilidadeMelhorEnvido(isHand, pontosEnvido);
            }

        } else if (jogada.equals("TRUCO") || jogada.equals("RETRUCO") || jogada.equals("VALE4") || jogada.equals("PLAY_CARD")) {

            prob = 1 - getProbabilidadeMaoByEstadoJogo(isHand);
        }

        return prob;
    }*/

    public String mountHistoryEnvido() {
        String history = "";

        if (estadoJogo.getEnvidoHistory().size() > 0) {
            for (Action action : estadoJogo.getEnvidoHistory()) {
                String player = action.getPlayer().equals("1") ? "Agent": "Opponent";
                history += player + "-" + action.getAction() + "; ";
            }
        }
        //System.out.println(history);

        return history;
    }

    public String mountHistoryTruco() {
        String history = "";

        if (estadoJogo.getTrucoHistory().size() > 0) {
            for (Action action : estadoJogo.getTrucoHistory()) {
                String player = action.getPlayer().equals("1") ? "Agent": "Opponent";
                history += player + "-" + action.getAction() + "-(" + action.getWhen() + "); ";
            }
        }

        //System.out.println(history);

        return history;
    }

    public String mountHistoryCard() {
        String history = "";

        if (estadoJogo.getCardHistory().size() > 0) {
            for (Action action : estadoJogo.getCardHistory()) {
                String player = action.getPlayer().equals("1") ? "Agent": "Opponent";
                history += player + "-" + action.getAction() + "-(" + action.getWhen() + "); ";
            }
        }

        //System.out.println(history);

        return history;
    }

    public String getJogadaRealizada(String typeJogada, String jogadaChamada) {
        String jogada = "";

        switch (typeJogada) {
            case "ENVIDO":
                if (jogadaChamada.equals("Flor")) {
                    jogada = "FLOR";
                } else if (jogadaChamada.equals("ContraFlor")) {
                    jogada = "CONTRA_FLOR";
                } else if (jogadaChamada.equals("ContraFlorResto")) {
                    jogada = "CONTRA_FLOR_RESTO";
                } else if (jogadaChamada.equals("Envido")) {
                    jogada = "ENVIDO";
                } else if (jogadaChamada.equals("RealEnvido")) {
                    jogada = "REAL_ENVIDO";
                } else if (jogadaChamada.equals("FaltaEnvido")) {
                    jogada = "FALTA_ENVIDO";
                }
                break;
            case "TRUCO":
                if (jogadaChamada.equals("Truco")) {
                    jogada = "TRUCO";
                } else if (jogadaChamada.equals("ReTruco")) {
                    jogada = "RETRUCO";
                } else if (jogadaChamada.equals("ValeQuatro")) {
                    jogada = "VALE4";
                }
                break;
        }

        return jogada;
    }

    public boolean isUtilEnvido() {
        return utilEnvido;
    }

    public void setUtilEnvido(boolean utilEnvido) {
        this.utilEnvido = utilEnvido;
    }

    public boolean isUtilTruco() {
        return utilTruco;
    }

    public void setUtilTruco(boolean utilTruco) {
        this.utilTruco = utilTruco;
    }

    public boolean isUtilCarta() {
        return utilCarta;
    }

    public void setUtilCarta(boolean utilCarta) {
        this.utilCarta = utilCarta;
    }

    public boolean isHasDeception() {
        return hasDeception;
    }

    public void setHasDeception(boolean hasDeception) {
        this.hasDeception = hasDeception;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isCompulsoryRetention() {
        return compulsoryRetention;
    }

    public void setCompulsoryRetention(boolean compulsoryRetention) {
        this.compulsoryRetention = compulsoryRetention;
    }

    public List<JogadasChamadasModelo> getListaJogadasChamadas() {
        return listaJogadasChamadas;
    }

    public void setListaJogadasChamadas(List<JogadasChamadasModelo> listaJogadasChamadas) {
        this.listaJogadasChamadas = listaJogadasChamadas;
    }

    public List<JogadasAceitasModelo> getListaJogadasAceitas() {
        return listaJogadasAceitas;
    }

    public void setListaJogadasAceitas(List<JogadasAceitasModelo> listaJogadasAceitas) {
        this.listaJogadasAceitas = listaJogadasAceitas;
    }

    private int consultaIdPelaCarta(String carta, int Quem) {
        int id = 0;
        if (Quem == 1) {
            for (CartasModelo cartas : listaCartasRecebidasAgente1) {
                if (cartas.getCarta() == carta)
                    id = cartas.getId();
            }
            return id;
        } else {
            for (CartasModelo cartas : listaCartasRecebidasAgente2) {
                if (cartas.getCarta() == carta)
                    id = cartas.getId();
            }
            return id;
        }
    }

    private int consultaIdPelaCartaEx(String carta, int Quem) {
        int id = 0;
        if (Quem == 1) {
            for (CartasModelo cartas : listaCartasAgente1TemNaMao) {
                if (cartas.getCarta() == carta)
                    id = cartas.getId();
            }
            return id;
        } else {
            for (CartasModelo cartas : listaCartasAgente2TemNaMao) {
                if (cartas.getCarta() == carta)
                    id = cartas.getId();
            }
            return id;
        }
    }

    public int getStateDecision() {

        int stateDecision = cartasJogadasMesa.size() + 1;

        return stateDecision;
    }

    public String getTypeCarta(String carta) {

        if (carta.equals(listaCartasRecebidasAgente1.get(0).getCarta())) {
            return "HIGH_CARD";
        } else if (carta.equals(listaCartasRecebidasAgente1.get(1).getCarta())) {
            return "MEDIUM_CARD";
        } else {
            return "LOW_CARD";
        }
    }

    public Decision createDefaultDecision(int typeDecision, int typeAction) {

        Decision decision = new Decision();
        decision.setMatch(controlaPartidaAuto.getMatch());
        decision.setPlayer(controlaPartidaAuto.getPlayer1());
        decision.setHandNumber(controlaPartidaAuto.getRodadaNumber());
        decision.setAgenteScore(getScoreAtualPlayer1());
        decision.setOponenteScore(getScoreAtualPlayer2());

        if (typeDecision == 1) {
            decision.setEnvidoHandStrength(calcularPontosEnvidoAgente(1));
            decision.setLastMove(listaPontosChamados.size() == 0 ? null : listaPontosChamados.get(listaPontosChamados.size() - 1).getJogadaChamada());
        } else if (typeDecision == 2) {
            decision.setEnvidoHandStrength(deck.getStrenghtHand(listaCartasRecebidasAgente1.get(0).getCarta(),
                    listaCartasRecebidasAgente1.get(1).getCarta(), listaCartasRecebidasAgente1.get(2).getCarta()));
            decision.setLastMove(listaJogadasChamadas.size() == 0 ? null : listaJogadasChamadas.get(listaJogadasChamadas.size() - 1).getJogadaChamada());
        } else {
            decision.setEnvidoHandStrength(deck.getStrenghtHand(listaCartasRecebidasAgente1.get(0).getCarta(),
                    listaCartasRecebidasAgente1.get(1).getCarta(), listaCartasRecebidasAgente1.get(2).getCarta()));
            decision.setLastMove(listaCartasJogadasAgente1.size() == 0 ? null : getTypeCarta(listaCartasJogadasAgente1.get(listaCartasJogadasAgente1.size() - 1).getCarta()));
        }

        decision.setStateDecision(getStateDecision());

        decision.setBluff1Success(controlaPartidaAuto.getCountBluff1Success());
        decision.setBluff2Success(controlaPartidaAuto.getCountBluff2Success());
        decision.setBluff3Success(controlaPartidaAuto.getCountBluff3Success());
        decision.setBluff4Success(controlaPartidaAuto.getCountBluff4Success());
        decision.setBluff5Success(controlaPartidaAuto.getCountBluff5Success());
        decision.setBluff6Success(controlaPartidaAuto.getCountBluff6Success());

        decision.setBluff1Failure(controlaPartidaAuto.getCountBluff1Failure());
        decision.setBluff2Failure(controlaPartidaAuto.getCountBluff2Failure());
        decision.setBluff3Failure(controlaPartidaAuto.getCountBluff3Failure());
        decision.setBluff4Failure(controlaPartidaAuto.getCountBluff4Failure());
        decision.setBluff5Failure(controlaPartidaAuto.getCountBluff5Failure());
        decision.setBluff6Failure(controlaPartidaAuto.getCountBluff6Failure());

        decision.setBluff1Opponent(controlaPartidaAuto.getCountBluff1Opponent());
        decision.setBluff2Opponent(controlaPartidaAuto.getCountBluff2Opponent());
        decision.setBluff3Opponent(controlaPartidaAuto.getCountBluff3Opponent());
        decision.setBluff4Opponent(controlaPartidaAuto.getCountBluff4Opponent());
        decision.setBluff5Opponent(controlaPartidaAuto.getCountBluff5Opponent());
        decision.setBluff6Opponent(controlaPartidaAuto.getCountBluff6Opponent());

        decision.setBluff1ShowDown(controlaPartidaAuto.getCountBluff1ShowDown());
        decision.setBluff2ShowDown(controlaPartidaAuto.getCountBluff2ShowDown());
        decision.setBluff3ShowDown(controlaPartidaAuto.getCountBluff3ShowDown());
        decision.setBluff4ShowDown(controlaPartidaAuto.getCountBluff4ShowDown());
        decision.setBluff5ShowDown(controlaPartidaAuto.getCountBluff5ShowDown());
        decision.setBluff6ShowDown(controlaPartidaAuto.getCountBluff6ShowDown());

        decision.setTypeAction(typeAction);
        decision.setTypeDecision(typeDecision);

        decision.setIsActiveLearning(0);

        decision.setIsBluff(0);
        decision.setPossiblePoints(0);
        decision.setBluffDetectedOpponent(0);

        decision.setIndSuccess(0);


        return decision;
    }

}



