/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.jobs;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Notificacion;
import cr.ac.ucr.sigebi.models.NotificacionModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author alvaro.cascante
 */
public class EnviarNotificacionesJob implements Job {
    
    public static final Log LOG = LogFactory.getLog(EnviarNotificacionesJob.class);
    public static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    
    @Resource
    private NotificacionModel notificacionModel;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            LOG.info("Inicio proceso de envio automatico de notificaciones " + SDF.format(new Date()));
            Calendar calendar = Calendar.getInstance();
            List<Notificacion> notificaciones = notificacionModel.listar(calendar.getTime(), Constantes.DOMINI0_NOTIFICACION, Constantes.ESTADO_NOTIFICACION_CREADA, Constantes.ESTADO_NOTIFICACION_ENVIO_FALLIDO);
            for (Notificacion notificacion : notificaciones) {
                notificacionModel.enviarCorreo(notificacion);
                LOG.info("-- ID: " + notificacion.getIdNotificacion() + " -- Correo: " + notificacion.getDestinatario());
            }
        } catch (FWExcepcion err) {
            LOG.error("Error al enviar notificaciones automaticas: " + err.getMessage());
        } finally {
            LOG.info("Fin proceso de envio automatico de notificaciones " + SDF.format(new Date()));
        }
    }
}
