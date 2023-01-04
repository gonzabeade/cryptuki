package ar.edu.itba.paw.exception;

import java.util.Collection;

public class BadMultipartFormatException extends RuntimeException {

    // TODO - Is it the best place for this class?
    public static class MultipartDescriptor {
        private String type;
        private String paramName;

        public MultipartDescriptor(String type, String paramName) {
            this.type = type;
            this.paramName = paramName;
        }

        public String getType() {
            return type;
        }

        public String getParamName() {
            return paramName;
        }

        @Override
        public String toString() {
            return String.format("{name: %s, content-type: %s]", paramName, type);
        }
    }

    private Collection<MultipartDescriptor> multipartDescriptors;
    public BadMultipartFormatException(Collection<MultipartDescriptor> multipartDescriptors) {
        super("Multipart format not respected.");
        this.multipartDescriptors = multipartDescriptors;
    }

    public Collection<MultipartDescriptor> getDescriptors() {
        return multipartDescriptors;
    }
}
