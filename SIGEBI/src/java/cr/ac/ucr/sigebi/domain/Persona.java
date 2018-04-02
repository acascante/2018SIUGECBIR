/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author jorge.serrano
 */
@Entity(name = "Persona")
@Table(name = "SF_PERSONA")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "SGB_SQ_PERSONA", sequenceName = "SIGEBI_OAF.SGB_SQ_ADJUNTO", initialValue = 1, allocationSize = 1)
public class Persona extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @Basic(optional = false)
    @Column(name = "NUM_PERSONA")
    private Long numPersona;

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
    
    @Column(name = "ES_ESTUDIANTE")
    private String esEstudiante;
    
    @Column(name = "ES_FUNCIONARIO")
    private String esFuncionario;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Persona() {}
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SET's y GET's ">
    public Long getNumPersona() {
        return numPersona;
    }

    public void setNumPersona(Long numPersona) {
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

    public String getEsEstudiante() {
        return esEstudiante;
    }

    public void setEsEstudiante(String esEstudiante) {
        this.esEstudiante = esEstudiante;
    }

    public String getEsFuncionario() {
        return esFuncionario;
    }

    public void setEsFuncionario(String esFuncionario) {
        this.esFuncionario = esFuncionario;
    }

    public String getNombreCompleto() {
        StringBuilder nombreCompleto = new StringBuilder(" ");
        if (this.nombre != null && !this.nombre.isEmpty()) {
            nombreCompleto.append(this.nombre);
        }
        if (this.primerApellido != null && !this.primerApellido.isEmpty()) {
            nombreCompleto.append(" ");
            nombreCompleto.append(this.primerApellido);
        }
        if (this.segundoApellido != null && !this.segundoApellido.isEmpty()) {
            nombreCompleto.append(" ");
            nombreCompleto.append(this.segundoApellido);
        }
        return nombreCompleto.toString();
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
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
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