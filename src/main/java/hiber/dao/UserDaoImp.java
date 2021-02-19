package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String models, int series){
      Query query = sessionFactory.getCurrentSession().createQuery("from Car where model = :nameModels AND series = :nameSeries");
      query.setParameter("nameModels", models);
      query.setParameter("nameSeries", series);
      Car car = (Car) query.getResultList().get(0);
      query = sessionFactory.getCurrentSession().createQuery("from User where car = :carId");
      query.setParameter("carId", car);
      return (User) query.getResultList().get(0);
   }

}
