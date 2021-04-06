package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

public class SignUpViewModel extends ViewModel {

    private AccountRepo accountRepo;

    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    public SignUpViewModel() {
        this.accountRepo = new AccountRepo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onRegister() {
        Account account = new Account();
        account.setName(Name.getValue());
        account.setUsername(Username.getValue());
        account.setEmail(Email.getValue());
        account.setPassword(Password.getValue());
        Log.d("signUpVM",account.toString());
        this.accountRepo.registerAccount(account);
    }

}
