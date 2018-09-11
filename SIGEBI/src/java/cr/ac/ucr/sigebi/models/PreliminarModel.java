/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.PreliminarDao;
import cr.ac.ucr.sigebi.domain.Preliminar;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service("preliminarModel")
public class PreliminarModel {
    
    @Resource
    private PreliminarDao preliminarDao;
    
    public List<Preliminar> listar() throws FWExcepcion {
        return preliminarDao.listar();
    }

    public void guardar(Preliminar preliminar) throws FWExcepcion {
        preliminarDao.guardar(preliminar);
    }

    public List<Preliminar> listar(
                                    Integer primerRegistro
                                  , Integer ultimoRegistro
                                  , Long fltId
                                  , String fltIdentificacion
                                  , String fltDescripcion

                                  , String fltUnidad
                                  , String fltMarca
                                  , String fltModelo
                                  , String fltSerie
                                  , String fltOrden

                                  , String fltFactura
                                  , Long fltIdEstado) throws FWExcepcion {
        
        return preliminarDao.listar( primerRegistro
                                  ,  ultimoRegistro
                                  , fltId
                                  , fltIdentificacion
                                  , fltDescripcion

                                  , fltUnidad
                                  , fltMarca
                                  , fltModelo
                                  , fltSerie
                                  , fltOrden

                                  , fltFactura
                                  , fltIdEstado);
    }
    
    public Long contar(Long id
                                  , String identificacion
                                  , String descripcion

                                  , String unidad
                                  , String marca
                                  , String modelo
                                  , String serie
                                  , String orden

                                  , String factura
                                  , Long idEstado) throws FWExcepcion {
        
        
        return preliminarDao.contar(id
                                    , identificacion
                                    , descripcion

                                    , unidad
                                    , marca
                                    , modelo
                                    , serie
                                    , orden

                                    , factura
                                    , idEstado);
        
    }
}
