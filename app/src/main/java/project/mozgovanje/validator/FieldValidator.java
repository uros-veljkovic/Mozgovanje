package project.mozgovanje.validator;

import project.mozgovanje.credentials.CreateAccountCredentials;

public class FieldValidator {

    public static boolean validate(String... fields) {
        for (String field : fields) {
            if (field.isEmpty())
                return false;
        }

        return true;
    }

    public static boolean validate(CreateAccountCredentials credentials) {
        return !credentials.getUsername().isEmpty()
                && !credentials.getEmail().isEmpty()
                && (credentials.getPassword().equals(credentials.getConfirmPassword()));

    }
}
