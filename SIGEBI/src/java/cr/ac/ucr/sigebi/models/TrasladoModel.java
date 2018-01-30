/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.TrasladosDao;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.TrasladoDetalle;
import cr.ac.ucr.sigebi.domain.Traslado;
import cr.ac.ucr.sigebi.entities.UnidadEjecutoraEntity;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "trasladoModel")
@Scope("request")
public class TrasladoModel {
    
    @Resource
    private TrasladosDao trasladoDao;

    
    public void guardar(Traslado obj) {
        trasladoDao.guardar(obj);
    }
    
    public void guardarBienes(List<TrasladoDetalle> bienes){
        trasladoDao.guardarBienes(bienes);
    }
    
    public   void guardarBien(TrasladoDetalle bien){
        trasladoDao.guardarBien(bien);
    }
    
    public void eliminarBienes(Traslado traslado, Estado estado){
        trasladoDao.eliminarBienes(traslado, estado);
    }
    
    public Traslado traerPorId(Integer pId) {
        return trasladoDao.traerPorId(pId);
    }
    
    public List<Traslado> traerTodo(UnidadEjecutoraEntity unidadEjecutora) {
        return trasladoDao.traerTodo(unidadEjecutora);
    }
    
    public List<TrasladoDetalle> traerBienesTraslado(Integer trasladoId) {
        return trasladoDao.traerBienesTraslado(trasladoId);
    }
    
    public List<Traslado> trasladosListado(
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
