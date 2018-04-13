/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.UbicacionCommand;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.models.UbicacionModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.sigebi.utils.NodoSIGEBI;
import cr.ac.ucr.sigebi.utils.TreeSIGEBI;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerUbicacion")
@Scope("session")
public class UbicacionController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private UbicacionModel ubicacionModel;

    // Se usan en el jsp
    boolean modificarUbicacion = false;
    boolean agregarUbicacion = false;

    UbicacionCommand ubicacionCommand;

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public boolean isModificarUbicacion() {
        return modificarUbicacion;
    }

    public void setModificarUbicacion(boolean modificarUbicacion) {
        this.modificarUbicacion = modificarUbicacion;
    }

    public boolean isAgregarUbicacion() {
        return agregarUbicacion;
    }

    public void setAgregarUbicacion(boolean agregarUbicacion) {
        this.agregarUbicacion = agregarUbicacion;
    }

    public UbicacionCommand getUbicacionCommand() {
        return ubicacionCommand;
    }

    public void setUbicacionCommand(UbicacionCommand ubicacionCommand) {
        this.ubicacionCommand = ubicacionCommand;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public UbicacionController() {
        super();
        this.modificarUbicacion = false;
        this.agregarUbicacion = false;
    }

    @PostConstruct
    public void Inicializar() {
        ubicacionCommand = new UbicacionCommand();
        this.cargaDatosGenerales();
        this.buscar();
    }

    private void cargaDatosGenerales() {

    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Opciones pantalla">
    public void seleccionarNodo(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        //Se busca el nodo seleccionado
        NodoSIGEBI nodoSIGEBI = (NodoSIGEBI) event.getComponent().getAttributes().get("nodoSeleccionado");

        if (nodoSIGEBI.getObject() != null) {
            //Se busca las unidades
            List<Ubicacion> resultado = ubicacionModel.listarUbicacionPadre(((Ubicacion) nodoSIGEBI.getObject()));
            nodoSIGEBI.agregarNodos(new ArrayList<Object>(resultado), "detalle");

            //Se selecciona el nodo 
            ubicacionCommand.getTreeSIGEBI().setNodoSeleccionado(nodoSIGEBI);

            //Se asigna la ubicacion y ubicacion padre
            Ubicacion ubicacion = ((Ubicacion) nodoSIGEBI.getObject());
            ubicacionCommand.setUbicacion(ubicacion);
            ubicacionCommand.setUbicacionPadre(ubicacion.getPertenece());

            this.cargaDatosGenerales();

            modificarUbicacion = true;
            agregarUbicacion = false;


        } else {
            modificarUbicacion = false;
            agregarUbicacion = false;
            this.buscar();
        }
    }

    public void nuevoRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        //Se busca el nodo seleccionado
        NodoSIGEBI nodoSIGEBI = (NodoSIGEBI) event.getComponent().getAttributes().get("nodoSeleccionado");
        if (nodoSIGEBI.getObject() != null) {
        
            //Se selecciona el nodo 
            ubicacionCommand.getTreeSIGEBI().setNodoSeleccionado(nodoSIGEBI);

            //Se asigna la ubicacion y ubicacion padre
            Ubicacion ubicacion = ((Ubicacion) nodoSIGEBI.getObject());
            ubicacionCommand.setUbicacion(new Ubicacion());
            ubicacionCommand.setUbicacionPadre(ubicacion);

            this.cargaDatosGenerales();            
        }else{
            ubicacionCommand.setUbicacion(new Ubicacion());
            ubicacionCommand.setUbicacionPadre(new Ubicacion());            
        }
        modificarUbicacion = false;
        agregarUbicacion = true;
        this.cargaDatosGenerales();            
    }

    /**
     * Almacena la informacion
     */
    public void guardar() {
        try {
            if (validarForm()) {

                if (ubicacionCommand.getUbicacionPadre() != null && (ubicacionCommand.getUbicacionPadre().getId() != null && ubicacionCommand.getUbicacionPadre().getId() > 0)) {
                    ubicacionCommand.getUbicacion().setPertenece(ubicacionCommand.getUbicacionPadre());
                }else{
                    ubicacionCommand.getUbicacion().setPertenece(null);
                }
                if (agregarUbicacion) {
                    ubicacionCommand.getUbicacion().setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_GENERAL, Constantes.ESTADO_GENERAL_ACTIVO));
                    ubicacionCommand.getUbicacion().setUnidadEjecutora(this.unidadEjecutora);
                }
                ubicacionModel.almacenar(ubicacionCommand.getUbicacion());
                modificarUbicacion = false;
                agregarUbicacion = false;
                
                this.buscar();  

                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.controllerUbicacion.modificado.exitosamente"));
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerUbicacion.guardarDatos"));
        }
    }

    public boolean validarForm() {

        Ubicacion ubicacion = ubicacionCommand.getUbicacion();

        if (ubicacion.getDetalle().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerUbicacion.descripcion.requerido"));
            return false;
        }

        return true;
    }

    /**
     * Cambia el valor de alguno de los filtros
     *
     * @param pEvent
     */
    public void cambioFiltro(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            this.buscar();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerUbicacion.cambioFiltro"));
        }

    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Paginacion">
    private void buscar() {

        // Se cuenta la cantidad 
        this.setPrimerRegistro(1);
        this.contar();

        //Se consulta la lista 
        this.listar();
    }

    /**
     * Contabiliza las tomas fisicas unitaras
     */
    private void contar() {
        try {

            //Se cuenta la cantidad de registros
            Long contador = ubicacionModel.contar(ubicacionCommand.getFltDescripcionUbicacion());

            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerUbicacion.contar"));
        }
    }

    /**
     * Lista las tomas fisicas unitarias
     */
    private void listar() {
        try {

            List<Ubicacion> resultado = ubicacionModel.listar(ubicacionCommand.getFltDescripcionUbicacion(), this.getPrimerRegistro() - 1, this.getUltimoRegistro());

            //Se crea el tree que se utiliza en la pantalla
            ubicacionCommand.setTreeSIGEBI(new TreeSIGEBI(new ArrayList<Object>(resultado), "Ubicaciones", "detalle"));

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerUbicacion.listar"));
        }
    }

    /**
     * Pasa a la pagina sub-set
     *
     * @param pEvent
     */
    public void irPagina(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPag"));
        this.getPrimerRegistroPagina(numeroPagina);
        this.listar();
    }

    /**
     * Pasa al siguiente sub-set
     *
     * @param pEvent
     */
    public void siguiente(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getSiguientePagina();
        this.listar();
    }

    /**
     * Pasa al anterior sub-set
     *
     * @param pEvent
     */
    public void anterior(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPaginaAnterior();
        this.listar();
    }

    /**
     * Pasa al primero sub-set
     *
     * @param pEvent
     */
    public void primero(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setPrimerRegistro(1);
        this.listar();
    }

    /**
     * Pasa al ultimo sub-set
     *
     * @param pEvent
     */
    public void ultimo(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.getPrimerRegistroUltimaPagina();
        this.listar();
    }

    /**
     * Cambia la cantidad de registros por página
     *
     * @param pEvent
     */
    public void cambioRegistrosPorPagina(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));
        this.setPrimerRegistro(1);
        this.contar();
        this.listar();
    }
    //</editor-fold>

}
