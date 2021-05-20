package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

public class AuthActivityViewModel extends ViewModel {

    private static final String TAG = "AuthActivityViewModel";

    public AuthActivityViewModel(){}

    //TODO: Fer un loading.
    public boolean isLogged() {
        String token = Preferences.providePreferences().getString("token", "");
        if(!token.isEmpty()){
            Log.d(TAG, "isLogged() -> he trobat el token: " + token);
            AccountRepo accountRepo = new AccountRepo();
            accountRepo.get_account(token);
            return true;
        }
        Log.d(TAG, "isLogged() -> no he trobat cap token");
        return false;
    }

}
