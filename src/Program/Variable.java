package Program;

import javax.xml.stream.events.Characters;
import java.util.ArrayList;
import java.util.HashMap;

public class Variable {
    private static HashMap<String, Double> values;
    private static ArrayList<String> vars;

    public Variable(){
        values = new HashMap<>();
        vars = new ArrayList<>();
    }

    public static void addVarFromString(String function){
        int index = 0;
        if(function.contains("->")){
            index = function.indexOf("->");
            String var = Character.toString(function.charAt(index-1));
            double value = Double.parseDouble(Character.toString(function.charAt(index+2)));
            addVar(var, value);
            System.out.println("Added: " + var + "=" + value);
        }
    }

    public static void addVar(String var, double value){
        vars.add(var);
        values.put(var, value);
    }

    public static double getValue(String var){
        return values.get(var);
    }

    public static ArrayList<String> getVars(){
        return vars;
    }
}
