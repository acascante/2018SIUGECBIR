/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Documento;
import cr.ac.ucr.sigebi.domain.DocumentoActa;
import cr.ac.ucr.sigebi.domain.DocumentoDetalle;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import java.util.List;

import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "ActaDao")

public class ActaDao extends GenericDaoImpl {
    
    @Autowired
    private DaoHelper dao;
    
    @Transactional
    public DocumentoActa traerPorId(Integer pId) {
        Session session = dao.getSessionFactory().openSession();
        DocumentoActa resp = new DocumentoActa();
        try {
            // De momento utilizamos la referencia como el id del bien
            String sql = "from DocumentoActa s where s.id = :pid";
            Query q = session.createQuery(sql);
            q.setParameter("pid",pId);
            
            List l = q.list();
            resp = (DocumentoActa) l.get(0);
            
            session.close();
            return resp;
        } catch (Exception e) {
            return resp;
        }finally {
            session.close();
        }
    }
    
    @Transactional
    public List<DocumentoActa> listar(Long unidadEjecutora) {
        try {
            return dao.getHibernateTemplate().find("from Acta"); 
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.ActaDao.listar", "Error obtener los registros " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public List<DocumentoDetalle> traerBienesActa(Documento acta) {
        Session session = dao.getSessionFactory().openSession();
        try {
            
            String sql = "SELECT det FROM DocumentoDetalle det WHERE det.documento = :acta";
            Query query = session.createQuery(sql);
            query.setParameter("acta", acta);
            
            return (List<DocumentoDetalle>) query.list();
        } catch (Exception e) {
            return null;
        }finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<DocumentoDetalle> listarDetalles(Estado estado, Long id, String identificacionBien, Date fechaInicio, Date fechaFin, String orden, String orden1, String orden2, String orden3) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            StringBuilder sql = new StringBuilder("SELECT entity FROM DocumentoDetalle entity WHERE entity.documento IN ");
            
            if(fechaInicio != null && fechaFin != null){
                sql.append(" (SELECT da.documento FROM DocumentoAutorizacion da WHERE da.fecha BETWEEN :fechaInicio AND :fechaFin) ");
            } else {
                sql.append(" (SELECT da.documento FROM DocumentoAutorizacion da) ");
            }
            
            sql.append(" AND entity.documento.estado = :estado AND entity.discriminador = 2");
            if(id != null && id > 0) {
                sql.append(" AND entity.documento.id = :id ");
            } else {
                if(identificacionBien != null && identificacionBien.length() > 0){
                    sql.append(" AND UPPER(entity.bien.identificacion.identificacion) = UPPER(:identificacionBien) ");
                }
            }
            sql.append(" ORDER BY entity.documento ");
            if (orden1 != null && orden1.length() > 0) {
                sql.append(", entity.");
                sql.append(orden1);
                if (orden2 != null && orden2.length() > 0) {
                    sql.append(", entity.");
                    sql.append(orden2);
                    if (orden3 != null && orden3.length() > 0) {
                        sql.append(", entity.");
                        sql.append(orden3);
                    }
                }
                sql.append(" ").append(orden);
            }
            
            Query query = session.createQuery(sql.toString());
            
            if(fechaInicio != null && fechaFin != null) {
                query.setParameter("fechaInicio", fechaInicio);
                query.setParameter("fechaFin", fechaFin);
            }
            if(id != null && id > 0) {
                query.setParameter("id", id);
            } else {
                if(identificacionBien != null && identificacionBien.length() > 0) {
                    query.setParameter("identificacionBien", identificacionBien);
                }                
            }
            return (List<DocumentoDetalle>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.informeTecnico.listarInformes", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional
    public void guardar(DocumentoActa valor) {
        try {
            persist(valor);
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardar", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardar", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    @Transactional
    public void eliminarBienes(List<DocumentoDetalle> valores) {
        try {
            for(DocumentoDetalle valor : valores) {
                delete(valor);
            }
            
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    @Transactional
    public void guardarBienes(List<DocumentoDetalle> valores) {
        try {
            for(DocumentoDetalle valor : valores) {
                persist(valor);
            }
            
        } catch (DataAccessException e) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), e.getCause());
        } catch (Exception ex) {
            throw new FWExcepcion("sigebi.error.ActaDao.guardarBienes", "Error guardar el registro de tipo " + this.getClass(), ex.getCause());
        }
    }
    
    
    @Transactional(readOnly = true)
    public Long contarActas(UnidadEjecutora unidadEjecutora,
                            String fltIdActa,
                            String fltAutorizacion,
                            String fltEstado,
                            String fltFecha
    ) {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryActasConsultar(unidadEjecutora
                                                , fltIdActa
                                                , fltAutorizacion
                                                , fltEstado
                                                , fltFecha
                                                , true
                                                , session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.dao.informeTecnico.contarInformes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    
    @Transactional(readOnly = true)
    public List<DocumentoActa> listarActas(UnidadEjecutora unidadEjecutora,
                                        String fltIdActa,
                                        String fltAutorizacion,
                                        String fltEstado,
                                        String fltFecha,
                                        Integer pPrimerRegistro,
                                        Integer pUltimoRegistro
    ) {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryActasConsultar(unidadEjecutora
                                                , fltIdActa
                                                , fltAutorizacion
                                                , fltEstado
                                                , fltFecha
                                                , false
                                                , session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<DocumentoActa>) q.list();

        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.dao.informeTecnico.listarInformes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    
    private Query creaQueryActasConsultar(UnidadEjecutora unidadEjecutora,
                                        String fltIdActa,
                                        String fltAutorizacion,
                                        String fltEstado,
                                        String fltFecha,
                                        Boolean contar,
                                        Session session
    ) {
        StringBuilder sql = new StringBuilder("SELECT");

        if (contar) {
            sql.append(" count(s) FROM DocumentoActa s ");
        } else {
            sql.append(" s FROM DocumentoActa s ");
        }
        if (unidadEjecutora == null) {
            sql.append("WHERE 1 = 1 ");
        } else {
            sql.append("WHERE s.unidadEjecutora = :pnumUnidadEjec ");
        }

        if (fltIdActa != null && fltIdActa.length() > 0) {
            sql.append(" AND str( s.id  ) like :fltIdActa ");
        }
        if (fltAutorizacion != null && fltAutorizacion.length() > 0) {
            sql.append(" AND upper(s.autorizacion) like upper(:fltAutorizacion) ");
        }
        if (fltEstado != null && fltEstado.length() > 0) {
            sql.append(" AND str( s.estado.id ) = :fltEstado");
        }
        if(fltFecha != null && fltFecha.length() > 0) {
            sql.append(" AND to_char(s.fecha, 'YYYY-MM-DD') like upper(:fltFecha) ");
        }

        Query q = session.createQuery(sql.toString());
        if (unidadEjecutora != null) {
            q.setParameter("pnumUnidadEjec", unidadEjecutora);
        }
        if (fltIdActa != null && fltIdActa.length() > 0) {
            q.setParameter("fltIdActa", '%' + fltIdActa + '%');
        }
        if (fltAutorizacion != null && fltAutorizacion.length() > 0) {
            q.setParameter("fltAutorizacion", '%' + fltAutorizacion + '%');
        }
        if (fltEstado != null && fltEstado.length() > 0) {
            q.setParameter("fltEstado", fltEstado);
        }
        if(fltFecha != null && fltFecha.length() > 0) {
            q.setParameter("fltFecha", '%' + fltFecha.replace('/', '-') + '%');
        }

        return q;
    }
}