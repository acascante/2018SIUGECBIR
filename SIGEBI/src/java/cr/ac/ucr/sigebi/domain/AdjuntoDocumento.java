/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "AdjuntoDocumento")
@Table(name = "SIGEBI_OAF.SIGB_ADJUNTO_DOCUMENTO")
@PrimaryKeyJoinColumn(name = "ID_ADJUNTO_DOCUMENTO", referencedColumnName = "ID_ADJUNTO")
@DiscriminatorValue("2")
public class AdjuntoDocumento extends Adjunto implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    private Documento documento;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public AdjuntoDocumento() {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Sets y Gets">
    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.documento != null ? this.documento.hashCode() : 0);
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
        final AdjuntoDocumento other = (AdjuntoDocumento) obj;
        if (this.documento != other.documento && (this.documento == null || !this.documento.equals(other.documento))) {
            return false;
        }
        return true;
    }
    //</editor-fold>

}
