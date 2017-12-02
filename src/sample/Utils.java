package sample;

public class Utils {

    public static int getRoomNumber(String roomString) {
        return Integer.parseInt(roomString.split(" ")[0]);
    }
}