package gamer.Truco.Cartas;

public class CartaPorImportanciaModelo {

	private int id;
	private int valorImportancia;
	private String carta;

	public CartaPorImportanciaModelo(int id, int valorImportancia, String carta) {
		super();
		this.id = id;
		this.valorImportancia = valorImportancia;
		this.carta = carta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValorImportancia() {
		return valorImportancia;
	}

	public void setValorImportancia(int valorImportancia) {
		this.valorImportancia = valorImportancia;
	}

	public String getCarta() {
		return carta;
	}

	public void setCarta(String carta) {
		this.carta = carta;
	}

}
