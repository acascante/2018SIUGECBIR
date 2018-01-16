package cr.ac.ucr.framework.seguridad.entidades;

import cr.ac.ucr.framework.seguridad.ObjetoBase;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *
 * @author Adam M. Gamboa González
 * @since 27 Agosto 2010
 */
@Entity
@Table(name="SEGURIDAD_TIPO_UNIDAD")
public class SegTipoUnidad extends ObjetoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIPO_UNIDAD")
    private String id_tipo_unidad;

    @Basic(optional = false)
    @Column(name = "DSC_TIPO_UNIDAD")
    private String dsc_tipo_unidad;

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>>      Contructores      <<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public SegTipoUnidad(){}

    public SegTipoUnidad(String id_tipo){
        this.id_tipo_unidad = id_tipo;
    }

    public SegTipoUnidad(String id_tipo, String dsc_tipo){
        this.id_tipo_unidad = id_tipo;
        this.dsc_tipo_unidad = dsc_tipo;
    }

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/


    public String getDsc_tipo_unidad() {
        return dsc_tipo_unidad;
    }

    public void setDsc_tipo_unidad(String dsc_tipo_unidad) {
        this.dsc_tipo_unidad = dsc_tipo_unidad;
    }

    public String getId_tipo_unidad() {
        return id_tipo_unidad;
    }

    public void setId_tipo_unidad(String id_tipo_unidad) {
        this.id_tipo_unidad = id_tipo_unidad;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_tipo_unidad != null ? id_tipo_unidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegTipoUnidad)) {
            return false;
        }
        SegTipoUnidad other = (SegTipoUnidad) object;
        if ((this.id_tipo_unidad == null && other.id_tipo_unidad != null) || (this.id_tipo_unidad != null && !this.id_tipo_unidad.equals(other.id_tipo_unidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gbsys.seguridad.entidades.TipoUnidad[id=" + id_tipo_unidad + "]";
    }

}
