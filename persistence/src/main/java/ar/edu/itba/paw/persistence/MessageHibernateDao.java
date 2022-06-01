package ar.edu.itba.paw.persistence;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MessageHibernateDao implements MessageDao{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void sendMessage(Message.Builder builder) {
        entityManager.persist(builder);
    }
}
