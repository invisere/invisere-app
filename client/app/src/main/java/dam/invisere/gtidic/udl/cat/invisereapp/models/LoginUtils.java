package dam.invisere.gtidic.udl.cat.invisereapp.models;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginUtils {

    public static void checkPassword(String password){

        if(password.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")){
        }
        else{
            messageErrorPassword(password);
        }
    }

    public static String messageErrorPassword(String password){

            if(!password.matches("(?=.*[A-Z])")){
                return "Password does not contain capital letters";
            }
            else if(!password.matches("(?=.*[0-9])")){
                return "Password does not contain numbers";
            }
            else if(!password.matches("(?=.*[@#$%^&+=!])")){
                return "Password does not contain special characters";
            }

            return "Password has an invalid length";
    }

    public static void checkName(String name){

        if(name.matches("^[a-z ,.'-]+$")){
        }
        else{
            messageErrorName(name);
        }
    }

    public static String messageErrorName(String name){

        if(name.matches("^[@#$%^&+=!]")){
            return "The name cannot contain special characters";
        }

        return "Name has an invalid length";
    }

    public static void checkSurname(String surname){

        if(surname.matches("^[a-z ,.'-]+$")){
        }
        else{
            messageErrorSurname(surname);
        }
    }

    public static String messageErrorSurname(String surname){

        if(surname.matches("^[@#$%^&+=!]")){
            return "The name cannot contain special characters";
        }

        return "Surname has an invalid length";
    }

    public boolean isValidEmailAddress(String email){
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

}
