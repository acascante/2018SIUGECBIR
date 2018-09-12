/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.domain.Bien;
import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Solicitud;
import cr.ac.ucr.sigebi.domain.SolicitudDetalle;
import cr.ac.ucr.sigebi.domain.SolicitudSalida;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jairo.cisneros
 */
@Repository(value = "solicitudDao")

public class SolicitudDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;

    @Transactional
    public void agregar(Solicitud solicitud) throws FWExcepcion {
        try {
            this.persist(solicitud);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.agregar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional
    public void modificar(Solicitud solicitud) throws FWExcepcion {
        try {
            this.persist(solicitud);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.modificar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional
    public void modificar(SolicitudDetalle solicitudDetalle) throws FWExcepcion {
        try {
            this.persist(solicitudDetalle);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.modificar", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public Solicitud buscarPorId(Long id) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM Solicitud b WHERE b.id = :id";
            Query query = session.createQuery(sql);
            query.setParameter("id", id);

            return (Solicitud) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.buscarPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional
    public void agregarDetalleSolicitud(SolicitudDetalle solicitudDetalle) throws FWExcepcion {
        try {
            this.persist(solicitudDetalle);
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.agregarDetalle", "Error guardando registro de tipo " + this.getClass(), e.getCause());
        }
    }

    @Transactional(readOnly = true)
    public List<Solicitud> listarDonaciones(String id,
            UnidadEjecutora unidadEjecutora,
            Estado estado,
            Integer tipoSolicitud,
            Tipo tipoDonacion,
            String unidadReceptora,
            String donante,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = this.creaQueryListarDonaciones(id, unidadEjecutora, estado, tipoSolicitud, tipoDonacion, unidadReceptora, donante, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<Solicitud>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.listarDonaciones",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarDonaciones(String id,
            UnidadEjecutora unidadEjecutora,
            Estado estado,
            Integer tipoSolicitud,
            Tipo tipoDonacion,
            String unidadReceptora,
            String donante
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListarDonaciones(id, unidadEjecutora, estado, tipoSolicitud, tipoDonacion, unidadReceptora, donante, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.contarDonaciones",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListarDonaciones(String id,
            UnidadEjecutora unidadEjecutora,
            Estado estado,
            Integer tipoSolicitud,
            Tipo tipoDonacion,
            String unidadReceptora,
            String donante,
            Boolean contar,
            Session session
    ) {

        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(obj) FROM Solicitud obj ");
        } else {
            sql.append("SELECT obj FROM Solicitud obj");
        }

        //Select
        sql.append(" WHERE obj.discriminator = :tipoSolicitud ");

        if (id != null && id.length() > 0) {
            sql.append(" AND upper(obj.id) like upper(:id)");
        } else {

            if (estado != null) {
                sql.append(" AND obj.estado = :estado");
            }

            if (tipoDonacion != null) {
                sql.append(" AND obj.tipoDonacion = :tipoDonacion ");
            }

            if (unidadEjecutora != null) {
                sql.append(" and obj.unidadEjecutora = :unidadEjecutora");
            }

            if (unidadReceptora != null && unidadReceptora.length() > 0) {
                sql.append(" and upper(obj.unidadReceptora.descripcion) like upper(:unidadReceptora)");
            }

            if (donante != null && donante.length() > 0) {
                sql.append(" and upper(obj.donante) like upper(:donante)");
            }
        }

        sql.append(" ORDER BY obj.id desc ");

        Query q = session.createQuery(sql.toString());
        q.setParameter("tipoSolicitud", tipoSolicitud);

        if (id != null && id.length() > 0) {
            q.setParameter("id", '%' + id + '%');
        } else {

            if (estado != null) {
                q.setParameter("estado", estado);
            }

            if (tipoDonacion != null) {
                q.setParameter("tipoDonacion", tipoDonacion);
            }

            if (unidadEjecutora != null) {
                q.setParameter("unidadEjecutora", unidadEjecutora);
            }

            if (unidadReceptora != null && unidadReceptora.length() > 0) {
                q.setParameter("unidadReceptora", '%' + unidadReceptora + '%');
            }

            if (donante != null && donante.length() > 0) {
                q.setParameter("donante", '%' + donante + '%');
            }
        }

        return q;
    }

    @Transactional(readOnly = true)
    public List<SolicitudDetalle> listarDetallesSolicitud(Solicitud solicitud) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM SolicitudDetalle b WHERE b.solicitud = :solicitud";
            Query query = session.createQuery(sql);
            query.setParameter("solicitud", solicitud);
            return (List<SolicitudDetalle>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.listarDetallesSolicitud", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public SolicitudDetalle listarDetallesSolicitud(Solicitud solicitud, Bien bien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql = "SELECT b FROM SolicitudDetalle b WHERE b.solicitud = :solicitud and b.bien = :bien";
            Query query = session.createQuery(sql);
            query.setParameter("solicitud", solicitud);
            query.setParameter("bien", bien);
            return (SolicitudDetalle) query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.listarDetallesSolicitud", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional
    public void eliminarDetalleSolicitud(SolicitudDetalle obj) throws FWExcepcion {
        try {
            this.delete(obj);
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.eliminarDetalleSolicitud",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
    }
    
    @Transactional(readOnly = true)
    public List<Solicitud> movimientosPorBien(Bien bien) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            //String sql = "SELECT m FROM SolicitudDetalle m WHERE m.bien = :bien";
            String sql;
            sql = "SELECT m FROM Solicitud m WHERE m.detalles.bien = :bien";
            Query query = session.createQuery(sql);
            query.setParameter("bien", bien);
            return (List<Solicitud>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.movimientosPorBien", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
    
    @Transactional(readOnly = true)
    public List<SolicitudDetalle> listarDetallesSalidas(Long id, String identificacionBien, Date fechaInicio, Date fechaFin, String orden, String orden1, String orden2, String orden3) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            StringBuilder sql = new StringBuilder("SELECT entity FROM SolicitudDetalle entity WHERE entity.solicitud IN ");
            
            if(fechaInicio != null && fechaFin != null){
                sql.append(" (SELECT sa.solicitud FROM SolicitudAutorizacion sa WHERE sa.fecha BETWEEN :fechaInicio AND :fechaFin) ");
            } else {
                sql.append(" (SELECT sa.solicitud FROM SolicitudAutorizacion sa) ");
            }
            
            if(id != null && id > 0) {
                sql.append(" AND entity.id = :id ");
            } else {
                if(identificacionBien != null && identificacionBien.length() > 0){
                    sql.append(" AND UPPER(entity.bien.identificacion.identificacion) = UPPER(:identificacionBien) ");
                }
            }
            sql.append(" ORDER BY entity.solicitud ");
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
            return (List<SolicitudDetalle>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.listarDetallesSolicitud", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }
        
    @Transactional(readOnly = true)
    public List<SolicitudSalida> listarSalidas(String id,
            UnidadEjecutora unidadEjecutora,
            Estado estado,
            String cedula,
            String nombre,
            Tipo tipo,
            Integer tipoSolicitud,
            Date fecha,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) throws FWExcepcion {
        Session session = this.dao.getSessionFactory().openSession();
        try {
            //Se genera el query para la busqueda
            Query q = creaQueryListarSalidas(id, unidadEjecutora, estado, cedula, nombre, tipo, tipoSolicitud, fecha, false, session);

            //Paginacion
            if (!(pPrimerRegistro.equals(1) && pUltimoRegistro.equals(1))) {
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<SolicitudSalida>) q.list();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.listarSalidas",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    @Transactional(readOnly = true)
    public Long contarSalidas(String id,
            UnidadEjecutora unidadEjecutora,
            Estado estado,
            String cedula,
            String nombre,
            Tipo tipo,
            Integer tipoSolicitud,
            Date fecha            
    ) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListarSalidas(id, unidadEjecutora, estado, cedula, nombre, tipo, tipoSolicitud, fecha, true, session);

            //Se obtienen los resutltados
            return (Long) q.uniqueResult();

        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.solicitudDao.contarSalidas",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();
        }
    }

    private Query creaQueryListarSalidas(String id,
            UnidadEjecutora unidadEjecutora,
            Estado estado,
            String cedula,
            String nombre,
            Tipo tipo,
            Integer tipoSolicitud,
            Date fecha,
            Boolean contar,
            Session session
    ) {

        StringBuilder sql = new StringBuilder(" ");
        if (contar) {
            sql.append("SELECT count(obj) FROM SolicitudSalida obj ");
        } else {
            sql.append("SELECT obj FROM SolicitudSalida obj");
        }

        //Select
        sql.append(" WHERE obj.discriminator = :tipoSolicitud ");

        if (id != null && id.length() > 0) {
            sql.append(" AND upper(obj.id) like upper(:id)");
        } else {
            
            if (estado != null) {
                sql.append(" AND obj.estado = :estado");
            }

            if (tipo != null) {
                sql.append(" AND obj.tipo = :tipo ");
            }

            if (unidadEjecutora != null) {
                sql.append(" and obj.unidadEjecutora = :unidadEjecutora");
            }

            if (cedula != null && cedula.length() > 0) {
                sql.append(" and upper(obj.persona.identificacion) like upper(:cedula)");
            }

            if (nombre != null && nombre.length() > 0) {
                sql.append(" and upper(obj.persona.nombre) like upper(:nombre)");
            }

            if (fecha != null) {
                sql.append(" AND obj.fecha = :fecha ");
            }
        }
        
        sql.append(" ORDER BY obj.id desc ");

        Query q = session.createQuery(sql.toString());
        q.setParameter("tipoSolicitud", tipoSolicitud);

        if (id != null && id.length() > 0) {
            q.setParameter("id", '%' + id + '%');
        } else {

            if (estado != null) {
                q.setParameter("estado", estado);
            }

            if (tipo != null) {
                q.setParameter("tipo", tipo);
            }

            if (unidadEjecutora != null) {
                q.setParameter("unidadEjecutora", unidadEjecutora);
            }

            if (cedula != null && cedula.length() > 0) {
                q.setParameter("cedula", '%' + cedula + '%');
            }
            
            if (nombre != null && nombre.length() > 0) {
                q.setParameter("nombre", '%' + nombre + '%');
            }

            if (fecha != null) {
                q.setParameter("fecha", fecha);
            }
        }
        return q;
    }

}
