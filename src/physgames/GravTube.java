package physgames;

import java.util.ArrayList;

public class GravTube {

    private Point tl;
    private Point tr;
    private Point bl;
    private Point br;
    private ArrayList<Wall> bounds;
    private double g;

    public GravTube(int topLeftx, int topLefty, int bottomRightx, int bottomRighty) {
        tl = new Point(topLeftx, topLefty);
        br = new Point(bottomRightx, bottomRighty);
        tr = new Point(br.getX(), tl.getY());
        bl = new Point(tl.getX(), br.getY());
        g = 0;
        bounds = new ArrayList<>();
        bounds.add(new Wall(tl, bl, 4));
        bounds.add(new Wall(tr, br, 4));
        bounds.add(new Wall(tl, tr, 4));
        bounds.add(new Wall(bl, br, 4));
    }

    public GravTube(int topLeftx, int topLefty, int bottomRightx, int bottomRighty, double gravity) {
        tl = new Point(topLeftx, topLefty);
        br = new Point(bottomRightx, bottomRighty);
        tr = new Point(br.getX(), tl.getY());
        bl = new Point(tl.getX(), br.getY());
        g = gravity;
        bounds = new ArrayList<>();
        bounds.add(new Wall(tl, bl, 4));
        bounds.add(new Wall(tr, br, 4));
        bounds.add(new Wall(tl, tr, 4));
        bounds.add(new Wall(bl, br, 4));
    }

    public Point getBR() {
        return br;
    }

    public Point getBL() {
        return bl;
    }

    public Point getTL() {
        return tl;
    }

    public Point getTR() {
        return tr;
    }

    public void setTL(int topLeftx, int topLefty) {
        tl = new Point(topLeftx, topLefty);
        tr = new Point(br.getX(), tl.getY());
        bl = new Point(tl.getX(), br.getY());
        bounds = new ArrayList<>();
        bounds.add(new Wall(tl, bl, 4));
        bounds.add(new Wall(tr, br, 4));
        bounds.add(new Wall(tl, tr, 4));
        bounds.add(new Wall(bl, br, 4));
    }

    public void setBR(int bottomRightx, int bottomRighty) {
        br = new Point(bottomRightx, bottomRighty);
        tr = new Point(br.getX(), tl.getY());
        bl = new Point(tl.getX(), br.getY());
        bounds = new ArrayList<>();
        bounds.add(new Wall(tl, bl, 4));
        bounds.add(new Wall(tr, br, 4));
        bounds.add(new Wall(tl, tr, 4));
        bounds.add(new Wall(bl, br, 4));
    }

    public void setGravity(double gravity) {
        g = gravity;
    }

    public double getGravity() {
        return g;
    }

    public ArrayList<Wall> getWalls() {
        return bounds;
    }
    
    public String getCode()
    {
        return "level.addGravTube(new GravTube("+getTL().getIntX()+","+getTL().getIntY()+","+getBR().getIntX()+","+getBR().getIntY()+","+getGravity()+"));";
    }
}
