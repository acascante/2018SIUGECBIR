package cr.ac.ucr.framework.seguridad.entidades;

import cr.ac.ucr.framework.seguridad.ObjetoBase;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Objeto de las empresas
 * @author Adam M. Gamboa González
 * @since 27 Agosto 2010
 */
@Entity
@Table(name = "SEGURIDAD_EMPRESA")
public class SegEmpresa extends ObjetoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_empresa;

    @Basic(optional=false)
    @Column(name="NOM_EMPRESA")
    private String nombre_empresa;

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>>      Contructores      <<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public SegEmpresa(){

    }

    public SegEmpresa(Integer id_empresa, String nombre_empresa) {
        this.id_empresa = id_empresa;
        this.nombre_empresa = nombre_empresa;
    }



    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/


    public Integer getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(Integer id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }




    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_empresa != null ? id_empresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegEmpresa)) {
            return false;
        }
        SegEmpresa other = (SegEmpresa) object;
        if ((this.id_empresa == null && other.id_empresa != null) || (this.id_empresa != null && !this.id_empresa.equals(other.id_empresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gbsys.seguridad.entidades.Empresa[id=" + id_empresa + "]";
    }

}
