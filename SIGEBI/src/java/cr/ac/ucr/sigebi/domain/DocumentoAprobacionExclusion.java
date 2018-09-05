/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "DocumentoAprobacionExclusion")
@Table(name = "SIGEBI_OAF.SIGB_DOCUMENTO_APROBACION")
@DiscriminatorValue("6")
@PrimaryKeyJoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
public class DocumentoAprobacionExclusion extends Documento implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Column(name = "AUTORIZACION")
    private String autorizacion;
    
    @Column(name = "CEDULA")
    private String cedula;

    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
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
    //</editor-fold>
}