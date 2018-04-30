package UI;

import Math.FunctionHolder;

public class GraphWindow {
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
