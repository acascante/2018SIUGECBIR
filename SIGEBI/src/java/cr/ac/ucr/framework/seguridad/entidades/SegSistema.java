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
 * @author Adam M. Gamboa González
 */
@Entity
@Table(name = "SEGURIDAD_SISTEMA")
@NamedQueries({
    @NamedQuery(name = "SegSistema.findAll", query = "SELECT s FROM SegSistema s"),
    @NamedQuery(name = "SegSistema.findByIdSistema", query = "SELECT s FROM SegSistema s WHERE s.idSistema = :idSistema"),
    @NamedQuery(name = "SegSistema.findByDscSistema", query = "SELECT s FROM SegSistema s WHERE s.dscSistema = :dscSistema")}
)
public class SegSistema  extends ObjetoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID_SISTEMA")
    private String idSistema;

    @Column(name = "DSC_SISTEMA")
    private String dscSistema;

    /*@JoinTable(name = "SEG_SISTEMA_MODULO",
        inverseJoinColumns = {
            @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO")},
        joinColumns = {
            @JoinColumn(name = "ID_SISTEMA", referencedColumnName = "ID_SISTEMA")})
    @ManyToMany
    private transient Set<Modulo> modulos_sistema;*/

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      Constructores     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/

    public SegSistema() {
    }

    public SegSistema(String idSistema) {
        this.idSistema = idSistema;
    }

    public SegSistema(String idSistema, String dsc_sistema){
        this.idSistema = idSistema;
        this.dscSistema = dsc_sistema;
    }

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/

    public String getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(String idSistema) {
        this.idSistema = idSistema;
    }

    public String getDscSistema() {
        return dscSistema;
    }

    public void setDscSistema(String dscSistema) {
        this.dscSistema = dscSistema;
    }

    /*public List<Modulo> getModulos_sistema() {
        return modulos_sistema;
    }

    public void setModulos_sistema(List<Modulo> modulos_sistema) {
        this.modulos_sistema = modulos_sistema;
    }*/


    
    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSistema != null ? idSistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegSistema)) {
            return false;
        }
        SegSistema other = (SegSistema) object;
        if ((this.idSistema == null && other.idSistema != null) || (this.idSistema != null && !this.idSistema.equals(other.idSistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idSistema;
    }

}
