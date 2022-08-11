package ar.edu.itba.paw.cryptuki.form.legacy;

import ar.edu.itba.paw.cryptuki.form.annotation.MultipartCheck;
import ar.edu.itba.paw.cryptuki.form.annotation.MultipartSizeCheck;
import org.springframework.web.multipart.MultipartFile;

public class ProfilePicForm {
    @MultipartCheck
    @MultipartSizeCheck(maxSize = (1<<21))
    private MultipartFile multipartFile;
    private boolean isBuyer;

    public boolean isBuyer() {
        return isBuyer;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setBuyer(boolean buyer) {
        isBuyer = buyer;
    }
    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
