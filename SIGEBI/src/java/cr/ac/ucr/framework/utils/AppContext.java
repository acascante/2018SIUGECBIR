/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.framework.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;


/**
 * Clase que maneja el acceso de los usuarios al sistema
 * @version 1.1, 1/11/2011
 * @author Jonnathan, modificado por Guillermo Torres
 */
public class AppContext {
    private static ApplicationContext ctx;  
    /** 
     * Injected from the class "ApplicationContextProvider" which is automatically 
     * loaded during Spring-Initialization. 
     */  
    public static void setApplicationContext(ApplicationContext applicationContext) {  
        ctx = applicationContext;  
    }  
  
    /** 
     * Get access to the Spring ApplicationContext from everywhere in your Application. 
     * Obtener acceso al applicationContext de Spring de cualquier lugar de la aplicacion
     * @return 
     */  
    public static ApplicationContext getApplicationContext() {  
        return ctx;  
    }  
    public static void closeSession(){
        AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext)ctx;
        abstractApplicationContext.refresh();
    }
}
