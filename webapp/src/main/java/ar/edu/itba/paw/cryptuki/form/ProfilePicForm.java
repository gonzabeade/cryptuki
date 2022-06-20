package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.cryptuki.form.annotation.MultipartCheck;
import ar.edu.itba.paw.cryptuki.form.annotation.MultipartSizeCheck;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

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
