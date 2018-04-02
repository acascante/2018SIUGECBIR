/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.ProveedorDao;
import cr.ac.ucr.sigebi.domain.Proveedor;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "proveedorModel")

public class ProveedorModel {
    
    @Resource
    private ProveedorDao proveedorDao;
    
    public List<Proveedor> listar() throws FWExcepcion {
        return proveedorDao.listar();
    }
    
    public List<Proveedor> listar(String identificacion, String nombre, int primerRegistro, int cantRegistroPorPagina ) throws FWExcepcion {
        return proveedorDao.listar(identificacion, nombre, primerRegistro, cantRegistroPorPagina);
    }
    
    public Long contar(String identificacion, String nombre){
        return proveedorDao.contar(identificacion, nombre);
    }
    
    public Proveedor buscarPorId(Long id) throws FWExcepcion {
        return proveedorDao.buscarPorId(id);
    }

    public Proveedor buscarPorCedula(String cedula) throws FWExcepcion {
        return proveedorDao.buscarPorCedula(cedula);
    }

}