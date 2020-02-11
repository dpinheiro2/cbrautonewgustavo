package gamer.Truco.Cartas;

import java.util.ArrayList;
import java.util.List;

import cbr.AtualizaConsultas.AuxiliaConsultas.CartasModelo;

public class ConsultaCarta {

	List<CartasModelo> cartas;

	public CartasModelo ConsultaCartas(int id, String Naipe) {
		adicionaCartas();
		CartasModelo carta = new CartasModelo();
		for (CartasModelo C : cartas) {
			if (C.getId() == id && C.getNaipe().equals(Naipe))
				carta = C;
		}
		return carta;

	}

	public String ConsultaCartaPeloId(int id) {
		adicionaCartas();
		String carta = "Nao Jogada";
		for (CartasModelo C : cartas) {
			if (C.getId() == id)
				carta = C.getCarta();
		}
		return carta;
	}

	private void adicionaCartas() {
		cartas = new ArrayList<CartasModelo>();
		cartas.add(new CartasModelo("4c", 0, "COPAS", 4, 1));
		cartas.add(new CartasModelo("4p", 0, "BASTOS", 4, 1));
		cartas.add(new CartasModelo("4o", 0, "OURO", 4, 1));
		cartas.add(new CartasModelo("4e", 0, "ESPADAS", 4, 1));
		cartas.add(new CartasModelo("5c", 1, "COPAS", 5, 2));
		cartas.add(new CartasModelo("5p", 1, "BASTOS", 5, 2));
		cartas.add(new CartasModelo("5o", 1, "OURO", 5, 2));
		cartas.add(new CartasModelo("5e", 1, "ESPADAS", 5, 2));
		cartas.add(new CartasModelo("6c", 2, "COPAS", 6, 3));
		cartas.add(new CartasModelo("6p", 2, "BASTOS", 6, 3));
		cartas.add(new CartasModelo("6o", 2, "OURO", 6, 3));
		cartas.add(new CartasModelo("6e", 2, "ESPADAS", 6, 3));
		cartas.add(new CartasModelo("7c", 3, "COPAS", 7, 4));
		cartas.add(new CartasModelo("7p", 3, "BASTOS", 7, 4));
		cartas.add(new CartasModelo("10c", 4, "COPAS", 0, 6));
		cartas.add(new CartasModelo("10p", 4, "BASTOS", 0, 6));
		cartas.add(new CartasModelo("10o", 4, "OURO", 0, 6));
		cartas.add(new CartasModelo("10e", 4, "ESPADAS", 0, 6));
		cartas.add(new CartasModelo("11c", 5, "COPAS", 0, 7));
		cartas.add(new CartasModelo("11p", 5, "BASTOS", 0, 7));
		cartas.add(new CartasModelo("11o", 5, "OURO", 0, 7));
		cartas.add(new CartasModelo("11e", 5, "ESPADAS", 0, 7));
		cartas.add(new CartasModelo("12c", 6, "COPAS", 0, 8));
		cartas.add(new CartasModelo("12p", 6, "BASTOS", 0, 8));
		cartas.add(new CartasModelo("12o", 6, "OURO", 0, 8));
		cartas.add(new CartasModelo("12e", 6, "ESPADAS", 0, 8));
		cartas.add(new CartasModelo("1c", 7, "COPAS", 1, 12));
		cartas.add(new CartasModelo("1o", 7, "OURO", 1, 12));
		cartas.add(new CartasModelo("2c", 8, "COPAS", 2, 16));
		cartas.add(new CartasModelo("2p", 8, "BASTOS", 2, 16));
		cartas.add(new CartasModelo("2o", 8, "OURO", 2, 16));
		cartas.add(new CartasModelo("2e", 8, "ESPADAS", 2, 16));
		cartas.add(new CartasModelo("3c", 9, "COPAS", 3, 24));
		cartas.add(new CartasModelo("3p", 9, "BASTOS", 3, 24));
		cartas.add(new CartasModelo("3o", 9, "OURO", 3, 24));
		cartas.add(new CartasModelo("3e", 9, "ESPADAS", 3, 24));
		cartas.add(new CartasModelo("7o", 10, "OURO", 7, 40));
		cartas.add(new CartasModelo("7e", 11, "ESPADAS", 7, 42));
		cartas.add(new CartasModelo("1p", 12, "BASTOS", 1, 50));
		cartas.add(new CartasModelo("1e", 13, "ESPADAS", 1, 52));
	}
//	
//	public static void main(String[] args) {
//		//System.out.println(ConsultaCartaPeloId(52));
//		
//		
//	}

}
