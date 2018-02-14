package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import functions.*;

public class Graph extends JPanel {
    public String function;

    private GraphWindow graphWindow;
    private double mouseX = 0;
    private double mouseY = 0;
    private Function f;

   // private ArrayList<Line> lines;

    public Graph(final String function, GraphWindow window){
        this.function = function;
        graphWindow = window;

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX() * graphWindow.xScale + graphWindow.xMin;
                mouseY = (graphWindow.pixelHeight - e.getY()) * graphWindow.yScale + graphWindow.yMin;
                System.out.println(e.getX());
            }
        });

    }

    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //graphWindow = new GraphWindow(-10, 10, -10, 10, 789, 744);
        graphWindow.pixelWidth = getSize().width;
        graphWindow.pixelHeight = getSize().height;
        graphWindow.rescale();

        g.setColor(Color.BLACK);
        g.drawOval(0, 0, 10, 10);
        drawGrid(g);
        makeAxis(g);
        function(g);
        drawCoords(g);
        repaint();
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

    private void circle(Graphics g, Color color, boolean filled, double x1, double y1){
        int x = (int)((x1 - graphWindow.xMin) / graphWindow.xScale);
        int y = graphWindow.pixelHeight - (int)((y1 - graphWindow.yMin) / graphWindow.yScale);
        g.setColor(color);
        g.drawOval(x-2, y-2, 5, 5);
        if(filled)
            g.fillOval(x-2, y-2, 5, 5);
    }

    private void drawLine(Graphics g, double x1, double y1){
        int x = (int)((x1 - graphWindow.xMin) / graphWindow.xScale);
        int y = graphWindow.pixelHeight - (int)((y1 - graphWindow.yMin) / graphWindow.yScale);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, 2, 2);
        g.fillOval(x, y, 2, 2);
    }

    private void drawCoords(Graphics g){
        g.setColor(Color.RED);
        g.drawString("X: " + Double.toString(mouseX), 0, graphWindow.pixelHeight - 12);
        g.drawString("Y: " + Double.toString(mouseY), 0, graphWindow.pixelHeight);

        circle(g, Color.BLUE, false, mouseX, mouseY);
    }

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

    private void sLine(Graphics g, double x1, double y1, double x2, double y2){
        int xG = (int)((x1 - graphWindow.xMin) / graphWindow.xScale);
        int yG = graphWindow.pixelHeight - (int)((y1 - graphWindow.yMin) / graphWindow.yScale);

        int xx = (int)((x2 - graphWindow.xMin) / graphWindow.xScale);
        int yy = graphWindow.pixelHeight - (int)((y2 - graphWindow.yMin) / graphWindow.yScale);
        g.drawLine(xG, yG, xx, yy);
    }

    private void function(Graphics g){
        ArrayList<String>vars = new ArrayList<>();
        vars.add("y");
        vars.add("x");
        f = TokenizedFunctionFactory.createFunction(function, vars);

        double[] arg = new double[2];
        arg[0] = 1.0;
        arg[1] = 4.0;

        //System.out.println(f.evaluate(new FunctionArguments(arg)));
        if(!function.isEmpty()) {
            double lastX = graphWindow.xMin;
            arg[1] = lastX;
            double lastY = f.evaluate(new FunctionArguments(arg));
            double adder = graphWindow.resolution;
            for (double i = graphWindow.xMin; i <= graphWindow.xMax; i += adder) {
                try {
                    arg[1] = i;

                    sLine(g, lastX, lastY, i, f.evaluate(new FunctionArguments(arg)));

                    lastX = i;
                    lastY = f.evaluate(new FunctionArguments(arg));
                    //sqrt(abs(i))+2
                }catch (RuntimeException e){
                    continue;
                }
            }
        }
    }

    public Function getFunction(){
        return f;
    }

    private double function(double x, double exp){
        return Math.pow(x, exp);
    }

}
