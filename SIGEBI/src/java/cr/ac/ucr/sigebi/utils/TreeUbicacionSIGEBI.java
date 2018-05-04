package cr.ac.ucr.sigebi.utils;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.PaginacionOracle;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.models.UbicacionModel;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

public class TreeUbicacionSIGEBI extends TreeSIGEBI {

    //<editor-fold defaultstate="collapsed" desc="Artibutos>
    private UbicacionModel ubicacionModel;

    private PaginacionOracle paginacionOracle;

    private String fltDescripcion;

    private UnidadEjecutora fltUnidadEjecutora;

    private Boolean presentaPanelUbicacion;

    private Ubicacion ubicacion;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    
    public PaginacionOracle getPaginacionOracle() {
        return paginacionOracle;
    }

    public UnidadEjecutora getFltUnidadEjecutora() {
        return fltUnidadEjecutora;
    }

    public void setFltUnidadEjecutora(UnidadEjecutora fltUnidadEjecutora) {
        this.fltUnidadEjecutora = fltUnidadEjecutora;
    }

    public void setPaginacionOracle(PaginacionOracle paginacionOracle) {
        this.paginacionOracle = paginacionOracle;
    }

    public String getFltDescripcion() {
        return fltDescripcion;
    }

    public void setFltDescripcion(String fltDescripcion) {
        this.fltDescripcion = fltDescripcion;
    }

    public Boolean getPresentaPanelUbicacion() {
        return presentaPanelUbicacion;
    }

    public void setPresentaPanelUbicacion(Boolean presentaPanelUbicacion) {
        this.presentaPanelUbicacion = presentaPanelUbicacion;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public UbicacionModel getUbicacionModel() {
        return ubicacionModel;
    }

    public void setUbicacionModel(UbicacionModel ubicacionModel) {
        this.ubicacionModel = ubicacionModel;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores y metodos">

    public TreeUbicacionSIGEBI() {
        fltUnidadEjecutora = null;
    }

    public TreeUbicacionSIGEBI(UnidadEjecutora unidadEjecutora) {
        fltUnidadEjecutora = unidadEjecutora;
    }
    
    public void abrirPantalla() {
        
        this.inicializa(null, "Ubicaciones Principales", "detalle");
        
        //Se inicializa el objeto paginacion
        this.paginacionOracle = new PaginacionOracle();

        ArrayList<SelectItem> cantPorPaginas = new ArrayList<SelectItem>();
        cantPorPaginas.add(new SelectItem(5, "5"));
        cantPorPaginas.add(new SelectItem(10, "10"));
        cantPorPaginas.add(new SelectItem(25, "25"));
        cantPorPaginas.add(new SelectItem(50, "50"));
        this.paginacionOracle.setListaRegistrosPagina(cantPorPaginas);
        
        this.ubicacion = new Ubicacion();
        
        this.buscar();

        this.presentaPanelUbicacion = true;
    }

    public void cerrarPantalla() {
        this.presentaPanelUbicacion = false;
    }

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

        } else {
            
            this.buscar();
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Paginacion y busquedas">
    
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
    
    private void buscar() {

        // Se cuenta la cantidad 
        paginacionOracle.setPrimerRegistro(1);
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
            Long contador = ubicacionModel.contar(fltDescripcion, fltUnidadEjecutora);

            //Se actualiza la cantidad de registros segun los filtros
            paginacionOracle.setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerSeleccionaUbicacion.contar"));
        }
    }

    /**
     * Lista las tomas fisicas unitarias
     */
    private void listar() {
        try {

            List<Ubicacion> resultado = ubicacionModel.listar(fltDescripcion, fltUnidadEjecutora, paginacionOracle.getPrimerRegistro() - 1, paginacionOracle.getUltimoRegistro());

            //Se crea el tree que se utiliza en la pantalla
            this.asignaObjetos(new ArrayList<Object>(resultado));

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerSeleccionaUbicacion.listar"));
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
        paginacionOracle.getPrimerRegistroPagina(numeroPagina);
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
        paginacionOracle.getSiguientePagina();
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
        paginacionOracle.getPaginaAnterior();
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
        paginacionOracle.setPrimerRegistro(1);
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
        paginacionOracle.getPrimerRegistroUltimaPagina();
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
        paginacionOracle.setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));
        paginacionOracle.setPrimerRegistro(1);
        this.contar();
        this.listar();
    }

    //</editor-fold>
    
}
