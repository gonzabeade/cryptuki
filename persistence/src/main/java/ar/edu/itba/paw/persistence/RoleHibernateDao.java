package ar.edu.itba.paw.persistence;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class RoleHibernateDao implements  RoleDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> getRoleByDescription(String description) {
        return Optional.ofNullable(entityManager.find(Role.class,description));
    }
}
