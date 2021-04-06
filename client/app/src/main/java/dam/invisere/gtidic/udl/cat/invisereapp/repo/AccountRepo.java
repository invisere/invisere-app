package dam.invisere.gtidic.udl.cat.invisereapp.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

import dam.invisere.gtidic.udl.cat.invisereapp.EntryActivity;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.services.AccountServiceI;
import dam.invisere.gtidic.udl.cat.invisereapp.services.AccountServiceImpl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepo extends EntryActivity {

    private String TAG = "AccountRepo";

    private AccountServiceI accountService;

    private MutableLiveData<String> mResponseRegister;
    private MutableLiveData<String> mResponseLogin;

    String token = "";

    public AccountRepo() {
        this.accountService = new AccountServiceImpl();
        this.mResponseRegister = new MutableLiveData<>();
        this.mResponseLogin = new MutableLiveData<>();
    }

    public void registerAccount(Account account) {
        Log.d(TAG, "registerAccount() -> he rebut l'account: " + account);
        accountService.register(account).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int return_code = response.code();
                Log.d(TAG, "registerAccount() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        mResponseRegister.setValue("El registre s'ha fet correctament.");
                        break;
                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseRegister.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseRegister.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }
    public void createTokenUser(String auth){
        Log.d(TAG, "createTokenUser() -> he rebut el header: " + auth);
        accountService.login(auth).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int return_code = response.code();
                Log.d(TAG, "createTokenUser() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        try {
                            token = response.body().string().split(":")[1];
                            token = token.substring(2,token.length()-2);

                            mResponseLogin.setValue("El login s'ha fet correctament.");

                            Log.d(TAG, "Code 200 () -> envio el token: " + token);

                            Preferences.providePreferences().edit().putString("token", token).apply();
                            break;
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseLogin.setValue(error_msg);
                        Preferences.providePreferences().edit().remove("token").apply();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseLogin.setValue(error_msg);
                Preferences.providePreferences().edit().remove("token").apply();
                Log.d(TAG, error_msg);
            }
        });
    }

}
