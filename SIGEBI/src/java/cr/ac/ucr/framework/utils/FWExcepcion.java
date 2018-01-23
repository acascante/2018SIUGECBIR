/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.framework.utils;

import cr.ac.ucr.framework.seguridad.utils.UTF8;
import cr.ac.ucr.framework.vista.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public final class FWExcepcion extends RuntimeException {

    private int codigo;                             //codigo para diferenciar una entre las excepciones capturadas
    private String error_para_usuario;              //Error para el usuario
    private String error_para_programador;          //Error para el programador
    private Throwable causa;                        //Causa del error
    private static volatile boolean debug = true;   //Manteganse en true mientras este en desarrollo, esto para poder obtener el error para el programador cuando se obtenga el getError()
    private Log log; //variable que me va a permitir imprimir en el log del sistema

//-----------------------------------------------------------------------------------------------------------------------------  Constructores
    public FWExcepcion() {
        this("Error general", "No se especificó un mensaje de error", 1);
        this.printStackTrace();

    }   //constructor

    /**
     *
     * @param error_usuario String
     * @param error_programador String
     * @param codigo int
     * @since 27/10/2010
     */
    public FWExcepcion(final String error_usuario, final String error_programador, final int codigo) {
        this(Util.getEtiquetas(error_usuario), error_programador, codigo, null);
        this.printStackTrace();
    }   //constructor

    public FWExcepcion(final String error_para_usuario, final String error_para_programador, final Throwable causa) {
        this(Util.getEtiquetas(error_para_usuario), error_para_programador, 0, causa);
        this.printStackTrace();
    }

    /**
     *
     * @param error_usuario  error_usuario String
     * @param error_programador String
     * @param codigo int
     * @param causa Throwable
     * @since 27/10/2010
     */
    public FWExcepcion(final String error_usuario, final String error_programador, final int codigo, final Throwable causa) {
        setError_para_usuario(error_usuario);
        setError_para_programador(error_programador);
        setCausa(causa);
        setCodigo(codigo);
    }   //constructor

    /**
     *
     * @param error_usuario String
     * @param error_programador String
     * @since 27/10/2010
     */
    public FWExcepcion(final String error_usuario, final String error_programador) {
        setError_para_usuario(error_usuario);
        setError_para_programador(error_programador);
    }   //constructor

    /**
     *
     * @param error_usuario String
     * @since 31/10/2011
     */
    public FWExcepcion(final String error_usuario) {
        setError_para_usuario(error_usuario);
    }//constructor

    public FWExcepcion(Exception e) {
        setCausa(e.getCause());
    }

    @Override
    public String toString() {
        return getError();
    }   //toString

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    public void imprimirError(Class<?> entidad) {
        log = LogFactory.getLog(entidad.getClass());
        log.error(getError());
        Logger.getLogger(entidad.getClass().getName()).log(Level.SEVERE, null, getError());
    }//fin metodo imprimir

    //-----------------------------------------------------------------------------------------------------------------------------------  Sets y gets
    public void setError_para_usuario(String error_para_usuario) {
        this.error_para_usuario = UTF8.aUTF8(error_para_usuario);
    }

    public String getError_para_usuario() {
        return error_para_usuario;
    }

    public void setError_para_programador(String error_para_programador) {
        this.error_para_programador = error_para_programador;
    }

    public String getError_para_programador() {
        return error_para_programador;
    }

    public void setCausa(Throwable causa) {
        this.causa = causa;
    }

    public Throwable getCausa() {
        return causa;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getError() {
        if (debug) {
            if (!(causa == null)) {
                return getError_para_programador() + " en posición: " + getCodigo()
                        + "\nCausa: " + getCausa().toString();
            } else {
                return getError_para_programador();
            }   //else

        } else {
            return getError_para_usuario();
        }   //else
    }   //getError

    public void setError(String error) {
        if (debug) {
            error_para_programador = error;
        } else {
            error_para_usuario = error;
        }

    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
} //ExcepcionControl
