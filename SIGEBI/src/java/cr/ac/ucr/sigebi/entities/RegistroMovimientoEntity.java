/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
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
import javax.persistence.TemporalType;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "RegistroMovimientoEntity")
@Table(name = "SIGEBI_OAF.SGB_REGISTRO_MOVIMIENTO")
@SequenceGenerator(name = "SGB_SQ_REG_MOV_ID_REG_MOV",  sequenceName = "SIGEBI_OAF.SGB_SQ_REG_MOV_ID_REG_MOV", initialValue = 1, allocationSize = 1)
public class RegistroMovimientoEntity   extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_REG_MOV_ID_REG_MOV")
    @Column(name = "ID_REGISTRO_MOVIMIENTO")
    private Integer idRegistro;

    @Column(name = "ID_TIPO")
    private Integer idTipo;

    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private BienEntity idBien;

    @Column(name = "OBSERVACION")
    private String observacion;

    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "NUM_PERSONA")
    private Integer numeroPersona;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private EstadoEntity idEstado;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public RegistroMovimientoEntity() {
    }

    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Sets y Gets">
    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public BienEntity getIdBien() {
        return idBien;
    }

    public void setIdBien(BienEntity idBien) {
        this.idBien = idBien;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getNumeroPersona() {
        return numeroPersona;
    }

    public void setNumeroPersona(Integer numeroPersona) {
        this.numeroPersona = numeroPersona;
    }

    public EstadoEntity getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadoEntity idEstado) {
        this.idEstado = idEstado;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.idRegistro != null ? this.idRegistro.hashCode() : 0);
        hash = 97 * hash + (this.idTipo != null ? this.idTipo.hashCode() : 0);
        hash = 97 * hash + (this.idBien != null ? this.idBien.hashCode() : 0);
        hash = 97 * hash + (this.observacion != null ? this.observacion.hashCode() : 0);
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
        final RegistroMovimientoEntity other = (RegistroMovimientoEntity) obj;
        if ((this.observacion == null) ? (other.observacion != null) : !this.observacion.equals(other.observacion)) {
            return false;
        }
        if (this.idRegistro != other.idRegistro && (this.idRegistro == null || !this.idRegistro.equals(other.idRegistro))) {
            return false;
        }
        if (this.idTipo != other.idTipo && (this.idTipo == null || !this.idTipo.equals(other.idTipo))) {
            return false;
        }
        if (this.idBien != other.idBien && (this.idBien == null || !this.idBien.equals(other.idBien))) {
            return false;
        }
        return true;
    }

    //</editor-fold>
}
