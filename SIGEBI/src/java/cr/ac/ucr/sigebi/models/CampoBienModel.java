/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.CampoBienDao;
import cr.ac.ucr.sigebi.domain.CampoBien;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro.cascante
 */
@Service("campoBienModel")
public class CampoBienModel {
    
    @Resource private CampoBienDao campoBienDao;
    
    public List<CampoBien> listar() throws FWExcepcion {
        return campoBienDao.listar();
    }
    
    public List<CampoBien> listar(Integer primerRegistro, Integer ultimoRegistro)throws FWExcepcion {
        return campoBienDao.listar(primerRegistro, ultimoRegistro);
    }
    
    public Long contar() throws FWExcepcion {
        return campoBienDao.contar();
    }
    
    public CampoBien buscarPorId(Long id) throws FWExcepcion {
        return campoBienDao.buscarPorId(id);
    }
}
