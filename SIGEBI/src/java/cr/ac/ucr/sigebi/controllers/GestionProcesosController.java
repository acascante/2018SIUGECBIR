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
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.entities.DocumentoEntity;
import cr.ac.ucr.sigebi.entities.DocumentoRolEntity;
import cr.ac.ucr.sigebi.entities.DocumentoRolPersonaEntity;
import cr.ac.ucr.sigebi.entities.RolEntity;
import cr.ac.ucr.sigebi.entities.TipoEntity;
import cr.ac.ucr.sigebi.entities.UsuarioEntity;
import cr.ac.ucr.sigebi.models.DocumentoModel;
import cr.ac.ucr.sigebi.models.DocumentoRolModel;
import cr.ac.ucr.sigebi.models.DocumentoRolPersonaModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.RolModel;
import cr.ac.ucr.sigebi.models.SegUsuarioModel;
import cr.ac.ucr.sigebi.models.TipoModel;
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
public class GestionProcesosController extends BaseController{
    
    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    
    @Resource
    private TipoModel tipoModel;
    @Resource
    private UsuarioModel usuarioModel;

    @Resource
    private DocumentoModel documentoModel;

    @Resource
    private RolModel rolModel;

    @Resource
    private DocumentoRolModel documentoRolModel;

    @Resource
    private DocumentoRolPersonaModel documentoRolPersonaModel;

    @Resource
    private SegUsuarioModel segUsuarioModel;

    @Resource
    private EstadoModel estadoModel;
    
    //Listas para agregas
    List<SelectItem> tiposProceso;    
    List<SelectItem> documentosTipoProceso;    
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

    public DocumentoModel getDocumentoModel() {
        return documentoModel;
    }

    public void setDocumentoModel(DocumentoModel documentoModel) {
        this.documentoModel = documentoModel;
    }

    public DocumentoRolModel getDocumentoRolModel() {
        return documentoRolModel;
    }

    public void setDocumentoRolModel(DocumentoRolModel documentoRolModel) {
        this.documentoRolModel = documentoRolModel;
    }

    public DocumentoRolPersonaModel getDocumentoRolPersonaModel() {
        return documentoRolPersonaModel;
    }

    public void setDocumentoRolPersonaModel(DocumentoRolPersonaModel documentoRolPersonaModel) {
        this.documentoRolPersonaModel = documentoRolPersonaModel;
    }

    public SegUsuarioModel getSegUsuarioModel() {
        return segUsuarioModel;
    }

    public void setSegUsuarioModel(SegUsuarioModel segUsuarioModel) {
        this.segUsuarioModel = segUsuarioModel;
    }

    public List<SelectItem> getTiposProceso() {
        return tiposProceso;
    }

    public void setTiposProceso(List<SelectItem> tiposProceso) {
        this.tiposProceso = tiposProceso;
    }

    public List<SelectItem> getDocumentosTipoProceso() {
        return documentosTipoProceso;
    }

    public void setDocumentosTipoProceso(List<SelectItem> documentosTipoProceso) {
        this.documentosTipoProceso = documentosTipoProceso;
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
    public RolModel getRolModel() {
        return rolModel;
    }

    public void setRolModel(RolModel rolModel) {
        this.rolModel = rolModel;
    }

    public EstadoModel getEstadoModel() {
        return estadoModel;
    }

    public void setEstadoModel(EstadoModel estadoModel) {
        this.estadoModel = estadoModel;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    
    public GestionProcesosController() {
        super();
    }
    
    @PostConstruct
    public final void inicializar() {
        
       //Se consultan los tipos por dominio, procesos
       List<Tipo> lista = tipoModel.listarPorDominio(Constantes.DOMINI0_TIPO_PROCESO);
       tiposProceso = new ArrayList<SelectItem>();
       for (Tipo item : lista) {
           tiposProceso.add(new SelectItem(item.getIdTipo().toString(), item.getNombre()));
       } 
       
       //Se consultan los roles de la aplicacion
       List<RolEntity> listaRoles = rolModel.listarTodos();
       roles = new ArrayList<SelectItem>();
       for (RolEntity item : listaRoles) {
           roles.add(new SelectItem(item.getIdRol().toString(), item.getNombre()));
       }
       
       command = new GestionProcesoCommand();
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    
    /**
     * Se seleccionan el proceso
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
        
        //Se inicializa el documento y usuarios
        command.setIdDocumentoTipoProceso(-1L);
        documentosTipoProceso = null;
        
        //Se busca a los usuarios asociados al documento y al rol
        buscaUsuariosDocumentoRol();

        if(valor > 0){
            //Se cargan los documentos asociados al proceso
            List<DocumentoEntity> lista = documentoModel.buscarPorTipoProceso(valor);
            documentosTipoProceso = new ArrayList<SelectItem>();
            for (DocumentoEntity item : lista) {
                documentosTipoProceso.add(new SelectItem(item.getIdDocumento().toString(), item.getNombre()));
            }
        }        
    }
    
    /**
     * Se seleccionan el documento
     * @param event
     */    
    public void seleccionDocumento(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        // Se obtiene el id del documento
        Long valor = Long.parseLong(event.getNewValue().toString());
        command.setIdDocumentoTipoProceso(valor);

        //Se busca a los usuarios asociados al documento y al rol
        this.buscaUsuariosDocumentoRol();
    }
            
    /**
     * Se selecciona el rol
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
        
        //Se busca a los usuarios asociados al documento y al rol
        this.buscaUsuariosDocumentoRol();
    }
    
    private void buscaUsuariosDocumentoRol(){
        usuarios = null;
        this.setCantidadRegistros(0);
        if(command.getIdRol() > 0 && command.getIdDocumentoTipoProceso() > 0){
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
            List<DocumentoRolPersonaEntity> personasRolDocumento = documentoRolPersonaModel.buscarPorDocumentoRol(command.getIdDocumentoTipoProceso(), command.getIdRol());

            //Se buscan los usuarios
            this.usuarios = segUsuarioModel.listarUsuarios(command.getIdUsuario(), command.getNombreCompleto(), command.getCorreo(),
                    this.getPrimerRegistro() - 1, this.getUltimoRegistro());
            
            //Se seleccionan los usuarios asociados a los roles
            for (SegUsuario usuario : usuarios) {                
                for (DocumentoRolPersonaEntity personaRol : personasRolDocumento) {
                    if(personaRol.getIdUsuarioSeguridad().getIdUsuario().equals(usuario.getIdUsuario())){
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
     * @param pEvent
     */    
    public void agregar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            //Se busca el rol documento, si no existe se crea
            DocumentoRolEntity documentoRolEntity = documentoRolModel.buscarPorRolDocumento(command.getIdRol(), command.getIdDocumentoTipoProceso());
            if(documentoRolEntity == null){
                documentoRolEntity = new DocumentoRolEntity();
                documentoRolEntity.setIdRol(rolModel.buscarPorId(command.getIdRol()));
                documentoRolEntity.setIdDocumento(documentoModel.buscarPorId(command.getIdDocumentoTipoProceso()));  
                documentoRolModel.agregar(documentoRolEntity);
            }
            
            SegUsuario usuario = (SegUsuario) pEvent.getComponent().getAttributes().get("usuarioSelApro");        
            
            UsuarioEntity usr = usuarioModel.buscarPorId(usuario.getIdUsuario());
            //Se incluye el usuario al rol
            DocumentoRolPersonaEntity documentoRolPersona = new DocumentoRolPersonaEntity();
            documentoRolPersona.setIdDocumento(documentoRolEntity.getIdDocumento());
            documentoRolPersona.setIdRol(documentoRolEntity.getIdRol());
            documentoRolPersona.setIdUsuarioSeguridad(usr);            
            documentoRolPersona.setNumUnidadEjec(unidadEjecutora);
            documentoRolPersonaModel.agregar(documentoRolPersona);

            usuario.setMarcado(true);
                    
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    /**
     * Elimina el usuario
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
            DocumentoRolPersonaEntity documentoRolPersonaEntity =  documentoRolPersonaModel.buscarPorRolDocumentoUsuario(command.getIdRol(), command.getIdDocumentoTipoProceso(), usuario.getIdUsuario());
            documentoRolPersonaModel.eliminar(documentoRolPersonaEntity); 
            usuario.setMarcado(false);
            
            //Se verifica si el documento rol tiene usuarios asociados
            if (documentoRolModel.contarPorDocumento(documentoRolPersonaModel.contarPorDocumentoRol(command.getIdDocumentoTipoProceso(), command.getIdRol())) == 0){
                DocumentoRolEntity documentoRolEntity = documentoRolModel.buscarPorRolDocumento(command.getIdRol(), command.getIdDocumentoTipoProceso());
                documentoRolModel.eliminar(documentoRolEntity);
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
            if(command.getPresentarPanelAgregarDocumento()){
                command.setDocumentoEntity(new DocumentoEntity());
            }else if(command.getPresentarPanelModificarDocumento()){
                command.setDocumentoEntity(documentoModel.buscarPorId(command.getIdDocumentoTipoProceso()));                
            }else if(command.getPresentarPanelAgregarRol()){
                command.setRolEntity(new RolEntity());                
            }else if(command.getPresentarPanelModificarRol()){
                command.setRolEntity(rolModel.buscarPorId(command.getIdRol()));
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
    
    // <editor-fold defaultstate="collapsed" desc="Panel Popup Documento">
    
    /**
     * Agregar el documento a la base de datos 
     */    
    public void agregarDocumento() {
        try {
            DocumentoEntity documentoEntity = command.getDocumentoEntity();
            //TODO verificar si la busqueda debe ser ID
            documentoEntity.setIdProceso(tipoModel.buscarPorId(command.getIdTipoProceso()));
            documentoEntity.setIdEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINI0_ESTADO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
            if(validarFormDocumento()){
                documentoModel.agregar(documentoEntity);
                documentosTipoProceso.add(new SelectItem(documentoEntity.getIdDocumento().toString(), documentoEntity.getNombre()));
                command.setPresentarPanel(Boolean.FALSE);
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    /**
     * modifica el documento a la base de datos 
     */   
    public void modificarDocumento() {
        try {
            if(validarFormDocumento()){
                documentoModel.modificar(command.getDocumentoEntity()); 
                JsfUtil.modificarItem(documentosTipoProceso, command.getDocumentoEntity().getIdDocumento().toString(), command.getDocumentoEntity().getNombre(), null);
                command.setPresentarPanel(Boolean.FALSE);
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    public boolean validarFormDocumento(){
        //Validaciones de campos
        if (command.getDocumentoEntity().getNombre().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.nombre.requerido"));
            return false;
        }
        if (command.getDocumentoEntity().getDetalle().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.detalle.requerido"));
            return false;
        } 
        if (!command.getDocumentoEntity().getOrden().toString().matches(Constantes.PATTERN_NUMERIC)) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.orden.no.numerico"));
            return false;
        }else if (command.getDocumentoEntity().getOrden() == null || command.getDocumentoEntity().getOrden() <= 0 ) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.orden.requerido"));
            return false;
        }        
        if (command.getDocumentoEntity().getIdEstado() == null ) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.estado.requerido"));
            return false;
        }
        if (command.getDocumentoEntity().getIdProceso() == null ) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.proceso.requerido"));
            return false;
        }
        
        //Validaciones de datos
        //FIXME Jairo validar error numerico
        if(command.getPresentarPanelAgregarDocumento()){
            
            //Se verifica si ya existe un documento con el nombre indicado
            if (documentoModel.contarDocumentosValidator(null, command.getIdTipoProceso(), null, command.getDocumentoEntity().getNombre()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un documento con el orden indicado
            if (documentoModel.contarDocumentosValidator(null, command.getIdTipoProceso(), command.getDocumentoEntity().getOrden(), null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.orden.ya.existe"));
                return false;
            }    
        }else if(command.getPresentarPanelModificarDocumento()){
             //Se verifica si ya existe un documento con el nombre indicado
            if (documentoModel.contarDocumentosValidator(command.getDocumentoEntity().getIdDocumento(), command.getIdTipoProceso(), null, command.getDocumentoEntity().getNombre()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un documento con el orden indicado
            if (documentoModel.contarDocumentosValidator(command.getDocumentoEntity().getIdDocumento(), command.getIdTipoProceso(), command.getDocumentoEntity().getOrden(), null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.orden.ya.existe"));
                return false;
            } 
        }        
        return true;        
    }
    
    /**
     * Elimina el documento a la base de datos 
     */   
    public void eliminarDocumento() {
        try {
            if(validarFormEliminarDocumento()){
                documentoModel.eliminar(command.getDocumentoEntity());
                documentosTipoProceso = JsfUtil.eliminarItem(documentosTipoProceso, command.getDocumentoEntity().getIdDocumento().toString());
                command.setPresentarPanel(Boolean.FALSE);
                
                command.setIdDocumentoTipoProceso(-1L);
                
                //Se busca los usuarios por rol y documento
                this.buscaUsuariosDocumentoRol();                
            }            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    public boolean validarFormEliminarDocumento(){
        
        //Se valida que el documento no este asociado a un rol
        if (documentoRolModel.contarPorDocumento(command.getDocumentoEntity().getIdDocumento()) > 0){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.asociado.rol"));
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
            RolEntity rolEntity = command.getRolEntity();
            rolEntity.setIdEstado(estadoModel.buscarPorDominioEstado(Constantes.DOMINI0_ESTADO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
            if(validarFormRol()){
                rolModel.agregar(rolEntity);            
                roles.add(new SelectItem(rolEntity.getIdRol(), rolEntity.getNombre()));
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
            if(validarFormRol()){
                RolEntity rolEntity = command.getRolEntity();
                rolModel.modificar(rolEntity); 
                JsfUtil.modificarItem(roles, rolEntity.getIdRol().toString(), rolEntity.getNombre(), null);
                command.setPresentarPanel(Boolean.FALSE);
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    public boolean validarFormRol(){
        //Validaciones de campos
        if (command.getRolEntity().getCodigo().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.rol.codigo.requerido"));
            return false;
        }
        if (command.getRolEntity().getNombre().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.documento.rol.nombre.requerido"));
            return false;
        }
        
        if(command.getPresentarPanelAgregarRol()){
            
            //Se verifica si ya existe un rol con el nombre indicado
            if (rolModel.contarRolesValidator(null, null, command.getRolEntity().getNombre()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un documento con el codigo indicado
            if (rolModel.contarRolesValidator(null, command.getRolEntity().getCodigo(), null) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.codigo.ya.existe"));
                return false;
            }    
        }else if(command.getPresentarPanelModificarRol()){
             //Se verifica si ya existe un rol con el nombre indicado
            if (rolModel.contarRolesValidator(command.getRolEntity().getIdRol(), null, command.getRolEntity().getNombre()) > 0) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.nombre.ya.existe"));
                return false;
            }

            //Se verifica si ya existe un rol con el codigo indicado
            if (rolModel.contarRolesValidator(command.getRolEntity().getIdRol(), command.getRolEntity().getCodigo(), null) > 0) {
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
            if(validarFormEliminarRol()){
                 RolEntity rolEntity = command.getRolEntity();
                 roles = JsfUtil.eliminarItem(roles, rolEntity.getIdRol().toString());
                 rolModel.eliminar(rolEntity); 

                 command.setPresentarPanel(Boolean.FALSE);
                 command.setIdRol(-1L);

                 //Se busca los usuarios por rol y documento
                 this.buscaUsuariosDocumentoRol();                

            }            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    public boolean validarFormEliminarRol(){
        
        if (rolModel.verificaRolUso(command.getRolEntity().getIdRol()) > 0){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerGestionProcesos.rol.asociado"));
            return false;
        }        
        return true;        
    }
    //</editor-fold>
    
}
