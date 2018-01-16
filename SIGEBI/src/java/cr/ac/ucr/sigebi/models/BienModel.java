/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.BienDao;
import cr.ac.ucr.sigebi.entities.BienEntity;
import cr.ac.ucr.sigebi.entities.EstadoEntity;
import cr.ac.ucr.sigebi.entities.LoteEntity;
import cr.ac.ucr.sigebi.entities.RegistroMovimientoEntity;
import cr.ac.ucr.sigebi.entities.UbicacionBien;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "bienModel")
@Scope("request")
public class BienModel {
    
    @Resource
    private BienDao bienDao;
    
    @Resource
    RegistroMovimientoModel registroMovimientoModel;
    
    public BienEntity traerPorId(Integer pId) {
        BienEntity respAux = new BienEntity();
        try {
            respAux = bienDao.traerPorId(pId);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.bien.obtenerTipo",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
        return respAux;
    }
    
    
    
    public List<BienEntity> traerTodo(int unidEjecutora
                                    ){
    
    try {
            return bienDao.traerTodo(unidEjecutora);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.model.bien.obtenerTodoCategoria",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }
    
    
    public List<VistaBienes> traerConFiltros( int unidEjecutora
                                            , String fltPlaca
                                            , String fltDescripcion
                                            , String fltMarca
                                            , String fltModelo
                                            , String fltSerie
                                            , String fltEstado
                                            , int pagina
                                            , int cantReg
                                            ){
    
    try {
            return bienDao.traerConFiltros(unidEjecutora
                                    ,  fltPlaca
                                    ,  fltDescripcion
                                    ,  fltMarca
                                    ,  fltModelo
                                    ,  fltSerie
                                    ,  fltEstado
                                    , pagina
                                    , cantReg
                                    );

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {

            throw new FWExcepcion("sigebi.error.model.bien.traerConFiltros",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }
    public int consultaCantidadRegistros(int unidEjecutora,
             String fltPlaca,
             String fltDescripcion,
             String fltMarca,
             String fltModelo,
             String fltSerie,
             String fltEstado
    ){
        try{
            return bienDao.consultaCantidadRegistros(unidEjecutora
                                    ,  fltPlaca
                                    ,  fltDescripcion
                                    ,  fltMarca
                                    ,  fltModelo
                                    ,  fltSerie
                                    ,  fltEstado
                                    );
        }catch(Exception err){
            
        }
        return 0;
    }
    
    public List<ViewBienEntity> listarBienes(Integer unidEjecutora,
             String fltIdBien,
             String fltDescripcion,
             String fltMarca,
             String fltModelo,
             String fltSerie,
             String fltEstado,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) {
        Integer[] fltEstadosBienes = {Integer.parseInt(fltEstado)};
        return this.listarBienes(unidEjecutora, fltIdBien, fltDescripcion, fltMarca, fltModelo, fltSerie, fltEstadosBienes, pPrimerRegistro, pUltimoRegistro);

    }

    public List<ViewBienEntity> listarBienes(Integer unidEjecutora,
             String fltIdBien,
             String fltDescripcion,
             String fltMarca,
             String fltModelo,
             String fltSerie,
             Integer[] fltEstadosBienes,
             Integer pPrimerRegistro,
             Integer pUltimoRegistro
    ) {

        try {
            return bienDao.listarBienes(unidEjecutora, 
                    fltIdBien, 
                    fltDescripcion, 
                    fltMarca, 
                    fltModelo, 
                    fltSerie, 
                    fltEstadosBienes, 
                    pPrimerRegistro, 
                    pUltimoRegistro);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {

            throw new FWExcepcion("sigebi.error.model.bien.listarBienes",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }
    
    public Long contarBienes(Integer unidEjecutora,
             String fltIdBien,
             String fltDescripcion,
             String fltMarca,
             String fltModelo,
             String fltSerie,
             String fltEstado           
    ) {
        Integer[] fltEstadosBienes = {Integer.parseInt(fltEstado)};
        return this.contarBienes(unidEjecutora, fltIdBien, fltDescripcion, fltMarca, fltModelo, fltSerie, fltEstadosBienes);

    }

    public Long contarBienes(Integer unidEjecutora,
             String fltIdBien,
             String fltDescripcion,
             String fltMarca,
             String fltModelo,
             String fltSerie,
             Integer[] fltEstadosBienes
    ) {

        try {
            return bienDao.contarBienes(unidEjecutora, fltIdBien, fltDescripcion, fltMarca, fltModelo, fltSerie, fltEstadosBienes);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {

            throw new FWExcepcion("sigebi.error.model.bien.contarBienes",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }
    
    public Integer generarId(){
        try{
            return bienDao.obtenerId();
        }catch(Exception err){
            
        }
        return 0;
    }
    
    public String guardarBien(BienEntity bien){
        try{
            
            return bienDao.registrarBien(bien);
        }
        catch(Exception err){
            return err.getMessage();
        }
    }
    
    
    public int placaDisponible(int unidadEjecutoa, boolean capitalizable){
        return bienDao.getPlaca(unidadEjecutoa, capitalizable);
    }
    
    
    public String guardarGarantia( int idBien
                                , String iniGarantia
                                , String finGarantia
                                , String descripcion){
        try{
            return bienDao.guardarGarantia(idBien
                                        , iniGarantia
                                        , finGarantia
                                        , descripcion);
        }
        catch(Exception err){
            return err.getMessage();
        }
    }
    
    
    public List<LoteEntity> getLotes(){
    try {
            return bienDao.getLotes();
        } catch (Exception ex) {
            return new ArrayList<LoteEntity>();
        }
    }
    
    
    public float getMontoCapitalizable(){
        return bienDao.getMontoCapitalizable();
    }

    public void cambiaEstadoBien(Collection<ViewBienEntity> bienesView, EstadoEntity estado, String observacion, Integer telefono){        
        for (ViewBienEntity bienView : bienesView) {   
            BienEntity  bien = bienDao.traerPorId(bienView.getIdBien());
            this.cambiaEstadoBien(bien, estado, observacion, telefono);
        }
    }

    public void cambiaEstadoBien(BienEntity bien, EstadoEntity estado, String observacion, Integer telefono) {
        //Se registra el movimiento
        RegistroMovimientoEntity regisMov = new RegistroMovimientoEntity();
        regisMov.setIdBien(bien);
        regisMov.setFecha(new Date());
        regisMov.setIdEstado(estado);
        regisMov.setIdTipo(bien.getTipoBien());
        regisMov.setObservacion(observacion);
        regisMov.setNumeroPersona(telefono);
        registroMovimientoModel.agregar(regisMov);

        //Se actualiza el estado del bien
        bien.setIdEstado(estado);
        bien.setSeleccionado(false);
        this.actualizarBien(bien);

    }
    
    public void actualizarBien(BienEntity bien){        
        bienDao.actualizarBien(bien);    
    }
    
    
    public String sincronizarBien(BienEntity bien, String usuario){        
        return bienDao.sincronizarBien(bien, usuario);    
    }
    
    public String actualizarBien(BienEntity bien, int idUbicacion){        
        return bienDao.actualizarBien(bien, idUbicacion);    
    }
    
    
    public String registrarUbicacion(UbicacionBien ubicacion){
        return bienDao.registrarUbicacion(ubicacion);
    }
    
    public Long contarBienes(Integer estadoInterno) throws FWExcepcion {
        return bienDao.contarBienes(estadoInterno);
    }
    
    public List<BienEntity> listarBienesEstadoInterno(Integer primerRegistro, Integer ultimoRegistro, Integer estadoInterno) throws FWExcepcion {
        return bienDao.listarBienesEstadoInterno(primerRegistro, ultimoRegistro, estadoInterno);
    }
}