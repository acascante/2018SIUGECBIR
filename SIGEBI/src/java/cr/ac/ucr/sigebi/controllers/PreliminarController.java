/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.PreliminarCommand;
import cr.ac.ucr.sigebi.domain.AutorizacionRolPersona;
import cr.ac.ucr.sigebi.domain.Preliminar;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.models.AutorizacionRolPersonaModel;
import cr.ac.ucr.sigebi.models.PreliminarModel;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author jorge.serrano
 */
@Controller(value = "controllerPreliminar")
@Scope("session")
@RequestMapping("preliminar")
public class PreliminarController extends BaseController  {

    
    @RequestMapping(value="listaUnidades", method=RequestMethod.POST)
    public @ResponseBody String unidadesEjecutoras() {
        
            //si no lo está, generamos las sugerencias con la ayuda del IUsuarioBO
            return "Hola guapo soy una respuesta del server :-*";
    }
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    
    private Long fltId;
    private String fltIdentificacion;
    private String fltDescripcion;
    private String fltUnidad;
    private String fltMarca;
    private String fltModelo;
    private String fltSerie;
    private String fltOrden;
    private String fltFactura;
    private Long fltIdEstado;
    
    @Resource private PreliminarModel preliminarModel;
    @Resource private UnidadEjecutoraModel unidadEjecutoraModel;
    @Resource private UnidadEjecutoraModel unidadModel;
    @Resource private AutorizacionRolPersonaModel autorizacionRolPersonaModel;
    
    private PreliminarCommand command;
    private boolean modalVisible;
    List<UnidadEjecutora> unidadesEjecutoras;
    private UnidadEjecutora unidadActual;
    private Usuario usuario;
    private Preliminar preliminar;
    
    private AutorizacionRolPersona autorizado;
    boolean usuarioAdmin;
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Inicio Clase">
    
    public PreliminarController() {
        super();
        
        fltId = -1L;
        fltIdentificacion = "";
        fltDescripcion = "";
        fltUnidad = "";
        fltMarca= "";
        fltModelo = "";
        fltSerie = "";
        fltOrden = "";
        fltFactura = "";
        fltIdEstado = -1L;
        
    }
    
    
    @PostConstruct
    public final void inicializar() {
        try {    
            usuario = this.usuarioSIGEBI;
            unidadActual = this.unidadEjecutora;
            unidadesEjecutoras = unidadEjecutoraModel.listar();
            command = new PreliminarCommand();
            
            preliminar = new Preliminar();
            preliminar.setUnidad(unidadActual);
            preliminar.setUsuarioRegistra(usuario);
            preliminar.setUnidad(unidadActual);
            
            command.setPreliminar(preliminar);
            modalVisible = false;
            
            autorizado = autorizacionRolPersonaModel.buscar(Constantes.CODIGO_AUTORIZACION_ADMINISTRADOR, Constantes.CODIGO_ROL_ADMINISTRADOR_AUTORIZACION_ADMINISTRADOR, usuarioSIGEBI, unidadEjecutora);
        
            usuarioAdmin = ( (autorizado != null) && (autorizado.getAutorizacionRol().getId() != null) );
            
            
            
            listar();
            consultarUnidades();
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.informeTecnicoController.inicializar"));
        }
    }


    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Set's & Get's">
    
    
    public  List<UnidadEjecutora> getUnidadesEjecutoras() {    
        return unidadesEjecutoras;
    }
    
    public void setUnidadesEjecutoras(List<UnidadEjecutora> unidadesEjecutoras) {
        this.unidadesEjecutoras = unidadesEjecutoras;
    }

    public boolean isUsuarioAdmin() {
        return usuarioAdmin;
    }
    

    public  String getFltIdentificacion() {
        return fltIdentificacion;
    }

    public  void setFltIdentificacion(String fltIdentificacion) {
        this.fltIdentificacion = fltIdentificacion;
    }

    public  String getFltDescripcion() {
        return fltDescripcion;
    }

    public  void setFltDescripcion(String fltDescripcion) {
        this.fltDescripcion = fltDescripcion;
    }

    public  String getFltUnidad() {
        return fltUnidad;
    }

    public  void setFltUnidad(String fltUnidad) {
        this.fltUnidad = fltUnidad;
    }

    public  String getFltMarca() {
        return fltMarca;
    }

    public  void setFltMarca(String fltMarca) {
        this.fltMarca = fltMarca;
    }

    public  String getFltModelo() {
        return fltModelo;
    }

    public  void setFltModelo(String fltModelo) {
        this.fltModelo = fltModelo;
    }

    public  String getFltSerie() {
        return fltSerie;
    }

    public  void setFltSerie(String fltSerie) {
        this.fltSerie = fltSerie;
    }

    public  String getFltOrden() {
        return fltOrden;
    }

    public  void setFltOrden(String fltOrden) {
        this.fltOrden = fltOrden;
    }

    public  String getFltFactura() {
        return fltFactura;
    }

    public  void setFltFactura(String fltFactura) {
        this.fltFactura = fltFactura;
    }


    public PreliminarCommand getCommand() {
        return command;
    }

    public void setCommand(PreliminarCommand command) {
        this.command = command;
    }

    public boolean isModalVisible() {
        return modalVisible;
    }

    public void setModalVisible(boolean modalVisible) {
        this.modalVisible = modalVisible;
    }
    
    
    
    
    
    //</editor-fold>
    
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Listados">
    
    
    
    public void consultarUnidades(){
        try{
            unidadesEjecutoras = new ArrayList<UnidadEjecutora>();
            unidadesEjecutoras = unidadModel.listar();
        }
        catch(Exception err){
            
        }
    }
    
    
    public void listar(){
        try{
            
            Long cantReg = preliminarModel.contar(fltId
                    , fltIdentificacion
                    , fltDescripcion
                    , fltUnidad
                    , fltMarca
                    , fltModelo
                    , fltSerie
                    , fltOrden
                    , fltFactura
                    , fltIdEstado);
            this.setCantidadRegistros(cantReg.intValue());
            
            
            int primerRegistro = this.getPrimerRegistro()-1;
            int ultimoRegistro = this.getUltimoRegistro();
            List<Preliminar> preliminares = null;
            preliminares = preliminarModel.listar(primerRegistro
                                  ,  ultimoRegistro
                                  , fltId
                                  , fltIdentificacion
                                  , fltDescripcion

                                  , fltUnidad
                                  , fltMarca
                                  , fltModelo
                                  , fltSerie
                                  , fltOrden

                                  , fltFactura
                                  , fltIdEstado
            
            );
            command.setPreliminares(preliminares);
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
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
        this.listar();

    }
    
    
    public void cambioFiltro(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        
        this.setPrimerRegistro(1);
        this.listar();
        
    }
    
    public void listaPrimero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listar();
    }
    
    
    public void listaAnterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listar();
    }
    
    
    
    public void listaIrPagina(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPag"));
        this.getPrimerRegistroPagina(numeroPagina);
        this.listar();
    }
    
    
    
    public void listaSiguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listar();
    }

    
    public void listaUltimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listar();
    }

    
    
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    
    
    public void mostrarPanel(){
        
            
        modalVisible = true;
    }
    
    public void cerrarPanel(){
        
        modalVisible = false;
        
        preliminar = new Preliminar();
        preliminar.setUnidad(unidadActual);
        preliminar.setUsuarioRegistra(usuario);
        preliminar.setUnidad(unidadActual);

        command.setPreliminar(preliminar);
        
    }
    
    public void guardarPreliminar(){
        try{
            String string = command.getUnidadString();
                UnidadEjecutora unidad = null;
            if (string.contains("-")) {
                try{
                    String[] parts = string.split("-");
                    String part1 = parts[0]; // 004
                    Long idUnidad = Long.parseLong(part1);

                    unidad = unidadEjecutoraModel.buscarPorId(idUnidad);
                }
                catch(Exception err){
                }
            } else {
                throw new IllegalArgumentException("String " + string + " does not contain -");
            }
            
            command.getPreliminar().setUnidad(unidad);
            
            
            preliminarModel.guardar(command.getPreliminar());
            
            command = new PreliminarCommand();
            
            preliminar = new Preliminar();
            preliminar.setUnidad(unidadActual);
            preliminar.setUsuarioRegistra(usuario);
            preliminar.setUnidad(unidadActual);
            
            command.setPreliminar(preliminar);
            
            
            this.listar();
            this.cerrarPanel();
            
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Preliminar.Mns.ExitoGuardar"));
        }
        catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Preliminar.Mns.ErrorGuardar"));
        }
    }
    
    public void verDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se obtiene el informe que se desea presentar 
            preliminar = (Preliminar) pEvent.getComponent().getAttributes().get("itemSeleccionado");
            command.setPreliminar(preliminar);
            
            modalVisible = true;;
        }catch(Exception err){
            
        }
        
    }
    
    //</editor-fold>
    
}
