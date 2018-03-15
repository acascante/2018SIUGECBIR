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
import cr.ac.ucr.sigebi.models.PrestamoModel;
import cr.ac.ucr.sigebi.commands.PrestamoCommand;
import cr.ac.ucr.sigebi.commands.ListarPrestamosCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.RegistroMovimientoSolicitud;
import cr.ac.ucr.sigebi.domain.SolicitudPrestamo;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.RegistroMovimientoModel;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
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
@Controller(value = "controllerAgregarPrestamos")
@Scope("session")
public class AgregarPrestamoController extends BaseController {
    
    private final int ACCION_RECHAZAR = 1;
    private final int ACCION_REVISAR = 2;
    private final int ACCION_ANULAR = 4;
    private final int ACCION_RECHAZAR_BIEN = 3;
    
    public class ListadoBienes extends BaseController {
        
        private ListarBienesCommand command;
        private Estado estadoInternoNormal;
        private Estado estadoInternoRechazado;
        private Map<Long, Bien> bienes;
        private Map<Long, Boolean> bienesSeleccionados;

        public ListadoBienes() {
            super();
            this.command = new ListarBienesCommand();
            this.bienes = new HashMap<Long, Bien>();
            this.bienesSeleccionados = new HashMap<Long, Boolean>();            
        }

        public ListadoBienes(Estado estadoNormal, Estado estadoRechazado) {
            this();
            this.estadoInternoNormal = estadoNormal;
            this.estadoInternoRechazado = estadoRechazado;
        }
        
        private void inicializarListado() {
            this.setPrimerRegistro(1);
            this.contarBienes();
            this.listarBienes();
        }
        
        private void contarBienes() {
            try {
                Long contador = bienModel.contar(this.command.getFltIdCodigo(), this.unidadEjecutora, this.command.getFltIdentificacion(), this.command.getFltDescripcion(), this.command.getFltMarca(), this.command.getFltModelo(), this.command.getFltSerie(), this.estadoInternoNormal, this.estadoInternoRechazado);
                this.setCantidadRegistros(contador.intValue());
            } catch (FWExcepcion e) {
                Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
            } catch (NumberFormatException e) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.contarNotificaciones"));
            }
        }

        private void listarBienes() {
            try {
                List<Bien> itemsBienes = bienModel.listar(this.getPrimerRegistro() - 1, this.getUltimoRegistro(), this.command.getFltIdCodigo(), this.unidadEjecutora , command.getFltIdentificacion(), command.getFltDescripcion(), command.getFltMarca(), command.getFltModelo(), command.getFltSerie(), this.estadoInternoNormal, this.estadoInternoRechazado);
                this.bienes.clear();
                for (Bien item : itemsBienes) {
                    this.bienes.put(item.getId(), item);
                }
           } catch (FWExcepcion e) {
               Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
           } catch (NumberFormatException e) {
               Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.listarNotificaciones"));
           } 
        }

        public void cambioFiltro(ValueChangeEvent pEvent) {
            try {
                if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                    pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                    pEvent.queue();
                    return;
                }
                this.inicializarListado();
            } catch (Exception err) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.cambioFiltro"));
            }
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

        public Estado getEstadoInternoRechazado() {
            return estadoInternoRechazado;
        }

        public void setEstadoInternoRechazado(Estado estadoInternoRechazado) {
            this.estadoInternoRechazado = estadoInternoRechazado;
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
    
    @Resource private BienModel bienModel;
    @Resource private PrestamoModel prestamoModel;
    @Resource private RegistroMovimientoModel registroMovimientoModel;
    @Resource private UnidadEjecutoraModel unidadEjecutoraModel;
    
    private PrestamoCommand command;
    
    private List<SelectItem> itemsTipo;
    private List<SelectItem> itemsEntidad;
    private Map<Long, String> entidades;
    
    private String mensajeExito;
    private String mensaje;
    
    private boolean visiblePanelBienes;
    private boolean visiblePanelObservacion;
    
    private boolean visibleBotonGuardar;
    private boolean visibleBotonAgregarBienes;
    
    private boolean visibleBotonEliminarBien;
    private boolean visibleBotonSolicitarBien;
    private boolean visibleBotonRechazarBien;
    
    private boolean visibleBotonSolicitar;
    private boolean visibleBotonRechazar;
    private boolean visibleBotonRevisar;
    private boolean visibleBotonAprobar;
    private boolean visibleBotonAnular;
    
    private boolean visibleBotonRechazarObservacion;
    private boolean visibleBotonRechazarBienObservacion;
    private boolean visibleBotonRevisarObservacion;
    private boolean visibleBotonAnularObservacion;
    
    private boolean solicitudRegistrada;
    private boolean disableEntidades;
    private int accion;

    public AgregarPrestamoController() {
        super();
    }
    
    private void inicializarNuevo() {
        Estado estado = this.estadoPorDominioValor(Constantes.DOMINIO_PRESTAMO, Constantes.ESTADO_PRESTAMO_CREADO);
        this.command = new PrestamoCommand(this.unidadEjecutora, estado);
        this.solicitudRegistrada = false;
        this.disableEntidades = true;
        this.entidades = new HashMap<Long, String>();
        inicializarDatos();
    }
    
    private void inicializarDetalle(SolicitudPrestamo prestamo) {
        prestamo.setDetalles(prestamoModel.listarDetalles(prestamo));
        this.command = new PrestamoCommand(prestamo);
        this.solicitudRegistrada = true;
        this.disableEntidades = false;
        this.entidades = new HashMap<Long, String>();
        cargarEntidades(this.tipoPorId(command.getIdTipoEntidad()));
        inicializarDatos();
    }

    private void inicializarDatos() {
        Estado estadoInternoNormal = estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        Estado estadoInternoRechazado = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_EXCLUSION_RECHAZADO);
        this.listadoBienes = new ListadoBienes(estadoInternoNormal, estadoInternoRechazado);        
        this.mensajeExito = new String();
        this.mensaje = new String();
        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_PRESTAMO_ENTIDAD);
        if (!tipos.isEmpty()) {
            itemsTipo = new ArrayList<SelectItem>();
            for (Tipo item : tipos) {
                this.itemsTipo.add(new SelectItem(item.getId(), item.getNombre()));
            }
        }
    }
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            String messageValidacion = validarForm(root);
            if (Constantes.OK.equals(messageValidacion)) {
                Tipo tipo = this.tipoPorId(command.getIdTipoEntidad());
                
                // Almaceno o actualizo Solicitud
                SolicitudPrestamo solicitud = command.getPrestamo(tipo);
                this.prestamoModel.salvar(solicitud);
                if (!command.getDetallesEliminar().isEmpty()) {
                    this.prestamoModel.eliminarDetalles(command.getDetallesEliminar());
                }
                
                List<Bien> listBienes = new ArrayList<Bien>(command.getBienes().values());
                if (!listBienes.isEmpty()) {
                    this.bienModel.actualizar(listBienes);
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
                    almacenarObservacion(tipo);
                    mensajeExito = "Los datos se actualizaron con éxito.";
                }
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarPrestamoController.class.getName()).log(Level.SEVERE, null, ex);
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
            Util.navegar(Constantes.VISTA_PRESTAMO_NUEVO);
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
            
            Long id = (Long)event.getComponent().getAttributes().get(ListarPrestamosCommand.KEY_PRESTAMO);
            SolicitudPrestamo prestamo = prestamoModel.buscarPorId(id);
            inicializarDetalle(prestamo);
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_PRESTAMO_NUEVO);
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Validaciones">
    public String validarForm(UIViewRoot root) {
        if (command.getBienes().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.prestamos.error.bienes");
        }
        if (command.getIdTipoEntidad().equals(Constantes.DEFAULT_ID)) {
            return Util.getEtiquetas("sigebi.label.prestamos.error.tipo");
        }
        if (command.getEntidad().isEmpty()) {
            return Util.getEtiquetas("sigebi.label.prestamos.error.entidad");
        }
        return Constantes.OK;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Movimientos sobre la Solicitud">
    public void solicitarPrestamo() {
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_SOLICITADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_SOLICITADO);
    }
    
    public void rechazarPrestamo() {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_RECHAZAR;
    }
    
    public void aprobarPrestamo() {
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_APROBADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_APROBADO);
    }

    public void revisarPrestamo() {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_REVISAR;
    }
    
    public void anularPrestamo() {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_ANULAR;
    }

    public void cerrarPanelObservaciones() {
        this.visiblePanelObservacion = false;
    }
    
    public void rechazarPrestamoObservacion() {
        this.visiblePanelObservacion = false;
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_RECHAZADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_RECHAZADO);
    }
    
    public void revisarPrestamoObservacion() {
        this.visiblePanelObservacion = false;
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_CREADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO);
    }
    public void anularPrestamoObservacion() {
        this.visiblePanelObservacion = false;
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_ANULADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_ANULADO);
    }
    
    private void movimientoPrestamo(int solicitud, int bienInterno) {
        try {
            Tipo tipo = this.tipoPorId(command.getIdTipoEntidad());
            Estado estadoSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_PRESTAMO, solicitud);
            this.command.setEstado(estadoSolicitud);
            this.prestamoModel.salvar(command.getPrestamo(tipo));
            
            // Actualiza todos los bienes con el estado segun el movimiento que se hizo a la solicitud
            Estado estadoInternoBien = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, bienInterno);
            List<Bien> listBienes = new ArrayList<Bien>(command.getBienes().values());
            for (Bien bien : listBienes) {
                if (!estadoInternoBien.equals(bien.getEstadoInterno()) &&                                               // Si el bien tiene un estado diferente se actualiza
                    !Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_APROBADO.equals(bien.getEstadoInterno().getValor())) {    // Si esta aprobado no se modifica
                    bien.setEstadoInterno(estadoInternoBien);
                    bienModel.actualizar(bien);
                }
            }
            almacenarObservacion(tipo);
            mensajeExito = "Exclusion procesada exitosamente.";
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarExclusionController.class.getName()).log(Level.SEVERE, null, ex);
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
                command.getPrestamo(tipo));
            registroMovimientoModel.agregar(registroMovimientoSolicitud);
        }
        
        if (!command.getObservacion().isEmpty()) {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;

            RegistroMovimientoSolicitud registroMovimientoSolicitud = new RegistroMovimientoSolicitud(
                this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_SOLICITUD), 
                command.getObservacion(), 
                telefono, 
                new Date(), 
                usuarioSIGEBI, 
                command.getEstado(),
                command.getPrestamo(tipo));
            registroMovimientoModel.agregar(registroMovimientoSolicitud);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Movimientos sobre Bienes">
    public void mostarPanelAgregarBienes() {
        this.listadoBienes.inicializarListado();
        this.setVisiblePanelBienes(true);
    }

    public void cerrarPanelAgregarBienes() {
        this.setVisiblePanelBienes(false);
        Estado estadoEnSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO);
        
        for (Map.Entry<Long, Boolean> entry : this.listadoBienes.bienesSeleccionados.entrySet()) {
            if (entry.getValue()) {
                Bien bien = this.listadoBienes.bienes.get(entry.getKey());
                bien.setEstadoInterno(estadoEnSolicitud);
                this.command.getBienes().put(bien.getId(), bien);
                this.command.getBienesAgregar().add(bien);
            }
        }
    }

    public void eliminarBien(ActionEvent event) {
        Estado estadoInternoNormal = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        Bien bien = this.command.getBienes().get(idBien);
        
        bien.setEstadoInterno(estadoInternoNormal);
        this.command.getBienesEliminar().add(bien); // Lo agrego a la lista de bienes a eliminar
        this.command.getBienes().remove(idBien);    // Lo saco de la lista de bienes que se muestran en pantalla
        if (this.command.getDetalles().containsKey(bien.getId())) { // Si esta en la lista de detalles, es xq se trata de un detalle existente en la BD
            this.command.getDetallesEliminar().add(this.command.getDetalles().get(bien.getId()));   // Lo agrego a la lista de detalles a eliminar
            this.command.getDetalles().remove(bien.getId());    // Lo elimino de la lista de detalles, esto ahorita no sirve
        }                                                       // xq la version de hibernate no permite actualizar colecciones
    }
    
    public void solicitarBien(ActionEvent event) {
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        movimientoBien(idBien, Constantes.ESTADO_PRESTAMO_SOLICITADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_SOLICITADO);
    }
    
    public void rechazarBien(ActionEvent event) {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_RECHAZAR_BIEN;
    }

    public void rechazarBienObservacion(ActionEvent event) {
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        movimientoBien(idBien, Constantes.ESTADO_PRESTAMO_RECHAZADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_RECHAZADO);        
    }
    
    private void movimientoBien(Long idBien, int solicitud, int bienInterno) {
        Bien bien = this.command.getBienes().get(idBien);
        Estado estadoBienInterno = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, bienInterno);
        Estado estadoSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_PRESTAMO, solicitud);
        
        bien.setEstadoInterno(estadoBienInterno);
        bienModel.actualizar(bien);
        
        // Si todos los bienes de la solicitud tienen el mismo estado, se modifica el estado de la solicitud
        if (verificarSolicitud(estadoBienInterno)) {
            Tipo tipo = this.tipoPorId(command.getIdTipoEntidad());
            this.command.setEstado(estadoSolicitud);
            this.prestamoModel.salvar(command.getPrestamo(tipo));
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
    
    //<editor-fold defaultstate="collapsed" desc="Cargar Entidades">
    public void cambioTipoEntidad(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        
        if (Constantes.DEFAULT_ID.equals(command.getIdTipoEntidad())) {
            itemsEntidad.clear();
            this.setDisableEntidades(true);
        } else {
            cargarEntidades(this.tipoPorId(command.getIdTipoEntidad()));
        }
    }
    
    private void cargarEntidades(Tipo tipo) {
        try {
            this.disableEntidades = false;
            itemsEntidad = new ArrayList<SelectItem>();
            switch(tipo.getValor()) {
                case 1: //
                break;

                case 2: // UNIDAD EJECUTORA
                    List<UnidadEjecutora> unidades = unidadEjecutoraModel.listar();
                    for (UnidadEjecutora unidad : unidades) {
                        if(unidad.getDescripcion().equals(this.command.getEntidad())) {
                            this.command.setIdEntidad(unidad.getId());
                        }
                        this.itemsEntidad.add(new SelectItem(unidad.getId(), unidad.getDescripcion()));
                        this.entidades.put(unidad.getId(), unidad.getDescripcion());
                    }
                break;

                case 3: //
                break;

                case 4: //
                break;                        
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.cargarSubCategorias"));
        }
    }
    
    public void cargarEntidad(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        Long idEntidad = command.getIdEntidad();
        String entidad = entidades.get(idEntidad);
        this.command.setEntidad(entidad);
    }    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public PrestamoCommand getCommand() {
        return command;
    }

    public void setCommand(PrestamoCommand command) {
        this.command = command;
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
    
    public boolean isSolicitudRegistrada() {
        return solicitudRegistrada;
    }

    public void setSolicitudRegistrada(boolean solicitudRegistrada) {
        this.solicitudRegistrada = solicitudRegistrada;
    }
    
    public List<SelectItem> getItemsEntidad() {
        return itemsEntidad;
    }

    public void setItemsEntidad(List<SelectItem> itemsEntidad) {
        this.itemsEntidad = itemsEntidad;
    }
    
    public boolean isDisableEntidades() {
        return disableEntidades;
    }

    public void setDisableEntidades(boolean disableEntidades) {
        this.disableEntidades = disableEntidades;
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

    public boolean isVisibleBotonSolicitar() {
        this.visibleBotonSolicitar = false;
        if (Constantes.ESTADO_PRESTAMO_CREADO.equals(this.command.getEstado().getValor()) && this.solicitudRegistrada) {
            this.visibleBotonSolicitar = true;
        }
        return visibleBotonSolicitar;
    }

    public boolean isVisibleBotonRechazar() {
        this.visibleBotonRechazar = false;
        if (Constantes.ESTADO_PRESTAMO_RECHAZADO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonRechazar = true;
        }
        return visibleBotonRechazar;
    }

    public boolean isVisibleBotonAprobar() {
        this.visibleBotonAprobar = false;
        if (Constantes.ESTADO_PRESTAMO_APROBADO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonAprobar = true;
        }
        return visibleBotonAprobar;
    }
    
    public boolean isVisibleBotonAnular() {
        this.visibleBotonAnular = false;
        if (Constantes.ESTADO_PRESTAMO_ANULADO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonAnular = true;
        }
        return visibleBotonAnular;
    }
   
    public boolean isVisibleBotonRevisar() {
        this.visibleBotonRevisar = false;
        if (Constantes.ESTADO_PRESTAMO_SOLICITADO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonRevisar = true;
        }        
        return visibleBotonRevisar;
    }

    public boolean isVisibleBotonGuardar() {
        this.visibleBotonGuardar = false;
        if (Constantes.ESTADO_PRESTAMO_CREADO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonGuardar = true;
        }
        return visibleBotonGuardar;
    }

    public boolean isVisibleBotonAgregarBienes() {
        this.visibleBotonAgregarBienes = false;
        if (Constantes.ESTADO_PRESTAMO_CREADO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonAgregarBienes = true;
        }
        return visibleBotonAgregarBienes;
    }

    public boolean isVisibleBotonEliminarBien() {
        this.visibleBotonEliminarBien = false;
        if (Constantes.ESTADO_PRESTAMO_CREADO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonEliminarBien = true;
        }
        return visibleBotonEliminarBien;
    }

    public boolean isVisibleBotonSolicitarBien() {
        this.visibleBotonSolicitarBien = false;
        if (Constantes.ESTADO_PRESTAMO_CREADO.equals(this.command.getEstado().getValor())) {
            this.visibleBotonSolicitarBien = true;
        }
        return visibleBotonSolicitarBien;
    }

    public boolean isVisibleBotonRechazarBien() {
        this.visibleBotonRechazarBien = true;
        if (Constantes.ESTADO_PRESTAMO_RECHAZADO.equals(this.command.getEstado().getValor())  || 
            Constantes.ESTADO_PRESTAMO_ANULADO.equals(this.command.getEstado().getValor())  || 
            Constantes.ESTADO_PRESTAMO_APROBADO.equals(this.command.getEstado().getValor())) {
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
    
    public boolean isVisibleBotonAnularObservacion() {
        this.visibleBotonAnularObservacion = false;
        if (this.accion == ACCION_ANULAR) {
            this.visibleBotonAnularObservacion = true;    
        }
        return visibleBotonAnularObservacion;
    }
    
    public void setVisiblePanelBienes(boolean visiblePanelBienes) {
        this.visiblePanelBienes = visiblePanelBienes;
    }

    public void setVisiblePanelObservacion(boolean visiblePanelObservacion) {
        this.visiblePanelObservacion = visiblePanelObservacion;
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