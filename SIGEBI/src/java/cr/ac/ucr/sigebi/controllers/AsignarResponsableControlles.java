/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.ResponsableCommand;
import cr.ac.ucr.sigebi.domain.AsignarResponsableHistorico;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.RegistroMovimientoBien;
import cr.ac.ucr.sigebi.domain.Usuario;
import cr.ac.ucr.sigebi.models.AsignarResponsableHistoricoModel;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.RegistroMovimientoModel;
import cr.ac.ucr.sigebi.models.UsuarioModel;
import cr.ac.ucr.sigebi.utils.Constantes;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jorge.serrano
 */
@Controller(value = "responsableController")
@Scope("session")
public class AsignarResponsableControlles  extends ListadoBienesGeneralController {
    
    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    
    private ResponsableCommand responsableCommand;
    
    
    private RegistroMovimientoBien movBien;
    
    @Resource
    private RegistroMovimientoModel modelRegistroMovim;
            
    @Resource
    private BienModel modelBien;
    
    private boolean validarFormulario;
    
    private List<Bien> misBienes;
    private String miUsuario;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    
    public AsignarResponsableControlles( ) {
        this.responsableCommand = new ResponsableCommand();
        this.movBien = new RegistroMovimientoBien();
        validarFormulario = false;
        
        misBienes = new ArrayList<Bien>();
        
        
    }
    
    
    @PostConstruct
    private void incializaDatos() {
        try{
            cargoMisDatos();
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Responsable.exito.CargarMisDatos"));
        }
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SETs & GETs">

    public List<Bien> getMisBienes() {
        return misBienes;
    }

    public void setMisBienes(List<Bien> misBienes) {
        this.misBienes = misBienes;
    }

    
    public boolean isValidarFormulario() {
        validarFormulario = this.validarForm();
        return validarFormulario;
    }

    public void setValidarFormulario(boolean validarFormulario) {
        this.validarFormulario = validarFormulario;
    }
    
    
    public ResponsableCommand getResponsableCommand() {
        return responsableCommand;
    }

    public void setResponsableCommand(ResponsableCommand responsableCommand) {
        this.responsableCommand = responsableCommand;
    }

    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos Buscar Usuario">
    
    
    @Resource
    private UsuarioModel modelUsuario;
    
    public void mostrarPanelUsuarios() {
        inicialListadoUsuarios();
        //this.proveedores = modelProveedor.listar(responsableCommand.getUsuarioCommand()getFiltroIdentificacion(), responsableCommand.getUsuarioCommand().getFiltroNombre());
        this.responsableCommand.setVisiblePanelUsuarios(true);
    }

    public void cerrarPanelUsuarios() {
        this.responsableCommand.setVisiblePanelUsuarios(false);
    } 
    
    public void limpiarUsuario() {
        
        responsableCommand.limpiarUsuarioCommand();
        
    }
    
    /**
     * Cambia la cantidad de registros por página
     *
     * @param pEvent
     */
    public void cambioRegistrosPorPaginaUsuarios(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int cantReg = Integer.parseInt(pEvent.getNewValue().toString());
        responsableCommand.getUsuarioCommand().setCantRegistroPorPagina(cantReg);        
        responsableCommand.getUsuarioCommand().setPrimerRegistro(1);
        
        this.inicialListadoUsuarios();

    }
    
    public void irPaginaUsuarios(ActionEvent pEvent)  {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPag"));
        responsableCommand.getUsuarioCommand().getPrimerRegistroPagina(numeroPagina);
        this.inicialListadoUsuarios();
    }

    public void siguienteUsuarios(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        responsableCommand.getUsuarioCommand().getSiguientePagina();
        this.inicialListadoUsuarios();
    }

    public void anteriorUsuarios(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        responsableCommand.getUsuarioCommand().getPaginaAnterior();
        this.inicialListadoUsuarios();
    }

    public void primeroUsuarios(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        responsableCommand.getUsuarioCommand().setPrimerRegistro(1);
        this.inicialListadoUsuarios();
    }

    public void ultimoUsuarios(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        responsableCommand.getUsuarioCommand().getPrimerRegistroUltimaPagina();
        this.inicialListadoUsuarios();
    }

    public void filtroUsuario(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                 pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                 pEvent.queue();
                 return;
            }
            this.inicialListadoUsuarios();
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.filtroProveedor"));
        }
    }
    
    private void inicialListadoUsuarios(){
        responsableCommand.getUsuarioCommand().setPrimerRegistro(1);
        Long cantReg = modelUsuario.contarUsuarios(responsableCommand.getUsuarioCommand().getFiltroIdUdsuario()
                                                , responsableCommand.getUsuarioCommand().getFiltroNombre().replace(" ", "%")
                                                , null // Filtro de correo electrónico
                                                );
        responsableCommand.getUsuarioCommand().setCantidadRegistros(cantReg.intValue());
        listarUsuarios();
    }
    
    private void listarUsuarios(){
        responsableCommand.getUsuarioCommand().setUsuarios(
                
                    modelUsuario.listarUsuarios(responsableCommand.getUsuarioCommand().getFiltroIdUdsuario()
                                                , responsableCommand.getUsuarioCommand().getFiltroNombre().replace(" ", "%")
                                                , null  // Filtro de correo electrónico
                                                , responsableCommand.getUsuarioCommand().getPrimerRegistro()-1
                                                , responsableCommand.getUsuarioCommand().getCantRegistroPorPagina())
                
        );
    }
    
    public void selecionarUsuario(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            Usuario usuario = (Usuario) event.getComponent().getAttributes().get("usuarioSeleccionado");
            responsableCommand.getUsuarioCommand().setUsuario(usuario);
            
            this.responsableCommand.setVisiblePanelUsuarios(false);
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.agregarBienController.selecionarProveedor"));
        }

    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos Buscar Bienes">
    
    public void abrirListaBienes() {
        try {
            consultaBienes = 3;            
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
                // Lo elimino de los datos que estoy mostrando
                bienesSeleccionados.remove(bien.getId());
            }else{
                bienesSeleccionados.put(bien.getId(), bien);
            }
            bienesAsociados = new ArrayList<Bien>( bienesSeleccionados.values() );
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }
    
    
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
                bienesSeleccionados.remove(bien.getId());
            }else{
                bien.setUsuarioResponsable(responsableCommand.getUsuarioCommand().getUsuario());
                bien.setEstadoAsignacion(responsableCommand.getEstadoAsignacionPendiente());
                
                bienesSeleccionados.put(bien.getId(), bien);
            }
            //bienesAsociados = new Array<>(bienesSeleccionados.values());
            bienesAsociados = new ArrayList<Bien>( bienesSeleccionados.values() );
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
        
    }
    
    
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Asignar">
    
    public void verMisBienesAsignados() {
        try {

            cargoMisDatos();
            Util.navegar(Constantes.KEY_VISTA_MIS_BIENES);

        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
    }
    
    public boolean validarForm(){
        Usuario usr = responsableCommand.getUsuarioCommand().getUsuario();
        if( ( usr != null ) && ( usr.getId() != null ) && (usr.getId().length() > 0))
            if(bienesAsociados.size() > 0)
                return true;
//            else
//                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Responsable.error.validar.bienes"));
//        }
//        else{
//            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Responsable.error.validar.usuario"));
//        }
        return false;
    }
    
    // registra asignación en los biens, los deja en estado pendiente
    public void guardarAsignacionConfirmacion(){
        try{
            if(validarForm())     
                this.getResponsableCommand().setVisiblePanelConfirmacion(true);
            else{
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Responsable.error.validar"));
            }
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Responsable.error.validar"));
        }
    }
    
    public void cerrarPanelConfirmar(){
        try{
            this.getResponsableCommand().setVisiblePanelConfirmacion(false);
        }catch(Exception err){
            
        }
    }
    
    
    public void guardarAsignacion(){
        try{
            this.getResponsableCommand().setVisiblePanelConfirmacion(false);
            
            List<Bien> bienes = new ArrayList<Bien>();
            
            
            for(Bien bien : bienesAsociados){
                bien.setUsuarioResponsable(responsableCommand.getUsuarioCommand().getUsuario());
                modelBien.actualizar(bien);
                guardarMovimientoBien(bien);
            }
            
            
            //modelBien.actualizar(bienesAsociados);
            responsableCommand = new ResponsableCommand();
            this.inicializar();
            
            cargoMisDatos();
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Responsable.exito.Guardar"));
            
        }catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Responsable.error.Guardar"));
        }
    }
    
    
    private void guardarMovimientoBien(Bien bien){
        movBien = new RegistroMovimientoBien();
        
        movBien.setBien(bien);
        movBien.setDiscriminator(Constantes.DISCRIMINATOR_REGISTRO_MOVIMIENTO_BIEN);
        movBien.setEstado(this.responsableCommand.getEstadoAsignacionPendiente());
        movBien.setFecha(new Date());
        movBien.setObservacion(  Util.getEtiquetas("sigebi.Responsable.mensaje.movimiento") 
                + bien.getIdentificacion().getIdentificacion() + " a "
                + responsableCommand.getUsuarioCommand().getUsuarioSeleccionado()+ ". "
                );
        movBien.setTipo(this.tipoPorDominioValor(Constantes.DOMINIO_PROCESO, Constantes.TIPO_PROCESO_RESPONSABLE));
        movBien.setUsuario(usuarioSIGEBI);
        
        modelRegistroMovim.agregar(movBien);
    }
    
    
    
    private void guardarMovimientoRechazar(Bien bien){
        movBien = new RegistroMovimientoBien();
        
        movBien.setBien(bien);
        movBien.setDiscriminator(Constantes.DISCRIMINATOR_REGISTRO_MOVIMIENTO_BIEN);
        movBien.setEstado(this.responsableCommand.getEstadoAsignacionPendiente());
        movBien.setFecha(new Date());
        movBien.setObservacion(  Util.getEtiquetas("sigebi.Responsable.mensaje.movimientoRechazo") 
                + bien.getIdentificacion().getIdentificacion() + " a "
                + responsableCommand.getUsuarioCommand().getUsuarioSeleccionado()+ ". "
                );
        movBien.setTipo(this.tipoPorDominioValor(Constantes.DOMINIO_PROCESO, Constantes.TIPO_PROCESO_RESPONSABLE_RECHAZAR));
        movBien.setUsuario(usuarioSIGEBI);
        
        modelRegistroMovim.agregar(movBien);
    }
    
    
    
    //</editor-fold>
    
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Mis Bienes">
    
    
    
    private Map<Long, Bien> bienesKeyMisBienes;
    private Boolean misBienesSeleccionados;
    private Boolean visiblePanelConfirmacionMisBienes;
    private void cargoMisDatos(){
        misBienes = new ArrayList<Bien>();
        
        misBienes = modelBien.listarMisBienes( 1
                                            , 70
                                            , unidadEjecutora
                                            , ""

                                            , ""
                                            , ""
                                            , ""
                                            , ""
                                            , null

                                            , usuarioSIGEBI);
        
        
        bienesKeyMisBienes = new HashMap<Long, Bien>();
        misBienesSeleccionados = false;
        visiblePanelConfirmacionMisBienes = false;
        
    }
    
    public String getMiUsuario() {
        miUsuario = usuarioSIGEBI.getId() + " ~ " + usuarioSIGEBI.getNombreCompleto();
        return miUsuario;
    }
    
    public void checkSeleccionarBien(ValueChangeEvent pEvent){
        try{
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            Bien bienSel = (Bien) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if (bienesKeyMisBienes.containsKey(bienSel.getId())) {
                bienesKeyMisBienes.remove(bienSel.getId());
            } else {
                //bienSel.setEstadoAsignacion(responsableCommand.getEstadoAsignacionAsignado());
                bienesKeyMisBienes.put(bienSel.getId(), bienSel);
            }
            misBienesSeleccionados = bienesKeyMisBienes.size() > 0;

        }catch(Exception err){
            
        }
    }
    
    @Resource
    AsignarResponsableHistoricoModel histModel;
    Boolean rechazarBienes;
    public void confirmaAceptarBienes(){
        try{
            List<Bien> modifBienes = new ArrayList<Bien>();
            for(Bien bienSel : new ArrayList<Bien>(bienesKeyMisBienes.values())){
                
                if(!rechazarBienes){
                    bienSel.setEstadoAsignacion(responsableCommand.getEstadoAsignacionAsignado());
                    modifBienes.add(bienSel);

                    // Guardamos la asignación en un histórico
                    AsignarResponsableHistorico hist = new AsignarResponsableHistorico();
                    hist.setBien(bienSel);
                    hist.setResponsable(bienSel.getUsuarioResponsable());
                    hist.setUnidadEjecutora(unidadEjecutora);
                    hist.setFechaDesde(new Date());
                    histModel.guargar(hist);
                    
                }
                else{
                    
                    bienSel.setEstadoAsignacion(null);
                    bienSel.setUsuarioResponsable(null);
                    modifBienes.add(bienSel);
                    this.guardarMovimientoRechazar(bienSel);
                    
                }
            }
            
            modelBien.actualizar(modifBienes);
            cargoMisDatos();
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Responsable.exito.Guardar"));
            
        }
        catch(Exception err){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.Responsable.error.Guardar"));
        }
    }
    
    public void aceptarBienes(){
        try{
            rechazarBienes = false;
            responsableCommand.setMensajeConfirmar(Util.getEtiquetas("sigebi.Responsable.Confirma.Aceptar"));
            visiblePanelConfirmacionMisBienes = true;
        }
        catch(Exception err){
            
        }
    }
    
    public void cerrarPanelMisBienesConfirmar(){
        try{
            visiblePanelConfirmacionMisBienes = false;
        }
        catch(Exception err){
            
        }
    }
    
    
    public void rechazarBienes(){
        try{
            rechazarBienes = true;
            responsableCommand.setMensajeConfirmar(Util.getEtiquetas("sigebi.Responsable.Confirma.Rechaza"));
            visiblePanelConfirmacionMisBienes = true;
        }
        catch(Exception err){
            
        }
    }
    
    public Boolean getMisBienesSeleccionados() {
        return misBienesSeleccionados;
    }
    
    
    public Boolean getVisiblePanelConfirmacionMisBienes() {
        return visiblePanelConfirmacionMisBienes;
    }

    public void setVisiblePanelConfirmacionMisBienes(Boolean visiblePanelConfirmacionMisBienes) {
        this.visiblePanelConfirmacionMisBienes = visiblePanelConfirmacionMisBienes;
    }


    
    //</editor-fold>

    
}
