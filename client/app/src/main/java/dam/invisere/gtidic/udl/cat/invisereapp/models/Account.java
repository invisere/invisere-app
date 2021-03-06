package dam.invisere.gtidic.udl.cat.invisereapp.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

public class Account {

    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public Account(String name, String username, String email, String password){
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Account(){
        this.name = "";
        this.username = "";
        this.email = "";
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setPassword(String password) {
        this.password = (password != null) ? Utils.encode(password, "16", 29000) : null;
    }


}
