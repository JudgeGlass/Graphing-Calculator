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

public class QuadraticFormula {
    public static double plus(final double a, final double b, final double c){
        double top = -b + Math.sqrt((b*b)-4*a*c);
        double bottom = 2 * a;

        return top/bottom;
    }

    public static double minus(final double a, final double b, final double c){
        double top = -b - Math.sqrt((b*b)-4*a*c);
        double bottom = 2 * a;

        return top/bottom;
    }
}
