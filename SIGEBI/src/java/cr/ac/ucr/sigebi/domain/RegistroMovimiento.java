/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "RegistroMovimiento")
@Table(name = "SIGEBI_OAF.SIGB_REGISTRO_MOVIMIENTO")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discriminator")
@SequenceGenerator(name = "SGB_SQ_REG_MOV_ID_REG_MOV", sequenceName = "SIGEBI_OAF.SGB_SQ_REG_MOV_ID_REG_MOV", initialValue = 1, allocationSize = 1)
public class RegistroMovimiento extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SGB_SQ_REG_MOV_ID_REG_MOV")
    @Column(name = "ID_REGISTRO_MOVIMIENTO")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo tipo;

    @Column(name = "OBSERVACION")
    private String observacion;

    @Column(name = "TELEFONO")
    private Integer telefono;

    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    private Estado estado;

    @Column(name = "DISCRIMINATOR")
    private Integer discriminator;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    
    public RegistroMovimiento() {
    }

    public RegistroMovimiento(Tipo tipo, String observacion, Integer telefono, Date fecha, Usuario usuario, Estado estado, Integer discriminator) {
        this.tipo = tipo;
        this.observacion = observacion;
        this.fecha = fecha;
        this.usuario = usuario;
        this.estado = estado;
        this.discriminator = discriminator;
        this.telefono = telefono;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public Integer getId() {
        return id;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(Integer discriminator) {
        this.discriminator = discriminator;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.tipo != null ? this.tipo.hashCode() : 0);
        hash = 23 * hash + (this.observacion != null ? this.observacion.hashCode() : 0);
        hash = 23 * hash + (this.fecha != null ? this.fecha.hashCode() : 0);
        hash = 23 * hash + (this.estado != null ? this.estado.hashCode() : 0);
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
        final RegistroMovimiento other = (RegistroMovimiento) obj;
        if ((this.observacion == null) ? (other.observacion != null) : !this.observacion.equals(other.observacion)) {
            return false;
        }
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.tipo != other.tipo && (this.tipo == null || !this.tipo.equals(other.tipo))) {
            return false;
        }
        if (this.fecha != other.fecha && (this.fecha == null || !this.fecha.equals(other.fecha))) {
            return false;
        }
        if (this.estado != other.estado && (this.estado == null || !this.estado.equals(other.estado))) {
            return false;
        }
        return true;
    }

    //</editor-fold>
}
