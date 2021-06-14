package dam.invisere.gtidic.udl.cat.invisereapp.services;

import java.util.List;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Place;
import dam.invisere.gtidic.udl.cat.invisereapp.models.PublicProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Route;
import dam.invisere.gtidic.udl.cat.invisereapp.network.RetrofitClientInstance;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class AccountServiceImpl implements AccountServiceI {

    private Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<ResponseBody> register(Account account) {
        return retrofit.create(AccountServiceI.class).register(account);
    }

    @Override
    public Call<ResponseBody> login(String auth){
        return retrofit.create(AccountServiceI.class).login(auth);
    }

    @Override
    public Call<ResponseBody> delete_token(String deleteToken, String token) {
        return retrofit.create(AccountServiceI.class).delete_token(deleteToken, token);
    }

    @Override
    public Call<AccountProfile> get_account(String token){
        return retrofit.create(AccountServiceI.class).get_account(token);
    }

    @Override
    public Call<ResponseBody> updateAccount(Account account, String token){
        return retrofit.create(AccountServiceI.class).updateAccount(account,token);
    }

    @Override
    public Call<ResponseBody> updatePhoto(MultipartBody.Part photo, String token){
        return retrofit.create(AccountServiceI.class).updatePhoto(photo,token);
    }

    @Override
    public Call<ResponseBody> recovery(String email) {
        return retrofit.create(AccountServiceI.class).recovery(email);
    }

    @Override
    public Call<ResponseBody> password_update(String email, String password, String code) {
        return retrofit.create(AccountServiceI.class).password_update(email, password, code);
    }

    @Override
    public Call<PublicProfile> get_public_account(String token, String username){
        return retrofit.create(AccountServiceI.class).get_public_account(token,username);
    }

    @Override
    public Call<List<Route>> get_routes(String token){
        return retrofit.create(AccountServiceI.class).get_routes(token);
    }

    @Override
    public Call<List<Route>> get_own_routes(String token){
        return retrofit.create(AccountServiceI.class).get_own_routes(token);
    }

    @Override
    public Call<List<Route>> get_favorite_routes(String token) {
        return retrofit.create(AccountServiceI.class).get_favorite_routes(token);
    }

    @Override
    public Call<List<Place>> get_places(String token){
        return retrofit.create(AccountServiceI.class).get_places(token);
    }

    @Override
    public Call<PublicProfile> add_route_favorite(String token, int id) {
        return retrofit.create(AccountServiceI.class).add_route_favorite(token, id);
    }

}
