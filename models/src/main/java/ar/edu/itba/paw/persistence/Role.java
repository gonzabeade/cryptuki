package ar.edu.itba.paw.persistence;

public final class Role {

    private Integer id;
    private String desc;

    protected Role(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }
    public String getDesc() {
        return desc;
    }
}
