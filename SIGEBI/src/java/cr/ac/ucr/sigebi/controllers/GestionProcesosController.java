/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.seguridad.entidades.SegUsuario;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.commands.GestionProcesoCommand;
import cr.ac.ucr.sigebi.domain.Autorizacion;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Rol;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.models.AutorizacionModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.RolModel;
import cr.ac.ucr.sigebi.models.SegUsuarioModel;
import cr.ac.ucr.sigebi.models.TipoModel;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
import cr.ac.ucr.sigebi.models.UsuarioModel;
import cr.ac.ucr.sigebi.utils.JsfUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerGestionProceso")
@Scope("session")
public class GestionProcesosController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private TipoModel tipoModel;

    @Resource
    private UsuarioModel usuarioModel;

    @Resource
    private AutorizacionModel autorizacionModel;

    @Resource
    private RolModel rolModel;

    @Resource
    private AutorizacionRolModel autorizacionRolModel;

    @Resource
    private AutorizacionRolPersonaModel autorizacionRolPersonaModel;

    @Resource
    private SegUsuarioModel segUsuarioModel;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private UnidadEjecutoraModel unidadEjecutoraModel;

    //Listas para agregas
    List<SelectItem> tiposProceso;
    List<SelectItem> autorizacionesTipoProceso;
    List<SelectItem> roles;
    List<SegUsuario> usuarios;

    GestionProcesoCommand command;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public TipoModel getTipoModel() {
        return tipoModel;
    }

    public void setTipoModel(TipoModel tipoModel) {
        this.tipoModel = tipoModel;
    }

    public UsuarioModel getUsuarioModel() {
        return usuarioModel;
    }

    public void setUsuarioModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }

    public AutorizacionModel getAutorizacionModel() {
        return autorizacionModel;
    }

    public void setAutorizacionModel(AutorizacionModel autorizacionModel) {
        this.autorizacionModel = autorizacionModel;
    }

    public RolModel getRolModel() {
        return rolModel;
    }

    public void setRolModel(RolModel rolModel) {
        this.rolModel = rolModel;
    }

    public AutorizacionRolModel getAutorizacionRolModel() {
        return autorizacionRolModel;
    }

    public void setAutorizacionRolModel(AutorizacionRolModel autorizacionRolModel) {
        this.autorizacionRolModel = autorizacionRolModel;
    }

    public AutorizacionRolPersonaModel getAutorizacionRolPersonaModel() {
        return autorizacionRolPersonaModel;
    }

    public void setAutorizacionRolPersonaModel(AutorizacionRolPersonaModel autorizacionRolPersonaModel) {
        this.autorizacionRolPersonaModel = autorizacionRolPersonaModel;
    }

    public SegUsuarioModel getSegUsuarioModel() {
        return segUsuarioModel;
    }

    public void setSegUsuarioModel(SegUsuarioModel segUsuarioModel) {
        this.segUsuarioModel = segUsuarioModel;
    }

    public EstadoModel getEstadoModel() {
        return estadoModel;
    }

    public void setEstadoModel(EstadoModel estadoModel) {
        this.estadoModel = estadoModel;
    }

    public List<SelectItem> getTiposProceso() {
        return tiposProceso;
    }

    public void setTiposProceso(List<SelectItem> tiposProceso) {
        this.tiposProceso = tiposProceso;
    }

    public List<SelectItem> getAutorizacionesTipoProceso() {
        return autorizacionesTipoProceso;
    }

    public void setAutorizacionesTipoProceso(List<SelectItem> autorizacionesTipoProceso) {
        this.autorizacionesTipoProceso = autorizacionesTipoProceso;
    }

    public List<SelectItem> getRoles() {
        return roles;
    }

    public void setRoles(List<SelectItem> roles) {
        this.roles = roles;
    }

    public List<SegUsuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<SegUsuario> usuarios) {
        this.usuarios = usuarios;
    }

    public GestionProcesoCommand getCommand() {
        return command;
    }

    public void setCommand(GestionProcesoCommand command) {
        this.command = command;
    }

    public UnidadEjecutoraModel getUnidadEjecutoraModel() {
        return unidadEjecutoraModel;
    }

    public void setUnidadEjecutoraModel(UnidadEjecutoraModel unidadEjecutoraModel) {
        this.unidadEjecutoraModel = unidadEjecutoraModel;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public GestionProcesosController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        //Se consultan los tipos por dominio, procesos
        List<Tipo> lista = tipoModel.listarPorDominio(Constantes.DOMINIO_PROCESO);
        tiposProceso = new ArrayList<SelectItem>();
        for (Tipo item : lista) {
            tiposProceso.add(new SelectItem(item.getId().toString(), item.getNombre()));
        }

        //Se consultan los roles de la aplicacion
        List<Rol> listaRoles = rolModel.listarTodos();
        roles = new ArrayList<SelectItem>();
        for (Rol item : listaRoles) {
            roles.add(new SelectItem(item.getId().toString(), item.getNombre()));
        }

        command = new GestionProcesoCommand();
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    /**
     * Se seleccionan el proceso
     *
     * @param event
     */
    public void seleccionProceso(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        // Se obtiene el id del proceso
        Integer valor = Integer.parseInt(event.getNewValue().toString());
        command.setIdTipoProceso(valor);

        //Se inicializa el autorizacion y usuarios
        command.setIdAutorizacionTipoProceso(-1L);
        autorizacionesTipoProceso = null;

        //Se busca a los usuarios asociados al autorizacion y al rol
        buscaUsuariosAutorizacionRol();

        if (valor > 0) {
            //Se cargan los autorizacions asociados al proceso
            List<Autorizacion> lista = autorizacionModel.buscarPorTipoProceso(valor);
            autorizacionesTipoProceso = new ArrayList<SelectItem>();
            for (Autorizacion item : lista) {
                autorizacionesTipoProceso.add(new SelectItem(item.getId().toString(), item.getNombre()));
            }
        }
    }

    /**
     * Se seleccion la autorizacion
     *
     * @param event
     */
    public void seleccionAutorizacion(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        // Se obtiene el id del autorizacion
        Long valor = Long.parseLong(event.getNewValue().toString());
        command.setIdAutorizacionTipoProceso(valor);

        //Se busca a los usuarios asociados al autorizacion y al rol
        this.buscaUsuariosAutorizacionRol();
    }

    /**
     * Se selecciona el rol
     *
     * @param event
     */
    public void seleccionRol(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        // Se obtiene el id del rol
        Long valor = Long.parseLong(event.getNewValue().toString());
        command.setIdRol(valor);

        //Se busca a los usuarios asociados al autorizacion y al rol
        this.buscaUsuariosAutorizacionRol();
    }

    private void buscaUsuariosAutorizacionRol() {
        usuarios = null;
        this.setCantidadRegistros(0);
        if (command.getIdRol() > 0 && command.getIdAutorizacionTipoProceso() > 0) {
            //Se buscan los usuarios       
            this.contarUsuarios();
            this.listarUsuarios();
        }
    }

    /**
     * Lista los usuarios
     */
    private void listarUsuarios() {
        try {

            //Se cargan los usuario asociados al rol
            List<AutorizacionRolPersona> personasRolAutorizacion = autorizacionRolPersonaModel.buscarPorAutorizacionRol(command.getIdAutorizacionTipoProceso(), command.getIdRol());

            //Se buscan los usuarios
            this.usuarios = segUsuarioModel.listarUsuarios(command.getIdUsuario(), command.getNombreCompleto(), 
                    command.getCorreo(), this.getPrimerRegistro() - 1, this.getUltimoRegistro());

            //Se seleccionan los usuarios asociados a los roles
            for (SegUsuario usuario : usuarios) {
                for (AutorizacionRolPersona personaRol : personasRolAutorizacion) {
                    if (personaRol.getUsuarioSeguridad().getId().equals(usuario.getIdUsuario())) {
                        usuario.setMarcado(true);
                    }
                }
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.listarUsuarios"));
        }
    }

    /**
     * Agregar el usuario
     *
     * @param pEvent
     */
    public void agregar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se busca el rol autorizacion, si no existe se crea
            AutorizacionRol autorizacionRol = autorizacionRolModel.buscarPorRolAutorizacion(command.getIdRol(), command.getIdAutorizacionTipoProceso());
            if (autorizacionRol == null) {
                autorizacionRol = new AutorizacionRol();
                autorizacionRol.setRol(rolModel.buscarPorId(command.getIdRol()));
                autorizacionRol.setAutorizacion(autorizacionModel.buscarPorId(command.getIdAutorizacionTipoProceso()));
                autorizacionRolModel.agregar(autorizacionRol);
            }

            SegUsuario usuario = (SegUsuario) pEvent.getComponent().getAttributes().get("usuarioSelApro");
            Usuario usr = usuarioModel.buscarPorId(usuario.getIdUsuario());
//TODO arreglar unidad ejecutora
//            UnidadEjecutora unidadEjecutora = unidadEjecutoraModel.buscarPorId(unidadEjecutoraId);

            //Se incluye el usuario al rol
            AutorizacionRolPersona autorizacionRolPersona = new AutorizacionRolPersona();
            autorizacionRolPersona.setAutorizacionRol(autorizacionRol);            
            autorizacionRolPersona.setUsuarioSeguridad(usr);
//            autorizacionRolPersona.setUnidadEjecutora(unidadEjecutora);
            autorizacionRolPersonaModel.agregar(autorizacionRolPersona);

            usuario.setMarcado(true);

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    /**
     * Elimina el usuario
     *
     * @param pEvent
     */
    public void eliminar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se busca el objeto para eliminar
            SegUsuario usuario = (SegUsuario) pEvent.getComponent().getAttributes().get("usuarioSelRech");
            AutorizacionRolPersona autorizacionRolPersona = autorizacionRolPersonaModel.buscarPorRolAutorizacionUsuario(command.getIdRol(), command.getIdAutorizacionTipoProceso(), usuario.getIdUsuario());
            autorizacionRolPersonaModel.eliminar(autorizacionRolPersona);
            usuario.setMarcado(false);

            //Se verifica si la autorizacion rol tiene usuarios asociados
            if (autorizacionRolPersonaModel.contarPorAutorizacionRol(command.getIdAutorizacionTipoProceso(), command.getIdRol()) == 0) {
                AutorizacionRol autorizacionRol = autorizacionRolModel.buscarPorRolAutorizacion(command.getIdRol(), command.getIdAutorizacionTipoProceso());
                autorizacionRolModel.eliminar(autorizacionRol);
            }

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    /**
     * Contabiliza los usuarios
     */
    private void contarUsuarios() {
        try {

            //Se cuenta la cantidad de registros
            Long contador = segUsuarioModel.contarUsuarios(command.getIdUsuario(), command.getNombreCompleto(), command.getCorreo());

            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.contarUsuarios"));
        }
    }

    /**
     * Cambia el valor de alguno de los filtros
     *
     * @param pEvent
     */
    public void cambioFiltro(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            this.contarUsuarios();

            this.setPrimerRegistro(1);

            this.listarUsuarios();

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }

    }

    /**
     * Muestra el panel de acuerdo a la accion
     *
     * @param pEvent
     */
    public void mostrarPanel(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            //Se muestra el panel de acuerdo a la accion
            String accion = (String) pEvent.getComponent().getAttributes().get("accion");
            command.setAccion(Integer.parseInt(accion));
            command.setPresentarPanel(Boolean.TRUE);
            if (command.getPresentarPanelAgregarAutorizacion()) {
                command.setAutorizacion(new Autorizacion());
            } else if (command.getPresentarPanelModificarAutorizacion()) {
                command.setAutorizacion(autorizacionModel.buscarPorId(command.getIdAutorizacionTipoProceso()));
            } else if (command.getPresentarPanelAgregarRol()) {
                command.setRol(new Rol());
            } else if (command.getPresentarPanelModificarRol()) {
                command.setRol(rolModel.buscarPorId(command.getIdRol()));
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public void cerrarPanel() {
        try {
            command.setPresentarPanel(Boolean.FALSE);
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Paginacion">
    /**
     * Pasa a la pagina sub-set
     *
     * @param pEvent
     */
    public void irPagina(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPag"));
        this.getPrimerRegistroPagina(numeroPagina);
        this.listarUsuarios();
    }

    /**
     * Pasa al siguiente sub-set
     *
     * @param pEvent
     */
    public void siguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listarUsuarios();
    }

    /**
     * Pasa al anterior sub-set
     *
     * @param pEvent
     */
    public void anterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listarUsuarios();
    }

    /**
     * Pasa al primero sub-set
     *
     * @param pEvent
     */
    public void primero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listarUsuarios();
    }

    /**
     * Pasa al ultimo sub-set
     *
     * @param pEvent
     */
    public void ultimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listarUsuarios();
    }

    /**
     * Cambia la cantidad de registros por página
     *
     * @param pEvent
     */
    public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));
        this.setPrimerRegistro(1);
        this.listarUsuarios();
    }

    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Panel Popup Autorizacion">
    /**
     * Agregar el autorizacion a la base de datos
     */
    public void agregarAutorizacion() {
        try {
            Autorizacion autorizacion = command.getAutorizacion();
            autorizacion.setTipoProceso(tipoModel.buscarPorId(command.getIdTipoProceso()));
            autorizacion.setEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
            if (validarFormAutorizacion()) {
                autorizacionModel.agregar(autorizacion);
                autorizacionesTipoProceso.add(new SelectItem(autorizacion.getId().toString(), autorizacion.getNombre()));
                command.setPresentarPanel(Boolean.FALSE);
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    /**
     * modifica el autorizacion a la base de datos
     */
    public void modificarAutorizacion() {
        try {
            if (validarFormAutorizacion()) {
                autorizacionModel.modificar(command.getAutorizacion());
                JsfUtil.modificarItem(autorizacionesTipoProceso, command.getAutorizacion().getId().toString(), command.getAutorizacion().getNombre(), null);
                command.setPresentarPanel(Boolean.FALSE);
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public boolean validarFormAutorizacion() {
        //Validaciones de campos
        if (command.getAutorizacion().getNombre().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.nombre.requerido"));
            return false;
        }
        if (command.getAutorizacion().getDetalle().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.detalle.requerido"));
            return false;
        }
        if (!command.getAutorizacion().getOrden().toString().matches(Constantes.PATTERN_NUMERIC)) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.orden.no.numerico"));
            return false;
        } else if (command.getAutorizacion().getOrden() == null || command.getAutorizacion().getOrden() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.orden.requerido"));
            return false;
        }
        if (command.getAutorizacion().getEstado() == null) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.estado.requerido"));
            return false;
        }
        if (command.getAutorizacion().getTipoProceso()== null) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.proceso.requerido"));
            return false;
        }

        //Validaciones de datos
        if (command.getPresentarPanelAgregarAutorizacion()) {

            //Se verifica si ya existe un autorizacion con el nombre indicado
            if (autorizacionModel.contarAutorizacionsValidator(null, command.getIdTipoProceso(), null, command.getAutorizacion().getNombre()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un autorizacion con el orden indicado
            if (autorizacionModel.contarAutorizacionsValidator(null, command.getIdTipoProceso(), command.getAutorizacion().getOrden(), null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.orden.ya.existe"));
                return false;
            }
        } else if (command.getPresentarPanelModificarAutorizacion()) {
            //Se verifica si ya existe un autorizacion con el nombre indicado
            if (autorizacionModel.contarAutorizacionsValidator(command.getAutorizacion().getId(), command.getIdTipoProceso(), null, command.getAutorizacion().getNombre()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un autorizacion con el orden indicado
            if (autorizacionModel.contarAutorizacionsValidator(command.getAutorizacion().getId(), command.getIdTipoProceso(), command.getAutorizacion().getOrden(), null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.orden.ya.existe"));
                return false;
            }
        }
        return true;
    }

    /**
     * Elimina el autorizacion a la base de datos
     */
    public void eliminarAutorizacion() {
        try {
            if (validarFormEliminarAutorizacion()) {
                autorizacionModel.eliminar(command.getAutorizacion());
                autorizacionesTipoProceso = JsfUtil.eliminarItem(autorizacionesTipoProceso, command.getAutorizacion().getId().toString());
                command.setPresentarPanel(Boolean.FALSE);

                command.setIdAutorizacionTipoProceso(-1L);

                //Se busca los usuarios por rol y autorizacion
                this.buscaUsuariosAutorizacionRol();
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public boolean validarFormEliminarAutorizacion() {

        //Se valida que el autorizacion no este asociado a un rol
        if (autorizacionRolModel.contarPorAutorizacion(command.getAutorizacion().getId()) > 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.asociado.rol"));
            return false;
        }
        return true;
    }

    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Panel Popup Rol">
    /**
     * Agregar el rol a la base de datos
     */
    public void agregarRol() {
        try {
            Rol rol = command.getRol();
            rol.setEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
            if (validarFormRol()) {
                rolModel.agregar(rol);
                roles.add(new SelectItem(rol.getId(), rol.getNombre()));
                command.setPresentarPanel(Boolean.FALSE);
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    /**
     * modifica el rol a la base de datos
     */
    public void modificarRol() {
        try {
            if (validarFormRol()) {
                Rol rol = command.getRol();
                rolModel.modificar(rol);
                JsfUtil.modificarItem(roles, rol.getId().toString(), rol.getNombre(), null);
                command.setPresentarPanel(Boolean.FALSE);
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public boolean validarFormRol() {
        //Validaciones de campos
        if (command.getRol().getCodigo().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.rol.codigo.requerido"));
            return false;
        }
        if (command.getRol().getNombre().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.rol.nombre.requerido"));
            return false;
        }

        if (command.getPresentarPanelAgregarRol()) {

            //Se verifica si ya existe un rol con el nombre indicado
            if (rolModel.contarRolesValidator(null, null, command.getRol().getNombre()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un autorizacion con el codigo indicado
            if (rolModel.contarRolesValidator(null, command.getRol().getCodigo(), null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.codigo.ya.existe"));
                return false;
            }
        } else if (command.getPresentarPanelModificarRol()) {
            //Se verifica si ya existe un rol con el nombre indicado
            if (rolModel.contarRolesValidator(command.getRol().getId(), null, command.getRol().getNombre()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un rol con el codigo indicado
            if (rolModel.contarRolesValidator(command.getRol().getId(), command.getRol().getCodigo(), null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.codigo.ya.existe"));
                return false;
            }
        }
        return true;
    }

    /**
     * Elimina el rol a la base de datos
     */
    public void eliminarRol() {
        try {
            if (validarFormEliminarRol()) {
                Rol rol = command.getRol();
                roles = JsfUtil.eliminarItem(roles, rol.getId().toString());
                rolModel.eliminar(rol);

                command.setPresentarPanel(Boolean.FALSE);
                command.setIdRol(-1L);

                //Se busca los usuarios por rol y autorizacion
                this.buscaUsuariosAutorizacionRol();

            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public boolean validarFormEliminarRol() {

        if (rolModel.verificaRolUso(command.getRol().getId()) > 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.asociado"));
            return false;
        }
        return true;
    }
    //</editor-fold>

}
