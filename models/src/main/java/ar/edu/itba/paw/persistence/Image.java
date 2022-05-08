package ar.edu.itba.paw.persistence;

public final class Image {

    private final String username;
    private final byte[] bytes;
    private final String imageType;

    protected Image(String username, byte[] bytes, String imageType) {
        this.username = username;
        this.bytes = bytes;
        this.imageType = imageType;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getImageType() {
        return imageType;
    }
}
