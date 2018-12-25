/*
 * TCSS 305 - Assignment 6
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


/**
 * Creates score panel to display players score.
 * 
 * @author gehry guest
 * @version 2.0
 */
public class GUIScorePanel extends JPanel implements PropertyChangeListener {

    /** A number for serialization. */
    private static final long serialVersionUID = 3853865786110681353L;

    /** Create a dimension for the score panel window size. */
    private static final Dimension DEFAULT_SIZE = new Dimension(180, 65);
    
    /** Starting delay for timers. */
    private static final int STARTING_TIME = 1000;
    
    /** Five is used for when to reset the nextLevelCounter,
     *  and initialize nextLevelCounter, also when a level up occurs.*/
    private static final int FIVE = 5;
    
    /** Label for current score. */
    private JLabel myScore;

    /** Label for current lines cleared. */
    private JLabel myLinesCleared;

    /** Label for current level. */
    private JLabel myLevel;
    
    /** Label for lines cleared until next level. */
    private JLabel myNextLevelIn;

    /** Timer to increment score every second. */
    private Timer myScoreTimer;
    
    /** Timer from GUIFrame to decrement. */
    private Timer myBoardTimer;
    
    /** Counter for current score. */
    private int myScoreCount;
    
    /** Counter for current level. */
    private int myLevelCount;
    
    /** Counter for current lines cleared. */
    private int myLinesClearedCount;

    /** Counter for lines needed until next level. */
    private int myNextLevelCounter;
    
    /** How fast the animation will play for the game board.. */
    private int myBoardTimerSpeedUp;
    
    /** Formats the current score string. */
    private final String myCurrentScore = "     Current Score: ";

    /** Formats the current level string. */
    private final String myCurrentLevel = "     Current Level: ";
    
    /** Formats the next level String. */
    private final String myNextLevel = "     Next level in: ";
    
    /** Formats the current lines cleared String. */
    private final String myCurrentLinesCleared = "     Lines Cleared: ";

    
    /**
     * Calls method to set up score panel and initialize values.
     * 
     * @param theTimer timer from GUIFrame.
     */
    public GUIScorePanel(final Timer theTimer) {
        
        myScoreCount = 0;
        
        myLinesClearedCount = 0;
        
        myLevelCount = 1;
        
        myBoardTimerSpeedUp = STARTING_TIME;
        
        myNextLevelCounter = FIVE;
        
        myBoardTimer = theTimer;

        myScoreTimer = new Timer(STARTING_TIME, new ScoreTimerListener());

        setUp();
    }
    

    /**
     * Sets up the appearance of the score panel.
     */
    public void setUp() {
        
        final int rows = 4;
        
        final int columns = 1;
        
        myScore = new JLabel(myCurrentScore + myScoreCount);
        
        myLevel = new JLabel(myCurrentLevel + myLevelCount);
        
        myLinesCleared = new JLabel(myCurrentLinesCleared + myLinesClearedCount);
        
        myNextLevelIn = new JLabel(myNextLevel + myNextLevelCounter);

        final Color darkPurple = new Color(75, 0, 130);

        final TitledBorder title = new TitledBorder(new LineBorder(Color.MAGENTA, 2), "Score",
                                                    TitledBorder.CENTER,
                                                    TitledBorder.ABOVE_TOP, null, Color.CYAN);

        this.setBorder(title);
        
        this.setLayout(new GridLayout(rows, columns));
        
        myScore.setForeground(Color.CYAN);
        
        myLevel.setForeground(Color.CYAN);
        
        myLinesCleared.setForeground(Color.CYAN);
        
        myNextLevelIn.setForeground(Color.CYAN);
        
        this.add(myScore);
        
        this.add(myLinesCleared);
        
        this.add(myNextLevelIn);
        
        this.add(myLevel);

        this.setPreferredSize(DEFAULT_SIZE);

        this.setBackground(darkPurple);

        this.setVisible(true);
    }
    
    
    /** To stop score timer. */
    public void stopTimer() {
        myScoreTimer.stop();
    }
    
    
    /** To start score timer. */
    public void startTimer() {
        myScoreTimer.start();
    }
    
    
    /** To reset all values in this class. */
    public void resetValues() {
        
        myScoreTimer.restart();
        
        myScoreCount = 0;
        
        myLinesClearedCount = 0;
        
        myLevelCount = 1;
        
        myNextLevelCounter = FIVE;
        
        myBoardTimerSpeedUp = STARTING_TIME;
        
        myScore.setText(myCurrentScore + myScoreCount);

        myLevel.setText(myCurrentLevel + myLevelCount);
        
        myLinesCleared.setText(myCurrentLinesCleared + myLinesClearedCount);
        
        myNextLevelIn.setText(myNextLevel + myNextLevelCounter);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        final int decrementVal = 100;
        
        if ("lines cleared".equals(theEvent.getPropertyName())) {
            
            final Integer[] lineCleared = (Integer[]) theEvent.getNewValue(); 

            for (int i = 0; i < lineCleared.length; i++) {
                
                myLinesClearedCount += 1;
                
                myNextLevelCounter -= 1;
                
                if (myLinesClearedCount % FIVE == 0) { 
                    
                    myLevelCount += 1;
                    
                    myNextLevelCounter = FIVE;
                    
                    if (myBoardTimerSpeedUp > decrementVal) {
                        
                        myBoardTimerSpeedUp -= decrementVal;
                        
                        myBoardTimer.setDelay(myBoardTimerSpeedUp);
                    
                    }
                    
                    myLevel.setText(myCurrentLevel + myLevelCount);
                }
            }
            myLinesCleared.setText(myCurrentLinesCleared + myLinesClearedCount);
            
            myNextLevelIn.setText(myNextLevel + myNextLevelCounter);
        } 
    };
    
    
    /**
     * A inner class that listens for timer events and loads the loading bar.
     * 
     */
    private class ScoreTimerListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvt) {
            final int increaseScore = 10;
            
            myScore.setText(myCurrentScore + myScoreCount);

            myScoreCount += increaseScore;
        }
    }
}
