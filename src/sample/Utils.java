package sample;

import javafx.scene.control.ChoiceBox;

public class Utils {

    public static String getStringSelection(ChoiceBox choiceBox) {
        String retval = "";
        Object obj = choiceBox.getSelectionModel().getSelectedItem();

        if(obj != null) {
            retval = obj.toString();
        }
        return retval;
    }

    public static int getRoomNumber  (String roomString) throws NumberFormatException {
        return Integer.parseInt(roomString.split(" ")[0]);
    }

    public static String getDoctorName(String doctorString) {
        String retval = "";
        if(!doctorString.isEmpty()) {
            String[] split = doctorString.split(" ");
            retval = split[0] + " " + split[1];
        }
        return retval;
    }
}