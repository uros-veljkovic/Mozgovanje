package project.mozgovanje.util.string;

public class StringUtil {

    public static String convertIntToString(int value) {
        return String.valueOf(value);
    }

    public static String getPoints(int points) {
        return "" + points + " pts";
    }

    public static String getCurrentQuizState(int currentQuestionIndex, int lastQuestionIndex){
        return currentQuestionIndex + "/" + lastQuestionIndex;
    }


    public static String getCorrectAnswerColor(){
        return "#85bb65";
    }

    public static String getWrongAnswerColor(){
        return "#ff0033";
    }

    public static boolean isEmpty(String string){
        return string.isEmpty();
    }

    public static String toUpper(String string, int charIndex){

        char[] arrayOfChars = string.toCharArray();
        arrayOfChars[3] = Character.toUpperCase(arrayOfChars[charIndex]);

        return new String(arrayOfChars);
    }

}
