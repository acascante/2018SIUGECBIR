/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain.reportes;

import cr.ac.ucr.sigebi.domain.DocumentoActa;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteExclusiones implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long idDocumento;
    private String autorizacion;
    private Date fecha;
    private String identificacion;
    private String descripcion;
    private String estado;
    private String unidadEjecutora;    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteExclusiones() {
        super();
    }
    
    public ReporteExclusiones(DocumentoDetalle detalle, DocumentoActa documento) {
        this.idDocumento = documento.getId();
        this.autorizacion = documento.getAutorizacion();
        this.identificacion = detalle.getBien().getIdentificacion()!= null ? detalle.getBien().getIdentificacion().getIdentificacion() : null;
        this.descripcion = detalle.getBien().getDescripcion();
        this.estado = detalle.getBien().getEstado().getNombre();
        this.unidadEjecutora = detalle.getBien().getUnidadEjecutora().getDescripcion();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(String unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }    
    //</editor-fold>
}