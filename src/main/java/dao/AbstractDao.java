package dao;

import hibernate.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

public abstract class AbstractDao {
    //pobierzemy hibernate util(narzędzie do komunikacji z bazą) i wytyczymy menadzera
    protected final HibernateUtil hibernateUtil = HibernateUtil.getInstance();//protected jest tu domyślne, nie trzeba pisać
    protected final EntityManager entityManager = hibernateUtil.getEntityManager();


}
