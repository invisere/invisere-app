package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentLoginBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.LoginViewModel;

public class LoginFragment extends Fragment {

    private Button buttonSignup;
    private TextInputLayout textUsername;
    private TextInputLayout textPassword;
    private Button buttonLogin;
    private LoginViewModel loginViewModel;

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

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        FragmentLoginBinding fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        View view = fragmentLoginBinding.getRoot();
        fragmentLoginBinding.setLifecycleOwner(this);
        fragmentLoginBinding.setViewModel(loginViewModel);

        buttonSignup = view.findViewById(R.id.button_login_to_signup);
        textUsername = view.findViewById(R.id.TextField_username_login);
        textPassword = view.findViewById(R.id.TextField_password_login);
        buttonLogin = view.findViewById(R.id.button_login);

        textUsername.setErrorEnabled(true);
        textPassword.setErrorEnabled(true);

        buttonSignup.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment));

        return view;
    }
}