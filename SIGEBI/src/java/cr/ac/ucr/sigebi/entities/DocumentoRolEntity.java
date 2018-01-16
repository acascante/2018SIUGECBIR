/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "DocumentoRolEntity")
@Table(name = "SIGEBI_OAF.SGB_DOCUMENTO_ROL")
public class DocumentoRolEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    private DocumentoEntity idDocumento;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")
    private RolEntity idRol;
       
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public DocumentoRolEntity() {       
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">

    public DocumentoEntity getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(DocumentoEntity idDocumento) {
        this.idDocumento = idDocumento;
    }

    public RolEntity getIdRol() {
        return idRol;
    }

    public void setIdRol(RolEntity idRol) {
        this.idRol = idRol;
    }
    //</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.idDocumento != null ? this.idDocumento.hashCode() : 0);
        hash = 53 * hash + (this.idRol != null ? this.idRol.hashCode() : 0);
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
        final DocumentoRolEntity other = (DocumentoRolEntity) obj;
        return true;
    }
    
    //</editor-fold>

}
