package dam.invisere.gtidic.udl.cat.invisereapp.services;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Token;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AccountServiceI {

    @POST("/users/register")
    Call<ResponseBody> register(@Body Account account);

    @POST("/account/create_token")
    Call<ResponseBody> login(@Header("Authorization") String auth);

    @POST("/account/delete_token")
    Call<ResponseBody> delete_token(@Body Token deleteToken, @Header("Authorization") String token);

    @GET("/account/profile")
    Call<AccountProfile> get_account(@Header("Authorization") String token);

    @POST("/account/update")
    Call<ResponseBody> update(@Body Account account, @Header("Authorization") String token);

    @Multipart
    @POST("/account/profile/update_profile_image")
    Call<ResponseBody> updatePhoto(@Part MultipartBody.Part photo, @Header("Authorization") String token);

    @POST("/account/recovery")
    Call<ResponseBody> recovery(@Body Account account);



}
