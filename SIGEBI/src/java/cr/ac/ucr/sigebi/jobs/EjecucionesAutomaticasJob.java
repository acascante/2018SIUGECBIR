/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.jobs;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.models.NotificacionModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author alvaro.cascante
 */
public class EjecucionesAutomaticasJob {
    
    public static final Log LOG = LogFactory.getLog(EjecucionesAutomaticasJob.class);
    public static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    
    @Resource 
    private NotificacionModel notificacionModel;
    
    public void envioNotificaciones()  {
        try {
            LOG.info("Inicio proceso de envio automatico de notificaciones " + SDF.format(new Date()));
            notificacionModel.enviarNotificaciones();
        } catch (FWExcepcion err) {
            LOG.error("Error al enviar notificaciones automaticas: " + err.getMessage());
        } finally {
            LOG.info("Fin proceso de envio automatico de notificaciones " + SDF.format(new Date()));
        }
    }
    
}
