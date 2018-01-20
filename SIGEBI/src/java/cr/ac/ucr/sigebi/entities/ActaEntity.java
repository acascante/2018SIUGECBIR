/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "ActaEntity")
@Table(name = "SIGEBI_OAF.SGB_ACTA")
@SequenceGenerator(name = "SGB_SQ_ACTA", sequenceName = "SIGEBI_OAF.SGB_SQ_ACTA", initialValue = 1, allocationSize = 1)
public class ActaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_ACTA")
    @Column(name = "ID_ACTA") // NUMBER 
    private int idActa;

    @Column(name = "AUTORIZACION") // VARCHAR2 (100 Byte) 
    private String autorizacion;

    @Column(name = "FECHA") // DATE  
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo idTipo;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado idEstado;

    @Column(name = "NUM_UNIDAD_EJEC") // VARCHAR2 (200 Byte) 
    private Long unidadEjecutora;

    @Column(name = "CEDULA") // VARCHAR2 (100 Byte) 
    private String cedula;

    @Column(name = "RAZON_SOCIAL") // VARCHAR2 (200 Byte) 
    private String razonSocial;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Contructores">
    public ActaEntity() {
        fecha = new Date();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GETs & SETs">
    public int getIdActa() {
        return idActa;
    }

    public void setIdActa(int idActa) {
        this.idActa = idActa;
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

    public Tipo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Tipo idTipo) {
        this.idTipo = idTipo;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Long getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(Long unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idActa;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActaEntity)) {
            return false;
        }
        ActaEntity other = (ActaEntity) object;
        if (this.idActa != other.idActa) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.ucr.sigebi.entities.ActaEntity[ id=" + idActa + " ]";
    }

    //</editor-fold>
}
