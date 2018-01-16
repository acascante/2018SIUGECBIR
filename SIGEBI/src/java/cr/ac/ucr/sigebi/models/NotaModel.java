/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.models;

import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.daos.NotaDao;
import cr.ac.ucr.sigebi.entities.NotaEntity;
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

    public NotaEntity obtenerValor(Integer pId) {
        NotaEntity respAux = new NotaEntity();
        try {
            respAux = notaDao.traerPorId(pId);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.obteneClasificacion",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
        return respAux;
    }

    public List<NotaEntity> traerTodo(Integer idBien) {

        try {

            return notaDao.traerTodo(idBien);

        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.notaDao.traerTodo ",
                    "Error obtener los datos de un tipo, error generado en la clase " + this.getClass()
                    + " en el método obtenerTipo(Integer pIdTipo)", ex);
        }
    }

    public String guardarNuevo(NotaEntity nota) {
        try {
            return notaDao.guardarNota(nota);

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Integer obtenerId() {
        try {
            return notaDao.obtenerId();
        } catch (Exception err) {
            return 1;
        }

    }

    
    
    public String eliminarNota(NotaEntity nota){
        try{
            return notaDao.eliminarNota(nota);
        }catch(Exception err){
            return err.getMessage();
        }
    }
    
}
