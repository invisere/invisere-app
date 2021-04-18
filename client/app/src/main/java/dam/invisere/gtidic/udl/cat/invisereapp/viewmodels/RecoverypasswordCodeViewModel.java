package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.RecoveryPasswordActivity;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.Utils;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.AccountValidator;
import dam.invisere.gtidic.udl.cat.invisereapp.validators.ValidationResultImpl;

public class RecoverypasswordCodeViewModel extends ViewModel {

    private final String TAG = "RpassCodeViewModel";
    private final AccountRepo accountRepo;

    public MutableLiveData<String> Email = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<ValidationResultImpl> PasswordValidaton = new MutableLiveData<>();
    public MutableLiveData<String> Code = new MutableLiveData<>();

    public RecoverypasswordCodeViewModel() {
        accountRepo = new AccountRepo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onConfirm(View view) {
        Log.d(TAG, "onConfirm() -> " + Email.getValue());
        PasswordValidaton.setValue(AccountValidator.checkPassword(Password.getValue()));
        if(Password.getValue() != null && Code.getValue() != null && PasswordValidaton.getValue().isSuccess()) {
            accountRepo.password_update(Email.getValue(), Utils.encode(Password.getValue(), "16", 29000), Code.getValue());
            Intent intent = new Intent(view.getContext(), RecoveryPasswordActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}
