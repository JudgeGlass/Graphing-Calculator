package UI;

import UI.PointD;

import java.awt.*;

public class CircleInfo {
    private PointD center;
    private double r;
    private String name;
    private Color color;

    public CircleInfo(final String name, final PointD center, final double r, final Color color){
        this.name = name;
        this.center = center;
        this.r = r;
        this.color = color;
    }

    public PointD getCenter(){
        return center;
    }

    public double getRadius(){
        return r;
    }

    public String getName(){ return name; }

    public Color getColor(){ return color; }
}
