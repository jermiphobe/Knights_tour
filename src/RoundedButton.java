import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;


class RoundedButton extends Component {

    ActionListener actionListener;     // Post action events to listeners
    String label;                      // The Button's text
    protected boolean pressed = false; // true if the button is detented.
    boolean entered = false;
    /**
     * Constructs a RoundedButton with no label.
     */
    public RoundedButton() {
        this("");
    }

    /**
     * Constructs a RoundedButton with the specified label.
     *
     * @param label the label of the button
     */
    public RoundedButton(String label) {
        this.label = label;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    /**
     * gets the label
     *
     * @see setLabel
     */
    public String getLabel() {
        return label;
    }

    /**
     * sets the label
     *
     * @see getLabel
     */
    public void setLabel(String label) {
        this.label = label;
        invalidate();
        repaint();
    }

    /**
     * paints the RoundedButton
     */
    @Override
    public void paint(Graphics g) {
        
        if (entered) {
        	  // paint the interior of the button
            if (pressed) {
                g.setColor(getBackground().darker().darker());
            } else {
            	g.setColor(getBackground().darker());;
            }
        	
        } else {
        	g.setColor(getBackground());
        }
        
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        // draw the perimeter of the button
        g.setColor(getBackground().darker().darker().darker());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        // draw the label centered in the button
        Font f = getFont();
        if (f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            g.setColor(getForeground());
            g.drawString(label, getWidth() / 2 - fm.stringWidth(label) / 2, getHeight() / 2 + fm.getMaxDescent());
        }
    }

    /**
     * The preferred size of the button.
     */
    @Override
    public Dimension getPreferredSize() {
        Font f = getFont();
        if (f != null) {
            FontMetrics fm = getFontMetrics(getFont());
            int max = Math.max(fm.stringWidth(label) + 40, fm.getHeight() + 40);
            return new Dimension(max, max);
        } else {
            return new Dimension(100, 100);
        }
    }

    /**
     * The minimum size of the button.
     */
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(100, 100);
    }

    /**
     * Adds the specified action listener to receive action events from this
     * button.
     *
     * @param listener the action listener
     */
    public void addActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.add(actionListener, listener);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    /**
     * Removes the specified action listener so it no longer receives action
     * events from this button.
     *
     * @param listener the action listener
     */
    public void removeActionListener(ActionListener listener) {
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }

    /**
     * Paints the button and distribute an action event to all listeners.
     */
    @Override
    public void processMouseEvent(MouseEvent e) {
        Graphics g;
        switch (e.getID()) {
            case MouseEvent.MOUSE_PRESSED:
                // render myself inverted....
                pressed = true;

                // Repaint might flicker a bit. To avoid this, you can use
                // double buffering (see the Gauge example).
                repaint();
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (actionListener != null) {
                    actionListener.actionPerformed(new ActionEvent(
                            this, ActionEvent.ACTION_PERFORMED, label));
                }
                // render myself normal again
                if (pressed == true) {
                    pressed = false;

                    // Repaint might flicker a bit. To avoid this, you can use
                    // double buffering (see the Gauge example).
                    repaint();
                }
                break;
            case MouseEvent.MOUSE_ENTERED:
            	 entered = true;

                 // Repaint might flicker a bit. To avoid this, you can use
                 // double buffering (see the Gauge example).
                 repaint();
            	
                break;
            case MouseEvent.MOUSE_EXITED:
                if (pressed == true) {
                    // Cancel! Don't send action event.
                    pressed = false;

                    // Repaint might flicker a bit. To avoid this, you can use
                    // double buffering (see the Gauge example).
                    repaint();

                    // Note: for a more complete button implementation,
                    // you wouldn't want to cancel at this point, but
                    // rather detect when the mouse re-entered, and
                    // re-highlight the button. There are a few state
                    // issues that that you need to handle, which we leave
                    // this an an excercise for the reader (I always
                    // wanted to say that!)
                } else {
                	entered = false;
                	repaint();
                }
                break;
        }
        super.processMouseEvent(e);
    }
}