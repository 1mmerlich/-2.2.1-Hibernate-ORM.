package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      user.getCar().setUser(user);
      sessionFactory.getCurrentSession().save(user);
      user.setCar_id(user.getCar().getId());
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   public User getUserByCar(String model, int series) {
      String HQL = "SELECT u FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series";
      try {
         return sessionFactory.getCurrentSession().createQuery(HQL, User.class)
                 .setParameter("model", model)
                 .setParameter("series", series)
                 .uniqueResult();
      } catch (NoResultException e) {
         return null; // Возвращаем null, если результатов нет
      }
   }

}