/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.NotificacionDao;
import cr.ac.ucr.sigebi.domain.Notificacion;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service(value = "notificacionModel")
@Scope("request")
public class NotificacionModel {
    
    public static final Log LOG = LogFactory.getLog(NotificacionModel.class);
    
    @Value("${sigebi.notificacion.correo.from}")
    private String correoFrom;
    
    @Value("${sigebi.notificacion.correo.user.password}")
    private String password;
    
    @Value("${sigebi.notificacion.correo.hostSMTP}")
    private String hostSMPT;
    
    @Value("${sigebi.notificacion.correo.puertoSMTP}")
    private String puertoSMPT;
    
    @Value("${sigebi.notificacion.correo.hostPOP3}")
    private String hostPOP3;
    
    @Value("${sigebi.notificacion.correo.puertoPOP3}")
    private String puertoPOP3;
    
    @Value("${sigebi.notificacion.correo.starttls}")
    private String startTLS;
    
    @Value("${sigebi.notificacion.correo.auth}")
    private String auth;
    
    @Value("${sigebi.notificacion.correo.content}")
    private String content;
    
    @Resource
    private NotificacionDao notificacionDao;
    
    public List<Notificacion> listar(Integer primerRegistro, Integer ultimoRegistro, Long id, String asunto, String destinatario, String mensaje, Date fecha, Integer estado) throws FWExcepcion {
        return notificacionDao.listar(primerRegistro, ultimoRegistro, id, asunto, destinatario, mensaje, fecha, estado);
    }
    
    public List<Notificacion> listar(Date fecha, String dominio, Integer... estados) throws FWExcepcion {
        return notificacionDao.listar(fecha, dominio, estados);
    }
    
    public Long contar(Long id, String asunto, String destinatario, String mensaje, Date fecha, Integer estado) throws FWExcepcion {
        return notificacionDao.contar(id, asunto, destinatario, mensaje, fecha, estado);
    }
    
    public void salvar(Notificacion notificacion) throws FWExcepcion {
        notificacionDao.salvar(notificacion);
    }
    
    public void enviarCorreo(Notificacion notificacion) throws FWExcepcion {
        try {
            LOG.info("-- ID: " + notificacion.getIdNotificacion() + " -- Correo: " + notificacion.getDestinatario());
            String to = notificacion.getDestinatario();
            
            Properties properties = new Properties();
            properties.put("mail.smtp.host", hostSMPT);
            properties.put("mail.smtp.port", puertoSMPT);
            properties.put("mail.smtp.starttls.enable", startTLS);
            properties.put("mail.smtp.auth", auth);
            properties.put("mail.user", correoFrom);
            properties.put("mail.password", password);
            
            Session session;
            session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correoFrom, password);
                }
            });
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoFrom));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(notificacion.getAsunto());
            message.setContent(notificacion.getMensaje(), content);

            Transport.send(message);
        } catch (Exception e) {
            System.err.print(e.getCause());
            throw new FWExcepcion("sigebi.error.controllerListarNotificaciones.enviarNotificacion", "Error al enviar notificacion" + this.getClass(), e.getCause());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public String getCorreoFrom() {
        return correoFrom;
    }

    public void setCorreoFrom(String correoFrom) {
        this.correoFrom = correoFrom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostSMPT() {
        return hostSMPT;
    }

    public void setHostSMPT(String hostSMPT) {
        this.hostSMPT = hostSMPT;
    }

    public String getPuertoSMPT() {
        return puertoSMPT;
    }

    public void setPuertoSMPT(String puertoSMPT) {
        this.puertoSMPT = puertoSMPT;
    }

    public String getHostPOP3() {
        return hostPOP3;
    }

    public void setHostPOP3(String hostPOP3) {
        this.hostPOP3 = hostPOP3;
    }

    public String getPuertoPOP3() {
        return puertoPOP3;
    }

    public void setPuertoPOP3(String puertoPOP3) {
        this.puertoPOP3 = puertoPOP3;
    }
    //</editor-fold>
}