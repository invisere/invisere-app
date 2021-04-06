package dam.invisere.gtidic.udl.cat.invisereapp.validators;

import android.widget.CheckBox;

import dam.invisere.gtidic.udl.cat.invisereapp.EntryActivity;
import dam.invisere.gtidic.udl.cat.invisereapp.R;


public class AccountValidator extends EntryActivity {

    static String EmailRegex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]{1,3}$";

    static String NameUsernameRegex = "^[a-zñáéíóúñA-ZÁÉÍÓÚÑ]+[\\s|-]?[a-zñáéíóúñA-ZÁÉÍÓÚÑ]+[\\s|-]?[a-zñáéíóúñA-ZÁÉÍÓÚÑ]+$";

    static String PasswordRegex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[$%&|<>#!@?¿=^()€/ºª#+-.-_,;])(?=\\S+$).{8,}$";

    static String SpecialCharactersRegex = "^(?=.*[$%&|<>#!@?¿=^()€/ºª#+-.-_,;]).*$";

    static String NumbersRegex = "^(?=.*[0-9]).*$";

    public static ValidationResultImpl checkName(String name) {
        String msg = "";
        boolean success = true;
        if(name == null || !name.matches(NameUsernameRegex)) {
            msg = messageErrorName(name);
            success = false;
        }
        return new ValidationResultImpl(success, msg);
    }

    //public static int messageErrorName(String name){
    public static String messageErrorName(String name){

        if(name == null){
            return (getContext().getResources().getString(R.string.Message_error_name_empty));
        }

        else if(name.matches(SpecialCharactersRegex)){
            return (getContext().getResources().getString(R.string.Message_error_name_cannot_contain_special_characters));
        }

        else if(name.matches(NumbersRegex)){
            return (getContext().getResources().getString(R.string.Message_error_name_cannot_contain_number));
        }
        else {
            return (getContext().getResources().getString(R.string.Message_error_name_has_invalid_length));
        }
    }

    public static ValidationResultImpl checkUsername(String username) {
        String msg = "";
        boolean success = true;
        if(username == null || !username.matches(NameUsernameRegex)) {
            msg = messageErrorUsername(username);
            success = false;
        }
        return new ValidationResultImpl(success, msg);
    }

    public static String messageErrorUsername(String username){

        if(username == null){
            return (getContext().getResources().getString(R.string.Message_error_username_empty));
        }

        else if(username.matches(SpecialCharactersRegex)){
            return (getContext().getResources().getString(R.string.Message_error_username_cannot_contain_special_characters));
        }

        else if(username.matches(NumbersRegex)){
            return (getContext().getResources().getString(R.string.Message_error_username_cannot_contain_number));
        }
        else {
            return (getContext().getResources().getString(R.string.Message_error_username_has_invalid_length));
        }
    }

    public static ValidationResultImpl checkEmail(String email) {
        String msg = "";
        boolean success = true;
        if(email == null || !email.matches(EmailRegex)) {
            msg = messageErrorEmail(email);
            success = false;
        }
        return new ValidationResultImpl(success, msg);
    }

    public static String messageErrorEmail(String email){
        if(email == null){
            return (getContext().getResources().getString(R.string.Message_error_email_empty));
        }
        else return (getContext().getResources().getString(R.string.Message_error_email_invalid));
    }

    public static ValidationResultImpl checkPassword(String password) {
        String msg = "";
        boolean success = true;
        if(password == null || !password.matches(PasswordRegex)) {
            msg = messageErrorPassword(password);
            success = false;
        }
        return new ValidationResultImpl(success, msg);
    }

    public static String messageErrorPassword(String password){

        if(password == null){
            return (getContext().getResources().getString(R.string.Message_error_password_empty));
        }
        else if(!password.matches("^(?=.*[A-Z]).*$")) {
            return (getContext().getResources().getString(R.string.Message_error_password_does_not_contain_capital_letters));
        }
        else if(!password.matches(NumbersRegex)){
            return (getContext().getResources().getString(R.string.Message_error_password_does_not_contain_number));
        }
        else if(!password.matches(SpecialCharactersRegex)){
            return (getContext().getResources().getString(R.string.Message_error_password_does_not_contain_special_characters));
        }
        else {
            return (getContext().getResources().getString(R.string.Message_error_password_has_invalid_length));
        }
    }

    public static boolean checkEULA(CheckBox checkBox){
        if(checkBox.isChecked())
            return true;
        else{
            checkBox.setError(getContext().getResources().getString(R.string.Message_error_eula));
            return false;
        }
    }

}