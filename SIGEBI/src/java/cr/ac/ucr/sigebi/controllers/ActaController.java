/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Rol;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.entities.ActaDetalleEntity;
import cr.ac.ucr.sigebi.models.ActaModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.entities.ActaEntity;
import cr.ac.ucr.sigebi.entities.DocumentoEntity;
import cr.ac.ucr.sigebi.models.TipoModel;
import cr.ac.ucr.sigebi.entities.RolEntity;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import cr.ac.ucr.sigebi.entities.ViewDocumAprobEntity;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.DocumentoRolEstadoModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolModel;
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
@Controller(value = "actaController")
@Scope("session")
public class ActaController extends ListadoBienesGeneralController {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    ActaEntity acta;

    //TODO el manejo de estados debe modificarse para que si agrega un nuevo estado no haya que modificar el codigo
    Estado estadoGeneralActivo;
    Estado estadoGeneralPendiente;
    Estado estadoGeneralAprobado;
    Estado estadoGeneralRechazado;
    
    List<ActaEntity> actasRegistradas;

    List<SelectItem> tiposActaOptions;
    String tipoActaSeleccionada;
    String selectValorDefecto;

    String actaIdEstado;
    String actaNombreEstado;

    DateFormat formatter;// = new SimpleDateFormat("dd/MM/yyyy");
    Date actaFecha;

    String fechaRegistro;

    boolean registrado;
    boolean esDonacion;
    boolean permitirEdicion;

    Tipo tipoSeleccionado;


    Map<Long, Rol> rolesDelUsuarioActualDesecho;
    Map<Long, Rol> rolesDelUsuarioActualDonacion;
    
    @Resource
    private TipoModel tipoModel;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private ActaModel actaModel;

    @Resource
    private AutorizacionRolPersonaModel autorizacionRolPersonaModel;

    @Resource
    private BienModel bienModel;

    
    @Resource
    private UsuarioModel usuarioModel;
    Usuario usuarioRegistrado;
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    public ActaController() {
    }

    @PostConstruct
    private void incializaDatos() {
        try{
            estadoGeneralActivo = estadoModel.buscarPorDominioEstado( Constantes.DOMINI0_ESTADO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO);
            estadoGeneralPendiente = estadoModel.buscarPorDominioEstado( Constantes.DOMINI0_ESTADO_GENERAL, Constantes.ESTADO_GENERAL_PENDIENTE);
            estadoGeneralAprobado = estadoModel.buscarPorDominioEstado( Constantes.DOMINI0_ESTADO_GENERAL, Constantes.ESTADO_GENERAL_APROBADO);
            estadoGeneralRechazado = estadoModel.buscarPorDominioEstado( Constantes.DOMINI0_ESTADO_GENERAL, Constantes.ESTADO_GENERAL_RECHAZADO);
    //      
            usuarioRegistrado = usuarioModel.buscarPorId(codPersonaReg);
            rolesDelUsuarioActualDesecho = new HashMap<Long, Rol>();
            rolesDelUsuarioActualDonacion = new HashMap<Long, Rol>();

            List<AutorizacionRolPersona> rolesAprobacionDonacion =  autorizacionRolPersonaModel.buscarRolesPorAutorizacionUsuario(Constantes.ID_DOCUMENTO_ACTA_DONACION, usuarioRegistrado.getId(), unidadEjecutoraId);     
            List<AutorizacionRolPersona> rolesAprobacionDesecho =  autorizacionRolPersonaModel.buscarRolesPorAutorizacionUsuario(Constantes.ID_DOCUMENTO_ACTA_DESECHO, usuarioRegistrado.getId(), unidadEjecutoraId);     


            //Estos son los roles registrados para este USUARIO en cada DOCUMENTO
    //        for(AutorizacionRolPersona item : rolesAprobacionDesecho)
    //            rolesDelUsuarioActualDesecho.put(item.getIdRol().getId(), item.getIdRol());
    //        for(AutorizacionRolPersona item : rolesAprobacionDonacion)
    //            rolesDelUsuarioActualDonacion.put(item.getIdRol().getId(), item.getIdRol());
    //                
            listarActas();
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.ActaListar.MSGError.Listado"));
        }
    }

    private void iniciaDatos() {
        try {
            consultaBienes = 2;

            tiposActaOptions = new ArrayList<SelectItem>();
            cargarTipo();
            acta = new ActaEntity();
            acta.setUnidadEjecutora(unidadEjecutoraId);

            acta.setIdEstado(estadoGeneralPendiente);

            registrado = false;
            esDonacion = false;

            selectValorDefecto = Constantes.SELECT_DEFAULT;
            tipoActaSeleccionada = selectValorDefecto;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            actaFecha = formatter.parse(formatter.format(acta.getFecha()));
            fechaRegistro = formatter.format(acta.getFecha());

            actaNombreEstado = estadoGeneralPendiente.getNombre();

            bienesAsociados = new ArrayList<ViewBienEntity>();

            //Indica el n�mero de consulta que voy a utilizar en el popUp de Bienes
            estadosBienes = null;
            consultaBienes = 2;
            inicializaBuscarBienes();
            
            documentoRoles = new ArrayList<ViewDocumAprobEntity>();
            permitirEdicion = permitirEditar();

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    void cargarTipo() {
        try {
            //Aqui traemos los tipos de bienes para los Select
            List<Tipo> tipoEntity = tipoModel.listarPorDominio(Constantes.DOMINI0_ESTADO_ACTA);
            for (Tipo item : tipoEntity) {
                if (item.getNombre().toUpperCase().contains("DONAC")) {
                    valorDonacion = item.getIdTipo().toString();
                }
                tiposActaOptions.add(new SelectItem("" + item.getIdTipo(), item.getNombre()));
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="GETs & SETs">

    public boolean isPermitirEdicion() {
        return permitirEdicion;
    }

    public void setPermitirEdicion(boolean permitirEdicion) {
        this.permitirEdicion = permitirEdicion;
    }
    
    public List<ActaEntity> getActasRegistradas() {
        return actasRegistradas;
    }

    public void setActasRegistradas(List<ActaEntity> actasRegistradas) {
        this.actasRegistradas = actasRegistradas;
    }

    public String getSelectValorDefecto() {
        return selectValorDefecto;
    }

    public void setSelectValorDefecto(String selectValorDefecto) {
        this.selectValorDefecto = selectValorDefecto;
    }

    public boolean isEsDonacion() {
        return esDonacion;
    }

    public void setEsDonacion(boolean esDonacion) {
        this.esDonacion = esDonacion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isRegistrado() {
        return registrado;
    }

    public void setRegistrado(boolean registrado) {
        this.registrado = registrado;
    }

    public ActaEntity getActa() {
        return acta;
    }

    public void setActa(ActaEntity acta) {
        this.acta = acta;
    }

    public List<SelectItem> getTiposActaOptions() {
        return tiposActaOptions;
    }

    public void setTiposActaOptions(List<SelectItem> tiposActaOptions) {
        this.tiposActaOptions = tiposActaOptions;
    }

    public String getTipoActaSeleccionada() {
        return tipoActaSeleccionada;
    }

    public void setTipoActaSeleccionada(String tipoActaSeleccionada) {
        this.tipoActaSeleccionada = tipoActaSeleccionada;
    }

    public String getActaIdEstado() {
        return actaIdEstado;
    }

    public void setActaIdEstado(String actaIdEstado) {
        this.actaIdEstado = actaIdEstado;
    }

    public String getActaNombreEstado() {
        return actaNombreEstado;
    }

    public void setActaNombreEstado(String actaNombreEstado) {
        this.actaNombreEstado = actaNombreEstado;
    }

    public Date getActaFecha() {
        return actaFecha;
    }

    public void setActaFecha(Date actaFecha) {
        this.actaFecha = actaFecha;
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Navagar">
    
    
    public void verDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            iniciaDatos();
            ActaEntity item = (ActaEntity) pEvent.getComponent().getAttributes().get("itemSeleccionado");
            if (item.getIdActa() > 0) {
                acta = item;
                tipoActaSeleccionada = acta.getIdTipo().getIdTipo().toString();
                esDonacion = tipoActaSeleccionada.equals(Constantes.ACTA_ID_TIPO_DONACION);
                tipoSeleccionado = acta.getIdTipo();
                
                actaFecha = formatter.parse(formatter.format(acta.getFecha()));
                fechaRegistro = formatter.format(acta.getFecha());

                bienesAsociados = actaModel.traerBienesActa(acta.getIdActa());

                for (ViewBienEntity valor : bienesAsociados) {
                    bienesSeleccionados.put(valor.getIdBien(), valor);
                }

                listarRolesAprobacion();
                permitirEdicion = permitirEditar();
            }

            Util.navegar(Constantes.KEY_VISTA_ACTA);//"actaDetalle"
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

            iniciaDatos();

            Util.navegar(Constantes.KEY_VISTA_ACTA);

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

            listarActas();
            //inicializaDatos();
            Util.navegar(Constantes.KEY_VISTA_LISTAR_ACTAS);

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    
    //</editor-fold>
    
    
     
    
    //<editor-fold defaultstate="collapsed" desc="Listado Actas">
    
    
    String fltIdActa = "";
    String fltAutorizacion = "";
    String fltFecha = "";
    String fltEstados = "";
    
    
    
    public void listarActas(){
        try{
//            traslados = trasladoModel.trasladosListado(
//                    unidadOrigen
//                    , fltIdTraslado
//                    , fltUnidadOrigen
//                    , fltUnidadDestino
//                    , fltFecha
//                    , fltEstados
//                    , this.getPrimerRegistro()-1
//                    , this.getUltimoRegistro()
//                    );

                actasRegistradas = actaModel.listarActas(unidadEjecutoraId
                        , fltIdActa
                        , fltAutorizacion
                        , fltEstados
                        , fltFecha
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
            this.listarActas();
        }catch(Exception err){
            
        }
    }
    
    public void listadoCantidadRegistros(){
        try{
            Long contador;
            
            contador = actaModel.consultaCantidadRegistros(unidadEjecutoraId
                        , fltIdActa
                        , fltAutorizacion
                        , fltEstados
                        , fltFecha
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
        this.listarActas();
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
        this.listarActas();
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
        this.listarActas();
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
        this.listarActas();
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
        this.listarActas();
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
        this.listarActas();

    }
    

    
    
    
    public void trasladosCambioFiltro() {
        //this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));  
        this.setPrimerRegistro(1);
        listadoInicializaDatos();
        
        //Mensaje.agregarErrorAdvertencia("Cambio en filtro no parametro.");
        
    }
    
    public void actasCambioFiltro(ValueChangeEvent event) {
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

    
    public String getFltEstados() {
        return fltEstados;
    }

    public void setFltEstados(String fltEstado) {
        this.fltEstados = fltEstado;
    }

    public String getFltIdActa() {
        return fltIdActa;
    }

    public void setFltIdActa(String fltIdActa) {
        this.fltIdActa = fltIdActa;
    }

    public String getFltAutorizacion() {
        return fltAutorizacion;
    }

    public void setFltAutorizacion(String fltAutorizacion) {
        this.fltAutorizacion = fltAutorizacion;
    }

    public String getFltFecha() {
        return fltFecha;
    }

    public void setFltFecha(String fltFecha) {
        this.fltFecha = fltFecha;
    }
    
    
    
    
    
    //</editor-fold>
    
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Registro Acta">
    
    public void guardar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            guardarActa();
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Acta.MsnError.ActaError"));
            //Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    public void selectTipoActaCambio(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        // Obtyengo el valor seleccionado
        String valor = event.getNewValue().toString();
        tipoSeleccionado = null;
        if (!valor.equals(Constantes.SELECT_DEFAULT)) {
            tipoSeleccionado = tipoModel.buscarPorId(Integer.parseInt(valor));
        }

        esDonacion = valor.equals(Constantes.ACTA_ID_TIPO_DONACION);
    }

    public void activarActa(){
        try{
//            acta.setIdEstado(estadoGeneralActivo);
            guardarActa();
        
            listarActas();
            //inicializaDatos();
            Util.navegar(Constantes.KEY_VISTA_LISTAR_ACTAS);
        }catch(Exception err){
            Mensaje.agregarErrorFatal(Util.getEtiquetas("sigebi.Acta.MsnError.ActaError"));
        }
    }
    
    private void guardarActa(){
        if (registroEsValido()) {
            actaModel.guardar(acta);
            //GUARDAR LOS BIENES ASOCIADOS
            List<ActaDetalleEntity> bienesAsoc = getBienesAsoc();
            actaModel.guardarBienes(bienesAsoc);
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Acta.MsnError.ActaGuardada"));
        }
    }
    
    private boolean registroEsValido() {
        String mensaje = "";
        if (tipoActaSeleccionada.equals(Constantes.SELECT_DEFAULT)) {
            mensaje = Util.getEtiquetas("sigebi.Acta.MsnError.TipoActa");
            //return false;
        }

        if (acta.getAutorizacion().length() < 4) {
            mensaje = Util.getEtiquetas("sigebi.Acta.MsnError.Autorizacion");
            //return false;

        }
        if (tipoSeleccionado == null) {
            mensaje = Util.getEtiquetas("sigebi.Acta.MsnError.TipoActa");
        } else {
            acta.setIdTipo(tipoSeleccionado);
        }
        // Validamos si es donaci�n
        if (esDonacion) {
            if (acta.getCedula().length() < 5) {
                mensaje = Util.getEtiquetas("sigebi.Acta.MsnError.ActaCedula");
                //return false;
            }
            if (acta.getRazonSocial().length() < 5) {
                mensaje = Util.getEtiquetas("sigebi.Acta.MsnError.ActaRazonSocial");
                //return false;
            }
        }

        if (bienesAsociados.isEmpty()) {
            mensaje = Util.getEtiquetas("sigebi.Acta.MsnError.BienesSeleccionados");
            //return false;
        }

        if (!mensaje.equals("")) {
            Mensaje.agregarErrorAdvertencia(mensaje);
            return false;
        } else {
            if (acta.getIdEstado() == null) {
//                acta.setIdEstado(estadoGeneralPendiente);
            }
        }

        bienesAsociados = new ArrayList<ViewBienEntity>(bienesSeleccionados.values());

        return true;

    }

    private List<ActaDetalleEntity> getBienesAsoc() {
        List<ActaDetalleEntity> detalle = new ArrayList<ActaDetalleEntity>();
        for (ViewBienEntity bn : bienesAsociados) {     // foreach grade in grades
            //Bien bien = bienModel.traerPorId(bn.getIdBien());
            ActaDetalleEntity valor = new ActaDetalleEntity(acta.getIdActa(),
                     bn.getIdBien(),
                     estadoGeneralPendiente.getId());
            detalle.add(valor);
        }

        return detalle;
    }
    
    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Listado Roles Aprobacion por Documento">
    String valorDonacion;

    @Resource
    private AutorizacionRolModel documRolModel;
    
    //Los Roles de la tabla
    List<ViewDocumAprobEntity> documentoRoles;
    
    Map<Integer, ViewDocumAprobEntity> rolesIncluidos;

    public void listarRolesAprobacion() {
        try {
            List<ViewDocumAprobEntity> roles = null;
             rolesIncluidos = new HashMap<Integer, ViewDocumAprobEntity>();
             documentoRoles = new ArrayList<ViewDocumAprobEntity>();
            
            //COnsulto los roles por tipo
            if (tipoActaSeleccionada.equals(valorDonacion))
                roles = documRolModel.buscarRolDocumId(Constantes.ID_DOCUMENTO_ACTA_DONACION.intValue(), acta.getIdActa());
            else 
                roles = documRolModel.buscarRolDocumId(Constantes.ID_DOCUMENTO_ACTA_DESECHO.intValue(), acta.getIdActa());
            
                
            //Reviso los roles por documento
            for (ViewDocumAprobEntity valor : roles) {
                //Los agrego sin que se repitan los roles
                if( !rolesIncluidos.containsKey(valor.getRolId())  ){
                    rolesIncluidos.put(valor.getRolId(), valor);
                    // Los marco como marcados.
                    if(( valor.getUsuario() == null) || (valor.getUsuario().equals("")) ){
                        if(acta.getIdEstado().equals(estadoGeneralActivo.getId()))
                            valor.setMarcado(rolesDelUsuarioActualDonacion.containsKey(Long.parseLong(valor.getRolId().toString())));
                    }
                        
                        
                    documentoRoles.add(valor);
                }

            }
            

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Acta.MsnError.ActaError"));
            //Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    public boolean permitirEditar() {
        return acta.getIdEstado().equals(estadoGeneralPendiente.getId());
    }

    @Resource
    DocumentoRolEstadoModel docRolEstModel;
    /**
     * Aprueba el informe
     *
     * @param pEvent
     */
    public void aprobar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            // Inicializo el ViewDocumAprobEntity para registrarlo en base de datos
            ViewDocumAprobEntity docum = (ViewDocumAprobEntity) pEvent.getComponent().getAttributes().get("documentoAprobar");
            DocumentoEntity documentoEntity = new DocumentoEntity();
            documentoEntity.setIdDocumento( Long.parseLong(""+docum.getIdDocumento() ) );
            RolEntity rolEntity = new RolEntity();
            rolEntity.setIdRol( Long.parseLong(""+docum.getRolId() ) );
            //Inicializo el registro DocumentoRolEstadoEntity
//            DocumentoRolEstadoEntity registro = new DocumentoRolEstadoEntity( Long.parseLong(""+acta.getIdActa())
//                                                                            , documentoEntity
//                                                                            , rolEntity
//                                                                            , estadoGeneralAprobado);
            //Completo los datos
//            registro.setFecha(new Date());
//            registro.setIdUsuarioSeguridad(usuarioRegistrado);
//            //Envío a guardar
//            docRolEstModel.agregar(registro);
//            
            listarRolesAprobacion();
            if(actaAprobada()){
//                acta.setIdEstado(estadoGeneralAprobado);
                actaModel.guardar(acta);
            }
                
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    private boolean actaAprobada(){
        for(ViewDocumAprobEntity valor : documentoRoles){
            if( (valor.getIdEstado() == null) || (valor.getIdEstado().getIdEstado().equals( estadoGeneralRechazado.getId() ) ))
                return false;
        }
        return true;
    }

    /**
     * Rechaza el informe
     *
     * @param pEvent
     */
    public void rechazar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            // Inicializo el ViewDocumAprobEntity para registrarlo en base de datos
            ViewDocumAprobEntity docum = (ViewDocumAprobEntity) pEvent.getComponent().getAttributes().get("documentoRechazar");
            DocumentoEntity documentoEntity = new DocumentoEntity();
            documentoEntity.setIdDocumento( Long.parseLong(""+docum.getIdDocumento() ) );
            RolEntity rolEntity = new RolEntity();
            rolEntity.setIdRol( Long.parseLong(""+docum.getRolId() ) );
            //Inicializo el registro DocumentoRolEstadoEntity
//            DocumentoRolEstadoEntity registro = new DocumentoRolEstadoEntity( Long.parseLong(""+acta.getIdActa())
//                                                                            , documentoEntity
//                                                                            , rolEntity
//                                                                            , estadoGeneralRechazado);
            //Completo los datos
//            registro.setFecha(new Date());
//            registro.setIdUsuarioSeguridad(usuarioRegistrado);
            //Envío a guardar
//            docRolEstModel.agregar(registro);
            
  //          acta.setIdEstado(estadoGeneralRechazado);
            actaModel.guardar(acta);
                
            listarRolesAprobacion();
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public List<ViewDocumAprobEntity> getDocumentoRoles() {
        return documentoRoles;
    }

    public void setDocumentoRoles(List<ViewDocumAprobEntity> documentoRoles) {
        this.documentoRoles = documentoRoles;
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Listado Bienes">
    public void cerrarListaBienes() {
        try {
            mostrarDialogBienes = false;
        } catch (Exception err) {
        }
    }

    public void abrirListaBienes() {
        try {
            consultaBienes = 2;

            mostrarBienes();
            mostrarDialogBienes = true;
        } catch (Exception err) {
        }
    }

    //</editor-fold>
    
    
}
