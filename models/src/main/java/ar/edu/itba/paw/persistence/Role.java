package ar.edu.itba.paw.persistence;

public final class Role {

    private int id;
    private String description;

    protected Role(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
}
