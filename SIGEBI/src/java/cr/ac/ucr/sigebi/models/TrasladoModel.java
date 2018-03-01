/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.TrasladosDao;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.TrasladoDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudTraslado;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "trasladoModel")

public class TrasladoModel {
    
    @Resource
    private TrasladosDao trasladoDao;

    
    public void guardar(SolicitudTraslado obj) {
        trasladoDao.guardar(obj);
    }
    
    public void guardarBienes(List<TrasladoDetalle> bienes){
        trasladoDao.guardarBienes(bienes);
    }
    
    public void eliminarBienes(List<TrasladoDetalle> bienes){
        trasladoDao.eliminarBienes(bienes);
    }
    
    public   void guardarBien(TrasladoDetalle bien){
        trasladoDao.guardarBien(bien);
    }
    
    public SolicitudTraslado traerPorId(Integer pId) {
        return trasladoDao.traerPorId(pId);
    }
    
    public List<SolicitudTraslado> traerTodo(UnidadEjecutora unidadEjecutora) {
        return trasladoDao.traerTodo(unidadEjecutora);
    }
    
    public List<TrasladoDetalle> traerBienesTraslado(SolicitudTraslado traslado) {
        return trasladoDao.traerBienesTraslado(traslado);
    }
    
    public List<SolicitudTraslado> trasladosListado(
            UnidadEjecutora unidadEjecutora
            , String fltIdTraslado
            , String fltUnidadOrigen
            , String fltUnidadDestino
            , String fltFecha
            , String fltEstados
            , int primerRegistro
            , int ultimoRegistro
    ) {
        return trasladoDao.trasladosListado(
                unidadEjecutora
                , fltIdTraslado
                , fltUnidadOrigen
                , fltUnidadDestino
                , fltFecha
                , fltEstados
                , primerRegistro
                , ultimoRegistro
            );
    }
    
    
    public Long contarTrasladosListado(
              UnidadEjecutora unidadEjecutora
            , String fltIdTraslado
            , String fltUnidadOrigen
            , String fltUnidadDestino
            , String fltFecha
            , String fltEstados
    ){
        return trasladoDao.contarTrasladosListado(
                unidadEjecutora
                , fltIdTraslado
                , fltUnidadOrigen
                , fltUnidadDestino
                , fltFecha
                , fltEstados
            );
    }
    
}
