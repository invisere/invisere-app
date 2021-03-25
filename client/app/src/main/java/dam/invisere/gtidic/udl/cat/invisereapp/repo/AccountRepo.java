package dam.invisere.gtidic.udl.cat.invisereapp.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.services.AccountServiceI;
import dam.invisere.gtidic.udl.cat.invisereapp.services.AccountServiceImpl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepo {

    private String TAG = "AccountRepo";

    private AccountServiceI accountService;

    private MutableLiveData<String> mResponseRegister;

    public AccountRepo() {
        this.accountService = new AccountServiceImpl();
        this.mResponseRegister = new MutableLiveData<>();
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
}
