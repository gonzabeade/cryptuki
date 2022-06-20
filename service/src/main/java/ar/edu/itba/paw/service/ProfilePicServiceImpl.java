package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.ProfilePicture;
import ar.edu.itba.paw.persistence.ProfilePicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<ProfilePicture> getProfilePicture(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");
        return profilePicDao.getProfilePicture(username);
    }

    @Override
    @Secured("ROLE_USER")
    @Transactional
    @PreAuthorize("#username == authentication.principal.username")
    public void uploadProfilePicture(String username, byte[] profilePicture, String type) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        if (type == null)
            throw new NullPointerException("Image type cannot be null");

        profilePicDao.uploadProfilePicture(username, profilePicture, type);
    }
}
