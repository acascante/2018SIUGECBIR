/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.seguridad.entidades.SegUnidadEjecutora;
import cr.ac.ucr.framework.seguridad.entidades.SegUsuario;
import cr.ac.ucr.framework.vista.VistaUsuario;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.EstadoModel;
import cr.ac.ucr.sigebi.models.VistaBienes;
import cr.ac.ucr.sigebi.entities.BienEntity;
import cr.ac.ucr.sigebi.entities.EstadoEntity;
import java.util.ArrayList;
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
@Controller(value = "controllerListados")
@Scope("session")
public class listadosController {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    // Variables Locales
    VistaUsuario lVistaUsuario;
    int unidadEjecutora;
    String nombreUnidad;
    String codPersonaReg;
    String usuarioRegistrado;

    String error;

    @Resource
    private BienModel bienMod;

    // Lista de los Bienes
    List<VistaBienes> bienes;

    // Filtros para listar los Bienes 
    String fltPlaca;
    String fltDescripcion;
    String fltMarca;
    String fltModelo;
    String fltSerie;
    String fltEstado;

    // comboBox subCategorias
    List<SelectItem> estadosOptions;

    @Resource
    private EstadoModel estadoModel;

    //</editor-fold>
    

    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public int getPagina() {
        return paginaActual;
    }

    public void setPagina(int pagina) {
        this.paginaActual = pagina;
    }

    public int getCatRegistros() {
        return cantRegistros;
    }

    public void setCatRegistros(int cantRegistros) {
        this.cantRegistros = cantRegistros;
    }

    public List<SelectItem> getEstadosOptions() {
        return estadosOptions;
    }

    public void setEstadosOptions(List<SelectItem> estadosOptions) {
        this.estadosOptions = estadosOptions;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public List<VistaBienes> getBienes() {
        return bienes;
    }

    public void setBienes(List<VistaBienes> bienes) {
        this.bienes = bienes;
    }

    public String getFltIdBien() {
        return fltPlaca;
    }

    public void setFltIdBien(String fltIdBien) {
        this.fltPlaca = fltIdBien;
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

    //</editor-fold>
    

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public listadosController() {
    }

    //</editor-fold>
    

    //<editor-fold defaultstate="collapsed" desc="Metodos">
    @PostConstruct
    private void incializaBienes() {
        try {
            lVistaUsuario = (VistaUsuario) Util.obtenerVista("#{vistaUsuario}");

            //Obtener Unidad Ejecutora
            SegUnidadEjecutora unidad = lVistaUsuario.getgUnidadActual();
            unidadEjecutora = unidad.getUnidadEjecutoraLlave().getIdUnidadEjecutora();
            nombreUnidad = unidad.getDscUnidadEjecutora();

            //Obtener Usuario
            SegUsuario usuario = lVistaUsuario.getgUsuarioActual();
            codPersonaReg = usuario.getIdUsuario();
            usuarioRegistrado = usuario.getNombre_completo();

            //Cargar Estados
            List<Estado> estados;
            estados = estadoModel.listar();
            estadosOptions = new ArrayList<SelectItem>();
            for (Estado item : estados) {
                estadosOptions.add(new SelectItem(item.getIdEstado().toString(), item.getNombre()));
            }
            fltEstado = "NA";
            fltPlaca = "";
            fltDescripcion = "";
            fltMarca = "";
            fltModelo = "";
            fltSerie = "";

            paginaActual = 1;
            cantRegMostrar = 5;

            
            bienesPorSincronizar = new HashMap<Integer, VistaBienes>();
            
            consultarCantidadRegistros();
            consultarBienes();

        } catch (Exception err) {
            error = err.getMessage();
        }
    }

    public void cambioFiltro(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        try {

            if (fltEstado.equals("-1")) {
                fltEstado = "NA";
            }

            if (fltEstado == null) {
                fltEstado = "NA";
            }

            consultarCantidadRegistros();

            consultarBienes();

        } catch (Exception err) {
            error = err.getMessage();
        }
    }

    private void consultarBienes() {
        try {
            bienes = new ArrayList<VistaBienes>();

            bienes = bienMod.traerConFiltros(unidadEjecutora,
                     fltPlaca,
                     fltDescripcion,
                     fltMarca,
                     fltModelo,
                     fltSerie,
                     fltEstado,
                     paginaActual,
                     cantRegMostrar
            );
            validarPagina();
        } catch (Exception err) {
            error = err.getMessage();
        }

    }

    
    //</editor-fold>
    

    //<editor-fold defaultstate="collapsed" desc="PAGINACIÓN">
    String selCantRegistros;
    int paginaActual;
    int cantRegistros;
    int cantRegMostrar;
    int canPaginas;
    boolean esPrimeraPagina;
    boolean esUltimaPagina;

    

    
    
    public void consultarCantidadRegistros() {
        try {
            cantRegistros = bienMod.consultaCantidadRegistros(unidadEjecutora,
                     fltPlaca,
                     fltDescripcion,
                     fltMarca,
                     fltModelo,
                     fltSerie,
                     fltEstado);

            canPaginas = cantRegistros / cantRegMostrar;
            int residuo = cantRegistros % cantRegMostrar;
            validarPagina();
            if (residuo > 0) {
                canPaginas++;
            }
        } catch (Exception err) {

        }

    }

    public void validarPagina() {

        esPrimeraPagina = (paginaActual == 1);
        esUltimaPagina = (paginaActual == canPaginas);

    }

    public void cambioCantRegistros(ValueChangeEvent event) {
        try{
            
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            // Obtyengo el valor seleccionado
            cantRegMostrar = Integer.parseInt(event.getNewValue().toString());

            consultarCantidadRegistros();
            consultarBienes();
        }catch(Exception err){
            error = err.getMessage();
        }
    }

    public void paginaPrimera(ActionEvent pEvent) {
        try {
            paginaActual = 1;
            consultarBienes();
        } catch (Exception err) {

        }
    }

    public void paginaAnterior(ActionEvent pEvent) {
        try {
            paginaActual = paginaActual-1;
            consultarBienes();
        } catch (Exception err) {

        }
    }

    public void paginaSiguiente(ActionEvent pEvent) {
        try {
            paginaActual = paginaActual+1;
            consultarBienes();
        } catch (Exception err) {

        }
    }

    public void paginaUltima(ActionEvent pEvent) {
        try {
            paginaActual = canPaginas;
            consultarBienes();
        } catch (Exception err) {

        }
    }

    public String getSelCantRegistros() {
        return selCantRegistros;
    }

    public void setSelCantRegistros(String selCantRegistros) {
        this.selCantRegistros = selCantRegistros;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public int getCantRegistros() {
        return cantRegistros;
    }

    public void setCantRegistros(int cantRegistros) {
        this.cantRegistros = cantRegistros;
    }

    public int getCanPaginas() {
        return canPaginas;
    }

    public void setCanPaginas(int canPaginas) {
        this.canPaginas = canPaginas;
    }

    public boolean isEsPrimeraPagina() {
        return esPrimeraPagina;
    }

    public void setEsPrimeraPagina(boolean esPrimeraPagina) {
        this.esPrimeraPagina = esPrimeraPagina;
    }

    public boolean isEsUltimaPagina() {
        return esUltimaPagina;
    }

    public void setEsUltimaPagina(boolean esUltimaPagina) {
        this.esUltimaPagina = esUltimaPagina;
    }

    //</editor-fold>
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Sincronizar">
    // Mapas para los bienese seleccionados en pantalla
    HashMap<Integer, VistaBienes> bienesPorSincronizar;// = new HashMap<Integer, VistaBienes>();
    
    int estadoPendiente = 3;
    boolean sincronizar;

    public boolean isSincronizar() {
        return (bienesPorSincronizar.size() > 0);
    }

    public void setSincronizar(boolean sincronizar) {
        this.sincronizar = sincronizar;
    }
    
    @Resource
    EstadoModel modelEstado;
    
    public void checkBienPorSincronizar(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            VistaBienes bienSincro = (VistaBienes) pEvent.getComponent().getAttributes().get("bienSeleccionado");
            if(bienesPorSincronizar.containsKey(bienSincro.getIdBien())){
                bienesPorSincronizar.remove(bienSincro.getIdBien());
                Mensaje.agregarInfo("Se agregó el bien tamaño " + bienesPorSincronizar.size() );
                bienSincro.setSeleccionado(true);
            }else{
                bienesPorSincronizar.put(bienSincro.getIdBien(), bienSincro);
                Mensaje.agregarInfo("Se agregó el bien tamaño " + bienesPorSincronizar.size() );
                bienSincro.setSeleccionado(false);
            }
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }

    public void sincronizarTodo(ActionEvent pEvent) {
        try{
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            // TODO revisar dominio de este ID
            //EstadoEntity estadoSinc = modelEstado.obtenerPorId(4);
            String resp = "";
            for(Map.Entry<Integer, VistaBienes> bienSincro : bienesPorSincronizar.entrySet()){
                BienEntity bien = bienMod.traerPorId(bienSincro.getKey());
                //bien.setIdEstado(estadoSinc);
                resp += sincronizar(bien);
            }
            bienesPorSincronizar.clear();
            if(resp.equals("")){
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.sincronizarBienes.Exito"));
            }
            else{
                Mensaje.agregarErrorAdvertencia(resp/*Util.getEtiquetas("sigebi.sincronizarBienes.Error")*/);
            }
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerListarBienSincronizar.checkBienPorSincronizar"));
        }
    }

    public void sincronizarBien(ActionEvent pEvent){
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }
            
            VistaBienes bienSincro = (VistaBienes) pEvent.getComponent().getAttributes().get("bienSincronizar");
            
            //TODO revisar cual es el dominio de este estado
            //EstadoEntity estadoSinc = modelEstado.obtenerPorId(4);
            
            BienEntity bien = bienMod.traerPorId(bienSincro.getIdBien());
            
            //bien.setIdEstado(estadoSinc);
            
            String resp = sincronizar(bien);
            
            if(resp.equals("")){
                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.sincronizarBien.Exito"));
            }
            else{
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.sincronizarBien.Error"));
            }
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err.getMessage());
        }
    }
    
    private String sincronizar(BienEntity bien){
        try
        {
            return bienMod.sincronizarBien(bien, lVistaUsuario.getgUsuarioActual().getIdUsuario());
        }
        catch(NullPointerException err)
        {
            return "Error por valor nulo.";
        }
        catch(Exception err){
            return err.getMessage();
        }
    } 
    
    public HashMap<Integer, VistaBienes> getBienesPorSincronizar() {
        return bienesPorSincronizar;
    }

    public void setBienesPorSincronizar(HashMap<Integer, VistaBienes> bienesPorSincronizar) {
        this.bienesPorSincronizar = bienesPorSincronizar;
    }
    
    public int getEstadoPendiente() {
        return estadoPendiente;
    }

    public void setEstadoPendiente(int estadoPendiente) {
        this.estadoPendiente = estadoPendiente;
    }
    
    
    //</editor-fold>

    
}
