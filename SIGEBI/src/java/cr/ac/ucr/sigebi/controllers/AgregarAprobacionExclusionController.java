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
import cr.ac.ucr.sigebi.commands.AprobacionExclusionCommand;
import cr.ac.ucr.sigebi.commands.ListarAprobacionesExclusionCommand;
import cr.ac.ucr.sigebi.commands.ListarBienesCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.DocumentoAprobacionExclusion;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.DocumentoModel;
import java.util.ArrayList;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerAgregarAprobacionExclusion")
@Scope("session")
public class AgregarAprobacionExclusionController extends BaseController {
    
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

        public ListadoBienes(Estado estadoInternoNormal, Estado estadoActivo) {
            this();
            this.estadoInternoNormal = estadoInternoNormal;
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
                List<Bien> itemsBienes = bienModel.listar(this.getPrimerRegistro() - 1, this.getUltimoRegistro(), this.command.getFltIdCodigo(), this.unidadEjecutora , this.command.getFltIdentificacion(), this.command.getFltDescripcion(), this.command.getFltMarca(), this.command.getFltModelo(), this.command.getFltSerie(), this.estadoActivo, this.estadoInternoNormal);
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
        public List<Bien> getItemsBien() {
            List<Bien> list = new ArrayList<Bien>(this.bienes.values());
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

        public Map<Long, Bien> getAllBienes() {
            return allBienes;
        }

        public void setAllBienes(Map<Long, Bien> allBienes) {
            this.allBienes = allBienes;
        }
        
        public Map<Long, Bien> getBienes() {
            return bienes;
        }

        public void setBienes(Map<Long, Bien> bienes) {
            this.bienes = bienes;
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
    
    private AprobacionExclusionCommand command;
    
    private String mensajeExito;
    private String mensaje;
    
    private boolean visiblePanelBienes;
    private boolean visiblePanelConfirmacion;
    
    private boolean visibleBotonGuardar;
    private boolean visibleBotonAgregarBienes;
    private boolean visibleBotonEliminarBien;
    
    private boolean visibleBotonRechazar;
    private boolean visibleBotonRechazarBien;
    private boolean visibleBotonAprobar;
    
    private boolean solicitudRegistrada;
    private boolean autorizadoAprobar;
    
    private Long bienSeleccionado;
    
    public AgregarAprobacionExclusionController() {
        super();
    }
    
    private void inicializarNuevo() {
        Estado estadoAprobacionProceso = this.estadoPorDominioValor(Constantes.DOMINIO_APROBACION_EXCLUSION, Constantes.ESTADO_APROBACION_EXCLUSION_PROCESO);
        this.command = new AprobacionExclusionCommand(this.unidadEjecutora, estadoAprobacionProceso);
        this.solicitudRegistrada = false;
        this.autorizadoAprobar = false;        
        inicializarDatos();
    }
    
    private void inicializarDetalle(DocumentoAprobacionExclusion documento) {
        documento.setDetallesDocumento(documentoModel.listarDetalles(documento));
        this.command = new AprobacionExclusionCommand(documento);
        this.solicitudRegistrada = true;
        this.autorizadoAprobar = inicializarAutorizaciones();
        inicializarDatos();
    }

    private boolean inicializarAutorizaciones() {
        int codigoAutorizacion = Constantes.CODIGO_AUTORIZACION_APROBAR_EXCLUSION_DESECHO;
        Boolean result = autorizacionRolPersonaModel.buscarAutorizacion(codigoAutorizacion, this.unidadEjecutora, this.usuarioSIGEBI);
        if (!result) {
            codigoAutorizacion = Constantes.CODIGO_AUTORIZACION_APROBAR_EXCLUSION_DONACION;
            result = autorizacionRolPersonaModel.buscarAutorizacion(codigoAutorizacion, this.unidadEjecutora, this.usuarioSIGEBI);
        }
        return result;
    }
    
    private void inicializarDatos() {
        Estado estadoInternoInformeTecnicoAprobado = estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_INFORME_TECNICO_APROBADO);
        Estado estadoActivo = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_ACTIVO);
        this.listadoBienes = new ListadoBienes(estadoInternoInformeTecnicoAprobado, estadoActivo);

        this.mensajeExito = new String();
        this.mensaje = new String();
        
        this.visiblePanelConfirmacion = false;
        this.visiblePanelBienes = false;        
    }
    
    public void guardarDatos() {
        try {
            this.visiblePanelConfirmacion = false;
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            String messageValidacion = validarForm(root);
            if (Constantes.OK.equals(messageValidacion)) {
               
                // Almaceno o actualizo Solicitud
                // Se almacenan o actualizan los detalles tambien
                DocumentoAprobacionExclusion documento = this.command.getDocumentoAprobacion();
                this.documentoModel.agregarConDetalles(documento);
                if (!this.command.getDetallesEliminar().isEmpty()) {
                    this.documentoModel.eliminarDetalles(this.command.getDetallesEliminar());
                }
                
                // Acualizo el estado de bienes que se agregaron
                List<Bien> listBienesAgregar = new ArrayList<Bien>();
                listBienesAgregar.addAll(this.command.getBienesAgregar());
                if (!listBienesAgregar.isEmpty()) {
                    this.bienModel.actualizar(listBienesAgregar);
                }
                
                // Acualizo el estado de bienes que se eliminaron
                List<Bien> listBienesEliminar = new ArrayList<Bien>(this.command.getBienesEliminar());
                if (!listBienesEliminar.isEmpty()) {
                    this.bienModel.actualizar(listBienesEliminar);
                }
                
                if (!this.solicitudRegistrada) {
                    this.solicitudRegistrada = true;
                    this.command.setId(documento.getId());
                    this.mensajeExito = "Los datos se salvaron con éxito.";
                } else {
                    this.mensajeExito = "Los datos se actualizaron con éxito.";
                }
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarAprobacionExclusionController.class.getName()).log(Level.SEVERE, null, ex);
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
            Util.navegar(Constantes.KEY_VISTA_APROBACION_NUEVA);
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    public void detalleRegistro(ActionEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            Long id = (Long)event.getComponent().getAttributes().get(ListarAprobacionesExclusionCommand.KEY_DOCUMENTO);
            DocumentoAprobacionExclusion documento = documentoModel.buscarPorId(id);
            inicializarDetalle(documento);
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.KEY_VISTA_APROBACION_NUEVA);
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }

    public void verDetalle(DocumentoAprobacionExclusion documento, String vistaOrigen) {
        
        try{
            this.inicializarDetalle(documento);

            this.vistaOrigen = vistaOrigen;
            Util.navegar(Constantes.KEY_VISTA_APROBACION_NUEVA);

        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen, true);
        } else {
            Util.navegar(Constantes.KEY_VISTA_APROBACION_NUEVA, true);
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Validaciones">
    public String validarForm(UIViewRoot root) {
        if (command.getBienes().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.aprobacion.error.bienes");
        }
        return Constantes.OK;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Movimientos sobre la Solicitud">
    public void confirmarSolicitud() {
        this.visiblePanelConfirmacion = true;
    }
    
    public void cancelarSolicitud() {
        this.visiblePanelConfirmacion = false;
    }

    public void rechazarExclusion() {  // Abre ventana de confirmacion
        movimientoExclusion(Constantes.ESTADO_APROBACION_EXCLUSION_RECHAZADA, Constantes.ESTADO_INTERNO_BIEN_APROBACION_RECHAZADA);
    }
    
    public void aprobarExclusion() {  // Cambia bienes a estado aprobado
        movimientoExclusion(Constantes.ESTADO_APROBACION_EXCLUSION_APLICADA, Constantes.ESTADO_INTERNO_BIEN_APROBACION_APLICADA);
    }

    private void movimientoExclusion(int solicitud, int bienInterno) {
        try {
            Estado estadoSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_APROBACION_EXCLUSION, solicitud);
            this.command.setEstado(estadoSolicitud);
            this.documentoModel.agregar(command.getDocumentoAprobacion());
            
            // Actualiza todos los bienes con el estado segun el movimiento que se hizo a la solicitud
            Estado estadoInternoBien = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, bienInterno);
            List<Bien> listBienes = new ArrayList<Bien>(command.getListBienes());
            for (Bien bien : listBienes) {
                if (!estadoInternoBien.equals(bien.getEstadoInterno()) &&                                               // Si el bien tiene un estado diferente se actualiza
                    !Constantes.ESTADO_INTERNO_BIEN_APROBACION_RECHAZADA.equals(bien.getEstadoInterno().getValor())) {    // Si esta aprobado no se modifica
                    bien.setEstadoInterno(estadoInternoBien);
                    bienModel.actualizar(bien);
                }
            }
            this.visiblePanelConfirmacion = false;
            mensajeExito = "Solicitud procesada exitosamente.";
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
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
        Estado estadoEnSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_APROBACION_CREADA);
        
        if (!this.listadoBienes.bienesSeleccionados.isEmpty()) {
            for (Map.Entry<Long, Boolean> rowBien : this.listadoBienes.bienesSeleccionados.entrySet()) {
                Bien bien = this.listadoBienes.allBienes.get(rowBien.getKey());
                if (rowBien.getValue()) {
                    bien.setEstadoInterno(estadoEnSolicitud);
                    this.command.addBien(bien); //Detalles -- this.bienes.put(bien.getId(), new BienDetalle(bien));
                    this.command.getBienesAgregar().add(bien);  // Lista de bienes a los que se les debe actualizar el estado
                } else if (this.command.getBienes().containsKey(bien.getId())){
                    this.command.getBienes().remove(bien.getId());    // Lo saco de la lista de bienes que se muestran en pantalla
                }
            }
        }
    }

    public void eliminarBien(ActionEvent event) {
        Estado estadoInternoInformeTecnicoAprobado = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_INFORME_TECNICO_APROBADO);
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        Bien bien = this.listadoBienes.allBienes.containsKey(idBien) ? this.listadoBienes.allBienes.get(idBien) : this.command.getBien(idBien);
        this.command.getBienesAgregar().remove(bien); // Lo elimino de la lista de bienes a agregar
        
        bien.setEstadoInterno(estadoInternoInformeTecnicoAprobado);
        this.command.getBienes().remove(idBien);    // Lo saco de la lista de bienes que se muestran en pantalla
        if (this.command.getDetalles().containsKey(bien.getId())) { // Si esta en la lista de detalles, es xq se trata de un detalle existente en la BD
            this.command.getBienesEliminar().add(bien); // Lo agrego a la lista de bienes a eliminar
        }
    }
    
    public void rechazarBien(ActionEvent event) {
        movimientoBien(this.bienSeleccionado, Constantes.ESTADO_APROBACION_EXCLUSION_RECHAZADA, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
    }

    public void aprobarBien(ActionEvent event) {
        movimientoBien(this.bienSeleccionado, Constantes.ESTADO_APROBACION_EXCLUSION_APLICADA, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
    }

    private void movimientoBien(Long idBien, int solicitud, int bienInterno) {
        Bien bien = this.command.getBien(idBien);
        Estado estadoBienInterno = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, bienInterno);
        Estado estadoSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_APROBACION_EXCLUSION, solicitud);
        
        bien.setEstadoInterno(estadoBienInterno);
        this.bienModel.actualizar(bien);
        
        // Si todos los bienes de la solicitud tienen el mismo estado, se modifica el estado de la solicitud
        if (verificarSolicitud(estadoBienInterno)) {
            this.command.setEstado(estadoSolicitud);
            this.documentoModel.agregar(command.getDocumentoAprobacion());
        }
    }
    
    private boolean verificarSolicitud(Estado estado) {
         for (Bien bien : this.command.getListBienes()) {
            if (!bien.getEstadoInterno().equals(estado)) {
                return false;
            }
        }
        return true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
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

    public boolean isSolicitudRegistrada() {
        return solicitudRegistrada;
    }

    public void setSolicitudRegistrada(boolean solicitudRegistrada) {
        this.solicitudRegistrada = solicitudRegistrada;
    }

    public boolean isAutorizadoAprobar() {
        return autorizadoAprobar;
    }

    public void setAutorizadoAprobar(boolean autorizadoAprobar) {
        this.autorizadoAprobar = autorizadoAprobar;
    }

    public AprobacionExclusionCommand getCommand() {
        return command;
    }

    public void setCommand(AprobacionExclusionCommand command) {
        this.command = command;
    }
    
    public Long getBienSeleccionado() {
        return bienSeleccionado;
    }

    public void setBienSeleccionado(Long bienSeleccionado) {
        this.bienSeleccionado = bienSeleccionado;
    }
    
    public boolean isVisiblePanelBienes() {
        return visiblePanelBienes;
    }

    public void setVisiblePanelBienes(boolean visiblePanelBienes) {
        this.visiblePanelBienes = visiblePanelBienes;
    }

    public boolean isVisiblePanelConfirmacion() {
        return visiblePanelConfirmacion;
    }
    
    public void setVisiblePanelConfirmacion(boolean visiblePanelConfirmacion) {
        this.visiblePanelConfirmacion = visiblePanelConfirmacion;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Visibilidad de Objetos">
    public boolean isVisibleBotonRechazar() {
        this.visibleBotonRechazar = false;
        if (Constantes.ESTADO_APROBACION_EXCLUSION_PROCESO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonRechazar = true;
        }
        return visibleBotonRechazar;
    }
    
    public boolean isVisibleBotonRechazarBien() {
        this.visibleBotonRechazarBien = false;
        if (Constantes.ESTADO_APROBACION_EXCLUSION_PROCESO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonRechazarBien = true;
        }
        return visibleBotonRechazarBien;
    }

    public boolean isVisibleBotonAprobar() {
        this.visibleBotonAprobar = false;
        if (Constantes.ESTADO_APROBACION_EXCLUSION_PROCESO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonAprobar = true;
        }
        return visibleBotonAprobar;
    }
    
    public boolean isVisibleBotonGuardar() {
        this.visibleBotonGuardar = false;
        if (Constantes.ESTADO_APROBACION_EXCLUSION_PROCESO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonGuardar = true;
        }
        return visibleBotonGuardar;
    }

    public boolean isVisibleBotonAgregarBienes() {
        this.visibleBotonAgregarBienes = false;
        if (Constantes.ESTADO_APROBACION_EXCLUSION_PROCESO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonAgregarBienes = true;
        }
        return visibleBotonAgregarBienes;
    }

    public boolean isVisibleBotonEliminarBien() {
        this.visibleBotonEliminarBien = false;
        if (Constantes.ESTADO_APROBACION_EXCLUSION_PROCESO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonEliminarBien = true;
        }
        return visibleBotonEliminarBien;
    }
    
    

    public void setVisibleBotonRechazar(boolean visibleBotonRechazar) {
        this.visibleBotonRechazar = visibleBotonRechazar;
    }

    public void setVisibleBotonAprobar(boolean visibleBotonAprobar) {
        this.visibleBotonAprobar = visibleBotonAprobar;
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
    //</editor-fold>
}