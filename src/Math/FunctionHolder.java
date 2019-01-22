/*
 * Copyright 2018 Hunter Wilcox
 * 
 * This file is part of GraphingCalculator.
 *
 * GraphingCalculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GraphingCalculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GraphingCalculator.  If not, see <http://www.gnu.org/licenses/>.
 */

package Math;

import functions.FunctionStore;

public class FunctionHolder {


    public String y1;
    public String y2;
    public String y3;
    public String y4;
    public String y5;

    public FunctionHolder(String[] functions){
        y1 = functions[0];
        y2 = functions[1];
        y3 = functions[2];
        y4 = functions[3];
        y5 = functions[4];
    }

    /***
     * Stores the values of the functions into the function store.
     * */

    public void store(){
        if(y1 != null){
            if(!y1.isEmpty())
                FunctionStore.getStore().storeFunction("y1", y1);
        }

        if(y2 != null){
            if(!y2.isEmpty())
                FunctionStore.getStore().storeFunction("y2", y2);
        }

        if(y3 != null){
            if(!y3.isEmpty())
                FunctionStore.getStore().storeFunction("y3", y3);
        }

        if(y4 != null){
            if(!y4.isEmpty())
                FunctionStore.getStore().storeFunction("y4", y4);
        }

        if(y5 != null){
            if(!y5.isEmpty())
                FunctionStore.getStore().storeFunction("y5", y5);
        }
    }

    /***
     * Clears the function data (Not from FunctionStore).
     * */

    public void clearFunctionHolder(){
        y1 = "";
        y2 = "";
        y3 = "";
        y4 = "";
        y5 = "";
    }

    public boolean y1Empty(){
        return y1.isEmpty();
    }

    public boolean y2Empty(){
        return y2.isEmpty();
    }

    public boolean y3Empty(){
        return y3.isEmpty();
    }

    public boolean y4Empty(){
        return y4.isEmpty();
    }

    public boolean y5Empty(){
        return y5.isEmpty();
    }
}
