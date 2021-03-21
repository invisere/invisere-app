package dam.invisere.gtidic.udl.cat.invisereapp.models;

public class AccountUser {

    private String name;
    private String username;
    private String mail;
    private String password;

    public AccountUser(String name, String username, String mail, String password){
        this.name = name;
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public AccountUser(){
        this.name = "";
        this.username = "";
        this.mail = "";
        this.password = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
