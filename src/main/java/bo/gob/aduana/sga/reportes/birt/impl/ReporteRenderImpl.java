package bo.gob.aduana.sga.reportes.birt.impl;

import bo.gob.aduana.sga.reportes.birt.ReporteRender;
import bo.gob.aduana.sga.reportes.utils.LectorArchivosProperties;
import com.bo.openlogics.core.util.SGAUtils;
import org.apache.log4j.Logger;
import org.eclipse.birt.report.engine.api.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Service
public class ReporteRenderImpl implements ReporteRender {

    private static final Logger logger = Logger
            .getLogger(ReporteRenderImpl.class);

    private IReportEngine engine;

    private String outputFolder;

    public ReporteRenderImpl() {
        final EngineConfig config = new EngineConfig();
        engine = new ReportEngine(config);
        engine.changeLogLevel(Level.WARNING);
        this.outputFolder = LectorArchivosProperties.REPORTES_LOCAL_OUTPUT_FOLDER;
    }

	/*
	 * @see
	 * bo.gob.aduana.sga.reportes.birt.ReporteRender#generaReporte(java.lang
	 * .String, java.lang.String, java.lang.String) jsonString: String en
	 * formato JSON para generar el reporte pathFile: Nombre del archivo para
	 * genera el reporte renderTipo: Formato para generar el reporte "html" o
	 * "pdf"
	 */

    @Override
    public String generaReporte(String jsonString, String uri, String pathFile,
                                String renderTipo) throws Exception {

        logger.info("generaReporte1: --> " + pathFile);

        logger.info("Datos del Reporte: " + pathFile + " "
                + renderTipo);

        // adicionamos el parametro json para generar el reporte
        Map<String, Object> extraParams = new HashMap<String, Object>();
        extraParams.put("myParam", jsonString);
        extraParams.put("codigoBarras", uri);
        //System.out.println("URI:"+uri);

        try {

            // del CLASSPATH/reporte leemos el archivo y generar el reporte
            // InputStream stream = ReporteRenderImpl.class
            // .getResourceAsStream(this.outputFolder + "/input/" + pathFile);

            InputStream stream = new FileInputStream(this.outputFolder
                    + "/input/" + pathFile);
            final IReportRunnable design = engine.openReportDesign(stream);

            logger.info("generaReporte2: --> " + pathFile + " -->: "
                    + this.outputFolder + "/input/" + pathFile);

            // Create task to run and render the report,
            final IRunAndRenderTask task = engine
                    .createRunAndRenderTask(design);
            // Set parent classloader for engine
            task.getAppContext().put(
                    EngineConstants.APPCONTEXT_CLASSLOADER_KEY,
                    ReporteRenderImpl.class.getClassLoader());

            for (String extraParamNames : extraParams.keySet()) {
                Object extraParamValue = extraParams.get(extraParamNames);
                task.setParameterValue(extraParamNames, extraParamValue);
            }

            final IRenderOption options = new RenderOption();
            options.setOutputFormat(renderTipo);

            // Crear directorio para almacenar los reportes
            SGAUtils.createDirectory(outputFolder);

            // nombre del archivo
			String nombreFile = outputFolder + "/" + Math.random()
					+ "." + options.getOutputFormat();
			options.setOutputFileName(nombreFile);

            logger.info("generaReporte3: --> " + pathFile);

            if (options.getOutputFormat().equalsIgnoreCase("html")) {
                System.out.println("generarreporte4->");
                final HTMLRenderOption htmlOptions = new HTMLRenderOption(
                        options);
                htmlOptions.setImageDirectory("img");
                htmlOptions.setHtmlPagination(false);
                htmlOptions.setHtmlRtLFlag(false);
                htmlOptions.setEmbeddable(false);
                htmlOptions.setSupportedImageFormats("PNG");

            } else if (options.getOutputFormat().equalsIgnoreCase("pdf")) {
                System.out.println("generarreporte5->");
                final PDFRenderOption pdfOptions = new PDFRenderOption(options);
                System.out.println("generarreporte6->");
                pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW,
                        IPDFRenderOption.FIT_TO_PAGE_SIZE);
                System.out.println("generarreporte7->");
                pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW,
                        IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);
                System.out.println("generarreporte8->");
            }
            System.out.println("generarreporte9");
            task.setRenderOption(options);

            // run and render report
            task.run();

            task.close();

            return nombreFile;

        } catch (Exception ex) {
            throw new Exception("No se pudo generar el reporte: "
                    + ex.getMessage());
        }
    }

    @Override
    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    @Override
    public String generaCertificado(String jsonString, String uri, String foto,
                                    String fecha, String pathFile, String renderTipo) throws Exception {

        logger.info("generaReporte1: --> " + pathFile);

        // adicionamos el parametro json para generar el reporte
        Map<String, Object> extraParams = new HashMap<String, Object>();
        extraParams.put("myParam", jsonString);
        extraParams.put("qrCode", uri);
        extraParams.put("foto", foto);
        extraParams.put("fecha", fecha);

        try {

            // del CLASSPATH/reporte leemos el archivo y generar el reporte
            // InputStream stream = ReporteRenderImpl.class
            // .getResourceAsStream(this.outputFolder + "/input/" + pathFile);

            InputStream stream = new FileInputStream(this.outputFolder
                    + "/input/" + pathFile);
            final IReportRunnable design = engine.openReportDesign(stream);

            logger.info("generaReporte2: --> " + pathFile + " -->: "
                    + this.outputFolder + "/input/" + pathFile);

            // Create task to run and render the report,
            final IRunAndRenderTask task = engine
                    .createRunAndRenderTask(design);
            // Set parent classloader for engine
            task.getAppContext().put(
                    EngineConstants.APPCONTEXT_CLASSLOADER_KEY,
                    ReporteRenderImpl.class.getClassLoader());

            for (String extraParamNames : extraParams.keySet()) {
                Object extraParamValue = extraParams.get(extraParamNames);
                task.setParameterValue(extraParamNames, extraParamValue);
            }

            final IRenderOption options = new RenderOption();
            options.setOutputFormat(renderTipo);

            // Crear directorio para almacenar los reportes
            //SGAUtils.createDirectory(outputFolder);

            // nombre del archivo
            String nombreFile = outputFolder + "/" + Math.random()
                    + "." + options.getOutputFormat();
            options.setOutputFileName(nombreFile);

            logger.info("generaReporte3: --> " + pathFile);

            if (options.getOutputFormat().equalsIgnoreCase("html")) {

                final HTMLRenderOption htmlOptions = new HTMLRenderOption(
                        options);
                htmlOptions.setImageDirectory("img");
                htmlOptions.setHtmlPagination(false);
                htmlOptions.setHtmlRtLFlag(false);
                htmlOptions.setEmbeddable(false);
                htmlOptions.setSupportedImageFormats("PNG");

            } else if (options.getOutputFormat().equalsIgnoreCase("pdf")) {

                final PDFRenderOption pdfOptions = new PDFRenderOption(options);
                pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW,
                        IPDFRenderOption.FIT_TO_PAGE_SIZE);
                pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW,
                        IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);
            }

            task.setRenderOption(options);

            // run and render report
            task.run();

            task.close();

            return nombreFile;

        } catch (Exception ex) {
            throw new Exception("No se pudo generar el reporte: "
                    + ex.getMessage());
        }
    }
}
