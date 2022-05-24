package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.NoSuchUserException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserAuthHibernateDao implements UserAuthDao{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<UserAuth> getUserAuthByUsername(String username) {
        TypedQuery<UserAuth> typedQuery = entityManager.createQuery("from UserAuth as ua where ua.username = :username ",UserAuth.class);
        typedQuery.setParameter("username",username);
        List<UserAuth> list = typedQuery.getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public UserAuth createUserAuth(UserAuth.Builder userAuthBuilder) {
        entityManager.persist(userAuthBuilder);
//        return getUserAuthByUsername(userAuthBuilder.getUsername()).get();
        return userAuthBuilder.build();
    }

    @Override
    public boolean verifyUser(String username, int code) {
        UserAuth userAuth = getUserAuthByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        boolean update =userAuth.getCode() == code;
        if(update){
            userAuth.setUserStatus(UserStatus.VERIFIED);
            entityManager.persist(userAuth);
        }
        return update;
}

    @Override
    public boolean changePassword(String username, String newPassword) {
        UserAuth userAuth = getUserAuthByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        userAuth.setPassword(newPassword);
        entityManager.persist(userAuth);
        return true;
    }

    @Override
    public Optional<UserAuth> getUserAuthByEmail(String email) {
        TypedQuery<UserAuth> query = entityManager.createQuery("from UserAuth as ua where ua.user.email = :email",UserAuth.class);
        query.setParameter("email",email);
        List<UserAuth> list= query.getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }
}
