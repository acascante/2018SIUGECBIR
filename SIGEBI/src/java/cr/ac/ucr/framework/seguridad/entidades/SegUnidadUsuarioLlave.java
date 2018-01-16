package cr.ac.ucr.framework.seguridad.entidades;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 *
 * @author Adrian Zamora Villalta
 */
@Embeddable
public class SegUnidadUsuarioLlave implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_UNIDAD_EJECUTORA")
    private Integer idUnidadEjecutora;

    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private String idUsuario;

    @Basic(optional = false)
    @Column(name = "ID_EMPRESA")
    private Integer idEmpresa;

    @Basic(optional = false)
    @Column(name = "ID_PERFIL")
    private Integer idPerfil;


   /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>>      Constructores     <<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/

    public SegUnidadUsuarioLlave() {
    }

    public SegUnidadUsuarioLlave(Integer idUnidadEjecutora, String idUsuario, Integer idEmpresa, Integer idPerfil) {
        this.idUnidadEjecutora = idUnidadEjecutora;
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.idPerfil = idPerfil;
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidadEjecutora != null ? idUnidadEjecutora.hashCode() : 0);
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegUnidadUsuarioLlave)) {
            return false;
        }
        SegUnidadUsuarioLlave other = (SegUnidadUsuarioLlave) object;
        if ((this.idUnidadEjecutora == null && other.idUnidadEjecutora != null) || (this.idUnidadEjecutora != null && !this.idUnidadEjecutora.equals(other.idUnidadEjecutora))) {
            return false;
        }
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ucr.geco.entidades.SisUnidadUsuarioPK[idUnidadEjecutora=" + idUnidadEjecutora + ", idUsuario=" + idUsuario + ", idEmpresa=" + idEmpresa + ", idPerfil=" + idPerfil + "]";
    }

}
