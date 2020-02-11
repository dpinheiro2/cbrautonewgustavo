package treinamentoModelo;

public class AgenteModelo {

	String TipoReusoIntraCluster;
	String TipoReusoExtraCluster;
	String TipoAprendizagem;
	String UsarCluster;
	String TipoRetencao;
	String TipoBase;
	double threshold;
	boolean recalcularCentroide;
	

	public AgenteModelo(String tipoReusoIntraCluster, String tipoReusoExtraCluster, String tipoAprendizagem,
			String usarCluster, String tipoRetencao, String tipoBase, double threshold, boolean recalcularCentroide) {
		super();
		TipoReusoIntraCluster = tipoReusoIntraCluster;
		TipoReusoExtraCluster = tipoReusoExtraCluster;
		TipoAprendizagem = tipoAprendizagem;
		UsarCluster = usarCluster;
		TipoRetencao = tipoRetencao;
		TipoBase = tipoBase;
		this.threshold = threshold;
		this.recalcularCentroide = recalcularCentroide;
	}

	public boolean isRecalcularCentroide() {
		return recalcularCentroide;
	}
	public void setRecalcularCentroide(boolean recalcularCentroide) {
		this.recalcularCentroide = recalcularCentroide;
	}
	public String getTipoReusoIntraCluster() {
		return TipoReusoIntraCluster;
	}
	public void setTipoReusoIntraCluster(String tipoReusoIntraCluster) {
		TipoReusoIntraCluster = tipoReusoIntraCluster;
	}
	public String getTipoReusoExtraCluster() {
		return TipoReusoExtraCluster;
	}
	public void setTipoReusoExtraCluster(String tipoReusoExtraCluster) {
		TipoReusoExtraCluster = tipoReusoExtraCluster;
	}
	public String getTipoAprendizagem() {
		return TipoAprendizagem;
	}
	public void setTipoAprendizagem(String tipoAprendizagem) {
		TipoAprendizagem = tipoAprendizagem;
	}
	public String getUsarCluster() {
		return UsarCluster;
	}
	public void setUsarCluster(String usarCluster) {
		UsarCluster = usarCluster;
	}
	public String getTipoRetencao() {
		return TipoRetencao;
	}
	public void setTipoRetencao(String tipoRetencao) {
		TipoRetencao = tipoRetencao;
	}
	public String getTipoBase() {
		return TipoBase;
	}
	public void setTipoBase(String tipoBase) {
		TipoBase = tipoBase;
	}
	public double getThreshold() {
		return threshold;
	}
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
	
	
}
