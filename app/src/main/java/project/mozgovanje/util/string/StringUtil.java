package project.mozgovanje.util.string;

public class StringUtil {

    public static String convertIntToString(int value) {
        return String.valueOf(value);
    }

    public static String getPoints(int points) {
        return "" + points + " pts";
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



}
