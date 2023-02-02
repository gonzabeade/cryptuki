package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.cryptuki.helper.MultipartDescriptor;

import javax.validation.ConstraintViolation;
import javax.validation.Path;

public class MultipartPartErrorDto {

    private String type;
    private String parameterName;

    public static MultipartPartErrorDto fromDescriptor(final MultipartDescriptor multipartDescriptor) {
        final MultipartPartErrorDto dto = new MultipartPartErrorDto();
        dto.parameterName = multipartDescriptor.getParamName();
        dto.type = multipartDescriptor.getType();
        return dto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
}
