package bo.gob.aduana.sga.reportes.service;

import bo.gob.aduana.sga.reportes.excepciones.ReporteGenerationException;

/**
 * Created with IntelliJ IDEA.
 * User: esalamanca
 * Date: 20-03-14
 * Time: 02:46 PM
 * Servicios para la generacion de reportes.
 */
public interface ReporteService {



    /*
     */

    public String generarEtiquetaArticulo(
            String tipoReporte,
            String variable,
            String formato,
            String path
    ) throws ReporteGenerationException;

    /**
     * Genera el Certificado de Registro de OCE
     *
     * @param tipoReporte Debe ser oceCertificado
     * @param variable    Nombre de usuario OCE
     * @param formato     HTML o PDF
     * @param path        Ruta donde se almacena el archivo
     * @return Nombre del archivo generado
     * @throws ReporteGenerationException
     */
//    public String generarCertificado(
//            String tipoReporte,
//            String variable,
//            String formato,
//            String path
//    ) throws ReporteGenerationException;

    /**
     * Realiza una consulta al SPF y genera datos para el QRCode
     *
     * @param usuario Nombre de usuario para la consulta
     * @return Datos requeridos para el QRCode
     * @throws Exception
     */
//    public String dataForQRCode(String usuario) throws Exception;
}
