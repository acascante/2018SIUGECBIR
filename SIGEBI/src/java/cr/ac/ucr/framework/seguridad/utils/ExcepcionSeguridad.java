package cr.ac.ucr.framework.seguridad.utils;


/**
 * Es la excepción genérica de Sippres. Todas las excepciones especializadas debe de heredar de esta clase.
 * Basado en la clase ExcepcionGECO creada por Pious
 * @author Pious
 * @version 1.0 - Adam M. Gamboa González - 04/06/2010
 * @since 27/10/2010
 */
public final class ExcepcionSeguridad extends RuntimeException {

    private int codigo;                             //codigo para diferenciar una entre las excepciones capturadas
    private String error_para_usuario;              //Error para el usuario
    private String error_para_programador;          //Error para el programador
    private Throwable causa;                        //Causa del error
    private static volatile boolean debug = false;   //Manteganse en true mientras este en desarrollo, esto para poder obtener el error para el programador cuando se obtenga el getError()

//-----------------------------------------------------------------------------------------------------------------------------  Constructores
    public ExcepcionSeguridad() {
        this("Error general", "No se especificó un mensaje de error", 1);
    }   //constructor

    /**
     *
     * @param error_usuario String
     * @param error_programador String
     * @param codigo int
     * @since 27/10/2010
     */
    public ExcepcionSeguridad(String error_usuario, String error_programador, int codigo) {
        this(error_usuario, error_programador, codigo, null);
    }   //constructor

    public ExcepcionSeguridad(String error_para_usuario, String error_para_programador, Throwable causa) {
        this(error_para_usuario, error_para_programador, 0, causa);
    }

    /**
     *
     * @param error_usuario String
     * @param error_programador String
     * @param codigo int
     * @param causa Throwable
     * @since 27/10/2010
     */
    public ExcepcionSeguridad(String error_usuario, String error_programador, int codigo, Throwable causa) {
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
    public ExcepcionSeguridad(String error_usuario, String error_programador) {
        setError_para_usuario(error_usuario);
        setError_para_programador(error_programador);
    }   //constructor

    public ExcepcionSeguridad(Exception e) {
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
                return getError_para_programador() + " en posición: " + getCodigo() +
                        "\nCausa: " + getCausa().toString();
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

