/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.PersonaDao;
import cr.ac.ucr.sigebi.domain.Persona;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service(value = "personaModel")

public class PersonaModel {
    
    @Resource private PersonaDao personaDao;
    
    public List<Persona> listar() throws FWExcepcion {
        return personaDao.listar();
    }
    
    public Long contar(Boolean estudiante, Boolean funcionario, Long id, String nombre, String primerApellido, String segundoApellido) throws FWExcepcion {
        return personaDao.contar(estudiante, funcionario, id, nombre, primerApellido, segundoApellido);
    }
    
    public List<Persona> listar(Integer primerRegistro, Integer ultimoRegistro, Boolean estudiante, Boolean funcionario, Long id, String nombre, String primerApellido, String segundoApellido) throws FWExcepcion {
        return personaDao.listar(primerRegistro, ultimoRegistro, estudiante, funcionario, id, nombre, primerApellido, segundoApellido);
    }
}