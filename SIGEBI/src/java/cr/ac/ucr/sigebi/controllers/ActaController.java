/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.*;
import cr.ac.ucr.sigebi.models.*;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
    List<Estado> estadosActa;
    List<SelectItem> estadosActaOptions;
    
    // Variables de estado Interno
    Estado estadoInternoActaPendiente;
    Estado estadoInternoActaProceso;
    Estado estadoInternoActaAplicada;
    Estado estadoInternoActaRechazada;
    
    List<DocumentoActa> actasRegistradas;
    List<DocumentoDetalle> bienesActa;
    List<DocumentoDetalle> bienesActaEliminar;
    
    Map<Long, DocumentoDetalle> actaDetalleMap;

    List<SelectItem> tiposActaOptions;
    String tipoActaSeleccionada;
    String selectValorDefecto;

    String actaIdEstado;
    String actaNombreEstado;

    DateFormat formatter;
    Date actaFecha;

    String fechaRegistro;

    boolean registrado;
    boolean esDonacion;
    boolean permitirEdicion;
    boolean mostrarActivarBtn;

    Tipo tipoSeleccionado;

    private List<SelectItem> itemsUnidadEjecutora;

    private boolean usuarioAdministrador;

    @Resource private ActaModel actaModel;
    @Resource private AutorizacionRolPersonaModel autorizacionRolPersonaModel;
    @Resource private BienModel bienModel;
    @Resource private TipoModel tipoModel;
    @Resource private UnidadEjecutoraModel unidadEjecutoraModel;
    @Resource private UsuarioModel usuarioModel;
    
    Usuario usuario;
    String valorDonacion;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    public ActaController() {
        super();
    }

    @PostConstruct
    private void incializaDatos() {
        try{
            AutorizacionRolPersona administrador = autorizacionRolPersonaModel.buscar(Constantes.CODIGO_AUTORIZACION_ADMINISTRADOR, Constantes.CODIGO_ROL_ADMINISTRADOR_AUTORIZACION_ADMINISTRADOR, usuarioSIGEBI, unidadEjecutora);
            usuarioAdministrador = administrador == null ? false : true;

            if (usuarioAdministrador) {
                List<UnidadEjecutora> unidadesEjecutoras = unidadEjecutoraModel.listar();
                if (!unidadesEjecutoras.isEmpty()) {
                    this.itemsUnidadEjecutora = new ArrayList<SelectItem>();
                    for (UnidadEjecutora item : unidadesEjecutoras) {
                        this.itemsUnidadEjecutora.add(new SelectItem(item.getId(), item.getDescripcionSmall()));
                    }
                }
            } else {
                this.setFltUnidadEjecutora(unidadEjecutora.getId());
            }

            // TODO manejar los estados en un List por dominio
            estadoGeneralActivo = this.estadoPorDominioValor( Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO );
            estadoGeneralPendiente = this.estadoPorDominioValor( Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_PENDIENTE );
            estadoGeneralAprobado = this.estadoPorDominioValor( Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_APROBADO );
            estadoGeneralRechazado = this.estadoPorDominioValor( Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_RECHAZADO );
            
            estadosActa = new ArrayList<Estado>();
            estadosActa.add(estadoGeneralActivo);
            estadosActa.add(estadoGeneralPendiente);
            estadosActa.add(estadoGeneralAprobado);
            estadosActa.add(estadoGeneralRechazado);
            
            //Estados Internos del Acta
            estadoInternoActaPendiente = this.estadoPorDominioValor( Constantes.DOMINIO_ACTA, Constantes.ESTADO_ACTA_PENDIENTE) ;
            estadoInternoActaProceso = this.estadoPorDominioValor( Constantes.DOMINIO_ACTA, Constantes.ESTADO_ACTA_PROCESO);
            estadoInternoActaAplicada = this.estadoPorDominioValor( Constantes.DOMINIO_ACTA, Constantes.ESTADO_ACTA_APLICADA);
            estadoInternoActaRechazada = this.estadoPorDominioValor( Constantes.DOMINIO_ACTA, Constantes.ESTADO_ACTA_RECHAZADA);
            
            usuario = usuarioModel.buscarPorId(codPersonaReg);

            bienesActa = new ArrayList<DocumentoDetalle>();
            cargarCombos();
            
            listadoInicializaDatos();
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.ActaListar.MSGError.Listado"));
        }
    }

    private void iniciaDatos() {
        try {
            consultaBienes = Constantes.BIENES_LISTADO_ACTAS;

            bienesSeleccionados = new HashMap<Long, Bien>();
            actaDetalleMap = new HashMap<Long, DocumentoDetalle>();
            
            bienesActa = new ArrayList<DocumentoDetalle>();
            bienesActaEliminar = new ArrayList<DocumentoDetalle>();
            
            acta = new DocumentoActa(estadoGeneralPendiente, null,"", unidadEjecutora);
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
            documentosAutorizacionesPorRol = new HashMap<String, DocumentoAutorizacion>(); 
            iniciaListadoBienes();
            
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
            permitirEdicion = permitirEditar();
            mostrarActivarBtn = permitirEdicion;
            
            if(!permitirEdicion)
                cargaRolesAprobacion();
        }
    }
    
    void cargarCombos() {
        try {
            tiposActaOptions = new ArrayList<SelectItem>();
            //Aqui traemos los tipos de bienes para los Select
            List<Tipo> tipoEntity = tipoModel.listarPorDominio(Constantes.DOMINIO_ACTA);
            Tipo donacion = new Tipo();
            Tipo desecho = new Tipo();
            for (Tipo item : tipoEntity) {
                if (item.getNombre().toUpperCase().contains("DONAC")) {
                    valorDonacion = item.getId().toString();
                    donacion = item;
                }
                else
                    desecho = item;
            }
            tiposActaOptions.add(new SelectItem("" + desecho.getId(), desecho.getNombre()));
            tiposActaOptions.add(new SelectItem("" + donacion.getId(), donacion.getNombre()));
            estadosActaOptions = new ArrayList<SelectItem>();
            for (Estado item : estadosActa) {
                estadosActaOptions.add( new SelectItem("" + item.getId(), item.getNombre()) );
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GETs & SETs">

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

    public List<SelectItem> getItemsUnidadEjecutora() {
        return itemsUnidadEjecutora;
    }

    public void setItemsUnidadEjecutora(List<SelectItem> itemsUnidadEjecutora) {
        this.itemsUnidadEjecutora = itemsUnidadEjecutora;
    }

    public boolean isUsuarioAdministrador() {
        return usuarioAdministrador;
    }

    public void setUsuarioAdministrador(boolean usuarioAdministrador) {
        this.usuarioAdministrador = usuarioAdministrador;
    }

    public Long getFltUnidadEjecutora() {
        return fltUnidadEjecutora;
    }

    public void setFltUnidadEjecutora(Long fltUnidadEjecutora) {
        ActaController.fltUnidadEjecutora = fltUnidadEjecutora;
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
            this.vistaOrigen = pEvent.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
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
            this.vistaOrigen = Util.getActualPant();

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
            
            if (vistaOrigen != null) {
                Util.navegar(vistaOrigen, true);
            } else {

                fltIdActa = "";
                fltAutorizacion = "";
                fltEstados = "";
                fltFecha = "";
                listadoInicializaDatos();
                //inicializaDatos();
                
                Util.navegar(Util.getAnteriorPant());
                //Util.navegar(Constantes.KEY_VISTA_LISTAR_ACTAS);
            }

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Listado Actas">
    private static String fltIdActa = "";
    private static String fltAutorizacion = "";
    private static String fltFecha = "";
    private static String fltEstados = "";
    private static Long fltUnidadEjecutora = -1L;
    
    public void listarActas(){
        try{
            int primerReg = this.getPrimerRegistro()-1;
            int ultimoReg = this.getUltimoRegistro();
            actasRegistradas = actaModel.listarActas(fltUnidadEjecutora, fltIdActa, fltAutorizacion, fltEstados.equals("-1") ? "" : fltEstados, fltFecha, primerReg, ultimoReg);
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }
    
    private void listadoInicializaDatos(){
        if(fltEstados.equals("")) {
            fltEstados = "-1";
        }
        this.listadoCantidadRegistros();
        this.setPrimerRegistro(1);
        this.listarActas();
    }
    
    public void listadoCantidadRegistros(){
        Long contador = 0L;
        contador = actaModel.consultaCantidadRegistros(fltUnidadEjecutora, fltIdActa, fltAutorizacion, fltEstados.equals("-1") ? "" : fltEstados, fltFecha);
        this.setCantidadRegistros(contador.intValue());
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
        int cantReg = Integer.parseInt(pEvent.getNewValue().toString());
        this.setCantRegistroPorPagina(cantReg);        
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

    public List<SelectItem> getEstadosActaOptions() {
        return estadosActaOptions;
    }

    public void setEstadosActaOptions(List<SelectItem> estadosActaOptions) {
        this.estadosActaOptions = estadosActaOptions;
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
    
    public boolean permitirEditar() {
        return acta.getEstado().getId().equals(estadoGeneralPendiente.getId());
    }
    
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
        
        if(esDonacion)
            this.setFltTipo(this.tipoInfTecDonacion);
        else
            this.setFltTipo(this.tipoInfTecDesecho);
            
        // Limpiar los bienes seleccionados
        //Mensaje.agregarInfo("Se debe limpiar la lista de los bienes seleccionados");
        List<Bien> bienes = new ArrayList<Bien>( bienesSeleccionados.values() );
        for(Bien bien : bienes){
            agregarQuitarBien(bien);
        }
    }

    public void activarActa(){
        try{
            
            if (bienesActa.isEmpty()) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Acta.MsnError.BienesSeleccionados"));
                return;
            }
            acta.setEstado(estadoGeneralActivo);
            guardarActa();
        
            
                //Actualizo los bienes para que queden excluidos
                for(DocumentoDetalle detalle : bienesActa){
                    Bien bien = detalle.getBien();
                    bien.setEstadoInterno(estadoInternoActaProceso);
                    bienModel.actualizar(bien);
                }
                
            
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
            
            // Eliminamos los bienes del detalle documento
            actaModel.eliminarBienes(bienesActaEliminar);
            
            //actualizamos los bienes que se eliminan con el estado Interno
            for(DocumentoDetalle detalle : bienesActaEliminar){
                Bien bien = detalle.getBien();
                bien.setEstadoInterno(this.estadoIntInfTecAprobado);
                
                bienModel.actualizar(bien);
            }
            
            
            actaModel.guardarBienes(bienesActa);
            
            //Actualizo los bienes que se eliminan con el estado Interno
            for(DocumentoDetalle detalle : bienesActa){
                Bien bien = detalle.getBien();
                bien.setEstadoInterno(estadoInternoActaPendiente);
                
                bienModel.actualizar(bien);
            }
            
            
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
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Listado Bienes">
    public void cerrarListaBienes() {
        try {
            mostrarDialogBienes = false;
        } catch (Exception err) {
        }
    }

    public void abrirListaBienes() {
        try {
            // Realiza la consulta de Actas (Solo bienes con Informe técnico Aprobado) 
            // Según tipo de Informe y Acta (Desecho / Docación)
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
            
            agregarQuitarBien(bien);
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }
    
    private void agregarQuitarBien(Bien bien){
        try{
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
        }
        catch(Exception err){
            
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
    
    //<editor-fold defaultstate="collapsed" desc="Aprobaciones y rechazos">
    
    
    @Resource private DocumentoAutorizacionModel documentoAutorizacionModel;
    @Resource private DocumentoModel documentoModel;

    //Mapa para la lista de autorizaciones para el documento
    HashMap<String, DocumentoAutorizacion> documentosAutorizacionesPorRol;

    boolean aprobacionRealizada = false;
    boolean rolPermiteModificar = false;
    
    
    private void cargaRolesAprobacion()
    {
        try {
        
            //Se listan las autorizaciones permitidas para el documento, se agrupan por rol. 
            if(esDonacion)
                documentosAutorizacionesPorRol = documentoModel.obtenerDocumentosAutorizacionPorRolGeneral(Constantes.CODIGO_AUTORIZACION_ACTA_DONACION, acta, usuario, unidadEjecutora);
            else
                documentosAutorizacionesPorRol = documentoModel.obtenerDocumentosAutorizacionPorRolGeneral(Constantes.CODIGO_AUTORIZACION_ACTA_DESECHO, acta, usuario, unidadEjecutora);
            
            eliminaMarcaDocumentosAceptarRechazar();
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.Acta.MsnError.Cargar"));
            //Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }

    private void eliminaMarcaDocumentosAceptarRechazar() {
        try {
            for (Iterator<String> iterator = documentosAutorizacionesPorRol.keySet().iterator(); iterator.hasNext();) {
                DocumentoAutorizacion documentoAutorizacion = documentosAutorizacionesPorRol.get((String) iterator.next());

                //Si el acta no esta aprobado o anulado se permite modificarlo si es que tiene permisos, solo para los casos marcados en true
                if (documentoAutorizacion != null && documentoAutorizacion.isMarcado()
                        && (acta.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_APROBADO)
                        || acta.getEstado().getValor().equals(Constantes.ESTADO_INFORME_TECNICO_ANULADO))) {
                    documentoAutorizacion.setMarcado(false);
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Acta.MsnError.Cargar"));
        }
    }

    
    /**
     * Aprueba el acta
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

            //Se obtiene el documento a modificar
            DocumentoAutorizacion documento = (DocumentoAutorizacion) pEvent.getComponent().getAttributes().get("documentoSelApro");
            documento.setEstado(estadoGeneralAprobado);
            documento.setFecha(new Date());
            documento.setUsuarioSeguridad(usuario);
            if (documento.getId() != null && documento.getId() > 0) {
                documentoAutorizacionModel.modificar(documento);
            } else {
                documentoAutorizacionModel.agregar(documento);
            }

            //Se modifica en el acta en los casos que aplique
            cambiaEstadoDocumento();

        } catch (FWExcepcion e) {
            e.printStackTrace();
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            e.printStackTrace();
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.actaController.aprobar"));
        }
    }

    /**
     * Rechaza el acta
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

            //Se obtiene el documento a modificar
            DocumentoAutorizacion documento = (DocumentoAutorizacion) pEvent.getComponent().getAttributes().get("documentoSelRech");
            documento.setEstado(estadoGeneralRechazado);
            documento.setFecha(new Date());
            documento.setUsuarioSeguridad(usuario);
            if (documento.getId() != null && documento.getId() > 0) {
                documentoAutorizacionModel.modificar(documento);
            } else {
                documentoAutorizacionModel.agregar(documento);
            }

            
            //Actualizo los bienes para que queden excluidos
            for(DocumentoDetalle detalle : bienesActa){
                Bien bien = detalle.getBien();
                bien.setEstadoInterno(estadoIntInfTecAprobado);
                
                bienModel.actualizar(bien);
            }
            
            //Se modifica en el acta en los casos que aplique
            //cambiaEstadoDocumento();
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.actaController.rechazar"));
        }

    }

    /**
     * Metodo que cambia el estado del acta para los casos en que todos sus
     * autorizaciones se aprueben o se rechacen
     */
    private void cambiaEstadoDocumento() {
        try {

            //Solo si el acta está en estado Activo
            if ( acta.getEstado().getId().equals(estadoGeneralActivo.getId()) ) {
                
                for (Iterator<String> iterator = documentosAutorizacionesPorRol.keySet().iterator(); iterator.hasNext();) {
                    DocumentoAutorizacion documentoAutorizacion = documentosAutorizacionesPorRol.get((String) iterator.next());
                    //Si el acta no esta aprobado o anulado se permite modificarlo si es que tiene permisos
                    if (documentoAutorizacion != null) {
                        //Se verifica si alguno está rechazado
                        if ( documentoAutorizacion.getEstado().getId().equals(estadoGeneralRechazado.getId())) {
                            acta.setEstado(estadoGeneralRechazado);
                            documentoModel.modificar(acta);
                            return;
                        }
                        //Si alguno no esta aprobado se sale del 
                        if ( ! documentoAutorizacion.getEstado().getId().equals(estadoGeneralAprobado.getId()) ) {
                            aprobacionRealizada = true;
                            return;
                        }
                    }
                }

                //Si todos estan aprobados se aprueba todo el acta
                acta.setEstado(estadoGeneralAprobado);
                documentoModel.modificar(acta);
                
                
                //Actualizo los bienes para que queden excluidos
                for(DocumentoDetalle detalle : bienesActa){
                    Bien bien = detalle.getBien();
                    bien.setEstadoInterno(estadoInternoActaAplicada);
                    if(bien.getCapitalizable())
                        bien.setEstado(estadoExclusionAprobada);
                    else
                        bien.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_INACTIVO));
                        
                    bienModel.actualizar(bien);
                }
                
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.actaController.cambiaEstadoDocumento"));
        }
    }

    public Collection<DocumentoAutorizacion> getListaDocumentosAutorizacionesPorRol() {
        return new ArrayList(documentosAutorizacionesPorRol.values());
    }
    
    //</editor-fold>
    
    
    
}
