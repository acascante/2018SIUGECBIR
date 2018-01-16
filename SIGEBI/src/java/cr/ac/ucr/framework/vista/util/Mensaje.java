/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cr.ac.ucr.framework.vista.util;

import cr.ac.ucr.framework.vista.VistaMensajes;

import java.util.ArrayList;
import java.util.List;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

/**
 *
 * @author piousp
 */
public class Mensaje {


    /**
     * Agrega un mensaje de error para mostrar de manera global en pantalla
     * Este mensaje es ROJO
     * @param mensaje String
     */
    public static void agregarErrorUsuario( String mensaje )
    {
        List<String> lista_mensajes = new ArrayList<String>();
        lista_mensajes.add(mensaje);
        VistaMensajes vista= (VistaMensajes) getBeanDeContexto("vistaMensajes");
        vista.setMensaje(lista_mensajes, VistaMensajes.TIPO.ERROR);
    }   //agregarErrorUsuario


    /**
     * Agrega un mensaje de error para mostrar de manera global en pantalla
     * Este mensaje es ROJO
     * @param mensaje String
     */
//    public static void agregarError( String mensaje )
//    {
//        List<String> lista_mensajes = new ArrayList<String>();
//        lista_mensajes.add(mensaje);
//        VistaMensajes vista= (VistaMensajes) getBeanDeContexto("vistaMensajes");
//        vista.setMensaje(lista_mensajes, VistaMensajes.TIPO.ERROR);
//    }   //agregarError

    /**
     * Agrega un mensaje de error para mostrar de manera global en pantalla
     * Este mensaje es ROJO
     * @param mensaje String
     */
    public static void agregarError(List<String> mensajes)
    {
        VistaMensajes vista= (VistaMensajes) getBeanDeContexto("vistaMensajes");
        vista.setMensaje(mensajes, VistaMensajes.TIPO.ERROR);
    }   //agregarError

    /**
     * Agrega un mensaje de error adecuado al modo de ejecución:
     * Si la aplicación está en modo debug, se muestra un popup con el mensaje
     * para el programador.
     * Si la aplicación NO está en modo debug, se muesta un mensaje de error
     * con el mensaje para el usuario.
     * @param e ExcepcionSICAD
     
    public static void agregarError(ExcepcionSICAD e)
    {
        if( !e.isDebug() )
        {
            agregarErrorUsuario(e.toString());
        }   //if
        else
        {
            agregarErrorFatal(e.toString());
        }   //else
    }   //agregarError
*/
    /**
     * Agrega un mensaje informativo para mostrar de manera global en pantalla
     * Este mensaje es verde.
     * @param mensaje String
     */
    public static void agregarInfo( String mensaje )
    {
        List<String> lista_mensajes = new ArrayList<String>();
        lista_mensajes.add(mensaje);
        VistaMensajes vista= (VistaMensajes) getBeanDeContexto("vistaMensajes");
        vista.setMensaje(lista_mensajes, VistaMensajes.TIPO.INFORMACION);
    }   //agregarInfo

     /**
     * Agrega un mensaje informativo para mostrar de manera global en pantalla
     * Este mensaje es verde.
     * @param mensaje String
     */
    public static void agregarInfo(List<String> mensajes)
    {
        VistaMensajes vista= (VistaMensajes) getBeanDeContexto("vistaMensajes");
        vista.setMensaje(mensajes, VistaMensajes.TIPO.INFORMACION);
    }   //agregarInfo

    /**
     * Agrega un mensaje de error a modo de advertencia para mostrar de manera global en pantalla
     * Este mensaje es amarillo.
     * @param mensaje
     */
    public static void agregarErrorAdvertencia( String mensaje ){
        List<String> lista_mensajes = new ArrayList<String>();
        lista_mensajes.add(mensaje);
        VistaMensajes vista= (VistaMensajes) getBeanDeContexto("vistaMensajes");
        vista.setMensaje(lista_mensajes, VistaMensajes.TIPO.ADVERTENCIA);
    }   //agregarErrorAdvertencia

    /**
     * Agrega un mensaje de error a modo de advertencia para mostrar de manera global en pantalla
     * Este mensaje es amarillo.
     * @param mensaje
     */
    public static void agregarErrorAdvertencia(List<String> mensajes){
        VistaMensajes vista= (VistaMensajes) getBeanDeContexto("vistaMensajes");
        vista.setMensaje(mensajes, VistaMensajes.TIPO.ADVERTENCIA);
    }   //agregarErrorAdvertencia

    /**
     * Agrega un mensaje de error fatal.
     * Este tipo de mensaje, levanta un popup y bloquea el IU.
     * Sólo debe usarce cuando ocurren excepciones inesperadas
     * @param mensaje String
     */
    public static void agregarErrorFatal( String mensaje ){
        List<String> lista_mensajes = new ArrayList<String>();
        lista_mensajes.add(mensaje);
        VistaMensajes vista= (VistaMensajes) getBeanDeContexto("vistaMensajes");
        vista.setMensajeFatal(lista_mensajes, VistaMensajes.TIPO.ERROR_FATAL);
    }   //agregarErrorFatal

    /**
     * Agrega un mensaje de error fatal.
     * Este tipo de mensaje, levanta un popup y bloquea el IU.
     * Sólo debe usarce cuando ocurren excepciones inesperadas
     * @param mensaje String
     */
    public static void agregarErrorFatal(List<String> mensajes){
        VistaMensajes vista= (VistaMensajes) getBeanDeContexto("vistaMensajes");
        vista.setMensajeFatal(mensajes, VistaMensajes.TIPO.ERROR_FATAL);
    }   //agregarErrorFatal

    /**
     * Método que retorna el bean del FacesContext.
     * Obtiene el valor del bean mediante los métodos específicos para <code>JSF 1.2</code>
     * @param nombre_bean Nombre del bean sin necesidad de escribirlo entre #{...}
     * @return Object con el bean.
     */
    private static Object getBeanDeContexto(String nombre_bean) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ValueExpression expression = app.getExpressionFactory().createValueExpression(facesContext.getELContext(),
                String.format("#{%s}", nombre_bean),
                Object.class);
        return expression.getValue(facesContext.getELContext());
    }

    public static void ocultarMensajes(){
      VistaMensajes vista= (VistaMensajes) getBeanDeContexto("vistaMensajes");
      vista.limpiarMensaje();
    }
}   //Mensaje