package physgames;

import java.util.ArrayList;

public class Level {

    private ArrayList<Wall> walls;
    private ArrayList<Wall> userWalls;
    private Point launch;
    private Point target;
    private double wind;
    private String message;
    private ArrayList<GravTube> gravs;

    public Level(Point p, Point t) {
        walls = new ArrayList<>();
        userWalls = new ArrayList<>();
        launch = new Point(p);
        target = new Point(t);
        wind = 0;
        message = "";
        gravs = new ArrayList<>();
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void addWall(Wall w) {
        walls.add(w);
    }

    public ArrayList<Wall> getUserWalls() {
        return userWalls;
    }

    public void getUserWallsCode() {
        System.out.println("level.setWind("+getWind()+");");
        for (Wall wall : userWalls) {
            if (wall.getEnd().getX() > 200) {
                System.out.println(wall.getCode());
            }
        }
        for(GravTube grav : gravs)
        {
            if(grav.getTL().getIntX()>200){
                System.out.println(grav.getCode());
            }
        }
        System.out.println("----------------------------------------------------");
    }

    public void addUserWall(Wall w) {
        userWalls.add(w);
    }

    public void setWind(double w) {
        wind = w;
    }

    public void setMessage(String in) {
        message = in;
    }

    public String getMessage() {
        return message;
    }

    public double getWind() {
        return wind;
    }

    public Point getStart() {
        return launch;
    }

    public Point getTarget() {
        return target;
    }

    public void addGravTube(GravTube g) {
        gravs.add(g);
    }

    public ArrayList<GravTube> getGravs() {
        return gravs;
    }
}
