package dam.invisere.gtidic.udl.cat.invisereapp.models;

public class PublicProfile {

    private String name;
    private String username;
    private int rutes[];
    private String photo;
    //private int fav;

    public PublicProfile(){
        this.name = "";
        this.username = "";
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int[] getRutes() {
        return rutes;
    }

    public void setRutes(int[] rutes) {
        this.rutes = rutes;
    }
}
