package bo.gob.aduana.sga.reportes.birt;

public interface ReporteRender {

	public String generaReporte(String jsonString, String uri, String pathFile, String renderTipo) throws Exception;

	public String generaCertificado(String jsonString, String uri, String foto,
			String fecha, String pathFile, String renderTipo) throws Exception;

	public void setOutputFolder(String outputFolder);
}
