package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShapeDrawer extends JComponent {

    private double mouseX;
    private double mouseY;

    private GraphWindow window;
    private JComboBox combox;

    private PointD circleCenter = new PointD(0, 0);

    private boolean label = false;
    private String message = "";

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
                if(combox.getSelectedItem().equals("Label")){
                    message = JOptionPane.showInputDialog(null, "Message", "Message", JOptionPane.INFORMATION_MESSAGE);
                    if(message.isEmpty()){
                        label = true;
                    }
                }
            }
        });
    }

    private void drawText(Graphics g, Color c, double x, double y, String text, int textSize){
        int xx = (int)((x - window.xMin) / window.xScale);
        int yy = window.pixelHeight - (int)((y - window.yMin) / window.yScale);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, textSize));
        g.setColor(c);
        g.drawString(text, xx, yy);
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

    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, this.getWidth(), this.getHeight());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        drawCoords(g);
        circle(g, Color.RED, false, mouseX, mouseY, 5);

        if(label){
            drawText(g,Color.BLACK, mouseX, mouseY, message, 15);
        }

    }
}
