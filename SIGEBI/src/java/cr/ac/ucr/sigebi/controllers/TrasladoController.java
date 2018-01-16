/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.entities.BienEntity;
import cr.ac.ucr.sigebi.entities.DocumentoEntity;
import cr.ac.ucr.sigebi.entities.DocumentoRolPersonaEntity;
import cr.ac.ucr.sigebi.entities.EstadoEntity;
import cr.ac.ucr.sigebi.entities.JustificacionEntity;
import cr.ac.ucr.sigebi.domain.Notificacion;
import cr.ac.ucr.sigebi.entities.TrasladoDetalleEntity;
import cr.ac.ucr.sigebi.entities.TrasladoEntity;
import cr.ac.ucr.sigebi.entities.UbicacionEntity;
import cr.ac.ucr.sigebi.entities.UnidadEjecutoraEntity;
import cr.ac.ucr.sigebi.entities.UsuarioEntity;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.DocumentoModel;
import cr.ac.ucr.sigebi.models.DocumentoRolPersonaModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.JustificacionModel;
import cr.ac.ucr.sigebi.models.NotificacionModel;
import cr.ac.ucr.sigebi.models.TipoModel;
import cr.ac.ucr.sigebi.models.TrasladoModel;
import cr.ac.ucr.sigebi.models.UbicacionModel;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
import cr.ac.ucr.sigebi.models.UsuarioModel;
import cr.ac.ucr.sigebi.utils.Constantes;
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
    
    EstadoEntity estadoGeneralPendiente;
    EstadoEntity estadoGeneralActivo;
    EstadoEntity estadoGeneralAnulado;
    
    EstadoEntity estadoGeneralAprobado;
    EstadoEntity estadoGeneralRechazado;
    
    List<TrasladoEntity> traslados;
    TrasladoEntity traslado;
    List<TrasladoDetalleEntity> trasladoDetalle;
    Map<Integer, TrasladoDetalleEntity> bienesAsociadosTraslados;
    
    UnidadEjecutoraEntity unidadOrigen;
    UnidadEjecutoraEntity unidadDestino;
    
    DateFormat formatter;// = new SimpleDateFormat("dd/MM/yyyy");
    String fechaRegistro;
    
    
    boolean permiteEdicion;
    boolean permiteRecibir;
    boolean permiteAnular;
    
    // VARIBLES PARA COMPROBAR USUARIOS CON PERMISOS
    Tipo tipoProcesoTraslado;
    DocumentoEntity documentoEnviar;
    DocumentoEntity documentoRecibir;
    
    // USUARIOS POR DOCUMENTO
    Map<String, UsuarioEntity> usuariosEnviar;
    Map<String, UsuarioEntity> usuariosRecibir;
    
    @Resource
    private TrasladoModel trasladoModel;
    
    @Resource
    private EstadoModel estadoModel;
    
    @Resource 
    private TipoModel tipoModel;
    
    @Resource 
    private DocumentoModel documentoModel;
    
    @Resource 
    private DocumentoRolPersonaModel documentoRolPersonaModel;
    
    @Resource
    private UsuarioModel usuarioModel;
    UsuarioEntity usuarioRegistrado;
    
    @Resource
    private BienModel bienModel;
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="GET's & SET's">

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
    
    public List<TrasladoEntity> getTraslados() {
        return traslados;
    }
    public void setTraslados(List<TrasladoEntity> traslados) {
        this.traslados = traslados;
    }

    public TrasladoEntity getTraslado() {
        return traslado;
    }

    public void setTraslado(TrasladoEntity traslado) {
        this.traslado = traslado;
    }

    public UnidadEjecutoraEntity getUnidadOrigen() {
        return unidadOrigen;
    }

    public void setUnidadOrigen(UnidadEjecutoraEntity unidadOrigen) {
        this.unidadOrigen = unidadOrigen;
    }

    public UnidadEjecutoraEntity getUnidadDestino() {
        return unidadDestino;
    }

    public void setUnidadDestino(UnidadEjecutoraEntity unidadDestino) {
        this.unidadDestino = unidadDestino;
    }

    public List<TrasladoDetalleEntity> getTrasladoDetalle() {
        return trasladoDetalle;
    }

    public void setTrasladoDetalle(List<TrasladoDetalleEntity> trasladoDetalle) {
        this.trasladoDetalle = trasladoDetalle;
    }
 
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Navegación">
    public void listadoTraslados(ActionEvent pEvent) {
        try{
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
        try{
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
        try{
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
        justificacion = new JustificacionEntity();
    }
    
    @PostConstruct
    private void incializaDatos() {
        
        //Asigno Usuario Envía
        usuarioRegistrado = usuarioModel.buscarPorId(codPersonaReg);
        
        //TODO cambiar implementacion de consulta de estados, VER ListarNotificacionesController
        
//        estadoGeneralPendiente= estadoModel.obtenerPorEstado(Constantes.ESTADO_GENERAL_PENDIENTE, Constantes.DOMINI0_ESTADO_GENERAL); 
//        estadoGeneralActivo = estadoModel.obtenerPorEstado(Constantes.ESTADO_GENERAL_ACTIVO, Constantes.DOMINI0_ESTADO_GENERAL); 
//        estadoGeneralAnulado = estadoModel.obtenerPorEstado(Constantes.ESTADO_GENERAL_ANULADO, Constantes.DOMINI0_ESTADO_GENERAL); //ESTADO_GENERAL_ANULADO;
//
//        estadoGeneralAprobado = estadoModel.obtenerPorEstado(Constantes.ESTADO_GENERAL_APROBADO, Constantes.DOMINI0_ESTADO_GENERAL);
//        estadoGeneralRechazado = estadoModel.obtenerPorEstado(Constantes.ESTADO_GENERAL_RECHAZADO, Constantes.DOMINI0_ESTADO_GENERAL);
//        
//        
//        estadosOptions = new ArrayList<SelectItem>();
//        
//        estadosOptions.add(new SelectItem(estadoGeneralPendiente.getIdEstado().toString(), estadoGeneralPendiente.getNombre()));
//        estadosOptions.add(new SelectItem(estadoGeneralActivo.getIdEstado().toString(), estadoGeneralActivo.getNombre()));
//        estadosOptions.add(new SelectItem(estadoGeneralAnulado.getIdEstado().toString(), estadoGeneralAnulado.getNombre()));
//        estadosOptions.add(new SelectItem(estadoGeneralAprobado.getIdEstado().toString(), estadoGeneralAprobado.getNombre()));
//        
        
        
        fltIdUnidad = "";
        fltNombreUnidad = "";
        
        inicializaTraslado();
        
        listadoInicializaDatos();
        
    }
    
    private void inicializaTraslado(){
        try{
            traslado = new TrasladoEntity();
            trasladoDetalle = new ArrayList<TrasladoDetalleEntity>();
            
            permiteEdicion = true;

            traslado.setIdEstado(estadoGeneralPendiente);
            ubicacionVisible= false;
            unidadDestino = new UnidadEjecutoraEntity();

            formatter = new SimpleDateFormat("dd/MM/yyyy");
            //Date actaFecha = formatter.parse(formatter.format(traslado.getFecha()));
            fechaRegistro = formatter.format(traslado.getFecha());
            iniciaUbicaciones();
            buscarUnidades();
            
            
            
            //Asigno Unidad Origen
            traslado.setNumUnidadOrigen(unidadOrigen);
            
            estadosBienes = null;
            consultaBienes = 2;
            inicializaBuscarBienes();
            
            usuariosEnviar = new HashMap<String, UsuarioEntity>();
            usuariosRecibir = new HashMap<String, UsuarioEntity>();

            // Busco Usuarios Enviar y Recibir
            tipoProcesoTraslado = tipoModel.buscarPorDominioNombre(Constantes.DOCUMENTO_TRASLADO, Constantes.DOMINI0_TIPO_PROCESO);

            List<DocumentoEntity> acciones =  documentoModel.buscarPorTipoProceso(tipoProcesoTraslado.getIdTipo());     
            for(DocumentoEntity item : acciones){
                if(item.getNombre().toUpperCase().equals(Constantes.DOCUMENTO_ENVIAR))
                    documentoEnviar = item;
                if(item.getNombre().toUpperCase().equals(Constantes.DOCUMENTO_RECIBIR))
                    documentoRecibir = item;
            }

            List<DocumentoRolPersonaEntity> usrsEnviar = documentoRolPersonaModel.buscarUsuaiosPorDocumento(documentoEnviar.getIdDocumento(), unidadEjecutora);
            for(DocumentoRolPersonaEntity usr : usrsEnviar)
                usuariosEnviar.put(usr.getIdUsuarioSeguridad().getIdUsuario(), usr.getIdUsuarioSeguridad());

            List<DocumentoRolPersonaEntity> usrsRecibir = documentoRolPersonaModel.buscarUsuaiosPorDocumento(documentoRecibir.getIdDocumento(), unidadEjecutora);
            for(DocumentoRolPersonaEntity usr : usrsRecibir)
                usuariosRecibir.put(usr.getIdUsuarioSeguridad().getIdUsuario(), usr.getIdUsuarioSeguridad());


            unidadOrigen = unidadModel.traerPorId(unidadEjecutora);
            
            
        }
        catch(Exception err){
            Mensaje.agregarErrorFatal(err.getMessage());
        }
        
    }
    
    
    public void verDetalle(ActionEvent pEvent) {
        try{
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            inicializaTraslado();
            TrasladoEntity item = (TrasladoEntity) pEvent.getComponent().getAttributes().get("itemSeleccionado");
            if(item.getIdTraslado()> 0 ){
                traslado = item;
                
                //actaFecha = formatter.parse(formatter.format(traslado.getFecha()));
                fechaRegistro = formatter.format(traslado.getFecha());
                
                
                permiteEdicion = permitirEdicion();
                permiteRecibir = permitirRecibir();
                
                if( traslado.getIdEstado().equals(estadoGeneralActivo) )
                    permiteAnular = usuariosEnviar.containsKey(this.codPersonaReg) || permiteRecibir;
                else
                    permiteAnular = false;
                
                trasladoDetalle = trasladoModel.traerBienesTraslado(traslado.getIdTraslado());
                bienesAsociadosTraslados = new HashMap<Integer, TrasladoDetalleEntity> ();
                for(TrasladoDetalleEntity valor : trasladoDetalle) {
                    
                    if(valor.getIdEstado().getIdEstado().equals(estadoGeneralPendiente.getIdEstado())){
                        valor.setMarcado(permiteRecibir);
                    }
                    
                    bienesAsociados.add(valor.getIdBien());
                    bienesSeleccionados.put(valor.getIdBien().getIdBien(), valor.getIdBien());
                    
                    bienesAsociadosTraslados.put(valor.getIdBien().getIdBien(), valor);
                    
                }
                trasladoDetalle = new ArrayList<TrasladoDetalleEntity>( bienesAsociadosTraslados.values() );
                this.listarBienes();
            }
            
            Util.navegar(Constantes.KEY_VISTA_TRASLADO_DETALLE);
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Listado Traslados">
    
    
    String fltIdTraslado = "";
    String fltUnidadOrigen = "";
    String fltUnidadDestino = "";
    String fltFecha = "";
    String fltEstados = "";
    
    
    
    public void listarTraslados(){
        try{
            traslados = trasladoModel.trasladosListado(
                    unidadOrigen
                    , fltIdTraslado
                    , fltUnidadOrigen
                    , fltUnidadDestino
                    , fltFecha
                    , fltEstados
                    , this.getPrimerRegistro()-1
                    , this.getUltimoRegistro()
                    );
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }
    
    private void listadoInicializaDatos(){
        try{
            
            if(fltEstados.equals("" ))
                fltEstados = "-1";
            
            this.listadoCantidadRegistros();
            this.setPrimerRegistro(1);
            this.listarTraslados();
        }catch(Exception err){
            
        }
    }
    
    public void listadoCantidadRegistros(){
        try{
            Long contador;
            
            contador = trasladoModel.contarTrasladosListado(
                    unidadOrigen
                    , fltIdTraslado
                    , fltUnidadOrigen
                    , fltUnidadDestino
                    , fltFecha
                    , fltEstados
                );
            
            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());
            
        }catch(Exception err){
            
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
     * Cambia la cantidad de registros por página
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
    
    public void guardarTraslado(ActionEvent pEvent){
        try{
            
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            if(validarRegistro()){
                trasladoModel.guardar(traslado);
                
                List<TrasladoDetalleEntity> detalle = getBienesAsoc();
                
                //trasladoModel.eliminarBienes(traslado, estadoGeneralPendiente);
                trasladoModel.guardarBienes(detalle);
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Traslado.Mns.ExitoGuardar"));
                listarTraslados();
            }
        }
        catch(Exception err){
            Mensaje.agregarErrorFatal(Util.getEtiquetas("sigebi.Modal.General.Error.Guardar"));
        }
        
    }
    
    private boolean validarRegistro(){
        try{
            String mensaje = "";
            //Validar Unidad Recibe
            if((traslado.getNumUnidadDestino().getIdUnidadEjec() == null) || (traslado.getNumUnidadDestino().getIdUnidadEjec()== 0))
                mensaje = Util.getEtiquetas("sigebi.Traslado.Mns.SelecUnidad");
            
            //Validar Ubicacion
            if( ( traslado.getIdUbicacion().getIdUbicacion() == null ) || (traslado.getIdUbicacion().getIdUbicacion() == 0) )
                mensaje = Util.getEtiquetas("sigebi.Traslado.Mns.SelecUnidad");
            
            
            traslado.setIdPersona(usuarioRegistrado);

            if(!mensaje.equals("")){
                Mensaje.agregarErrorAdvertencia(mensaje);
                return false;
            }
            return true;
        }catch(Exception err){
                Mensaje.agregarErrorAdvertencia(err.getMessage());
            return false;
        }
    }
    
    // jserrano cambio nuevo
    private List<TrasladoDetalleEntity> getBienesAsoc(){
        
        //trasladoDetalle
        List<TrasladoDetalleEntity> detalle = new ArrayList<TrasladoDetalleEntity>();
        for(TrasladoDetalleEntity bn : trasladoDetalle) {     // foreach grade in grades
            
            
            if(bn.getIdTraslado().getIdTraslado() > 0)
                return trasladoDetalle;
            
            TrasladoDetalleEntity valor = new TrasladoDetalleEntity( traslado
                                                           , bn.getIdBien()
                                                           , estadoGeneralPendiente);
            detalle.add(valor);
        }
        
        return detalle;
    }
    
    // Enviar a Unidad de destino
    public void enviarHaciaDestino(){
        //Cambiar estado del traslado
        try{
            if(validarRegistro()){
                traslado.setIdEstado(estadoGeneralActivo);
                trasladoModel.guardar(traslado);
                
                accion = constAccionEnviarARevision;
                enviarNotificacion();
                listarTraslados();
                Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);
            }
            
        }catch(Exception err){
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }
    
    // Solicitar Correcciones
    public void solicitarCorreccion(){
        try{
            accion = constAccionSolictCorreccion;
            mostrarJustificacionGeneral();
            
        }catch(Exception err){
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }
    
    private void aplicarSolicitudCorreccion(){
        traslado.setIdEstado(estadoGeneralPendiente);
        trasladoModel.guardar(traslado);

        listarTraslados();
        Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);
        
    }
    
    private void aplicarRevision(){
        if(validarRegistro()){
            traslado.setIdEstado(estadoGeneralPendiente);
            trasladoModel.guardar(traslado);

            listadoInicializaDatos();
            Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);
        }
    }
    
    public void devolverParaRevision(){
        accion = constAccionSolictCorreccion;
        mostrarJustificacionGeneral();
    }
    
    public void anularTraslado(){
        //Cambiar estado del traslado
        try{
            accion = constAccionAnularTraslado;
            mostrarJustificacionGeneral();
        }catch(Exception err){
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }
    
    private void aplicarAnularTraslado(){
            
        traslado.setIdEstado(estadoGeneralAnulado);
        trasladoModel.guardar(traslado);

        listadoInicializaDatos();
        Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);
            
    }
    
    private boolean permitirEdicion(){
        //Verifica Unidad de Origen
        if( ! traslado.getNumUnidadOrigen().getIdUnidadEjec().equals(unidadEjecutora) )
            return false;
        //Verifica permisos en DocumentoRolPersona
        if( !usuariosEnviar.containsKey(this.codPersonaReg) )
            return false;
        
        // Verifica que esté en estado PENDIENTE
        return traslado.getIdEstado().getIdEstado().equals(estadoGeneralPendiente.getIdEstado());
    }
    
    private boolean permitirRecibir(){
        //Verifica Unidad de Destino
        if( ! traslado.getNumUnidadDestino().getIdUnidadEjec().equals(unidadEjecutora) )
            return false;
        //Verifica permisos en DocumentoRolPersona
        if( ! usuariosRecibir.containsKey(this.codPersonaReg) )
            return false;
        
        // Verifica que esté en estado ACTIVO
        return traslado.getIdEstado().getIdEstado().equals(estadoGeneralActivo.getIdEstado());
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
            bienDetalle = new TrasladoDetalleEntity();
            //Se obtiene el bien que se va a aprobar 
            bienDetalle = (TrasladoDetalleEntity) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            
            mostrarConfirmacion();
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
        
    }
    
    private void aplicarAprobarBien(){
        try{
        //Primero se debe aplicar el cambio
        bienDetalle.setIdEstado(estadoGeneralAprobado);
        trasladoModel.guardarBien(bienDetalle);
        bienDetalle.setMarcado(false);
        
        //Busacamos que esten todos aprobados para cambiar estado del traslado
        
        
        
        //Lo cambiamos de unidad
        BienEntity bien = bienModel.traerPorId(bienDetalle.getIdBien().getIdBien());
        bien.setNumUnidadEjec(traslado.getNumUnidadDestino().getIdUnidadEjec());
        bienModel.actualizarBien(bien);
        
        
        //PENDIENTE SINCRONIZAR CON SIAF
        
        
        if(estaAprobada()){
            aprobarTraslado();
        }
        Mensaje.agregarErrorAdvertencia("PENDIENTE SINCRONIZAR CON SIAF");        //Lo sincronizamos
        
        cerrarConfirmacion();
        
        }catch(Exception err){
            Mensaje.agregarErrorFatal(Util.getEtiquetas("sigebi.Traslado.Mns.ErrorBien"));
        }
        
    }
    
    private boolean estaAprobada(){
        for(TrasladoDetalleEntity item : trasladoDetalle){
            if( ! item.getIdEstado().getIdEstado().equals(estadoGeneralAprobado.getIdEstado())){
                return false;
            }
        }
        return true;
    }
    
    private void aprobarTraslado(){
        try{
        traslado.setIdEstado(estadoGeneralAprobado);
        trasladoModel.guardar(traslado);

        listadoInicializaDatos();
        Util.navegar(Constantes.KEY_VISTA_TRASLADOS_LISTAR);
        }catch(Exception err){
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
            bienDetalle = new TrasladoDetalleEntity();
            //Se obtiene el bien que se va a aprobar 
            bienDetalle = (TrasladoDetalleEntity) pEvent.getComponent().getAttributes().get("bienSeleccionado");

            justificacion = new JustificacionEntity();

            iniciarJustificacion();

            panelNotificacionVisible = true;
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
        
    }
    
    private void aplicarRechazoBien(){
        bienDetalle.setIdEstado(estadoGeneralRechazado);
        trasladoModel.guardarBien(bienDetalle);
        //Mensaje.agregarErrorAdvertencia("Estado: "+bienDetalle.getIdEstado().getNombre() + " Bien :" + bienDetalle.getIdBien().getDescripcion() );
        
    }
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Buscar Unidades">
    
    @Resource
    UnidadEjecutoraModel unidadModel;
    
    public void mostrarUnidades(ActionEvent pEvent) {
        try{
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            unidadesVisible = true;
        }catch(Exception err){
            
        }
    }
    
    public void cerrarUnidades(ActionEvent pEvent) {
        try{
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            unidadesVisible = false;
        }catch(Exception err){
            
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
    private void buscarUnidades(){
        try{
            unidadesEjecutoras = new ArrayList<UnidadEjecutoraEntity>();
            unidadesEjecutoras = unidadModel.listarUnidades(fltIdUnidad.toUpperCase(), fltNombreUnidad.toUpperCase());
            //Mensaje.agregarInfo(unidadesEjecutoras.size()+ " unidades encontradas ");
        }
        catch(Exception err){
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }
    
    
    public void selecUnidad(ActionEvent pEvent){
        try{
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            UnidadEjecutoraEntity unidad = (UnidadEjecutoraEntity) pEvent.getComponent().getAttributes().get("unidadSeleccionada");
            
            traslado.setNumUnidadDestino(unidad);// = unidad;
            
            unidadesVisible = false;
        }catch(Exception err){
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }
    
    
    
    
    List<UnidadEjecutoraEntity> unidadesEjecutoras;

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

    
    
    public List<UnidadEjecutoraEntity> getUnidadesEjecutoras() {
        return unidadesEjecutoras;
    }

    public void setUnidadesEjecutoras(List<UnidadEjecutoraEntity> unidadesEjecutoras) {
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
    
    boolean ubicacionVisible;
    
    @Resource
    private UbicacionModel ubicModel;
    
    private void iniciaUbicaciones(){
        try{
            selectDefault = Constantes.SELECT_DEFAULT;

            ubicacionOptions = new ArrayList<SelectItem>();
            List<UbicacionEntity> ubicaciones;
            ubicaciones = ubicModel.traerTodo();
            for (UbicacionEntity item : ubicaciones) {
                ubicacionOptions.add(new SelectItem(item.getIdUbicacion() + "#" + item.getDetalle().replace("#", "-"), item.getDetalle().replace("#", "-")));
            }
        }catch(Exception err){
            Mensaje.agregarErrorFatal(err.getMessage());
        }
    }
    
    public void mostrarUbicaciones(ActionEvent pEvent) {
        try{
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            ubicacionVisible = true;
        }catch(Exception err){
            Mensaje.agregarErrorFatal(err.getMessage());
        }
        
    }
    
    //
    public void cerrarUbicacion(ActionEvent pEvent) {
        try{
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            ubicacionVisible = false;
        }catch(Exception err){
            Mensaje.agregarErrorFatal(err.getMessage());
        }
        
    }
    
    
    public void ubicacionSelectCambio(ValueChangeEvent event) {
        try{
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Obtyengo el valor seleccionado
            String valor = event.getNewValue().toString();
            String[] valores = valor.split("#");
            if(valores.length > 1){
                ubicacionId = valores[0];
                ubicacionNombre = valores[1];
                UbicacionEntity ubicacion = ubicModel.obtenerValor(Integer.parseInt(ubicacionId));
                traslado.setIdUbicacion(ubicacion);
            }
            else{
                ubicacionId = "";
                ubicacionNombre = "";
            }
            //Object valor2 = event.getSource();
            //idSelectUbicacion = valor;
        }
        catch(Exception err){
            Mensaje.agregarErrorFatal(err.getMessage());
        }
        
    }
    
    
    
    String selectUbicacion;
    String selectDefault;
    // comboBox subCategorias
    List<SelectItem> ubicacionOptions;
    String ubicacionId;
    String ubicacionNombre;

    public String getSelectUbicacion() {
        return selectUbicacion;
    }

    public void setSelectUbicacion(String selectUbicacion) {
        this.selectUbicacion = selectUbicacion;
    }

    public String getSelectDefault() {
        return selectDefault;
    }

    public void setSelectDefault(String selectDefault) {
        this.selectDefault = selectDefault;
    }

    public List<SelectItem> getUbicacionOptions() {
        return ubicacionOptions;
    }

    public void setUbicacionOptions(List<SelectItem> ubicacionOptions) {
        this.ubicacionOptions = ubicacionOptions;
    }

    public String getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(String ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public String getUbicacionNombre() {
        return ubicacionNombre;
    }

    public void setUbicacionNombre(String ubicacionNombre) {
        this.ubicacionNombre = ubicacionNombre;
    }
    
    
    
    public boolean isUbicacionVisible() {
        return ubicacionVisible;
    }

    public void setUbicacionVisible(boolean ubicacionVisible) {
        this.ubicacionVisible = ubicacionVisible;
    }
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Metodos Listado Bienes">
  

    public void abrirListaBienes() {
         try{
//            consultaBienes = 2;
//            mostrarBienes();
            mostrarDialogBienes = true;
        }catch(Exception err){
        }
    }

    public void cerrarListaBienes(){
         try{
            mostrarDialogBienes = false;
        }catch(Exception err){
        }
    }
    
    
    public void checkBienSeleccionadoTraslado(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            //bienesAsociados_1 = new ArrayList<ViewBienEntity>();
            ViewBienEntity bien = (ViewBienEntity) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if(bienesSeleccionados.containsKey(bien.getIdBien())){
                bienesSeleccionados.remove(bien.getIdBien());
                bienesAsociadosTraslados.remove(bien.getIdBien());
            }else{
                bienesSeleccionados.put(bien.getIdBien(), bien);
                bienesAsociadosTraslados.put(bien.getIdBien(), new TrasladoDetalleEntity(traslado,bien, estadoGeneralPendiente));
            }
            //bienesAsociados_1 = new Array<>(bienesSeleccionados_1.values());
            bienesAsociados = new ArrayList<ViewBienEntity>( bienesSeleccionados.values() );
            trasladoDetalle = new ArrayList<TrasladoDetalleEntity>( bienesAsociadosTraslados.values() );
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }

    
    
    
    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Justificaciones y Notificaciones">
    
    
    
    @Resource
    JustificacionModel justificacionModel;
    
    @Resource
    NotificacionModel notificacionModel;
    
    public void agergarJustificacion(){
        try{
            if(justificacion.getObservaciones().length() > 3){
                justificacionModel.guardar(justificacion);
                panelNotificacionVisible = false;
                
                switch (accion){
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
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Traslado.Mns.ExitoModificar"));
            }
            else{
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Justificacion.Err.Observaciones"));
            }
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Justificacion.Err.Guardar"));
        }
    }
    
    public void cancelarJustificacion(){
        
        justificacion = new JustificacionEntity();
        bienDetalle = new TrasladoDetalleEntity();
        panelNotificacionVisible = false;
    }
    
    public void mostrarJustificacionGeneral(){
        justificacion = new JustificacionEntity();
        bienDetalle = new TrasladoDetalleEntity();
        
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
            bienDetalle = new TrasladoDetalleEntity();
            //Se obtiene el bien que se va a aprobar 
            bienDetalle = (TrasladoDetalleEntity) pEvent.getComponent().getAttributes().get("bienSeleccionado");

            justificacion = new JustificacionEntity();

            iniciarJustificacion();

            panelNotificacionVisible = true;
            
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
        
    }
    
    private void enviarNotificacion(){
        try{
            String mensaje = getMensajeEmail();
            
            Notificacion correo = new Notificacion();
            correo.setAsunto(Util.getEtiquetas("sigebi.Traslado.Email.Asunto"));
            correo.setDestinatario("jorse_9@yahoo.com");
            correo.setFecha(new Date());
            correo.setMensaje(mensaje);
            
            notificacionModel.enviarCorreo(correo);
            
            List<DocumentoRolPersonaEntity> usrsNotificar = new ArrayList<DocumentoRolPersonaEntity>();
            
            
            switch (accion){
                case  constAccionEnviarARevision:
                    usrsNotificar =  documentoRolPersonaModel.buscarUsuaiosPorDocumento(documentoRecibir.getIdDocumento(), traslado.getNumUnidadDestino().getIdUnidadEjec());
                    break;
                default:
                    usrsNotificar =  documentoRolPersonaModel.buscarUsuaiosPorDocumento(documentoEnviar.getIdDocumento(), traslado.getNumUnidadOrigen().getIdUnidadEjec());
                    break;
            }

            for(DocumentoRolPersonaEntity usr : usrsNotificar){
                correo.setDestinatario(usr.getIdUsuarioSeguridad().getCorreo());
                notificacionModel.enviarCorreo(correo);
            }
            
            
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Traslado.Err.Email"));
        }
    }
    
    private String getMensajeEmail(){
        
            String mensaje = "";
            
            switch (accion){
                case constAccionEnviarARevision:
                    mensaje = Util.getEtiquetas("sigebi.Traslado.Email.Mensaje1") + "<br /><br />";
                    mensaje += "Traslado # "+justificacion.getIdDocumento().toString()+ "<br /><br />";
                    mensaje += Util.getEtiquetas("sigebi.Traslado.Email.Mensaje2").replaceAll("{0}", traslado.getNumUnidadDestino().getDscUnidadEjecutora()) + "<br /><br />";
                    mensaje += Util.getEtiquetas("sigebi.Traslado.Email.Mensaje3");
                    break;
                case constAccionAnularTraslado:
                    mensaje = Util.getEtiquetas("sigebi.Traslado.Email.Mensaje1") + "<br /><br />";
                    mensaje += justificacion.getEncabezado() + "<br />";
                    mensaje += "Usuario: "+justificacion.getUsuarioRegistra().getNombre_completo()+ "<br /><br />";
                    mensaje += "Traslado # "+justificacion.getIdDocumento().toString()+ "<br /><br />";
                    mensaje += justificacion.getObservaciones()+ "<br /><br />";
                    mensaje += Util.getEtiquetas("sigebi.Traslado.Email.Mensaje3");
                    break;
                case constAccionRechazarBien:
                    mensaje = Util.getEtiquetas("sigebi.Traslado.Email.Mensaje1") + "<br /><br />";
                    mensaje += justificacion.getEncabezado() + "<br />";
                    mensaje += "Usuario: "+ justificacion.getUsuarioRegistra().getNombre_completo()+ "<br /><br />";
                    mensaje += "Traslado # "+ justificacion.getIdDocumento().toString()+ "<br /><br />";
                    mensaje += "Bien "+ justificacion.getIdBien().getIdPlaca().getPlaca()+ "<br /><br />";
                    mensaje += justificacion.getObservaciones()+ "<br /><br />";
                    mensaje += Util.getEtiquetas("sigebi.Traslado.Email.Mensaje3");
                    break;
                case constAccionSolictCorreccion:
                    mensaje = Util.getEtiquetas("sigebi.Traslado.Email.Mensaje1") + "<br /><br />";
                    mensaje += justificacion.getEncabezado() + "<br />";
                    mensaje += "Usuario: "+justificacion.getUsuarioRegistra().getNombre_completo()+ "<br /><br />";
                    mensaje += "Traslado # "+justificacion.getIdDocumento().toString()+ "<br /><br />";
                    mensaje += justificacion.getObservaciones()+ "<br /><br />";
                    mensaje += Util.getEtiquetas("sigebi.Traslado.Email.Mensaje3");
                    break;
            }
            
            return mensaje;
    }
    
    private void iniciarJustificacion(){
        String encabezado = "";
        
        switch (accion){
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
        
        justificacion.setUsuarioRegistra(usuarioRegistrado);
        justificacion.setDocumentoTipo(Constantes.DOCUMENTO_TRASLADO);
        justificacion.setIdDocumento(Long.parseLong(traslado.getIdTraslado().toString()));
        justificacion.setEncabezado(encabezado);
        if(bienDetalle.getIdBien() != null)
            justificacion.setIdBien(bienDetalle.getIdBien());
    }
    
    private void mostrarConfirmacion(){
        
            switch (accion){
                case  constAccionAceptarBien:
                    mensajeConfirmacion = Util.getEtiquetas("sigebi.Traslado.Mns..Confirmacion.Aceptar");
                    break;
                default:
                    mensajeConfirmacion =  "Mensaje sin definir.";//Util.getEtiquetas("sigebi.Traslado.Mns..Confirmacion.Aceptar");
                    break;
            }

        panelConfirmacionVisible = true;
    }
    
    public void cerrarConfirmacion(){
        panelConfirmacionVisible = false;
    }
    public void confirmacionAceptar(){
        
            switch (accion){
                case  constAccionAceptarBien:
                    aplicarAprobarBien();
                    break;
                default:
                    mensajeConfirmacion =  "Mensaje sin definir.";//Util.getEtiquetas("sigebi.Traslado.Mns..Confirmacion.Aceptar");
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
    private JustificacionEntity justificacion;
    private List<JustificacionEntity> justificaciones;
    private TrasladoDetalleEntity bienDetalle;

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
    
    public TrasladoDetalleEntity getBienDetalle() {
        return bienDetalle;
    }

    public void setBienDetalle(TrasladoDetalleEntity bienDetalle) {
        this.bienDetalle = bienDetalle;
    }
    
    public boolean isPanelNotificacionVisible() {
        return panelNotificacionVisible;
    }

    public void setPanelNotificacionVisible(boolean panelNotificacionVisible) {
        this.panelNotificacionVisible = panelNotificacionVisible;
    }
    
    public JustificacionEntity getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(JustificacionEntity justificacion) {
        this.justificacion = justificacion;
    }

    public List<JustificacionEntity> getJustificaciones() {
        return justificaciones;
    }

    public void setJustificaciones(List<JustificacionEntity> justificaciones) {
        this.justificaciones = justificaciones;
    }

    
    
    
    //</editor-fold>
    
    //</editor-fold>



}