package dam.invisere.gtidic.udl.cat.invisereapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;


public class EntryActivityViewModel extends ViewModel {

    private static final String TAG = "EntryActivityViewModel";

    public EntryActivityViewModel() {
    }

    public boolean isLogged() {
        String token = Preferences.providePreferences().getString("token", "");
        Log.d(TAG, "isLogged() -> he trobat el token: " + token);
        if(!token.isEmpty()){
            AccountRepo accountRepo = new AccountRepo();
            accountRepo.get_account(token);
            return true;
        }
        return false;
    }

}
