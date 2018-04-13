/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.framework.vista;

import com.icesoft.faces.context.effects.JavascriptContext;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import cr.ac.ucr.framework.seguridad.entidades.SegElemento;
import cr.ac.ucr.framework.seguridad.entidades.SegUnidadEjecutora;
import cr.ac.ucr.framework.service.SeguridadMgr;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.MenuItemPrincipal;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller(value = "vistaMenu")
@Scope("session")
public class VistaMenu {
    
    // <editor-fold defaultstate="collapsed" desc="Atributos">
    //Permite conectarme con el manager de seguridad

    @Resource
    private SeguridadMgr gSeguridadMgr;
    @Resource
    private UnidadEjecutoraModel unidadEjecutoraModel;
    
    //variable para armar el menú horizontal que le aparecera al usuario
    private List<MenuItemPrincipal> gMenuHorizontal;
    /**
     * si es true es porque tiene mas de un perfil y tiene que mostrarse un
     * popup para que el usuario elija entre los diferentes de ellos.
     */
    private boolean gMostrarPerfiles;
    //Elemento que guarda los datos del menú dependiendo del tipo de formulación.
    private List<MenuItemPrincipal> gMenuTipoFormulacion;
    //fecha del sistema que se le muestra al usuario en el menú
    private Date gFechaSistema;
    //Para dar formato a la fecha del sistema
    private TimeZone zona;
    //se utiliza para saber que menú se esta trabajando si el principal, propuesta o  evaluación.
    private String gEtiquetaSeleccionMenu;
    /**
     * variable que se utiliza para cambiar de la plantilla base index a la
     * plantilla general del sistema
     */
    private String gNavegarInicio;
    //si esta falso el menu principal es el que esta activo si esta en true el que se encuentra activo es el de formulacion /evaluacion
    private boolean gMenuSeleccionado;
    public static String INTEGRAL = "MODULO_SIGECU_MODIFICACION_INTEGRAL";
    public static String PARCIAL = "MODULO_SIGECU_MODIFICACION_PARCIAL";
    public static String CREACION_CARR = "MODULO_SIGECU_CREACION_CARRERA";
    public static String GENERAL = "MODULO_SIGECU_GENERAL";
    public static String MENU_PRIN = "MENU_PRINCIPAL";
    public static String MENU_PROP = "MENU_PROPUESTA";
    public static String ADMINISTRATIVA_INTEGRAL = "MODULO_SIGECU_ADMINISTRATIVA_M_I";
    public static String ADMINISTRATIVA_PARCIAL = "MODULO_SIGECU_ADMINISTRATIVA_M_P";
    public static String ADMINISTRATIVA_CREACION_CARR = "MODULO_SIGECU_ADMINISTRATIVA_C_C";
    private HashMap<String, List<String>> perfilesUnidad;
//    	parametroMgr.findParametroById(MENU_PROPUESTA)
//	parametroMgr.findParametroById(MENU_PROP)
//MODULO_SIGECU_MODIFICACION_INTEGRAL	2036
//MODULO_SIGECU_CREACION_CARRERA	2037	
//MODULO_SIGECU_MODIFICACION_PARCIAL	2035	
//MODULO_SIGECU_GENERAL	2034

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos"> 
    /**
     * inicializa las variables de la vista
     */
    @PostConstruct
    private void inicializar() {
        gMenuHorizontal = new ArrayList<MenuItemPrincipal>();
        gMenuTipoFormulacion = new ArrayList<MenuItemPrincipal>();
        gMostrarPerfiles = false;
        gEtiquetaSeleccionMenu = Util.getEtiquetas("label.menu.mostrarMenuPrincipal");
        gFechaSistema = new Date();
        cargarPerfilesParaUnidades();
    }

    /**
     * Método que se encarga de llenar el hashMap perfilesUnidad con el cual se valida
     * a que perfiles tiene acceso cada unidad ejecutara en el sistema, este
     * metodo se carga basado en una selección previa de perfiles por unidad, y
     * parametros creados para indicar esos perfiles y unidades
     *
     * @Autor Guillermo Torres H. 09-08-2012
     */
    public void cargarPerfilesParaUnidades() {
        //inicializo el hashMap
        perfilesUnidad = new HashMap<String, List<String>>();
        //Datos quemados para ingreso al SIIAGC
        String idPerfil = "50737";
        List<String> unidadesEjec = new ArrayList<String>();
        unidadesEjec.add("UCR");
        perfilesUnidad.put(idPerfil, unidadesEjec);
        /**
         * *************************************************
         */
        //Comienzo a cargar los perfiles
        //Perfil de formulador
        /*String idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-FORMULADOR").getValorParametro();
        List<String> unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_TIPO_UNIDAD-ESCUELA").getValorParametro());
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_TIPO_UNIDAD-COORDINACION_DOCENCIA_S_R").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
        //Perfil de decanatura
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-DECANATURA").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_TIPO_UNIDAD-DIRECCION_SEDE_REGIONAL").getValorParametro());
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_TIPO_UNIDAD-FACULTAD").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
        //Perfil de Comision Interdiciplinaria
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-COM_INTER").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_TIPO_UNIDAD-DIRECCION_SEDE_REGIONAL").getValorParametro());
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_TIPO_UNIDAD-FACULTAD").getValorParametro());
        unidadesEjec.add(parametroMgr.findParametroById("BUZON_TIPO_UNIDAD_INT").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/


        //Perfil de vicerrectoria de docencia
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-VICERRECTORIA").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_UNIDAD_EJECUTORA-VD").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
        //Perfil de CEA
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-CEA").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_UNIDAD_EJECUTORA-CEA").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
        //Perfil de JEFATURA
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-JEFATURA_DIEA").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_UNIDAD_EJECUTORA-DIEA").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
        //Perfil de EVALUADOR
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-EVALUADOR").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_UNIDAD_EJECUTORA-DIEA").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
        //Perfil de aplicador de SAE
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-APLICADORSAE").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_UNIDAD_EJECUTORA-DIEA").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
        //Perfil de COVO
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-COVO").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_UNIDAD_EJECUTORA-COVO").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
        //Perfil de ORI
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-ORI").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_UNIDAD_EJECUTORA-ORI").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
        //Perfil de consejo Universitario
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-CONSEJOUNI").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_UNIDAD_EJECUTORA-CU").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
        //Perfil de adminitrador SIGECU
        /*idPerfil = parametroMgr.findParametroById("SEGURIDAD_PERFIL-ADM").getValorParametro();
        unidadesEjec = new ArrayList<String>();
        unidadesEjec.add(parametroMgr.findParametroById("SEGURIDAD_UNIDAD_EJECUTORA-DIEA").getValorParametro());
        perfilesUnidad.put(idPerfil, unidadesEjec);*/
    }//fin del método

    /**
     * Método que se encarga de validar que el usuario tiene acceso al sistema
     * con el perfil seleccionado y la unidad que tiene, para eso revisa en el
     * hashmap perfilesUnidad con el perfil que pasa por parametro, obtiene la
     * lista correspondiente y revisa si son letras el tipo de unidad, si viene
     * el un código el id de unidad
     *
     * @param pPerfilSeleccionado - perfil seleccionado
     * @param pTipoUnidad - Tipo de unidad del usuario
     * @param pIdUnidadEjecut - id de la unidad ejecutora con la q se loguea el
     * usuario
     * @return true si el usuario tiene acceso al sistema o false de lo
     * contrario
     * @since 10-08-2012 Guillermo Torres.
     */
    public boolean validarPerfilUnidadEjecutora(String pPerfilSeleccionado,
            String pTipoUnidad, String pIdUnidadEjecut) {
        List<String> unidades = perfilesUnidad.get(pPerfilSeleccionado);
        boolean acceso = false;
        try {
            //reviso q tiene la lista de unidades si string o integer
            //osea si el tipo de unidad o el id de la unidad ejecutora
            Integer.parseInt(unidades.get(0));
            acceso = unidades.contains(pIdUnidadEjecut);
        } catch (NumberFormatException nfe) {
            //si entra acá es un string y lo comparo con el tipo de unidad
            acceso = unidades.contains(pTipoUnidad);
        }

        return acceso;
    }//fin del metodo

    /**
     * Metodo encargado de cargar el menu_horizontal de opciones si no tiene mas
     * de un perfil asociado, basandose en Elementos de menu_horizontal, que
     * tiene asociados el usuario_actual para el sistema que se va a encargar de
     * administrar.
     */
    public void cargarMenu(ActionEvent event) {
        gMenuHorizontal.clear();
        VistaUsuario lVistaUsuario = (VistaUsuario) Util.obtenerVista("#{vistaUsuario}");

        if (!lVistaUsuario.obtenerPerfilesUsuario()) {//si solo tiene un perfil asignado entra
            List<SegElemento> lista_elementos = new ArrayList<SegElemento>();
            try {
                /*if (!validarPerfilUnidadEjecutora(lVistaUsuario.getgPerfilSeleccionado(),
                        lVistaUsuario.getgUnidadActual().getIdTipoUnidad(),
                        "" + lVistaUsuario.getgUnidadActual().
                        getUnidadEjecutoraLlave().getIdUnidadEjecutora())) {
                    Mensaje.agregarErrorUsuario(Util.getEtiquetas("error.usuario.perfil.invalido"));
                    return;
                }*/

                /*int[] tipos = {Integer.parseInt(parametroMgr.findParametroById(MENU_PRIN).getValorParametro().toString()), Integer.parseInt(parametroMgr.findParametroById(MENU_PRIN).getValorParametro().toString())};
                int[] tipoModulo = {Integer.parseInt(parametroMgr.findParametroById(GENERAL).getValorParametro().toString()), Integer.parseInt(parametroMgr.findParametroById(GENERAL).getValorParametro().toString())};*/
                //Datos quemados SIIAGC
//                int[] tipos = {207, 207};
//                int[] tipoModulo = {2214, 2214};
                int[] tipos = {2,2};
                int[] tipoModulo = {3,3};
                //Elementos a los cuales puede acceder por parte del sistema elegido el valor 4 es para el menu_horizontal superior
                lista_elementos = gSeguridadMgr.obtenerElemUsuaUnidadEjecSist(
                        lVistaUsuario.getgUsuarioActual().getIdUsuario(),
                        lVistaUsuario.getgUnidadActual(), tipos, tipoModulo);
                lista_elementos = Util.ordenarElementos(lista_elementos);
                //lista_elementos=cs.ordenarElementos(elementos_seguridad);
                //Creo los items del menu_horizontal
                agregarMenu(lista_elementos, gMenuHorizontal);
                lVistaUsuario.setgPopUnidades(false);
                gMostrarPerfiles = false;
                //cargarBuzon();
                gNavegarInicio = "principal";
            } catch (FWExcepcion ex) {
                Mensaje.agregarErrorAdvertencia(ex.getError_para_usuario());
                gMostrarPerfiles = false;
                //reviso q el error sea importante para imprimirlo en el log
                if (ex.getError_para_programador() != null) {
                    ex.imprimirError(this.getClass());
                }
            }//fin catch
        } else {
            lVistaUsuario.setgPopUnidades(false);
            lVistaUsuario.setgPopPerfiles(true);
        }
    }

    /*public void cargarMenuPrincipalSegunPerfil(ActionEvent event) {
        gMenuHorizontal.clear();
        VistaUsuario lVistaUsuario = (VistaUsuario) Util.obtenerVista("#{vistaUsuario}");
        List<SegElemento> lista_elementos = new ArrayList<SegElemento>();
        try {
            if (!validarPerfilUnidadEjecutora(lVistaUsuario.getgPerfilSeleccionado(),
                    lVistaUsuario.getgUnidadActual().getIdTipoUnidad(),
                    "" + lVistaUsuario.getgUnidadActual().
                    getUnidadEjecutoraLlave().getIdUnidadEjecutora())) {
                Mensaje.agregarErrorUsuario(Util.getEtiquetas("error.usuario.perfil.invalido"));
                return;
            }
            Object[] tipos = {parametroMgr.findParametroById(MENU_PRIN).getValorParametro(),
                parametroMgr.findParametroById(MENU_PRIN).getValorParametro()};
            lista_elementos = gSeguridadMgr.obtenerElementosSegunPerfil(
                    lVistaUsuario.getgPerfilSeleccionado(), tipos);

            lista_elementos = Util.ordenarElementos(lista_elementos);
            agregarMenu(lista_elementos, gMenuHorizontal);
            if (lVistaUsuario.getgPerfilSeleccionado().equals("50475")) {
                Util.navegar("principal_sistema");
            } else {
                //cargarBuzon();
            }
            gNavegarInicio = "principal";
            gMostrarPerfiles = true;
            lVistaUsuario.setgPopPerfiles(false);
        } catch (ExcepcionSIIAGC e) {
            Logger.getLogger(VistaMenu.class.getName()).log(Level.SEVERE, null, e);
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
    }//fin cargarMenuPrincipalSegunPerfil*/

    /*public void cambiarPerfil(ValueChangeEvent event) {
        if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
        } else {
            if (event.getNewValue() != null) {
                gMenuTipoFormulacion.clear();
                cargarMenuPrincipalSegunPerfil(null);
                Mensaje.agregarInfo("Se ha cambiado de perfil");
                if (gEtiquetaSeleccionMenu.compareTo(Util.getEtiquetas("label.menu.mostrarMenuPrincipal")) == 0
                        && Util.obtenerVariableSession(Util.getEtiquetas("sesion.numero.propuesta")) != null) {
                    cambiarEtiqueta(null);
                    JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "llamar(true);");
                }
            }
        }
    }//fin cambiarPerfil*/

    //método que me redirige a la pantalla principal
    public String IrInicio() {
        return gNavegarInicio;
    }

    public void cambiarUnidadEjecutora(ValueChangeEvent e) {
        if (e.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
            e.setPhaseId(PhaseId.INVOKE_APPLICATION);
            e.queue();
        } else {
            if (e.getNewValue() != null) {
                int id_unidad = Integer.parseInt(e.getNewValue().toString());
                VistaUsuario lvistaUsuario = (VistaUsuario) Util.obtenerVista("#{vistaUsuario}");
                List<SegUnidadEjecutora> lUnidadesUsuario = new ArrayList<SegUnidadEjecutora>();
                try {
                    lUnidadesUsuario = lvistaUsuario.getUnidadesDistintas(
                            gSeguridadMgr.obtenerUnidadesUsuarioPorSistema(
                            lvistaUsuario.getgUsuarioActual().getIdUsuario()));
                } catch (FWExcepcion ex) {
                    Mensaje.agregarErrorAdvertencia(ex.getError_para_usuario());
                }
                if (lvistaUsuario.getgIdUnidadItem() != id_unidad) {
//                    boolean etiquetaCambiada = false;
                    lvistaUsuario.setgIdUnidadItem(id_unidad);
                    
                    for (int i = 0; i < lUnidadesUsuario.size(); i++) {
                        if (lUnidadesUsuario.get(i).
                                getUnidadEjecutoraLlave().getIdUnidadEjecutora().equals(id_unidad)) {
                            lvistaUsuario.setgUnidadActual(lUnidadesUsuario.get(i));
                            //Se asigna la unidad ejecutora
                            Long id  = Long.parseLong(lUnidadesUsuario.get(i).getUnidadEjecutoraLlave().getIdUnidadEjecutora().toString());
                            UnidadEjecutora unidadEjecutora = unidadEjecutoraModel.buscarPorId(id);
                            lvistaUsuario.setUnidadEjecutoraSIGEBI(unidadEjecutora);
                            
                            break;
                        }
                    }
                    gMenuTipoFormulacion.clear();
                    this.cargarMenu(null);
                    Mensaje.agregarInfo(Util.getEtiquetas("cambio.unidad.ejecutora"));
                    if (gEtiquetaSeleccionMenu.equals(Util.getEtiquetas("label.menu.mostrarMenuPrincipal")) &&
                        Util.obtenerVariableSession(Util.getEtiquetas("sesion.numero.propuesta")) != null) {
//                        etiquetaCambiada = true;
                        cambiarEtiqueta(null);
                        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "llamar(true);");
                    }
//                    if (gEtiquetaSeleccionMenu.equals(Util.getEtiquetas("label.menu.mostrarMenuFormulacion")) &&
//                        !etiquetaCambiada) {
//                        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "llamar(true);");
//                    }
                }
            }
        }//fin del else
    }//fin del metodo

    public void cambiarEtiqueta(ActionEvent event) {
        if (gEtiquetaSeleccionMenu.equals(Util.getEtiquetas("label.menu.mostrarMenuFormulacion"))) {
            gEtiquetaSeleccionMenu = Util.getEtiquetas("label.menu.mostrarMenuPrincipal");
            gMenuSeleccionado = true;
        } else {
            if (gEtiquetaSeleccionMenu.equals(Util.getEtiquetas("label.menu.mostrarMenuPrincipal"))) {
                gEtiquetaSeleccionMenu = Util.getEtiquetas("label.menu.mostrarMenuFormulacion");
                gMenuSeleccionado = false;
            }
        }
    }

    /**
     * Método que me pasa de un conjunto una lista de la entidad a una lista de
     * objetos menu item que van a ser los que se le muestren al usuario
     *
     * @param lista_elementos que van a ser mapeados a una lista de menu item
     * @param menu
     */
    public void agregarMenu(List<SegElemento> lista_elementos, List<MenuItemPrincipal> menu) {
        int cant_hijos_borrados = 0;
        for (int i = 0; i < lista_elementos.size(); i++) {
            SegElemento opcion = lista_elementos.get(i);
            if (opcion.getIdElementoPadre() == null) {
                MenuItemPrincipal item_menu = this.crearMenuItem(opcion);
                lista_elementos.remove(opcion);
                i--;
                cant_hijos_borrados = lista_elementos.size();
                lista_elementos = agregarHijosMenu(item_menu, lista_elementos);
                if (cant_hijos_borrados > lista_elementos.size()) {
                    i = -1;
                }
                menu.add(item_menu);
            }
        }
    }//fin del método agregarMenu

    public List<SegElemento> agregarHijosMenu(MenuItemPrincipal padre, List<SegElemento> hijos) {
        for (int i = 0; i < hijos.size(); i++) {
            SegElemento elemento = hijos.get(i);
            if (elemento.getIdElementoPadre() != null) {
                if (elemento.getIdElementoPadre().getIdElemento() == padre.getId_menu()) {
                    MenuItemPrincipal item = crearMenuItem(elemento);
                    padre.getChildren().add(item);
                    hijos.remove(elemento);
                    i = -1;
                    agregarHijosMenu(item, hijos);

                }
            }
        }
        return hijos;
    }//fin agregarHijosMenu

    /**
     * Crea un menuItem segun el objeto recibido..
     *
     * @param objeto Objeto Base con la informacion del Item
     * @return Item de Menu, con label, acciones, entre otras.
     */
    private MenuItemPrincipal crearMenuItem(ObjetoBase objeto) {
        String label = "";
        MenuItemPrincipal lNuevoItem = new MenuItemPrincipal();

        if (objeto instanceof SegElemento) {
            label = ((SegElemento) objeto).getDscNombre();
            lNuevoItem.setId_menu(((SegElemento) objeto).getIdElemento());
            lNuevoItem.setRegla_navegacion(((SegElemento) objeto).getRegla_navegacion());
        }

        lNuevoItem.setValue(label); //Coloco la etiqueta o valor mostrado por el item
        lNuevoItem.setId("_" + lNuevoItem.getId_menu()); //setea el id con un _ antes del identificador por reglas de faces
        /*--------------------------------------------------------------------*/
//      lNuevoItem.setActionListener(FacesContext.getCurrentInstance().getApplication().createMethodBinding("#{vistaNavegacion.navegar}",   new Class[]{ActionEvent.class}));
        /*--------------------------------------------------------------------*/
        ActionListener[] listeners = lNuevoItem.getActionListeners();
        for (ActionListener listener : listeners) {
            lNuevoItem.removeActionListener(listener);
        }
        lNuevoItem.addActionListener(new MethodExpressionActionListener(FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{vistaNavegacion.navegar}", null, new Class[]{ActionEvent.class})));
        /*--------------------------------------------------------------------*/
        return lNuevoItem;
    }

    /**
     * Método que me indica si el menú es el de formulación
     *
     * @return boolean - que es verdadero si se trata del menú de formulación
     */
    public boolean isMenuFormulacion() {
        return gEtiquetaSeleccionMenu.equalsIgnoreCase(Util.getEtiquetas("label.menu.mostrarMenuFormulacion"));
    }//fin del método isMenuFormulacion

    /**
     * Método que me indica si el menú es el principal
     *
     * @return boolean - que es verdadero si se trata del menú principal
     */
    public boolean isMenuPrincipal() {
        return gEtiquetaSeleccionMenu.equalsIgnoreCase(Util.getEtiquetas("label.menu.mostrarMenuPrincipal"));
    }//fin del método isMenuFormulacion
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Sets y Gets"> 

    public boolean isgMenuSeleccionado() {
        return gMenuSeleccionado;
    }

    public SeguridadMgr getgSeguridadMgr() {
        return gSeguridadMgr;
    }

    public void setgSeguridadMgr(SeguridadMgr gSeguridadMgr) {
        this.gSeguridadMgr = gSeguridadMgr;
    }

    public void setgMenuSeleccionado(boolean gMenuSeleccionado) {
        this.gMenuSeleccionado = gMenuSeleccionado;
    }

    public UnidadEjecutoraModel getUnidadEjecutoraModel() {
        return unidadEjecutoraModel;
    }

    public void setUnidadEjecutoraModel(UnidadEjecutoraModel unidadEjecutoraModel) {
        this.unidadEjecutoraModel = unidadEjecutoraModel;
    }

    public List<MenuItemPrincipal> getgMenuHorizontal() {
        return gMenuHorizontal;
    }

    public String getgNavegarInicio() {
        return gNavegarInicio;
    }

    public void setgNavegarInicio(String gNavegarInicio) {
        this.gNavegarInicio = gNavegarInicio;
    }

    public void setgMenuHorizontal(List<MenuItemPrincipal> gMenuHorizontal) {
        this.gMenuHorizontal = gMenuHorizontal;
    }

    public boolean isgMostrarPerfiles() {
        return gMostrarPerfiles;
    }

    public void setgMostrarPerfiles(boolean gMostrarPerfiles) {
        this.gMostrarPerfiles = gMostrarPerfiles;
    }

    public Date getgFechaSistema() {
        return gFechaSistema;
    }

    public void setgFechaSistema(Date gFechaSistema) {
        this.gFechaSistema = gFechaSistema;
    }

    public TimeZone getZona() {
        return zona;
    }

    public void setZona(TimeZone zona) {
        this.zona = zona;
    }

    public String getgEtiquetaSeleccionMenu() {
        return gEtiquetaSeleccionMenu;
    }

    public void setgEtiquetaSeleccionMenu(String gEtiquetaSeleccionMenu) {
        this.gEtiquetaSeleccionMenu = gEtiquetaSeleccionMenu;
    }

    public List<MenuItemPrincipal> getgMenuTipoFormulacion() {
        return gMenuTipoFormulacion;
    }

    public void setgMenuTipoFormulacion(List<MenuItemPrincipal> gMenuTipoFormulacion) {
        this.gMenuTipoFormulacion = gMenuTipoFormulacion;
    }

    public int getCantMenuFormulacion() {
        if (this.gMenuTipoFormulacion == null) {
            return 0;
        } else {
            return this.gMenuTipoFormulacion.size();
        }
    }
    // </editor-fold>
    
}//fin de la clase
