package ar.edu.itba.paw.persistence;

public final class UserAuth{

    private Integer id;
    private String password;
    private String username;
    private String roleDescriptor;
    private Integer code;
    private UserStatus userStatus;



    public static class Builder {
        private Integer id;
        private String username;
        private String password;
        private String roleDescriptor;
        private Integer code;
        private UserStatus userStatus;


        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public Builder id(int id) { this.id = id; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder password(String password) { this.password = password; return this; }

        public Builder role(String role){this.roleDescriptor = role; return this; }

        public Builder code(Integer code){this.code = code ; return this;}

        public Builder userStatus(UserStatus userStatus){this.userStatus=userStatus;return this;}


        public UserAuth build() {
            return new UserAuth(this);
        }

        public Integer getId() {
            return id;
        }
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }

        public String getRoleDescriptor(){return roleDescriptor; }

        public Integer getCode() {
            return code;
        }

        public UserStatus getUserStatus() {
            return userStatus;
        }
    }

    private UserAuth(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.roleDescriptor = builder.roleDescriptor; //only creating instance in persistence
        this.code= builder.getCode();
        this.userStatus = builder.userStatus;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword(){return password;}

    public String getRole() {
        return roleDescriptor;
    }

    public Integer getCode() {
        return code;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }
}