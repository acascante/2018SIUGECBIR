/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

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
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.models.AutorizacionModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.RolModel;
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
    private UsuarioModel usuarioModel;

    @Resource
    private AutorizacionModel autorizacionModel;

    @Resource
    private RolModel rolModel;

    @Resource
    private AutorizacionRolModel autorizacionRolModel;

    @Resource
    private AutorizacionRolPersonaModel autorizacionRolPersonaModel;

    //Listas para agregas
    List<SelectItem> tiposProceso;
    List<SelectItem> autorizacionesTipoProceso;
    List<SelectItem> autorizacionesRol;
    List<Usuario> usuarios;

    GestionProcesoCommand command;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
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

    public List<SelectItem> getAutorizacionesRol() {
        return autorizacionesRol;
    }

    public void setAutorizacionesRol(List<SelectItem> autorizacionesRol) {
        this.autorizacionesRol = autorizacionesRol;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public GestionProcesoCommand getCommand() {
        return command;
    }

    public void setCommand(GestionProcesoCommand command) {
        this.command = command;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public GestionProcesosController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        try {
            //Se consultan los tipos por dominio, procesos
            List<Tipo> lista = this.tiposPorDominio(Constantes.DOMINIO_PROCESO);
            tiposProceso = new ArrayList<SelectItem>();
            for (Tipo item : lista) {
                tiposProceso.add(new SelectItem(item.getId().toString(), item.getNombre()));
            }
            command = new GestionProcesoCommand();
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.inicializar"));
        }
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

        try {
            //Se inicializa la lista de autorizaciones
            autorizacionesTipoProceso = null;
            autorizacionesRol = null;

            // Se obtiene el id del proceso
            Long valor = command.getTipoProceso().getIdTemporal();
            command.getAutorizacion().setIdTemporal(-1L);
            
            if (valor > 0) {

                //Se actualiza el tipo de proceso
                command.setTipoProceso(this.tipoPorId(valor));
                command.getTipoProceso().setIdTemporal(valor);                

                //Se cargan los autorizacions asociados al proceso
                List<Autorizacion> lista = autorizacionModel.buscarPorTipoProceso(command.getTipoProceso());
                autorizacionesTipoProceso = new ArrayList<SelectItem>();
                for (Autorizacion item : lista) {
                    autorizacionesTipoProceso.add(new SelectItem(item.getId().toString(), item.getNombre()));
                }
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.seleccionProceso"));
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
        try {
            
            //Se inicializa la lista de roles por autorizacion
            autorizacionesRol = null;

            // Se obtiene el id del autorizacion
            Long valor = command.getAutorizacion().getIdTemporal();
            if (valor > 0) {

                //Se actualiza la autorizacion
                command.setAutorizacion(autorizacionModel.buscarPorId(valor));
                command.getAutorizacion().setIdTemporal(valor);

                //Se cargan las autorizaciones rol, asociadas a la autorizacion
                List<AutorizacionRol> listaRoles = autorizacionRolModel.buscarPorAutorizacion(valor);
                autorizacionesRol = new ArrayList<SelectItem>();
                for (AutorizacionRol item : listaRoles) {
                    autorizacionesRol.add(new SelectItem(item.getId().toString(), item.getRol().getNombre()));
                }
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.seleccionAutorizacion"));
        }
    }

    /**
     * Se selecciona el rol
     *
     * @param event
     */
    public void seleccionRolAutorizacion(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        try {
            // Se obtiene el id del rol
            Long valor = command.getAutorizacionRol().getIdTemporal();
            if (valor > 0) {
                //Se actualiza el rol  
                command.setAutorizacionRol(autorizacionRolModel.buscarPorId(valor));
                command.getAutorizacionRol().setIdTemporal(valor);
            }

            //Se busca a los usuarios asociados al autorizacion y al rol
            this.buscaUsuariosAutorizacionRol();
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.seleccionRol"));
        }

    }

    private void buscaUsuariosAutorizacionRol() {
        try {
            usuarios = null;
            this.setCantidadRegistros(0);

            if (command.getAutorizacionRol() != null && command.getAutorizacionRol().getIdTemporal() > 0) {
                //Se buscan los usuarios       
                this.contarUsuarios();
                this.listarUsuarios();
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.buscaUsuariosAutorizacionRol"));
        }
    }

    /**
     * Lista los usuarios
     */
    private void listarUsuarios() {
        try {

            //Se cargan los usuario asociados al rol
            List<AutorizacionRolPersona> personasRolAutorizacion = null;
            if (command.getAutorizacionRol() != null) {
                personasRolAutorizacion = autorizacionRolPersonaModel.buscar(command.getAutorizacionRol(), unidadEjecutora);
            }

            //Se buscan los usuarios del sistema de acuerdo a los filtros
            this.usuarios = usuarioModel.listarUsuarios(command.getIdUsuario(), command.getNombreCompleto(), command.getCorreo(), this.getPrimerRegistro() - 1, this.getUltimoRegistro());

            //Se marcan aquellos que tengan una autorizacion registrada
            if (personasRolAutorizacion != null) {

                //Se seleccionan los usuarios asociados a los roles
                for (Usuario usuario : usuarios) {
                    for (AutorizacionRolPersona personaRol : personasRolAutorizacion) {
                        if (personaRol.getUsuarioSeguridad().getId().equals(usuario.getId())) {
                            usuario.setMarcado(true);
                        }
                    }
                }
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.listarUsuarios"));
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
            AutorizacionRol autorizacionRol = command.getAutorizacionRol();

            //Se busca el usuario
            Usuario usuario = (Usuario) pEvent.getComponent().getAttributes().get("usuarioSelApro");

            //Se incluye el usuario al rol
            AutorizacionRolPersona autorizacionRolPersona = new AutorizacionRolPersona();
            autorizacionRolPersona.setAutorizacionRol(autorizacionRol);
            autorizacionRolPersona.setUsuarioSeguridad(usuario);
            autorizacionRolPersona.setUnidadEjecutora(unidadEjecutora);
            autorizacionRolPersonaModel.agregar(autorizacionRolPersona);

            //Se marca el usuario como incluido
            usuario.setMarcado(true);

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.agregarUsuario"));
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
            Usuario usuario = (Usuario) pEvent.getComponent().getAttributes().get("usuarioSelRech");
            AutorizacionRolPersona autorizacionRolPersona = autorizacionRolPersonaModel.buscar(command.getAutorizacionRol(), usuario, unidadEjecutora);

            autorizacionRolPersonaModel.eliminar(autorizacionRolPersona);
            usuario.setMarcado(false);

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.eliminarUsuario"));
        }
    }

    /**
     * Contabiliza los usuarios
     */
    private void contarUsuarios() {

        try {
            //Se cuenta la cantidad de registros
            Long contador = usuarioModel.contarUsuarios(command.getIdUsuario(), command.getNombreCompleto(), command.getCorreo());

            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.contarUsuarios"));
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
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.cambioFiltro"));
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
                command.setAutorizacionNew(new Autorizacion());
            } else if (command.getPresentarPanelModificarAutorizacion()) {
                command.setAutorizacionNew(command.getAutorizacion());
            } else if (command.getPresentarPanelAgregarRol()) {
                command.setAutorizacionRolNew(new AutorizacionRol());
                command.getAutorizacionRolNew().setRol(new Rol());
            } else if (command.getPresentarPanelModificarRol()) {
                command.setAutorizacionRolNew(command.getAutorizacionRol());
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.mostrarPanel"));
        }
    }

    public void cerrarPanel() {
        try {
            command.setPresentarPanel(Boolean.FALSE);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.cerrarPanel"));
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

            Autorizacion autorizacion = command.getAutorizacionNew();
            autorizacion.setTipoProceso(command.getTipoProceso());
            autorizacion.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
            if (validarFormAutorizacion()) {
                autorizacionModel.agregar(autorizacion);
                autorizacionesTipoProceso.add(new SelectItem(autorizacion.getId().toString(), autorizacion.getNombre()));
                command.setPresentarPanel(Boolean.FALSE);
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.agregarAutorizacion"));
        }
    }

    /**
     * modifica el autorizacion a la base de datos
     */
    public void modificarAutorizacion() {
        try {
            if (validarFormAutorizacion()) {
                Autorizacion autorizacion = command.getAutorizacionNew();
                autorizacionModel.modificar(autorizacion);
                JsfUtil.modificarItem(autorizacionesTipoProceso, autorizacion.getId().toString(), autorizacion.getNombre(), null);
                command.setAutorizacion(autorizacion);
                command.setPresentarPanel(Boolean.FALSE);
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.modificarAutorizacion"));
        }
    }

    public boolean validarFormAutorizacion() {
        //Validaciones de campos
        if (command.getAutorizacionNew().getNombre().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.nombre.requerido"));
            return false;
        }
        if (command.getAutorizacionNew().getDetalle().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.detalle.requerido"));
            return false;
        }
        
        if (command.getAutorizacionNew().getOrden() == null || command.getAutorizacionNew().getOrden() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.orden.requerido"));
            return false;
        }else if (!command.getAutorizacionNew().getOrden().toString().matches(Constantes.PATTERN_NUMERIC)) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.orden.no.numerico"));
            return false;
        }
        
        if (command.getAutorizacionNew().getCodigo() == null || command.getAutorizacionNew().getOrden() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.codigo.requerido"));
            return false;
        }else if (!command.getAutorizacionNew().getCodigo().toString().matches(Constantes.PATTERN_NUMERIC)) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.codigo.no.numerico"));
            return false;
        } 
        
        if (command.getAutorizacionNew().getEstado() == null) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.estado.requerido"));
            return false;
        }
        if (command.getAutorizacionNew().getTipoProceso() == null) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.proceso.requerido"));
            return false;
        }

        //Validaciones de datos
        if (command.getPresentarPanelAgregarAutorizacion()) {

            //Se verifica si ya existe un autorizacion con el nombre indicado
            if (autorizacionModel.contarAutorizacionsValidator(null, null, null, command.getAutorizacionNew().getNombre(), null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un autorizacion con el orden indicado
            if (autorizacionModel.contarAutorizacionsValidator(null, null, command.getAutorizacionNew().getOrden(), null, null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.orden.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un autorizacion con el codigo indicado
            if (autorizacionModel.contarAutorizacionsValidator(null, null, null, null, command.getAutorizacionNew().getCodigo()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.codigo.ya.existe"));
                return false;
            }
        } else if (command.getPresentarPanelModificarAutorizacion()) {
            //Se verifica si ya existe un autorizacion con el nombre indicado
            if (autorizacionModel.contarAutorizacionsValidator(command.getAutorizacionNew().getId(), null, null, command.getAutorizacionNew().getNombre(), null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un autorizacion con el orden indicado
            if (autorizacionModel.contarAutorizacionsValidator(command.getAutorizacionNew().getId(),null, command.getAutorizacionNew().getOrden(), null, null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.orden.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un autorizacion con el codigo indicado
            if (autorizacionModel.contarAutorizacionsValidator(command.getAutorizacionNew().getId(), null, null, null, command.getAutorizacionNew().getCodigo()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.codigo.ya.existe"));
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
                autorizacionModel.eliminar(command.getAutorizacionNew());
                autorizacionesTipoProceso = JsfUtil.eliminarItem(autorizacionesTipoProceso, command.getAutorizacionNew().getId().toString());
                command.setPresentarPanel(Boolean.FALSE);
                command.setAutorizacion(new Autorizacion());

                //Se busca los usuarios por rol y autorizacion
                this.buscaUsuariosAutorizacionRol();
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.eliminarAutorizacion"));
        }
    }

    public boolean validarFormEliminarAutorizacion() {

        //Se valida que el autorizacion no este asociado a un rol
        if (autorizacionRolModel.contarPorAutorizacion(command.getAutorizacionNew().getId()) > 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.asociado.rol"));
            return false;
        }
        return true;
    }

    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Panel Popup Autorizacion Rol">
    /**
     * Agregar el rol a la base de datos
     */
    public void agregarRol() {
        try {
            
            AutorizacionRol autorizacionRol = command.getAutorizacionRolNew();
            if (validarFormRol()) {
                
                //Se agrega el rol
                autorizacionRol.getRol().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));            
                rolModel.agregar(autorizacionRol.getRol());

                //Se agrega la autorizacion rol
                autorizacionRol.setAutorizacion(command.getAutorizacion());
                autorizacionRolModel.agregar(autorizacionRol);

                //Se agrega a la lista
                autorizacionesRol.add(new SelectItem(autorizacionRol.getId(), autorizacionRol.getRol().getNombre()));

                //Se oculta el panel
                command.setPresentarPanel(Boolean.FALSE);                
            }            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.agregarRol"));
        }
    }

    /**
     * modifica el rol a la base de datos
     */
    public void modificarRol() {
        try {
            if (validarFormRol()) {
                AutorizacionRol autorizacionRol = command.getAutorizacionRolNew();
                rolModel.modificar(autorizacionRol.getRol());
                JsfUtil.modificarItem(autorizacionesRol, autorizacionRol.getId().toString(), autorizacionRol.getRol().getNombre(), null);
                command.setPresentarPanel(Boolean.FALSE);
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.modificarRol"));
        }
    }

    
    
    public boolean validarFormRol() {
        //Validaciones de campos
        if (command.getAutorizacionRolNew().getRol().getCodigo() == null || command.getAutorizacionRolNew().getRol().getCodigo() < 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.rol.codigo.requerido"));
            return false;
        }
        if (command.getAutorizacionRolNew().getRol().getNombre().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.autorizacion.rol.nombre.requerido"));
            return false;
        }

        if (command.getPresentarPanelAgregarRol()) {

            //Se verifica si ya existe un rol con el nombre indicado
            if (rolModel.contarRolesValidator(null, null, command.getAutorizacionRolNew().getRol().getNombre()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un autorizacion con el codigo indicado
            if (rolModel.contarRolesValidator(null, command.getAutorizacionRolNew().getRol().getCodigo(), null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.codigo.ya.existe"));
                return false;
            }
        } else if (command.getPresentarPanelModificarRol()) {
            //Se verifica si ya existe un rol con el nombre indicado
            if (rolModel.contarRolesValidator(command.getAutorizacionRolNew().getRol().getId(), null, command.getAutorizacionRolNew().getRol().getNombre()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un rol con el codigo indicado
            if (rolModel.contarRolesValidator(command.getAutorizacionRolNew().getRol().getId(), command.getAutorizacionRolNew().getRol().getCodigo(), null) > 0) {
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
                
                AutorizacionRol autorizacionRol = command.getAutorizacionRolNew();
                autorizacionesRol = JsfUtil.eliminarItem(autorizacionesRol, autorizacionRol.getId().toString());
                
                Rol rol = autorizacionRol.getRol();
                
                //Se elimina la autorizacion rol
                autorizacionRolModel.eliminar(autorizacionRol);
                
                //Se elimina el rol asociado a la autorizacion rol
                rolModel.eliminar(rol);

                //Se oculta
                command.setPresentarPanel(Boolean.FALSE);
                command.setAutorizacionRolNew(new AutorizacionRol());
                command.getAutorizacionRolNew().setRol(new Rol());
                
                //Se busca los usuarios por rol y autorizacion
                this.buscaUsuariosAutorizacionRol();

            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerGestionProcesos.eliminarRol"));
        }
    }

    public boolean validarFormEliminarRol() {

        if (rolModel.verificaRolUso(command.getAutorizacionRolNew().getRol().getId()) > 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.asociado"));
            return false;
        }
        return true;
    }
    //</editor-fold>

}
