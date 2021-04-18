package dam.invisere.gtidic.udl.cat.invisereapp.services;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
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
    public Call<ResponseBody> update(Account account, String token){
        return retrofit.create(AccountServiceI.class).update(account,token);
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

}
