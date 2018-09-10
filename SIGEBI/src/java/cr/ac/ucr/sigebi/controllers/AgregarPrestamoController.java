/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.context.effects.JavascriptContext;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.ListarBienesCommand;
import cr.ac.ucr.sigebi.commands.ListarPersonasCommand;
import cr.ac.ucr.sigebi.models.PrestamoModel;
import cr.ac.ucr.sigebi.commands.PrestamoCommand;
import cr.ac.ucr.sigebi.commands.ListarPrestamosCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Convenio;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Persona;
import cr.ac.ucr.sigebi.domain.RegistroMovimientoSolicitud;
import cr.ac.ucr.sigebi.domain.SolicitudPrestamo;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.reportes.ReporteBienDetalle;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.ConvenioModel;
import cr.ac.ucr.sigebi.models.PersonaModel;
import cr.ac.ucr.sigebi.models.RegistroMovimientoModel;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alvaro.cascante
 */
@Controller(value = "controllerAgregarPrestamos")
@Scope("session")
public class AgregarPrestamoController extends BaseController {
    
    private static class ParametrosReporte {
        private static final String UNIDAD_CUSTODIO = "UNIDAD_CUSTODIO";
	private static final String USUARIO = "USUARIO";
        
        private static final String INSTITUCION = "INSTITUCION";
        private static final String NOMBRE_REPORTE = "NOMBRE_REPORTE";
	private static final String VALOR_INSTITUCION = "UNIVERSIDAD DE COSTA RICA";
        private static final String VALOR_NOMBRE_REPORTE = "Reporte de Roles y Usuarios";
        
        private static final String ID = "ID";
        private static final String TIPO_ENTIDAD = "TIPO_ENTIDAD";
        private static final String ENTIDAD = "ENTIDAD";
        private static final String FECHA = "FECHA";
    }
    
    
    private final int ACCION_RECHAZAR = 1;
    private final int ACCION_REVISAR = 2;
    private final int ACCION_ANULAR = 4;
    private final int ACCION_RECHAZAR_BIEN = 3;
    private final int ACCION_DEVOLVER = 5;
    private final int ACCION_DEVOLVER_BIEN = 6;
    
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
    
    public class ListadoPersonas extends BaseController {
        
        private ListarPersonasCommand command;
        private Boolean estudiante;
        private Boolean funcionario;
        private List<Persona> personas;
        
        public ListadoPersonas() {
            super();
            this.command = new ListarPersonasCommand();
            this.personas = new ArrayList<Persona>();
        }
        
        public ListadoPersonas(Boolean estudiante, Boolean funcionario) {
            this();
            this.funcionario = funcionario;
            this.estudiante = estudiante;
        }
        
        private void inicializarListado() {
            this.setPrimerRegistro(1);
            this.contarEntidades();
            this.listarEntidades();
        }
        
        private void contarEntidades() {
            try {
                Long contador = personaModel.contar(this.estudiante, this.funcionario, this.command.getFltIdCodigo(), this.command.getFltNombre(), null,  this.command.getFltPrimerApellido(), this.command.getFltSegundoApellido());
                this.setCantidadRegistros(contador.intValue());
            } catch (FWExcepcion e) {
                Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
            }
        }

        private void listarEntidades() {
            try {
                this.personas.clear();
                this.personas = personaModel.listar(this.getPrimerRegistro() - 1, this.getUltimoRegistro(), this.estudiante, this.funcionario, this.command.getFltIdCodigo(), this.command.getFltNombre(), null, this.command.getFltPrimerApellido(), this.command.getFltSegundoApellido());                
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
        public ListarPersonasCommand getCommand() {
            return command;
        }

        public void setCommand(ListarPersonasCommand command) {
            this.command = command;
        }

        public List<Persona> getPersonas() {
            return personas;
        }

        public void setPersonas(List<Persona> personas) {
            this.personas = personas;
        }

        public Boolean getEstudiante() {
            return estudiante;
        }

        public void setEstudiante(Boolean estudiante) {
            this.estudiante = estudiante;
        }

        public Boolean getFuncionario() {
            return funcionario;
        }

        public void setFuncionario(Boolean funcionario) {
            this.funcionario = funcionario;
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
            this.listarEntidades();
        }

        public void siguiente(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.getSiguientePagina();
            this.listarEntidades();
        }

        public void anterior(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.getPaginaAnterior();
            this.listarEntidades();
        }

        public void primero(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.setPrimerRegistro(1);
            this.listarEntidades();
        }

        public void ultimo(ActionEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.getPrimerRegistroUltimaPagina();
            this.listarEntidades();
        }

        public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));          
            this.setPrimerRegistro(1);
            this.listarEntidades();
        }
        // </editor-fold>
    }
    
    ListadoBienes listadoBienes;
    ListadoPersonas listadoPersonas;
    
    @Resource private AutorizacionRolPersonaModel autorizacionRolPersonaModel;
    @Resource private BienModel bienModel;
    @Resource private ConvenioModel convenioModel;
    @Resource private PersonaModel personaModel;
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
    private boolean visiblePanelConfirmacion;
    private boolean visiblePanelBuscarPersonas;
    private boolean visibleCampoEntidad;
    
    private boolean visibleBotonGuardar;
    private boolean visibleBotonAgregarBienes;
    
    private boolean visibleBotonEliminarBien;
    private boolean visibleBotonSolicitarBien;
    private boolean visibleBotonRechazarBien;
    private boolean visibleBotonDevolverBien;
    
    private boolean visibleBotonSolicitar;
    private boolean visibleBotonRechazar;
    private boolean visibleBotonRevisar;
    private boolean visibleBotonAprobar;
    private boolean visibleBotonAnular;
    private boolean visibleBotonDevolver;
    
    private boolean visibleBotonRechazarObservacion;
    private boolean visibleBotonRechazarBienObservacion;
    private boolean visibleBotonDevolverObservacion;
    private boolean visibleBotonDevolverBienObservacion;
    private boolean visibleBotonRevisarObservacion;
    private boolean visibleBotonAnularObservacion;
    
    private boolean solicitudRegistrada;
    private boolean disableEntidades;
    private boolean autorizadoAprobar;
    
    private int accion;
    private Long bienSeleccionado;
    
    public AgregarPrestamoController() {
        super();
    }
    
    private void inicializarNuevo() {
        Estado estadoPrestamoCreado = this.estadoPorDominioValor(Constantes.DOMINIO_PRESTAMO, Constantes.ESTADO_PRESTAMO_CREADO);
        this.command = new PrestamoCommand(this.unidadEjecutora, estadoPrestamoCreado, this.usuarioSIGEBI);
        this.solicitudRegistrada = false;
        this.autorizadoAprobar = false;
        this.disableEntidades = true;
        this.visibleCampoEntidad = false;
        this.entidades = new HashMap<Long, String>();
        inicializarDatos();
    }
    
    private void inicializarDetalle(SolicitudPrestamo prestamo) {
        prestamo.setDetallesPrestamo(prestamoModel.listarDetalles(prestamo));
        this.command = new PrestamoCommand(prestamo);
        this.solicitudRegistrada = true;
        this.autorizadoAprobar = inicializarAutorizaciones();
        this.disableEntidades = false;
        this.entidades = new HashMap<Long, String>();
        cargarEntidadDetalle(this.tipoPorId(this.command.getIdTipoEntidad()));
        inicializarDatos();
    }

    private boolean inicializarAutorizaciones() {
        int codigoAutorizacion = Constantes.CODIGO_AUTORIZACION_PRESTAMO;
        return autorizacionRolPersonaModel.buscarAutorizacion(codigoAutorizacion, this.unidadEjecutora, this.usuarioSIGEBI);
    }
    
    private void inicializarDatos() {
        Estado estadoInternoNormal = estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        Estado estadoActivo = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_ACTIVO);
        this.listadoBienes = new ListadoBienes(estadoInternoNormal, estadoActivo);
        this.listadoPersonas = new ListadoPersonas();
        this.mensajeExito = new String();
        this.mensaje = new String();
        
        this.visiblePanelConfirmacion = false;
        this.visiblePanelObservacion = false;
        this.visiblePanelBienes = false;
        this.visiblePanelBuscarPersonas = false;
        
        List<Tipo> tipos = this.tiposPorDominio(Constantes.DOMINIO_PRESTAMO_ENTIDAD);
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
                Tipo tipo = this.tipoPorId(this.command.getIdTipoEntidad());
                
                // Almaceno o actualizo Solicitud
                // Se almacenan o actualizan los detalles tambien
                SolicitudPrestamo solicitud = this.command.getPrestamo(tipo);
                this.prestamoModel.salvar(solicitud);   
                if (!this.command.getDetallesEliminar().isEmpty()) {
                    this.prestamoModel.eliminarDetalles(this.command.getDetallesEliminar());
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
                    this.command.setId(solicitud.getId());
                    this.mensajeExito = "Los datos se salvaron con éxito.";
                } else {
                    almacenarObservacion(tipo);
                    this.mensajeExito = "Los datos se actualizaron con éxito.";
                }
            } else {
                Mensaje.agregarErrorAdvertencia(messageValidacion);
            }
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
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
            
            Long id = (Long)event.getComponent().getAttributes().get(ListarPrestamosCommand.KEY_PRESTAMO);
            SolicitudPrestamo prestamo = prestamoModel.buscarPorId(id);
            inicializarDetalle(prestamo);
            this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
            Util.navegar(Constantes.VISTA_PRESTAMO_NUEVO);
        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }

    public void verDetalle(SolicitudPrestamo movimiento, String vistaOrigen) {
        
        try{
            this.inicializarDetalle(movimiento);

            this.vistaOrigen = vistaOrigen;
            Util.navegar(Constantes.VISTA_PRESTAMO_NUEVO);

        } catch (FWExcepcion err) {
            this.mensaje = err.getMessage();
        }
    }
    
    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen, true);
        } else {
            Util.navegar(Constantes.VISTA_PRESTAMO_LISTADO, true);
        }
    }
    
    private void almacenarObservacion(Tipo tipo) {
        if (!command.getObservacionConfirmacion().isEmpty()) {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;

            RegistroMovimientoSolicitud registroMovimientoSolicitud = new RegistroMovimientoSolicitud(
                this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_SOLICITUD), 
                this.command.getObservacionConfirmacion(), 
                telefono, 
                new Date(), 
                usuarioSIGEBI, 
                this.command.getEstado(),
                this.command.getPrestamo(tipo));
            this.registroMovimientoModel.agregar(registroMovimientoSolicitud);
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
        
        List<PrestamoCommand.BienDetalle> bienDetalles = this.command.getListBienesDetalle();
        for (PrestamoCommand.BienDetalle bienDetalle : bienDetalles) {
            if (bienDetalle.getFechaInicio() == null) {
                return Util.getEtiquetas("sigebi.label.prestamos.error.fecha.invalida");
            }
            else if (bienDetalle.getFechaFin() == null) {
                return Util.getEtiquetas("sigebi.label.prestamos.error.fecha.invalida");
            }
            else if (bienDetalle.getFechaFin().before(bienDetalle.getFechaInicio())) {                
                return Util.getEtiquetas("sigebi.label.prestamos.error.fechas");
            }
        }
        return Constantes.OK;
    }
    
    public void validarFecha(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            Date fecha = (Date)value;
            
            Date today = new Date();
            Calendar calendar = Calendar.getInstance(Constantes.DEFAULT_TIME_ZONE);
            calendar.setTime(today);
            calendar.add(Calendar.DATE, -1);
            if (!this.solicitudRegistrada && fecha.before(calendar.getTime()) ) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.prestamos.error.fecha.menor.hoy"));
                ((UIInput) component).setValid(false); 
            } 
        } catch (Exception e ) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.label.prestamos.error.fecha.invalida"));
            ((UIInput) component).setValid(false);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Movimientos sobre la Solicitud">
    public void solicitarPrestamo() {   // Cambia bienes a estado solicitado
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_SOLICITADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_SOLICITADO);
    }
    
    public void devolverPrestamo() {   // Cambia bienes a estado normal
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_DEVOLVER;        
    }
    
    public void rechazarPrestamo() {  // Abre ventana de confirmacion
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_RECHAZAR;
    }
    
    public void aprobarPrestamo() {  // Cambia bienes a estado aprobado
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_APROBADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_APROBADO);
    }

    public void revisarPrestamo() { // Abre ventana de confirmacion
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_REVISAR;
    }
    
    public void anularPrestamo() { // Abre ventana de confirmacion
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_ANULAR;
    }

    public void cerrarPanelObservaciones() {
        this.visiblePanelObservacion = false;
    }
    
    public void devolverPrestamoObservacion() {
        this.visiblePanelObservacion = false;
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_DEVUELTO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
    }
    
    public void rechazarPrestamoObservacion() { // Cambia bienes a estado normal
        this.visiblePanelObservacion = false;
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_RECHAZADO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
    }
    
    public void revisarPrestamoObservacion() { // Cambia bienes a estado en prestamo
        this.visiblePanelObservacion = false;
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_CREADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO);
    }
    
    public void anularPrestamoObservacion() { // Cambia bienes a estado normal
        this.visiblePanelObservacion = false;
        movimientoPrestamo(Constantes.ESTADO_PRESTAMO_ANULADO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
    }
    
    private void movimientoPrestamo(int solicitud, int bienInterno) {
        try {
            Tipo tipo = this.tipoPorId(command.getIdTipoEntidad());
            Estado estadoSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_PRESTAMO, solicitud);
            this.command.setEstado(estadoSolicitud);
            this.prestamoModel.salvar(command.getPrestamo(tipo));
            
            // Actualiza todos los bienes con el estado segun el movimiento que se hizo a la solicitud
            Estado estadoInternoBien = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, bienInterno);
            List<Bien> listBienes = new ArrayList<Bien>(command.getListBienes());
            for (Bien bien : listBienes) {
                if (!estadoInternoBien.equals(bien.getEstadoInterno()) &&                                               // Si el bien tiene un estado diferente se actualiza
                    !Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_APROBADO.equals(bien.getEstadoInterno().getValor())) {    // Si esta aprobado no se modifica
                    bien.setEstadoInterno(estadoInternoBien);
                    bienModel.actualizar(bien);
                }
            }
            almacenarObservacion(tipo);
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
        Estado estadoEnSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO);
        
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
        Estado estadoInternoNormal = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        Bien bien = this.listadoBienes.allBienes.containsKey(idBien) ? this.listadoBienes.allBienes.get(idBien) : this.command.getBien(idBien);
        this.command.getBienesAgregar().remove(bien); // Lo elimino de la lista de bienes a agregar
        
        bien.setEstadoInterno(estadoInternoNormal);
        this.command.getBienes().remove(idBien);    // Lo saco de la lista de bienes que se muestran en pantalla
        if (this.command.getDetalles().containsKey(bien.getId())) { // Si esta en la lista de detalles, es xq se trata de un detalle existente en la BD
            this.command.getBienesEliminar().add(bien); // Lo agrego a la lista de bienes a eliminar
        }
    }
    
    public void solicitarBien(ActionEvent event) {
        Long idBien = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
        movimientoBien(idBien, Constantes.ESTADO_PRESTAMO_SOLICITADO, Constantes.ESTADO_INTERNO_BIEN_PRESTAMO_SOLICITADO);
    }
    
    public void devolverBien(ActionEvent event) {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_DEVOLVER_BIEN;
        this.bienSeleccionado = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
    }
    
    public void rechazarBien(ActionEvent event) {
        this.command.setObservacionConfirmacion(new String());
        this.visiblePanelObservacion = true;
        this.accion = ACCION_RECHAZAR_BIEN;
        this.bienSeleccionado = (Long) event.getComponent().getAttributes().get("bienSeleccionado");
    }
    
    public void rechazarBienObservacion() {
        this.visiblePanelObservacion = false;
        movimientoBien(this.bienSeleccionado, Constantes.ESTADO_PRESTAMO_RECHAZADO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);        
    }

    public void devolverBienObservacion() {
        this.visiblePanelObservacion = false;
        movimientoBien(this.bienSeleccionado, Constantes.ESTADO_PRESTAMO_DEVUELTO, Constantes.ESTADO_INTERNO_BIEN_NORMAL);
    }
    
    private void movimientoBien(Long idBien, int solicitud, int bienInterno) {
        Bien bien = this.command.getBien(idBien);
        Estado estadoBienInterno = this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, bienInterno);
        Estado estadoSolicitud = this.estadoPorDominioValor(Constantes.DOMINIO_PRESTAMO, solicitud);
        
        bien.setEstadoInterno(estadoBienInterno);
        this.bienModel.actualizar(bien);
        
        // Si todos los bienes de la solicitud tienen el mismo estado, se modifica el estado de la solicitud
        if (verificarSolicitud(estadoBienInterno)) {
            Tipo tipo = this.tipoPorId(command.getIdTipoEntidad());
            this.command.setEstado(estadoSolicitud);
            this.prestamoModel.salvar(command.getPrestamo(tipo));
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
    
    //<editor-fold defaultstate="collapsed" desc="Cargar Entidades">
    public void cambioTipoEntidad(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        
        if (Constantes.DEFAULT_ID.equals(command.getIdTipoEntidad())) {
            this.itemsEntidad.clear();
            this.disableEntidades = true;
            this.visiblePanelBuscarPersonas = false;
        } else {
            cargarEntidades(this.tipoPorId(command.getIdTipoEntidad()));
        }
    }
    
    private void cargarEntidadDetalle(Tipo tipo) {
        switch(tipo.getValor()) {
            case 1: // ESTUDIANTES
                cargaEntidadPersona();
            break;
            
            case 2: 
                cargarEntidades(tipo);
            break;
            
            case 3: // FUNDACION UCR
            case 4: // EMPLEADO
                cargaEntidadPersona();
            break;
            
            case 5: 
                cargarEntidades(tipo);
            break;            
        }
    }
    
    private void cargaEntidadPersona() {
        this.visibleCampoEntidad = true;
    }
    
    private void cargarEntidades(Tipo tipo) {
        try {
            this.disableEntidades = false;
            this.itemsEntidad = new ArrayList<SelectItem>();
            switch(tipo.getValor()) {
                case 1: // ESTUDIANTES
                    this.listadoPersonas.estudiante = true;
                    this.listadoPersonas.funcionario = false;
                    this.listadoPersonas.inicializarListado();
                    this.visiblePanelBuscarPersonas = true;
                    this.visibleCampoEntidad = true;
                break;

                case 2: // UNIDAD EJECUTORA
                    this.visibleCampoEntidad = false;
                    List<UnidadEjecutora> unidades = unidadEjecutoraModel.listar();
                    for (UnidadEjecutora unidad : unidades) {
                        if(unidad.getDescripcion().equals(this.command.getEntidad())) {
                            this.command.setIdEntidad(unidad.getId());
                        }
                        this.itemsEntidad.add(new SelectItem(unidad.getId(), unidad.getDescripcion()));
                        this.entidades.put(unidad.getId(), unidad.getDescripcion());
                    }
                break;

                case 3: // FUNDACION UCR
                case 4: // EMPLEADO
                    this.listadoPersonas.estudiante = false;
                    this.listadoPersonas.funcionario = true;
                    this.listadoPersonas.inicializarListado();
                    this.visiblePanelBuscarPersonas = true;
                    this.visibleCampoEntidad = true;
                break;
                
                case 5: // ENTIDAD EXTERNA (CONVENIOS)
                    this.visibleCampoEntidad = false;
                    List<Convenio> convenios = this.convenioModel.listarActivos(unidadEjecutora, new Date());
                    if  (convenios.isEmpty()) {
                        Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.agregarBienController.cargarSubCategorias"));
                    } else {
                        for (Convenio convenio : convenios) {
                            if(convenio.getInstitucion().equals(this.command.getEntidad())) {
                                this.command.setIdEntidad(convenio.getId());
                            }
                            this.itemsEntidad.add(new SelectItem(convenio.getId(), convenio.getInstitucion()));
                            this.entidades.put(convenio.getId(), convenio.getInstitucion());
                        }
                    }
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
        Long idEntidad = this.command.getIdEntidad();
        String entidad = this.entidades.get(idEntidad);
        this.command.setEntidad(entidad);
    }
    
    public void abrirPanelPersonas() {
        Tipo tipo = this.tipoPorId(command.getIdTipoEntidad());
    
        switch(tipo.getValor()) {
                case 1: // ESTUDIANTES
                    this.listadoPersonas.estudiante = true;
                    this.listadoPersonas.funcionario = false;
                    this.listadoPersonas.inicializarListado();
                    this.visiblePanelBuscarPersonas = true;
                    this.visibleCampoEntidad = true;
                break;
                
                case 3: // FUNDACION UCR
                case 4: // EMPLEADO
                    this.listadoPersonas.estudiante = false;
                    this.listadoPersonas.funcionario = true;
                    this.listadoPersonas.inicializarListado();
                    this.visiblePanelBuscarPersonas = true;
                    this.visibleCampoEntidad = true;
                break;
        }
    }
    
    public void cerrarPanelPersonas() {
        this.visiblePanelBuscarPersonas = false;
    }
    
    public void selecionarPersona(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        Persona persona = (Persona) event.getComponent().getAttributes().get("personaSeleccionada");
        this.command.setEntidad(persona.getNombreCompleto());
        this.visiblePanelBuscarPersonas = false;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Generar Reporte">
    public void generarReporte() {
        try {
            //reportes/reporteSobrantes.jrxml 
            
            List<PrestamoCommand.BienDetalle> detalles = this.command.getListBienesDetalle();
            if (!detalles.isEmpty()) {
                String template = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reportePrestamo.jrxml");;
                String outputFile = cr.ac.ucr.framework.reporte.componente.utilitario.Util.ConvertirRutas("/reportes/reportePrestamo");

                ArrayList<ReporteBienDetalle> datosReporte = new ArrayList<ReporteBienDetalle>();
                for (PrestamoCommand.BienDetalle detalle : detalles) {
                    datosReporte.add(new ReporteBienDetalle(detalle));
                }

                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(datosReporte);
                JasperReport jasperReport = JasperCompileManager.compileReport(template);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, generarParametros(), beanColDataSource);

                JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile + Constantes.TIPO_REPORTE_PDF_EXTENSION);
                JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), "reporte('reportePrestamo','" + Constantes.TIPO_REPORTE_PDF_EXTENSION + "');");                    

                Mensaje.agregarInfo("Reporte generado exitosamente");
            } else {
                Mensaje.agregarErrorAdvertencia("No hay datos para el reporte");
            }            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
            this.mensaje = err.getMessage();
        }
    }
    
    public Map generarParametros() {
        Map parametros = new HashMap();
        parametros.put(ParametrosReporte.INSTITUCION, ParametrosReporte.VALOR_INSTITUCION);
        parametros.put(ParametrosReporte.NOMBRE_REPORTE, ParametrosReporte.VALOR_NOMBRE_REPORTE);
        parametros.put(ParametrosReporte.UNIDAD_CUSTODIO, unidadEjecutora.getDescripcion());
        parametros.put(ParametrosReporte.USUARIO, this.usuarioSIGEBI.getNombreCompleto());
        
        Tipo tipo = this.tipoPorId(this.command.getIdTipoEntidad());
        parametros.put(ParametrosReporte.ID, this.command.getId());
        parametros.put(ParametrosReporte.TIPO_ENTIDAD, tipo.getNombre());
        parametros.put(ParametrosReporte.ENTIDAD, this.command.getEntidad());
        parametros.put(ParametrosReporte.FECHA, this.command.getFecha());
        return parametros;
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

    public ListadoPersonas getListadoPersonas() {
        return listadoPersonas;
    }

    public void setListadoPersonas(ListadoPersonas listadoPersonas) {
        this.listadoPersonas = listadoPersonas;
    }
    
    public boolean isSolicitudRegistrada() {
        return solicitudRegistrada;
    }

    public void setSolicitudRegistrada(boolean solicitudRegistrada) {
        this.solicitudRegistrada = solicitudRegistrada;
    }

    public Map<Long, String> getEntidades() {
        return entidades;
    }

    public void setEntidades(Map<Long, String> entidades) {
        this.entidades = entidades;
    }

    public boolean isVisibleCampoEntidad() {
        return visibleCampoEntidad;
    }

    public void setVisibleCampoEntidad(boolean visibleCampoEntidad) {
        this.visibleCampoEntidad = visibleCampoEntidad;
    }

    public boolean isVisiblePanelBuscarPersonas() {
        return visiblePanelBuscarPersonas;
    }

    public void setVisiblePanelBuscarPersonas(boolean visiblePanelBuscarPersonas) {
        this.visiblePanelBuscarPersonas = visiblePanelBuscarPersonas;
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
        if (Constantes.ESTADO_PRESTAMO_CREADO.equals(this.command.getEstado().getValor()) && this.solicitudRegistrada) {
            this.visibleBotonSolicitar = true;
        }
        return visibleBotonSolicitar;
    }
    
    public boolean isVisibleBotonDevolver() {
        this.visibleBotonDevolver = false;
        if (Constantes.ESTADO_PRESTAMO_APROBADO.equals(this.command.getEstado().getValor()) && this.solicitudRegistrada) {
            this.visibleBotonDevolver = true;
        }
        return visibleBotonDevolver;
    }    

    public boolean isVisibleBotonRechazar() {
        this.visibleBotonRechazar = false;
        if (Constantes.ESTADO_PRESTAMO_SOLICITADO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonRechazar = true;
        }
        return visibleBotonRechazar;
    }

    public boolean isVisibleBotonAprobar() {
        this.visibleBotonAprobar = false;
        if (Constantes.ESTADO_PRESTAMO_SOLICITADO.equals(this.command.getEstado().getValor()) && this.autorizadoAprobar) {
            this.visibleBotonAprobar = true;
        }
        return visibleBotonAprobar;
    }
    
    public boolean isVisibleBotonAnular() {
        this.visibleBotonAnular = false;
        if (Constantes.ESTADO_PRESTAMO_SOLICITADO.equals(this.command.getEstado().getValor())) {
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
        if (Constantes.ESTADO_PRESTAMO_CREADO.equals(this.command.getEstado().getValor()) && this.solicitudRegistrada) {
            this.visibleBotonSolicitarBien = true;
        }
        return visibleBotonSolicitarBien;        
    }

    public boolean isVisibleBotonDevolverBien() {
        this.visibleBotonDevolverBien = false;
        if (Constantes.ESTADO_PRESTAMO_APROBADO.equals(this.command.getEstado().getValor()) && this.solicitudRegistrada) {
            this.visibleBotonDevolverBien = true;
        }
        return visibleBotonDevolverBien;
    }
    
    public boolean isVisibleBotonRechazarBien() {
        this.visibleBotonRechazarBien = false;
        if (this.autorizadoAprobar) {
            this.visibleBotonRechazarBien = true;
        }
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

    public boolean isVisibleBotonDevolverObservacion() {
        this.visibleBotonDevolverObservacion = false;
        if (this.accion == ACCION_DEVOLVER) {
            this.visibleBotonDevolverObservacion = true;    
        }
        return visibleBotonDevolverObservacion;
    }

    public boolean isVisibleBotonDevolverBienObservacion() {
        this.visibleBotonDevolverBienObservacion = false;
        if (this.accion == ACCION_DEVOLVER_BIEN) {
            this.visibleBotonDevolverBienObservacion = true;    
        }        
        return visibleBotonDevolverBienObservacion;
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