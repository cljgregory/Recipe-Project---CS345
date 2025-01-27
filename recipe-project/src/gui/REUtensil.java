package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import recipeBookComponents.Recipe;
import recipeBookComponents.Step;
import recipeBookComponents.Utensil;

/**
 * Recipe Editor Panel for Utensils.
 * 
 * @author Ethan Pae & Colin Gregory
 * @version 4.0
 */
public class REUtensil extends JPanel
{

  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  private static final long serialVersionUID = 1L;
  private static final Dimension TXT_BOX_STD = new Dimension(130, 28);
  private static final String NLINE = "\n"; // Conforming to CheckStyle
  private static final String ERROR = "ERROR"; // Conforming to CheckStyle
  private Recipe recipe;
  @SuppressWarnings("unused")
  private RecipeEditor recipeEditor;

  private DefaultListModel<String> utensilList; // Sprint 2 LIST
  private JList<String> myList; // Sprint 2 LIST

  private JTextField nameText;
  private JTextField detailText;
  private JButton addButton;
  private JButton delete;

  /**
   * Creates the Utensil panel within Recipe Editor.
   * 
   * @param recipe a recipe
   * @param recipeEditor the recipe editor
   */
  public REUtensil(final Recipe recipe, final RecipeEditor recipeEditor)
  {
    this.recipeEditor = recipeEditor;

    this.recipe = recipe;
    // Initialize the head JPanel
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    Dimension borderSize = new Dimension(565, 245);
    this.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK),
        STRINGS.getString("UTENSILS")));
    this.setMaximumSize(borderSize);
    this.setPreferredSize(borderSize);
    this.setMinimumSize(borderSize);

    // Initialize the inner JPanel (Top half)
    JPanel innerPanel = new JPanel();
    innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    // Create components for innerPanel (Top half)
    nameText = new JTextField();
    detailText = new JTextField();
    addButton = new JButton(STRINGS.getString("ADD"));

    nameText.setMaximumSize(TXT_BOX_STD);
    nameText.setPreferredSize(TXT_BOX_STD);
    detailText.setMaximumSize(TXT_BOX_STD);
    detailText.setPreferredSize(TXT_BOX_STD);

    // Add components to innerPanel (Top half)
    innerPanel.add(new JLabel(STRINGS.getString("NAME_COLON"))); // Add string "Name: " to display
    innerPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Spacing
    innerPanel.add(nameText); // Add text entry prompting for "Name: "
    innerPanel.add(Box.createRigidArea(new Dimension(20, 0))); // Spacing
    innerPanel.add(new JLabel(STRINGS.getString("DETAILS_COLON"))); // Add string "Details: " to
                                                                    // display
    innerPanel.add(detailText); // Add text entry prompting for "Details: "
    innerPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Spacing
    innerPanel.add(addButton); // Button for adding entry.

    // Add innerPanel (Top half)
    this.add(innerPanel);

    // Initialize components for innerPanel2 (Bottom half)
    utensilList = new DefaultListModel<String>();
    myList = new JList<String>(utensilList);
    myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane listPane = new JScrollPane(myList);
    listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    myList.setSize(10, 10); // do I need this??
    delete = new JButton(STRINGS.getString("DELETE")); // Delete button #FIXME: TBD delete
                                                       // button in REUtensil

    // Initialize innerPanel2 itself
    JPanel innerPanel2 = new JPanel(); // Bottom half
    innerPanel2.setLayout(new BoxLayout(innerPanel2, BoxLayout.X_AXIS));

    // Initialize the list area
    myList.setBorder(new TitledBorder(new LineBorder(Color.BLACK)));
    myList.setMaximumSize(new Dimension(640, 240));

    // Format the delete button
    delete.setVerticalTextPosition(AbstractButton.BOTTOM);
    delete.setHorizontalTextPosition(AbstractButton.CENTER);
    delete.setAlignmentY(BOTTOM_ALIGNMENT);

    // Add components to innerPanel2 (Bottom half)
    innerPanel2.add(Box.createRigidArea(new Dimension(10, 0)));
    // innerPanel2.add(textPane); old text area
    innerPanel2.add(listPane); // NEW LIST
    innerPanel2.add(Box.createRigidArea(new Dimension(5, 0)));
    innerPanel2.add(delete);
    innerPanel2.add(Box.createRigidArea(new Dimension(70, 0)));

    this.add(innerPanel2);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    // These buttons will be diabled until the user hits new
    nameText.setEnabled(false);
    detailText.setEnabled(false);
    addButton.setEnabled(false);
    delete.setEnabled(false);

    // add button actions DUPLICATES ALLOWED
    addButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        // Retrieve Variables
        String name = nameText.getText();
        String details = detailText.getText();
        Utensil selection = new Utensil(name, details);

        // Add new utensil to recipe object's utensils list
        if (name != null && !name.trim().isEmpty())
        {

          if (recipe.addUtensil(selection))
          {
            utensilList.addElement(selection.toString()); // LIST adds elements to list
          }
          else
          {
            // Display error message if utensil already exists in list
            JOptionPane.showMessageDialog(REUtensil.this, STRINGS.getString("UT_ALREADY_EXISTS"),
                STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
          }
        }
        else
        {
          JOptionPane.showMessageDialog(REUtensil.this, STRINGS.getString("PLZ_ENTER_UT_NAME"),
              STRINGS.getString(ERROR), JOptionPane.ERROR_MESSAGE);
        }

        // For testing purposes

        nameText.setText("");
        detailText.setText("");
        recipeEditor.setDocumentChanged();
      }
    });
    
    nameText.getDocument().addDocumentListener(new DocumentListener()
    {

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        if (nameText.getText().isEmpty())
        {
          addButton.setEnabled(false);
        }

      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        addButton.setEnabled(true);

      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        // TODO Auto-generated method stub
        
      }

      

    });

    // Add the mouse listener to the panel
    innerPanel2.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseClicked(final MouseEvent e)
      {
        // Call clearSelection() on the JList when the mouse is clicked
        myList.clearSelection();
      }
    });
    
    // delete button actions
    delete.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        if (recipe.getUtensils().size() > 0) // If there exists entries
        {
          // remove utensil from the list
          String selectedUtensil = myList.getSelectedValue();
          if (selectedUtensil != null)
          { // if selection
            utensilList.removeElement(selectedUtensil);
          }

          // remove utensil from recipe IF to string matches selected utensil
          // DON'T edit something while you are iterating over it
          Boolean firstRemoved = true;
          ArrayList<Utensil> utensils = new ArrayList<>(recipe.getUtensils());
          for (Utensil utensil : utensils)
          {
            if (firstRemoved && selectedUtensil.contains(utensil.toString()))
            {
              recipe.removeUtensil(utensil);
              firstRemoved = false;
            }
          }

        }
        recipeEditor.setDocumentChanged();
      }
    });

    myList.addListSelectionListener(new ListSelectionListener()
    {
      @Override
      public void valueChanged(final ListSelectionEvent e)
      {
        if (!e.getValueIsAdjusting())
        {
          String selectedIngredient = myList.getSelectedValue();
          Recipe r = recipeEditor.getRecipe();
          ArrayList<Step> steps = r.getSteps();

          boolean ingredientUsedInStep = false;
          if (selectedIngredient != null)
          {
	          for (Step step : steps)
	          {
	            Utensil utensil = step.getSourceUtensil();
	            if (utensil != null)
	            { // Add null check here
	              String s = utensil.getName();
	              if (selectedIngredient.contains(s))
	              {
	                ingredientUsedInStep = true;
	                break;
	              }
	            }
	          }
          }

          delete.setEnabled(!ingredientUsedInStep);
        }
      }
    });

  }

  /**
   * Displays the Utensil Panel.
   */
  public void show()
  {
    this.setVisible(true);
  }

  /**
   * Updates the utensil list.
   * @param utensils list of utensils
   */
  public void updateUtensilsList(final ArrayList<Utensil> utensils)
  {

    // Make a copy of the utensils list before iterating through it

    // Add the previously loaded utensils to the area JTextArea
    for (Utensil entry : utensils)
    {
      utensilList.addElement(entry.toString() + NLINE);
      recipe.addUtensil(entry);
    }

  }

  /**
   * Gets the current utensils list.
   * @return utenil list
   */
  public List<Utensil> getUtensils()
  {
    return recipe.getUtensils();
  }

  /**
   * Clears the list.
   */
  public void resetPanel()
  {
    utensilList.clear();
  }

  /**
   * Enables the utenil panel buttons.
   */
  public void utensilEnableButton()
  {
    nameText.setEnabled(true);
    detailText.setEnabled(true);
    addButton.setEnabled(false);
    delete.setEnabled(true);
  }

  /**
   * Disables the buttons once new is pressed.
   */
  public void utensilDisableButtons()
  {
    nameText.setEnabled(false);
    detailText.setEnabled(false);
    addButton.setEnabled(false);
    delete.setEnabled(false);
  }

  /**
   * Clears the utensils from the list.
   */
  public void clearUtensils()
  {
    utensilList.clear();
    recipe.clearUtensils();
  }
}
