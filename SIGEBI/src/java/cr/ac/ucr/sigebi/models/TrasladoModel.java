/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.sigebi.daos.TrasladosDao;
import cr.ac.ucr.sigebi.daos.UnidadEjecutoraDao;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import cr.ac.ucr.sigebi.domain.SolicitudDetalleTraslado;
import cr.ac.ucr.sigebi.domain.SolicitudTraslado;
import cr.ac.ucr.sigebi.utils.Constantes;
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

    @Resource
    private UnidadEjecutoraDao unidadEjecutoraDao;

        
    public void guardar(SolicitudTraslado obj) {
        trasladoDao.guardar(obj);
    }
    
    public void guardarBienes(List<SolicitudDetalleTraslado> bienes){
        trasladoDao.guardarBienes(bienes);
    }
    
    public void eliminarBienes(List<SolicitudDetalleTraslado> bienes){
        trasladoDao.eliminarBienes(bienes);
    }
    
    public   void guardarBien(SolicitudDetalleTraslado bien){
        trasladoDao.guardarBien(bien);
    }
    
    public SolicitudTraslado traerPorId(Integer pId) {
        return trasladoDao.traerPorId(pId);
    }
    
    public List<SolicitudTraslado> traerTodo(UnidadEjecutora unidadEjecutora) {
        return trasladoDao.traerTodo(unidadEjecutora);
    }
    
    public List<SolicitudDetalleTraslado> traerBienesTraslado(SolicitudTraslado traslado) {
        return trasladoDao.traerBienesTraslado(traslado);
    }
    
    public List<SolicitudTraslado> trasladosListado(Long idUnidadEjecutora, String fltIdTraslado, String fltUnidadDestino, String fltFecha, String fltEstados, int primerRegistro, int ultimoRegistro) {
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return trasladoDao.trasladosListado(null, fltIdTraslado, fltUnidadDestino, fltFecha, fltEstados, primerRegistro, ultimoRegistro);
        }
        else {
            return trasladoDao.trasladosListado(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), fltIdTraslado, fltUnidadDestino, fltFecha, fltEstados, primerRegistro, ultimoRegistro);
        }        
    }
    
    public Long contarTrasladosListado(Long idUnidadEjecutora, String fltIdTraslado, String fltUnidadDestino, String fltFecha, String fltEstados){
        if (idUnidadEjecutora.equals(Constantes.DEFAULT_ID)) {
            return trasladoDao.contarTrasladosListado(null, fltIdTraslado, fltUnidadDestino, fltFecha, fltEstados);
        }
        else {
            return trasladoDao.contarTrasladosListado(unidadEjecutoraDao.buscarPorId(idUnidadEjecutora), fltIdTraslado, fltUnidadDestino, fltFecha, fltEstados);
        }
    }    
}
