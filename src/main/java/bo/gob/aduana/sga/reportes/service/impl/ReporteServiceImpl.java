package bo.gob.aduana.sga.reportes.service.impl;


import bo.gob.aduana.sga.reportes.birt.ReporteRender;
import bo.gob.aduana.sga.reportes.excepciones.ReporteGenerationException;
import bo.gob.aduana.sga.reportes.service.ReporteService;


import bo.gob.aduana.sga.reportes.utils.LectorArchivosProperties;


import com.bo.openlogic.core.bean.ArticulosReportes;
import com.bo.openlogic.core.bean.JsonResult;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA. User: esalamanca Date: 20-03-14 Time: 02:47 PM
 * Implementacion de los servicios de generacion de reportes.
 */
@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ReporteRender reporteRender;

    @Autowired
    private RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(ReporteServiceImpl.class);


    @Override
    public String generarEtiquetaArticulo(String tipoReporte, String variable, String formato, String path) throws ReporteGenerationException {
        try {
            RestTemplate restTemplate = new RestTemplate();

            logger.info("URL " + LectorArchivosProperties.APPLICATION_URL_SALESFORCE_SERVICE + "etiqueta/articulo/" + variable);
            //Consumimos el nuevo servicio con data.estado = REGISTRADO
            /*ResponseEntity<com.bo.openlogic.core.bean.JsonResult> responseEntity = restTemplate.getForEntity(
                    LectorArchivosProperties.APPLICATION_URL_SALESFORCE_SERVICE+"etiqueta/articulo/" + variable,
                    com.bo.openlogic.core.bean.JsonResult.class
            );*/
            //ResponseEntity<JsonResult> response = restTemplate.exchange(, HttpMethod.GET,null,com.bo.openlogic.core.bean.JsonResult.class);
//
            com.bo.openlogic.core.bean.JsonResult jsonResult = restTemplate.getForObject(LectorArchivosProperties.APPLICATION_URL_SALESFORCE_SERVICE + "etiqueta/articulo/" + variable, JsonResult.class);

            if (jsonResult.getSuccess()) {

                // Convertimos los datos en JSON
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result", JSONObject.fromObject(jsonResult.getResult()));
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                String json = mapper.writeValueAsString(jsonResult.getResult());
                System.out.println("JSON:" + json);
                ArticulosReportes articulosReportes = mapper.readValue(json, ArticulosReportes.class);

                // convert byte array back to BufferedImage
                System.out.println(articulosReportes.getPrecio());
                InputStream in = new ByteArrayInputStream(articulosReportes.getUpc());
                BufferedImage bImageFromConvert = ImageIO.read(in);
                File codigoBarras = new File(LectorArchivosProperties.REPORTES_LOCAL_OUTPUT_FOLDER + "/" + variable + "afrodita.png");

                ImageIO.write(bImageFromConvert, "jpg", codigoBarras);

                BufferedImage originalImage = ImageIO.read(new File(LectorArchivosProperties.REPORTES_LOCAL_OUTPUT_FOLDER + "/"+variable+"afrodita.png"));
                // convert BufferedImage to byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(originalImage, "png", baos);
                baos.flush();
                baos.close();

                System.out.println("ABS:"+codigoBarras.getAbsolutePath()+"---"+codigoBarras.getPath()+" "+codigoBarras.toURI().getPath());
                reporteRender.setOutputFolder(path);

                return reporteRender.generaReporte(jsonObject.toString(), codigoBarras.getAbsolutePath(), tipoReporte + ".rptdesign", formato);

            } else {
                throw new ReporteGenerationException("Datos no encontrados.");
            }
//

        } catch (Exception e) {
            e.printStackTrace();
//            logger.info(e.getMessage());
            throw new ReporteGenerationException(e.getMessage());
        }
    }

}



