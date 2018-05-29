/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Mensaje;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.commands.TomaFisicaCommand;
import cr.ac.ucr.sigebi.commands.TomaFisicaCommand.ObjetoCarga;
import cr.ac.ucr.sigebi.commands.TomaFisicaCommand.ObjetoCargaLote;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Categoria;
import cr.ac.ucr.sigebi.domain.Clasificacion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Lote;
import cr.ac.ucr.sigebi.domain.SubCategoria;
import cr.ac.ucr.sigebi.domain.SubClasificacion;
import cr.ac.ucr.sigebi.domain.TomaFisica;
import cr.ac.ucr.sigebi.domain.TomaFisicaLote;
import cr.ac.ucr.sigebi.domain.TomaFisicaSobrante;
import cr.ac.ucr.sigebi.domain.TomaFisicaUnitaria;
import cr.ac.ucr.sigebi.domain.Ubicacion;
import cr.ac.ucr.sigebi.models.BienModel;
import cr.ac.ucr.sigebi.models.CategoriaModel;
import cr.ac.ucr.sigebi.models.ClasificacionModel;
import cr.ac.ucr.sigebi.models.LoteModel;
import cr.ac.ucr.sigebi.models.SubCategoriaModel;
import cr.ac.ucr.sigebi.models.SubClasificacionModel;
import cr.ac.ucr.sigebi.models.TomaFisicaLoteModel;
import cr.ac.ucr.sigebi.models.TomaFisicaModel;
import cr.ac.ucr.sigebi.models.TomaFisicaSobranteModel;
import cr.ac.ucr.sigebi.models.TomaFisicaUnitariaModel;
import cr.ac.ucr.sigebi.models.UbicacionModel;
import cr.ac.ucr.sigebi.utils.NodoSIGEBI;
import cr.ac.ucr.sigebi.utils.TreeUbicacionSIGEBI;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerTomaFisica")
@Scope("session")
public class TomaFisicaController extends BaseController {

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    @Resource
    private TomaFisicaModel tomaFisicaModel;
    
    @Resource
    private UbicacionModel ubicacionModel;
    
    @Resource
    private TomaFisicaLoteModel tomaFisicaLoteModel;
    
    @Resource
    private TomaFisicaUnitariaModel tomaFisicaUnitariaModel;
    
    @Resource
    private TomaFisicaSobranteModel tomaFisicaSobranteModel; 

    @Resource
    private BienModel bienModel;

    @Resource
    private LoteModel loteModel;

    @Resource
    private CategoriaModel categoriaModel;

    @Resource
    private SubCategoriaModel subCategoriaModel;

    @Resource
    private ClasificacionModel clasificacionModel;

    @Resource
    private SubClasificacionModel subClasificacionModel;
    
    private TreeUbicacionSIGEBI treeUbicacionSIGEBI;
    
    // Se usan en el jsp
    boolean yaRegistrada = false;
    boolean habilitaUbicacion = false;

    TomaFisicaCommand tomaFisicaCommand;
    

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">

    public TreeUbicacionSIGEBI getTreeUbicacionSIGEBI() {
        return treeUbicacionSIGEBI;
    }

    public void setTreeUbicacionSIGEBI(TreeUbicacionSIGEBI treeUbicacionSIGEBI) {
        this.treeUbicacionSIGEBI = treeUbicacionSIGEBI;
    }

    public boolean isYaRegistrada() {
        return yaRegistrada;
    }

    public void setYaRegistrada(boolean yaRegistrada) {
        this.yaRegistrada = yaRegistrada;
    }

    public TomaFisicaCommand getTomaFisicaCommand() {
        return tomaFisicaCommand;
    }

    public void setTomaFisicaCommand(TomaFisicaCommand tomaFisicaCommand) {
        this.tomaFisicaCommand = tomaFisicaCommand;
    }

    public boolean isHabilitaUbicacion() {
        return habilitaUbicacion;
    }

    public void setHabilitaUbicacion(boolean habilitaUbicacion) {
        this.habilitaUbicacion = habilitaUbicacion;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Navegación del MENÚ">
    public void nuevoRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        yaRegistrada = false;

        this.prepararNuevaTomaFisica();

        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_TOMA_FISICA_DETALLE);
    }

    public void modificarRegistro(ActionEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }

        this.prepararModificacionTomaFisica((TomaFisica) event.getComponent().getAttributes().get("tomaFisicaSeleccionada"));

        this.vistaOrigen = event.getComponent().getAttributes().get(Constantes.KEY_VISTA_ORIGEN).toString();
        Util.navegar(Constantes.KEY_VISTA_TOMA_FISICA_DETALLE);

    }

    public void regresarListado() {
        if (vistaOrigen != null) {
            Util.navegar(vistaOrigen, true);
        } else {
            Util.navegar(Constantes.KEY_VISTA_LISTAR_TOMA_FISICA, true);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public TomaFisicaController() {
        super();        
    }
    
    private void prepararNuevaTomaFisica() {
        tomaFisicaCommand = new TomaFisicaCommand();
        this.cargaDatosGenerales();
    }

    private void prepararModificacionTomaFisica(TomaFisica tomaFisica) {
        yaRegistrada = true;
        tomaFisicaCommand = new TomaFisicaCommand(tomaFisica);
        this.cargaDatosGenerales();
    }

    private void cargaDatosGenerales() {

        //Se consultan los tipos por dominio
        tomaFisicaCommand.setTiposOptions(new ArrayList<SelectItem>());
        for (Tipo tipo : this.tiposPorDominio(Constantes.DOMINIO_TOMA_FISICA)) {
            tomaFisicaCommand.getTiposOptions().add(new SelectItem(tipo.getId().toString(), tipo.getNombre()));
        }

        //Se consultan los tipos por dominio
        tomaFisicaCommand.setTiposMotivoOptions(new ArrayList<SelectItem>());
        for (Tipo tipo : this.tiposPorDominio(Constantes.DOMINIO_TOMA_FISICA_MOTIVO)) {
            tomaFisicaCommand.getTiposMotivoOptions().add(new SelectItem(tipo.getId().toString(), tipo.getNombre()));
        }
        
        
        //Se habilita la ubicacion para la toma fisica
        habilitaUbicacionTomaFisica();

        if(yaRegistrada){
            
            //Registros por pagina para las busquedas
            ArrayList<SelectItem> cantPorPaginas = new ArrayList<SelectItem>();
            cantPorPaginas.add(new SelectItem(5, "5"));
            cantPorPaginas.add(new SelectItem(10, "10"));
            cantPorPaginas.add(new SelectItem(25, "25"));
            cantPorPaginas.add(new SelectItem(50, "50"));
            this.setListaRegistrosPagina(cantPorPaginas);            
            tomaFisicaCommand.getTomaFisicaUnitariaCommand().setListaRegistrosPagina(cantPorPaginas);
            tomaFisicaCommand.getTomaFisicaLoteCommand().setListaRegistrosPagina(cantPorPaginas);
            tomaFisicaCommand.getTomaFisicaSobranteCommand().setListaRegistrosPagina(cantPorPaginas);
            
            
            //Se consultan los lotes
            tomaFisicaCommand.setLoteOptions(new ArrayList<SelectItem>());
            for (Lote lote : loteModel.listar()) {
                tomaFisicaCommand.getLoteOptions().add(new SelectItem(lote.getId().toString(), lote.getDescripcion()));
            }     

            //Se consultan las categorias
            tomaFisicaCommand.setCategoriaOptions(new ArrayList<SelectItem>());
            for (Categoria categoria : categoriaModel.listar()) {
                tomaFisicaCommand.getCategoriaOptions().add(new SelectItem(categoria.getId().toString(), categoria.getDescripcion()));
            } 
            
            //Se listan tomas fisicas por unidad
            buscarTomasFisicasUnitarias();        

            //Se listan tomas fisicas por lote
            buscarTomasFisicasLotes();        

            //Se listan tomas fisicas sobrantes
            buscarTomasFisicasSobrantes();
        }
        
        treeUbicacionSIGEBI = new TreeUbicacionSIGEBI(unidadEjecutora);
        treeUbicacionSIGEBI.setUbicacionModel(ubicacionModel);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Toma Fisica">
    
    /**
     * Metodo que busca y asigna al command el tipo seleccionado
     *
     * @param event
     */
    public void cambiarTipo(ValueChangeEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Se obtiene el id del tipoDonacion
            Long valor = tomaFisicaCommand.getTomaFisica().getTipo().getIdTemporal();
            if (valor > 0) {
                tomaFisicaCommand.getTomaFisica().setTipo(this.tipoPorId(valor));
                tomaFisicaCommand.getTomaFisica().getTipo().setIdTemporal(valor);
                habilitaUbicacionTomaFisica();
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambiarTipo"));
        }
    }

    private void habilitaUbicacionTomaFisica(){
        if(tomaFisicaCommand.getTomaFisica().getTipo().getValor() != null && tomaFisicaCommand.getTomaFisica().getTipo().getValor().equals(Constantes.TIPO_TOMA_FISICA_COMPLETO)){
            this.habilitaUbicacion = false;
            tomaFisicaCommand.getUbicacion().setIdTemporal(-1L);
        }else{
            this.habilitaUbicacion = true;
        }      
    }
    
    
     /**
     * Metodo que busca y asigna al command el motivo seleccionado
     *
     * @param event
     */
    public void cambiarTipoMotivo(ValueChangeEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Se obtiene el id del tipoDonacion
            Long valor = tomaFisicaCommand.getTomaFisica().getMotivo().getIdTemporal();
            if (valor > 0) {
                tomaFisicaCommand.getTomaFisica().setMotivo(this.tipoPorId(valor));
                tomaFisicaCommand.getTomaFisica().getMotivo().setIdTemporal(valor);
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambiarTipoMotivo"));
        }
    }

    /**
     * Metodo que busca y asigna al command la ubicacion seleccionada
     *
     * @param event
     */
    public void cambiarUbicacion(ActionEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            //Se busca el nodo seleccionado
            NodoSIGEBI nodoSIGEBI = (NodoSIGEBI) event.getComponent().getAttributes().get("nodoSeleccionado");
            
            //Se asigna la ubicacion 
            tomaFisicaCommand.setUbicacion((Ubicacion) nodoSIGEBI.getObject());
            if (tomaFisicaCommand.getUbicacion() != null) {
                tomaFisicaCommand.getUbicacion().setIdTemporal(tomaFisicaCommand.getUbicacion().getId());
                habilitaUbicacionTomaFisica(); 
            }
            
            //Se cierra el panel
            treeUbicacionSIGEBI.setPresentaPanelUbicacion(false);
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambiarUbicacion"));
        }
    }
    
    /**
     * Almacena la informacion de la toma fisica
     */
    public void guardaTomaFisica() {
        try {
            if (validarForm()) {
                
                TomaFisica tomaFisica = null;
                if(!yaRegistrada){
            
                    //Se almacena la tomaFisica
                    tomaFisica = tomaFisicaCommand.prepararTomaFisica(this.estadoPorDominioValor(Constantes.DOMINIO_TOMA_FISICA, Constantes.ESTADO_TOMA_FISICA_PENDIENTE));
                    tomaFisicaModel.modificar(tomaFisica);
                    
                    //Se marca como registrada
                    this.yaRegistrada = true;
                
                }else{
                    //Se modifica la toma fisica
                    tomaFisica = tomaFisicaCommand.prepararTomaFisica(null);
                    tomaFisicaModel.modificar(tomaFisica);
                }
                                
                //Se actualiza los datos de la toma fisica
                this.prepararModificacionTomaFisica(tomaFisica);

                Mensaje.agregarInfo(Util.getEtiquetas("sigebi.controllerTomaFisica.modificado.exitosamente"));
            }

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.guardarDatos"));
        }
    }
    
    public boolean validarForm() {

        TomaFisica tomaFisica = tomaFisicaCommand.getTomaFisica();

        if (tomaFisica.getTipo() == null || tomaFisica.getTipo().getId() == null || tomaFisica.getTipo().getId() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerTomaFisica.tipo.requerido"));
            return false;
        }

        if (tomaFisica.getMotivo() == null || tomaFisica.getMotivo().getId() == null || tomaFisica.getMotivo().getId() <= 0) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerTomaFisica.motivo.requerido"));
            return false;
        }
        
        if(this.habilitaUbicacion && (tomaFisicaCommand.getUbicacion() == null || tomaFisicaCommand.getUbicacion().getIdTemporal()== null || tomaFisicaCommand.getUbicacion().getIdTemporal() <= 0)){
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerTomaFisica.ubicacion.requerido"));
            return false;
        }

        if (tomaFisica.getDescripcion().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerTomaFisica.descripcion.requerido"));
            return false;
        }
        
        return true;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos Toma Fisica Unitaria">
    
    public void agregarBienPorIdentificacionTomasFisicaUnitaria(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            Bien bien = bienModel.buscarPorIdentificacion(tomaFisicaCommand.getTomaFisicaUnitariaCommand().getIdentificacionBusqueda());
            if(bien == null){
                tomaFisicaCommand.getTomaFisicaUnitariaCommand().setBien(new Bien());
                Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerTomaFisica.agregarBienPorIdentificacionTomasFisicaUnitaria"));
            }else{
                
                //Se asigna para presentar los datos por pantalla
                tomaFisicaCommand.getTomaFisicaUnitariaCommand().setBien(bien);
                
                //Valida que el no se haya registrado
                if(tomaFisicaUnitariaModel.buscarPorBien(tomaFisicaCommand.getTomaFisica(), bien) != null){
                    Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerTomaFisica.agregarBienPorIdentificacionTomasFisicaUnitaria.ya.existe"));                                
                }else{
                    //Se agrega la toma fisica unitaria
                    TomaFisicaUnitaria tomaFisicaUnitaria = new TomaFisicaUnitaria(tomaFisicaCommand.getTomaFisica(), bien);
                    tomaFisicaUnitariaModel.agregar(tomaFisicaUnitaria);                    
                    
                    //Se limpia la identificacion de la pantalla
                    tomaFisicaCommand.getTomaFisicaUnitariaCommand().setIdentificacionBusqueda("");
                    
                    //Se listan los datos
                    this.buscarTomasFisicasUnitarias();

                    Mensaje.agregarInfo(Util.getEtiquetas("sigebi.controllerTomaFisica.agregarBienPorIdentificacionTomasFisicaUnitaria.exitosamente"));                
                }                
            }            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambioFiltroTomasFisicaUnitaria"));
        }
    }
    
    public void eliminarTomaFisicaUnitaria(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se obtiene la toma fisica
            TomaFisicaUnitaria tomaFisicaUnitaria = (TomaFisicaUnitaria) pEvent.getComponent().getAttributes().get("tomaFisicaEliminarSel");

            //Se elimina de la base de datos 
            tomaFisicaUnitariaModel.eliminar(tomaFisicaUnitaria);     
            
            //Se consultan los datos
            this.buscarTomasFisicasUnitarias();
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.eliminarTomaFisicaUnitaria"));
        }
    }
    
    public void cambioFiltroTomasFisicaUnitaria(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            // Se lista por los criterios indicados
            buscarTomasFisicasUnitarias();
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambioFiltroTomasFisicaUnitaria"));
        }

    }
    
    private void buscarTomasFisicasUnitarias(){
        
        // Se cuenta la cantidad de tomas fisicas unitarias
        tomaFisicaCommand.getTomaFisicaUnitariaCommand().setPrimerRegistro(1);
        this.contarTomaFisicaUnitaras();

        //Se consulta la lista de tomas fisicas unitarias
        this.listarTomaFisicaUnitaras();
    }

    /**
     * Contabiliza las tomas fisicas unitaras
     */
    private void contarTomaFisicaUnitaras() {
        try {

            //Se cuenta la cantidad de registros
            Long contador = tomaFisicaUnitariaModel.contar(tomaFisicaCommand.getTomaFisicaUnitariaCommand().getFltId(), tomaFisicaCommand.getTomaFisicaUnitariaCommand().getFltDescripcion(), 
                    tomaFisicaCommand.getTomaFisicaUnitariaCommand().getFltIdentificacion(), tomaFisicaCommand.getTomaFisica());

            //Se actualiza la cantidad de registros segun los filtros
            tomaFisicaCommand.getTomaFisicaUnitariaCommand().setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.contarTomaFisicaUnitaras"));
        }
    }

    /**
     * Lista las tomas fisicas unitarias
     */
    private void listarTomaFisicaUnitaras() {
        try {

            List<TomaFisicaUnitaria> resultado = tomaFisicaUnitariaModel.listar(tomaFisicaCommand.getTomaFisicaUnitariaCommand().getFltId(), tomaFisicaCommand.getTomaFisicaUnitariaCommand().getFltDescripcion(), 
                    tomaFisicaCommand.getTomaFisicaUnitariaCommand().getFltIdentificacion(), tomaFisicaCommand.getTomaFisica(), tomaFisicaCommand.getTomaFisicaUnitariaCommand().getPrimerRegistro() - 1, tomaFisicaCommand.getTomaFisicaUnitariaCommand().getUltimoRegistro());
            this.tomaFisicaCommand.setTomasFisicasUnitarias(resultado);            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.listarTomaFisicaUnitaras"));
        }
    }
   
    /**
     * Pasa a la pagina sub-set
     *
     * @param pEvent
     */
    public void irPaginaTomaFisicaUnitaras(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPagTomaFisicaUnitaria"));
        tomaFisicaCommand.getTomaFisicaUnitariaCommand().getPrimerRegistroPagina(numeroPagina);
        this.listarTomaFisicaUnitaras();
    }

    /**
     * Pasa al siguiente sub-set
     *
     * @param pEvent
     */
    public void siguienteTomaFisicaUnitaras(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaUnitariaCommand().getSiguientePagina();
        this.listarTomaFisicaUnitaras();
    }

    /**
     * Pasa al anterior sub-set
     *
     * @param pEvent
     */
    public void anteriorTomaFisicaUnitaras(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaUnitariaCommand().getPaginaAnterior();
        this.listarTomaFisicaUnitaras();
    }

    /**
     * Pasa al primero sub-set
     *
     * @param pEvent
     */
    public void primeroTomaFisicaUnitaras(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaUnitariaCommand().setPrimerRegistro(1);
        this.listarTomaFisicaUnitaras();
    }

    /**
     * Pasa al ultimo sub-set
     *
     * @param pEvent
     */
    public void ultimoTomaFisicaUnitaras(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaUnitariaCommand().getPrimerRegistroUltimaPagina();
        this.listarTomaFisicaUnitaras();
    }

    /**
     * Cambia la cantidad de registros por página
     *
     * @param pEvent
     */
    public void cambioRegistrosPorPaginaTomaFisicaUnitaras(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaUnitariaCommand().setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));
        tomaFisicaCommand.getTomaFisicaUnitariaCommand().setPrimerRegistro(1);
        this.contarTomaFisicaUnitaras();
        this.listarTomaFisicaUnitaras();
    }
    
    //</editor-fold> 

    //<editor-fold defaultstate="collapsed" desc="Metodos Toma Fisica Lote">
    
    /**
     * Metodo que busca y asigna al command el lote seleccionado
     * @param event
     */
    public void cambiarLote(ValueChangeEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }

            // Se obtiene el id del tipoDonacion
            Long valor = tomaFisicaCommand.getTomaFisicaLoteCommand().getLote().getIdTemporal();
            if (valor > 0) {
                tomaFisicaCommand.getTomaFisicaLoteCommand().setLote(loteModel.buscarPorId(valor));
                tomaFisicaCommand.getTomaFisicaLoteCommand().getLote().setIdTemporal(valor);
                agregaTomaFisicaLote();
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambiarUbicacion"));
        }
    }
    
    /**
     * Actualiza los datos de acuerdo a la cantidad
     * @param event
     */
    public void cambiarCantidadLote(ValueChangeEvent event) {
        try {
            if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
                event.queue();
                return;
            }
            
            agregaTomaFisicaLote();

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambiarUbicacion"));
        }
    }
    
    private void agregaTomaFisicaLote(){    
        
        if(tomaFisicaCommand.getTomaFisicaLoteCommand().getCantidad() != null 
                && tomaFisicaCommand.getTomaFisicaLoteCommand().getCantidad() != 0 
                && tomaFisicaCommand.getTomaFisicaLoteCommand().getLote() != null 
                && tomaFisicaCommand.getTomaFisicaLoteCommand().getLote().getIdTemporal() > 0){
            
            //Se busca si ya existe la toma por lote          
            TomaFisicaLote tomaFisicaLote = tomaFisicaLoteModel.buscarPorLote(tomaFisicaCommand.getTomaFisica(), tomaFisicaCommand.getTomaFisicaLoteCommand().getLote());            
            
            //Se crea o se actualiza
            if(tomaFisicaLote != null){
                Long cantidad = tomaFisicaLote.getCantidad() + tomaFisicaCommand.getTomaFisicaLoteCommand().getCantidad();
                if(cantidad > 0){
                    tomaFisicaLote.setCantidad(cantidad);
                    tomaFisicaLoteModel.modificar(tomaFisicaLote);
                }else{
                    tomaFisicaLoteModel.eliminar(tomaFisicaLote);
                }
                mensajeActualizaTomaFisicaLote();
            }else{
                if(tomaFisicaCommand.getTomaFisicaLoteCommand().getCantidad() > 0){
                    tomaFisicaLote = new TomaFisicaLote(tomaFisicaCommand.getTomaFisica(), tomaFisicaCommand.getTomaFisicaLoteCommand().getLote(), tomaFisicaCommand.getTomaFisicaLoteCommand().getCantidad());
                    tomaFisicaLoteModel.agregar(tomaFisicaLote);
                    mensajeActualizaTomaFisicaLote();
                }    
            }
        }        
    }

    private void mensajeActualizaTomaFisicaLote() {
        //Se limpian los datos
        tomaFisicaCommand.getTomaFisicaLoteCommand().setCantidad(0L);
        tomaFisicaCommand.getTomaFisicaLoteCommand().getLote().setIdTemporal(-1L);

        //Se actualiza la lista
        this.buscarTomasFisicasLotes();

        Mensaje.agregarInfo(Util.getEtiquetas("sigebi.controllerTomaFisica.agregaTomaFisicaLote.exitosamente"));

    }
    
    public void cambioFiltroTomasFisicaLote(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            // Se lista por los criterios indicados
            buscarTomasFisicasLotes();
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambioFiltroTomasFisicaLote"));
        }

    }
    
    private void buscarTomasFisicasLotes(){
        
        // Se cuenta la cantidad de tomas fisicas unitarias
        tomaFisicaCommand.getTomaFisicaLoteCommand().setPrimerRegistro(1);
        this.contarTomaFisicaLote();

        //Se consulta la lista de tomas fisicas unitarias
        this.listarTomaFisicaLote();
    }

    /**
     * Contabiliza las tomas fisicas unitaras
     */
    private void contarTomaFisicaLote() {
        try {

            //Se cuenta la cantidad de registros
            Long contador = tomaFisicaLoteModel.contar(tomaFisicaCommand.getTomaFisicaLoteCommand().getFltId(), tomaFisicaCommand.getTomaFisicaLoteCommand().getFltLote(), tomaFisicaCommand.getTomaFisica());

            //Se actualiza la cantidad de registros segun los filtros
            tomaFisicaCommand.getTomaFisicaLoteCommand().setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.contarTomaFisicaLote"));
        }
    }

    /**
     * Lista las tomas fisicas unitarias
     */
    private void listarTomaFisicaLote() {
        try {

            List<TomaFisicaLote> resultado = tomaFisicaLoteModel.listar(tomaFisicaCommand.getTomaFisicaLoteCommand().getFltId(), tomaFisicaCommand.getTomaFisicaLoteCommand().getFltLote(), 
                    tomaFisicaCommand.getTomaFisica(), tomaFisicaCommand.getTomaFisicaLoteCommand().getPrimerRegistro() - 1, tomaFisicaCommand.getTomaFisicaLoteCommand().getUltimoRegistro());
            this.tomaFisicaCommand.setTomasFisicasLotes(resultado);            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.listarTomaFisicaLote"));
        }
    }
   
    /**
     * Pasa a la pagina sub-set
     *
     * @param pEvent
     */
    public void irPaginaTomaFisicaLote(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPagTomaFisicaLote"));
        tomaFisicaCommand.getTomaFisicaLoteCommand().getPrimerRegistroPagina(numeroPagina);
        this.listarTomaFisicaLote();
    }

    /**
     * Pasa al siguiente sub-set
     *
     * @param pEvent
     */
    public void siguienteTomaFisicaLote(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaLoteCommand().getSiguientePagina();
        this.listarTomaFisicaLote();
    }

    /**
     * Pasa al anterior sub-set
     *
     * @param pEvent
     */
    public void anteriorTomaFisicaLote(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaLoteCommand().getPaginaAnterior();
        this.listarTomaFisicaLote();
    }

    /**
     * Pasa al primero sub-set
     *
     * @param pEvent
     */
    public void primeroTomaFisicaLote(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaLoteCommand().setPrimerRegistro(1);
        this.listarTomaFisicaLote();
    }

    /**
     * Pasa al ultimo sub-set
     *
     * @param pEvent
     */
    public void ultimoTomaFisicaLote(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaLoteCommand().getPrimerRegistroUltimaPagina();
        this.listarTomaFisicaLote();
    }

    /**
     * Cambia la cantidad de registros por página
     *
     * @param pEvent
     */
    public void cambioRegistrosPorPaginaTomaFisicaLote(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaLoteCommand().setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));
        tomaFisicaCommand.getTomaFisicaLoteCommand().setPrimerRegistro(1);
        this.contarTomaFisicaLote();
        this.listarTomaFisicaLote();
    }
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos Toma Fisica Sobrante">
        
     public void cambiarCategoria(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        
        try {
            //Se limpian subcategoria, clasificacion y  sub clasificacion
            if (tomaFisicaCommand.getSubCategoriaOptions() != null) {
                tomaFisicaCommand.getSubCategoriaOptions().clear();
                tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubCategoria().setIdTemporal(-1L);
            }
            if (tomaFisicaCommand.getClasificacionOptions() != null) {
                tomaFisicaCommand.getClasificacionOptions().clear();
                tomaFisicaCommand.getTomaFisicaSobranteCommand().getClasificacion().setIdTemporal(-1L);
            }
            if (tomaFisicaCommand.getSubClasificacionOptions() != null) {
                tomaFisicaCommand.getSubClasificacionOptions().clear();
                tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubClasificacion().setIdTemporal(-1L);
            }
            
            Long valor = tomaFisicaCommand.getTomaFisicaSobranteCommand().getCategoria().getIdTemporal();
            if (valor > 0) {
                tomaFisicaCommand.getTomaFisicaSobranteCommand().setCategoria(categoriaModel.buscarPorId(valor));
                tomaFisicaCommand.getTomaFisicaSobranteCommand().getCategoria().setIdTemporal(valor);
                
                //Se cargan las subcategorias
                List<SubCategoria> subCategorias =subCategoriaModel.listar(tomaFisicaCommand.getTomaFisicaSobranteCommand().getCategoria());
                if (!subCategorias.isEmpty()) {
                    tomaFisicaCommand.setSubCategoriaOptions(new ArrayList<SelectItem>());
                    for (SubCategoria item : subCategorias) {
                        tomaFisicaCommand.getSubCategoriaOptions().add(new SelectItem(item.getId(), item.getDescripcion()));
                    }
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambioCategoria"));
        }

    } 
     
    public void cambiarSubCategoria(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        
        try {
                    
            //Se limpian clasificacion y  sub clasificacion
            if (tomaFisicaCommand.getClasificacionOptions() != null) {
                tomaFisicaCommand.getClasificacionOptions().clear();
                tomaFisicaCommand.getTomaFisicaSobranteCommand().getClasificacion().setIdTemporal(-1L);
            }
            if (tomaFisicaCommand.getSubClasificacionOptions() != null) {
                tomaFisicaCommand.getSubClasificacionOptions().clear();
                tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubClasificacion().setIdTemporal(-1L);
            }
            
            Long valor = tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubCategoria().getIdTemporal();
            if (valor > 0) {
                tomaFisicaCommand.getTomaFisicaSobranteCommand().setSubCategoria(subCategoriaModel.buscarPorId(valor));
                tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubCategoria().setIdTemporal(valor);
                
                //Se cargan las Clasificaciones
                List<Clasificacion> clasificaciones = clasificacionModel.listarPorSubCategoria(tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubCategoria());
                if (!clasificaciones.isEmpty()) {
                    tomaFisicaCommand.setClasificacionOptions(new ArrayList<SelectItem>());
                    for (Clasificacion item : clasificaciones) {
                        tomaFisicaCommand.getClasificacionOptions().add(new SelectItem(item.getId(), item.getNombre()));
                    }
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambioSubCategoria"));
        }

    }

    public void cambiarClasificacion(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        
        try {
                    
            //Se limpian sub clasificacion
            if (tomaFisicaCommand.getSubClasificacionOptions() != null) {
                tomaFisicaCommand.getSubClasificacionOptions().clear();
                tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubClasificacion().setIdTemporal(-1L);
            }
            
            Long valor = tomaFisicaCommand.getTomaFisicaSobranteCommand().getClasificacion().getIdTemporal();
            if (valor > 0) {
                tomaFisicaCommand.getTomaFisicaSobranteCommand().setClasificacion(clasificacionModel.buscarPorId(valor));
                tomaFisicaCommand.getTomaFisicaSobranteCommand().getClasificacion().setIdTemporal(valor);
                
                //Se cargan las sub clasificaciones
                List<SubClasificacion> subClasificaciones = subClasificacionModel.listar(tomaFisicaCommand.getTomaFisicaSobranteCommand().getClasificacion());
                if (!subClasificaciones.isEmpty()) {
                    tomaFisicaCommand.setSubClasificacionOptions(new ArrayList<SelectItem>());
                    for (SubClasificacion item : subClasificaciones) {
                        tomaFisicaCommand.getSubClasificacionOptions().add(new SelectItem(item.getId(), item.getNombre()));
                    }
                }
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambioSubCategoria"));
        }

    } 
    
     
    public void cambiarSubClasificacion(ValueChangeEvent event) {
        if (!event.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
            return;
        }
        
        try {                                
            Long valor = tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubClasificacion().getIdTemporal();
            if (valor > 0) {
                tomaFisicaCommand.getTomaFisicaSobranteCommand().setSubClasificacion(subClasificacionModel.buscarPorId(valor));
                tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubClasificacion().setIdTemporal(valor);                
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambioSubCategoria"));
        }
    } 
    
    /**
     * Almacena la informacion de la toma fisica de sobrantes
     */
    public void agregarTomaFisicaSobrante() {
        try {
            if (validarFormTomaFisicaSobrante()) {
                
                //Se crea, no se valida a solicitud de oscar si ya existe una toma sobrante con la misma identificacion
                tomaFisicaSobranteModel.agregar(tomaFisicaCommand.getTomaFisicaSobranteCommand().prepararTomaFisicaSobrante(null, tomaFisicaCommand.getTomaFisica()));
                mensajeActualizaTomaFisicaSobrante();                
            }
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.guardarDatos"));
        }
    }
    
    public boolean validarFormTomaFisicaSobrante() {

        TomaFisicaCommand.TomaFisicaSobranteCommand command = tomaFisicaCommand.getTomaFisicaSobranteCommand();
        if (command.getTomaFisicaSobrante().getDescripcion().isEmpty()) {
            Mensaje.agregarErrorAdvertencia(Util.getEtiquetas("sigebi.error.controllerTomaFisica.descripcion.requerido"));
            return false;
        }
        
        return true;
    }

    private void mensajeActualizaTomaFisicaSobrante() {
        
        //Se limpian los datos
        tomaFisicaCommand.getTomaFisicaSobranteCommand().setTomaFisicaSobrante(new TomaFisicaSobrante());
        tomaFisicaCommand.getTomaFisicaSobranteCommand().getCategoria().setIdTemporal(-1L);
        tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubCategoria().setIdTemporal(-1L);
        tomaFisicaCommand.getTomaFisicaSobranteCommand().getClasificacion().setIdTemporal(-1L);
        tomaFisicaCommand.getTomaFisicaSobranteCommand().getSubClasificacion().setIdTemporal(-1L);

        //Se actualiza la lista
        this.buscarTomasFisicasSobrantes();

        Mensaje.agregarInfo(Util.getEtiquetas("sigebi.controllerTomaFisica.agregaTomaFisicaSobrante.exitosamente"));

    }
    
    public void cambioFiltroTomasFisicaSobrante(ValueChangeEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            // Se lista por los criterios indicados
            buscarTomasFisicasSobrantes();
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.cambioFiltroTomasFisicaSobrante"));
        }

    }
    
    public void eliminarTomaFisicaSobrante(ActionEvent pEvent) {
        try {
            if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
                pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
                pEvent.queue();
                return;
            }

            //Se obtiene la toma fisica
            TomaFisicaSobrante tomaFisicaSobrante = (TomaFisicaSobrante) pEvent.getComponent().getAttributes().get("tomaFisicaSobranteEliminarSel");

            //Se elimina de la base de datos 
            tomaFisicaSobranteModel.eliminar(tomaFisicaSobrante);     
            
            //Se consultan los datos
            this.buscarTomasFisicasSobrantes();
            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.eliminarTomaFisicaSobrante"));
        }
    }
    
    private void buscarTomasFisicasSobrantes(){
        
        // Se cuenta la cantidad de tomas fisicas unitarias
        tomaFisicaCommand.getTomaFisicaSobranteCommand().setPrimerRegistro(1);
        this.contarTomaFisicaSobrante();

        //Se consulta la lista de tomas fisicas unitarias
        this.listarTomaFisicaSobrante();
    }

    /**
     * Contabiliza las tomas fisicas unitaras
     */
    private void contarTomaFisicaSobrante() {
        try {

            //Se cuenta la cantidad de registros
            Long contador = tomaFisicaSobranteModel.contar(tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltId(), tomaFisicaCommand.getTomaFisica(),
                    tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltIdentificacion(),tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltDescripcion(), 
                    tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltSerie(),tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltMarca(), tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltModelo());
            
            //Se actualiza la cantidad de registros segun los filtros
            tomaFisicaCommand.getTomaFisicaSobranteCommand().setCantidadRegistros(contador.intValue());

        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.contarTomaFisicaSobrante"));
        }
    }
    
    /**
     * Lista las tomas fisicas unitarias
     */
    private void listarTomaFisicaSobrante() {
        try {

            List<TomaFisicaSobrante> resultado = tomaFisicaSobranteModel.listar(tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltId(), tomaFisicaCommand.getTomaFisica(),
                    tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltIdentificacion(),tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltDescripcion(), 
                    tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltSerie(),tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltMarca(), tomaFisicaCommand.getTomaFisicaSobranteCommand().getFltModelo(), 
                    tomaFisicaCommand.getTomaFisicaSobranteCommand().getPrimerRegistro() - 1, tomaFisicaCommand.getTomaFisicaSobranteCommand().getUltimoRegistro());
            this.tomaFisicaCommand.setTomasFisicasSobrantes(resultado);            
        } catch (FWExcepcion e) {
            Mensaje.agregarErrorAdvertencia(e.getError_para_usuario());
        } catch (Exception e) {
            Mensaje.agregarErrorAdvertencia(e, Util.getEtiquetas("sigebi.error.controllerTomaFisica.listarTomaFisicaSobrante"));
        }
    }
   
    /**
     * Pasa a la pagina sub-set
     *
     * @param pEvent
     */
    public void irPaginaTomaFisicaSobrante(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        int numeroPagina = Integer.parseInt(Util.getRequestParameter("numPagTomaFisicaSobrante"));
        tomaFisicaCommand.getTomaFisicaSobranteCommand().getPrimerRegistroPagina(numeroPagina);
        this.listarTomaFisicaSobrante();
    }

    /**
     * Pasa al siguiente sub-set
     *
     * @param pEvent
     */
    public void siguienteTomaFisicaSobrante(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaSobranteCommand().getSiguientePagina();
        this.listarTomaFisicaSobrante();
    }

    /**
     * Pasa al anterior sub-set
     *
     * @param pEvent
     */
    public void anteriorTomaFisicaSobrante(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaSobranteCommand().getPaginaAnterior();
        this.listarTomaFisicaSobrante();
    }

    /**
     * Pasa al primero sub-set
     *
     * @param pEvent
     */
    public void primeroTomaFisicaSobrante(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaSobranteCommand().setPrimerRegistro(1);
        this.listarTomaFisicaSobrante();
    }

    /**
     * Pasa al ultimo sub-set
     *
     * @param pEvent
     */
    public void ultimoTomaFisicaSobrante(ActionEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaSobranteCommand().getPrimerRegistroUltimaPagina();
        this.listarTomaFisicaSobrante();
    }

    /**
     * Cambia la cantidad de registros por página
     *
     * @param pEvent
     */
    public void cambioRegistrosPorPaginaTomaFisicaSobrante(ValueChangeEvent pEvent) {
        if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        tomaFisicaCommand.getTomaFisicaSobranteCommand().setCantRegistroPorPagina(Integer.parseInt(pEvent.getNewValue().toString()));
        tomaFisicaCommand.getTomaFisicaSobranteCommand().setPrimerRegistro(1);
        this.contarTomaFisicaLote();
        this.listarTomaFisicaLote();
    }
    
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Carga Unitaria Excel">
    //C:\SIGEBI_FINAL\web\Documentos
    public void leerArchivoTomaUnitaria(FileInputStream file){
        try{
            
            
            
            Workbook workbook = new XSSFWorkbook(file);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            ObjetoCarga lineaCarga;
            
            List<ObjetoCarga> objetosCarga = new ArrayList<ObjetoCarga>();
            tomaFisicaCommand.setObjetosCarga(objetosCarga);
            // Row en el que me voy a desplazar 
            int r = 0;
            while (iterator.hasNext()) {
                
                Row currentRow = datatypeSheet.getRow(r);
                
                if(r > 0){
                    if(currentRow != null){
                        if((currentRow.getCell(0) != null) || (currentRow.getCell(1) != null)){

                            lineaCarga = obtenerLinea(currentRow);
                            if(lineaCarga != null
                                &&lineaCarga.getIdentificacion() != null
                                && lineaCarga.getIdentificacion() != null
                                && lineaCarga.getIdentificacion() != null
                                    )
                                objetosCarga.add(lineaCarga);
                        }
                    }
                    else
                        break;
                }
                r++;
            }
            
            tomaFisicaCommand.setObjetosCarga(objetosCarga);
            //obtenerDatos(cellData);
        }
        catch(Exception err){
            err.printStackTrace();
        }
    }
    
    
    private ObjetoCarga obtenerLinea(Row currentRow){
        try{
            ObjetoCarga lineaCarga;
            lineaCarga = tomaFisicaCommand.getNewObjetoCarga();
            
            String iden = "";
            String desc = "";
            
            if(currentRow.getCell(0) != null)
                iden = currentRow.getCell(0).toString();
            lineaCarga.setIdentificacion(iden);
                    
            if(currentRow.getCell(1) != null)
                desc = currentRow.getCell(1).toString();
            lineaCarga.setDescripcion((String) desc);
            
            validarLineaCargaUnitaria(lineaCarga);
            return lineaCarga;
            
        }catch(Exception err){
            return tomaFisicaCommand.getNewObjetoCarga();
        }
    }
    
    
    public void validarLineaCargaUnitaria(ObjetoCarga lineaCarga){
        try{
            String descError =  Util.getEtiquetas("sigebi.CargarInventario.ErrorSobrante") + ": " ;
            lineaCarga.setEsSobrante(false);
            if(lineaCarga.getIdentificacion().length() > 0){
                Bien bien = bienModel.buscarPorIdentificacion(lineaCarga.getIdentificacion());
                if( (bien != null) && (bien.getId() != null) && (bien.getId() > 0)){
                    lineaCarga.setBien(bien);
                    if(bien.getUnidadEjecutora().getId().equals(this.unidadEjecutora.getId())){
                        if((this.tomaFisicaCommand.getUbicacion() == null) || (this.tomaFisicaCommand.getUbicacion().getId() == null)){
                            lineaCarga.setBien(bien);
                        }else{
                            if(this.tomaFisicaCommand.getUbicacion().getId().equals(bien.getUbicacion().getId())){
                                lineaCarga.setBien(bien);
                            }
                            else{
                                descError += Util.getEtiquetas("sigebi.CargarInventario.ErrorBienOtraUbicacion");
                                //Pertenece a otra Ubicación
                                //Lo pongo como sobrante
                                lineaCarga.setEsSobrante(true);
                                lineaCarga.setDescripcionError( descError );
                            }
                        }   
                    }
                    else{
                        descError += Util.getEtiquetas("sigebi.CargarInventario.ErrorBienOtraUnidad");
                        //Pertenece a otra Unidad
                        //Lo pongo como sobrante
                        lineaCarga.setEsSobrante(true);
                        lineaCarga.setDescripcionError( descError );
                    }
                }else{
                    
                    //El bién no se encontró
                    
                    //Si tiene descripción y una identificación menor a 30 
                    //Lo pongo como sobrante
                    if(lineaCarga.getIdentificacion().length() > 0 && lineaCarga.getIdentificacion().length() < 31 ){
                        lineaCarga.setEsSobrante(true);
                        descError += Util.getEtiquetas("sigebi.CargarInventario.ErrorBienNoEncontrado");
                    }
                    else{
                        descError = Util.getEtiquetas("sigebi.CargarInventario.ErrorIdentifInvalida");
                    }
                    lineaCarga.setDescripcionError( descError );
                }
            }
            else{
                
                //Campo sin Identificación
                
                //Si tiene descripción 
                //Lo pongo como sobrante
                if(lineaCarga.getDescripcion().length() > 0 && lineaCarga.getDescripcion().length() < 200 ){
                        lineaCarga.setEsSobrante(true);
                        descError += Util.getEtiquetas("sigebi.CargarInventario.ErrorSinIdentificacion");
                    }
                    else{
                        descError = Util.getEtiquetas("sigebi.CargarInventario.ErrorDescripcionReq");
                    }
                lineaCarga.setDescripcionError( descError );
            }
        }catch(Exception err){
            lineaCarga.setDescripcionError( Util.getEtiquetas("sigebi.CargarInventario.ErrorBienNoEncontrado") );
        }
    }
    
    
    public void subirFile(ActionEvent event) {
        try {
            InputFile inputFile = (InputFile) event.getSource();
            FileInfo fileInfo = inputFile.getFileInfo();
            
            
            File fileName = inputFile.getFile();
            
            FileInputStream file = new FileInputStream(fileName);
            
            leerArchivoTomaUnitaria(file);
            tomaFisicaCommand.setMostrarErroresCargaUnitaria(false);
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.Bien.Error.Registro"));
        }
    }

    
    public void procesarCargaUnitaria(){
        try{
            if(tomaFisicaCommand.getObjetosCarga().size() > 0){
                String respuesta = "";
                List<String> respErrores = new ArrayList();
                for(ObjetoCarga carga : tomaFisicaCommand.getObjetosCarga()){
                    
                    respuesta = "";
                    
                    //Agrego Sobrantes
                    if(carga.getEsSobrante()){
                        
                        
                                
                        TomaFisicaSobrante sobrante = new TomaFisicaSobrante(); 

                        sobrante.setIdentificacion(carga.getIdentificacion());
                        sobrante.setDescripcion(carga.getDescripcion());
                        sobrante.setTomaFisica(tomaFisicaCommand.getTomaFisica());
                        
                        String descSobrante = carga.getDescripcion();
                        if (descSobrante == null || descSobrante.isEmpty() ) {
                            //No tiene descripción pero el bien si existe
                            if(carga.getBien() == null || 
                               carga.getBien().getIdentificacion() == null ||
                               carga.getBien().getIdentificacion().getIdentificacion().length() == 0    ){
                                respuesta = Util.getEtiquetas("sigebi.CargarInventario.ErrorDescripcionReq");
                                }
                            else{
                                
                                sobrante.setDescripcion(carga.getBien().getDescripcion());
                                tomaFisicaSobranteModel.agregar(sobrante);
                            
                            }
                            
                        }
                        else
                            tomaFisicaSobranteModel.agregar(sobrante);
                    }
                    else{
                        if(carga.getDescripcionError() != null && carga.getDescripcionError().length() > 0)
                            respuesta = " - Bien "+ carga.getIdentificacion()+ ": " + carga.getDescripcionError();
                        else
                            respuesta = agregarBien(carga.getBien());
                    }
                    
                    if(respuesta.length() > 0)
                        respErrores.add(respuesta);
                }
                tomaFisicaCommand.setErroresRegistradosCargaUnitaria(respErrores);
                if(respErrores.size()> 0)
                    tomaFisicaCommand.setMostrarErroresCargaUnitaria(true);
                else
                    tomaFisicaCommand.setMostrarErroresCargaUnitaria(false);
                //rendered="#{controllerTomaFisica.tomaFisicaCommand.objetoCarga.mostrarErrores}"
                
                //Se actualiza la lista
                this.buscarTomasFisicasSobrantes();
            }
            else{
                Mensaje.agregarErrorAdvertencia( Util.getEtiquetas("sigebi.CargarInventario.ErrorVacio") );
            }
        }catch(Exception err){
            
        }
    }
    
    
    
    private String agregarBien(Bien bien){
        try{
            
            //Se asigna para presentar los datos por pantalla
            tomaFisicaCommand.getTomaFisicaUnitariaCommand().setBien(bien);

            //Valida que el no se haya registrado
            if(tomaFisicaUnitariaModel.buscarPorBien(tomaFisicaCommand.getTomaFisica(), bien) != null){
                return " - Bien "+bien.getIdentificacion().getIdentificacion()+": "+Util.getEtiquetas("sigebi.error.controllerTomaFisica.agregarBienPorIdentificacionTomasFisicaUnitaria.ya.existe"); 
            }else{
                //Se agrega la toma fisica unitaria
                TomaFisicaUnitaria tomaFisicaUnitaria = new TomaFisicaUnitaria(tomaFisicaCommand.getTomaFisica(), bien);
                tomaFisicaUnitariaModel.agregar(tomaFisicaUnitaria);                    

                //Se limpia la identificacion de la pantalla
                tomaFisicaCommand.getTomaFisicaUnitariaCommand().setIdentificacionBusqueda("");

                //Se listan los datos
                this.buscarTomasFisicasUnitarias();

                return "";// Util.getEtiquetas("sigebi.controllerTomaFisica.agregarBienPorIdentificacionTomasFisicaUnitaria.exitosamente");                
            }
        }
        catch(Exception err){
            return  " - Bien "+bien.getIdentificacion().getIdentificacion()+": "+Util.getEtiquetas("Etiqueta error al cargar el bien a la toma fisica.");
        }
    }
    
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Carga Lotes Excel">
    
    
    
    
    public void subirFileLotes(ActionEvent event) {
        try {
            InputFile inputFile = (InputFile) event.getSource();
            FileInfo fileInfo = inputFile.getFileInfo();
            
            
            File fileName = inputFile.getFile();
            
            FileInputStream file = new FileInputStream(fileName);
            
            leerExcelLotes(fileName);
            //leerArchivoTomaLotes(file);
            tomaFisicaCommand.setMostrarErroresCargaUnitaria(false);
            
        } catch (Exception err) {
            Mensaje.agregarErrorAdvertencia(err, Util.getEtiquetas("sigebi.CargarInventario.LabelErrores"));
        }
    }

    
    public void validarLineaCargaLotes(ObjetoCargaLote lineaCarga){
        try{
            Lote lote = new Lote();
            //Valido si el ID exite
            if(lineaCarga.getIdentificacion().length() > 0){
                //Valido que la Descripción exista
                if(lineaCarga.getDescripcion().length() > 0){
                    //Valido que el ID exista
                    int valInt = 0;
                    if( tryParseInt(lineaCarga.getIdentificacion()) ){
                        lote = loteModel.buscarPorId( Long.parseLong(lineaCarga.getIdentificacion()) );
                        
                        if(lote == null || lote.getId() == null){
                            lineaCarga.setDescripcionError( Util.getEtiquetas("sigebi.CargarInventario.Error.LoteNoEncontrado") );
                        }
                        else{
                            //Valido que la descripción y el ID coinsidan
                            if( lote.getDescripcion().compareTo(lineaCarga.getDescripcion()) == 0 ){
                                //Agrego el valor
                                lineaCarga.setLote(lote);
                            }
                            else{
                                lineaCarga.setDescripcionError( Util.getEtiquetas("sigebi.CargarInventario.Error.LoteNoCoinsiden") );
                            }
                        }
                    }
                    else{
                        lineaCarga.setDescripcionError( Util.getEtiquetas("sigebi.CargarInventario.Error.LoteInvalidId") );
                    }
                    
                }
                else{
                    lineaCarga.setDescripcionError( Util.getEtiquetas("sigebi.CargarInventario.Error.LoteDescripcion") );
                }
                
            }
            else{
                lineaCarga.setDescripcionError( Util.getEtiquetas("sigebi.CargarInventario.Error.LoteId") );
            }
            
            
        }catch(Exception err){
            lineaCarga.setDescripcionError( Util.getEtiquetas("sigebi.CargarInventario.Error.LoteLinea") );
        }
        lineaCarga.setEsValido(lineaCarga.getDescripcionError().length() == 0);
    }
    
    
    public ObjetoCargaLote objetoCargaLote(Row currentRow){
        
        ObjetoCargaLote lineaCarga = tomaFisicaCommand.getNewObjetoCargaLote();
        try{
            
            String desc = "";
            String iden = "";
            String cant = "0";
            Float valInt = 0f; 
            //int type1 = currentRow.getCell(0).getCellType();

            if(currentRow.getCell(0) != null)
                iden = currentRow.getCell(0).toString();
            lineaCarga.setIdentificacion(iden);

            if(currentRow.getCell(1) != null)
                desc = currentRow.getCell(1).toString();
            lineaCarga.setDescripcion(desc);

            if(currentRow.getCell(2) != null)
                cant = currentRow.getCell(2).toString();
            
            //Valido la cantidad
            if(tryParseInt(cant))
                valInt = Float.parseFloat(cant);
            lineaCarga.setCantidad(valInt.intValue());
            
            validarLineaCargaLotes(lineaCarga);
            
            return lineaCarga;
            
        }catch(Exception err){
            return tomaFisicaCommand.getNewObjetoCargaLote();
        }
    }
    
    
    private void leerExcelLotes(File file) throws IOException{
        FileInputStream excelFile = null;
        try{
            excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            ObjetoCargaLote lineaCarga;
            
            List<ObjetoCargaLote> objetosCargaLote = new ArrayList<ObjetoCargaLote>();
            tomaFisicaCommand.setObjetosCargaLote(objetosCargaLote);
            // Row en el que me voy a desplazar 
            int r = 0;
            while (iterator.hasNext()) {
                
                Row currentRow = datatypeSheet.getRow(r);
                
                if(r > 0){
                    
                    if(currentRow != null){
                        if((currentRow.getCell(0) != null) || (currentRow.getCell(1) != null)){
                            lineaCarga = objetoCargaLote(currentRow);
                            if(lineaCarga != null
                                &&lineaCarga.getIdentificacion() != null
                                && lineaCarga.getIdentificacion() != null
                                && lineaCarga.getIdentificacion() != null
                                    )
                                tomaFisicaCommand.getObjetosCargaLote().add(lineaCarga);
                        }
                    }
                    else
                        break;
                }
                r++;
            }
        }
        catch(Exception err){
            Mensaje.agregarErrorAdvertencia(err.getCause().getMessage());
        }
        finally{
            if(excelFile != null)
                excelFile.close();
            
        }
    }
    
    
    private boolean tryParseInt(String value) {  
        try {  
            Float.parseFloat(value);  
            return true;  
         } catch (NumberFormatException e) {  
            return false;  
         }  
    }
   
    
    public void procesarCargaLotes(){
        try{
            if(tomaFisicaCommand.getObjetosCargaLote().size() > 0){
                String respuesta = "";
                List<String> respErrores = new ArrayList();
                int linea = 0;
                for(ObjetoCargaLote carga : tomaFisicaCommand.getObjetosCargaLote()){
                    linea ++;
                    respuesta = "";
                        if(carga.getDescripcionError() != null && carga.getDescripcionError().length() > 0)
                            respuesta = " - Linea: "+linea+" -> "+ carga.getIdentificacion()+ ": " + carga.getDescripcionError();
                        else{
                            tomaFisicaCommand.getTomaFisicaLoteCommand().setCantidad(Long.parseLong(carga.getCantidad()+""));
                            carga.getLote().setIdTemporal(carga.getLote().getId());
                            tomaFisicaCommand.getTomaFisicaLoteCommand().setLote(carga.getLote());
                            
                            respuesta = agregaTomaFisicaLoteCarga(linea);
                        }
                    if(respuesta.length() > 0)
                        respErrores.add(respuesta);
                }
                tomaFisicaCommand.setErroresRegistradosCargaLotes(respErrores);
                    
                //rendered="#{controllerTomaFisica.tomaFisicaCommand.objetoCarga.mostrarErrores}"
                
                //Se actualiza la lista
                this.buscarTomasFisicasSobrantes();
            }
            else{
                Mensaje.agregarErrorAdvertencia( Util.getEtiquetas("sigebi.CargarInventario.ErrorVacio") );
            }
        }catch(Exception err){
            
        }
    }
    
    
    private String agregaTomaFisicaLoteCarga(int linea){    
        
        try{
        if(tomaFisicaCommand.getTomaFisicaLoteCommand().getCantidad() != null 
                && tomaFisicaCommand.getTomaFisicaLoteCommand().getCantidad() != 0 
                && tomaFisicaCommand.getTomaFisicaLoteCommand().getLote() != null 
                && tomaFisicaCommand.getTomaFisicaLoteCommand().getLote().getIdTemporal() > 0){
            
            //Se busca si ya existe la toma por lote          
            TomaFisicaLote tomaFisicaLote = tomaFisicaLoteModel.buscarPorLote(tomaFisicaCommand.getTomaFisica(), tomaFisicaCommand.getTomaFisicaLoteCommand().getLote());            
            
            //Se crea o se actualiza
            if(tomaFisicaLote != null){
                Long cantidad = tomaFisicaLote.getCantidad() + tomaFisicaCommand.getTomaFisicaLoteCommand().getCantidad();
                if(cantidad > 0){
                    tomaFisicaLote.setCantidad(cantidad);
                    tomaFisicaLoteModel.modificar(tomaFisicaLote);
                }else{
                    tomaFisicaLoteModel.eliminar(tomaFisicaLote);
                }
                mensajeActualizaTomaFisicaLote();
            }else{
                if(tomaFisicaCommand.getTomaFisicaLoteCommand().getCantidad() > 0){
                    tomaFisicaLote = new TomaFisicaLote(tomaFisicaCommand.getTomaFisica(), tomaFisicaCommand.getTomaFisicaLoteCommand().getLote(), tomaFisicaCommand.getTomaFisicaLoteCommand().getCantidad());
                    tomaFisicaLoteModel.agregar(tomaFisicaLote);
                    mensajeActualizaTomaFisicaLote();
                }    
            }
        }
        else{
            return Util.getEtiquetas("sigebi.CargarInventario.sinDatos"); 
        }
        }
        catch(Exception err){
            return Util.getEtiquetas("sigebi.CargarInventario.errorLinea"); 
        }
        return "";
    }

    
    
    //</editor-fold>
    
}
