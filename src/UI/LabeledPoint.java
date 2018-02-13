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
import java.awt.Font;
import java.awt.Graphics;

/**
 * A point that appears on a Graph with a label
 */
public class LabeledPoint {
    private PointD point;
    private String label;
    private Color color;
    
    public LabeledPoint(PointD point, String label, Color color) {
        this.point = point;
        this.label = label;
        this.color = color;
    }
    
    public void draw(Graphics g, Font f, GraphWindow window) {
        g.setColor(color);
        g.setFont(f);
        int x = (int) ((point.x - window.xMin) / window.xScale);
        int y = window.pixelHeight - (int)((point.y - window.yMin) / window.yScale);
        
        // Diameter 4, hollow circle
        g.drawOval(x-2, y-2, 4, 4);
        int hOff = g.getFontMetrics(f).getHeight();
        int wOff = g.getFontMetrics(f).stringWidth(label);
        g.drawString(label, x - wOff / 2, y + hOff);
    }
    
}
