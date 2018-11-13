package UI;

import Program.ApplicationInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class ShapeDrawer extends JComponent {

    private double mouseX;
    private double mouseY;

    public static ArrayList<CircleInfo> circleInfo = new ArrayList<>();
    public static ArrayList<LabelInfo> labelInfo = new ArrayList<>();
    public static ArrayList<SegmentInfo> segmentInfo = new ArrayList<>();

    private GraphWindow window;
    private JComboBox combox;

    private PointD circleCenter = new PointD(0, 0);

    private boolean label = false;
    private String message = "";

    private PointD seg1;
    private PointD seg2;

    public ShapeDrawer(final GraphWindow window, final JComboBox combox){
        this.window = window;
        this.combox = combox;
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX() * window.xScale + window.xMin; // Gets the mouse X
                mouseY = (window.pixelHeight - e.getY()) * window.yScale + window.yMin; // Gets the mouse Y
                repaint();
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ApplicationInfo.Shape shape = ApplicationInfo.currentShape;

                switch (shape){
                    case CIRCLE:
                        new CircleWindow(mouseX, mouseY);
                        break;
                    case TRIANGLE:

                        break;
                    case SEGMENT:
                        if(seg1 == null){
                            seg1 = new PointD(mouseX, mouseY);
                        }else{
                            seg2 = new PointD(mouseX, mouseY);
                            addSegment();
                        }
                        break;
                    case LABEL:
                        addLabel();
                        break;
                }
            }
        });
    }

    private void drawText(Graphics g, Color c, double x, double y, String text, int textSize){
        int xx = (int)((x - window.xMin) / window.xScale);
        int yy = window.pixelHeight - (int)((y - window.yMin) / window.yScale);
        g.setColor(c);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, textSize));
        g.drawString(text, xx, yy);
    }

    private void drawLine(Graphics g, double x1, double y1, double x2, double y2){
        int xG = (int)((x1 - window.xMin) / window.xScale);
        int yG = window.pixelHeight - (int)((y1 - window.yMin) / window.yScale);

        int xx = (int)((x2 - window.xMin) / window.xScale);
        int yy = window.pixelHeight - (int)((y2 - window.yMin) / window.yScale);
        g.drawLine(xG, yG, xx, yy);
    }

    private void circle(Graphics g, Color color, boolean filled, double x1, double y1, int size){
        int x = (int)((x1 - window.xMin) / window.xScale);
        int y = window.pixelHeight - (int)((y1 - window.yMin) / window.yScale);
        g.setColor(color);
        g.drawOval(x-(size / 2), y-(size / 2), size, size);
        if(filled)
            g.fillOval(x-(size / 2), y-(size / 2), size, size);
    }

    private void drawCoords(Graphics g){
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        g.setColor(Color.BLACK);
        g.drawString("X: " + String.format("%.2f", mouseX), 5, this.getHeight() - 20);
        g.drawString("Y: " + String.format("%.2f", mouseY), 5, this.getHeight() - 5);
    }

    private void verticalLine(Graphics g, Color color, double x1){
        g.setColor(color);
        int x = (int) ((x1 - window.xMin) / window.xScale);
        g.drawLine(x, 0, x, window.pixelHeight);
    }

    private void horizontalLine(Graphics g, Color color, double y1){
        g.setColor(color);
        int y = window.pixelHeight - (int) ((y1 - window.yMin) / window.yScale);
        g.drawLine(0, y, window.pixelWidth, y);
    }

    private void drawGrid(Graphics g) {
        for (int i = (int) window.xMin; i <= window.xMax; i++) {
            if (i == 0)
                continue;
            verticalLine(g, Color.LIGHT_GRAY, i);
        }

        for (int i = (int) window.yMin; i <= window.yMax; i++) {
            if (i == 0)
                continue;
            horizontalLine(g, Color.LIGHT_GRAY, i);
        }
    }

    private void makeAxis(Graphics g){
        horizontalLine(g, Color.BLACK, 0);
        verticalLine(g, Color.BLACK, 0);

        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

        // From "GraphingCalculator" by Justin Wilcox https://github.com/Nitori-/GraphingCalculator
        g.drawString((float) window.yMax + "", window.pixelWidth / 2, 11);
        g.drawString((float) window.yMin + "", window.pixelWidth / 2,
                window.pixelHeight);
        g.drawString((float) window.xMin + "", 0, window.pixelHeight / 2);

        int width = g.getFontMetrics(new Font(Font.SANS_SERIF, Font.BOLD, 15)).stringWidth(
                "" + (float) window.xMax);
        g.drawString((float) window.xMax + "", window.pixelWidth - width,
                window.pixelHeight / 2);
        //////////////
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        drawCoords(g);
        makeAxis(g);
        drawGrid(g);
        circle(g, Color.RED, false, mouseX, mouseY, 5);
        drawCustomCircle(g);
        drawLabels(g);
        drawSegments(g);
    }

    //Draw custom circle
    private void drawCustomCircle(Graphics g){
        for(final CircleInfo info: circleInfo) {

            circle(g, info.getColor(), true, info.getCenter().x, info.getCenter().y, 5);

            double lastX = info.getCenter().x + info.getRadius();
            double lastY = info.getCenter().y;

            for (int d = 0; d <= 360; d++) {
                g.setColor(info.getColor());
                double rise = info.getRadius() * sin(d);
                double run = info.getRadius() * cos(d);

                drawText(g, info.getColor(), info.getCenter().x - .1, info.getCenter().y + .3, info.getName(), 12);
                drawLine(g, lastX, lastY, info.getCenter().x + run, info.getCenter().y + rise);

                lastX = info.getCenter().x + run;
                lastY = info.getCenter().y + rise;
            }
        }
    }

    private void drawLabels(Graphics g){
        for(final LabelInfo info: labelInfo){
            drawText(g, Color.BLACK, info.getX(), info.getY(), info.getText(), 12);
        }
    }

    private void drawSegments(Graphics g){
        g.setColor(Color.BLACK);
        for(final SegmentInfo info: segmentInfo){
            drawLine(g, info.getPos1().x, info.getPos1().y, info.getPos2().x, info.getPos2().y);
        }
    }

    private double sin(final double degree){
        return Math.sin(Math.toRadians(degree));
    }

    private double cos(final double degree){
        return Math.cos(Math.toRadians(degree));
    }

    private void addLabel(){
        try{
            String text = JOptionPane.showInputDialog(null, "Text:");
            if(text == null) return;
            labelInfo.add(new LabelInfo(text, mouseX, mouseY));
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSegment(){
        try{
            double x = Double.parseDouble(JOptionPane.showInputDialog(null, "Position 1 X:", seg1.x));
            double y = Double.parseDouble(JOptionPane.showInputDialog(null, "Position 1 Y:", seg1.y));
            double xx = Double.parseDouble(JOptionPane.showInputDialog(null, "Position 2 X:", seg2.x));
            double yy = Double.parseDouble(JOptionPane.showInputDialog(null, "Position 2 Y:", seg2.y));

            seg1.x = x;
            seg1.y = y;
            seg2.x = xx;
            seg2.y = yy;

            segmentInfo.add(new SegmentInfo(seg1, 'A', seg2, 'B'));

            seg1 = null;
            seg2 = null;
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
