package gamer.ui.utils;

import java.util.Optional;

import cbr.cbrDescriptions.TrucoDescription;
import gamer.Truco.Cartas.ConsultaCarta;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class AjudaPontos {
	
	 boolean resposta;

	 public boolean criaAlerta(String contentText, TrucoDescription gameStateRobo, String titleText){

		 
		 ButtonType Sim = new ButtonType("SIM", ButtonBar.ButtonData.OK_DONE);
		 ButtonType Nao = new ButtonType("NAO", ButtonBar.ButtonData.CANCEL_CLOSE);
		 Alert alerta = new Alert(AlertType.WARNING,
		         "The format for dates is year.month.day. "
		         + "For example, today is .",
		         Sim,
		         Nao);
		 
		 String PontosEnvido = String.valueOf(gameStateRobo.getPontosEnvidoRobo());
	
		 int PrimeiraJogadaOponente;
		 if(gameStateRobo.getPrimeiraCartaHumano()!= null)
			 PrimeiraJogadaOponente = gameStateRobo.getPrimeiraCartaHumano();
		 else
			 PrimeiraJogadaOponente = 0;
	
		 
		 String PrimeiraCartaOponente = new ConsultaCarta().ConsultaCartaPeloId(PrimeiraJogadaOponente);
		 String TentosHumano = String.valueOf(gameStateRobo.getTentosAnterioresHumano());
		 String TentosRobo = String.valueOf(gameStateRobo.getTentosAnterioresRobo());

		 
		 String Data = "";
		 
		 Data += "\n\n Pontos Envido = " + PontosEnvido; 
		 Data  += "\n Primeir Carta Oponente = " + PrimeiraCartaOponente; 
		 Data  += "\n Tentos Humano = " + TentosHumano; 
		 Data  += "\n Tentos Robo = " + TentosRobo; 

		 
		 alerta.setTitle(titleText);
		 alerta.setHeaderText(contentText);
		 alerta.setContentText(Data);

		 alerta.setTitle(titleText);
		 
		 Optional<ButtonType> result = alerta.showAndWait();
		 if (result.get().getButtonData() == Sim.getButtonData()) {
		     resposta = true;
		 }
		 else {
			 resposta = false;
		 }

		 
		 return resposta;   
		}
	
}
