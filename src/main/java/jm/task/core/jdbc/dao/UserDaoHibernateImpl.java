package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        openSessions("CREATE TABLE IF NOT EXISTS USERS (id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT, firstName VARCHAR(45), lastName VARCHAR(45), age TINYINT);");
    }

    @Override
    public void dropUsersTable() {
        openSessions("DROP TABLE IF EXISTS USERS");

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            transaction.commit();
            return session.createQuery("from User").list();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            assert transaction != null;
            transaction.rollback();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        openSessions("TRUNCATE TABLE USERS");

    }

    private void openSessions(String sqlString) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sqlString).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            assert transaction != null;
            transaction.rollback();
        }
    }
}
