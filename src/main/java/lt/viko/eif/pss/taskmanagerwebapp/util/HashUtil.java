package lt.viko.eif.pss.taskmanagerwebapp.util;

import lt.viko.eif.pss.taskmanagerwebapp.model.User;
import lt.viko.eif.pss.taskmanagerwebapp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashUtil {
    public static String encryptPassword(String password){
        if(password.isEmpty()){
            throw new RuntimeException();
        }
        return BCrypt.hashpw(password,BCrypt.gensalt(10));
    }
    public static boolean checkPassword(String password,String hashedPassword){
        if(password.isEmpty() || hashedPassword == null || password == null ||  hashedPassword.isEmpty()){
            return false;
        }
        return BCrypt.checkpw(password,hashedPassword);
    }

}
