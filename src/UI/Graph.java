package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import functions.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class Graph extends JPanel {
    public String function;

    private GraphWindow graphWindow;
    private double mouseX = 0;
    private double mouseY = 0;
    private Function f;
    public ArrayList<PointD> points;
    public ArrayList<String> vars;

    public Graph(final String function, GraphWindow window){
        this.function = function;
        graphWindow = window;
        points = new ArrayList<>();

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                repaint2();
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

    /**
     * Shows cursor position
     * */

    private void drawCoords(Graphics g){
        g.setColor(Color.RED);
        g.drawString("X: " + String.format("%.2f", mouseX), 0, graphWindow.pixelHeight - 12);
        g.drawString("Y: " + String.format("%.2f", mouseY), 0, graphWindow.pixelHeight);

        circle(g, Color.BLUE, false, mouseX, mouseY, 5);
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
        try {
            f = TokenizedFunctionFactory.createFunction(function, vars); // Initializes the function
        }catch (RuntimeException e){
            e.printStackTrace();
            function = "";
        }

        double[] arg = new double[2]; // Sets the first arguments
        arg[0] = 1.0;
        arg[1] = 4.0;

        if(!function.isEmpty()) { // Checks to see if a function is entered
            double lastX = graphWindow.xMin; // The first x is the first point of the line

            arg[1] = lastX;
            double lastY = f.evaluate(new FunctionArguments(arg)); // The first y is the first point of the line

            double adder = graphWindow.resolution; // How many is incremented

            for (double i = graphWindow.xMin; i <= graphWindow.xMax; i += adder) {
                try {
                    arg[1] = i; // Makes arg[1] the value of i

                    double y = f.evaluate(new FunctionArguments(arg));
                    if(Double.isNaN(y)){
                        drawLine(g, i, y, i, y);
                        lastX = 0;
                        lastY = 0;
                        continue;
                    }
                    drawLine(g, lastX, lastY, i, y); // Draws the line

                    lastX = i; // Sets lastX as i
                    lastY = y; // Sets lastY as the function of i
                }catch (RuntimeException e){
                    continue;
                }
            }
        }

        if(points.size() != 0){
            for(int i = 0; i < points.size(); i++){
                System.out.println(points.get(i).toString());
                circle(g, Color.RED, true, points.get(i).x, points.get(i).y, 7);
            }
        }
    }

    /**
     * Returns the function
     * */

    public Function getFunction(){
        return f;
    }

    public void repaint2(){
        repaint();
    }
}
