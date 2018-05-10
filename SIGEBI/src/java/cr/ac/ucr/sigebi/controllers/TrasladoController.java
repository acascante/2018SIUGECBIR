/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.daos.ReporteDao;
import cr.ac.ucr.sigebi.domain.Autorizacion;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.SolicitudDetalleTraslado;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Justificacion;
import cr.ac.ucr.sigebi.domain.Notificacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.SolicitudTraslado;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.AutorizacionModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.JustificacionModel;
import cr.ac.ucr.sigebi.models.NotificacionModel;
import cr.ac.ucr.sigebi.models.TipoModel;
import cr.ac.ucr.sigebi.models.TrasladoModel;
import cr.ac.ucr.sigebi.models.UbicacionModel;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
import cr.ac.ucr.sigebi.models.UsuarioModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.utils.NodoSIGEBI;
import cr.ac.ucr.sigebi.utils.TreeUbicacionSIGEBI;
import static java.lang.Compiler.command;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @author jorge.serrano
 */
@Controller(value = "trasladoController")
@Scope("session")
public class TrasladoController extends ListadoBienesGeneralController {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    Estado estadoGeneralPendiente;
    Estado estadoGeneralActivo;
    Estado estadoGeneralAnulado;
    Estado estadoGeneralAprobado;
    Estado estadoGeneralRechazado;
    
    Estado estadoBienActivo;
    Estado estadoBienTraslado;

    //Variables Traslados
    List<SolicitudTraslado> traslados;
    SolicitudTraslado traslado;
    
    //Variables Traslado Detalle
    List<SolicitudDetalleTraslado> trasladoDetalle;
    List<SolicitudDetalleTraslado> trasladoDetalleEliminar;
    Map<Long, SolicitudDetalleTraslado> bienesAsociadosTraslado;

    UnidadEjecutora unidadOrigen;
    UnidadEjecutora unidadDestino;

    DateFormat formatter;// = new SimpleDateFormat("dd/MM/yyyy");
    String fechaRegistro;

    boolean permiteEdicion;
    boolean permiteRecibir;
    boolean permiteAnular;

    // VARIBLES PARA COMPROBAR USUARIOS CON PERMISOS
    Autorizacion autorizacionEnviar;
    Autorizacion autorizacionRecibir;

    // USUARIOS POR DOCUMENTO
    Map<String, Usuario> usuariosEnviar;
    Map<String, Usuario> usuariosRecibir;

    @Resource private TrasladoModel trasladoModel;
    @Resource  private EstadoModel estadoModel;
    @Resource private TipoModel tipoModel;
    @Resource private AutorizacionModel autorizacionModel;
    @Resource private AutorizacionRolPersonaModel autorizacionRolPersonaModel;
    @Resource private UsuarioModel usuarioModel;
    @Resource private BienModel bienModel;
    
    Usuario usuarioRegistradoClass;


    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's & SET's">

    public Estado getEstadoGeneralActivo() {
        return estadoGeneralActivo;
    }

    public void setEstadoGeneralActivo(Estado estadoGeneralActivo) {
        this.estadoGeneralActivo = estadoGeneralActivo;
    }
    public Estado getEstadoGeneralPendiente() {
        return estadoGeneralPendiente;
    }

    public void setEstadoGeneralPendiente(Estado estadoGeneralPendiente) {
        this.estadoGeneralPendiente = estadoGeneralPendiente;
    }

    public Estado getEstadoGeneralRechazado() {
        return estadoGeneralRechazado;
    }

    public void setEstadoGeneralRechazado(Estado estadoGeneralRechazado) {
        this.estadoGeneralRechazado = estadoGeneralRechazado;
    }
    
    public boolean isPermiteRecibir() {
        return permiteRecibir;
    }

    public void setPermiteRecibir(boolean permiteRecibir) {
        this.permiteRecibir = permiteRecibir;
    }

    public boolean isPermiteAnular() {
        return permiteAnular;
    }

    public void setPermiteAnular(boolean permiteAnular) {
        this.permiteAnular = permiteAnular;
    }

    public boolean isPermiteEdicion() {
        return permiteEdicion;
    }

    public void setPermiteEdicion(boolean permiteEdicion) {
        this.permiteEdicion = permiteEdicion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<SolicitudTraslado> getTraslados() {
        return traslados;
    }

    public void setTraslados(List<SolicitudTraslado> traslados) {
        this.traslados = traslados;
    }

    public SolicitudTraslado getTraslado() {
        return traslado;
    }

    public void setTraslado(SolicitudTraslado traslado) {
        this.traslado = traslado;
    }

    public UnidadEjecutora getUnidadOrigen() {
        return unidadOrigen;
    }

    public void setUnidadOrigen(UnidadEjecutora unidadOrigen) {
        this.unidadOrigen = unidadOrigen;
    }

    public UnidadEjecutora getUnidadDestino() {
        return unidadDestino;
    }

    public void setUnidadDestino(UnidadEjecutora unidadDestino) {
        this.unidadDestino = unidadDestino;
    }

    public List<SolicitudDetalleTraslado> getTrasladoDetalle() {
        return trasladoDetalle;
    }

    public void setTrasladoDetalle(List<SolicitudDetalleTraslado> trasladoDetalle) {
        this.trasladoDetalle = trasladoDetalle;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Navegacion">
    public void listadoTraslados(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            listadoInicializaDatos();
            Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    public void nuevoRegistro(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            inicializaTraslado();

            Util.navegar(Constantes.KEY_VISTA_TRASLADO_DETALLE);

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    public void regresar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            listadoInicializaDatos();
            Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    
    
    
    public TrasladoController() {
        justificacion = new Justificacion();
        treeUbicacionSIGEBI = new TreeUbicacionSIGEBI(unidadEjecutora);
    } 

    @PostConstruct
    private void incializaDatos() {
        try{
            //Asigno Usuario EnvÃ­a
            usuarioRegistradoClass = usuarioModel.buscarPorId(codPersonaReg);

            //TODO cambiar implementacion de consulta de estados, VER ListarNotificacionesController
            estadoGeneralPendiente = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_PENDIENTE);
            estadoGeneralActivo = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO);
            estadoGeneralAnulado = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ANULADO); //ESTADO_GENERAL_ANULADO;

            estadoGeneralAprobado = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_APROBADO);
            estadoGeneralRechazado = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_RECHAZADO);

            estadoBienActivo = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_ACTIVO);
            estadoBienTraslado = estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_TRASLADO);
                        
            estadosOptions = new ArrayList<SelectItem>();

            estadosOptions.add(new SelectItem(estadoGeneralPendiente.getId().toString(), estadoGeneralPendiente.getNombre()));
            estadosOptions.add(new SelectItem(estadoGeneralActivo.getId().toString(), estadoGeneralActivo.getNombre()));
            estadosOptions.add(new SelectItem(estadoGeneralAnulado.getId().toString(), estadoGeneralAnulado.getNombre()));
            estadosOptions.add(new SelectItem(estadoGeneralAprobado.getId().toString(), estadoGeneralAprobado.getNombre()));
            
            fltIdUnidad = "";
            fltNombreUnidad = "";

            // Busco Usuarios Enviar y Recibir
            autorizacionEnviar = autorizacionModel.buscarPorCodigo(Constantes.CODIGO_AUTORIZACION_TRASLADO_ENVIAR);
            autorizacionRecibir = autorizacionModel.buscarPorCodigo(Constantes.CODIGO_AUTORIZACION_TRASLADO_RECIBIR);
            
            inicializaTraslado();
            listadoInicializaDatos();

        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.Traslado.Err.Cargar"));
        }
        
    }

    private void inicializaTraslado() {
        try {
            traslado = new SolicitudTraslado(estadoGeneralPendiente, unidadEjecutora);
            trasladoDetalle = new ArrayList<SolicitudDetalleTraslado>();
            trasladoDetalleEliminar = new ArrayList<SolicitudDetalleTraslado>();
            
            trasladoDetalle = new ArrayList<SolicitudDetalleTraslado>(  );
            bienesAsociados = new ArrayList<Bien>();
            bienesAsociadosTraslado =  new HashMap<Long, SolicitudDetalleTraslado>();
            bienesSeleccionados = new HashMap<Long, Bien>();
            
            permiteEdicion = true;

            //traslado.setEstado(estadoGeneralPendiente);
            unidadDestino = new UnidadEjecutora();

            unidadOrigen = unidadModel.buscarPorId(unidadEjecutora.getId());
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            //Date actaFecha = formatter.parse(formatter.format(traslado.getFecha()));
            fechaRegistro = formatter.format(traslado.getFecha());
            iniciaUbicaciones();
            buscarUnidades();

            //Asigno Unidad Origen
            traslado.setUnidadEjecutora(unidadOrigen);

//            estadosBienes = null;
            consultaBienes = 1;
            iniciaListadoBienes();
            
            
//          Usuarios con permisos para enviar y recibir
            usuariosEnviar = new HashMap<String, Usuario>();
            usuariosRecibir = new HashMap<String, Usuario>();
            List<AutorizacionRolPersona> usrsEnviar = autorizacionRolPersonaModel.buscar(autorizacionEnviar, unidadEjecutora, usuarioRegistradoClass);
            for (AutorizacionRolPersona usr : usrsEnviar) {
                usuariosEnviar.put(usr.getUsuarioSeguridad().getId(), usr.getUsuarioSeguridad());
            }
            List<AutorizacionRolPersona> usrsRecibir = autorizacionRolPersonaModel.buscar(autorizacionRecibir, unidadEjecutora, usuarioRegistradoClass);
            for (AutorizacionRolPersona usr : usrsRecibir) {
                usuariosRecibir.put(usr.getUsuarioSeguridad().getId(), usr.getUsuarioSeguridad());
            }

        } catch (Exception err) {
            Mensaje.agregarErrorFatal(err.getMessage());
        }

    }

    public void verDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            inicializaTraslado();
            SolicitudTraslado item = (SolicitudTraslado) pEvent.getComponent().getAttributes().get("itemSeleccionado");
            cargarDatosTraslado(item);
            Util.navegar(Constantes.KEY_VISTA_TRASLADO_DETALLE);
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }
    
    public void verDetalle(SolicitudTraslado item, String vistaOrigen) {
        try {
            inicializaTraslado();
            this.vistaOrigen = vistaOrigen;
            cargarDatosTraslado(item);
            Util.navegar(Constantes.KEY_VISTA_TRASLADO_DETALLE);
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }
    
    private void cargarDatosTraslado(SolicitudTraslado item){
        if (item.getId() > 0) {
            traslado = item;

            //actaFecha = formatter.parse(formatter.format(traslado.getFecha()));
            fechaRegistro = formatter.format(traslado.getFecha());
            permiteEdicion = permitirEdicion();
            permiteRecibir = permitirRecibir();
            permiteAnular = permitirAnular();

            trasladoDetalle = trasladoModel.traerBienesTraslado(traslado);
            bienesAsociadosTraslado = new HashMap<Long, SolicitudDetalleTraslado>();
            for (SolicitudDetalleTraslado valor : trasladoDetalle) {
                if (valor.getEstado().getId().equals(estadoGeneralPendiente.getId())) {
                    valor.setMarcado(permiteRecibir);
                }
                bienesAsociados.add(valor.getBien());
                bienesSeleccionados.put(valor.getBien().getId(), valor.getBien());
                bienesAsociadosTraslado.put(valor.getBien().getId(), valor);
            }
            trasladoDetalle = new ArrayList<SolicitudDetalleTraslado>(bienesAsociadosTraslado.values());
            this.listarBienes();
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Listado Traslados">
    String fltIdTraslado = "";
    String fltUnidadOrigen = "";
    String fltUnidadDestino = "";
    String fltFecha = "";
    String fltEstados = "";

    public void listarTraslados() {
        try {
            traslados = trasladoModel.trasladosListado(
                    unidadOrigen,
                    fltIdTraslado,
                    fltUnidadOrigen,
                    fltUnidadDestino,
                    fltFecha,
                    fltEstados,
                    this.getPrimerRegistro() - 1,
                    this.getUltimoRegistro()
            );
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    private void listadoInicializaDatos() {
        try {

            if (fltEstados.equals("")) {
                fltEstados = "-1";
            }

            this.listadoCantidadRegistros();
            this.setPrimerRegistro(1);
            this.listarTraslados();
        } catch (Exception err) {

        }
    }

    public void listadoCantidadRegistros() {
        try {
            Long contador;

            contador = trasladoModel.contarTrasladosListado(
                    unidadOrigen,
                    fltIdTraslado,
                    fltUnidadOrigen,
                    fltUnidadDestino,
                    fltFecha,
                    fltEstados
            );

            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());

        } catch (Exception err) {

        }
    }

    public void trasladoIrPagina(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPag"));
        this.getPrimerRegistroPagina(numeroPagina);
        this.listarTraslados();
    }

    /**
     * Pasa al siguiente sub-set de estudiantes
     *
     * @param pEvent
     */
    public void trasladoSiguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listarTraslados();
    }

    /**
     * Pasa al anterior sub-set de estudiantes
     *
     * @param pEvent
     */
    public void trasladoAnterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listarTraslados();
    }

    /**
     * Pasa al primero sub-set de estudiantes
     *
     * @param pEvent
     */
    public void trasladoPrimero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listarTraslados();
    }

    /**
     * Pasa al ultimo sub-set de Estudiantes
     *
     * @param pEvent
     */
    public void trasladoUltimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listarTraslados();
    }

    /**
     * Cambia la cantidad de registros por pÃ¡gina
     *
     * @param pEvent
     */
    public void trasladoCambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));
        this.setPrimerRegistro(1);
        this.listarTraslados();

    }

    public void trasladosCambioFiltro() {
        //this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));  
        this.setPrimerRegistro(1);
        listadoInicializaDatos();

        //Mensaje.agregarErrorAdvertencia("Cambio en filtro no parametro.");
    }

    public void trasladosCambioFiltro(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        // Obtengo el valor seleccionado
        String valor = event.getNewValue().toString();
        //this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));        
        this.setPrimerRegistro(1);
        listadoInicializaDatos();

        //Mensaje.agregarErrorAdvertencia("Cambio en filtro.");
    }

    public String getFltIdTraslado() {
        return fltIdTraslado;
    }

    public void setFltIdTraslado(String fltIdTraslado) {
        this.fltIdTraslado = fltIdTraslado;
    }

    public String getFltUnidadOrigen() {
        return fltUnidadOrigen;
    }

    public void setFltUnidadOrigen(String fltUnidadOrigen) {
        this.fltUnidadOrigen = fltUnidadOrigen;
    }

    public String getFltUnidadDestino() {
        return fltUnidadDestino;
    }

    public void setFltUnidadDestino(String fltUnidadDestino) {
        this.fltUnidadDestino = fltUnidadDestino;
    }

    public String getFltFecha() {
        return fltFecha;
    }

    public void setFltFecha(String fltFecha) {
        this.fltFecha = fltFecha;
    }

    public String getFltEstados() {
        return fltEstados;
    }

    public void setFltEstados(String fltEstado) {
        this.fltEstados = fltEstado;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public void guardarTraslado(ActionEvent pEvent) {
        try {

            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            if (validarRegistro()) {
                trasladoModel.guardar(traslado);

                //Actualizo el estado de lso Bienes que se eliminan
                Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
                Tipo tipoMovimientoTraslado = this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_BIEN);                
                for(SolicitudDetalleTraslado item : trasladoDetalleEliminar){
                    //Se registra el movimiento y se actualiza el bien
                    item.getBien().setEstado(estadoBienActivo);
                    bienModel.cambiaEstadoBien(item.getBien(), item.getBien().getEstado(), Util.getEtiquetas("sigebi.Traslado.Mns.Movimiento"), telefono, usuarioRegistradoClass, tipoMovimientoTraslado);

                }
                
                //Actualizo el estado de los Bienes que se agregan
                for(SolicitudDetalleTraslado item : trasladoDetalle){
                    if(item.getBien().getEstado().getId() != estadoBienTraslado.getId()){
                        //Se registra el movimiento y se actualiza el bien
                        item.getBien().setEstado(estadoBienTraslado);
                        bienModel.cambiaEstadoBien(item.getBien(), item.getBien().getEstado(), Util.getEtiquetas("sigebi.Traslado.Mns.Movimiento"), telefono, usuarioRegistradoClass, tipoMovimientoTraslado);
                    }
                }
                
                trasladoModel.eliminarBienes( trasladoDetalleEliminar );
                trasladoModel.guardarBienes(trasladoDetalle);
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Traslado.Mns.ExitoGuardar"));
                listarTraslados();
            }
        } catch (Exception err) {
            Mensaje.agregarErrorFatal(Util.getEtiquetas("sigebi.Modal.General.Error.Guardar"));
        }
    }
    
    
    private boolean validarRegistro() {
        try {
            String mensaje = "";
            //Validar Unidad Recibe
            if ((traslado.getUnidadEjecutoraDestino().getId()== null) || (traslado.getUnidadEjecutoraDestino().getId() == 0)) {
                mensaje = Util.getEtiquetas("sigebi.Traslado.Mns.SelecUnidad");
            }

            //Validar Ubicacion
            if ((traslado.getUbicacion().getId() == null) || (traslado.getUbicacion().getId() == 0)) {
                mensaje = Util.getEtiquetas("sigebi.Traslado.Mns.SelecUbicacion");
            }
            
            traslado.setUsuario(usuarioRegistradoClass);

            if (!mensaje.equals("")) {
                Mensaje.agregarErrorAdvertencia(mensaje);
                return false;
            }
            return true;
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
            return false;
        }
    }

    // Enviar a Unidad de destino
    public void enviarHaciaDestino() {
        //Cambiar estado del traslado
        try {
            if (validarRegistro() && (trasladoDetalle.size() > 0)) {
                traslado.setEstado(estadoGeneralActivo);
                trasladoModel.guardar(traslado);

                trasladoModel.eliminarBienes( trasladoDetalleEliminar );
                trasladoModel.guardarBienes(trasladoDetalle);
                accion = constAccionEnviarARevision;
                enviarNotificacion();
                listarTraslados();
                Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);
                
                //CAMBIO EL ESTADO DE LOS BIENES QUE VAN EN EL DETALLE
                for(SolicitudDetalleTraslado detalle : trasladoDetalle){
                    Bien bien = bienModel.buscarPorId(detalle.getBien().getId());
                    bien.setUnidadEjecutora(traslado.getUnidadEjecutoraDestino());
                    if( bien.getUnidadEjecutora().getId().equals(this.unidadEjecutora.getId()) ){
                        bien.setEstadoInterno(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_TRASLADO));
                        bienModel.actualizar(bien);
                    }
                }
                
                
            }else{
                if(!trasladoDetalle.isEmpty())
                    Mensaje.agregarErrorFatal(Util.getEtiquetas("sigebi.Acta.MsnError.BienesSeleccionados"));
            }

        } catch (Exception err) {
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }

    // Solicitar Correcciones
    public void solicitarCorreccion() {
        try {
            accion = constAccionSolictCorreccion;
            mostrarJustificacionGeneral();

        } catch (Exception err) {
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }

    private void aplicarSolicitudCorreccion() {
        traslado.setEstado(estadoGeneralPendiente);
        trasladoModel.guardar(traslado);

        
        //CAMBIO EL ESTADO INTERNO DE LOS BIENES QUE VAN EN EL DETALLE
        for(SolicitudDetalleTraslado detalle : trasladoDetalle){
            Bien bien = bienModel.buscarPorId(detalle.getBien().getId());
            bien.setUnidadEjecutora(traslado.getUnidadEjecutoraDestino());
            //if( bien.getUnidadEjecutora().getId().equals(this.unidadEjecutora.getId()) ){
            bien.setEstadoInterno(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL));
            bienModel.actualizar(bien);
           // }
        }
        
        listadoInicializaDatos();
        Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);

    }

    private void aplicarRevision() {
        if (validarRegistro()) {
            traslado.setEstado(estadoGeneralPendiente);
            trasladoModel.guardar(traslado);

            listadoInicializaDatos();
            Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);
        }
    }

    public void devolverParaRevision() {
        accion = constAccionSolictCorreccion;
        mostrarJustificacionGeneral();
    }

    public void anularTraslado() {
        //Cambiar estado del traslado
        try {
            accion = constAccionAnularTraslado;
            mostrarJustificacionGeneral();
        } catch (Exception err) {
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }

    private void aplicarAnularTraslado() {

        traslado.setEstado(estadoGeneralAnulado);
        trasladoModel.guardar(traslado);

        //CAMBIO EL ESTADO INTERNO DE LOS BIENES QUE VAN EN EL DETALLE
        for(SolicitudDetalleTraslado detalle : trasladoDetalle){
            Bien bien = bienModel.buscarPorId(detalle.getBien().getId());
            bien.setUnidadEjecutora(traslado.getUnidadEjecutoraDestino());
            //if( bien.getUnidadEjecutora().getId().equals(this.unidadEjecutora.getId()) ){
            bien.setEstadoInterno(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL));
            bienModel.actualizar(bien);
           // }
        }
        
        listadoInicializaDatos();
        Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);

    }

    private boolean permitirEdicion() {
        //Verifica Unidad de Origen
        if (!traslado.getUnidadEjecutora().getId().equals(unidadEjecutora.getId())) {
            return false;
        }
        //Verifica permisos en DocumentoRolPersona
        if (!usuariosEnviar.containsKey(this.codPersonaReg)) {
            return false;
        }

        // Verifica que estÃ© en estado PENDIENTE
        return traslado.getEstado().getId().equals(estadoGeneralPendiente.getId());
        //return false;
    }

    private boolean permitirRecibir() {
        //Verifica Unidad de Destino
        if (!traslado.getUnidadEjecutoraDestino().getId().equals(unidadEjecutora.getId())) {
            return false;
        }
        //Verifica permisos en DocumentoRolPersona
        if (!usuariosRecibir.containsKey(this.codPersonaReg)) {
            return false;
        }

        // Verifica que estÃ© en estado ACTIVO
        return traslado.getEstado().getId().equals(estadoGeneralActivo.getId());
        //return true;
    }

    private boolean permitirAnular(){
        if( (traslado.getId()!=null)&&(traslado.getId() > 0) ){
            if (traslado.getEstado().equals(estadoGeneralActivo)) {
                return usuariosEnviar.containsKey(this.codPersonaReg) || permiteRecibir;
            } else {
                return false;
            }
        }
        return false;
    }
    
    //ACCIONES BIENES
    public void aprobarBien(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            accion = constAccionAceptarBien;
            bienDetalle = new SolicitudDetalleTraslado();
            //Se obtiene el bien que se va a aprobar 
            bienDetalle = (SolicitudDetalleTraslado) pEvent.getComponent().getAttributes().get("bienSeleccionado");

            mostrarConfirmacion();

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }

    }
    
    private void aplicarAprobarBien() {
        try {
            //Primero se debe aplicar el cambio
            bienDetalle.setEstado(estadoGeneralAprobado);
            bienDetalle.setFecha(new Date());
            bienDetalle.setUsuarioRecibe(usuarioRegistradoClass);
            trasladoModel.guardarBien(bienDetalle);
            bienDetalle.setMarcado(false);

            //Busacamos que esten todos aprobados para cambiar estado del traslado
            //Lo cambiamos de unidad
            Bien bien = bienModel.buscarPorId(bienDetalle.getBien().getId());
            bien.setUnidadEjecutora(traslado.getUnidadEjecutoraDestino());
            bien.setEstado(estadoBienActivo);
            bien.setEstadoInterno(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN_INTERNO, Constantes.ESTADO_INTERNO_BIEN_NORMAL));
            
            //Se registra el movimiento y se actualiza el bien
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
            bienModel.cambiaEstadoBien(bien, bien.getEstado(), Util.getEtiquetas("sigebi.Traslado.Movimiento"), telefono, 
                    usuarioSIGEBI, this.tipoPorDominioValor(Constantes.DOMINIO_REGISTRO_MOVIMIENTO, Constantes.TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_BIEN));
            
            //PENDIENTE SINCRONIZAR CON SIAF
            if (estaAprobada()) {
                aprobarTraslado();
            }
            Mensaje.agregarErrorAdvertencia("PENDIENTE SINCRONIZAR CON SIAF");        //Lo sincronizamos

            cerrarConfirmacion();

        } catch (Exception err) {
            Mensaje.agregarErrorFatal(Util.getEtiquetas("sigebi.Traslado.Mns.ErrorBien"));
        }

    }
    
    private boolean estaAprobada() {
        for (SolicitudDetalleTraslado item : trasladoDetalle) {
            if (!item.getEstado().getId().equals(estadoGeneralAprobado.getId())) {
                return false;
            }
        }
        return true;
    }

    private void aprobarTraslado() {
        try {
            traslado.setEstado(estadoGeneralAprobado);
            trasladoModel.guardar(traslado);

            listadoInicializaDatos();
            Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);
        } catch (Exception err) {
            Mensaje.agregarErrorFatal(Util.getEtiquetas("sigebi.Traslado.Mns.AprobarTraslado"));
        }
    }

    public void rechazarBien(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            accion = constAccionRechazarBien;
            bienDetalle = new SolicitudDetalleTraslado();
            //Se obtiene el bien que se va a aprobar 
            bienDetalle = (SolicitudDetalleTraslado) pEvent.getComponent().getAttributes().get("bienSeleccionado");

            justificacion = new Justificacion();
            iniciarJustificacion();

            panelNotificacionVisible = true;
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }

    }

    private void aplicarRechazoBien() {
        //try {
            bienDetalle.setEstado(estadoGeneralRechazado);
            bienDetalle.setFecha(new Date());
            bienDetalle.setUsuarioRecibe(usuarioRegistradoClass);
            trasladoModel.guardarBien(bienDetalle);
            //Mensaje.agregarErrorAdvertencia("Estado: "+bienDetalle.getId().getNombre() + " Bien :" + bienDetalle.getBien().getDescripcion() );
//        } catch (Exception err) {
//            Mensaje.agregarErrorFatal(Util.getEtiquetas("sigebi.Traslado.Mns.ErrorBien"));
//        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Buscar Unidades">
    @Resource
    UnidadEjecutoraModel unidadModel;

    public void mostrarUnidades(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            unidadesVisible = true;
        } catch (Exception err) {

        }
    }

    public void cerrarUnidades(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            unidadesVisible = false;
        } catch (Exception err) {

        }
    }

    public void cambioFiltroUnidad(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            buscarUnidades();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.cambioFiltro"));
        }

    }

    private void buscarUnidades() {
        try {
            unidadesEjecutoras = new ArrayList<UnidadEjecutora>();
            unidadesEjecutoras = unidadModel.listar(fltIdUnidad.toUpperCase(), fltNombreUnidad.toUpperCase(), unidadEjecutora.getId());
            //Mensaje.agregarInfo(unidadesEjecutoras.size()+ " unidades encontradas ");
        } catch (Exception err) {
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }

    public void selecUnidad(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            UnidadEjecutora unidad = (UnidadEjecutora) pEvent.getComponent().getAttributes().get("unidadSeleccionada");

            traslado.setUnidadEjecutoraDestino(unidad);// = unidad;
            traslado.setUbicacion(new Ubicacion());

            unidadesVisible = false;
        } catch (Exception err) {
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }

    List<UnidadEjecutora> unidadesEjecutoras;

    boolean unidadesVisible;
    String fltIdUnidad;
    String fltNombreUnidad;

    public String getFltNombreUnidad() {
        return fltNombreUnidad;
    }

    public void setFltNombreUnidad(String fltNombreUnidad) {
        this.fltNombreUnidad = fltNombreUnidad;
    }

    public String getFltIdUnidad() {
        return fltIdUnidad;
    }

    public void setFltIdUnidad(String fltIdUnidad) {
        this.fltIdUnidad = fltIdUnidad;
    }

    public List<UnidadEjecutora> getUnidadesEjecutoras() {
        return unidadesEjecutoras;
    }

    public void setUnidadesEjecutoras(List<UnidadEjecutora> unidadesEjecutoras) {
        this.unidadesEjecutoras = unidadesEjecutoras;
    }

    public boolean isUnidadesVisible() {
        return unidadesVisible;
    }

    public void setUnidadesVisible(boolean unidadesVisible) {
        this.unidadesVisible = unidadesVisible;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Buscar Ubicaciones">
    @Resource
    private UbicacionModel ubicModel;

    boolean habilitaUbicacion = false;
    
    private TreeUbicacionSIGEBI treeUbicacionSIGEBI;


    public TreeUbicacionSIGEBI getTreeUbicacionSIGEBI() {
        return treeUbicacionSIGEBI;
    }

    public void setTreeUbicacionSIGEBI(TreeUbicacionSIGEBI treeUbicacionSIGEBI) {
        this.treeUbicacionSIGEBI = treeUbicacionSIGEBI;
    }

    
    private void iniciaUbicaciones() {
        try {
            treeUbicacionSIGEBI = new TreeUbicacionSIGEBI(traslado.getUnidadEjecutoraDestino());
            treeUbicacionSIGEBI.setUbicacionModel(ubicModel);
        } catch (Exception err) { 
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }

    public void mostrarUbicaciones(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            if( (traslado.getUnidadEjecutoraDestino() != null) && (traslado.getUnidadEjecutoraDestino().getId() > 0l) ){
                iniciaUbicaciones();
                treeUbicacionSIGEBI.abrirPantalla();
            }else
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Traslado.Mns.PendienteUbicacion"));
                
        } catch (Exception err) {
            Mensaje.agregarErrorFatal(err.getMessage());
        }

    }

        
    public void ubicacionSelectCambio(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            //Se busca el nodo seleccionado
            NodoSIGEBI nodoSIGEBI = (NodoSIGEBI) event.getComponent().getAttributes().get("nodoSeleccionado");

            if(nodoSIGEBI != null){
                //Se asigna la ubicacion 
                traslado.setUbicacion((Ubicacion) nodoSIGEBI.getObject());
                if (traslado.getUbicacion() != null) {
                    traslado.getUbicacion().setIdTemporal(traslado.getUbicacion().getId());
                }
            }
 
            //Se cierra el panel
            treeUbicacionSIGEBI.setPresentaPanelUbicacion(false);            

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambiarUbicacion"));
        }
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos Listado Bienes">
    public void abrirListaBienes() {
        try {
            consultaBienes = 1;            
            iniciaListadoBienes();
            mostrarDialogBienes = true;
        } catch (Exception err) {
        }
    }

    public void cerrarListaBienes() {
        try {
            mostrarDialogBienes = false;
        } catch (Exception err) {
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Justificaciones y Notificaciones">
    @Resource
    JustificacionModel justificacionModel;

    @Resource private NotificacionModel notificacionModel;

    public void agergarJustificacion() {
        try {
            if (justificacion.getObservaciones().length() > 3) {
                justificacionModel.guardar(justificacion);
                panelNotificacionVisible = false;

                switch (accion) {
//                    case constAccionEnviarARevision:
//                        aplicarRevision();
//                        break;
                    case constAccionRechazarBien:
                        aplicarRechazoBien();
                        enviarNotificacion();
                        break;
                    case constAccionAnularTraslado:
                        aplicarAnularTraslado();
                        enviarNotificacion();
                        break;
                    case constAccionSolictCorreccion:
                        aplicarSolicitudCorreccion();
                        enviarNotificacion();
                        break;
                }
                
                cargarDatosTraslado(traslado);
                
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Traslado.Mns.ExitoModificar"));
            } else {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Justificacion.Err.Observaciones"));
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Justificacion.Err.Guardar"));
        }
    }

    public void cancelarJustificacion() {

        justificacion = new Justificacion();
        bienDetalle = new SolicitudDetalleTraslado();
        panelNotificacionVisible = false;
    }

    public void mostrarJustificacionGeneral() {
        justificacion = new Justificacion();
        bienDetalle = new SolicitudDetalleTraslado();

        iniciarJustificacion();

        panelNotificacionVisible = true;
    }

    public void mostrarJustificacionDeBien(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            accion = constAccionRechazarBien;
            bienDetalle = new SolicitudDetalleTraslado();
            //Se obtiene el bien que se va a aprobar 
            bienDetalle = (SolicitudDetalleTraslado) pEvent.getComponent().getAttributes().get("bienSeleccionado");

            justificacion = new Justificacion();

            iniciarJustificacion();

            panelNotificacionVisible = true;

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }

    }

    private void enviarNotificacion() {
        try {
            String mensaje = getMensajeEmail();

            Notificacion correo = new Notificacion();
//            correo.setAsunto(Util.getEtiquetas("sigebi.Traslado.Email.Asunto"));
//            correo.setDestinatario("jorse_9@yahoo.com");
//            correo.setFecha(new Date());
//            correo.setMensaje(mensaje);
//
//            notificacionModel.enviarCorreo(correo);


            List<AutorizacionRolPersona> usrsNotificar = new ArrayList<AutorizacionRolPersona>();

            switch (accion) {
                case constAccionEnviarARevision:
                    usrsNotificar = autorizacionRolPersonaModel.buscar(autorizacionEnviar, unidadDestino);
                    break;
                default:
                    usrsNotificar = autorizacionRolPersonaModel.buscar(autorizacionRecibir, unidadDestino);
                    break;
            }

            for (AutorizacionRolPersona usr : usrsNotificar) {
                correo.setDestinatario(usr.getUsuarioSeguridad().getCorreo());
              //  notificacionModel.enviarCorreo(correo);
            }

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Traslado.Err.Email"));
        }
    }

    private String getMensajeEmail() {

        String mensaje = "";

        switch (accion) {
            case constAccionEnviarARevision:
                mensaje = Util.getEtiquetas("sigebi.Traslado.Email.Mensaje1") + "<br /><br />";
                mensaje += "Traslado # " + justificacion.getIdDocumento().toString() + "<br /><br />";
                mensaje += Util.getEtiquetas("sigebi.Traslado.Email.Mensaje2").replaceAll("{0}", traslado.getUnidadEjecutoraDestino().getDescripcion()) + "<br /><br />";
                mensaje += Util.getEtiquetas("sigebi.Traslado.Email.Mensaje3");
                break;
            case constAccionAnularTraslado:
                mensaje = Util.getEtiquetas("sigebi.Traslado.Email.Mensaje1") + "<br /><br />";
                mensaje += justificacion.getEncabezado() + "<br />";
                mensaje += "Usuario: " + justificacion.getUsuarioRegistra().getNombreCompleto() + "<br /><br />";
                mensaje += "Traslado # " + justificacion.getIdDocumento().toString() + "<br /><br />";
                mensaje += justificacion.getObservaciones() + "<br /><br />";
                mensaje += Util.getEtiquetas("sigebi.Traslado.Email.Mensaje3");
                break;
            case constAccionRechazarBien:
                mensaje = Util.getEtiquetas("sigebi.Traslado.Email.Mensaje1") + "<br /><br />";
                mensaje += justificacion.getEncabezado() + "<br />";
                mensaje += "Usuario: " + justificacion.getUsuarioRegistra().getNombreCompleto()+ "<br /><br />";
                mensaje += "Traslado # " + justificacion.getIdDocumento().toString() + "<br /><br />";
                mensaje += "Bien " + justificacion.getBien().getIdentificacion().getIdentificacion()+ "<br /><br />";
                mensaje += justificacion.getObservaciones() + "<br /><br />";
                mensaje += Util.getEtiquetas("sigebi.Traslado.Email.Mensaje3");
                break;
            case constAccionSolictCorreccion:
                mensaje = Util.getEtiquetas("sigebi.Traslado.Email.Mensaje1") + "<br /><br />";
                mensaje += justificacion.getEncabezado() + "<br />";
                mensaje += "Usuario: " + justificacion.getUsuarioRegistra().getNombreCompleto() + "<br /><br />";
                mensaje += "Traslado # " + justificacion.getIdDocumento().toString() + "<br /><br />";
                mensaje += justificacion.getObservaciones() + "<br /><br />";
                mensaje += Util.getEtiquetas("sigebi.Traslado.Email.Mensaje3");
                break;
        }

        return mensaje;
    }

    private void iniciarJustificacion() {
        String encabezado = "";

        switch (accion) {
            case constAccionSolictCorreccion:
                encabezado = Util.getEtiquetas("sigebi.Justificacion.Encabezado.SolicitudCambios");
                break;
            case constAccionAnularTraslado:
                encabezado = Util.getEtiquetas("sigebi.Justificacion.Encabezado.Anular");
                break;
            case constAccionRechazarBien:
                encabezado = Util.getEtiquetas("sigebi.Justificacion.Encabezado.RechazoBien");
                break;
        }
        
        justificacion.setUsuarioRegistra(usuarioRegistradoClass);
        
        justificacion.setDocumentoTipo(Constantes.DOCUMENTO_TRASLADO);
        justificacion.setIdDocumento(Long.parseLong(traslado.getId().toString()));
        justificacion.setEncabezado(encabezado);
        if (bienDetalle.getBien() != null) {
            justificacion.setBien(bienDetalle.getBien());
        }
    }

    private void mostrarConfirmacion() {

        switch (accion) {
            case constAccionAceptarBien:
                mensajeConfirmacion = Util.getEtiquetas("sigebi.Traslado.Mns.Confirmacion.Aceptar");
                break;
            default:
                mensajeConfirmacion = Util.getEtiquetas("sigebi.Traslado.Mns.Confirmacion.Aceptar");
                break;
        }

        panelConfirmacionVisible = true;
    }

    public void cerrarConfirmacion() {
        panelConfirmacionVisible = false;
    }

    public void confirmacionAceptar() {

        switch (accion) {
            case constAccionAceptarBien:
                aplicarAprobarBien();
                break;
            default:
                mensajeConfirmacion = Util.getEtiquetas("sigebi.Traslado.Mns.Confirmacion.Aceptar");
                break;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Variables">
    int accion;
    static final int constAccionEnviarARevision = 1;
    static final int constAccionRechazarBien = 2;
    static final int constAccionAnularTraslado = 3;
    static final int constAccionSolictCorreccion = 4;

    static final int constAccionAceptarBien = 5;

    private String mensajeConfirmacion;

    private boolean panelNotificacionVisible = false;
    private boolean panelConfirmacionVisible = false;
    private Justificacion justificacion;
    private List<Justificacion> justificaciones;
    private SolicitudDetalleTraslado bienDetalle;

    public boolean isPanelConfirmacionVisible() {
        return panelConfirmacionVisible;
    }

    public void setPanelConfirmacionVisible(boolean panelConfirmacionVisible) {
        this.panelConfirmacionVisible = panelConfirmacionVisible;
    }

    public String getMensajeConfirmacion() {
        return mensajeConfirmacion;
    }

    public void setMensajeConfirmacion(String mensajeConfirmacion) {
        this.mensajeConfirmacion = mensajeConfirmacion;
    }

    public SolicitudDetalleTraslado getBienDetalle() {
        return bienDetalle;
    }

    public void setBienDetalle(SolicitudDetalleTraslado bienDetalle) {
        this.bienDetalle = bienDetalle;
    }

    public boolean isPanelNotificacionVisible() {
        return panelNotificacionVisible;
    }

    public void setPanelNotificacionVisible(boolean panelNotificacionVisible) {
        this.panelNotificacionVisible = panelNotificacionVisible;
    }

    public Justificacion getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(Justificacion justificacion) {
        this.justificacion = justificacion;
    }

    public List<Justificacion> getJustificaciones() {
        return justificaciones;
    }

    public void setJustificaciones(List<Justificacion> justificaciones) {
        this.justificaciones = justificaciones;
    }

    //</editor-fold>
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Seleccionar Bien">

    /**
     *
     * @param pEvent
     */
    @Override
    public void checkBienSeleccionado(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            //bienesAsociados = new ArrayList<Bien>();
            Bien bien = (Bien) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if(bienesSeleccionados.containsKey(bien.getId())){
                
                //Si el bien ya estaba registrado lo agrego a una lista para ser eliminado
                if((bienesAsociadosTraslado.get(bien.getId()).getId()!= null)&&
                        (bienesAsociadosTraslado.get(bien.getId()).getId() > 0)){
                    trasladoDetalleEliminar.add(bienesAsociadosTraslado.get(bien.getId()));
                }
                // Lo elimino de los datos que estoy mostrando
                bienesSeleccionados.remove(bien.getId());
                bienesAsociadosTraslado.remove(bien.getId());
            }else{
                bienesSeleccionados.put(bien.getId(), bien);
                bienesAsociadosTraslado.put(bien.getId(), getTrasladoDetalleDefault(bien));
            }
            trasladoDetalle = new ArrayList<SolicitudDetalleTraslado>( bienesAsociadosTraslado.values() );
            bienesAsociados = new ArrayList<Bien>( bienesSeleccionados.values() );
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }
    
    
    public void quitarBienSeleccionado(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
        
            //bienesAsociados = new ArrayList<Bien>();
            Bien bien;
            bien = (Bien) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if(bienesSeleccionados.containsKey(bien.getId())){
                
                //Si el bien ya estaba registrado lo agrego a una lista para ser eliminado
                if((bienesAsociadosTraslado.get(bien.getId()).getId()!= null)&&
                        (bienesAsociadosTraslado.get(bien.getId()).getId() > 0))
                    trasladoDetalleEliminar.add(bienesAsociadosTraslado.get(bien.getId()));
                // Lo elimino de los datos que estoy mostrando
                bienesSeleccionados.remove(bien.getId());
                bienesAsociadosTraslado.remove(bien.getId());
            }else{
                bienesSeleccionados.put(bien.getId(), bien);
                bienesAsociadosTraslado.put(bien.getId(), getTrasladoDetalleDefault(bien));
            }
            trasladoDetalle = new ArrayList<SolicitudDetalleTraslado>( bienesAsociadosTraslado.values() );
            bienesAsociados = new ArrayList<Bien>( bienesSeleccionados.values() );
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }
    
    private SolicitudDetalleTraslado getTrasladoDetalleDefault(Bien bien){
        try{
            SolicitudDetalleTraslado det = new SolicitudDetalleTraslado(traslado, bien, estadoGeneralPendiente);
            //det.setEstado(estadoGeneralPendiente);
            return det;
        }catch(Exception e){
            return null;
        }
    }
    

    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Mostrar Reporte">

    @Resource private ReporteDao reporteDao;
    
    public void mostrarReporteTraslados(){
        try{
            String ubicReporte = "C:\\SIGEBI_V6\\web\\reportes\\trasladosReporte.jasper"; 
            String exportReporte = "traslados";

            reporteDao.ejecutarReporte( exportReporte, ubicReporte, null, "PDF");
            
        }catch(Exception e){
            Mensaje.agregarErrorAdvertencia( e, Util.getEtiquetas("sigebi.Traslado.Err.Reporte"));
        }
    }
    

    // </editor-fold>
    
    
}
