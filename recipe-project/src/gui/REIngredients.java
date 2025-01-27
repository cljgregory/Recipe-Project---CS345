package gui;

import recipeBookComponents.Step;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JPopupMenu;
import recipeBookComponents.Ingredient;
import recipeBookComponents.Recipe;

/**
 * Recipe Editor Panel for Ingredients.
 * 
 * @author Ethan Pae & Colin Gregory
 * @version 4.0
 */
public class REIngredients extends JPanel
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
  @SuppressWarnings("unused")
  private static final String TAB = "\t";
  private static final String NLINE = "\n"; // Conforming to CheckStyle
  private static final String DELETE = "DELETE"; // Conforming to CheckStyle
  private Recipe recipe;
  @SuppressWarnings("unused")
  private JTextArea area;
  private RecipeEditor recipeEditor;

  private DefaultListModel<String> ingredientList; // Sprint 2 LIST
  private JList<String> myList; // Sprint 2 LIST

  private Ingredient selection;
  private double gramsPerMl = 0.0;
  private int caloriesPerHundredGrams = 0;

  private JTextField nameText;
  private JTextField detailText;
  private JTextField amountText;
  private JButton add;
  private JButton delete;
  private JComboBox<String> unitSelection;

  /**
   * Constructs the Ingredients panel for Recipe Editor.
   * 
   * @param recipe
   *          title
   * @param recipeEditor For communicating with the main frame and the other RE classes
   */
  public REIngredients(final Recipe recipe, final RecipeEditor recipeEditor)
  {
    this.recipeEditor = recipeEditor;
    this.recipe = recipe;
    initialize(); // Create the panel
    show(); // Display the panel
  }

  /**
   * Creates the Ingredients panel within Recipe Editor.
   */
  public void initialize()
  {
    // Initialize the head JPanel
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    Dimension borderSize = new Dimension(565, 245);
    this.setBorder(
        BorderFactory.createTitledBorder(new LineBorder(Color.BLACK), STRINGS.getString("INGRED")));
    this.setMaximumSize(borderSize);
    this.setPreferredSize(borderSize);
    this.setMinimumSize(borderSize);

    // Initialize the inner JPanel (Top half)
    JPanel innerPanel = new JPanel();
    innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));

    // Create components for innerPanel (Top half)
    nameText = new JTextField(5);
    detailText = new JTextField(5);
    amountText = new JTextField(5);
    unitSelection = new JComboBox<String>();
    add = new JButton(STRINGS.getString("ADD"));
    new ArrayList<Step>();

    // **COMBO BOX ITEMS**
    String[] comboBoxItems = {"", STRINGS.getString("CUPS"), STRINGS.getString("DRAMS"),
        STRINGS.getString("FL_OZ"), STRINGS.getString("GALLONS"), STRINGS.getString("GRAMS"),
        STRINGS.getString("ML"), STRINGS.getString("OZ"), STRINGS.getString("PINTS"),
        STRINGS.getString("PINCHES"), STRINGS.getString("POUNDS"), STRINGS.getString("TB"),
        STRINGS.getString("TSP"), STRINGS.getString("QUARTS")};
    for (int i = 0; i < comboBoxItems.length; i++)
    {
      unitSelection.addItem(comboBoxItems[i]);
    }

    // Resize everything
    nameText.setMaximumSize(TXT_BOX_STD);
    nameText.setPreferredSize(TXT_BOX_STD); // Maximum size fixes the horizontal sizes
    detailText.setMaximumSize(TXT_BOX_STD); // while preferred size seems to fix
    detailText.setPreferredSize(TXT_BOX_STD); // the vertical sides. Not sure why

    amountText.setMaximumSize(TXT_BOX_STD); // just from experimentation it seems
    amountText.setPreferredSize(TXT_BOX_STD); // to work that way.
    unitSelection.setMaximumSize(COMBO_BOX_STD);
    unitSelection.setPreferredSize(COMBO_BOX_STD); // Also fixed to a local constant for efficiency

    // Add components to innerPanel (Top half)
    innerPanel.add(new JLabel(STRINGS.getString("NAME_COLON"))); // Add string "Name: " to display
    innerPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Spacing
    innerPanel.add(nameText); // Add text entry prompting for "Name: "
    innerPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Spacing
    innerPanel.add(new JLabel(STRINGS.getString("DETAILS_COLON"))); // Add string "Details: " to
                                                                    // display
    innerPanel.add(detailText); // Add text entry prompting for "Details: "
    innerPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Spacing
    innerPanel.add(new JLabel(STRINGS.getString("AMOUNT_COLON"))); // Add string "Amount: " to
                                                                   // display
    innerPanel.add(amountText);
    innerPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Spacing
    innerPanel.add(new JLabel(STRINGS.getString("UNIT_COLON"))); // Unit combo box text display
    innerPanel.add(unitSelection); // The unit combo box itself
    innerPanel.add(Box.createRigidArea(new Dimension(5, 0))); // Spacing
    innerPanel.add(add); // Button for adding entry.

    // Add innerPanel (Top half)
    this.add(innerPanel);
    this.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing

    // Sprint 2 init LIST components
    ingredientList = new DefaultListModel<String>();
    myList = new JList<String>(ingredientList);
    myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane listPane = new JScrollPane(myList);
    listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    myList.setSize(10, 10); // do I need this??

    // Initialize components for innerPanel2 (Bottom half)
    delete = new JButton(STRINGS.getString(DELETE)); // Delete button

    // REIngredients
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
    // innerPanel2.add(areaPane);
    innerPanel2.add(listPane);
    innerPanel2.add(Box.createRigidArea(new Dimension(5, 0)));
    innerPanel2.add(delete);
    innerPanel2.add(Box.createRigidArea(new Dimension(70, 0)));

    this.add(innerPanel2);
    this.add(Box.createRigidArea(new Dimension(0, 10)));

    // These buttons will be diabled until the user hits new
    nameText.setEnabled(false);
    detailText.setEnabled(false);
    amountText.setEnabled(false);
    add.setEnabled(false);
    delete.setEnabled(false);
    unitSelection.setEnabled(false);

    // Add button functionality DUPLICATES NOT ALLOWED
    add.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(final ActionEvent e)
      {

        // Retrieve Variables
        String name = nameText.getText();
        String details = detailText.getText();

        Double amount = getAmount(amountText); // Method added for readability

        String unit = unitSelection.getSelectedItem().toString();
        selection = new Ingredient(name, details, amount, unit);

        // add when it DOES NOT have calorie info
        if (!(selection.getHasCalInfo()))
        {
          JPopupMenu popup = new JPopupMenu();
          initPopUp(popup); // flexible ingredients
          popup.show(innerPanel2, 0, innerPanel2.getHeight());
        }

        // Double check for duplicates
        Boolean duplicate = false;
        ArrayList<Ingredient> ingredients = new ArrayList<>(recipe.getIngredients());
        for (Ingredient ingredient : ingredients)
        {
          if (selection.toString().equals(ingredient.toString()))
          {
            duplicate = true;
          }
        }

        if (!(duplicate)) // List doesn't exist
        {
          recipe.addIngredient(selection);
          ingredientList.addElement(selection.toString()); // LIST adds elements to list
        }
        recipeEditor.setDocumentChanged();
        nameText.setText("");
        detailText.setText("");
        amountText.setText("");
        unitSelection.setSelectedIndex(0);
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
              Ingredient ingre = step.getIngredient();
              if (ingre != null)
              { // Add null check here
                String s = ingre.getName();
                if (selectedIngredient.contains(s))
                {
                  ingredientUsedInStep = true;
                  break;
                }
              }
            }
          }
          delete.setEnabled(!ingredientUsedInStep);
          recipeEditor.setDocumentChanged();
        }
      }
    });
    
    nameText.getDocument().addDocumentListener(new DocumentListener()
    {

      @Override
      public void removeUpdate(final DocumentEvent e)
      {
        if (nameText.getText().isEmpty())
        {
          add.setEnabled(false);
        }

      }

      @Override
      public void insertUpdate(final DocumentEvent e)
      {
        add.setEnabled(true);

      }

      @Override
      public void changedUpdate(final DocumentEvent e)
      {
        // Not used

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

        ArrayList<Ingredient> ingredients = new ArrayList<>(recipe.getIngredients());
        if (ingredients.size() > 0) // If there exists entries
        {
          // remove ingredient from the list
          String selectedIngredient = myList.getSelectedValue();
          if (selectedIngredient != null)
          { // if selection
            ingredientList.removeElement(selectedIngredient);
          }

          // remove ingredient from recipe IF to string matches selected ingredient
          Boolean firstRemoved = true;
          for (Ingredient ingredient : ingredients)
          {
            if (firstRemoved && selectedIngredient.contains(ingredient.toString()))
            {
              recipe.removeIngredient(ingredient);
              firstRemoved = false;
            }
          }
        }
        recipeEditor.setDocumentChanged();
      }
    });

  }

  /**
   * Creates the Ingredients popup.
   * 
   * @param popup
   *          the popup
   */
  public void initPopUp(final JPopupMenu popup)
  {
    // Create a new JFrame object
    JFrame popupFrame = new JFrame(STRINGS.getString("ADD_CALS_Q"));

    // Set the layout of the content pane
    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

    // Add the components to the content pane
    // calories information and panel
    JTextField caloriesField = new JTextField(10);
    caloriesField.setEditable(true);
    JLabel caloriesLabel = new JLabel(STRINGS.getString("CALS_PER_G"));
    JPanel caloriesPanel = new JPanel();
    caloriesPanel.add(caloriesLabel);
    caloriesPanel.add(caloriesField);

    // grams information and panel
    JTextField gramsField = new JTextField(10);
    gramsField.setEditable(true);
    JLabel gramsLabel = new JLabel(STRINGS.getString("G_PER_ML"));
    JPanel gramsPanel = new JPanel();
    gramsPanel.add(gramsLabel);
    gramsPanel.add(gramsField);

    // the buttons
    JButton addButton = new JButton(STRINGS.getString("ADD_CALS"));
    JButton declineButton = new JButton(STRINGS.getString(DELETE));
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addButton);
    buttonPanel.add(declineButton);

    // Vertically stacked popup panel
    JPanel popupPanel = new JPanel();
    popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));

    popupPanel.add(caloriesPanel);
    popupPanel.add(gramsPanel);
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
        // get the details from grams and calories field if entered
        gramsField.setEditable(true);
        caloriesField.setEditable(true);
        if (!(gramsField.getText().isEmpty()) && (!(caloriesField.getText().isEmpty())))
        {
          // set global fields
          try
          {
            gramsPerMl = Double.parseDouble(gramsField.getText()); // grams per ml
            caloriesPerHundredGrams = Integer.parseInt(caloriesField.getText()); // calories per 100
            selection.setCalsPerGram(caloriesPerHundredGrams);
            selection.setGramsPerMl(gramsPerMl);
            popupFrame.dispose();
          }
          catch (NumberFormatException nfe)
          {
            JOptionPane.showMessageDialog(null, STRINGS.getString("INPUT_NUMBER_PLZ"),
                STRINGS.getString("ERROR"), JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });

    // Add an ActionListener to the Close button to hide the JFrame
    declineButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(final ActionEvent e)
      {
        selection.setCalsPerGram(0);
        selection.setGramsPerMl(0.0);
        selection.setHasCalInfoFalse();
        popupFrame.dispose();
      }
    });

    ingredientList.addListDataListener(new ListDataListener()
    {

      @Override
      public void intervalRemoved(final ListDataEvent e)
      {
        // Not used

      }

      @Override
      public void intervalAdded(final ListDataEvent e)
      {
        // NOt used

      }

      @Override
      public void contentsChanged(final ListDataEvent e)
      {
        // Not used

      }
    });
  }

  /**
   * Displays the Ingredients Panel.
   */
  public void show()
  {
    this.setVisible(true);
  }

  /**
   * Ensures the text entered in amount can be parsed to a double.
   * 
   * @param amountTxt
   *          the text field with text inside it
   * @return the parsed value of text entered in text field
   */
  private Double getAmount(final JTextField amountTxt)
  {
    try // Check for valid double value in amount
    {
      return Double.parseDouble(amountTxt.getText());
    }
    catch (NumberFormatException j)
    {
      return Double.NaN;
    }
  }

  /**
   * Updates the ing list.
   * 
   * @param arrayList
   */
  public void updateIngredientsList(final ArrayList<Ingredient> arrayList)
  {

    // Add the existing ingredients from the recipe to the area JTextArea
    for (Ingredient entry : arrayList)
    {
      ingredientList.addElement(entry.toString() + NLINE);
      recipe.addIngredient(entry);
    }

  }

  /**
   * Gets the ing list.
   * 
   * @return ing list
   */
  public List<Ingredient> getIngredients()
  {
    return recipe.getIngredients();
  }

  /**
   * Clears the list.
   */
  public void resetPanel()
  {
    ingredientList.clear();
  }

  /**
   * Enables the buttons once new is pressed.
   */
  public void ingredientEnableButtons()
  {
    nameText.setEnabled(true);
    detailText.setEnabled(true);
    amountText.setEnabled(true);
    add.setEnabled(false);
    delete.setEnabled(true);
    unitSelection.setEnabled(true);
  }

  /**
   * Disables the buttons once new is pressed.
   */
  public void ingredientDisableButtons()
  {
    nameText.setEnabled(false);
    detailText.setEnabled(false);
    amountText.setEnabled(false);
    add.setEnabled(false);
    delete.setEnabled(false);
    unitSelection.setEnabled(false);
  }

}
