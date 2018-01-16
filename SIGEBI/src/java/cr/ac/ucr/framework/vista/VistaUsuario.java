package cr.ac.ucr.framework.vista;

import cr.ac.ucr.framework.seguridad.entidades.SegPerfil;
import cr.ac.ucr.framework.seguridad.entidades.SegUnidadEjecutora;
import cr.ac.ucr.framework.seguridad.entidades.SegUnidadUsuario;
import cr.ac.ucr.framework.seguridad.entidades.SegUsuario;
import cr.ac.ucr.framework.seguridad.utils.SHA256;
import cr.ac.ucr.framework.seguridad.utils.UTF8;
import cr.ac.ucr.framework.daoHibernate.DaoHelper;

import cr.ac.ucr.framework.service.SeguridadMgr;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value = "vistaUsuario")
@Scope("session")
public class VistaUsuario {

    // <editor-fold defaultstate="collapsed" desc="Atributos">
    //Manager de seguridad
    @Resource
    private SeguridadMgr seguridadMgr;

    private boolean gLoginDirecto = true;
    //Variable para almacenar los datos del usuario del sistema
    private SegUsuario gUsuarioActual;
    //unidad ejecutara que selecciona el usuario.
    private SegUnidadEjecutora gUnidadActual;
    //recinto asociado --Agregado por Manuel
    private SegUnidadEjecutora gUnidadEjecutoraPadre;
    private List<SegUnidadEjecutora> gSucesores;
    private SegUnidadEjecutora gRecinto;
    private SegUnidadEjecutora gFacultad;
    private SegUnidadEjecutora gSede;
    //termina agregado por Manuel Sanabria
    //Variable donde se va a guardar el perfil que selecione el usuario
    private String gPerfilSeleccionado;
    //booleano que me permite manejar el popup de perfiles
    private boolean gPopPerfiles;
    //booleano que me permite manejar el popup de unidades
    public boolean gPopUnidades;
    //lista de Unidades que se le despliegan al usuario
    private List<SelectItem> gUnidades;
    //lista de perfiles que se le despliegan al usuario
    private List<SelectItem> gPerfiles;
    //variable donde se guardan las unidades a las que tiene acceso el usuario
    private List<SegUnidadEjecutora> gUnidadesUsuario;
    //guarda el id de la unidad que escoja el usuario
    private int gIdUnidadItem;
    //Mostrar menú ingresar
    private int mostrarIngreso;
    private boolean habilitaBotonAceptar;

    private boolean unidadAcadPadre;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos">
    /**
     * inicializa las variables de la vista
     *
     * @since 10/02/2011
     */
    @PostConstruct
    public final void inicializar() {
        mostrarIngreso = -1;
        gPerfilSeleccionado = "";
        gUnidadActual = new SegUnidadEjecutora();
        gUnidades = new ArrayList<SelectItem>();
        gPerfiles = new ArrayList<SelectItem>();
        gUnidadesUsuario = new ArrayList<SegUnidadEjecutora>();
        this.gUsuarioActual = new SegUsuario();

        this.habilitaBotonAceptar = false;

        this.gSucesores = new ArrayList<SegUnidadEjecutora>();
        gUnidadEjecutoraPadre = new SegUnidadEjecutora();
        gRecinto = new SegUnidadEjecutora();
        gSede = new SegUnidadEjecutora();
        gFacultad = new SegUnidadEjecutora();
    }

    /**
     * Método para ingresar al sistema valida el inicio del sistema
     */
    public String ingresar() {
        String lPaginaSiguiente = "";
        Util.setValorComponente("login:usuario", "styleClass", true, "inputTextLogin");
        Util.setValorComponente("login:clave", "styleClass", true, "inputTextLogin");
        if (this.gUsuarioActual.getIdUsuario().equals("")) {
            Util.setValorComponente("SIGECU:usuario", "styleClass", true, "error_inputTextLogin");
            Mensaje.agregarErrorAdvertencia("Usuario vacio");

//        } else if (this.gUsuarioActual.getCodClave().equals("")) {
//            Util.setValorComponente("SIGECU:clave", "styleClass", true, "error_inputTextLogin");
//            Mensaje.agregarErrorAdvertencia("Clave vacía");
        } else {
            /**
             * ***************************************************
             */
            Mensaje.ocultarMensajes();
            //llamo al método que hace el login
            if (login());
            {
                lPaginaSiguiente = "ingresar";
                Util.agregarVariableSession(
                        Util.getEtiquetas("sesion.usuario"), gUsuarioActual.getIdUsuario());
            }
        }//fin del método ingresar
        return lPaginaSiguiente;
    }//fin ingresar

    /**
     * hace inicicio de sesion de seguridad y LDAP en caso de hacer bien el
     * login en LDAP actualiza la gClave en seguridad
     *
     * @since 10/02/2011
     */
    public boolean login() {
        /**
         * ***************************************************
         */
        boolean ingresar = false;
        boolean lLoginCorrecto = false;
        boolean lConexion = true;
        boolean lLoginUCR = false;
        String lClaveEncriptada = "";
        if (gLoginDirecto) { // esto en el caso de que el login se este haciendo directamente en el sistema oesa que no venga de otro
            lClaveEncriptada = SHA256.getInstancia().encriptar(this.gUsuarioActual.getCodClave());
        } else {
            lClaveEncriptada = this.gUsuarioActual.getCodClave();
        }
        try {
            lLoginCorrecto = seguridadMgr.autorizarLoginUCR(this.gUsuarioActual.getIdUsuario(),
                    this.gUsuarioActual.getCodClave());
            lLoginUCR = true;
            //throw new ExcepcionSeguridad();
        } catch (FWExcepcion e) {//Si se cae, debemos de tratar de loguear por
            //el sistema propio de SIGECU
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
            try {
                lLoginCorrecto = seguridadMgr.autorizarLoginSeguridad(gUsuarioActual.getIdUsuario(),
                        lClaveEncriptada);

            } catch (FWExcepcion ex) {
                Mensaje.agregarErrorAdvertencia(ex.getError_para_usuario());
                lConexion = false;
            }
        }
        try {
            if (lLoginCorrecto) {
                try {
                    gUsuarioActual = seguridadMgr.buscarUsuarioLowerCase(
                            new SegUsuario(gUsuarioActual.getIdUsuario()));
                } catch (FWExcepcion x) {
                    Mensaje.agregarErrorAdvertencia(x.getError_para_usuario());
                }
                if (gUsuarioActual == null) {//Si el usuario indicado no existe en SIGECU no continuo
                    Mensaje.agregarErrorAdvertencia(UTF8.aUTF8("El usuario no se encuentra registrado en Seguridad"));
                } else if (lLoginUCR) {//Si se logueo por la UCR, actualizo la contraseña UTILIZADA para el LDAP en la base de datos de SIGECU
                    gUsuarioActual.setCodClave(lClaveEncriptada);
                    try {
                        //cargo los demas datos del usuario
                        seguridadMgr.actualizarUsuario(gUsuarioActual);
                        //remuevo la clave del usuario apesar de que este encriptada, para que no quede en memoria
                        gUsuarioActual.setCodClave(null);
                    } catch (FWExcepcion exp) {
                        Mensaje.agregarErrorAdvertencia(exp.getError_para_usuario());
                    }//catch
                }
                ingresar = cargarUnidades();
            } else if (lConexion) {
                Mensaje.agregarErrorAdvertencia(UTF8.aUTF8("Usuario o contraseña inválida."));
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorUsuario(UTF8.aUTF8(Util.getEtiquetas("mensaje.conexion")));
        }
        return ingresar;
    }//fin login

    /**
     * obtiene las Unidades asociadas al usuario y las presenta en un pop-up
     * para que el usuario seleccione la unidad con la que va a trabajar
     *
     * @since 10/02/2011
     */
    public boolean cargarUnidades() {
        boolean ingresar = false;
        gUnidadesUsuario.clear();
        List<SegUnidadEjecutora> lUnidadesUsuario = gUnidadesUsuario;
        List<SegUnidadUsuario> lListUnidUsuario = new ArrayList<SegUnidadUsuario>();
        try {
            lListUnidUsuario = seguridadMgr.obtenerUnidadesUsuarioPorSistema(gUsuarioActual.getIdUsuario());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        }
        lUnidadesUsuario = this.getUnidadesDistintas(lListUnidUsuario);
        lUnidadesUsuario = Util.ordenarUnidadesEjecutoras(lUnidadesUsuario);//ordeno las unidades alfabeticamente por el nombre
        gUnidadesUsuario = lUnidadesUsuario;
        final int LCantUnidades = lUnidadesUsuario.size();  //encuentro la cantidad de unidades asociadas al usuario
        // mostrarFecha();
        //Procedo a acceder
        if (LCantUnidades == 1) {//Si tiene una sola unidad
            this.cargarItemsUnidadesEjecutoras();
            gPopUnidades = true;//Cargo los modulos y elementos, accesibles al usuario

            //ingresar = true;
            //Oculto el modal
        } else if (LCantUnidades > 1) {//Si tiene varias unidades, debe elegir una
            this.cargarItemsUnidadesEjecutoras();
            gPopUnidades = true; //Muestro el modal

        } else {//SI no tiene unidades asociadas hay un error, no puede loguearse
            Mensaje.agregarErrorAdvertencia(UTF8.aUTF8("El usuario " + gUsuarioActual.getIdUsuario() + ", no tiene acceso al sistema"));
        }
        return ingresar;
    }//fin del método cargar Unidades

    /**
     * se ponen las unidades ejecutaras en un select item para presentarlas en
     * el combo al usuario
     *
     * @since 10/02/2011
     */
    public void cargarItemsUnidadesEjecutoras() {
        gUnidades.clear();
        if (gUnidadesUsuario != null) {
            gUnidadActual = gUnidadesUsuario.get(0);
            for (int i = 0; i < gUnidadesUsuario.size(); i++) {
                gUnidades.add(i, new SelectItem(gUnidadesUsuario.get(i).getSisUnidadEjecutoraPK().getIdUnidadEjecutora(), gUnidadesUsuario.get(i).getDscUnidadEjecutora()));
            }//fin del for
        }//fin del if
    }//fin del método

    public boolean obtenerPerfilesUsuario() {//SI TIENE MAS DE UN PERFIL ASOCIADO DEVUELVE TRUE 
        boolean lVariosPerfil;
        List<SegPerfil> perfiles_seguridad = new ArrayList<SegPerfil>();
        try {
            perfiles_seguridad = seguridadMgr.obtenerPerfilesUsuario(gUsuarioActual.getIdUsuario(), gUnidadActual);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorUsuario(e.toString());
        }
        if (perfiles_seguridad.size() > 1) {
            cargarPerfilesSelect(perfiles_seguridad);
            lVariosPerfil = true;
        } else {
            gPerfilSeleccionado = perfiles_seguridad.get(0).getIdPerfil().toString();
            lVariosPerfil = false;//si solo tiene un perfil
        }
        return lVariosPerfil;
    }//fin obtenerPerfilesUsuario

    //método de clase que me trae los perfiles para una unidad
    private void cargarPerfilesSelect(List<SegPerfil> perfiles_seguridad) {
        gPerfiles.clear();
        for (int i = 0; i < perfiles_seguridad.size(); i++) {
            if (i == 0) {
                gPerfilSeleccionado = perfiles_seguridad.get(i).getIdPerfil().toString();
            }
            gPerfiles.add(new SelectItem(perfiles_seguridad.get(i).getIdPerfil(), perfiles_seguridad.get(i).getDscPerfil(), perfiles_seguridad.get(i).getDscPerfil()));
        }
    }

    /**
     * asigna la unidad actual seleccionada por el usuario
     *
     * @param event que se genera con la acción del usuario
     */
    public void asignarUnidadActual(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }//if
        //Busco la unidad ejecutora seleccionada y actualizo la unidad actual
        Integer unidadEjecSelect = Integer.valueOf(event.getNewValue().toString());
        if (unidadEjecSelect != null) {
            for (SegUnidadEjecutora temp : gUnidadesUsuario) {
                if (temp.getSisUnidadEjecutoraPK().getIdUnidadEjecutora().equals(unidadEjecSelect)) {
                    gUnidadActual = temp;
                    //limpar las variables para llegar al recinto
                    gUnidadEjecutoraPadre = null;
                    this.gSucesores = new ArrayList<SegUnidadEjecutora>();
                    gRecinto = null;
                    gSede = null;
                    gFacultad = null;
                    // Si existe una unidad ejecutora seleccionada se busca la unidad ejecutora padre  - Agreagado por Manuel Sanabria -
                    if (gUnidadActual.getId_unidad_ejecutora_padre() != null) {
                        gUnidadEjecutoraPadre = this.seguridadMgr.obtenerUnidadEjecutoraPadre(gUnidadActual.getId_unidad_ejecutora_padre());
                    }
                    if (gUnidadEjecutoraPadre != null) {
                        SegUnidadEjecutora aux = new SegUnidadEjecutora();
                        aux = gUnidadEjecutoraPadre;
                        while (aux.getId_unidad_ejecutora_padre() != null) {
                            if ("SRC".equals(aux.getIdTipoUnidad())) {
                                gRecinto = aux;
                            } else if ("SSD".equals(aux.getIdTipoUnidad())) {
                                gSede = aux;
                            } else if ("SFC".equals(aux.getIdTipoUnidad())) {
                                gFacultad = aux;
                            }
                            this.gSucesores.add(aux);
                            aux = this.seguridadMgr.obtenerUnidadEjecutoraPadre(aux.getId_unidad_ejecutora_padre());
                        }

                    }//agregado por Manuel Sanabria
                }//if
            }//for
            if (gUnidadActual.getCodigoReferencia() == null) {
                gUnidadActual.setCodigoReferencia(unidadEjecSelect + "");
            }
            Util.agregarVariableSession(Util.getEtiquetas("sesion.numero.unidad"),
                    gUnidadActual.getCodigoReferencia());
        }//if
    }

    /**
     * Asigna eel perfil actual seleccionado por el usuario
     *
     * @param event que se genera con la acción del usuario
     */
    public void asignarPerfilActual(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }//if
        if (event.getNewValue().toString() != null) {
            gPerfilSeleccionado = event.getNewValue().toString();
        }
    }//fin asignarPerfilActual

    /**
     * El siguiente metodo toma una lista de UnidadUsuario, y devuelve cuantas
     * unidades Ejecutoras distintas hay en dicho usuario.
     *
     * @param lista Lista de unidades Usuario que tiene el usuario ingresado
     * @return Lista de unidades ejecutoras (sin repetir), a las que puede
     * acceder el usuario
     *
     */
    public List<SegUnidadEjecutora> getUnidadesDistintas(List<SegUnidadUsuario> lista) {
        Hashtable unidades_distintas = new Hashtable();
        ArrayList<SegUnidadEjecutora> resultado_unidades = new ArrayList<SegUnidadEjecutora>();
        for (SegUnidadUsuario uni_usu : lista) {
            String llave_unidad = uni_usu.getUnidadUsuarioLlave().getIdUnidadEjecutora() + "-" + uni_usu.getUnidadUsuarioLlave().getIdEmpresa();
            if (!unidades_distintas.containsKey(llave_unidad)) {
                unidades_distintas.put(llave_unidad, llave_unidad);
                resultado_unidades.add(uni_usu.getUnidad_ejecutora());
            }
        }
        return resultado_unidades;
    }

    /**
     * método que me limpia los popup de la pantalla de inicio, tanto el de
     * elección de unidades académicas, como el de selección de perfil de
     * usuario. Generalmente se utiliza para el boton cancelar
     */
    public void cerrarModalUnidad() {
        gPopUnidades = false;
        gPopPerfiles = false;
        inicializar();
    }

    public boolean isHabilitaBotonAceptar() {
        return habilitaBotonAceptar;
    }

    public void setHabilitaBotonAceptar(boolean habilitaBotonAceptar) {
        this.habilitaBotonAceptar = habilitaBotonAceptar;
    }

    public String salir() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        inicializar();
        return "index";
    }
    
  



    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Sets y Gets">
    /**
     * @return the mostrarIngreso
     */
    public int getMostrarIngreso() {
        return mostrarIngreso;
    }

    /**
     * @param mostrarIngreso the mostrarIngreso to set
     */
    public void setMostrarIngreso(int mostrarIngreso) {
        this.mostrarIngreso = mostrarIngreso;
    }

    public boolean isgLoginDirecto() {
        return gLoginDirecto;
    }

    public void setgLoginDirecto(boolean gLoginDirecto) {
        this.gLoginDirecto = gLoginDirecto;
    }

    public List<SelectItem> getgPerfiles() {
        return gPerfiles;
    }

    public void setgPerfiles(List<SelectItem> gPerfiles) {
        this.gPerfiles = gPerfiles;
    }

    public boolean isgPopPerfiles() {
        return gPopPerfiles;
    }

    public void setgPopPerfiles(boolean gPopPerfiles) {
        this.gPopPerfiles = gPopPerfiles;
    }

    public boolean isgPopUnidades() {
        return gPopUnidades;
    }

    public void setgPopUnidades(boolean gPopUnidades) {
        this.gPopUnidades = gPopUnidades;
    }

    public List<SelectItem> getgUnidades() {
        return gUnidades;
    }

    public void setgUnidades(List<SelectItem> gUnidades) {
        this.gUnidades = gUnidades;
    }

    public SegUsuario getgUsuarioActual() {
        return gUsuarioActual;
    }

    public void setgUsuarioActual(SegUsuario gUsuarioActual) {
        this.gUsuarioActual = gUsuarioActual;
    }

    public List<SegUnidadEjecutora> getgUnidadesUsuario() {
        return gUnidadesUsuario;
    }

    public void setgUnidadesUsuario(List<SegUnidadEjecutora> gUnidadesUsuario) {
        this.gUnidadesUsuario = gUnidadesUsuario;
    }

    public int getgIdUnidadItem() {
        return gIdUnidadItem;
    }

    public void setgIdUnidadItem(int gIdUnidadItem) {
        this.gIdUnidadItem = gIdUnidadItem;
    }

    public String getgPerfilSeleccionado() {
        return gPerfilSeleccionado;
    }

    public void setgPerfilSeleccionado(String gPerfilSeleccionado) {
        this.gPerfilSeleccionado = gPerfilSeleccionado;
    }

    public SegUnidadEjecutora getgUnidadActual() {
        return gUnidadActual;
    }

    public void setgUnidadActual(SegUnidadEjecutora gUnidadActual) {
        this.gUnidadActual = gUnidadActual;
    }

    public SeguridadMgr getSeguridadMgr() {
        return seguridadMgr;
    }

    public void setSeguridadMgr(SeguridadMgr seguridadMgr) {
        this.seguridadMgr = seguridadMgr;
    }

    public SegUnidadEjecutora getgUnidadEjecutoraPadre() {
        return gUnidadEjecutoraPadre;
    }

    public void setgUnidadEjecutoraPadre(SegUnidadEjecutora gUnidadEjecutoraPadre) {
        this.gUnidadEjecutoraPadre = gUnidadEjecutoraPadre;
    }

    //agregados por -Manuel Sanabria-
    public List<SegUnidadEjecutora> getgSucesores() {
        return gSucesores;
    }

    public void setgSucesores(List<SegUnidadEjecutora> gSucesores) {
        this.gSucesores = gSucesores;
    }

    public SegUnidadEjecutora getgRecinto() {
        return gRecinto;
    }

    public void setgRecinto(SegUnidadEjecutora gRecinto) {
        this.gRecinto = gRecinto;
    }

    public SegUnidadEjecutora getgFacultad() {
        return gFacultad;
    }

    public void setgFacultad(SegUnidadEjecutora gFacultad) {
        this.gFacultad = gFacultad;
    }

    public SegUnidadEjecutora getgSede() {
        return gSede;
    }

    public void setgSede(SegUnidadEjecutora gSede) {
        this.gSede = gSede;
    }
    // </editor-fold>

}//fin de la clase
