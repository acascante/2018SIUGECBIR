/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.MonedaDao;
import cr.ac.ucr.sigebi.domain.Moneda;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "monedaModel")
@Scope("request")
public class MonedaModel {

    @Resource
    private MonedaDao monedaDao;

    public Moneda obtenerValor(String pCodCateg) throws FWExcepcion {
        return monedaDao.traerPorId(pCodCateg);
    }

    public List<Moneda> traerTodo() throws FWExcepcion {
        return monedaDao.traerTodo();
    }

}
