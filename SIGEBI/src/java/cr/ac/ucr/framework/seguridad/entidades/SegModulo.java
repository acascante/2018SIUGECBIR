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
@Table(name = "SEGURIDAD_MODULO")
@NamedQueries({
    @NamedQuery(name = "SegModulo.findAll", query = "SELECT s FROM SegModulo s"),
    @NamedQuery(name = "SegModulo.findByIdModulo", query = "SELECT s FROM SegModulo s WHERE s.idModulo = :idModulo"),
    @NamedQuery(name = "SegModulo.findByDscNombre", query = "SELECT s FROM SegModulo s WHERE s.dscNombre = :dscNombre")
})
//@SequenceGenerator(name = "sequence_modulo", sequenceName = "SEQ_GECO_SEG_MODULO")
public class SegModulo extends ObjetoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID_MODULO")
    private Integer idModulo;

    @Basic(optional = false)
    @Column(name = "DSC_NOMBRE")
    private String dscNombre;

    


    public transient static final String SECUENCIA = "SEQ_SEGURIDAD_MODULO";

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      Constructores    <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public SegModulo() {
    }

    public SegModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    public SegModulo(Integer idModulo, String dscNombre) {
        this.idModulo = idModulo;
        this.dscNombre = dscNombre;
    }

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/

    public Integer getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    public String getDscNombre() {
        return dscNombre;
    }

    public void setDscNombre(String dscNombre) {
        this.dscNombre = dscNombre;
    } 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModulo != null ? idModulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegModulo)) {
            return false;
        }
        SegModulo other = (SegModulo) object;
        if ((this.idModulo == null && other.idModulo != null) || (this.idModulo != null && !this.idModulo.equals(other.idModulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getDscNombre();
    }

}
