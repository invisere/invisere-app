package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.nio.charset.StandardCharsets;

import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

public class LoginViewModel extends ViewModel {


    private static final String TAG = "LoginViewModel";
    public AccountRepo accountRepo;

    MainActivityViewModel mainActivityViewModel = new MainActivityViewModel();

    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    public LoginViewModel() {
        this.accountRepo = new AccountRepo();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onLogin() {
        Log.d(TAG, "onLogin()");
        String auth = Username.getValue() + ":" + Password.getValue();
        byte[] data = auth.getBytes(StandardCharsets.UTF_8);
        auth = Base64.encodeToString(data, Base64.DEFAULT);
        auth = ("Authentication: " + auth).trim();

        this.accountRepo.createTokenUser(auth);
    }
}
