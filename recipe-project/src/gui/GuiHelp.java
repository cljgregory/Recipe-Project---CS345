package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import recipeBookComponents.Recipe;
import recipeBookUtilities.ConvertUtil;

/**
 * Helper methods for repetitive code in GUI construction.
 * 
 * @author Ethan Pae
 * @version Sprint 2
 */
public class GuiHelp
{
  public static final Dimension BUTTON_STD = new Dimension(50, 50);
  private static final String VOLUME = "Volume"; // Internal, they don't
  private static final String MASS = "Mass"; // need translation
  private static final String INVALID = "INVALID"; // ^^^^ thanks :) -dylan
  private static Recipe recipe;

  /**
   * Creates a KeyListener that triggers the provided action when the Enter key is pressed.
   * 
   * @param desiredKey
   *          The key to be bound to the action.
   * @param action
   *          The ActionListener to be triggered by the Enter key.
   * @return A KeyListener that listens for the Enter key press event.
   */
  public static KeyListener addHotKey(final int desiredKey, final ActionListener action)
  {
    return new KeyListener()
    {
      @Override
      public void keyTyped(final KeyEvent e)
      {
      }

      @Override
      public void keyPressed(final KeyEvent e)
      {
        if (e.getKeyCode() == desiredKey)
        {
          action.actionPerformed(null);
        }
      }

      @Override
      public void keyReleased(final KeyEvent e)
      {
      }
    };
  }

  /**
   * Adds a label and a JComboBox to a JPanel with specified spacings.
   * 
   * @param panel
   *          The JPanel to add components to.
   * @param component
   *          The JComboBox to be added to the panel.
   * @param label
   *          The label text for the JComboBox.
   * @param spacing1
   *          The spacing between the label and the JComboBox.
   * @param spacing2
   *          The spacing after the JComboBox.
   */
  public static void addLabelAndDB(final JPanel panel, final JComponent component,
      final String label, final int spacing1, final int spacing2)
  {
    panel.add(new JLabel(label));
    panel.add(spacingX(spacing1));
    panel.add(component);
    panel.add(spacingX(spacing2));
  }

  /**
   * Creates a WindowFocusListener that keeps the input text field selected when the window gains
   * focus.
   * 
   * @param frame
   *          The JFrame containing the input text field.
   * @param inputTextField
   *          The JTextField to be selected when the window gains focus.
   * @return A WindowFocusListener that listens for window focus events.
   */
  public static WindowFocusListener keepTextSelected(final JFrame frame,
      final JTextField inputTextField)
  {
    return new WindowAdapter()
    {
      @Override
      public void windowGainedFocus(final WindowEvent e)
      {
        inputTextField.requestFocusInWindow();
        inputTextField.selectAll();
      }
    };
  }

  /**
   * Creates a rigid area that expands horizontally.
   * 
   * @param xPos
   *          The x axis modifier for the rigid area.
   * @return A rigid area representing the horizontal spacing.
   */
  public static Component spacingX(final int xPos)
  {
    return Box.createRigidArea(new Dimension(xPos, 0));
  }

  /**
   * Creates a rigid area that expands vertically.
   * 
   * @param yPos
   *          The y axis modifier for the rigid area.
   * @return A rigid area representing the vertical spacing.
   */
  public static Component spacingY(final int yPos)
  {
    return Box.createRigidArea(new Dimension(0, yPos));
  }

  /**
   * Flexible action method for resetting values within a GUI interface. Assumes two text fields and
   * three drop down objects, any can be null.
   * 
   * @param <T>
   *          Allows for any type of variable
   * @param textOne
   *          The first textField to be reset
   * @param textTwo
   *          The second textField to be reset
   * @param dropOne
   *          The first drop-down to be reset
   * @param dropTwo
   *          The second drop-down to be reset
   * @param dropThree
   *          The third drop-down to be reset
   * @param isFixedOne
   *          If the items in dropOne should not be deleted
   * @param isFixedTwo
   *          If the items in dropTwo should not be deleted
   * @param isFixedThree
   *          If the items in dropThree should not be deleted
   * @param buttonOne
   *          For enabling or disabling menu buttons, only accepts JMenuItem and JButton
   * @return The ActionListener that resets the arguments
   */
  public static <T> ActionListener resetAction(final JTextField textOne, final JTextField textTwo,
      final JComboBox<T> dropOne, final JComboBox<T> dropTwo, final JComboBox<T> dropThree,
      final boolean isFixedOne, final boolean isFixedTwo, final boolean isFixedThree,
      final Component buttonOne)
  {
    return new ActionListener()
    {

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        if (textOne != null)
          textOne.setText(ConvertUtil.BLANK);
        if (textTwo != null)
          textTwo.setText("_________");
        if (dropOne != null)
        {
          if (dropOne.getItemCount() > 0)
          {
            if (isFixedOne)
              dropOne.setSelectedIndex(0); // Set to blank item
            else
            {
              dropOne.removeAllItems(); // Remove all items
              dropOne.setEnabled(false); // Disable dropdown
              // Be sure this dropdown can be re-enabled
            }
          }
        }
        if (dropTwo != null)
        {
          if (isFixedTwo)
            dropTwo.setSelectedIndex(0); // Set to blank item
          else
          {
            dropTwo.removeAllItems(); // Remove all items
            dropTwo.setEnabled(false); // Disable dropdown
            // Be sure this dropdown can be re-enabled
          }
        }
        if (dropThree != null)
        {
          if (isFixedThree)
            dropThree.setSelectedIndex(0); // Set to blank item
          else
          {
            dropThree.removeAllItems(); // Remove all items
            dropThree.setEnabled(false); // Disable dropdown
            // Be sure this dropdown can be re-enabled
          }
        }
        if (buttonOne != null)
        {
          // Ensure it's either a JMenuItem or JButton
          if (buttonOne.getClass() == JButton.class || buttonOne.getClass() == JMenuItem.class)
            buttonOne.setEnabled(true);
        }
      }
    };
  }


  /**
   * Gets the value of private Recipe variable.
   * 
   * @return the value of recipe
   */
  public static Recipe getRecipe()
  {
    return recipe;
  }

  /**
   * Helper method for creating error pop-ups.
   * 
   * @param cond
   *          The condition to check, runs the pop-up if condition is true
   * @param frame
   *          The frame in which the pop-up will occur in
   * @param reason
   *          The explanation for the error
   * @param title
   *          The title of the window
   * @return True if error cond was true and false if cond was not
   */
  public static boolean inputCheck(final Boolean cond, final JFrame frame, final String reason,
      final String title)
  {
    if (cond)
    {
      JOptionPane.showMessageDialog(frame, reason, // INVALID UNIT ENTRY
          title, JOptionPane.ERROR_MESSAGE);
      return true;
    }
    return false;
  }

  /**
   * Initializes the top button components.
   * 
   * @param frame
   *          The frame to hold the panel
   * @param panel
   *          the panel to hold the buttons
   */
  public static void makeTopButtonComponents(final JComponent frame, final JComponent panel)
  {
    if (panel.getClass() != JMenuItem.class)
      panel.setLayout(new GridLayout(1, 5, 5, 5));
    frame.setLayout(new FlowLayout(FlowLayout.LEFT));
  }

  /**
   * Adds a button to the frame.
   * 
   * @param frame
   *          Component for adding the panel
   * @param panel
   *          Component for adding the buttons
   * @param button
   *          Button to be added to Component, can be either JMenuItem or JButton
   * @param image
   *          Image associated with the button
   * @param onHover
   *          Message that occurs when hovering over the button
   * @param action
   *          Action the button performs
   */
  public static void addButtonToTop(final JComponent frame, final JComponent panel,
      final Component button, final String image, final String onHover, final ActionListener action)
  {
    // Find button type
    Component newButton;

    if (button.getClass() == JButton.class || button.getClass() == JMenuItem.class)
    {
      if (button.getClass() == JButton.class)
      {
        newButton = new JButton();
        newButton = button;
        newButton.setMaximumSize(BUTTON_STD);
        newButton.setPreferredSize(BUTTON_STD);
        newButton.setMinimumSize(BUTTON_STD);
        newButton.setBackground(Color.white);
      }
      else if (button.getClass() == JMenuItem.class)
      {
        newButton = new JMenuItem();
        newButton = button;
      }
      else
        return;

      if (image != null)
      {
        ImageIcon icon = loadImageIcon(image);
        ((AbstractButton) newButton).setIcon(icon); // Not sure why it casts but
      }
      if (onHover != null)
        ((JComponent) newButton).setToolTipText(onHover); // it works
      
      if (action != null)
        ((AbstractButton) newButton).addActionListener(action);
      
      panel.add(newButton);
      frame.add(panel);
    }
  }

  private static ImageIcon loadImageIcon(final String name)
  {
    URL url = GuiHelp.class.getResource("/iconsAndResources/" + name);
    ImageIcon icon = new ImageIcon(url);
    return icon;
  }

  /**
   * Type is used to determine whether the comboBoxes value selected is a "volume" or "mass" without
   * making the selected type a class.
   * 
   * @param field
   *          the field that contains the String item
   * @return a string representing the type of the selected value.
   */
  public static String getType(final JComboBox<String> field)
  {
    String type = new String();
    String selectedItem = field.getSelectedItem().toString();
    // inb4 long ass if statement
    if (selectedItem.equals(ConvertUtil.MLS) || selectedItem.equals(ConvertUtil.CUPSS)
        || selectedItem.equals(ConvertUtil.FLOS) || selectedItem.equals(ConvertUtil.GALS)
        || selectedItem.equals(ConvertUtil.PINCHESS) || selectedItem.equals(ConvertUtil.PINTSS)
        || selectedItem.equals(ConvertUtil.QUARTSS) || selectedItem.equals(ConvertUtil.TBSPSS)
        || selectedItem.equals(ConvertUtil.TSPSS))
    {
      type = VOLUME;
    }
    else if (selectedItem.equals(ConvertUtil.BLANK))
    {
      type = INVALID; // Blank Option
    }
    else
    {
      type = MASS;
    }
    return type;
  }

}
