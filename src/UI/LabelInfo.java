package UI;

public class LabelInfo {
    private double x;
    private double y;

    private String text;

    public LabelInfo(final String text, final double x, final double y){
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public double getX(){ return x; }
    public double getY(){ return y; }
    public String getText(){ return text; }
}
