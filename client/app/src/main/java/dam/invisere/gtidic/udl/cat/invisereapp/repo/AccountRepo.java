package dam.invisere.gtidic.udl.cat.invisereapp.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.IOException;

import dam.invisere.gtidic.udl.cat.invisereapp.EntryActivity;
import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.models.AccountProfile;
import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.services.AccountServiceI;
import dam.invisere.gtidic.udl.cat.invisereapp.services.AccountServiceImpl;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ReturnCodeI;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ReturnCodeImpl;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepo extends EntryActivity {

    private String TAG = "AccountRepo";

    private AccountServiceI accountService;

    public MutableLiveData<ReturnCodeI> mResponseRegister;
    public MutableLiveData<ReturnCodeI> mResponseLogin;
    private MutableLiveData<String> mResponseGetAccount;
    private MutableLiveData<String> mResponseDeleteToken;
    private MutableLiveData<String> mResponseUpdate;
    private MutableLiveData<String> mResponseRecovery;

    String token = "";
    public static AccountProfile profile;


    public AccountRepo() {
        this.token = Preferences.providePreferences().getString("token", "");
        this.accountService = new AccountServiceImpl();
        this.mResponseRegister = new MutableLiveData<>();
        this.mResponseLogin = new MutableLiveData<>();
        this.mResponseGetAccount = new MutableLiveData<>();
        this.mResponseDeleteToken = new MutableLiveData<>();
        this.mResponseUpdate = new MutableLiveData<>();
        this.mResponseRecovery = new MutableLiveData<>();
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
                        mResponseRegister.setValue(new ReturnCodeImpl(true, return_code, 0));
//                        mResponseRegister.setValue("El registre s'ha fet correctament.");
                        break;
                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseRegister.setValue(new ReturnCodeImpl(true, return_code, 0));
//                        mResponseRegister.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mResponseRegister.setValue(new ReturnCodeImpl(false));
                String error_msg = "Error: " + t.getMessage();
//                mResponseRegister.setValue(error_msg);
                mResponseRegister.setValue(new ReturnCodeImpl(false));
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
//                            mResponseLogin.setValue("El login s'ha fet correctament.");
                            Log.d(TAG, "onResponse () -> envio el token: " + token);
                            Preferences.providePreferences().edit().putString("token", token).commit();
                            mResponseLogin.setValue(new ReturnCodeImpl(true, return_code, 200));
                            break;
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    default:
                        String error_msg = "Error: " + response.errorBody();
//                        mResponseLogin.setValue(error_msg);
                        mResponseLogin.setValue(new ReturnCodeImpl(true, return_code, 0));
                        Preferences.providePreferences().edit().remove("token").apply();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mResponseLogin.setValue(new ReturnCodeImpl(false));
                String error_msg = "Error: " + t.getMessage();
//                mResponseLogin.setValue(error_msg);
                Preferences.providePreferences().edit().remove("token").apply();
                Log.d(TAG, error_msg);
            }
        });
    }

    public void deleteTokenUser(String token){
        Log.d(TAG, "deleteTokenUser() -> he rebut el token: " + token);
        accountService.delete_token(token, token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int return_code = response.code();
                Log.d(TAG, "deleteTokenUser() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        Log.d(TAG, "Code 200 () -> deleteTokenUser: " + token);
                        mResponseDeleteToken.setValue("Token deleted successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseDeleteToken.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseDeleteToken.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }


    public void get_account(String token){
        Log.d(TAG, "get_account() -> he rebut el header: " + token);

        accountService.get_account(token).enqueue(new Callback<AccountProfile>() {

            @Override
            public void onResponse(Call<AccountProfile> call, Response<AccountProfile> response) {

                int return_code = response.code();
                Log.d(TAG, "get_account() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        profile = response.body();
                        Log.d(TAG, "Code 200 () -> get_account: " + profile);
                        Preferences.providePreferences().edit().putString("account" ,new Gson().toJson(profile)).apply();

                        mResponseGetAccount.setValue("Profile loaded successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseGetAccount.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<AccountProfile> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseGetAccount.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }


    public void updateAccount(Account account){
        Log.d(TAG, "UpdateAccount() -> he rebut el header: " + token);

        accountService.updateAccount(account,token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int return_code = response.code();
                Log.d(TAG, "UpdateAccount() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        Log.d(TAG, "Code 200 () -> Updated: ");
                        mResponseUpdate.setValue("Profile updated successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseUpdate.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseUpdate.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }



    public void updatePhoto(MultipartBody.Part photo, String token){
        Log.d(TAG, "AccountRepo -> updatePhoto() -> he rebut el header: " + token);

        accountService.updatePhoto(photo,token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int return_code = response.code();
                Log.d(TAG, "updatePhoto() -> ha rebut el codi: " + return_code);

                switch (return_code) {
                    case 200:
                        Log.d(TAG, "Code 200 () -> Updated: ");
                        mResponseUpdate.setValue("Profile updated successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseUpdate.setValue(error_msg);
                        break;
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseUpdate.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }

    public void recovery(String email) {
        accountService.recovery(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int return_code = response.code();
                Log.d(TAG, "Recovery() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        Log.d(TAG, "Code 200 () -> Recovery: ");
                        mResponseRecovery.setValue("Recovery successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseRecovery.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseRecovery.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }

    public void password_update(String email, String password, String code) {
        accountService.password_update(email, password, code).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int return_code = response.code();
                Log.d(TAG, "password_update() -> ha rebut el codi: " + return_code);
                switch (return_code) {
                    case 200:
                        Log.d(TAG, "Code 200 () -> password_update: ");
                        mResponseRecovery.setValue("password_update successfully.");
                        break;

                    default:
                        String error_msg = "Error: " + response.errorBody();
                        mResponseRecovery.setValue(error_msg);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseRecovery.setValue(error_msg);
                Log.d(TAG, error_msg);
            }
        });
    }

}
