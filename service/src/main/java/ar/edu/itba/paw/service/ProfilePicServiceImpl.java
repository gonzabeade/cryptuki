package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Image;
import ar.edu.itba.paw.persistence.ProfilePicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Service
public class ProfilePicServiceImpl implements ProfilePicService {

    ProfilePicDao profilePicDao;

    @Autowired
    public ProfilePicServiceImpl(ProfilePicDao profilePicDao) {
        this.profilePicDao = profilePicDao;
    }

    @Override
    public Optional<Image> getProfilePicture(String username) throws URISyntaxException, IOException {
        Optional<Image> maybeImage = profilePicDao.getProfilePicture(username);
        Image image;
        if(!maybeImage.isPresent()){

            BufferedImage bImage = ImageIO.read(new File(this.getClass().getClassLoader().getResource("default-Profile.png").toURI()));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos );
            byte [] data = bos.toByteArray();
            image =  new Image(username,data,"image/png");
        }else {
            image=maybeImage.get();
        }
        return Optional.of(image);
    }

    @Override
    public void uploadProfilePicture(String username, byte[] profilePicture, String type) {
        profilePicDao.uploadProfilePicture(username, profilePicture, type);
    }
}
