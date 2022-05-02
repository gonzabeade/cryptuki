package ar.edu.itba.paw.persistence;

public final class UserAuth{

    private int id;
    private String password;
    private String username;
    private String roleDescriptor;
    private Integer code;
    private UserStatus userStatus;

    public static class Builder {
        private Integer id;
        private String username;
        private String password;
        private String role;
        private Integer code;
        private UserStatus userStatus = UserStatus.UNVERIFIED;

        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public Builder withId(int id) { this.id = id; return this; }
        public Builder withRole(String role){this.role = role; return this; }
        public Builder withPassword(String password){this.password = password; return this; }

        public Builder withCode(Integer code){this.code = code ; return this;}
        public Builder withUserStatus(UserStatus userStatus){this.userStatus=userStatus;return this;}


        public int getId() {
            return id;
        }
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
        public String getRole(){return role; }
        public int getCode() {
            return code;
        }
        public UserStatus getUserStatus() {
            return userStatus;
        }
        protected UserAuth build() {
            return new UserAuth(this);
        }
    }

    private UserAuth(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.code = builder.code;
        this.roleDescriptor = builder.role; //only creating instance in persistence
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
    public UserStatus getUserStatus() {
        return userStatus;
    }
    public int getCode() { return code; };
}