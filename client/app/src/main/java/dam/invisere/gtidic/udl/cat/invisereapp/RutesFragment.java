package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import dam.invisere.gtidic.udl.cat.invisereapp.preferences.Preferences;
import dam.invisere.gtidic.udl.cat.invisereapp.repo.AccountRepo;

import static android.content.ContentValues.TAG;

public class RutesFragment extends Fragment {

    private AccountRepo accountRepo;

    public RutesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountRepo = new AccountRepo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rutes, container, false);
    }

    public void logout(View view) {
        String token = Preferences.providePreferences().getString("token","");
        accountRepo.deleteTokenUser(token);
        Log.d(TAG, "Logout() -> he rebut el token: " + token);

        Preferences.providePreferences().edit().clear().apply();

        getActivity().finish();
    }
}