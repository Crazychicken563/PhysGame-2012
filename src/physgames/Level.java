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

    public void getCode(boolean all) {
        System.out.println("level = new Level(new Point" + launch.toString() + ", new Point" + target.toString() + ");");
        System.out.println("level.setMessage(\"Level_Description\");");
        System.out.println("level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));");
        System.out.println("level.setWind(" + getWind() + ");");
        if (all) {
            for (Wall w : walls) {
                System.out.println(w.getCode());
            }
        }
        for (Wall wall : userWalls) {
            if (wall.getEnd().getX() > 200) {
                System.out.println(wall.getCode());
            }
        }
        for (GravTube grav : gravs) {
            if (grav.getTL().getIntX() > 200) {
                System.out.println(grav.getCode());
            }
        }
        //int dx = 0;
        //for(int i = 0;i<10;i++)
        //{
        //    level.addUserWall(new Wall(10, 60+dx, 190, 60+dx, 1));
        //    dx+=10;
        //}
        System.out.println("levels.add(level);");
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

    public void setLaunch(int x, int y) {
        launch.setX(x);
        launch.setY(y);
    }

    public void setTarget(int x, int y) {
        target.setX(x);
        target.setY(y);
    }
}
