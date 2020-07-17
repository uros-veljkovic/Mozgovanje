package project.mozgovanje.util.validator;

import project.mozgovanje.model.credentials.CreateAccountCredentials;
import project.mozgovanje.model.credentials.LoginCredentials;
import project.mozgovanje.util.exception.FieldsEmptyException;

public class FieldValidator {

    public static boolean validate(CreateAccountCredentials credentials) throws FieldsEmptyException {
        if (!credentials.getUsername().isEmpty()
                && !credentials.getEmail().isEmpty()
                && !credentials.getPassword().isEmpty()
                && !credentials.getConfirmPassword().isEmpty()
                && (credentials.getPassword().equals(credentials.getConfirmPassword()))) {
            return true;
        } else throw new FieldsEmptyException("Molimo popunite polja za prijavu");

    }

    public static boolean validate(LoginCredentials credentials) throws FieldsEmptyException {
        if (!credentials.getEmail().isEmpty() && !credentials.getPassword().isEmpty()) {
            return true;
        } else {
            throw new FieldsEmptyException("Molimo popunite polja za prijavu");
        }
    }

    public static boolean validFields(String... fields) {
        for (String filed : fields) {
            if(filed.isEmpty()) return false;
        }
        return true;
    }
}
