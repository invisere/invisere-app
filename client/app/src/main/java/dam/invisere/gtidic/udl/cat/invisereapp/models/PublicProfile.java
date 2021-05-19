package dam.invisere.gtidic.udl.cat.invisereapp.models;

public class PublicProfile {

    private String name;
    private String username;
    private int[] rutes;
    private String photo;

    public PublicProfile(String name, String username, int[] rutes, String photo) {
        this.name = name;
        this.username = username;
        this.rutes = rutes;
        this.photo = photo;
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
