package dam.invisere.gtidic.udl.cat.invisereapp.services;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;

import dam.invisere.gtidic.udl.cat.invisereapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class AccountServiceImpl implements AccountServiceI {

    private Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<ResponseBody> register(Account account) {
        return retrofit.create(AccountServiceI.class).register(account);
    }
}
