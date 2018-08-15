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
import UI.PointD;

import java.util.ArrayList;

public class GetScatterPlotSave {
    private String filename;
    private ArrayList<PointD> points;
    private int counter;

    public GetScatterPlotSave(final String filename){
        this.filename = filename;

        points = new ArrayList<>();

        counter = 12;
        while (!Utils.readLine(filename, counter).equals("## PLOT END ##")){
            String[] split = Utils.readLine(filename, counter).split(",");
            points.add(new PointD(Double.parseDouble(split[0]), Double.parseDouble(split[1])));
            counter++;
        }
    }

    public ArrayList<PointD> getPoints() {
        return points;
    }

    public String getFilename(){
        return filename;
    }

    public int getLastLineIndex(){return counter;}

    public String toString(){
        return filename;
    }
}
