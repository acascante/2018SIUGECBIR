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
import cr.ac.ucr.sigebi.commands.ListarBienesCommand;
import cr.ac.ucr.sigebi.commands.ListarMantenimientosCommand;
import cr.ac.ucr.sigebi.commands.MantenimientoCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Evento;
import cr.ac.ucr.sigebi.domain.RegistroMovimientoSolicitud;
import cr.ac.ucr.sigebi.domain.SolicitudMantenimiento;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.EventoModel;
import cr.ac.ucr.sigebi.models.RegistroMovimientoModel;
import cr.ac.ucr.sigebi.models.SolicitudMantenimientoModel;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerAgregarMantenimientos")
@Scope("session")
public class AgregarMantenimientoController extends BaseController {

    private final int ACCION_SOLICITAR_CORRECCION = 1;
    private final int ACCION_RECHAZAR = 2;
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
    
    public class ListadoEventos extends BaseController {
        
        private List<Evento> eventos;
        private Long idDetalle;
        private Double costoTotal;

        public ListadoEventos() {
            super();
            this.eventos = new ArrayList<Evento>();
        }

        public ListadoEventos(Long idDetalle) {
            this();
            this.idDetalle = idDetalle;
        }

        private void inicializarListado() {
            this.setPrimerRegistro(1);
            this.contarEventos();
            this.listarEventos();
            this.costoTotalEventos();
        }
        
        private void costoTotalEventos() {
            try {
                this.costoTotal = eventoModel.totalCosto(idDetalle);
            } catch (FWExcepcion e) {
                Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
            }
        }
        
        private void contarEventos() {
            try {
                Long contador = eventoModel.contar(idDetalle);
                this.setCantidadRegistros(contador.intValue());
            } catch (FWExcepcion e) {
                Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
            }
        }

        private void listarEventos() {
            try {
                this.eventos = eventoModel.listar(this.getPrimerRegistro() - 1, this.getUltimoRegistro(), idDetalle);
           } catch (FWExcepcion e) {
               Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
           }
       }

        // <editor-fold defaultstate="collapsed" desc="Get's Set's">
        public List<Evento> getEventos() {
            return eventos;
        }

        public void setEventos(List<Evento> eventos) {
            this.eventos = eventos;
        }

        public Long getIdDetalle() {
            return idDetalle;
        }

        public void setIdDetalle(Long idDetalle) {
            this.idDetalle = idDetalle;
        }

        public Double getCostoTotal() {
            return costoTotal;
        }

        public void setCostoTotal(Double costoTotal) {
            this.costoTotal = costoTotal;
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
            this.listarEventos();
        }

        public void siguiente(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.getSiguientePagina();
            this.listarEventos();
        }

        public void anterior(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.getPaginaAnterior();
            this.listarEventos();
        }

        public void primero(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.setPrimerRegistro(1);
            this.listarEventos();
        }

        public void ultimo(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.getPrimerRegistroUltimaPagina();
            this.listarEventos();
        }

        public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));          
            this.setPrimerRegistro(1);
            this.listarEventos();
        }
        // </editor-fold>
    }
    
    ListadoBienes listadoBienes;
    ListadoEventos listadoEventos;
    
    @Resource private AutorizacionRolPersonaModel autorizacionRolPersonaModel;
    @Resource private BienModel bienModel;
    @Resource private EventoModel eventoModel;
    @Resource private SolicitudMantenimientoModel mantenimientoModel;
    @Resource private RegistroMovimientoModel registroMovimientoModel;
    
    private MantenimientoCommand command;
    
    private String mensajeExito;
    private String mensaje;
    
    private boolean visiblePanelBienes;
    private boolean visiblePanelObservacion;
    private boolean visiblePanelConfirmacion;
    private boolean visiblePanelEvento;
    private boolean visiblePanelListadoEventos;
    
    private boolean visibleBotonGuardar;
    private boolean visibleBotonAgregarBienes;
    
    private boolean visibleBotonEliminarBien;
    private boolean visibleBotonAceptarBien;
    private boolean visibleBotonRechazarBien;
    private boolean visibleBotonAplicarBien;
    private boolean visibleBotonFinalizarBien;
    private boolean visibleBotonEventoBien;
    private boolean visibleBotonListarEvento;
    
    private boolean visibleBotonAnular;
    private boolean visibleBotonSolicitarCorreccion;    
    private boolean visibleBotonAceptar;
    private boolean visibleBotonRechazar;
    private boolean visibleBotonAplicar;
    private boolean visibleBotonFinalizar;
    
    private boolean visibleBotonRechazarObservacion;
    private boolean visibleBotonRechazarBienObservacion;
    private boolean visibleBotonSolicitarObservacion;
    
    private boolean autorizadoAprobar;
    
    private boolean solicitudRegistrada;
    
    private int accion;
    private Long bienSeleccionado;
    
    public AgregarMantenimientoController() {
        super();
    }
    
    private void inicializarNuevo() {
        Estado estadoNuevo = this.estadoPorDominioValor(Constantes.DOMINIO_SOLICITUD_MANTENIMIENTO, Constantes.ESTADO_MANTENIMIENTO_NUEVO);
        this.command = new MantenimientoCommand(this.unidadEjecutora, estadoNuevo, this.usuarioSIGEBI);
        this.solicitudRegistrada = false;
        this.autorizadoAprobar = false;
        inicializarDatos();
    }
    
    private void inicializarDetalle(SolicitudMantenimiento solicitud) {
        solicitud.setDetalles(mantenimientoModel.listarDetalles(solicitud));
        this.command = new MantenimientoCommand(solicitud);
        this.solicitudRegistrada = true;
        this.autorizadoAprobar = inicializarAutorizaciones();
        inicializarDatos();
    }
    
    private boolean inicializarAutorizaciones() {
        return autorizacionRolPersonaModel.buscarAutorizacion(Constantes.CODIGO_AUTORIZACION_MANTENIMIENTO, this.unidadEjecutora, this.usuarioSIGEBI);
    }

    private void inicializarDatos() {
        Estado estadoInternoNormal = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        Estado estadoActivo = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_ACTIVO);
        this.listadoBienes = new ListadoBienes(estadoInternoNormal, estadoActivo);
        this.listadoEventos = new ListadoEventos();
        this.mensajeExito = new String();
        this.mensaje = new String();
        
        this.visiblePanelConfirmacion = false;
        this.visiblePanelObservacion = false;
        this.visiblePanelBienes = false;
        this.visiblePanelEvento = false;
        this.visiblePanelListadoEventos = false;
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
                // Almaceno o actualizo Solicitud
                // Se almacenan o actualizan los detalles tambien
                SolicitudMantenimiento solicitud = command.getSolicitud();
                this.mantenimientoModel.salvar(solicitud);
                if (!command.getDetallesEliminar().isEmpty()) {
                    this.mantenimientoModel.eliminarDetalles(command.getDetallesEliminar());
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
                
                if (!this.solicitudRegistrada) {
                    this.solicitudRegistrada = true;
                    command.setId(solicitud.getId());
                    mensajeExito = "Los datos se salvaron con éxito.";
                } else {
                    almacenarObservacion();
                    mensajeExito = "Los datos se actualizaron con éxito.";
                }                
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
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
            Util.navegar(Constantes.KEY_VISTA_MANTENIMIENTO_NUEVO);
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
            
            Long id = (Long)event.getComponent().getAttributes().get(ListarMantenimientosCommand.KEY_MANTENIMIENTO);
            SolicitudMantenimiento solicitud = mantenimientoModel.buscarPorId(id);
            inicializarDetalle(solicitud);
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.KEY_VISTA_MANTENIMIENTO_NUEVO);
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }
    
    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen, true);
        } else {
            Util.navegar(Constantes.KEY_VISTA_MANTENIMIENTO_LISTADO, true);
        }
    }    
    
    private void almacenarObservacion() {
        if (!command.getObservacionConfirmacion().isEmpty()) {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;

            RegistroMovimientoSolicitud registroMovimientoSolicitud = new RegistroMovimientoSolicitud(
                this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_SOLICITUD), 
                command.getObservacionConfirmacion(), 
                telefono, 
                new Date(), 
                usuarioSIGEBI, 
                command.getEstado(),
                command.getSolicitud());
            registroMovimientoModel.agregar(registroMovimientoSolicitud);
        }
    }
    
    public void verDetalle(SolicitudMantenimiento solicitud, String vistaOrigen) {
        inicializarDetalle(solicitud);
        this.vistaOrigen = vistaOrigen;
        Util.navegar(Constantes.KEY_VISTA_MANTENIMIENTO_NUEVO);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Validaciones">
    public String validarForm(UIViewRoot root) {
        if (command.getBienes().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.solicitudes.error.validacion.bienes");
        }
        return Constantes.OK;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Movimientos sobre la Solicitud">
    public void solicitarCorreccionMantenimiento() {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_SOLICITAR_CORRECCION;
    }
    
    public void rechazarMantenimiento() {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_RECHAZAR;
    }
    
    public void cerrarPanelObservaciones() {
        this.visiblePanelObservacion = false;
    }
    
    public void solicitarCorreccionMantenimientoObservacion() {
        this.visiblePanelObservacion = false;
        movimientoSolicitud(Constantes.ESTADO_MANTENIMIENTO_CORRECCION_SOLICITADA, Constantes.ESTADO_INTERNO_BIEN_SOLICITUD_MANTENIMIENTO, 0);
    }
        
    public void rechazarMantenimientoObservacion() {
        this.visiblePanelObservacion = false;
        movimientoSolicitud(Constantes.ESTADO_MANTENIMIENTO_RECHAZADO, Constantes.ESTADO_INTERNO_BIEN_NORMAL, 0);
    }
    
    public void anularSolicitud() {
        movimientoSolicitud(Constantes.ESTADO_MANTENIMIENTO_ANULADO, Constantes.ESTADO_INTERNO_BIEN_NORMAL, 0);
    }

    public void aceptarSolicitud() {
        movimientoSolicitud(Constantes.ESTADO_MANTENIMIENTO_APROBADO, Constantes.ESTADO_INTERNO_BIEN_MANTENIMIENTO_APROBADO, Constantes.ESTADO_BIEN_TRANSITO_POR_REPARACION);
    }
    
    public void aplicarSolicitud() {
        movimientoSolicitud(Constantes.ESTADO_MANTENIMIENTO_APLICADO, Constantes.ESTADO_INTERNO_BIEN_MANTENIMIENTO_APLICADO, 0);
    }
    
    public void finalizarSolicitud() {
        movimientoSolicitud(Constantes.ESTADO_MANTENIMIENTO_FINALIZADO, Constantes.ESTADO_INTERNO_BIEN_NORMAL, Constantes.ESTADO_BIEN_ACTIVO);
    }
    
    private void movimientoSolicitud(int valorEstadoSolicitud, int valorEstadoBienInterno, int valorEstadoBien) {
        try {
            Estado estadoSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_SOLICITUD_MANTENIMIENTO, valorEstadoSolicitud);
            this.command.setEstado(estadoSolicitud);
            this.mantenimientoModel.salvar(this.command.getSolicitud());
            
            // Actualiza todos los bienes con el estado segun el movimiento que se hizo a la solicitud
            Estado estadoInternoBien = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, valorEstadoBienInterno);
            
            List<Bien> listBienes = new ArrayList<Bien>(this.command.getBienes().values());            
            for (Bien bien : listBienes) {
                if (!estadoInternoBien.equals(bien.getEstadoInterno()) &&                                                   // Si el bien tiene un estado diferente se actualiza
                    !Constantes.ESTADO_INTERNO_BIEN_MANTENIMIENTO_ANULADO.equals(bien.getEstadoInterno().getValor()) &&
                    !Constantes.ESTADO_INTERNO_BIEN_MANTENIMIENTO_FINALIZADO.equals(bien.getEstadoInterno().getValor())) {    // Si esta aprobado no se modifica
                    bien.setEstadoInterno(estadoInternoBien);
                }
                if (valorEstadoBien != 0) {
                    bien.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, valorEstadoBien));
                }
                bienModel.actualizar(bien);
            }
            almacenarObservacion();
            this.mensajeExito = "Solicitud de Mantenimiento procesada exitosamente.";
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarMantenimientoController.class.getName()).log(Level.SEVERE, null, ex);
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
        Estado estadoEnSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_SOLICITUD_MANTENIMIENTO);
        
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
    
    public void agregarEvento(ActionEvent event) {
        this.command.setEventoDescripcion("");
        this.command.setFecha(new Date());
        this.command.setEventoCosto(0.0);
        this.visiblePanelEvento = true;
        this.bienSeleccionado = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
    }

    public void verListadoEventos(ActionEvent event) {
        this.visiblePanelListadoEventos = true;
        this.listadoEventos.setIdDetalle((Long) event.getComponent().getAttributes().get("bienSeleccionado"));        
        this.listadoEventos.inicializarListado();
    }
    
    public void listadoEventosCerrar() {
        this.visiblePanelListadoEventos = false;
    }

    public void agregarEventoAceptar() {
        if (validarEventoDescripcion() && validarEventoCosto() && validarEventoFecha()) {
            this.eventoModel.salvar(this.command.getEvento(this.bienSeleccionado));
            this.visiblePanelEvento = false;
            this.mensajeExito = "Evento Almacenado Exitosamente.";
        }
    }
    
    public boolean validarEventoDescripcion() {
        if (this.command.getEventoDescripcion() == null || this.command.getEventoDescripcion().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.mantenimiento.evento.error.descripcion"));
            return false;
        }
        return true;
    }
    
    public boolean validarEventoCosto() {
        if (this.command.getEventoCosto() == null || this.command.getEventoCosto().isNaN()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.mantenimiento.evento.error.costo"));
            return false;
        }
        return true;
    }
    
    public boolean validarEventoFecha() {
        if (!(this.command.getEventoFecha() instanceof Date)) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.mantenimiento.evento.error.fecha"));
            return false;
        }
        return true;
    }

    public void agregarEventoCancelar() {
        this.visiblePanelEvento = false;
    }

    public void rechazarBien(ActionEvent event) {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_RECHAZAR_BIEN;
        this.bienSeleccionado = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
    }
    
    public void rechazarBienObservacion() {
        this.visiblePanelObservacion = false;
        movimientoBien(this.bienSeleccionado, Constantes.ESTADO_MANTENIMIENTO_RECHAZADO, Constantes.ESTADO_INTERNO_BIEN_NORMAL, 0);        
    }
    
    public void aceptarBien(ActionEvent event) {
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        movimientoBien(idBien, Constantes.ESTADO_MANTENIMIENTO_APROBADO, Constantes.ESTADO_INTERNO_BIEN_MANTENIMIENTO_APROBADO, Constantes.ESTADO_BIEN_TRANSITO_POR_REPARACION);        
    }
    
    public void aplicarBien(ActionEvent event) {
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        movimientoBien(idBien, Constantes.ESTADO_MANTENIMIENTO_APLICADO, Constantes.ESTADO_INTERNO_BIEN_MANTENIMIENTO_APLICADO, 0);        
    }
    
    public void finalizarBien(ActionEvent event) {
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        movimientoBien(idBien, Constantes.ESTADO_MANTENIMIENTO_FINALIZADO, Constantes.ESTADO_INTERNO_BIEN_NORMAL, Constantes.ESTADO_BIEN_ACTIVO);        
    }
    
    private void movimientoBien(Long idBien, int valorEstadoSolicitud, int valorEstadoBienInterno, int valorEstadoBien) {
        Bien bien = this.command.getBienes().get(idBien);
        Estado estadoBienInterno = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, valorEstadoBienInterno);
        Estado estadoSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_SOLICITUD_MANTENIMIENTO, valorEstadoSolicitud);
        
        bien.setEstadoInterno(estadoBienInterno);
        if (valorEstadoBien != 0) {
            bien.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, valorEstadoBien));
        }
        this.bienModel.actualizar(bien);
        
        // Si todos los bienes de la solicitud tienen el mismo estado, se modifica el estado de la solicitud
        if (verificarSolicitud(estadoBienInterno)) {
            this.command.setEstado(estadoSolicitud);
            this.mantenimientoModel.salvar(this.command.getSolicitud());
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
    public MantenimientoCommand getCommand() {
        return command;
    }

    public void setCommand(MantenimientoCommand command) {
        this.command = command;
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

    public ListadoEventos getListadoEventos() {
        return listadoEventos;
    }

    public void setListadoEventos(ListadoEventos listadoEventos) {
        this.listadoEventos = listadoEventos;
    }

    public boolean isSolicitudRegistrada() {
        return solicitudRegistrada;
    }

    public void setSolicitudRegistrada(boolean solicitudRegistrada) {
        this.solicitudRegistrada = solicitudRegistrada;
    }

    public Long getBienSeleccionado() {
        return bienSeleccionado;
    }

    public void setBienSeleccionado(Long bienSeleccionado) {
        this.bienSeleccionado = bienSeleccionado;
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

    public boolean isVisiblePanelEvento() {
        return visiblePanelEvento;
    }

    public boolean isVisiblePanelListadoEventos() {
        return visiblePanelListadoEventos;
    }

    public boolean isVisibleBotonAnular() {
        this.visibleBotonAnular = false;
        if (Constantes.ESTADO_MANTENIMIENTO_NUEVO.equals(this.command.getEstado().getValor()) && this.solicitudRegistrada ||
            Constantes.ESTADO_MANTENIMIENTO_APLICADO.equals(this.command.getEstado().getValor()) && this.solicitudRegistrada ||
            Constantes.ESTADO_MANTENIMIENTO_CORRECCION_SOLICITADA.equals(this.command.getEstado().getValor()) && this.solicitudRegistrada) {
            this.visibleBotonAnular = true;
        }
        return visibleBotonAnular;
    }

    public boolean isVisibleBotonSolicitarCorreccion() {
        this.visibleBotonSolicitarCorreccion = false;
        if (Constantes.ESTADO_MANTENIMIENTO_APROBADO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonSolicitarCorreccion = true;
        }
        return visibleBotonSolicitarCorreccion;
    }

    public boolean isVisibleBotonAceptar() {
        this.visibleBotonAceptar = false;
        if (Constantes.ESTADO_MANTENIMIENTO_APLICADO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonAceptar = true;
        }
        return visibleBotonAceptar;
    }
   
    public boolean isVisibleBotonRechazar() {
        this.visibleBotonRechazar = false;
        if (Constantes.ESTADO_MANTENIMIENTO_APLICADO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonRechazar = true;
        }
        return visibleBotonRechazar;
    }

    public boolean isVisibleBotonAplicar() {
        this.visibleBotonAplicar = false;
        if (Constantes.ESTADO_MANTENIMIENTO_NUEVO.equals(this.command.getEstado().getValor()) &&
            this.solicitudRegistrada) {
            this.visibleBotonAplicar = true;
        }        
        return visibleBotonAplicar;
    }

    public boolean isVisibleBotonFinalizar() {
        this.visibleBotonFinalizar = false;
        if (Constantes.ESTADO_MANTENIMIENTO_APROBADO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonFinalizar = true;
        }       
        return visibleBotonFinalizar;
    }

    public boolean isVisibleBotonGuardar() {
        this.visibleBotonGuardar = false;
        if (Constantes.ESTADO_MANTENIMIENTO_NUEVO.equals(this.command.getEstado().getValor()) ||
            Constantes.ESTADO_MANTENIMIENTO_CORRECCION_SOLICITADA.equals(this.command.getEstado().getValor())) {
            this.visibleBotonGuardar = true;
        }
        return visibleBotonGuardar;
    }

    public boolean isVisibleBotonAgregarBienes() {
        this.visibleBotonAgregarBienes = false;
        if (Constantes.ESTADO_MANTENIMIENTO_NUEVO.equals(this.command.getEstado().getValor()) ||
            Constantes.ESTADO_MANTENIMIENTO_CORRECCION_SOLICITADA.equals(this.command.getEstado().getValor())) {
            this.visibleBotonAgregarBienes = true;
        }
        return visibleBotonAgregarBienes;
    }

    public boolean isVisibleBotonEliminarBien() {
        this.visibleBotonEliminarBien = false;
        if (Constantes.ESTADO_MANTENIMIENTO_NUEVO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonEliminarBien = true;
        }
        return visibleBotonEliminarBien;
    }

    public boolean isVisibleBotonAceptarBien() {
        this.visibleBotonAceptarBien = false;
        if (Constantes.ESTADO_MANTENIMIENTO_APLICADO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonAceptarBien = true;
        }
        return visibleBotonAceptarBien;
    }

    public boolean isVisibleBotonRechazarBien() {
        this.visibleBotonRechazarBien = false;
        if (Constantes.ESTADO_MANTENIMIENTO_APLICADO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonRechazarBien = true;
        }
        return visibleBotonRechazarBien;
    }
    
    public boolean isVisibleBotonAplicarBien() {
        this.visibleBotonAplicarBien = false;
        if ((Constantes.ESTADO_MANTENIMIENTO_NUEVO.equals(this.command.getEstado().getValor()) ||
            Constantes.ESTADO_MANTENIMIENTO_CORRECCION_SOLICITADA.equals(this.command.getEstado().getValor())) &&
            this.solicitudRegistrada) {
            this.visibleBotonAplicarBien = true;
        }
        return visibleBotonAplicarBien;
    }

    public boolean isVisibleBotonFinalizarBien() {
        this.visibleBotonFinalizarBien = false;
        if (Constantes.ESTADO_MANTENIMIENTO_APROBADO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonFinalizarBien = true;
        } 
        return visibleBotonFinalizarBien;
    }
    
    public boolean isVisibleBotonEventoBien() {
        this.visibleBotonEventoBien = false;
        if (Constantes.ESTADO_MANTENIMIENTO_APROBADO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonEventoBien = true;
        } 
        return visibleBotonEventoBien;
    }
    
    public boolean isVisibleBotonListarEvento() {
        this.visibleBotonListarEvento = false;
        if (Constantes.ESTADO_MANTENIMIENTO_APROBADO.equals(this.command.getEstado().getValor()) ||
            Constantes.ESTADO_MANTENIMIENTO_FINALIZADO.equals(this.command.getEstado().getValor()) &&
            this.autorizadoAprobar) {    
            this.visibleBotonListarEvento = true;
        } 
        return visibleBotonListarEvento;
    }
    
    public boolean isVisibleBotonSolicitarObservacion() {
        this.visibleBotonSolicitarObservacion = false;
        if (this.accion == ACCION_SOLICITAR_CORRECCION) {
            this.visibleBotonSolicitarObservacion = true;    
        }
        return visibleBotonSolicitarObservacion;
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

    public void setVisiblePanelBienes(boolean visiblePanelBienes) {
        this.visiblePanelBienes = visiblePanelBienes;
    }

    public void setVisiblePanelObservacion(boolean visiblePanelObservacion) {
        this.visiblePanelObservacion = visiblePanelObservacion;
    }

    public void setVisiblePanelConfirmacion(boolean visiblePanelConfirmacion) {
        this.visiblePanelConfirmacion = visiblePanelConfirmacion;
    }

    public void setVisiblePanelEvento(boolean visiblePanelEvento) {
        this.visiblePanelEvento = visiblePanelEvento;
    }

    public void setVisiblePanelListadoEventos(boolean visiblePanelListadoEventos) {
        this.visiblePanelListadoEventos = visiblePanelListadoEventos;
    }

    public void setVisibleBotonRechazar(boolean visibleBotonRechazar) {
        this.visibleBotonRechazar = visibleBotonRechazar;
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
    
    public void setVisibleBotonRechazarBien(boolean visibleBotonRechazarBien) {
        this.visibleBotonRechazarBien = visibleBotonRechazarBien;
    }
    
    public void setVisibleBotonRechazarObservacion(boolean visibleBotonRechazarObservacion) {
        this.visibleBotonRechazarObservacion = visibleBotonRechazarObservacion;
    }

    public void setVisibleBotonAceptarBien(boolean visibleBotonAceptarBien) {
        this.visibleBotonAceptarBien = visibleBotonAceptarBien;
    }

    public void setVisibleBotonAplicarBien(boolean visibleBotonAplicarBien) {
        this.visibleBotonAplicarBien = visibleBotonAplicarBien;
    }

    public void setVisibleBotonFinalizarBien(boolean visibleBotonFinalizarBien) {
        this.visibleBotonFinalizarBien = visibleBotonFinalizarBien;
    }

    public void setVisibleBotonEventoBien(boolean visibleBotonEventoBien) {
        this.visibleBotonEventoBien = visibleBotonEventoBien;
    }

    public void setVisibleBotonListarEvento(boolean visibleBotonListarEvento) {
        this.visibleBotonListarEvento = visibleBotonListarEvento;
    }

    public void setVisibleBotonAnular(boolean visibleBotonAnular) {
        this.visibleBotonAnular = visibleBotonAnular;
    }

    public void setVisibleBotonSolicitarCorreccion(boolean visibleBotonSolicitarCorreccion) {
        this.visibleBotonSolicitarCorreccion = visibleBotonSolicitarCorreccion;
    }

    public void setVisibleBotonAceptar(boolean visibleBotonAceptar) {
        this.visibleBotonAceptar = visibleBotonAceptar;
    }

    public void setVisibleBotonAplicar(boolean visibleBotonAplicar) {
        this.visibleBotonAplicar = visibleBotonAplicar;
    }

    public void setVisibleBotonFinalizar(boolean visibleBotonFinalizar) {
        this.visibleBotonFinalizar = visibleBotonFinalizar;
    }

    public void setVisibleBotonSolicitarObservacion(boolean visibleBotonSolicitarObservacion) {
        this.visibleBotonSolicitarObservacion = visibleBotonSolicitarObservacion;
    }
    
    
    public void setVisibleBotonRechazarBienObservacion(boolean visibleBotonRechazarBienObservacion) {
        this.visibleBotonRechazarBienObservacion = visibleBotonRechazarBienObservacion;
    }
    //</editor-fold>
}

