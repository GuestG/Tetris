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

/**
 * Creates panel in which game will be played on.
 * 
 * @author gehry guest
 * @version 2.0
 */
public class GUIGamePanel extends JPanel implements PropertyChangeListener {

    /** A number for serialization. */
    private static final long serialVersionUID = 2576630543621018480L;

    /** default size for boxes. */
    private static final int BOX_SIZE = 25;

    /** creates new board object. */

    /** String representation of the board. */
    private String myBoardRepresentation;

    /** 2d array for holding coordinate points of blocks. */
    private int[][] myBoardList;

    /**
     * Constructor for initializing values for fields.
     * 
     */
    public GUIGamePanel() {

        final int height = 25;

        final int width = 12;

        myBoardList = new int[height][width];

        setUp();
    }

    /**
     * Sets up the JPanel with preferred dimensions, borders, and color.
     * 
     */
    public void setUp() {

        final Dimension defaultPanelSize = new Dimension(300, 20);

        this.setPreferredSize(defaultPanelSize);

        this.setOpaque(false);

        this.setBorder(new LineBorder(Color.MAGENTA, 2));

        this.setVisible(true);
    }

    /** {@inheritDoc} */
    @Override
    public void paintComponent(final Graphics theGraphics) {

        super.paintComponent(theGraphics);

        final int heightOfGrid = 526; // makes 20 boxes for height

        final int widthOfGrid = 276; // makes 10 boxes for width

        final int lineWidths = 525; // creates line widths

        final int lineHeights = 275; // creates line heights

        final Graphics2D graphics = (Graphics2D) theGraphics;

        graphics.setStroke(new BasicStroke(1));

        graphics.setColor(Color.MAGENTA);

        for (int c = 0; c < widthOfGrid; c += BOX_SIZE) {

            for (int r = 0; r < heightOfGrid; r += BOX_SIZE) {
                if (c != 0) {
                    graphics.drawLine(BOX_SIZE, r, lineHeights, r);
    
                    graphics.drawLine(c, BOX_SIZE, c, lineWidths);
                }
            }
        }

        for (int i = 0; i < myBoardList.length; i++) {

            for (int j = 0; j < myBoardList[i].length; j++) {

                final int x = myBoardList[i][j];

                final int y = i;

                graphics.setPaint(Color.CYAN);

                if (x != 0) {
                    graphics.fillRect(x * BOX_SIZE, y * BOX_SIZE, BOX_SIZE, BOX_SIZE);

                    graphics.setPaint(Color.MAGENTA);

                    graphics.drawRect(x * BOX_SIZE, y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
                }

            }

            Arrays.fill(myBoardList[i], 0);
        }

    }

    /** {@inheritDoc} */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        final String[] boardBreakDownChars;

        if ("piece moved".equals(theEvent.getPropertyName())) {

            myBoardRepresentation = (String) theEvent.getNewValue();

            boardBreakDownChars = myBoardRepresentation.split("\n");

            final String[] newBoardNoTop = Arrays.copyOfRange(boardBreakDownChars, 4,
                                                              boardBreakDownChars.length);

            for (int i = 0; i < newBoardNoTop.length; i++) {

                for (int j = 0; j < newBoardNoTop[i].length(); j++) {

                    if (!Character.isWhitespace(newBoardNoTop[i].charAt(j))
                        & newBoardNoTop[i].charAt(j) != '|'
                        & newBoardNoTop[i].charAt(j) != '-') {

                        myBoardList[i][j] = j;
                    }
                }
            }
        }
        repaint();
    }
}
