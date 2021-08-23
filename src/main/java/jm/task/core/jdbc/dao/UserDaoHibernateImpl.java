package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class UserDaoHibernateImpl implements UserDao {

    private static final String CREATE_TABLE_SQL = "create table if not exists users(\n" +
            "    id bigint not null auto_increment,\n" +
            "    name varchar(250),\n" +
            "    lastname varchar(250),\n" +
            "    age tinyint,\n" +
            "    primary key(id)\n" +
            ");";
    private static final String DROP_TABLE_SQL = "drop table if exists users;";
    private static final String CLEAN_TABLE_SQL = "truncate table users;";

    private static final SessionFactory SESSION_FACTORY = HibernateUtil.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_SQL).executeUpdate();
            transaction.commit();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                log.warn("Failed to close session:" + e.getMessage());
            }
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try {
            session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE_SQL).executeUpdate();
            transaction.commit();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                log.warn("Failed to close session:" + e.getMessage());
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try{
            session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                log.warn("Failed to close session:" + e.getMessage());
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
            transaction.commit();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                log.warn("Failed to close session:" + e.getMessage());
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        List<User> list;
        try {
            session = SESSION_FACTORY.openSession();
            list = session.createQuery("from User").list();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                log.warn("Failed to close session:" + e.getMessage());
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CLEAN_TABLE_SQL).executeUpdate();
            transaction.commit();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                log.warn("Failed to close session:" + e.getMessage());
            }
        }

    }
}
