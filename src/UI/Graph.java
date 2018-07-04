package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Program.CorrectFunction;
import functions.*;

public class Graph extends JPanel {
    private GraphWindow graphWindow;

    private double mouseX = 0;
    private double mouseY = 0;
    private double realMouseX;
    private double realMouseY;

    public ArrayList<PointD> points;

    private JTextArea area;

    public Graph(GraphWindow window, JTextArea area){
        graphWindow = window;
        points = new ArrayList<>();
        this.area = area;

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                repaint2();
                realMouseX = e.getX();
                realMouseY = e.getY();
                mouseX = e.getX() * graphWindow.xScale + graphWindow.xMin; // Gets the mouse X
                mouseY = (graphWindow.pixelHeight - e.getY()) * graphWindow.yScale + graphWindow.yMin; // Gets the mouse Y
            }
        });
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        graphWindow.pixelWidth = getSize().width;
        graphWindow.pixelHeight = getSize().height;
        graphWindow.rescale();

        if(graphWindow.drawLines) {
            drawGrid(g);
        }

        makeAxis(g);
        function(g);
        drawCoords(g);
    }

    private void verticalLine(Graphics g, Color color, double x1){
        g.setColor(color);
        int x = (int) ((x1 - graphWindow.xMin) / graphWindow.xScale);
        g.drawLine(x, 0, x, graphWindow.pixelHeight);
    }

    private void horizontalLine(Graphics g, Color color, double y1){
        g.setColor(color);
        int y = graphWindow.pixelHeight - (int) ((y1 - graphWindow.yMin) / graphWindow.yScale);
        g.drawLine(0, y, graphWindow.pixelWidth, y);
    }

    /**
     * Draw a filled or non-filled circle
     * */

    private void circle(Graphics g, Color color, boolean filled, double x1, double y1, int size){
        int x = (int)((x1 - graphWindow.xMin) / graphWindow.xScale);
        int y = graphWindow.pixelHeight - (int)((y1 - graphWindow.yMin) / graphWindow.yScale);
        g.setColor(color);
        g.drawOval(x-(size / 2), y-(size / 2), size, size);
        if(filled)
            g.fillOval(x-(size / 2), y-(size / 2), size, size);
    }

    private void box(Graphics g, double x1, double y1, int size){
        int x = (int)((x1 - graphWindow.xMin) / graphWindow.xScale-2.5);
        int y = graphWindow.pixelHeight-2 - (int)((y1 - graphWindow.yMin) / graphWindow.yScale);
        g.setColor(Color.BLACK);

        g.drawRect(x, y, size, size);
    }

    /**
     * Shows cursor position
     * */

    private void drawCoords(Graphics g){
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        g.setColor(Color.RED);
        g.drawString("X: " + String.format("%.2f", mouseX), 0, graphWindow.pixelHeight - 12);
        g.drawString("Y: " + String.format("%.2f", mouseY), 0, graphWindow.pixelHeight);

        circle(g, Color.BLUE, false, mouseX, mouseY, 4);
    }

    /**
     * Draws the dark x and y axis lines (0, 0);
     * */

    private void makeAxis(Graphics g){
        horizontalLine(g, Color.BLACK, 0);
        verticalLine(g, Color.BLACK, 0);

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        // From "GraphingCalculator" by Justin Wilcox https://github.com/Nitori-/GraphingCalculator
        g.drawString((float) graphWindow.yMax + "", graphWindow.pixelWidth / 2, 11);
        g.drawString((float) graphWindow.yMin + "", graphWindow.pixelWidth / 2,
                graphWindow.pixelHeight);
        g.drawString((float) graphWindow.xMin + "", 0, graphWindow.pixelHeight / 2);

        int width = g.getFontMetrics(new Font(Font.SANS_SERIF, Font.BOLD, 15)).stringWidth(
                "" + (float) graphWindow.xMax);
        g.drawString((float) graphWindow.xMax + "", graphWindow.pixelWidth - width,
                graphWindow.pixelHeight / 2);
        //////////////
    }

    private void drawText(Graphics g, Color c, double x, double y, String text, int textSize){
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, textSize));
        g.setColor(c);
        g.drawString(text, (int)x, (int)y);
    }

    /**
     * Draws a grid across the xMin to xMax and yMin to yMax
     * */

    private void drawGrid(Graphics g){
        for(int i = (int) graphWindow.xMin; i<= graphWindow.xMax; i++){
            if(i == 0)
                continue;
            verticalLine(g, Color.LIGHT_GRAY, i);
        }

        for(int i = (int) graphWindow.yMin; i<= graphWindow.yMax; i++){
            if(i == 0)
                continue;
            horizontalLine(g, Color.LIGHT_GRAY, i);
        }
    }

    /**
     * Used for drawing the line
     * */

    private void drawLine(Graphics g, double x1, double y1, double x2, double y2){
        //g.setColor(Color.BLACK);
        int xG = (int)((x1 - graphWindow.xMin) / graphWindow.xScale);
        int yG = graphWindow.pixelHeight - (int)((y1 - graphWindow.yMin) / graphWindow.yScale);

        int xx = (int)((x2 - graphWindow.xMin) / graphWindow.xScale);
        int yy = graphWindow.pixelHeight - (int)((y2 - graphWindow.yMin) / graphWindow.yScale);
        g.drawLine(xG, yG, xx, yy);
    }

    /**
     * Draws the line
     * */
    private void function(Graphics g){
        ArrayList<String> vars = new ArrayList<>();
        vars.add("y");
        vars.add("x");

        ArrayList<Function> f = new ArrayList<>();

        if(graphWindow.fh == null) return;
        f.add(TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(graphWindow.fh.y1), vars));
        f.add(TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(graphWindow.fh.y2), vars));
        f.add(TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(graphWindow.fh.y3), vars));
        f.add(TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(graphWindow.fh.y4), vars));
        f.add(TokenizedFunctionFactory.createFunction(CorrectFunction.addMul(graphWindow.fh.y5), vars));

        if(points.size() != 0){
            for(int i = 0; i < points.size(); i++){
                box(g, points.get(i).x, points.get(i).y, 5);
            }
        }

        if(graphWindow.fh == null)
            return;

        if(graphWindow.fh.y1 != null)
            drawText(g, Color.BLACK, 5, 12, "f1(x)= " + graphWindow.fh.y1, 12);
        if(graphWindow.fh.y2 != null)
            drawText(g, Color.BLUE, 5, 25, "f2(x)= " + graphWindow.fh.y2, 12);
        if(graphWindow.fh.y3 != null)
            drawText(g, Color.RED, 5, 38, "f3(x)= " + graphWindow.fh.y3, 12);
        if(graphWindow.fh.y4 != null)
            drawText(g, Color.ORANGE, 5, 51, "f4(x)= " + graphWindow.fh.y4, 12);
        if(graphWindow.fh.y5 != null)
            drawText(g, Color.MAGENTA, 5, 64, "f5(x)= " + graphWindow.fh.y5, 12);

        double[] arg = new double[2]; // Sets the first arguments
        arg[0] = 0.0;
        arg[1] = 4.0;

        if(graphWindow.fh != null) { // Checks to see if a function is entered
            double lastX = 0;

            arg[1] = graphWindow.xMin;
            double lastY;
            double adder = graphWindow.resolution / 100; // How many is incremented
            g.setColor(Color.BLACK);
            for(int a = 0; a < f.size(); ++a) {
                try {
                    if (f.get(a) == null)
                        continue;
                    switch (a){
                        case 1:
                            g.setColor(Color.BLUE);
                            break;
                        case 2:
                            g.setColor(Color.RED);
                            break;
                        case 3:
                            g.setColor(Color.ORANGE);
                            break;
                        case 4:
                            g.setColor(Color.MAGENTA);
                            break;
                    }

                    arg[1] = graphWindow.xMin;
                    lastY = f.get(a).evaluate(new FunctionArguments(arg));
                    lastX = graphWindow.xMin;
                    for (double i = graphWindow.xMin; i <= graphWindow.xMax; i += adder) {
                        try {
                            arg[1] = i; // Makes arg[1] the value of i

                            double y1 = f.get(a).evaluate(new FunctionArguments(arg));
                            if (Double.isNaN(y1)) {
                                lastX = i;
                                arg[1] = i + adder;
                                lastY = f.get(a).evaluate(new FunctionArguments(arg));
                                continue;
                            }

                            drawLine(g, lastX, lastY, i, y1); // Draws the line
                            lastX = i; // Sets lastX as i
                            lastY = y1; // Sets lastY as the function of i

                        }catch (RuntimeException e) {
                            if(!area.getText().contains(e.getMessage()))
                                area.append("Graph Error: " + e.getMessage() + "\n");
                            continue;
                        }
                    }
                }catch (Exception e){
                    continue;
                }
            }
        }
    }

    public void repaint2(){
        repaint();
    }
}
