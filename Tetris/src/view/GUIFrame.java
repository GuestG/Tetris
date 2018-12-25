/*
 * TCSS 305 - Assignment 6
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import model.Board;

/**
 * Sets up the Frame of the GUI.
 * 
 * @author gehry guest
 * @version 2.0
 */
public class GUIFrame extends KeyAdapter implements PropertyChangeListener {

    /** Time between frames updating. */
    private static final int ANITMATION_TIME = 1000;

    /** Padding between GUI components. */
    private static final int BORDER_PADDING = 10;
    
    /** Color for Frame of GUI. */
    private final Color myMainPanelsColors = new Color(47, 0, 76);

    /** Color for setting the background of the frame. */
    private final Color myDarkPurple = new Color(75, 0, 130);

    /** Frame for all GUI elements. */
    private JFrame myFrame;

    /** Panel for game to be played on. */
    private GUIGamePanel myGamePanel;

    /** Panel for next piece. */
    private GUINextPiecePanel myNextPiecePanel;

    /** Panel for scores. */
    private GUIScorePanel myScorePanel;

    /** Sets up JMenuBar for the frame. */
    private final GUIMenuBar myMenuBar;

    /** Calls board to use methods from. */
    private Board myBoard;

    /** Creates timer for animations. */
    private Timer myTimer;

    /** Toggle for when the game is paused. */
    private boolean myPauseToggle;

    /** Key listener for pause button. */
    private PauseListener myPausekeyListener;

    
    /**
     * Constructor to initialize values for GUI's components.
     * 
     */
    public GUIFrame() {

        myTimer = new Timer(ANITMATION_TIME, new TimeListener());

        myMenuBar = new GUIMenuBar();

        myBoard = new Board();

        myFrame = new JFrame();

        myGamePanel = new GUIGamePanel();
        
        myPausekeyListener = new PauseListener();

        myNextPiecePanel = new GUINextPiecePanel();

        myScorePanel = new GUIScorePanel(myTimer);

        myPauseToggle = false;
    }

    
    /**
     * add components to GUI and starts timer.
     * 
     */
    public void start() {

        setUpEast();

        setUpCenter();

        defaultFrame();
    }
    

    /**
     * Sets up the frame of the GUI and gives it defaults to set up.
     * 
     */
    private void defaultFrame() {

        final Dimension defDimension = new Dimension(515, 610);

        myMenuBar.addPropertyChangeListener(this);

        myScorePanel.addPropertyChangeListener(this);

        myBoard.addPropertyChangeListener(myGamePanel);

        myBoard.addPropertyChangeListener(myNextPiecePanel);

        myBoard.addPropertyChangeListener(myScorePanel);

        myBoard.addPropertyChangeListener(this);

        myFrame.setSize(defDimension);

        myFrame.setMinimumSize(defDimension);

        myFrame.add(setUpCenter(), BorderLayout.CENTER);

        myFrame.add(setUpEast(), BorderLayout.EAST);

        myFrame.setJMenuBar(myMenuBar.createMenuBar());

        myFrame.setTitle("Tetris");

        myFrame.setBackground(myMainPanelsColors);

        myFrame.setLocationRelativeTo(null);

        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myFrame.setResizable(false);

        myFrame.setVisible(true);
    }
    

    /**
     * Creates east panel for score board and piece preview.
     * 
     * @return JPanel for JFrame
     */
    private JPanel setUpEast() {

        final JPanel eastPanel = new JPanel();

        final BoxLayout bLayout = new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS);

        eastPanel.setLayout(bLayout);

        eastPanel.setBackground(myMainPanelsColors);

        eastPanel.add(myNextPiecePanel);

        eastPanel.setBorder(new EmptyBorder(BORDER_PADDING, BORDER_PADDING, BORDER_PADDING,
                                            BORDER_PADDING));

        eastPanel.add(Box.createVerticalStrut(BORDER_PADDING));

        eastPanel.add(myScorePanel);

        return eastPanel;
    }
    

    /**
     * the panel in which the game is played on.
     * 
     * @return JPanel sets up default operations with this panel.
     */
    private JPanel setUpCenter() {

        final int smallerPadding = 3;

        final JPanel centerPanel = new JPanel();

        final BoxLayout b = new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS);

        centerPanel.setLayout(b);

        centerPanel.setBorder(new EmptyBorder(BORDER_PADDING, BORDER_PADDING, BORDER_PADDING,
                                              smallerPadding));

        centerPanel.add(myGamePanel);

        centerPanel.setBackground(myMainPanelsColors);

        return centerPanel;
    }

    
    /** {@inheritDoc} */
    @Override
    public void keyPressed(final KeyEvent theEvent) {
    
        if (KeyEvent.VK_LEFT == theEvent.getKeyCode()
            || "A".equals(KeyEvent.getKeyText(theEvent.getKeyCode()))) {
            myBoard.left();

        } else if (KeyEvent.VK_RIGHT == theEvent.getKeyCode()
                 || "D".equals(KeyEvent.getKeyText(theEvent.getKeyCode()))) {
            myBoard.right();

        } else if (KeyEvent.VK_DOWN == theEvent.getKeyCode()
                 || "S".equals(KeyEvent.getKeyText(theEvent.getKeyCode()))) {
            myBoard.down();

        } else if (KeyEvent.VK_UP == theEvent.getKeyCode()
                 || "W".equals(KeyEvent.getKeyText(theEvent.getKeyCode()))) {
            myBoard.rotate();

        } else if (KeyEvent.VK_SPACE == theEvent.getKeyCode()) {

            myBoard.drop();
        }
    }
    
    /**
     * displays the game over screen.
     * 
     * @return JOptionPane with components.
     */
    public JOptionPane gameOverScreen() {

        final JPanel gameOverPanel = new JPanel();

        final JLabel imgLabel = new JLabel("", new ImageIcon("./resources/gameover.gif"),
                                           SwingConstants.CENTER);

        gameOverPanel.setBackground(myDarkPurple);

        gameOverPanel.add(imgLabel);

        final JOptionPane pane = new JOptionPane(gameOverPanel);

        pane.setBackground(myDarkPurple);

        final JDialog dialog = pane.createDialog(null, "-You've Lost-");

        dialog.setVisible(true);

        dialog.dispose();

        return pane;
    }
    

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if ("new game".equals(theEvent.getPropertyName())) {
            
            myTimer.start();

            myBoard.newGame();
            
            myScorePanel.resetValues();

            myScorePanel.startTimer();
            
            myPausekeyListener.removeKey();
            
            myPauseToggle = false;
            
            myFrame.addKeyListener(this);
            
            myPausekeyListener.addKey();


        } else if ("end game".equals(theEvent.getPropertyName())) {

            myTimer.stop();
            
            myScorePanel.stopTimer();

            myFrame.removeKeyListener(this);
            
            myPausekeyListener.removeKey();
            
            
        } else if ("game over".equals(theEvent.getPropertyName())) {

            gameOverScreen();

            myTimer.stop();

            myScorePanel.stopTimer();
            
            myFrame.removeKeyListener(this);

            myPausekeyListener.removeKey();

        }
    }
    

    /**
     * A inner class that listens for timer events and moves the Tetris blocks
     * down with each tick.
     * 
     */
    private class TimeListener implements ActionListener {

        /** {@inheritDoc} */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {

            myBoard.down();
        }
    }

    
    /**
     * Key Listener for the pause key event.
     * 
     * @author gehry guest
     */
    private class PauseListener extends KeyAdapter {

        /** {@inheritDoc} */
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            
            final String letterP = "P";
            
            if (letterP.equals(KeyEvent.getKeyText(theEvent.getKeyCode()))
                && !myPauseToggle) {

                myFrame.removeKeyListener(GUIFrame.this);

                myTimer.stop();

                myScorePanel.stopTimer();

                myPauseToggle = true;
                
            } else if (letterP.equals(KeyEvent.getKeyText(theEvent.getKeyCode()))
                     && myPauseToggle) {
 
                myFrame.removeKeyListener(GUIFrame.this);
                
                myFrame.addKeyListener(GUIFrame.this);
                
                myTimer.start();

                myScorePanel.startTimer();

                myPauseToggle = false;
            }
        }
        
        /** Removes the pause key functionality. */
        public void removeKey() {
            
            myFrame.removeKeyListener(this);
        }
        
        
        /** Adds the pause key functionality. */
        public void addKey() {
            
            myFrame.addKeyListener(this);
        }
    }

}
