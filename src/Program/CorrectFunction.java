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

import FileIO.Utils;
import functions.FunctionStore;

import java.util.ArrayList;

public class CorrectFunction {
    private static boolean enable = true;

    public static void setEnabled(boolean b){
        enable = b;
    }

    public static String addMul(String function){
        String nf1 = addMulLeft(function.replaceAll("\\s", ""));

        if(enable)
            return addPer(addZeros(addMulRight(nf1)));
        return function;
    }


    private static String addMulLeft(String function){
        boolean continueF = false;
        ArrayList<Integer> dotI = new ArrayList<>();
        for(int i = 0; i<function.length();i++){
            if(function.charAt(i) == 'x'){
                dotI.add(i);
            }
        }

        StringBuilder nf = new StringBuilder(function);
        int am = 0;
        for(int i = 0; i<dotI.size();i++){
            continueF = false;
            int dotP = dotI.get(i);
            if(dotP == 0){
                //am++;
                continue;
            }

            switch (function.charAt(dotP-1)){
                case '*':
                    continueF = true;
                    break;
                case '+':
                    continueF = true;
                    break;
                case '-':
                    continueF = true;
                    break;
                case '/':
                    continueF = true;
                    break;
                case '^':
                    continueF = true;
                    break;
                case '(':
                    continueF = true;
                    break;
            }

            if(continueF)continue;

            if(dotP > 0){
                nf.insert(dotP+am, '*');
                am++;
            }
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

    private static String addZeros(String function){
        boolean continueF = false;
        ArrayList<Integer> dotI = new ArrayList<>();
        for(int i = 0; i<function.length();i++){
            if(function.charAt(i) == '.'){
                dotI.add(i);
            }
        }

        StringBuilder nf = new StringBuilder(function);
        int am = 0;
        for(int i = 0; i<dotI.size();i++){
            int dotP = dotI.get(i);
            if(dotP == 0){
                nf.insert(dotP, '0');
                am++;
                continue;
            }

            switch (function.charAt(dotP-1)){
                case '1':
                    continueF = true;
                    break;
                case '2':
                    continueF = true;
                    break;
                case '3':
                    continueF = true;
                    break;
                case '4':
                    continueF = true;
                    break;
                case '5':
                    continueF = true;
                    break;
                case '6':
                    continueF = true;
                    break;
                case '7':
                    continueF = true;
                    break;
                case '8':
                    continueF = true;
                    break;
                case '9':
                    continueF = true;
                    break;
                case '0':
                    continueF = true;
                    break;
            }

            if(continueF)continue;

            if(dotP > 0){
                nf.insert(dotP+am, '0');
                am++;
            }
        }

        return nf.toString();
    }

    private static ArrayList<Integer> opIndexes(String function){
        ArrayList<String> allOP = Utils.combineStringList(FunctionStore.getStore().getOperators(), FunctionStore.getStore().getIdNames());
        ArrayList<Integer> opI = new ArrayList<>();
        for(int i = 0; i<allOP.size();i++){
            for(int x = function.indexOf(allOP.get(i), 0); x<function.length();x++) {
                if (function.indexOf(allOP.get(i), x) >= 0 && !opI.contains(function.indexOf(allOP.get(i),x))) {
                    opI.add(function.indexOf(allOP.get(i), x) + allOP.get(i).length());
                }
            }
        }
        opI.sort(Integer::compareTo);
        return opI;
    }

    private static String addPer(String function){
        ArrayList<Integer> avoid = new ArrayList<>();
        int offset = 0;
        boolean avoidedZ = false;
        for(int i = 0; i<function.length();i++){
            int pI = (function.indexOf('(', i) >= 0) ? function.indexOf('(', i) : -1;
            if(pI == -1 || function.charAt(pI+1) == '(' || opIndexes(function).contains(pI))continue;

            for(int x = pI;x<function.length();x++){
                if(function.charAt(x) == '(')offset++;
                if(function.charAt(x) == ')') offset--;
                if(offset == 0){
                    //System.out.println("AVOIDING: " + x);
                    avoid.add(x);
                    if(pI == 0)avoidedZ = true;
                    break;
                }
            }
        }

        if(!function.contains(")") && !function.contains("("))
            return function;

        ArrayList<Integer> indexes = new ArrayList<>();
        int first = function.indexOf(')', 0);
        indexes.add(first);
        for(int i = first; i<function.length();i++){
            if(!indexes.contains(function.indexOf(')', i)) && function.indexOf(')', i) >= 0 && !avoid.contains(function.indexOf(')', i)))
                indexes.add(function.indexOf(')', i));
        }

        String nf = function;
        for(int i = 0; i<indexes.size();i++){
            //System.out.println("INDEX: " + indexes.get(i));
            if(avoid.contains(indexes.get(i)))continue;
            if(avoidedZ)
                nf = insertPos(indexes.get(i)+i-1, nf);
            else
                nf = insertPos(indexes.get(i)+i, nf);
        }

        ArrayList<String> allOP = Utils.combineStringList(FunctionStore.getStore().getOperators(), FunctionStore.getStore().getIdNames());
        ArrayList<Integer> opI = new ArrayList<>();
        for(int i = 0; i<allOP.size();i++){
            for(int x = nf.indexOf(allOP.get(i), 0); x<nf.length();x++) {
                if (nf.indexOf(allOP.get(i), x) >= 0 && !opI.contains(nf.indexOf(allOP.get(i),x))) {
                    opI.add(nf.indexOf(allOP.get(i), x));
                }
            }
        }

        opI.sort(Integer::compareTo);
        for(int i = 0; i<opI.size();i++){
            nf = insertNeg(opI.get(i)+i, nf);
        }

        //System.out.println("FUNCTION: " + nf);
        return nf;
    }

    private static String insertPos(int index, String function){
        StringBuilder sb = new StringBuilder(function);

        sb.insert(index+1, ')');

        return sb.toString();
    }

    private static String insertNeg(int index, String function){
        StringBuilder sb = new StringBuilder(function);

        //if(sb.charAt())
        sb.insert(index, '(');

        return sb.toString();
    }
}
