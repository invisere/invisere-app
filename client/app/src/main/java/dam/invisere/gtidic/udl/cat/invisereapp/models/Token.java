package dam.invisere.gtidic.udl.cat.invisereapp.models;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("token")
    private String token;

    public Token(String token) {
        this.token = token;
    }
}
