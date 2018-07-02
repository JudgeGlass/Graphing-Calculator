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
        //y4 = functions[3];
        //y5 = functions[4];
    }

    public void setY1(String y1){
        this.y1 = y1;
    }

    public void setY2(String y2) {
        this.y2 = y2;
    }

    public void setY3(String y3) {
        this.y3 = y3;
    }

    public void setY4(String y4) {
        this.y4 = y4;
    }

    public void setY5(String y5) {
        this.y5 = y5;
    }

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
    }

    public String getByIndex(int a){
        switch (a){
            case 0:
                return y1;
            case 1:
                return y2;
            case 2:
                return y3;
            case 3:
                return y4;
            case 4:
                return y5;
        }

        return null;
    }

    public void clearFunctionHolder(){
        y1 = "";
        y2 = "";
        y3 = "";
        y4 = "";
        y5 = "";
    }

    public boolean y1Empty(){
        if(y1.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean y2Empty(){
        if(y2.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean y3Empty(){
        if(y3.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean y4Empty(){
        if(y4.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean y5Empty(){
        if(y5.isEmpty()){
            return true;
        }
        return false;
    }
}
