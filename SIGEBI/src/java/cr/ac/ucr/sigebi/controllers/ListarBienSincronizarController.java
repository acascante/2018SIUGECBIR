/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.models.UnidadEjecutoraModel;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerListarBienSincronizar")
@Scope("session")
public class ListarBienSincronizarController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private BienModel bienMod;

    @Resource
    private EstadoModel estadoModel;

    @Resource
    private UnidadEjecutoraModel unidadModel;

    @Resource
    EstadoModel modelEstado;

    // Lista de los Bienes
    List<Bien> bienes;

    // Filtros para listar los Bienes 
    String fltIdBien = "";
    String fltDescripcion = "";
    String fltMarca = "";
    String fltModelo = "";
    String fltSerie = "";
    String fltEstado = "-1";

    // comboBox subCategorias
    ArrayList<Estado> estadosFiltros;
    List<SelectItem> estadosOptions;

    Map<Long, Bien> bienesPorSincronizar;
    Map<Long, Bien> bienesPorRechazar;
    Map<Long, Bien> bienesEnviarSincronizar;

    boolean sincronizar;
    boolean panelObservaVisible = false;
    String observacionCliente = "";

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public boolean cerrarPanelObserva() {
        panelObservaVisible = false;
        return false;
    }

    public boolean isPanelObservaVisible() {
        return panelObservaVisible;
    }

    public void setPanelObservaVisible(boolean panelObservaVisible) {
        this.panelObservaVisible = panelObservaVisible;
    }

    public String getObservacionCliente() {
        return observacionCliente;
    }

    public void setObservacionCliente(String observacionCliente) {
        this.observacionCliente = observacionCliente;
    }

    public boolean isSincronizar() {
        return (bienesEnviarSincronizar.isEmpty());
    }

    public void setSincronizar(boolean sincronizar) {
        this.sincronizar = sincronizar;
    }

    public BienModel getBienMod() {
        return bienMod;
    }

    public void setBienMod(BienModel bienMod) {
        this.bienMod = bienMod;
    }

    public EstadoModel getEstadoModel() {
        return estadoModel;
    }

    public void setEstadoModel(EstadoModel estadoModel) {
        this.estadoModel = estadoModel;
    }

    public UnidadEjecutoraModel getUnidadModel() {
        return unidadModel;
    }

    public void setUnidadModel(UnidadEjecutoraModel unidadModel) {
        this.unidadModel = unidadModel;
    }

    public List<Bien> getBienes() {
        return bienes;
    }

    public void setBienes(List<Bien> bienes) {
        this.bienes = bienes;
    }

    public String getFltIdBien() {
        return fltIdBien;
    }

    public void setFltIdBien(String fltIdBien) {
        this.fltIdBien = fltIdBien;
    }

    public String getFltDescripcion() {
        return fltDescripcion;
    }

    public void setFltDescripcion(String fltDescripcion) {
        this.fltDescripcion = fltDescripcion;
    }

    public String getFltMarca() {
        return fltMarca;
    }

    public void setFltMarca(String fltMarca) {
        this.fltMarca = fltMarca;
    }

    public String getFltModelo() {
        return fltModelo;
    }

    public void setFltModelo(String fltModelo) {
        this.fltModelo = fltModelo;
    }

    public String getFltSerie() {
        return fltSerie;
    }

    public void setFltSerie(String fltSerie) {
        this.fltSerie = fltSerie;
    }

    public String getFltEstado() {
        return fltEstado;
    }

    public void setFltEstado(String fltEstado) {
        this.fltEstado = fltEstado;
    }

    public List<SelectItem> getEstadosOptions() {
        return estadosOptions;
    }

    public void setEstadosOptions(List<SelectItem> estadosOptions) {
        this.estadosOptions = estadosOptions;
    }

    public Map<Long, Bien> getBienesPorSincronizar() {
        return bienesPorSincronizar;
    }

    public void setBienesPorSincronizar(Map<Long, Bien> bienesPorSincronizar) {
        this.bienesPorSincronizar = bienesPorSincronizar;
    }

    public Map<Long, Bien> getBienesPorRechazar() {
        return bienesPorRechazar;
    }

    public void setBienesPorRechazar(Map<Long, Bien> bienesPorRechazar) {
        this.bienesPorRechazar = bienesPorRechazar;
    }

    public void setBienesEnviarSincronizar(HashMap<Long, Bien> bienesEnviarSincronizar) {
        this.bienesEnviarSincronizar = bienesEnviarSincronizar;
    }

    public EstadoModel getModelEstado() {
        return modelEstado;
    }

    public void setModelEstado(EstadoModel modelEstado) {
        this.modelEstado = modelEstado;
    }

    public ArrayList<Estado> getEstadosFiltros() {
        return estadosFiltros;
    }

    public void setEstadosFiltros(ArrayList<Estado> estadosFiltros) {
        this.estadosFiltros = estadosFiltros;
    }

    public Map<Long, Bien> getBienesEnviarSincronizar() {
        return bienesEnviarSincronizar;
    }

    public void setBienesEnviarSincronizar(Map<Long, Bien> bienesEnviarSincronizar) {
        this.bienesEnviarSincronizar = bienesEnviarSincronizar;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public ListarBienSincronizarController() {
        super();
    }

    @PostConstruct
    public final void inicializar() {

        // Mapas para los bienese seleccionados en pantalla
        bienesPorSincronizar = new HashMap<Long, Bien>();
        bienesPorRechazar = new HashMap<Long, Bien>();
        bienesEnviarSincronizar = new HashMap<Long, Bien>();

        estadosFiltros = new ArrayList<Estado>();
        estadosFiltros.add(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE));
        estadosFiltros.add(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR));

        estadosOptions = new ArrayList<SelectItem>();
        for (Estado item : estadosFiltros) {
            estadosOptions.add(new SelectItem(item.getId().toString(), item.getNombre()));
        }

        // Se cuenta la cantidad de bienes
        this.setPrimerRegistro(1);
        this.contarBienes();

        //Se consulta la lista de los bienes
        this.listarBienes();

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    /**
     * check bien para sincronizar
     *
     * @param pEvent
     */
    public void checkBienPorSincronizar(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            Bien bienSinco = (Bien) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if (bienesPorSincronizar.containsKey(bienSinco.getId())) {
                bienesPorSincronizar.remove(bienSinco.getId());
            } else {
                bienesPorSincronizar.put(bienSinco.getId(), bienSinco);
            }

        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }

    public void checkBienPorRechazar(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            Bien bienSinco = (Bien) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if (bienesPorRechazar.containsKey(bienSinco.getId())) {
                bienesPorRechazar.remove(bienSinco.getId());
            } else {
                bienesPorRechazar.put(bienSinco.getId(), bienSinco);
            }

        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorRechazar"));
        }
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
            this.contarBienes();

            this.setPrimerRegistro(1);

            this.listarBienes();
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.cambioFiltro"));
        }

    }

    private void contarBienes() {
        try {
            Long contador = 0l;
            if (fltEstado == null || (fltEstado != null && fltEstado.equals("-1"))) {
                contador = bienMod.contar(unidadEjecutora,
                        null,
                        fltIdBien,
                        fltDescripcion,
                        fltMarca,
                        fltModelo,
                        fltSerie,
                        null,
                        null,
                        estadosFiltros.toArray(new Estado[estadosFiltros.size()])
                );
            } else {

                contador = bienMod.contar(unidadEjecutora,
                        null,
                        fltIdBien,
                        fltDescripcion,
                        fltMarca,
                        fltModelo,
                        fltSerie,
                        null,
                        null,
                        this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Integer.parseInt(fltEstado))
                );
            }

            //Se actualiza la cantidad de registros segun los filtros
            this.setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.contarBienes"));
        }
    }

    private void listarBienes() {
        try {
            if (fltEstado == null || (fltEstado != null && fltEstado.equals("-1"))) {
                this.bienes = bienMod.listar(this.getPrimerRegistro() - 1,
                        this.getUltimoRegistro(),
                        unidadEjecutora,
                        null,
                        fltIdBien,
                        fltDescripcion,
                        fltMarca,
                        fltModelo,
                        fltSerie,
                        null,
                        null,
                        estadosFiltros.toArray(new Estado[estadosFiltros.size()])
                );
            } else {
                this.bienes = bienMod.listar(this.getPrimerRegistro() - 1,
                        this.getUltimoRegistro(),
                        unidadEjecutora,
                        null,
                        fltIdBien,
                        fltDescripcion,
                        fltMarca,
                        fltModelo,
                        fltSerie,
                        null,
                        null,
                        this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Integer.parseInt(fltEstado))
                );
            }
            for (Bien bien : this.bienes) {
                bien.setSeleccionado(bienesPorSincronizar.containsKey(bien.getId()) || bienesPorRechazar.containsKey(bien.getId()));
            }
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.listarBienes"));
        }
    }

    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Paginacion">
    /**
     * Pasa a la pagina sub-set de bienes
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
        this.listarBienes();
    }

    /**
     * Pasa al siguiente sub-set de bienes
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
        this.listarBienes();
    }

    /**
     * Pasa al anterior sub-set de bienes
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
        this.listarBienes();
    }

    /**
     * Pasa al primero sub-set de bienes
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
        this.listarBienes();
    }

    /**
     * Pasa al ultimo sub-set de Estudiantes
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
        this.listarBienes();
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
        this.listarBienes();

    }

// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Panel Observacion Solicitud Sincronizar o Rechazar">
    public void rechazarBien() {
        try {
            if (this.observacionCliente == null || this.observacionCliente.isEmpty()) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.rechazarBien.sin.observacion"));
            } else {
                Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;

                bienMod.cambiaEstadoBien(this.bienesPorRechazar.values(), this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE), observacionCliente, telefono);
                this.bienesPorRechazar.clear();

                observacionCliente = "";

                //Se consulta la vista nuevamente
                this.listarBienes();

                //Se oculta el panel
                this.cerrarPanelObserva();
            }
        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.rechazarBien"));
        }
    }

    public void solicitarSincronizacion() {
        if (this.bienesPorSincronizar.isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.solicitarSincronizacion.sin.bienes.sincronizar"));
        } else {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
            bienMod.cambiaEstadoBien(this.bienesPorSincronizar.values(), this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR), observacionCliente, telefono);
            this.bienesPorSincronizar.clear();

            //Se consulta la vista nuevamente
            this.listarBienes();
        }
    }

    public boolean mostrarPanelObserva(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return false;
        }

        if (this.bienesEnviarSincronizar.isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.mostrarPanelObserva.sin.bienes.rechazar"));
            return false;
        } else {
            //Se presenta el panel de obervacion
            panelObservaVisible = true;
            return true;
        }
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Sincronizar">
    public void checkBienEnviarSincronizar(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            Bien bienSincro = (Bien) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if (bienesEnviarSincronizar.containsKey(bienSincro.getId())) {
                bienesEnviarSincronizar.remove(bienSincro.getId());
            } else {
                bienesEnviarSincronizar.put(bienSincro.getId(), bienSincro);
            }

        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienEnviarSincronizar"));
        }
    }

    public void sincronizarTodo(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            if (bienesEnviarSincronizar.isEmpty()) {
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.sincronizarBien.Lista.ListaVacia"));
                return;
            }

            for (Map.Entry<Long, Bien> bienSincro : bienesEnviarSincronizar.entrySet()) {
                Bien bien = bienMod.buscarPorId(bienSincro.getKey());
                bien.setEstado(this.estadoPorDominioValor(Constantes.DOMINIO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE_ACTIVACION));
                bienMod.sincronizarBien(bien, lVistaUsuario.getgUsuarioActual().getIdUsuario());
                bienesEnviarSincronizar.remove(bienSincro.getKey());
            }
            bienesPorSincronizar.clear();
            Mensaje.agregarInfo(Util.getEtiquetas("sigebi.sincronizarBienes.Exito"));

            //Actualiza la lista
            this.listarBienes();

        } catch (FWExcepcion err) {
            Mensaje.agregarErrorAdvertencia(err.getError_para_usuario());
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err,Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.sincronizarTodo"));
        }
    }

    //</editor-fold>
}
