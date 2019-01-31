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

import Program.ApplicationInfo;
import Program.Log;
import UI.CalculatorWindow;

public class Main {
    public static void main(String args[]){
        Log.info("OS Name:\t\t" + System.getProperty("os.name"));
        Log.info("OS Version:\t\t" + System.getProperty("os.version"));
        Log.info("OS Arch:\t\t" + System.getProperty("os.arch"));
        Log.info("Java Version:\t" + System.getProperty("java.version"));
        Log.info("Program Version:\t" + ApplicationInfo.VERSION);

        Log.info("Starting....");

        new CalculatorWindow();
    }
}
