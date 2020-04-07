package outros;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import java.io.*;
import org.apache.commons.lang.SystemUtils;

import cbr.AtualizaConsultas.AuxiliaConsultas.CartasModelo;
import gamer.Truco.Cartas.DarCartasModelo;

public class GeraCartas {

	private static List<CartasModelo> Cartas1;
	private static List<CartasModelo> Cartas2;
	private static String diretorio ="C:/ufsm/logs/";
	static void darCartas() {

		Cartas1 = new ArrayList<CartasModelo>();
		Cartas2 = new ArrayList<CartasModelo>();

		DarCartasModelo darCartasAuto = new DarCartasModelo();

		List<CartasModelo> listaCartas = new ArrayList<CartasModelo>();

		// adiciona 6 cartas e retorna, depois na interface retorna 3 p/ cada jogador
		for (int i = 0; i < 6; i++) {
			listaCartas.add(darCartasAuto.entregarCartas());
			if (i < 3) {
				Cartas1.add(listaCartas.get(i));
			} else {
				Cartas2.add(listaCartas.get(i));
			}
		}

		// Sorting
		Collections.sort(Cartas1, new Comparator<CartasModelo>() {
			public int compare(CartasModelo carta2, CartasModelo carta1) {
				return Integer.compare(carta1.valorImportancia, carta2.valorImportancia);
			}
		});
		Collections.sort(Cartas2, new Comparator<CartasModelo>() {
			public int compare(CartasModelo carta2, CartasModelo carta1) {
				return Integer.compare(carta1.valorImportancia, carta2.valorImportancia);
			}
		});
	}

	public static void GerarCartas(int NumeroPartidas) {

		String Path = diretorio;


		int numerodeMaos = 30;

		for (int j = 1; j <= NumeroPartidas; j++) {

			String Set1 = "";
			String Set2 = "";
			
			for (int i = 1; i < numerodeMaos; i++) {
//				//System.out.println("RODADA " + i);
				darCartas();
				String c1 = Cartas1.get(0).toStringFile() + "-" + Cartas1.get(1).toStringFile() + "-"
						+ Cartas1.get(2).toStringFile();

				String c2 = Cartas2.get(0).toStringFile() + "-" + Cartas2.get(1).toStringFile() + "-"
						+ Cartas2.get(2).toStringFile();

//				//System.out.println("c1\t " + c1);
//				//System.out.println("c2\t " + c2);
				Set1 = Set1 + c1 + "\n";
				Set2 = Set2 + c2 + "\n";
			}

			try {
				File arquivo = new File(Path + "Jogador1-Partida" + j + ".txt");
//				File arquivo = new File(Path);
				FileWriter fw = new FileWriter(arquivo, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(Set1);
//				bw.append(Dados);
				bw.flush();
				bw.close();
				// //System.out.println("concluido");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				File arquivo = new File(Path + "Jogador2-Partida" + j + ".txt");// File arquivo = new File(Path);
				FileWriter fw = new FileWriter(arquivo, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(Set2);
//				bw.append(Dados);
				bw.flush();
				bw.close();
				// //System.out.println("concluido");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		//System.out.println("Done");
	}

	public static List<CartasModelo> Ler(int Quem, int PartidaNumber, int Index) {

		String Path = diretorio +"Jogador" + Quem + "-Partida" + PartidaNumber + ".txt";

		BufferedReader br = null;
		String Linhalida = "";
		String cardsSplitBy = "-";
		String dataSplitBy = ",";

		List<CartasModelo> CartasRecebidas = new ArrayList<CartasModelo>();
		String[] cartasArquivo = null;
		try {
			int i = 0;
			br = new BufferedReader(new FileReader(Path));
			while ((Linhalida = br.readLine()) != null) {
				cartasArquivo = Linhalida.split(cardsSplitBy);
				if (i == Index)
					break;
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < 3; i++) {
//			//System.out.println(cartasArquivo[i]);
			String[] dados = cartasArquivo[i].split(dataSplitBy);
			CartasModelo c = new CartasModelo(dados[0], Integer.valueOf(dados[1]), dados[2], Integer.valueOf(dados[3]),
					Integer.valueOf(dados[4]));
			CartasRecebidas.add(c);
		}
		return CartasRecebidas;
	}

	public static void main(String[] args) {
//		//System.out.println("Cll");
		GerarCartas(100);
	}

}
