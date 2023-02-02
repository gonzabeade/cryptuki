package ar.edu.itba.paw.cryptuki.exception;

import ar.edu.itba.paw.cryptuki.helper.MultipartDescriptor;

import java.util.Collection;

/**
 * This class is a controller-level exception.
 * The other layers should not be aware of this Exception
 * That is why it is placed in its own package inside the controller layer
 */
public class BadMultipartFormatException extends RuntimeException {



    private Collection<MultipartDescriptor> multipartDescriptors;
    public BadMultipartFormatException(Collection<MultipartDescriptor> multipartDescriptors) {
        super("Multipart format not respected.");
        this.multipartDescriptors = multipartDescriptors;
    }

    public Collection<MultipartDescriptor> getDescriptors() {
        return multipartDescriptors;
    }
}
