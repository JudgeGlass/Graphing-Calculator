package Program;

import FileIO.Utils;
import UI.*;

import java.awt.*;

public class GetShapeSave {
    private static int counter;

    public static void store(final String filename, int lastValue){
        counter = lastValue + 2;

        while(!Utils.readLine(filename, counter).equals("## END SHAPE ##")){
            final String info = Utils.readLine(filename, counter);

            String[] data = info.split(";");

            if(data[0].equals("CIR")){
                double x = Double.parseDouble(data[2]);
                double y = Double.parseDouble(data[3]);
                double r = Double.parseDouble(data[4]);
                ShapeDrawer.circleInfo.add(new CircleInfo(data[1], new PointD(x, y), r, getColorFromString(data[5])));
            }else if(data[0].equals("SEG")){
                double x = Double.parseDouble(data[1]);
                double y = Double.parseDouble(data[2]);
                double xx = Double.parseDouble(data[3]);
                double yy = Double.parseDouble(data[4]);
                PointD pos1 = new PointD(x, y);
                PointD pos2 = new PointD(xx, yy);
                ShapeDrawer.segmentInfo.add(new SegmentInfo(pos1, 'A', pos2, 'B'));
            }else if(data[0].equals("LBL")){
                double x = Double.parseDouble(data[2]);
                double y = Double.parseDouble(data[3]);
                ShapeDrawer.labelInfo.add(new LabelInfo(data[1], x, y));
            }

            counter++;
        }
    }

    private static Color getColorFromString(final String color){
        if(color.equals("red")){
            return Color.RED;
        }else if(color.equals("blue")){
            return Color.BLUE;
        }else if(color.equals("black")){
            return Color.BLACK;
        }else if(color.equals("green")){
            return Color.GREEN;
        }

        return Color.BLACK;
    }
}
