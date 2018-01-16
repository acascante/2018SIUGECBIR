package cr.ac.ucr.framework.seguridad.entidades;

import cr.ac.ucr.framework.seguridad.ObjetoBase;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 *
 * @author huevo
 */
@Entity
@Table(name = "SEGURIDAD_TIPO_ELEMENTO")
@NamedQueries({
    @NamedQuery(name = "SegTipoElemento.findAll", query = "SELECT s FROM SegTipoElemento s"),
    @NamedQuery(name = "SegTipoElemento.findByIdTipoElemento", query = "SELECT s FROM SegTipoElemento s WHERE s.idTipoElemento = :idTipoElemento"),
    @NamedQuery(name = "SegTipoElemento.findByDscTipoElemento", query = "SELECT s FROM SegTipoElemento s WHERE s.dscTipoElemento = :dscTipoElemento")}
)
public class SegTipoElemento extends ObjetoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_ELEMENTO")
    private Integer idTipoElemento;

    @Basic(optional = false)
    @Column(name = "DSC_TIPO_ELEMENTO")
    private String dscTipoElemento;

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      Constructores     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public SegTipoElemento() {
    }

    public SegTipoElemento(Integer idTipoElemento) {
        this.idTipoElemento = idTipoElemento;
    }

    public SegTipoElemento(Integer idTipoElemento, String dscTipoElemento) {
        this.idTipoElemento = idTipoElemento;
        this.dscTipoElemento = dscTipoElemento;
    }

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/

    public Integer getIdTipoElemento() {
        return idTipoElemento;
    }

    public void setIdTipoElemento(Integer idTipoElemento) {
        this.idTipoElemento = idTipoElemento;
    }

    public String getDscTipoElemento() {
        return dscTipoElemento;
    }

    public void setDscTipoElemento(String dscTipoElemento) {
        this.dscTipoElemento = dscTipoElemento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoElemento != null ? idTipoElemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegTipoElemento)) {
            return false;
        }
        SegTipoElemento other = (SegTipoElemento) object;
        if ((this.idTipoElemento == null && other.idTipoElemento != null) || (this.idTipoElemento != null && !this.idTipoElemento.equals(other.idTipoElemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idTipoElemento.toString();
    }

}
