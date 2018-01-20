/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.FaltaProveedorDao;
import cr.ac.ucr.sigebi.entities.PersonaEntity;
import cr.ac.ucr.sigebi.entities.ProveedorEntity;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "proveedorModel")
@Scope("request")
public class ProveedorModel {
    
    @Resource
    private FaltaProveedorDao proveedorDao;
    
    
     /**
     * Obtiene la información detallada de un estudiante
     *
     * @param pCarne Carné universitario
     * @param pCarrera Carrera seleccionada
     * @return
     */
    public ProveedorEntity buscarProveedor(Integer pIdTipo) {
        ProveedorEntity tipoAux = new ProveedorEntity();
        try {
            tipoAux = proveedorDao.traerPorId(pIdTipo);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.obtenerTipo",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
        return tipoAux;
    }
    
    public List<PersonaEntity> traerTodos() {
        List<PersonaEntity> tipoAux = new ArrayList<PersonaEntity>();
        try {
            
            tipoAux = proveedorDao.traerTodo();

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.obtenerTipo",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
        return tipoAux;
    }
    
    public List<PersonaEntity> filtroProveedores(String identificacion
                                                  ,String nombre) {
         List<PersonaEntity> tipoAux = new ArrayList<PersonaEntity>();
        try {
            
            tipoAux = proveedorDao.filtroProveedores( identificacion
                                                    , nombre);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.obtenerTipo",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
        return tipoAux;
    }
}
