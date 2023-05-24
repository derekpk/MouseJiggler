/**
 * copyright 2023, Derek Keogh
 */
package ie.decoder.mouse.jiggler;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * This class gets the width and heights of your screens
 * 
 * @author Derek
 *
 */
public class MultiScreenDimension {

    private static MultiScreenDimension classInstance = null;
    private static final int RANDOM_MIN = 101;
    
    private int combinedScreenWidth = 0;
    private int height = 0;
    private String screenDetails;
    
    private MultiScreenDimension () {
        setCombinedMonitorWidthAndHeight();
    }
    
    public static MultiScreenDimension getInstance() {
        if (classInstance == null) {
            classInstance = new MultiScreenDimension();
        }
        
        return classInstance;
    }
    
    private void setCombinedMonitorWidthAndHeight() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        this.height = ge.getDefaultScreenDevice().getDisplayMode().getHeight();
        
        GraphicsDevice[] gdArray = ge.getScreenDevices();
        StringBuilder sb = new StringBuilder();
        sb.append("Your system has " + gdArray.length + " screens\n");
        for(GraphicsDevice gd : gdArray) {
            DisplayMode dm = gd.getDisplayMode();
            sb.append("Width : " + dm.getWidth() + " ,Height : " + dm.getHeight() + "\n");
            this.combinedScreenWidth += dm.getWidth();
        }
        screenDetails = sb.toString();
        //System.out.println(screenDetails);
    }
    
    public int getRandomHeight() {
        return getRandomNumber(RANDOM_MIN, this.height);
    }
    
    public int getRandomWidth() {
        return getRandomNumber(RANDOM_MIN, this.combinedScreenWidth);
    }

    private int getRandomNumber(final int min,
                                final int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
