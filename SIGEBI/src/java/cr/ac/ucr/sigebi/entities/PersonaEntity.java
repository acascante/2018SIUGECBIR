/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.entities;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "PersonaEntity")
@Table(name = "SF_PERSONA")
public class PersonaEntity  extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "NUM_PERSONA")
    private Integer numPersona;//
    
    @Column(name = "IDETIFICACION")
    private String identificacion;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;
    
    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;
    
    @Column(name = "ES_PROVEEDOR")
    private String esProveedor;
    
    
    
//    @Column(name = "ID_ESTADO")
//    private Integer idEstado;

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    
    
    public PersonaEntity() {
    }

    
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">

    public Integer getNumPersona() {
        return numPersona;
    }

    public void setNumPersona(Integer numPersona) {
        this.numPersona = numPersona;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getEsProveedor() {
        return esProveedor;
    }

    public void setEsProveedor(String esProveedor) {
        this.esProveedor = esProveedor;
    }

    

    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Sobrecargas">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numPersona != null ? numPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaEntity)) {
            return false;
        }
        PersonaEntity other = (PersonaEntity) object;
        if ((this.numPersona == null && other.numPersona != null) || (this.numPersona != null && !this.numPersona.equals(other.numPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.PersonaEntity[numPersona=" + numPersona + "]";
    }
    //</editor-fold>
    

    
    
}
