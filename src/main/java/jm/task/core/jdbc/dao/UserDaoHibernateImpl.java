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
        openSessions(sqlCreateTable);
    }

    @Override
    public void dropUsersTable() {
        openSessions(sqlDropTable);

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
            return session.createQuery("from User").list();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        openSessions(sqlCleanTable);

    }

    private void openSessions(String sqlCleanTable) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sqlCleanTable).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (HibernateException hibernateException) {
            hibernateException.printStackTrace();
            assert transaction != null;
            transaction.rollback();
        }
    }
}
