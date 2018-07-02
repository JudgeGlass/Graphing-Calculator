package Program;

import FileIO.Utils;
import functions.FunctionStore;

import java.util.ArrayList;

public class GetDefinedFunctions {
    private static String filename;
    private static ArrayList<String> keys;
    private static int counter;

    public static void store(final String filename, final int lastIndex){
        GetDefinedFunctions.filename = filename;
        keys = new ArrayList<>();
        counter = lastIndex + 2;
        while(!Utils.readLine(filename, counter).equals("## END DEF ##")){
            final String function = Utils.readLine(filename, counter);
            String split[] = function.split("=");

            FunctionStore.getStore().storeFunction(split[0], split[1]);

            counter++;
        }
    }

    public static String getFilename(){return filename;}

    public static ArrayList<String> getKeys(){return keys;}
}
