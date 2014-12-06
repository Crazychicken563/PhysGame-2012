package physgames;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PhysGames extends Applet implements MouseMotionListener, MouseListener, KeyListener, Runnable {

    private boolean establishedVars;
    private Thread animation = null;
    private double time;
    private ArrayList<Point> coords;
    private ArrayList<Wall> walls;
    private ArrayList<Wall> permWalls = new ArrayList<>();
    private Graphics offScreen;
    private Image image;
    private PhysicsEngine physics;
    private static int ZERO_X = 200;
    private static int ZERO_Y = 500;
    private int xVarGraph;
    private int yVarGraph;
    private static int PIXEL_SCALER = 3;
    private boolean setUpWalls;
    private int moveWallX = -1;
    private int moveWallY = -1;
    private boolean clicked = false;
    private boolean restart = false;
    private ArrayList<Level> levels = new ArrayList<>();
    private int currLevel = 1;
    private int wait = 0;
    private boolean inZeroG;
    private boolean powerBallUnlocked;
    private boolean usingPowerBall;
    private ArrayList<GravTube> gravs;
    private boolean lockLaunch;
    private int currGrav;
    private int enterG = -1;
    private boolean setUpWind = false;

    @Override
    public void init() {
        establishedVars = false;
        setUpWalls = false;
        resize(710, 520);
        image = createImage(710, 520);
        offScreen = image.getGraphics();
        time = 0;
        coords = new ArrayList();
        walls = new ArrayList();
        inZeroG = false;
        powerBallUnlocked = false;
        usingPowerBall = false;
        lockLaunch = false;
        permWalls.add(new Wall(200, 0, 200, 500, 1));
        permWalls.add(new Wall(700, 0, 700, 500, 1));
        permWalls.add(new Wall(200, 0, 700, 0, 1));
        permWalls.add(new Wall(200, 500, 700, 500, 1));
        currGrav = -1;
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        //INIT LEVELS
        //LEVEL Builder
        Level level = new Level(new Point(-100, 200), new Point(100, 250));
        for (int i = 0; i < 100; i++) {
            level.addUserWall(new Wall(10, 60, 190, 60, 1));
            level.addUserWall(new Wall(10, 80, 190, 80, 2));
            level.addUserWall(new Wall(10, 100, 190, 100, 3));
            level.addGravTube(new GravTube(50, 160, 90, 120));
        }
        /*level.addWall(new Wall(532, 146, 532, 372, 1));
         level.addWall(new Wall(534, 146, 644, 146, 1));
         level.addWall(new Wall(532, 372, 644, 372, 1));
         level.addWall(new Wall(644, 372, 644, 146, 1));
         level.addWall(new Wall(517, 141, 542, 141, 3));
         level.addWall(new Wall(295, 316, 532, 154, 1));*/
        levels.add(level);
        //LEVEL 1
        level = new Level(new Point(400, 200), new Point(300, 300));
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.setMessage("This is REALLY hard");
        levels.add(level);
        //LEVEL 2
        level = new Level(new Point(400, 200), new Point(300, 300));
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.setWind(5);
        level.setMessage("Its windy now");
        levels.add(level);
        //LEVEL 3
        level = new Level(new Point(400, 200), new Point(450, 500));
        level.setMessage("Wind is really picking up now");
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.setWind(10);
        level.addWall(new Wall(480, 500, 370, 300, 1));
        level.addWall(new Wall(420, 500, 310, 300, 1));
        levels.add(level);
        //LEVEL 4
        level = new Level(new Point(400, 200), new Point(450, 500));
        level.setMessage("Can you do it without touching\nthe walls?");
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.setWind(10);
        level.addWall(new Wall(480, 500, 370, 300, 3));
        level.addWall(new Wall(420, 500, 310, 300, 3));
        levels.add(level);
        //LEVEL 5
        level = new Level(new Point(125, 400), new Point(400, 100));
        level.setMessage("Here is a wall. Use it.");
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.addWall(new Wall(300, 80, 310, 400, 1));
        level.addWall(new Wall(350, 80, 360, 350, 1));
        level.addWall(new Wall(370, 80, 360, 350, 1));
        level.addWall(new Wall(420, 80, 410, 400, 1));
        level.addWall(new Wall(300, 80, 420, 80, 1));
        level.addWall(new Wall(361, 420, 300, 500, 1));
        level.addWall(new Wall(360, 420, 420, 500, 1));
        //level.addWall(new Wall(359,420,361,420,1));
        level.addUserWall(new Wall(10, 60, 190, 60, 1));
        levels.add(level);
        //LEVEL 6
        level = new Level(new Point(498, 2), new Point(200, 0));
        level.setMessage("Perhaps a bouncy wall will help.");
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.addUserWall(new Wall(10, 60, 190, 60, 2));
        levels.add(level);
        //LEVEL 7
        level = new Level(new Point(125, 400), new Point(540, 210));
        level.setMessage("This is actually difficult");
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.addWall(new Wall(280, 80, 600, 100, 3));
        level.addWall(new Wall(300, 130, 500, 150, 3));
        level.addWall(new Wall(380, 195, 500, 190, 3));
        level.addWall(new Wall(600, 100, 500, 190, 3));
        level.addWall(new Wall(280, 80, 340, 230, 3));
        level.addWall(new Wall(340, 230, 600, 230, 3));
        level.addUserWall(new Wall(10, 60, 190, 60, 1));
        level.addUserWall(new Wall(10, 70, 190, 70, 2));
        levels.add(level);
        //LEVEL 8
        level = new Level(new Point(325, 175), new Point(325, 125));
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.addGravTube(new GravTube(410, 350, 460, 100, 10));
        level.addWall(new Wall(300, 100, 550, 100, 1));
        level.addWall(new Wall(300, 100, 300, 350, 1));
        level.addWall(new Wall(300, 350, 550, 350, 1));
        level.addWall(new Wall(550, 100, 550, 350, 1));
        level.addWall(new Wall(460, 300, 550, 300, 1));
        level.addWall(new Wall(300, 150, 410, 150, 1));
        levels.add(level);
        //LEVEL 8
        level = new Level(new Point(325, 125), new Point(325, 385));
        level.setMessage("Zero g");
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.addGravTube(new GravTube(350, 400, 400, 10));
        level.addWall(new Wall(350, 100, 400, 400, 1));
        level.addWall(new Wall(350, 10, 400, 310, 1));
        level.addWall(new Wall(350, 10, 700, 10, 1));
        level.addWall(new Wall(300, 400, 700, 400, 1));
        level.addWall(new Wall(350, 400, 350, 100, 1));
        level.addWall(new Wall(400, 10, 400, 310, 1));
        level.addWall(new Wall(300, 400, 300, 0, 1));
        levels.add(level);
        //LEVEL 9
        level = new Level(new Point(325, 125), new Point(325, 385));
        level.setMessage("Don't touch the walls");
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.addGravTube(new GravTube(350, 400, 400, 10));
        level.addWall(new Wall(350, 100, 400, 400, 3));
        level.addWall(new Wall(350, 10, 400, 310, 3));
        level.addWall(new Wall(350, 10, 700, 10, 1));
        level.addWall(new Wall(300, 400, 700, 400, 1));
        level.addWall(new Wall(350, 400, 350, 100, 1));
        level.addWall(new Wall(400, 10, 400, 310, 1));
        level.addWall(new Wall(300, 400, 300, 0, 1));
        levels.add(level);
        //LEVEL 10
        level = new Level(new Point(325, 125), new Point(425, 385));
        level.setMessage("Launch it");
        level.addGravTube(new GravTube(200, 500, 200, 500, -9.8));
        level.addGravTube(new GravTube(451, 399, 499, 250, 12));
        level.addWall(new Wall(450, 400, 450, 50, 1));
        level.addWall(new Wall(500, 350, 500, 0, 1));
        level.addWall(new Wall(400, 400, 700, 400, 1));
        level.addWall(new Wall(400, 400, 400, 0, 1));
        levels.add(level);
        //LEVEL 11
        level = new Level(new Point(450, 80), new Point(250, 50));
        level.setMessage("Enjoy :)");//Launch at maxVelocity @ -180
        level.addGravTube(new GravTube(200, 500, 700, 333, 6));
        level.addGravTube(new GravTube(200, 333, 700, 166, -12));
        level.addGravTube(new GravTube(200, 166, 700, 0));
        level.addWall(new Wall(670, 390, 670, 440, 1));
        level.addWall(new Wall(400, 415, 670, 390, 1));
        level.addWall(new Wall(400, 465, 670, 440, 1));
        //level.addUserWall(new Wall(400, 415, 345, 460, 1));//User Wall
        level.addWall(new Wall(400, 465, 405, 500, 3));
        level.addWall(new Wall(345, 460, 340, 300, 3));
        level.addWall(new Wall(300, 460, 200, 300, 3));
        level.addWall(new Wall(300, 460, 200, 470, 3));
        //level.addUserWall(new Wall(230, 280, 250, 210, 2));//User Wall
        level.addWall(new Wall(360, 270, 400, 310, 1));
        level.addWall(new Wall(402, 310, 420, 250, 1));
        level.addWall(new Wall(420, 250, 420, 166, 1));
        level.addWall(new Wall(402, 310, 403, 318, 1));
        level.addWall(new Wall(400, 310, 401, 320, 1));
        level.addWall(new Wall(395, 320, 700, 321, 2));
        level.addWall(new Wall(342, 320, 395, 320, 2));
        level.addWall(new Wall(200, 166, 650, 166, 1));
        level.addWall(new Wall(540, 0, 540, 146, 2));
        level.addWall(new Wall(420, 20, 420, 166, 2));
        level.addWall(new Wall(300, 0, 300, 146, 2));
        //level.addUserWall(new Wall(650, 0, 700, 100, 2));//User Wall
        level.addUserWall(new Wall(10, 60, 190, 60, 1));
        level.addUserWall(new Wall(10, 70, 190, 70, 2));
        level.addUserWall(new Wall(10, 80, 190, 80, 2));
        levels.add(level);
        //currLevel = 12;
        physics = new PhysicsEngine(levels.get(currLevel).getStart().getIntX(), levels.get(currLevel).getStart().getIntY());
        walls = levels.get(currLevel).getUserWalls();
        gravs = levels.get(currLevel).getGravs();
    }

    @Override
    public void start() {
        if (animation == null) {
            animation = new Thread(this, "Thread");
            animation.start();
        }
        JOptionPane.showMessageDialog(this, "This is a physics puzzle game where to objective is to launch the blue ball so that it hits the red target.\n"
                + "The controls are simple:\n"
                + "Set you speed and launch angle with the mouse\n"
                + "Press \"R\" when you want to restart a level\n"
                + "Press \"W\" to move around walls. Press it again when finished\n"
                + "Press \"L\" to lock the launch angle and velocity if you want to move the walls and launch in the same place\n"
                + "Other important info:\n"
                + "Black walls are normal.\n"
                + "Green walls make you bounce higher.\n"
                + "Red walls make you stick to them. This is bad.\n"
                + "Wind, velocity, and moveable walls are located in the top left corner.\n"
                + "The gravity of an area is displayed in the lower left of that area; yes gravity can change.\n"
                + "That is all for instructions, now go solve puzzles!", "WELCOME!", 2);
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        while (animation == thread) {
            repaint();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("Velocity Probably reached Infinity IDK");
                physics = new PhysicsEngine(levels.get(currLevel).getStart().getIntX(), levels.get(currLevel).getStart().getIntY());
                restart = true;
                establishedVars = false;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (physics == null) {
            return;
        }
        offScreen.setColor(Color.white);
        offScreen.fillRect(0, 0, 1000, 1000);
        offScreen.setColor(Color.red);
        offScreen.drawOval(levels.get(currLevel).getTarget().getIntX() - 10, levels.get(currLevel).getTarget().getIntY() - 10, 20, 20);
        //offScreen.drawRect(200, 0, 700, 500);
        offScreen.setColor(Color.blue);
        for (int i = 0; i < gravs.size(); i++) {
            ArrayList<Wall> toDraw = gravs.get(i).getWalls();
            for (int j = 0; j < toDraw.size(); j++) {
                Wall temp = gravs.get(i).getWalls().get(j);
                offScreen.drawLine(temp.x1(), temp.y1(), temp.x2(), temp.y2());
            }
            offScreen.drawString(roundTwoDecimals(gravs.get(i).getGravity()) + "Gs", gravs.get(i).getTL().getIntX(), gravs.get(i).getTL().getIntY());
        }
        if (usingPowerBall) {
            offScreen.setColor(Color.orange);
        }
        offScreen.drawOval(modXToDrawCoord(physics.lastLocation().getIntX() - 2), modYToDrawCoord(physics.lastLocation().getIntY() + 2), 5, 5);
        offScreen.setColor(Color.black);
        if (setUpWalls) {
            for (int i = 0; i < walls.size(); i++) {
                if (i == moveWallX) {
                    offScreen.setColor(Color.red);
                }
                offScreen.drawOval(walls.get(i).x1() - 3, walls.get(i).y1() - 3, 7, 7);
                offScreen.setColor(Color.black);
                if (i == moveWallY) {
                    offScreen.setColor(Color.red);
                }
                offScreen.drawOval(walls.get(i).x2() - 3, walls.get(i).y2() - 3, 7, 7);
                offScreen.setColor(Color.black);
                //offScreen.drawLine(modXToDrawCoord(walls.get(i).x1()), modYToDrawCoord(walls.get(i).y1()), modXToDrawCoord(walls.get(i).x2()), modYToDrawCoord(walls.get(i).y2()));
            }
            if (currLevel == 0) {
                for (int i = 0; i < gravs.size(); i++) {
                    if (i == (moveWallX + 2) * -1) {
                        offScreen.setColor(Color.red);
                    }
                    offScreen.drawOval(gravs.get(i).getTL().getIntX() - 3, gravs.get(i).getTL().getIntY() - 3, 7, 7);
                    offScreen.setColor(Color.black);
                    if (i == (moveWallY + 2) * -1) {
                        offScreen.setColor(Color.red);
                    }
                    offScreen.drawOval(gravs.get(i).getBR().getIntX() - 3, gravs.get(i).getBR().getIntY() - 3, 7, 7);
                    offScreen.setColor(Color.black);
                    //offScreen.drawLine(modXToDrawCoord(walls.get(i).x1()), modYToDrawCoord(walls.get(i).y1()), modXToDrawCoord(walls.get(i).x2()), modYToDrawCoord(walls.get(i).y2()));
                }
            }
        }
        for (int i = 0; i < walls.size(); i++) {
            Wall temp = walls.get(i);
            if (temp.getType() == 2) {
                offScreen.setColor(Color.GREEN);
            }
            if (temp.getType() == 3) {
                offScreen.setColor(Color.red);
            }
            offScreen.drawLine(temp.x1(), temp.y1(), temp.x2(), temp.y2());
            offScreen.setColor(Color.BLACK);
        }
        ArrayList<Wall> levelWalls = levels.get(currLevel).getWalls();
        for (int i = 0; i < levelWalls.size(); i++) {
            Wall temp = levelWalls.get(i);
            if (temp.getType() == 2) {
                offScreen.setColor(Color.GREEN);
            }
            if (temp.getType() == 3) {
                offScreen.setColor(Color.red);
            }
            offScreen.drawLine(temp.x1(), temp.y1(), temp.x2(), temp.y2());
            offScreen.setColor(Color.BLACK);
        }
        offScreen.setColor(Color.BLACK);
        for (int i = 0; i < permWalls.size(); i++) {
            offScreen.drawLine(permWalls.get(i).x1(), permWalls.get(i).y1(), permWalls.get(i).x2(), permWalls.get(i).y2());
        }
        if (!establishedVars && !setUpWalls) {
            xVarGraph = modXToMathCoord(xVarGraph);
            yVarGraph = modYToMathCoord(yVarGraph);
            double tempVelocity = physics.distance(levels.get(currLevel).getStart().getX(), levels.get(currLevel).getStart().getY(), xVarGraph, yVarGraph) / PIXEL_SCALER;
            if (usingPowerBall) {
                if (tempVelocity > 150) {
                    tempVelocity = 150;
                }
            } else {
                if (tempVelocity > 80) {
                    tempVelocity = 80;
                }
            }
            physics.setVelocity(tempVelocity);
            physics.setLaunchAngle(physics.findLaunchAngle(xVarGraph, yVarGraph, physics.distance(levels.get(currLevel).getStart().getX(), levels.get(currLevel).getStart().getY(), xVarGraph, yVarGraph)));
            xVarGraph = modXToDrawCoord(xVarGraph);
            yVarGraph = modYToDrawCoord(yVarGraph);
            offScreen.drawString("Velocity: " + roundTwoDecimals(physics.getVelocity() / 10) + "m/s, Angle: " + roundTwoDecimals(physics.getLaunchAngle()) + "\u00B0", xVarGraph, yVarGraph);
            offScreen.setColor(Color.red);
            offScreen.drawLine(modXToDrawCoord(levels.get(currLevel).getStart().getIntX()), modYToDrawCoord(levels.get(currLevel).getStart().getIntY()), xVarGraph, yVarGraph);
        }
        offScreen.setColor(Color.black);
        offScreen.drawString("Wind: " + roundTwoDecimals(levels.get(currLevel).getWind()), 10, 20);
        double vel = physics.getVelocity();
        offScreen.drawString("Velocity: " + roundTwoDecimals(vel / 10) + "m/s", 10, 35);
        if (vel >= 340 && !powerBallUnlocked) {
            physics = new PhysicsEngine(levels.get(currLevel).getStart().getIntX(), levels.get(currLevel).getStart().getIntY());
            powerBallUnlocked = true;
            restart = true;
            establishedVars = false;
            coords.clear();
            JOptionPane.showMessageDialog(this, "You broke the speed of sound and unlocked the powerball. Press \"P\" to activate.\n"
                    + "With the powerball, energy is not lost on impact and red walls are nothing but a eye-sore on the screen.\n"
                    + "Finally, maximum launch velocity is increased to 1.5m/s", "Easter Egg Unlocked!", 2);
        }
        offScreen.drawString(levels.get(currLevel).getMessage(), 10, 50);
        if (establishedVars) {
            physics.update(time);
            if (!restart) {
                coords.add(new Point(modXToDrawCoord(physics.lastLocation().getIntX()), modYToDrawCoord(physics.lastLocation().getIntY())));
                time += .01;
                for (int i = 0; i < walls.size(); i++) {
                    if (wallContact(walls.get(i))) {
                        i = walls.size();
                    }
                }
                for (int i = 0; i < permWalls.size(); i++) {
                    if (wallContact(permWalls.get(i))) {
                        i = permWalls.size();
                    }
                }
                for (int i = 0; i < levelWalls.size(); i++) {
                    if (wallContact(levelWalls.get(i))) {
                        i = levelWalls.size();
                    }
                }
                if (currGrav >= 0 && checkZeroG(gravs.get(currGrav), physics.lastLocation())) {
                    //System.out.println("derp");
                } else {
                    for (int i = 0; i < gravs.size(); i++) {
                        double velocity = physics.findOverallVelocity(time);
                        if (checkZeroG(gravs.get(i), physics.lastLocation()) && !inZeroG) {
                            //System.out.println("Entered");
                            physics = new PhysicsEngine(physics.lastLocation().getX(), physics.lastLocation().getY(), velocity, physics.currAngle(time), levels.get(currLevel).getWind());
                            physics.setGravity(gravs.get(i).getGravity());
                            time = 0;
                            inZeroG = true;
                            currGrav = i;
                            i = gravs.size();
                        } else if (i != currGrav && !checkZeroG(gravs.get(i), physics.lastLocation()) && inZeroG) {
                            //System.out.println("Exit");
                            physics = new PhysicsEngine(physics.lastLocation().getX(), physics.lastLocation().getY(), velocity, physics.currAngle(time), levels.get(currLevel).getWind());
                            time = 0;
                            inZeroG = false;
                            currGrav = -1;
                        }
                        //System.out.println("NOPE");
                    }
                }
                if (targetHit()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.err.println("There is no reason for this to break here. but I'll catch whatever happens anyway");
                    }
                    currLevel++;
                    restart = true;
                    inZeroG = false;
                    currGrav = -1;
                    establishedVars = false;
                    coords.clear();
                    try {
                        if (currLevel < levels.size()) {
                            physics = new PhysicsEngine(levels.get(currLevel).getStart().getIntX(), levels.get(currLevel).getStart().getIntY());
                            walls = levels.get(currLevel).getUserWalls();
                            gravs = levels.get(currLevel).getGravs();
                        } else {
                            //System.out.println("YOU WON!");
                            physics = null;
                            int winner = JOptionPane.showConfirmDialog(this, "A WINNER IS YOU", "YOU ARE A WINNER", 0);
                            //System.out.println(winner);
                            if (winner == 1) {
                                JOptionPane.showMessageDialog(this, ":(", "That makes me sad.", 1);
                            }
                            //JOptionPane.showMessageDialog(this, "A WINNER IS YOU", "YOU ARE A WINNER", 2);
                            System.exit(1);
                            return;
                        }
                    } catch (Exception e) {
                    }
                }
            } else {
                establishedVars = false;
                restart = false;
                inZeroG = false;
                currGrav = -1;
                coords.clear();
                physics = new PhysicsEngine(levels.get(currLevel).getStart().getIntX(), levels.get(currLevel).getStart().getIntY());
                //wind = r.nextDouble()*4-2
                //System.out.println(time);
            }
        }
        for (int i = 0; i < coords.size() - 1; i++) {
            if (coords.get(i).getX() != -1 && coords.get(i + 1).getX() != -1) {
                offScreen.setColor(Color.red);
                offScreen.drawLine(coords.get(i).getIntX(), coords.get(i).getIntY(), coords.get(i + 1).getIntX(), coords.get(i + 1).getIntY());
                //offScreen.setColor(Color.blue);
                //.drawOval(coords.get(i).getIntX(), coords.get(i).getIntY(), 1, 1);
            }
        }
        if (coords.size() > 200) {
            coords.remove(0);
        }
        g.drawImage(image, 0, 0, this);
    }

    public boolean wallContact(Wall wall) {
        int x1 = modXToMathCoord(wall.x1());
        int y1 = modYToMathCoord(wall.y1());
        int x2 = modXToMathCoord(wall.x2());
        int y2 = modYToMathCoord(wall.y2());
        if (physics.isWallContactV2(x1, y1, x2, y2, time)) {//testing
            double currGravity = physics.getGravity();
            //System.out.println(walls.get(i));
            //System.out.println(x1 + "," + y1 + "," + x2 + "," + y2);
            //System.out.println("Contact");
            //System.out.println(wait);
            //System.out.println(physics.getDeflection(x1, y1, x2, y2, time));
            double angle = physics.getDeflection(x1, y1, x2, y2, time);
            //System.out.println(x1 + "," + x2 + "," + y1 + "," + y2 + "," + time);
            //System.out.println(angle);
            double velocity = physics.findOverallVelocity(time);
            if (wall.getType() == 1) {
                //System.out.println(wait);
                if (wait <= 300) {
                    //velocity = velocity + 1;//testing
                    physics = new PhysicsEngine(physics.lastLocation().getX(), physics.lastLocation().getY(), velocity, angle + 0.01, levels.get(currLevel).getWind());
                    physics.setGravity(currGravity);
                    wait = 0;
                } else {
                    //System.out.println(wait);
                    if (usingPowerBall) {
                        physics = new PhysicsEngine(physics.lastLocation().getX(), physics.lastLocation().getY(), velocity, angle, levels.get(currLevel).getWind());
                    } else {
                        physics = new PhysicsEngine(physics.lastLocation().getX(), physics.lastLocation().getY(), velocity / 1.2, angle, levels.get(currLevel).getWind());
                    }
                    physics.setGravity(currGravity);
                    wait = 0;
                }
            } else if (wall.getType() == 2) {
                //System.out.println("ferp");
                if (usingPowerBall) {
                    physics = new PhysicsEngine(physics.lastLocation().getX(), physics.lastLocation().getY(), velocity * 1.1, angle, levels.get(currLevel).getWind());
                } else {
                    physics = new PhysicsEngine(physics.lastLocation().getX(), physics.lastLocation().getY(), velocity * 1.1, angle, levels.get(currLevel).getWind());
                }
                physics.setGravity(currGravity);
                wait = 0;
            } else if (wall.getType() == 3) {
                if (usingPowerBall) {
                    return false;
                } else {
                    physics = new PhysicsEngine(physics.lastLocation().getX(), physics.lastLocation().getY(), 0, -angle, 0);
                    physics.setGravity(0);
                }
                wait = 0;
            } else if (wall.getType() == 4) {
                wait++;
                return false;
            }
            time = 0;
            return true;
        }
        wait++;
        return false;
    }

    public boolean checkZeroG(GravTube grav, Point loc) {
        int x1 = modXToMathCoord(grav.getTL().getIntX());
        int x2 = modXToMathCoord(grav.getBR().getIntX());
        int y1 = modYToMathCoord(grav.getBR().getIntY());
        int y2 = modYToMathCoord(grav.getTL().getIntY());
        //System.out.println(x1+","+y1+","+x2+","+y2);
        //System.out.println(loc);
        if (loc.getX() < x2 && loc.getX() > x1 && loc.getY() < y1 && loc.getY() > y2) {
            return true;
        }
        return false;
    }

    public boolean targetHit() {
        if (distance(physics.lastLocation().getX(), physics.lastLocation().getY(), modXToMathCoord(levels.get(currLevel).getTarget().getIntX()), modYToMathCoord(levels.get(currLevel).getTarget().getIntY())) < 10) {
            return true;
        }
        return false;
    }

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void stop() {
        animation = null;
    }

    public double roundTwoDecimals(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return Double.POSITIVE_INFINITY;
        }
        try {
            DecimalFormat twoDForm = new DecimalFormat("#.##");
            return Double.valueOf(twoDForm.format(d));
        } catch (Exception e) {
            System.err.println("There is no reason for this to break here. but I'll catch whatever happens anyway");
            return d;
        }
    }

    public int modXToMathCoord(int x) {
        return x - ZERO_X;
    }

    public int modXToDrawCoord(int x) {
        return x + ZERO_X;
    }

    public int modYToMathCoord(int y) {
        return -y + ZERO_Y;
    }

    public int modYToDrawCoord(int y) {
        return ZERO_Y - y;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (setUpWalls) {
            //offScreen.drawRect(10,10,30,10);
            if (currLevel == 0) {
                if (e.getX() >= 10 && e.getX() <= 40 && e.getY() >= 10 && e.getY() <= 20) {
                    setUpWind = !setUpWind;
                }
            }
            if (currLevel == 0) {
                for (int i = 0; i < gravs.size(); i++) {
                    if (e.getX() > 200) {
                        if (checkZeroG(gravs.get(i), new Point(modXToMathCoord(e.getX()), modYToMathCoord(e.getY())))) {
                            enterG = i;
                            i = gravs.size();
                        }
                    }
                }
            }
            if (clicked) {
                if (moveWallX > -1) {
                    Wall temp = walls.remove(moveWallX);
                    temp.setStart(new Point(e.getX(), e.getY()));
                    walls.add(new Wall(temp.getStart(), temp.getEnd(), temp.getType()));
                }
                if (moveWallY > -1) {
                    Wall temp = walls.remove(moveWallY);
                    temp.setEnd(new Point(e.getX(), e.getY()));
                    walls.add(new Wall(temp.getStart(), temp.getEnd(), temp.getType()));
                }
                if (moveWallX < -1 && currLevel == 0) {
                    GravTube temp = gravs.get((moveWallX + 2) * -1);
                    temp.setTL(e.getX(), e.getY());
                    //walls.add(new Wall(temp.getStart(), temp.getEnd(), temp.getType()));
                }
                if (moveWallY < -1 && currLevel == 0) {
                    GravTube temp = gravs.get((moveWallY + 2) * -1);
                    temp.setBR(e.getX(), e.getY());
                    //walls.add(new Wall(temp.getStart(), temp.getEnd(), temp.getType()));
                }
                clicked = false;
                moveWallX = -1;
                moveWallY = -1;
            } else if (!clicked) {
                moveWallX = -1;
                moveWallY = -1;
                for (int i = 0; i < walls.size(); i++) {
                    //System.out.println(e.getX() + "," + e.getY());
                    if (physics.distance(walls.get(i).x1(), walls.get(i).y1(), e.getX(), e.getY()) < 4) {
                        moveWallX = i;
                        //System.out.println("ClickXWallPoint");
                        clicked = true;
                    }
                    if (physics.distance(walls.get(i).x2(), walls.get(i).y2(), e.getX(), e.getY()) < 4) {
                        moveWallY = i;
                        //System.out.println("ClickYWallPoint");
                        clicked = true;
                    }
                }
                for (int i = 0; i < gravs.size(); i++) {
                    //System.out.println(e.getX() + "," + e.getY());
                    if (physics.distance(gravs.get(i).getTL().getIntX(), gravs.get(i).getTL().getIntY(), e.getX(), e.getY()) < 4) {
                        moveWallX = (-1 * i) - 2;
                        //System.out.println("ClickXWallPoint");
                        clicked = true;
                    }
                    if (physics.distance(gravs.get(i).getBR().getIntX(), gravs.get(i).getBR().getIntY(), e.getX(), e.getY()) < 4) {
                        moveWallY = (-1 * i) - 2;
                        //System.out.println("ClickYWallPoint");
                        clicked = true;
                    }
                }
            }
        }
        if (!setUpWalls) {
            if (!establishedVars) {
                if (!lockLaunch) {
                    xVarGraph = e.getX();
                    yVarGraph = e.getY();
                }
                establishedVars = true;
                xVarGraph = modXToMathCoord(xVarGraph);
                yVarGraph = modYToMathCoord(yVarGraph);
                //System.out.println(xVarGraph + "," + yVarGraph);
                //xVarGraph=423;
                //yVarGraph=405;
                //physics = new PhysicsEngine(launchPosition.getIntX(), launchPosition.getIntY());
                double tempVelocity = physics.distance(levels.get(currLevel).getStart().getX(), levels.get(currLevel).getStart().getY(), xVarGraph, yVarGraph) / PIXEL_SCALER;
                if (usingPowerBall) {
                    if (tempVelocity > 150) {
                        tempVelocity = 150;
                    }
                } else {
                    if (tempVelocity > 80) {
                        tempVelocity = 80;
                    }
                }
                physics = new PhysicsEngine(levels.get(currLevel).getStart().getX(), levels.get(currLevel).getStart().getY(), tempVelocity,
                        physics.findLaunchAngle(xVarGraph, yVarGraph, physics.distance(levels.get(currLevel).getStart().getX(),
                        levels.get(currLevel).getStart().getY(), xVarGraph, yVarGraph)), levels.get(currLevel).getWind());
                if (gravs.size() > 0 && checkZeroG(gravs.get(0), physics.lastLocation())) {
                    inZeroG = true;
                    physics.setGravity(gravs.get(0).getGravity());
                }
                xVarGraph = modXToDrawCoord(xVarGraph);
                yVarGraph = modYToDrawCoord(yVarGraph);
                coords = new ArrayList();
                time = 0;
                //physics.setGravity(0);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!establishedVars && !lockLaunch) {
            xVarGraph = e.getX();
            yVarGraph = e.getY();
        }
        if (setUpWalls) {
            if (moveWallX < -1 && currLevel == 0) {
                GravTube temp = gravs.get((moveWallX + 2) * -1);
                temp.setTL(e.getX(), e.getY());
                //walls.add(new Wall(temp.getStart(), temp.getEnd(), temp.getType()));
            }
            if (moveWallY < -1 && currLevel == 0) {
                GravTube temp = gravs.get((moveWallY + 2) * -1);
                temp.setBR(e.getX(), e.getY());
                //walls.add(new Wall(temp.getStart(), temp.getEnd(), temp.getType()));
            }
            if (moveWallX > -1) {
                Wall temp = walls.remove(moveWallX);
                temp.setStart(new Point(e.getX(), e.getY()));
                walls.add(moveWallX, temp);
            }
            if (moveWallY > -1) {
                Wall temp = walls.remove(moveWallY);
                temp.setEnd(new Point(e.getX(), e.getY()));
                walls.add(moveWallY, temp);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyChar() + 0);
        if (e.getKeyChar() == '1'
                || e.getKeyChar() == '2'
                || e.getKeyChar() == '3'
                || e.getKeyChar() == '4'
                || e.getKeyChar() == '5'
                || e.getKeyChar() == '6'
                || e.getKeyChar() == '7'
                || e.getKeyChar() == '8'
                || e.getKeyChar() == '9') {
            if (enterG != -1) {
                if (e.isAltDown()) {
                    gravs.get(enterG).setGravity(gravs.get(enterG).getGravity() - 0.1 * ((new Integer(e.getKeyChar() + ""))));
                } else {
                    gravs.get(enterG).setGravity(gravs.get(enterG).getGravity() + 0.1 * ((new Integer(e.getKeyChar() + ""))));
                }
            }
            if (setUpWind) {
                if (e.isAltDown()) {
                    levels.get(currLevel).setWind(levels.get(currLevel).getWind() - 0.1 * ((new Integer(e.getKeyChar() + ""))));
                } else {
                    levels.get(currLevel).setWind(levels.get(currLevel).getWind() + 0.1 * ((new Integer(e.getKeyChar() + ""))));
                }
            }
        }
        if (e.getKeyChar() == 'r') {
            restart = true;
        }
        if (e.getKeyChar() == 'w') {
            if (currLevel != 0) {
                setUpWalls = !setUpWalls;
                restart = true;
            }
        }
        if (e.getKeyChar() == 'p' && powerBallUnlocked) {
            usingPowerBall = !usingPowerBall;
        }
        if (e.getKeyChar() == 'l') {
            lockLaunch = !lockLaunch;
        }
        if (e.getKeyChar() == 'c' && currLevel == 0) {
            levels.get(currLevel).getUserWallsCode();
        }
        if (e.getKeyChar() == 'b') {
            if (currLevel != 0) {
                currLevel = 0;
                setUpWalls = true;
            } else {
                currLevel = 1;
                setUpWalls = false;
            }
            restart = true;
            walls = levels.get(currLevel).getUserWalls();
            gravs = levels.get(currLevel).getGravs();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
