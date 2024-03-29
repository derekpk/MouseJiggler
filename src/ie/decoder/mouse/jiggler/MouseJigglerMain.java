/**
 * copyright 2023, Derek Keogh
 */
package ie.decoder.mouse.jiggler;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import static javax.swing.JOptionPane.showMessageDialog;
import org.joda.time.DateTime;

/**
 * Main class for the Mouse jiggler
 * 
 * @author Derek
 *
 */
public class MouseJigglerMain {

    private static final int ONE_SECOND = 1000;
    private static final int TEN = 10;
    private static final int FIFTY = 50;
    private static final int MINUS_FIFTY = -50;
    private static final int ZERO = 0;
    private static final String YOUR_MOUSE_JIGGLER_IS_STOPPING_THANK_YOU = "Your program is stopping\n\n            Thank you";
    private static final int EXIT_BOX_XY = 100;

    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            boolean moveDown = true;
            Point startPoint = MouseInfo.getPointerInfo().getLocation();
            Point interimPoint = MouseInfo.getPointerInfo().getLocation();

            Point nextPoint;
            while (true) {
                activateThreadSleep(ONE_SECOND, ONE_SECOND * TEN);

                nextPoint = MouseInfo.getPointerInfo().getLocation();
                if (interimPoint.x == nextPoint.x && interimPoint.y == nextPoint.y) {
                    moveRandom(robot);
                    //moveDown = moveDown(robot, nextPoint, moveDown);
                }
                interimPoint = MouseInfo.getPointerInfo().getLocation();
                if (interimPoint.x <= EXIT_BOX_XY && interimPoint.y <= EXIT_BOX_XY) {
                    showMessageDialog(null, YOUR_MOUSE_JIGGLER_IS_STOPPING_THANK_YOU);
                    robot.mouseMove(startPoint.x, startPoint.y);
                    System.exit(ZERO);
                }
            }
        } catch (AWTException ex) {
            // I'm not to worried about exceptions
            ex.printStackTrace();
        }
    }

    private static void shutDownIfAfterSixAtNight() {
        DateTime date = new DateTime();
        if(date.getHourOfDay() >= 18) {
            System.exit(ZERO);
        }
    }
    
    private static void activateThreadSleep(int lowerLimit, int upperLimit) {
        shutDownIfAfterSixAtNight();
        try {
            Thread.sleep((int) ((Math.random() * (upperLimit - lowerLimit)) + lowerLimit));
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * This will move the pointer anywhere on the screen with the exception of
     * a small area in the top left that is used to determine if the app exits
     * 
     * @param robot
     */
    private static void moveRandom(final Robot robot) {
        robot.mouseMove(MultiScreenDimension.getInstance().getRandomWidth(),
                        MultiScreenDimension.getInstance().getRandomHeight());
    }
    /**
     * Call this if you only want to mouve the pointer plus of minus 50 pixels.
     * 
     * @param robot
     * @param point
     * @param moveDown
     * @return
     */
    private static boolean moveDown(final Robot robot,
                                    final Point point,
                                    boolean moveDown) {
        int distance = moveDown ? MINUS_FIFTY : FIFTY;
        robot.mouseMove(point.x + distance, point.y + distance);
        return moveDown ? false : true;
    }
}