/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.models.ExclusionModel;
import cr.ac.ucr.sigebi.commands.ExclusionCommand;
import cr.ac.ucr.sigebi.commands.ListarBienesCommand;
import cr.ac.ucr.sigebi.commands.ListarExclusionesCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.DocumentoInformeTecnico;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.RegistroMovimientoSolicitud;
import cr.ac.ucr.sigebi.domain.SolicitudExclusion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.DocumentoModel;
import cr.ac.ucr.sigebi.models.RegistroMovimientoModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerAgregarExclusiones")
@Scope("session")
public class AgregarExclusionController extends BaseController {

    private final int ACCION_RECHAZAR = 1;
    private final int ACCION_REVISAR = 2;
    private final int ACCION_RECHAZAR_BIEN = 3;
    
    public class ListadoBienes extends BaseController {
        
        private ListarBienesCommand command;
        private Estado estadoInternoNormal;
        private Estado estadoActivo;
        private Map<Long, Bien> allBienes;
        private Map<Long, Bien> bienes;
        private Map<Long, Boolean> bienesSeleccionados;

        public ListadoBienes() {
            super();
            this.command = new ListarBienesCommand();
            this.bienes = new HashMap<Long, Bien>();
            this.allBienes = new HashMap<Long, Bien>();
            this.bienesSeleccionados = new HashMap<Long, Boolean>();
        }

        public ListadoBienes(Estado estadoNormal, Estado estadoActivo) {
            this();
            this.estadoInternoNormal = estadoNormal;
            this.estadoActivo = estadoActivo;
        }
        
        private void inicializarListado() {
            this.setPrimerRegistro(1);
            this.contarBienes();
            this.listarBienes();
        }
        
        private void contarBienes() {
            try {
                Long contador = bienModel.contar(this.command.getFltIdCodigo(), this.unidadEjecutora, this.command.getFltIdentificacion(), this.command.getFltDescripcion(), this.command.getFltMarca(), this.command.getFltModelo(), this.command.getFltSerie(), this.estadoActivo, this.estadoInternoNormal);
                this.setCantidadRegistros(contador.intValue());
            } catch (FWExcepcion e) {
                Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
            }
        }

        private void listarBienes() {
            try {
                List<Bien> itemsBienes = bienModel.listar(this.getPrimerRegistro() - 1, this.getUltimoRegistro(), this.command.getFltIdCodigo(), this.unidadEjecutora , command.getFltIdentificacion(), command.getFltDescripcion(), command.getFltMarca(), command.getFltModelo(), command.getFltSerie(), this.estadoActivo, this.estadoInternoNormal);
                this.bienes.clear();
                for (Bien item : itemsBienes) {
                    this.allBienes.put(item.getId(), item);
                    this.bienes.put(item.getId(), item);
                }
           } catch (FWExcepcion e) {
               Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
           }
       }

        public void cambioFiltro(ValueChangeEvent event) {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            this.inicializarListado();
        }

        // <editor-fold defaultstate="collapsed" desc="Get's Set's">
        public List<Bien> getItemsBienes() {
            List<Bien> list = new ArrayList<Bien>(bienes.values());
            return list;
        }

        public ListarBienesCommand getCommand() {
            return command;
        }

        public void setCommand(ListarBienesCommand command) {
            this.command = command;
        }

        public Estado getEstadoInternoNormal() {
            return estadoInternoNormal;
        }

        public void setEstadoInternoNormal(Estado estadoInternoNormal) {
            this.estadoInternoNormal = estadoInternoNormal;
        }

        public Estado getEstadoActivo() {
            return estadoActivo;
        }

        public void setEstadoActivo(Estado estadoActivo) {
            this.estadoActivo = estadoActivo;
        }

        public Map<Long, Bien> getBienes() {
            return bienes;
        }

        public void setBienes(Map<Long, Bien> bienes) {
            this.bienes = bienes;
        }

        public Map<Long, Bien> getAllBienes() {
            return allBienes;
        }

        public void setAllBienes(Map<Long, Bien> allBienes) {
            this.allBienes = allBienes;
        }

        public Map<Long, Boolean> getBienesSeleccionados() {
            return bienesSeleccionados;
        }

        public void setBienesSeleccionados(Map<Long, Boolean> bienesSeleccionados) {
            this.bienesSeleccionados = bienesSeleccionados;
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Paginacion">
        public void irPagina(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPag"));
            this.getPrimerRegistroPagina(numeroPagina);
            this.listarBienes();
        }

        public void siguiente(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.getSiguientePagina();
            this.listarBienes();
        }

        public void anterior(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.getPaginaAnterior();
            this.listarBienes();
        }

        public void primero(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.setPrimerRegistro(1);
            this.listarBienes();
        }

        public void ultimo(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.getPrimerRegistroUltimaPagina();
            this.listarBienes();
        }

        public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));          
            this.setPrimerRegistro(1);
            this.listarBienes();
        }
        // </editor-fold>
    }
    
    ListadoBienes listadoBienes;
    
    @Resource private AutorizacionRolPersonaModel autorizacionRolPersonaModel;
    @Resource private BienModel bienModel;
    @Resource private DocumentoModel documentoModel;
    @Resource private ExclusionModel exclusionModel;
    @Resource private RegistroMovimientoModel registroMovimientoModel;
    
    private ExclusionCommand command;
    
    private List<SelectItem> itemsTipo;
    
    private String mensajeExito;
    private String mensaje;
    
    private boolean visiblePanelBienes;
    private boolean visiblePanelObservacion;
    private boolean visiblePanelConfirmacion;
    
    private boolean visibleBotonGuardar;
    private boolean visibleBotonAgregarBienes;
    
    private boolean visibleBotonEliminarBien;
    private boolean visibleBotonSolicitarBien;
    private boolean visibleBotonRechazarBien;
    
    private boolean visibleBotonSolicitar;
    private boolean visibleBotonRechazar;
    private boolean visibleBotonRevisar;
    private boolean visibleBotonAprobar;
    
    private boolean visibleBotonRechazarObservacion;
    private boolean visibleBotonRechazarBienObservacion;
    private boolean visibleBotonRevisarObservacion;
    
    private boolean autorizadoAprobar;
    
    private boolean exclusionRegistrada;
    
    private int accion;
    private Long bienSeleccionado;
    
    public AgregarExclusionController() {
        super();
    }
    
    private void inicializarNuevo() {
        Estado estadoExclusionCreada = this.estadoPorDominioValor(Constantes.DOMINIO_EXCLUSION, Constantes.ESTADO_EXCLUSION_CREADA);
        this.command = new ExclusionCommand(this.unidadEjecutora, estadoExclusionCreada, this.usuarioSIGEBI);
        this.exclusionRegistrada = false;
        this.autorizadoAprobar = false;
        inicializarDatos();
    }
    
    private void inicializarDetalle(SolicitudExclusion exclusion) {
        exclusion.setDetalles(exclusionModel.listarDetalles(exclusion));
        this.command = new ExclusionCommand(exclusion);
        this.exclusionRegistrada = true;
        this.autorizadoAprobar = inicializarAutorizaciones();
        inicializarDatos();
    }
    
    private boolean inicializarAutorizaciones() {
        Tipo tipo = this.tipoPorId(this.command.getIdTipo());
        int codigoAutorizacion;
        switch (tipo.getValor()) {
            case Constantes.TIPO_EXCLUSION_DESECHO:
                codigoAutorizacion = Constantes.CODIGO_AUTORIZACION_EXCLUSION_DESECHO;
            break;
            
            case Constantes.TIPO_EXCLUSION_DONACION:
                codigoAutorizacion = Constantes.CODIGO_AUTORIZACION_EXCLUSION_DONACION;
            break;
            
            default: 
                return true;
        }
        
        if (codigoAutorizacion != 0) {
            return autorizacionRolPersonaModel.buscarAutorizacion(codigoAutorizacion, this.unidadEjecutora, this.usuarioSIGEBI);
        }
        return false;
    }

    private void inicializarDatos() {
        Estado estadoInternoNormal = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        Estado estadoActivo = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_ACTIVO);
        this.listadoBienes = new ListadoBienes(estadoInternoNormal, estadoActivo);
        this.mensajeExito = new String();
        this.mensaje = new String();
        
        this.visiblePanelConfirmacion = false;
        this.visiblePanelObservacion = false;
        this.visiblePanelBienes = false;
        
        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_EXCLUSION);
        if (!tipos.isEmpty()) {
            this.itemsTipo = new ArrayList<SelectItem>();
            for (Tipo item : tipos) {
                this.itemsTipo.add(new SelectItem(item.getId(), item.getNombre()));
            }
        }
    }
    
    public void confirmarSolicitud() {
        this.visiblePanelConfirmacion = true;
    }
    
    public void cancelarSolicitud() {
        this.visiblePanelConfirmacion = false;
    }
    
    public void guardarDatos() {
        try {
            this.visiblePanelConfirmacion = false;
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            String messageValidacion = validarForm(root);
            if (Constantes.OK.equals(messageValidacion)) {
                Tipo tipo = this.tipoPorId(command.getIdTipo());
                
                // Almaceno o actualizo Solicitud
                // Se almacenan o actualizan los detalles tambien
                SolicitudExclusion exclusion = command.getExclusion(tipo);
                this.exclusionModel.salvar(exclusion);
                if (!command.getDetallesEliminar().isEmpty()) {
                    this.exclusionModel.eliminarDetalles(command.getDetallesEliminar());
                }
                
                List<Bien> listBienesAgregar = new ArrayList<Bien>();
                listBienesAgregar.addAll(this.command.getBienesAgregar());
                if (!listBienesAgregar.isEmpty()) {
                    this.bienModel.actualizar(listBienesAgregar);
                }
                
                List<Bien> listBienesEliminar = new ArrayList<Bien>(command.getBienesEliminar());
                if (!listBienesEliminar.isEmpty()) {
                    this.bienModel.actualizar(listBienesEliminar);
                }
                
                if (!this.exclusionRegistrada) {
                    this.exclusionRegistrada = true;
                    command.setIdExclusion(exclusion.getId());
                    mensajeExito = "Los datos se salvaron con éxito.";
                } else {
                    almacenarObservacion(tipo);
                    mensajeExito = "Los datos se actualizaron con éxito.";
                }                
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarExclusionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void nuevoRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            inicializarNuevo();
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_EXCLUSION_NUEVA);
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }
    
    public void detalleRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            Long id = (Long)event.getComponent().getAttributes().get(ListarExclusionesCommand.KEY_EXCLUSION);
            SolicitudExclusion exclusion = exclusionModel.buscarPorId(id);
            inicializarDetalle(exclusion);
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_EXCLUSION_NUEVA);
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }
    
    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen, true);
        } else {
            Util.navegar(Constantes.VISTA_EXCLUSION_LISTADO, true);
        }
    }    
    
    private void almacenarObservacion(Tipo tipo) {
        if (!command.getObservacionConfirmacion().isEmpty()) {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;

            RegistroMovimientoSolicitud registroMovimientoSolicitud = new RegistroMovimientoSolicitud(
                this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_SOLICITUD), 
                command.getObservacionConfirmacion(), 
                telefono, 
                new Date(), 
                usuarioSIGEBI, 
                command.getEstado(),
                command.getExclusion(tipo));
            registroMovimientoModel.agregar(registroMovimientoSolicitud);
        }
    }
    
    public void verDetalle(SolicitudExclusion exclusion, String vistaOrigen) {
        inicializarDetalle(exclusion);
        this.vistaOrigen = vistaOrigen;
        Util.navegar(Constantes.VISTA_EXCLUSION_NUEVA);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Validaciones">
    public String validarForm(UIViewRoot root) {
        if (command.getBienes().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.exclusiones.error.validacion.bienes");
        }
        if (command.getIdTipo().equals(Constantes.DEFAULT_ID)) {
            return Util.getEtiquetas("sigebi.label.exclusiones.error.validacion.tipo");
        }
        return Constantes.OK;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Movimientos sobre la Solicitud">
    public void solicitarExclusion() {
        movimientoExclusion(Constantes.ESTADO_EXCLUSION_SOLICITADA, Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_SOLICITADO, false);
    }
    
    public void rechazarExclusion() {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_RECHAZAR;
    }
    
    public void aprobarExclusion() {
        Tipo tipo = this.tipoPorId(this.command.getIdTipo());
        if (tipo.getValor().equals(Constantes.TIPO_EXCLUSION_DESECHO) || tipo.getValor().equals(Constantes.TIPO_EXCLUSION_DONACION)) {
            // Exclusiones por desecho o donacion generan Informes Tecnicos
            Estado estadoInformeTecnicoNuevo = this.estadoPorDominioValor(Constantes.DOMINIO_INFORME_TECNICO, Constantes.ESTADO_INFORME_TECNICO_NUEVO);
            List<Bien> listBienes = new ArrayList<Bien>(command.getBienes().values());
            for (Bien bien : listBienes) {
                DocumentoInformeTecnico dit = new DocumentoInformeTecnico(estadoInformeTecnicoNuevo, null, bien, "Creacion Automatica de Informe", unidadEjecutora);
                this.documentoModel.agregar(dit);
            }
            movimientoExclusion(Constantes.ESTADO_EXCLUSION_APROBADA, Constantes.ESTADO_INTERNO_BIEN_INFORME_TECNICO, false);
        } else {
            movimientoExclusion(Constantes.ESTADO_EXCLUSION_APROBADA, Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_APROBADO, true);
        }
    }

    public void revisarExclusion() {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_REVISAR;
    }

    public void cerrarPanelObservaciones() {
        this.visiblePanelObservacion = false;
    }
    
    public void rechazarExclusionObservacion() {
        this.visiblePanelObservacion = false;
        movimientoExclusion(Constantes.ESTADO_EXCLUSION_RECHAZADA, Constantes.ESTADO_INTERNO_BIEN_NORMAL, false);
    }
    
    public void revisarExclusionObservacion() {
        this.visiblePanelObservacion = false;
        movimientoExclusion(Constantes.ESTADO_EXCLUSION_CREADA, Constantes.ESTADO_INTERNO_BIEN_EXCLUSION, false);
    }
    
    private void movimientoExclusion(int solicitud, int bienInterno, boolean actualizarEstadoBien) {
        try {
            Tipo tipo = this.tipoPorId(this.command.getIdTipo());
            Estado estadoSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_EXCLUSION, solicitud);
            this.command.setEstado(estadoSolicitud);
            this.exclusionModel.salvar(this.command.getExclusion(tipo));
            
            // Actualiza todos los bienes con el estado segun el movimiento que se hizo a la solicitud
            Estado estadoInternoBien = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, bienInterno);
            Estado estadoBienInactivo = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_INACTIVO);
            Estado estadoBienPendiente = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR);
            List<Bien> listBienes = new ArrayList<Bien>(this.command.getBienes().values());
            
            boolean modificar;
            for (Bien bien : listBienes) {
                modificar = false;
                if (actualizarEstadoBien) {
                    if (bien.getCapitalizable()) {
                        bien.setEstado(estadoBienPendiente);                        
                    } else {
                        bien.setEstado(estadoBienInactivo);
                    }
                    modificar = true;
                }
                if (!estadoInternoBien.equals(bien.getEstadoInterno()) &&                                               // Si el bien tiene un estado diferente se actualiza
                    !Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_APROBADO.equals(bien.getEstadoInterno().getValor())) {    // Si esta aprobado no se modifica
                    bien.setEstadoInterno(estadoInternoBien);
                    modificar = true;
                }
                if (modificar) {
                    bienModel.actualizar(bien);
                }
            }
            almacenarObservacion(tipo);
            this.mensajeExito = "Exclusion procesada exitosamente.";
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarExclusionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Movimientos sobre Bienes">
    public void mostarPanelAgregarBienes() {
        this.listadoBienes.inicializarListado();
        this.visiblePanelBienes = true;
    }

    public void cerrarPanelAgregarBienes() {
        this.visiblePanelBienes = false;
        Estado estadoEnSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_EXCLUSION);
        
        if (!this.listadoBienes.bienesSeleccionados.isEmpty()) {
            for (Map.Entry<Long, Boolean> rowBien : this.listadoBienes.bienesSeleccionados.entrySet()) {
                Bien bien = this.listadoBienes.allBienes.get(rowBien.getKey());
                if (rowBien.getValue()) {
                    bien.setEstadoInterno(estadoEnSolicitud);
                    this.command.addBien(bien); //this.bienes.put(bien.getId(), bien);
                    this.command.getBienesAgregar().add(bien);
                } else if (this.command.getBienes().containsKey(bien.getId())){
                    this.command.getBienes().remove(bien.getId());    // Lo saco de la lista de bienes que se muestran en pantalla
                }
            }
        }
    }

    public void eliminarBien(ActionEvent event) {
        Estado estadoInternoNormal = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        Bien bien = this.listadoBienes.allBienes.containsKey(idBien) ? this.listadoBienes.allBienes.get(idBien) : this.command.getBien(idBien);
        
        bien.setEstadoInterno(estadoInternoNormal);
        this.command.getBienes().remove(idBien);    // Lo saco de la lista de bienes que se muestran en pantalla
        if (this.command.getDetalles().containsKey(bien.getId())) { // Si esta en la lista de detalles, es xq se trata de un detalle existente en la BD
            this.command.getBienesEliminar().add(bien); // Lo agrego a la lista de bienes a eliminar
        }
    }
    
    public void solicitarBien(ActionEvent event) {
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        movimientoBien(idBien, Constantes.ESTADO_EXCLUSION_SOLICITADA, Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_SOLICITADO);
    }
    
    public void rechazarBien(ActionEvent event) {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_RECHAZAR_BIEN;
        this.bienSeleccionado = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
    }

    public void rechazarBienObservacion() {
        this.visiblePanelObservacion = false;
        movimientoBien(this.bienSeleccionado, Constantes.ESTADO_EXCLUSION_RECHAZADA, Constantes.ESTADO_INTERNO_BIEN_NORMAL);        
    }
    
    private void movimientoBien(Long idBien, int solicitud, int bienInterno) {
        Bien bien = this.command.getBienes().get(idBien);
        Estado estadoBienInterno = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, bienInterno);
        Estado estadoSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_EXCLUSION, solicitud);
        
        bien.setEstadoInterno(estadoBienInterno);
        this.bienModel.actualizar(bien);
        
        // Si todos los bienes de la solicitud tienen el mismo estado, se modifica el estado de la solicitud
        if (verificarSolicitud(estadoBienInterno)) {
            Tipo tipo = this.tipoPorId(this.command.getIdTipo());
            this.command.setEstado(estadoSolicitud);
            this.exclusionModel.salvar(this.command.getExclusion(tipo));
        }
    }
    
    private boolean verificarSolicitud(Estado estado) {
         for (Bien bien : this.command.getBienes().values()) {
            if (!bien.getEstadoInterno().equals(estado)) {
                return false;
            }
        }
        return true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public ExclusionCommand getCommand() {
        return command;
    }

    public void setCommand(ExclusionCommand command) {
        this.command = command;
    }

    public Long getBienSeleccionado() {
        return bienSeleccionado;
    }

    public void setBienSeleccionado(Long bienSeleccionado) {
        this.bienSeleccionado = bienSeleccionado;
    }

    public List<SelectItem> getItemsTipo() {
        return itemsTipo;
    }

    public void setItemsTipo(List<SelectItem> itemsTipo) {
        this.itemsTipo = itemsTipo;
    }
    
    public String getMensajeExito() {
        return mensajeExito;
    }

    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
        
    public ListadoBienes getListadoBienes() {
        return listadoBienes;
    }

    public void setListadoBienes(ListadoBienes listadoBienes) {
        this.listadoBienes = listadoBienes;
    }
    
    public boolean isExclusionRegistrada() {
        return exclusionRegistrada;
    }

    public void setExclusionRegistrada(boolean exclusionRegistrada) {
        this.exclusionRegistrada = exclusionRegistrada;
    }

    public boolean isAutorizadoAprobar() {
        return autorizadoAprobar;
    }

    public void setAutorizadoAprobar(boolean autorizadoAprobar) {
        this.autorizadoAprobar = autorizadoAprobar;
    }
    
    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Visibilidad de Objetos">
    public boolean isVisiblePanelBienes() {
        return visiblePanelBienes;
    }

    public boolean isVisiblePanelObservacion() {
        return visiblePanelObservacion;
    }

    public boolean isVisiblePanelConfirmacion() {
        return visiblePanelConfirmacion;
    }

    public boolean isVisibleBotonSolicitar() {
        this.visibleBotonSolicitar = false;
        if (Constantes.ESTADO_EXCLUSION_CREADA.equals(this.command.getEstado().getValor()) && this.exclusionRegistrada) {
            this.visibleBotonSolicitar = true;
        }
        return visibleBotonSolicitar;
    }

    public boolean isVisibleBotonRechazar() {
        this.visibleBotonRechazar = false;
        if (Constantes.ESTADO_EXCLUSION_SOLICITADA.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonRechazar = true;
        }
        return visibleBotonRechazar;
    }

    public boolean isVisibleBotonAprobar() {
        this.visibleBotonAprobar = false;
        if (Constantes.ESTADO_EXCLUSION_SOLICITADA.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonAprobar = true;
        }
        return visibleBotonAprobar;
    }
   
    public boolean isVisibleBotonRevisar() {
        this.visibleBotonRevisar = false;
        if (Constantes.ESTADO_EXCLUSION_SOLICITADA.equals(this.command.getEstado().getValor())) {
            this.visibleBotonRevisar = true;
        }        
        return visibleBotonRevisar;
    }

    public boolean isVisibleBotonGuardar() {
        this.visibleBotonGuardar = false;
        if (Constantes.ESTADO_EXCLUSION_CREADA.equals(this.command.getEstado().getValor())) {
            this.visibleBotonGuardar = true;
        }
        return visibleBotonGuardar;
    }

    public boolean isVisibleBotonAgregarBienes() {
        this.visibleBotonAgregarBienes = false;
        if (Constantes.ESTADO_EXCLUSION_CREADA.equals(this.command.getEstado().getValor())) {
            this.visibleBotonAgregarBienes = true;
        }
        return visibleBotonAgregarBienes;
    }

    public boolean isVisibleBotonEliminarBien() {
        this.visibleBotonEliminarBien = false;
        if (Constantes.ESTADO_EXCLUSION_CREADA.equals(this.command.getEstado().getValor())) {
            this.visibleBotonEliminarBien = true;
        }
        return visibleBotonEliminarBien;
    }

    public boolean isVisibleBotonSolicitarBien() {
        this.visibleBotonSolicitarBien = false;
        if (Constantes.ESTADO_EXCLUSION_CREADA.equals(this.command.getEstado().getValor()) && this.exclusionRegistrada) {
            this.visibleBotonSolicitarBien = true;
        }
        return visibleBotonSolicitarBien;
    }

    public boolean isVisibleBotonRechazarBien() {
        this.visibleBotonRechazarBien = false;
        if (this.autorizadoAprobar) {
            this.visibleBotonRechazarBien = true;
        }
        if (Constantes.ESTADO_EXCLUSION_RECHAZADA.equals(this.command.getEstado().getValor())  || 
            Constantes.ESTADO_EXCLUSION_APROBADA.equals(this.command.getEstado().getValor())) {
            this.visibleBotonSolicitarBien = false;
        }
        return visibleBotonRechazarBien;
    }
    
    public boolean isVisibleBotonRechazarObservacion() {
        this.visibleBotonRechazarObservacion = false;
        if (this.accion == ACCION_RECHAZAR) {
            this.visibleBotonRechazarObservacion = true;    
        }
        return visibleBotonRechazarObservacion;
    }

    public boolean isVisibleBotonRechazarBienObservacion() {
        this.visibleBotonRechazarBienObservacion = false;
        if (this.accion == ACCION_RECHAZAR_BIEN) {
            this.visibleBotonRechazarBienObservacion = true;    
        }        
        return visibleBotonRechazarBienObservacion;
    }

    public boolean isVisibleBotonRevisarObservacion() {
        this.visibleBotonRevisarObservacion = false;
        if (this.accion == ACCION_REVISAR) {
            this.visibleBotonRevisarObservacion = true;    
        }
        return visibleBotonRevisarObservacion;
    }
    
    public void setVisiblePanelBienes(boolean visiblePanelBienes) {
        this.visiblePanelBienes = visiblePanelBienes;
    }

    public void setVisiblePanelObservacion(boolean visiblePanelObservacion) {
        this.visiblePanelObservacion = visiblePanelObservacion;
    }

    public void setVisiblePanelConfirmacion(boolean visiblePanelConfirmacion) {
        this.visiblePanelConfirmacion = visiblePanelConfirmacion;
    }

    public void setVisibleBotonSolicitar(boolean visibleBotonSolicitar) {
        this.visibleBotonSolicitar = visibleBotonSolicitar;
    }

    public void setVisibleBotonRechazar(boolean visibleBotonRechazar) {
        this.visibleBotonRechazar = visibleBotonRechazar;
    }

    public void setVisibleBotonAprobar(boolean visibleBotonAprobar) {
        this.visibleBotonAprobar = visibleBotonAprobar;
    }

    public void setVisibleBotonRevisar(boolean visibleBotonRevisar) {
        this.visibleBotonRevisar = visibleBotonRevisar;
    }
    
    public void setVisibleBotonGuardar(boolean visibleBotonGuardar) {
        this.visibleBotonGuardar = visibleBotonGuardar;
    }

    public void setVisibleBotonAgregarBienes(boolean visibleBotonAgregarBienes) {
        this.visibleBotonAgregarBienes = visibleBotonAgregarBienes;
    }

    public void setVisibleBotonEliminarBien(boolean visibleBotonEliminarBien) {
        this.visibleBotonEliminarBien = visibleBotonEliminarBien;
    }
    
    public void setVisibleBotonSolicitarBien(boolean visibleBotonSolicitarBien) {
        this.visibleBotonSolicitarBien = visibleBotonSolicitarBien;
    }
    
    public void setVisibleBotonRechazarBien(boolean visibleBotonRechazarBien) {
        this.visibleBotonRechazarBien = visibleBotonRechazarBien;
    }
    
    public void setVisibleBotonRechazarObservacion(boolean visibleBotonRechazarObservacion) {
        this.visibleBotonRechazarObservacion = visibleBotonRechazarObservacion;
    }

    public void setVisibleBotonRechazarBienObservacion(boolean visibleBotonRechazarBienObservacion) {
        this.visibleBotonRechazarBienObservacion = visibleBotonRechazarBienObservacion;
    }

    public void setVisibleBotonRevisarObservacion(boolean visibleBotonRevisarObservacion) {
        this.visibleBotonRevisarObservacion = visibleBotonRevisarObservacion;
    }
    //</editor-fold>
}

