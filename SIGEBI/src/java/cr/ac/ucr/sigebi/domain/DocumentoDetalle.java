/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "DocumentoDetalle")
@Table(name = "SIGEBI_OAF.SIGB_DOCUMENTO_DETALLE")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "SGB_SQ_DOCUMENTO_DETALLE", sequenceName = "SIGEBI_OAF.SGB_SQ_DOCUMENTO_DETALLE", initialValue = 1, allocationSize = 1)
public class DocumentoDetalle extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_DOCUMENTO_DETALLE")
    @Column(name = "ID_DOCUMENTO_DETALLE")
    private Long id;

    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Documento documento;

    @ManyToOne
    @JoinColumn(name = "ID_BIEN", referencedColumnName = "ID_BIEN")
    private Bien bien;

    @Column(name = "DISCRIMINATOR")
    private Integer discriminator;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">

    public DocumentoDetalle() {
    }

    public DocumentoDetalle(Documento documento, Bien bien) {
        this.documento = documento;
        this.bien = bien;
        this.discriminator = Constantes.DISCRIMINATOR_DOCUMENTO_DETALLE_GENERAL;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Integer getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(Integer discriminator) {
        this.discriminator = discriminator;
    }
    public Bien getBien() {
        return bien;
    }

    public void setBien(Bien bien) {
        this.bien = bien;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (this.documento != null ? this.documento.hashCode() : 0);
        hash = 71 * hash + (this.discriminator != null ? this.discriminator.hashCode() : 0);
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
        final DocumentoDetalle other = (DocumentoDetalle) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.documento != other.documento && (this.documento == null || !this.documento.equals(other.documento))) {
            return false;
        }
        if (this.discriminator != other.discriminator && (this.discriminator == null || !this.discriminator.equals(other.discriminator))) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
