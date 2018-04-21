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

import UI.Graph;
import UI.GraphWindow;
import UI.PointD;

import java.io.File;

public class SaveSettings {
    private String filename;
    private String conf = "";
    private GraphWindow window;
    private Graph graph;

    public SaveSettings(String filename, GraphWindow window, Graph graph){
        this.filename = filename;
        this.window = window;
        this.graph = graph;
    }

    public void update(){
        conf = "";
       addToConf("xmin=" + window.xMin);
       addToConf("xmax=" + window.xMax);
       addToConf("ymin=" + window.yMin);
       addToConf("ymax=" + window.yMax);

       if(window.fh != null) {
           addToConf("YVal=true");
           addToConf("Y1=" + window.fh.y1);
           addToConf("Y2=" + window.fh.y2);
           addToConf("Y3=" + window.fh.y3);
       }else
            addToConf("YVal=false");

       if(graph.points.size() != 0 || graph.points != null){
           addToConf("## PLOT ##");
           for(PointD d: graph.points){
               addToConf(Double.toString(d.x)+","+Double.toString(d.y));
           }
           addToConf("## PLOT END ##");
       }

       writeSave();
    }

    private void addToConf(String text){
        conf += text + "\n";
    }

    private void writeSave(){
        if(new File(filename).exists()){
            new File(filename).delete();
        }
        Utils.writeFile(filename, conf);
    }
}
