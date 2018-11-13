package UI;

public class SegmentInfo {
    private PointD pos1;
    private char pos1Name;

    private PointD pos2;
    private char pos2Name;

    public SegmentInfo(final PointD pos1, final char pos1Name, final PointD pos2, final char pos2Name){
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.pos1Name = pos1Name;
        this.pos2Name = pos2Name;
    }

    public PointD getPos1() {
        return pos1;
    }

    public char getPos1Name() {
        return pos1Name;
    }

    public PointD getPos2() {
        return pos2;
    }

    public char getPos2Name() {
        return pos2Name;
    }

}
