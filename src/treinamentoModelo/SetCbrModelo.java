package treinamentoModelo;

public class SetCbrModelo {
	
	AgenteModelo agenteModelo1;
	AgenteModelo agenteModelo2;
	
	
	public SetCbrModelo(AgenteModelo agenteModelo1, AgenteModelo agenteModelo2) {
		this.agenteModelo1 = agenteModelo1;
		this.agenteModelo2 = agenteModelo2;
		
	}
	
	
	
	public AgenteModelo getAgenteModelo1() {
		return agenteModelo1;
	}
	public void setAgenteModelo1(AgenteModelo agenteModelo1) {
		this.agenteModelo1 = agenteModelo1;
	}
	public AgenteModelo getAgenteModelo2() {
		return agenteModelo2;
	}
	public void setAgenteModelo2(AgenteModelo agenteModelo2) {
		this.agenteModelo2 = agenteModelo2;
	}
	
	public String getTipoReusoExtraCluster1() {
		return  agenteModelo1.getTipoReusoExtraCluster();
	}
	public void setTipoReusoExtraCluster1(String tipoReusoExtraCluster1) {
			agenteModelo1.setTipoReusoExtraCluster(tipoReusoExtraCluster1);
	}
	
	public String getTipoReusoExtraCluster2() {
	  return	agenteModelo2.getTipoReusoExtraCluster();
	}
	public void setTipoReusoExtraCluster2(String tipoReusoExtraCluster2) {
		agenteModelo2.setTipoReusoExtraCluster(tipoReusoExtraCluster2);
	}
	
	public void setThreshold2(double threshold2) {
	       agenteModelo2.setThreshold(threshold2);
	}
	public double getThreshold1() {
		return agenteModelo1.getThreshold();
	}
	public void setThreshold1(double threshold1) {
		agenteModelo1.setThreshold(threshold1);
	}
	public double getThreshold2() {
		return agenteModelo2.getThreshold();
	}
	
	
	public String getTipoAprendizagem1() {
		return  agenteModelo1.getTipoAprendizagem();
	}
	
	public void setTipoAprendizagem1(String tipoAprendizagem1) {
		agenteModelo1.setTipoAprendizagem(tipoAprendizagem1);
	}
	
	public String getUsarCluster1() {
		return agenteModelo1.getUsarCluster();
	}
	
	public void setUsarCluster1(String usarCluster1) {
		agenteModelo1.setUsarCluster(usarCluster1);
	}
	
	public String getTipoRetencao1() {
		return agenteModelo1.getTipoRetencao();
	}
	public void setTipoRetencao1(String tipoRetencao1) {
		agenteModelo1.setTipoRetencao(tipoRetencao1);
	}
	
	public String getTipoBase1() {
		
		return agenteModelo1.getTipoBase();
	}
	
	public void setTipoBase1(String tipoBase1) {
		agenteModelo1.setTipoBase(tipoBase1);
	}
	
	public String getTipoAprendizagem2() {
		return agenteModelo2.getTipoAprendizagem();
	}
	
	public void setTipoAprendizagem2(String tipoAprendizagem2) {
		agenteModelo2.setTipoAprendizagem(tipoAprendizagem2);
	}
	
	public String getUsarCluster2() {
		return agenteModelo2.getUsarCluster();
	}
	public void setUsarCluster2(String usarCluster2) {
		agenteModelo2.setUsarCluster(usarCluster2);
	}
	public String getTipoRetencao2() {
		return agenteModelo2.getTipoRetencao();
	}
	public void setTipoRetencao2(String tipoRetencao2) {
		agenteModelo2.setTipoRetencao(tipoRetencao2);
	}
	public String getTipoBase2() {
		return agenteModelo2.getTipoBase();
	}
	public void setTipoBase2(String tipoBase2) {
		agenteModelo2.setTipoBase(tipoBase2);
	}
	public String getTipoReusoIntraCluster1() {
		return agenteModelo1.getTipoReusoIntraCluster();
	}
	public void setTipoReusoIntraCluster1(String tipoReusoIntraCluster1) {
		agenteModelo1.setTipoReusoIntraCluster(tipoReusoIntraCluster1);
	}
	public String getTipoReusoIntraCluster2() {
		return agenteModelo2.getTipoReusoIntraCluster();
	}
	public void setTipoReusoIntraCluster2(String tipoReusoIntraCluster2) {
		agenteModelo2.setTipoReusoIntraCluster(tipoReusoIntraCluster2);
	}
	
	public void setRecalcularCentroideAgente1(boolean recalcularUm) {
		agenteModelo1.setRecalcularCentroide(recalcularUm);
	}
public void setRecalcularCentroideAgente2(boolean recalcularDois) {
		agenteModelo2.setRecalcularCentroide(recalcularDois);
	}
public boolean getRecalculaCentroideAgenteUm() {
	return agenteModelo1.isRecalcularCentroide();
}

public boolean getRecalculaCentroideAgenteDois() {
	return agenteModelo2.isRecalcularCentroide();
}

}
