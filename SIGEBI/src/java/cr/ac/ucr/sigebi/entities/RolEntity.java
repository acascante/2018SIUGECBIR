/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.sigebi.domain.Estado;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "RolEntity")
@Table(name = "SIGEBI_OAF.SGB_ROL")
@SequenceGenerator(name = "SGB_SQ_ROL",  sequenceName = "SIGEBI_OAF.SGB_SQ_ROL", initialValue = 1, allocationSize = 1)
public class RolEntity extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
 
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_ROL")
    @Column(name = "ID_ROL")
    private Long idRol;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "NOMBRE")
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado idEstado;
       
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    public RolEntity() {       
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }
    //</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.idRol != null ? this.idRol.hashCode() : 0);
        hash = 97 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 97 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 97 * hash + (this.idEstado != null ? this.idEstado.hashCode() : 0);
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
        final RolEntity other = (RolEntity) obj;
        return true;
    }
    
    //</editor-fold>

}
