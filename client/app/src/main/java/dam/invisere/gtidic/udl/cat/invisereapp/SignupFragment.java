package dam.invisere.gtidic.udl.cat.invisereapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import dam.invisere.gtidic.udl.cat.invisereapp.validators.AccountValidator;
import dam.invisere.gtidic.udl.cat.invisereapp.utils.EULA;

public class SignupFragment extends Fragment {

    private Button buttonLogin;
    private TextInputLayout textName;
    private TextInputLayout textUsername;
    private TextInputLayout textEmail;
    private TextInputLayout textPassword;
    private CheckBox checkBoxEula;
    private Button buttonRegister;

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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

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

        buttonLogin.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment);
        });

        checkBoxEula.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                new EULA(checkBoxEula).show(getChildFragmentManager(), "EULAConfirmationDialog");
            }
        });

        buttonRegister.setOnClickListener(v -> {
            String name = textName.getEditText().getText().toString();
            String username = textUsername.getEditText().getText().toString();
            String email = textEmail.getEditText().getText().toString();
            String password = textPassword.getEditText().getText().toString();

            boolean cN = AccountValidator.checkName(name, textName);
            boolean cS = AccountValidator.checkUsername(username, textUsername);
            boolean vE = AccountValidator.isValidEmailAddress(email, textEmail);
            boolean cP = AccountValidator.checkPassword(password, textPassword);
            boolean cE = AccountValidator.checkEULA(checkBoxEula);

            if (cN && cS && vE && cP && cE){
                Toast toast = Toast.makeText(getContext(), "SignUp successfull.", Toast.LENGTH_SHORT);
                toast.show();
            }

        });

        return view;
    }
}