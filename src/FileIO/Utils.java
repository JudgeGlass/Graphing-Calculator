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

package FileIO;

import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Utils {

    /***
     * Allows you to get the characters after a certain character.
     *
     * @param txt A String
     * @param ch The character separator.
     * */

    public static String indexOf(@NotNull String txt, char ch) {
        return txt.substring(txt.lastIndexOf(ch) + 1);
    }

    /***
     * Allows you to read any line in a given file.
     *
     * @param fileName The file name / path.
     * @param lineNumber The you want to read.
     * */

    public static String readLine(@NotNull String fileName, int lineNumber) {
        String line;
        try {
            line = Files.readAllLines(Paths.get(fileName)).get(lineNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return line;
    }

    /***
     * Write any type of string data to a file.
     *
     * @param fileName The file name / path.
     * @param txt The file content.
     * */

    public static void writeFile(String fileName, String txt) {
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.print(txt);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, "Error:\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static ArrayList<String> combineStringList(ArrayList<String> array1, ArrayList<String> array2){
        ArrayList<String> newArray = new ArrayList<>();

        newArray.addAll(array1);
        newArray.addAll(array2);

        return newArray;
    }
}
