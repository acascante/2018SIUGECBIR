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
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudExclusion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.models.BienModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.component.UIInput;
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

    public class ListadoBienes extends BaseController {
        
        private ListarBienesCommand command;
        private Estado estadoInternoNormal;
        private Map<Long, Bien> bienes;
        private Map<Long, Boolean> bienesSeleccionados;

        public ListadoBienes() {
            super();
            this.command = new ListarBienesCommand();
            this.bienes = new HashMap<Long, Bien>();
            this.bienesSeleccionados = new HashMap<Long, Boolean>();            
        }

        public ListadoBienes(Estado estadoNormal) {
            this();
            this.estadoInternoNormal = estadoNormal;
        }
        
        private void inicializarListado() {
            this.setPrimerRegistro(1);
            this.contarBienes();
            this.listarBienes();
        }
        
        private void contarBienes() {
            try {
                Long contador = bienModel.contar(this.command.getFltIdCodigo(), this.unidadEjecutora, this.command.getFltIdentificacion(), this.command.getFltDescripcion(), this.command.getFltMarca(), this.command.getFltModelo(), this.command.getFltSerie(), this.estadoInternoNormal);
                this.setCantidadRegistros(contador.intValue());
            } catch (FWExcepcion e) {
                Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
            } catch (NumberFormatException e) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.contarNotificaciones"));
            }
        }

        private void listarBienes() {
            try {
               List<Bien> itemsBienes = bienModel.listar(this.getPrimerRegistro() - 1, this.getUltimoRegistro(), this.command.getFltIdCodigo(), this.unidadEjecutora , command.getFltIdentificacion(), command.getFltDescripcion(), command.getFltMarca(), command.getFltModelo(), command.getFltSerie(), this.estadoInternoNormal);
                for (Bien item : itemsBienes) {
                    this.bienes.put(item.getId(), item);
                }
           } catch (FWExcepcion e) {
               Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
           } catch (NumberFormatException e) {
               Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarNotificaciones.listarNotificaciones"));
           } catch (Exception e) {
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
    @Resource private ExclusionModel exclusionModel;
    
    private ExclusionCommand command;
    
    private List<SelectItem> itemsTipo;
    
    private Estado estadoInternoNormal;
    private String mensajeExito;
    private String mensaje;
    
    private boolean visiblePanelBienes;
    private boolean visibleBotonSolicitar;
    private boolean visibleBotonGuardar;
    private boolean visibleBotonAgregarBienes;
    private boolean exclusionRegistrada;
  
    public AgregarExclusionController() {
        super();
    }
    
    private void inicializarNuevo() {
        Estado estado = this.estadoPorDominioValor(Constantes.DOMINIO_EXCLUSION, Constantes.ESTADO_EXCLUSION_CREADA);
        this.command = new ExclusionCommand(this.unidadEjecutora, estado);
        this.visibleBotonSolicitar = false;
        this.exclusionRegistrada = false;
        inicializarDatos();
    }
    
    private void inicializarDetalle(SolicitudExclusion exclusion) {
        exclusion.setDetalles(exclusionModel.listarDetalles(exclusion));
        this.command = new ExclusionCommand(exclusion);
        this.visibleBotonSolicitar = true;
        this.exclusionRegistrada = true;
        inicializarDatos();
    }

    private void inicializarDatos() {
        this.estadoInternoNormal = estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        this.listadoBienes = new ListadoBienes(estadoInternoNormal);
        this.mensajeExito = new String();
        this.mensaje = new String();

        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_EXCLUSION);
        if (!tipos.isEmpty()) {
            itemsTipo = new ArrayList<SelectItem>();
            for (Tipo item : tipos) {
                this.itemsTipo.add(new SelectItem(item.getId(), item.getNombre()));
            }
        }
        
        this.inicializarBotones();
    }
    
    private void inicializarBotones() {
        if (Constantes.ESTADO_EXCLUSION_SOLICITADA.equals(this.command.getEstado().getValor())) {
            this.visibleBotonSolicitar = false;
            this.visibleBotonGuardar = false;
            this.visibleBotonAgregarBienes = false;
        } else {
            if (!this.exclusionRegistrada) {
                this.visibleBotonSolicitar = false;
                this.visibleBotonGuardar = true;
                this.visibleBotonAgregarBienes = true;
            } else {
                this.visibleBotonSolicitar = true;
                this.visibleBotonGuardar = true;
                this.visibleBotonAgregarBienes = true;
            }
        }
    }
    
    public void guardarDatos() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            UIInput component =  new UIInput();
            String messageValidacion = validarForm(root, component);
            if (Constantes.OK.equals(messageValidacion)) {
                Tipo tipo = this.tipoPorId(command.getIdTipo());
                
                // Almaceno o actualizo Solicitud
                this.exclusionModel.salvar(command.getExclusion(tipo));
                this.exclusionModel.eliminarDetalles(command.getDetallesEliminar());

                List<Bien> listBienes = new ArrayList<Bien>(command.getBienes().values());
                List<Bien> listBienesEliminar = new ArrayList<Bien>(command.getBienesEliminar());
                this.bienModel.actualizar(listBienes);
                this.bienModel.actualizar(listBienesEliminar);                
                
                if (!this.exclusionRegistrada) {
                    this.exclusionRegistrada = true;
                    mensajeExito = "Los datos se salvaron con éxito.";
                } else {
                    mensajeExito = "Los datos se actualizaron con éxito.";
                }
                this.inicializarBotones();
            } else {
                component.setValid(false);
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            mensaje = err.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(AgregarExclusionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void solicitarExclusion() {
        try {
            Tipo tipo = this.tipoPorId(command.getIdTipo());
            Estado estadoSolicitar = this.estadoPorDominioValor(Constantes.DOMINIO_EXCLUSION, Constantes.ESTADO_EXCLUSION_SOLICITADA);
            this.command.setEstado(estadoSolicitar);
            this.exclusionModel.salvar(command.getExclusion(tipo));
            mensajeExito = "Exclusion solicitada.";
            this.inicializarBotones();
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
    
    //<editor-fold defaultstate="collapsed" desc="Validaciones">
    public String validarForm(UIViewRoot root, UIInput component) {
        if (command.getBienes().isEmpty()) {
            return Util.getEtiquetas("sigebi.error.controllerAgregarExclusion.validacion.bienes");
        }
        if (command.getIdTipo().equals(Constantes.DEFAULT_ID)) {
            component = (UIInput) root.findComponent("frmDetalleExclusion:cmbTipo");
            return Util.getEtiquetas("sigebi.error.controllerAgregarExclusion.validacion.tipo");
        }
        return Constantes.OK;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Bienes">
    public void mostarPanelAgregarBienes() {
        this.listadoBienes.inicializarListado();
        this.setVisiblePanelBienes(true);
    }

    public void cerrarPanelAgregarBienes() {
        this.setVisiblePanelBienes(false);
        Estado estadoEnSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_EXCLUSION );
        
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
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public ExclusionCommand getCommand() {
        return command;
    }

    public void setCommand(ExclusionCommand command) {
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

    public Estado getEstadoInternoNormal() {
        return estadoInternoNormal;
    }

    public void setEstadoInternoNormal(Estado estadoInternoNormal) {
        this.estadoInternoNormal = estadoInternoNormal;
    }
    public boolean isVisiblePanelBienes() {
        return visiblePanelBienes;
    }

    public void setVisiblePanelBienes(boolean visiblePanelBienes) {
        this.visiblePanelBienes = visiblePanelBienes;
    }

    public boolean isVisibleBotonSolicitar() {
        return visibleBotonSolicitar;
    }

    public void setVisibleBotonSolicitar(boolean visibleBotonSolicitar) {
        this.visibleBotonSolicitar = visibleBotonSolicitar;
    }

    public boolean isVisibleBotonGuardar() {
        return visibleBotonGuardar;
    }

    public void setVisibleBotonGuardar(boolean visibleBotonGuardar) {
        this.visibleBotonGuardar = visibleBotonGuardar;
    }

    public boolean isVisibleBotonAgregarBienes() {
        return visibleBotonAgregarBienes;
    }

    public void setVisibleBotonAgregarBienes(boolean visibleBotonAgregarBienes) {
        this.visibleBotonAgregarBienes = visibleBotonAgregarBienes;
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
    
    //</editor-fold>
}