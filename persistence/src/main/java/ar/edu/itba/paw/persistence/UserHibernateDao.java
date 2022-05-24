package ar.edu.itba.paw.persistence;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserHibernateDao implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(entityManager.find(User.class,email));
    }

    @Override
    public User createUser(User.Builder user) {
        return null;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("from User as u where u.userAuth.username=:username",User.class);
        query.setParameter("username",username);
        List<User> list=query.getResultList();
        return list.isEmpty()?Optional.empty():Optional.of(list.get(0));
    }

    @Override
    public void updateLastLogin(String username) {

    }

    @Override
    public void incrementUserRating(String username, int rating) {


    }
}
