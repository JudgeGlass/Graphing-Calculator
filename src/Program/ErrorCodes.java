package Program;

import javax.swing.*;

public class ErrorCodes {
    public static void printErrorCode(final int errorCode){
        System.err.println("Error: " + errorCode);
    }

    public static void printErrorCode(final int errorCode, final String message){
        System.err.println(message);
        System.err.println(String.format("Error: 0x%H", errorCode));
    }

    public static void errorDialog(final int errorCode, final String message){
        JOptionPane.showMessageDialog(null, String.format("Error 0x%H:\n%s", errorCode, message), "Error", JOptionPane.ERROR_MESSAGE);
        printErrorCode(errorCode, message);
    }

    public static final int ACCESS_DENIED = 0x5AB981;
    public static final int FILE_NOT_FOUND = 0x5AB982;
    public static final int INTERNAL_ERROR = 0x5AB983;
    public static final int FUNCTION_ERROR = 0x5AB984;
    public static final int READ_ERROR = 0x5AB985;
    public static final int INVALID_DATA = 0x5AB986;
}
