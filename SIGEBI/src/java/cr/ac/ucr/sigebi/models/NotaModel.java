/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.NotaDao;
import cr.ac.ucr.sigebi.domain.Nota;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge.serrano
 */
@Service(value = "notaModel")
@Scope("request")
public class NotaModel {

    @Resource
    private NotaDao notaDao;

    public Nota obtenerValor(Long pId) {
        return notaDao.traerPorId(pId);
    }

    public List<Nota> traerTodo(Long idBien) {

        return notaDao.traerTodo(idBien);
    }

    public void guardarNuevo(Nota nota) {
        notaDao.guardarNota(nota);
    }

    public void eliminarNota(Nota nota){
        notaDao.eliminarNota(nota);
    }
    
}
