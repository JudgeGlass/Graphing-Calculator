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

import FileIO.SaveSettings;

/***
 * All of the program information.
 * */

public class ApplicationInfo {
    public static final String PRODUCT_NAME = "Graphing Calculator";
    public static final String VERSION = "1.1.1 ALPHA 2";
    public static final String BUILD_DATE = "WIP";
    public static final boolean UNSTABLE_BUILD = true;
    public static final String SOURCE_URL = "https://github.com/JudgeGlass/Graphing-Calculator";
    public static final String AUTHOR = "Hunter Wilcox";
    public static final String TESTED_JRES = "[8, 9, 10]";
    public static final String LICENSE = "GPLv3.0";

    public enum Shape{
        CIRCLE,
        TRIANGLE,
        SQUARE,
        LABEL,
        SEGMENT
    }

    public static Shape currentShape = Shape.CIRCLE;
    public static boolean useDegrees = false;

    public static SaveSettings STATIC_SAVE = null;
}
