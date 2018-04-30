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

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static String indexOf(String txt, char ch) {
        return txt.substring(txt.lastIndexOf(ch) + 1);
    }

    public static String readLine(String fileName, int lineNumber) {
        String line;
        try {
            line = Files.readAllLines(Paths.get(fileName)).get(lineNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return line;
    }

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

    public static ArrayList<Integer> sort(ArrayList<Integer> list){
        list.sort(Integer::compareTo);
        return list;
    }
}
