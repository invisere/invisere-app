package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.models.Account;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.AccountValidator;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ValidationResultImpl;

public class SignUpViewModel extends ViewModel {

    private AccountRepo accountRepo;

    public MutableLiveData<String> Name = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> NameValidaton = new MutableLiveData<>();
    public MutableLiveData<String> Username = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> UsernameValidaton = new MutableLiveData<>();
    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> EmailValidaton = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> PassowrdValidaton = new MutableLiveData<>();

    public SignUpViewModel() {
        this.accountRepo = new AccountRepo();
    }

    public void onRegister() {
        if(isFormValid()) {
            Account account = new Account();
            account.setName(Name.getValue());
            account.setUsername(Username.getValue());
            account.setEmail(Email.getValue());
            account.setPassword(Password.getValue());
            Log.d("signUpVM", account.toString());
            this.accountRepo.registerAccount(account);
        }
    }

    public boolean isFormValid(){
        NameValidaton.setValue(AccountValidator.checkName(Name.getValue()));
        UsernameValidaton.setValue(AccountValidator.checkUsername(Username.getValue()));
        EmailValidaton.setValue(AccountValidator.checkEmail(Email.getValue()));
        PassowrdValidaton.setValue(AccountValidator.checkPassword(Password.getValue()));

        if(NameValidaton.getValue().isSuccess() && UsernameValidaton.getValue().isSuccess() && EmailValidaton.getValue().isSuccess() && PassowrdValidaton.getValue().isSuccess())
            return true;
        return false;
    }

}
