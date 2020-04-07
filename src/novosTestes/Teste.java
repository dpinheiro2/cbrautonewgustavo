package novosTestes;

import hibernate.utils.HibernateUtil;
import model.Decision;
import model.Match;
import model.Player;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Universidade Federal de Santa Maria
 * Pós-Graduação em Ciência da Computação
 * Tópicos em Computação Aplicada
 * Daniel Pinheiro Vargas
 * Criado em 18/03/2020.
 */


public class Teste {

    static Session session = null;

    public static void main(String[] args) {

        Player player1 = getPlayer("probabilidadechance", "probabilidadechance", 1, "ativo");
        Player player2 = getPlayer("probabilidadechance", "probabilidadechance", 1, "imitacao");

        Match match = new Match();


        Decision decision1 = new Decision();

        decision1.setMatch(match);
        decision1.setPlayer(player1);
        decision1.setHandNumber(1);

        match.getDecisions().add(decision1);



        Decision decision2 = new Decision();

        decision2.setMatch(match);
        decision2.setPlayer(player1);
        decision2.setHandNumber(2);

        match.getDecisions().add(decision2);


        match.setPlayer1(player1);
        match.setPlayer2(player2);
        match.setPointsPlayer1(24);
        match.setPointsPlayer2(18);
        match.setWinner(player1);


        saveMatch(match);


    }

    static void saveMatch(Match match) {

        try {
            if(session == null || !session.isOpen()) session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(match);
            session.getTransaction().commit();
            System.out.println("Partida inserida com sucesso!");

            if(session.isOpen() ) session.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Player getPlayer(String extraClusterReuse, String intraClusterReuse, int indCluster, String caseBase) {

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
        return player;
    }
}
