package bo.gob.aduana.sga.reportes.controller;

import bo.gob.aduana.sga.reportes.excepciones.ReporteGenerationException;
import bo.gob.aduana.sga.reportes.service.ReporteService;
import bo.gob.aduana.sga.reportes.utils.LectorArchivosProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA. User: esalamanca Date: 10-03-14 Time: 12:16 PM
 * Servicios REST para proyecto REPORTES.
 */
@Controller
@RequestMapping(value = "/rest")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @RequestMapping(value = "/saludo/{nombre}", method = RequestMethod.GET)
    @ResponseBody
    public String saludo(@PathVariable String nombre) {
        return "Saludos " + nombre
                + ". Bienvenido a la ver. 1.0.1 del Gestor de Reportes del SGA";
    }

    @RequestMapping(value = "/{tipoReporte}/{variable}/{formato}", method = RequestMethod.GET)
    @ResponseBody
    public void getReporte(HttpServletRequest request,
                           HttpServletResponse response, @PathVariable String tipoReporte,
                           @PathVariable String variable, @PathVariable String formato) {
        // real path de los resources
        String outputFolderPath = LectorArchivosProperties.REPORTES_SERVLET_OUTPUT_FOLDER;
        try {

            String fileName;

            // voy a recibir un string con el nombre de archivo
            fileName = reporteService.generarEtiquetaArticulo(tipoReporte, variable, formato, outputFolderPath);

            // obtener inputstream del archivo
            InputStream inputStream = new FileInputStream(fileName);

            // enviar inputstream en el response.getoutputstream
            FileCopyUtils.copy(inputStream, response.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ReporteGenerationException e) {
            e.printStackTrace();
        }
    }

    /*

     */
    @RequestMapping(value = "etiqueta/{tipoReporte}/{variable}/{formato}", method = RequestMethod.GET)
    @ResponseBody
    public void getReporteEtiquetaArticulo(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable String tipoReporte,
                           @PathVariable String variable,
                           @PathVariable String formato) {
        // real path de los resources
        String outputFolderPath = LectorArchivosProperties.REPORTES_SERVLET_OUTPUT_FOLDER;
        try {

            String fileName;

            // voy a recibir un string con el nombre de archivo
            fileName = reporteService.generarEtiquetaArticulo(tipoReporte, variable, formato, outputFolderPath);

            // obtener inputstream del archivo
            InputStream inputStream = new FileInputStream(fileName);

            // enviar inputstream en el response.getoutputstream
            FileCopyUtils.copy(inputStream, response.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ReporteGenerationException e) {
            e.printStackTrace();
        }
    }

	/*@RequestMapping(value = "/qrcode/{data}", method = RequestMethod.GET)
    @ResponseBody
	public void getReporteWithQRCode(HttpServletResponse response, @PathVariable String data) {
		try {

            //Sacar datos para el QR Code
            String datos = reporteService.dataForQRCode(data);

			//Generar el QRCode
            QRCodeGenerator qrCodeGenerator = new QRCodeGeneratorImpl(datos);
			ByteArrayOutputStream qrCodeStream = qrCodeGenerator.drawToOutputStream();

			response.setContentType("image/png");
			response.setContentLength(qrCodeStream.size());
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			OutputStream outputStream = response.getOutputStream();
			outputStream.write(qrCodeStream.toByteArray());
			outputStream.flush();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @RequestMapping(value = "/qrcodeFromData/{data}", method = RequestMethod.GET)
    @ResponseBody
    public void getQRCodeFromData(HttpServletResponse response, @PathVariable String data) {
        try {

            //Generar el QRCode
            QRCodeGenerator qrCodeGenerator = new QRCodeGeneratorImpl(data);
            ByteArrayOutputStream qrCodeStream = qrCodeGenerator.drawToOutputStream();

            response.setContentType("image/png");
            response.setContentLength(qrCodeStream.size());
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            OutputStream outputStream = response.getOutputStream();
            outputStream.write(qrCodeStream.toByteArray());
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
