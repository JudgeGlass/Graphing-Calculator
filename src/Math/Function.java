package Math;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;

public class Function {
    private String function;

    public Function(String function){
        this.function = function;
    }

    public void solve(double x){
        function = function.replace("x", Double.toString(x));
        String[] parts = function.split("(?<=[-+*/^])|(?=[-+*/^])");

        String newF = "";
        double probelm = 0;
        for(int i = 0;i<parts.length;i++){
            if(parts[i] == "^"){
                probelm = Math.pow(Double.parseDouble(parts[i-1]), Double.parseDouble(parts[i+1]));
                newF += probelm;
                i+=1;
            }
            newF += parts[i];
        }

        System.out.println(newF);
    }
}
