package gamer.ui.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cbr.cbrDescriptions.TrucoDescription;
import gamer.Truco.Cartas.ConsultaCarta;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

public class AjudaCarta {
	
	 int resposta = 0;


	 public int criaAlerta(String contentText, TrucoDescription gameStateRobo, String titleText){
		 

		 ButtonType Alta = new ButtonType("Alta", ButtonBar.ButtonData.OTHER);
		 ButtonType Media = new ButtonType("Media", ButtonBar.ButtonData.OK_DONE);
		 ButtonType Baixa = new ButtonType("Baixa", ButtonBar.ButtonData.CANCEL_CLOSE);
		 
		 
		 
		 Alert alerta = new Alert(AlertType.WARNING,
		         "The format for dates is year.month.day. "
		         + "For example, today is .",
		         Alta,Media,Baixa);
		 
		 String CartaAlta  =  new ConsultaCarta().ConsultaCartaPeloId(gameStateRobo.getCartaAltaRobo());
		 String CartaMedia  =  new ConsultaCarta().ConsultaCartaPeloId(gameStateRobo.getCartaMediaRobo());
		 String CartaBaixa  =  new ConsultaCarta().ConsultaCartaPeloId(gameStateRobo.getCartaBaixaRobo());
		 
		 int PrimeiraJogada;
		 int PrimeiraJogadaOponente;
		 int SegundaJogada; 
		 int SegundaJogadaOponente;
		 int TerceiraJogada; 
		 int TerceiraJogadaOponente;
		 
		 
		 if(gameStateRobo.getPrimeiraCartaRobo()!= null)
			 PrimeiraJogada = gameStateRobo.getPrimeiraCartaRobo();
		 else
			 PrimeiraJogada = 0;

		 if(gameStateRobo.getPrimeiraCartaHumano()!= null)
			 PrimeiraJogadaOponente = gameStateRobo.getPrimeiraCartaHumano();
		 else
			 PrimeiraJogadaOponente = 0;
	
		 if(gameStateRobo.getSegundaCartaRobo()!= null)
			 SegundaJogada = gameStateRobo.getSegundaCartaRobo();
		 else
			 SegundaJogada  = 0;
		
		 if(gameStateRobo.getSegundaCartaHumano()!= null)
			 SegundaJogadaOponente = gameStateRobo.getSegundaCartaHumano();
		 else
			 SegundaJogadaOponente= 0;
		 
		 
		 if(gameStateRobo.getTerceiraCartaRobo()!= null)
			 TerceiraJogada = gameStateRobo.getTerceiraCartaRobo();
		 else
			 TerceiraJogada  = 0;
		
		 if(gameStateRobo.getTerceiraCartaHumano()!= null)
			 TerceiraJogadaOponente = gameStateRobo.getTerceiraCartaHumano();
		 else
			 TerceiraJogadaOponente= 0;
		 
		 
		 String PrimeiraCarta  =  new ConsultaCarta().ConsultaCartaPeloId(PrimeiraJogada);
		 String PrimeiraCartaOponente  =  new ConsultaCarta().ConsultaCartaPeloId(PrimeiraJogadaOponente);
		 
		 String SegundaCarta  =  new ConsultaCarta().ConsultaCartaPeloId(SegundaJogada);
		 String SegundaCartaOponente  =  new ConsultaCarta().ConsultaCartaPeloId(SegundaJogadaOponente);
	
		 String GanhadorPrimeiraRodada  =  String.valueOf(gameStateRobo.getGanhadorPrimeiraRodada());
		 String GanhadorSegundaRodada  =  String.valueOf(gameStateRobo.getGanhadorSegundaRodada());
		 
		 
		 String Data = "";
		 
		 Data += "\n Carta Alta = " + CartaAlta ;
		 Data += "\n Carta Media = " + CartaMedia;
		 Data += "\n Carta Baixa = " + CartaBaixa;
		
		 Data += "\n\n Primeira Carta = " + PrimeiraCarta;
		 Data += "\n PrimeiraCarta Oponente = " + PrimeiraCartaOponente;
		 
		 Data += "\n Segunda Carta = " + SegundaCarta;
		 Data += "\n Segunda Carta Oponente = " + SegundaCartaOponente;
		 
		 Data += "\n\n Ganhador Primeira Rodada = " + GanhadorPrimeiraRodada;
		 Data += "\n Ganhador Segunda Rodada = " +  GanhadorSegundaRodada;
		 
		 
	 
		 alerta.setTitle(titleText);
		 alerta.setHeaderText(contentText);
		 alerta.setContentText(Data);

		 alerta.setTitle(titleText);
		 Optional<ButtonType> result = alerta.showAndWait();
//
		 if (result.get().getButtonData() == Alta.getButtonData()) {
		     resposta = gameStateRobo.getCartaAltaRobo();
		 }
		 else if(result.get().getButtonData() == Baixa.getButtonData()){
		     resposta = gameStateRobo.getCartaBaixaRobo();
		 }else {
		     resposta = gameStateRobo.getCartaMediaRobo();
		 }
		 
		 return resposta;   
		}
	
}
