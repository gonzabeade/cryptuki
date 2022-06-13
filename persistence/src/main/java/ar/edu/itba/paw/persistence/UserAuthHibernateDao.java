package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.UserAuth;
import ar.edu.itba.paw.model.UserStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserAuthHibernateDao implements UserAuthDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public UserAuth createUserAuth(int userId, String username, String password, int verificationCode) {
        UserAuth userAuth = new UserAuth(userId, username, password, verificationCode);
        em.persist(userAuth);
        return userAuth;
    }

    @Override
    public Optional<UserAuth> getUserAuthByUsername(String username) {
        TypedQuery<UserAuth> typedQuery = em.createQuery("from UserAuth as ua where ua.username = :username and status <> :status ", UserAuth.class);
        typedQuery.setParameter("username", username);
        typedQuery.setParameter("status", UserStatus.KICKED.ordinal());
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserAuth> getUserAuthByEmail(String email) {
        TypedQuery<UserAuth> typedQuery = em.createQuery("from UserAuth as ua where ua.user.email = :email  and status <> :status ", UserAuth.class);
        typedQuery.setParameter("email", email);
        typedQuery.setParameter("status", UserStatus.KICKED);
        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    @Override
    public boolean verifyUser(String username, int code) {
        UserAuth userAuth = getUserAuthByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        if (userAuth.getCode() == code){
            userAuth.setUserStatus(UserStatus.VERIFIED);
            em.persist(userAuth);
            return true;
        }
        return false;
}

    @Override
    public boolean changePassword(String username, String newPassword) {
        UserAuth userAuth = getUserAuthByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        userAuth.setPassword(newPassword);
        em.persist(userAuth);
        return true;
    }

    @Override
    public void kickoutUser(int userId) {
        UserAuth auth = em.find(UserAuth.class, userId);
        auth.setUserStatus(UserStatus.KICKED);
        em.persist(auth);
    }

}
