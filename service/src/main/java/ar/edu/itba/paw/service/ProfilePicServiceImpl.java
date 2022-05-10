package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import ar.edu.itba.paw.persistence.Image;
import ar.edu.itba.paw.persistence.ProfilePicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Service
public class ProfilePicServiceImpl implements ProfilePicService {

    private final ProfilePicDao profilePicDao;

    @Autowired
    public ProfilePicServiceImpl(ProfilePicDao profilePicDao) {
        this.profilePicDao = profilePicDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Image> getProfilePicture(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return profilePicDao.getProfilePicture(username);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Secured("ROLE_USER")
    @PreAuthorize("#username == authentication.principal.username")
    public void uploadProfilePicture(String username, byte[] profilePicture, String type) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        if (type == null)
            throw new NullPointerException("Image type cannot be null");

        try {
            profilePicDao.uploadProfilePicture(username, profilePicture, type);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }
}
