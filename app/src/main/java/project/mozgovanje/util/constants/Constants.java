package project.mozgovanje.util.constants;

public interface Constants {

    public static final String FIRESTORE_QUESTION_COLLECTION = "Questions";
    public static final String FIRESTORE_PENDING_QUESTION_COLLECTION = "Pending questions";
    public static final String FIRESTORE_USER_COLLECTION = "Users";

    public static final String FIRESTORE_ZEN_SCOREBOARD_COLLECTION = "Zen scoreboard";
    public static final String FIRESTORE_TEST_SCOREBOARD_COLLECTION = "Test scoreboard";
    public static final String FIRESTORE_GEEK_SCOREBOARD_COLLECTION = "Geek scoreboard";

    public static final String MODE = "MODE";
    public static final int ZEN_MODE = 1;
    public static final int TEST_MODE= 2;
    public static final int GEEK_MODE = 3;

    public static final String ANSWER_A = "a";
    public static final String ANSWER_B = "b";
    public static final String ANSWER_C = "c";
    public static final String ANSWER_D = "d";

    // ======== PARCELABLE ========
    public static final String ARRAY_LIST_WRONG_QUESTIONS = "ARRAY_LIST_WRONG_QUESTIONS";
    public static final String ARRAY_LIST_CORRECT_QUESTIONS = "ARRAY_LIST_CORRECT_QUESTIONS";

    public static final String EDIT_TEXT_BORDER_GREEN_FILEPATH = "/res/layout/edit_text_green_border.xml";
    public static final String EDIT_TEXT_BORDER_RED_FILEPATH = "/res/layout/edit_text_red_border.xml";
    public static final String EDIT_TEXT_BORDER_BLACK_FILEPATH = "/res/layout/edit_text_border.xml";

}
