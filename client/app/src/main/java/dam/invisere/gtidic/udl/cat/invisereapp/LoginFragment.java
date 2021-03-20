package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    private Button buttonSignup;
    private TextInputLayout textUsername;
    private TextInputLayout textPassword;
    private Button buttonLogin;

    public LoginFragment() {
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        buttonSignup = view.findViewById(R.id.button_login_to_signup);
        textUsername = view.findViewById(R.id.TextField_username_login);
        textPassword = view.findViewById(R.id.TextField_password_login);
        buttonLogin = view.findViewById(R.id.button_login);

        textUsername.setErrorEnabled(true);
        textPassword.setErrorEnabled(true);

        buttonSignup.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);
        });

        return view;
    }
}