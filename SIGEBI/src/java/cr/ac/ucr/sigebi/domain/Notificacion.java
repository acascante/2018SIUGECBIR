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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "Notificacion")
@Table(name = "SIGEBI_OAF.SIGB_NOTIFICACION")
@SequenceGenerator(name="sqNotificaciones", sequenceName = "SIGEBI_OAF.SGB_SQ_NOTIFICACIONES", initialValue=1, allocationSize=1)
public class Notificacion extends ObjetoBase implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sqNotificaciones")
    @Column(name = "ID_NOTIFICACION")
    private Long id;
    
    @Column(name = "ASUNTO")
    private String asunto;
    
    @Column(name = "MENSAJE")
    private String mensaje;
    
    @Column(name = "DESTINATARIO")
    private String destinatario;
    
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estado;
    
    @Column(name = "PRIORIDAD")
    private Integer prioridad;
    
    @Column(name = "FECHA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Notificacion() {}
    
    public Notificacion(Long id, String asunto, String mensaje, String destinatario, Estado estado, Integer prioridad, Date fecha) {
        this.id = id;
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinatario = destinatario;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fecha = fecha;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Notificacion)) {
            return false;
        }
        return this.id.equals(((Notificacion)object).id);
    }

    @Override
    public String toString() {
        return "entidades.Notificacion[id=" + this.id + "]";
    }
    //</editor-fold>
}