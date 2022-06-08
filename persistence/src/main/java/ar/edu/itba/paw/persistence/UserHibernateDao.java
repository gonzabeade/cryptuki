package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.exception.NoSuchUserException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserHibernateDao implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<User> getUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("from User as u where u.email=:email",User.class);
        query.setParameter("email",email);
        List<User> list=query.getResultList();
        return list.isEmpty()?Optional.empty():Optional.of(list.get(0));
    }

    @Override
    public User createUser(User.Builder userBuilder) {
        User user = userBuilder.build();
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("from User as u where u.userAuth.username=:username",User.class);
        query.setParameter("username",username);
        List<User> list=query.getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public void updateLastLogin(String username) {
        User user = getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        user.setLastLogin(LocalDateTime.now());
    }

    @Override
    public void incrementUserRating(String username, int rating) {
        User user = getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        user.setRatingSum(user.getRatingSum()+rating);
        user.setRatingCount(user.getRatingCount()+1);
        entityManager.persist(user);
    }
}
