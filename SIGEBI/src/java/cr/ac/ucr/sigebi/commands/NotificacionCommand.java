/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.domain.Notificacion;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author alvaro.cascante
 */
public class NotificacionCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long idNotificacion;
    private String asunto;
    private String mensajeCorreo;
    private String destinatario;
    private Estado estado;
    private Integer prioridad;
    private Date fecha;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public NotificacionCommand() {
        super();
        this.fecha = getDefaultDate();
        this.prioridad = Constantes.PRIORIDAD_NOTIFICACION_NORMAL;
    }

    public NotificacionCommand(Notificacion notificacion) {
        this.idNotificacion = notificacion.getIdNotificacion();
        this.asunto = notificacion.getAsunto();
        this.mensajeCorreo = notificacion.getMensaje();
        this.destinatario = notificacion.getDestinatario();
        this.estado = notificacion.getEstado();
        this.prioridad = notificacion.getPrioridad();
        this.fecha = notificacion.getFecha();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public Notificacion getNotificacion() {
        Notificacion notificacion = new Notificacion();
        notificacion.setAsunto(this.asunto);
        notificacion.setDestinatario(this.destinatario);
        notificacion.setMensaje(this.mensajeCorreo);
        notificacion.setEstado(this.estado);
        notificacion.setPrioridad(this.prioridad);
        notificacion.setFecha(this.fecha);
        return notificacion;
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    private Date getDefaultDate() {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance(Constantes.DEFAULT_TIME_ZONE);
        calendar.setTime(today);
        return calendar.getTime();
    }
    
    public TimeZone getTimeZone() {
        return Constantes.DEFAULT_TIME_ZONE;
    }
    
    public String getDatePattern() {
        return Constantes.DEFAULT_DATE_PATTERN;
    }
    
    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensajeCorreo() {
        return mensajeCorreo;
    }

    public void setMensajeCorreo(String mensajeCorreo) {
        this.mensajeCorreo = mensajeCorreo;
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
}