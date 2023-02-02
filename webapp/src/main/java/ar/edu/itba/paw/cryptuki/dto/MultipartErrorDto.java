package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.cryptuki.exception.BadMultipartFormatException;
import ar.edu.itba.paw.cryptuki.helper.MultipartDescriptor;
import org.springframework.web.multipart.MultipartException;

import java.util.Collection;
import java.util.stream.Collectors;

public class MultipartErrorDto {

    private String message;
    private Collection<MultipartPartErrorDto> structure;

    public static MultipartErrorDto fromBadMultipartFormatException(final BadMultipartFormatException exception) {
        final MultipartErrorDto dto = new MultipartErrorDto();
        dto.structure = exception.getDescriptors().stream().map(d -> MultipartPartErrorDto.fromDescriptor(d)).collect(Collectors.toList());
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<MultipartPartErrorDto> getStructure() {
        return structure;
    }

    public void setStructure(Collection<MultipartPartErrorDto> structure) {
        this.structure = structure;
    }
}
