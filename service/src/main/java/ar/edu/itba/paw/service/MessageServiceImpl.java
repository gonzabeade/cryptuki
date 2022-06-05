package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Message;
import ar.edu.itba.paw.persistence.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageDao messageDao;

    @Override
    @Transactional
    public void sendMessage(Message.Builder builder) {
        messageDao.sendMessage(builder);
    }
}
