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

public class FunctionHolder {


    public String y1;
    public String y2;
    public String y3;

    public FunctionHolder(String[] functions){
        y1 = functions[0];
        y2 = functions[1];
        y3 = functions[2];
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

    public String getByIndex(int a){
        if(a == 0){
            return y1;
        }

        if(a == 1){
            return y2;
        }

        if(a == 2){
            return y3;
        }

        return null;
    }

    public void clearFunctionHolder(){
        y1 = "";
        y2 = "";
        y3 = "";
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
}
