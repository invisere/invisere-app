package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.MainActivity;
import dam.invisere.gtidic.udl.cat.invisereapp.RecoveryPasswordActivity;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";
    public AccountRepo accountRepo;

    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    public LoginViewModel() {
        this.accountRepo = new AccountRepo();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onLogin(View view) {
        Log.d(TAG, "onLogin()");
        String auth = Utils.createAuth(Username.getValue(), Password.getValue());
        this.accountRepo.createTokenUser(auth);
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        view.getContext().startActivity(intent);
    }

    public void forgotPassowrd(View view) {
        Intent intent = new Intent(view.getContext(), RecoveryPasswordActivity.class);
        view.getContext().startActivity(intent);
    }
}
