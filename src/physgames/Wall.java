package physgames;

final class Wall {

    private Point start;
    private Point end;
    private int type;
    //1: normal
    //2: bouncy
    //3: sticky

    public Wall(Point s, Point e, int t) {
        start = new Point(s);
        end = new Point(e);
        type = t;
        resetCoords();
    }

    public Wall(int x1, int y1, int x2, int y2, int t) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
        resetCoords();
        type = t;
    }

    public void resetCoords() {
        if (end.getX() < start.getX()) {
            Point temp = new Point(end);
            end = new Point(start);
            start = new Point(temp);
        }
        if (x2() - x1() < 4) {
            end = new Point(start.getX() + 4, end.getY());
        }
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public int x1() {
        return start.getIntX();
    }

    public int y1() {
        return start.getIntY();
    }

    public int x2() {
        return end.getIntX();
    }

    public int y2() {
        return end.getIntY();
    }
    
    public void setType(int t)
    {
        type = t;
    }
    
    public int getType()
    {
        return type;
    }

    public void setStart(Point s) {
        start = new Point(s);
        //resetCoords();
    }

    public void setEnd(Point e) {
        end = new Point(e);
        //resetCoords();
    }

    @Override
    public String toString() {
        return start.getX() + "," + start.getY() + "," + end.getX() + "," + end.getY();
    }
}
