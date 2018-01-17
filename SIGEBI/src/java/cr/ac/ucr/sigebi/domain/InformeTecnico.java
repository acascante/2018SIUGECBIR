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
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Column(name = "EVALUACION")
    private String evaluacion;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo tipoInforme;
    
    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien bien;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Tipo getTipoInforme() {
        return tipoInforme;
    }

    public void setTipoInforme(Tipo tipoInforme) {
        this.tipoInforme = tipoInforme;
    }

    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.evaluacion != null ? this.evaluacion.hashCode() : 0);
        hash = 83 * hash + (this.tipoInforme != null ? this.tipoInforme.hashCode() : 0);
        hash = 83 * hash + (this.bien != null ? this.bien.hashCode() : 0);
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
        if (this.tipoInforme != other.tipoInforme && (this.tipoInforme == null || !this.tipoInforme.equals(other.tipoInforme))) {
            return false;
        }
        if (this.bien != other.bien && (this.bien == null || !this.bien.equals(other.bien))) {
            return false;
        }
        return true;
    }
}