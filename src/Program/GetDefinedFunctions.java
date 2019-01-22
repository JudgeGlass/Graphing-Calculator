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

public class GetDefinedFunctions {
    private static String filename;
    private static ArrayList<String> keys;
    private static int counter;

    /***
     * Gets all the saved functions in the save file.
     *
     * @param filename The save file path.
     * @param lastIndex Continues off the last read index.
     * */

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

    public static int getLastIndex(){return counter;}
}
