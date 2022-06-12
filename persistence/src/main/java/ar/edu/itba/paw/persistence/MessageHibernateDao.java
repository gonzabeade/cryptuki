package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MessageHibernateDao implements MessageDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void sendMessage(int sender, int trade, String message) {
        Message newMessage = new Message(sender, trade, message);
        entityManager.persist(newMessage);
    }
}
