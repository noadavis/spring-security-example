package springboot.v2.security.example.user;

public class UserForm {
    public String username = "";
    public String fullname = "";
    public String email = "";
    public String password = "";
    public String passcheck = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasscheck() {
        return passcheck;
    }

    public void setPasscheck(String passcheck) {
        this.passcheck = passcheck;
    }
}
