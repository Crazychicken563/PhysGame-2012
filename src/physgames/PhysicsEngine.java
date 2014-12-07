package physgames;

import java.util.ArrayList;

public final class PhysicsEngine {

    private Point startPoint;
    private double launchAngle;
    private double velocity;
    private double xVelocity;
    private double yVelocity;
    private boolean xNegative;
    private boolean yNegative;
    private double wind;
    private double gravity = -9.8;
    private ArrayList<Point> flightPath;

    public PhysicsEngine() {
        startPoint = new Point(0, 0);
        launchAngle = 0;
        velocity = 0;
        xVelocity = 0;
        yVelocity = 0;
        wind = 0;
        xNegative = false;
        yNegative = false;
        flightPath = new ArrayList();
        flightPath.add(startPoint);
    }

    public PhysicsEngine(int x, int y) {
        startPoint = new Point(x, y);
        launchAngle = 0;
        velocity = 0;
        xVelocity = 0;
        yVelocity = 0;
        wind = 0;
        xNegative = false;
        yNegative = false;
        flightPath = new ArrayList();
        flightPath.add(startPoint);
    }

    public PhysicsEngine(double x, double y, double velocityIn, double launchAngleIn, double windIn) {
        startPoint = new Point(x, y);
        launchAngle = launchAngleIn;
        velocity = velocityIn;
        xVelocity = findStartXVelocity(velocity, launchAngle);
        yVelocity = findStartYVelocity(velocity, launchAngle);
        wind = windIn;
        if (launchAngle < -90 || launchAngle > 90) {
            xNegative = true;
        } else {
            xNegative = false;
        }
        if (launchAngle < 0) {
            yNegative = true;
        } else {
            yNegative = false;
        }
        flightPath = new ArrayList();
        flightPath.add(startPoint);
    }

    public void setGravity(double g) {
        gravity = g;
    }

    public double getGravity() {
        return gravity;
    }

    public void setXVelocity(double vel) {
        xVelocity = vel;
    }

    public void setYVelocity(double vel) {
        yVelocity = vel;
    }

    public void update(double time) {
        Point temp = new Point();
        temp.setX(findPosition(startPoint.getX(), xVelocity, wind, time));
        temp.setY(findPosition(startPoint.getY(), yVelocity, gravity, time));
        flightPath.add(temp);
        if (temp.getY() < flightPath.get(flightPath.size() - 2).getY()) {
            yNegative = true;
        } else {
            yNegative = false;
        }
        if (temp.getX() < flightPath.get(flightPath.size() - 2).getX()) {
            xNegative = true;
        } else {
            xNegative = false;
        }
    }

    public double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public double findLaunchAngle(double x1, double y1, double velocity) {
        double x = x1 - startPoint.getX();
        double y = y1 - startPoint.getY();
        if (velocity != 0) {
            if (y < 0) {
                return -Math.toDegrees(Math.acos((x) / (velocity)));
            } else {
                return Math.toDegrees(Math.acos((x) / (velocity)));
            }
        }
        return 0;
    }

    private double findAngle(double x, double y, double velocity) {
        if (velocity != 0) {
            if (y < 0) {
                return -Math.toDegrees(Math.acos((x) / (velocity)));
            } else {
                return Math.toDegrees(Math.acos((x) / (velocity)));
            }
        }
        return 0;
    }

    public double findStartXVelocity(double velocity, double launchAngle) {
        return (velocity * Math.cos(Math.toRadians(launchAngle)));
    }

    public double findStartYVelocity(double velocity, double launchAngle) {
        return (velocity * Math.sin(Math.toRadians(launchAngle)));
    }

    public double findXVelocity(double time) {
        return xVelocity + wind * time;
    }

    public double findYVelocity(double time) {
        return yVelocity + gravity * time;
    }

    public double findPosition(double pos, double velocity, double acceleration, double time) {
        return (pos + velocity * time + 0.5 * acceleration * Math.pow(time, 2));
    }

    public double findOverallVelocity(double time) {
        double xV = findXVelocity(time);
        double yV = findYVelocity(time);
        //System.out.println("xV: "+xV+" yV: "+yV);
        return Math.sqrt(xV * xV + yV * yV);
    }

    public double getDeflection(int x1, int y1, int x2, int y2, double time) {
        double wallHeight = y2 - y1;
        double wallWidth = x2 - x1;
        if (wallWidth == 0) {
            wallWidth = 0.000000000001;
        }
        double beta = Math.toDegrees(Math.atan(wallHeight / wallWidth));
        double xV = findXVelocity(time);
        if (xV == 0) {
            xV = 0.000000000001;
        }
        double yV = findYVelocity(time);
        double alpha = Math.toDegrees(Math.atan(yV / xV));
        alpha = Math.abs(alpha);
        double angle;
        if (!yNegative) {
            if (xNegative && alpha > 0) {
                beta = -beta;
            }
            if (!xNegative) {
                beta = -beta;
            }
        }
        if (xNegative) {
            angle = 180 + 2 * beta - alpha;
        } else {
            angle = 2 * beta + alpha;
        }
        if (Math.abs(angle) > 180) {
            angle = 180 - Math.abs(angle) % 180;
            angle = angle * -1;
        }
        if (!yNegative) {
            angle = -angle;
        }
        return angle;
    }

    public double getDeflectionV2(int x1, int y1, int x2, int y2, double time) {
        double wallHeight = y2 - y1;
        double wallWidth = x2 - x1;
        if (wallWidth == 0) {
            wallWidth = 0.000000000001;
        }
        double beta = Math.toDegrees(Math.atan(wallHeight / wallWidth));
        double xV = findXVelocity(time);
        if (xV == 0) {
            xV = 0.000000000001;
        }
        double yV = findYVelocity(time);
        double vel = findOverallVelocity(time);
        double alpha = -90;
        if (vel != 0) {
            if (yV < 0) {
                alpha = -Math.toDegrees(Math.acos((xV) / (vel)));
            } else {
                alpha = Math.toDegrees(Math.acos((xV) / (vel)));
            }
        }
        //alpha = Math.abs(alpha);
        //beta = Math.abs(beta);
        System.out.println(beta);
        //System.out.println(alpha);
        //System.out.println(lastLocation());
        double angle = 2 * (beta - Math.abs(alpha)) - 90;
        if (Math.abs(angle) > 180) {
            //System.out.println("Real Angle"+angle);
            angle = 180 - Math.abs(angle) % 180;
            return angle * -1;
        }
        //System.out.println(angle);
        return angle;
    }

    public boolean isWallContactV2(int x1, int y1, int x2, int y2, double time) {
        double wallSlope = ((y2 - y1) * 1.0) / ((x2 - x1) * 1.0);
        //System.out.println(wallSlope);
        if (wallSlope > Integer.MAX_VALUE) {
            //wallSlope = Integer.MAX_VALUE;
            wallSlope = Integer.MAX_VALUE;
        } else if (wallSlope < Integer.MIN_VALUE) {
            //wallSlope = Integer.MIN_VALUE;
            wallSlope = Integer.MIN_VALUE;
        }
        //System.out.println(wallSlope);
        //double tooAdd = (1-(wallSlope));
        double wallYInt = y1 - wallSlope * x1;
        int yOne = y1;
        int yTwo = y2;
        if (y1 > y2) {
            yTwo = y1;
            yOne = y2;
        }
        boolean inRange = false;
        if (wallSlope < -1 || wallSlope > 1) {
            if (lastLocation().getY() >= yOne && lastLocation().getY() <= yTwo) {
                inRange = true;
            }
        } else {
            if (lastLocation().getX() >= x1 && lastLocation().getX() <= x2) {
                inRange = true;
            }
        }
        if (inRange) {
            double ObjX1 = lastLocation().getX();
            double ObjY1 = lastLocation().getY();
            double ObjX2 = findPosition(startPoint.getX(), xVelocity, wind, time);
            double ObjY2 = findPosition(startPoint.getY(), yVelocity, gravity, time);
            //System.out.println(ObjX1+","+ObjY1+","+ObjX2+","+ObjY2);
            boolean verticalLine = false;
            if (ObjX2 - ObjX1 == 0) {
                verticalLine = true;
            }
            double ObjSlope = (ObjY2 - ObjY1) * 1.0 / (ObjX2 - ObjX1) * 1.0;
            if (ObjSlope > Integer.MAX_VALUE) {
                ObjSlope = Integer.MAX_VALUE;
            }
            if (ObjSlope < Integer.MIN_VALUE) {
                ObjSlope = Integer.MIN_VALUE;
            }
            double ObjYInt = ObjY1 - ObjSlope * ObjX1;
            //boolean verticalLine = false;
            //System.out.println(wallSlope - ObjSlope);
            //System.out.println(wallSlope);
            //System.out.println(ObjSlope);
            double xInt = (ObjYInt - wallYInt) / (wallSlope - ObjSlope);
            double yInt = (wallSlope * xInt + wallYInt);
            //System.out.println(xInt + "," + yInt);
            double left;
            double right;
            //System.out.println("y = " + wallSlope + " * x + " + wallYInt);
            //System.out.println("y = " + ObjSlope + " * x + " + ObjYInt);
            if (ObjX1 > ObjX2) {
                left = ObjX2;
                right = ObjX1;
            } else {
                left = ObjX1;
                right = ObjX2;
            }
            if (verticalLine) {
                //System.out.println("here");
                left = left - .000001;
                right = right + .000001;
            }
            //System.out.println(left + "<" + xInt + "<" + right);
            if (left <= xInt && xInt <= right) {
                //System.out.println(left + "<" + xInt + "<" + right);
                if (ObjY1 > ObjY2) {
                    left = ObjY2;
                    right = ObjY1;
                } else {
                    left = ObjY1;
                    right = ObjY2;
                }
                //System.out.println(left + "<" + yInt + "<" + right);
                //System.out.println("y = " + wallSlope + " * x + " + wallYInt);
                //System.out.println("y = " + ObjSlope + " * x + " + ObjYInt);
                //System.out.println(ObjX1+","+ObjY1+","+ObjX2+","+ObjY2);
                if (left <= yInt && yInt <= right) {
                    //System.out.println(left+"<"+xInt+"<"+right);
                    //System.out.println(left + "<" + yInt + "<" + right);
                    //System.out.println("y = " + wallSlope + " * x + " + wallYInt);
                    //System.out.println("y = " + ObjSlope + " * x + " + ObjYInt);
                    //System.out.println(velocity);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Point> getFlightPath() {
        return flightPath;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getLaunchAngle() {
        return launchAngle;
    }

    public Point lastLocation() {
        return flightPath.get(flightPath.size() - 1);
    }

    public void setVelocity(double in) {
        velocity = in;
    }

    public void setLaunchAngle(double in) {
        launchAngle = in;
        xVelocity = findStartXVelocity(velocity, launchAngle);
        yVelocity = findStartYVelocity(velocity, launchAngle);
    }

    public double currAngle(double time) {
        double xV = findXVelocity(time);
        double yV = findYVelocity(time);
        //System.out.println(xV);
        //System.out.println(yV);
        return findAngle(xV, yV, findOverallVelocity(time));
    }

    public void setStartPoint(Point in) {
        startPoint = new Point(in.getX(), in.getY());
        flightPath.add(startPoint);
    }
}
