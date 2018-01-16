/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.framework.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Clase que maneja el acceso de los usuarios al sistema
 * @version 1.1, 1/11/2011
 * @author Jonnathan, modificado por Guillermo Torres
 */
public class ApplicationContextProvider implements ApplicationContextAware {
    
    public void setApplicationContext(ApplicationContext ctx) throws BeansException{
        AppContext.setApplicationContext(ctx);
    }
}
