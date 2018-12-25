/* 
 * TCSS 305 - Assignment 6
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Class to start splash screen then start up the game.
 * 
 * @author gehry guest
 * @version 2.0
 */
public class SplashScreen extends JWindow {
    
    /** A number for Serialization. */
    private static final long serialVersionUID = -2102839873375270630L;
                    
    /** Constant time for each update. */
    private static final int TIMER_PAUSE = 100; 

    /** Max amount the progress bar can reach. */
    private static final int PROGBAR_MAX = 100;
    
    /** The font size of the title in the splash screen. */
    private static final float SPLASH_TITLE_FONT_SIZE = 45f;
    
    /** The font size of the title in the splash screen. */
    private static final float SPLASH_LOADING_FONT_SIZE = 30f;
                    
    /** Creates progress bar a rectangle that continually fills up. */
    private final JProgressBar myProgressBar;
    
    /** count to increment bar and check progress of bar. */
    private int myCount = 1;
        
    /** Timer for bar to update to. */
    private Timer myProgressBarTimer;
    
    /** name of custom font. */
    private String myFontName = "Resources/sujet___.ttf";
    
    /** Value for holding custom font. */
    private Font myCustomFont;
    
    /**
     * Initializes fields with reasonable values.
     * 
     */
    public SplashScreen() {
        
        myProgressBar = new JProgressBar();
        
        myProgressBarTimer = new Timer(TIMER_PAUSE, new TimeListener());
        
        myProgressBarTimer.start();
        
        try {
            myCustomFont = Font.createFont(Font.TRUETYPE_FONT 
                                         , new File(myFontName)).deriveFont(Font.BOLD,
                                                                SPLASH_TITLE_FONT_SIZE);
            
            final GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
            
            g.registerFont(Font.createFont(Font.TRUETYPE_FONT , new File(myFontName)));
            
        } catch (final IOException | FontFormatException exception) {   
        }
        
        setUp();

    }
    
    /**
     * sets up containers, JPanels, and progress bar with layouts and colors.
     * 
     */
    private void setUp() {
        
        final Container container = getContentPane();

        final JPanel panel = new JPanel();
        
        final JLabel imgLabel = new JLabel("",
                                        new ImageIcon("./resources/Skeleton_floppy.gif"), 
                                        SwingConstants.CENTER);

        final JLabel text = new JLabel("-TETRIS-", SwingConstants.CENTER);

        text.setFont(myCustomFont);
        
        text.setForeground(Color.RED);
        
        container.add(panel, BorderLayout.CENTER);
        
        container.add(text, BorderLayout.NORTH);
        
        container.setBackground(Color.BLACK);
        
        panel.add(imgLabel, BorderLayout.CENTER);
        
        panel.setBackground(Color.BLACK);

        myProgressBar.setMaximum(PROGBAR_MAX);
        
        myProgressBar.setStringPainted(true);
        
        myProgressBar.setForeground(Color.RED);
        
        myProgressBar.setBackground(Color.BLACK);
        
        myProgressBar.setBorderPainted(false);
        
        container.add(myProgressBar, BorderLayout.SOUTH);
  
        this.pack();
        
        this.setLocationRelativeTo(null);
        
        this.setVisible(true);

    }

    /**
     * Starts the Tetris game.
     */
    private void startGame() {
        
        new GUIFrame().start();
    }
    
    /**
    * A inner class that listens for timer events and loads the loading bar.
    * 
    */
    private class TimeListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvt) {
            myProgressBar.setValue(myCount);
            
            final String x = Integer.toString(myCount);
            
            myProgressBar.setFont(myCustomFont.deriveFont(SPLASH_LOADING_FONT_SIZE));
            
            myProgressBar.setString("Loading " + x + "%");
            
            if (PROGBAR_MAX == myCount) {
                
                myProgressBarTimer.stop();
                
                SplashScreen.this.setVisible(false);
                
                startGame();
            }
            myCount++;
        }
    };
}
