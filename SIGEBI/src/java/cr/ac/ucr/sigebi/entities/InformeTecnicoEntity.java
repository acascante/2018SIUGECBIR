/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "InformeTecnicoEntity")
@Table(name = "SIGEBI_OAF.SGB_INFORME_TECNICO")
@SequenceGenerator(name = "SGB_SQ_INFORME_TECNICO",  sequenceName = "SIGEBI_OAF.SGB_SQ_INFORME_TECNICO", initialValue = 1, allocationSize = 1)
public class InformeTecnicoEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_INFORME_TECNICO")
    @Column(name = "ID_INFORME_TECNICO")
    private Long idInformeTecnico;

    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien idBien;
    
    @Column(name = "EVALUACION")
    private String evaluacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo idTipo;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado idEstado;
       
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public InformeTecnicoEntity() {       
    }

    public InformeTecnicoEntity(Bien bien, Estado idEstado) {     
         this.idBien = bien;
         this.idEstado = idEstado;
         this.evaluacion = "";
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">

    public Long getIdInformeTecnico() {
        return idInformeTecnico;
    }

    public void setIdInformeTecnico(Long idInformeTecnico) {
        this.idInformeTecnico = idInformeTecnico;
    }

    public Bien getIdBien() {
        return idBien;
    }

    public void setIdBien(Bien idBien) {
        this.idBien = idBien;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
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

    //</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.idInformeTecnico != null ? this.idInformeTecnico.hashCode() : 0);
        hash = 61 * hash + (this.idBien != null ? this.idBien.hashCode() : 0);
        hash = 61 * hash + (this.evaluacion != null ? this.evaluacion.hashCode() : 0);
        hash = 61 * hash + (this.idTipo != null ? this.idTipo.hashCode() : 0);
        hash = 61 * hash + (this.idEstado != null ? this.idEstado.hashCode() : 0);
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
        final InformeTecnicoEntity other = (InformeTecnicoEntity) obj;
        if ((this.evaluacion == null) ? (other.evaluacion != null) : !this.evaluacion.equals(other.evaluacion)) {
            return false;
        }
        if (this.idInformeTecnico != other.idInformeTecnico && (this.idInformeTecnico == null || !this.idInformeTecnico.equals(other.idInformeTecnico))) {
            return false;
        }
        if (this.idBien != other.idBien && (this.idBien == null || !this.idBien.equals(other.idBien))) {
            return false;
        }
        if (this.idTipo != other.idTipo && (this.idTipo == null || !this.idTipo.equals(other.idTipo))) {
            return false;
        }
        if (this.idEstado != other.idEstado && (this.idEstado == null || !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }
    //</editor-fold>

}
