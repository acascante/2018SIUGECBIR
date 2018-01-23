/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.BienCommand;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.Moneda;
import cr.ac.ucr.sigebi.domain.Proveedor;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.models.CategoriaModel;
import cr.ac.ucr.sigebi.models.ClasificacionModel;
import cr.ac.ucr.sigebi.models.MonedaModel;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.LoteModel;
import cr.ac.ucr.sigebi.models.TipoModel;
import cr.ac.ucr.sigebi.utils.Constantes;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
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
@Controller(value = "controllerBienes")
@Scope("session")
public class BienController extends BaseController {
    
    //<editor-fold defaultstate="collapsed" desc="Variables de la Clase">
    List<SelectItem> tiposBienOptions;
    List<SelectItem> origenesBienOptions;
    List<SelectItem> lotesOptions;
    List<SelectItem> categoriasOptions;
    List<SelectItem> clasificacionesOptions;
    List<SelectItem> monedasOptions;
    
    List<SelectItem> subClasificacionesOptions;
    List<SelectItem> subCategoriasOptions;
    
    @Resource private TipoModel tipoModel;
    @Resource private LoteModel loteModel;
    @Resource private CategoriaModel categoriaModel;
    @Resource private ClasificacionModel clasificacionModel;
    @Resource private MonedaModel monedaModel;
    @Resource private BienModel bienModel;
    
    String mensaje;
    String mensajeNota;
    
    float tipoCambioDollar = 570;
    float tipoCambioEuro = 640;
    
    String mensajeExito;
    boolean bienRegistrado;

    private BienCommand command;
    //</editor-fold>
    

    //<editor-fold defaultstate="collapsed" desc="Inicializa Datos">
    public BienController() {
        super();
        command = new BienCommand();
        cargarCombos();
    }

    private void cargarCombos() {
        try {
            List<Tipo> tipos = tipoModel.listarPorDominio(Constantes.DOMINIO_BIEN);
            if (!tipos.isEmpty()) {
                tiposBienOptions = new ArrayList<SelectItem>();
                for (Tipo item : tipos) {
                    tiposBienOptions.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Tipo> tiposOrigen = tipoModel.listarPorDominio(Constantes.DOMINIO_ORIGEN);
            if (!tiposOrigen.isEmpty()) {
                origenesBienOptions = new ArrayList<SelectItem>();
                for (Tipo item : tiposOrigen) {
                    origenesBienOptions.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Lote> lotes = loteModel.listar();
            if (!lotes.isEmpty()) {
                lotesOptions = new ArrayList<SelectItem>();
                for (Lote item : lotes) {
                    lotesOptions.add(new SelectItem(item.getId(), item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Categoria> categorias = categoriaModel.listar();
            if (!categorias.isEmpty()) {
                categoriasOptions = new ArrayList<SelectItem>();
                for (Categoria item : categorias) {
                    categoriasOptions.add(new SelectItem(item.getId(), item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Clasificacion> clasificaciones = clasificacionModel.listar();
            if (!clasificaciones.isEmpty()) {
                clasificacionesOptions = new ArrayList<SelectItem>();
                for (Clasificacion item : clasificaciones) {
                    clasificacionesOptions.add(new SelectItem(item.getId(), item.getNombre()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
            
            List<Moneda> monedas = monedaModel.listar();
            
            if (!monedas.isEmpty()) {
                monedasOptions = new ArrayList<SelectItem>();
                for (Moneda item : monedas) {
                    monedasOptions.add(new SelectItem(item.getId(), item.getDescripcion()));  // ID + Nombre -- Usado para combo de filtro para enviar el ID al Dao para la consulta
                }
            }
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    private Double getValorColones() {
        //TODO como se va a manejar el tipo de cambio?
        try {
            if (command.getMoneda().getId() == 2L) {
                return command.getCosto() * tipoCambioDollar;
            }
            if (command.getMoneda().getId() == 3L) {
                return command.getCosto() * tipoCambioEuro;
            }
        } catch (Exception err) {
            mensaje = err.getMessage();

        }
        return command.getCosto();
    }

    public void guardarDatos() {
        try {
            if (prepararBien()) {
                
            }
               
            if (mensaje.equals("")) {
                bienModel.almacenar(command.getBien());
                bienRegistrado = true;
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.Bien.Error.ActualizacionExito"));
            }
            else {
                Mensaje.agregarErrorAdvertencia(mensaje);
            }
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    public void registrarNuevoBien() {
        try {
            Integer idPlaca = buscarPlaca();
            if (idPlaca > 0) {
                Identificacion placa = new Identificacion();
                placa.setId(idPlaca);
                command.setIdentificacion(placa);
            } else {
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.Placa");
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public boolean prepararBien() {
        try {
            
            //Descripción
            if(command.getDescripcion().length() < 4){
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.IngresoDescripcion");
                return false;
            }

            //TODO Monto Capitalizable
//            if (getValorColones() > montoCapitalizable) {
//                command.setCapitalizable(true);
//            } else {
//                command.setCapitalizable(false);
//            }

            //Validar Cantidad
            if (!(command.getCantidad() > 0)) {
                mensaje = Util.getEtiquetas("sigebi.Bien.Error.Cantidad");
                return false;
            }
            return true;
        } catch (Exception err) {
            mensaje = err.getMessage();
            return false;
        }
    }

    public int buscarPlaca() {
        // TODO Como se va a manejar la asignacion de placas
        return 1;
    }
    
    public String getMensajeExito() {
        return mensajeExito;
    }

    public void setMensajeExito(String mensajeExito) {
        this.mensajeExito = mensajeExito;
    }

    public boolean isBienRegistrado() {
        return bienRegistrado;
    }

    public void setBienRegistrado(boolean bienRegistrado) {
        this.bienRegistrado = bienRegistrado;
    }

    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Navegación del MENÚ">
    public void abrirDetalle(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            ViewBienEntity item = (ViewBienEntity) pEvent.getComponent().getAttributes().get("tipoSeleccionado");
            this.vistaOrigen = "reg_manual";
            
            this.abrirDetalle(item.getIdBien());
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    public void abrirDetalleSincronizar(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            ViewBienEntity item = (ViewBienEntity) pEvent.getComponent().getAttributes().get("tipoSeleccionado");
            this.vistaOrigen = "sincronizar";
            
            this.abrirDetalle(item.getIdBien());
            Util.navegar("bien_nuevo");


        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }
    
    private void abrirDetalle(Integer idBien) {
        try{
        this.vistaOrigen = "reg_manual";

        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }
    
    public void nuevoRegistro(ActionEvent pEvent) {
        try{
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            this.vistaOrigen = "reg_manual";
            Util.navegar("bien_nuevo");
        } catch (Exception err) {
            mensaje = err.getMessage();
        }
    }

    public void regresarListado() {
        if(vistaOrigen != null){
            Util.navegar(vistaOrigen);
        }else{
            Util.navegar("reg_manual");
        }
    }

    //</editor-fold>
    
    
    public void cambioSelectCategoria(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        // Obtengo el valor seleccionado
        String valor = event.getNewValue().toString();
    }

    public void cambioSelectSubCategoria(ValueChangeEvent event) {

        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
    }

    public void cambioSelectClasificacion(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
    }

    public void cargaComboSubCategoria(String valor) {
        
    }

    public void cargaComboClasificacion(String valor) {
        
    }

    public void cargaComboSubClasificacion(String valor) {

    }

    boolean disableComboClasificacion;
    boolean disableComboSubClasificacion;

    public boolean isDisableComboClasificacion() {
        return disableComboClasificacion;
    }

    public void setDisableComboClasificacion(boolean disableComboClasificacion) {
        this.disableComboClasificacion = disableComboClasificacion;
    }

    public boolean isDisableComboSubClasificacion() {
        return disableComboSubClasificacion;
    }

    public void setDisableComboSubClasificacion(boolean disableComboSubClasificacion) {
        this.disableComboSubClasificacion = disableComboSubClasificacion;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Panel Observacion Sincronizar o Rechazar">
    
    boolean panelObservaVisible = false;
    boolean accionSincronizar = false;
    boolean accionRechazar = false;
    String observacionCliente = "";
    
    public void rechazarBien() {
        if (this.observacionCliente == null || this.observacionCliente.isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.rechazarBien.sin.observacion"));
        } else {
            Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
           // bienMod.cambiaEstadoBien(this.bien, estadoModel.obtenerPorEstado(Constantes.DOMINIO_ESTADO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE), observacionCliente, telefono);
            observacionCliente = "";
            //Se oculta el panel
            this.cerrarPanelObserva();
        }
    }

    public void solicitarSincronizacion() {
        Integer telefono = lVistaUsuario.getgUsuarioActual().getTelefono1() != null ? Integer.parseInt(lVistaUsuario.getgUsuarioActual().getTelefono1()) : 0;
        //bienMod.cambiaEstadoBien(this.bien, estadoModel.buscarPorDominioEstado(Constantes.DOMINIO_ESTADO_BIEN, Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR), observacionCliente, telefono);
        //Se oculta el panel
        this.cerrarPanelObserva();

    }
    
    public boolean mostrarPanelObserva(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return false;
        }

        //Se presenta el panel de obervacion
        panelObservaVisible = true;
        return true;
    }

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
    //</editor-fold>
}