package ar.edu.itba.paw;

public class Role {

    private int id;
    private String desc;

    public Role(int id, String desc) {
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
