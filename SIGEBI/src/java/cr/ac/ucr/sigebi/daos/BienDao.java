/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.daos;

import cr.ac.ucr.framework.daoHibernate.DaoHelper;
import cr.ac.ucr.framework.daoImpl.GenericDaoImpl;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.sigebi.models.VistaBienes;
import cr.ac.ucr.sigebi.entities.BienEntity;
import cr.ac.ucr.sigebi.entities.ClasificacionEntity;
import cr.ac.ucr.sigebi.entities.LoteEntity;
import cr.ac.ucr.sigebi.entities.PlacasEntity;
import cr.ac.ucr.sigebi.entities.SincronizarEntity;
import cr.ac.ucr.sigebi.entities.SubClasificacionEntity;
import cr.ac.ucr.sigebi.entities.UbicacionBien;
import cr.ac.ucr.sigebi.entities.ViewBienEntity;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jorge.serrano
 */
@Repository(value = "bienDao")
@Scope("request")
public class BienDao extends GenericDaoImpl {

    @Autowired
    private DaoHelper dao;
    

    @Transactional
    public BienEntity traerPorId(Integer pId) {
        BienEntity tipoAux = new BienEntity();
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "pIdBien";
            Object[] params = new Object[1];
            params[0] = pId;
            tipoAux = (BienEntity) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("BienEntity.traerPorId", nameParams, params).get(0);
        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.dao.bien.traerPorId", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }
        return tipoAux;
    }

    public List<BienEntity> traerTodo(int unidEjecutora) {
        try {
            String[] nameParams = new String[1];
            nameParams[0] = "pnumUnidadEjec";
            Object[] params = new Object[1];
            params[0] = unidEjecutora;
            return (List<BienEntity>) dao.getHibernateTemplate().findByNamedQueryAndNamedParam("BienEntity.traeTodo", nameParams, params);

        } catch (Exception e) {
            throw new FWExcepcion("sigebi.error.dao.bien.traerTodo",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }

    }
    
    @Transactional
    public List<VistaBienes> traerConFiltros(int unidEjecutora
            , String fltPlaca
            , String fltDescripcion
            , String fltMarca
            , String fltModelo
            , String fltSerie
            , String fltEstado
            , int pagina
            , int cantReg
    ) {
         Session session = dao.getSessionFactory().openSession();
         List<VistaBienes> resp = new ArrayList<VistaBienes>();
        try {

            String sql = "        SELECT * \n" +
                        "        FROM ( \n" +
                        "                \n" +
                        "            SELECT CONS.* \n" +
                        "                      , ROWNUM NUM\n" +
                        "            FROM \n" +
                        "            (\n" +
                        "            \n" +
                        "                SELECT BIEN.ID_BIEN\n" +
                        "                      , PLACA.NUMERO_IDENTIFICACION PLACA\n" +
                        "                      , BIEN.DESCRIPCION\n" +
                        "                      , CARACT.MARCA\n" +
                        "                      , CARACT.MODELO\n" +
                        "                      , CARACT.SERIE\n" +
                        "                      \n" +
                        "                      , ESTADO.NOMBRE\n" +
                        "                      , BIEN.ID_ESTADO\n" +
                        "                      \n" +
                        "                \n" +
                        "                FROM SIGEBI_OAF.SGB_BIEN BIEN\n" +
                        "                    LEFT JOIN SIGEBI_OAF.SGB_IDENTIFICACION PLACA\n" +
                        "                    ON (BIEN.ID_BIEN = PLACA.ID_BIEN)\n" +
                        "                    LEFT JOIN SIGEBI_OAF.SGB_ESTADO ESTADO\n" +
                        "                    ON(BIEN.ID_ESTADO = ESTADO.ID_ESTADO)\n" +
                        "                    FULL OUTER JOIN\n" +
                        "                (\n" +
                        "                    SELECT NVL(MA.BIEN, NVL(MO.BIEN, SE.BIEN ) ) AS ID_BIEN\n" +
                        "                        , MA.MARCA\n" +
                        "                        , MO.MODELO\n" +
                        "                        , SE.SERIE\n" +
                        "                    FROM\n" +
                        "                    \n" +
                        "                    (\n" +
                        "                        SELECT DT.ID_BIEN AS BIEN, DT.DETALLE AS MARCA\n" +
                        "                        FROM SIGEBI_OAF.SGB_TIPO TP\n" +
                        "                            LEFT JOIN SIGEBI_OAF.SGB_DATO_BIEN DT\n" +
                        "                            ON( TP.ID_TIPO = DT.ID_TIPO )\n" +
                        "                        WHERE DOMINIO = 'CARACTERISTICA'\n" +
                        "                            AND(NOMBRE = 'MARCA')\n" +
                        "                    ) MA FULL OUTER JOIN\n" +
                        "                    (\n" +
                        "                        SELECT DT.ID_BIEN AS BIEN, DT.DETALLE AS MODELO\n" +
                        "                        FROM SIGEBI_OAF.SGB_TIPO TP\n" +
                        "                            LEFT JOIN SIGEBI_OAF.SGB_DATO_BIEN DT\n" +
                        "                            ON( TP.ID_TIPO = DT.ID_TIPO )\n" +
                        "                        WHERE DOMINIO = 'CARACTERISTICA'\n" +
                        "                            AND(NOMBRE = 'MODELO')\n" +
                        "                    ) MO\n" +
                        "                    ON (MA.BIEN = MO.BIEN)\n" +
                        "                    FULL OUTER JOIN\n" +
                        "                    (\n" +
                        "                        SELECT DT.ID_BIEN AS BIEN, DT.DETALLE AS SERIE\n" +
                        "                        FROM SIGEBI_OAF.SGB_TIPO TP\n" +
                        "                            LEFT JOIN SIGEBI_OAF.SGB_DATO_BIEN DT\n" +
                        "                            ON( TP.ID_TIPO = DT.ID_TIPO )\n" +
                        "                        WHERE DOMINIO = 'CARACTERISTICA'\n" +
                        "                            AND(NOMBRE = 'SERIE')\n" +
                        "                    ) SE\n" +
                        "                    ON (SE.BIEN = MO.BIEN OR SE.BIEN = MA.BIEN)\n" +
                        "                    \n" +
                        "                ) CARACT\n" +
                        "                ON(BIEN.ID_BIEN = CARACT.ID_BIEN)\n                " +
                        "                     WHERE BIEN.NUM_UNIDAD_EJEC = :pUnidadEjecutora \n"+
                        "                   AND UPPER(PLACA.NUMERO_IDENTIFICACION) LIKE UPPER(:pPlaca) \n"+
                        "                   AND UPPER(BIEN.DESCRIPCION) LIKE UPPER(:pDescripcion)\n";
                        if(fltMarca.length() > 0)
                            sql += " AND UPPER(CARACT.MARCA) LIKE UPPER(:pMarca)\n";
                        if(fltModelo.length() > 0)
                            sql += " AND UPPER(CARACT.MODELO) LIKE UPPER(:pModelo)\n";
                    
                        if(fltSerie.length() > 0)
                            sql += "  AND UPPER(CARACT.SERIE) LIKE UPPER(:pSerie)\n";
                    
                        if( ! fltEstado.equals("NA"))
                            sql += " AND BIEN.ID_ESTADO = :pEstado ";
                        sql += " ORDER BY BIEN.DESCRIPCION\n " +
                                "            ) CONS\n" +
                                "            \n" +
                                "        ) PAG\n"+
                                "         WHERE PAG.NUM > :pRegIni \n" +
                                "           AND PAG.NUM <= :pRegFin";
            
            SQLQuery q = session.createSQLQuery(sql);
            
            q.setParameter("pUnidadEjecutora", unidEjecutora);
            q.setParameter("pPlaca", "%"+fltPlaca+"%");
            q.setParameter("pDescripcion", "%"+fltDescripcion+"%");
            
            
            if(fltMarca.length() > 0)
                q.setParameter("pMarca", "%"+fltMarca+"%");
            if(fltModelo.length() > 0)
                q.setParameter("pModelo", "%"+fltModelo+"%");
            if(fltSerie.length() > 0)
                q.setParameter("pSerie", "%"+fltSerie+"%");
            if( ! fltEstado.equals("NA"))
                q.setParameter("pEstado", fltEstado);
            
            int regIni = (pagina - 1) * cantReg;
            int regFin = pagina * cantReg;
            
            q.setParameter("pRegIni", regIni);
            q.setParameter("pRegFin", regFin);
            
            List l = q.list();


            session.close();

            
            
            for ( Object result :  l) {
                Object[] val = (Object[]) result;
                VistaBienes valor = new VistaBienes( Integer.parseInt(val[0].toString())
                                                , (String) val[1]
                                                , (String) val[2]
                                                , (String) val[3]
                                                , (String) val[4]
                                                , (String) val[5]
                                                , (String) val[6]
                                                , Integer.parseInt(val[7].toString())
                                                , Integer.parseInt(val[8].toString())
                                         );
                resp.add(valor);
            }
            return resp;
            
        } catch (Exception err) {
            session.close();
        }

        if (session.isOpen()) {
            session.close();
        }
        return null;

    }

    
    public int consultaCantidadRegistros(int unidEjecutora
             , String fltPlaca
             , String fltDescripcion
             , String fltMarca
             , String fltModelo
             , String fltSerie
             ,String fltEstado
    ) {
         Session session = dao.getSessionFactory().openSession();
         int resp = -1;
        try {

            String sql = "        SELECT * \n" +
                        "        FROM ( \n" +
                        "                \n" +
                        "            SELECT COUNT(*) CANT_REGISTROS\n" +
                        "            FROM \n" +
                        "            (\n" +
                        "            \n" +
                        "                SELECT BIEN.ID_BIEN\n" +
                        "                      , PLACA.NUMERO_IDENTIFICACION PLACA\n" +
                        "                      , BIEN.DESCRIPCION\n" +
                        "                      , CARACT.MARCA\n" +
                        "                      , CARACT.MODELO\n" +
                        "                      , CARACT.SERIE\n" +
                        "                      \n" +
                        "                      , ESTADO.NOMBRE\n" +
                        "                      , BIEN.ID_ESTADO\n" +
                        "                      \n" +
                        "                \n" +
                        "                FROM SIGEBI_OAF.SGB_BIEN BIEN\n" +
                        "                    LEFT JOIN SIGEBI_OAF.SGB_IDENTIFICACION PLACA\n" +
                        "                    ON (BIEN.ID_BIEN = PLACA.ID_BIEN)\n" +
                        "                    LEFT JOIN SIGEBI_OAF.SGB_ESTADO ESTADO\n" +
                        "                    ON(BIEN.ID_ESTADO = ESTADO.ID_ESTADO)\n" +
                        "                    FULL OUTER JOIN\n" +
                        "                (\n" +
                        "                    SELECT NVL(MA.BIEN, NVL(MO.BIEN, SE.BIEN ) ) AS ID_BIEN\n" +
                        "                        , MA.MARCA\n" +
                        "                        , MO.MODELO\n" +
                        "                        , SE.SERIE\n" +
                        "                    FROM\n" +
                        "                    \n" +
                        "                    (\n" +
                        "                        SELECT DT.ID_BIEN AS BIEN, DT.DETALLE AS MARCA\n" +
                        "                        FROM SIGEBI_OAF.SGB_TIPO TP\n" +
                        "                            LEFT JOIN SIGEBI_OAF.SGB_DATO_BIEN DT\n" +
                        "                            ON( TP.ID_TIPO = DT.ID_TIPO )\n" +
                        "                        WHERE DOMINIO = 'CARACTERISTICA'\n" +
                        "                            AND(NOMBRE = 'MARCA')\n" +
                        "                    ) MA FULL OUTER JOIN\n" +
                        "                    (\n" +
                        "                        SELECT DT.ID_BIEN AS BIEN, DT.DETALLE AS MODELO\n" +
                        "                        FROM SIGEBI_OAF.SGB_TIPO TP\n" +
                        "                            LEFT JOIN SIGEBI_OAF.SGB_DATO_BIEN DT\n" +
                        "                            ON( TP.ID_TIPO = DT.ID_TIPO )\n" +
                        "                        WHERE DOMINIO = 'CARACTERISTICA'\n" +
                        "                            AND(NOMBRE = 'MODELO')\n" +
                        "                    ) MO\n" +
                        "                    ON (MA.BIEN = MO.BIEN)\n" +
                        "                    FULL OUTER JOIN\n" +
                        "                    (\n" +
                        "                        SELECT DT.ID_BIEN AS BIEN, DT.DETALLE AS SERIE\n" +
                        "                        FROM SIGEBI_OAF.SGB_TIPO TP\n" +
                        "                            LEFT JOIN SIGEBI_OAF.SGB_DATO_BIEN DT\n" +
                        "                            ON( TP.ID_TIPO = DT.ID_TIPO )\n" +
                        "                        WHERE DOMINIO = 'CARACTERISTICA'\n" +
                        "                            AND(NOMBRE = 'SERIE')\n" +
                        "                    ) SE\n" +
                        "                    ON (SE.BIEN = MO.BIEN OR SE.BIEN = MA.BIEN)\n" +
                        "                    \n" +
                        "                ) CARACT\n" +
                        "                ON(BIEN.ID_BIEN = CARACT.ID_BIEN)\n                " +
                        "                     WHERE BIEN.NUM_UNIDAD_EJEC = :pUnidadEjecutora \n"+
                        "                   AND UPPER(PLACA.NUMERO_IDENTIFICACION) LIKE UPPER(:pPlaca) \n"+
                        "                   AND UPPER(BIEN.DESCRIPCION) LIKE UPPER(:pDescripcion)\n";
                        if(fltMarca.length() > 0)
                            sql += " AND UPPER(CARACT.MARCA) LIKE UPPER(:pMarca)\n";
                        if(fltModelo.length() > 0)
                            sql += " AND UPPER(CARACT.MODELO) LIKE UPPER(:pModelo)\n";
                    
                        if(fltSerie.length() > 0)
                            sql += "  AND UPPER(CARACT.SERIE) LIKE UPPER(:pSerie)\n";
                    
                        if( ! fltEstado.equals("NA"))
                            sql += " AND BIEN.ID_ESTADO = :pEstado ";
                        sql += " ORDER BY BIEN.DESCRIPCION\n " +
                                "            ) CONS\n" +
                                "            \n" +
                                "        ) PAG";
            
            SQLQuery q = session.createSQLQuery(sql);
            
            q.setParameter("pUnidadEjecutora", unidEjecutora);
            q.setParameter("pPlaca", "%"+fltPlaca+"%");
            q.setParameter("pDescripcion", "%"+fltDescripcion+"%");
            
            
            if(fltMarca.length() > 0)
                q.setParameter("pMarca", "%"+fltMarca+"%");
            if(fltModelo.length() > 0)
                q.setParameter("pModelo", "%"+fltModelo+"%");
            if(fltSerie.length() > 0)
                q.setParameter("pSerie", "%"+fltSerie+"%");
            if( ! fltEstado.equals("NA"))
                q.setParameter("pEstado", fltEstado);
            
            
            List l = q.list();


            session.close();

            
            
            for ( Object result :  l) {
                resp = Integer.parseInt(result.toString());
            }
            return resp;
            
        } catch (Exception err) {
            session.close();
        }

        if (session.isOpen()) {
            session.close();
        }
        return -1;

    }

    
    
    
    
    
    @Transactional
    public String guardarGarantia(BienEntity bien) {
        try {

            String[] nameParams = new String[1];
            nameParams[0] = "pinicioGarantia";
            nameParams[1] = "pfinGarantia";
            nameParams[2] = "pdescGarantia";
            nameParams[3] = "pIdBien";
            Object[] params = new Object[1];
            params[0] = bien.getInicioGarantia();
            params[1] = bien.getFinGarantia();
            params[2] = bien.getDescGarantia();
            params[3] = bien.getIdBien();

            Object resp = dao.getHibernateTemplate().findByNamedQueryAndNamedParam("BienEntity.updateGarantia", nameParams, params);

            return "";

        } catch (Exception e) {

            return e.getMessage();

            /*
            throw new FWExcepcion("sigebi.error.bienDao.traerTodo",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
             */
        }

    }
    

    //BienEntity.updateGarantia
    @Transactional
    public Integer obtenerId() {
        try {
            Session session = dao.getSessionFactory().openSession();
            String sql = "SELECT COUNT(s), MAX(s.idBien) FROM BienEntity s";
            Query q = session.createQuery(sql);

            List l = q.list();

            Object result[] = (Object[]) l.get(0);
            Long cant = (Long) result[0];
            if(result[1] == null){
                session.close();
                return 1;
            }
            PlacasEntity idBien  = (PlacasEntity) result[1];
            
            Integer max = idBien.getIdBien();
            
            session.close();
            Integer cant2 = cant.intValue();

            if (cant2 > max) {
                return cant2 + 1;
            }
            return max + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    public String registrarBien(BienEntity bien) {
        int idBien = 0;
        try{
            idBien = obtenerId();
        }catch(Exception err){
            
        }
        Session session = dao.getSessionFactory().openSession();

        try {
            if(idBien < 1)
                return "Error al generar del idBien.";
            session.beginTransaction();
            
            
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String fecAdq = df.format(bien.getFecAdquisicion());
            
            // Modificar el Insert actualmente error
            // hacer un SQL nativo 
            String sql = "        INSERT INTO SIGEBI_OAF.SGB_BIEN" +
                        "        (" +
                        "            ID_BIEN" +
                        "        ,	ID_IDENTIFICACION" +
                    
                        "        ,	CANTIDAD" +
                        "        ,	CODIGO_SUB_CATEGORIA" +
                        "        ,	COSTO" +
                        "        ,	DESCRIPCION" +
                        "        " +
                        "        ,	FECHA_ADQUISICION" +
                        "        ,	ID_ESTADO" +
                        "        ,	ID_MONEDA" +
                        "        ,	ID_PERSONA" +
                        "        ,	ID_SUB_CLASIFICACION" +
                        "        " +
                        "        ,	NUM_LOTE" +
                        "        ,	NUM_UNIDAD_EJEC" +
                        "        ,	ORIGEN" +
                        "        ,	PROVEEDOR" +
                        "        ,	TIPO_BIEN" +
                        "        " +
                        "        ,	CAPITALIZABLE" +
"				,	ID_UBICACION " +
"				,	DESCRIPCION_UBICACION " +
                        "        ) " +
                        "        VALUES" +
                        "        (" +
                        "            " + idBien + 
                        "        ,	" + bien.getIdPlaca()+
                    
                        "        ,	" + bien.getCantidad() +
                        "        ,	'"+bien.getCodSubCategoria()+"'" +
                        "        ,	" + bien.getCosto()+
                        "        ,	:descripcion" + //bien.getDescripcion()+"'" +
                        "        " +
                        "        ,	TO_DATE('"+fecAdq+"','DD/MM/YYYY')" +
                        "        ,	" + bien.getIdEstado().getIdEstado()+
                        "        ,	" + bien.getIdMoneda()+
                        "        ,	" + bien.getNumPersona()+
                        "        ,	" + bien.getIdSubClasificacion()+
                        "        " +
                        "        ,	" + bien.getNumLote()+
                        "        ,	" + bien.getNumUnidadEjec()+
                        "        ,	" + bien.getOrigen()+
                        "        ,	" + bien.getProveedor()+
                        "        ,	" + bien.getTipoBien()+
                        "        " +
                        "        ,	" + bien.getCapitalizable()+
                        "       ,   :pIdUbicacion "+
                        "       ,   :pDescUbicacion "+
                        ") ";
            
            
            //session.save(bien);
            SQLQuery q = session.createSQLQuery(sql);
            q.setParameter("descripcion", bien.getDescripcion());
            
            q.setParameter("pIdUbicacion", bien.getIdUbicacion());
            q.setParameter("pDescUbicacion", bien.getDescUbicacion());
            
            int resp = q.executeUpdate();
            
            session.getTransaction().commit();
            
            // bien.getIdBien()
            boolean esCapitalizable = bien.getCapitalizable() == 1;
            int idPlaca = getPlaca(bien.getNumUnidadEjec(), esCapitalizable);
            
            String updatePlaca = "update PlacasEntity set idBien = :pIdBien" +
    				" where idPlaca = :pIdPlaca";
            
            Query query = session.createQuery(updatePlaca);
            query.setParameter("pIdBien", idBien);
            query.setParameter("pIdPlaca", idPlaca);
            
            session.beginTransaction();
            int result = query.executeUpdate();
            session.getTransaction().commit();
               
            String getPlaca = "from PlacasEntity s where s.idPlaca =  " + idPlaca;
            
            query = session.createQuery(getPlaca);
            
            PlacasEntity placa = (PlacasEntity) query.list().get(0);
            
            session.close();

            //FIXMEbien.setIdBien(placa);
            
            return "";
        }
        catch(JDBCException  err){
            session.getTransaction().rollback();
            session.close();
            SQLException cause = (SQLException) err.getCause();
            //evaluate cause and find out what was the problem
            return cause.getMessage();
        }
        catch (NullPointerException e) {
            session.getTransaction().rollback();
            session.close();
            return e.getMessage();
        } catch (Exception err) {
            
            session.getTransaction().rollback();
            session.close();
                //return "Error al registrar placa";
            return err.getMessage();
        }
    }

    
    public String actualizarBien(BienEntity bien, int idUbicacion) {
        
        Session session = dao.getSessionFactory().openSession();

        try {
            
            session.beginTransaction();
            
            
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String fecAdq = df.format(bien.getFecAdquisicion());
            
            // Modificar el Insert actualmente error
            // hacer un SQL nativo 
            String sqlUpdate = "         UPDATE SIGEBI_OAF.SGB_BIEN \n" +
"                               SET	CANTIDAD             = :pCantidad\n" +
"				,	CODIGO_SUB_CATEGORIA = :pCodSubcateg\n" +
"				,	COSTO                = :pCosto\n" +
"				,	DESCRIPCION          = :pDescripcion\n" +
"				 \n" +
"				,	FECHA_ADQUISICION    = :pFecAdquicision\n" +
"				,	ID_ESTADO            = :pIdEstado\n" +
"				,	ID_MONEDA            = :pIdMoneda\n" +
"				,	ID_SUB_CLASIFICACION = :pIdSubClasificacion\n" +
//"				 \n" +
"				,	NUM_LOTE             = :pNumLote \n" +
"				,	ORIGEN               = :pOrigen \n" +
"				,	PROVEEDOR            = :pProveedor \n" +
"				,	TIPO_BIEN            = :pTipoBien	\n" +
//"				 \n" +
"				,	CAPITALIZABLE        = :pCapitalizable \n" +
"				,	ID_UBICACION         = :pIdUbicacion \n" +
"				,	DESCRIPCION_UBICACION = :pDescUbicacion \n" +
                "		WHERE ID_BIEN = :pIdBien";
            
            int pIdBien = bien.getIdBien();//FIXME
            int pCantidad = bien.getCantidad();            
            String pCodSubcateg = bien.getCodSubCategoria();
            float pCosto = bien.getCosto();
            String pDescripcion = bien.getDescripcion();

            int pIdEstado = bien.getIdEstado().getIdEstado();
            int pIdMoneda = bien.getIdMoneda();
            //int pIdPersona = bien.getNumPersona(); 
            //int pIdSubClasificacion = bien.getIdSubClasificacion();
            //session.save(bien);
            SQLQuery q = session.createSQLQuery(sqlUpdate);
            
            
            
            q.setParameter("pIdBien", pIdBien);
            
            q.setParameter("pCantidad", pCantidad);
            q.setParameter("pCodSubcateg", pCodSubcateg);
            q.setParameter("pCosto", pCosto);
            q.setParameter("pDescripcion", pDescripcion);
            
            q.setParameter("pFecAdquicision", bien.getFecAdquisicion());
            
            q.setParameter("pIdEstado", pIdEstado);
            q.setParameter("pIdMoneda", pIdMoneda);
            if( bien.getIdSubClasificacion() == null )
                q.setParameter("pIdSubClasificacion", null);
            else
                q.setParameter("pIdSubClasificacion", bien.getIdSubClasificacion());
         
            if(bien.getNumLote() == null)
                q.setParameter("pNumLote", null);
            else
                q.setParameter("pNumLote", bien.getNumLote());
                
            int pOrigen = bien.getOrigen();
            q.setParameter("pOrigen", pOrigen);
            q.setParameter("pProveedor", bien.getProveedor());
            q.setParameter("pTipoBien", bien.getTipoBien());
            q.setParameter("pCapitalizable", bien.getCapitalizable());
            
            q.setParameter("pIdUbicacion", bien.getIdUbicacion());
            q.setParameter("pDescUbicacion", bien.getDescUbicacion());
            
            int resp = q.executeUpdate();
            
            session.getTransaction().commit();
            
            // bien.getIdBien()
            
//            String updateUbicacion = "update PlacasEntity set idBien = :pIdBien" +
//    				" where idPlaca = :pIdPlaca";
//            
//            Query query = session.createQuery(updatePlaca);
//            query.setParameter("pIdBien", idBien);
//            query.setParameter("pIdPlaca", idPlaca);
//            
//            session.beginTransaction();
//            int result = query.executeUpdate();
//            session.getTransaction().commit();
//               
//            String getPlaca = "from PlacasEntity s where s.idPlaca =  " + idPlaca;
//            
//            query = session.createQuery(getPlaca);
//            
//            PlacasEntity placa = (PlacasEntity) query.list().get(0);
//            
            session.close();

            
            return "";
        } catch (Exception err) {
            
            session.getTransaction().rollback();
            session.close();
                //return "Error al registrar placa";
            return err.getMessage();
        }
    }

    public String registrarUbicacion(UbicacionBien ubicacion){
        Session session = dao.getSessionFactory().openSession();
        String resp;
        try {
            session.beginTransaction();
            
            session.save(ubicacion);
            
            session.getTransaction().commit();
            session.close();
            
            return "";
        } catch (Exception err) {
            session.close();
            resp = err.getMessage();
        }

        return resp;
    }
    
    
    
    
    public int getPlaca(int unidadEjecutoa, boolean capitalizable) {
        Session session = dao.getSessionFactory().openSession();
        try {

            String sql = "SELECT nvl( min(s.idPlaca), -1 ) as placa FROM PlacasEntity s "
                        + " WHERE s.idBien is null "
                        + " AND s.unidadEjecutora = " + unidadEjecutoa + " ";
            
            if(capitalizable)
                sql = sql + " AND s.tipoPlaca = 10";
            else
                sql = sql + " AND s.tipoPlaca = 11";
                
            Query q = session.createQuery(sql);

            List l = q.list();

            int result = (Integer) l.get(0);

            session.close();

            return result;
        } catch (Exception err) {
            session.close();
        }

        if (session.isOpen()) {
            session.close();
        }
        return -1;
    }

    
    public PlacasEntity getPlaca(int idBien) {
        Session session = dao.getSessionFactory().openSession();
        try {

            String sql = "SELECT s FROM PlacasEntity s "
                        + " WHERE s.idBien = :pIdBien ";
            
            Query q = session.createQuery(sql);
            q.setParameter("pIdBien", idBien);
            
            List l = q.list();

            PlacasEntity result = (PlacasEntity) l.get(0);

            session.close();

            return result;
        } catch (Exception err) {
            session.close();
        }

        if (session.isOpen()) {
            session.close();
        }
        return null;
    }

    
    
    public String guardarGarantia( int idBien
                                , String iniGarantia
                                , String finGarantia
                                , String descripcion
        ){
        Session session = dao.getSessionFactory().openSession();
        try {
            String sql; // = "SELECT s FROM TipoEntity s WHERE s.dominio like '"+tipoCaracter+"'";
            
            sql = "      UPDATE SIGEBI_OAF.SGB_BIEN" +
                "            SET INICIO_GARANTIA = TO_DATE('"+iniGarantia+"','DD/MM/YYYY')" +
                "            , FIN_GARANTIA  = TO_DATE('"+finGarantia+"','DD/MM/YYYY')" +
                "            , DESCRIPCION_GARANTIA = '"+descripcion+"'" +
                "        WHERE ID_BIEN = "+idBien ;
            
            SQLQuery query = session.createSQLQuery(sql);

            int resp = query.executeUpdate();
            
            session.close();
            return "";

        } catch (Exception e) {
            session.close();
            return e.getMessage();
        }
    }
    
    

    public List<LoteEntity> getLotes() {
        Session session = dao.getSessionFactory().openSession();
        List<LoteEntity> resp = new ArrayList<LoteEntity>();
        try {

            String sql = "FROM LoteEntity s ";
            Query q = session.createQuery(sql);

            resp = (List<LoteEntity> )q.list();

            session.close();
        } catch (Exception err) {
            session.close();
        }

        return resp;
    }

    
    
    public float getMontoCapitalizable() {
        Session session = dao.getSessionFactory().openSession();
        try {

            String sql = "      SELECT valor " +
                        "        FROM sf_parametros_general " +
                        "        WHERE id_parametro = 'PG_MONTO_CAPITALIZA' " +
                        "            AND num_empresa = 1";

            SQLQuery query = session.createSQLQuery(sql);
            
            List l = query.list();

            float result;
            
            String var = l.get(0).toString();
            
            try{
                result = Float.parseFloat(var);
            }catch(Exception err){
                result = 0;
            }
            if(result == 0)
                try{
                    int res = (Integer) l.get(0);
                    result = Float.parseFloat(res+"");
                }catch(Exception err){
                    result = 0;
                }
            
            
            session.close();

            return result;
        } catch (Exception err) {
            session.close();
        }

        if (session.isOpen()) {
            session.close();
        }
        return -1;
    }

    @Transactional
    public List<ViewBienEntity> listarBienes(Integer unidEjecutora,
            String fltIdBien,
            String fltDescripcion,
            String fltMarca,
            String fltModelo,
            String fltSerie,
            Integer[] fltEstadosBienes,
            Integer pPrimerRegistro,
            Integer pUltimoRegistro
    ) {
        Session session = dao.getSessionFactory().openSession();
        try {

            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListarBienes(unidEjecutora, fltIdBien, fltDescripcion, fltMarca, fltModelo, fltSerie, fltEstadosBienes, false, session);
            
            //Paginacion
            if(!(pPrimerRegistro.equals(1)&& pUltimoRegistro.equals(1))){
                q.setFirstResult(pPrimerRegistro);
                q.setMaxResults(pUltimoRegistro - pPrimerRegistro);
            }
            //Se obtienen los resutltados
            return (List<ViewBienEntity>) q.list();   

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getCause());
            throw new FWExcepcion("sigebi.error.dao.bien.listarBienes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    @Transactional(readOnly = true)
    public Long contarBienes(Integer unidEjecutora,
            String fltIdBien,
            String fltDescripcion,
            String fltMarca,
            String fltModelo,
            String fltSerie,
            Integer[] fltEstadosBienes
    ) {
        Session session = dao.getSessionFactory().openSession();
        try {
            
            //Se genera el query para la busqueda de los bienes
            Query q = this.creaQueryListarBienes(unidEjecutora, fltIdBien, fltDescripcion, fltMarca, fltModelo, fltSerie, fltEstadosBienes, true, session);

            //Se obtienen los resutltados
            return (Long)q.uniqueResult();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new FWExcepcion("sigebi.error.dao.bien.contarBienes",
                    "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        }finally{
            session.close();        
        }        
    }
    
    private Query creaQueryListarBienes(Integer unidEjecutora,
            String fltIdBien,
            String fltDescripcion,
            String fltMarca,
            String fltModelo,
            String fltSerie,
            Integer[] fltEstadosBienes,
            Boolean contar, 
            Session session
    ) {
        String sql;
        if (contar) {
            sql = "SELECT count(s) FROM ViewBienEntity s ";
        } else {
            sql = "SELECT s FROM ViewBienEntity s ";
        }
        //Select
        sql = sql + " WHERE s.numUnidadEjec = :pnumUnidadEjec ";
        if(fltIdBien != null && fltIdBien.length() > 0){
           sql = sql +  " AND upper(s.idPlaca) like upper(:fltIdBien) ";
        }
        if(fltDescripcion != null && fltDescripcion.length() > 0){
            sql = sql + " AND upper(s.descripcion) like upper(:fltDescripcion) ";
        }
        if(fltMarca != null && fltMarca.length() > 0){
            sql = sql + " AND upper(s.marca) like upper(:fltMarca) ";
        }
        if(fltModelo != null && fltModelo.length() > 0){
            sql = sql + " AND upper(s.modelo) like upper(:fltModelo) ";
        }
        if(fltSerie != null && fltSerie.length() > 0){
            sql = sql + " AND upper(s.serie) like upper(:fltSerie) ";
        }
        if(fltEstadosBienes != null && fltEstadosBienes.length > 0){
            sql = sql + " AND s.idEstado.estado in (:fltEstadosBienes)";
        }
        
        Query q = session.createQuery(sql);
        q.setParameter("pnumUnidadEjec", unidEjecutora);
        if(fltIdBien != null && fltIdBien.length() > 0){
            q.setParameter("fltIdBien", '%' + fltIdBien + '%');
        }
        if(fltDescripcion != null && fltDescripcion.length() > 0){
            q.setParameter("fltDescripcion", '%' + fltDescripcion + '%');
        }
        if(fltMarca != null && fltMarca.length() > 0){
            q.setParameter("fltMarca", '%' + fltMarca + '%');
        }
        if(fltModelo != null && fltModelo.length() > 0){
            q.setParameter("fltModelo", '%' + fltModelo + '%');
        }
        if(fltSerie != null && fltSerie.length() > 0){
            q.setParameter("fltSerie", '%' + fltSerie + '%');
        }
        if(fltEstadosBienes != null && fltEstadosBienes.length > 0){
            q.setParameterList("fltEstadosBienes", fltEstadosBienes);
        }
        return q;
    }
    
    @Transactional
    public void actualizarBien(BienEntity bien){
        this.persist(bien);    
    }
    
    @Resource
    SubClasificacionDao subClasDao;
    
    @Resource
    ClasificacionDao clasfDao;
    
    @Transactional
    public String sincronizarBien( BienEntity bien,  String usaurioSincro ) throws FWExcepcion{
        try {
            SincronizarEntity bienSinc = new SincronizarEntity(bien);
            Session session = dao.getSessionFactory().openSession();
            
            // Agrego Usuario Fecha y Placa
            Date today = new Date();
            bienSinc.setFechaAdicion(today);
            bienSinc.setAdicionadoPor(usaurioSincro);
            PlacasEntity placa = getPlaca(bien.getIdBien());
            bienSinc.setPlaca(placa.getPlaca());
            
            // Busco la Clasificación 
            if(bien.getIdSubClasificacion() != null){
                SubClasificacionEntity subClasif =  subClasDao.traerPorId(bien.getIdSubClasificacion());
                ClasificacionEntity clasif =        clasfDao.traerPorId(subClasif.getIdClasificacion());
                bienSinc.setDescripcion(clasif.getNombre());
            }
            
            session.beginTransaction();
            session.save(bienSinc);
            
            session.getTransaction().commit();
            session.close();
            
            actualizarBien(bien);
            
            return "";
        
        }
        catch(JDBCException  err){
            SQLException cause = (SQLException) err.getCause();
            //evaluate cause and find out what was the problem
            return cause.getMessage();
        }
        catch (NullPointerException e) {
            return e.getMessage();
        }
        catch (Exception e) {
            return e.getMessage();
        }

    }
    
    @Transactional(readOnly = true)
    public Long contarBienes(Integer estadoInterno) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQueryListarBienes(session, true, estadoInterno);
            return (Long)query.uniqueResult();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.dao.bien.contarBienes", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }
    }
    
    @Transactional(readOnly = true)
    public List<BienEntity> listarBienesEstadoInterno(Integer primerRegistro, Integer ultimoRegistro, Integer estadoInterno) throws FWExcepcion {
        Session session = dao.getSessionFactory().openSession();
        try {
            Query query = this.creaQueryListarBienes(session, false, estadoInterno);
            
            if(!(primerRegistro.equals(1) && ultimoRegistro.equals(1))) {
                query.setFirstResult(primerRegistro);
                query.setMaxResults(ultimoRegistro - primerRegistro);
            }
            return (List<BienEntity>) query.list();
        } catch (HibernateException e) {
            throw new FWExcepcion("sigebi.error.notificacionDao.listar", "Error obtener los registros de tipo " + this.getClass(), e.getCause());
        } finally {
            session.close();        
        }        
    }

    private Query creaQueryListarBienes(Session session, Boolean contar, Integer estadoInterno) {
        StringBuilder sql = new StringBuilder("SELECT ");
        if (contar) {
            sql.append("count(b) ");
        } else {
            sql.append("b ");
        }
        
        sql.append("from BienEntity b WHERE b.estadoInterno = :estadoInterno");
        
        Query query = session.createQuery(sql.toString());
        query.setParameter("estadoInterno", estadoInterno);
        return query;
    }
}