package gamer.Truco.Cartas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import cbr.AtualizaConsultas.AuxiliaConsultas.CartasModelo;

public class DarCartasModelo {
	List<CartasModelo> cartas;

	public DarCartasModelo() {
//		adicionarTodasCartas1();
		adicionarTodasCartas2();
//		adicionarCartasFlor();
	}

	private void adicionarTodasCartas1() {

		cartas = new ArrayList<CartasModelo>();
		cartas.add(new CartasModelo("4c", 0, "COPAS", 4, 00)); // 4-p
		cartas.add(new CartasModelo("4p", 0, "BASTOS", 4, 01)); // 4-p
		cartas.add(new CartasModelo("4o", 0, "OURO", 4, 02)); // 4-o
		cartas.add(new CartasModelo("4e", 0, "ESPADAS", 4, 03)); // 4-e
		cartas.add(new CartasModelo("5c", 1, "COPAS", 5, 10)); // 5-c
		cartas.add(new CartasModelo("5p", 1, "BASTOS", 5, 11)); // 5-p
		cartas.add(new CartasModelo("5o", 1, "OURO", 5, 12)); // 5-o
		cartas.add(new CartasModelo("5e", 1, "ESPADAS", 5, 13)); // 5-e
		cartas.add(new CartasModelo("6c", 2, "COPAS", 6, 20)); // 6-c
		cartas.add(new CartasModelo("6p", 2, "BASTOS", 6, 21)); // 6-p
		cartas.add(new CartasModelo("6o", 2, "OURO", 6, 22)); // 6-o
		cartas.add(new CartasModelo("6e", 2, "ESPADAS", 6, 23)); // 6-e
		cartas.add(new CartasModelo("7c", 3, "COPAS", 7, 30)); // 7-c
		cartas.add(new CartasModelo("7p", 3, "BASTOS", 7, 31)); // 7-p
		cartas.add(new CartasModelo("10c", 4, "COPAS", 0, 40)); // 10-c
		cartas.add(new CartasModelo("10p", 4, "BASTOS", 0, 41)); // 10-p
		cartas.add(new CartasModelo("10o", 4, "OURO", 0, 42)); // 10-o
		cartas.add(new CartasModelo("10e", 4, "ESPADAS", 0, 43)); // 10-e
		cartas.add(new CartasModelo("11c", 5, "COPAS", 0, 50)); // 11-c
		cartas.add(new CartasModelo("11p", 5, "BASTOS", 0, 51)); // 11-p
		cartas.add(new CartasModelo("11o", 5, "OURO", 0, 52)); // 11-o
		cartas.add(new CartasModelo("11e", 5, "ESPADAS", 0, 53)); // 11-e
		cartas.add(new CartasModelo("12c", 6, "COPAS", 0, 60)); // 12-c
		cartas.add(new CartasModelo("12p", 6, "BASTOS", 0, 61)); // 12-p
		cartas.add(new CartasModelo("12o", 6, "OURO", 0, 62)); // 12-o
		cartas.add(new CartasModelo("12e", 6, "ESPADAS", 0, 63)); // 12-e
		cartas.add(new CartasModelo("1c", 7, "COPAS", 1, 70)); // 1-c
		cartas.add(new CartasModelo("1o", 7, "OURO", 1, 71)); // 1-o
		cartas.add(new CartasModelo("2c", 8, "COPAS", 2, 80)); // 2-c
		cartas.add(new CartasModelo("2p", 8, "BASTOS", 2, 81)); // 2-p
		cartas.add(new CartasModelo("2o", 8, "OURO", 2, 82)); // 2-o
		cartas.add(new CartasModelo("2e", 8, "ESPADAS", 2, 83)); // 2-e
		cartas.add(new CartasModelo("3c", 9, "COPAS", 3, 90)); // 3-c
		cartas.add(new CartasModelo("3p", 9, "BASTOS", 3, 91)); // 3-p
		cartas.add(new CartasModelo("3o", 9, "OURO", 3, 92)); // 3-o
		cartas.add(new CartasModelo("3e", 9, "ESPADAS", 3, 93)); // 3-e
		cartas.add(new CartasModelo("7o", 10, "OURO", 7, 100)); // 7-o
		cartas.add(new CartasModelo("7e", 11, "ESPADAS", 7, 110)); // 7-e
		cartas.add(new CartasModelo("1p", 12, "BASTOS", 1, 120)); // 1-p
		cartas.add(new CartasModelo("1e", 13, "ESPADAS", 1, 130)); // 1-e

	}

	private void adicionarTodasCartas2() {

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

	private void adicionarCartasFlor() {

		cartas = new ArrayList<CartasModelo>();
		cartas.add(new CartasModelo("4o", 0, "OURO", 4, 1));
		cartas.add(new CartasModelo("5o", 1, "OURO", 5, 2));
		cartas.add(new CartasModelo("6o", 2, "OURO", 6, 3));
		cartas.add(new CartasModelo("10o", 4, "OURO", 0, 6));
		cartas.add(new CartasModelo("11o", 5, "OURO", 0, 7));
		cartas.add(new CartasModelo("12o", 6, "OURO", 0, 8));
		cartas.add(new CartasModelo("1c", 7, "COPAS", 1, 12));
		cartas.add(new CartasModelo("1o", 7, "OURO", 1, 12));
		cartas.add(new CartasModelo("2o", 8, "OURO", 2, 16));
		cartas.add(new CartasModelo("3o", 9, "OURO", 3, 24));
		cartas.add(new CartasModelo("7o", 10, "OURO", 7, 40));

		/*
		 * aqui da cartas do mesmo naipe apenas cartas = new ArrayList<CartasModelo>();
		 * cartas.add(new CartasModelo("4p", 0, "BASTOS", 4, 01)); cartas.add(new
		 * CartasModelo("5p", 1, "BASTOS", 5, 11)); cartas.add(new CartasModelo("6p", 2,
		 * "BASTOS", 6, 21)); cartas.add(new CartasModelo("7p", 3, "BASTOS", 7, 31));
		 * cartas.add(new CartasModelo("10p", 4, "BASTOS", 0, 41)); cartas.add(new
		 * CartasModelo("11p", 5, "BASTOS", 0, 51)); cartas.add(new CartasModelo("1p",
		 * 12, "BASTOS", 1, 120)); cartas.add(new CartasModelo("2p", 8, "BASTOS", 2,
		 * 81));
		 * 
		 */

	}

	public CartasModelo entregarCartas() {
		Collections.shuffle(cartas);
		CartasModelo retorno = cartas.get(0);
		for (int i = 0; i < cartas.size(); i++) {
			if (cartas.get(i).getCarta().equalsIgnoreCase(retorno.getCarta()))
				cartas.remove(i);
		}

		return retorno;

	}

	public class RandomCollection<E> {
		private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
		private final Random random;
		private double total = 0;

		public RandomCollection() {
			this(new Random());
		}

		public RandomCollection(Random random) {
			this.random = random;
		}

		public RandomCollection<E> add(double weight, E result) {
			if (weight <= 0)
				return this;
			total += weight;
			map.put(total, result);
			return this;
		}

		public E next() {
			double value = random.nextDouble() * total;
			return map.higherEntry(value).getValue();
		}
	}

	public CartasModelo entregarCartasFracas() {

		float prob1 = 70 / 14;
		float prob2 = 20 / 13;
		float prob3 = 10 / 13;

		RandomCollection<Object> rc = new RandomCollection<>().add(prob1, new CartasModelo("4c", 0, "COPAS", 4, 0))
				.add(prob1, new CartasModelo("4p", 0, "BASTOS", 4, 1))
				.add(prob1, new CartasModelo("4o", 0, "OURO", 4, 1))
				.add(prob1, new CartasModelo("4e", 0, "ESPADAS", 4, 1))
				.add(prob1, new CartasModelo("5c", 1, "COPAS", 5, 2))
				.add(prob1, new CartasModelo("5p", 1, "BASTOS", 5, 2))
				.add(prob1, new CartasModelo("5o", 1, "OURO", 5, 2))
				.add(prob1, new CartasModelo("5e", 1, "ESPADAS", 5, 2))
				.add(prob1, new CartasModelo("6c", 2, "COPAS", 6, 3))
				.add(prob1, new CartasModelo("6p", 2, "BASTOS", 6, 3))
				.add(prob1, new CartasModelo("6o", 2, "OURO", 6, 3))
				.add(prob1, new CartasModelo("6e", 2, "ESPADAS", 6, 3))
				.add(prob1, new CartasModelo("7c", 3, "COPAS", 7, 4))
				.add(prob1, new CartasModelo("7p", 3, "BASTOS", 7, 4))
				.add(prob2, new CartasModelo("10c", 4, "COPAS", 0, 6))
				.add(prob2, new CartasModelo("10p", 4, "BASTOS", 0, 6))
				.add(prob2, new CartasModelo("10o", 4, "OURO", 0, 6))
				.add(prob2, new CartasModelo("10e", 4, "ESPADAS", 0, 6))
				.add(prob2, new CartasModelo("11c", 5, "COPAS", 0, 7))
				.add(prob2, new CartasModelo("11p", 5, "BASTOS", 0, 7))
				.add(prob2, new CartasModelo("11o", 5, "OURO", 0, 7))
				.add(prob2, new CartasModelo("11e", 5, "ESPADAS", 0, 7))
				.add(prob2, new CartasModelo("12c", 6, "COPAS", 0, 8))
				.add(prob2, new CartasModelo("12p", 6, "BASTOS", 0, 8))
				.add(prob2, new CartasModelo("12o", 6, "OURO", 0, 8))
				.add(prob2, new CartasModelo("12e", 6, "ESPADAS", 0, 8))
				.add(prob2, new CartasModelo("1c", 7, "COPAS", 1, 12))
				.add(prob3, new CartasModelo("1o", 7, "OURO", 1, 12))
				.add(prob3, new CartasModelo("2c", 8, "COPAS", 2, 16))
				.add(prob3, new CartasModelo("2p", 8, "BASTOS", 2, 16))
				.add(prob3, new CartasModelo("2o", 8, "OURO", 2, 16))
				.add(prob3, new CartasModelo("2e", 8, "ESPADAS", 2, 16))
				.add(prob3, new CartasModelo("3c", 9, "COPAS", 3, 24))
				.add(prob3, new CartasModelo("3p", 9, "BASTOS", 3, 24))
				.add(prob3, new CartasModelo("3o", 9, "OURO", 3, 24))
				.add(prob3, new CartasModelo("3e", 9, "ESPADAS", 3, 24))
				.add(prob3, new CartasModelo("7o", 10, "OURO", 7, 40))
				.add(prob3, new CartasModelo("7e", 11, "ESPADAS", 7, 42))
				.add(prob3, new CartasModelo("1p", 12, "BASTOS", 1, 50))
				.add(prob3, new CartasModelo("1e", 13, "ESPADAS", 1, 52));
		return (CartasModelo) rc.next();
	}

}
