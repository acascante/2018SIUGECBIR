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
@Entity(name = "ActaDetalle")
@Table(name = "SIGEBI_OAF.SIGB_ACTA_DETALLE")
@SequenceGenerator(name = "SGB_SQ_ACTA_DETALLE", sequenceName = "SIGEBI_OAF.SGB_SQ_ACTA_DETALLE", initialValue = 1, allocationSize = 1)
public class ActaDetalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_ACTA_DETALLE")
    @Column(name = "ID_ACTA_DETALLE")
    private Long idActaDetalle;

    @ManyToOne
    @JoinColumn(name = "ID_ACTA", referencedColumnName = "ID_DOCUMENTO")
    private Documento idActa;

    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private BienEntity idBien;

    public Long getIdActaDetalle() {
        return idActaDetalle;
    }

    public void setIdActaDetalle(Long idActaDetalle) {
        this.idActaDetalle = idActaDetalle;
    }

    public Documento getIdActa() {
        return idActa;
    }

    public void setIdActa(Documento idActa) {
        this.idActa = idActa;
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
        hash = 83 * hash + (this.idActaDetalle != null ? this.idActaDetalle.hashCode() : 0);
        hash = 83 * hash + (this.idActa != null ? this.idActa.hashCode() : 0);
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
        final ActaDetalle other = (ActaDetalle) obj;
        if (this.idActaDetalle != other.idActaDetalle && (this.idActaDetalle == null || !this.idActaDetalle.equals(other.idActaDetalle))) {
            return false;
        }
        if (this.idActa != other.idActa && (this.idActa == null || !this.idActa.equals(other.idActa))) {
            return false;
        }
        if (this.idBien != other.idBien && (this.idBien == null || !this.idBien.equals(other.idBien))) {
            return false;
        }
        return true;
    }

}
