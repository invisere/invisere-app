package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.nio.charset.StandardCharsets;

import dam.invisere.gtidic.udl.cat.invisereapp.MainActivity;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

public class LoginViewModel extends ViewModel {


    private static final String TAG = "LoginViewModel";
    private AccountRepo accountRepo;

    MainActivityViewModel mainActivityViewModel = new MainActivityViewModel();

    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    public LoginViewModel() {
        this.accountRepo = new AccountRepo();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onLogin(View view) {
        Log.d(TAG, "onLogin()");
        String auth = Username.getValue() + ":" + Password.getValue();
        byte[] data = auth.getBytes(StandardCharsets.UTF_8);
        auth = Base64.encodeToString(data, Base64.DEFAULT);
        auth = ("Authentication: " + auth).trim();

        this.accountRepo.createTokenUser(auth);

        if(mainActivityViewModel.isLogged()){
            Context context = view.getContext();
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

    }
}
