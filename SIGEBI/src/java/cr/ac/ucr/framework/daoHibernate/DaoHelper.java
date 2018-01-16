/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.framework.daoHibernate;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository(value = "daoHelper")
@Scope(value = "request")
public class DaoHelper extends HibernateDaoSupport {

    @Autowired
    public DaoHelper(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}