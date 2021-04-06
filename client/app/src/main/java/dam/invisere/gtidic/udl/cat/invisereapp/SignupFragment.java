package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import dam.invisere.gtidic.udl.cat.invisereapp.databinding.FragmentSignupBinding;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.EULA;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.LoginUtils;
import dam.invisere.gtidic.udl.cat.invisereapp.viewmodels.SignUpViewModel;

public class SignupFragment extends Fragment {

    private Button buttonLogin;
    private TextInputLayout textName;
    private TextInputLayout textUsername;
    private TextInputLayout textEmail;
    private TextInputLayout textPassword;
    private CheckBox checkBoxEula;
    private Button buttonRegister;
    private SignUpViewModel signUpViewModel;

    public SignupFragment() {
    }

    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        FragmentSignupBinding fragmentSignupBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);
        View view = fragmentSignupBinding.getRoot();
        fragmentSignupBinding.setLifecycleOwner(this);
        fragmentSignupBinding.setViewModel(signUpViewModel);

        buttonLogin = view.findViewById(R.id.button_signup_to_login);
        textName = view.findViewById(R.id.TextField_name_register);
        textUsername = view.findViewById(R.id.TextField_username_register);
        textEmail = view.findViewById(R.id.TextField_email_register);
        textPassword = view.findViewById(R.id.TextField_password_register);
        checkBoxEula = view.findViewById(R.id.checkbox_eula);
        buttonRegister = view.findViewById(R.id.Button_register);

        textName.setErrorEnabled(true);
        textUsername.setErrorEnabled(true);
        textEmail.setErrorEnabled(true);
        textPassword.setErrorEnabled(true);

        buttonLogin.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment));

        checkBoxEula.setOnCheckedChangeListener((buttonView, isChecked) -> new EULA(checkBoxEula).show(getChildFragmentManager(), "EULAConfirmationDialog"));

        buttonRegister.setOnClickListener(v -> {
            String name = textName.getEditText().getText().toString();
            String username = textUsername.getEditText().getText().toString();
            String email = textEmail.getEditText().getText().toString();
            String password = textPassword.getEditText().getText().toString();

            boolean cN = LoginUtils.checkName(name, textName);
            boolean cS = LoginUtils.checkUsername(username, textUsername);
            boolean vE = LoginUtils.isValidEmailAddress(email, textEmail);
            boolean cP = LoginUtils.checkPassword(password, textPassword);
            boolean cE = LoginUtils.checkEULA(checkBoxEula);

            if (cN && cS && vE && cP && cE){
                Toast toast = Toast.makeText(getContext(), "SignUp successfull.", Toast.LENGTH_SHORT);
                toast.show();
            }

        });

        return view;
    }
}