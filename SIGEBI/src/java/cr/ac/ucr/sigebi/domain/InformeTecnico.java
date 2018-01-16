/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.sigebi.entities.*;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "InformeTecnico")
@Table(name = "SIGEBI_OAF.SIGB_INFORME_TECNICO")
@PrimaryKeyJoinColumn(name = "ID_INFORME_TECNICO", referencedColumnName = "ID_DOCUMENTO")
public class InformeTecnico extends Documento implements Serializable {
    
    @Column(name = "EVALUACION")
    private String evaluacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private TipoEntity idTipo;
    
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private BienEntity idBien;

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public TipoEntity getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoEntity idTipo) {
        this.idTipo = idTipo;
    }

    public BienEntity getIdBien() {
        return idBien;
    }

    public void setIdBien(BienEntity idBien) {
        this.idBien = idBien;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.evaluacion != null ? this.evaluacion.hashCode() : 0);
        hash = 83 * hash + (this.idTipo != null ? this.idTipo.hashCode() : 0);
        hash = 83 * hash + (this.idBien != null ? this.idBien.hashCode() : 0);
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
        final InformeTecnico other = (InformeTecnico) obj;
        if ((this.evaluacion == null) ? (other.evaluacion != null) : !this.evaluacion.equals(other.evaluacion)) {
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

}
