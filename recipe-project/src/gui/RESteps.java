package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.LineBorder;
import recipeBookComponents.*;

/**
 * Recipe Editor Panel, allows adding/deleting steps in a recipe.
 * 
 * @author Dylan Marti
 * @version 2.0
 */
public class RESteps extends JPanel
{

  // added for internationalization
  public static final ResourceBundle STRINGS;
  public static final Locale LOCALE = Locale.getDefault();
  static
  {
    STRINGS = ResourceBundle.getBundle("gui.Strings");
  }

  private static final long serialVersionUID = 1L;
  private static final Dimension TXT_BOX_STD = new Dimension(100, 25);
  private static final Dimension COMBO_BOX_STD = new Dimension(75, 25);
  private static final String ERROR = STRINGS.getString("ERROR");
  private static final String NUMPLEASE = STRINGS.getString("INPUT_NUMBER_PLZ");
  private JComboBox<String> onSelection;
  private JComboBox<String> utensilSelection;
  private Recipe recipe;
  private RecipeEditor recipeEditor;

  private DefaultListModel<String> stepList; // Sprint 2 LIST
  private JList<String> myList; // Sprint 2 LIST
  private Step selection;
  private JButton delete;
  private JButton add;
  private JTextField detailText;
  private JComboBox<String> actionSelection;

  /**
   * Constructs the Steps panel for Recipe Editor.
   * 
   * @param recipe
   *          being made
   * @param recipeEditor yes
   */
  public RESteps(final Recipe recipe, final RecipeEditor recipeEditor)
  {
    this.recipeEditor = recipeEditor;
    this.recipe = recipe;
    initialize(); // Create the panel
    show(); // Display the panel
  }

  /**
   * Creates the Steps panel within Recipe Editor.
   */
  public void initialize()
  {
    // Initialize the head JPanel
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    Dimension borderSize = new Dimension(565, 245);
    this.setBorder(
        BorderFactory.createTitledBorder(new LineBorder(Color.BLACK), STRINGS.getString("STEPS")));
    this.setMaximumSize(borderSize);
    this.setPreferredSize(borderSize);
    this.setMinimumSize(borderSize);

    // Initialize the inner JPanel (Top half)
    JPanel innerPanel = new JPanel();
    innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));

    // Create components for innerPanel (Top half)
    actionSelection = new JComboBox<String>();
    onSelection = new JComboBox<String>();
    utensilSelection = new JComboBox<String>();
    detailText = new JTextField(10);
    add = new JButton(STRINGS.getString("ADD"));

    updateComboBoxes();

    // Add a PropertyChangeListener to the Recipe object
    recipe.addPropertyChangeListener(new PropertyChangeListener()
    {
      @Override
      public void propertyChange(final PropertyChangeEvent evt)
      {
        String propertyName = evt.getPropertyName();

        if (propertyName.equals("ingredients") || propertyName.equals("utensils"))
        // not sure how this factors into internationalization
        {
          updateComboBoxes();
        }
      }
    });

    // Combo Box Items
    String[] actions = {"", STRINGS.getString("BAKE"), STRINGS.getString("BOIL"),
        STRINGS.getString("BROIL"), STRINGS.getString("COOK"), STRINGS.getString("DIP"),
        STRINGS.getString("DRAIN"), STRINGS.getString("HEAT"), STRINGS.getString("IGNITE"),
        STRINGS.getString("MELT"), STRINGS.getString("MIX"), STRINGS.getString("PUT"),
        STRINGS.getString("SAUTE"), STRINGS.getString("STIR"), STRINGS.getString("SIMMER")};
    for (int i = 0; i < actions.length; i++)
    {
      actionSelection.addItem(actions[i]);
    }

    // Resize everything
    actionSelection.setMaximumSize(COMBO_BOX_STD);
    actionSelection.setPreferredSize(COMBO_BOX_STD);

    onSelection.setMaximumSize(COMBO_BOX_STD);
    onSelection.setPreferredSize(COMBO_BOX_STD);

    utensilSelection.setMaximumSize(COMBO_BOX_STD);
    utensilSelection.setPreferredSize(COMBO_BOX_STD);

    detailText.setMaximumSize(TXT_BOX_STD);
    detailText.setPreferredSize(TXT_BOX_STD);

    // Add components to innerPanel (Top half)
    // Action dropdown
    innerPanel.add(new JLabel(STRINGS.getString("ACTION_COLON"))); // Unit combo box text display
    innerPanel.add(actionSelection); // The unit combo box itself
    innerPanel.add(Box.createRigidArea(new Dimension(3, 0))); // Spacing

    // On dropdown
    innerPanel.add(new JLabel(STRINGS.getString("ON_COLON"))); // Unit combo box text display
    innerPanel.add(onSelection); // The unit combo box itself
    innerPanel.add(Box.createRigidArea(new Dimension(2, 0))); // Spacing

    // Utensil dropdown
    innerPanel.add(new JLabel(STRINGS.getString("UTENSIL_COLON"))); // Unit combo box text display
    innerPanel.add(utensilSelection); // The unit combo box itself
    innerPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Spacing

    // Details text box
    innerPanel.add(new JLabel(STRINGS.getString("DETAILS_COLON"))); // Add string "Details: " to
                                                                    // display
    innerPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Spacing
    innerPanel.add(detailText); // Add text entry prompting for "Details: "

    innerPanel.add(add); // Add button

    // Add innerPanel (Top half)
    this.add(innerPanel);
    this.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing

    new JTextArea(10, 10);
    @SuppressWarnings("unused")
    JScrollPane areaPane; // For ScrollBar functionality
    delete = new JButton(STRINGS.getString("DELETE")); // Delete button
                                                       // RESteps
    JPanel innerPanel2 = new JPanel(); // Bottom half
    innerPanel2.setLayout(new BoxLayout(innerPanel2, BoxLayout.X_AXIS));

    // Sprint 2 init LIST components
    stepList = new DefaultListModel<String>();
    myList = new JList<String>(stepList);
    myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane listPane = new JScrollPane(myList);
    listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    myList.setSize(10, 10); // do I need this??

    // Format the delete button
    delete.setVerticalTextPosition(AbstractButton.BOTTOM);
    delete.setHorizontalTextPosition(AbstractButton.CENTER);
    delete.setAlignmentY(BOTTOM_ALIGNMENT);

    // Add components to innerPanel2 (Bottom half)
    innerPanel2.add(Box.createRigidArea(new Dimension(10, 0)));
    innerPanel2.add(listPane);
    // innerPanel2.add(areaPane);
    innerPanel2.add(Box.createRigidArea(new Dimension(5, 0)));
    innerPanel2.add(delete);
    innerPanel2.add(Box.createRigidArea(new Dimension(70, 0)));

    this.add(innerPanel2);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    // These buttons will be disabled until the user hits new

    add.setEnabled(false);
    delete.setEnabled(false);
    actionSelection.setEnabled(false);
    onSelection.setEnabled(false);
    utensilSelection.setEnabled(false);
    detailText.setEnabled(false);

    // Add button functionality
    add.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        // Retrieve Variables
        String stepAction = actionSelection.getSelectedItem().toString();
        String onIngredientOrUtensil = onSelection.getSelectedItem().toString();
        String utensil = utensilSelection.getSelectedItem().toString();
        String stepDetails = detailText.getText();

        // get the ingredient information
        Ingredient ingredient = null; // name details amount unit
        for (Ingredient i : recipe.getIngredients())
        {
          if (onIngredientOrUtensil.equals(i.getName()))
          {
            ingredient = new Ingredient(onIngredientOrUtensil, i.getDetails(), i.getAmount(),
                i.getUnit());
          }
        }

        // get the source utensil information
        Utensil sourceUtensil = null;
        for (Utensil u : recipe.getUtensils())
        {
          if (onIngredientOrUtensil.equals(u.getName()))
          {
            sourceUtensil = new Utensil(onIngredientOrUtensil, u.getDetails());
          }
        }

        // get the destination utensil information
        Utensil desinationUtensil = null;
        for (Utensil u : recipe.getUtensils())
        {
          if (utensil.equals(u.getName()))
          {
            desinationUtensil = new Utensil(utensil, u.getDetails());
          }
        }

        selection = new Step(stepAction, ingredient, sourceUtensil, desinationUtensil, stepDetails,
            0.0); // TODO

        // Double check for duplicates
        Boolean duplicate = false;
        ArrayList<Step> steps = new ArrayList<>(recipe.getSteps());
        for (Step step : steps)
        {
          if (selection.toString().equals(step.toString()))
          {
            duplicate = true;
          }
        }

        if (!(duplicate)) // List doesn't exist
        {
          recipe.addStep(selection);
          stepList.addElement(selection.toString()); // LIST adds elements to list
        }
        // Print out Recipe's Steps to double check
        recipeEditor.setDocumentChanged();
        onSelection.setSelectedIndex(0);
        utensilSelection.setSelectedIndex(0);
        detailText.setText("");
        actionSelection.setSelectedIndex(0);

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

    delete.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {
        List<Step> steps = recipe.getSteps();
        if (steps.size() > 0) // If there exists entries
        {
          // remove ingredient from the list
          String selectedStep = myList.getSelectedValue();
          if (selectedStep != null)
          { // if selection
            stepList.removeElement(selectedStep);
          }

          // remove ingredient from recipe IF to string matches selected ingredient
          Step stepToRemove = null;
          for (Step step : steps)
          {
            if (step.toString().equals(selectedStep))
            {
              stepToRemove = step;
              break;
            }
          }

          if (stepToRemove != null)
          {
            recipe.removeStep(stepToRemove);
            // recipeEditor.setDocumentChanged();
          }

          recipeEditor.setRecipe(recipe);
        }
        recipeEditor.setDocumentChanged();
      }
    });
  }

  /**
   * Creates the Ingredients popup.
   * 
   * @param popup
   *          a popup
   */
  public void initPopUp(final JPopupMenu popup)
  {
    // Create a new JFrame object
    JFrame popupFrame = new JFrame(STRINGS.getString("ADD_STEP_TIME_Q"));

    // Set the layout of the content pane
    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

    // Add the components to the content pane
    // calories information and panel
    JTextField timeField = new JTextField(10);
    timeField.setEditable(true);
    JLabel timeLabel = new JLabel(STRINGS.getString("MIN_PER_STEP"));
    JPanel timePanel = new JPanel();
    timePanel.add(timeLabel);
    timePanel.add(timeField);

    // the buttons
    JButton addButton = new JButton(STRINGS.getString("ADD_STEP_TIME"));
    JButton declineButton = new JButton(STRINGS.getString("DECLINE"));
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addButton);
    buttonPanel.add(declineButton);

    // Vertically stacked popup panel
    JPanel popupPanel = new JPanel();
    popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));

    popupPanel.add(timePanel);
    popupPanel.add(buttonPanel);

    contentPane.add(popupPanel);

    // Set the content pane of the JFrame object
    popupFrame.setContentPane(contentPane);

    // Set the size of the JFrame object
    popupFrame.setSize(400, 200);
    popupFrame.setLocationRelativeTo(null); // Centered
    popupFrame.setVisible(true); // Show the JFrame object

    // Add an ActionListener to the Close button to hide the JFrame
    declineButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent e)
      {
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }
    });

    // Add an ActionListener to the Close button to hide the JFrame
    addButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent e)
      {
        if (!(timeField.getText().isEmpty()))
        {
          // set global fields
          try
          {
            selection.setTime(timeField.getText());
            popupFrame.dispose();
          }
          catch (NumberFormatException nfe)
          {
            JOptionPane.showMessageDialog(null, NUMPLEASE, ERROR, JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });

    // Add an ActionListener to the Close button to hide the JFrame
    declineButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent e)
      {
        popupFrame.dispose();
      }
    });
  }

  /**
   * Updates the items in the drop-downs respectively.
   */
  private void updateComboBoxes()
  {
    if (recipe.getIngredients().size() != 0)
    {
      // Clear the combo box
      onSelection.removeAllItems();
      // Add ingredients to onSelection
      for (Ingredient ingredient : recipe.getIngredients())
      {
        onSelection.addItem(ingredient.getName());
      }
      // Add utensils to onSelection
      for (Utensil utensil : recipe.getUtensils())
      {
        onSelection.addItem(utensil.getName());
      }
    }
    else
    {
      onSelection.removeAllItems();
      // onSelection.addItem("");
    }

    if (recipe.getUtensils().size() != 0)
    {
      // Clear the combo box
      utensilSelection.removeAllItems();
      // Add utensils to utensilSelection
      for (Utensil utensil : recipe.getUtensils())
      {
        utensilSelection.addItem(utensil.getName());
      }
    }
    else
    {
      utensilSelection.removeAllItems();
      // utensilSelection.addItem("");
    }
    if (onSelection.getItemCount() != 0 && utensilSelection.getItemCount() != 0)
    {
      stepEnableButtons();
    }
  }

  /**
   * Displays the Steps Panel.
   */
  public void show()
  {
    this.setVisible(true);
  }

  /**
   * Updates the step list with the steps list given.
   * 
   * @param arrayList
   *          the step arraylist
   */
  public void updateStepsList(final ArrayList<Step> arrayList)
  {

    // Add the existing and new steps from the recipe to the area JTextArea
    List<Step> steps = arrayList;
    for (Step entry : steps)
    {

      stepList.addElement(entry.toString());
      recipe.addStep(entry);
    }
    recipeEditor.setDocumentChanged();
  }

  /**
   * Gets the steps.
   * 
   * @return steps
   */
  public List<Step> getSteps()
  {
    return recipe.getSteps();
  }

  /**
   * Clears t=and resets the panel.
   */
  public void resetPanel()
  {
    stepList.clear();
    onSelection.removeAllItems();
    utensilSelection.removeAllItems();
  }

  /**
   * Enables the buttons.
   */
  public void stepEnableButtons()
  {
    add.setEnabled(true);
    delete.setEnabled(true);
    actionSelection.setEnabled(true);
    onSelection.setEnabled(true);
    utensilSelection.setEnabled(true);
    detailText.setEnabled(true);
  }

  /**
   * Disables the buttons once new is pressed.
   */
  public void stepsDisableButtons()
  {
    add.setEnabled(false);
    detailText.setEnabled(false);
    actionSelection.setEnabled(false);
    delete.setEnabled(false);
    utensilSelection.setEnabled(false);
    onSelection.setEnabled(false);
  }
}
