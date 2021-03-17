package dam.invisere.gtidic.udl.cat.invisereapp.models;

public class LoginUtils {

    public static void checkPassword(String password){

        if(password.matches("^(?=.\\d)(?=.([A-Z])\\w+)(?=.*([0-9]+)).{8,20}$")){
        }
        else{
            messageErrorPassword(password);
        }
    }

    public static String messageErrorPassword(String password){

            if(!password.matches("^[A-Z]")){
                return "Password does not contain capital letters";
            }
            else if(!password.matches("^[a-z]")){
                return "Password does not contain lowercase";
            }
            else if(!password.matches("^[@#$%]")){
                return "Password does not contain special characters";
            }

            return "Password has an invalid length";
    }

    public static void checkName(String name){

        if(name.matches("^(?=.\\d)(?=.([A-Z])\\w+).{2,15}$")){
        }
        else{
            messageErrorName(name);
        }
    }

    public static String messageErrorName(String name){

        if(name.matches("^[@#$%]")){
            return "The name cannot contain special characters";
        }

        return "Name has an invalid length";
    }

    public static void checkSurname(String surname){

        if(surname.matches("^(?=.\\d)(?=.([A-Z])\\w+).{2,15}$")){
        }
        else{
            messageErrorSurname(surname);
        }
    }

    public static String messageErrorSurname(String surname){

        if(surname.matches("^[@#$%]")){
            return "The name cannot contain special characters";
        }

        return "Surname has an invalid length";
    }



}
