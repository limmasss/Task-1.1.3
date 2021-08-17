package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
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

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = null;
        Session session = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_SQL).executeUpdate();
            transaction.commit();
            session.flush();
        } finally {
            session.close();
            sessionFactory.close();
        }

    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactory = null;
        Session session = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE_SQL).executeUpdate();
            transaction.commit();
            session.flush();
        } finally {
            session.close();
            sessionFactory.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = null;
        Session session = null;
        try{
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = null;
        Session session = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
            transaction.commit();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory sessionFactory = null;
        Session session = null;
        List<User> list;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            list = session.createSQLQuery("select * from users").addEntity(User.class).list();
        } finally {
            session.close();
            sessionFactory.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = null;
        Session session = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CLEAN_TABLE_SQL).executeUpdate();
            transaction.commit();
            session.flush();
        } finally {
            session.close();
            sessionFactory.close();
        }

    }
}
