package cr.ac.ucr.framework.vista.util;

import cr.ac.ucr.framework.seguridad.entidades.SegElemento;
import cr.ac.ucr.framework.seguridad.entidades.SegUnidadEjecutora;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.VistaNavegacion;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;


public class Util {

    private static Util INSTANCE = null;
    private static String sistema = "SIGEBI";
    private static String actualPant = "principal";
    private static String anteriorPant = "login";

    // Private constructor suppresses
    private Util() {
    }

    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciación múltiple
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Util();
        }
    }

    public static Util getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    /**
     * Get parameter value from request scope.
     *
     * @param name the name of the parameter
     * @return the parameter value
     */
    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    public static void agregarErrorLog(String descripcion, Exception e) {
//        Log log = LogFactory.getLog("");
//        log.error(descripcion, e);
    }

    /**
     * Método que me permite pasar de un lista de cualquier tipo,
     * a una lista de select item
     * @param objetoBase - Lista base donde vienen los objetos que deseo tranformar a tipo select item.
     * @param parametro_value - variable del componente base que se desea usar como value en el select item.
     * @param parametro_label - variable del componente base que se desar usar como label en el select item.
     * @param concatenar - indicador para saber si si debe concatenar el value con el label o no ejm: 000031 Informática
     * @return una lista de igual cantidad de campos para select item.
     * @throws Exception Si los parametros que se pasan no estan el la clase o el accesor de estos no tiene el nombre correcto,
     * se devuelve una excepción.
     */
    public static List<SelectItem> parseToSelectItem(List objetoBase, String parametro_value, String parametro_label, boolean concatenar) throws FWExcepcion {
        parametro_value = "get" + ("" + parametro_value.charAt(0)).toUpperCase() + parametro_value.substring(1);
        parametro_label = "get" + ("" + parametro_label.charAt(0)).toUpperCase() + parametro_label.substring(1);

        ArrayList<SelectItem> items = new ArrayList<SelectItem>();
        for (Object act : objetoBase) {
            Class<?> c = act.getClass();

            String value;
            try {
                value = c.getMethod(parametro_value).invoke(act).toString();

                String label = c.getMethod(parametro_label).invoke(act).toString();

                if (concatenar) {
                    label = value + "    " + label;
                }
                items.add(new SelectItem(value, label));
            } catch (Exception e) {
                throw new FWExcepcion(e);
            }
        }//fin del for
        return items;
    } //fin parseToSelectItem

    /**
     * Método que me permite pasar de un lista de cualquier tipo,
     * a una lista de select item
     * @param objetoBase - Lista base donde vienen los objetos que deseo tranformar a tipo select item.
     * @param parametro_value - variable del componente base que se desea usar como value en el select item.
     * @param parametro_label - variable del componente base que se desar usar como label en el select item.
     * @param concatenar - indicador para saber si si debe concatenar el value con el label o no ejm: 000031 Informática
     * @return una lista de igual cantidad de campos para select item.
     * @throws Exception Si los parametros que se pasan no estan el la clase o el accesor de estos no tiene el nombre correcto,
     * se devuelve una excepción.
     */
    public static List<SelectItem> parseToSelectItem(List<SelectItem> items, List objetoBase, String parametro_value, String parametro_label, boolean concatenar) throws FWExcepcion {
        parametro_value = "get" + ("" + parametro_value.charAt(0)).toUpperCase() + parametro_value.substring(1);
        parametro_label = "get" + ("" + parametro_label.charAt(0)).toUpperCase() + parametro_label.substring(1);

        for (Object act : objetoBase) {
            Class<?> c = act.getClass();

            String value;
            try {
                value = c.getMethod(parametro_value).invoke(act).toString();

                String label = c.getMethod(parametro_label).invoke(act).toString();

                if (concatenar) {
                    label = value + "    " + label;
                }
                items.add(new SelectItem(value, label));
            } catch (Exception e) {
                throw new FWExcepcion(e);
            }
        }//fin del for
        return items;
    } //fin parseToSelectItem

    /**
     * establece el valor de cualquier componente de html a traves del nombre y el key_componente, el valor por param
     * @param nombre nombre del componente
     * @param focus setea el focus al componente si es necesario
     * @param param valor que desea insertar
     */
    public static void setValorComponente(String nombre, String key_componente, boolean focus, Object... param) {
        FacesContext.getCurrentInstance().getViewRoot().findComponent(nombre).getAttributes().put(key_componente, param[0]);
        if (focus) {
            setfocusComponente(nombre);
        }
    }

    /**
     * Establece el focus al componente por medio del id
     * @param nombre id del componente
     */
    public static void setfocusComponente(String nombre) {
    }
    /*obtener valor del archivo de etiquetas_es*/

    public static synchronized String getEtiquetas(String etiqueta) {
        ResourceBundle resources;
        String bundle = "cr.ac.ucr.framework.vista.util.etiquetas_es";
        try {
            resources = ResourceBundle.getBundle(bundle);
        } catch (MissingResourceException x) {

            throw new InternalError(x.getMessage());
        }
        return resources.getString(etiqueta);
    }

    /**
     * devuelve una fecha segun el patron que se le envie
     * @param patron
     * @param fecha
     * @return
     */
    public static String obtenerFecha(String patron, Calendar fecha) {
        String output;
        String pattern = patron;
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat(pattern, new Locale("es", "CR"));
        output = formatter.format(fecha.getTime());
        return output;
    }

    /**
     * devuelve la vista que desea
     * @param expr nombre de la vista
     * @return
     */
//    public static Object obtenerVista(String expr) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        ValueBinding binding =
//                context.getApplication().createValueBinding(expr);
//        return binding.getValue(context);
//    }
    
    public static Object obtenerVista(String expr) {
        ELContext context = FacesContext.getCurrentInstance().getELContext();
        ExpressionFactory ef = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
        ValueExpression ve = ef.createValueExpression(context, expr, Object.class);
        return ve.getValue(context);
    }

    /**
     * remueve la vista de la sesión
     * @param vista_nombre
     */
    public static void removerVistaSesion(String vista_nombre) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove(vista_nombre);
    }

    /**
     * remueve la vista de la sesión
     * @param vista_nombre
     */
    public static void removerVistasSesion() {
        FacesContext context = FacesContext.getCurrentInstance();
        Iterator variablesSession = context.getExternalContext().getSessionMap().values().iterator();
        while (variablesSession.hasNext()) {
            String variable = variablesSession.next().toString();
            if (variable.contains("vista")) {
                if (!variable.contains("VistaNavegacion")
                        && !variable.contains("VistaMenu")
                        && !variable.contains("VistaUsuario")) {
                    variablesSession.remove();
                }

            }
        }
        agregarVariableSession(
                Util.getEtiquetas("sesion.id.malla.modificada"), null);
        /*for(int i = 0; i < context.getExternalContext().getSessionMap().size(); i++){
        if(context.getExternalContext().getSessionMap().){
        remove("vistaPropuesta");
        }
        }*/

    }

    /**
     * Devuelve la fecha actual del sistema.
     * @return java.util.Date con la fecha actual
     * @since 17/08/2009
     */
    public static Date getFechaActual() {
        return GregorianCalendar.getInstance(new Locale("es", "CR")).getTime();
    } //getFechaActual

    public static String getFechaLetras(Date fechaDate){
        SimpleDateFormat formateador = new SimpleDateFormat(
        "dd 'de' MMMM 'de' yyyy", new Locale("es", "CR"));
        String fecha = formateador.format(fechaDate);
        return fecha;
    }
    
    public static String getSistema() {
        return sistema;
    }
    
    /*
    public static void cambiarLocalidad(String localidad) {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(localidad));
    }*/

    public static void navegar(String direccion) {
        anteriorPant = actualPant;
        actualPant = direccion;
        
        VistaNavegacion navegar = (VistaNavegacion) obtenerVista("#{vistaNavegacion}");
        navegar.navegacionBotones(direccion);
    }

    public static void navegarRegresar() {
        VistaNavegacion navegar = (VistaNavegacion) obtenerVista("#{vistaNavegacion}");
        navegar.navegacionBotones( navegar.getgPaginaAnterior() );
    }
    public static void navegar(String direccion, boolean limpiarVistas) {
        if (limpiarVistas) {
            removerVistasSesion();
        }
        anteriorPant = actualPant;
        actualPant = direccion;
        
        VistaNavegacion navegar = (VistaNavegacion) obtenerVista("#{vistaNavegacion}");
        navegar.navegacionBotones(direccion);
    }

    /**
     * Agrega una variable a sesion
     * @param pNombre Nombre de la variable
     * @param pValor Valor de la variable
     */
    public static void agregarVariableSession(String pNombre, Object pValor) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //Con esto obtenemos la request
        HttpServletRequest request;
        request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        //Acceso a la sesión http
        HttpSession session = request.getSession();
        session.setAttribute(pNombre, pValor);
    }

    /**
     * Obtener una variable a sesion
     * @param pNombre Nombre de la variable
     */
    public static Object obtenerVariableSession(String pNombre) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //Con esto obtenemos la request
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        //Acceso a la sesión http
        HttpSession session = request.getSession();
        return session.getAttribute(pNombre);
    }

    /**
     * Ordena una lista de elementos, segun su numero de orden.
     * @param lista_elementos lista de elementos a ordenar
     * @return Lista de elementos ordenada
     */
    public static List<SegElemento> ordenarElementos(List<SegElemento> lista_elementos) {
        Collections.sort(lista_elementos, new Comparator() {

            public int compare(Object obj1, Object obj2) {
                Integer orden1 = ((SegElemento) obj1).getNumOrden();
                Integer orden2 = ((SegElemento) obj2).getNumOrden();

                if ((orden1 != null) && (orden2 != null)) {
                    return orden1.compareTo(orden2);
                } else {
                    return 1;
                }
            }
        });
        return lista_elementos;
    }

    /**
     *Obtiene el valor de habilitado para el estado de la fila que se va a editar
     * segun el valor de habilitado de la lista de registros
     * @return 
     */
    public static Long obtenerIdEstado(boolean estado) {
        if (estado == true) {
            return new Long(1);
        } else {
            return new Long(2);
        }
    }

    /**
     *Obtiene el valor de habilitado para el estado de la fila que se va a editar
     * segun el valor de habilitado de la lista de registros
     * @return 
     */
    public static boolean castEstado(Long estado) {
        if (estado == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Convierte un número BigDecimal a Integer
     * @param pNumero Número en BigDecimal
     * @return numero Integer
     */
    public static Integer convertirBigDecimalAInteger(BigDecimal pNumero) {
        return Integer.valueOf(pNumero.toPlainString());
    }

    /**
     * Convierte un número BigDecimal a Long
     * @param pNumero Número en BigDecimal
     * @return numero Integer
     */
    public static Long convertirBigDecimalALong(BigDecimal pNumero) {
        return Long.valueOf(pNumero.toPlainString());
    }

    /**
     * Ordena la lista de las unidades ejecutoras en orden alfabetico segun la descripcion o nombre de estas.
     * @param lista_unidades Lista de unidades a ordenar
     * @return Lista de Unidades Ejecutoras ordenadas.
     */
    public static List<SegUnidadEjecutora> ordenarUnidadesEjecutoras(List<SegUnidadEjecutora> lista_unidades) {
        Collections.sort(lista_unidades, new Comparator<SegUnidadEjecutora>() {

            public int compare(SegUnidadEjecutora obj1, SegUnidadEjecutora obj2) {
                String orden1 = obj1.getDscUnidadEjecutora();
                String orden2 = obj2.getDscUnidadEjecutora();

                if ((orden1 != null) && (orden2 != null)) {
                    return orden1.compareTo(orden2);
                } else {
                    return 1;
                }
            }
        });
        return lista_unidades;
    }
    
    //Carga la lista de annos para su modificacion
    public static List<SelectItem> cargarAnnosModificacion(){
        List<SelectItem> lista = new ArrayList<SelectItem>();
        
//        Date d = new Date();
//        Integer i = (d.getYear() + 1900);
        Calendar lCalendario = Calendar.getInstance();
        Integer i = new Integer(lCalendario.get(Calendar.YEAR));
        SelectItem si = new SelectItem();
        si.setLabel(String.format("%s",i));
        si.setValue(i);
        lista.add(si);
        for(int j = 0; j<5; j++){
            si = new SelectItem();
            i+=1;
            si.setLabel(String.format("%s",i));
            si.setValue(i);
            lista.add(si);
        }
        return lista;    
    }
    
    //Carga la lista de annos para su modificacion
    public static List<SelectItem> cargarAnnosModificacion(Integer anno){
        List<SelectItem> lista = new ArrayList<SelectItem>();
        Integer i = anno;
        SelectItem si = new SelectItem();
        si.setLabel(String.format("%s",i));
        si.setValue(i);
        lista.add(si);
        for(int j = 0; j<5; j++){
            si = new SelectItem();
            i+=1;
            si.setLabel(String.format("%s",i));
            si.setValue(i);
            lista.add(si);
        }
        return lista;    
    }

    public static String getActualPant() {
        return actualPant;
    }

    public static void setActualPant(String actualPant) {
        Util.actualPant = actualPant;
    }

    public static String getAnteriorPant() {
        return anteriorPant;
    }

    public static void setAnteriorPant(String anteriorPant) {
        Util.anteriorPant = anteriorPant;
    }
    
    
    
    
}
