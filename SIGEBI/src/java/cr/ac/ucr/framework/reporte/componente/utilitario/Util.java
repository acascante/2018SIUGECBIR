/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.framework.reporte.componente.utilitario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * @author Adrián Zamora Villalta
 * @version 1.1
 * @since 07/07/2015
 * 
 * Clase utilitaria del sistema
 */
public class Util {

    public static String ConvertirRutas(String ruta) throws Exception {
        FacesContext tempFacesContext = FacesContext.getCurrentInstance();
        ServletContext tempServletContext = (ServletContext) tempFacesContext.getExternalContext().getContext();
        return tempServletContext.getRealPath(ruta);
    }

    public static String obtenerServlet() throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String urlPadre = facesContext.getExternalContext().getRequestContextPath();
        //***FMCreturn urlPadre + "/VisualizacionReporte";
        return "http://localhost:7001//FRAMEWORK//VisualizacionReporte";
    }

    /**
     * Método que retorna el bean del FacesContext.
     * Obtiene el valor del bean mediante los métodos específicos para <code>JSF 1.2</code>
     * @param nombre_bean Nombre del bean sin necesidad de escribirlo entre #{...}
     * @return Object con el bean.
     */
    public static Object getBeanDeContexto(String nombre_bean) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ValueExpression expression = app.getExpressionFactory().createValueExpression(facesContext.getELContext(),
                String.format("#{%s}", nombre_bean),
                Object.class);
        return expression.getValue(facesContext.getELContext());
    }

    public static TipoExportacion obtenerTipoExportar(int tipo_exportar) {
        for (TipoExportacion te : TipoExportacion.values()) {
            if (te.getId() == tipo_exportar) {
                return te;
            }
        }
        return TipoExportacion.PDF;
    }

    public static void ejecutarServlet(String ruta_servlet) throws MalformedURLException, IOException {

        //Se incluye el url del servlet del proyecto.
        URL url = new URL(ruta_servlet);
        //Se realiza la coneccion con el url.
        URLConnection con = url.openConnection();
        //Se habilita la entrada de datos al servlet
        //con.setDoOutput(true);
        //Se habilita la salida de datos al servlet
        //con.setDoInput(true);
        //Se indica que se habilitará la posibilidad de enviar objectos serializables
        //con.setRequestProperty("Content-Type", "application/x-java-serialized-object");

        //Se ingresan los parametros deseados
        /*ObjectOutputStream entrada = new ObjectOutputStream(con.getOutputStream());
        entrada.writeObject("");
        entrada.flush();
        entrada.close();*/

        //Se ejecuta el servlet para obtener valores deseados.
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        /*String decodedString;
        while ((decodedString = in.readLine()) != null) {
            System.out.println(decodedString);
        }*/
        in.close();   
    
    }
    
    public static void ejecutarServlet(String ruta_servlet, Map param) throws MalformedURLException, IOException {

        //Se incluye el url del servlet del proyecto.
        URL url = new URL(ruta_servlet);
        //Se realiza la coneccion con el url.
        URLConnection con = url.openConnection();
        //Se habilita la entrada de datos al servlet
        //***con.setDoOutput(true);
        //Se habilita la salida de datos al servlet
        //con.setDoInput(true);
        //Se indica que se habilitará la posibilidad de enviar objectos serializables
        con.setRequestProperty("Content-Type", "application/x-java-serialized-object");

        //Se ingresan los parametros deseados
        /***
        ObjectOutputStream entrada = new ObjectOutputStream(con.getOutputStream()); 
        entrada.writeObject(param); 
        entrada.flush(); 
        entrada.close();
         */

        //Se ejecuta el servlet para obtener valores deseados.
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        /*String decodedString;
        while ((decodedString = in.readLine()) != null) {
            System.out.println(decodedString);
        }*/
        in.close();   
    
    }
}
