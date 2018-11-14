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

package UI;

import Math.FunctionHolder;

public class GraphWindow {
    enum DrawType{
        SOLD_TOP,
        SOLD_BOTTOM,
        LINE,
        CIRCLE
    }


    public double xMin;
    public double xMax;
    public double yMin;
    public double yMax;
    public int pixelWidth;
    public int pixelHeight;
    public double xScale;
    public double yScale;
    public double resolution = 1;
    public double tableInc;
    public boolean drawLines;
    public DrawType currentDrawType = DrawType.LINE;
    public FunctionHolder fh;

    public GraphWindow(int xMin, int xMax, int yMin, int yMax, int pixelWidth, int pixelHeight){
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        this.xScale = (this.xMax-this.xMin) /(double) pixelWidth;
        this.yScale = (this.yMax-this.yMin) / (double)pixelHeight;
        drawLines = true;
        tableInc = 1;
    }

    public void rescale() {
        this.xScale = (xMax - xMin) / ((double) pixelWidth);
        this.yScale = (yMax - yMin) / ((double) pixelHeight);
    }

    public int windowToPixelY(double y) {
        return pixelHeight - (int) ((y - yMin) / yScale);
    }

    public int windowToPixelX(double x) {
        return (int) ((x - xMin) / xScale);
    }

    public void setFunction(String[] function) {
        fh = new FunctionHolder(function);
    }
}
