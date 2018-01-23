/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "DocumentoActa")
@Table(name = "SIGEBI_OAF.SIGB_DOCUMENTO_ACTA")
@DiscriminatorValue("2")
@PrimaryKeyJoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
public class DocumentoActa extends Documento implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Column(name = "CEDULA")
    private String cedula;

    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;

    @Column(name = "AUTORIZACION")
    private String autorizacion;

    @Column(name = "FECHA") // TODO revisar el campo fecha por que el documento tambien lo tiene
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID")
    private UnidadEjecutora unidadEjecutora;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo tipoActa;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public Tipo getTipoActa() {
        return tipoActa;
    }

    public void setTipoActa(Tipo tipoActa) {
        this.tipoActa = tipoActa;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.cedula != null ? this.cedula.hashCode() : 0);
        hash = 79 * hash + (this.razonSocial != null ? this.razonSocial.hashCode() : 0);
        hash = 79 * hash + (this.autorizacion != null ? this.autorizacion.hashCode() : 0);
        hash = 79 * hash + (this.fecha != null ? this.fecha.hashCode() : 0);
        hash = 79 * hash + (this.unidadEjecutora != null ? this.unidadEjecutora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentoActa other = (DocumentoActa) obj;
        if ((this.cedula == null) ? (other.cedula != null) : !this.cedula.equals(other.cedula)) {
            return false;
        }
        if ((this.razonSocial == null) ? (other.razonSocial != null) : !this.razonSocial.equals(other.razonSocial)) {
            return false;
        }
        if ((this.autorizacion == null) ? (other.autorizacion != null) : !this.autorizacion.equals(other.autorizacion)) {
            return false;
        }
        if (this.fecha != other.fecha && (this.fecha == null || !this.fecha.equals(other.fecha))) {
            return false;
        }
        if (this.unidadEjecutora != other.unidadEjecutora && (this.unidadEjecutora == null || !this.unidadEjecutora.equals(other.unidadEjecutora))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
