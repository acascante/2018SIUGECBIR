/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.DocumentoActa;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import cr.ac.ucr.sigebi.domain.AutorizacionRol;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Rol;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.models.ActaModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.DocumentoAutorizacion;
import cr.ac.ucr.sigebi.entities.DocumentoEntity;
import cr.ac.ucr.sigebi.models.TipoModel;
//import cr.ac.ucr.sigebi.entities.RolEntity;
import cr.ac.ucr.sigebi.entities.ViewDocumAprobEntity;
import cr.ac.ucr.sigebi.models.DocumentoRolEstadoModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.AutorizacionRolModel;
import cr.ac.ucr.sigebi.models.DocumentoAutorizacionModel;
import cr.ac.ucr.sigebi.models.UsuarioModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.text.DateFormat;
import java.text.ParseException;
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
    DocumentoActa acta;

    //TODO el manejo de estados debe modificarse para que si agrega un nuevo estado no haya que modificar el codigo
    Estado estadoGeneralActivo;
    Estado estadoGeneralPendiente;
    Estado estadoGeneralAprobado;
    Estado estadoGeneralRechazado;
    
    List<DocumentoActa> actasRegistradas;
    List<DocumentoDetalle> bienesActa;
    List<DocumentoDetalle> bienesActaEliminar;
    
    Map<Long, DocumentoDetalle> actaDetalleMap;

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
    boolean mostrarActivarBtn;

    Tipo tipoSeleccionado;


    //Map<Long, Rol> rolesDelUsuarioActualDesecho;
    
    @Resource
    private TipoModel tipoModel;

    @Resource
    private EstadoModel estadoModel;

    @Resource private ActaModel actaModel;
    @Resource private AutorizacionRolPersonaModel autorizacionRolPersonaModel;
    @Resource private UsuarioModel usuarioModel;
    
    Usuario usuario;
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    public ActaController() {
        super();
    }

    @PostConstruct
    private void incializaDatos() {
        try{
            // TODO manejar los estados en un List por dominio
            estadoGeneralActivo = estadoModel.buscarPorDominioEstado( Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO);
            estadoGeneralPendiente = estadoModel.buscarPorDominioEstado( Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_PENDIENTE);
            estadoGeneralAprobado = estadoModel.buscarPorDominioEstado( Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_APROBADO);
            estadoGeneralRechazado = estadoModel.buscarPorDominioEstado( Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_RECHAZADO);
    //      
            usuario = usuarioModel.buscarPorId(codPersonaReg);
//            rolesDelUsuarioActualDesecho = new HashMap<Long, Rol>();
//            rolesDelUsuarioActualDonacion = new HashMap<Long, Rol>();

            bienesActa = new ArrayList<DocumentoDetalle>();
            
            //List<AutorizacionRolPersona> rolesAprobacionDonacion =  autorizacionRolPersonaModel.buscarRolesPorAutorizacionUsuario(Long.parseLong(Constantes.CODIGO_AUTORIZACION_ACTA_DONACION.toString()), usuarioRegistrado.getId(), unidadEjecutora.getId());     
            //List<AutorizacionRolPersona> rolesAprobacionDesecho =  autorizacionRolPersonaModel.buscarRolesPorAutorizacionUsuario(Long.parseLong(Constantes.CODIGO_AUTORIZACION_ACTA_DESECHO.toString()), usuarioRegistrado.getId(), unidadEjecutora.getId());     


            //Estos son los roles registrados para este USUARIO en cada DOCUMENTO
//            for(AutorizacionRolPersona item : rolesAprobacionDesecho)
//                rolesDelUsuarioActualDesecho.put(item.getAutorizacionRol().getRol().getId(), item.getAutorizacionRol().getRol());
//            for(AutorizacionRolPersona item : rolesAprobacionDonacion)
//                rolesDelUsuarioActualDonacion.put(item.getAutorizacionRol().getRol().getId(), item.getAutorizacionRol().getRol());
                    
            listadoInicializaDatos();
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.ActaListar.MSGError.Listado"));
        }
    }

    private void iniciaDatos() {
        try {
            consultaBienes = Constantes.BIENES_LISTADO_ACTAS;

            tiposActaOptions = new ArrayList<SelectItem>();
            bienesSeleccionados = new HashMap<Long, Bien>();
            actaDetalleMap = new HashMap<Long, DocumentoDetalle>();
            
            bienesActa = new ArrayList<DocumentoDetalle>();
            bienesActaEliminar = new ArrayList<DocumentoDetalle>();
            
            cargarTipo();
            acta = new DocumentoActa(estadoGeneralPendiente
                                    ,null
                                    ,""
                                    , unidadEjecutora
                            );
            acta.setUnidadEjecutora(unidadEjecutora);

            acta.setEstado(estadoGeneralPendiente);

            registrado = false;
            esDonacion = false;
            mostrarActivarBtn = false;

            selectValorDefecto = Constantes.SELECT_DEFAULT;
            tipoActaSeleccionada = selectValorDefecto;
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            actaFecha = formatter.parse(formatter.format(acta.getFecha()));
            fechaRegistro = formatter.format(acta.getFecha());

            actaNombreEstado = estadoGeneralPendiente.getNombre();

            iniciaListadoBienes();
            
            documentoRoles = new ArrayList<AutorizacionRol>();
            permitirEdicion = permitirEditar();

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    private void cargarDetalle(DocumentoActa item) throws ParseException{
        if (item.getId() > 0) {
                acta = item;
                
                tipoActaSeleccionada = acta.getTipo().getId().toString();
                esDonacion = tipoActaSeleccionada.equals(Constantes.ACTA_ID_TIPO_DONACION);
                tipoSeleccionado = acta.getTipo();
                
                actaFecha = formatter.parse(formatter.format(acta.getFecha()));
                fechaRegistro = formatter.format(acta.getFecha());

                bienesActa = actaModel.traerBienesActa(acta);

                if(bienesActa != null)
                    for (DocumentoDetalle valor : bienesActa) {
                        bienesSeleccionados.put(valor.getBien().getId(), valor.getBien());
                        actaDetalleMap.put(valor.getBien().getId(), valor);
                    }

                listarRolesAprobacion();
                permitirEdicion = permitirEditar();
                mostrarActivarBtn = permitirEdicion;
            }
    }
    
    
    void cargarTipo() {
        try {
            //Aqui traemos los tipos de bienes para los Select
            List<Tipo> tipoEntity = tipoModel.listarPorDominio(Constantes.DOMINIO_ACTA);
            for (Tipo item : tipoEntity) {
                if (item.getNombre().toUpperCase().contains("DONAC")) {
                    valorDonacion = item.getId().toString();
                }
                tiposActaOptions.add(new SelectItem("" + item.getId(), item.getNombre()));
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="GETs & SETs">

    public boolean isMostrarActivarBtn() {
        return mostrarActivarBtn;
    }

    public void setMostrarActivarBtn(boolean mostrarActivarBtn) {
        this.mostrarActivarBtn = mostrarActivarBtn;
    }
    
    public boolean isPermitirEdicion() {
        return permitirEdicion;
    }

    public void setPermitirEdicion(boolean permitirEdicion) {
        this.permitirEdicion = permitirEdicion;
    }
    
    public List<DocumentoActa> getActasRegistradas() {
        return actasRegistradas;
    }

    public void setActasRegistradas(List<DocumentoActa> actasRegistradas) {
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

    public DocumentoActa getActa() {
        return acta;
    }

    public void setActa(DocumentoActa acta) {
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

    public List<DocumentoDetalle> getBienesActa() {
        return bienesActa;
    }

    public void setBienesActa(List<DocumentoDetalle> bienesActa) {
        this.bienesActa = bienesActa;
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Navegar">
    
    
    public void verDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            iniciaDatos();
            DocumentoActa item = (DocumentoActa) pEvent.getComponent().getAttributes().get("itemSeleccionado");
            cargarDetalle(item);
            Util.navegar(Constantes.KEY_VISTA_ACTA);//"actaDetalle"
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.Acta.MsnError.Cargar"));
            //Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
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
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.Acta.MsnError.Cargar"));
            //Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    public void regresar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            
            fltIdActa = "";
            fltAutorizacion = "";
            fltEstados = "";
            fltFecha = "";
            listadoInicializaDatos();
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
                actasRegistradas = actaModel.listarActas(unidadEjecutora.getId()
                        , fltIdActa
                        , fltAutorizacion
                        , fltEstados.equals("-1") ? "" : fltEstados
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
            Long contador = 0L;
            
            contador = actaModel.consultaCantidadRegistros(unidadEjecutora.getId()
                        , fltIdActa
                        , fltAutorizacion
                        , fltEstados.equals("-1") ? "" : fltEstados
                        , fltFecha
                );
            
            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());
            
        }catch(Exception err){
            
        }
    }
    
    
    public void actaIrPagina(ActionEvent pEvent) {
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
    public void actaSiguiente(ActionEvent pEvent) {
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
    public void actaAnterior(ActionEvent pEvent) {
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
    public void actaPrimero(ActionEvent pEvent) {
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
    public void actaUltimo(ActionEvent pEvent) {
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
    public void actaCambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));        
        this.setPrimerRegistro(1);
        this.listarActas();

    }
    

    
    
    
    public void actasCambioFiltro() {
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
            listadoInicializaDatos();
            
            permitirEdicion = permitirEditar();
            mostrarActivarBtn = permitirEdicion;
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Acta.MsnError.ActaError"));
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
            tipoSeleccionado = tipoModel.buscarPorId(Long.parseLong(valor));
        }

        esDonacion = valor.equals(Constantes.ACTA_ID_TIPO_DONACION);
    }

    public void activarActa(){
        try{
            
            if (bienesActa.isEmpty()) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Acta.MsnError.BienesSeleccionados"));
                return;
            }
            acta.setEstado(estadoGeneralActivo);
            guardarActa();
        
            listadoInicializaDatos();
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
            
            actaModel.eliminarBienes(bienesActaEliminar);
            actaModel.guardarBienes(bienesActa);
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Acta.MsnError.ActaGuardada"));
            mostrarActivarBtn = true;
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
            acta.setTipo(tipoSeleccionado);
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

        if (!mensaje.equals("")) {
            Mensaje.agregarErrorAdvertencia(mensaje);
            return false;
        } else 
            if (acta.getEstado() == null) 
                acta.setEstado(estadoGeneralPendiente);
            
        //bienesAsociados = new ArrayList<ActaDetalle>(bienesSeleccionados.values());

        return true;

    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Listado Roles Aprobacion por Documento">
    String valorDonacion;

    @Resource
    private DocumentoAutorizacionModel documRolModel;
    
    //Los Roles de la tabla
    List<AutorizacionRol> documentoRoles;
    
    //DocumentoAutorizacion documentoRoles;
    
    Map<Integer, ViewDocumAprobEntity> rolesIncluidos;

    public void listarRolesAprobacion() {
        try {
            

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Acta.MsnError.ActaError"));
            //Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    public boolean permitirEditar() {
        return acta.getEstado().getId().equals(estadoGeneralPendiente.getId());
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
            Rol rolEntity = new Rol();
//            rolEntity.setId() Long.parseLong(""+docum.getRol() ) );
            //Inicializo el registro DocumentoRolEstadoEntity
//            DocumentoRolEstadoEntity registro = new DocumentoRolEstadoEntity( Long.parseLong(""+acta.getId())
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
//                acta.setEstado(estadoGeneralAprobado);
                actaModel.guardar(acta);
            }
                
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    private boolean actaAprobada(){
//        for(AutorizacionRol valor : documentoRoles){
//            if( (valor.get() == null) || (valor.getIdEstado().getIdEstado().equals( estadoGeneralRechazado.getId() ) ))
//                return false;
//        }
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
//            RolEntity rolEntity = new RolEntity();
//             rolEntity.setIdRol( Long.parseLong(""+docum.getRolId() ) );
            //Inicializo el registro DocumentoRolEstadoEntity
//            DocumentoRolEstadoEntity registro = new DocumentoRolEstadoEntity( Long.parseLong(""+acta.getId())
//                                                                            , documentoEntity
//                                                                            , rolEntity
//                                                                            , estadoGeneralRechazado);
            //Completo los datos
//            registro.setFecha(new Date());
//            registro.setIdUsuarioSeguridad(usuarioRegistrado);
            //Envío a guardar
//            docRolEstModel.agregar(registro);
            
  //          acta.setEstado(estadoGeneralRechazado);
            actaModel.guardar(acta);
                
            listarRolesAprobacion();
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public List<AutorizacionRol> getDocumentoRoles() {
        return documentoRoles;
    }

    public void setDocumentoRoles(List<AutorizacionRol> documentoRoles) {
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
                if((actaDetalleMap.get(bien.getId()).getId()!= null)&&
                        (actaDetalleMap.get(bien.getId()).getId() > 0))
                    bienesActaEliminar.add(actaDetalleMap.get(bien.getId()));
                // Lo elimino de los datos que estoy mostrando
                bienesSeleccionados.remove(bien.getId());
                actaDetalleMap.remove(bien.getId());
            }else{
                bienesSeleccionados.put(bien.getId(), bien);
                actaDetalleMap.put(bien.getId(), getActaDetalleDefault(bien));
            }
            bienesActa = new ArrayList<DocumentoDetalle>( actaDetalleMap.values() );
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
                if((actaDetalleMap.get(bien.getId()).getId()!= null)&&
                        (actaDetalleMap.get(bien.getId()).getId() > 0))
                    bienesActaEliminar.add(actaDetalleMap.get(bien.getId()));
                // Lo elimino de los datos que estoy mostrando
                bienesSeleccionados.remove(bien.getId());
                actaDetalleMap.remove(bien.getId());
            }else{
                bienesSeleccionados.put(bien.getId(), bien);
                actaDetalleMap.put(bien.getId(), getActaDetalleDefault(bien));
            }
            bienesActa = new ArrayList<DocumentoDetalle>( actaDetalleMap.values() );
            bienesAsociados = new ArrayList<Bien>( bienesSeleccionados.values() );
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }
    
    private DocumentoDetalle getActaDetalleDefault(Bien bien){
        try{
            DocumentoDetalle det = new DocumentoDetalle();
            det.setDocumento(acta);
            det.setBien(bien);
            det.setDiscriminator(Constantes.DISCRIMINATOR_DOCUMENTO_ACTA);
            return det;
        }catch(Exception e){
            return null;
        }
    }
    

    // </editor-fold>
    
}
