/*
 * Copyright 2012 Justin Wilcox
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

import java.awt.Color;
import java.awt.Graphics;

/**
 * An infinite line that appears on a Graph
 */
public class Line {
    private double slope;
    private PointD p1;
    private PointD p2;
    private Color color;

    /**
     * p1 and p2 are points on the line. The slope is then computed from that 
     */
    public Line(PointD p1, PointD p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        if(p1.x != p2.x) {
            slope = (p1.y - p2.y)/(p1.x - p2.x); 
        }
        this.color = color;
    }

    public void drawLine(Graphics g, GraphWindow window) {
        if (p1.x == p2.x) {
            int x = window.windowToPixelX(p1.x);
            g.setColor(color);
            g.drawLine(x, 0, x, window.pixelHeight);
        } else {
            // y = m(x - xi) + yi
            int x1 = window.windowToPixelX(window.xMin);
            int y1 = window.windowToPixelY(slope * (window.xMin - p1.x) + p1.y);
            
            int x2 = window.windowToPixelX(window.xMax);
            int y2 = window.windowToPixelY(slope * (window.xMax - p1.x) + p1.y);
            g.setColor(color);
            g.drawLine(x1, y1, x2, y2);
        }
    }
    
    public PointD getP1() {
        return p1;
    }
    
    public PointD getP2() {
        return p2;
    }
    
    public double getSlope() {
        return slope;
    }
    
    public Color getColor() {
        return color;
    }
}
