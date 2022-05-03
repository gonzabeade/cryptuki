package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Image;
import ar.edu.itba.paw.persistence.ProfilePicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfilePicServiceImpl implements ProfilePicService {

    ProfilePicDao profilePicDao;

    @Autowired
    public ProfilePicServiceImpl(ProfilePicDao profilePicDao) {
        this.profilePicDao = profilePicDao;
    }

    @Override
    public Optional<Image> getProfilePicture(String username) {
        return profilePicDao.getProfilePicture(username);
    }

    @Override
    public void uploadProfilePicture(String username, byte[] profilePicture, String type) {
        profilePicDao.uploadProfilePicture(username, profilePicture, type);
    }
}
