package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.ProfilePicture;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class ProfilePicHibernateDao implements ProfilePicDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<ProfilePicture> getProfilePicture(String username) {
        TypedQuery<ProfilePicture> tq = em.createQuery("from ProfilePicture AS pp where pp.user.userAuth.username = :username", ProfilePicture.class);
        tq.setParameter("username", username);
        try {
            return Optional.of(tq.getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    @Override
    public void uploadProfilePicture(String username, byte[] profilePicture, String type) {
        TypedQuery<User> tq = em.createQuery("from User AS u where u.userAuth.username = :username", User.class);
        tq.setParameter("username", username);
        User user;
        try {
            user = tq.getSingleResult();
        } catch (NoResultException nre) {
            throw new NoSuchUserException(username);
        }
        ProfilePicture pp = new ProfilePicture(user, profilePicture, type);
        em.persist(pp);
    }
}
