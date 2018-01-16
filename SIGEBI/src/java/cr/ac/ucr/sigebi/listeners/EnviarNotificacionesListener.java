/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.listeners;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cr.ac.ucr.sigebi.jobs.EnviarNotificacionesJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author alvaro.cascante
 */
public class EnviarNotificacionesListener implements ServletContextListener {
    
    Scheduler scheduler = null;
    
    @Override
    public void contextInitialized(ServletContextEvent servletContext) {
        try {
            String cronExpression = servletContext.getServletContext().getInitParameter("enviarNotificacionesCronExpression");
            JobDetail job = newJob(EnviarNotificacionesJob.class).withIdentity("CronQuartzJob", "Group").build();
            Trigger trigger = newTrigger().withIdentity("TriggerName", "Group").withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(EnviarNotificacionesListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       try {
           scheduler.shutdown();
       } catch(SchedulerException e) {
       }
    }
}