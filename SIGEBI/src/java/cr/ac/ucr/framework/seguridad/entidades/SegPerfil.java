package cr.ac.ucr.framework.seguridad.entidades;

import cr.ac.ucr.framework.seguridad.ObjetoBase;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 *
 * @author Adrián Zamora Villalta
 */
@Entity
@Table(name = "SEGURIDAD_PERFIL")
@NamedQueries({
    @NamedQuery(name = "SegPerfil.findAll", query = "SELECT s FROM SegPerfil s"),
    @NamedQuery(name = "SegPerfil.findByIdPerfil", query = "SELECT s FROM SegPerfil s WHERE s.idPerfil = :idPerfil"),
    @NamedQuery(name = "SegPerfil.findByDscNombre", query = "SELECT s FROM SegPerfil s WHERE s.dscNombre = :dscNombre"),
    @NamedQuery(name = "SegPerfil.findByDscPerfil", query = "SELECT s FROM SegPerfil s WHERE s.dscPerfil = :dscPerfil")}
)
@NamedNativeQuery(name="SegPerfil.findPerfilesUsuario",resultClass=SegPerfil.class,
        query="select * from seguridad_perfil p where p.id_perfil in "
        + "(select uu.id_perfil from seguridad_unidad_usuario uu where uu.id_usuario=? and "
        + "uu.id_unidad_ejecutora=? and "
        + "uu.id_perfil in (select id_perfil from seguridad_elemento_perfil ep "
        + "where ep.id_elemento in (select id_elemento from "
        + "seguridad_modulo_elemento me where me.id_modulo "
        + "in (select sm.id_modulo from seguridad_sistema_modulo sm "
        + "where sm.id_sistema=?))))"
        )
//@SequenceGenerator(name = "sequence_perfil", sequenceName = "SEQ_GECO_SEG_PERFIL")
public class SegPerfil  extends ObjetoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID_PERFIL")
    private Integer idPerfil;

    @Basic(optional = false)
    @Column(name = "DSC_NOMBRE")
    private String dscNombre;

    @Column(name = "DSC_PERFIL")
    private String dscPerfil;

  
   




   public transient static final String SECUENCIA = "SEQ_SEGURIDAD_PERFIL";
   
   /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>>      Constructores     <<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/

    public SegPerfil() {
    }

    public SegPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public SegPerfil(Integer idPerfil, String dscNombre) {
        this.idPerfil = idPerfil;
        this.dscNombre = dscNombre;
    }

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getDscNombre() {
        return dscNombre;
    }

    public void setDscNombre(String dscNombre) {
        this.dscNombre = dscNombre;
    }

    public String getDscPerfil() {
        return dscPerfil;
    }

    public void setDscPerfil(String dscPerfil) {
        this.dscPerfil = dscPerfil;
    }

   

    

    

     



    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegPerfil)) {
            return false;
        }
        SegPerfil other = (SegPerfil) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(idPerfil);
    }

}
