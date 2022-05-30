package ar.edu.itba.paw.persistence;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleHibernateDao implements  RoleDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> getRoleByDescription(String description) {
        TypedQuery<Role> query = entityManager.createQuery("from Role as r where r.description = :description",Role.class);
        query.setParameter("description",description);
        List<Role> roleList = query.getResultList();
        return roleList.isEmpty() ? Optional.empty() : Optional.of(roleList.get(0));
    }

}
