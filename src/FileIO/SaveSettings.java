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

import Program.ApplicationInfo;
import Program.Log;
import UI.*;
import functions.FunctionStore;

import java.io.File;

public class SaveSettings {
    private String filename;
    private String conf = "";
    private GraphWindow window;
    private Graph graph;
    private ListManager listManager;


    /***
     * Initialization of the save data handler
     *
     * @param filename The save file location and name.
     * @param window The graph window
     * @param graph The main graph
     * */

    public SaveSettings(String filename, GraphWindow window, Graph graph, ListManager listManager){
        this.filename = filename;
        this.window = window;
        this.graph = graph;
        this.listManager = listManager;
    }

    /***
     * The function that is called every time a piece of data changes.
     * */

    public void update(){
        Log.info("Adding graph settings to save...");
        conf = "";
        addToConf("version=" + ApplicationInfo.VERSION);
        addToConf("qal="  + window.resolution);
       addToConf("xmin=" + window.xMin);
       addToConf("xmax=" + window.xMax);
       addToConf("ymin=" + window.yMin);
       addToConf("ymax=" + window.yMax);

       if(window.fh != null) {
           Log.info("Functions found. Adding to save...");
           addToConf("YVal=true");
           addToConf("Y1=" + window.fh.y1);
           addToConf("Y2=" + window.fh.y2);
           addToConf("Y3=" + window.fh.y3);
           addToConf("Y4=" + window.fh.y4);
           addToConf("Y5=" + window.fh.y5);
           addToConf("deg=" + ApplicationInfo.useDegrees);
       }else {
           Log.info("No functions found");
           addToConf("YVal=false");
           addToConf("Y1=");
           addToConf("Y2=");
           addToConf("Y3=");
           addToConf("Y4=");
           addToConf("Y5=");
           addToConf("deg=false");
       }

       Log.info("Adding list data to save...");
       addToConf("L1=" + listManager.toString(ListManager.Lists.L1));
       addToConf("L2=" + listManager.toString(ListManager.Lists.L2));
       addToConf("L3=" + listManager.toString(ListManager.Lists.L3));

       addToConf("xList=" + listManager.xList);
       addToConf("YList=" + listManager.yList);

       Log.info("Adding user defined functions to save...");
       addToConf("## DEF FUNCTIONS ##");
       for(String functionID: FunctionStore.getStore().getIdNames()){
           if(functionID.equals("y1") || functionID.equals("y2") || functionID.equals("y3") || functionID.equals("y4") || functionID.equals("y5")) continue;
           addToConf(functionID + "=" + FunctionStore.getStore().getHashFunctions().get(functionID));
       }

       addToConf("## END DEF ##");

       Log.info("Adding shape data to save...");
       addToConf("## SHAPE START ##");
       for (final CircleInfo info: ShapeDrawer.circleInfo){
           addToConf("CIR;" + info.getName() + ";" + info.getCenter().x + ";" + info.getCenter().y + ";"
           + info.getRadius() + ";" + info.getColorString());
       }

        for (final SegmentInfo info: ShapeDrawer.segmentInfo){
            addToConf("SEG;" + info.getPos1().x + ";" + info.getPos1().y + ";" + info.getPos2().x + ";" + info.getPos2().y);
        }

        for (final LabelInfo info: ShapeDrawer.labelInfo){
            addToConf("LBL;" + info.getText() + ";" + info.getX() + ";" + info.getY());
        }

        addToConf("## END SHAPE ##");

        Log.info("Writing save file...");

       writeSave();
    }

    private void addToConf(String text){
        conf += text + "\n";
    }

    /***
     * Writes the save file every time a piece of data changes.
     * */

    private void writeSave(){
        if(new File(filename).exists()){
            new File(filename).delete();
        }
        Utils.writeFile(filename, conf);
    }
}
