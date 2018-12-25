/* 
 * TCSS 305 - Assignment 6
 */

package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;


/**
 * Class that creates menu bar for use in GUIFrame.
 * 
 * @author gehry guest
 * @version 2.0
 */
public class GUIMenuBar implements ActionListener {

    /** Menu bar for adding all components to. */
    private final JMenuBar myMenuBar;
    
    /** Menu item to see controls of game. */
    private final JMenuItem myControlsButton;
    
    /** Menu item to see scoring algorithm. */
    private final JMenuItem myScoringButton;
    
    /** Menu item to show credits to artist who's artwork was used in creating the program. */
    private final JMenuItem myCreditsButton;
    
    /** Menu item to close program. */
    private final JMenuItem myExitButton;
    
    /** Menu item to start a new game. */
    private final JMenuItem myNewGameButton;  
    
    /** Menu item to end the game. */
    private final JMenuItem myEndButton;
    
    /** Color for Menu compounds of GUI. */
    private final Color myDarkPurple = new Color(75, 0, 130);
    
    /** Support for firing property change events from this class. */
    private final PropertyChangeSupport myPcs;
    
    /**
     * Initializes fields with reasonable values and takes current board from GUIFrame.
     * 
     */
    public GUIMenuBar() {
        
        myMenuBar = new JMenuBar();
        
        myControlsButton = new JMenuItem("Controls...");
        
        myScoringButton = new JMenuItem("Scoring...");
        
        myCreditsButton = new JMenuItem("Credits...");
        
        myNewGameButton = new JMenuItem("New Game");
        
        myEndButton = new JMenuItem("End Game");
        
        myExitButton = new JMenuItem("Exit");
        
        myPcs = new PropertyChangeSupport(this);
        
    }
    

    /** Creates the menu bar with sub-menus and actions.
     * 
     * @return JMenuBar for the GUI to use.
     */
    public JMenuBar createMenuBar() {
            
        final JMenu optionButtons = new JMenu("Options");
        
        final JMenu about = new JMenu("About");
        
        myControlsButton.addActionListener(this);
            
        myScoringButton.addActionListener(this);

        myCreditsButton.addActionListener(this);
            
        myNewGameButton.addActionListener(this);
        
        myEndButton.addActionListener(this);
        
        myExitButton.addActionListener(this);
        
        optionButtons.setForeground(Color.CYAN);
       
        about.setForeground(Color.CYAN);
        
        about.setBackground(Color.PINK);
        
        setMenuColors();
        
        myEndButton.setEnabled(false);
       
        optionButtons.add(myNewGameButton); 
        
        optionButtons.addSeparator();
        
        optionButtons.add(myEndButton);
        
        optionButtons.addSeparator();
         
        optionButtons.add(myExitButton);
        
        about.add(myScoringButton);
        
        about.addSeparator();
        
        about.add(myControlsButton);
        
        about.addSeparator();
        
        about.add(myCreditsButton);
        
        myMenuBar.add(optionButtons);
        
        myMenuBar.add(about);
        
        return myMenuBar;
    }
    
    
    /**
     * Sets up colors for JMenuItems.
     */
    public void setMenuColors() {
        
        myMenuBar.setBackground(myDarkPurple);
        
        myMenuBar.setBorderPainted(true);
        
        myMenuBar.setBorder(new LineBorder(Color.MAGENTA, 2));
        
        myMenuBar.setForeground(Color.BLACK);
        
        myControlsButton.setForeground(Color.CYAN);
        
        myScoringButton.setForeground(Color.CYAN);
        
        myCreditsButton.setForeground(Color.CYAN);
        
        myNewGameButton.setForeground(Color.CYAN);
        
        myEndButton.setForeground(Color.CYAN);
        
        myExitButton.setForeground(Color.CYAN);
        
        myControlsButton.setBackground(myDarkPurple);
        
        myScoringButton.setBackground(myDarkPurple);
        
        myCreditsButton.setBackground(myDarkPurple);
        
        myNewGameButton.setBackground(myDarkPurple);
        
        myEndButton.setBackground(myDarkPurple);
        
        myExitButton.setBackground(myDarkPurple);
        
    }
    
    /**
     * Adds a listener for property change events from this class.
     * 
     * @param theListener a PropertyChangeListener to add.
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        
        myPcs.addPropertyChangeListener(theListener);
    }
    
    /**
     * Removes a listener for property change events from this class.
     * 
     * @param theListener a PropertyChangeListener to remove.
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        
        myPcs.removePropertyChangeListener(theListener);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        
        if (theEvent.getSource() == myControlsButton) {
            
            JOptionPane.showMessageDialog(null, "Controls \n"
                            + "To Rotate Pieces: Press ethier \"w\" or the Up Arrow Key.\n"
                            + "To Move Left: Press ethier \"a\" or the Left Arrow Key.\n"
                            + "To Move Down: Press ethier \"s\" or the Down Arrow Key.\n"
                            + "To Move Right: Press ethier \"d\" or the Right Arrow Key.\n"
                            + "\n"
                            + "To pause the game press : p",
                              "Controls",
                              JOptionPane.PLAIN_MESSAGE);
            
        } else if (theEvent.getSource() == myScoringButton) {
            
            JOptionPane.showMessageDialog(null, "- Every Four lines cleared causes "
                            + "the game to become increasingly more difficult.\n"
                            + "- Survive as long as you can every second you "
                            + "survive you gain ten points until \nyou lose. \n",
                             "Scoring",
                             JOptionPane.PLAIN_MESSAGE);
            
        } else if (theEvent.getSource() == myCreditsButton) {
            
            JOptionPane.showMessageDialog(null, "Art for SplashScreen: "
                            + "http://genuinehuman.tumblr.com/post/143018095307/"
                            + "gunship-the-gunship-floppy-disk-styled-vinyl-is \n"
                            + "Also known as: Jason Tammemagi\n"
                            + "known for his pixleart."
                            , "Credits"
                            , JOptionPane.PLAIN_MESSAGE);
            
        } else if (theEvent.getSource() == myNewGameButton) {

            myPcs.firePropertyChange("new game", false, true);
            
            myEndButton.setEnabled(true);
            
            myNewGameButton.setEnabled(false);
            
        } else if (theEvent.getSource() == myEndButton) {
            
            myPcs.firePropertyChange("end game", false, true);
            
            myEndButton.setEnabled(false);
            
            myNewGameButton.setEnabled(true);
            
        } else if (theEvent.getSource() == myExitButton) {
            
            System.exit(0);
            
        }   
    }
}
