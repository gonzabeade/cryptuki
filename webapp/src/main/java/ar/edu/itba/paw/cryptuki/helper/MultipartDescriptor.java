package ar.edu.itba.paw.cryptuki.helper;

public class MultipartDescriptor {
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