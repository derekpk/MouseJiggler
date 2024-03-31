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

    private static final int EXIT_AREA_XY = 100;
    private static final int ONE_SECOND = 1000;
    private static final int ONE_HUNDRED = 100;
    private static final int SIX_PM = 18;
    private static final int FIFTY = 50;
    private static final int FIVE = 5;
    private static final int ONE = 1;
    private static final int TWO = 2;

    private static final int TEN = 10;
    private static final int MINUS_FIFTY = -50;
    private static final int ZERO = 0;
    private static final String YOUR_MOUSE_JIGGLER_IS_STOPPING_THANK_YOU = "Your program is stopping\n\n            Thank you";
    private static final String YOUR_MOUSE_JIGGLER_SAY_ITS_AFTER_SIX = "It's after 6pm, your program is stopping\n\n            Thank you";

    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            boolean moveDown = true;
            boolean moveWheelUp = true;
            boolean moveWheel = true;
            Point startPoint = MouseInfo.getPointerInfo().getLocation();
            Point interimPoint = MouseInfo.getPointerInfo().getLocation();

            while (true) {
                moveWheel = (activateThreadSleep(ONE_SECOND, ONE_SECOND * TEN) % TWO == ZERO) ? true : false;

                Point nextPoint = MouseInfo.getPointerInfo().getLocation();
                if (interimPoint.x == nextPoint.x && interimPoint.y == nextPoint.y) {
                    if(moveWheel && nextPoint.x > ONE_HUNDRED) {
                        moveWheelUp = moveWheel(robot, moveWheelUp, ONE, FIVE);
                    }
                    moveRandom(robot);
                }
                interimPoint = MouseInfo.getPointerInfo().getLocation();
                
                doWeShutown(startPoint, interimPoint, robot);
            }
        } catch (AWTException ex) {
            // I'm not to worried about exceptions
            ex.printStackTrace();
        }
    }
    
    private static boolean moveWheel(final Robot robot,
                                     boolean moveWheelUp,
                                     final int lowerLimit,
                                     final int upperLimit) {
        int distance = (int) ((Math.random() * (upperLimit - lowerLimit)) + lowerLimit);
        
        if (moveWheelUp) {
            robot.mouseWheel(distance);
        } else {
            robot.mouseWheel(-distance);
        }

        return !moveWheelUp;
    }

    private static void doWeShutown(final Point startPoint,
                                    final Point interimPoint,
                                    final Robot robot) {
        if(interimPoint.getX() <= EXIT_AREA_XY && interimPoint.getY() <= EXIT_AREA_XY) {
            shutDown(startPoint, robot, YOUR_MOUSE_JIGGLER_IS_STOPPING_THANK_YOU,true);
        }
        shutDownIfAfter(SIX_PM, startPoint, robot);
    }
    
    private static void shutDownIfAfter(int hourOfDay,
                                        final Point startPoint,
                                        final Robot robot) {
        DateTime date = new DateTime();
        if(date.getHourOfDay() >= hourOfDay) {
            shutDown(startPoint, robot, YOUR_MOUSE_JIGGLER_SAY_ITS_AFTER_SIX,true);
        }
    }
    private static void shutDown(final Point startPoint,
                                 final Robot robot,
                                 final String message,
                                 final boolean showMessageBox) {
        if(showMessageBox) {
            showMessageDialog(null, message);
        }
        robot.mouseMove(startPoint.x, startPoint.y);
        System.exit(ZERO);
    }
    
    private static int activateThreadSleep(int lowerLimit, int upperLimit) {
        int sleep = (int) ((Math.random() * (upperLimit - lowerLimit)) + lowerLimit);
        try {
            Thread.sleep(sleep);
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        }
        return sleep;
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

}