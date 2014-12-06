/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physgames;

/**
 *
 * @author Seva
 */
public class MathTest {
    public static void main(String[] args)
    {
        System.out.println("Angle " + getDeflectionV2(0,0,0,0));
    }
    public static double getDeflectionV2(int x1, int y1, int x2, int y2) {
        double wallHeight = y2 - y1;
        double wallWidth = x2 - x1;
        double beta = Math.toDegrees(Math.atan(wallHeight / wallWidth));
        //User set for testing purposes
        double xV = -111;
        double yV = 0.001;
        double alpha = Math.toDegrees(Math.atan(yV / xV));
        System.out.println(beta);
        System.out.println(alpha);
        double angle = 0;
        return angle;
    }
}
