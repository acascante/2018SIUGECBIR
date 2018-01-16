package cr.ac.ucr.framework.seguridad.entidades;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * 
 * @author Huevo
 */
 @Embeddable
public class SegUnidadEjecutoraLlave implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_UNIDAD_EJECUTORA")
    private Integer idUnidadEjecutora;
    @Basic(optional = false)
    @Column(name = "ID_EMPRESA")
    private Integer idEmpresa;

   /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>>     Consutructores     <<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public SegUnidadEjecutoraLlave() {
    }

    public SegUnidadEjecutoraLlave(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public SegUnidadEjecutoraLlave(Integer idUnidadEjecutora, Integer idEmpresa) {
        this.idUnidadEjecutora = idUnidadEjecutora;
        this.idEmpresa = idEmpresa;
    }

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/

    public Integer getIdUnidadEjecutora() {
        return idUnidadEjecutora;
    }

    public void setIdUnidadEjecutora(Integer idUnidadEjecutora) {
        this.idUnidadEjecutora = idUnidadEjecutora;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidadEjecutora != null ? idUnidadEjecutora.hashCode() : 0);
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegUnidadEjecutoraLlave)) {
            return false;
        }
        SegUnidadEjecutoraLlave other = (SegUnidadEjecutoraLlave) object;
        if ((this.idUnidadEjecutora == null && other.idUnidadEjecutora != null) || (this.idUnidadEjecutora != null && !this.idUnidadEjecutora.equals(other.idUnidadEjecutora))) {
            return false;
        }
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idUnidadEjecutora + "%" + idEmpresa;
    }

}
