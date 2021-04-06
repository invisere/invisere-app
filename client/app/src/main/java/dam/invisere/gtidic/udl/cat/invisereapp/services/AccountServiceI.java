package dam.invisere.gtidic.udl.cat.invisereapp.services;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AccountServiceI {

    @POST("/users/register")
    Call<ResponseBody> register(@Body Account account);

    @POST("/account/create_token")
    Call<ResponseBody> login(@Header("Authorization") String auth);

    @GET("/account/profile")
    Call<ResponseBody> get_account(@Header("Authorization") String token);

    @POST("/account/update")
    Call<ResponseBody> update(@Body Account account, @Header("Authorization") String token);

    @POST("/account/profile/update_profile_image")
    Call<ResponseBody> updatePhoto(@Part MultipartBody.Part image_file, @Header("Authorization") String token);


}
