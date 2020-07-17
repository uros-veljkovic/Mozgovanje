package project.mozgovanje.util.constants;

public interface Constants {

    String ADMIN_EMAIL = "admin.admin@gmail.com";

    String FIRESTORE_QUESTION_COLLECTION = "Questions";
    String FIRESTORE_USERS_COLLECTION = "Users";
    String FIRESTORE_PENDING_QUESTION_COLLECTION = "Pending_questions";

    String FIRESTORE_ZEN_SCOREBOARD_COLLECTION = "Zen_scoreboard";
    String FIRESTORE_TEST_SCOREBOARD_COLLECTION = "Test_scoreboard";
    String FIRESTORE_GEEK_SCOREBOARD_COLLECTION = "Geek_scoreboard";

    String MODE = "MODE";
    int ZEN_MODE = 1;
    int TEST_MODE= 2;
    int GEEK_MODE = 3;

    String ANSWER_A = "a";
    String ANSWER_B = "b";
    String ANSWER_C = "c";
    String ANSWER_D = "d";

    // ======== PARCELABLE ========
    String ARRAY_LIST_WRONG_QUESTIONS = "ARRAY_LIST_WRONG_QUESTIONS";
    String ARRAY_LIST_CORRECT_QUESTIONS = "ARRAY_LIST_CORRECT_QUESTIONS";

    String EDIT_TEXT_BORDER_GREEN_FILEPATH = "/res/layout/edit_text_squared_green_border.xml";
    String EDIT_TEXT_BORDER_RED_FILEPATH = "/res/layout/edit_text_squared_red_border.xml";
    String EDIT_TEXT_BORDER_BLACK_FILEPATH = "/res/layout/edit_text_border.xml";

    String ERROR_EMAIL = "Unesite validnu email adresu.";
    String ERROR_PASSWORD = "MINIMUM 6 karaktera, 1 veliko slovo, 1 malo slovo, 1 broj.";
    String ERROR_CONFIRM_PASSWORD = "Lozinke moraju biti identicne.";
    String ERROR_USERNAME_TOO_WEEK = "Username mora imati najmanje 4 karaktera";
    String ERROR_USERNAME_TOO_STRONG = "Username može imati najviše 15 karaktera";
}
