/* 
 * TCSS 305 - Assignment 6
 */

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


/**
 * Creates panel for the pieces that are going to be dropped next.
 * 
 * @author gehry guest
 * @version 2.0
 */
public class GUINextPiecePanel extends JPanel implements PropertyChangeListener {

    /** A number for serialization. */
    private static final long serialVersionUID = 7727726316384950106L;
    
    /** size for box and grid.  */
    private static final int BOX_SIZE = 30;

    /** default size for panel. */
    private static final Dimension DEFAULT_SIZE = new Dimension(180, 65);
    
    /** String representation of the board. */
    private String myBoardRepresentation;
    
    /** List to hold preview pieces. */
    private int[][] myPreviewList;
     
    
    /**
     *  Initializes fields with reasonable values.
     * 
     */
    public GUINextPiecePanel() {
        
        final int height = 25;
        
        final int width = 10;
        
        myPreviewList = new int[height][width];
        
        setUp();
    }
    
    
    /**
     * Sets up Panel with values along with a border.
     * 
     */
    public void setUp() {

        final Color darkPurple = new Color(75, 0, 130);
        
        this.setPreferredSize(DEFAULT_SIZE);
        
        this.setBackground(darkPurple);
        
        this.setVisible(true);
        
        final TitledBorder title = new TitledBorder(
                                   new LineBorder(Color.MAGENTA, 2), 
                                   "Next Piece", TitledBorder.CENTER,
                                   TitledBorder.BELOW_BOTTOM, null , 
                                   Color.CYAN);
        this.setBorder(title);
    }
    
    
    /** {@inheritDoc} */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        
        super.paintComponent(theGraphics);
        
        final Color hotPink = new Color(255, 105, 180);
        
        final int heightOfGrid = 151; // makes 4 boxes for height
        
        final int widthOfGrid = 151; // makes 4 boxes for width
        
        final int lineWidths = 150; // creates line widths
        
        final int lineHeights = 150; // creates line heights
        
        final Graphics2D graphics = (Graphics2D) theGraphics; 
        
        graphics.setStroke(new BasicStroke(1));
        
        graphics.setColor(Color.CYAN);
        
        for (int c = 0; c < widthOfGrid; c += BOX_SIZE) {

            for (int r = 0; r < heightOfGrid; r += BOX_SIZE) {
                if (c != 0) {
                    graphics.drawLine(BOX_SIZE, r, lineHeights, r);
    
                    graphics.drawLine(c, BOX_SIZE, c, lineWidths);
                }
            }
        }
        
        for (int i = 0; i < myPreviewList.length; i++) {

            for (int j = 0; j < myPreviewList[i].length; j++) {
                
                final int x  = myPreviewList[i][j];
                
                final int y = i + 2;

                graphics.setPaint(hotPink);

                if (x != 0) {
                    graphics.fillRect(x * BOX_SIZE, y * BOX_SIZE
                                      , BOX_SIZE, BOX_SIZE);
                    
                    graphics.setPaint(Color.CYAN);
                    
                    graphics.drawRect(x * BOX_SIZE, y * BOX_SIZE
                                      , BOX_SIZE, BOX_SIZE);
                }
            }
            
            Arrays.fill(myPreviewList[i], 0);
        }
    }
    
    
    /** {@inheritDoc} */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        final String[] boardBreakDownChars;
        
        if ("preview".equals(theEvent.getPropertyName())) {
            
            myBoardRepresentation = (String) theEvent.getNewValue();

            boardBreakDownChars = myBoardRepresentation.split("\n");

            final String[] onlyTop = Arrays.copyOfRange(boardBreakDownChars
                                             , 1, boardBreakDownChars.length - 1);
            
            for (int i = 0; i < onlyTop.length; i++) {

                for (int j = 0; j < onlyTop[i].length(); j++) {
  
                    if (!Character.isWhitespace(onlyTop[i].charAt(j))
                                    & onlyTop[i].charAt(j) != '|'
                                    & onlyTop[i].charAt(j) != '-') {
                       
                        myPreviewList[i][j] = j + 1;
                    }
                }
            }
            repaint();
        }
    }
}
