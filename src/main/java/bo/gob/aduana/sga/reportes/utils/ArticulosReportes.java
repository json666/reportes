package bo.gob.aduana.sga.reportes.utils;

import java.io.Serializable;

/**
 * Created by json on 9/10/14.
 */
public class ArticulosReportes implements Serializable {
    private Long id;
    private Double precio ;
    private byte[] upc ;
    private String descripcionArticulo ;

    public ArticulosReportes() {
    }

    public ArticulosReportes(Long id, Double precio, byte[] upc) {
        this.id = id;
        this.precio = precio;
        this.upc = upc;
    }

    public ArticulosReportes(Long id, Double precio, byte[] upc, String descripcionArticulo) {
        this.id = id;
        this.precio = precio;
        this.upc = upc;
        this.descripcionArticulo = descripcionArticulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public byte[] getUpc() {
        return upc;
    }

    public void setUpc(byte[] upc) {
        this.upc = upc;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }
}
