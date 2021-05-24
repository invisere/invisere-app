package dam.invisere.gtidic.udl.cat.invisereapp.models;


public class AccountProfile {

    private String created_at;
    private String name;
    private String username;
    private String email;
    private String photo;

    public AccountProfile(){
        this.name = "Name";
        this.username = "username";
        this.email = "email";
        this.photo = "";
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
