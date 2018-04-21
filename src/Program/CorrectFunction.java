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

package Program;

import java.util.ArrayList;

public class CorrectFunction {
    public static String addMul(String funtion){
        String nf1 = addMulLeft(funtion.replaceAll("\\s", ""));

        return addMulRight(nf1);
    }

    private static String addMulLeft(String function){
        ArrayList<Integer> posXs = new ArrayList<>();
        for(int i = 0; i < function.length(); i++){
            if(!posXs.contains(function.indexOf('x', i)) && function.indexOf('x', i) >= 0)
                posXs.add(function.indexOf('x', i));
        }

        StringBuilder nf = new StringBuilder(function);
        boolean insert = false;
        int am = 0;
        for(int i = 0; i<posXs.size();i++){
            int xIndex = posXs.get(i);
            if(xIndex != 0) {
                switch (function.charAt(xIndex - 1)) {
                    case '1':
                        insert = true;
                        am++;
                        break;
                    case '2':
                        insert = true;
                        am++;
                        break;
                    case '3':
                        insert = true;
                        am++;
                        break;
                    case '4':
                        insert = true;
                        am++;
                        break;
                    case '5':
                        insert = true;
                        am++;
                        break;
                    case '6':
                        insert = true;
                        am++;
                        break;
                    case '7':
                        insert = true;
                        am++;
                        break;
                    case '8':
                        insert = true;
                        am++;
                        break;
                    case '9':
                        insert = true;
                        am++;
                        break;
                    case '0':
                        insert = true;
                        am++;
                        break;
                    case ')':
                        insert = true;
                        am++;
                        break;
                }
            }
            if(insert) {
                if (i > 0 && am > 1)
                    nf.insert(xIndex+1, '*');
                else
                    nf.insert(xIndex, '*');
            }
            insert = false;
        }

        return nf.toString();
    }

    private static String addMulRight(String function){
        ArrayList<Integer> posXs = new ArrayList<>();
        for(int i = 0; i < function.length(); i++){
            if(!posXs.contains(function.indexOf('x', i)) && function.indexOf('x', i) >= 0)
                posXs.add(function.indexOf('x', i));
        }

        StringBuilder nf = new StringBuilder(function);
        boolean insert = false;
        int am = 0;
        for(int i = 0; i<posXs.size();i++){
            int xIndex = posXs.get(i);
            if(xIndex + 1 < function.length()) {
                switch (function.charAt(xIndex + 1)) {
                    case '1':
                        insert = true;
                        am++;
                        break;
                    case '2':
                        insert = true;
                        am++;
                        break;
                    case '3':
                        insert = true;
                        am++;
                        break;
                    case '4':
                        insert = true;
                        am++;
                        break;
                    case '5':
                        insert = true;
                        am++;
                        break;
                    case '6':
                        insert = true;
                        am++;
                        break;
                    case '7':
                        insert = true;
                        am++;
                        break;
                    case '8':
                        insert = true;
                        am++;
                        break;
                    case '9':
                        insert = true;
                        am++;
                        break;
                    case '0':
                        insert = true;
                        am++;
                        break;
                    case '(':
                        insert = true;
                        am++;
                        break;
                }
            }
            if(insert) {
                if (i > 0 && am > 1)
                    nf.insert(xIndex+am, '*');
                else
                    nf.insert(xIndex+1, '*');
            }
            insert = false;
        }

        return nf.toString();
    }
}
