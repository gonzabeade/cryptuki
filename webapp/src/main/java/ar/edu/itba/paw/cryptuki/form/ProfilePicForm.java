package ar.edu.itba.paw.cryptuki.form;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class ProfilePicForm {
    @NotNull(message = "Seleccione una imagen.")
    private MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
